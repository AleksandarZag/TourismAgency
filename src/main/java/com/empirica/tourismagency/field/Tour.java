package com.empirica.tourismagency.field;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Tour{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String title;
	private String date;
	private String category;
	private String category2;
	private String category3;
	private String category4;
	private double price;
	private boolean active=true;

	@Column(columnDefinition="text")
	private String description;
	private int quantity;

	@Transient
	private MultipartFile tourImage;
	@Transient
	private MultipartFile tourImage2;
	@Transient
	private MultipartFile tourImage3;
	@Transient
	private MultipartFile tourImage4;
	@Transient
	private MultipartFile tourImage5;


	@OneToMany(mappedBy = "tour")
	@JsonIgnore
	private List<TourToReservationItem> tourToReservationItemList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCategory2() {
		return category2;
	}

	public void setCategory2(String category2) {
		this.category2 = category2;
	}

	public String getCategory3() {
		return category3;
	}

	public void setCategory3(String category3) {
		this.category3 = category3;
	}

	public String getCategory4() {
		return category4;
	}

	public void setCategory4(String category4) {
		this.category4 = category4;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public MultipartFile getTourImage() {
		return tourImage;
	}

	public void setTourImage(MultipartFile tourImage) {
		this.tourImage = tourImage;
	}

	public MultipartFile getTourImage2() {
		return tourImage2;
	}

	public void setTourImage2(MultipartFile tourImage2) {
		this.tourImage2 = tourImage2;
	}

	public MultipartFile getTourImage3() {
		return tourImage3;
	}

	public void setTourImage3(MultipartFile tourImage3) {
		this.tourImage3 = tourImage3;
	}

	public MultipartFile getTourImage4() {
		return tourImage4;
	}

	public void setTourImage4(MultipartFile tourImage4) {
		this.tourImage4 = tourImage4;
	}

	public MultipartFile getTourImage5() {
		return tourImage5;
	}

	public void setTourImage5(MultipartFile tourImage5) {
		this.tourImage5 = tourImage5;
	}

	public List<TourToReservationItem> getTourToReservationItemList() {
		return tourToReservationItemList;
	}

	public void setTourToReservationItemList(List<TourToReservationItem> tourToReservationItemList) {
		this.tourToReservationItemList = tourToReservationItemList;
	}
}