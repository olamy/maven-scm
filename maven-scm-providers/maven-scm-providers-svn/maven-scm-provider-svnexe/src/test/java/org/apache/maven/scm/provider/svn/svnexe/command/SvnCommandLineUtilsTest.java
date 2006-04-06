package org.apache.maven.scm.provider.svn.svnexe.command;

/*
 * Copyright 2001-2006 The Apache Software Foundation.
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
 */

import junit.framework.TestCase;
import org.apache.maven.scm.provider.svn.repository.SvnScmProviderRepository;

import java.io.File;

/**
 * @author <a href="mailto:evenisse@apache.org">Emmanuel Venisse</a>
 * @version $Id$
 */
public class SvnCommandLineUtilsTest
    extends TestCase
{
    public void testCryptPassword()
    {
        SvnScmProviderRepository repo =
            new SvnScmProviderRepository( "https://svn.apache.org/repos/asf/maven/scm/trunk", "username", "password" );
        String clString =
            SvnCommandLineUtils.cryptPassword( SvnCommandLineUtils.getBaseSvnCommandLine( new File( "." ), repo ) );
        assertEquals( "svn --username username --password ***** --non-interactive", clString );

        repo = new SvnScmProviderRepository( "https://svn.apache.org/repos/asf/maven/scm/trunk", "username", null );
        clString =
            SvnCommandLineUtils.cryptPassword( SvnCommandLineUtils.getBaseSvnCommandLine( new File( "." ), repo ) );
        assertEquals( "svn --username username --non-interactive", clString );
    }
}