package com.shaimitchell.taid;

import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shaimitchell on 16-06-27.
 */
public class BasicAuth implements VariableChangeListener{

    private final String USERNAME = "test";
    private final String PASSWORD = "test";
    public String mResponse = "";
    public VariableChangeListener variableChangeListener;

    public boolean isChanged = false;

    public void send(RequestQueue requestQueue) {

//        Uri.Builder uri = new Uri.Builder();
//        uri.scheme("https");
//        uri.authority("192.168.2.20:8000");
//        uri.path("api/v0/students/?format=json");

        // URL to hit to get info for students
//        String url = "http://192.168.2.20:8000/api/v0/students/?format=json";
        String url = "http://142.1.90.52:8000/api/v0/students/?format=json";
        StringRequest request = new StringRequest(Request.Method.GET, url, listener,
                errorListener) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return createBasicAuthHeader(USERNAME, PASSWORD);
            }
        };

        requestQueue.add(request);
    }

    Map<String, String> createBasicAuthHeader(String username, String password) {
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
            Log.d("SHAI","Success Response: " + response.toString());
            resetIsChanged();
            mResponse = response.toString();
            setIsChanged();
        }
    };

    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            if (error.networkResponse != null) {
                Log.d("SHAI","Error Response code: " + error.networkResponse.statusCode);
                resetIsChanged();
                mResponse = "Error Response code: " + error.networkResponse.statusCode;
                setIsChanged();
            }

        }
    };


    @Override
    public void onVariableChanged(Boolean variableThatHasChanged) {
        if (variableThatHasChanged){

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
