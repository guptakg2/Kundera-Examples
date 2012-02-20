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
package com.impetus.kundera.examples.email.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.impetus.kundera.examples.email.entities.Email;

/**
 * @author amresh.singh
 *
 */
public class EmailServiceDaoImpl implements EmailServiceDao
{
    EntityManagerFactory emf;
    
    public EmailServiceDaoImpl()
    {
        emf = Persistence.createEntityManagerFactory("twingo");
    }

    @Override
    public void addEmail(Object email)
    {
        EntityManager em = emf.createEntityManager();
        em.persist(email);
        em.close();
    }

    @Override
    public Object findEmail(Object messageId)
    {
        EntityManager em = emf.createEntityManager();
        Object email = em.find(Email.class, messageId);
        em.close();
        return email;
    }

    @Override
    public List<?> findAllEmails()
    {
        EntityManager em = emf.createEntityManager();
        Query q = em.createQuery("select e from Email e");
        List<?> emails = q.getResultList();
        em.close();
        return emails;
    }

    @Override
    public void deleteEmail(Object email)
    {
        EntityManager em = emf.createEntityManager();
        em.remove(email);
        em.close();        
    }

    @Override
    public void close()
    {
        if(emf != null) {
            emf.close();
        }        
    }   
    
    

}
