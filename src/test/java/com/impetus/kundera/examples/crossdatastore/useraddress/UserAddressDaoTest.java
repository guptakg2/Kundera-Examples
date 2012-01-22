package com.impetus.kundera.examples.crossdatastore.useraddress;

import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.impetus.kundera.examples.crossdatastore.useraddress.dao.UserAddressDaoImpl;
import com.impetus.kundera.examples.crossdatastore.useraddress.entities.HabitatBi1To1FK;
import com.impetus.kundera.examples.crossdatastore.useraddress.entities.HabitatBi1To1PK;
import com.impetus.kundera.examples.crossdatastore.useraddress.entities.HabitatBi1ToM;
import com.impetus.kundera.examples.crossdatastore.useraddress.entities.HabitatBiMTo1;
import com.impetus.kundera.examples.crossdatastore.useraddress.entities.HabitatUni1To1FK;
import com.impetus.kundera.examples.crossdatastore.useraddress.entities.HabitatUni1To1PK;
import com.impetus.kundera.examples.crossdatastore.useraddress.entities.HabitatUni1ToM;
import com.impetus.kundera.examples.crossdatastore.useraddress.entities.HabitatUniMTo1;
import com.impetus.kundera.examples.crossdatastore.useraddress.entities.HabitatUniMToM;
import com.impetus.kundera.examples.crossdatastore.useraddress.entities.PersonalData;
import com.impetus.kundera.examples.crossdatastore.useraddress.entities.Personnel;
import com.impetus.kundera.examples.crossdatastore.useraddress.entities.PersonnelBi1To1FK;
import com.impetus.kundera.examples.crossdatastore.useraddress.entities.PersonnelBi1To1PK;
import com.impetus.kundera.examples.crossdatastore.useraddress.entities.PersonnelBi1ToM;
import com.impetus.kundera.examples.crossdatastore.useraddress.entities.PersonnelBiMTo1;
import com.impetus.kundera.examples.crossdatastore.useraddress.entities.PersonnelUni1To1FK;
import com.impetus.kundera.examples.crossdatastore.useraddress.entities.PersonnelUni1To1PK;
import com.impetus.kundera.examples.crossdatastore.useraddress.entities.PersonnelUni1ToM;
import com.impetus.kundera.examples.crossdatastore.useraddress.entities.PersonnelUniMTo1;
import com.impetus.kundera.examples.crossdatastore.useraddress.entities.PersonnelUniMToM;

/**
 * The Class UserAddressDaoTest.
 */
public class UserAddressDaoTest
{

    // Change this as per your unit testing requirements, alongwith entity
    // definition
    /** The persistence unit. */
    String persistenceUnit = "twissandra,rdbms";

    /** The dao. */
    UserAddressDaoImpl dao;

    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @Before
    public void setUp() throws Exception
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
    }

    /**
     * Test.
     */
    @Test
    public void test()
    {
        /* Unidirectional */
        savePersonUniOneToOneFK();
        findPersonUniOneToOneFK();

//         savePersonUniOneToOnePK();
//         findPersonUniOneToOnePK();

         savePersonUniOneToMany();
         findPersonUniOneToMany();

         savePersonUniManyToOne();
         findPersonUniManyToOne();

        // savePersonUniManyToMany();
        // findPersonUniManyToMany();

        /* Bidirectional */
        // savePersonBiOneToOneFK();
        // findPersonBiOneToOneFK();

        // savePersonBiOneToOnePK();
        // findPersonBiOneToOnePK();

        // savePersonBiOneToMany();
        // findPersonBiOneToMany();

        // savePersonBiManyToOne();
        // findPersonBiManyToOne();

        // savePersonBiManyToMany();
        // findPersonBiManyToMany();
    }

    /**
     * Save person.
     */
    public void savePerson()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);

        Personnel person = new Personnel();
        person.setPersonId("1");
        person.setPersonName("Amresh");
        person.setPersonalData(new PersonalData("www.amresh.com", "amry.ks@gmail.com", "xamry"));
        dao.savePerson(person);

        dao.closeEntityManagerFactory();
    }

    /**
     * Save person uni one to one fk.
     */
    public void savePersonUniOneToOneFK()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);

        PersonnelUni1To1FK person = new PersonnelUni1To1FK();
        person.setPersonId("unionetoonefk_1");
        person.setPersonName("Amresh");
        person.setPersonalData(new PersonalData("www.amresh.com", "amry.ks@gmail.com", "xamry"));

        HabitatUni1To1FK address = new HabitatUni1To1FK();
        address.setAddressId("unionetoonefk_a");
        address.setStreet("123, New street");
        person.setAddress(address);
        dao.savePerson(person);

        dao.closeEntityManagerFactory();

    }

    /**
     * Find person uni one to one fk.
     */
    public void findPersonUniOneToOneFK()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);

        // Find Person
        PersonnelUni1To1FK p = (PersonnelUni1To1FK) dao.findPerson(PersonnelUni1To1FK.class, "unionetoonefk_1");
        Assert.assertNotNull(p);
        Assert.assertEquals("unionetoonefk_1", p.getPersonId());
        Assert.assertEquals("Amresh", p.getPersonName());
        PersonalData pd = p.getPersonalData();
        Assert.assertNotNull(pd);
        Assert.assertEquals("www.amresh.com", pd.getWebsite());

        HabitatUni1To1FK add = p.getAddress();
        Assert.assertNotNull(add);
        Assert.assertNotNull(add.getAddressId());
        Assert.assertEquals("unionetoonefk_a", add.getAddressId());
        Assert.assertEquals("123, New street", add.getStreet());

        dao.closeEntityManagerFactory();

    }

    /**
     * Save person uni one to one pk.
     */
    public void savePersonUniOneToOnePK()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);

        PersonnelUni1To1PK person = new PersonnelUni1To1PK();
        person.setPersonId("unionetoonepk_1");
        person.setPersonName("Amresh");
        person.setPersonalData(new PersonalData("www.amresh.com", "amry.ks@gmail.com", "xamry"));

        HabitatUni1To1PK address = new HabitatUni1To1PK();
        address.setPersonId("unionetoonepk_1");
        address.setAddressId("unionetoonepk_a");
        address.setStreet("123, New street");
        person.setAddress(address);

        dao.savePerson(person);

        dao.closeEntityManagerFactory();
    }

    /**
     * Find person uni one to one pk.
     */
    public void findPersonUniOneToOnePK()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);

        // Find Person
        PersonnelUni1To1PK p = (PersonnelUni1To1PK) dao.findPerson(PersonnelUni1To1PK.class, "unionetoonepk_1");
        Assert.assertNotNull(p);
        Assert.assertEquals("unionetoonepk_1", p.getPersonId());
        Assert.assertEquals("Amresh", p.getPersonName());
        PersonalData pd = p.getPersonalData();
        Assert.assertNotNull(pd);
        Assert.assertEquals("www.amresh.com", pd.getWebsite());

        HabitatUni1To1PK add = p.getAddress();
        Assert.assertNotNull(add);
        Assert.assertNotNull(add.getPersonId());
        Assert.assertNotNull(add.getAddressId());
        Assert.assertEquals("unionetoonepk_1", add.getPersonId());
        Assert.assertEquals("unionetoonepk_a", add.getAddressId());
        Assert.assertEquals("123, New street", add.getStreet());

        dao.closeEntityManagerFactory();
    }

    /**
     * Save person uni one to many.
     */
    public void savePersonUniOneToMany()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);

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
        dao.savePerson(personnel);

        dao.closeEntityManagerFactory();
    }

    /**
     * Find person uni one to many.
     */
    public void findPersonUniOneToMany()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);

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

        dao.closeEntityManagerFactory();
    }

    /**
     * Save person uni many to one.
     */
    public void savePersonUniManyToOne()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);

        PersonnelUniMTo1 person1 = new PersonnelUniMTo1();
        person1.setPersonId("unimanytoone_1");
        person1.setPersonName("Amresh");
        person1.setPersonalData(new PersonalData("www.amresh.com", "amry.ks@gmail.com", "xamry"));

        PersonnelUniMTo1 person2 = new PersonnelUniMTo1();
        person2.setPersonId("unimanytoone_2");
        person2.setPersonName("Vivek");
        person2.setPersonalData(new PersonalData("www.vivek.com", "vivek@gmail.com", "mevivs"));

        HabitatUniMTo1 address = new HabitatUniMTo1();
        address.setAddressId("unimanytoone_a");
        address.setStreet("AAAAAAAAAAAAA");

        person1.setAddress(address);
        person2.setAddress(address);

        Set<PersonnelUniMTo1> persons = new HashSet<PersonnelUniMTo1>();
        persons.add(person1);
        persons.add(person2);

        dao.savePersons(persons);

        dao.closeEntityManagerFactory();
    }

    /**
     * Find person uni many to one.
     */
    public void findPersonUniManyToOne()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);

        // Find Person 1
        PersonnelUniMTo1 p1 = (PersonnelUniMTo1) dao.findPerson(PersonnelUniMTo1.class, "unimanytoone_1");
        Assert.assertNotNull(p1);
        Assert.assertEquals("unimanytoone_1", p1.getPersonId());
        Assert.assertEquals("Amresh", p1.getPersonName());
        PersonalData pd = p1.getPersonalData();
        Assert.assertNotNull(pd);
        Assert.assertEquals("www.amresh.com", pd.getWebsite());

        HabitatUniMTo1 add = p1.getAddress();
        Assert.assertNotNull(add);

        Assert.assertEquals("unimanytoone_a", add.getAddressId());
        Assert.assertEquals("AAAAAAAAAAAAA", add.getStreet());

        // Find Person 2
        PersonnelUniMTo1 p2 = (PersonnelUniMTo1) dao.findPerson(PersonnelUniMTo1.class, "unimanytoone_2");
        Assert.assertNotNull(p2);
        Assert.assertEquals("unimanytoone_2", p2.getPersonId());
        Assert.assertEquals("Vivek", p2.getPersonName());
        PersonalData pd2 = p2.getPersonalData();
        Assert.assertNotNull(pd2);
        Assert.assertEquals("www.vivek.com", pd2.getWebsite());

        HabitatUniMTo1 add2 = p2.getAddress();
        Assert.assertNotNull(add2);

        Assert.assertEquals("unimanytoone_a", add2.getAddressId());
        Assert.assertEquals("AAAAAAAAAAAAA", add2.getStreet());

        dao.closeEntityManagerFactory();
    }

    /**
     * Save person uni many to many.
     */
    public void savePersonUniManyToMany()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);

        PersonnelUniMToM person1 = new PersonnelUniMToM();
        person1.setPersonId("unimanytomany_1");
        person1.setPersonName("Amresh");
        person1.setPersonalData(new PersonalData("www.amresh.com", "amry.ks@gmail.com", "xamry"));

        PersonnelUniMToM person2 = new PersonnelUniMToM();
        person2.setPersonId("unimanytomany_2");
        person2.setPersonName("Vivek");
        person2.setPersonalData(new PersonalData("www.vivek.com", "vivek@gmail.com", "mevivs"));

        HabitatUniMToM address1 = new HabitatUniMToM();
        address1.setAddressId("unimanytomany_a");
        address1.setStreet("AAAAAAAAAAAAA");

        HabitatUniMToM address2 = new HabitatUniMToM();
        address2.setAddressId("unimanytomany_b");
        address2.setStreet("BBBBBBBBBBBBBBB");

        HabitatUniMToM address3 = new HabitatUniMToM();
        address3.setAddressId("unimanytomany_c");
        address3.setStreet("CCCCCCCCCCC");

        Set<HabitatUniMToM> person1Addresses = new HashSet<HabitatUniMToM>();
        Set<HabitatUniMToM> person2Addresses = new HashSet<HabitatUniMToM>();

        person1Addresses.add(address1);
        person1Addresses.add(address2);

        person2Addresses.add(address2);
        person2Addresses.add(address3);

        person1.setAddresses(person1Addresses);
        person2.setAddresses(person2Addresses);

        Set<PersonnelUniMToM> persons = new HashSet<PersonnelUniMToM>();
        persons.add(person1);
        persons.add(person2);

        dao.savePersons(persons);

        dao.closeEntityManagerFactory();
    }

    /**
     * Find person uni many to many.
     */
    public void findPersonUniManyToMany()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);

        PersonnelUniMToM person1 = (PersonnelUniMToM) dao.findPerson(PersonnelUniMToM.class, "unimanytomany_1");
        Assert.assertNotNull(person1);
        Assert.assertEquals("unimanytomany_1", person1.getPersonId());
        Assert.assertEquals("Amresh", person1.getPersonName());
        PersonalData pd1 = person1.getPersonalData();
        Assert.assertNotNull(pd1);
        Assert.assertEquals("www.amresh.com", pd1.getWebsite());
        Set<HabitatUniMToM> addresses1 = person1.getAddresses();
        Assert.assertNotNull(addresses1);
        Assert.assertFalse(addresses1.isEmpty());
        Assert.assertEquals(2, addresses1.size());
        HabitatUniMToM address11 = (HabitatUniMToM) addresses1.toArray()[0];
        Assert.assertNotNull(address11);
        HabitatUniMToM address12 = (HabitatUniMToM) addresses1.toArray()[1];
        Assert.assertNotNull(address12);

        PersonnelUniMToM person2 = (PersonnelUniMToM) dao.findPerson(PersonnelUniMToM.class, "unimanytomany_2");
        Assert.assertNotNull(person2);

        Assert.assertEquals("unimanytomany_2", person2.getPersonId());
        Assert.assertEquals("Vivek", person2.getPersonName());
        PersonalData pd2 = person2.getPersonalData();
        Assert.assertNotNull(pd2);
        Assert.assertEquals("www.vivek.com", pd2.getWebsite());
        Set<HabitatUniMToM> addresses2 = person2.getAddresses();
        Assert.assertNotNull(addresses2);
        Assert.assertFalse(addresses2.isEmpty());
        Assert.assertEquals(2, addresses2.size());
        HabitatUniMToM address21 = (HabitatUniMToM) addresses2.toArray()[0];
        Assert.assertNotNull(address21);
        HabitatUniMToM address22 = (HabitatUniMToM) addresses2.toArray()[1];
        Assert.assertNotNull(address22);

        dao.closeEntityManagerFactory();
    }

    /**
     * Save person bi one to one fk.
     */
    public void savePersonBiOneToOneFK()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);

        PersonnelBi1To1FK person = new PersonnelBi1To1FK();
        person.setPersonId("bionetoonefk_1");
        person.setPersonName("Amresh");
        person.setPersonalData(new PersonalData("www.amresh.com", "amry.ks@gmail.com", "xamry"));

        HabitatBi1To1FK address = new HabitatBi1To1FK();
        address.setAddressId("bionetoonefk_a");
        address.setStreet("123, New street");
        person.setAddress(address);
        address.setPerson(person);

        dao.savePerson(person);

        dao.closeEntityManagerFactory();
    }

    /**
     * Find person bi one to one fk.
     */
    public void findPersonBiOneToOneFK()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);

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

        dao.closeEntityManagerFactory();
    }

    /**
     * Save person bi one to one pk.
     */
    public void savePersonBiOneToOnePK()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);

        PersonnelBi1To1PK person = new PersonnelBi1To1PK();
        person.setPersonId("bionetoonepk_1");
        person.setPersonName("Amresh");
        person.setPersonalData(new PersonalData("www.amresh.com", "amry.ks@gmail.com", "xamry"));

        HabitatBi1To1PK address = new HabitatBi1To1PK();
        address.setPersonId("bionetoonepk_1");
        address.setAddressId("bionetoonepk_a");
        address.setStreet("123, New street");
        person.setAddress(address);
        address.setPerson(person);

        dao.savePerson(person);

        dao.closeEntityManagerFactory();
    }

    /**
     * Find person bi one to one pk.
     */
    public void findPersonBiOneToOnePK()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);

        // Find Person
        PersonnelBi1To1PK p = (PersonnelBi1To1PK) dao.findPerson(PersonnelBi1To1PK.class, "bionetoonepk_1");
        Assert.assertNotNull(p);
        Assert.assertEquals("bionetoonepk_1", p.getPersonId());
        Assert.assertEquals("Amresh", p.getPersonName());
        PersonalData pd = p.getPersonalData();
        Assert.assertNotNull(pd);
        Assert.assertEquals("www.amresh.com", pd.getWebsite());

        HabitatBi1To1PK address = p.getAddress();
        Assert.assertNotNull(address);
        Assert.assertEquals("bionetoonepk_1", address.getPersonId());
        Assert.assertEquals("bionetoonepk_a", address.getAddressId());
        Assert.assertEquals("123, New street", address.getStreet());

        PersonnelBi1To1PK pp = address.getPerson();
        Assert.assertNotNull(pp);
        Assert.assertEquals("bionetoonepk_1", pp.getPersonId());
        Assert.assertEquals("Amresh", pp.getPersonName());

        dao.closeEntityManagerFactory();
    }

    /**
     * Save person bi one to many.
     */
    public void savePersonBiOneToMany()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);

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
        dao.savePerson(personnel);

        dao.closeEntityManagerFactory();
    }

    /**
     * Find person bi one to many.
     */
    public void findPersonBiOneToMany()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);

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

        dao.closeEntityManagerFactory();
    }

    /**
     * Save person bi many to one.
     */
    public void savePersonBiManyToOne()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);

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

        dao.closeEntityManagerFactory();
    }

    /**
     * Find person bi many to one.
     */
    public void findPersonBiManyToOne()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);

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

        dao.closeEntityManagerFactory();
    }

    /**
     * Save person bi many to many.
     */
    public void savePersonBiManyToMany()
    {

    }

    /**
     * Find person bi many to many.
     */
    public void findPersonBiManyToMany()
    {

    }

}
