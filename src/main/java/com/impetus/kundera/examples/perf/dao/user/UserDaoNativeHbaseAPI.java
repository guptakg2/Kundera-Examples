package com.impetus.kundera.examples.perf.dao.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import com.impetus.kundera.examples.perf.dto.UserHbaseDTO;

public class UserDaoNativeHbaseAPI implements UserDao {

	@Override
	public void init() {

	}

	@Override
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

	@Override
	public void updateUser(Object userDTO) {

	}

	@Override
	public void insertUser(Object user) {
		HTable htable = null;
		UserHbaseDTO User = (UserHbaseDTO) user;
		try {
			htable = new HTable("USER");
			Put p = new Put(Bytes.toBytes(User.getUserId()));
			p.add(Bytes.toBytes("userName"), Bytes.toBytes("userName"),
					Bytes.toBytes(User.getUserName()));
			p.add(Bytes.toBytes("password"), Bytes.toBytes("password"),
					Bytes.toBytes(User.getPassword()));
			p.add(Bytes.toBytes("relation"), Bytes.toBytes("relation"),
					Bytes.toBytes(User.getRelationshipStatus()));
			htable.put(p);
		} catch (IOException e) {

			e.printStackTrace();
		}

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
}
