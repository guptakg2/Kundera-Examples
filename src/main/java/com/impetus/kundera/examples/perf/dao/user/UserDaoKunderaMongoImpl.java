package com.impetus.kundera.examples.perf.dao.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.impetus.kundera.examples.perf.dto.UserMongoDTO;

public class UserDaoKunderaMongoImpl implements UserDao {

	private int counter;
	private EntityManagerFactory emf;
	private static final String PERSISTENCE_UNIT = "perfmongo";
	 

	// private static final String HOST = "localhost";
	// private static final String PORT = "27017";
	// private static final String KEYSPACE = "KunderaKeyspace";
	// private static final String COLUMN_FAMILY = "User";

	@Override
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

	@Override
	public void insertUsers(int rangeValue, int counter) {
		this.counter = counter;
		List<UserMongoDTO> users = new ArrayList<UserMongoDTO>();
		users = prepareDataSet(rangeValue,counter);
		// System.out.println("Inserting users");
		// long t1 = System.currentTimeMillis();

		// for (UserDTO user : users) {
		// insertUser(user);
		// }

		for (int i = 0; i < users.size(); i++) {
			UserMongoDTO user = users.get(i);
			insertUser(user);
		}
		// long t2 = System.currentTimeMillis();
		// System.out.println("Kundera Performance: insertUsers(" + users.size()
		// + ")>>>\t" + (t2 - t1));

	}

	public void insertUser(UserMongoDTO user) {
		EntityManager em = emf.createEntityManager();
		em.persist(user);
		em.close();
		em = null;
	}

	@Override
	public void updateUser(Object user) {
		EntityManager em = emf.createEntityManager();
		em.persist(user);
		em.close();

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
		if (emf != null) {
			System.out.println("<<<<<<< Shututdown called>>>>>");
			emf.close();
		}

	}

	@Override
	public void insertUser(Object user) {
		// TODO Auto-generated method stub

	}

	private List<UserMongoDTO> prepareDataSet(final Integer rangeValue,int counter) {
		List<UserMongoDTO> users = new ArrayList<UserMongoDTO>();
		for (int i = 0; i < rangeValue; i++) {
			int key = rangeValue == 1 ? counter : i;
			UserMongoDTO user = new UserMongoDTO();
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
