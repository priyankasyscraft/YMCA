package com.ymca.WebManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.ymca.AppManager.DataManager;
import com.ymca.AppManager.SharedPreference;
import com.ymca.Constants.Constant;
import com.ymca.UserInterFace.RefreshDataListener;
import com.ymca.UserInterFace.Refreshable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Soni on 17-Aug-16.
 */
public class JsonCaller {

    private static final String TAG = "JSONFragment";
    //region Singleton method.......
    private static JsonCaller mInstance = null;
    private ParseOp lastOP;
    private String response;

    public static JsonCaller getInstance() {
        if (mInstance == null) {
            mInstance = new JsonCaller();
        }
        return mInstance;
    }

    //endregion Single tone........
    private JsonCaller() {

    }


    RefreshDataListener refreshDataListener;
    ArrayList<RefreshDataListener> refreshDataListeners = new ArrayList<>();
    private HttpCaller httpCaller = new HttpCaller();

    public void setRefreshDataListener(RefreshDataListener refreshDataListene) {
        this.refreshDataListeners.add(refreshDataListener);

        for (int i = 0; i < refreshDataListeners.size(); i++) {
            this.refreshDataListener = refreshDataListene;
        }

    }

    public void sendRefreshData(Refreshable data, int refreshCode) {
        if (refreshDataListener != null)
            this.refreshDataListener.onRefreshData(data, refreshCode);
    }

    public enum ParseOp {
        ERROR_CODES, ADD_CARD,
    }
    public static final int REFRESH_CODE_SERVER_ERROR = 0;
    public static final int REFRESH_CODE_ERROR_CODE = 1;
    public static final int REFRESH_CODE_ADD_CARD = 2;

    public void getErrorCode(Map<String, Object> params) {
        SoapCaller soapCaller = new SoapCaller();
        soapCaller.setErrorCode(params);
        soapCaller.execute(ParseOp.ERROR_CODES);
    }

    public void getAddCard(Map<String, Object> params){
        SoapCaller soapCaller = new SoapCaller();
        soapCaller.setAddCard(params);
        soapCaller.execute(ParseOp.ADD_CARD);
    }


    private class SoapCaller extends AsyncTask<ParseOp, Void, String> {


        private Map<String, Object> errorCode,addCard;

        public void setErrorCode(Map<String, Object> params) {
            this.errorCode = params;
        }public void setAddCard(Map<String, Object> params) {
            this.addCard = params;
        }

        @Override
        protected String doInBackground(ParseOp... params) {

            lastOP = params[0];
            switch (lastOP) {
                case ERROR_CODES:
                    try {
                        return   httpCaller.getErrorCode(errorCode);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    break;
                case ADD_CARD:
                    try {
                        return   httpCaller.getAddCard(addCard);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    break;
            }

            return null;
        }

        protected void onPostExecute(String response) {
            if (response != null) {
                try {
                    processResult(response, lastOP);
                    Log.e("API response", lastOP.toString()+"/n"+response);
//                    Log.e(TAG, response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


        private void processResult(String response, ParseOp lastOP) throws JSONException {
            switch (lastOP) {
                case ERROR_CODES:
                    try {
                        if (response.equals("") || response.contains("<div")) {
                            DataManager.getInstance().showServerErrorPopUp(DataManager.getInstance().getAppCompatActivity());
                        } else {
                                JSONObject jsonObject1 = new JSONObject(response);
//                            String errorStr = jsonObject1.getString("error");
//                            if (!errorStr.equals("")) {
//                                AlertDialog.Builder builder = new AlertDialog.Builder(
//                                        DataManager.getInstance().getAppCompatActivity());
//                                builder.setTitle("Alert");
//                                builder.setMessage(errorStr);
//                                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
//
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//
//                                        dialog.dismiss();
//
//                                    }
//                                });
//
//                                builder.show();
//
//                            } else {
                            SharedPreference.setDataInSharedPreference(DataManager.getInstance().getAppCompatActivity(), Constant.errorCodes,response);
                                sendRefreshData(null,REFRESH_CODE_ERROR_CODE);
//                            }


                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case ADD_CARD:
                    try {
                        if (response.equals("") || response.contains("<div")) {
                            DataManager.getInstance().showServerErrorPopUp(DataManager.getInstance().getAppCompatActivity());
                        } else {

                            JSONObject jsonObject1 = new JSONObject(response);
                            String errorStr = jsonObject1.getString("error");
                            String message = jsonObject1.getString("message");
                            if (!errorStr.equals("")) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(
                                        DataManager.getInstance().getAppCompatActivity());
                                builder.setTitle("Alert");
                                builder.setMessage(errorStr);
                                builder.setCancelable(false);
                                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        dialog.dismiss();
                                        sendRefreshData(null,REFRESH_CODE_ADD_CARD);
                                    }
                                });

                                builder.show();

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(
                                        DataManager.getInstance().getAppCompatActivity());
                                builder.setTitle("Alert");
                                builder.setMessage(message);
                                builder.setCancelable(false);
                                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        dialog.dismiss();
                                        sendRefreshData(null,REFRESH_CODE_ADD_CARD);
                                    }
                                });

                                builder.show();
                            }


                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }


    }
}
