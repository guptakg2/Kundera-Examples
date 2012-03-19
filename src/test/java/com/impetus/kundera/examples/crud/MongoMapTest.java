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

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.impetus.kundera.examples.crud.entities.MapEntitySample;

/**
 * Junit test case for mongo database.
 * 
 * @author vivek.mishra
 * 
 */
public class MongoMapTest
{

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {}

    /**
     * Test case for @see https://github.com/impetus-opensource/Kundera/issues/49
     */
    @Test
    public void testMapIssue()
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("twingo");
        EntityManager em = emf.createEntityManager();
        String id = UUID.randomUUID().toString();
        MapEntitySample stuff = new MapEntitySample();
        stuff.setStuffId(id);
        stuff.getMyMap().put(99,1234);
        stuff.getMyMap().put(12,67432);
        em.persist(stuff);
        MapEntitySample reloadStuff = em.find(MapEntitySample.class, id);
        Assert.assertNotNull(reloadStuff);
        Assert.assertNotNull(reloadStuff.getMyMap());
        Assert.assertEquals(2, reloadStuff.getMyMap().size());
        Assert.assertEquals(1234, reloadStuff.getMyMap().get("99").intValue());
        Assert.assertEquals(67432, reloadStuff.getMyMap().get("12").intValue());
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {}

}
