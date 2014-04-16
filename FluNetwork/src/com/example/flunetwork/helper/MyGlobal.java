package com.example.flunetwork.helper;


import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

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
	public static ArrayList<Event> hiddenEvents = new ArrayList<Event>();

	public static String userID = null;
	//private static Boolean Registered; 

	//Constants
	public static final double IMPOSSIBLE_LOCATION = 9999.9;
	// Time between two back button pushes to push app to background in ms
	public static final double TIME_DIFF = 2000; 
	public final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

	public final static String IS_REGISTERED_PREF = "isRegistered";

	public final static int COI_ONE = 9;
	public final static int COI_TWO = 8;
	public final static int COI_THREE = 7;
	public final static int COI_FOUR = 6;
	public final static int COI_FIVE = 5;

	private static final int MILLISECONDS_PER_SECOND = 1000;
	// Update frequency in seconds
	public static final int UPDATE_INTERVAL_IN_SECONDS = 60;
	// Update frequency in milliseconds
	public static final long UPDATE_INTERVAL =
			MILLISECONDS_PER_SECOND * UPDATE_INTERVAL_IN_SECONDS;
	// The fastest update frequency, in seconds
	public static final int FASTEST_INTERVAL_IN_SECONDS = 1;
	// A fast frequency ceiling in milliseconds
	public static final long FASTEST_INTERVAL =
			MILLISECONDS_PER_SECOND * FASTEST_INTERVAL_IN_SECONDS;


	/**
	 * @return the registered
	 */
	public static Boolean IsRegistered(Context context) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		Boolean storedPreference = preferences.getBoolean(IS_REGISTERED_PREF, false);
		return storedPreference;
	}
	/**
	 * @param registered the registered to set
	 */
	public static void setRegistered(Context context, Boolean registered) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean(IS_REGISTERED_PREF, registered); 
		editor.commit();
	}
}

