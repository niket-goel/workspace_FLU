package com.example.entity;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;
/**
 * Class representing an event.
 * @author Nik
 *
 */
@Entity
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	//The publishing user's ID
	
	private long upVotes;
	private long downVotes;
	/**
	 * Mandatory fields at the time of event creation
	 */
	private long ownerId;
	private Date eventTime;
	private double eventLong;
	private double eventLat;
	private String eventLocation;
	private EventType eventType; //Unused -- we hope future generations can use it \m/!!!
	private String eventDescription;
	private String eventName;
	private String eventCOI;
	//TODO Find a way to get the GPS coordinates from address provided by user
	
	/**
	 * Default Constructor.
	 * @exception Should not be used to create an event that has to be stored in the datastore. Use parameterized constructor. 
	 * 
	 */
	public Event() {
		ownerId = 0;
		upVotes = 0;
		downVotes = 0;
		eventTime = null ;
		eventLat = 0.0;
		eventLong = 0.0;
		eventType = EventType.Default;
		eventDescription = "";
		setEventName(null);
	}
	
	/**
	 * Creates a new Event with all the required fields. Must be used to create event while storing in the datastore.
	 * @param ownerId	ID of the user publishing the event. Pass user.getKey().
	 * @param eventTime	The date and time of the event.
	 * @param eventLocation The GPS coordinates of the event	
	 * @param eventType The type of event being created. Use enumeration Event.EventType.
	 */
	public Event(long ownerId, Date eventTime, double eventLat, double eventLong, String eventName,String eventDescription)
			//,EventType eventType)  
	{
		super();
		this.ownerId = ownerId;
		this.eventTime = eventTime;
		this.eventLat = eventLat;
		this.eventLong = eventLong;
		this.eventDescription = eventDescription;
		this.setEventName(eventName);
	}


	public long getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}
	public long getUpVotes() {
		return upVotes;
	}
	public void setUpVotes(long upVotes) {
		this.upVotes = upVotes;
	}
	public long getDownVotes() {
		return downVotes;
	}
	public void setDownVotes(long downVotes) {
		this.downVotes = downVotes;
	}
	public Date getEventTime() {
		return eventTime;
	}
	public void setEventTime(Date eventTime) {
		this.eventTime = eventTime;
	}
	
	public EventType getEventType() {
		return eventType;
	}
	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}
	public Key getKey() {
		return key;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public double getEventLong() {
		return eventLong;
	}

	public void setEventLong(double eventLong) {
		this.eventLong = eventLong;
	}

	public double getEventLat() {
		return eventLat;
	}

	public void setEventLat(double eventLat) {
		this.eventLat = eventLat;
	}

	public String getEventDescription() {
		return eventDescription;
	}

	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}

	public String getEventLocation() {
		return eventLocation;
	}

	public void setEventLocation(String eventLocation) {
		this.eventLocation = eventLocation;
	}

	/**
	 * @return the eventCOI
	 */
	public String getEventCOI() {
		return eventCOI;
	}

	/**
	 * @param eventCOI the eventCOI to set
	 */
	public void setEventCOI(String eventCOI) {
		this.eventCOI = eventCOI;
	}


}
/**
 * Types of Events.
 * @author Nik
 *
 */
enum EventType{
	//The default value of the type of event
	Default,
	Academic,
	Social,
	Free;
	
}
