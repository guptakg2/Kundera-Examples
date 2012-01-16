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

import javax.persistence.EntityManager;

import com.impetus.kundera.examples.perf.dao.KunderaCassandraBaseDao;
import com.impetus.kundera.examples.perf.dto.UserCassandraDTO;

/**
 * @author amresh.singh
 * 
 */
public class UserDaoKunderaCassandraImpl extends KunderaCassandraBaseDao
		implements UserDao {

	@Override
	public void init() {
		startup();
	}

	@Override
	public void insertUsers(int rangeValue, int counter) {
		List<UserCassandraDTO> users = new ArrayList<UserCassandraDTO>();
		users = prepareDataSet(rangeValue, counter);
		// System.out.println("Inserting users");
		// long t1 = System.currentTimeMillis();

		for (int i = 0; i < users.size(); i++) {
			UserCassandraDTO user = users.get(i);
			insertUser(user);
		}
		// long t2 = System.currentTimeMillis();
		// System.out.println("Kundera Performance: insertUsers(" + users.size()
		// + ")>>>\t" + (t2 - t1));
	}

	/**
	 * Inserts user table object.
	 * 
	 * @param user
	 *            user object.
	 */
	public void insertUser(UserCassandraDTO user) {
		// init();
		// System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"+emf +
		// ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		EntityManager em = emf.createEntityManager();
		em.persist(user);
		em.close();
		em = null;
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
		System.out.println("<<<<<<< Shututdown called>>>>>");
		shutdown();
	}

	@Override
	public void insertUser(Object user) {

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
