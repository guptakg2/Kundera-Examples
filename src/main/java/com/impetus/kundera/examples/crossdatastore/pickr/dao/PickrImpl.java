/**
 * Copyright 2012 Impetus Infotech.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.impetus.kundera.examples.crossdatastore.pickr.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * Implementation class for Pickr functionality
 * 
 * @author amresh.singh
 */

public class PickrImpl implements Pickr
{

    EntityManagerFactory emf;

    public PickrImpl(String persistenceUnitName)
    {
        if (emf == null)
        {
            emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        }
    }

    @Override
    public void addPhotographer(Object p)
    {

        EntityManager em = emf.createEntityManager();
        em.persist(p);
        em.close();
    }

    @Override
    public Object getPhotographer(Class<?> entityClass, String photographerId)
    {
        EntityManager em = emf.createEntityManager();
        Object p = em.find(entityClass, photographerId);
        return p;
    }

    @Override
    public List<Object> getAllPhotographers(String className)
    {
        EntityManager em = emf.createEntityManager();
        Query q = em.createQuery("select p from " + className + " p");
        List<Object> photographers = q.getResultList();
        em.close();
        return photographers;
    }

    @Override
    public void deletePhotographer(Object p)
    {
        EntityManager em = emf.createEntityManager();
        em.remove(p);
        em.close();
    }

    @Override
    public void mergePhotographer(Object p)
    {
        EntityManager em = emf.createEntityManager();
        em.merge(p);
        em.close();
    }

    @Override
    public void close()
    {
        emf.close();
    }

}