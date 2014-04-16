package com.example.entity;

import com.example.flunetwork.EMF;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.datanucleus.query.JPACursorHelper;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Api(name = "eventendpoint", namespace = @ApiNamespace(ownerDomain = "example.com", ownerName = "example.com", packagePath = "entity"))
public class EventEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listEvent")
	public CollectionResponse<Event> listEvent(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<Event> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from Event as Event");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<Event>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (Event obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Event> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	
/*	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listEvent")
	public CollectionResponse<Event> listEvent(
			@Named("cursor") String geoHashString,
			@Named("eventList") List<String> userEventKeys,
			@Named("limit") Integer limit) {

		geoHashString = geoHashString.substring(0, 8);

		EntityManager mgr = null;
		Cursor cursor = null;
		
		List<Event> execute = null;
		List<String>eventsInVicinity = null;
		List<String>eventsToReturn = new ArrayList<String>();
		
		Query query;
		

		try {
			mgr = getEntityManager();
			//Query query = mgr.createQuery("select from Event as Event");
			query = mgr.createQuery("SELECT u.eventKey FROM UserEventList u WHERE u.geoHash LIKE '"+geoHashString+"%'");
			eventsInVicinity = (List<String>) query.getResultList();
			
			for (String key : eventsInVicinity)
			{
				if(!userEventKeys.contains(key))
					eventsToReturn.add(key);
			}
			String queryClause = "( ";
			for (String key: eventsToReturn)
			{
				queryClause = queryClause +"'"+ key.toString() + "', ";
			}
			
			queryClause = queryClause.substring(0, queryClause.lastIndexOf(","))+" )";
			
			query = mgr.createQuery("SELECT from Event as Event WHERE Key IN "+queryClause);
			execute = (List<Event>)query.getResultList();
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<Event>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				goeHashString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (Event obj : execute)
				;
		} finally {
			mgr.close();
		}
		if(execute.isEmpty())
		{
			Event dummyEvent = new Event();
			dummyEvent.setEventName("There was no event to be returned");
			execute.add(dummyEvent);
		}
		return CollectionResponse.<Event> builder().setItems(execute).build();
				
	}*/

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getEvent")
	public Event getEvent(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		Event event = null;
		try {
			event = mgr.find(Event.class, id);
		} finally {
			mgr.close();
		}
		return event;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param event the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertEvent")
	public Event insertEvent(Event event) {
		EntityManager mgr = getEntityManager();
		try {
			if (containsEvent(event)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.persist(event);
			
		} finally {
			mgr.close();
		}
		return event;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param event the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateEvent")
	public Event updateEvent(Event event) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsEvent(event)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.persist(event);
		} finally {
			mgr.close();
		}
		return event;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeEvent")
	public void removeEvent(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		try {
			Event event = mgr.find(Event.class, id);
			mgr.remove(event);
		} finally {
			mgr.close();
		}
	}

	private boolean containsEvent(Event event) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			if(event.getKey() == null)
				return false;
			Event item = mgr.find(Event.class, event.getKey());
			if (item == null) {
				contains = false;
			}
		} finally {
			mgr.close();
		}
		return contains;
	}

	private static EntityManager getEntityManager() {
		return EMF.get().createEntityManager();
	}

}
