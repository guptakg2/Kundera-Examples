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

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case to perform simple CRUD operation.(insert, delete, merge, and select)
 * 
 * @author vivek.mishra
 * 
 *  Run this script to create column family in cassandra with indexes.
 *         create column family PERSON with comparator=UTF8Type and
 *         column_metadata=[{column_name: PERSON_NAME, validation_class:
 *         UTF8Type, index_type: KEYS}, {column_name: AGE, validation_class:
 *         IntegerType, index_type: KEYS}];
 * 
 */
public class PersonTest extends BaseTest
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
        em.persist(prepareData("1", 10));
        em.persist(prepareData("2", 20));
        em.persist(prepareData("3", 15));

        // find by id.
        PersonCassandra p = em.find(PersonCassandra.class, "1");
        Assert.assertNotNull(p);
        Assert.assertEquals("vivek", p.getPersonName());

        // find by name.
        Query q = em.createQuery("Select p from Person p where p.PERSON_NAME = vivek");
        List<PersonCassandra> results = q.getResultList();
        Assert.assertNotNull(results);
        Assert.assertFalse(results.isEmpty());
        Assert.assertEquals(3, results.size());

        // find by name and age.
        q = em.createQuery("Select p from Person p where p.PERSON_NAME = vivek and p.AGE > 10");
        results = q.getResultList();
        Assert.assertNotNull(results);
        Assert.assertFalse(results.isEmpty());
        Assert.assertEquals(2, results.size());

        // find by name, age clause
        q = em.createQuery("Select p from Person p where p.PERSON_NAME = vivek and p.AGE > 10 and p.AGE <20");
        results = q.getResultList();
        Assert.assertNotNull(results);
        Assert.assertFalse(results.isEmpty());
        Assert.assertEquals(1, results.size());
    }

    /**
     * On merge.
     */
    @Test
    public void onMerge()
    {
        em.persist(prepareData("1", 10));
        PersonCassandra p = em.find(PersonCassandra.class, "1");
        Assert.assertNotNull(p);
        Assert.assertEquals("vivek", p.getPersonName());
        // modify record.
        p.setPersonName("newvivek");
        em.merge(p);

        Query q = em.createQuery("Select p from Person p where p.PERSON_NAME = vivek");
        List<PersonCassandra> results = q.getResultList();
        Assert.assertNotNull(results);
        Assert.assertEquals(2, results.size());

        q = em.createQuery("Select p from Person p where p.PERSON_NAME = newvivek");
        results = q.getResultList();
        Assert.assertNotNull(results);
        Assert.assertEquals(1, results.size());
        Assert.assertNotSame("vivek", results.get(0).getPersonName());
        Assert.assertEquals("newvivek", results.get(0).getPersonName());

    }

    /**
     * Tear down.
     *
     * @throws Exception the exception
     */
    @After
    public void tearDown() throws Exception
    {/*
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
