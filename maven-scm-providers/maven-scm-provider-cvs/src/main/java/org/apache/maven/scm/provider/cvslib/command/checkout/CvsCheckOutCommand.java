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

import org.apache.maven.scm.ScmException;
import org.apache.maven.scm.command.checkout.AbstractCheckOutCommand;
import org.apache.maven.scm.command.checkout.CheckOutScmResult;
import org.apache.maven.scm.provider.ScmProviderRepository;
import org.apache.maven.scm.provider.cvslib.command.CvsCommand;
import org.apache.maven.scm.provider.cvslib.repository.CvsScmProviderRepository;

import org.codehaus.plexus.util.cli.CommandLineException;
import org.codehaus.plexus.util.cli.CommandLineUtils;
import org.codehaus.plexus.util.cli.Commandline;

/**
 * @author <a href="mailto:evenisse@apache.org">Emmanuel Venisse </a>
 * @author <a href="mailto:trygvis@inamo.no">Trygve Laugst&oslash;l</a>
 * @version $Id$
 */
public class CvsCheckOutCommand
    extends AbstractCheckOutCommand
    implements CvsCommand
{
    protected CheckOutScmResult executeCheckOutCommand( ScmProviderRepository repo, File workingDirectory, String tag, File[] files )
        throws ScmException
    {
        CvsScmProviderRepository repository = (CvsScmProviderRepository) repo;

        Commandline cl = new Commandline();

        cl.setExecutable( "cvs" );

        cl.setWorkingDirectory( workingDirectory.getAbsolutePath() );

        cl.createArgument().setValue( "-d" );

        cl.createArgument().setValue( repository.getCvsRoot() );

        cl.createArgument().setValue( "-q" );

        cl.createArgument().setValue( "checkout" );

        if ( tag != null )
        {
            cl.createArgument().setValue( "-r" + tag );
        }

        cl.createArgument().setValue( repository.getModule() );

        CvsCheckOutConsumer consumer = new CvsCheckOutConsumer();

        CommandLineUtils.StringStreamConsumer stderr = new CommandLineUtils.StringStreamConsumer();

        int exitCode;

        try
        {
            exitCode = CommandLineUtils.executeCommandLine( cl, consumer, stderr );
        }
        catch( CommandLineException ex )
        {
            throw new ScmException( "Error while executing command.", ex );
        }

        if ( exitCode != 0 )
        {
            return new CheckOutScmResult( "The cvs command failed.", stderr.getOutput() );
        }

        return new CheckOutScmResult( consumer.getCheckedOutFiles() );
    }
}
