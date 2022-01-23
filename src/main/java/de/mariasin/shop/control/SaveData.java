package de.mariasin.shop.control;

import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.primefaces.model.file.UploadedFile;

import de.mariasin.shop.entity.Image;
import de.mariasin.shop.util.HibernateUtil;

@Named
@ApplicationScoped
public class SaveData {
	
	Logger logger = Logger.getLogger(this.getClass());

	private Image uploadedImage;
	
	private byte[] image;
	
	private byte[] imageSmall;
	
	private String description;
	
	private Session session;

	
	public Image getUploadedImage() {
		return uploadedImage;
	}

	public void setUploadedImage(Image uploadedImage) {
		this.uploadedImage = uploadedImage;
	}
	
	public void setImage(UploadedFile image) {
		try {
			this.image = IOUtils.toByteArray(image.getInputStream());
		} catch (IOException e) {
			logger.error(e);
		}
	}

	public void setImageSmall(UploadedFile imageSmall) {
		try {
			this.imageSmall = IOUtils.toByteArray(imageSmall.getInputStream());
		} catch (IOException e) {
			logger.error(e);
		}
	}

	public void save() {
		this.uploadedImage = new Image();
		this.uploadedImage.setDescription(description);
		this.uploadedImage.setPath("testPath");
		this.uploadedImage.setPathSmall("testPathSmall");
		this.uploadedImage.setImage(image);
		this.uploadedImage.setImageSmall(imageSmall);
		this.uploadedImage.setPrice(102); //TODO Price löschen?
		logger.info(String.format("Save Image %01$s", uploadedImage));

		try {
			logger.info(session);
			this.session = HibernateUtil.createSession();
			logger.info(session);
			this.session.beginTransaction();
			this.session.save(this.uploadedImage);
			this.session.getTransaction().commit();
			this.session.close();
			logger.info(String.format("Das Entity %01$s wurde in DB gespeichert.", uploadedImage));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}
	
	public Image delete(String id) {
		logger.info(String.format("Delete Image with ID = %01$s", id));
		
		Image delete = null;
		try {
			this.session = HibernateUtil.createSession();
			logger.info("Session open: " + session.isOpen());
			this.session.beginTransaction();
			delete = this.session.load(Image.class, Integer.valueOf(id));
			if (null != delete) {
				this.session.delete(delete);
			}
			this.session.getTransaction().commit();
			this.session.close();
			logger.info(String.format("Das Entity %01$s wurde in DB gespeichert.", uploadedImage));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return delete;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
