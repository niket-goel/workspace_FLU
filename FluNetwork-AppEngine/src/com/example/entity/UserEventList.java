package com.example.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;
/**
 * Class representing the relationship between user and event.
 * @author Nik
 *
 */
@Entity
public class UserEventList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	
	private String geoHash;
	private String eventKey;
	private int hopNumber;
	/**
	 * @return the userMail
	 */
	public String getUserMail() {
		return geoHash;
	}
	public Key getKey() {
		return key;
	}
	/**
	 * @param userMail the userMail to set
	 */
	public void setUserMail(String userMail) {
		this.geoHash = userMail;
	}
	/**
	 * @return the eventKey
	 */
	public String getEventKey() {
		return eventKey;
	}
	/**
	 * @param eventKey the eventKey to set
	 */
	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}
	/**
	 * @return the hopNumber
	 */
	public int getHopNumber() {
		return hopNumber;
	}
	/**
	 * @param hopNumber the hopNumber to set
	 */
	public void setHopNumber(int hopNumber) {
		this.hopNumber = hopNumber;
	}
}
