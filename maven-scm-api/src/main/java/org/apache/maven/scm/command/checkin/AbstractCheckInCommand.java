package org.apache.maven.scm.command.checkin;

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

import org.apache.maven.scm.CommandParameter;
import org.apache.maven.scm.CommandParameters;
import org.apache.maven.scm.ScmException;
import org.apache.maven.scm.ScmResult;
import org.apache.maven.scm.command.AbstractCommand;
import org.apache.maven.scm.provider.ScmProviderRepository;




/**
 * @author <a href="mailto:evenisse@apache.org">Emmanuel Venisse</a>
 * @author <a href="mailto:trygvis@inamo.no">Trygve Laugst&oslash;l</a>
 * @version $Id$
 */
public abstract class AbstractCheckInCommand
    extends AbstractCommand
{
    public final static String NAME = "check-in";

    protected abstract CheckInScmResult executeCheckInCommand( ScmProviderRepository repository, File workingDirectory, String message, String tag, File[] files )
        throws ScmException;

    public ScmResult executeCommand( ScmProviderRepository repository, File workingDirectory, CommandParameters parameters )
        throws ScmException
    {
        String message = parameters.getString( CommandParameter.MESSAGE );

        String tag = parameters.getString( CommandParameter.TAG, null );

        File[] files = parameters.getFileArray( CommandParameter.FILES, new File[ 0 ] );

        return executeCheckInCommand( repository, workingDirectory, message, tag, files );
    }
}
