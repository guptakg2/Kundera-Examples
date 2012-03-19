/*******************************************************************************
 * * Copyright 2011 Impetus Infotech.
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
package com.impetus.kundera.examples.crud.entities;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Sample entity to hold map @see https://github.com/impetus-opensource/Kundera/issues/49
 * 
 * @author vivek.mishra
 *
 */
@Entity
@Table(name = "MapEntitySample", schema = "KunderaExamples@twingo")
public class MapEntitySample
{

    @Id
    @Column(name = "stuffId")
    private String stuffId;

    @Column(name = "myMap")
    private Map<Integer, Integer> myMap = new HashMap<Integer, Integer>();

    /**
     * @return the stuffId
     */
    public String getStuffId()
    {
        return stuffId;
    }

    /**
     * @param stuffId the stuffId to set
     */
    public void setStuffId(String stuffId)
    {
        this.stuffId = stuffId;
    }

    /**
     * @return the myMap
     */
    public Map<Integer, Integer> getMyMap()
    {
        return myMap;
    }

    /**
     * @param myMap the myMap to set
     */
    public void setMyMap(Map<Integer, Integer> myMap)
    {
        this.myMap = myMap;
    }
   
    
    @Override
    public String toString() {
        return "Stuff{" +
                "stuffId='" + stuffId + '\'' +
                ", stuff=" + (myMap==null ? "" : myMap.size()) +
                '}';
    }
}
