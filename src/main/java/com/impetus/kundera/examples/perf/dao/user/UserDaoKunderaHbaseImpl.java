package com.impetus.kundera.examples.perf.dao.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.impetus.kundera.examples.perf.dto.UserHbaseDTO;


public class UserDaoKunderaHbaseImpl implements UserDao {

	private EntityManagerFactory emf;
	private static final String PERSISTENCE_UNIT = "perfhbase";

	// private static final String HOST = "localhost";
	// private static final String PORT = "27017";
	// private static final String KEYSPACE = "KunderaKeyspace";
	// private static final String COLUMN_FAMILY = "User";

	public void init() {
		if (emf == null) {
			try {
				emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("EMF Created successfully");
	}

	public void insertUsers(int rangeValue, int counter) {
		List<UserHbaseDTO> users = new ArrayList<UserHbaseDTO>();
		users = prepareDataSet(rangeValue, counter);
		// System.out.println("Inserting users");
		// long t1 = System.currentTimeMillis();

		// for (UserDTO user : users) {
		// insertUser(user);
		// }

		for (int i = 0; i < users.size(); i++) {
			UserHbaseDTO user = users.get(i);
			insertUser(user);
		}
		// long t2 = System.currentTimeMillis();
		// System.out.println("Kundera Performance: insertUsers(" + users.size()
		// + ")>>>\t" + (t2 - t1));

	}

	public void insertUser(UserHbaseDTO user) {
		EntityManager em = emf.createEntityManager();
		em.persist(user);
		em.close();
		em = null;
	}

	public void cleanup() {
		if (emf != null) {
			System.out.println("<<<<<<< Shututdown called>>>>>");
			emf.close();
		}

	}

	private static List<UserHbaseDTO> prepareDataSet(final Integer rangeValue,
			int counter) {
		List<UserHbaseDTO> users = new ArrayList<UserHbaseDTO>();
		for (int i = 0; i < rangeValue; i++) {
			int key = rangeValue == 1 ? counter : i;

			UserHbaseDTO user = new UserHbaseDTO();
			user.setUserId(getString("userId_", key));
			user.setUserName(getString("Amry_", key));
			user.setPassword(getString("password_", key));
			user.setRelationshipStatus(getString("relation_", key));
			users.add(user);
		}
		return users;
	}

	private static String getString(String fieldName, int key) {
		StringBuilder strBuild = new StringBuilder(fieldName);
		strBuild.append(key);
		return strBuild.toString();
	}

	@Override
	public void updateUser(Object userDTO) {

	}

	@Override
	public void insertUser(Object user) {

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
}
