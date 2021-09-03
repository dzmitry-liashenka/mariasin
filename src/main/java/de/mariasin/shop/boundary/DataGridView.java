package de.mariasin.shop.boundary;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import de.mariasin.shop.control.ImageService;
import de.mariasin.shop.entity.Image;

@Named
@ViewScoped
public class DataGridView implements Serializable {

	private static final long serialVersionUID = 6764466183521817274L;

	private List<Image> images;
    private Image selectedImage;
    
    @Inject
    private ImageService imageService;
	
    @PostConstruct
    public void init() {
    	images = imageService.getImages();
    }
    
    public List<Image> getImages() {
		return images;
    }
    
    public void setService(ImageService service) {
        this.imageService = service;
    }

	public Image getSelectedImage() {
		return selectedImage;
	}

	public void setSelectedImage(Image selectedImage) {
		this.selectedImage = selectedImage;
	}
	
    public void clearMultiViewState() {
        FacesContext context = FacesContext.getCurrentInstance();
        String viewId = context.getViewRoot().getViewId();
        PrimeFaces.current().multiViewState().clearAll(viewId, true, this::showMessage);
    }
    
    private void showMessage(String clientId) {
        FacesContext.getCurrentInstance()
                .addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, clientId + " multiview state has been cleared out", null));
    }
}
