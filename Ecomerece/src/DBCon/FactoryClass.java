package DBCon;

public class FactoryClass {
	public StoreDAO getObject() throws Exception {
		return new DB_Properties();
	}

	public StoreDAO getObject(int pgno, int pgsize) throws Exception {
		return new DB_Properties(pgno, pgsize);
	}
}
