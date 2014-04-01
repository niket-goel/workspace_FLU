package com.example.flunetwork.helper;


import com.example.entity.eventendpoint.model.Event;
import com.example.entity.userendpoint.model.User;
/**
 * A class to store global variables which need to be reused throughout the application
 * @author
 *
 */
public class MyGlobal {
	// The global location object which will have the user's last known location at all times.
	public static GPSTracker currentLoc = null;
	public static User currentUser = null;
	public static Event currentEvent = null;

	
	//Constants
	public static final double IMPOSSIBLE_LOCATION = 9999.9;
	// Time between two back button pushes to push app to background in ms
	public static final double TIME_DIFF = 2000; 
}
