package org.apache.maven.scm.provider.perforce.command.changelog;

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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

import junit.framework.TestCase;

import org.apache.maven.scm.command.changelog.ChangeLogEntry;

/**
 * @author <a href="mailto:evenisse@apache.org">Emmanuel Venisse</a>
 * @version $Id$
 */
public class PerforceChangeLogConsumerTest extends TestCase
{
	/** the {@link PerforceChangeLogConsumer} used for testing */
	private PerforceChangeLogConsumer instance;
	/** file with test results to check against */
	private String testFile;

	/**
	 * Create a test with the given name
	 * @param testName the name of the test
	 */
	public PerforceChangeLogConsumerTest(String testName)
	{
		super(testName);
	}

	/**
	 * Initialize per test data
	 * @throws Exception when there is an unexpected problem
	 */
	public void setUp() throws Exception
	{
		String baseDir = System.getProperty("basedir");
		assertNotNull("The system property basedir was not defined.", baseDir);
		testFile = baseDir + "/src/test/resources/perforce/perforcelog.txt";
		instance = new PerforceChangeLogConsumer();
	}

	/**
	 * Test of parse method
	 * @throws Exception when there is an unexpected problem
	 */
	public void testParse() throws Exception
	{
		FileInputStream fis = new FileInputStream(testFile);
		BufferedReader in = new BufferedReader(new InputStreamReader(fis));
		String s = in.readLine();
		while ( s != null )
		{
			instance.consumeLine( s );
			s = in.readLine();
		}

		ArrayList entries = new ArrayList(instance.getModifications());
		assertEquals("Wrong number of entries returned", 7, entries.size());
        ChangeLogEntry entry = (ChangeLogEntry) entries.get(0);
        assertEquals("Entry 0 was parsed incorrectly", 
                     "\t<changelog-entry>\n" +
                     "\t\t<date>2003-10-15</date>\n" +
                     "\t\t<time>13:38:40</time>\n" +
                     "\t\t<author><![CDATA[jim]]></author>\n" +
                     "\t\t<file>\n" +
                     "\t\t\t<name>//depot/test/junk/linefeed.txt</name>\n" +
                     "\t\t\t<revision>3</revision>\n" +
                     "\t\t</file>\n" +
                     "\t\t<msg><![CDATA[	Where's my change #\n" +
                     "]]></msg>\n" +
                     "\t</changelog-entry>\n",
                     entry.toXML());

        entry = (ChangeLogEntry) entries.get(3);
        assertEquals("Entry 3 was parsed incorrectly", 
                     "\t<changelog-entry>\n" +
                     "\t\t<date>2003-10-01</date>\n" +
                     "\t\t<time>16:24:20</time>\n" +
                     "\t\t<author><![CDATA[jim]]></author>\n" +
                     "\t\t<file>\n" +
                     "\t\t\t<name>//depot/test/demo/demo.c</name>\n" +
                     "\t\t\t<revision>4</revision>\n" +
                     "\t\t</file>\n" +
                     "\t\t<msg><![CDATA[	Backing out my test changes\n" +
                     "\t\n" +
                     "\tUpdating a description\n" +
                     "]]></msg>\n" +
                     "\t</changelog-entry>\n",
                     entry.toXML());

        entry = (ChangeLogEntry) entries.get(6);
        assertEquals("Entry 6 was parsed incorrectly", 
                     "\t<changelog-entry>\n" +
                     "\t\t<date>2003-08-07</date>\n" +
                     "\t\t<time>17:21:57</time>\n" +
                     "\t\t<author><![CDATA[mcronin]]></author>\n" +
                     "\t\t<file>\n" +
                     "\t\t\t<name>//depot/test/demo/demo.c</name>\n" +
                     "\t\t\t<revision>1</revision>\n" +
                     "\t\t</file>\n" +
                     "\t\t<file>\n" +
                     "\t\t\t<name>//depot/test/demo/dictcalls.txt</name>\n" +
                     "\t\t\t<revision>1</revision>\n" +
                     "\t\t</file>\n" +
                     "\t\t<file>\n" +
                     "\t\t\t<name>//depot/test/demo/dictwords.txt</name>\n" +
                     "\t\t\t<revision>1</revision>\n" +
                     "\t\t</file>\n" +
                     "\t\t<msg><![CDATA[	demonstration of Perforce on Windows, Unix and VMS.\n" +
                     "]]></msg>\n" +
                     "\t</changelog-entry>\n",
                     entry.toXML());
	}
}
