package com.impetus.kundera.examples.crossdatastore.useraddress;


import java.util.HashSet;
import java.util.Set;

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

public class UserAddressDaoTest extends TestCase
{    
	
	//Change this as per your unit testing requirements, alongwith entity definition
    String persistenceUnit = "rdbms,addhbase";
    UserAddressDaoImpl dao;

    protected void setUp() throws Exception
    {
        super.setUp();       
        
    }

    protected void tearDown() throws Exception
    {
        super.tearDown();          
    }
    
    
    
    public void test() {
    	/* Unidirectional */
    	//savePersonUniOneToOneFK();
    	//findPersonUniOneToOneFK();
    	
    	//savePersonUniOneToOnePK();
    	//findPersonUniOneToOnePK();
    	
    	savePersonUniOneToMany();
    	//findPersonUniOneToMany();
    	
    	/*savePersonUniManyToOne();
    	findPersonUniManyToOne();*/
    	
    	//savePersonUniManyToMay();
    	//findPersonUniManyToMany();
    	
    	/* Bidirectional */
    	/*savePersonBiOneToOneFK();
    	findPersonBiOneToOneFK();
    	
    	savePersonBiOneToOnePK();
    	findPersonBiOneToOnePK();
    	
    	savePersonBiOneToMany();
    	findPersonBiOneToMany();
    	
    	savePersonBiManyToOne();
    	findPersonBiManyToOne();*/
    	
    	//savePersonBiManyToMany();
    	//findPersonBiManyToMany();
    }     
    
    
    public void savePerson() {
    	dao = new UserAddressDaoImpl(persistenceUnit);
    	
    	Personnel person = new Personnel();
    	person.setPersonId("1");
        person.setPersonName("Amresh");
        person.setPersonalData(new PersonalData("www.amresh.com", "amry.ks@gmail.com", "xamry"));
        dao.savePerson(person);
        
        dao.closeEntityManagerFactory();
    }
    
    
    

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

    
    public void findPersonUniOneToOneFK() {    	    	
    	dao = new UserAddressDaoImpl(persistenceUnit);
    	
    	//Find Person
        PersonnelUni1To1FK p = (PersonnelUni1To1FK)dao.findPerson(PersonnelUni1To1FK.class, "unionetoonefk_1");
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
    
    public void findPersonUniOneToOnePK() {
    	dao = new UserAddressDaoImpl(persistenceUnit);
    	
    	//Find Person
        PersonnelUni1To1PK p = (PersonnelUni1To1PK)dao.findPerson(PersonnelUni1To1PK.class, "unionetoonepk_1");
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
    
    public void savePersonUniOneToMany() {
    	dao = new UserAddressDaoImpl(persistenceUnit);
    	
    	//Save Person
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
    
    public void findPersonUniOneToMany() {
    	dao = new UserAddressDaoImpl(persistenceUnit);
    	
    	//Find Person
        PersonnelUni1ToM p = (PersonnelUni1ToM)dao.findPerson(PersonnelUni1ToM.class, "unionetomany_1");
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
        
        for(HabitatUni1ToM address : adds)
        {
        	assertNotNull(address.getStreet());
        }
        
        dao.closeEntityManagerFactory();
    }   
    
    
    
    
    public void savePersonUniManyToOne() {
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
    
    public void findPersonUniManyToOne() {
    	dao = new UserAddressDaoImpl(persistenceUnit);
    	
    	//Find Person 1
        PersonnelUniMTo1 p1 = (PersonnelUniMTo1)dao.findPerson(PersonnelUniMTo1.class, "unimanytoone_1");
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
    	
    	
    	//Find Person 2
        PersonnelUniMTo1 p2 = (PersonnelUniMTo1)dao.findPerson(PersonnelUniMTo1.class, "unimanytoone_2");
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
    
    public void savePersonUniManyToMay() {
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
    
    public void findPersonUniManyToMany() {
    	
    }
    
    
    public void savePersonBiOneToOneFK() {
    	dao = new UserAddressDaoImpl(persistenceUnit);
    	
    	PersonnelBi1To1FK person = new PersonnelBi1To1FK();  
        person.setPersonId("bionetoonefk_1");
        person.setPersonName("Amresh");
        person.setPersonalData(new PersonalData("www.amresh.com", "amry.ks@gmail.com", "xamry"));
        
        HabitatBi1To1FK address = new HabitatBi1To1FK();
        address.setAddressId("bionetoonefk_a");
        address.setStreet("123, New street");        
        person.setAddress(address);        
        dao.savePerson(person); 
        
        dao.closeEntityManagerFactory();
    }  
    
    public void findPersonBiOneToOneFK() {
    	dao = new UserAddressDaoImpl(persistenceUnit);
    	
    	//Find Person
        PersonnelBi1To1FK p = (PersonnelBi1To1FK)dao.findPerson(PersonnelBi1To1FK.class, "bionetoonefk_1");
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
    
    public void savePersonBiOneToOnePK() {
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
    
    public void findPersonBiOneToOnePK() {
    	dao = new UserAddressDaoImpl(persistenceUnit);
    	
    	//Find Person
        PersonnelBi1To1PK p = (PersonnelBi1To1PK)dao.findPerson(PersonnelBi1To1PK.class, "bionetoonepk_1");
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
    
	public void savePersonBiOneToMany() {
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
    
    public void findPersonBiOneToMany() {
    	dao = new UserAddressDaoImpl(persistenceUnit);
    	
    	//Find Person
        PersonnelBi1ToM p = (PersonnelBi1ToM)dao.findPerson(PersonnelBi1ToM.class, "bionetomany_1");
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
        
        for(HabitatBi1ToM address : adds) {
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
    
    public void savePersonBiManyToOne() {
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
    
    public void findPersonBiManyToOne() {
    	dao = new UserAddressDaoImpl(persistenceUnit);
    	
    	//Find Person 1
        PersonnelBiMTo1 p1 = (PersonnelBiMTo1)dao.findPerson(PersonnelBiMTo1.class, "bimanytoone_1");
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

    	//Find Person 2
        PersonnelBiMTo1 p2 = (PersonnelBiMTo1)dao.findPerson(PersonnelBiMTo1.class, "bimanytoone_2");
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
    
    public void savePersonBiManyToMany() {
    	
    }
    
    public void findPersonBiManyToMany() {
    	
    }   

}
