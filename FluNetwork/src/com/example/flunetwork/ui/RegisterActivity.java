package com.example.flunetwork.ui;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.example.entity.userendpoint.Userendpoint;
import com.example.entity.userendpoint.model.User;
import com.example.flunetwork.CloudEndpointUtils;
import com.example.flunetwork.R;
import com.example.flunetwork.helper.GPSTracker;
import com.example.flunetwork.helper.MyGlobal;
import com.github.davidmoten.geo.GeoHash;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson.JacksonFactory;

import android.os.AsyncTask;
import android.os.Bundle;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class RegisterActivity extends Activity implements OnClickListener{

	Button registerBtn;
	double latitude;
	double longitude;

	ProgressBar bar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		registerBtn = (Button)findViewById(R.id.regButton); 
		registerBtn.setOnClickListener(this);
		MyGlobal.currentLoc = new GPSTracker(this);	
		bar = (ProgressBar) this.findViewById(R.id.regprogressBar);

	}

	@Override
	protected void onResume() {
		super.onResume();

		if(MyGlobal.currentLoc.canGetLocation())
		{
			MyGlobal.currentLoc.getLocation();
			latitude = MyGlobal.currentLoc.getLatitude();
			longitude = MyGlobal.currentLoc.getLongitude();
		}
		else
		{
			MyGlobal.currentLoc.showSettingsAlert();
		}

		if(!MyGlobal.IsRegistered(getApplicationContext()))
		{
			registerBtn.setVisibility(Button.VISIBLE);
		}
		else
		{
			registerBtn.setVisibility(Button.INVISIBLE);
			startActivity(new Intent(this,LandingActivity.class));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.events_display, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		if(v == registerBtn)
		{
			new CreateUserTask().execute();

		}
	}
	/**
	 * AsyncTask for calling Mobile Assistant API for checking into a place (e.g., a store)
	 */
	private class CreateUserTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			registerBtn.setVisibility(View.INVISIBLE);
			bar.setVisibility(View.VISIBLE);
		}

		/**
		 * Calls appropriate CloudEndpoint to indicate that user checked into a place.
		 *
		 * @param params the place where the user is checking in.
		 */
		@Override
		protected Void doInBackground(Void... params) {

			String userHash = GeoHash.encodeHash(MyGlobal.currentLoc.getLatitude(), 
					MyGlobal.currentLoc.getLongitude(), MyGlobal.COI_ONE);
			User newUser = new User();
			String uName = getUsername()!=null ? getUsername() : "anonymous";
			newUser.setUserName(uName);
			String uMail = getUsermail()!=null ? getUsermail() : "anonymous@user.com";
			newUser.setUserEmailID(uMail);
			newUser.setUserLat(MyGlobal.currentLoc.getLatitude());
			newUser.setUserLong(MyGlobal.currentLoc.getLongitude());
			newUser.setUserGeoHash(userHash);
			newUser.setIsPublisher(false);
			// TODO push user location

			Userendpoint.Builder builder = new Userendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
					null);

			builder = CloudEndpointUtils.updateBuilder(builder);

			Userendpoint endpoint = builder.build();


			try {
				MyGlobal.currentUser = endpoint.insertUser(newUser).execute();
				// TODO use the update function instead of the insert one. Deploy the latest 
				// backend for it to work. Even then it might not work.
				//MyGlobal.currentUser = endpoint.updateUser(newUser).execute();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			// TODO Put some kind of progress bar
			super.onPostExecute(result);
			if(MyGlobal.currentUser != null)
			{ 
				MyGlobal.setRegistered(getApplicationContext(), true);
				startActivity(new Intent(getApplicationContext(),LandingActivity.class));
			}
			else
			{
				bar.setVisibility(View.GONE);
				registerBtn.setVisibility(View.VISIBLE);
				Toast.makeText(getApplicationContext(), "Could not contact server", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	
	private String getUsername(){
	    AccountManager manager = AccountManager.get(this); 
	    Account[] accounts = manager.getAccountsByType("com.google"); 
	    List<String> possibleEmails = new LinkedList<String>();

	    for (Account account : accounts) {
	      // TODO: Check possibleEmail against an email regex or treat
	      // account.name as an email address only for certain account.type values.
	      possibleEmails.add(account.name);
	    }

	    if(!possibleEmails.isEmpty() && possibleEmails.get(0) != null){
	        String email = possibleEmails.get(0);
	        String[] parts = email.split("@");
	        if(parts.length > 0 && parts[0] != null)
	            return parts[0];
	        else
	            return null;
	    }else
	        return null;
	}
	
	private String getUsermail(){
	    AccountManager manager = AccountManager.get(this); 
	    Account[] accounts = manager.getAccountsByType("com.google"); 
	    List<String> possibleEmails = new LinkedList<String>();

	    for (Account account : accounts) {
	      // TODO: Check possibleEmail against an email regex or treat
	      // account.name as an email address only for certain account.type values.
	      possibleEmails.add(account.name);
	    }

	    if(!possibleEmails.isEmpty() && possibleEmails.get(0) != null){
	        String email = possibleEmails.get(0);
	        if(email != null)
	            return email;
	        else
	            return null;
	    }else
	        return null;
	}
}
