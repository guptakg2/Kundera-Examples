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

import com.impetus.kundera.examples.crossdatastore.pickr.entities.AlbumUni_1_1_1_1;
import com.impetus.kundera.examples.crossdatastore.pickr.entities.AlbumUni_1_M_1_M;
import com.impetus.kundera.examples.crossdatastore.pickr.entities.PersonalData;
import com.impetus.kundera.examples.crossdatastore.pickr.entities.PhotoUni_1_1_1_1;
import com.impetus.kundera.examples.crossdatastore.pickr.entities.PhotoUni_1_M_1_M;
import com.impetus.kundera.examples.crossdatastore.pickr.entities.PhotographerUni_1_1_1_1;
import com.impetus.kundera.examples.crossdatastore.pickr.entities.PhotographerUni_1_M_1_M;

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
       addPhotographerUni_1_1_1_1();
       //getPhotographerUni_1_1_1_1(); 
        
    }
    
    public void addPhotographerUni_1_1_1_1() {
    	PhotographerUni_1_1_1_1 p = new PhotographerUni_1_1_1_1();
        p.setPhotographerId(photographerId);
        p.setPhotographerName("Amresh");
        p.setPersonalData(new PersonalData("www.amresh.com", "amresh.singh@impetus.co.in", "xamry"));
        
        AlbumUni_1_1_1_1 album = new AlbumUni_1_1_1_1("album_1", "My Phuket Vacation", "Went Phuket with friends"); 
        
        PhotoUni_1_1_1_1 photo = new PhotoUni_1_1_1_1("photo_1", "One beach", "On beach with friends"); 
        
        album.setPhoto(photo);
        
        p.setAlbum(album);        

        pickr.addPhotographer(p);
    }
    
    public void getPhotographerUni_1_1_1_1() {
    	PhotographerUni_1_1_1_1 p = (PhotographerUni_1_1_1_1)pickr.getPhotographer(PhotographerUni_1_1_1_1.class, ""+photographerId);

        Assert.assertNotNull(p);
        Assert.assertEquals(1, p.getPhotographerId());
        Assert.assertEquals("Amresh", p.getPhotographerName());
        Assert.assertNotNull(p.getPersonalData());
        Assert.assertEquals("www.amresh.com", p.getPersonalData().getWebsite());
        Assert.assertEquals("amresh.singh@impetus.co.in", p.getPersonalData().getEmail());
        Assert.assertEquals("xamry", p.getPersonalData().getYahooId());
        
        Assert.assertNotNull(p.getAlbum());               
        AlbumUni_1_1_1_1 album = p.getAlbum();
        Assert.assertNotNull(album);
        Assert.assertTrue(album.getAlbumId().equals("album_1"));
        Assert.assertEquals("My Phuket Vacation", album.getAlbumName());
        Assert.assertEquals("Went Phuket with friends", album.getAlbumDescription());
        
        PhotoUni_1_1_1_1 photo = album.getPhoto();
        Assert.assertNotNull(photo);
        Assert.assertEquals("photo_1", photo.getPhotoId());
        Assert.assertEquals("One Beach", photo.getPhotoCaption());
        
    }
    
}
