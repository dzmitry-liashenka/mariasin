package de.mariasin.shop.boundary;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.inject.Inject;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.apache.commons.io.IOUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import de.mariasin.shop.control.SaveData;
import de.mariasin.shop.entity.Image;
import de.mariasin.shop.entity.User;

public class CustomerListBeanTest {
	
	private static SessionFactory sessionFactory;
	
	private Session session;
	
	private Image image;
	
	@Inject
	SaveData saveData;
	
	@Before
	public void setUp() {
	      session = createSession();
	      image = new Image();
	      image.setDescription("image descriprion");
	      image.setPrice(100);
	      image.setImage("blablabla2".getBytes());
	      image.setImageSmall("imagesmall1".getBytes());
	      image.setPath("pathToImage");
	      image.setPathSmall("path to imageSmall");
		try {
			image.setImage(IOUtils.toByteArray(getImageAsStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		System.out.println(String.format("Das Entity %01$s wurde in DB gespeichert.", image));
		
		
	}	
	
	@Test
	public void getAll() {

		try {
			session.beginTransaction();
//			session.save(image);
//			session.getTransaction().commit();
			
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<Image> cq = cb.createQuery(Image.class);
		    Root<Image> rootEntry = cq.from(Image.class);
		    CriteriaQuery<Image> all = cq.select(rootEntry);

		    TypedQuery<Image> allQuery = session.createQuery(all);
		    List<Image> resultList = allQuery.getResultList();
			System.out.println(resultList.size());
			resultList.forEach(i -> System.out.println(i.getDescription()));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail("Keine entity wurde gefunden!");
		}
		
	}
	
	@Ignore("Transactional funktioniert nicht")
	@Transactional(rollbackOn = Image.class)
	@Test
	public void testDelete() {
		try {
			session.beginTransaction();
			
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<Image> cq = cb.createQuery(Image.class);
		    Root<Image> rootEntry = cq.from(Image.class);
		    CriteriaQuery<Image> all = cq.select(rootEntry);

		    TypedQuery<Image> allQuery = session.createQuery(all);
		    List<Image> resultList = allQuery.getResultList();
			int sizeBeforeDelete = resultList.size();
			System.out.println(sizeBeforeDelete);
			resultList.forEach(i -> System.out.println(i.getDescription() + i.getId()));
			
			Integer id = resultList.get(0).getId();

			session.close();
			
			if (null == saveData) {
				saveData = new SaveData();
			}
			
			Image delete = saveData.delete(String.valueOf(resultList.get(0).getId()));
			
			assertTrue(delete.getId().equals(id));
			
			session = createSession();
			session.beginTransaction();
			cb = session.getCriteriaBuilder();
			cq = cb.createQuery(Image.class);
		    rootEntry = cq.from(Image.class);
		    all = cq.select(rootEntry);

		    allQuery = session.createQuery(all);
		    resultList = allQuery.getResultList();
		    int sizeAfterDelete = resultList.size();
			System.out.println(sizeAfterDelete);
			resultList.forEach(i -> System.out.println(i.getDescription() + i.getId()));
			
			assertTrue(sizeAfterDelete < sizeBeforeDelete);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail("Keine entity wurde gefunden!");
		}
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
		File file = new File("/home/mitry/git/mariasin/src/test/resources/de/mariasin/shop/boundary/image1.png");
		InputStream stream = null;
		try {
			stream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return stream;
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
