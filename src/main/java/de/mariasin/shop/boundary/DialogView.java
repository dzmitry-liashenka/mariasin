package de.mariasin.shop.boundary;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

import de.mariasin.shop.entity.Image;

@Named("dialogView")
@RequestScoped
public class DialogView {
	
    public void viewProducts() {
        Map<String,Object> options = new HashMap<>();
        options.put("resizable", false);
        PrimeFaces.current().dialog().openDynamic("viewImage", options, null);
    }

	public void viewProductsCustomized() {
		Map<String, Object> options = new HashMap<>();
		options.put("modal", true);
		options.put("width", 1200);
		options.put("height", 1000);
		options.put("contentWidth", "100%");
		options.put("contentHeight", "100%");
		options.put("headerElement", "customheader");
		
	 PrimeFaces.current().dialog().openDynamic("viewImage", options, null);

	}
	
    public void chooseProduct() {
        Map<String,Object> options = new HashMap<>();
        options.put("resizable", false);
        options.put("draggable", false);
        options.put("modal", true);
        PrimeFaces.current().dialog().openDynamic("selectProduct", options, null);
    }
    
    public void onProductChosen(SelectEvent event) {
        Image image = (Image) event.getObject();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Product Selected", "Name:" + image.getDescription());

		FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void showMessage() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Message", " Always Bet on Prime!");

        PrimeFaces.current().dialog().showMessageDynamic(message);
    }
}
