package com.example.flunetwork.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.google.api.client.util.DateTime;
import com.example.entity.eventendpoint.model.Event;
import com.example.flunetwork.R;
import com.example.flunetwork.helper.GPSTracker;
import com.example.flunetwork.helper.MyGlobal;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class EventDetailActivity extends FragmentActivity{

	GoogleMap map;
	Event eventToDisplay;

	TextView eventDateTxtVw;
	TextView eventTimeTxtVw;
	TextView eventNameTxtVw;
	TextView eventLocationTxtVw;
	TextView eventDecriptionTxtVw;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_detail);

		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapDisplayLoc)).getMap();
		map.setMyLocationEnabled(true);

		eventToDisplay = MyGlobal.currentEvent;
		/*eventToDisplay.setEventDescription("This is a default description");
		eventToDisplay.setEventLat(29.6433692 + .01);
		eventToDisplay.setEventLong(-82.3474775);
		eventToDisplay.setEventName("Awesome Event");*/

		eventDateTxtVw = (TextView)findViewById(R.id.eventDateTextView);
		eventTimeTxtVw = (TextView)findViewById(R.id.eventTimeTextView);
		eventNameTxtVw = (TextView)findViewById(R.id.eventNameTextView);
		eventLocationTxtVw = (TextView)findViewById(R.id.eventLocationTextView);
		eventDecriptionTxtVw = (TextView)findViewById(R.id.eventDescriptionTextView);

		//currentEvent.setEventTime(new Date());

	}

	@Override
	protected void onResume() {
		super.onResume();
		this.setTitle(eventToDisplay.getEventName());

		eventDateTxtVw.setText(extractDateString(eventToDisplay.getEventTime()));
		eventTimeTxtVw.setText(extractTimeString(eventToDisplay.getEventTime()));
		eventNameTxtVw.setText(eventToDisplay.getEventName());
		eventDecriptionTxtVw.setText(eventToDisplay.getEventDescription());

		MyGlobal.currentLoc = new GPSTracker(this);
		if(MyGlobal.currentLoc.canGetLocation())
		{
			MyGlobal.currentLoc.getLocation();
		}
		else
		{
			MyGlobal.currentLoc.showSettingsAlert();
		}


		LatLng UserLatLng = new LatLng(MyGlobal.currentLoc.getLatitude(), MyGlobal.currentLoc.getLongitude());
		LatLng EventLatLng = new LatLng(eventToDisplay.getEventLat(),eventToDisplay.getEventLong());

		Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
		String eventAdrressLine = "";
		List<Address> addresses = null;
		if(eventToDisplay.getEventLat() > 0.0 && eventToDisplay.getEventLat() < 9999  
				&& eventToDisplay.getEventLong() < 0.0 && eventToDisplay.getEventLong() < 9999)
		{
			try 
			{
				addresses = geoCoder.getFromLocation(eventToDisplay.getEventLat(), eventToDisplay.getEventLong(), 1);
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		else
		{
			Toast.makeText(this, "Error! Could not fetch event location", Toast.LENGTH_SHORT).show();
		}
		if(addresses != null)
		{
			if(!addresses.get(0).getAddressLine(0).isEmpty())
				eventAdrressLine = addresses.get(0).getAddressLine(0);
			else
				eventAdrressLine = addresses.get(0).getLocality()+", "+addresses.get(0).getCountryCode();
		}
		else 
		{
			eventAdrressLine = "Event Location";
		}

		//eventLocationTxtVw.setText(eventToDisplay.getEventLocation());
		eventLocationTxtVw.setText(eventAdrressLine);
		//TODO add the functionality to add multiple markers here, if the 
		//address entered returns more than one matches. Currently it is not needed
		//as it is assumed that addresses entered are precise locations.

		Marker userLocation = map.addMarker(new MarkerOptions().position(UserLatLng)
				.title("Your location").flat(false).icon(BitmapDescriptorFactory
						.fromResource(R.drawable.ic_launcher)));
		Marker eventLocation = map.addMarker(new MarkerOptions().position(EventLatLng)
				.title(eventAdrressLine).flat(false));
		// Move the camera instantly to current location with a zoom of 15.
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(EventLatLng, 15));

		// Zoom in, animating the camera.
		map.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);

	}

	private String extractTimeString(DateTime eventTime) {
		String timeString =null;
		if(eventTime!=null)
		{
			timeString = eventTime.toString();
			timeString = timeString.substring(0, timeString.indexOf("T"));
		}
		else
		{
			timeString = "--:--";
		}
		return timeString;
	}

	private String extractDateString(DateTime eventTime) {
		// TODO Auto-generated method stub
		String dateString = null; 
		if(eventTime!=null)
		{
			dateString = eventTime.toString();
			dateString = dateString.substring(0, dateString.indexOf("T"));
		}
		else
			dateString = "--/--/--";
		return dateString;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.event_detail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
