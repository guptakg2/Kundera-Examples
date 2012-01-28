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
package com.impetus.kundera.examples.crossdatastore.pickr;

import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.impetus.kundera.examples.crossdatastore.pickr.entities.AlbumUni_1_M_1_M;
import com.impetus.kundera.examples.crossdatastore.pickr.entities.PersonalData;
import com.impetus.kundera.examples.crossdatastore.pickr.entities.PhotoUni_1_M_1_M;
import com.impetus.kundera.examples.crossdatastore.pickr.entities.PhotographerUni_1_M_1_M;

/**
 * @author amresh.singh
 *
 */
public class PickrTestUni_1_M_1_M extends PickrBaseTest
{
    @Before
    public void setUp() throws Exception
    {
        super.setUp();
    }

    /**
     * Tear down.
     *
     * @throws Exception the exception
     */
    @After
    public void tearDown() throws Exception
    {
        super.tearDown();
    }

    /**
     * Test.
     */
    @Test
    public void test()
    {
        addPhotographer();
        getPhotographer();
        getAllPhotographers();
    	//deletePhotographer();
        
    }
    
    private void addPhotographer()
    {
        PhotographerUni_1_M_1_M p = preparePhotographer();

        pickr.addPhotographer(p);
    }

    private void getPhotographer()
    {
        PhotographerUni_1_M_1_M p = (PhotographerUni_1_M_1_M)pickr.getPhotographer(PhotographerUni_1_M_1_M.class, ""+photographerId);

        assertPhotographer(p);

    }
    
    private void getAllPhotographers() {
    	List<Object> ps = pickr.getAllPhotographers(PhotographerUni_1_M_1_M.class.getSimpleName());
    	PhotographerUni_1_M_1_M p = (PhotographerUni_1_M_1_M)ps.get(0);
    	
    	assertPhotographer(p);
    	
    }
    
    private void deletePhotographer() {
    	PhotographerUni_1_M_1_M p = (PhotographerUni_1_M_1_M)pickr.getPhotographer(PhotographerUni_1_M_1_M.class, ""+photographerId);
    	Assert.assertNotNull(p);
    	pickr.deletePhotographer(p);
    	PhotographerUni_1_M_1_M p2 = (PhotographerUni_1_M_1_M)pickr.getPhotographer(PhotographerUni_1_M_1_M.class, ""+photographerId);
    	Assert.assertNull(p);
    
    }
    
    private PhotographerUni_1_M_1_M preparePhotographer() {
		PhotographerUni_1_M_1_M p = new PhotographerUni_1_M_1_M();
        p.setPhotographerId(photographerId);
        p.setPhotographerName("Amresh");
        p.setPersonalData(new PersonalData("www.amresh.com", "amresh.singh@impetus.co.in", "xamry"));
        
        AlbumUni_1_M_1_M album1 = new AlbumUni_1_M_1_M("album_1", "My Phuket Vacation", "Went Phuket with friends"); 
        album1.addPhoto(new PhotoUni_1_M_1_M("photo_1", "One beach", "On beach with friends"));
        album1.addPhoto(new PhotoUni_1_M_1_M("photo_2", "In Hotel", "Chilling out in room"));
        album1.addPhoto(new PhotoUni_1_M_1_M("photo_3", "At Airport", "So tired"));
        
        
        AlbumUni_1_M_1_M album2 = new AlbumUni_1_M_1_M("album_2", "Office Pics", "Annual office party photos");
        album2.addPhoto(new PhotoUni_1_M_1_M("photo_4", "Office Team event", "Shot at Fun park"));
        album2.addPhoto(new PhotoUni_1_M_1_M("photo_5", "My Team", "My team is the best"));
                
        p.addAlbum(album1);
        p.addAlbum(album2);
		return p;
	}
    
    private void assertPhotographer(PhotographerUni_1_M_1_M p) {
    	Assert.assertNotNull(p);
        Assert.assertEquals(1, p.getPhotographerId());
        Assert.assertEquals("Amresh", p.getPhotographerName());
        Assert.assertNotNull(p.getPersonalData());
        Assert.assertEquals("www.amresh.com", p.getPersonalData().getWebsite());
        Assert.assertEquals("amresh.singh@impetus.co.in", p.getPersonalData().getEmail());
        Assert.assertEquals("xamry", p.getPersonalData().getYahooId());
        
        Assert.assertNotNull(p.getAlbums());
        Assert.assertFalse(p.getAlbums().isEmpty());
        Assert.assertEquals(2, p.getAlbums().size());
        
        AlbumUni_1_M_1_M album1 = p.getAlbums().get(0);
        Assert.assertNotNull(album1);
        Assert.assertTrue(album1.getAlbumId().equals("album_1") || album1.getAlbumId().equals("album_2"));
        
        Assert.assertFalse(album1.getAlbumName().length() == 0);
        Assert.assertFalse(album1.getAlbumDescription().length() == 0);
        
        List<PhotoUni_1_M_1_M> album1Photos = album1.getPhotos();
        Assert.assertNotNull(album1Photos);
        Assert.assertFalse(album1Photos.isEmpty());
        Assert.assertFalse(album1Photos.size() < 2);
        
        PhotoUni_1_M_1_M album1Photo1 = album1Photos.get(0);
        Assert.assertNotNull(album1Photo1);
        Assert.assertEquals(7, album1Photo1.getPhotoId().length());       
        
        AlbumUni_1_M_1_M album2 = p.getAlbums().get(1);
        Assert.assertNotNull(album2);
        Assert.assertTrue(album2.getAlbumId().equals("album_1") || album2.getAlbumId().equals("album_2"));
        
        Assert.assertFalse(album2.getAlbumName().length() == 0);
        Assert.assertFalse(album2.getAlbumDescription().length() == 0);
        
        List<PhotoUni_1_M_1_M> album2Photos = album2.getPhotos();
        Assert.assertNotNull(album2Photos);
        Assert.assertFalse(album2Photos.isEmpty());
        Assert.assertFalse(album2Photos.size() < 2);
        
        PhotoUni_1_M_1_M album2Photo1 = album2Photos.get(0);
        Assert.assertNotNull(album2Photo1);
        Assert.assertEquals(7, album2Photo1.getPhotoId().length());
    }

}
