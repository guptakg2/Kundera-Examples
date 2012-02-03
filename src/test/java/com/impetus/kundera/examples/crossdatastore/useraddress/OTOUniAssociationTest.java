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
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.impetus.kundera.examples.crossdatastore.useraddress.entities.HabitatUni1To1FK;
import com.impetus.kundera.examples.crossdatastore.useraddress.entities.PersonalData;
import com.impetus.kundera.examples.crossdatastore.useraddress.entities.PersonnelUni1To1FK;

/**
 * One to one association test for {@see} cassandra, mongodb,HBase and RDBMS combination.
 * 
 * @author vivek.mishra
 */
public class OTOUniAssociationTest extends TwinAssociation
{

    @BeforeClass
    public static void init()
    {
        List<Class> clazzz = new ArrayList<Class>(2);
        clazzz.add(PersonnelUni1To1FK.class);
        clazzz.add(HabitatUni1To1FK.class);
        init(clazzz,"twingo","twissandra");
    }
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception
    {
       setUpInternal();
    }

    @Test
    public void testInsert()
    {
        for(Map<Class,String> c: combinations)
        {
            switchPersistenceUnits(c);
            insert();
            findPersonUniOneToOneFK();
        }
    }

    private void insert()
    {
        PersonnelUni1To1FK person = new PersonnelUni1To1FK();
        HabitatUni1To1FK address = new HabitatUni1To1FK();
        person.setPersonId("unionetoonefk_1");
        person.setPersonName("Amresh");
        person.setPersonalData(new PersonalData("www.amresh.com", "amry.ks@gmail.com", "xamry"));
        address.setAddressId("unionetoonefk_a");
        address.setStreet("123, New street");
        person.setAddress(address);
        dao.insert(person);
    }
    
    public void findPersonUniOneToOneFK()
    {
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
    }

    
    @Test
    public void testMerge()
    {
        
    }
    
    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception
    {
        dao.closeEntityManagerFactory();
    }

    
}
