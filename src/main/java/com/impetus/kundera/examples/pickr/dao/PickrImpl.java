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

import javax.persistence.EntityManager;

import com.impetus.kundera.examples.pickr.entities.Album;
import com.impetus.kundera.examples.pickr.entities.PersonalData;
import com.impetus.kundera.examples.pickr.entities.Photographer;
import com.impetus.kundera.loader.Configuration;

/**
 * <Description of functionality provided by this class>
 * 
 * @author amresh.singh
 */
public class PickrImpl implements Pickr
{
    EntityManager em;
    
    public PickrImpl(String persistenceUnitName) {
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
        em.close();
    }

    @Override
    public void createAlbum(String name, String description)
    {
       Photographer p = em.find(Photographer.class, "1");
       
       Album album = new Album();
       album.setAlbumName(name);
       album.setAlbumDescription(description);
       
       p.addAlbum(album);
       
       em.persist(p);
       em.close();
    }

    @Override
    public void addPhotoToAlbum(String albumName, String photoId, String caption, String description)
    {
        

    }
}