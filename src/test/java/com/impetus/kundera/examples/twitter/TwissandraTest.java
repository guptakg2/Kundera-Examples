/*
 * Copyright 2011 Impetus Infotech.
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
package com.impetus.kundera.examples.twitter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.impetus.kundera.examples.twitter.query.CassandraQuerySuite;

/**
 * Test case for Cassandra.
 * 
 * @author amresh.singh
 */
public class TwissandraTest extends CassandraQuerySuite
{

    /** The Constant LOG. */
    private static final Log LOG = LogFactory.getLog(TwissandraTest.class);


    @Override
    protected void setUp() throws Exception
    {
       
        setUpInternal("twissandra");
    }

    /**
     * Test on execute.
     */
    public void testOnExecute() throws Exception
    {
       executeTestSuite();
    }

    /**
     * Test on execute query.
     */
    
   /*public void testOnQuery()
    {
        executeQuerySuite();
    }*/
    

    @Override
    protected void tearDown() throws Exception
    {
        tearDownInternal();
    } 
}
