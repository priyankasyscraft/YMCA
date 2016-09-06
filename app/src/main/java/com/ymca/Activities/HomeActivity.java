package com.ymca.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.firebase.iid.FirebaseInstanceId;
import com.ymca.Adapters.DrawerAdapter;
import com.ymca.AppManager.DataManager;
import com.ymca.AppManager.SharedPreference;
import com.ymca.Constants.Constant;
import com.ymca.Fragments.AddCardFragment;
import com.ymca.Fragments.CampFragment;
import com.ymca.Fragments.CardShowFragment;
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
import com.ymca.Fragments.TrainerDetailFragment;
import com.ymca.Fragments.TrainerFragment;
import com.ymca.Fragments.WebViewFragment;
import com.ymca.ModelClass.DrawerModel;
import com.ymca.R;
import com.ymca.UserInterFace.RefreshDataListener;
import com.ymca.UserInterFace.Refreshable;
import com.ymca.WebManager.JsonCaller;

import org.json.JSONException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, RefreshDataListener {


    // TODO: 08-Aug-16 Left Menu All Fragments
    private HomeFragment homeFragment = new HomeFragment();
    private NotificationFragment notificationFragment = new NotificationFragment();
    private MyCardsFragment myCardsFragment = new MyCardsFragment();
    private ScheduleFragment scheduleFragment = new ScheduleFragment();
    private LocationFragment locationFragment = new LocationFragment();
    private SettingFragment settingFragment = new SettingFragment();
    private EventCalenderFragment eventCalenderFragment = new EventCalenderFragment();
    private CardShowFragment cardShowFragment = new CardShowFragment();
    private TrainerFragment trainerFragment = new TrainerFragment();


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
    private int WEBVIEW_REQUEST_CODE = 1800;
    private File casted_image;
    private Bitmap bitamp;
    private String locationid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        setData();
        JsonCaller.getInstance().setRefreshDataListener(this);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e("refreshedToken: ", "device toke" + " " + refreshedToken);

        displayLocationSettingsRequest(getApplicationContext(), Constant.locationRequestInt);

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
        if (getIntent().getExtras() != null) {
            Bundle extras = getIntent().getExtras();
            String newString = extras.getString("noti");
            DataManager.getInstance().setFlagNotification(true);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame, notificationFragment, Constant.notificationFragment)
                    .commit();
        }else if(DataManager.getInstance().isFlagMyCardBack()){
//            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            DataManager.getInstance().setFlagCheckIn(true);
            DataManager.getInstance().setFlagMyCardBack(false);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame, myCardsFragment, Constant.myCardFragment)
                    .addToBackStack(getSupportFragmentManager().getClass().getName())
                    .commit();
        }
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void setData() {
//        printKeyHash(HomeActivity.this);
        int[] navDrawerImg = {
                1,
                R.mipmap.nav_home,
                R.mipmap.nav_check_id,
                R.mipmap.nav_location,
                R.mipmap.nav_announcemnet,
                R.mipmap.nav_settings,
                2,
                R.mipmap.nav_schedule,
                R.mipmap.nav_prog,
                R.mipmap.nav_class_activ,
                R.mipmap.nav_trainer,
                R.mipmap.nav_camp,
                R.mipmap.nav_sports_league,
                R.mipmap.nav_event_calender,
                3,
                R.mipmap.nav_donate,
                R.mipmap.nav_partners,
                R.mipmap.nav_about_y,
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
                getResources().getString(R.string.trainee),
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
        View footerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_layout, null, false);
        ImageView fbShare = (ImageView) footerView.findViewById(R.id.fbShare);
        ImageView tweetShare = (ImageView) footerView.findViewById(R.id.tweetShare);
        ImageView instaShare = (ImageView) footerView.findViewById(R.id.instaShare);

        fbShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(HomeActivity.this, "Fb click", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(HomeActivity.this, FacebookLogin.class);
//                startActivity(intent);

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://www.facebook.com/pages/Springfield-Illinois-YMCA/116375146481"));
                startActivity(i);
            }
        });

        tweetShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                try {
//                    loginButtonClick();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                Toast.makeText(HomeActivity.this, "Tweet click", Toast.LENGTH_SHORT).show();
            }
        });

        instaShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String_to_File("http://www.northpennymca.org/content/wp-content/uploads/2013/01/summer-camp-1.jpg");
//                new LongOperation().execute();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

            }
        });

        mDrawerList.addFooterView(footerView);
        mDrawerList.setAdapter(drawerAdapter);
    }

    @Override
    public void onRefreshData(Refreshable refreshable, int requestCode) {

        if (requestCode == JsonCaller.REFRESH_CODE_ADD_CARD) {
//            addCardFragment.onRefreshData(refreshable,requestCode);
            cardShowFragment.onRefreshData(refreshable, requestCode);
        } else if (requestCode == JsonCaller.REFRESH_CODE_ALL_CARDS) {
            if (DataManager.getInstance().isFlagCheckIn()) {
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.content_frame, myCardsFragment,Constant.myCardFragment)
//                        .commit();
                myCardsFragment.onRefreshData(refreshable, requestCode);
            } else {
                homeFragment.onRefreshData(refreshable, requestCode);
            }
        } else if (requestCode == JsonCaller.REFRESH_CODE_DELETE_CARDS) {
            homeFragment.onRefreshData(refreshable, requestCode);
        } else if (requestCode == JsonCaller.REFRESH_CODE_ADD_CARD_NULL) {
            if (DataManager.getInstance().isFlagCheckIn()) {
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.content_frame, myCardsFragment, Constant.myCardFragment)
//                        .commit();
                myCardsFragment.onRefreshData(refreshable, requestCode);
            } else {
                homeFragment.onRefreshData(refreshable, requestCode);
            }
        } else if (requestCode == JsonCaller.REFRESH_CODE_SCHEDULE_DATA_INSTRU) {
            if (DataManager.getInstance().isFlagScedule()) {
                scheduleFragment.onRefreshData(refreshable, requestCode);
            } else {
                homeFragment.onRefreshData(refreshable, requestCode);
            }
        } else if (requestCode == JsonCaller.REFRESH_CODE_SCHEDULE_DATA_CLASS) {
            if (DataManager.getInstance().isFlagScedule()) {
                scheduleFragment.onRefreshData(refreshable, requestCode);
            } else {
                homeFragment.onRefreshData(refreshable, requestCode);
            }
        } else if (requestCode == JsonCaller.REFRESH_CODE_SCHEDULE_DATA_AREA) {
            if (DataManager.getInstance().isFlagScedule()) {
                scheduleFragment.onRefreshData(refreshable, requestCode);
            } else {
                homeFragment.onRefreshData(refreshable, requestCode);
            }
        } else if (requestCode == JsonCaller.REFRESH_CODE_SCHEDULE_DATA) {
            if (DataManager.getInstance().isFlagScedule()) {
                scheduleFragment.onRefreshData(refreshable, requestCode);
            } else {
                homeFragment.onRefreshData(refreshable, requestCode);
            }
        } else if (requestCode == JsonCaller.REFRESH_CODE_SCHEDULE_DATA_DATE_NULL) {
            if (DataManager.getInstance().isFlagScedule()) {
                scheduleFragment.onRefreshData(refreshable, requestCode);
            } else {
                homeFragment.onRefreshData(refreshable, requestCode);
            }
        } else if (requestCode == JsonCaller.REFRESH_CODE_SCHEDULE_DATA_INSTRU_NULL) {
            if (DataManager.getInstance().isFlagScedule()) {
                scheduleFragment.onRefreshData(refreshable, requestCode);
            } else {
                homeFragment.onRefreshData(refreshable, requestCode);
            }
        } else if (requestCode == JsonCaller.REFRESH_CODE_SCHEDULE_DATA_CLASS_NULL) {
            if (DataManager.getInstance().isFlagScedule()) {
                scheduleFragment.onRefreshData(refreshable, requestCode);
            } else {
                homeFragment.onRefreshData(refreshable, requestCode);
            }
        } else if (requestCode == JsonCaller.REFRESH_CODE_SCHEDULE_AREA_NULL) {
            if (DataManager.getInstance().isFlagScedule()) {
                scheduleFragment.onRefreshData(refreshable, requestCode);
            } else {
                homeFragment.onRefreshData(refreshable, requestCode);
            }
        } else if (requestCode == JsonCaller.REFRESH_CODE_INSTRUCTOR_DETAIL) {
            homeFragment.onRefreshData(refreshable, requestCode);
        } else if (requestCode == JsonCaller.REFRESH_CODE_CLASS_DETAIL) {
            homeFragment.onRefreshData(refreshable, requestCode);
        } else if (requestCode == JsonCaller.REFRESH_CODE_INSTRUCT_CLASS_DETAIL) {
            homeFragment.onRefreshData(refreshable, requestCode);
        } else if (requestCode == JsonCaller.REFRESH_CODE_LOCATION_LIST) {
            if (DataManager.getInstance().isFlagLocation()) {
                locationFragment.onRefreshData(refreshable, requestCode);
            } else if (DataManager.getInstance().isFlagSettingLocation()) {
                settingFragment.onRefreshData(refreshable, requestCode);
            } else if (DataManager.getInstance().isFlagScedule()) {
                scheduleFragment.onRefreshData(refreshable, requestCode);
            } else {
                homeFragment.onRefreshData(refreshable, requestCode);
            }
        } else if (requestCode == JsonCaller.REFRESH_CODE_FACILITY_LIST) {
            if (!DataManager.getInstance().isFlagLocation()) {
                homeFragment.onRefreshData(refreshable, requestCode);
            } else {
                locationFragment.onRefreshData(refreshable, requestCode);
            }
        } else if (requestCode == JsonCaller.REFRESH_CODE_TRAINER_LIST) {
            trainerFragment.onRefreshData(refreshable, requestCode);
        } else if (requestCode == JsonCaller.REFRESH_CODE_EVENT_LIST) {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                getSupportFragmentManager().beginTransaction().remove(settingFragment).commit();
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame, eventCalenderFragment, Constant.eventCalenderFragment)
                    .addToBackStack(getSupportFragmentManager().getClass().getName())
                    .commit();
        } else if (requestCode == JsonCaller.REFRESH_CODE_EVENT_NULL) {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                getSupportFragmentManager().beginTransaction().remove(settingFragment).commit();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame, eventCalenderFragment, Constant.eventCalenderFragment)
                    .addToBackStack(getSupportFragmentManager().getClass().getName())
                    .commit();
        } else if (requestCode == JsonCaller.REFRESH_CODE_EVENT_DETAIL) {
            eventCalenderFragment.onRefreshData(refreshable, requestCode);
        } else if (requestCode == JsonCaller.REFRESH_CODE_TRAINER_DETAIL) {
            trainerFragment.onRefreshData(refreshable, requestCode);
        } else if (requestCode == JsonCaller.REFRESH_CODE_NOTIFY_LIST) {
            if (DataManager.getInstance().isFlagNotification()) {
                notificationFragment.onRefreshData(refreshable, requestCode);
            } else {
                homeFragment.onRefreshData(refreshable, requestCode);
            }
        } else if (requestCode == JsonCaller.REFRESH_CODE_BADGE_COUNT) {
            homeFragment.onRefreshData(refreshable, requestCode);
        }

    }

    private class LongOperation extends AsyncTask<Object, Object, Bitmap> {

        @Override
        protected Bitmap doInBackground(Object... objects) {

            try {
                // TODO: 12-Aug-16 Change Img Url coming from API
                URL url = new URL("http://www.northpennymca.org/content/wp-content/uploads/2013/01/summer-camp-1.jpg");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                bitamp = BitmapFactory.decodeStream(input);
                return bitamp;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            saveImage(result);

        }


        @Override
        protected void onPreExecute() {
        }


    }

    private void saveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/scan_qr_images");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            Uri uri = Uri.parse("file://" + file.getAbsolutePath());
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setPackage("com.instagram.android");
            share.putExtra(Intent.EXTRA_STREAM, uri);
            share.putExtra(Intent.EXTRA_SHORTCUT_NAME, "YMCA");
            share.setType("image/*");
            share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(share, "Share image File"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String printKeyHash(Activity context) {


        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

//            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=========", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }

    @Override
    public void onBackPressed() {

        try {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            }

            FragmentManager fm = getSupportFragmentManager();
            int count = fm.getBackStackEntryCount();
            Log.e("Count", String.valueOf(count));
            Fragment fr = fm.findFragmentById(R.id.content_frame);
            isCheck = DataManager.chkStatus(this);
            if (isCheck) {
                if (count > 1) {
                    if (fr.getTag().equals(Constant.dateFragment)) {
                        super.onBackPressed();
                    } else if (fr.getTag().equals(Constant.classFragment)) {
                        super.onBackPressed();
                    } else if (fr.getTag().equals(Constant.eventFragment)) {
                        super.onBackPressed();
                    } else if (fr.getTag().equals(Constant.addMyCardFragment)) {
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
                        if (DataManager.getInstance().isFlagCardShow()) {
                            super.onBackPressed();
                        } else {
                            super.onBackPressed();
                        }
                    } else if (fr.getTag().equals(Constant.myCardFragment)) {
                        super.onBackPressed();
//                        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                        getSupportFragmentManager()
//                                .beginTransaction()
//                                .replace(R.id.content_frame, homeFragment, Constant.homeFragment)
//                                .addToBackStack(getSupportFragmentManager().getClass().getName())
//                                .commit();

                    } else if (fr.getTag().contains(Constant.homeFragment)) {
                        if (doubleBackToExitPressedOnce) {
//                        super.onBackPressed();
                            HomeActivity.this.finish();
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
                } else if (count == 1) {
                    if(!fr.getTag().contains(Constant.homeFragment)){
                        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.content_frame, homeFragment, Constant.homeFragment)
                                .addToBackStack(getSupportFragmentManager().getClass().getName())
                                .commit();
                    }else {
                        HomeActivity.this.finish();
                    }


                } else {
                    HomeActivity.this.finish();
                }


//            }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        isCheck = DataManager.chkStatus(this);
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
//                DataManager.getInstance().showProgressMessage(this, "Progress");
//                String deviceToken = SharedPreference.getSharedPrefData(this, Constant.deviceToken);
//                Map<String, Object> params = new LinkedHashMap<>();
//
//                params.put("device_type", "1");
//                params.put("device_token", deviceToken);
//                JsonCaller.getInstance().getAllCard(params);
                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                getSupportFragmentManager().beginTransaction().remove(settingFragment).commit();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame, myCardsFragment, Constant.myCardFragment)
                            .addToBackStack(getSupportFragmentManager().getClass().getName())
                            .commit();
                    break;
                case 3:
//                    getSupportFragmentManager()
//                            .beginTransaction()
//                            .replace(R.id.content_frame, new EventCalenderFragment(), Constant.eventCalenderFragment)
//                            .commit();
                    DataManager.getInstance().setFlagLocation(true);
                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                getSupportFragmentManager().beginTransaction().remove(settingFragment).commit();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame, locationFragment, Constant.locationFramgnet)
                            .addToBackStack(getSupportFragmentManager().getClass().getName())
                            .commit();
                    break;
                case 4:
//                    FragmentManager fragmentManager = getSupportFragmentManager();
//
//                    FragmentTransaction ft = fragmentManager.beginTransaction();
//                    ft.replace(R.id.content_frame, notificationFragment,Constant.notificationFragment);
//                    fragmentManager.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                    ft.addToBackStack(getSupportFragmentManager().getClass().getName());
//                    ft.commit();

//                    FragmentManager fm = getSupportFragmentManager();
//                    fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                    FragmentTransaction ft = fm.beginTransaction();
//                    ft.replace(R.id.content_frame, notificationFragment,Constant.notificationFragment);
//                    ft.addToBackStack(fm.getClass().getName());
//                    ft.commit();
                    DataManager.getInstance().setFlagNotification(true);
                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                getSupportFragmentManager().beginTransaction().remove(settingFragment).commit();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame, notificationFragment, Constant.notificationFragment)
                            .addToBackStack(getSupportFragmentManager().getClass().getName())
                            .commit();
                    break;
                case 5:
                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                getSupportFragmentManager().beginTransaction().remove(settingFragment).commit();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame, settingFragment, Constant.settingFragment)
                            .addToBackStack(getSupportFragmentManager().getClass().getName())
                            .commit();

//                FragmentManager fm = getSupportFragmentManager();
//                fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                FragmentTransaction ft = fm.beginTransaction();
//
//                ft.replace(R.id.content_frame, settingFragment, Constant.settingFragment);
//
//                ft.addToBackStack(fm.getClass().getName());
//                ft.commit();
                    break;
                case 6:
//                    getSupportFragmentManager()
//                            .beginTransaction()
//                            .replace(R.id.content_frame, facilityFragment, Constant.facilityFragment)
//                            .commit();
                    break;
                case 7:
                    DataManager.getInstance().setFlagScedule(true);
                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                getSupportFragmentManager().beginTransaction().remove(settingFragment).commit();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame, scheduleFragment, Constant.scheduleFragment)
                            .addToBackStack(getSupportFragmentManager().getClass().getName())
                            .commit();
                    break;
                case 8:

//                DataManager.getInstance().showIFramePopUp(this);


                    DataManager.getInstance().setFlagWebView(true);
                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                getSupportFragmentManager().beginTransaction().remove(settingFragment).commit();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame, new WebViewFragment(), Constant.webViewFragment)
                            .addToBackStack(getSupportFragmentManager().getClass().getName())
                            .commit();
                    break;
                case 9:

                    DataManager.getInstance().setFlagWebView(true);
                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                getSupportFragmentManager().beginTransaction().remove(settingFragment).commit();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame, new WebViewFragment(), Constant.webViewFragment)
                            .addToBackStack(getSupportFragmentManager().getClass().getName())
                            .commit();
//                    getSupportFragmentManager()
//                            .beginTransaction()
//                            .replace(R.id.content_frame, new DonateFragment(), Constant.donateFragment)
//                            .commit();
                    break;
                case 10:

                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                getSupportFragmentManager().beginTransaction().remove(settingFragment).commit();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame, trainerFragment, Constant.traineeFragment)
                            .addToBackStack(getSupportFragmentManager().getClass().getName())
                            .commit();
                    break;
                case 11:

//                DataManager.getInstance().showIFramePopUp(this);
                    DataManager.getInstance().setFlagWebView(true);
                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                getSupportFragmentManager().beginTransaction().remove(settingFragment).commit();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame, new WebViewFragment(), Constant.webViewFragment)
                            .addToBackStack(getSupportFragmentManager().getClass().getName())
                            .commit();
                    break;
                case 12:

//                DataManager.getInstance().showIFramePopUp(this);
                    DataManager.getInstance().setFlagWebView(true);
                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                getSupportFragmentManager().beginTransaction().remove(settingFragment).commit();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame, new WebViewFragment(), Constant.webViewFragment)
                            .addToBackStack(getSupportFragmentManager().getClass().getName())
                            .commit();
                    break;
                case 13:
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df1 = new SimpleDateFormat("yyyy/MM/dd");
                    DataManager.getInstance().showProgressMessage(this, "Progress");
                    if (SharedPreference.getSharedPrefData(this, Constant.defaultLocationId) != null) {
                        locationid = SharedPreference.getSharedPrefData(this, Constant.defaultLocationId);
                    } else {
                        locationid = "1";
                    }
                    Map<String, Object> objectMap = new LinkedHashMap<>();
                    String date = df1.format(c.getTime());
                    objectMap.put("date", date);

                    JsonCaller.getInstance().getEventList(objectMap);

//                    getSupportFragmentManager()
//                            .beginTransaction()
//                            .replace(R.id.content_frame, eventCalenderFragment, Constant.eventCalenderFragment)
//                            .addToBackStack(null)
//                            .commit();
                    break;
                case 14:

                    break;
                case 15:
                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                getSupportFragmentManager().beginTransaction().remove(settingFragment).commit();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame, new WebViewFragment(), Constant.webViewFragment)
                            .addToBackStack(getSupportFragmentManager().getClass().getName())
                            .commit();
                    break;
                case 16:

//                DataManager.getInstance().showIFramePopUp(this);
                    DataManager.getInstance().setFlagWebView(true);
                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                getSupportFragmentManager().beginTransaction().remove(settingFragment).commit();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame, new WebViewFragment(), Constant.webViewFragment)
                            .addToBackStack(getSupportFragmentManager().getClass().getName())
                            .commit();
                    break;
                case 17:

//                DataManager.getInstance().showIFramePopUp(this);
                    DataManager.getInstance().setFlagWebView(true);

                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                getSupportFragmentManager().beginTransaction().remove(settingFragment).commit();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame, new WebViewFragment(), Constant.webViewFragment)
                            .addToBackStack(getSupportFragmentManager().getClass().getName())
                            .commit();
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
        String lat = "" + mCurrentLocation.getLatitude();
        String lon = "" + mCurrentLocation.getLongitude();
        Log.e(TAG, "Firing Lat/Longs........" + lat + "........" + lon);
        SharedPreference.setDataInSharedPreference(this, Constant.lati, lat);
        SharedPreference.setDataInSharedPreference(this, Constant.longi, lon);
    }

    public void loginButtonClick() throws IOException, JSONException {


        Constant.twitter = new TwitterFactory().getInstance();
        try {
            Constant.twitter.setOAuthConsumer(Constant.TWITTER_CONSUMER_KEY,
                    Constant.TWITTER_CONSUMER_SECRET);
            Constant.rToken = Constant.twitter
                    .getOAuthRequestToken(Constant.TWITTER_CALLBACK_URL);
            final Intent intent = new Intent(HomeActivity.this, TwitterWebView.class);
            intent.putExtra(TwitterWebView.EXTRA_URL, Constant.rToken.getAuthenticationURL());
            startActivityForResult(intent, WEBVIEW_REQUEST_CODE);
            /*	startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(Constant.rToken.getAuthenticationURL())));*/
        } catch (IllegalStateException e) {

            if (!Constant.twitter.getAuthorization().isEnabled()) {
                System.exit(-1);
            }
            e.printStackTrace();
        } catch (Exception e) {
            Toast.makeText(HomeActivity.this,
                    "Network Host not responding", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1800:
                if (resultCode == Activity.RESULT_OK) {
                    String verifier = data.getExtras().getString(Constant.URL_TWITTER_OAUTH_VERIFIER);
                    AccessToken at = null;
                    try {
                        StatusUpdate status = null;
                        at = Constant.twitter.getOAuthAccessToken(Constant.rToken,
                                verifier);
                        Log.e("imgUrl====", "http://www.ymcamn.org/themes/custom/ymca/img/ymca-logo-social.png" + "\n" + "Twitter share text");
                        String_to_File("http://www.ymcamn.org/themes/custom/ymca/img/ymca-logo-social.png");
                        String shareTexts = "Twitter share text";

                        if (shareTexts.length() <= 140) {
                            status = new StatusUpdate(shareTexts);

                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(
                                    DataManager.getInstance().getAppCompatActivity());
                            builder.setTitle("Sorry");
                            builder.setMessage("Share text out of length");
                            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();

                                }
                            });
                            builder.show();
                            Toast.makeText(DataManager.getInstance().getAppCompatActivity(), "", Toast.LENGTH_SHORT).show();
                        }
                        status.setMedia(casted_image);

                        try {
                            ConfigurationBuilder builder = new ConfigurationBuilder();
                            builder.setOAuthConsumerKey(Constant.TWITTER_CONSUMER_KEY);
                            builder.setOAuthConsumerSecret(Constant.TWITTER_CONSUMER_SECRET);

                            // Access Token
                            AccessToken accessToken = new AccessToken(at.getToken(),
                                    at.getTokenSecret());
                            Twitter twitter = new TwitterFactory(builder.build())
                                    .getInstance(accessToken);
                            twitter.updateStatus(status);
                            // Update status

                        } catch (TwitterException e) {
                            // Error in updating status
//                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e("Twitter Update Error", e.getMessage());
                        }

                        // TODO: 09-Aug-16 Here Call Api
//                        SharedPreferences sharedPreferences = DataManager.getInstance().getActivity().getSharedPreferences(Constant.TEMP_PREF_NAME, Activity.MODE_PRIVATE);
//                        String consId = sharedPreferences.getString(Constant.CONS_ID, null);
//                        String offerId = sharedPreferences.getString(Constant.OFFER_ID, null);
//                        JsonCaller.getInstance().getShareEmail(consId, offerId, "3");


                    } catch (NullPointerException e) {
                        // TODO Auto-generated catch block

                    } catch (TwitterException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    Constant.twitter.setOAuthAccessToken(at);

                }
                break;
            case 10:
                if (resultCode == Activity.RESULT_CANCELED) {
                    displayLocationSettingsRequest(this, Constant.locationRequestInt);
                }

                break;
        }
    }

    public File String_to_File(String prImg) {

        try {

            File cacheDir = DataManager.getInstance().getAppCompatActivity().getDir("test", Activity.MODE_PRIVATE);
            File fileWithinMyDir = new File(cacheDir, "");
            casted_image = new File(fileWithinMyDir, "attachment.jpg");
            if (casted_image.exists()) {
                casted_image.delete();

            }
            casted_image.createNewFile();

            FileOutputStream fos = new FileOutputStream(casted_image);
            String pngJpg = null;
            if (prImg.contains(".png")) {
                pngJpg = prImg.replace(".png", ".jpg");
            } else {
                pngJpg = prImg;
            }
            URL url = new URL(pngJpg);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.connect();
            InputStream in = connection.getInputStream();

            byte[] buffer = new byte[1024];
            int size = 0;
            while ((size = in.read(buffer)) > 0) {
                fos.write(buffer, 0, size);
            }
            fos.close();
            return casted_image;

        } catch (Exception e) {

            System.out.print(e);
            // e.printStackTrace();

        }
        return casted_image;
    }

    public void displayLocationSettingsRequest(final Context context, final int REQUEST_CHECK_SETTINGS) {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.e("check", "All location settings are satisfied.");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.e("check", "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(HomeActivity.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            Log.e("check", "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.e("check", "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        displayLocationSettingsRequest(HomeActivity.this, Constant.locationRequestInt);
                        break;
                }
            }
        });
    }


}
