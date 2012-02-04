package com.impetus.kundera.examples.crossdatastore.useraddress;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.impetus.kundera.examples.crossdatastore.useraddress.entities.HabitatBi1ToM;

import com.impetus.kundera.examples.crossdatastore.useraddress.entities.PersonalData;
import com.impetus.kundera.examples.crossdatastore.useraddress.entities.PersonnelBi1ToM;

public class OTMBiAssociationTest extends TwinAssociation
{
    /**
     * Inits the.
     */
    @BeforeClass
    public static void init()
    {
        List<Class> clazzz = new ArrayList<Class>(2);
        clazzz.add(PersonnelBi1ToM.class);
        clazzz.add(HabitatBi1ToM.class);
        init(clazzz, "twingo", "twissandra", "twibase");
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
        PersonnelBi1ToM p = (PersonnelBi1ToM) dao.findPerson(PersonnelBi1ToM.class, "bionetomany_1");
        Assert.assertNotNull(p);
        Assert.assertEquals("bionetomany_1", p.getPersonId());
        Assert.assertEquals("Amresh", p.getPersonName());
        PersonalData pd = p.getPersonalData();
        Assert.assertNotNull(pd);
        Assert.assertEquals("www.amresh.com", pd.getWebsite());

        Set<HabitatBi1ToM> adds = p.getAddresses();
        Assert.assertNotNull(adds);
        Assert.assertFalse(adds.isEmpty());
        Assert.assertEquals(2, adds.size());

        for (HabitatBi1ToM address : adds)
        {
            Assert.assertNotNull(address);
            PersonnelBi1ToM person = address.getPerson();
            Assert.assertNotNull(person);
            Assert.assertEquals(p.getPersonId(), person.getPersonId());
            Assert.assertEquals(p.getPersonName(), person.getPersonName());
            Assert.assertNotNull(person.getAddresses());
            Assert.assertFalse(person.getAddresses().isEmpty());
            Assert.assertEquals(2, person.getAddresses().size());
        }

    }

    @Override
    protected void insert()
    {
        PersonnelBi1ToM personnel = new PersonnelBi1ToM();
        personnel.setPersonId("bionetomany_1");
        personnel.setPersonName("Amresh");
        personnel.setPersonalData(new PersonalData("www.amresh.com", "amry.ks@gmail.com", "xamry"));

        Set<HabitatBi1ToM> addresses = new HashSet<HabitatBi1ToM>();
        HabitatBi1ToM address1 = new HabitatBi1ToM();
        address1.setAddressId("bionetomany_a");
        address1.setStreet("AAAAAAAAAAAAA");

        HabitatBi1ToM address2 = new HabitatBi1ToM();
        address2.setAddressId("bionetomany_b");
        address2.setStreet("BBBBBBBBBBB");

        addresses.add(address1);
        addresses.add(address2);
        personnel.setAddresses(addresses);
        dao.insert(personnel);
        col.add(personnel);
        col.add(address1);
        col.add(address2);

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
    }
}
