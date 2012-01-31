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

import com.impetus.kundera.examples.crossdatastore.pickr.entities.AlbumBi_1_1_1_1;
import com.impetus.kundera.examples.crossdatastore.pickr.entities.PersonalData;
import com.impetus.kundera.examples.crossdatastore.pickr.entities.PhotoBi_1_1_1_1;
import com.impetus.kundera.examples.crossdatastore.pickr.entities.PhotographerBi_1_1_1_1;

/**
 * @author amresh.singh
 *
 */
public class PickrTestBi_1_1_1_1 extends PickrBaseTest
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
        //getPhotographer();
        //getAllPhotographers();
        //deletePhotographer();      

    }

    @Override
    public void addPhotographer()
    {
        PhotographerBi_1_1_1_1 p = populatePhotographer();
        pickr.addPhotographer(p);
    }
    
    

    @Override
    protected void updatePhotographer()
    {
        PhotographerBi_1_1_1_1 p = (PhotographerBi_1_1_1_1) pickr.getPhotographer(PhotographerBi_1_1_1_1.class, ""
                + photographerId);
        assertPhotographer(p);
        p.setPhotographerName("Vivek");       

        pickr.mergePhotographer(p);
        
        PhotographerBi_1_1_1_1 p2 = (PhotographerBi_1_1_1_1)pickr.getPhotographer(PhotographerBi_1_1_1_1.class, "" + photographerId);
        Assert.assertNotNull(p2);
        Assert.assertEquals("Vivek", p2.getPhotographerName());         
    }

    @Override
    protected void getPhotographer()
    {
        PhotographerBi_1_1_1_1 p = (PhotographerBi_1_1_1_1) pickr.getPhotographer(PhotographerBi_1_1_1_1.class, ""
                + photographerId);
        assertPhotographer(p);
        
    }

    @Override
    protected void getAllPhotographers()
    {
        List<Object> ps = pickr.getAllPhotographers(PhotographerBi_1_1_1_1.class.getSimpleName());
        PhotographerBi_1_1_1_1 p = (PhotographerBi_1_1_1_1)ps.get(0);
        
        assertPhotographer(p);
        
    }

    @Override
    protected void deletePhotographer()
    {
        PhotographerBi_1_1_1_1 p = (PhotographerBi_1_1_1_1)pickr.getPhotographer(PhotographerBi_1_1_1_1.class, ""+photographerId);
        assertPhotographer(p);
        pickr.deletePhotographer(p);
        PhotographerBi_1_1_1_1 p2 = (PhotographerBi_1_1_1_1)pickr.getPhotographer(PhotographerBi_1_1_1_1.class, ""+photographerId);
        Assert.assertNull(p2);
        
    }   


    private void assertPhotographer(PhotographerBi_1_1_1_1 p)
    {
        Assert.assertNotNull(p);
        Assert.assertEquals(1, p.getPhotographerId());
        Assert.assertEquals("Amresh", p.getPhotographerName());
        Assert.assertNotNull(p.getPersonalData());
        Assert.assertEquals("www.amresh.com", p.getPersonalData().getWebsite());
        Assert.assertEquals("amresh.singh@impetus.co.in", p.getPersonalData().getEmail());
        Assert.assertEquals("xamry", p.getPersonalData().getYahooId());

        Assert.assertNotNull(p.getAlbum());
        AlbumBi_1_1_1_1 album = p.getAlbum();
        Assert.assertNotNull(album);
        Assert.assertTrue(album.getAlbumId().equals("album_1"));
        Assert.assertEquals("My Phuket Vacation", album.getAlbumName());
        Assert.assertEquals("Went Phuket with friends", album.getAlbumDescription());

        PhotoBi_1_1_1_1 photo = album.getPhoto();
        Assert.assertNotNull(photo);
        Assert.assertEquals("photo_1", photo.getPhotoId());
        Assert.assertEquals("One beach", photo.getPhotoCaption());
        
        PhotographerBi_1_1_1_1 pRev = album.getPhotographer();
        Assert.assertNotNull(pRev);
        
        
        AlbumBi_1_1_1_1 albumRev = photo.getAlbum();
        Assert.assertNotNull(albumRev);
    }

    private PhotographerBi_1_1_1_1 populatePhotographer()
    {
        PhotographerBi_1_1_1_1 p = new PhotographerBi_1_1_1_1();
        p.setPhotographerId(photographerId);
        p.setPhotographerName("Amresh");
        p.setPersonalData(new PersonalData("www.amresh.com", "amresh.singh@impetus.co.in", "xamry"));

        AlbumBi_1_1_1_1 album = new AlbumBi_1_1_1_1("album_1", "My Phuket Vacation", "Went Phuket with friends");

        PhotoBi_1_1_1_1 photo = new PhotoBi_1_1_1_1("photo_1", "One beach", "On beach with friends");

        album.setPhoto(photo);

        p.setAlbum(album);
        return p;
    }
    

}
