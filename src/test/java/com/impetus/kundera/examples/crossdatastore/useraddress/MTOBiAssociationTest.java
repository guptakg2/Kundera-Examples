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

import com.impetus.kundera.examples.crossdatastore.useraddress.entities.HabitatBiMTo1;

import com.impetus.kundera.examples.crossdatastore.useraddress.entities.PersonalData;
import com.impetus.kundera.examples.crossdatastore.useraddress.entities.PersonnelBiMTo1;


public class MTOBiAssociationTest extends TwinAssociation
{

    /**
     * Inits the.
     */
    @BeforeClass
    public static void init()
    {
        List<Class> clazzz = new ArrayList<Class>(2);
        clazzz.add(PersonnelBiMTo1.class);
        clazzz.add(HabitatBiMTo1.class);
        init(clazzz, "twingo", "twissandra");
    }

    /**
     * Sets the up.
     *
     * @throws Exception the exception
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
	protected void find() {
		 // Find Person 1
        PersonnelBiMTo1 p1 = (PersonnelBiMTo1) dao.findPerson(PersonnelBiMTo1.class, "bimanytoone_1");
        Assert.assertNotNull(p1);
        Assert.assertEquals("bimanytoone_1", p1.getPersonId());
        Assert.assertEquals("Amresh", p1.getPersonName());
        PersonalData pd = p1.getPersonalData();
        Assert.assertNotNull(pd);
        Assert.assertEquals("www.amresh.com", pd.getWebsite());

        HabitatBiMTo1 add = p1.getAddress();
        Assert.assertNotNull(add);

        Assert.assertEquals("bimanytoone_b", add.getAddressId());
        Set<PersonnelBiMTo1> people = add.getPeople();
        Assert.assertNotNull(people);
        Assert.assertFalse(people.isEmpty());
        Assert.assertEquals(2, people.size());

        // Find Person 2
        PersonnelBiMTo1 p2 = (PersonnelBiMTo1) dao.findPerson(PersonnelBiMTo1.class, "bimanytoone_2");
        Assert.assertNotNull(p2);
        Assert.assertEquals("bimanytoone_2", p2.getPersonId());
        Assert.assertEquals("Vivek", p2.getPersonName());
        PersonalData pd2 = p2.getPersonalData();
        Assert.assertNotNull(pd2);
        Assert.assertEquals("www.vivek.com", pd2.getWebsite());

        HabitatBiMTo1 add2 = p2.getAddress();
        Assert.assertNotNull(add2);

        Assert.assertEquals("bimanytoone_b", add2.getAddressId());
        Set<PersonnelBiMTo1> people2 = add2.getPeople();
        Assert.assertNotNull(people2);
        Assert.assertFalse(people2.isEmpty());
        Assert.assertEquals(2, people2.size());

		
	}

	@Override
	protected void insert() {
		  PersonnelBiMTo1 person1 = new PersonnelBiMTo1();
	        person1.setPersonId("bimanytoone_1");
	        person1.setPersonName("Amresh");
	        person1.setPersonalData(new PersonalData("www.amresh.com", "amry.ks@gmail.com", "xamry"));

	        PersonnelBiMTo1 person2 = new PersonnelBiMTo1();
	        person2.setPersonId("bimanytoone_2");
	        person2.setPersonName("Vivek");
	        person2.setPersonalData(new PersonalData("www.vivek.com", "vivek@gmail.com", "mevivs"));

	        HabitatBiMTo1 address = new HabitatBiMTo1();
	        address.setAddressId("bimanytoone_b");
	        address.setStreet("AAAAAAAAAAAAA");

	        person1.setAddress(address);
	        person2.setAddress(address);

	        Set<PersonnelBiMTo1> persons = new HashSet<PersonnelBiMTo1>();
	        persons.add(person1);
	        persons.add(person2);

	        dao.savePersons(persons);
		
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
     * @throws Exception the exception
     */
    @After
    public void tearDown() throws Exception
    {
		tearDownInternal();
		}
}
