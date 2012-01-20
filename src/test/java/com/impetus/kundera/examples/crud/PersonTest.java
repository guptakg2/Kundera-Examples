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
 * Test case to perform simple CRUD operation.
 * 
 * @author vivek.mishra
 * 
 * create column family PERSON with comparator=UTF8Type and column_metadata=[{column_name: PERSON_NAME, validation_class: UTF8Type, index_type: KEYS}, {column_name: AGE, validation_class: IntegerType, index_type: KEYS}];
 *
 */
public class PersonTest
{

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception
    {
    }

    
    @Test
    public void onInsert()
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("twissandra");
        EntityManager em = emf.createEntityManager();
        em.persist(prepareData("1",10));
        em.persist(prepareData("2",20));
        em.persist(prepareData("3",15));

        // find by id.        
        Person p = em.find(Person.class, "1");
        Assert.assertNotNull(p);
        Assert.assertEquals("vivek", p.getPersonName());

        // find by name.
        Query q = em.createQuery("Select p from Person p where p.PERSON_NAME = vivek");
        List<Person> results = q.getResultList();
        Assert.assertNotNull(results);
        Assert.assertFalse(results.isEmpty());
        Assert.assertEquals(3, results.size());
        
        // find by name and age.
        q = em.createQuery("Select p from Person p where p.PERSON_NAME = vivek and p.AGE > 10");
        results = q.getResultList();
        Assert.assertNotNull(results);
        Assert.assertFalse(results.isEmpty());
        Assert.assertEquals(2, results.size());
        
        //find by name, age clause
        q = em.createQuery("Select p from Person p where p.PERSON_NAME = vivek and p.AGE > 10 and p.AGE <20");
        results = q.getResultList();
        Assert.assertNotNull(results);
        Assert.assertFalse(results.isEmpty());
        Assert.assertEquals(1, results.size());
        emf.close();
    }
    
    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception
    {
    }

    private Person prepareData(String rowKey, int age)
    {
        Person o = new Person();
        o.setPersonId(rowKey);
        o.setPersonName("vivek");
        o.setAge(age);
        return o;
    }
}
