package com.impetus.kundera.examples.perf.dao.user;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.impetus.kundera.examples.perf.dto.UserMongoDTO;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class UserDaoMorphiaImpl implements UserDao {

	private EntityManagerFactory emf;
	private EntityManager em;
	private Datastore datastore;
	private int counter;

	@Override
	public void init() {

		emf = Persistence.createEntityManagerFactory("perfmongo");
		em = emf.createEntityManager();
		Mongo mongo = null;
		try {
			mongo = new Mongo();
		} catch (UnknownHostException e) {

			e.printStackTrace();
		} catch (MongoException e) {

			e.printStackTrace();
		}

		datastore = new Morphia().createDatastore(mongo, "KunderaExamples");
	}

	@Override
	public void insertUsers(int rangeValue, int counter) {
		this.counter = counter;
		List<UserMongoDTO> users = new ArrayList<UserMongoDTO>();
		users = prepareDataSet(rangeValue, counter);
		// System.out.println("Inserting users");
		// long t1 = System.currentTimeMillis();

		for (UserMongoDTO user : users) {
			insertUser(user);
		}
		// long t2 = System.currentTimeMillis();
		// System.out.println("Morphia Performance: insertUsers(" + users.size()
		// + ")>>>\t" + (t2 - t1));
	}

	public void insertUser(UserMongoDTO user) {
		datastore.save(user);
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

		em.close();
		emf.close();

	}

	@Override
	public void insertUser(Object user) {
		// TODO Auto-generated method stub

	}

	private List<UserMongoDTO> prepareDataSet(final Integer rangeValue,
			int counter) {
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
