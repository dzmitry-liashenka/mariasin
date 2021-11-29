package de.mariasin.shop.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import de.mariasin.shop.entity.Image;
import de.mariasin.shop.entity.User;

public class HibernateUtil {
	
	private static Session session;
 
	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {

			try {
				Configuration configuration = new Configuration();

				// Hibernate settings equivalent to hibernate.cfg.xml's properties
				Properties settings = new Properties();
				settings.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
				settings.put(Environment.URL, "jdbc:mysql://localhost:3306/demo?useSSL=false");
				settings.put(Environment.USER, "root");
				settings.put(Environment.PASS, "root");
				settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");

				settings.put(Environment.SHOW_SQL, "true");

				settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

				settings.put(Environment.HBM2DDL_AUTO, "create-drop");

				configuration.setProperties(settings);
				configuration.addAnnotatedClass(User.class);
				configuration.addAnnotatedClass(Image.class);

				ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
						.applySettings(configuration.getProperties()).build();
				System.out.println("Hibernate Java Config serviceRegistry created");
				sessionFactory = configuration.buildSessionFactory(serviceRegistry);
				return sessionFactory;

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return sessionFactory;
	}
	
	public static Session createSession() {
		// Hibernate 5.4 SessionFactory example without XML
		Map<String, String> settings = new HashMap<>();
		settings.put("connection.driver_class", "org.postgresql.Driver");
		settings.put("dialect", "org.hibernate.dialect.PostgreSQLDialect");
		settings.put("hibernate.connection.url", "jdbc:postgresql://localhost:5432/shop");
		settings.put("hibernate.connection.username", "mitry");
		settings.put("hibernate.connection.password", "111");
		settings.put("hibernate.current_session_context_class", "thread");
		settings.put("hibernate.show_sql", "true");
		settings.put("hibernate.format_sql", "true");

		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(settings).build();

		MetadataSources metadataSources = new MetadataSources(serviceRegistry);
		metadataSources.addAnnotatedClass(Image.class);
		metadataSources.addAnnotatedClass(User.class);
		Metadata metadata = metadataSources.buildMetadata();

		SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
		session = sessionFactory.getCurrentSession();

		return session;
	}
}