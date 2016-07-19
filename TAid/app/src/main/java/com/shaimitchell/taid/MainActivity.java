package com.shaimitchell.taid;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.shaimitchell.taid.Enums.FRAG_TYPE;
import com.shaimitchell.taid.Fragments.MultiEntryFragment;
import com.shaimitchell.taid.LocalDb.DbAdapter;
import com.shaimitchell.taid.Models.Student;

import java.net.InetAddress;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MultiEntryFragment.OnFragmentInteractionListener {

    private RestServices mRestServices = new RestServices();
    private String protocol = "http://";
    private String domain = "192.168.0.13";
    private String port = ":8001/";
    private String path;
    private String url;
    Context context;
    TextView mTextView;
    RequestQueue mRequestQueue;
    FragmentManager fragmentManager;
    DbAdapter dbAdapter;

    private boolean varToChange = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTextView = (TextView) findViewById(R.id.text_view);
        mRequestQueue = RequestQueueSingleton.getInstance(this.getApplicationContext()).getRequestQueue();

        dbAdapter = new DbAdapter(this);

        context = this;


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        if (drawer != null) {
            drawer.setDrawerListener(toggle);
//            drawer.addDrawerListener(toggle);
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

        mTextView.setVisibility(View.GONE);
        Fragment frag = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        // Removes all fragments in the fragment container
        if (frag != null) {
            getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentById(R.id.fragment_container)).commit();
        }

        if (id == R.id.all_students) {
            path = "api/v0/students/?format=json";
            url = buildURL(protocol, domain, port, path);
            Log.i("check", "step 1");
            if (isConnected(this)) {
                if (mRequestQueue != null) {
                    mRestServices.sendGetRequest(mRequestQueue, url);
                    mRestServices.setVariableChangeListener(new VariableChangeListener() {
                        // this is where we need to check . this is all wrong, need to redo!
                        @Override
                        public void onVariableChanged(Boolean variableThatHasChanged) {
                            if (variableThatHasChanged && mTextView != null) {
                                String resp = mRestServices.mResponse;
                                MultiEntryFragment studentFragment = new MultiEntryFragment().newInstance(resp, FRAG_TYPE.STUDENT, null);
                                fragmentManager = getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.add(R.id.fragment_container, studentFragment, "HELLO");
                                fragmentTransaction.commit();
                            }
//                            else if (!isInternetAvailable()) {
//                                Log.i("check", "please work");
//                                Cursor cursor = dbAdapter.getAllStudents();
//                                MultiEntryFragment studentFragment = new MultiEntryFragment().newInstance("", FRAG_TYPE.STUDENT, cursor);
//                                fragmentManager = getSupportFragmentManager();
//                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                                fragmentTransaction.add(R.id.fragment_container, studentFragment, "HELLO");
//                                fragmentTransaction.commit();
//                            }
                        }
                    });
                }
            }else{
                Log.i("check", "please work");
                Cursor cursor = dbAdapter.getAllStudents();
                MultiEntryFragment studentFragment = new MultiEntryFragment().newInstance("", FRAG_TYPE.STUDENT, cursor);
                fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.fragment_container, studentFragment, "HELLO");
                fragmentTransaction.commit();
            }
        } else if (id == R.id.all_instructors) {
            path = "api/v0/instructors/?format=json";
            url = buildURL(protocol, domain, port, path);
            if (mRequestQueue != null) {
                mRestServices.sendGetRequest(mRequestQueue, url);
                mRestServices.setVariableChangeListener(new VariableChangeListener() {

                    @Override
                    public void onVariableChanged(Boolean variableThatHasChanged) {
                        if (variableThatHasChanged && mTextView != null) {
                            String resp = mRestServices.mResponse;
                            MultiEntryFragment instructorFragment = new MultiEntryFragment().newInstance(resp, FRAG_TYPE.INSTRUCTOR, null);
                            fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.add(R.id.fragment_container, instructorFragment, "HELLO");
                            fragmentTransaction.commit();
                        }
                    }
                });
            }

        } else if (id == R.id.all_teaching_assistants) {
            path = "api/v0/teaching_assistants/?format=json";
            url = buildURL(protocol, domain, port, path);
            if (mRequestQueue != null) {
                mRestServices.sendGetRequest(mRequestQueue, url);
                mRestServices.setVariableChangeListener(new VariableChangeListener() {

                    @Override
                    public void onVariableChanged(Boolean variableThatHasChanged) {
                        if (variableThatHasChanged && mTextView != null) {
                            String resp = mRestServices.mResponse;
                            MultiEntryFragment teachingAssistantFragment = new MultiEntryFragment().newInstance(resp, FRAG_TYPE.TEACHING_ASSISTANT, null);
                            fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.add(R.id.fragment_container, teachingAssistantFragment, "HELLO");
                            fragmentTransaction.commit();
                        }
                    }
                });
            }

        } else if (id == R.id.all_tutorials) {
            path = "api/v0/tutorials/?format=json";
            url = buildURL(protocol, domain, port, path);
            if (mRequestQueue != null) {
                mRestServices.sendGetRequest(mRequestQueue, url);
                mRestServices.setVariableChangeListener(new VariableChangeListener() {

                    @Override
                    public void onVariableChanged(Boolean variableThatHasChanged) {
                        if (variableThatHasChanged && mTextView != null) {
                            String resp = mRestServices.mResponse;
                            MultiEntryFragment tutorialFragment = new MultiEntryFragment().newInstance(resp, FRAG_TYPE.TUTORIAL, null);
                            fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.add(R.id.fragment_container, tutorialFragment, "HELLO");
                            fragmentTransaction.commit();
                        }
                    }
                });
            }

        } else if (id == R.id.all_practicals) {
            path = "api/v0/practicals/?format=json";
            url = buildURL(protocol, domain, port, path);
            if (mRequestQueue != null) {
                mRestServices.sendGetRequest(mRequestQueue, url);
                mRestServices.setVariableChangeListener(new VariableChangeListener() {

                    @Override
                    public void onVariableChanged(Boolean variableThatHasChanged) {
                        if (variableThatHasChanged && mTextView != null) {
                            String resp = mRestServices.mResponse;
                            MultiEntryFragment practicalFragment = new MultiEntryFragment().newInstance(resp, FRAG_TYPE.PRACTICAL, null);
                            fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.add(R.id.fragment_container, practicalFragment, "HELLO");
                            fragmentTransaction.commit();
                        }
                    }
                });
            }

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public String buildURL(String protocol, String domain, String port, String path){
        return protocol+domain+port+path;
    }


    // returns true if the device is connected to the internet
    private boolean isNetworkAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;

    }

    public static boolean isConnected(Context context) {
        ConnectivityManager
                cm = (ConnectivityManager) context.getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
    }


    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com"); //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }

    }
    @Override
    public void onFragmentInteraction(boolean bool) {

    }


}

