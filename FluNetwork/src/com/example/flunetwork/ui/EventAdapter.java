package com.example.flunetwork.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.entity.eventendpoint.model.Event;
import com.example.flunetwork.R;
import com.example.flunetwork.R.drawable;
import com.google.android.gms.internal.ct;


public class EventAdapter extends ArrayAdapter<Event> {
	   
	   private List<Event> EventList;
	   private Context context;
	    
	   public EventAdapter(List<Event> myEvents , Context ctx) {
	       super(ctx, R.layout.event_list_item, myEvents);
	       this.EventList = (ArrayList<Event>) myEvents; 
	       this.context = ctx;
	   }
	    
	   public View getView(int position, View convertView, ViewGroup parent) {
	        
	       // First let's verify the convertView is not null
	       if (convertView == null) {
	           // This a new view we inflate the new layout
	           LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	           convertView = inflater.inflate(R.layout.event_list_item, parent, false);
	       }
	           // Now we can fill the layout with the right values
	           TextView eventNameView = (TextView) convertView.findViewById(R.id.eventName);
	           TextView eventLocationView = (TextView) convertView.findViewById(R.id.eventLocation);
	           TextView eventTimeView = (TextView) convertView.findViewById(R.id.eventTime);
	           TextView eventDateView = (TextView) convertView.findViewById(R.id.eventDate);
	           TextView eventDescriptionView = (TextView) convertView.findViewById(R.id.eventDescriptionView);
	           LinearLayout rootLayout = (LinearLayout) convertView.findViewById(R.id.eventListItemLayout);
	           //Event eventId = myEvents.get(position); TODO
	           //TODO assign the actual image here
	           Drawable bgImage = context.getResources().getDrawable(R.drawable.default_list_image);
	           bgImage.setAlpha(30);
	           rootLayout.setBackground(bgImage);
	           //rootLayout.setAlpha(0.2f);
	           // TODO Perform NULL checks
	           if(EventList.get(position).getEventName() != null && !EventList.get(position).getEventName().isEmpty())
	        	   eventNameView.setText(EventList.get(position).getEventName().toString());
	           else
	        	   eventNameView.setText("I don't think they named it.");
	           //TODO Change this to show the location string 
	           if(EventList.get(position).getEventLocation() != null && !EventList.get(position).getEventLocation().isEmpty())
	        	   eventLocationView.setText(EventList.get(position).getEventLocation().toString());
	           else
	           {
	        	   //eventDescriptionView.setTextSize(20.0f);
	        	   eventLocationView.setText("Oops! We don't remember where!");
	           }
	           
	           if(EventList.get(position).getEventTime() != null)
	           {
	        	   eventTimeView.setText(getTimeString(EventList.get(position).getEventTime().toString()));
	        	   eventDateView.setText(getDateString(EventList.get(position).getEventTime().toString()));
	           }
	           else
	           {
	        	   
	        	   eventTimeView.setText("Dunno when!");
	        	   eventDateView.setText(null);
	           }
	        	
	           if(EventList.get(position).getEventDescription() != null)
	        	   eventDescriptionView.setText(EventList.get(position).getEventDescription().toString());
	           else
	        	   eventTimeView.setText("That's all we heard about the event!!!");
	       return convertView;
	   }
	   
	   String getDateString(String dateTime)
	   {
		   String dateString = null;
		   String[] parts = dateTime.split("T");
		   dateString = parts[0];
		   return dateString;
	   }
	   
	   String getTimeString(String dateTime)
	   {
		   String timeString = null;
		   String[] parts = dateTime.split("T");
		   parts = parts[1].split(".");
		   timeString = parts[0]+" hrs";
		   return timeString;
	   }
	   
}
