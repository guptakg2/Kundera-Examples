/*
 * Copyright 2011 Impetus Infotech.
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
package com.impetus.kundera.examples.pickr;

import com.impetus.kundera.examples.pickr.dao.Pickr;
import com.impetus.kundera.examples.pickr.dao.PickrImpl;

import junit.framework.TestCase;

/**
 * Test case for Pickr application
 * @author amresh.singh
 */
public class PickrTest extends TestCase
{
    Pickr pickr;
    protected void setUp() throws Exception
    {
        super.setUp();
        pickr = new PickrImpl("piccandra");
    }   

    /*public void testAddPhotographer()
    {
        pickr.addPhotographer("1", "Amresh", "xamry@impetus.co.in", "Noida");
    }*/

    public void testCreateAlbum()
    {
        pickr.createAlbum("My Phuket Vacation", "Went Phuket with friends");
    }

    public void testAddPhotoToAlbum()
    {
        //fail("Not yet implemented");
    }
    
    protected void tearDown() throws Exception
    {
        super.tearDown();
    }

}
