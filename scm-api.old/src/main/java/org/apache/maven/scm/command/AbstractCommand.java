package org.apache.maven.scm.command;

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
import java.io.PrintWriter;

import org.apache.maven.scm.ScmException;
import org.apache.maven.scm.util.Commandline;
import org.apache.maven.scm.util.DefaultConsumer;
import org.apache.maven.scm.util.StreamConsumer;
import org.apache.maven.scm.util.StreamPumper;

/**
 * @author <a href="mailto:evenisse@apache.org">Emmanuel Venisse</a>
 * @version $Id$
 */
public abstract class AbstractCommand implements Command
{
    private String workingDir;
    private StreamConsumer consumer = new DefaultConsumer();
    
    public void setWorkingDirectory(String workingDir)
    {
        File dir = new File(workingDir);
        dir.mkdirs();
        this.workingDir = workingDir;
    }

    public String getWorkingDirectory()
    {
        return workingDir;
    }

    /* (non-Javadoc)
     * @see org.apache.maven.scm.command.Command#execute()
     */
    public void execute() throws Exception
    {
        try
        {
            Commandline cl = getCommandLine();
            if (workingDir != null)
            {
                cl.setWorkingDirectory(workingDir);
            }
            Process p = cl.execute();
            StreamPumper errorPumper =
                new StreamPumper(
                    p.getErrorStream(),
                    new PrintWriter(System.err, true));
            new Thread(errorPumper).start();
            StreamPumper inputPumper =
                new StreamPumper(
                    p.getInputStream(),
                    getConsumer());
            
            errorPumper.start();
            inputPumper.start();
            int exitVal = p.waitFor();
            System.out.println("ExitValue: " + exitVal);        
        }
        catch (Exception e)
        {
            //log.error(NAME + " command failed.", e);
        }
    }

    public void setConsumer(StreamConsumer consumer) throws ScmException
    {
        this.consumer = consumer;
    }

    public StreamConsumer getConsumer()
    {
        return consumer;
    }
}
