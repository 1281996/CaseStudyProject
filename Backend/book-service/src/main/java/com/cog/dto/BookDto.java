package com.cog.dto;

import java.io.Serializable;

public class BookDto implements Serializable {

	private static final long serialVersionUID = 4405087152380643868L;

    private String publisher;
	
	private String title;
	
	private String category;
	
	private String image;
	
	private Double price;
	
	private String content;

	public BookDto() {
		
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "BookDto [publisher=" + publisher + ", title=" + title + ", category=" + category + ", image=" + image
				+ ", price=" + price + ", content=" + content + "]";
	}
	
	
}
