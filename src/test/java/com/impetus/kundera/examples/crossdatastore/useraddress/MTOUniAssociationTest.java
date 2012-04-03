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

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import org.junit.BeforeClass;
import org.junit.Test;

import com.impetus.kundera.examples.cli.CassandraCli;
import com.impetus.kundera.examples.cli.HBaseCli;
import com.impetus.kundera.examples.crossdatastore.useraddress.entities.HabitatUniMTo1;
import com.impetus.kundera.examples.crossdatastore.useraddress.entities.PersonalData;
import com.impetus.kundera.examples.crossdatastore.useraddress.entities.PersonnelUniMTo1;

/**
 * @author vivek.mishra
 * 
 */

public class MTOUniAssociationTest extends TwinAssociation {
	/**
	 * Inits the.
	 */
	@BeforeClass
	public static void init() throws Exception {
		 if(RUN_IN_EMBEDDED_MODE) {
	            CassandraCli.cassandraSetUp();
	        }
	        
	        List<Class> clazzz = new ArrayList<Class>(2);
	        clazzz.add(PersonnelUniMTo1.class);
	        clazzz.add(HabitatUniMTo1.class);
	        init(clazzz, ALL_PUs_UNDER_TEST);
	}

	/**
	 * Sets the up.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@Before
	public void setUp() throws Exception {
		setUpInternal();
	}

	/**
	 * Test insert.
	 */
	@Test
	public void testInsert() {
		tryOperation();
	}

	@Override
	protected void find() {
		// Find Person 1
		PersonnelUniMTo1 p1 = (PersonnelUniMTo1) dao.findPerson(
				PersonnelUniMTo1.class, "unimanytoone_1");
		Assert.assertNotNull(p1);
		Assert.assertEquals("unimanytoone_1", p1.getPersonId());
		Assert.assertEquals("Amresh", p1.getPersonName());
		PersonalData pd = p1.getPersonalData();
		Assert.assertNotNull(pd);
		Assert.assertEquals("www.amresh.com", pd.getWebsite());

		HabitatUniMTo1 add = p1.getAddress();
		Assert.assertNotNull(add);

		Assert.assertEquals("unimanytoone_a", add.getAddressId());
		Assert.assertEquals("AAAAAAAAAAAAA", add.getStreet());

		// Find Person 2
		PersonnelUniMTo1 p2 = (PersonnelUniMTo1) dao.findPerson(
				PersonnelUniMTo1.class, "unimanytoone_2");
		Assert.assertNotNull(p2);
		Assert.assertEquals("unimanytoone_2", p2.getPersonId());
		Assert.assertEquals("Vivek", p2.getPersonName());
		PersonalData pd2 = p2.getPersonalData();
		Assert.assertNotNull(pd2);
		Assert.assertEquals("www.vivek.com", pd2.getWebsite());

		HabitatUniMTo1 add2 = p2.getAddress();
		Assert.assertNotNull(add2);

		Assert.assertEquals("unimanytoone_a", add2.getAddressId());
		Assert.assertEquals("AAAAAAAAAAAAA", add2.getStreet());

	}

	@Override
	protected void insert() {
		PersonnelUniMTo1 person1 = new PersonnelUniMTo1();
		person1.setPersonId("unimanytoone_1");
		person1.setPersonName("Amresh");
		person1.setPersonalData(new PersonalData("www.amresh.com",
				"amry.ks@gmail.com", "xamry"));

		PersonnelUniMTo1 person2 = new PersonnelUniMTo1();
		person2.setPersonId("unimanytoone_2");
		person2.setPersonName("Vivek");
		person2.setPersonalData(new PersonalData("www.vivek.com",
				"vivek@gmail.com", "mevivs"));

		HabitatUniMTo1 address = new HabitatUniMTo1();
		address.setAddressId("unimanytoone_a");
		address.setStreet("AAAAAAAAAAAAA");

		person1.setAddress(address);
		person2.setAddress(address);

		Set<PersonnelUniMTo1> persons = new HashSet<PersonnelUniMTo1>();
		persons.add(person1);
		persons.add(person2);

		dao.savePersons(persons);

		col.add(person1);
		col.add(person2);
		col.add(address);
	}
	
	 @Override
	    protected void update()
	    {
	        try
	        {
	            for(Object entity : col) {
	                if(entity.getClass().equals(PersonnelUniMTo1.class)) {
	                    PersonnelUniMTo1 personnel = (PersonnelUniMTo1) entity;
	                    personnel.setPersonName("Saurabh");
	                    dao.merge(personnel);
	                } else if(entity.getClass().equals(HabitatUniMTo1.class)) {
	                    HabitatUniMTo1 address = (HabitatUniMTo1) entity;
	                    address.setStreet("Brand new street");
	                    dao.merge(address);
	                }
	            }
	            
	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	            Assert.fail();
	        }
	    }

	    @Override
	    protected void remove()
	    {
	        try
	        {
	            
	            PersonnelUniMTo1 p1 = (PersonnelUniMTo1) dao.findPerson(PersonnelUniMTo1.class, "unimanytoone_1");
	            dao.removePerson(p1);
	            
	            PersonnelUniMTo1 p2 = (PersonnelUniMTo1) dao.findPerson(PersonnelUniMTo1.class, "unimanytoone_2");
	            dao.removePerson(p2);
	            
	            PersonnelUniMTo1 p3 = (PersonnelUniMTo1) dao.findPerson(PersonnelUniMTo1.class, "unimanytoone_3");
	            dao.removePerson(p3);
	            
	            
	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	            Assert.fail();
	        }
	    }

	@Test
	public void testMerge() {

	}

	/**
	 * Tear down.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@After
	public void tearDown() throws Exception {
		tearDownInternal();
		CassandraCli.dropKeySpace("KunderaExamples");
	}

	@Override
	protected void loadDataForPERSONNEL() throws TException,
			InvalidRequestException, UnavailableException, TimedOutException,
			SchemaDisagreementException {
		KsDef ksDef = null;

		CfDef cfDef = new CfDef();
		cfDef.name = "PERSONNEL";
		cfDef.keyspace = "KunderaExamples";
		cfDef.column_type = "Super";
		cfDef.setComparator_type("UTF8Type");
		cfDef.setDefault_validation_class("UTF8Type");
		ColumnDef columnDef = new ColumnDef(ByteBuffer.wrap("PERSON_NAME"
				.getBytes()), "UTF8Type");
		// columnDef.index_type = IndexType.KEYS;
		cfDef.addToColumn_metadata(columnDef);

		ColumnDef columnDef1 = new ColumnDef(ByteBuffer.wrap("ADDRESS_ID"
				.getBytes()), "IntegerType");
		// columnDef1.index_type = IndexType.KEYS;
		cfDef.addToColumn_metadata(columnDef1);

		List<CfDef> cfDefs = new ArrayList<CfDef>();
		cfDefs.add(cfDef);

		try {
			ksDef = CassandraCli.client.describe_keyspace("KunderaExamples");
			CassandraCli.client.set_keyspace("KunderaExamples");

			List<CfDef> cfDefn = ksDef.getCf_defs();

			// CassandraCli.client.set_keyspace("KunderaExamples");
			for (CfDef cfDef1 : cfDefn) {

				if (cfDef1.getName().equalsIgnoreCase("PERSONNEL")) {

					CassandraCli.client.system_drop_column_family("PERSONNEL");

				}
			}
			CassandraCli.client.system_add_column_family(cfDef);

		} catch (NotFoundException e) {

			ksDef = new KsDef("KunderaExamples",
					"org.apache.cassandra.locator.SimpleStrategy", cfDefs);
			ksDef.setReplication_factor(1);
			CassandraCli.client.system_add_keyspace(ksDef);
		}

		CassandraCli.client.set_keyspace("KunderaExamples");

	}

	@Override
	protected void loadDataForHABITAT() throws TException,
			InvalidRequestException, UnavailableException, TimedOutException,
			SchemaDisagreementException {
		KsDef ksDef = null;
		CfDef cfDef2 = new CfDef();
		cfDef2.name = "ADDRESS";
		cfDef2.keyspace = "KunderaExamples";

		ColumnDef columnDef1 = new ColumnDef(ByteBuffer.wrap("STREET"
				.getBytes()), "UTF8Type");
		columnDef1.index_type = IndexType.KEYS;
		cfDef2.addToColumn_metadata(columnDef1);

		List<CfDef> cfDefs = new ArrayList<CfDef>();
		cfDefs.add(cfDef2);

		try {
			ksDef = CassandraCli.client.describe_keyspace("KunderaExamples");
			CassandraCli.client.set_keyspace("KunderaExamples");
			List<CfDef> cfDefss = ksDef.getCf_defs();
			// CassandraCli.client.set_keyspace("KunderaExamples");
			for (CfDef cfDef : cfDefss) {

				if (cfDef.getName().equalsIgnoreCase("ADDRESS")) {

					CassandraCli.client.system_drop_column_family("ADDRESS");

				}
			}
			CassandraCli.client.system_add_column_family(cfDef2);
		} catch (NotFoundException e) {

			ksDef = new KsDef("KunderaExamples",
					"org.apache.cassandra.locator.SimpleStrategy", cfDefs);

			ksDef.setReplication_factor(1);
			CassandraCli.client.system_add_keyspace(ksDef);

		}
		CassandraCli.client.set_keyspace("KunderaExamples");

	}

	@Override
	protected void hBaseLoadDataForPERSONNEL() throws TException,
			InvalidRequestException, UnavailableException, TimedOutException,
			SchemaDisagreementException {
		HBaseCli.createColumnFamily("PERSONNEL", "PERSONNEL");
		HBaseCli.addColumn("PERSONNEL", "PERSONNEL", "PERSON_NAME");
		HBaseCli.addColumn("PERSONNEL", "PERSONNEL", "ADDRESS_ID");

	}

	@Override
	protected void hBaseLoadDataForHABITAT() throws TException,
			InvalidRequestException, UnavailableException, TimedOutException,
			SchemaDisagreementException {
		HBaseCli.createColumnFamily("ADDRESS", "ADDRESS");
		HBaseCli.addColumn("ADDRESS", "ADDRESS", "STREET");

	}


}
