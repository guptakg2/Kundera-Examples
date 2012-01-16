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
package com.impetus.kundera.examples.perf.dao.user;

import java.util.ArrayList;
import java.util.List;

import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.MutationResult;
import me.prettyprint.hector.api.mutation.Mutator;

import com.impetus.kundera.examples.perf.dao.HectorBaseDao;
import com.impetus.kundera.examples.perf.dto.UserCassandraDTO;

/**
 * @author amresh.singh
 * 
 */
public class UserDaoHectorImpl extends HectorBaseDao implements UserDao {

	private int counter;

	@Override
	public void init() {
		startup();
	}

	@Override
	public void insertUsers(int rangeValue, int counter) {
		this.counter = counter;
		List<UserCassandraDTO> users = new ArrayList<UserCassandraDTO>();
		users = prepareDataSet(rangeValue, counter);

		long t1 = System.currentTimeMillis();
		// Add rows
		for (int i = 0; i < users.size(); i++) {
			UserCassandraDTO user = users.get(i);

			insertUser(user);
		}

		// long t2 = System.currentTimeMillis();
		// System.out.println("Hector Performance: insertUsers(" + users.size()
		// + ")>>>\t" + (t2 - t1));
	}

	@Override
	public void updateUser(Object userDTO) {
	}

	@Override
	public void findUserById(String userId) {
	}

	@Override
	public void findUserByUserName(String userName) {
	}

	@Override
	public void deleteUser(String userId) {
	}

	public void insertUser(UserCassandraDTO user) {
		Mutator<String> mutator = HFactory.createMutator(keyspace,
				StringSerializer.get());

		mutator.addInsertion(user.getUserId(), COLUMN_FAMILY_USER,
				HFactory.createStringColumn("user_name", user.getUserName()))
				.addInsertion(
						user.getUserId(),
						COLUMN_FAMILY_USER,
						HFactory.createStringColumn("password",
								user.getPassword()))
				.addInsertion(
						user.getUserId(),
						COLUMN_FAMILY_USER,
						HFactory.createStringColumn("relation",
								user.getRelationshipStatus()));
		MutationResult me = mutator.execute();
		// mutator.
	}

	@Override
	public void cleanup() {
		shutdown();
	}

	@Override
	public void insertUser(Object user) {
		// TODO Auto-generated method stub

	}

	private List<UserCassandraDTO> prepareDataSet(final Integer rangeValue,
			int counter) {
		List<UserCassandraDTO> users = new ArrayList<UserCassandraDTO>();
		for (int i = 0; i < rangeValue; i++) {
			int key = rangeValue == 1 ? counter : i;
			UserCassandraDTO user = new UserCassandraDTO();
			user.setUserId(getString("userId_", key));
			user.setUserName(getString("Amry_", key));
			user.setPassword(getString("password_", key));
			user.setRelationshipStatus(getString("relation_", key));
			users.add(user);
		}
		return users;
	}

	private String getString(String fieldName, int key) {
		StringBuilder strBuild = new StringBuilder(fieldName);
		strBuild.append(key);
		return strBuild.toString();
	}
}
