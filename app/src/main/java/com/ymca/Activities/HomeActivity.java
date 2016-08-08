package com.ymca.Activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
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

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.iid.FirebaseInstanceId;
import com.ymca.Adapters.DrawerAdapter;
import com.ymca.AppManager.DataManager;
import com.ymca.AppManager.SharedPreference;
import com.ymca.Constants.Constant;
import com.ymca.Fragments.CampFragment;
import com.ymca.Fragments.DonateFragment;
import com.ymca.Fragments.EventCalenderFragment;
import com.ymca.Fragments.EventFragment;
import com.ymca.Fragments.FacilityFragment;
import com.ymca.Fragments.HomeFragment;
import com.ymca.Fragments.LocationFragment;
import com.ymca.Fragments.MyCardsFragment;
import com.ymca.Fragments.NotificationFragment;
import com.ymca.Fragments.ScheduleFragment;
import com.ymca.Fragments.SettingFragment;
import com.ymca.ModelClass.DrawerModel;
import com.ymca.PullListLoader.XListView;
import com.ymca.R;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {


    // TODO: 08-Aug-16 Left Menu All Fragments
    private EventFragment eventFragment = new EventFragment();
    private HomeFragment homeFragment = new HomeFragment();
    private NotificationFragment notificationFragment = new NotificationFragment();
    private MyCardsFragment myCardsFragment = new MyCardsFragment();
    private ScheduleFragment scheduleFragment = new ScheduleFragment();
    private FacilityFragment facilityFragment = new FacilityFragment();
    private LocationFragment locationFragment = new LocationFragment();
    private CampFragment campFragment = new CampFragment();
    private SettingFragment settingFragment = new SettingFragment();
    private EventCalenderFragment eventCalenderFragment = new EventCalenderFragment();
    private DonateFragment donateFragment = new DonateFragment();

    private boolean isCheck = false;
    private boolean doubleBackToExitPressedOnce = false;
    private ListView mDrawerList;
    private ArrayList<DrawerModel> drawerModels = new ArrayList<>();
    private DrawerAdapter drawerAdapter;
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation;
    private String TAG = "Home Activity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setData();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//        Log.e("refreshedToken: ", refreshedToken );

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);


        mDrawerList.setOnItemClickListener(this);
        toggle.syncState();
        createLocationRequest();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content_frame, homeFragment, Constant.homeFragment)
                .addToBackStack(getSupportFragmentManager().getClass().getName())
                .commit();
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void setData() {
        int[] navDrawerImg = {
                1,
                R.mipmap.nav_home,
                R.mipmap.nav_check_id,
                R.mipmap.nav_announcment,
                R.mipmap.nav_prog,
                R.mipmap.nav_facility,
                2,
                R.mipmap.nav_event,
                R.mipmap.nav_donate,
                R.mipmap.nav_contact,
                R.mipmap.nav_contact,
                R.mipmap.nav_contact,
                R.mipmap.nav_contact,
                3,
                R.mipmap.nav_settings,
                R.mipmap.nav_settings,
                R.mipmap.nav_settings,
        };

        int[] navTextBfImg = {
                R.drawable.bg_leftmenu_heading,
                0,
                0,
                0,
                0,
                0,
                R.drawable.bg_leftmenu_heading,
                0,
                0,
                0,
                0,
                0,
                0,
                R.drawable.bg_leftmenu_heading,
                0,
                0,
                0,
        };
        int[] colors = new int[]{
                Color.parseColor("#AAAAAA"),
                Color.WHITE,
                Color.WHITE,
                Color.WHITE,
                Color.WHITE,
                Color.WHITE,
                Color.parseColor("#AAAAAA"),
                Color.WHITE,
                Color.WHITE,
                Color.WHITE,
                Color.WHITE,
                Color.WHITE,
                Color.WHITE,

                Color.parseColor("#AAAAAA"),
                Color.WHITE,
                Color.WHITE,
                Color.WHITE,
        };

        String[] name = {
                getResources().getString(R.string.membership_info),
                getResources().getString(R.string.home),
                getResources().getString(R.string.check_in),
                getResources().getString(R.string.location),
                getResources().getString(R.string.announcement),
                getResources().getString(R.string.setting),
                getResources().getString(R.string.progrm_activity),
                getResources().getString(R.string.schedule),
                getResources().getString(R.string.program_register),
                getResources().getString(R.string.clas_activity),
                getResources().getString(R.string.camp),
                getResources().getString(R.string.sports),
                getResources().getString(R.string.event_caln),
                getResources().getString(R.string.spiring_field),
                getResources().getString(R.string.donate),
                getResources().getString(R.string.sponsers),
                getResources().getString(R.string.about_y),
        };

        for (int i = 0; i < colors.length; i++) {
            DrawerModel drawerModel = new DrawerModel();
            drawerModel.setMenuName(name[i]);
            drawerModel.setMenuColor(colors[i]);
            drawerModel.setMenuImg(navDrawerImg[i]);
            drawerModel.setMenuTextBg(navTextBfImg[i]);
            drawerModels.add(drawerModel);
        }
        drawerAdapter = new DrawerAdapter(getApplicationContext(), drawerModels);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList.setAdapter(drawerAdapter);
    }

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
//                        super.onBackPressed();
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
//                    getSupportFragmentManager()
//                            .beginTransaction()
//                            .replace(R.id.content_frame, new EventCalenderFragment(), Constant.eventCalenderFragment)
//                            .commit();
                    DataManager.getInstance().setFlagLocation(true);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame, locationFragment, Constant.locationFramgnet)
                            .commit();
                    break;
                case 4:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame, notificationFragment, Constant.notificationFragment)
                            .commit();
                    break;
                case 5:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame, settingFragment, Constant.settingFragment)
                            .commit();
                    break;
                case 6:
//                    getSupportFragmentManager()
//                            .beginTransaction()
//                            .replace(R.id.content_frame, facilityFragment, Constant.facilityFragment)
//                            .commit();
                    break;
                case 7:
                    DataManager.getInstance().setFlagScedule(true);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame, scheduleFragment, Constant.scheduleFragment)
                            .commit();
                    break;
                case 8:

                    DataManager.getInstance().showIFramePopUp(this);
//                    getSupportFragmentManager()
//                            .beginTransaction()
//                            .replace(R.id.content_frame, eventFragment, Constant.eventFragment)
//                            .commit();
                    break;
                case 9:

                    DataManager.getInstance().showIFramePopUp(this);
//                    getSupportFragmentManager()
//                            .beginTransaction()
//                            .replace(R.id.content_frame, new DonateFragment(), Constant.donateFragment)
//                            .commit();
                    break;
                case 10:

                    DataManager.getInstance().showIFramePopUp(this);
                    break;
                case 11:

                    DataManager.getInstance().showIFramePopUp(this);
                    break;
                case 12:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame, eventCalenderFragment, Constant.eventCalenderFragment)
                            .commit();
                    break;
                case 13:

                    break;
                case 14:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame, donateFragment, Constant.donateFragment)
                            .commit();
                    break;
                case 15:

                    DataManager.getInstance().showIFramePopUp(this);
                    break;
                case 16:

                    DataManager.getInstance().showIFramePopUp(this);
                    break;

            }
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.e(TAG, "onStart fired ..............");
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        startLocationUpdates();
    }

    protected void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
        Log.e(TAG, "Location update started ..............: ");
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(TAG, "Connection failed: " + connectionResult.toString());
    }


    @Override
    public void onLocationChanged(Location location) {
        Log.e(TAG, "Firing onLocationChanged..............................................");
        mCurrentLocation = location;

    }

}
