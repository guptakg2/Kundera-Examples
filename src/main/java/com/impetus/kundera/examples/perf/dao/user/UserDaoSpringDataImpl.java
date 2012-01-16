package com.impetus.kundera.examples.perf.dao.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.document.mongodb.MongoOperations;

import com.impetus.kundera.examples.perf.dao.SpringMongoConfig;
import com.impetus.kundera.examples.perf.dto.UserMongoDTO;

public class UserDaoSpringDataImpl implements UserDao {

	private MongoOperations mongoOperation;

	private int counter;

	@Override
	public void init() {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(
				SpringMongoConfig.class);
		mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");

	}

	@Override
	public void insertUsers(int rangeValue, int counter) {
		this.counter = counter;
		List<UserMongoDTO> users = new ArrayList<UserMongoDTO>();
		users = prepareDataSet(rangeValue);
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
		// System.out.println("Spring Data Performance: insertUsers(" +
		// users.size()
		// + ")>>>\t" + (t2 - t1));
	}

	public void insertUser(UserMongoDTO user) {

		mongoOperation.save("USER", user);

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

	}

	@Override
	public void insertUser(Object user) {
		// TODO Auto-generated method stub

	}

	private List<UserMongoDTO> prepareDataSet(final Integer rangeValue) {
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
