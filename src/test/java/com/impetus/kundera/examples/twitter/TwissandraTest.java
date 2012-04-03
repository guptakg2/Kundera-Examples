/*
 * Copyright 2011 Impetus Infotech.
 *
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
package com.impetus.kundera.examples.twitter;

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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.thrift.TException;

import com.impetus.kundera.examples.cli.CassandraCli;
import com.impetus.kundera.examples.twitter.query.CassandraQuerySuite;

/**
 * Test case for Cassandra. addTweets();
 * 
 * @author amresh.singh
 */
public class TwissandraTest extends CassandraQuerySuite {

	/** The Constant LOG. */
	private static final Log LOG = LogFactory.getLog(TwissandraTest.class);

	@Override
	protected void setUp() throws Exception {
		CassandraCli.cassandraSetUp();
		loadDataForUser();
		loadDataForExternalLinks();
		loadDataForPreference();
		setUpInternal("twissandraForLucene");

	}

	/**
	 * Test on execute.
	 */
	public void testOnExecute() throws Exception {
		executeTestSuite();
	}

	/**
	 * Test on execute query.
	 */

	/*
	 * public void testOnQuery() { executeQuerySuite(); }
	 */

	public void loadDataForUser() throws InvalidRequestException, TException,
			SchemaDisagreementException {
		KsDef ksDef = null;
		CfDef cfDef = new CfDef();
		cfDef.name = "USER";
		cfDef.keyspace = "KunderaExamples";
		cfDef.column_type = "Super";
		cfDef.setComparator_type("UTF8Type");
		cfDef.setDefault_validation_classIsSet(true);

		ColumnDef columnDef = new ColumnDef(ByteBuffer.wrap("FRIEND_ID"
				.getBytes()), "UTF8Type");

		cfDef.addToColumn_metadata(columnDef);

		ColumnDef columnDef2 = new ColumnDef(ByteBuffer.wrap("FOLLOWER_ID"
				.getBytes()), "UTF8Type");
		cfDef.addToColumn_metadata(columnDef2);

		ColumnDef columnDef3 = new ColumnDef(ByteBuffer.wrap("PREFERENCE_ID"
				.getBytes()), "UTF8Type");
		cfDef.addToColumn_metadata(columnDef3);

		List<CfDef> cfDefs = new ArrayList<CfDef>();
		cfDefs.add(cfDef);

		try {
			ksDef = com.impetus.kundera.examples.cli.CassandraCli.client
					.describe_keyspace("KunderaExamples");
			CassandraCli.client.set_keyspace("KunderaExamples");

			List<CfDef> cfDefn = ksDef.getCf_defs();

			for (CfDef cfDef1 : cfDefn) {
				if (cfDef1.getName().equalsIgnoreCase("USER")) {
					CassandraCli.client.system_drop_column_family("USER");

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

	public void loadDataForPreference() throws InvalidRequestException,
			TException, SchemaDisagreementException {
		KsDef ksDef = null;
		CfDef cfDef2 = new CfDef();
		cfDef2.name = "PREFERENCE";
		cfDef2.keyspace = "KunderaExamples";

		ColumnDef columnDef1 = new ColumnDef(ByteBuffer.wrap("WEBSITE_THEME"
				.getBytes()), "UTF8Type");
		columnDef1.index_type = IndexType.KEYS;
		cfDef2.addToColumn_metadata(columnDef1);

		ColumnDef columnDef2 = new ColumnDef(ByteBuffer.wrap("PRIVACY_LEVEL"
				.getBytes()), "UTF8Type");
		columnDef2.index_type = IndexType.KEYS;
		cfDef2.addToColumn_metadata(columnDef2);

		List<CfDef> cfDefs = new ArrayList<CfDef>();
		cfDefs.add(cfDef2);
		try {

			ksDef = com.impetus.kundera.examples.cli.CassandraCli.client
					.describe_keyspace("KunderaExamples");

			CassandraCli.client.set_keyspace("KunderaExamples");

			List<CfDef> cfDefn = ksDef.getCf_defs();

			for (CfDef cfDef1 : cfDefn) {
				if (cfDef1.getName().equalsIgnoreCase("PREFERENCE")) {
					CassandraCli.client.system_drop_column_family("PREFERENCE");
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

	public void loadDataForExternalLinks() throws InvalidRequestException,
			TException, SchemaDisagreementException {
		KsDef ksDef = null;
		CfDef cfDef3 = new CfDef();
		cfDef3.name = "EXTERNAL_LINK";
		cfDef3.keyspace = "KunderaExamples";

		ColumnDef columnDef1 = new ColumnDef(ByteBuffer.wrap("LINK_TYPE"
				.getBytes()), "UTF8Type");
		columnDef1.index_type = IndexType.KEYS;
		cfDef3.addToColumn_metadata(columnDef1);

		ColumnDef columnDef2 = new ColumnDef(ByteBuffer.wrap("LINK_ADDRESS"
				.getBytes()), "UTF8Type");
		columnDef2.index_type = IndexType.KEYS;
		cfDef3.addToColumn_metadata(columnDef2);

		ColumnDef columnDef3 = new ColumnDef(ByteBuffer.wrap("USER_ID"
				.getBytes()), "UTF8Type");
		columnDef3.index_type = IndexType.KEYS;
		cfDef3.addToColumn_metadata(columnDef3);

		List<CfDef> cfDefs = new ArrayList<CfDef>();
		cfDefs.add(cfDef3);
		try {
			ksDef = com.impetus.kundera.examples.cli.CassandraCli.client
					.describe_keyspace("KunderaExamples");
			CassandraCli.client.set_keyspace("KunderaExamples");

			List<CfDef> cfDefn = ksDef.getCf_defs();

			for (CfDef cfDef1 : cfDefn) {
				if (cfDef1.getName().equalsIgnoreCase("EXTERNAL_LINK")) {
					CassandraCli.client
							.system_drop_column_family("EXTERNAL_LINK");
				}
			}
			CassandraCli.client.system_add_column_family(cfDef3);
		} catch (NotFoundException e) {

			ksDef = new KsDef("KunderaExamples",
					"org.apache.cassandra.locator.SimpleStrategy", cfDefs);
			ksDef.setReplication_factor(1);
			CassandraCli.client.system_add_keyspace(ksDef);

		}
		CassandraCli.client.set_keyspace("KunderaExamples");

	}

	@Override
	protected void tearDown() throws Exception {
		tearDownInternal();
		CassandraCli.dropKeySpace("KunderaExamples");
	}
}
