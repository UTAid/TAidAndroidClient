package com.shaimitchell.taid;

import android.content.Context;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.shaimitchell.taid.Enums.FRAG_TYPE;
import com.shaimitchell.taid.Fragments.MultiEntryFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MultiEntryFragment.OnFragmentInteractionListener {

    private RestServices mRestServices = new RestServices();
    private String protocol = "http://";
    private String domain = "192.168.2.20";
    private String port = ":8000/";
    private String path;
    private String url;
    Context context;
    private VariableChangeListener variableChangeListener;
    private boolean varToChange = false;

    // TextView that holds the data returned from the database
    TextView mTextView;

    // Instantiate the RequestQueue
    RequestQueue mRequestQueue;

    FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTextView = (TextView) findViewById(R.id.text_view);
        mRequestQueue = RequestQueueSingleton.getInstance(this.getApplicationContext()).getRequestQueue();

        context = this;


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

        mTextView.setVisibility(View.GONE);
        Fragment frag = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        // Removes all fragments in the fragment container
        if (frag != null) {
            getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentById(R.id.fragment_container)).commit();
        }

        if (id == R.id.all_students) {
            path = "api/v0/students/?format=json";
            url = buildURL(protocol, domain, port, path);
            if (mRequestQueue != null) {
                mRestServices.sendGetRequest(mRequestQueue, url);
                mRestServices.setVariableChangeListener(new VariableChangeListener() {

                    @Override
                    public void onVariableChanged(Boolean variableThatHasChanged) {
                        if (variableThatHasChanged && mTextView != null) {
                            String resp = mRestServices.mResponse;
                            MultiEntryFragment studentFragment = new MultiEntryFragment().newInstance(resp, FRAG_TYPE.STUDENT);
                            fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.add(R.id.fragment_container, studentFragment, "HELLO");
                            fragmentTransaction.commit();
                        }
                    }
                });
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
                            MultiEntryFragment instructorFragment = new MultiEntryFragment().newInstance(resp, FRAG_TYPE.INSTRUCTOR);
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
                            MultiEntryFragment teachingAssistantFragment = new MultiEntryFragment().newInstance(resp, FRAG_TYPE.TEACHING_ASSISTANT);
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
                            MultiEntryFragment tutorialFragment = new MultiEntryFragment().newInstance(resp, FRAG_TYPE.TUTORIAL);
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
                            MultiEntryFragment practicalFragment = new MultiEntryFragment().newInstance(resp, FRAG_TYPE.PRACTICAL);
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

    @Override
    public void onFragmentInteraction(boolean bool) {

    }
}

