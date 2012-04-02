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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.impetus.kundera.examples.crossdatastore.useraddress.entities.HabitatUni1ToM;
import com.impetus.kundera.examples.crossdatastore.useraddress.entities.PersonalData;
import com.impetus.kundera.examples.crossdatastore.useraddress.entities.PersonnelUni1To1FK;
import com.impetus.kundera.examples.crossdatastore.useraddress.entities.PersonnelUni1ToM;

/**
 * @author vivek.mishra
 * 
 */
public class OTMUniAssociationTest extends TwinAssociation
{
    /**
     * Inits the.
     */
    @BeforeClass
    public static void init() throws Exception
    {
        if(RUN_IN_EMBEDDED_MODE) {
            CassandraCli.cassandraSetUp();
        }
        
        List<Class> clazzz = new ArrayList<Class>(2);
        clazzz.add(PersonnelUni1ToM.class);
        clazzz.add(HabitatUni1ToM.class);
        init(clazzz, ALL_PUs_UNDER_TEST);
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

    @Override
    protected void find()
    {
        // Find Person
        PersonnelUni1ToM p = (PersonnelUni1ToM) dao.findPerson(PersonnelUni1ToM.class, "unionetomany_1");
        Assert.assertNotNull(p);
        Assert.assertEquals("unionetomany_1", p.getPersonId());
        Assert.assertEquals("Amresh", p.getPersonName());
        PersonalData pd = p.getPersonalData();
        Assert.assertNotNull(pd);
        Assert.assertEquals("www.amresh.com", pd.getWebsite());

        Set<HabitatUni1ToM> adds = p.getAddresses();
        Assert.assertNotNull(adds);
        Assert.assertFalse(adds.isEmpty());
        Assert.assertEquals(2, adds.size());

        for (HabitatUni1ToM address : adds)
        {
            Assert.assertNotNull(address.getStreet());
        }

    }

    @Override
    protected void insert()
    {
        // Save Person
        PersonnelUni1ToM personnel = new PersonnelUni1ToM();
        personnel.setPersonId("unionetomany_1");
        personnel.setPersonName("Amresh");
        personnel.setPersonalData(new PersonalData("www.amresh.com", "amry.ks@gmail.com", "xamry"));

        Set<HabitatUni1ToM> addresses = new HashSet<HabitatUni1ToM>();
        HabitatUni1ToM address1 = new HabitatUni1ToM();
        address1.setAddressId("unionetomany_a");
        address1.setStreet("AAAAAAAAAAAAA");

        HabitatUni1ToM address2 = new HabitatUni1ToM();
        address2.setAddressId("unionetomany_b");
        address2.setStreet("BBBBBBBBBBB");

        addresses.add(address1);
        addresses.add(address2);
        personnel.setAddresses(addresses);
        dao.insert(personnel);
        col.add(personnel);
        col.add(address1);
        col.add(address2);

    }    

    @Override
    protected void update()
    {
        try
        {
            for(Object entity : col) {
                if(entity.getClass().equals(PersonnelUni1ToM.class)) {
                    PersonnelUni1ToM personnel = (PersonnelUni1ToM) entity;
                    personnel.setPersonName("Saurabh");
                    dao.merge(personnel);
                } else if(entity.getClass().equals(HabitatUni1ToM.class)) {
                    HabitatUni1ToM address = (HabitatUni1ToM) entity;
                    address.setStreet("Brand new street");
                    dao.merge(address);
                }
            }
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Override
    protected void remove()
    {
        try
        {
            
            PersonnelUni1ToM p = (PersonnelUni1ToM) dao.findPerson(PersonnelUni1ToM.class, "unionetomany_1");
            dao.removePerson(p);          
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
    @Test
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
        if(AUTO_MANAGE_SCHEMA) {
            CassandraCli.dropKeySpace("KunderaExamples");
        }
        
    }

    @Override
    protected void loadDataForPERSONNEL() throws TException, InvalidRequestException, UnavailableException,
            TimedOutException, SchemaDisagreementException
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
            ksDef = CassandraCli.client.describe_keyspace("KunderaExamples");
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
    protected void loadDataForHABITAT() throws TException, InvalidRequestException, UnavailableException,
            TimedOutException, SchemaDisagreementException
    {
        KsDef ksDef = null;
        CfDef cfDef2 = new CfDef();
        cfDef2.name = "ADDRESS";
        cfDef2.keyspace = "KunderaExamples";

        ColumnDef columnDef1 = new ColumnDef(ByteBuffer.wrap("STREET".getBytes()), "UTF8Type");
        columnDef1.index_type = IndexType.KEYS;
        cfDef2.addToColumn_metadata(columnDef1);

        ColumnDef columnDef2 = new ColumnDef(ByteBuffer.wrap("PERSON_ID".getBytes()), "IntegerType");
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
