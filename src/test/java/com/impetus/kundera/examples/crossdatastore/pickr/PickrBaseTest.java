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

import java.io.File;

import com.impetus.kundera.examples.crossdatastore.pickr.dao.Pickr;
import com.impetus.kundera.examples.crossdatastore.pickr.dao.PickrImpl;
import com.impetus.kundera.metadata.model.KunderaMetadata;
import com.impetus.kundera.metadata.model.PersistenceUnitMetadata;

/**
 * @author amresh.singh
 *
 */
public abstract class PickrBaseTest
{
    Pickr pickr;
    int photographerId;

    
    protected void setUp() throws Exception
    {
        photographerId = 1;
        pickr = new PickrImpl("picmysql");
    }

    public void test()
    {       
               
    }
    
    protected void tearDown() throws Exception
    {
        pickr.close();
        //cleanLuceneDirectory();
    }
    
    protected abstract void addPhotographer();
    protected abstract void updatePhotographer();
    protected abstract void getPhotographer();
    protected abstract void getAllPhotographers();
    protected abstract void deletePhotographer();
    
    
    private void cleanLuceneDirectory() {
        PersistenceUnitMetadata puMetadata = KunderaMetadata.INSTANCE.getApplicationMetadata().getPersistenceUnitMetadata("piccandra");
        if(puMetadata != null ) {
            String luceneDir = puMetadata.getProperty("index_home_dir");
            if (luceneDir != null && luceneDir.length() > 0)
            {
                System.out.println("Cleaning up lucene folder " + luceneDir);
                File directory = new File(luceneDir);
                // Get all files in directory
                File[] files = directory.listFiles();
                for (File file : files)
                {
                    // Delete each file
                    if (!file.delete())
                    {
                        // Failed to delete file
                        System.out.println("Failed to delete " + file);
                    }
                }
            }
        }
        
        
    }
    
}
