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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case to perform simple CRUD operation.(insert, delete, merge, and
 * select)
 * 
 * @author vivek.mishra
 * 
 *         Run this script to create column family in cassandra with indexes.
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
     * @throws Exception
     *             the exception
     */
    @Before
    public void setUp() throws Exception
    {
        emf = Persistence.createEntityManagerFactory("twingo,twissandra");
        em = emf.createEntityManager();
    }

    /**
     * On insert mongo.
     */
    @Test
    public void onInsertMongo()
    {
        em.persist(prepareMongoInstance("1", 10));
        em.persist(prepareMongoInstance("2", 20));
        em.persist(prepareMongoInstance("3", 15));
        PersonMongo p = findById(PersonMongo.class, "1", em);
        Assert.assertNotNull(p);
        Assert.assertEquals("vivek", p.getPersonName());
        assertFindByName(em, "PersonMongo", PersonMongo.class);
        assertFindByNameAndAge(em, "PersonMongo", PersonMongo.class);
        assertFindByNameAndAgeGTAndLT(em, "PersonMongo", PersonMongo.class);
        assertFindByNameAndAgeBetween(em, "PersonMongo", PersonMongo.class);
        assertFindByRange(em, "PersonMongo", PersonMongo.class);
        assertFindWithoutWhereClause(em, "PersonMongo", PersonMongo.class);
    }

    /**
     * On merge mongo.
     */
    @Test
    public void onMergeMongo()
    {
        em.persist(prepareData("1", 10));
        PersonMongo p = findById(PersonMongo.class, "1", em);
        Assert.assertNotNull(p);
        Assert.assertEquals("vivek", p.getPersonName());
        // modify record.
        p.setPersonName("newvivek");
        em.merge(p);
        assertOnMerge(em, "PersonMongo", PersonMongo.class);
    }

    /**
     * On insert cassandra.
     */
    @Test
    public void onInsertCassandra()
    {
        em.persist(prepareData("1", 10));
        em.persist(prepareData("2", 20));
        em.persist(prepareData("3", 15));
        PersonCassandra p = findById(PersonCassandra.class, "1", em);
        Assert.assertNotNull(p);
        Assert.assertEquals("vivek", p.getPersonName());
        assertFindByName(em, "PersonCassandra", PersonCassandra.class);
        assertFindByNameAndAge(em, "PersonCassandra", PersonCassandra.class);
        assertFindByNameAndAgeGTAndLT(em, "PersonCassandra", PersonCassandra.class);
        assertFindByNameAndAgeBetween(em, "PersonCassandra", PersonCassandra.class);
        assertFindByRange(em, "PersonCassandra", PersonCassandra.class);
        assertFindWithoutWhereClause(em, "PersonCassandra", PersonCassandra.class);
    }

    /**
     * On merge cassandra.
     */
    @Test
    public void onMergeCassandra()
    {
        em.persist(prepareData("1", 10));
        PersonCassandra p = findById(PersonCassandra.class, "1", em);
        Assert.assertNotNull(p);
        Assert.assertEquals("vivek", p.getPersonName());
        // modify record.
        p.setPersonName("newvivek");
        em.merge(p);

        assertOnMerge(em, "PersonCassandra", PersonCassandra.class);
    }


    /**
     * Tear down.
     * 
     * @throws Exception
     *             the exception
     */
    @After
    public void tearDown() throws Exception
    {/*
      * Delete is working, but as row keys are not deleted from cassandra, so
      * resulting in issue while reading back. // Delete
      * em.remove(em.find(Person.class, "1")); em.remove(em.find(Person.class,
      * "2")); em.remove(em.find(Person.class, "3")); em.close(); emf.close();
      * em = null; emf = null;
      */
    }
}
