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

import com.impetus.kundera.examples.crossdatastore.pickr.entities.album.AlbumBi_M_M_1_1;
import com.impetus.kundera.examples.crossdatastore.pickr.entities.photo.PhotoBi_M_M_1_1;
import com.impetus.kundera.examples.crossdatastore.pickr.entities.photographer.PhotographerBi_M_M_1_1;

/**
 * @author amresh.singh
 * 
 */
public class PickrTestBi_M_M_1_1 extends PickrBaseTest
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
        List<PhotographerBi_M_M_1_1> ps = populatePhotographers();

        for (PhotographerBi_M_M_1_1 p : ps)
        {
            pickr.addPhotographer(p);
        }
    }

    @Override
    protected void updatePhotographer()
    {
        PhotographerBi_M_M_1_1 p1 = (PhotographerBi_M_M_1_1) pickr.getPhotographer(PhotographerBi_M_M_1_1.class,
                "" + 1);
        assertPhotographer(p1, 1);
        p1.setPhotographerName("Amresh2");

        pickr.mergePhotographer(p1);

        PhotographerBi_M_M_1_1 p1Modified = (PhotographerBi_M_M_1_1) pickr.getPhotographer(
                PhotographerBi_M_M_1_1.class, "" + 1);
        Assert.assertNotNull(p1Modified);
        Assert.assertEquals("Amresh2", p1Modified.getPhotographerName());

        PhotographerBi_M_M_1_1 p2 = (PhotographerBi_M_M_1_1) pickr.getPhotographer(PhotographerBi_M_M_1_1.class,
                "" + 2);
        assertPhotographer(p2, 2);
        p1.setPhotographerName("Vivek2");

        pickr.mergePhotographer(p2);

        PhotographerBi_M_M_1_1 p2Modified = (PhotographerBi_M_M_1_1) pickr.getPhotographer(
                PhotographerBi_M_M_1_1.class, "" + 2);
        Assert.assertNotNull(p2Modified);
        Assert.assertEquals("Vivek2", p2Modified.getPhotographerName());
    }

    @Override
    protected void getPhotographer()
    {
        PhotographerBi_M_M_1_1 p1 = (PhotographerBi_M_M_1_1) pickr.getPhotographer(PhotographerBi_M_M_1_1.class,
                "" + 1);
        assertPhotographer(p1, 1);

        PhotographerBi_M_M_1_1 p2 = (PhotographerBi_M_M_1_1) pickr.getPhotographer(PhotographerBi_M_M_1_1.class,
                "" + 2);
        assertPhotographer(p2, 2);
    }

    @Override
    protected void getAllPhotographers()
    {
        List<Object> ps = pickr.getAllPhotographers(PhotographerBi_M_M_1_1.class.getSimpleName());

        for (Object p : ps)
        {
            PhotographerBi_M_M_1_1 pp = (PhotographerBi_M_M_1_1) p;
            Assert.assertNotNull(pp);
            assertPhotographer(pp, pp.getPhotographerId());
        }

    }

    @Override
    protected void deletePhotographer()
    {
        PhotographerBi_M_M_1_1 p1 = (PhotographerBi_M_M_1_1) pickr.getPhotographer(PhotographerBi_M_M_1_1.class,
                "" + 1);
        assertPhotographer(p1, 1);
        pickr.deletePhotographer(p1);

        PhotographerBi_M_M_1_1 p1AfterDeletion = (PhotographerBi_M_M_1_1) pickr.getPhotographer(
                PhotographerBi_M_M_1_1.class, "" + 1);
        Assert.assertNull(p1AfterDeletion);

        PhotographerBi_M_M_1_1 p2 = (PhotographerBi_M_M_1_1) pickr.getPhotographer(PhotographerBi_M_M_1_1.class,
                "" + 2);
        Assert.assertNotNull(p2);
        pickr.deletePhotographer(p2);

        PhotographerBi_M_M_1_1 p2AfterDeletion = (PhotographerBi_M_M_1_1) pickr.getPhotographer(
                PhotographerBi_M_M_1_1.class, "" + 2);
        Assert.assertNull(p2AfterDeletion);

    }

    private void assertPhotographer(PhotographerBi_M_M_1_1 p, int photographerId)
    {

        if (photographerId == 1)
        {
            Assert.assertNotNull(p);
            Assert.assertEquals(1, p.getPhotographerId());
            Assert.assertEquals("Amresh", p.getPhotographerName());
        
            Assert.assertNotNull(p.getAlbums());
            Assert.assertFalse(p.getAlbums().isEmpty());
            Assert.assertEquals(2, p.getAlbums().size());

            AlbumBi_M_M_1_1 album1 = p.getAlbums().get(0);
            Assert.assertNotNull(album1);
            Assert.assertTrue(album1.getAlbumId().startsWith("album_"));
            PhotoBi_M_M_1_1 photo1 = album1.getPhoto();
            Assert.assertNotNull(photo1);
            Assert.assertTrue(photo1.getPhotoId().startsWith("photo_"));

            AlbumBi_M_M_1_1 album2 = p.getAlbums().get(1);
            Assert.assertNotNull(album2);
            Assert.assertTrue(album2.getAlbumId().startsWith("album_"));
            PhotoBi_M_M_1_1 photo2 = album2.getPhoto();
            Assert.assertNotNull(photo2);
            Assert.assertTrue(photo2.getPhotoId().startsWith("photo_"));

        }
        else if (photographerId == 2)
        {
            Assert.assertNotNull(p);
            Assert.assertEquals(2, p.getPhotographerId());
            Assert.assertEquals("Vivek", p.getPhotographerName());
        
            Assert.assertNotNull(p.getAlbums());
            Assert.assertFalse(p.getAlbums().isEmpty());
            Assert.assertEquals(2, p.getAlbums().size());

            AlbumBi_M_M_1_1 album1 = p.getAlbums().get(0);
            Assert.assertNotNull(album1);
            Assert.assertTrue(album1.getAlbumId().startsWith("album_"));
            PhotoBi_M_M_1_1 photo1 = album1.getPhoto();
            Assert.assertNotNull(photo1);
            Assert.assertTrue(photo1.getPhotoId().startsWith("photo_"));

            AlbumBi_M_M_1_1 album2 = p.getAlbums().get(1);
            Assert.assertNotNull(album2);
            Assert.assertTrue(album2.getAlbumId().startsWith("album_"));
            PhotoBi_M_M_1_1 photo2 = album2.getPhoto();
            Assert.assertNotNull(photo2);
            Assert.assertTrue(photo2.getPhotoId().startsWith("photo_"));
        }
        else
        {
            Assert.fail("Invalid Photographer ID: " + photographerId);
        }

    }

    private List<PhotographerBi_M_M_1_1> populatePhotographers()
    {
        List<PhotographerBi_M_M_1_1> photographers = new ArrayList<PhotographerBi_M_M_1_1>();

        // Photographer 1
        PhotographerBi_M_M_1_1 p1 = new PhotographerBi_M_M_1_1();
        p1.setPhotographerId(1);
        p1.setPhotographerName("Amresh");
        
        // Photographer 2
        PhotographerBi_M_M_1_1 p2 = new PhotographerBi_M_M_1_1();
        p2.setPhotographerId(2);
        p2.setPhotographerName("Vivek");
        
        AlbumBi_M_M_1_1 album1 = new AlbumBi_M_M_1_1("album_1", "My Phuket Vacation", "Went Phuket with friends");
        AlbumBi_M_M_1_1 album2 = new AlbumBi_M_M_1_1("album_2", "My Shimla Vacation", "Went Shimla with friends");
        AlbumBi_M_M_1_1 album3 = new AlbumBi_M_M_1_1("album_3", "My Zurik Vacation", "Went Zurik with friends");

        album1.setPhoto(new PhotoBi_M_M_1_1("photo_1", "One beach", "On beach with friends"));
        album2.setPhoto(new PhotoBi_M_M_1_1("photo_2", "In Hotel", "Chilling out in room"));
        album3.setPhoto(new PhotoBi_M_M_1_1("photo_3", "At Airport", "So tired"));

        p1.addAlbum(album1);
        p1.addAlbum(album2);

        p2.addAlbum(album2);
        p2.addAlbum(album3);

        photographers.add(p1);
        photographers.add(p2);

        return photographers;
    }

}
