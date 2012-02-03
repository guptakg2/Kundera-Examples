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

import com.impetus.kundera.examples.crossdatastore.useraddress.entities.HabitatUniMTo1;
import com.impetus.kundera.examples.crossdatastore.useraddress.entities.PersonalData;
import com.impetus.kundera.examples.crossdatastore.useraddress.entities.PersonnelUniMTo1;

/**
 * @author vivek.mishra
 *
 */
public class MTOUniAssociationTest extends TwinAssociation
{
	/**
     * Inits the.
     */
    @BeforeClass
    public static void init()
    {
        List<Class> clazzz = new ArrayList<Class>(2);
        clazzz.add(PersonnelUniMTo1.class);
        clazzz.add(HabitatUniMTo1.class);
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

		
	}

	@Override
	protected void insert() {
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

		
	}
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
