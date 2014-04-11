package com.example.flunetwork.ui;

import java.io.IOException;
import java.util.ArrayList;

import android.location.Location;
import android.location.LocationListener;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
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
import com.example.flunetwork.helper.MyGlobal;
import com.example.flunetwork.ui.EventAdapter;
import com.example.flunetwork.ui.SwipeDismissListViewTouchListener.DismissCallbacks;
import com.github.davidmoten.geo.GeoHash;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson.JacksonFactory;


public class LandingActivity extends Activity implements OnClickListener,
GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener,
LocationListener{

	ArrayList<Event> myEvents;
	EventAdapter myEventAdapter;

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
		// initList();
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
		
		if(myEvents == null)
		{
			myEvents = new ArrayList<Event>();
		}

		new GetEventsTask().execute();
		
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
								// TODO Auto-generated method stub
								return true;
							}
						});
		lv.setOnTouchListener(touchListener);
		lv.setOnScrollListener(touchListener.makeScrollListener());
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		int itemId = item.getItemId();
//		Implements our logic
//		item.setVisible(false);
//		MyGlobal.hiddenEvents.add(myEvents.get(itemId));
//		Toast.makeText(this, "Item id ["+itemId+"]", Toast.LENGTH_SHORT).show();
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	@Override
	public void onClick(View v) {
		if(v == addEventButton)
		{
			//create a new intent that will launch the new 'page'
			Intent addEventIntent = new Intent(LandingActivity.this, AddEventActivity.class);
			startActivity(addEventIntent);
		}
		else if(v == refreshListButton)
		{
			new GetEventsTask().execute();
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
	private class GetEventsTask extends AsyncTask<Void, Void, Void> {

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
		protected Void doInBackground(Void... params) {
			Eventendpoint.Builder builder = new Eventendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
					null);

			builder = CloudEndpointUtils.updateBuilder(builder);

			Eventendpoint endpoint = builder.build();


			try {
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
			//TODO
		}

		/**
		 * Calls appropriate CloudEndpoint to indicate that user checked into a place.
		 *
		 * @param params The new Location of the user
		 */
		@Override
		protected Void doInBackground(Void... params) {

			MyGlobal.currentUser.setUserLat(MyGlobal.currentLoc.getLatitude());
			MyGlobal.currentUser.setUserLong(MyGlobal.currentLoc.getLongitude());

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
			// TODO 
			super.onPostExecute(result);

		}
	}




	// Manage periodical location updates


	// Define the callback method that receives location updates
	@Override
	public void onLocationChanged(Location location) {
		// Report to the UI that the location was updated
		String msg = "Updated Location: " +
				Double.toString(location.getLatitude()) + "," +
				Double.toString(location.getLongitude());
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
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

	}


}