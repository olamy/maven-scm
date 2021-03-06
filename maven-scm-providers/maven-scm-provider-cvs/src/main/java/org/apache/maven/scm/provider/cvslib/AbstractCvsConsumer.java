package org.apache.maven.scm.provider.cvslib;

/*
 * LICENSE
 */

import java.util.ArrayList;
import java.util.List;

import org.apache.maven.scm.ScmFile;
import org.apache.maven.scm.ScmFileStatus;

import org.codehaus.plexus.util.cli.StreamConsumer;

/**
 * @author <a href="mailto:trygvis@inamo.no">Trygve Laugst&oslash;l</a>
 * @author <a href="mailto:trygvis@inamo.no">Trygve Laugst&oslash;l</a>
 * @version $Id$
 */
public class AbstractCvsConsumer
    implements StreamConsumer
{
    private List files = new ArrayList();

    private List allowedCodes = new ArrayList();

    public AbstractCvsConsumer()
    {
    }

    // ----------------------------------------------------------------------
    // StreamConsumer Implementation
    // ----------------------------------------------------------------------

    public void consumeLine( String line )
    {
        if ( line.length() < 3 )
        {
            System.err.println( "Unable to parse output from command: line length must be bigger than 3." );
        }

        String status = line.substring( 0, 2 );

        String file = line.substring( 2 );

        if ( !allowedCodes.contains( status ) )
        {
            System.err.println( "Unexpected file status: '" + status.charAt( 0 ) + "'." );

            return;
        }

        if ( status.equals( "U " ) )
        {
            files.add( new ScmFile( file, ScmFileStatus.UPDATED ) );
        }
        else if ( status.equals( "P " ) )
        {
            files.add( new ScmFile( file, ScmFileStatus.PATCHED ) );
        }
        else if ( status.equals( "C " ) )
        {
            files.add( new ScmFile( file, ScmFileStatus.CONFLICT ) );
        }
        else
        {
            System.err.println( "Unknown status: '" + status + "'." );
        }
    }

    public List getFiles()
    {
        return files;
    }

    // ----------------------------------------------------------------------
    // 
    // ----------------------------------------------------------------------

    public void allowCode( char code )
    {
        allowedCodes.add( Character.toString( Character.toUpperCase( code ) ) + " " );
    }
}
