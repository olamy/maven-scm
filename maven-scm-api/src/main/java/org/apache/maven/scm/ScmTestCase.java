package org.apache.maven.scm;

import java.io.File;

import org.apache.maven.scm.manager.ScmManager;
import org.apache.maven.scm.repository.ScmRepository;

import junit.framework.TestCase;

import org.codehaus.plexus.PlexusTestCase;
import org.codehaus.plexus.util.FileUtils;
import org.codehaus.plexus.util.cli.CommandLineUtils;
import org.codehaus.plexus.util.cli.Commandline;
import org.codehaus.plexus.util.cli.CommandLineUtils.StringStreamConsumer;


/**
 * @author <a href="mailto:jason@maven.org">Jason van Zyl</a>
 * @version $Id$
 */
public abstract class ScmTestCase
    extends PlexusTestCase
{
    private boolean debugExecute;

    public ScmTestCase()
    {
    }

    protected String getModule()
    {
        fail( "getModule() must be overridden." );

        return null;
    }

    // ----------------------------------------------------------------------
    //
    // ----------------------------------------------------------------------

    protected void setUp()
        throws Exception
    {
        super.setUp();

        FileUtils.deleteDirectory( getWorkingDirectory() );
    }

    protected ScmManager getScmManager()
        throws Exception
    {
        return (ScmManager) lookup( ScmManager.ROLE );
    }

    protected ScmRepository makeScmRepository( String scmUrl )
    	throws Exception
    {
        return getScmManager().makeScmRepository( scmUrl );
    }

    // ----------------------------------------------------------------------
    // Utility Methods
    // ----------------------------------------------------------------------

    protected File getRepository()
    {
        return getTestFile( "/src/test/repository" );
    }

    protected File getWorkingDirectory()
    {
        String testName = this.getClass().getName();

        String caseName = ((TestCase)this).getName();

        return getTestFile( "target/workingDirectory/" + testName + "/" + caseName );
    }

    protected void setDebugExecute( boolean debugExecute )
    {
        this.debugExecute = debugExecute;
    }

    protected void execute( File workingDirectory, String executable, String arguments )
        throws Exception
    {
        Commandline cl = new Commandline();

        cl.setExecutable( executable );

        cl.setWorkingDirectory( workingDirectory.getAbsolutePath() );

        cl.addArguments( Commandline.translateCommandline( arguments ) );

        StringStreamConsumer stdout = new CommandLineUtils.StringStreamConsumer();

        StringStreamConsumer stderr = new CommandLineUtils.StringStreamConsumer();

        int exitValue = CommandLineUtils.executeCommandLine( cl, stdout, stderr );

        if ( debugExecute || exitValue != 0 )
        {
            System.err.println( "-----------------------------------------" );
            System.err.println( "Standard output: " );
            System.err.println( "-----------------------------------------" );
            System.err.println( stdout.getOutput() );
            System.err.println( "-----------------------------------------" );

            System.err.println( "Standard error: " );
            System.err.println( "-----------------------------------------" );
            System.err.println( stderr.getOutput() );
            System.err.println( "-----------------------------------------" );
        }

        if ( exitValue != 0 )
        {
            fail( "Exit value wasn't 0, was:" + exitValue );
        }
    }
}
