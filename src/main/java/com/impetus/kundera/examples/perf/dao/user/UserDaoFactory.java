package com.impetus.kundera.examples.perf.dao.user;

public class UserDaoFactory {
	public static UserDao getUserDao(String provider) {
		if (provider.equalsIgnoreCase("kunderaMongo")) {
			return new UserDaoKunderaMongoImpl();
		} else if (provider.equalsIgnoreCase("nativeMongoAPI")) {
			return new UserDaoNativeMongoAPIImpl();
		} else if (provider.equalsIgnoreCase("springData")) {
			return new UserDaoSpringDataImpl();
		} else if (provider.equalsIgnoreCase("morphia")) {
			return new UserDaoMorphiaImpl();
		} else if (provider.equalsIgnoreCase("kunderaCassandra")) {
			return new UserDaoKunderaCassandraImpl();
		} else if (provider.equalsIgnoreCase("pelops")) {
			return new UserDaoPelopsImpl();
		} else if (provider.equalsIgnoreCase("hector")) {
			return new UserDaoHectorImpl();
		} else if (provider.equalsIgnoreCase("kunderaHbase")) {
			return new UserDaoKunderaHbaseImpl();
		} else if (provider.equalsIgnoreCase("NativeHbaseAPI")) {
			return new UserDaoNativeHbaseAPI();
		} else {
			return null;
		}
	}
}
