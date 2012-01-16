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

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ConsistencyLevel;
import org.scale7.cassandra.pelops.Mutator;
import org.scale7.cassandra.pelops.Pelops;
import org.scale7.cassandra.pelops.exceptions.PelopsException;

import com.impetus.kundera.examples.perf.dao.PelopsBaseDao;
import com.impetus.kundera.examples.perf.dto.UserCassandraDTO;

/**
 * @author amresh.singh
 * 
 */
public class UserDaoPelopsImpl extends PelopsBaseDao implements UserDao {

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
		// long t1 = System.currentTimeMillis();

		for (UserCassandraDTO user : users) {
			insertUser(user);
		}

		// long t2 = System.currentTimeMillis();
		// System.out.println("Pelops Performance: insertUsers(" + users.size()
		// + ")>>>\t" + (t2 - t1));
	}

	public void insertUser(UserCassandraDTO user) {
		try {
			Mutator mutator = Pelops.createMutator(getPoolName());
			List<Column> columns = new ArrayList<Column>();

			long currentTime = System.currentTimeMillis();

			Column nameColumn = new Column();
			nameColumn.setName("user_name".getBytes("utf-8"));
			nameColumn.setValue(user.getUserName().getBytes("utf-8"));
			nameColumn.setTimestamp(currentTime);
			columns.add(nameColumn);

			Column passwordColumn = new Column();
			passwordColumn.setName("password".getBytes("utf-8"));
			passwordColumn.setValue(user.getPassword().getBytes("utf-8"));
			passwordColumn.setTimestamp(currentTime);
			columns.add(passwordColumn);

			Column relationColumn = new Column();
			relationColumn.setName("relation".getBytes("utf-8"));
			relationColumn.setValue(user.getRelationshipStatus().getBytes(
					"utf-8"));
			relationColumn.setTimestamp(currentTime);
			columns.add(relationColumn);

			mutator.writeColumns(COLUMN_FAMILY, user.getUserId(), columns);
			mutator.execute(ConsistencyLevel.ONE);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (PelopsException e) {
			e.printStackTrace();
		}
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
