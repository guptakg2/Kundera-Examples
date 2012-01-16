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
package com.impetus.kundera.examples.crossdatastore.pickr;

import java.util.List;

import junit.framework.TestCase;

import com.impetus.kundera.examples.crossdatastore.pickr.dao.Pickr;
import com.impetus.kundera.examples.crossdatastore.pickr.dao.PickrImpl;
import com.impetus.kundera.examples.crossdatastore.pickr.entities.Album;
import com.impetus.kundera.examples.crossdatastore.pickr.entities.PersonalData;
import com.impetus.kundera.examples.crossdatastore.pickr.entities.Photo;
import com.impetus.kundera.examples.crossdatastore.pickr.entities.Photographer;

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
        pickr = new PickrImpl("picmysql,piccandra,picongo");
    }

    public void test()
    {
        //addPhotographerAlbumsAndPhotos();
        getPhotographer();
        //getAllPhotographers();       
    }

    public void addPhotographerAndAlbums()
    {
        Photographer p = new Photographer();
        p.setPhotographerId(photographerId);
        p.setPhotographerName("Amresh");
        p.setPersonalData(new PersonalData("www.amresh.com", "amresh.singh@impetus.co.in", "xamry"));
        p.addAlbum(new Album("album_1", "My Phuket Vacation", "Went Phuket with friends"));
        p.addAlbum(new Album("album_2", "Office Pics", "Annual office party photos"));

        pickr.addPhotographer(p);
    }
    
    public void addPhotographerAlbumsAndPhotos()
    {
        Photographer p = new Photographer();
        p.setPhotographerId(photographerId);
        p.setPhotographerName("Amresh");
        p.setPersonalData(new PersonalData("www.amresh.com", "amresh.singh@impetus.co.in", "xamry"));
        
        Album album1 = new Album("album_1", "My Phuket Vacation", "Went Phuket with friends"); 
        album1.addPhoto(new Photo("photo_1", "One beach", "On beach with friends"));
        album1.addPhoto(new Photo("photo_2", "In Hotel", "Chilling out in room"));
        album1.addPhoto(new Photo("photo_3", "At Airport", "So tired"));
        
        
        Album album2 = new Album("album_2", "Office Pics", "Annual office party photos");
        album2.addPhoto(new Photo("photo_4", "Office Team event", "Shot at Fun park"));
        album2.addPhoto(new Photo("photo_5", "My Team", "My team is the best"));
                
        p.addAlbum(album1);
        p.addAlbum(album2);

        pickr.addPhotographer(p);
    }


    public void addPhotographer()
    {
        Photographer p = new Photographer();
        p.setPhotographerId(photographerId);
        p.setPhotographerName("Amresh");
        p.setPersonalData(new PersonalData("www.amresh.com", "amresh.singh@impetus.co.in", "xamry"));
        
        pickr.addPhotographer(p);
    }

    public void createAlbums()
    {
        pickr.createAlbum("album_1", "My Phuket Vacation", "Went Phuket with friends");
        pickr.createAlbum("album_2", "Office Pics", "Annual office party photos");
    }

    public void addPhotoToAlbum()
    { 
        // fail("Not yet implemented");

    }

    public void getPhotographer()
    {
        Photographer p = pickr.getPhotographer(photographerId);

        assertNotNull(p);
        assertEquals("1", p.getPhotographerId());
        assertEquals("Amresh", p.getPhotographerName());
        assertNotNull(p.getPersonalData());
        assertEquals("www.amresh.com", p.getPersonalData().getWebsite());
        assertEquals("amresh.singh@impetus.co.in", p.getPersonalData().getEmail());
        assertEquals("xamry", p.getPersonalData().getYahooId());
        
        assertNotNull(p.getAlbums());
        assertFalse(p.getAlbums().isEmpty());
        assertEquals(2, p.getAlbums().size());
        
        Album album1 = p.getAlbums().get(0);
        assertNotNull(album1);
        assertTrue(album1.getAlbumId().equals("album_1") || album1.getAlbumId().equals("album_2"));
        
        assertFalse(album1.getAlbumName().length() == 0);
        assertFalse(album1.getAlbumDescription().length() == 0);
        
        List<Photo> album1Photos = album1.getPhotos();
        assertNotNull(album1Photos);
        assertFalse(album1Photos.isEmpty());
        assertFalse(album1Photos.size() < 2);
        
        Photo album1Photo1 = album1Photos.get(0);
        assertNotNull(album1Photo1);
        assertEquals(7, album1Photo1.getPhotoId().length());       
        
        Album album2 = p.getAlbums().get(1);
        assertNotNull(album2);
        assertTrue(album2.getAlbumId().equals("album_1") || album2.getAlbumId().equals("album_2"));
        
        assertFalse(album2.getAlbumName().length() == 0);
        assertFalse(album2.getAlbumDescription().length() == 0);
        
        List<Photo> album2Photos = album2.getPhotos();
        assertNotNull(album2Photos);
        assertFalse(album2Photos.isEmpty());
        assertFalse(album2Photos.size() < 2);
        
        Photo album2Photo1 = album2Photos.get(0);
        assertNotNull(album2Photo1);
        assertEquals(7, album2Photo1.getPhotoId().length());

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
        pickr.close();
    }

}
