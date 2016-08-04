package com.ymca.Activities;

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

import com.ymca.Adapters.DrawerAdapter;
import com.ymca.AppManager.DataManager;
import com.ymca.Constants.Constant;
import com.ymca.Fragments.CampFragment;
import com.ymca.Fragments.DonateFragment;
import com.ymca.Fragments.EventFragment;
import com.ymca.Fragments.FacilityFragment;
import com.ymca.Fragments.HomeFragment;
import com.ymca.Fragments.MyCardsFragment;
import com.ymca.Fragments.NotificationFragment;
import com.ymca.Fragments.ScheduleFragment;
import com.ymca.Fragments.SettingFragment;
import com.ymca.R;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {


    private EventFragment eventFragment = new EventFragment();
    private HomeFragment homeFragment = new HomeFragment();
    private NotificationFragment notificationFragment = new NotificationFragment();
    private MyCardsFragment myCardsFragment = new MyCardsFragment();
    private ScheduleFragment scheduleFragment = new ScheduleFragment();
    private FacilityFragment facilityFragment = new FacilityFragment();
    private CampFragment campFragment = new CampFragment();
    private boolean isCheck = false;
    private boolean doubleBackToExitPressedOnce = false;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        DataManager.getInstance().setAppCompatActivity(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

//        ImageView customButton = (ImageView)toolbar.findViewById(R.id.customButton);


        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
                .addToBackStack(getSupportFragmentManager().getClass().getName())
                .commit();
    }

//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        }
//
//        FragmentManager fm = getSupportFragmentManager();
//        int count = fm.getBackStackEntryCount();
//        Log.e("Count", String.valueOf(count));
//        Fragment fr = fm.findFragmentById(R.id.content_frame);
//        isCheck = DataManager.chkStatus();
//        if (isCheck) {
//            if (count > 1) {
//                if (fr.getTag().equals(Constant.dateFragment)) {
//                    super.onBackPressed();
//                } else if (fr.getTag().equals(Constant.classFragment)) {
//                    super.onBackPressed();
//                } else if (fr.getTag().equals(Constant.eventFragment)) {
//                    super.onBackPressed();
//                } else if (fr.getTag().equals(Constant.classDetailFragment)) {
////                    super.onBackPressed();
//                    fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                    getSupportFragmentManager()
//                            .beginTransaction()
//                            .replace(R.id.content_frame, scheduleFragment, Constant.scheduleFragment)
//                            .addToBackStack(getSupportFragmentManager().getClass().getName())
//                            .commit();
//                } else if (fr.getTag().equals(Constant.instructorDetailFrag)) {
//                    fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                    getSupportFragmentManager()
//                            .beginTransaction()
//                            .replace(R.id.content_frame, scheduleFragment, Constant.scheduleFragment)
//                            .addToBackStack(getSupportFragmentManager().getClass().getName())
//                            .commit();
//                } else if (fr.getTag().equals(Constant.scheduleFragment)) {
//                    getSupportFragmentManager()
//                            .beginTransaction()
//                            .replace(R.id.content_frame, homeFragment, Constant.homeFragment)
//                            .commit();
//                } else if (fr.getTag().equals(Constant.homeClassFragment)) {
////                    fm.popBackStack(2, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                    super.onBackPressed();
//                } else if (fr.getTag().equals(Constant.homeClassDetailFragment)) {
////                    fm.popBackStack(2, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                    super.onBackPressed();
//                } else if (fr.getTag().equals(Constant.cardShowFragment)) {
//                    getSupportFragmentManager()
//                            .beginTransaction()
//                            .replace(R.id.content_frame, myCardsFragment, Constant.myCardFragment)
//                            .commit();
//                } else if (fr.getTag().equals(Constant.myCardFragment)) {
//                    getSupportFragmentManager()
//                            .beginTransaction()
//                            .replace(R.id.content_frame, homeFragment, Constant.homeFragment)
//                            .commit();
//                } else if (fr.getTag().contains(Constant.homeFragment)) {
//                    if (doubleBackToExitPressedOnce) {
//                        super.onBackPressed();
//                        HomeActivity.this.finish();
//                        return;
//                    }
//                    this.doubleBackToExitPressedOnce = true;
//                    Toast.makeText(this, "Please click Back again to exit", Toast.LENGTH_SHORT).show();
//
//                    new Handler().postDelayed(new Runnable() {
//
//                        @Override
//                        public void run() {
//                            doubleBackToExitPressedOnce = false;
//                        }
//                    }, 2000);
//                } else {
//                    super.onBackPressed();
//                }
//            } else {
//                if (fr.getTag().contains(Constant.homeFragment)) {
//                    if (doubleBackToExitPressedOnce) {
//                        super.onBackPressed();
//                        HomeActivity.this.finish();
//                        return;
//                    }
//                    this.doubleBackToExitPressedOnce = true;
//                    Toast.makeText(this, "Please click Back again to exit", Toast.LENGTH_SHORT).show();
//
//                    new Handler().postDelayed(new Runnable() {
//
//                        @Override
//                        public void run() {
//                            doubleBackToExitPressedOnce = false;
//                        }
//                    }, 2000);
//                } else {
//                    getSupportFragmentManager()
//                            .beginTransaction()
//                            .replace(R.id.content_frame, homeFragment, Constant.homeFragment)
//                            .commit();
//                }
//
//
//            }
//        }
//
//
//    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        FragmentManager fm = getSupportFragmentManager();
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
                } else if (fr.getTag().equals(Constant.classDetailFragment)) {
                    super.onBackPressed();
                } else if (fr.getTag().equals(Constant.instructorDetailFrag)) {
                    super.onBackPressed();
                } else if (fr.getTag().equals(Constant.scheduleFragment)) {
                    super.onBackPressed();
                } else if (fr.getTag().equals(Constant.homeClassFragment)) {
                    super.onBackPressed();
                } else if (fr.getTag().equals(Constant.homeClassDetailFragment)) {
                    super.onBackPressed();
                } else if (fr.getTag().equals(Constant.cardShowFragment)) {

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame, myCardsFragment, Constant.myCardFragment)
                            .commit();
                } else if (fr.getTag().equals(Constant.myCardFragment)) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame, homeFragment, Constant.homeFragment)
                            .commit();
                } else if (fr.getTag().contains(Constant.homeFragment)) {
                    if (doubleBackToExitPressedOnce) {
                        super.onBackPressed();
                        HomeActivity.this.finish();
                        return;
                    } else {
                        this.doubleBackToExitPressedOnce = true;
                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                doubleBackToExitPressedOnce = false;
                            }
                        }, 2000);
                        Toast.makeText(this, "Please click Back again to exit", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    super.onBackPressed();
                }
            } else {
                if (fr.getTag().contains(Constant.homeFragment)) {
                    if (doubleBackToExitPressedOnce) {
                        super.onBackPressed();
                        HomeActivity.this.finish();
                        return;
                    } else {
                        this.doubleBackToExitPressedOnce = true;
                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                doubleBackToExitPressedOnce = false;
                            }
                        }, 2000);
                        Toast.makeText(this, "Please click Back again to exit", Toast.LENGTH_SHORT).show();
                    }
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
        isCheck = DataManager.chkStatus();
        if (isCheck) {
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
                    DataManager.getInstance().setFlagCheckIn(true);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame, myCardsFragment, Constant.myCardFragment)
                            .commit();
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
                    DataManager.getInstance().showIFramePopUp(this);
                    break;
                case 6:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame, facilityFragment, Constant.facilityFragment)
                            .commit();
                    break;
                case 7:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame, campFragment, Constant.campFragment)
                            .commit();
                    break;
                case 8:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame, eventFragment, Constant.eventFragment)
                            .commit();
                    break;
                case 9:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame, new DonateFragment(), Constant.donateFragment)
                            .commit();
                    break;
                case 10:

                    break;
                case 11:
                    break;
                case 12:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame, new SettingFragment(), Constant.settingFragment)
                            .commit();
                    break;

            }
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }


}
