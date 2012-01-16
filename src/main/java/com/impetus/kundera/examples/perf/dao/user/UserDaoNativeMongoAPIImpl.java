package com.impetus.kundera.examples.perf.dao.user;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.impetus.kundera.examples.perf.dto.UserMongoDTO;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class UserDaoNativeMongoAPIImpl implements UserDao {

	private Mongo m;
	private DB db;
	private DBCollection coll;
	private BasicDBObject doc;
	private BasicDBObject Adoc;
	private int counter;

	@Override
	public void init() {
		try {
			m = new Mongo();
		} catch (UnknownHostException e) {

			e.printStackTrace();
		} catch (MongoException e) {

			e.printStackTrace();
		}

		db = m.getDB("KunderaExamples");
		coll = db.getCollection("USER");
	}

	@Override
	public void insertUsers(int rangeValue, int counter) {
		this.counter = counter;
		List<UserMongoDTO> users = new ArrayList<UserMongoDTO>();
		users = prepareDataSet(rangeValue, counter);
		// long t1 = System.currentTimeMillis();

		// for (UserDTO user : users) {
		// insertUser(user);
		// }
		for (int i = 0; i < users.size(); i++) {
			UserMongoDTO user = users.get(i);
			insertUser(user);
		}
		// long t2 = System.currentTimeMillis();
		// System.out.println("nativeAPI Performance: insertUsers(" +
		// users.size()
		// + ")>>>\t" + (t2 - t1));
	}

	public void insertUser(UserMongoDTO user) {

		doc = new BasicDBObject();
		doc.put("UserId", user.getUserId());
		doc.put("UserName", user.getUserName());
		doc.put("Password", user.getPassword());
		doc.put("RelationshipStatus", user.getRelationshipStatus());
		//
		// Adoc = new BasicDBObject();
		// Adoc.put("AddressId", user.getAddress().getAddressId());
		// Adoc.put("Street", user.getAddress().getStreet());
		//
		// doc.put("Address", Adoc);
		coll.insert(doc);
	}

	@Override
	public void updateUser(Object user) {

		// doc = new BasicDBObject();
		// doc.put("UserId", user.getUserId());
		// doc.put("UserName", user.getUserName());
		// doc.put("Password", user.getPassword());
		// doc.put("RelationshipStatus", user.getRelationshipStatus());
		//
		// coll.update(new BasicDBObject("_id", user.getUserId()), doc);
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
