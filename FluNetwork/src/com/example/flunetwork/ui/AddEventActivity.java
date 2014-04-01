package com.example.flunetwork.ui;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.ErrorManager;

import com.example.entity.eventendpoint.Eventendpoint;
import com.example.entity.eventendpoint.model.Event;
import com.example.flunetwork.CloudEndpointUtils;
import com.example.flunetwork.R;
import com.example.flunetwork.helper.CustomDateTimePicker;
import com.example.flunetwork.helper.MyGlobal;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.client.util.DateTime;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class AddEventActivity extends Activity implements OnClickListener{

	private static final int SELECT_PICTURE = 1;
	
	CustomDateTimePicker custom;
	Event newEvent;
	Bitmap bmp;


	EditText eventName;
	AutoCompleteTextView eventLocation;
	EditText eventDescription;
	Button setEventTimeBtn;
	ImageView eventImageView;

	String eventNameString;
	String eventLocationString;
	String eventTimeString;
	String eventDescriptionString; 
	String selectedImagePath;
	double eventLat = 0;
	double eventLong = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_event);

		// submit event button	
		Button submitEventButton = (Button) findViewById(R.id.submitEventButton);
		submitEventButton.setOnClickListener(submitEvent) ;

		// get all view IDs for text
		eventName = (EditText) findViewById(R.id.eventNameWhat);
		eventLocation = (AutoCompleteTextView) findViewById(R.id.eventLocationWhere);
		eventDescription = (EditText) findViewById(R.id.eventDescription);
		setEventTimeBtn = (Button) findViewById(R.id.setDateTimeBtn);
		eventImageView = (ImageView) findViewById(R.id.uploadImageView);

		eventImageView.setOnClickListener(this);
		eventLocation.setAdapter(new AutoCompleteAdapter(this));

		custom = new CustomDateTimePicker(this,
				new CustomDateTimePicker.ICustomDateTimeListener() {

			@Override
			public void onSet(Dialog dialog, Calendar calendarSelected,
					Date dateSelected, int year, String monthFullName,
					String monthShortName, int monthNumber, int date,
					String weekDayFullName, String weekDayShortName,
					int hour24, int hour12, int min, int sec,
					String AM_PM) {
				/*setEventTimeBtn
	                            .setText(calendarSelected
	                                            .get(Calendar.DAY_OF_MONTH)
	                                   + "/" + (monthNumber+1) + "/" + year
	                                    + ", " + hour12 + ":" + min
	                                    + " " + AM_PM);*/
				String minutes = String.valueOf(min);
				if(min<10)
				{
					minutes = "0"+minutes;
				}
				setEventTimeBtn
				.setText(calendarSelected
						.get(Calendar.DAY_OF_MONTH)
						+ "/" + (monthNumber+1) + "/" + year
						+ ", " + hour24 + minutes + " hrs");
				newEvent.setEventTime(new DateTime(dateSelected));
				setEventTimeBtn.setGravity(Gravity.LEFT);
				setEventTimeBtn.setGravity(Gravity.CENTER_VERTICAL);
			}

			@Override
			public void onCancel() {

			}
		});
		/**
		 * Pass Directly current time format it will return AM and PM if you set
		 * false
		 */
		custom.set24HourFormat(true);
		/**
		 * Pass Directly current data and time to show when it pop up
		 */
		custom.setDate(Calendar.getInstance());

		setEventTimeBtn.setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						custom.showDialog();
					}
				});
	}

	@Override
	protected void onResume() {
		super.onResume();
		newEvent = new Event();
		newEvent.setEventLat(MyGlobal.IMPOSSIBLE_LOCATION);
		newEvent.setEventLong(MyGlobal.IMPOSSIBLE_LOCATION);
	}


	//@Override
	public OnClickListener submitEvent = new OnClickListener() {

		public void onClick(View v) {
			Boolean incompleteFlag = false;
			String errorMsg = "Come on!!! You have to tell us more.";
			// Resetting the UI
			resetUI();

			eventNameString = eventName.getText().toString();
			eventLocationString = eventLocation.getText().toString();
			eventDescriptionString = eventDescription.getText().toString();

			if(eventNameString == null || eventNameString.isEmpty())
			{
				eventName.setHintTextColor(Color.RED);
				incompleteFlag = true;
			}
			if(eventLocationString == null || eventLocationString.isEmpty())
			{
				eventLocation.setHintTextColor(Color.RED);
				incompleteFlag = true;
			}
			//TODO check the date time is set and is valid.
			if(custom.isDateSet() == false)
			{
				setEventTimeBtn.setTextColor(Color.RED);
				incompleteFlag = true;
			}
			//TODO make this work
		/*	if(newEvent.getEventLat() == MyGlobal.IMPOSSIBLE_LOCATION 
					|| newEvent.getEventLong() == MyGlobal.IMPOSSIBLE_LOCATION)
			{
				if(incompleteFlag)
				{
					errorMsg = errorMsg + "\n Also, ";
					errorMsg = "Please pick a location nearest to the event location from the suggestions.";
				}
				else
				{
					incompleteFlag = true;
					errorMsg = "Please pick a location nearest to the event location from the suggestions.";
				}
			}*/
			if(incompleteFlag)
			{
				Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
				Toast.makeText(getApplicationContext(), "We know!!! Our programmers are lazy!!!", Toast.LENGTH_SHORT).show();
			}
			else 
			{
				new CreateEventTask().execute();
			}
		}
	};

	public void resetUI()
	{
		eventName.setHintTextColor(Color.LTGRAY);
		eventLocation.setHintTextColor(Color.LTGRAY);
		eventDescription.setHintTextColor(Color.LTGRAY);
		setEventTimeBtn.setTextColor(getResources().getColor(R.color.textColorFlu));
	}

	/**
	 * AsyncTask for calling Mobile Assistant API for Pushing an event to the server
	 */
	private class CreateEventTask extends AsyncTask<Void, Void, Void> {

		/**
		 * Calls appropriate CloudEndpoint to add a user created event.
		 *
		 * @param params the event the user just created.
		 */
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Assuming that the string entered in the description
			// is a well defined location, and can be reverse geocoded.

			//new date
			newEvent.setEventName(eventNameString);
			newEvent.setEventDescription(eventDescriptionString);
			//newEvent.setEventTime(

			//user.setLocation(new FluLocation(latitude,longitude)); TODO

			Eventendpoint.Builder builder = new Eventendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
					null);

			builder = CloudEndpointUtils.updateBuilder(builder);

			Eventendpoint endpoint = builder.build();


			try {
				endpoint.insertEvent(newEvent).execute();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			finish();
		}
	}

	// And the corresponding Adapter
	private class AutoCompleteAdapter extends ArrayAdapter<Address> implements Filterable {

		private LayoutInflater inflater;
		private Geocoder geocoder;
		private StringBuilder locationSuggestion = new StringBuilder();

		public AutoCompleteAdapter(final Context context) {
			super(context, -1);
			inflater = LayoutInflater.from(context);
			geocoder = new Geocoder(context);
		}

		@Override
		public View getView(final int position, final View convertView, final ViewGroup parent) {
			final TextView tv;
			if (convertView != null) {
				tv = (TextView) convertView;
			} else {
				tv = (TextView) inflater.inflate(android.R.layout.simple_dropdown_item_1line, parent, false);
			}

			tv.setText(createFormattedAddressFromAddress(getItem(position)));
			return tv;
		}

		private String createFormattedAddressFromAddress(final Address address) 
		{
			locationSuggestion.setLength(0);
			final int addressLineSize = address.getMaxAddressLineIndex();

			for (int i = 0; i < addressLineSize; i++) 
			{
				locationSuggestion.append(address.getAddressLine(i));
				if (i != addressLineSize - 1) 
				{
					locationSuggestion.append(", ");
				}
			}
			newEvent.setEventLocation(locationSuggestion.toString());
			return locationSuggestion.toString();
		}

		@Override
		public Filter getFilter() {
			Filter myFilter = new Filter() {
				@Override
				protected FilterResults performFiltering(final CharSequence constraint) {
					List<Address> addressList = null;
					if (constraint != null) {
						try {
							//newEvent.setEventLat(MyGlobal.IMPOSSIBLE_LOCATION);
							//newEvent.setEventLong(MyGlobal.IMPOSSIBLE_LOCATION);
							addressList = geocoder.getFromLocationName((String) constraint, 5);
						} catch (IOException e) {
						}
					}
					if (addressList == null) {
						addressList = new ArrayList<Address>();
					}

					final FilterResults filterResults = new FilterResults();
					filterResults.values = addressList;
					filterResults.count = addressList.size();

					return filterResults;
				}

				@SuppressWarnings("unchecked")
				@Override
				protected void publishResults(final CharSequence contraint, final FilterResults results) {
					clear();
					for (Address address : (List<Address>) results.values) {
						add(address);
					}
					if (results.count > 0) {
						notifyDataSetChanged();
					} else {
						notifyDataSetInvalidated();
					}
				}

				@Override
				public CharSequence convertResultToString(final Object resultValue) 
				{ 
					newEvent.setEventLat(((Address)resultValue).getLatitude());
					newEvent.setEventLong(((Address)resultValue).getLongitude());
					return resultValue == null ? "" : ((Address) resultValue).getAddressLine(0)+", "+((Address) resultValue).getAddressLine(1);
				}
			};
			return myFilter;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == eventImageView)
		{
			openGallery();
		}
	}


	private void openGallery()
	{
		Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), SELECT_PICTURE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultcode, Intent data)
	{
		super.onActivityResult(requestCode, resultcode, data);
		
		 if (resultcode == RESULT_OK) {
		        if (requestCode == SELECT_PICTURE) {
		            Uri selectedImageUri = data.getData();
		            if (Build.VERSION.SDK_INT < 19) {
		                selectedImagePath = getPath(selectedImageUri);
		                Bitmap bitmap = BitmapFactory.decodeFile(selectedImagePath);
		                eventImageView.setImageBitmap(bitmap);

		            }
		            else {
		                ParcelFileDescriptor parcelFileDescriptor;
		                try {
		                    parcelFileDescriptor = getContentResolver().openFileDescriptor(selectedImageUri, "r");
		                    FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
		                    Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
		                    parcelFileDescriptor.close();
		                    eventImageView.setImageBitmap(image);

		                } catch (FileNotFoundException e) {
		                    e.printStackTrace();
		                } catch (IOException e) {
		                    // TODO Auto-generated catch block
		                    e.printStackTrace();
		                }
		            }
		        }
		    }
		}

		/**
		 * helper to retrieve the path of an image URI
		 */
		@SuppressWarnings("deprecation")
		public String getPath(Uri uri) {
		        if( uri == null ) {
		            return null;
		        }
		        String[] projection = { MediaStore.Images.Media.DATA };
		        Cursor cursor = managedQuery(uri, projection, null, null, null);
		        if( cursor != null ){
		            int column_index = cursor
		            .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		            cursor.moveToFirst();
		            return cursor.getString(column_index);
		        }
		        return uri.getPath();
		}
		
}


