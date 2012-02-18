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

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import com.impetus.kundera.examples.spring.SimpleComment.Day;

/**
 * @author amresh.singh
 *
 */
public class SpringExampleDaoTest
{
    SpringExampleDao dao;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception
    {
        XmlBeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("applicationContext.xml"));

        dao = (SpringExampleDao) beanFactory.getBean("springExampleDao");
        
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception
    {
        dao = null;
    }
    
    /*@Test
    public void testAddComment() {
    	dao.addComment(1, "xamry", "No comment!", Day.SATURDAY);
    } */  
      
    @Test
    public void testGetCommentById()
    {
        SimpleComment comment = dao.getCommentById("1");
        
        assertNotNull(comment);
        assertEquals(1, comment.getId());
        assertEquals("xamry", comment.getUserName());
        assertEquals("No comment!", comment.getCommentText());
        assertEquals(Day.SATURDAY, comment.getDayOfComment());
    }

    
    public void getAllComments()
    {
        fail("Not yet implemented");
    }

}
