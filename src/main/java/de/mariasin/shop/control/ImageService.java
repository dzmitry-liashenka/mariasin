package de.mariasin.shop.control;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.primefaces.model.DefaultStreamedContent;

import de.mariasin.shop.dto.ImageDto;
import de.mariasin.shop.entity.Image;
import de.mariasin.shop.util.HibernateUtil;

@Named
@ApplicationScoped
public class ImageService implements Serializable {

	private static final long serialVersionUID = 3878414898549823840L;
	
	Logger logger = Logger.getLogger(this.getClass());
	
	private List<Image> images;
	
	private Session session;
	
	//String description, byte[] image, String path, byte[] imageSmall, String pathSmall
	
	@PostConstruct
    public void init() {
		images = new ArrayList<>();
		images.add(new Image(1, "Baltic sea 31x41cm 2021", "gallery/Baltic_sea.jpg", "gallery/small/Baltic_sea.jpg"));
		images.add(new Image(2, "Sunset 31x41cm 2021", "gallery/Sunset.jpg", "gallery/small/Sunset.jpg"));
		images.add(new Image(3, "Stormy 31x41cm 2021", "gallery/Stormy.jpg", "gallery/small/Stormy.jpg"));
		images.add(new Image(4, "Madeira 31x41cm 2021", "gallery/Madeira.jpg", "gallery/small/Madeira.jpg"));
		images.add(new Image(5, "Water lace 41x31cm 2020", "gallery/Water_lace.jpg", "gallery/small/Water_lace.jpg"));
		images.add(new Image(6, "Ship 41x31cm, 2021", "gallery/Ship.jpg", "gallery/small/Ship.jpg"));
		images.add(new Image(7, "Once in Porto 38x56cm 2021", "gallery/Once_in_Porto.jpg", "gallery/small/Once_in_Porto.jpg"));
		images.add(new Image(8, "Stone 56x38cm 2021", "gallery/Stone.jpg", "gallery/small/Stone.jpg"));
		images.add(new Image(9, "Foam 31x41cm 2021", "gallery/Foam.jpg", "gallery/small/Foam.jpg"));
		images.add(new Image(10, "Once upon a time 56x76cm 2021", "gallery/Once_upon_a_time.jpg", "gallery/small/Once_upon_a_time.jpg"));
		images.add(new Image(11, "Bremen 41x31cm 2020", "gallery/Bremen.jpg", "gallery/small/Bremen.jpg"));
		images.add(new Image(12, "Paris 31x41cm 2021", "gallery/Paris.jpg", "gallery/small/Paris.jpg"));
		images.add(new Image(13, "Dresden 41x31cm 2021", "gallery/Dresden.jpg", "gallery/small/Dresden.jpg"));
		
	}
	
    public List<Image> getImages() {
        return this.images;
    }

    /**
     * Liefert die Liste mit exitierte in der Datenbank {@linkplain Image}.
     * @return Liste mit {@linkplain Image}
     */
    public List<Image> getBilder() {
    	this.session = HibernateUtil.createSession();
    	this.session.beginTransaction();
		
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Image> cq = cb.createQuery(Image.class);
	    Root<Image> rootEntry = cq.from(Image.class);
	    CriteriaQuery<Image> all = cq.select(rootEntry);

	    TypedQuery<Image> allQuery = session.createQuery(all);
	    List<Image> resultList = allQuery.getResultList();
//	    this.session.cancelQuery();
	    this.session.close();
	    
        return resultList;
 
    }
    
    public List<ImageDto> getGalleryImages(){
    	
    	List<ImageDto> galleryImages = new ArrayList<>();
    	List<Image> bilder = getBilder();
    	
    	for (Image image : bilder) {
    		
    		
    		byte[] imageSmall = image.getImageSmall();
    		DefaultStreamedContent smallContent = DefaultStreamedContent.builder()
		    		.contentType("image/jpeg")
		    		.stream(() -> new ByteArrayInputStream(imageSmall))
		    		.build();
    		
    		byte[] bytesOfImage = image.getImage();
    		DefaultStreamedContent content = DefaultStreamedContent.builder()
    				.contentType("image/jpeg")
    				.stream(() -> new ByteArrayInputStream(bytesOfImage))
    				.build();
    		
    		ImageDto imageDto = new ImageDto(image.getId(), image.getDescription(), smallContent, content);
    	
    		galleryImages.add(imageDto);
		}
    	
    	logger.info(String.format("GalleryImages mit size %01$s", galleryImages.size()));
    	
    	return galleryImages;
    }
    


}
