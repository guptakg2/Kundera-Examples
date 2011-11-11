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

import com.impetus.kundera.examples.pickr.entities.Photographer;

/**
 * Interface for Pickr application
 * 
 * @author amresh.singh
 */
public interface Pickr
{
    void addPhotographerAndAlbums(Photographer p);

    void addPhotographer(String id, String name, String email, String address);

    void createAlbum(String id, String name, String description);

    void addPhotoToAlbum(String albumName, String photoId, String caption, String description);

    Photographer getPhotographer(String photographerId);

    public List<Photographer> getAllPhotographers();
    
    public void close();
}
