/**
 * 
 */
package com.impetus.kundera.examples.perf.executor;

import java.util.ArrayList;
import java.util.List;

import com.impetus.kundera.examples.perf.dao.user.UserDao;
import com.impetus.kundera.examples.perf.dao.user.UserDaoFactory;
import com.impetus.kundera.examples.perf.dto.UserMongoDTO;

/**
 * @author impadmin
 * 
 */
public class KunderaPerformanceRunner {
	private UserDao userDao;
	private int counter = 0;;
	private List<UserMongoDTO> users = new ArrayList<UserMongoDTO>();

	private void init(String client) {
		if (userDao == null) {
			userDao = UserDaoFactory.getUserDao(client);
		}
		userDao.init();

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		KunderaPerformanceRunner runner = new KunderaPerformanceRunner();
		if (args[2].equalsIgnoreCase("c")) {
			runner.run(Integer.parseInt(args[0]), args[1], false,
					Integer.parseInt(args[3]));
		} else if (args[2].equalsIgnoreCase("cb")) {
			runner.bulkLoadOnConcurrentThreads(Integer.parseInt(args[0]),
					args[1], Integer.parseInt(args[3]));

		} else if (args[2].equalsIgnoreCase("b")) {
			runner.onBulkLoad(Integer.parseInt(args[0]), args[1]);
		}
		// runner.run(Integer.parseInt(args[0]),args[1]);
		// runner.close();
	}

	public void run(final int num, String client, final boolean isBulkLoad,
			int noOfRecs) {
		try {

			init(client);
			System.out.println("<<<<<<On Max Concurrent users Insert>>>>>>");
			long t1 = System.currentTimeMillis();
			if (!isBulkLoad) {
				onConcurrentTest(noOfRecs);
			} else {
				onConcurrentBulkLoad(num, noOfRecs);
			}

			// TODO add Task executor to control threads and close data
			long t2 = System.currentTimeMillis();
			System.out.println("Kundera Performance: MaxinsertUsers(" + num
					+ "), total number of records/thread(" + noOfRecs
					+ ")>>>\t" + (t2 - t1));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private void onConcurrentBulkLoad(final int num, final int noOfRecs) {
		// ExecutorService executor = Executors.newFixedThreadPool(num);
		// List<Future> tasks = new ArrayList<Future>();
		// Future task = null;
		for (int i = 1; i <= num; i++) {
			ConThreadExecutor c = new ConThreadExecutor(userDao, noOfRecs, i);
			try {
				c.t.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// try
		// {
		// Thread.sleep(45000);
		// executor.shutdown();
		// executor.awaitTermination(500, TimeUnit.MILLISECONDS);
		// }
		/*
		 * catch (InterruptedException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

		/*
		 * for(int i=0 ; i<num ; i++) { new Thread(new Runnable() {
		 * 
		 * @Override public void run() {
		 * userDao.insertUsers(prepareDataSet(noOfRecs)); // user=null; }
		 * }).start();
		 * 
		 * }
		 */
	}

	private void onConcurrentTest(int num) {

		for (int i = 0; i < num; i++) {
			ConThreadExecutor c = new ConThreadExecutor(userDao, 1, i);
			try {
				c.t.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		/*
		 * ExecutorService executor = Executors.newFixedThreadPool(num); for(int
		 * i =1 ; i<=num ; i++) { executor.execute(new
		 * ConThreadExecutor(userDao, 1, i)); }
		 * 
		 * try { Thread.sleep(500); executor.shutdown();
		 * executor.awaitTermination(500, TimeUnit.MILLISECONDS); } catch
		 * (InterruptedException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
		// ConThreadExecutor t = new ConThreadExecutor(userDao, 1);
		/*
		 * for (final UserDTO user : prepareDataSet(num)) { new Thread(new
		 * Runnable() {
		 * 
		 * @Override public void run() { // init(client);
		 * 
		 * userDao.insertUser(user); // user=null; } }).start(); }
		 */
	}

	public void onBulkLoad(int rangeValue, String client) {
		init(client);
		System.out.println("<<<<<<On Max Single Insert>>>>>>");
		long t1 = System.currentTimeMillis();
		// userDao.insertUsers(prepareDataSet(rangeValue));
		userDao.insertUsers(rangeValue, counter);
		long t2 = System.currentTimeMillis();
		System.out.println("Kundera Performance: MaxinsertUsers(" + 1
				+ "), total number of records(" + rangeValue + ")>>>\t"
				+ (t2 - t1));
		close();
	}

	public void bulkLoadOnConcurrentThreads(int rangeValue, String client,
			int noOfRecs) {
		run(rangeValue, client, true, noOfRecs);
	}

	public void close() {
		userDao.cleanup();
	}

	/**
	 * On user persist
	 * 
	 * @param rangeValue
	 *            range value.
	 */

	private List<UserMongoDTO> prepareDataSet(final Integer rangeValue) {
		for (int i = 0; i <= rangeValue; i++) {
			UserMongoDTO user = new UserMongoDTO();
			user.setUserId(getString("userId_", i));
			user.setUserName(getString("Amry_", i));
			user.setPassword(getString("password_", i));
			user.setRelationshipStatus(getString("relation_", i));
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
