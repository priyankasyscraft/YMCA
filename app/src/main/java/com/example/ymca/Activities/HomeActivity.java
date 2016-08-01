package com.example.ymca.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ymca.Adapters.DrawerAdapter;
import com.example.ymca.AppManager.DataManager;
import com.example.ymca.Constants.Constant;
import com.example.ymca.Fragments.EventFragment;
import com.example.ymca.Fragments.HomeFragment;
import com.example.ymca.Fragments.NotificationFragment;
import com.example.ymca.R;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    boolean mSlideState = false;
    private EventFragment eventFragment = new EventFragment();
    private HomeFragment homeFragment = new HomeFragment();
    private boolean isCheck = false;
    private boolean doubleBackToExitPressedOnce = false;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private ListView mDrawerList;
    int[] navDrawerImg = {1,
            R.mipmap.nav_home,
            R.mipmap.nav_check_id,
            2,
            R.mipmap.nav_announcment,
            R.mipmap.nav_prog,
            R.mipmap.nav_facility,
            R.mipmap.nav_camp,
            R.mipmap.nav_event,
            R.mipmap.nav_donate,
            R.mipmap.nav_contact,
            3,
            R.mipmap.nav_settings,
    };
    int[] colors = new int[]{
            Color.parseColor("#AAAAAA"),
            Color.WHITE,
            Color.WHITE,

            Color.parseColor("#AAAAAA"),
            Color.WHITE,
            Color.WHITE,
            Color.WHITE,
            Color.WHITE,
            Color.WHITE,
            Color.WHITE,
            Color.WHITE,

            Color.parseColor("#AAAAAA"),
            Color.WHITE,
    };
    private NotificationFragment notificationFragment = new NotificationFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        DataManager.getInstance().setAppCompatActivity(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

//        ImageView customButton = (ImageView)toolbar.findViewById(R.id.customButton);


        setSupportActionBar(toolbar);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);

        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        mDrawerList.setAdapter(new DrawerAdapter(getApplicationContext(),
                getResources().getStringArray(R.array.drawerText), navDrawerImg, colors));

        mDrawerList.setOnItemClickListener(this);
        toggle.syncState();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content_frame, homeFragment, Constant.homeFragment)
                .addToBackStack(getFragmentManager().getClass().getName())
                .commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        fm = getSupportFragmentManager();
        int count = fm.getBackStackEntryCount();
        Log.e("Count", String.valueOf(count));
        Fragment fr = fm.findFragmentById(R.id.content_frame);
        isCheck = DataManager.chkStatus();
        if (isCheck) {
            if (count > 1) {
                if (fr.getTag().equals(Constant.dateFragment)) {
                    super.onBackPressed();
                } else if (fr.getTag().equals(Constant.classFragment)) {
                    super.onBackPressed();
                } else if (fr.getTag().equals(Constant.eventFragment)) {
                    super.onBackPressed();
                }
            } else {
                if (fr.getTag().contains(Constant.homeFragment)) {
                    if (doubleBackToExitPressedOnce) {
                        super.onBackPressed();
                        HomeActivity.this.finish();
                        return;
                    }
                    this.doubleBackToExitPressedOnce = true;
                    Toast.makeText(this, "Please click Back again to exit", Toast.LENGTH_SHORT).show();

                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            doubleBackToExitPressedOnce = false;
                        }
                    }, 2000);
                } else {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame, homeFragment, Constant.homeFragment)
                            .commit();
                }


            }
        }


    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        switch (position) {
            case 0:
                break;
            case 1:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, homeFragment, Constant.homeFragment)
                        .commit();
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, notificationFragment, Constant.notificationFragment)
                        .commit();
                break;
            case 5:
                break;
            case 6:

                break;
            case 7:

                break;
            case 8:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, eventFragment, Constant.eventFragment)
                        .commit();
                break;
            case 9:
                break;
            case 10:
                break;
            case 11:
                break;
            case 12:
                break;

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }


}
