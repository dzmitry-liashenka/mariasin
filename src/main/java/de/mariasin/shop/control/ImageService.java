package de.mariasin.shop.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import de.mariasin.shop.entity.Image;

@Named
@ApplicationScoped
public class ImageService implements Serializable {

	private static final long serialVersionUID = 3878414898549823840L;
	
	private List<Image> images;
	
	@PostConstruct
    public void init() {
		images = new ArrayList<>();
		images.add(new Image(1, "Water lace, 41х31 cm, 2020", "gallery/Water_lace.jpg"));
		images.add(new Image(2, "Flight, 41x31cm, 2021. Unavailable.", "gallery/Flight.jpg"));
		images.add(new Image(3, "Stormy Baltic Sea, 31x41 cm, 2021", "gallery/Stormy_Baltic_Sea.jpg"));
		images.add(new Image(4, "Dreaming, 31x41, 2021", "gallery/Dreaming.jpg"));
		images.add(new Image(5, "Ship, 41x31 cm, 2021", "gallery/Ship.jpg"));
		images.add(new Image(6, "Summer day, 41x31 cm, 2021", "gallery/Summer_day.jpg"));
		images.add(new Image(7, "Ocean, 31x41 cm, 2021", "gallery/Ocean.jpg"));
		images.add(new Image(8, "Bremen, Marktplatz, 41x31 cm, 2020. Unavailable.", "gallery/Bremen_Marktplatz.jpg"));
		images.add(new Image(9, "Waves, 31x41 cm, 2021", "gallery/Waves.jpg"));
		images.add(new Image(10, "Foam, 31x41, 2021", "gallery/Foam.jpg"));
		images.add(new Image(11, "Waterfall, 41x31 cm, 2021", "gallery/Waterfall.jpg"));
		images.add(new Image(12, "Bremen. Schnoor, 31x41 cm, 2021. Unavailable.", "gallery/Bremen_Schnoor.jpg"));
		images.add(new Image(13, "Albertbruecke, Dresden, 41x31 cm, 2021", "gallery/Albertbruecke_Dresden.jpg"));
		images.add(new Image(14, "Water magic, 56x38 cm, 2021", "gallery/Water_magic.jpg"));
		images.add(new Image(15, "Once in Porto, 38x56 cm, 2021", "gallery/Once_in_Porto.jpg"));
		
	}
	
    public List<Image> getImages() {
        return new ArrayList<>(images);
    }
    
    

}
