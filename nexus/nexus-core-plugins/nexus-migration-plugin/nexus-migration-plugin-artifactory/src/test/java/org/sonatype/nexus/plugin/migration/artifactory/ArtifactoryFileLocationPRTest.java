package org.sonatype.nexus.plugin.migration.artifactory;

import java.io.File;

import org.codehaus.plexus.context.Context;
import org.junit.Assert;
import org.junit.Test;
import org.sonatype.nexus.AbstractNexusTestCase;
import org.sonatype.nexus.plugin.migration.artifactory.dto.FileLocationRequestDTO;
import org.sonatype.nexus.plugin.migration.artifactory.dto.FileLocationResource;
import org.sonatype.nexus.plugin.migration.artifactory.dto.GroupResolutionDTO;
import org.sonatype.nexus.plugin.migration.artifactory.dto.MigrationSummaryDTO;
import org.sonatype.nexus.plugin.migration.artifactory.dto.MigrationSummaryResponseDTO;
import org.sonatype.plexus.rest.resource.PlexusResource;

public class ArtifactoryFileLocationPRTest
    extends AbstractNexusTestCase
{

    @Override
    protected void customizeContext( Context ctx )
    {
        super.customizeContext( ctx );

        try
        {
            System.setProperty( "plexus." + WORK_CONFIGURATION_KEY, (String) ctx.get( WORK_CONFIGURATION_KEY ) );
        }
        catch ( Exception e )
        {
            throw new RuntimeException( e );
        }
    }

    @Test
    public void testPost()
        throws Exception
    {
        PlexusResource resource = this.lookup( PlexusResource.class, "artifactoryFileLocation" );

        FileLocationRequestDTO dto = new FileLocationRequestDTO();
        FileLocationResource fileLocationResource = new FileLocationResource();
        dto.setData( fileLocationResource );

        File backupFile = new File( "./target/test-classes/backup-files/artifactory130.zip" ).getCanonicalFile();
        System.out.println( "backupFile.getAbsolutePath(): " + backupFile.getAbsolutePath() );

        fileLocationResource.setFileLocation( backupFile.getAbsolutePath() );

        MigrationSummaryResponseDTO result = (MigrationSummaryResponseDTO) resource.post( null, null, null, dto );
        MigrationSummaryDTO resultDto = result.getData();

        Assert.assertEquals( backupFile, new File( resultDto.getBackupLocation() ) );

        // Nexus 1832
        GroupResolutionDTO repo = resultDto.getGroupResolution( "repo" );
        Assert.assertNotNull( repo );
        Assert.assertEquals( "repo", repo.getGroupId() );
    }

}
