/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://code.google.com/p/google-apis-client-generator/
 * (build: 2014-04-15 19:10:39 UTC)
 * on 2014-04-24 at 19:54:29 UTC 
 * Modify at your own risk.
 */

package com.example.entity.usereventlistendpoint;

/**
 * Service definition for Usereventlistendpoint (v1).
 *
 * <p>
 * This is an API
 * </p>
 *
 * <p>
 * For more information about this service, see the
 * <a href="" target="_blank">API Documentation</a>
 * </p>
 *
 * <p>
 * This service uses {@link UsereventlistendpointRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class Usereventlistendpoint extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.16.0-rc of the usereventlistendpoint library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
  }

  /**
   * The default encoded root URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_ROOT_URL = "https://flu-network.appspot.com/_ah/api/";

  /**
   * The default encoded service path of the service. This is determined when the library is
   * generated and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_SERVICE_PATH = "usereventlistendpoint/v1/";

  /**
   * The default encoded base URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   */
  public static final String DEFAULT_BASE_URL = DEFAULT_ROOT_URL + DEFAULT_SERVICE_PATH;

  /**
   * Constructor.
   *
   * <p>
   * Use {@link Builder} if you need to specify any of the optional parameters.
   * </p>
   *
   * @param transport HTTP transport, which should normally be:
   *        <ul>
   *        <li>Google App Engine:
   *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
   *        <li>Android: {@code newCompatibleTransport} from
   *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
   *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
   *        </li>
   *        </ul>
   * @param jsonFactory JSON factory, which may be:
   *        <ul>
   *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
   *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
   *        <li>Android Honeycomb or higher:
   *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
   *        </ul>
   * @param httpRequestInitializer HTTP request initializer or {@code null} for none
   * @since 1.7
   */
  public Usereventlistendpoint(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  Usereventlistendpoint(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "getUserEventList".
   *
   * This request holds the parameters needed by the the usereventlistendpoint server.  After setting
   * any optional parameters, call the {@link GetUserEventList#execute()} method to invoke the remote
   * operation.
   *
   * @param id
   * @return the request
   */
  public GetUserEventList getUserEventList(java.lang.Long id) throws java.io.IOException {
    GetUserEventList result = new GetUserEventList(id);
    initialize(result);
    return result;
  }

  public class GetUserEventList extends UsereventlistendpointRequest<com.example.entity.usereventlistendpoint.model.UserEventList> {

    private static final String REST_PATH = "usereventlist/{id}";

    /**
     * Create a request for the method "getUserEventList".
     *
     * This request holds the parameters needed by the the usereventlistendpoint server.  After
     * setting any optional parameters, call the {@link GetUserEventList#execute()} method to invoke
     * the remote operation. <p> {@link GetUserEventList#initialize(com.google.api.client.googleapis.s
     * ervices.AbstractGoogleClientRequest)} must be called to initialize this instance immediately
     * after invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected GetUserEventList(java.lang.Long id) {
      super(Usereventlistendpoint.this, "GET", REST_PATH, null, com.example.entity.usereventlistendpoint.model.UserEventList.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
      return super.executeUsingHead();
    }

    @Override
    public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
      return super.buildHttpRequestUsingHead();
    }

    @Override
    public GetUserEventList setAlt(java.lang.String alt) {
      return (GetUserEventList) super.setAlt(alt);
    }

    @Override
    public GetUserEventList setFields(java.lang.String fields) {
      return (GetUserEventList) super.setFields(fields);
    }

    @Override
    public GetUserEventList setKey(java.lang.String key) {
      return (GetUserEventList) super.setKey(key);
    }

    @Override
    public GetUserEventList setOauthToken(java.lang.String oauthToken) {
      return (GetUserEventList) super.setOauthToken(oauthToken);
    }

    @Override
    public GetUserEventList setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetUserEventList) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetUserEventList setQuotaUser(java.lang.String quotaUser) {
      return (GetUserEventList) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetUserEventList setUserIp(java.lang.String userIp) {
      return (GetUserEventList) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public GetUserEventList setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public GetUserEventList set(String parameterName, Object value) {
      return (GetUserEventList) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "insertUserEventList".
   *
   * This request holds the parameters needed by the the usereventlistendpoint server.  After setting
   * any optional parameters, call the {@link InsertUserEventList#execute()} method to invoke the
   * remote operation.
   *
   * @param content the {@link com.example.entity.usereventlistendpoint.model.UserEventList}
   * @return the request
   */
  public InsertUserEventList insertUserEventList(com.example.entity.usereventlistendpoint.model.UserEventList content) throws java.io.IOException {
    InsertUserEventList result = new InsertUserEventList(content);
    initialize(result);
    return result;
  }

  public class InsertUserEventList extends UsereventlistendpointRequest<com.example.entity.usereventlistendpoint.model.UserEventList> {

    private static final String REST_PATH = "usereventlist";

    /**
     * Create a request for the method "insertUserEventList".
     *
     * This request holds the parameters needed by the the usereventlistendpoint server.  After
     * setting any optional parameters, call the {@link InsertUserEventList#execute()} method to
     * invoke the remote operation. <p> {@link InsertUserEventList#initialize(com.google.api.client.go
     * ogleapis.services.AbstractGoogleClientRequest)} must be called to initialize this instance
     * immediately after invoking the constructor. </p>
     *
     * @param content the {@link com.example.entity.usereventlistendpoint.model.UserEventList}
     * @since 1.13
     */
    protected InsertUserEventList(com.example.entity.usereventlistendpoint.model.UserEventList content) {
      super(Usereventlistendpoint.this, "POST", REST_PATH, content, com.example.entity.usereventlistendpoint.model.UserEventList.class);
    }

    @Override
    public InsertUserEventList setAlt(java.lang.String alt) {
      return (InsertUserEventList) super.setAlt(alt);
    }

    @Override
    public InsertUserEventList setFields(java.lang.String fields) {
      return (InsertUserEventList) super.setFields(fields);
    }

    @Override
    public InsertUserEventList setKey(java.lang.String key) {
      return (InsertUserEventList) super.setKey(key);
    }

    @Override
    public InsertUserEventList setOauthToken(java.lang.String oauthToken) {
      return (InsertUserEventList) super.setOauthToken(oauthToken);
    }

    @Override
    public InsertUserEventList setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (InsertUserEventList) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public InsertUserEventList setQuotaUser(java.lang.String quotaUser) {
      return (InsertUserEventList) super.setQuotaUser(quotaUser);
    }

    @Override
    public InsertUserEventList setUserIp(java.lang.String userIp) {
      return (InsertUserEventList) super.setUserIp(userIp);
    }

    @Override
    public InsertUserEventList set(String parameterName, Object value) {
      return (InsertUserEventList) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "listUserEventList".
   *
   * This request holds the parameters needed by the the usereventlistendpoint server.  After setting
   * any optional parameters, call the {@link ListUserEventList#execute()} method to invoke the remote
   * operation.
   *
   * @return the request
   */
  public ListUserEventList listUserEventList() throws java.io.IOException {
    ListUserEventList result = new ListUserEventList();
    initialize(result);
    return result;
  }

  public class ListUserEventList extends UsereventlistendpointRequest<com.example.entity.usereventlistendpoint.model.CollectionResponseUserEventList> {

    private static final String REST_PATH = "usereventlist";

    /**
     * Create a request for the method "listUserEventList".
     *
     * This request holds the parameters needed by the the usereventlistendpoint server.  After
     * setting any optional parameters, call the {@link ListUserEventList#execute()} method to invoke
     * the remote operation. <p> {@link ListUserEventList#initialize(com.google.api.client.googleapis.
     * services.AbstractGoogleClientRequest)} must be called to initialize this instance immediately
     * after invoking the constructor. </p>
     *
     * @since 1.13
     */
    protected ListUserEventList() {
      super(Usereventlistendpoint.this, "GET", REST_PATH, null, com.example.entity.usereventlistendpoint.model.CollectionResponseUserEventList.class);
    }

    @Override
    public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
      return super.executeUsingHead();
    }

    @Override
    public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
      return super.buildHttpRequestUsingHead();
    }

    @Override
    public ListUserEventList setAlt(java.lang.String alt) {
      return (ListUserEventList) super.setAlt(alt);
    }

    @Override
    public ListUserEventList setFields(java.lang.String fields) {
      return (ListUserEventList) super.setFields(fields);
    }

    @Override
    public ListUserEventList setKey(java.lang.String key) {
      return (ListUserEventList) super.setKey(key);
    }

    @Override
    public ListUserEventList setOauthToken(java.lang.String oauthToken) {
      return (ListUserEventList) super.setOauthToken(oauthToken);
    }

    @Override
    public ListUserEventList setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (ListUserEventList) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public ListUserEventList setQuotaUser(java.lang.String quotaUser) {
      return (ListUserEventList) super.setQuotaUser(quotaUser);
    }

    @Override
    public ListUserEventList setUserIp(java.lang.String userIp) {
      return (ListUserEventList) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String cursor;

    /**

     */
    public java.lang.String getCursor() {
      return cursor;
    }

    public ListUserEventList setCursor(java.lang.String cursor) {
      this.cursor = cursor;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.Integer limit;

    /**

     */
    public java.lang.Integer getLimit() {
      return limit;
    }

    public ListUserEventList setLimit(java.lang.Integer limit) {
      this.limit = limit;
      return this;
    }

    @Override
    public ListUserEventList set(String parameterName, Object value) {
      return (ListUserEventList) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "removeUserEventList".
   *
   * This request holds the parameters needed by the the usereventlistendpoint server.  After setting
   * any optional parameters, call the {@link RemoveUserEventList#execute()} method to invoke the
   * remote operation.
   *
   * @param id
   * @return the request
   */
  public RemoveUserEventList removeUserEventList(java.lang.Long id) throws java.io.IOException {
    RemoveUserEventList result = new RemoveUserEventList(id);
    initialize(result);
    return result;
  }

  public class RemoveUserEventList extends UsereventlistendpointRequest<Void> {

    private static final String REST_PATH = "usereventlist/{id}";

    /**
     * Create a request for the method "removeUserEventList".
     *
     * This request holds the parameters needed by the the usereventlistendpoint server.  After
     * setting any optional parameters, call the {@link RemoveUserEventList#execute()} method to
     * invoke the remote operation. <p> {@link RemoveUserEventList#initialize(com.google.api.client.go
     * ogleapis.services.AbstractGoogleClientRequest)} must be called to initialize this instance
     * immediately after invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected RemoveUserEventList(java.lang.Long id) {
      super(Usereventlistendpoint.this, "DELETE", REST_PATH, null, Void.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public RemoveUserEventList setAlt(java.lang.String alt) {
      return (RemoveUserEventList) super.setAlt(alt);
    }

    @Override
    public RemoveUserEventList setFields(java.lang.String fields) {
      return (RemoveUserEventList) super.setFields(fields);
    }

    @Override
    public RemoveUserEventList setKey(java.lang.String key) {
      return (RemoveUserEventList) super.setKey(key);
    }

    @Override
    public RemoveUserEventList setOauthToken(java.lang.String oauthToken) {
      return (RemoveUserEventList) super.setOauthToken(oauthToken);
    }

    @Override
    public RemoveUserEventList setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (RemoveUserEventList) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public RemoveUserEventList setQuotaUser(java.lang.String quotaUser) {
      return (RemoveUserEventList) super.setQuotaUser(quotaUser);
    }

    @Override
    public RemoveUserEventList setUserIp(java.lang.String userIp) {
      return (RemoveUserEventList) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public RemoveUserEventList setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public RemoveUserEventList set(String parameterName, Object value) {
      return (RemoveUserEventList) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "updateUserEventList".
   *
   * This request holds the parameters needed by the the usereventlistendpoint server.  After setting
   * any optional parameters, call the {@link UpdateUserEventList#execute()} method to invoke the
   * remote operation.
   *
   * @param content the {@link com.example.entity.usereventlistendpoint.model.UserEventList}
   * @return the request
   */
  public UpdateUserEventList updateUserEventList(com.example.entity.usereventlistendpoint.model.UserEventList content) throws java.io.IOException {
    UpdateUserEventList result = new UpdateUserEventList(content);
    initialize(result);
    return result;
  }

  public class UpdateUserEventList extends UsereventlistendpointRequest<com.example.entity.usereventlistendpoint.model.UserEventList> {

    private static final String REST_PATH = "usereventlist";

    /**
     * Create a request for the method "updateUserEventList".
     *
     * This request holds the parameters needed by the the usereventlistendpoint server.  After
     * setting any optional parameters, call the {@link UpdateUserEventList#execute()} method to
     * invoke the remote operation. <p> {@link UpdateUserEventList#initialize(com.google.api.client.go
     * ogleapis.services.AbstractGoogleClientRequest)} must be called to initialize this instance
     * immediately after invoking the constructor. </p>
     *
     * @param content the {@link com.example.entity.usereventlistendpoint.model.UserEventList}
     * @since 1.13
     */
    protected UpdateUserEventList(com.example.entity.usereventlistendpoint.model.UserEventList content) {
      super(Usereventlistendpoint.this, "PUT", REST_PATH, content, com.example.entity.usereventlistendpoint.model.UserEventList.class);
    }

    @Override
    public UpdateUserEventList setAlt(java.lang.String alt) {
      return (UpdateUserEventList) super.setAlt(alt);
    }

    @Override
    public UpdateUserEventList setFields(java.lang.String fields) {
      return (UpdateUserEventList) super.setFields(fields);
    }

    @Override
    public UpdateUserEventList setKey(java.lang.String key) {
      return (UpdateUserEventList) super.setKey(key);
    }

    @Override
    public UpdateUserEventList setOauthToken(java.lang.String oauthToken) {
      return (UpdateUserEventList) super.setOauthToken(oauthToken);
    }

    @Override
    public UpdateUserEventList setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (UpdateUserEventList) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public UpdateUserEventList setQuotaUser(java.lang.String quotaUser) {
      return (UpdateUserEventList) super.setQuotaUser(quotaUser);
    }

    @Override
    public UpdateUserEventList setUserIp(java.lang.String userIp) {
      return (UpdateUserEventList) super.setUserIp(userIp);
    }

    @Override
    public UpdateUserEventList set(String parameterName, Object value) {
      return (UpdateUserEventList) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link Usereventlistendpoint}.
   *
   * <p>
   * Implementation is not thread-safe.
   * </p>
   *
   * @since 1.3.0
   */
  public static final class Builder extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient.Builder {

    /**
     * Returns an instance of a new builder.
     *
     * @param transport HTTP transport, which should normally be:
     *        <ul>
     *        <li>Google App Engine:
     *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
     *        <li>Android: {@code newCompatibleTransport} from
     *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
     *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
     *        </li>
     *        </ul>
     * @param jsonFactory JSON factory, which may be:
     *        <ul>
     *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
     *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
     *        <li>Android Honeycomb or higher:
     *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
     *        </ul>
     * @param httpRequestInitializer HTTP request initializer or {@code null} for none
     * @since 1.7
     */
    public Builder(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
        com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      super(
          transport,
          jsonFactory,
          DEFAULT_ROOT_URL,
          DEFAULT_SERVICE_PATH,
          httpRequestInitializer,
          false);
    }

    /** Builds a new instance of {@link Usereventlistendpoint}. */
    @Override
    public Usereventlistendpoint build() {
      return new Usereventlistendpoint(this);
    }

    @Override
    public Builder setRootUrl(String rootUrl) {
      return (Builder) super.setRootUrl(rootUrl);
    }

    @Override
    public Builder setServicePath(String servicePath) {
      return (Builder) super.setServicePath(servicePath);
    }

    @Override
    public Builder setHttpRequestInitializer(com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      return (Builder) super.setHttpRequestInitializer(httpRequestInitializer);
    }

    @Override
    public Builder setApplicationName(String applicationName) {
      return (Builder) super.setApplicationName(applicationName);
    }

    @Override
    public Builder setSuppressPatternChecks(boolean suppressPatternChecks) {
      return (Builder) super.setSuppressPatternChecks(suppressPatternChecks);
    }

    @Override
    public Builder setSuppressRequiredParameterChecks(boolean suppressRequiredParameterChecks) {
      return (Builder) super.setSuppressRequiredParameterChecks(suppressRequiredParameterChecks);
    }

    @Override
    public Builder setSuppressAllChecks(boolean suppressAllChecks) {
      return (Builder) super.setSuppressAllChecks(suppressAllChecks);
    }

    /**
     * Set the {@link UsereventlistendpointRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setUsereventlistendpointRequestInitializer(
        UsereventlistendpointRequestInitializer usereventlistendpointRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(usereventlistendpointRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}