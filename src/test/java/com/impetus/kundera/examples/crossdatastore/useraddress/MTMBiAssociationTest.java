package com.impetus.kundera.examples.crossdatastore.useraddress;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

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
import com.impetus.kundera.examples.crossdatastore.useraddress.entities.HabitatBiMToM;
import com.impetus.kundera.examples.crossdatastore.useraddress.entities.PersonnelBiMToM;

public class MTMBiAssociationTest extends TwinAssociation

{

	/**
	 * Inits the.
	 */
	@BeforeClass
	public static void init() throws Exception {
		CassandraCli.cassandraSetUp();
		List<Class> clazzz = new ArrayList<Class>(2);
		clazzz.add(PersonnelBiMToM.class);
		clazzz.add(HabitatBiMToM.class);
		init(clazzz, "twingo", "twissandra");
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
		// TODO Auto-generated method stub

	}

	@Override
	protected void insert() {
		// TODO Auto-generated method stub

	}

	/**
	 * Test merge.
	 */
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
		cfDef.addToColumn_metadata(columnDef);

		ColumnDef columnDef1 = new ColumnDef(ByteBuffer.wrap("ADDRESS_ID"
				.getBytes()), "IntegerType");
		cfDef.addToColumn_metadata(columnDef1);

		List<CfDef> cfDefs = new ArrayList<CfDef>();
		cfDefs.add(cfDef);

		try {
			ksDef = CassandraCli.client.describe_keyspace("KunderaExamples");
			CassandraCli.client.set_keyspace("KunderaExamples");

			List<CfDef> cfDefn = ksDef.getCf_defs();
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

		ColumnDef columnDef2 = new ColumnDef(ByteBuffer.wrap("PERSON_ID"
				.getBytes()), "IntegerType");
		columnDef2.index_type = IndexType.KEYS;
		cfDef2.addToColumn_metadata(columnDef2);

		List<CfDef> cfDefs = new ArrayList<CfDef>();
		cfDefs.add(cfDef2);

		try {
			ksDef = CassandraCli.client.describe_keyspace("KunderaExamples");
			CassandraCli.client.set_keyspace("KunderaExamples");
			List<CfDef> cfDefss = ksDef.getCf_defs();
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
		HBaseCli.addColumn("ADDRESS", "ADDRESS", "PERSON_ID");

	}

}
