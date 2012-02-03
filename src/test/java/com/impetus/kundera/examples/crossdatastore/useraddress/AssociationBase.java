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
package com.impetus.kundera.examples.crossdatastore.useraddress;

import java.util.Iterator;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.cassandra.thrift.InvalidRequestException;
import org.apache.cassandra.thrift.SchemaDisagreementException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.impetus.kundera.examples.crossdatastore.useraddress.dao.UserAddressDaoImpl;
import com.impetus.kundera.metadata.KunderaMetadataManager;
import com.impetus.kundera.metadata.model.EntityMetadata;

/**
 * @author vivek.mishra
 *
 */
public abstract class AssociationBase
{
   
    protected EntityManager em;

    protected UserAddressDaoImpl dao;
    
    /** the log used by this class. */
    private static Log log = LogFactory.getLog(AssociationBase.class);

    
    protected void setUpInternal()
    {
        String persistenceUnits="twissandra,twibase,twingo";
        dao = new UserAddressDaoImpl(persistenceUnits);
        em = dao.getEntityManager(persistenceUnits);
    }


    /**
     * Switch over persistence units.
     * @param entityPuCol
     */
    protected void switchPersistenceUnits(Map<Class, String> entityPuCol)
    {
        if(entityPuCol != null)
        {
            Iterator<Class> iter = entityPuCol.keySet().iterator();
            log.info("Invocation for:");
            while(iter.hasNext())
            {
                Class clazz = iter.next();
                String pu = entityPuCol.get(clazz);
                EntityMetadata mAdd = KunderaMetadataManager.getEntityMetadata(pu, clazz);
                mAdd.setPersistenceUnit(pu);
                
                log.info("persistence unit:" + pu + "class::" + clazz.getCanonicalName());
            }
        }
    }

    /**
     * @throws SchemaDisagreementException 
     * @throws InvalidRequestException 
     * 
     */
    protected void tearDownInternal() throws InvalidRequestException, SchemaDisagreementException
    {
       dao.closeEntityManagerFactory();
       CassandraCli.truncate("KunderaExamples", "localhost", 9160,"ADDRESS","PERSONNEL");
    }
    
}
