package net.codejava.userAddress;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import net.codejava.userInfo.UserInfo;

@Entity
@Table(name="user_address")
public class UserAddress {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long ID;
	
	@OneToOne
    @JoinColumn(name = "user_id")
	private UserInfo userInfo;
	
	@Column(name="Street",nullable = false)
	private String street;
	
	@Column(name="Apartment",nullable = false)
	private String apartment;
	
	@Column(name="Postcode",nullable = false)
	private int postcode;
	
	@Column(name="City",nullable = false)
	private String city;
	
	@Column(name="State",nullable = false)
	private String state;

	public Long getID() {
		return ID;
	}

	public void setID(Long iD) {
		ID = iD;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getApartment() {
		return apartment;
	}

	public void setApartment(String apartment) {
		this.apartment = apartment;
	}

	public int getPostcode() {
		return postcode;
	}

	public void setPostcode(int postcode) {
		this.postcode = postcode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
}
