package com.example.entity;


import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Email;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PhoneNumber;

/**
 * Class representing a single user.
 * @author Nik
 *
 */
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	private String userEmailID;
	private String userName;
	private Boolean isPublisher;
	private double userLong;
	private double userLat;
	private String userGeoHash;
	
/*	public User() {
		phoneNumber = null;
		userName = "Anonymous";
		isPublisher = false;
		userLat = 0.0;
		userLong = 0.0;
	}*/
	
	/**
	 * Key is read only.
	 * @return Key value
	 */
	public Key getKey() {
		return key;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Boolean getIsPublisher() {
		return isPublisher;
	}

	public void setIsPublisher(Boolean isPublisher) {
		this.isPublisher = isPublisher;
	}
	public double getUserLong() {
		return userLong;
	}

	public void setUserLong(double userLong) {
		this.userLong = userLong;
	}

	public double getUsertLat() {
		return userLat;
	}

	public void setUserLat(double userLat) {
		this.userLat = userLat;
	}
	/**
	 * @return the userGeoHash
	 */
	public String getUserGeoHash() {
		return userGeoHash;
	}
	/**
	 * @param userGeoHash the userGeoHash to set
	 */
	public void setUserGeoHash(String userGeoHash) {
		this.userGeoHash = userGeoHash;
	}
	/**
	 * @return the userEmailID
	 */
	public String getUserEmailID() {
		return userEmailID;
	}
	/**
	 * @param userEmailID the userEmailID to set
	 */
	public void setUserEmailID(String userEmailID) {
		this.userEmailID = userEmailID;
	}
	
}


