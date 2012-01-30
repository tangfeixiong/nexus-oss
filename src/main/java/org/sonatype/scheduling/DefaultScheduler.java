/**
 * Copyright (c) 2008 Sonatype, Inc. All rights reserved.
 *
 * This program is licensed to you under the Apache License Version 2.0,
 * and you may not use this file except in compliance with the Apache License Version 2.0.
 * You may obtain a copy of the Apache License Version 2.0 at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the Apache License Version 2.0 is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Apache License Version 2.0 for the specific language governing permissions and limitations there under.
 */
package org.sonatype.scheduling;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;
import org.codehaus.plexus.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonatype.scheduling.schedules.RunNowSchedule;
import org.sonatype.scheduling.schedules.Schedule;

/**
 * A simple facade to ScheduledThreadPoolExecutor as Plexus component.
 * 
 * @author cstamas
 */
@Component( role = Scheduler.class )
public class DefaultScheduler
    implements Scheduler
{
    private final Logger logger = LoggerFactory.getLogger( getClass() );

    @Requirement
    private TaskConfigManager taskConfig;

    private final AtomicInteger idGen;

    private final ScheduledThreadPoolExecutor scheduledExecutorService;

    private final ConcurrentHashMap<String, List<ScheduledTask<?>>> tasksMap;

    public DefaultScheduler()
    {
        idGen = new AtomicInteger( 0 );
        tasksMap = new ConcurrentHashMap<String, List<ScheduledTask<?>>>();
        scheduledExecutorService =
            (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool( 20, new PlexusThreadFactory(
                Thread.MIN_PRIORITY ) );
    }

    protected Logger getLogger()
    {
        return logger;
    }

    // ==

    public void initializeTasks()
    {
        getLogger().info( "Initializing Scheduler..." );

        // this call delegates to task config manager that loads up the persisted tasks (if any)
        // and performs a series of callbacks to this to make them "alive"
        taskConfig.initializeTasks( this );

        // wind up the "idGen" source, to the max ID we got loaded up from config (the generated IDs are persisted)
        int maxId = 0;
        for ( Map.Entry<String, List<ScheduledTask<?>>> entry : getAllTasks().entrySet() )
        {
            for ( ScheduledTask<?> task : entry.getValue() )
            {
                maxId = Math.max( maxId, Integer.parseInt( task.getId() ) );
            }
        }
        idGen.set( maxId );
    }

    public void shutdown()
    {
        getLogger().info( "Shutting down Scheduler..." );
        
        getScheduledExecutorService().shutdown();
        getScheduledExecutorService().setExecuteExistingDelayedTasksAfterShutdownPolicy( false );
        getScheduledExecutorService().setContinueExistingPeriodicTasksAfterShutdownPolicy( false );

        try
        {
            boolean stopped = getScheduledExecutorService().awaitTermination( 1, TimeUnit.SECONDS );

            if ( !stopped )
            {
                Map<String, List<ScheduledTask<?>>> runningTasks = getRunningTasks();

                if ( !runningTasks.isEmpty() )
                {
                    getScheduledExecutorService().shutdownNow();
                    getLogger().warn( "Scheduler shut down forcedly with tasks running." );
                }
                else
                {
                    getLogger().info( "Scheduler shut down cleanly with tasks scheduled." );
                }
            }
        }
        catch ( InterruptedException e )
        {
            getLogger().info( "Termination interrupted", e );
        }
    }

    @Deprecated
    public SchedulerTask<?> createTaskInstance( String taskType )
        throws IllegalArgumentException
    {
        return taskConfig.createTaskInstance( taskType );
    }

    public <T> T createTaskInstance( Class<T> taskType )
        throws IllegalArgumentException
    {
        return taskConfig.createTaskInstance( taskType );
    }

    public ScheduledThreadPoolExecutor getScheduledExecutorService()
    {
        return scheduledExecutorService;
    }

    protected <T> void addToTasksMap( ScheduledTask<T> task, boolean store )
    {
        tasksMap.putIfAbsent( task.getType(), new CopyOnWriteArrayList<ScheduledTask<?>>() );
        tasksMap.get( task.getType() ).add( task );

        if ( store )
        {
            taskConfig.addTask( task );
        }
    }

    protected <T> void removeFromTasksMap( ScheduledTask<T> task )
    {
        final List<ScheduledTask<?>> tasks = tasksMap.get( task.getType() );

        if ( tasks != null )
        {
            tasks.remove( task );

            // this is potentially problematic, might _remove_ concurrently added new task
            // but, this is only here to keep map keys small, but the keys (task types) are actually
            // rather small, so I see no point of pruning map for keys
            // if ( tasks.size() == 0 )
            // {
            // tasksMap.remove( task.getType() );
            // }
        }

        taskConfig.removeTask( task );
    }

    protected void taskRescheduled( ScheduledTask<?> task )
    {
        taskConfig.addTask( task );
    }

    protected String generateId()
    {
        return String.valueOf( idGen.incrementAndGet() );
    }

    public <T> ScheduledTask<T> initialize( String id, String name, String type, Callable<T> callable,
                                            Schedule schedule, boolean enabled )
    {
        return schedule( id, name, type, callable, schedule, enabled, false );
    }

    public ScheduledTask<Object> submit( String name, Runnable runnable )
    {
        return schedule( name, runnable, new RunNowSchedule() );
    }

    public ScheduledTask<Object> schedule( String name, Runnable runnable, Schedule schedule )
    {
        // use the name of the class as the type.
        return schedule( name, runnable.getClass().getSimpleName(), Executors.callable( runnable ), schedule );
    }

    public <T> ScheduledTask<T> submit( String name, Callable<T> callable )
    {
        return schedule( name, callable, new RunNowSchedule() );
    }

    public <T> ScheduledTask<T> schedule( String name, Callable<T> callable, Schedule schedule )
    {
        return schedule( name, callable.getClass().getSimpleName(), callable, schedule );
    }

    protected <T> ScheduledTask<T> schedule( String name, String type, Callable<T> callable, Schedule schedule )
    {
        return schedule( generateId(), name, type, callable, schedule, true );
    }

    protected <T> ScheduledTask<T> schedule( String id, String name, String type, Callable<T> callable,
                                             Schedule schedule, boolean enabled, boolean store )
    {
        DefaultScheduledTask<T> dct = new DefaultScheduledTask<T>( id, name, type, this, callable, schedule );
        dct.setEnabled( enabled );
        addToTasksMap( dct, store );
        dct.start();
        return dct;
    }

    protected <T> ScheduledTask<T> schedule( String id, String name, String type, Callable<T> callable,
                                             Schedule schedule, boolean store )
    {
        return schedule( id, name, type, callable, schedule, true, store );
    }

    public <T> ScheduledTask<T> updateSchedule( ScheduledTask<T> task )
        throws RejectedExecutionException, NullPointerException
    {
        // Simply add the task to config, will find existing by id, remove, then store new
        taskConfig.addTask( task );
        return task;
    }

    // ==

    public Map<String, List<ScheduledTask<?>>> getAllTasks()
    {
        Map<String, List<ScheduledTask<?>>> result = new HashMap<String, List<ScheduledTask<?>>>( tasksMap.size() );

        for ( Map.Entry<String, List<ScheduledTask<?>>> entry : tasksMap.entrySet() )
        {
            if ( !entry.getValue().isEmpty() )
            {
                result.put( entry.getKey(), new ArrayList<ScheduledTask<?>>( entry.getValue() ) );
            }
        }

        return result;
    }

    public ScheduledTask<?> getTaskById( String id )
        throws NoSuchTaskException
    {
        if ( StringUtils.isEmpty( id ) )
        {
            throw new IllegalArgumentException( "The Tasks cannot have null IDs!" );
        }

        final Collection<List<ScheduledTask<?>>> activeTasks = getAllTasks().values();

        for ( List<ScheduledTask<?>> tasks : activeTasks )
        {
            for ( ScheduledTask<?> task : tasks )
            {
                if ( task.getId().equals( id ) )
                {
                    return task;
                }
            }
        }

        throw new NoSuchTaskException( id );
    }

    public Map<String, List<ScheduledTask<?>>> getActiveTasks()
    {
        Map<String, List<ScheduledTask<?>>> result = getAllTasks();

        List<ScheduledTask<?>> tasks = null;

        // filter for activeOrSubmitted
        for ( Iterator<String> c = result.keySet().iterator(); c.hasNext(); )
        {
            String cls = c.next();
            tasks = result.get( cls );

            for ( Iterator<ScheduledTask<?>> i = tasks.iterator(); i.hasNext(); )
            {
                ScheduledTask<?> task = i.next();

                if ( !task.getTaskState().isActiveOrSubmitted() )
                {
                    i.remove();
                }
            }

            if ( tasks.isEmpty() )
            {
                c.remove();
            }
        }

        return result;
    }

    public Map<String, List<ScheduledTask<?>>> getRunningTasks()
    {
        Map<String, List<ScheduledTask<?>>> result = getAllTasks();
        List<ScheduledTask<?>> tasks = null;

        // filter for RUNNING
        for ( Iterator<String> c = result.keySet().iterator(); c.hasNext(); )
        {
            String cls = c.next();
            tasks = result.get( cls );

            for ( Iterator<ScheduledTask<?>> i = tasks.iterator(); i.hasNext(); )
            {
                ScheduledTask<?> task = i.next();

                if ( !TaskState.RUNNING.equals( task.getTaskState() ) )
                {
                    i.remove();
                }
            }

            if ( tasks.isEmpty() )
            {
                c.remove();
            }
        }

        return result;
    }
}
