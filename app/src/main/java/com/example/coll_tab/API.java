package com.example.coll_tab;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class API {
    final static protected String SERVER_API = "https://sleepy-fortress-39163.herokuapp.com/api/";
    final static protected String LOGIN_URL = SERVER_API + "auth/";
    final static protected String CHILD_URL = SERVER_API + "childs/";
    final static protected String PARENT_URL = SERVER_API + "parents/";
    final static protected String STATS_URL = SERVER_API + "mathstats/";
    final static protected String NOTES_URL = SERVER_API + "notes/";
    final static protected String REGISTER_URL = SERVER_API + "users/parent";

    protected static RequestQueue queue;

    public static void childRequest(final VolleyCallback callback, String child_id) {
        queue = Volley.newRequestQueue((Context) callback);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, CHILD_URL + child_id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String responseString) {
//                        System.out.println(responseString);
                        callback.onSuccess(responseString);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(MainActivity.this, "not work"
//                        , Toast.LENGTH_LONG).show();
                Log.v("debug", "Error childRequest");
            }
        });
        queue.add(stringRequest);
    }

    public static void loginRequest(Context context, final String userId, final String password) {
        final VolleyCallback callback = (VolleyCallback) context;
        queue = Volley.newRequestQueue(context);
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("userId", userId);
            jsonBody.put("password", password);
        } catch (Exception ex) {

        }

        MetaRequest stringRequest = new MetaRequest(Request.Method.POST, LOGIN_URL,jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        System.out.println(response);
                        callback.onSuccess(response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError("");
                Log.v("debug", "Error loginRequest");
            }
        });
//        {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("userId", userId);
//                params.put("password", password);
//                return params;
//            }
//        };
        queue.add(stringRequest);
    }

    public static void parentRequest(Context context, final String parent_id) {
        final VolleyCallback callback = (VolleyCallback) context;
        queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest (Request.Method.GET, PARENT_URL + parent_id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                callback.onError("");
                Log.v("debug", "Error parentRequest");
            }
        });
        queue.add(stringRequest);
    }

    public static void updateParentRequest(Context context, final String parent_id, final String JWT, final String[] parentDetails) {
        final VolleyCallback callback = (VolleyCallback) context;
        queue = Volley.newRequestQueue(context);
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("firstName", parentDetails[0]);
            jsonBody.put("lastName", parentDetails[1]);
            jsonBody.put("id", parentDetails[2]);
            jsonBody.put("phone", parentDetails[3]);
            jsonBody.put("password", parentDetails[4]);
        } catch (Exception ex) {

        }

        JsonObjectRequest stringRequest = new JsonObjectRequest (Request.Method.PUT, PARENT_URL + parent_id, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess(response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError("");
                Log.v("debug", "Error loginRequest");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("X-Auth-Token", JWT);

                return params;
            }
        };
        queue.add(stringRequest);
    }

    public static void childPerformanceRequest(Context context, final String child_id) {
        final VolleyCallback callback = (VolleyCallback) context;
        queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest (Request.Method.GET, STATS_URL + child_id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        System.out.println(response);
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                callback.onError("");
                Log.v("debug", "Error parentRequest");
            }
        });
        queue.add(stringRequest);
    }

    public static void childNotesRequest(Context context, final String child_id) {
        final VolleyCallback callback = (VolleyCallback) context;
        queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest (Request.Method.GET, NOTES_URL + child_id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                callback.onError("");
                Log.v("debug", "Error childNotesRequest");
            }
        });
        queue.add(stringRequest);
    }

    public static void getParentsRequest(Context context) {
        final VolleyCallback callback = (VolleyCallback) context;
        queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest (Request.Method.GET, PARENT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        System.out.println(response);
//                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                callback.onError("");
                Log.v("debug", "Error getParentRequest");
            }
        });
        queue.add(stringRequest);
    }

    public static void registerRequest(Context context, final String[] details) {
        final VolleyCallback callback = (VolleyCallback) context;
        queue = Volley.newRequestQueue(context);
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("id", details[0]);
            jsonBody.put("password", details[1]);
            jsonBody.put("firstName", details[2]);
            jsonBody.put("lastName", details[3]);
            jsonBody.put("phone", details[4]);

        } catch (Exception ex) {

        }

        MetaRequest stringRequest = new MetaRequest(Request.Method.POST, REGISTER_URL, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        callback.onSuccess(response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError("");
                Log.v("debug", "Error loginRequest");
            }
        });
        queue.add(stringRequest);
    }
}


class MetaRequest extends JsonObjectRequest {

    public MetaRequest(int method, String url, JSONObject jsonRequest, Response.Listener
            <JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    public MetaRequest(String url, JSONObject jsonRequest, Response.Listener<JSONObject>
            listener, Response.ErrorListener errorListener) {
        super(url, jsonRequest, listener, errorListener);
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
            JSONObject jsonResponse = new JSONObject(jsonString);
//            jsonResponse.put("data", new JSONArray(jsonString));
            jsonResponse.put("headers", new JSONObject(response.headers));
            return Response.success(jsonResponse,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }
}