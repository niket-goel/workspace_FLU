package com.example.flunetwork.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.entity.eventendpoint.Eventendpoint;
import com.example.entity.eventendpoint.model.CollectionResponseEvent;
import com.example.entity.eventendpoint.model.Event;
import com.example.entity.userendpoint.Userendpoint;
import com.example.flunetwork.CloudEndpointUtils;
import com.example.flunetwork.R;
import com.example.flunetwork.helper.GPSTracker;
import com.example.flunetwork.helper.MyGlobal;
import com.example.flunetwork.ui.EventAdapter;
import com.example.flunetwork.ui.SwipeDismissListViewTouchListener.DismissCallbacks;
import com.github.davidmoten.geo.GeoHash;
import com.github.davidmoten.geo.LatLong;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.model.LatLng;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson.JacksonFactory;


public class LandingActivity extends Activity implements OnClickListener,
GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener,
LocationListener{

	ArrayList<Event> myEvents;
	EventAdapter myEventAdapter;
	LocationClient locClient;
	LocationRequest mLocationRequest;

	Button addEventButton;
	Button refreshListButton;
	ListView lv;
	ProgressBar bar;

	long backPressedTime;

	GeoHash geohash;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_landing);
		lv  = (ListView) findViewById(R.id.listView);
		addEventButton = (Button)findViewById(R.id.eventAddButton); 
		refreshListButton = (Button)findViewById(R.id.eventRefreshButton); 
		bar = (ProgressBar) this.findViewById(R.id.progressBar);
		
		registerForContextMenu(lv);  
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parentAdapter, View view, int position,long id) {
				MyGlobal.currentEvent = myEvents.get(position);
				startActivity(new Intent(getApplicationContext(),EventDetailActivity.class));
			}
		});		
		 
		addEventButton.setOnClickListener(this);
		refreshListButton.setOnClickListener(this);
		
		MyGlobal.currentLoc = new GPSTracker(this);
		
		if(myEvents == null)
		{
			myEvents = new ArrayList<Event>();
		}

		if(MyGlobal.currentLoc.canGetLocation())
		{
			MyGlobal.currentLoc.getLocation();
			LatLong latlong = new LatLong(MyGlobal.currentLoc.getLatitude(), MyGlobal.currentLoc.getLongitude());
			new GetEventsTask().execute(GeoHash.encodeHash(latlong, MyGlobal.COI_ONE));
		}
		else
		{
			MyGlobal.currentLoc.showSettingsAlert();
		}
		
		SwipeDismissListViewTouchListener touchListener =
				new SwipeDismissListViewTouchListener(
						lv,
						new DismissCallbacks() {
							public void onDismiss(ListView listView, int[] reverseSortedPositions) {
								for (int position : reverseSortedPositions) {
									MyGlobal.hiddenEvents.add(myEventAdapter.getItem(position));
									myEventAdapter.remove(myEventAdapter.getItem(position));

								}
								myEventAdapter.notifyDataSetChanged();
							}

							@Override
							public boolean canDismiss(int position) {
								return true;
							}
						});
		lv.setOnTouchListener(touchListener);
		lv.setOnScrollListener(touchListener.makeScrollListener());
		
		//Location management
		locClient = new LocationClient(this, this, this);
		 // Create the LocationRequest object
        mLocationRequest = LocationRequest.create();
        // Use high accuracy
        mLocationRequest.setPriority(
                LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        // Set the update interval to 5 seconds
        mLocationRequest.setInterval(MyGlobal.UPDATE_INTERVAL);
        // Set the fastest update interval to 1 second
        mLocationRequest.setFastestInterval(MyGlobal.FASTEST_INTERVAL);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	@Override
	public void onClick(View v) {
		if(v == addEventButton)
		{
			Intent addEventIntent = new Intent(LandingActivity.this, AddEventActivity.class);
			startActivity(addEventIntent);
		}
		else if(v == refreshListButton)
		{
			MyGlobal.currentLoc = new GPSTracker(this);
			if(MyGlobal.currentLoc.canGetLocation())
			{
				MyGlobal.currentLoc.getLocation();
				LatLong latlong = new LatLong(MyGlobal.currentLoc.getLatitude(), MyGlobal.currentLoc.getLongitude());
				new GetEventsTask().execute(GeoHash.encodeHash(latlong, MyGlobal.COI_ONE));
			}
			else
			{
				MyGlobal.currentLoc.showSettingsAlert();
			}
		}
	}


	@Override
	protected void onResume() {
		super.onResume();
	
	}

	@Override
	public void onBackPressed() {
		if (backPressedTime + MyGlobal.TIME_DIFF > System.currentTimeMillis()) {
			super.onBackPressed();
			moveTaskToBack(true);
		}
		else
		{
			backPressedTime = System.currentTimeMillis();
			Toast.makeText(this, R.string.exit_press_back_twice_message, Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * AsyncTask for calling Mobile Assistant API for getting the list of events
	 */
	private class GetEventsTask extends AsyncTask<String, String, Void> {

		/**
		 * Calls appropriate CloudEndpoint to indicate that user checked into a place.
		 *
		 * @param params the place where the user is checking in.
		 */
		private CollectionResponseEvent eventList = null;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			bar.setVisibility(View.VISIBLE);
		}

		@Override
		protected Void doInBackground(String... params) {
			Eventendpoint.Builder builder = new Eventendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
					null);

			String geohash = params[0];
			builder = CloudEndpointUtils.updateBuilder(builder);
			List<String> eventsOnDevice = new ArrayList<String>();
			for(Event e : myEvents)
			{
				eventsOnDevice.add(e.getKey().toString());
			}
			Eventendpoint endpoint = builder.build();


			try {
				 //TODO eventList = endpoint.listEvent(geohash,eventsOnDevice,0).execute();
				eventList = endpoint.listEvent().execute();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			bar.setVisibility(View.INVISIBLE);
			if(eventList != null)
			{
				if(eventList.size() == 1 && ((List<Event>)eventList.getItems()).get(0).getEventName().equals("There was no event to be returned"))
				{
					Toast.makeText(getApplicationContext(), "No events around you!", Toast.LENGTH_SHORT).show();
				}
				myEvents.clear();
				myEvents.addAll(eventList.getItems());
				// Removing the hidden elements from the event list
				if(MyGlobal.hiddenEvents != null)
				{
					for(Event e: MyGlobal.hiddenEvents)
					{
						myEvents.remove(e);
					}
				}
				myEventAdapter = new EventAdapter(myEvents, getApplicationContext());
				lv.setAdapter(myEventAdapter);
			}
			else
			{
				Toast.makeText(getApplicationContext(), "Could not contact server. Will try to get events later !", Toast.LENGTH_SHORT).show();
			}
		}
	}


	private class UpdateUserTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		/**
		 * Calls appropriate CloudEndpoint to indicate that user checked into a place.
		 *
		 * @param params The new Location of the user
		 */
		@Override
		protected Void doInBackground(Void... params) {

			if(MyGlobal.currentLoc.canGetLocation())
			{
				MyGlobal.currentLoc.getLocation();
				MyGlobal.currentUser.setUserLat(MyGlobal.currentLoc.getLatitude());
				MyGlobal.currentUser.setUserLong(MyGlobal.currentLoc.getLongitude());
			}
			else
			{
				MyGlobal.currentLoc.showSettingsAlert();
			}

			Userendpoint.Builder builder = new Userendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
					null);

			builder = CloudEndpointUtils.updateBuilder(builder);

			Userendpoint endpoint = builder.build();


			try {
				MyGlobal.currentUser = endpoint.updateUser(MyGlobal.currentUser).execute();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if(MyGlobal.currentLoc.canGetLocation())
			{
				MyGlobal.currentLoc.getLocation();
				LatLong latlong = new LatLong(MyGlobal.currentLoc.getLatitude(), MyGlobal.currentLoc.getLongitude());
				new GetEventsTask().execute(GeoHash.encodeHash(latlong, MyGlobal.COI_ONE));
			}
			else
			{
				MyGlobal.currentLoc.showSettingsAlert();
			}

		}
	}

	// Manage periodical location updates


	// Define the callback method that receives location updates
	@Override
	public void onLocationChanged(Location location) {
		// Report to the UI that the location was updated
		new UpdateUserTask().execute();

	}


	/*
	 * Called by Location Services when the request to connect the
	 * client finishes successfully. At this point, you can
	 * request the current location or start periodic updates
	 */
	@Override
	public void onConnected(Bundle dataBundle) {
		// Display the connection status
		Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
		//locClient.requestLocationUpdates(mLocationRequest, this);
	}
	/*
	 * Called by Location Services if the connection to the
	 * location client drops because of an error.
	 */
	@Override
	public void onDisconnected() {
		// Display the connection status
		Toast.makeText(this, "Disconnected. Please re-connect.",
				Toast.LENGTH_SHORT).show();
	}
	/*
	 * Called by Location Services if the attempt to
	 * Location Services fails.
	 */
	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		/*
		 * Google Play services can resolve some errors it detects.
		 * If the error has a resolution, try sending an Intent to
		 * start a Google Play services activity that can resolve
		 * error.
		 */
		if (connectionResult.hasResolution()) {
			try {
				// Start an Activity that tries to resolve the error
				connectionResult.startResolutionForResult(
						this,
						MyGlobal.CONNECTION_FAILURE_RESOLUTION_REQUEST);
				/*
				 * Thrown if Google Play services canceled the original
				 * PendingIntent
				 */
			} catch (IntentSender.SendIntentException e) {
				// Log the error
				e.printStackTrace();
			}
		} else {
			/*
			 * If no resolution is available, display a dialog to the
			 * user with the error.
			 */
			//showErrorDialog(connectionResult.getErrorCode());
			Toast.makeText(this, "I was supposed to pop an error here", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Connection Lost", Toast.LENGTH_SHORT).show();

	}


}