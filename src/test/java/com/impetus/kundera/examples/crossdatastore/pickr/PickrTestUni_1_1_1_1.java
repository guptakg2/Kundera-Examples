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

import com.impetus.kundera.examples.crossdatastore.pickr.entities.album.AlbumUni_1_1_1_1;
import com.impetus.kundera.examples.crossdatastore.pickr.entities.photo.PhotoUni_1_1_1_1;
import com.impetus.kundera.examples.crossdatastore.pickr.entities.photographer.PhotographerUni_1_1_1_1;

/**
 * @author amresh.singh
 * 
 */
public class PickrTestUni_1_1_1_1 extends PickrBaseTest
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
        PhotographerUni_1_1_1_1 p = populatePhotographer();
        pickr.addPhotographer(p);
    }
    
    

    @Override
    protected void updatePhotographer()
    {
        PhotographerUni_1_1_1_1 p = (PhotographerUni_1_1_1_1) pickr.getPhotographer(PhotographerUni_1_1_1_1.class, ""
                + photographerId);
        assertPhotographer(p);
        p.setPhotographerName("Vivek");       

        pickr.mergePhotographer(p);
        
        PhotographerUni_1_1_1_1 p2 = (PhotographerUni_1_1_1_1)pickr.getPhotographer(PhotographerUni_1_1_1_1.class, "" + photographerId);
        Assert.assertNotNull(p2);
        Assert.assertEquals("Vivek", p2.getPhotographerName());         
    }

    @Override
    protected void getPhotographer()
    {
        PhotographerUni_1_1_1_1 p = (PhotographerUni_1_1_1_1) pickr.getPhotographer(PhotographerUni_1_1_1_1.class, ""
                + photographerId);
        assertPhotographer(p);
        
    }

    @Override
    protected void getAllPhotographers()
    {
        List<Object> ps = pickr.getAllPhotographers(PhotographerUni_1_1_1_1.class.getSimpleName());
        PhotographerUni_1_1_1_1 p = (PhotographerUni_1_1_1_1)ps.get(0);
        
        assertPhotographer(p);
        
    }

    @Override
    protected void deletePhotographer()
    {
        PhotographerUni_1_1_1_1 p = (PhotographerUni_1_1_1_1)pickr.getPhotographer(PhotographerUni_1_1_1_1.class, ""+photographerId);
        assertPhotographer(p);
        pickr.deletePhotographer(p);
        PhotographerUni_1_1_1_1 p2 = (PhotographerUni_1_1_1_1)pickr.getPhotographer(PhotographerUni_1_1_1_1.class, ""+photographerId);
        Assert.assertNull(p2);
        
    }   


    private void assertPhotographer(PhotographerUni_1_1_1_1 p)
    {
        Assert.assertNotNull(p);
        Assert.assertEquals(1, p.getPhotographerId());
        Assert.assertEquals("Amresh", p.getPhotographerName());
                
        Assert.assertNotNull(p.getAlbum());
        AlbumUni_1_1_1_1 album = p.getAlbum();
        Assert.assertNotNull(album);
        Assert.assertTrue(album.getAlbumId().equals("album_1"));
        Assert.assertEquals("My Phuket Vacation", album.getAlbumName());
        Assert.assertEquals("Went Phuket with friends", album.getAlbumDescription());

        PhotoUni_1_1_1_1 photo = album.getPhoto();
        Assert.assertNotNull(photo);
        Assert.assertEquals("photo_1", photo.getPhotoId());
        Assert.assertEquals("One beach", photo.getPhotoCaption());
    }

    private PhotographerUni_1_1_1_1 populatePhotographer()
    {
        PhotographerUni_1_1_1_1 p = new PhotographerUni_1_1_1_1();
        p.setPhotographerId(photographerId);
        p.setPhotographerName("Amresh");
        
        AlbumUni_1_1_1_1 album = new AlbumUni_1_1_1_1("album_1", "My Phuket Vacation", "Went Phuket with friends");

        PhotoUni_1_1_1_1 photo = new PhotoUni_1_1_1_1("photo_1", "One beach", "On beach with friends");

        album.setPhoto(photo);

        p.setAlbum(album);
        return p;
    }

}
