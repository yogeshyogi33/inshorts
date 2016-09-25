package com.example.junaid.inshorts;

import android.app.FragmentTransaction;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.junaid.inshorts.adapters.ScreenSlidePagerAdapter;
import com.example.junaid.inshorts.apiclients.Service;
import com.example.junaid.inshorts.helpers.DbHelper;
import com.example.junaid.inshorts.helpers.ImageHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

//import fr.castorflex.android.verticalviewpager.VerticalViewPager;

public class MainActivity extends AppCompatActivity implements Service.Result,
        SlidingFragment.AppData,
        NavigationView.OnNavigationItemSelectedListener,
        GestureDetector.OnGestureListener {

    private JSONArray mJsonArray ;
    private List<Card> mCards;
    private Handler mHandler = new Handler();
    DbHelper mDbHelper;
    NavigationView navigationView;
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Service service = new Service(this,this);
        service.callApi();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.getBackground().setAlpha(150);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void result(String response) {
        JSONObject jObject = null;
        String body = "";
        String imageUrl = "";
        response    =   getApplicationContext().getResources().getString(R.string.response);
        try {
            jObject = new JSONObject(response);

            mJsonArray = jObject.getJSONArray("cards");

            mDbHelper = new DbHelper(this);
            DbHelper dbHelper = new DbHelper(this);
            mCards = dbHelper.getCards(mJsonArray);

            ScreenSlidePagerAdapter screenSlidePagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
//            ((ViewPager) findViewById(R.id.pager)).setAdapter(screenSlidePagerAdapter);

            VerticalViewPager verticalViewPager = (VerticalViewPager) findViewById(R.id.pager);
            verticalViewPager.setAdapter(screenSlidePagerAdapter);

            ImageHelper.getInstance().init(this);
//            ImageHelper.getInstance().displayImage(imageUrl, (ImageView) findViewById(R.id.image));
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        ((TextView) findViewById(R.id.text)).setText(body);
    }

    @Override
    public JSONObject getData(int position) {
        String body = "";
        String imageUrl = "";
        String hdline;
        String reportedBy;
        String moreAt;
        JSONObject jsonObject = new JSONObject();
        try {
            body = mCards.get(position).body;
            imageUrl = mCards.get(position).imageUrl;
            hdline = mCards.get(position).shortLine;
            reportedBy = mCards.get(position).reportedBy;
            moreAt = mCards.get(position).moreAt;

            jsonObject.put("image_url", imageUrl);
            jsonObject.put("body", body);
            jsonObject.put("shortLine", hdline);
            jsonObject.put("reportby", reportedBy);
            jsonObject.put("more", moreAt);

            /*
            hdline = jsonObject.getString("shortLine");
            reportedBy = jsonObject.getString("reportby");
            moreAt = jsonObject.getString("more");

             */

        } catch (Exception e){

        }
        return jsonObject;
    }

    //side navigation menu items are handled below
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        final int id = item.getItemId();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (id) {
                    case R.id.nav_attendance:
                        //write what you want to do
                        break;
                    case R.id.nav_timetable:

                        break;


                    case R.id.nav_exit:
                        finish();
                        break;
                }
            }

        }, 300);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //toolbar menu items are handled below
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }


    //gestures

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Toast.makeText(this,"pressed",Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        if(!getSupportActionBar().isShowing()){
            getSupportActionBar().show();
        }else{
            getSupportActionBar().hide();
        }
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        getSupportActionBar().hide();
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        getSupportActionBar().hide();
        return true;
    }
}
