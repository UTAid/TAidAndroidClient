package com.shaimitchell.taid;

import android.util.Base64;
import android.util.Log;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Class to handle all the REST calls(PUT< GET, etc...)
 */
public class RestServices implements VariableChangeListener{

    private final String USERNAME = "test";
    private final String PASSWORD = "test";
    public  String mResponse = "";
    private VariableChangeListener variableChangeListener;

    private boolean isChanged = false;

    /**
     * Sends a GET request to the URL (url). On a successful response from the database, it saves
     * that response in a variable. On an error response, it also saves to a variable
     * @param requestQueue A volley request queue
     * @param url A String for the URL to request data from the database and saves the response to
     *            a variable
     */
    public void sendGetRequest(RequestQueue requestQueue, String url) {

        StringRequest request = new StringRequest(Request.Method.GET, url, listener,
                errorListener) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return createBasicAuthHeader(USERNAME, PASSWORD);
            }
        };

        requestQueue.add(request);
    }

    public Map<String, String> createBasicAuthHeader(String username, String password) {
        Map<String, String> headerMap = new HashMap<String, String>();

        String credentials = username + ":" + password;
        String base64EncodedCredentials =
                Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        headerMap.put("Authorization", "Basic " + base64EncodedCredentials);

        return headerMap;
    }

    Response.Listener<String> listener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.d("TEST","Successful Response: " + response.toString());
            setIsChanged();
            mResponse = response.toString();
            if (isChanged && variableChangeListener != null){
                variableChangeListener.onVariableChanged(isChanged);
            }

        }
    };

    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            if (error.networkResponse != null) {
                Log.d("TEST","Error Response code: " + error.networkResponse.statusCode);
                setIsChanged();
                mResponse = "Error Response code: " + error.networkResponse.statusCode;
            }
        }
    };

    @Override
    public void onVariableChanged(Boolean variableThatHasChanged) {
        if (variableThatHasChanged && variableChangeListener != null){
            variableChangeListener.onVariableChanged(isChanged);
        }

    }

    public void setVariableChangeListener(VariableChangeListener variableChangeListener) {
        this.variableChangeListener = variableChangeListener;
    }

    public void resetIsChanged(){
        if (isChanged){
            isChanged = false;
        }
    }

    public void setIsChanged(){
        isChanged = true;
    }
}
