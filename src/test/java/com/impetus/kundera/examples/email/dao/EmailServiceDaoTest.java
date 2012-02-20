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

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.impetus.kundera.examples.email.entities.Attachment;
import com.impetus.kundera.examples.email.entities.Contact;
import com.impetus.kundera.examples.email.entities.Email;

/**
 * @author amresh.singh
 *
 */
public class EmailServiceDaoTest
{

    /**
     * @throws java.lang.Exception
     */
    
    EmailServiceDao emailServiceDao;
    

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception
    {
        emailServiceDao = new EmailServiceDaoImpl();
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception
    {
        emailServiceDao.close();
    }

    /**
     * Test method for {@link com.impetus.kundera.examples.email.dao.EmailServiceDao#addEmail(java.lang.Object)}.
     */
    @Test
    public void testAddEmail()
    {
        Email email = new Email();
        
        email.setMessageId("1");
        email.setSubject("Please Join Meeting");
        email.setBody("Agenda: RFP discussion");
        email.setFrom(new Contact("a", "Amresh", "Singh", "xamry@gmail.com"));
        
        email.addTo(new Contact("b", "Vivek", "Mishra", "mevivs@gmail.com"));
        email.addTo(new Contact("c", "Saurabh", "Singh", "saurabh@gmail.com"));
        
        email.addAttachment(new Attachment("aaa", "Agenda.doc", "MS Word"));
        email.addAttachment(new Attachment("bbb", "MOM_Last_Meeting.xls", "MS Excel"));
        email.addAttachment(new Attachment("ccc", "Client_Feedback.txt", "Text"));
        
        
        emailServiceDao.addEmail(email);
        
    }

    /**
     * Test method for {@link com.impetus.kundera.examples.email.dao.EmailServiceDao#findEmail(java.lang.Object)}.
     */
    /*@Test
    public void testFindEmail()
    {
        Email email = (Email) emailServiceDao.findEmail("1");
        System.out.println(email);        
        
    }*/

    /**
     * Test method for {@link com.impetus.kundera.examples.email.dao.EmailServiceDao#findAllEmails()}.
     */
    /*@Test
    public void testFindAllEmails()
    {
        List<?> emails = emailServiceDao.findAllEmails();
        System.out.println(emails);
    }*/

    /**
     * Test method for {@link com.impetus.kundera.examples.email.dao.EmailServiceDao#deleteEmail(java.lang.Object)}.
     */
    /*@Test
    public void testDeleteEmail()
    {
        Email email = (Email) emailServiceDao.findEmail("1");
        emailServiceDao.deleteEmail(email);
    }*/

}
