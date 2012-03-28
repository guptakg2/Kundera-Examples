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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.Metamodel;

import org.apache.cassandra.thrift.InvalidRequestException;
import org.apache.cassandra.thrift.SchemaDisagreementException;
import org.apache.cassandra.thrift.TimedOutException;
import org.apache.cassandra.thrift.UnavailableException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.thrift.TException;

import com.impetus.kundera.PersistenceProperties;
import com.impetus.kundera.examples.cli.CassandraCli;
import com.impetus.kundera.examples.cli.HBaseCli;
import com.impetus.kundera.examples.crossdatastore.useraddress.dao.UserAddressDaoImpl;
import com.impetus.kundera.metadata.model.EntityMetadata;
import com.impetus.kundera.metadata.model.KunderaMetadata;
import com.impetus.kundera.metadata.model.MetamodelImpl;
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
        String persistenceUnits = "rdbms,twingo,twissandra,twibase";
        dao = new UserAddressDaoImpl(persistenceUnits);
        em = dao.getEntityManager(persistenceUnits);
        this.colFamilies = colFamilies;
    }

    /*    *//**
     * Switch over persistence units.
     * 
     * @param entityPuCol
     *            the entity pu col
     */
    /*
     * protected void switchPersistenceUnits(Map<Class, String> entityPuCol) {
     * if (entityPuCol != null) { Iterator<Class> iter =
     * entityPuCol.keySet().iterator(); log.warn("Invocation for:"); while
     * (iter.hasNext()) { Class clazz = iter.next(); String pu =
     * entityPuCol.get(clazz); Map<String, Metamodel> metaModels =
     * KunderaMetadata.INSTANCE.getApplicationMetadata().getMetamodelMap();
     * EntityMetadata mAdd = null; for (Metamodel m : metaModels.values()) {
     * mAdd = ((MetamodelImpl) m).getEntityMetadataMap().get(clazz); if (mAdd !=
     * null) { break; } } // EntityMetadata mAdd = //
     * KunderaMetadataManager.getMetamodel
     * (pu).getEntityMetadataMap().get(clazz); mAdd.setPersistenceUnit(pu);
     * KunderaMetadataManager.getMetamodel(pu).getEntityMetadataMap().put(clazz,
     * mAdd); log.warn("persistence unit:" + pu + "class::" +
     * clazz.getCanonicalName()); } } }
     */

    /**
     * Switch over persistence units.
     * 
     * @param entityPuCol
     *            the entity pu col
     * @throws SchemaDisagreementException
     * @throws TimedOutException
     * @throws UnavailableException
     * @throws InvalidRequestException
     * @throws TException
     * @throws IOException
     */
    protected void switchPersistenceUnits(Map<Class, String> entityPuCol) throws IOException, TException,
            InvalidRequestException, UnavailableException, TimedOutException, SchemaDisagreementException
    {
        if (entityPuCol != null)
        {
            Iterator<Class> iter = entityPuCol.keySet().iterator();
            log.warn("Invocation for:");
            while (iter.hasNext())
            {
                Class clazz = iter.next();
                String pu = entityPuCol.get(clazz);
                // EntityMetadata mAdd = KunderaMetadataManager
                // .getEntityMetadata(clazz);
                Map<String, Metamodel> metaModels = KunderaMetadata.INSTANCE.getApplicationMetadata().getMetamodelMap();
                EntityMetadata mAdd = null;
                for (Metamodel m : metaModels.values())
                {
                    mAdd = ((MetamodelImpl) m).getEntityMetadataMap().get(clazz);
                    if (mAdd != null)
                    {
                        break;
                    }
                }
                mAdd.setPersistenceUnit(pu);

                PersistenceUnitMetadata puMetadata = KunderaMetadata.INSTANCE.getApplicationMetadata()
                        .getPersistenceUnitMetadata(pu);

                String client = puMetadata.getProperties().getProperty(PersistenceProperties.KUNDERA_CLIENT);
                if (client.equalsIgnoreCase("pelops"))
                {
                    CassandraCli.cassandraSetUp();

                    if (mAdd.getTableName().equalsIgnoreCase("ADDRESS"))
                    {
                        loadDataForHABITAT();
                    }
                    else if (mAdd.getTableName().equalsIgnoreCase("PERSONNEL"))
                    {
                        loadDataForPERSONNEL();
                    }
                } else if(client.equalsIgnoreCase("hbase"))
                {
                    if(!HBaseCli.isStarted())
                    {
                        String tableName = puMetadata.getProperties().getProperty(
                                PersistenceProperties.KUNDERA_KEYSPACE);
                        HBaseCli.startCluster();
                        HBaseCli.createTable(tableName);
                        HBaseCli.addColumnFamily(tableName, "ADDRESS");
                        HBaseCli.addColumnFamily(tableName, "PERSONNEL");
                    }
                }

                String schema = puMetadata.getProperty(PersistenceProperties.KUNDERA_KEYSPACE);
                // System.out.println(schema);
                mAdd.setSchema(schema != null ? schema : "test");
                // mAdd.setSchema(schema)

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
        HBaseCli.stopCluster();

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
        CassandraCli.dropKeySpace("KunderaExamples");
    }

    protected abstract void loadDataForPERSONNEL() throws TException, InvalidRequestException, UnavailableException,
            TimedOutException, SchemaDisagreementException;

    protected abstract void loadDataForHABITAT() throws TException, InvalidRequestException, UnavailableException,
            TimedOutException, SchemaDisagreementException;

}