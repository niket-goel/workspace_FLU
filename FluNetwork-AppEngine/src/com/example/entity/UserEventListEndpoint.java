package com.example.entity;

import com.example.flunetwork.EMF;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JPACursorHelper;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Api(name = "usereventlistendpoint", namespace = @ApiNamespace(ownerDomain = "example.com", ownerName = "example.com", packagePath = "entity"))
public class UserEventListEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listUserEventList")
	public CollectionResponse<UserEventList> listUserEventList(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<UserEventList> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr
					.createQuery("select from UserEventList as UserEventList");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<UserEventList>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (UserEventList obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<UserEventList> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getUserEventList")
	public UserEventList getUserEventList(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		UserEventList usereventlist = null;
		try {
			usereventlist = mgr.find(UserEventList.class, id);
		} finally {
			mgr.close();
		}
		return usereventlist;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param usereventlist the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertUserEventList")
	public UserEventList insertUserEventList(UserEventList usereventlist) {
		EntityManager mgr = getEntityManager();
		try {
			if (containsUserEventList(usereventlist)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.persist(usereventlist);
		} finally {
			mgr.close();
		}
		return usereventlist;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param usereventlist the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateUserEventList")
	public UserEventList updateUserEventList(UserEventList usereventlist) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsUserEventList(usereventlist)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.persist(usereventlist);
		} finally {
			mgr.close();
		}
		return usereventlist;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeUserEventList")
	public void removeUserEventList(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		try {
			UserEventList usereventlist = mgr.find(UserEventList.class, id);
			mgr.remove(usereventlist);
		} finally {
			mgr.close();
		}
	}

	private boolean containsUserEventList(UserEventList usereventlist) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			if(usereventlist.getKey() == null)
				return false;
			UserEventList item = mgr.find(UserEventList.class,
					usereventlist.getKey());
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
