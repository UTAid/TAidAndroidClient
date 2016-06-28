package com.shaimitchell.taid;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.shaimitchell.taid.BasicAuth;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private BasicAuth mBasicAuth = new BasicAuth();
    public String resp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Instantiate the RequestQueue.
        final String url = "http://192.168.2.20:8000/api/v0/students/?format=json";
        final String url2 = "http://192.168.2.20:8000/api-auth/login";

        final TextView mTextView = (TextView)findViewById(R.id.text_view);
        final WebView mWebview = (WebView) findViewById(R.id.mWebview);
        WebSettings ws = mWebview.getSettings();

        final RequestQueue queue = MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();

        //Allows urls to load within webview
        mWebview.setWebViewClient(new WebViewClient());
        ws.setJavaScriptEnabled(true);

        final Button button = (Button) findViewById(R.id.button);
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    mBasicAuth.send(queue);
                    mBasicAuth.setVariableChangeListener(new VariableChangeListener() {
                        @Override
                        public void onVariableChanged(Boolean variableThatHasChanged) {
                            if (variableThatHasChanged){
                                mTextView.setText(mBasicAuth.mResponse);
                            }

                        }
                    });


                }
            });
        }

        final Button button2 = (Button) findViewById(R.id.button2);
        if (button2 != null) {
            button2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    mWebview.setVisibility(View.VISIBLE);
                    mWebview.loadUrl(url);
                }
            });
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void displayRequest(String url, final TextView mTextview){
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        mTextview.setText("Response: " + response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mTextview.setText(error.toString());

                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsObjRequest);
    }
}
