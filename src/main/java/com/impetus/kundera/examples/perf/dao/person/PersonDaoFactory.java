package com.impetus.kundera.examples.perf.dao.person;

public class PersonDaoFactory {
	public static PersonDao getUserDao(String provider) {
		if (provider.equalsIgnoreCase("kunderaMongo")) {
			return new PersonDaoKunderaMongoImpl();
		} else if (provider.equalsIgnoreCase("nativeMongoAPI")) {
			return new PersonDaoNativeMongoAPIImpl();
		} else if (provider.equalsIgnoreCase("springData")) {
			return new PersonDaoSpringDataImpl();
		} else if (provider.equalsIgnoreCase("morphia")) {
			return new PersonDaoMorphiaImpl();
		} else if (provider.equalsIgnoreCase("kunderaCassandra")) {
			return new PersonDaoKunderaCassandraImpl();
		} else if (provider.equalsIgnoreCase("pelops")) {
			return new PersonDaoPelopsImpl();
		} else if (provider.equalsIgnoreCase("hector")) {
			return new PersonDaoHectorImpl();
		} else {
			return null;
		}
	}
}
