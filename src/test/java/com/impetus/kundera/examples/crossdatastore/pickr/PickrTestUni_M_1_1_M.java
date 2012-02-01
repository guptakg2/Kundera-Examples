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

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.impetus.kundera.examples.crossdatastore.pickr.entities.AlbumUni_M_1_1_M;
import com.impetus.kundera.examples.crossdatastore.pickr.entities.PersonalData;
import com.impetus.kundera.examples.crossdatastore.pickr.entities.PhotoUni_M_1_1_M;
import com.impetus.kundera.examples.crossdatastore.pickr.entities.PhotographerUni_M_1_1_M;

/**
 * @author amresh.singh
 *
 */
public class PickrTestUni_M_1_1_M extends PickrBaseTest
{

    @Before
    public void setUp() throws Exception
    {
        super.setUp();
    }

    /**
     * Tear down.
     * 
     * @throws Exception
     *             the exception
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
        //updatePhotographer();
        getPhotographer();
        getAllPhotographers();
        deletePhotographer();      

    }
    

    @Override
    public void addPhotographer()
    {
        List<PhotographerUni_M_1_1_M> ps = populatePhotographers();
        
        for(PhotographerUni_M_1_1_M p : ps) {
            pickr.addPhotographer(p);
        }
    }

    @Override
    protected void updatePhotographer()
    {
        PhotographerUni_M_1_1_M p1 = (PhotographerUni_M_1_1_M) pickr.getPhotographer(PhotographerUni_M_1_1_M.class, "" + 1);
        assertPhotographer(p1, 1);
        p1.setPhotographerName("Amresh2"); 

        pickr.mergePhotographer(p1);
        
        PhotographerUni_M_1_1_M p1Modified = (PhotographerUni_M_1_1_M)pickr.getPhotographer(PhotographerUni_M_1_1_M.class, "" + 1);
        Assert.assertNotNull(p1Modified);
        Assert.assertEquals("Amresh2", p1Modified.getPhotographerName()); 
        
        PhotographerUni_M_1_1_M p2 = (PhotographerUni_M_1_1_M) pickr.getPhotographer(PhotographerUni_M_1_1_M.class, "" + 2);
        assertPhotographer(p2, 2);
        p1.setPhotographerName("Vivek2"); 

        pickr.mergePhotographer(p2);
        
        PhotographerUni_M_1_1_M p2Modified = (PhotographerUni_M_1_1_M)pickr.getPhotographer(PhotographerUni_M_1_1_M.class, "" + 2);
        Assert.assertNotNull(p2Modified);
        Assert.assertEquals("Vivek2", p2Modified.getPhotographerName());  
    }

    @Override
    protected void getPhotographer()
    {
        PhotographerUni_M_1_1_M p1 = (PhotographerUni_M_1_1_M) pickr.getPhotographer(PhotographerUni_M_1_1_M.class, "" + 1);
        assertPhotographer(p1, 1);
        
        PhotographerUni_M_1_1_M p2 = (PhotographerUni_M_1_1_M) pickr.getPhotographer(PhotographerUni_M_1_1_M.class, "" + 2);
        assertPhotographer(p2, 2);       
    }

    @Override
    protected void getAllPhotographers()
    {
        List<Object> ps = pickr.getAllPhotographers(PhotographerUni_M_1_1_M.class.getSimpleName());
        
        for(Object p : ps) {
            PhotographerUni_M_1_1_M pp = (PhotographerUni_M_1_1_M)p;
            Assert.assertNotNull(pp);
            assertPhotographer(pp, pp.getPhotographerId());
        }    
        
    }

    @Override
    protected void deletePhotographer()
    {
        PhotographerUni_M_1_1_M p1 = (PhotographerUni_M_1_1_M)pickr.getPhotographer(PhotographerUni_M_1_1_M.class, "" + 1);
        assertPhotographer(p1, 1);
        pickr.deletePhotographer(p1);
        
        PhotographerUni_M_1_1_M p1AfterDeletion = (PhotographerUni_M_1_1_M)pickr.getPhotographer(PhotographerUni_M_1_1_M.class, ""+1);
        Assert.assertNull(p1AfterDeletion);
        
        PhotographerUni_M_1_1_M p2 = (PhotographerUni_M_1_1_M)pickr.getPhotographer(PhotographerUni_M_1_1_M.class, "" + 2);
        Assert.assertNotNull(p2);
        pickr.deletePhotographer(p2);
        
        PhotographerUni_M_1_1_M p2AfterDeletion = (PhotographerUni_M_1_1_M)pickr.getPhotographer(PhotographerUni_M_1_1_M.class, ""+2);
        Assert.assertNull(p2AfterDeletion);
        
    }



    private void assertPhotographer(PhotographerUni_M_1_1_M p, int photographerId)
    {     
        
        if(photographerId == 1) {
            Assert.assertNotNull(p);
            Assert.assertEquals(1, p.getPhotographerId());
            Assert.assertEquals("Amresh", p.getPhotographerName());
            Assert.assertNotNull(p.getPersonalData());
            Assert.assertEquals("www.amresh.com", p.getPersonalData().getWebsite());
            Assert.assertEquals("amresh.singh@impetus.co.in", p.getPersonalData().getEmail());
            Assert.assertEquals("xamry", p.getPersonalData().getYahooId());

            Assert.assertNotNull(p.getAlbum());
            AlbumUni_M_1_1_M album = p.getAlbum();
            Assert.assertNotNull(album);
            Assert.assertTrue(album.getAlbumId().equals("album_1"));
            Assert.assertEquals("My Phuket Vacation", album.getAlbumName());
            Assert.assertEquals("Went Phuket with friends", album.getAlbumDescription());

            List<PhotoUni_M_1_1_M> albumPhotos = album.getPhotos();
            Assert.assertNotNull(albumPhotos);
            Assert.assertFalse(albumPhotos.isEmpty());
            Assert.assertEquals(3, albumPhotos.size());
            
            PhotoUni_M_1_1_M photo1 = albumPhotos.get(0);
            PhotoUni_M_1_1_M photo2 = albumPhotos.get(1);
            PhotoUni_M_1_1_M photo3 = albumPhotos.get(2);
            
            Assert.assertNotNull(photo1);
            Assert.assertTrue(photo1.getPhotoId().startsWith("photo_"));
            Assert.assertNotNull(photo2);
            Assert.assertTrue(photo2.getPhotoId().startsWith("photo_"));
            Assert.assertNotNull(photo3);
            Assert.assertTrue(photo3.getPhotoId().startsWith("photo_"));
        } else if(photographerId == 2) {
            Assert.assertNotNull(p);
            Assert.assertEquals(2, p.getPhotographerId());
            Assert.assertEquals("Vivek", p.getPhotographerName());
            Assert.assertNotNull(p.getPersonalData());
            Assert.assertEquals("www.vivek.com", p.getPersonalData().getWebsite());
            Assert.assertEquals("vivek.mishra@impetus.co.in", p.getPersonalData().getEmail());
            Assert.assertEquals("mevivs", p.getPersonalData().getYahooId());

            Assert.assertNotNull(p.getAlbum());
            AlbumUni_M_1_1_M album = p.getAlbum();
            Assert.assertNotNull(album);
            Assert.assertTrue(album.getAlbumId().equals("album_1"));
            Assert.assertEquals("My Phuket Vacation", album.getAlbumName());
            Assert.assertEquals("Went Phuket with friends", album.getAlbumDescription());

            List<PhotoUni_M_1_1_M> albumPhotos = album.getPhotos();
            Assert.assertNotNull(albumPhotos);
            Assert.assertFalse(albumPhotos.isEmpty());
            Assert.assertEquals(3, albumPhotos.size());
            
            PhotoUni_M_1_1_M photo1 = albumPhotos.get(0);
            PhotoUni_M_1_1_M photo2 = albumPhotos.get(1);  
            PhotoUni_M_1_1_M photo3 = albumPhotos.get(2);
            
            Assert.assertNotNull(photo1);
            Assert.assertTrue(photo1.getPhotoId().startsWith("photo_"));
            Assert.assertNotNull(photo2);
            Assert.assertTrue(photo2.getPhotoId().startsWith("photo_"));
            Assert.assertNotNull(photo3);
            Assert.assertTrue(photo3.getPhotoId().startsWith("photo_"));          
        } else {
            Assert.fail("Invalid Photographer ID: " + photographerId);  
        }     
        
    }

    private List<PhotographerUni_M_1_1_M> populatePhotographers()
    {
        List<PhotographerUni_M_1_1_M> photographers = new ArrayList<PhotographerUni_M_1_1_M>();
        
        //Photographer 1
        PhotographerUni_M_1_1_M p1 = new PhotographerUni_M_1_1_M();
        p1.setPhotographerId(1);
        p1.setPhotographerName("Amresh");
        p1.setPersonalData(new PersonalData("www.amresh.com", "amresh.singh@impetus.co.in", "xamry"));

        AlbumUni_M_1_1_M album = new AlbumUni_M_1_1_M("album_1", "My Phuket Vacation", "Went Phuket with friends");

        album.addPhoto(new PhotoUni_M_1_1_M("photo_1", "One beach", "On beach with friends"));
        album.addPhoto(new PhotoUni_M_1_1_M("photo_2", "In Hotel", "Chilling out in room"));
        album.addPhoto(new PhotoUni_M_1_1_M("photo_3", "At Airport", "So tired"));
        
        p1.setAlbum(album);
        
        //Photographer 2
        PhotographerUni_M_1_1_M p2 = new PhotographerUni_M_1_1_M();
        p2.setPhotographerId(2);
        p2.setPhotographerName("Vivek");
        p2.setPersonalData(new PersonalData("www.vivek.com", "vivek.mishra@impetus.co.in", "mevivs"));            
        
        p2.setAlbum(album);        
        
        photographers.add(p1);
        photographers.add(p2);
        
        return photographers;
    }
    
}
