package com.ymca.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.ymca.R;
import com.ymca.UserInterFace.RefreshDataListener;
import com.ymca.UserInterFace.Refreshable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;


public class FacebookLogin extends FragmentActivity implements RefreshDataListener {
    //	/private LoginButton loginButton;
    private static CallbackManager callbackManager;
    TextView faceBookId;
    private String regId;
    private byte[] imageBlob;
    ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.facebook_activity);

        initThreadPolicy();
        shareDialog = new ShareDialog(this);
        // this part is optional
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                // TODo: here set webservice for point getting

                // TODO: 09-Aug-16 Here Call api
//                SharedPreferences sharedPreferences = getSharedPreferences(Constant.TEMP_PREF_NAME, Activity.MODE_PRIVATE);
//                String consId = sharedPreferences.getString(Constant.CONS_ID, null);
//                String offerId = sharedPreferences.getString(Constant.OFFER_ID, null);
//                JsonCaller.getInstance().getShareFt(consId, offerId, "1");
                finish();
            }

            @Override
            public void onCancel() {
                Toast.makeText(FacebookLogin.this, "cancelled", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(FacebookLogin.this, "error", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        LoginManager.getInstance()
                .logInWithReadPermissions(FacebookLogin.this, Arrays.asList("public_profile", "user_friends", "email"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {


            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            private String firstName;
                            private String userId;
                            @SuppressWarnings("unused")
                            private String gender;
                            private String email;

                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                // Insert your code here
                                Log.e("Success", "" + object);
                                // {"id":"461376104045319","gender":"male","email":"suyash.syscraft@gmail.com","name":"Syscraft MobileTeam"}
                                try {
                                    firstName = object.getString("name");
                                    userId = object.getString("id");

                                    email = object.getString("email");
                                    gender = object.getString("gender");
                                    // String
                                    //ShareDialog.show(FacebookLogin.this, content);

                                    Bitmap image;
                                    try {
                                        URL url = new URL("http://www.ymcamn.org/themes/custom/ymca/img/ymca-logo-social.png");
                                        image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    if (ShareDialog.canShow(ShareLinkContent.class)) {
                                        Log.e("Data=======", "fb text"+"\n"+"http://www.ymcamn.org/themes/custom/ymca/img/ymca-logo-social.png");
                                        String data;
                                        String imgUrl,urls = "fb text";
                                        imgUrl = "http://www.ymcamn.org/themes/custom/ymca/img/ymca-logo-social.png";

//                                        String[]urlDeeplink = DataManager.getInstance().getShareFBText().split("ht");
//                                        String urr = urlDeeplink[1];
                                        ShareLinkContent linkContent = new ShareLinkContent.Builder()
                                                .setContentTitle("YMCA")
                                                .setContentDescription("fb text")
                                                .setImageUrl(Uri.parse(imgUrl))
                                                .build();

//                                        .setContentUrl(Uri.parse("ht" + "deep link url"))
                                        shareDialog.show(linkContent);
                                    }
                                } catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                                // String

                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
                Toast.makeText(FacebookLogin.this, "Login Cancel", Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(FacebookLogin.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                LoginManager.getInstance().logOut();
                finish();
            }

        });
    }


    void FacebooSDKInitialization() {
        FacebookSdk.sdkInitialize(FacebookLogin.this);
        callbackManager = CallbackManager.Factory.create();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);
        FacebookLogin.callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void initThreadPolicy() {
        // TODO Auto-generated method stub
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD) {
            StrictMode.ThreadPolicy tp = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(tp);
        }
    }



    @Override
    public void onRefreshData(Refreshable refreshable, int requestCode) {

    }
}
