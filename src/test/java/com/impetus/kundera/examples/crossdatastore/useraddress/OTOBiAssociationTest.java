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
import com.impetus.kundera.examples.crossdatastore.useraddress.entities.HabitatBi1To1FK;
import com.impetus.kundera.examples.crossdatastore.useraddress.entities.HabitatUni1To1FK;
import com.impetus.kundera.examples.crossdatastore.useraddress.entities.PersonalData;
import com.impetus.kundera.examples.crossdatastore.useraddress.entities.PersonnelBi1To1FK;
import com.impetus.kundera.examples.crossdatastore.useraddress.entities.PersonnelUni1To1FK;

public class OTOBiAssociationTest extends TwinAssociation
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
        clazzz.add(PersonnelBi1To1FK.class);
        clazzz.add(HabitatBi1To1FK.class);
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
        setUpInternal();
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
        PersonnelBi1To1FK p = (PersonnelBi1To1FK) dao.findPerson(PersonnelBi1To1FK.class, "bionetoonefk_1");
        Assert.assertNotNull(p);
        Assert.assertEquals("bionetoonefk_1", p.getPersonId());
        Assert.assertEquals("Amresh", p.getPersonName());
        PersonalData pd = p.getPersonalData();
        Assert.assertNotNull(pd);
        Assert.assertEquals("www.amresh.com", pd.getWebsite());

        HabitatBi1To1FK address = p.getAddress();
        Assert.assertNotNull(address);
        Assert.assertEquals("bionetoonefk_a", address.getAddressId());
        Assert.assertEquals("123, New street", address.getStreet());

        PersonnelBi1To1FK pp = address.getPerson();
        Assert.assertNotNull(pp);
        Assert.assertEquals("bionetoonefk_1", pp.getPersonId());
        Assert.assertEquals("Amresh", pp.getPersonName());
    }

    @Override
    protected void insert()
    {
        PersonnelBi1To1FK person = new PersonnelBi1To1FK();
        person.setPersonId("bionetoonefk_1");
        person.setPersonName("Amresh");
        person.setPersonalData(new PersonalData("www.amresh.com", "amry.ks@gmail.com", "xamry"));

        HabitatBi1To1FK address = new HabitatBi1To1FK();
        address.setAddressId("bionetoonefk_a");
        address.setStreet("123, New street");
        person.setAddress(address);
        address.setPerson(person);

        dao.insert(person);
        col.add(person);
        col.add(address);

    }    
    

    @Override
    protected void update()
    {
    }

    @Override
    protected void remove()
    {
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
        // cfDef.setColumn_type("super");
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
