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
import com.impetus.kundera.examples.crossdatastore.pickr.entities.AlbumBi_1_1_1_M;
import com.impetus.kundera.examples.crossdatastore.pickr.entities.PersonalData;
import com.impetus.kundera.examples.crossdatastore.pickr.entities.PhotoBi_1_1_1_M;
import com.impetus.kundera.examples.crossdatastore.pickr.entities.PhotoBi_1_M_1_M;
import com.impetus.kundera.examples.crossdatastore.pickr.entities.PhotographerBi_1_1_1_M;

/**
 * @author amresh.singh
 *
 */
public class PickrTestBi_1_1_1_M extends PickrBaseTest
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
        // updatePhotographer();
        getPhotographer();
        getAllPhotographers();
        deletePhotographer();

    }

    @Override
    public void addPhotographer()
    {
        PhotographerBi_1_1_1_M p = populatePhotographer();
        pickr.addPhotographer(p);
    }

    @Override
    protected void updatePhotographer()
    {
        PhotographerBi_1_1_1_M p = (PhotographerBi_1_1_1_M) pickr.getPhotographer(PhotographerBi_1_1_1_M.class, ""
                + photographerId);
        assertPhotographer(p);
        p.setPhotographerName("Vivek");

        pickr.mergePhotographer(p);

        PhotographerBi_1_1_1_M p2 = (PhotographerBi_1_1_1_M) pickr.getPhotographer(PhotographerBi_1_1_1_M.class, ""
                + photographerId);
        Assert.assertNotNull(p2);
        Assert.assertEquals("Vivek", p2.getPhotographerName());
    }

    @Override
    protected void getPhotographer()
    {
        PhotographerBi_1_1_1_M p = (PhotographerBi_1_1_1_M) pickr.getPhotographer(PhotographerBi_1_1_1_M.class, ""
                + photographerId);
        assertPhotographer(p);

    }

    @Override
    protected void getAllPhotographers()
    {
        List<Object> ps = pickr.getAllPhotographers(PhotographerBi_1_1_1_M.class.getSimpleName());
        PhotographerBi_1_1_1_M p = (PhotographerBi_1_1_1_M) ps.get(0);

        assertPhotographer(p);

    }

    @Override
    protected void deletePhotographer()
    {
        PhotographerBi_1_1_1_M p = (PhotographerBi_1_1_1_M) pickr.getPhotographer(PhotographerBi_1_1_1_M.class, ""
                + photographerId);
        assertPhotographer(p);
        pickr.deletePhotographer(p);
        PhotographerBi_1_1_1_M p2 = (PhotographerBi_1_1_1_M) pickr.getPhotographer(PhotographerBi_1_1_1_M.class, ""
                + photographerId);
        Assert.assertNull(p2);

    }

    private PhotographerBi_1_1_1_M populatePhotographer()
    {
        PhotographerBi_1_1_1_M p = new PhotographerBi_1_1_1_M();
        p.setPhotographerId(photographerId);
        p.setPhotographerName("Amresh");
        p.setPersonalData(new PersonalData("www.amresh.com", "amresh.singh@impetus.co.in", "xamry"));

        AlbumBi_1_1_1_M album1 = new AlbumBi_1_1_1_M("album_1", "My Phuket Vacation", "Went Phuket with friends");
        album1.addPhoto(new PhotoBi_1_1_1_M("photo_1", "One beach", "On beach with friends"));
        album1.addPhoto(new PhotoBi_1_1_1_M("photo_2", "In Hotel", "Chilling out in room"));
        album1.addPhoto(new PhotoBi_1_1_1_M("photo_3", "At Airport", "So tired"));      

        p.setAlbum(album1);
        
        return p;

    }

    private void assertPhotographer(PhotographerBi_1_1_1_M p)
    {
        Assert.assertNotNull(p);
        Assert.assertEquals(1, p.getPhotographerId());
        Assert.assertEquals("Amresh", p.getPhotographerName());
        Assert.assertNotNull(p.getPersonalData());
        Assert.assertEquals("www.amresh.com", p.getPersonalData().getWebsite());
        Assert.assertEquals("amresh.singh@impetus.co.in", p.getPersonalData().getEmail());
        Assert.assertEquals("xamry", p.getPersonalData().getYahooId());

        AlbumBi_1_1_1_M album = p.getAlbum();
        Assert.assertNotNull(album);
        Assert.assertEquals("album_1", album.getAlbumId());
        Assert.assertEquals("My Phuket Vacation", album.getAlbumName());
        Assert.assertEquals("Went Phuket with friends", album.getAlbumDescription());
        
        List<PhotoBi_1_1_1_M> photos = album.getPhotos();
        Assert.assertNotNull(photos);
        Assert.assertFalse(photos.isEmpty());
        Assert.assertEquals(3, photos.size());

        PhotoBi_1_1_1_M photo1 = photos.get(0);
        Assert.assertNotNull(photo1);
        Assert.assertEquals(7, photo1.getPhotoId().length());
        
        AlbumBi_1_1_1_M albumRev = photo1.getAlbum();
        Assert.assertNotNull(albumRev);        
    }
    
}
