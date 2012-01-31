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
import javax.persistence.Query;

import junit.framework.Assert;

/**
 * The Class BaseTest.
 * 
 * @author vivek.mishra
 */
public abstract class BaseTest
{

    /**
     * Prepare data.
     * 
     * @param rowKey
     *            the row key
     * @param age
     *            the age
     * @return the person
     */
    protected PersonCassandra prepareData(String rowKey, int age)
    {
        PersonCassandra o = new PersonCassandra();
        o.setPersonId(rowKey);
        o.setPersonName("vivek");
        o.setAge(age);
        return o;
    }

    /**
     * Prepare data.
     * 
     * @param rowKey
     *            the row key
     * @param age
     *            the age
     * @return the person
     */
    protected PersonMongo prepareMongoInstance(String rowKey, int age)
    {
        PersonMongo o = new PersonMongo();
        o.setPersonId(rowKey);
        o.setPersonName("vivek");
        o.setAge(age);
        return o;
    }

    /**
     * Find by id.
     * 
     * @param <E>
     *            the element type
     * @param clazz
     *            the clazz
     * @param rowKey
     *            the row key
     * @param em
     *            the em
     * @return the e
     */
    protected <E extends Object> E findById(Class<E> clazz, Object rowKey, EntityManager em)
    {
        return em.find(clazz, rowKey);
    }

    /**
     * Assert find by name.
     * 
     * @param <E>
     *            the element type
     * @param em
     *            the em
     * @param clazz
     *            the clazz
     * @param e
     *            the e
     */
    protected <E extends Object> void assertFindByName(EntityManager em, String clazz, E e)
    {

        String query = "Select p from " + clazz + " p where p.PERSON_NAME = vivek";
        // // find by name.
        Query q = em.createQuery(query);
        List<E> results = q.getResultList();
        Assert.assertNotNull(results);
        Assert.assertFalse(results.isEmpty());
        Assert.assertEquals(3, results.size());

    }

    /**
     * Assert find by name and age.
     * 
     * @param <E>
     *            the element type
     * @param em
     *            the em
     * @param clazz
     *            the clazz
     * @param e
     *            the e
     */
    protected <E extends Object> void assertFindByNameAndAge(EntityManager em, String clazz, E e)
    {
        Query q = em.createQuery("Select p from " + clazz + " p where p.PERSON_NAME = vivek and p.AGE > 10");
        List<E> results = q.getResultList();
        Assert.assertNotNull(results);
        Assert.assertFalse(results.isEmpty());
        Assert.assertEquals(2, results.size());
    }

    /**
     * Assert find by name and age gt and lt.
     * 
     * @param <E>
     *            the element type
     * @param em
     *            the em
     * @param clazz
     *            the clazz
     * @param e
     *            the e
     */
    protected <E extends Object> void assertFindByNameAndAgeGTAndLT(EntityManager em, String clazz, E e)
    {
        // // // find by name, age clause
        Query q = em.createQuery("Select p from " + clazz
                + " p where p.PERSON_NAME = vivek and p.AGE > 10 and p.AGE <20");
        List<E> results = q.getResultList();
        Assert.assertNotNull(results);
        Assert.assertFalse(results.isEmpty());
        Assert.assertEquals(1, results.size());
    }

    /**
     * Assert find by name and age between.
     * 
     * @param <E>
     *            the element type
     * @param em
     *            the em
     * @param clazz
     *            the clazz
     * @param e
     *            the e
     */
    protected <E extends Object> void assertFindByNameAndAgeBetween(EntityManager em, String clazz, E e)
    {
        // // find by between clause
        Query q = em.createQuery("Select p from " + clazz
                + " p where p.PERSON_NAME = vivek and p.AGE between 10 and 15");
        List<E> results = q.getResultList();
        Assert.assertNotNull(results);
        Assert.assertFalse(results.isEmpty());
        Assert.assertEquals(2, results.size());

    }

    /**
     * Assert find by range.
     * 
     * @param <E>
     *            the element type
     * @param em
     *            the em
     * @param clazz
     *            the clazz
     * @param e
     *            the e
     */
    protected <E extends Object> void assertFindByRange(EntityManager em, String clazz, E e)

    {
        // find by Range.
        Query q = em.createQuery("Select p from " + clazz + " p where p.PERSON_ID Between 1 and 2");
        List<E> results = q.getResultList();
        Assert.assertNotNull(results);
        Assert.assertFalse(results.isEmpty());
        Assert.assertEquals(2, results.size());
    }

    /**
     * Assert find without where clause.
     * 
     * @param <E>
     *            the element type
     * @param em
     *            the em
     * @param clazz
     *            the clazz
     * @param e
     *            the e
     */
    protected <E extends Object> void assertFindWithoutWhereClause(EntityManager em, String clazz, E e)
    {
        // find by without where clause.
        Query q = em.createQuery("Select p from " + clazz + " p");
        List<E> results = q.getResultList();
        Assert.assertNotNull(results);
        Assert.assertFalse(results.isEmpty());
        Assert.assertEquals(3, results.size());
    }

    /**
     * Assert on merge.
     *
     * @param <E> the element type
     * @param em the em
     * @param clazz the clazz
     * @param e the e
     */
    protected <E extends Object> void assertOnMerge(EntityManager em, String clazz, E e)
    {
        Query q = em.createQuery("Select p from " + clazz + " p where p.PERSON_NAME = vivek");
        List<E> results = q.getResultList();
        Assert.assertNotNull(results);
        Assert.assertEquals(2, results.size());

        q = em.createQuery("Select p from " + clazz + " p where p.PERSON_NAME = newvivek");
        results = q.getResultList();
        Assert.assertNotNull(results);
        Assert.assertEquals(1, results.size());
        Assert.assertNotSame("vivek", getPersonName(e, results.get(0)));
        Assert.assertEquals("newvivek", getPersonName(e, results.get(0)));
    }

    
    /**
     * Gets the person name.
     *
     * @param <E> the element type
     * @param e the e
     * @param result the result
     * @return the person name
     */
    private <E extends Object> String getPersonName(E e, Object result)
    {
        if(result instanceof PersonMongo)
        {
            return ((PersonMongo) result).getPersonName();
        } else if(result instanceof PersonCassandra)
        {
            return ((PersonCassandra) result).getPersonName();
        }
        
        throw new RuntimeException("Support for " + e + "is not yet supported");
    }
}
