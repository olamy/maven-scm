package org.apache.maven.scm.provider.cvslib;

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

import org.apache.maven.scm.ScmTestCase;

/**
 * @author <a href="mailto:evenisse@apache.org">Emmanuel Venisse</a>
 * @version $Id$
 */
public class CvsScmTest
    extends ScmTestCase
{
    public CvsScmTest(String name)
    {
        super(name);
    }
    
    public void setupRepository()
        throws Exception
    {
        repositoryInfo.setPassword("myPassword");
    }

    protected String getRepositoryDelimiter()
    {
        return ":";
    }

    protected String getSupportedScm()
    {
        return "cvs";
    }

    protected String getRepositoryUrl()
    {
        return "scm:cvs:pserver:anonymous@cvs.apache.org:/home/cvspublic:maven";
    }

    protected String getRepositoryClassName()
    {
        return "org.apache.maven.scm.provider.cvslib.repository.CvsRepository";
    }

    protected String getCommandWrapperClassName()
    {
        return "org.apache.maven.scm.provider.cvslib.command.CvsCommandWrapper";
    }
}
