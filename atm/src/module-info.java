// TODO: fix this. Hibernate requires open module. Not a good approach?
open module atm {
	requires org.hibernate.orm.core;
	requires java.sql;
	requires java.persistence;
}