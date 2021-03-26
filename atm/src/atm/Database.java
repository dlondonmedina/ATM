package atm;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Database {
	private static SessionFactory factory;
	
	public Database() {
		
		try {
			factory = new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object. " + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public Bank addBank(String name) {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer bankID = null;
		Bank bank = null;
		
		try {
			tx = session.beginTransaction();
			bank = new Bank(name);
			bankID = (Integer) session.save(bank);
			tx.commit();			
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		System.out.println(bankID);
		return bank;
	}
}
