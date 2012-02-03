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


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.impetus.kundera.examples.crossdatastore.useraddress.entities.HabitatUniMToM;
import com.impetus.kundera.examples.crossdatastore.useraddress.entities.PersonalData;
import com.impetus.kundera.examples.crossdatastore.useraddress.entities.PersonnelUniMToM;

/**
 * @author vivek.mishra
 *
 */
public class MTMUniAssociationTest extends TwinAssociation
{
	/**
	 * Inits the.
	 */
	@BeforeClass
	public static void init() {
		List<Class> clazzz = new ArrayList<Class>(2);
		clazzz.add(PersonnelUniMToM.class);
		clazzz.add(HabitatUniMToM.class);
		init(clazzz, "twingo", "twissandra"/*, "twibase"*/);
	}

	/**
	 * Sets the up.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@Before
	public void setUp() throws Exception {
		setUpInternal("ADDRESS", "PERSONNEL");
	}

	/**
	 * Test insert.
	 */
	@Test
	public void testInsert() {
		tryOperation();
	}


	@Override
	protected void find() {

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

		
	}

	@Override
	protected void insert() {
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
		
	}

	/**
	 * Test merge.
	 */
	@Test
	public void testMerge() {

	}

	/**
	 * Tear down.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@After
	public void tearDown() throws Exception {
		
		tearDownInternal();
	}

}
