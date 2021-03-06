package org.apache.maven.scm.provider.clearcase;

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
public class ClearcaseScmTest
    extends ScmTestCase
{
    public ClearcaseScmTest( String name )
    {
        super( name );
    }

    protected String getRepositoryDelimiter()
    {
        return ":";
    }

    protected String getSupportedScm()
    {
        return "clearcase";
    }

    protected String getRepositoryUrl()
    {
        return "scm:clearcase:";
    }

    protected String getRepositoryClassName()
    {
        return "org.apache.maven.scm.provider.clearcase.repository.ClearcaseRepository";
    }

    protected String getCommandWrapperClassName()
    {
        return "org.apache.maven.scm.provider.clearcase.command.ClearcaseCommandWrapper";
    }
}