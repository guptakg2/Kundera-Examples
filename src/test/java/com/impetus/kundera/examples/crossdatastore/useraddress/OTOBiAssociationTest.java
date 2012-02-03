package com.impetus.kundera.examples.crossdatastore.useraddress;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.impetus.kundera.examples.crossdatastore.useraddress.entities.HabitatBi1To1FK;
import com.impetus.kundera.examples.crossdatastore.useraddress.entities.PersonalData;
import com.impetus.kundera.examples.crossdatastore.useraddress.entities.PersonnelBi1To1FK;

public class OTOBiAssociationTest extends TwinAssociation
{
    /**
     * Inits the.
     */
    @BeforeClass
    public static void init()
    {
        List<Class> clazzz = new ArrayList<Class>(2);
        clazzz.add(PersonnelBi1To1FK.class);
        clazzz.add(HabitatBi1To1FK.class);
        init(clazzz, "twingo", "twissandra");
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
