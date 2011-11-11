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

import java.util.List;

import junit.framework.TestCase;

import com.impetus.kundera.examples.pickr.dao.Pickr;
import com.impetus.kundera.examples.pickr.dao.PickrImpl;
import com.impetus.kundera.examples.pickr.entities.Album;
import com.impetus.kundera.examples.pickr.entities.PersonalData;
import com.impetus.kundera.examples.pickr.entities.Photographer;

/**
 * Test case for Pickr application
 * 
 * @author amresh.singh
 */
public class PickrTest extends TestCase
{
    Pickr pickr;

    String photographerId;

    protected void setUp() throws Exception
    {
        super.setUp();
        photographerId = "1";
        pickr = new PickrImpl("piccandra,picongo");
    }

    public void test()
    {
        addPhotographerAndAlbums();
        getPhotographer();
        getAllPhotographers();
        
        
        pickr.close();
    }

    public void addPhotographerAndAlbums()
    {
        Photographer p = new Photographer();
        p.setPhotographerId(photographerId);
        p.setPersonalData(new PersonalData("Amresh", "amresh.singh@impetus.co.in", "Noida"));
        p.addAlbum(new Album("a", "My Phuket Vacation", "Went Phuket with friends"));
        p.addAlbum(new Album("b", "Office Pics", "Annual office party photos"));

        pickr.addPhotographerAndAlbums(p);
    }

    public void addPhotographer()
    {
        pickr.addPhotographer(photographerId, "Amresh", "xamry@impetus.co.in", "Noida");
    }

    public void createAlbums()
    {
        pickr.createAlbum("a", "My Phuket Vacation", "Went Phuket with friends");
        pickr.createAlbum("b", "Office Pics", "Annual office party photos");
    }

    public void addPhotoToAlbum()
    { // fail("Not yet implemented");

    }

    public void getPhotographer()
    {
        Photographer p = pickr.getPhotographer(photographerId);

        assertNotNull(p);
        assertEquals("1", p.getPhotographerId());
        assertNotNull(p.getPersonalData());
        assertEquals("Amresh", p.getPersonalData().getName());
        assertNotNull(p.getAlbums());
        assertFalse(p.getAlbums().isEmpty());
        assertEquals(2, p.getAlbums().size());

    }

    public void getAllPhotographers()
    {
        List<Photographer> photographers = pickr.getAllPhotographers();

        assertNotNull(photographers);
        assertFalse(photographers.isEmpty());
        assertEquals(1, photographers.size());
    }

    protected void tearDown() throws Exception
    {
        super.tearDown();
    }

}
