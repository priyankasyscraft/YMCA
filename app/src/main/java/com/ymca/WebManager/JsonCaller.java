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
import com.ymca.ModelClass.AreaModelClass;
import com.ymca.ModelClass.ClassesModelClass;
import com.ymca.ModelClass.DateModelClass;
import com.ymca.ModelClass.InstructorModelClass;
import com.ymca.ModelClass.MyCardModelClass;
import com.ymca.ModelClass.SliderModelClass;
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
        ERROR_CODES,
        ADD_CARD,
        GET_ALL_CARDS,
        GET_DELETE_CARD,
        SETTING_NOTIFY,
        SLIDER_IMGS,
        SCHEDULE_DATA_DATE,
        SCHEDULE_DATA_CLASS,
        SCHEDULE_DATA_INSTRUC,
        SCHEDULE_DATA_AREA,
    }

    public static final int REFRESH_CODE_SERVER_ERROR = 0;
    public static final int REFRESH_CODE_ERROR_CODE = 1;
    public static final int REFRESH_CODE_ADD_CARD = 2;
    public static final int REFRESH_CODE_ADD_CARD_NULL = 200;
    public static final int REFRESH_CODE_ALL_CARDS = 3;
    public static final int REFRESH_CODE_DELETE_CARDS = 4;
    public static final int REFRESH_CODE_SLIDER_IMAGES = 5;
    public static final int REFRESH_CODE_SLIDER_IMAGES_NULL = 500;
    public static final int REFRESH_CODE_SCHEDULE_DATA = 6;
    public static final int REFRESH_CODE_SCHEDULE_DATA_CLASS = 7;
    public static final int REFRESH_CODE_SCHEDULE_DATA_INSTRU = 8;
    public static final int REFRESH_CODE_SCHEDULE_DATA_AREA = 9;
    public static final int REFRESH_CODE_SCHEDULE_DATA_NULL = 600;

    public void getErrorCode(Map<String, Object> params) {

        SoapCaller soapCaller = new SoapCaller();
        soapCaller.setErrorCode(params);
        soapCaller.execute(ParseOp.ERROR_CODES);
    }

    public void getAddCard(Map<String, Object> params) {
        SoapCaller soapCaller = new SoapCaller();
        soapCaller.setAddCard(params);
        soapCaller.execute(ParseOp.ADD_CARD);
    }

    public void getAllCard(Map<String, Object> params) {
        SoapCaller soapCaller = new SoapCaller();
        soapCaller.setAllCard(params);
        soapCaller.execute(ParseOp.GET_ALL_CARDS);
    }

    public void getDeleteCard(Map<String, Object> params) {
        SoapCaller soapCaller = new SoapCaller();
        soapCaller.setDeleteCard(params);
        soapCaller.execute(ParseOp.GET_DELETE_CARD);
    }

    public void getSettingNotification(Map<String, Object> params) {
        SoapCaller soapCaller = new SoapCaller();
        soapCaller.setSettingNoti(params);
        soapCaller.execute(ParseOp.SETTING_NOTIFY);
    }

    public void getSliderImg(Map<String, Object> params) {
        SoapCaller soapCaller = new SoapCaller();
        soapCaller.setSliderImg(params);
        soapCaller.execute(ParseOp.SLIDER_IMGS);
    }

    public void getScheduleData(Map<String, Object> params) {
        SoapCaller soapCaller = new SoapCaller();
        soapCaller.setSliderImg(params);
        soapCaller.setSchedule(params);
        soapCaller.execute(ParseOp.SCHEDULE_DATA_DATE);
    }

    public void getScheduleDataClass(Map<String, Object> params) {
        SoapCaller soapCaller = new SoapCaller();
        soapCaller.setSliderImg(params);
        soapCaller.setSchedule(params);
        soapCaller.execute(ParseOp.SCHEDULE_DATA_CLASS);
    }

    public void getScheduleDataInstru(Map<String, Object> params) {
        SoapCaller soapCaller = new SoapCaller();
        soapCaller.setSliderImg(params);
        soapCaller.setSchedule(params);
        soapCaller.execute(ParseOp.SCHEDULE_DATA_INSTRUC);
    }

    public void getScheduleDataArea(Map<String, Object> params) {
        SoapCaller soapCaller = new SoapCaller();
        soapCaller.setSliderImg(params);
        soapCaller.setSchedule(params);
        soapCaller.execute(ParseOp.SCHEDULE_DATA_AREA);
    }


    private class SoapCaller extends AsyncTask<ParseOp, Void, String> {


        private Map<String, Object> errorCode, addCard, allCard, deleteCard;

        public void setErrorCode(Map<String, Object> params) {
            this.errorCode = params;
        }

        public void setAddCard(Map<String, Object> params) {
            this.addCard = params;
        }

        public void setAllCard(Map<String, Object> params) {
            this.allCard = params;
        }

        public void setDeleteCard(Map<String, Object> params) {
            this.deleteCard = params;
        }

        public void setSettingNoti(Map<String, Object> params) {
            this.deleteCard = params;
        }

        public void setSliderImg(Map<String, Object> params) {
            this.deleteCard = params;
        }

        public void setSchedule(Map<String, Object> params) {
            this.deleteCard = params;
        }

        @Override
        protected String doInBackground(ParseOp... params) {

            lastOP = params[0];
            switch (lastOP) {
                case ERROR_CODES:
                    try {
                        return httpCaller.getErrorCode(errorCode);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    break;
                case ADD_CARD:
                    try {
                        return httpCaller.getAddCard(addCard);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    break;
                case GET_ALL_CARDS:
                    try {
                        return httpCaller.getAllCardData(allCard);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    break;
                case GET_DELETE_CARD:
                    try {
                        return httpCaller.getDeleteCardData(deleteCard);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    break;

                case SETTING_NOTIFY:
                    try {
                        return httpCaller.getSettingNotify(deleteCard);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    break;
                case SLIDER_IMGS:
                    try {
                        return httpCaller.getSliderImg(deleteCard);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    break;
                case SCHEDULE_DATA_DATE:
                    try {
                        return httpCaller.getScheduleData(deleteCard);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    break;
                case SCHEDULE_DATA_CLASS:
                    try {
                        return httpCaller.getScheduleData(deleteCard);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    break;
                case SCHEDULE_DATA_INSTRUC:
                    try {
                        return httpCaller.getScheduleData(deleteCard);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    break;
                case SCHEDULE_DATA_AREA:
                    try {
                        return httpCaller.getScheduleData(deleteCard);
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
                    Log.e("API response", lastOP.toString() + "/n" + response);
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
                            SharedPreference.setDataInSharedPreference(DataManager.getInstance().getAppCompatActivity(), Constant.errorCodes, response);
                            sendRefreshData(null, REFRESH_CODE_ERROR_CODE);
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
                                String errorCode = SharedPreference.getSharedPrefData(DataManager.getInstance().getAppCompatActivity(), Constant.errorCodes);
                                JSONObject jsonObject = new JSONObject(errorCode);
                                JSONArray jsonArray = jsonObject.getJSONArray("app_error_code");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                    String code = jsonObject2.getString("code");
                                    if (code.equals(errorStr)) {
                                        String msg = jsonObject2.getString("msg");
                                        String msgs[] = msg.split("--");
                                        String msg1 = msgs[0];
                                        String msg2 = msgs[1];
                                        AlertDialog.Builder builder = new AlertDialog.Builder(DataManager.getInstance().getAppCompatActivity());
                                        builder.setTitle(msg1);
                                        builder.setCancelable(false);
                                        builder.setMessage(msg2);
                                        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                sendRefreshData(null, REFRESH_CODE_ADD_CARD);
                                            }
                                        });
                                        builder.show();
                                    }
                                }


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
                                        sendRefreshData(null, REFRESH_CODE_ADD_CARD);
                                    }
                                });

                                builder.show();
                            }


                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case GET_ALL_CARDS:
                    try {
                        if (response.equals("") || response.contains("<div")) {
                            DataManager.getInstance().showServerErrorPopUp(DataManager.getInstance().getAppCompatActivity());
                        } else {

                            JSONObject jsonObject1 = new JSONObject(response);
                            String errorStr = jsonObject1.getString("error");
                            String message = jsonObject1.getString("message");
                            if (!errorStr.equals("") && message.equals("")) {
                                String errorCode = SharedPreference.getSharedPrefData(DataManager.getInstance().getAppCompatActivity(), Constant.errorCodes);
                                JSONObject jsonObject = new JSONObject(errorCode);
                                JSONArray jsonArray = jsonObject.getJSONArray("app_error_code");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                    String code = jsonObject2.getString("code");
                                    if (code.equals(errorStr)) {
                                        String msg = jsonObject2.getString("msg");
                                        String msgs[] = msg.split("--");
                                        String msg1 = msgs[0];
                                        String msg2 = msgs[1];
                                        AlertDialog.Builder builder = new AlertDialog.Builder(DataManager.getInstance().getAppCompatActivity());
                                        builder.setTitle(msg1);
                                        builder.setCancelable(false);
                                        builder.setMessage(msg2);
                                        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                sendRefreshData(null, REFRESH_CODE_ALL_CARDS);
                                            }
                                        });
                                        builder.show();
                                    }
                                }


                            } else if (!errorStr.equals("")) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(DataManager.getInstance().getAppCompatActivity());
                                builder.setTitle("Alert");
                                builder.setCancelable(false);
                                builder.setMessage(errorStr);
                                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        DataManager.getInstance().hideProgressMessage();
                                        sendRefreshData(null, REFRESH_CODE_ADD_CARD_NULL);
                                    }
                                });
                                builder.show();


                            } else {
                                JSONArray jsonArray = jsonObject1.getJSONArray("data");
                                DataManager.getInstance().clearMyCardModelClasses();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    MyCardModelClass myCardModelClass = new MyCardModelClass();
                                    myCardModelClass.setUserName(jsonObject.optString("title"));
                                    myCardModelClass.setUserBarCodeNumber(jsonObject.optString("membership_no"));
                                    DataManager.getInstance().addMyCardModelClasses(myCardModelClass);
                                }
                                sendRefreshData(null, REFRESH_CODE_ALL_CARDS);
                            }


                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case GET_DELETE_CARD:

                    try {
                        if (response.equals("") || response.contains("<div")) {
                            DataManager.getInstance().showServerErrorPopUp(DataManager.getInstance().getAppCompatActivity());
                        } else {

                            JSONObject jsonObject1 = new JSONObject(response);
                            String errorStr = jsonObject1.getString("error");
                            String message = jsonObject1.getString("message");
                            if (!errorStr.equals("")) {
                                String errorCode = SharedPreference.getSharedPrefData(DataManager.getInstance().getAppCompatActivity(), Constant.errorCodes);
                                JSONObject jsonObject = new JSONObject(errorCode);
                                JSONArray jsonArray = jsonObject.getJSONArray("app_error_code");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                    String code = jsonObject2.getString("code");
                                    if (code.equals(errorStr)) {
                                        String msg = jsonObject2.getString("msg");

                                        String msgs[] = msg.split("--");
                                        String msg1 = msgs[0];
                                        String msg2 = msgs[1];
                                        AlertDialog.Builder builder = new AlertDialog.Builder(DataManager.getInstance().getAppCompatActivity());
                                        builder.setTitle(msg1);
                                        builder.setCancelable(false);
                                        builder.setMessage(msg2);
                                        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                sendRefreshData(null, REFRESH_CODE_ADD_CARD);
                                            }
                                        });
                                        builder.show();
                                    }
                                }


                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(DataManager.getInstance().getAppCompatActivity());
                                builder.setTitle("Success");
                                builder.setCancelable(false);
                                builder.setMessage("you have successfully delete this card.");
                                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        sendRefreshData(null, REFRESH_CODE_DELETE_CARDS);
                                    }
                                });
                                builder.show();
                            }


                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case SETTING_NOTIFY:
                    try {
                        if (response.equals("") || response.contains("<div")) {
                            DataManager.getInstance().showServerErrorPopUp(DataManager.getInstance().getAppCompatActivity());
                        } else {

                            JSONObject jsonObject1 = new JSONObject(response);
                            String errorStr = jsonObject1.getString("error");
                            String message = jsonObject1.getString("message");
                            if (!errorStr.equals("")) {
                                String errorCode = SharedPreference.getSharedPrefData(DataManager.getInstance().getAppCompatActivity(), Constant.errorCodes);
                                JSONObject jsonObject = new JSONObject(errorCode);
                                JSONArray jsonArray = jsonObject.getJSONArray("app_error_code");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                    String code = jsonObject2.getString("code");
                                    if (code.equals(errorStr)) {
                                        String msg = jsonObject2.getString("msg");
                                        String msgs[] = msg.split("--");
                                        String msg1 = msgs[0];
                                        String msg2 = msgs[1];
                                        AlertDialog.Builder builder = new AlertDialog.Builder(DataManager.getInstance().getAppCompatActivity());
                                        builder.setCancelable(false);
                                        builder.setTitle(msg1);
                                        builder.setMessage(msg2);
                                        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                DataManager.getInstance().hideProgressMessage();
//                                                sendRefreshData(null, REFRESH_CODE_ADD_CARD);
                                            }
                                        });
                                        builder.show();
                                    }
                                }


                            } else {

//                                sendRefreshData(null, REFRESH_CODE_DELETE_CARDS);
                            }


                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case SLIDER_IMGS:
                    try {
                        if (response.equals("") || response.contains("<div")) {
                            DataManager.getInstance().showServerErrorPopUp(DataManager.getInstance().getAppCompatActivity());
                        } else {

                            JSONObject jsonObject1 = new JSONObject(response);
                            String errorStr = jsonObject1.getString("error");
                            String message = jsonObject1.getString("message");
                            if (!errorStr.equals("") && message.equals("")) {
                                String errorCode = SharedPreference.getSharedPrefData(DataManager.getInstance().getAppCompatActivity(), Constant.errorCodes);
                                JSONObject jsonObject = new JSONObject(errorCode);
                                JSONArray jsonArray = jsonObject.getJSONArray("app_error_code");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                    String code = jsonObject2.getString("code");
                                    if (code.equals(errorStr)) {
                                        String msg = jsonObject2.getString("msg");
                                        String msgs[] = msg.split("--");
                                        String msg1 = msgs[0];
                                        String msg2 = msgs[1];
                                        AlertDialog.Builder builder = new AlertDialog.Builder(DataManager.getInstance().getAppCompatActivity());
                                        builder.setTitle(msg1);
                                        builder.setCancelable(false);
                                        builder.setMessage(msg2);
                                        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                sendRefreshData(null, REFRESH_CODE_ALL_CARDS);
                                            }
                                        });
                                        builder.show();
                                    }
                                }


                            } else if (!errorStr.equals("")) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(DataManager.getInstance().getAppCompatActivity());
                                builder.setTitle("Alert");
                                builder.setCancelable(false);
                                builder.setMessage(errorStr);
                                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        DataManager.getInstance().hideProgressMessage();
                                        sendRefreshData(null, REFRESH_CODE_SLIDER_IMAGES_NULL);
                                    }
                                });
                                builder.show();


                            } else {
                                JSONArray jsonArray = jsonObject1.getJSONArray("data");
                                DataManager.getInstance().clearSliderModelClasses();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    SliderModelClass sliderModelClass = new SliderModelClass();
                                    sliderModelClass.setSliderImgUrl(jsonObject.optString("meta_value"));
                                    DataManager.getInstance().addSliderModelClasses(sliderModelClass);
                                }
                                sendRefreshData(null, REFRESH_CODE_SLIDER_IMAGES);
                            }


                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
                case SCHEDULE_DATA_DATE:
                    try {
                        if (response.equals("") || response.contains("<div")) {
                            DataManager.getInstance().showServerErrorPopUp(DataManager.getInstance().getAppCompatActivity());
                        } else {

                            JSONObject jsonObject1 = new JSONObject(response);
                            String errorStr = jsonObject1.getString("error");
                            String message = jsonObject1.getString("message");
                            if (!errorStr.equals("") && message.equals("")) {
                                String errorCode = SharedPreference.getSharedPrefData(DataManager.getInstance().getAppCompatActivity(), Constant.errorCodes);
                                JSONObject jsonObject = new JSONObject(errorCode);
                                JSONArray jsonArray = jsonObject.getJSONArray("app_error_code");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                    String code = jsonObject2.getString("code");
                                    if (code.equals(errorStr)) {
                                        String msg = jsonObject2.getString("msg");
                                        String msgs[] = msg.split("--");
                                        String msg1 = msgs[0];
                                        String msg2 = msgs[1];
                                        AlertDialog.Builder builder = new AlertDialog.Builder(DataManager.getInstance().getAppCompatActivity());
                                        builder.setTitle(msg1);
                                        builder.setCancelable(false);
                                        builder.setMessage(msg2);
                                        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                sendRefreshData(null, REFRESH_CODE_SCHEDULE_DATA_NULL);
                                            }
                                        });
                                        builder.show();
                                    }
                                }


                            } else if (!errorStr.equals("")) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(DataManager.getInstance().getAppCompatActivity());
                                builder.setTitle("Alert");
                                builder.setCancelable(false);
                                builder.setMessage(errorStr);
                                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        DataManager.getInstance().hideProgressMessage();
                                        sendRefreshData(null, REFRESH_CODE_SCHEDULE_DATA_NULL);
                                    }
                                });
                                builder.show();


                            } else {
                                JSONArray jsonArray = jsonObject1.getJSONArray("data");
                                DataManager.getInstance().clearDateModelClasses();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    DateModelClass dateModelClass = new DateModelClass();
                                    dateModelClass.setScheduleDateId(jsonObject.optString("schedule_id"));
                                    dateModelClass.setScheduleDateName(jsonObject.optString("title"));
                                    dateModelClass.setScheduleDateAppointmentTime(jsonObject.optString("schedule_id"));
                                    dateModelClass.setScheduleDateId(jsonObject.optString("schedule_id"));
                                    dateModelClass.setScheduleDateId(jsonObject.optString("schedule_id"));
                                    DataManager.getInstance().addDateModelClasses(dateModelClass);
                                }
                                sendRefreshData(null, REFRESH_CODE_SCHEDULE_DATA);
                            }


                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
                case SCHEDULE_DATA_CLASS:
                    try {
                        if (response.equals("") || response.contains("<div")) {
                            DataManager.getInstance().showServerErrorPopUp(DataManager.getInstance().getAppCompatActivity());
                        } else {

                            JSONObject jsonObject1 = new JSONObject(response);
                            String errorStr = jsonObject1.getString("error");
                            String message = jsonObject1.getString("message");
                            if (!errorStr.equals("") && message.equals("")) {
                                String errorCode = SharedPreference.getSharedPrefData(DataManager.getInstance().getAppCompatActivity(), Constant.errorCodes);
                                JSONObject jsonObject = new JSONObject(errorCode);
                                JSONArray jsonArray = jsonObject.getJSONArray("app_error_code");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                    String code = jsonObject2.getString("code");
                                    if (code.equals(errorStr)) {
                                        String msg = jsonObject2.getString("msg");
                                        String msgs[] = msg.split("--");
                                        String msg1 = msgs[0];
                                        String msg2 = msgs[1];
                                        AlertDialog.Builder builder = new AlertDialog.Builder(DataManager.getInstance().getAppCompatActivity());
                                        builder.setTitle(msg1);
                                        builder.setCancelable(false);
                                        builder.setMessage(msg2);
                                        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                sendRefreshData(null, REFRESH_CODE_SCHEDULE_DATA_NULL);
                                            }
                                        });
                                        builder.show();
                                    }
                                }


                            } else if (!errorStr.equals("")) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(DataManager.getInstance().getAppCompatActivity());
                                builder.setTitle("Alert");
                                builder.setCancelable(false);
                                builder.setMessage(errorStr);
                                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        DataManager.getInstance().hideProgressMessage();
                                        sendRefreshData(null, REFRESH_CODE_SCHEDULE_DATA_NULL);
                                    }
                                });
                                builder.show();


                            } else {
                                JSONArray jsonArray = jsonObject1.getJSONArray("data");
                                DataManager.getInstance().clearClassesModelClassArrayList();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    ClassesModelClass dateModelClass = new ClassesModelClass();
                                    dateModelClass.setClassesId(jsonObject.optString("id"));
                                    dateModelClass.setClassesName(jsonObject.optString("schedule_class_name"));
                                    DataManager.getInstance().addClassesModelClassArrayList(dateModelClass);
                                }
                                sendRefreshData(null, REFRESH_CODE_SCHEDULE_DATA_CLASS);
                            }


                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
                case SCHEDULE_DATA_INSTRUC:
                    try {
                        if (response.equals("") || response.contains("<div")) {
                            DataManager.getInstance().showServerErrorPopUp(DataManager.getInstance().getAppCompatActivity());
                        } else {

                            JSONObject jsonObject1 = new JSONObject(response);
                            String errorStr = jsonObject1.getString("error");
                            String message = jsonObject1.getString("message");
                            if (!errorStr.equals("") && message.equals("")) {
                                String errorCode = SharedPreference.getSharedPrefData(DataManager.getInstance().getAppCompatActivity(), Constant.errorCodes);
                                JSONObject jsonObject = new JSONObject(errorCode);
                                JSONArray jsonArray = jsonObject.getJSONArray("app_error_code");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                    String code = jsonObject2.getString("code");
                                    if (code.equals(errorStr)) {
                                        String msg = jsonObject2.getString("msg");
                                        String msgs[] = msg.split("--");
                                        String msg1 = msgs[0];
                                        String msg2 = msgs[1];
                                        AlertDialog.Builder builder = new AlertDialog.Builder(DataManager.getInstance().getAppCompatActivity());
                                        builder.setTitle(msg1);
                                        builder.setCancelable(false);
                                        builder.setMessage(msg2);
                                        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                sendRefreshData(null, REFRESH_CODE_SCHEDULE_DATA_NULL);
                                            }
                                        });
                                        builder.show();
                                    }
                                }


                            } else if (!errorStr.equals("")) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(DataManager.getInstance().getAppCompatActivity());
                                builder.setTitle("Alert");
                                builder.setCancelable(false);
                                builder.setMessage(errorStr);
                                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        DataManager.getInstance().hideProgressMessage();
                                        sendRefreshData(null, REFRESH_CODE_SCHEDULE_DATA_NULL);
                                    }
                                });
                                builder.show();


                            } else {
                                JSONArray jsonArray = jsonObject1.getJSONArray("data");
                                DataManager.getInstance().clearInstructorModelClassArrayList();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    InstructorModelClass instructorModelClass = new InstructorModelClass();
                                    instructorModelClass.setInstructorId(jsonObject.optString("id"));
                                    instructorModelClass.setInstructorImg(jsonObject.optString("schedule_instructor_img_url"));
                                    instructorModelClass.setInstructorName(jsonObject.optString("schedule_instructor_name"));
                                    DataManager.getInstance().addInstructorModelClassArrayList(instructorModelClass);
                                }
                                sendRefreshData(null, REFRESH_CODE_SCHEDULE_DATA_INSTRU);
                            }


                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
                case SCHEDULE_DATA_AREA:
                    try {
                        if (response.equals("") || response.contains("<div")) {
                            DataManager.getInstance().showServerErrorPopUp(DataManager.getInstance().getAppCompatActivity());
                        } else {

                            JSONObject jsonObject1 = new JSONObject(response);
                            String errorStr = jsonObject1.getString("error");
                            String message = jsonObject1.getString("message");
                            if (!errorStr.equals("") && message.equals("")) {
                                String errorCode = SharedPreference.getSharedPrefData(DataManager.getInstance().getAppCompatActivity(), Constant.errorCodes);
                                JSONObject jsonObject = new JSONObject(errorCode);
                                JSONArray jsonArray = jsonObject.getJSONArray("app_error_code");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                    String code = jsonObject2.getString("code");
                                    if (code.equals(errorStr)) {
                                        String msg = jsonObject2.getString("msg");
                                        String msgs[] = msg.split("--");
                                        String msg1 = msgs[0];
                                        String msg2 = msgs[1];
                                        AlertDialog.Builder builder = new AlertDialog.Builder(DataManager.getInstance().getAppCompatActivity());
                                        builder.setTitle(msg1);
                                        builder.setCancelable(false);
                                        builder.setMessage(msg2);
                                        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                sendRefreshData(null, REFRESH_CODE_SCHEDULE_DATA_NULL);
                                            }
                                        });
                                        builder.show();
                                    }
                                }


                            } else if (!errorStr.equals("")) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(DataManager.getInstance().getAppCompatActivity());
                                builder.setTitle("Alert");
                                builder.setCancelable(false);
                                builder.setMessage(errorStr);
                                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        DataManager.getInstance().hideProgressMessage();
                                        sendRefreshData(null, REFRESH_CODE_SCHEDULE_DATA_NULL);
                                    }
                                });
                                builder.show();


                            } else {
                                JSONArray jsonArray = jsonObject1.getJSONArray("data");
                                DataManager.getInstance().clearAreaModelClassArrayList();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    AreaModelClass areaModelClass = new AreaModelClass();
                                    areaModelClass.setAreaId(jsonObject.optString("id"));
                                    areaModelClass.setAreaName(jsonObject.optString("schedule_area_name"));
                                    areaModelClass.setAreaInstructor(jsonObject.optString("schedule_area_description"));
                                    String date = DataManager.getInstance().hourConverter(jsonObject.optString("schedule_start_time"));
                                    areaModelClass.setAreaTime(date);
                                    DataManager.getInstance().addAreaModelClassArrayList(areaModelClass);
                                }
                                sendRefreshData(null, REFRESH_CODE_SCHEDULE_DATA_AREA);
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
