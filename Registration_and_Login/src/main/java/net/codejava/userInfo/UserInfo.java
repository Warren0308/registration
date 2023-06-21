package net.codejava.userInfo;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import net.codejava.user.User;
import net.codejava.userAddress.UserAddress;

@Entity
@Table(name="users_Info")
public class UserInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long ID;
	
	@OneToOne
    @JoinColumn(name = "user_id")
	private User username;
	
	@Column(nullable = false)
	private String gender;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(nullable = false,name = "birth_of_date")
	private LocalDate birth;
	
	@OneToOne(cascade =  CascadeType.ALL, mappedBy = "userInfo")
    private UserAddress address;
	
	@Column(unique = true,length = 100)
	private String url;
	
	public Long getID() {
		return ID;
	}

	public void setID(Long iD) {
		ID = iD;
	}

	public User getUsername() {
		return username;
	}

	public void setUsername(User username) {
		this.username = username;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public LocalDate getBirth() {
		return birth;
	}

	public void setBirth(LocalDate localDate) {
		this.birth = localDate;
	}

	public UserAddress getAddress() {
		return address;
	}

	public void setAddress(UserAddress address) {
		this.address = address;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
