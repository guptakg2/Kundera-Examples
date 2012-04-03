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
package com.impetus.kundera.examples.crud;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import junit.framework.Assert;

import org.apache.cassandra.thrift.CfDef;
import org.apache.cassandra.thrift.ColumnDef;
import org.apache.cassandra.thrift.IndexType;
import org.apache.cassandra.thrift.InvalidRequestException;
import org.apache.cassandra.thrift.KsDef;
import org.apache.cassandra.thrift.NotFoundException;
import org.apache.cassandra.thrift.SchemaDisagreementException;
import org.apache.cassandra.thrift.TimedOutException;
import org.apache.cassandra.thrift.UnavailableException;
import org.apache.thrift.TException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.impetus.kundera.examples.cli.CassandraCli;
import com.impetus.kundera.examples.cli.HBaseCli;
import com.impetus.kundera.examples.crud.entities.PersonCassandra;
import com.impetus.kundera.examples.crud.entities.PersonHBase;
import com.impetus.kundera.examples.crud.entities.PersonMongo;
import com.impetus.kundera.examples.crud.entities.PersonRDBMS;

/**
 * Test case to perform simple CRUD operation.(insert, delete, merge, and
 * select)
 * 
 * @author vivek.mishra
 * 
 *         Run this script to create column family in cassandra with indexes.
 *         create column family PERSON with comparator=UTF8Type and
 *         column_metadata=[{column_name: PERSON_NAME, validation_class:
 *         UTF8Type, index_type: KEYS}, {column_name: AGE, validation_class:
 *         IntegerType, index_type: KEYS}];
 * 
 */
public class PersonTest extends BaseTest
{
    /** The emf. */
    private static EntityManagerFactory emf;

    /** The em. */
    private static EntityManager em;

    private Map<Object, Object> col;

    /**
     * Sets the up.
     * 
     * @throws Exception
     *             the exception
     */
    @Before
    public void setUp() throws Exception
    {
        emf = Persistence.createEntityManagerFactory("twissandra,twingo,twibase,picmysql");
        em = emf.createEntityManager();
        col = new java.util.HashMap<Object, Object>();
    }

    /**
     * On insert mongo.
     */
    @Test
    public void onInsertMongo()
    {
        Object p1 = prepareMongoInstance("1", 10);
        Object p2 = prepareMongoInstance("2", 20);
        Object p3 = prepareMongoInstance("3", 15);
        em.persist(p1);
        em.persist(p2);
        em.persist(p3);
        col.put("1", p1);
        col.put("2", p2);
        col.put("3", p3);
        PersonMongo p = findById(PersonMongo.class, "1", em);
        Assert.assertNotNull(p);
        Assert.assertEquals("vivek", p.getPersonName());
        assertFindByName(em, "PersonMongo", PersonMongo.class, "vivek", "PERSON_NAME");
        assertFindByNameAndAge(em, "PersonMongo", PersonMongo.class, "vivek", "10", "PERSON_NAME");
        assertFindByNameAndAgeGTAndLT(em, "PersonMongo", PersonMongo.class, "vivek", "10", "20", "PERSON_NAME");
        assertFindByNameAndAgeBetween(em, "PersonMongo", PersonMongo.class, "vivek", "10", "15", "PERSON_NAME");
        assertFindByRange(em, "PersonMongo", PersonMongo.class, "1", "2", "PERSON_ID");
        assertFindWithoutWhereClause(em, "PersonMongo", PersonMongo.class);

        Query query = em.createNamedQuery("mongo.named.query");
        query.setParameter("name", "vivek");
        List<PersonMongo> results = query.getResultList();
        Assert.assertEquals(3, results.size());
    }

    /**
     * On merge mongo.
     */
    @Test
    public void onMergeMongo()
    {
        Object p1 = prepareMongoInstance("1", 10);
        Object p2 = prepareMongoInstance("2", 20);
        Object p3 = prepareMongoInstance("3", 15);
        em.persist(p1);
        em.persist(p2);
        em.persist(p3);
        col.put("1", p1);
        col.put("2", p2);
        col.put("3", p3);
        PersonMongo p = findById(PersonMongo.class, "1", em);
        Assert.assertNotNull(p);
        Assert.assertEquals("vivek", p.getPersonName());
        // modify record.
        p.setPersonName("newvivek");
        em.merge(p);
        assertOnMerge(em, "PersonMongo", PersonMongo.class, "vivek", "newvivek", "PERSON_NAME");
    }

    /**
     * On insert cassandra.
     */
     @Test
    public void onInsertCassandra() throws Exception 
    {
//        cassandraSetUp();
        CassandraCli.cassandraSetUp();
        loadData();

        Object p1 = prepareData("1", 10);
        Object p2 = prepareData("2", 20);
        Object p3 = prepareData("3", 15);
        em.persist(p1);
        em.persist(p2);
        em.persist(p3);
        // col.put("1", p1);
        // col.put("2", p2);
        // col.put("3", p3);

        PersonCassandra p = findById(PersonCassandra.class, "1", em);
        Assert.assertNotNull(p);
        Assert.assertEquals("vivek", p.getPersonName());
        assertFindByName(em, "PersonCassandra", PersonCassandra.class, "vivek", "PERSON_NAME");
        assertFindByNameAndAge(em, "PersonCassandra", PersonCassandra.class, "vivek", "10", "PERSON_NAME");
        assertFindByNameAndAgeGTAndLT(em, "PersonCassandra", PersonCassandra.class, "vivek", "10", "20", "PERSON_NAME");
        assertFindByNameAndAgeBetween(em, "PersonCassandra", PersonCassandra.class, "vivek", "10", "15", "PERSON_NAME");
        assertFindByRange(em, "PersonCassandra", PersonCassandra.class, "1", "2", "PERSON_ID");
        assertFindWithoutWhereClause(em, "PersonCassandra", PersonCassandra.class);
    }

    /**
     * On merge cassandra.
     */
     @Test
    public void onMergeCassandra() throws Exception
    {
         CassandraCli.cassandraSetUp();
         loadData();
        Object p1 = prepareData("1", 10);
        Object p2 = prepareData("2", 20);
        Object p3 = prepareData("3", 15);
        em.persist(p1);
        em.persist(p2);
        em.persist(p3);
        // col.put("1", p1);
        // col.put("2", p2);
        // col.put("3", p3);
        PersonCassandra p = findById(PersonCassandra.class, "1", em);
        Assert.assertNotNull(p);
        Assert.assertEquals("vivek", p.getPersonName());
        // modify record.
        p.setPersonName("newvivek");
        em.merge(p);

        assertOnMerge(em, "PersonCassandra", PersonCassandra.class, "vivek", "newvivek", "PERSON_NAME");
    }

    @Test
    public void onInsertHbase() throws Exception
    {
        HBaseCli.startCluster();
        HBaseCli.createColumnFamily("PERSON", "PERSON");
        HBaseCli.addColumn("PERSON", "PERSON", "PERSON_NAME");
        HBaseCli.addColumn("PERSON", "PERSON", "AGE");
        Object p1 = prepareHbaseInstance("1", 10);
        Object p2 = prepareHbaseInstance("2", 20);
        Object p3 = prepareHbaseInstance("3", 15);
        em.persist(p1);
        em.persist(p2);
        em.persist(p3);
        col.put("1", p1);
        col.put("2", p2);
        col.put("3", p3);
        PersonHBase personHBase = findById(PersonHBase.class, "1", em);
        Assert.assertNotNull(personHBase);
        Assert.assertEquals("vivek", personHBase.getPersonName());
        assertFindByName(em, "PersonHBase", PersonHBase.class, "vivek", "PERSON_NAME");
        assertFindByNameAndAge(em, "PersonHBase", PersonHBase.class, "vivek", "10", "PERSON_NAME");
        assertFindByNameAndAgeGTAndLT(em, "PersonHBase", PersonHBase.class, "vivek", "10", "20", "PERSON_NAME");
        assertFindByNameAndAgeBetween(em, "PersonHBase", PersonHBase.class, "vivek", "10", "15", "PERSON_NAME");
        assertFindByRange(em, "PersonHBase", PersonHBase.class, "1", "2", "PERSON_ID");
        assertFindWithoutWhereClause(em, "PersonHBase", PersonHBase.class);
    }

    // @Test
    // public void onMergeHbase() {
    // em.persist(prepareHbaseInstance("1", 10));
    // PersonHBase personHBase = findById(PersonHBase.class, "1", em);
    // Assert.assertNotNull(personHBase);
    // Assert.assertEquals("vivek", personHBase.getPersonName());
    // personHBase.setPersonName("Newvivek");
    //
    // em.merge(personHBase);
    // assertOnMerge(em, "PersonHBase", PersonHBase.class);
    // o.add(PersonHBase.class);
    // }

    // @Test
    public void onInsertRdbms()
    {
        Object p1 = prepareRDBMSInstance("1", 10);
        Object p2 = prepareRDBMSInstance("2", 20);
        Object p3 = prepareRDBMSInstance("3", 15);
        em.persist(p1);
        em.persist(p2);
        em.persist(p3);
        col.put("1", p1);
        col.put("2", p2);
        col.put("3", p3);
        em.persist(prepareRDBMSInstance("1", 10));
        em.persist(prepareRDBMSInstance("2", 15));
        em.persist(prepareRDBMSInstance("3", 20));
        PersonRDBMS personRDBMS = findById(PersonRDBMS.class, "1", em);
        Assert.assertNotNull(personRDBMS);
        Assert.assertEquals("vivek", personRDBMS.getPersonName());
        assertFindByName(em, "PersonRDBMS", PersonRDBMS.class, "vivek", "PERSON_NAME");
        assertFindByNameAndAge(em, "PersonRDBMS", PersonRDBMS.class, "vivek", "10", "PERSON_NAME");
        assertFindByNameAndAgeGTAndLT(em, "PersonRDBMS", PersonRDBMS.class, "vivek", "10", "20", "PERSON_NAME");
        assertFindByNameAndAgeBetween(em, "PersonRDBMS", PersonRDBMS.class, "vivek", "10", "15", "PERSON_NAME");
        assertFindByRange(em, "PersonRDBMS", PersonRDBMS.class, "1", "2", "PERSON_ID");
        assertFindWithoutWhereClause(em, "PersonRDBMS", PersonRDBMS.class);
    }

    // @Test
    // public void onMergeRdbms() {
    // em.persist(prepareRDBMSInstance("1", 10));
    // PersonRDBMS personRDBMS = findById(PersonRDBMS.class, "1", em);
    // Assert.assertNotNull(personRDBMS);
    // Assert.assertEquals("vivek", personRDBMS.getPersonName());
    // personRDBMS.setPersonName("Newvivek");
    //
    // em.merge(personRDBMS);
    // assertOnMerge(em, "PersonRDBMS", PersonRDBMS.class);
    // o.add(PersonRDBMS.class);
    // }

    /**
     * Tear down.
     * 
     * @throws Exception
     *             the exception
     */
    @After
    public void tearDown() throws Exception
    {/*
      * Delete is working, but as row keys are not deleted from cassandra, so
      * resulting in issue while reading back. // Delete
      * em.remove(em.find(Person.class, "1")); em.remove(em.find(Person.class,
      * "2")); em.remove(em.find(Person.class, "3")); em.close(); emf.close();
      * em = null; emf = null;
      */
        for (Object val : col.values())
        {
            em.remove(val);
        }
        HBaseCli.stopCluster();
        CassandraCli.dropKeySpace("KunderaExamples");
    }

    /**
     * Load cassandra specific data.
     * @throws TException
     * @throws InvalidRequestException
     * @throws UnavailableException
     * @throws TimedOutException
     * @throws SchemaDisagreementException
     */
    private void loadData() throws TException, InvalidRequestException, UnavailableException, TimedOutException,
            SchemaDisagreementException
    {

        KsDef ksDef = null;
        CfDef user_Def = new CfDef();
        user_Def.name = "PERSON";
        user_Def.keyspace = "KunderaExamples";
        user_Def.setComparator_type("UTF8Type");
        user_Def.setDefault_validation_class("UTF8Type");
        ColumnDef columnDef = new ColumnDef(ByteBuffer.wrap("PERSON_NAME".getBytes()), "UTF8Type");
        columnDef.index_type = IndexType.KEYS;
        user_Def.addToColumn_metadata(columnDef);
        ColumnDef columnDef1 = new ColumnDef(ByteBuffer.wrap("AGE".getBytes()), "UTF8Type");
        columnDef1.index_type = IndexType.KEYS;
        user_Def.addToColumn_metadata(columnDef1);

        List<CfDef> cfDefs = new ArrayList<CfDef>();
        cfDefs.add(user_Def);

        try
        {
            ksDef = CassandraCli.client.describe_keyspace("KunderaExamples");
            CassandraCli.client.set_keyspace("KunderaExamples");

            List<CfDef> cfDefn = ksDef.getCf_defs();

            for (CfDef cfDef1 : cfDefn)
            {

                if (cfDef1.getName().equalsIgnoreCase("PERSON"))
                {

                    CassandraCli.client.system_drop_column_family("PERSON");

                }
            }
            CassandraCli.client.system_add_column_family(user_Def);

        }
        catch (NotFoundException e)
        {

            ksDef = new KsDef("KunderaExamples", "org.apache.cassandra.locator.SimpleStrategy", cfDefs);
            ksDef.setReplication_factor(1);
            CassandraCli.client.system_add_keyspace(ksDef);
        }

        CassandraCli.client.set_keyspace("KunderaExamples");

    }
}
