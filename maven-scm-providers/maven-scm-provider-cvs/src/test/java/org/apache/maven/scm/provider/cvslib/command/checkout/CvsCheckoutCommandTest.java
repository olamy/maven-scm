package org.apache.maven.scm.provider.cvslib.command.checkout;

/* ====================================================================
 * Copyright 2003-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ====================================================================
 */

import java.io.File;
import java.util.List;

import org.apache.maven.scm.ScmFile;
import org.apache.maven.scm.ScmFileStatus;
import org.apache.maven.scm.command.checkout.CheckOutScmResult;
import org.apache.maven.scm.manager.ScmManager;
import org.apache.maven.scm.provider.cvslib.AbstractCvsScmTest;

/**
 * @author <a href="mailto:evenisse@apache.org">Emmanuel Venisse</a>
 * @author <a href="mailto:trygvis@inamo.no">Trygve Laugst&oslash;l</a>
 * @version $Id$
 */
public class CvsCheckoutCommandTest
    extends AbstractCvsScmTest
{
    protected String getModule()
    {
        return "test-repo/checkout";
    }

    public void testCheckOutWithoutTag()
    	throws Exception
    {
        ScmManager scmManager = getScmManager();

        CheckOutScmResult result = scmManager.checkOut( getScmUrl(), getWorkingDirectory(), null );

        if ( !result.isSuccess() )
        {
            fail( result.getMessage() + "\n" + result.getLongMessage() );
        }

        List files = result.getCheckedOutFiles();

        assertNotNull( files );

        assertEquals( 3, files.size() );

        assertCheckedOutFile( files, 0, "Foo.java", ScmFileStatus.UPDATED );

        assertCheckedOutFile( files, 1, "Readme.txt", ScmFileStatus.UPDATED );

        assertCheckedOutFile( files, 2, "src/java/org/apache/maven/MavenUtils.java", ScmFileStatus.UPDATED );
    }

    public void testCheckOutWithTag()
    	throws Exception
    {
        ScmManager scmManager = getScmManager();

        CheckOutScmResult result = scmManager.checkOut( getScmUrl(), getWorkingDirectory(), "1.107.4" );

        if ( !result.isSuccess() )
        {
            fail( result.getMessage() + "\n" + result.getLongMessage() );
        }

        List files = result.getCheckedOutFiles();

        assertNotNull( files );

        assertEquals( 1, files.size() );

        File mavenUtils = assertCheckedOutFile( files, 0, "src/java/org/apache/maven/MavenUtils.java", ScmFileStatus.UPDATED );

        assertEquals( 38403, mavenUtils.length() );
    }

    // ----------------------------------------------------------------------
    // 
    // ----------------------------------------------------------------------

    private File assertCheckedOutFile( List files, int i, String fileName, ScmFileStatus status )
    {
        File file = new File( getWorkingDirectory() + "/test-repo/checkout/" + fileName );

        assertTrue( file.getAbsolutePath() + " file doesn't exist.", file.exists() );

        ScmFile coFile = (ScmFile) files.get( i );

        assertSame( status, coFile.getStatus() );

        assertEquals( "test-repo/checkout/" + fileName, coFile.getPath() );

        return file;
    }
}
