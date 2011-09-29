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
package com.impetus.kundera.examples.pickr.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.impetus.kundera.client.Configuration;
import com.impetus.kundera.examples.pickr.entities.Album;
import com.impetus.kundera.examples.pickr.entities.PersonalData;
import com.impetus.kundera.examples.pickr.entities.Photographer;

/**
 * Implementation class for Pickr functionality
 * 
 * @author amresh.singh
 */
public class PickrImpl implements Pickr
{
    EntityManager em;

    public PickrImpl(String persistenceUnitName)
    {
        em = getEntityManager(persistenceUnitName);
    }

    public EntityManager getEntityManager(String persistenceUnitName)
    {
        Configuration conf = new Configuration();
        return conf.getEntityManager(persistenceUnitName);
    }

    @Override
    public void addPhotographer(String id, String name, String email, String address)
    {
        Photographer p = new Photographer();
        p.setPhotographerId(id);

        PersonalData pd = new PersonalData();
        pd.setName(name);
        pd.setEmail(email);
        pd.setAddress(address);

        p.setPersonalData(pd);

        em.persist(p);
    }

    @Override
    public void createAlbum(String id, String name, String description)
    {
        Photographer p = em.find(Photographer.class, "1");

        Album album = new Album();
        album.setAlbumId(id);
        album.setAlbumName(name);
        album.setAlbumDescription(description);

        p.addAlbum(album);

        em.persist(p);
    }

    @Override
    public void addPhotoToAlbum(String albumName, String photoId, String caption, String description)
    {

    }

    @Override
    public Photographer getPhotographer(String photographerId)
    {
        Photographer p = em.find(Photographer.class, "1");
        return p;
    }

    public List<Photographer> getAllPhotographers()
    {
        Query q = em.createQuery("select p from Photographer p");
        List<Photographer> photographers = q.getResultList();
        return photographers;
    }

}