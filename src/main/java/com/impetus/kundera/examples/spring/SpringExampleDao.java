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

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    public SimpleComment addComment(int id, String userName, String commentText)
    {
        SimpleComment simpleComment = new SimpleComment(id, userName, commentText);

        entityManager.persist(simpleComment);
        entityManager.close();

        return simpleComment;
    }

    public SimpleComment getCommentById(String Id)
    {

        SimpleComment simpleComment = entityManager.find(SimpleComment.class, Id);
        return simpleComment;
    }

    public List<SimpleComment> getAllComments()
    {

        Query query = entityManager.createQuery("SELECT c from SimpleComment c");
        List<SimpleComment> list = query.getResultList();

        return list;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager em)
    {
        this.entityManager = em;
    }

}
