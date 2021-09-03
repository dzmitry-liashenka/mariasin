package de.mariasin.shop.boundary;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.io.IOUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;
import org.junit.Before;
import org.junit.Test;

import de.mariasin.shop.entity.Image;
import de.mariasin.shop.entity.User;

public class CustomerListBeanTest {
	
	private static SessionFactory sessionFactory;
	
	private Session session;
	
	private Image image;
	
	@Before
	public void setUp() {
	      session = createSession();
	      image = new Image();
//	      image.setId(100002);
	      image.setDescription("image descriprion");
	      image.setPrice(100);
	      image.setImage("blablabla2".getBytes());
//		image.setImage(IOUtils.toByteArray(getImageAsStream()));
	}

	@Test
	public void testCreate() throws IOException {
		
		try {
			session.beginTransaction();
			session.save(image);
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail("Entity wurde nicht gespechert!");
		}
		System.out.println("Das Entity wurde in DB gespeichert.");
		
		
	}	
	
	@Test
	public void testFind() {
	
		try {
			session.beginTransaction();
			
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<Image> cr = cb.createQuery(Image.class);
			Root<Image> root = cr.from(Image.class);
			cr.select(root);
			
			Query<Image> query = session.createQuery(cr);
			List<Image> results = query.getResultList();
			
			
//TODO			Query<Image> query = session.createQuery("select _image from Image _images");
//			List list = query.list();
//			System.out.println(list.size());
//			Image find = session.get(Image.class, "100002");
//			System.out.println(find.getDescription());
			session.close();
			results.forEach( e -> {
				System.out.println(e.getDescription());
			});
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail("Entity wurde nicht gefunden!");
		}
	}

	private InputStream getImageAsStream() {
		InputStream inputStream = this.getClass()
				  .getClassLoader()
				  .getResourceAsStream("image1.png");
		return inputStream;
	}


	private static SessionFactory createSessionFactory() {
		Configuration configuration = new Configuration();
		configuration.configure();
		Properties properties = configuration.getProperties();
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
				properties).build();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		return sessionFactory;
		
	}
	
	private static Session createSession() {
		// Hibernate 5.4 SessionFactory example without XML
		Map<String, String> settings = new HashMap<>();
		settings.put("connection.driver_class", "org.postgresql.Driver");
		settings.put("dialect", "org.hibernate.dialect.PostgreSQLDialect");
		settings.put("hibernate.connection.url", 
				"jdbc:postgresql://localhost:5432/shop");
		settings.put("hibernate.connection.username", "mitry");
		settings.put("hibernate.connection.password", "111");
		settings.put("hibernate.current_session_context_class", "thread");
		settings.put("hibernate.show_sql", "true");
		settings.put("hibernate.format_sql", "true");
		
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(settings).build();

		
		  MetadataSources metadataSources = new MetadataSources(serviceRegistry);
		  metadataSources.addAnnotatedClass(Image.class);
		  metadataSources.addAnnotatedClass(User.class);
		  Metadata metadata = metadataSources.buildMetadata();
		  
		  SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
		  Session session = sessionFactory.getCurrentSession();
		  
		  return session;
		
	}
}
