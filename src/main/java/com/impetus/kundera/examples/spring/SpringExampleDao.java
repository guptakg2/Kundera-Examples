/*******************************************************************************
 * * Copyright 2012 Impetus Infotech.
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
package com.impetus.kundera.examples.spring;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

/**
 * @author amresh.singh
 * 
 */

@Service
public class SpringExampleDao
{

    EntityManagerFactory entityManagerFactory;

    public SimpleComment addComment(int id, String userName, String commentText)
    {
        SimpleComment simpleComment = new SimpleComment(id, userName, commentText);
        
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.persist(simpleComment);
        entityManager.close();

        return simpleComment;
    }

    public SimpleComment getCommentById(String Id)
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        SimpleComment simpleComment = entityManager.find(SimpleComment.class, Id);
        return simpleComment;
    }

    public List<SimpleComment> getAllComments()
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createQuery("SELECT c from SimpleComment c");
        List<SimpleComment> list = query.getResultList();

        return list;
    }

    /**
     * @return the entityManagerFactory
     */
    public EntityManagerFactory getEntityManagerFactory()
    {
        return entityManagerFactory;
    }

    /**
     * @param entityManagerFactory the entityManagerFactory to set
     */
    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory)
    {
        this.entityManagerFactory = entityManagerFactory;
    }

    /*@PersistenceContext
    public void setEntityManager(EntityManager em)
    {
        this.entityManager = em;
    }*/
    
    

}
