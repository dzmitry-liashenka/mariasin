package de.mariasin.shop.boundary;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import de.mariasin.shop.entity.User;

@Named
@ViewScoped
public class CustomerListBean implements Serializable {

	private static final long serialVersionUID = 4773746274170179581L;

	private List<User> users;


//	@Inject
//	private ImageService imageService;

	@PostConstruct
	public void init() {
		
	}

}
