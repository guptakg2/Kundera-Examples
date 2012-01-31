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
import java.util.Set;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.impetus.kundera.examples.crossdatastore.pickr.entities.AlbumBi_1_M_M_M;
import com.impetus.kundera.examples.crossdatastore.pickr.entities.PersonalData;
import com.impetus.kundera.examples.crossdatastore.pickr.entities.PhotoBi_1_M_M_M;
import com.impetus.kundera.examples.crossdatastore.pickr.entities.PhotographerBi_1_M_M_M;

/**
 * @author amresh.singh
 *
 */
public class PickrTestBi_1_M_M_M extends PickrBaseTest
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
         //addPhotographer();
        // updatePhotographer();
         //getPhotographer();
        //getAllPhotographers();
         deletePhotographer();

    }

    @Override
    public void addPhotographer()
    {
        PhotographerBi_1_M_M_M p = populatePhotographer();
        pickr.addPhotographer(p);
    }

    @Override
    protected void updatePhotographer()
    {
        PhotographerBi_1_M_M_M p = (PhotographerBi_1_M_M_M) pickr.getPhotographer(PhotographerBi_1_M_M_M.class, ""
                + photographerId);
        assertPhotographer(p);
        p.setPhotographerName("Vivek");

        pickr.mergePhotographer(p);

        PhotographerBi_1_M_M_M p2 = (PhotographerBi_1_M_M_M) pickr.getPhotographer(PhotographerBi_1_M_M_M.class, ""
                + photographerId);
        Assert.assertNotNull(p2);
        Assert.assertEquals("Vivek", p2.getPhotographerName());
    }

    @Override
    protected void getPhotographer()
    {
        PhotographerBi_1_M_M_M p = (PhotographerBi_1_M_M_M) pickr.getPhotographer(PhotographerBi_1_M_M_M.class, ""
                + photographerId);
        assertPhotographer(p);

    }

    @Override
    protected void getAllPhotographers()
    {
        List<Object> ps = pickr.getAllPhotographers(PhotographerBi_1_M_M_M.class.getSimpleName());
        PhotographerBi_1_M_M_M p = (PhotographerBi_1_M_M_M) ps.get(0);

        assertPhotographer(p);

    }

    @Override
    protected void deletePhotographer()
    {
        PhotographerBi_1_M_M_M p = (PhotographerBi_1_M_M_M) pickr.getPhotographer(PhotographerBi_1_M_M_M.class, ""
                + photographerId);
        assertPhotographer(p);
        pickr.deletePhotographer(p);
        PhotographerBi_1_M_M_M p2 = (PhotographerBi_1_M_M_M) pickr.getPhotographer(PhotographerBi_1_M_M_M.class, ""
                + photographerId);
        Assert.assertNull(p2);

    }

    private PhotographerBi_1_M_M_M populatePhotographer()
    {
        PhotographerBi_1_M_M_M p = new PhotographerBi_1_M_M_M();
        p.setPhotographerId(photographerId);
        p.setPhotographerName("Amresh");
        p.setPersonalData(new PersonalData("www.amresh.com", "amresh.singh@impetus.co.in", "xamry"));

        AlbumBi_1_M_M_M album1 = new AlbumBi_1_M_M_M("album_1", "My Phuket Vacation", "Went Phuket with friends");
        AlbumBi_1_M_M_M album2 = new AlbumBi_1_M_M_M("album_2", "Office Pics", "Annual office party photos");

        PhotoBi_1_M_M_M photo1 = new PhotoBi_1_M_M_M("photo_1", "One beach", "On beach with friends");
        PhotoBi_1_M_M_M photo2 = new PhotoBi_1_M_M_M("photo_2", "In Hotel", "Chilling out in room");
        PhotoBi_1_M_M_M photo3 = new PhotoBi_1_M_M_M("photo_3", "At Airport", "So tired");
        PhotoBi_1_M_M_M photo4 = new PhotoBi_1_M_M_M("photo_4", "Office Team event", "Shot at Fun park");
        PhotoBi_1_M_M_M photo5 = new PhotoBi_1_M_M_M("photo_5", "My Team", "My team is the best");
        
        album1.addPhoto(photo1);
        album1.addPhoto(photo2);
        album1.addPhoto(photo3);
        album1.addPhoto(photo4);
        
        album2.addPhoto(photo2);
        album2.addPhoto(photo3);
        album2.addPhoto(photo4);
        album2.addPhoto(photo5);
        
        p.addAlbum(album1);
        p.addAlbum(album2);
        
        return p;

    }

    private void assertPhotographer(PhotographerBi_1_M_M_M p)
    {
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
        
        //Album 1
        AlbumBi_1_M_M_M album1 = p.getAlbums().get(0);
        Assert.assertNotNull(album1);
        Assert.assertTrue(album1.getAlbumId().equals("album_1") || album1.getAlbumId().equals("album_2"));

        Assert.assertFalse(album1.getAlbumName().length() == 0);
        Assert.assertFalse(album1.getAlbumDescription().length() == 0);
        
        PhotographerBi_1_M_M_M revP = album1.getPhotographer();
        Assert.assertNotNull(revP);
        Assert.assertEquals(photographerId, revP.getPhotographerId());

        List<PhotoBi_1_M_M_M> album1Photos = album1.getPhotos();
        Assert.assertNotNull(album1Photos);
        Assert.assertFalse(album1Photos.isEmpty());
        Assert.assertFalse(album1Photos.size() < 2);

        PhotoBi_1_M_M_M album1Photo1 = album1Photos.get(0);
        Assert.assertNotNull(album1Photo1);
        Assert.assertEquals(7, album1Photo1.getPhotoId().length());
        
        Set<AlbumBi_1_M_M_M> revAlbums1 = album1Photo1.getAlbums();
        Assert.assertNotNull(revAlbums1);
        Assert.assertFalse(revAlbums1.isEmpty());
        Assert.assertFalse(revAlbums1.size() < 1);

        //Album 2
        AlbumBi_1_M_M_M album2 = p.getAlbums().get(1);
        Assert.assertNotNull(album2);
        Assert.assertTrue(album2.getAlbumId().equals("album_1") || album2.getAlbumId().equals("album_2"));

        Assert.assertFalse(album2.getAlbumName().length() == 0);
        Assert.assertFalse(album2.getAlbumDescription().length() == 0);

        PhotographerBi_1_M_M_M revP2 = album1.getPhotographer();
        Assert.assertNotNull(revP2);
        Assert.assertEquals(photographerId, revP2.getPhotographerId());
        
        List<PhotoBi_1_M_M_M> album2Photos = album2.getPhotos();
        Assert.assertNotNull(album2Photos);
        Assert.assertFalse(album2Photos.isEmpty());
        Assert.assertFalse(album2Photos.size() < 2);

        PhotoBi_1_M_M_M album2Photo1 = album2Photos.get(0);
        Assert.assertNotNull(album2Photo1);
        Assert.assertEquals(7, album2Photo1.getPhotoId().length());
        
        Set<AlbumBi_1_M_M_M> revAlbums2 = album2Photo1.getAlbums();
        Assert.assertNotNull(revAlbums2);
        Assert.assertFalse(revAlbums2.isEmpty());
        Assert.assertFalse(revAlbums2.size() < 1);
    }
    
}
