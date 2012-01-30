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
package com.impetus.kundera.examples.crud;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case to perform simple SelfAssociation.
 * 
 * @author vivek.mishra
 * 
 *         Run this script to create column family in cassandra with indexes.
 *         create column family PERSON with comparator=UTF8Type and
 *         column_metadata=[{column_name: PERSON_NAME,
 *         validation_class:UTF8Type, index_type: KEYS}, {column_name:
 *         AGE,validation_class:IntegerType, index_type: KEYS}, {column_name:
 *         PERSON_ID,validation_class:UTF8Type, index_type: KEYS}];
 * 
 */
public class PersonAssociationTest extends BaseTest
{

    /** The emf. */
    private EntityManagerFactory emf;

    /** The em. */
    private EntityManager em;

    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @Before
    public void setUp() throws Exception
    {
        emf = Persistence.createEntityManagerFactory("twissandra");
        em = emf.createEntityManager();
    }

    /**
     * On crud.
     */
    @Test
    public void onInsert()
    {
        Employee personAssociation = new Employee();
        personAssociation.setPersonId("1_e");
        personAssociation.setPersonName("emp");
        Employee manager = new Employee();
        manager.setPersonId("2_m");
        manager.setPersonName("mgr");
        personAssociation.setManager(manager);
        em.persist(personAssociation);
        Employee emp = em.find(Employee.class, "1_e");
        Assert.assertNotNull(emp);
        Assert.assertEquals("2_m", emp.getManager().getPersonId());
    }

    /**
     * Tear down.
     *
     * @throws Exception the exception
     */
    @After
    public void tearDown() throws Exception
    {
        emf.close();
        /*
        Delete is working, but as row keys are not deleted from cassandra, so resulting in issue while reading back.
        // Delete
        em.remove(em.find(Person.class, "1"));
        em.remove(em.find(Person.class, "2"));
        em.remove(em.find(Person.class, "3"));
        em.close();
        emf.close();
        em = null;
        emf = null;*/
    }

}
