package com.impetus.kundera.examples.perf.dao.user;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.impetus.kundera.examples.perf.dto.UserHbaseDTO;

public class UserDaoDataNucleusImpl // implements ValueGenerator
{
	// private String name;
	// private Properties properties;
	// private String key;
	// private HBaseAdmin admin;
	// private HTable table;
	// private long current = -1;

	//
	// public UserDaoDataNucleusImpl(String name, Properties props) {
	// this.name = name;
	// this.properties = props;
	// this.key = name;
	// }
	//
	// public String getName() {
	// return this.name;
	// }
	//
	// public Object next() {
	// return nextValue();
	// }
	//
	// public void allocate(int additional) {
	// if (this.table == null) {
	// this.init();
	// }
	// }
	//
	// public Object current() {
	// if (this.table == null) {
	// this.init();
	// }
	// return this.current();
	// }
	//
	// public long nextValue() {
	// if (this.table == null) {
	// this.init();
	// }
	//
	// try {
	// this.current = table.incrementColumnValue(Bytes.toBytes(key),
	// Bytes.toBytes("sequence"), Bytes.toBytes("seq"), 1);
	// } catch (IOException ex) {
	// throw new NucleusDataStoreException("Error incrementing sequence.",
	// ex);
	// }
	//
	// return this.current;
	// }
	//
	// public long currentValue() {
	// if (this.table == null) {
	// this.init();
	// }
	//
	// if (this.current < 0) {
	// throw new NucleusDataStoreException();
	// }
	//
	// return this.current;
	// }
	//
	// private synchronized void init() {
	// if (this.table == null) {
	// try {
	// // Configuration config = HBaseConfiguration.
	// // this.admin = new HBaseAdmin((HBaseConfiguration) config);
	//
	// if (!admin.tableExists("mySequenceTable")) {
	// HTableDescriptor ht = new HTableDescriptor(
	// "mySequenceTable");
	// HColumnDescriptor hcd = new HColumnDescriptor("sequence");
	// hcd.setCompressionType(Algorithm.NONE);
	// hcd.setMaxVersions(1);
	// ht.addFamily(hcd);
	// admin.createTable(ht);
	// }
	//
	// // this.table = new HTable((HBaseConfiguration) config,
	// // "mySequenceTable");
	// if (!this.table.exists(new Get(Bytes.toBytes(key)))) {
	// this.table.put(new Put(Bytes.toBytes(key)).add(
	// Bytes.toBytes("sequence"), Bytes.toBytes("seq"),
	// Bytes.toBytes(0l)));
	// }
	// } catch (IOException ex) {
	// NucleusLogger.CONNECTION.fatal(
	// "Error instantiating IncrementGenerator", ex);
	// }
	// }
	// // }
	// }

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("perfcassandra");
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		UserHbaseDTO userHbaseDTO = new UserHbaseDTO();
		userHbaseDTO.setUserId("1");
		userHbaseDTO.setUserName("kuldeep");
		userHbaseDTO.setRelationshipStatus("single");
		userHbaseDTO.setPassword("12345");

		entityManager.persist(userHbaseDTO);
		entityTransaction.commit();

		if (entityTransaction.isActive()) {
			entityTransaction.rollback();
		}

		entityManager.close();

	}
}
