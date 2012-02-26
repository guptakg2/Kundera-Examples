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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.cassandra.thrift.InvalidRequestException;
import org.apache.cassandra.thrift.SchemaDisagreementException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.impetus.kundera.PersistenceProperties;
import com.impetus.kundera.examples.crossdatastore.useraddress.dao.UserAddressDaoImpl;
import com.impetus.kundera.metadata.KunderaMetadataManager;
import com.impetus.kundera.metadata.model.EntityMetadata;
import com.impetus.kundera.metadata.model.PersistenceUnitMetadata;

/**
 * The Class AssociationBase.
 * 
 * @author vivek.mishra
 */
public abstract class AssociationBase
{

    /** The em. */
    protected EntityManager em;

    /** The dao. */
    protected UserAddressDaoImpl dao;

    /** the log used by this class. */
    private static Log log = LogFactory.getLog(AssociationBase.class);

    /** The col families. */
    private String[] colFamilies;

    protected List<Object> col = new ArrayList<Object>();

    /**
     * Sets the up internal.
     * 
     * @param colFamilies
     *            the new up internal
     */
    protected void setUpInternal(String... colFamilies)
    {
        String persistenceUnits = "twissandra,twibase,twingo,picmysql";
        dao = new UserAddressDaoImpl(persistenceUnits);
        em = dao.getEntityManager(persistenceUnits);
        this.colFamilies = colFamilies;
    }

    /**
     * Switch over persistence units.
     * 
     * @param entityPuCol
     *            the entity pu col
     */
    protected void switchPersistenceUnits(Map<Class, String> entityPuCol)
    {
        if (entityPuCol != null)
        {
            Iterator<Class> iter = entityPuCol.keySet().iterator();
            log.warn("Invocation for:");
            while (iter.hasNext())
            {
                Class clazz = iter.next();
                String pu = entityPuCol.get(clazz);
                EntityMetadata mAdd = KunderaMetadataManager.getEntityMetadata(clazz);
                mAdd.setPersistenceUnit(pu);
                PersistenceUnitMetadata puMetadata = KunderaMetadataManager.getPersistenceUnitMetadata(pu);
                String schema = puMetadata.getProperty(PersistenceProperties.KUNDERA_KEYSPACE);
//                System.out.println(schema);
                mAdd.setSchema(schema != null? schema:"test");
//                mAdd.setSchema(schema)

                log.warn("persistence unit:" + pu + "class::" + clazz.getCanonicalName());
            }
        }
    }

    /**
     * Tear down internal.
     * 
     * @throws InvalidRequestException
     *             the invalid request exception
     * @throws SchemaDisagreementException
     *             the schema disagreement exception
     */
    protected void tearDownInternal() throws InvalidRequestException, SchemaDisagreementException
    {

        for (Object o : col)
        {
            em.remove(o);
        }
        truncateSchema();
        dao.closeEntityManagerFactory();

    }

    /**
     * Truncates schema.
     * 
     * @throws InvalidRequestException
     *             the invalid request exception
     * @throws SchemaDisagreementException
     *             the schema disagreement exception
     */
    protected void truncateSchema() throws InvalidRequestException, SchemaDisagreementException
    {
        log.warn("Truncating....");
        CassandraCli.truncate("KunderaExamples", "localhost", 9160, colFamilies);
    }

}
