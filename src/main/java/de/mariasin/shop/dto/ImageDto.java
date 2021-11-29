package de.mariasin.shop.dto;

import org.primefaces.model.StreamedContent;

public class ImageDto {

	private Integer id;
	private String description;
	private StreamedContent imageSmall;
	private StreamedContent image;
	
	public ImageDto(Integer id, String description, StreamedContent imageSmall, StreamedContent image) {
		super();
		this.id = id;
		this.description = description;
		this.imageSmall = imageSmall;
		this.image = image;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public StreamedContent getImageSmall() {
		return imageSmall;
	}
	public void setImageSmall(StreamedContent imageSmall) {
		this.imageSmall = imageSmall;
	}
	public StreamedContent getImage() {
		return image;
	}
	public void setImage(StreamedContent image) {
		this.image = image;
	}
	
	
}
