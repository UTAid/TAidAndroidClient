package com.shaimitchell.taid;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import com.android.volley.RequestQueue;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RestServices mRestServices = new RestServices();
    private String protocol = "http://";
    private String domain = "192.168.2.20";
    private String port = ":8000/";
    private String path;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // TextView that holds the data returned from the database
        final TextView mTextView = (TextView) findViewById(R.id.text_view);

        // Instantiate the RequestQueue
        final RequestQueue mRequestQueue = RequestQueueSingleton.getInstance(this.getApplicationContext()).getRequestQueue();

        final Button allStudentsButton = (Button) findViewById(R.id.all_students_button);
        final Button oneStudentButton = (Button) findViewById(R.id.one_student_button);


        if (allStudentsButton != null) {
            allStudentsButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    path = "api/v0/students/?format=json";
                    url = buildURL(protocol, domain, port, path);
                    if (mRequestQueue != null) {
                        mRestServices.sendGetRequest(mRequestQueue, url);
                        mRestServices.setVariableChangeListener(new VariableChangeListener() {

                            @Override
                            public void onVariableChanged(Boolean variableThatHasChanged) {
                                if (variableThatHasChanged && mTextView != null) {
                                    mTextView.setText(mRestServices.mResponse);
                                    mRestServices.resetIsChanged();
                                }
                            }
                        });
                    }
                }
            });
        }

        if (oneStudentButton != null) {
            oneStudentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    oneStudentButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            path = "api/v0/students/testt123";
                            url = buildURL(protocol, domain, port, path);
                            if (mRequestQueue != null) {
                                mRestServices.sendGetRequest(mRequestQueue, url);
                                mRestServices.setVariableChangeListener(new VariableChangeListener() {

                                    @Override
                                    public void onVariableChanged(Boolean variableThatHasChanged) {
                                        if (variableThatHasChanged && mTextView != null) {
                                            mTextView.setText(mRestServices.mResponse);
                                            mRestServices.resetIsChanged();
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            });
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        if (drawer != null) {
            drawer.setDrawerListener(toggle);
        }
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public String buildURL(String protocol, String domain, String port, String path){
        return protocol+domain+port+path;
    }
}

