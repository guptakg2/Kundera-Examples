/*******************************************************************************
 * * Copyright 2011 Impetus Infotech.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 ******************************************************************************/
package com.impetus.kundera.examples.crossdatastore.useraddress;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.apache.cassandra.thrift.CfDef;
import org.apache.cassandra.thrift.ColumnDef;
import org.apache.cassandra.thrift.IndexType;
import org.apache.cassandra.thrift.InvalidRequestException;
import org.apache.cassandra.thrift.KsDef;
import org.apache.cassandra.thrift.NotFoundException;
import org.apache.cassandra.thrift.SchemaDisagreementException;
import org.apache.cassandra.thrift.TimedOutException;
import org.apache.cassandra.thrift.UnavailableException;
import org.apache.thrift.TException;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.impetus.kundera.examples.cli.CassandraCli;
import com.impetus.kundera.examples.crossdatastore.useraddress.entities.HabitatUni1To1FK;
import com.impetus.kundera.examples.crossdatastore.useraddress.entities.PersonnelUni1To1FK;

/**
 * One to one association test for {@see} cassandra, mongodb,HBase and RDBMS
 * combination.
 * 
 * Script to create super column family
 * 
 * Note: As PersonnelUni1To1FK is holding embedded collection so to test as a
 * super col family. create super column family as given below: create column
 * family PERSONNEL with comparator=UTF8Type and
 * default_validation_class=UTF8Type and key_validation_class=UTF8Type and
 * column_type=Super;
 * 
 * 
 * Note: To create as column family PERSONNEL use as given below: create column
 * family PERSONNEL with comparator=UTF8Type and column_metadata=[{column_name:
 * PERSON_NAME, validation_class: UTF8Type, index_type: KEYS}, {column_name:
 * AGE, validation_class: IntegerType, index_type: KEYS}];
 * 
 * @author vivek.mishra
 */

public class OTOUniAssociationTest extends TwinAssociation
{

    /**
     * Inits the.
     */
    @BeforeClass
    public static void init() throws Exception
    {
        CassandraCli.cassandraSetUp();
        List<Class> clazzz = new ArrayList<Class>(2);
        clazzz.add(PersonnelUni1To1FK.class);
        clazzz.add(HabitatUni1To1FK.class);
        init(clazzz, /* "rdbms", */"twingo", "twissandra", "twibase");
    }

    /**
     * Sets the up.
     * 
     * @throws Exception
     *             the exception
     */
    @Before
    public void setUp() throws Exception
    {
        setUpInternal("ADDRESS", "PERSONNEL");
    }

    /**
     * Test insert.
     */
    @Test
    public void testInsert()
    {
        tryOperation();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.impetus.kundera.examples.crossdatastore.useraddress.TwinAssociation
     * #insert()
     */
    protected void insert()
    {
        try
        {
            PersonnelUni1To1FK person = new PersonnelUni1To1FK();
            HabitatUni1To1FK address = new HabitatUni1To1FK();
            person.setPersonId("unionetoonefk_1");
            person.setPersonName("Amresh");
            // person.setPersonalData(new PersonalData("www.amresh.com",
            // "amry.ks@gmail.com", "xamry"));
            address.setAddressId("unionetoonefk_a");
            address.setStreet("123, New street");
            person.setAddress(address);
            dao.insert(person);
            col.add(person);
            col.add(address);
        }
        catch (Exception e)
        {
            Assert.fail();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.impetus.kundera.examples.crossdatastore.useraddress.TwinAssociation
     * #find()
     */
    protected void find()
    {
        // Find Person
        try
        {
            PersonnelUni1To1FK p = (PersonnelUni1To1FK) dao.findPerson(PersonnelUni1To1FK.class, "unionetoonefk_1");
            Assert.assertNotNull(p);
            Assert.assertEquals("unionetoonefk_1", p.getPersonId());
            Assert.assertEquals("Amresh", p.getPersonName());
            // PersonalData pd = p.getPersonalData();
            // Assert.assertNotNull(pd);
            // Assert.assertEquals("www.amresh.com", pd.getWebsite());

            HabitatUni1To1FK add = p.getAddress();
            Assert.assertNotNull(add);
            Assert.assertNotNull(add.getAddressId());
            Assert.assertEquals("unionetoonefk_a", add.getAddressId());
            Assert.assertEquals("123, New street", add.getStreet());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Test merge.
     */
    // @Test
    public void testMerge()
    {

    }

    /**
     * Tear down.
     * 
     * @throws Exception
     *             the exception
     */
    @After
    public void tearDown() throws Exception
    {
        tearDownInternal();
        CassandraCli.dropKeySpace("KunderaExamples");
    }

    @Override
    public void loadDataForPERSONNEL() throws InvalidRequestException, TException, SchemaDisagreementException
    {

        KsDef ksDef = null;

        CfDef cfDef = new CfDef();
        cfDef.name = "PERSONNEL";
        cfDef.keyspace = "KunderaExamples";
        cfDef.column_type = "Super";
        cfDef.setComparator_type("UTF8Type");
        cfDef.setDefault_validation_class("UTF8Type");
        ColumnDef columnDef = new ColumnDef(ByteBuffer.wrap("PERSON_NAME".getBytes()), "UTF8Type");
        cfDef.addToColumn_metadata(columnDef);

        List<CfDef> cfDefs = new ArrayList<CfDef>();
        cfDefs.add(cfDef);

        try
        {
            ksDef = com.impetus.kundera.examples.cli.CassandraCli.client.describe_keyspace("KunderaExamples");
            CassandraCli.client.set_keyspace("KunderaExamples");

            List<CfDef> cfDefn = ksDef.getCf_defs();

            // CassandraCli.client.set_keyspace("KunderaExamples");
            for (CfDef cfDef1 : cfDefn)
            {

                if (cfDef1.getName().equalsIgnoreCase("PERSONNEL"))
                {

                    CassandraCli.client.system_drop_column_family("PERSONNEL");

                }
            }
            CassandraCli.client.system_add_column_family(cfDef);

        }
        catch (NotFoundException e)
        {

            ksDef = new KsDef("KunderaExamples", "org.apache.cassandra.locator.SimpleStrategy", cfDefs);
            ksDef.setReplication_factor(1);
            CassandraCli.client.system_add_keyspace(ksDef);
        }

        CassandraCli.client.set_keyspace("KunderaExamples");

    }

    @Override
    public void loadDataForHABITAT() throws TException, InvalidRequestException, UnavailableException,
            TimedOutException, SchemaDisagreementException
    {

        KsDef ksDef = null;
        CfDef cfDef2 = new CfDef();
        cfDef2.name = "ADDRESS";
        cfDef2.keyspace = "KunderaExamples";

        ColumnDef columnDef2 = new ColumnDef(ByteBuffer.wrap("STREET".getBytes()), "UTF8Type");
        columnDef2.index_type = IndexType.KEYS;
        cfDef2.addToColumn_metadata(columnDef2);

        List<CfDef> cfDefs = new ArrayList<CfDef>();
        cfDefs.add(cfDef2);

        try
        {
            ksDef = CassandraCli.client.describe_keyspace("KunderaExamples");
            CassandraCli.client.set_keyspace("KunderaExamples");
            List<CfDef> cfDefss = ksDef.getCf_defs();
            // CassandraCli.client.set_keyspace("KunderaExamples");
            for (CfDef cfDef : cfDefss)
            {

                if (cfDef.getName().equalsIgnoreCase("ADDRESS"))
                {

                    CassandraCli.client.system_drop_column_family("ADDRESS");

                }
            }
            CassandraCli.client.system_add_column_family(cfDef2);
        }
        catch (NotFoundException e)
        {

            ksDef = new KsDef("KunderaExamples", "org.apache.cassandra.locator.SimpleStrategy", cfDefs);

            ksDef.setReplication_factor(1);
            CassandraCli.client.system_add_keyspace(ksDef);

        }
        CassandraCli.client.set_keyspace("KunderaExamples");

    }

}