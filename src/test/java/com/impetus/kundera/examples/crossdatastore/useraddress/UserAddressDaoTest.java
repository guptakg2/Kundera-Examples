package com.impetus.kundera.examples.crossdatastore.useraddress;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import junit.framework.Assert;
import junit.framework.TestCase;

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
import com.impetus.kundera.metadata.KunderaMetadataManager;
import com.impetus.kundera.metadata.model.EntityMetadata;
import com.impetus.kundera.metadata.model.KunderaMetadata;
import com.impetus.kundera.persistence.EntityManagerImpl;

public class UserAddressDaoTest extends TestCase
{

    // Change this as per your unit testing requirements, alongwith entity
    // definition
    String persistenceUnit = "addcassandra";

    UserAddressDaoImpl dao;

    protected void setUp() throws Exception
    {
        super.setUp();
    }

    protected void tearDown() throws Exception
    {
        super.tearDown();
    }

    public void test()
    {

        // savePerson();

        /* Unidirectional */
        savePersonUniOneToOneFK();
        // findPersonUniOneToOneFK();
        // mergePersonUniOneToOneFK();
        queryPersonUniOneToOneFK();
        // deletePersonUniOneToOneFK();

        // savePersonUniOneToOnePK();
        // findPersonUniOneToOnePK();
        // mergePersonUniOneToOnePK();
        // queryPersonUniOneToOnePK();
        // deletePersonUniOneToOnePK();

        // savePersonUniOneToMany();
        // findPersonUniOneToMany();
        // mergePersonUniOneToMany();
        // queryPersonUniOneToMany();
        // deletePersonUniOneToMany();

        // savePersonUniManyToOne();
        // findPersonUniManyToOne();
        // mergePersonUniManyToOne();
        // queryPersonUniManyToOne();
        // deletePersonUniManyToOne();

        // savePersonUniManyToMany();
        // findPersonUniManyToMany();
        // mergePersonUniManyToMany();
        // queryPersonUniManyToMany();
        // deletePersonUniManyToMany();

        /* Bidirectional */
        // savePersonBiOneToOneFK();
        // findPersonBiOneToOneFK();
        // mergePersonBiOneToOneFK();
        // queryPersonBiOneToOneFK();
        // deletePersonBiOneToOneFK();

        // savePersonBiOneToOnePK();
        // findPersonBiOneToOnePK();
        // mergePersonBiOneToOnePK();
        // queryPersonBiOneToOnePK();
        // deletePersonBiOneToOnePK();

        // savePersonBiOneToMany();
        // findPersonBiOneToMany();
        // mergePersonBiOneToMany();
        // queryPersonBiOneToMany();
        // deletePersonBiOneToMany();

        // savePersonBiManyToOne();
        // findPersonBiManyToOne();
        // mergePersonBiManyToOne();
        // queryPersonBiManyToOne();
        // deletePersonBiManyToOne();

        // savePersonBiManyToMany();
        // findPersonBiManyToMany();
        // mergePersonBiManyToMany();
        // queryPersonBiManyToMany();
        // deletePersonBiManyToMany();
    }

    public void savePerson()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);

        Personnel person = new Personnel();
        person.setPersonId("1");
        person.setPersonName("Amresh");
        person.setPersonalData(new PersonalData("www.amresh.com", "amry.ks@gmail.com", "xamry"));
        dao.insert(person);

        dao.closeEntityManagerFactory();
    }

    public void updatePerson()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);
        Personnel personnel = dao.getEntityManager(persistenceUnit).find(Personnel.class, "1");
        Assert.assertNotNull(personnel);
        personnel.setPersonName("Kuldeep2");
        dao.update(personnel);
        personnel = dao.getEntityManager(persistenceUnit).find(Personnel.class, "1");
        Assert.assertNotNull(personnel);
        Assert.assertEquals("Kuldeep2", personnel.getPersonName());
    }

    public void savePersonUniOneToOneFK()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);

        PersonnelUni1To1FK person = new PersonnelUni1To1FK();
        
        EntityMetadata m = KunderaMetadataManager.getEntityMetadata(persistenceUnit, PersonnelUni1To1FK.class);
        m.setPersistenceUnit("twissandra");
        person.setPersonId("unionetoonefk_1");
        person.setPersonName("Amresh");
        person.setPersonalData(new PersonalData("www.amresh.com", "amry.ks@gmail.com", "xamry"));

        HabitatUni1To1FK address = new HabitatUni1To1FK();
        address.setAddressId("unionetoonefk_a");
        address.setStreet("123, New street");
        person.setAddress(address);
        dao.insert(person);

        dao.closeEntityManagerFactory();

    }

    public void findPersonUniOneToOneFK()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);

        // Find Person
        PersonnelUni1To1FK p = (PersonnelUni1To1FK) dao.findPerson(PersonnelUni1To1FK.class, "unionetoonefk_1");
        assertNotNull(p);
        assertEquals("unionetoonefk_1", p.getPersonId());
        assertEquals("Amresh", p.getPersonName());
        PersonalData pd = p.getPersonalData();
        assertNotNull(pd);
        assertEquals("www.amresh.com", pd.getWebsite());

        HabitatUni1To1FK add = p.getAddress();
        assertNotNull(add);
        assertNotNull(add.getAddressId());
        assertEquals("unionetoonefk_a", add.getAddressId());
        assertEquals("123, New street", add.getStreet());

        dao.closeEntityManagerFactory();

    }

    public void mergePersonUniOneToOneFK()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);
        PersonnelUni1To1FK p = (PersonnelUni1To1FK) dao.findPerson(PersonnelUni1To1FK.class, "unionetoonefk_1");
        assertNotNull(p);
        p.setPersonName("NewAmresh");
        dao.merge(p);
        p = (PersonnelUni1To1FK) dao.findPerson(PersonnelUni1To1FK.class, "unionetoonefk_1");
        assertEquals("NewAmresh", p.getPersonName());
        PersonalData pd = p.getPersonalData();
        assertNotNull(pd);
        assertEquals("www.amresh.com", pd.getWebsite());

        HabitatUni1To1FK add = p.getAddress();
        assertNotNull(add);
        assertNotNull(add.getAddressId());
        assertEquals("unionetoonefk_a", add.getAddressId());
        assertEquals("123, New street", add.getStreet());

        dao.closeEntityManagerFactory();
    }

    public void deletePersonUniOneToOneFK()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);
        PersonnelUni1To1FK p = (PersonnelUni1To1FK) dao.findPerson(PersonnelUni1To1FK.class, "unionetoonefk_1");
        dao.removePerson(p);
        dao.closeEntityManagerFactory();
    }

    public void queryPersonUniOneToOneFK()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);
        String query = "Select p from PersonnelUni1To1FK p";
        Query q = dao.createQuery(query);
        List<PersonnelUni1To1FK> results = q.getResultList();
        Assert.assertNotNull(results);
        Assert.assertFalse(results.isEmpty());
        assertEquals(1, results.size());
        assertEquals("unionetoonefk_1", results.get(0).getPersonId());
        assertEquals("NewAmresh", results.get(0).getPersonName());
        assertEquals("amry.ks@gmail.com", results.get(0).getPersonalData().getEmail());
        assertEquals("unionetoonefk_a", results.get(0).getAddress().getAddressId());
        assertEquals("123, New street", results.get(0).getAddress().getStreet());
        dao.closeEntityManagerFactory();
    }

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

        dao.insert(person);

        dao.closeEntityManagerFactory();
    }

    public void findPersonUniOneToOnePK()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);

        // Find Person
        PersonnelUni1To1PK p = (PersonnelUni1To1PK) dao.findPerson(PersonnelUni1To1PK.class, "unionetoonepk_1");
        assertNotNull(p);
        assertEquals("unionetoonepk_1", p.getPersonId());
        assertEquals("Amresh", p.getPersonName());
        PersonalData pd = p.getPersonalData();
        assertNotNull(pd);
        assertEquals("www.amresh.com", pd.getWebsite());

        HabitatUni1To1PK add = p.getAddress();
        assertNotNull(add);
        assertNotNull(add.getPersonId());
        assertNotNull(add.getAddressId());
        assertEquals("unionetoonepk_1", add.getPersonId());
        assertEquals("unionetoonepk_a", add.getAddressId());
        assertEquals("123, New street", add.getStreet());

        dao.closeEntityManagerFactory();
    }

    public void mergePersonUniOneToOnePK()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);
        PersonnelUni1To1PK p = (PersonnelUni1To1PK) dao.findPerson(PersonnelUni1To1PK.class, "unionetoonepk_1");
        assertNotNull(p);
        p.setPersonName("NewAmresh");
        dao.merge(p);
        p = (PersonnelUni1To1PK) dao.findPerson(PersonnelUni1To1PK.class, "unionetoonepk_1");
        assertEquals("NewAmresh", p.getPersonName());
        PersonalData pd = p.getPersonalData();
        assertNotNull(pd);
        assertEquals("www.amresh.com", pd.getWebsite());

        HabitatUni1To1PK add = p.getAddress();
        assertNotNull(add);
        assertNotNull(add.getPersonId());
        assertNotNull(add.getAddressId());
        assertEquals("unionetoonepk_1", add.getPersonId());
        assertEquals("unionetoonepk_a", add.getAddressId());
        assertEquals("123, New street", add.getStreet());

        dao.closeEntityManagerFactory();
    }

    public void deletePersonUniOneToOnePK()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);
        PersonnelUni1To1PK p = (PersonnelUni1To1PK) dao.findPerson(PersonnelUni1To1PK.class, "unionetoonepk_1");
        dao.removePerson(p);
        dao.closeEntityManagerFactory();
    }

    public void queryPersonUniOneToOnePK()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);
        String query = "Select p from PersonnelUni1To1FK p";
        Query q = dao.createQuery(query);
        List<PersonnelUni1To1FK> results = q.getResultList();
        Assert.assertNotNull(results);
        Assert.assertFalse(results.isEmpty());
        assertEquals(1, results.size());
        assertEquals("unionetoonepk_1", results.get(0).getPersonId());
        assertEquals("NewAmresh", results.get(0).getPersonName());
        assertEquals("amry.ks@gmail.com", results.get(0).getPersonalData().getEmail());
        assertEquals("unionetoonepk_a", results.get(0).getAddress().getAddressId());
        assertEquals("123, New street", results.get(0).getAddress().getStreet());

        dao.closeEntityManagerFactory();
    }

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
        dao.insert(personnel);

        dao.closeEntityManagerFactory();
    }

    public void findPersonUniOneToMany()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);

        // Find Person
        PersonnelUni1ToM p = (PersonnelUni1ToM) dao.findPerson(PersonnelUni1ToM.class, "unionetomany_1");
        assertNotNull(p);
        assertEquals("unionetomany_1", p.getPersonId());
        assertEquals("Amresh", p.getPersonName());
        PersonalData pd = p.getPersonalData();
        assertNotNull(pd);
        assertEquals("www.amresh.com", pd.getWebsite());

        Set<HabitatUni1ToM> adds = p.getAddresses();
        assertNotNull(adds);
        assertFalse(adds.isEmpty());
        assertEquals(2, adds.size());

        for (HabitatUni1ToM address : adds)
        {
            assertNotNull(address.getStreet());
        }

        dao.closeEntityManagerFactory();
    }

    public void mergePersonUniOneToMany()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);
        PersonnelUni1ToM p = (PersonnelUni1ToM) dao.findPerson(PersonnelUni1ToM.class, "unionetomany_1");
        assertNotNull(p);
        p.setPersonName("Kuldeep");
        dao.merge(p);
        p = (PersonnelUni1ToM) dao.findPerson(PersonnelUni1ToM.class, "unionetomany_1");
        assertEquals("Kuldeep", p.getPersonName());
        PersonalData pd = p.getPersonalData();
        assertNotNull(pd);
        assertEquals("www.amresh.com", pd.getWebsite());

        Set<HabitatUni1ToM> adds = p.getAddresses();
        assertNotNull(adds);
        assertFalse(adds.isEmpty());
        assertEquals(2, adds.size());

        for (HabitatUni1ToM address : adds)
        {
            assertNotNull(address.getStreet());
        }
        dao.closeEntityManagerFactory();
    }

    public void deletePersonUniOneToMany()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);
        PersonnelUni1ToM p = (PersonnelUni1ToM) dao.findPerson(PersonnelUni1ToM.class, "unionetomany_1");
        dao.removePerson(p);
        dao.closeEntityManagerFactory();
    }

    public void queryPersonUniOneToMany()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);
        String query = "Select p from PersonnelUni1ToM p";
        Query q = dao.createQuery(query);
        List<PersonnelUni1ToM> results = q.getResultList();
        Assert.assertNotNull(results);
        Assert.assertFalse(results.isEmpty());
        Assert.assertEquals(1, results.size());
        assertEquals("Kuldeep", results.get(0).getPersonName());
        Set<HabitatUni1ToM> adds = results.get(0).getAddresses();
        assertEquals(2, adds.size());
        for (HabitatUni1ToM address : adds)
        {
            assertNotNull(address.getStreet());
        }
    }

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

    public void findPersonUniManyToOne()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);

        // Find Person 1
        PersonnelUniMTo1 p1 = (PersonnelUniMTo1) dao.findPerson(PersonnelUniMTo1.class, "unimanytoone_1");
        assertNotNull(p1);
        assertEquals("unimanytoone_1", p1.getPersonId());
        assertEquals("Amresh", p1.getPersonName());
        PersonalData pd = p1.getPersonalData();
        assertNotNull(pd);
        assertEquals("www.amresh.com", pd.getWebsite());

        HabitatUniMTo1 add = p1.getAddress();
        assertNotNull(add);

        assertEquals("unimanytoone_a", add.getAddressId());
        assertEquals("AAAAAAAAAAAAA", add.getStreet());

        // Find Person 2
        PersonnelUniMTo1 p2 = (PersonnelUniMTo1) dao.findPerson(PersonnelUniMTo1.class, "unimanytoone_2");
        assertNotNull(p2);
        assertEquals("unimanytoone_2", p2.getPersonId());
        assertEquals("Vivek", p2.getPersonName());
        PersonalData pd2 = p2.getPersonalData();
        assertNotNull(pd2);
        assertEquals("www.vivek.com", pd2.getWebsite());

        HabitatUniMTo1 add2 = p2.getAddress();
        assertNotNull(add2);

        assertEquals("unimanytoone_a", add2.getAddressId());
        assertEquals("AAAAAAAAAAAAA", add2.getStreet());

        dao.closeEntityManagerFactory();
    }

    public void mergePersonUniManyToOne()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);
        PersonnelUniMTo1 p1 = (PersonnelUniMTo1) dao.findPerson(PersonnelUniMTo1.class, "unimanytoone_1");
        assertNotNull(p1);
        p1.setPersonName("NewAmresh");
        dao.merge(p1);
        p1 = (PersonnelUniMTo1) dao.findPerson(PersonnelUniMTo1.class, "unimanytoone_1");
        assertEquals("NewAmresh", p1.getPersonName());
        assertEquals("unimanytoone_1", p1.getPersonId());
        PersonalData pd = p1.getPersonalData();
        assertNotNull(pd);
        assertEquals("www.amresh.com", pd.getWebsite());

        HabitatUniMTo1 add = p1.getAddress();
        assertNotNull(add);

        assertEquals("unimanytoone_a", add.getAddressId());
        assertEquals("AAAAAAAAAAAAA", add.getStreet());

        PersonnelUniMTo1 p2 = (PersonnelUniMTo1) dao.findPerson(PersonnelUniMTo1.class, "unimanytoone_2");
        assertNotNull(p2);
        assertEquals("unimanytoone_2", p2.getPersonId());
        assertEquals("Vivek", p2.getPersonName());
        PersonalData pd2 = p2.getPersonalData();
        assertNotNull(pd2);
        assertEquals("www.vivek.com", pd2.getWebsite());

        HabitatUniMTo1 add2 = p2.getAddress();
        assertNotNull(add2);

        assertEquals("unimanytoone_a", add2.getAddressId());
        assertEquals("AAAAAAAAAAAAA", add2.getStreet());
        dao.closeEntityManagerFactory();
    }

    public void deletePersonUniManyToOne()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);
        PersonnelUniMTo1 p1 = (PersonnelUniMTo1) dao.findPerson(PersonnelUniMTo1.class, "unimanytoone_1");
        PersonnelUniMTo1 p2 = (PersonnelUniMTo1) dao.findPerson(PersonnelUniMTo1.class, "unimanytoone_2");
        dao.removePerson(p1);
        dao.removePerson(p2);
        dao.closeEntityManagerFactory();
    }

    public void queryPersonUniManyToOne()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);
        String query = "Select p from PersonnelUniMTo1 p";
        Query q = dao.createQuery(query);
        List<PersonnelUniMTo1> results = q.getResultList();
        Assert.assertNotNull(results);
        Assert.assertFalse(results.isEmpty());
        assertEquals(2, results.size());
        dao.closeEntityManagerFactory();
    }

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

    public void findPersonUniManyToMany()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);

        PersonnelUniMToM person1 = (PersonnelUniMToM) dao.findPerson(PersonnelUniMToM.class, "unimanytomany_1");
        assertNotNull(person1);
        assertEquals("unimanytomany_1", person1.getPersonId());
        assertEquals("Amresh", person1.getPersonName());
        PersonalData pd1 = person1.getPersonalData();
        assertNotNull(pd1);
        assertEquals("www.amresh.com", pd1.getWebsite());
        Set<HabitatUniMToM> addresses1 = person1.getAddresses();
        assertNotNull(addresses1);
        assertFalse(addresses1.isEmpty());
        assertEquals(2, addresses1.size());
        HabitatUniMToM address11 = (HabitatUniMToM) addresses1.toArray()[0];
        assertNotNull(address11);
        HabitatUniMToM address12 = (HabitatUniMToM) addresses1.toArray()[1];
        assertNotNull(address12);

        PersonnelUniMToM person2 = (PersonnelUniMToM) dao.findPerson(PersonnelUniMToM.class, "unimanytomany_2");
        assertNotNull(person2);

        assertEquals("unimanytomany_2", person2.getPersonId());
        assertEquals("Vivek", person2.getPersonName());
        PersonalData pd2 = person2.getPersonalData();
        assertNotNull(pd2);
        assertEquals("www.vivek.com", pd2.getWebsite());
        Set<HabitatUniMToM> addresses2 = person2.getAddresses();
        assertNotNull(addresses2);
        assertFalse(addresses2.isEmpty());
        assertEquals(2, addresses2.size());
        HabitatUniMToM address21 = (HabitatUniMToM) addresses2.toArray()[0];
        assertNotNull(address21);
        HabitatUniMToM address22 = (HabitatUniMToM) addresses2.toArray()[1];
        assertNotNull(address22);

        dao.closeEntityManagerFactory();
    }

    public void mergePersonUniManyToMany()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);
        PersonnelUniMToM person1 = (PersonnelUniMToM) dao.findPerson(PersonnelUniMToM.class, "unimanytomany_1");
        assertNotNull(person1);
        person1.setPersonName("NewAmresh");
        dao.merge(person1);
        person1 = (PersonnelUniMToM) dao.findPerson(PersonnelUniMToM.class, "unimanytomany_1");
        assertEquals("NewAmresh", person1.getPersonName());
        PersonalData pd1 = person1.getPersonalData();
        assertNotNull(pd1);
        assertEquals("www.amresh.com", pd1.getWebsite());
        Set<HabitatUniMToM> addresses1 = person1.getAddresses();
        assertNotNull(addresses1);
        assertFalse(addresses1.isEmpty());
        assertEquals(2, addresses1.size());
        HabitatUniMToM address11 = (HabitatUniMToM) addresses1.toArray()[0];
        assertNotNull(address11);
        HabitatUniMToM address12 = (HabitatUniMToM) addresses1.toArray()[1];
        assertNotNull(address12);

        PersonnelUniMToM person2 = (PersonnelUniMToM) dao.findPerson(PersonnelUniMToM.class, "unimanytomany_2");
        assertNotNull(person2);

        assertEquals("unimanytomany_2", person2.getPersonId());
        assertEquals("Vivek", person2.getPersonName());
        PersonalData pd2 = person2.getPersonalData();
        assertNotNull(pd2);
        assertEquals("www.vivek.com", pd2.getWebsite());
        Set<HabitatUniMToM> addresses2 = person2.getAddresses();
        assertNotNull(addresses2);
        assertFalse(addresses2.isEmpty());
        assertEquals(2, addresses2.size());
        HabitatUniMToM address21 = (HabitatUniMToM) addresses2.toArray()[0];
        assertNotNull(address21);
        HabitatUniMToM address22 = (HabitatUniMToM) addresses2.toArray()[1];
        assertNotNull(address22);

        dao.closeEntityManagerFactory();
    }

    public void deletePersonUniManyToMany()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);
        PersonnelUniMToM person1 = (PersonnelUniMToM) dao.findPerson(PersonnelUniMToM.class, "unimanytomany_1");
        PersonnelUniMToM person2 = (PersonnelUniMToM) dao.findPerson(PersonnelUniMToM.class, "unimanytomany_2");
        dao.removePerson(person1);
        dao.removePerson(person2);
        dao.closeEntityManagerFactory();
    }

    public void queryPersonUniManyToMany()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);
        String query = "Select p from PersonnelUniMToM p";
        Query q = dao.createQuery(query);
        List<PersonnelUniMToM> results = q.getResultList();
        Assert.assertNotNull(results);
        Assert.assertFalse(results.isEmpty());
        assertEquals(2, results.size());
        dao.closeEntityManagerFactory();
    }

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

        dao.insert(person);

        dao.closeEntityManagerFactory();
    }

    public void findPersonBiOneToOneFK()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);

        // Find Person
        PersonnelBi1To1FK p = (PersonnelBi1To1FK) dao.findPerson(PersonnelBi1To1FK.class, "bionetoonefk_1");
        assertNotNull(p);
        assertEquals("bionetoonefk_1", p.getPersonId());
        assertEquals("Amresh", p.getPersonName());
        PersonalData pd = p.getPersonalData();
        assertNotNull(pd);
        assertEquals("www.amresh.com", pd.getWebsite());

        HabitatBi1To1FK address = p.getAddress();
        assertNotNull(address);
        assertEquals("bionetoonefk_a", address.getAddressId());
        assertEquals("123, New street", address.getStreet());

        PersonnelBi1To1FK pp = address.getPerson();
        assertNotNull(pp);
        assertEquals("bionetoonefk_1", pp.getPersonId());
        assertEquals("Amresh", pp.getPersonName());

        dao.closeEntityManagerFactory();
    }

    public void mergePersonBiOneToOneFK()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);
        PersonnelBi1To1FK p = (PersonnelBi1To1FK) dao.findPerson(PersonnelBi1To1FK.class, "bionetoonefk_1");
        assertNotNull(p);
        p.setPersonName("NewAmresh");
        dao.merge(p);
        p = (PersonnelBi1To1FK) dao.findPerson(PersonnelBi1To1FK.class, "bionetoonefk_1");
        assertEquals("NewAmresh", p.getPersonName());
        PersonalData pd = p.getPersonalData();
        assertNotNull(pd);
        assertEquals("www.amresh.com", pd.getWebsite());

        HabitatBi1To1FK address = p.getAddress();
        assertNotNull(address);
        assertEquals("bionetoonefk_a", address.getAddressId());
        assertEquals("123, New street", address.getStreet());

        PersonnelBi1To1FK pp = address.getPerson();
        assertNotNull(pp);
        assertEquals("bionetoonefk_1", pp.getPersonId());
        assertEquals("Amresh", pp.getPersonName());
        dao.closeEntityManagerFactory();
    }

    public void deletePersonBiOneToOneFK()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);
        PersonnelBi1To1FK p = (PersonnelBi1To1FK) dao.findPerson(PersonnelBi1To1FK.class, "bionetoonefk_1");
        dao.removePerson(p);
        dao.closeEntityManagerFactory();
    }

    public void queryPersonBiOneToOneFK()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);
        String query = "Select p from PersonnelBi1To1FK p";
        Query q = dao.createQuery(query);
        List<PersonnelBi1To1FK> results = q.getResultList();
        Assert.assertNotNull(results);
        Assert.assertFalse(results.isEmpty());
        assertEquals(1, results.size());
        dao.closeEntityManagerFactory();
    }

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

        dao.insert(person);

        dao.closeEntityManagerFactory();
    }

    public void findPersonBiOneToOnePK()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);

        // Find Person
        PersonnelBi1To1PK p = (PersonnelBi1To1PK) dao.findPerson(PersonnelBi1To1PK.class, "bionetoonepk_1");
        assertNotNull(p);
        assertEquals("bionetoonepk_1", p.getPersonId());
        assertEquals("Amresh", p.getPersonName());
        PersonalData pd = p.getPersonalData();
        assertNotNull(pd);
        assertEquals("www.amresh.com", pd.getWebsite());

        HabitatBi1To1PK address = p.getAddress();
        assertNotNull(address);
        assertEquals("bionetoonepk_1", address.getPersonId());
        assertEquals("bionetoonepk_a", address.getAddressId());
        assertEquals("123, New street", address.getStreet());

        PersonnelBi1To1PK pp = address.getPerson();
        assertNotNull(pp);
        assertEquals("bionetoonepk_1", pp.getPersonId());
        assertEquals("Amresh", pp.getPersonName());

        dao.closeEntityManagerFactory();
    }

    public void mergePersonBiOneToOnePK()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);
        PersonnelBi1To1PK p = (PersonnelBi1To1PK) dao.findPerson(PersonnelBi1To1PK.class, "bionetoonepk_1");
        assertNotNull(p);
        p.setPersonName("NewAmresh");
        dao.merge(p);
        p = (PersonnelBi1To1PK) dao.findPerson(PersonnelBi1To1PK.class, "bionetoonepk_1");
        assertEquals("NewAmresh", p.getPersonName());
        PersonalData pd = p.getPersonalData();
        assertNotNull(pd);
        assertEquals("www.amresh.com", pd.getWebsite());

        HabitatBi1To1PK address = p.getAddress();
        assertNotNull(address);
        assertEquals("bionetoonepk_1", address.getPersonId());
        assertEquals("bionetoonepk_a", address.getAddressId());
        assertEquals("123, New street", address.getStreet());

        PersonnelBi1To1PK pp = address.getPerson();
        assertNotNull(pp);
        assertEquals("bionetoonepk_1", pp.getPersonId());
        assertEquals("Amresh", pp.getPersonName());

        dao.closeEntityManagerFactory();
    }

    public void deletePersonBiOneToOnePK()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);
        PersonnelBi1To1PK p = (PersonnelBi1To1PK) dao.findPerson(PersonnelBi1To1PK.class, "bionetoonepk_1");
        dao.removePerson(p);
        dao.closeEntityManagerFactory();
    }

    public void queryPersonBiOneToOnePK()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);
        String query = "Select p from PersonnelBi1To1PK p";
        Query q = dao.createQuery(query);
        List<PersonnelBi1To1PK> results = q.getResultList();
        Assert.assertNotNull(results);
        Assert.assertFalse(results.isEmpty());
        assertEquals(1, results.size());
        dao.closeEntityManagerFactory();
    }

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
        dao.insert(personnel);

        dao.closeEntityManagerFactory();
    }

    public void findPersonBiOneToMany()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);

        // Find Person
        PersonnelBi1ToM p = (PersonnelBi1ToM) dao.findPerson(PersonnelBi1ToM.class, "bionetomany_1");
        assertNotNull(p);
        assertEquals("bionetomany_1", p.getPersonId());
        assertEquals("Amresh", p.getPersonName());
        PersonalData pd = p.getPersonalData();
        assertNotNull(pd);
        assertEquals("www.amresh.com", pd.getWebsite());

        Set<HabitatBi1ToM> adds = p.getAddresses();
        assertNotNull(adds);
        assertFalse(adds.isEmpty());
        assertEquals(2, adds.size());

        for (HabitatBi1ToM address : adds)
        {
            assertNotNull(address);
            PersonnelBi1ToM person = address.getPerson();
            assertNotNull(person);
            assertEquals(p.getPersonId(), person.getPersonId());
            assertEquals(p.getPersonName(), person.getPersonName());
            assertNotNull(person.getAddresses());
            assertFalse(person.getAddresses().isEmpty());
            assertEquals(2, person.getAddresses().size());
        }

        dao.closeEntityManagerFactory();
    }

    public void mergePersonBiOneToMany()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);
        PersonnelBi1ToM p = (PersonnelBi1ToM) dao.findPerson(PersonnelBi1ToM.class, "bionetomany_1");
        assertNotNull(p);
        p.setPersonName("NewAmresh");
        dao.merge(p);
        p = (PersonnelBi1ToM) dao.findPerson(PersonnelBi1ToM.class, "bionetomany_1");
        assertEquals("NewAmresh", p.getPersonName());
        PersonalData pd = p.getPersonalData();
        assertNotNull(pd);
        assertEquals("www.amresh.com", pd.getWebsite());

        Set<HabitatBi1ToM> adds = p.getAddresses();
        assertNotNull(adds);
        assertFalse(adds.isEmpty());
        assertEquals(2, adds.size());

        for (HabitatBi1ToM address : adds)
        {
            assertNotNull(address);
            PersonnelBi1ToM person = address.getPerson();
            assertNotNull(person);
            assertEquals(p.getPersonId(), person.getPersonId());
            assertEquals(p.getPersonName(), person.getPersonName());
            assertNotNull(person.getAddresses());
            assertFalse(person.getAddresses().isEmpty());
            assertEquals(2, person.getAddresses().size());
        }

        dao.closeEntityManagerFactory();
    }

    public void deletePersonBiOneToMany()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);
        PersonnelBi1ToM p = (PersonnelBi1ToM) dao.findPerson(PersonnelBi1ToM.class, "bionetomany_1");
        dao.removePerson(p);
        dao.closeEntityManagerFactory();
    }

    public void queryPersonBiOneToMany()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);
        String query = "Select p from PersonnelBi1ToM p";
        Query q = dao.createQuery(query);
        List<PersonnelBi1ToM> results = q.getResultList();
        Assert.assertNotNull(results);
        Assert.assertFalse(results.isEmpty());
        assertEquals(1, results.size());
        dao.closeEntityManagerFactory();
    }

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

    public void findPersonBiManyToOne()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);

        // Find Person 1
        PersonnelBiMTo1 p1 = (PersonnelBiMTo1) dao.findPerson(PersonnelBiMTo1.class, "bimanytoone_1");
        assertNotNull(p1);
        assertEquals("bimanytoone_1", p1.getPersonId());
        assertEquals("Amresh", p1.getPersonName());
        PersonalData pd = p1.getPersonalData();
        assertNotNull(pd);
        assertEquals("www.amresh.com", pd.getWebsite());

        HabitatBiMTo1 add = p1.getAddress();
        assertNotNull(add);

        assertEquals("bimanytoone_b", add.getAddressId());
        Set<PersonnelBiMTo1> people = add.getPeople();
        assertNotNull(people);
        assertFalse(people.isEmpty());
        assertEquals(2, people.size());

        // Find Person 2
        PersonnelBiMTo1 p2 = (PersonnelBiMTo1) dao.findPerson(PersonnelBiMTo1.class, "bimanytoone_2");
        assertNotNull(p2);
        assertEquals("bimanytoone_2", p2.getPersonId());
        assertEquals("Vivek", p2.getPersonName());
        PersonalData pd2 = p2.getPersonalData();
        assertNotNull(pd2);
        assertEquals("www.vivek.com", pd2.getWebsite());

        HabitatBiMTo1 add2 = p2.getAddress();
        assertNotNull(add2);

        assertEquals("bimanytoone_b", add2.getAddressId());
        Set<PersonnelBiMTo1> people2 = add2.getPeople();
        assertNotNull(people2);
        assertFalse(people2.isEmpty());
        assertEquals(2, people2.size());

        dao.closeEntityManagerFactory();
    }

    public void mergePersonBiManyToOne()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);
        PersonnelBiMTo1 p1 = (PersonnelBiMTo1) dao.findPerson(PersonnelBiMTo1.class, "bimanytoone_1");
        assertNotNull(p1);
        p1.setPersonName("NewAmresh");
        dao.merge(p1);
        p1 = (PersonnelBiMTo1) dao.findPerson(PersonnelBiMTo1.class, "bimanytoone_1");
        assertEquals("NewAmresh", p1.getPersonName());
        PersonalData pd = p1.getPersonalData();
        assertNotNull(pd);
        assertEquals("www.amresh.com", pd.getWebsite());

        HabitatBiMTo1 add = p1.getAddress();
        assertNotNull(add);

        assertEquals("bimanytoone_b", add.getAddressId());
        Set<PersonnelBiMTo1> people = add.getPeople();
        assertNotNull(people);
        assertFalse(people.isEmpty());
        assertEquals(2, people.size());

        // Find Person 2
        PersonnelBiMTo1 p2 = (PersonnelBiMTo1) dao.findPerson(PersonnelBiMTo1.class, "bimanytoone_2");
        assertNotNull(p2);
        assertEquals("bimanytoone_2", p2.getPersonId());
        assertEquals("Vivek", p2.getPersonName());
        PersonalData pd2 = p2.getPersonalData();
        assertNotNull(pd2);
        assertEquals("www.vivek.com", pd2.getWebsite());

        HabitatBiMTo1 add2 = p2.getAddress();
        assertNotNull(add2);

        assertEquals("bimanytoone_b", add2.getAddressId());
        Set<PersonnelBiMTo1> people2 = add2.getPeople();
        assertNotNull(people2);
        assertFalse(people2.isEmpty());
        assertEquals(2, people2.size());

        dao.closeEntityManagerFactory();
    }

    public void deletePersonBiManyToOne()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);
        PersonnelBiMTo1 p1 = (PersonnelBiMTo1) dao.findPerson(PersonnelBiMTo1.class, "bimanytoone_1");
        PersonnelBiMTo1 p2 = (PersonnelBiMTo1) dao.findPerson(PersonnelBiMTo1.class, "bimanytoone_2");
        dao.removePerson(p1);
        dao.removePerson(p2);
        dao.closeEntityManagerFactory();
    }

    public void queryPersonBiManyToOne()
    {
        dao = new UserAddressDaoImpl(persistenceUnit);
        String query = "Select p from PersonnelBiMTo1 p";
        Query q = dao.createQuery(query);
        List<PersonnelBiMTo1> results = q.getResultList();
        Assert.assertNotNull(results);
        Assert.assertFalse(results.isEmpty());
        assertEquals(2, results.size());
        dao.closeEntityManagerFactory();
    }

    public void savePersonBiManyToMany()
    {

    }

    public void findPersonBiManyToMany()
    {

    }

    public void mergePersonBiManyToMany()
    {

    }

    public void deletePersonBiManyToMany()
    {

    }

    public void queryPersonBiManyToMany()
    {

    }
    // public void query(){
    // dao = new UserAddressDaoImpl(persistenceUnit);
    // Query q =
    // em.createQuery("Select p from Person p where p.PERSON_NAME = vivek");
    // List<Person> results = q.getResultList();
    // Assert.assertNotNull(results);
    // Assert.assertFalse(results.isEmpty());
    // }
}