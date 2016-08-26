package com.ymca.WebManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import com.ymca.AppManager.DataManager;
import com.ymca.AppManager.SharedPreference;
import com.ymca.Constants.Constant;
import com.ymca.ModelClass.AreaModelClass;
import com.ymca.ModelClass.ClassDetailModelClass;
import com.ymca.ModelClass.ClassesModelClass;
import com.ymca.ModelClass.CustomTextModelClass;
import com.ymca.ModelClass.DateModelClass;
import com.ymca.ModelClass.EventDetailModelClass;
import com.ymca.ModelClass.EventModelClass;
import com.ymca.ModelClass.InstructorDetailModel;
import com.ymca.ModelClass.InstructorModelClass;
import com.ymca.ModelClass.LocationModelClass;
import com.ymca.ModelClass.MyCardModelClass;
import com.ymca.ModelClass.SliderModelClass;
import com.ymca.ModelClass.TraineeModelClass;
import com.ymca.ModelClass.TrainerDetailModelClass;
import com.ymca.UserInterFace.RefreshDataListener;
import com.ymca.UserInterFace.Refreshable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

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
        SCHEDULE_DATA_CLASS_DETAIL,
        SCHEDULE_DATA_INSTRUC,
        SCHEDULE_DATA_INSTRUC_DETAIL,
        SCHEDULE_DATA_AREA,
        LOCATION_LIST,
        LOCATION_DETAIL,
        TRAINER_LIST,
        TRAINER_DETAIL,
        EVENT_LIST,
        EVENT_DETAIL, SCHEDULE_DATA_INSTRUCTOR_CLASS_DETAIL,
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
    public static final int REFRESH_CODE_SCHEDULE_DATA_NULL = 600;
    public static final int REFRESH_CODE_SCHEDULE_DATA_CLASS = 7;
    public static final int REFRESH_CODE_SCHEDULE_DATA_INSTRU = 8;
    public static final int REFRESH_CODE_SCHEDULE_DATA_AREA = 9;
    public static final int REFRESH_CODE_INSTRUCTOR_DETAIL = 10;
    public static final int REFRESH_CODE_CLASS_DETAIL = 11;
    public static final int REFRESH_CODE_LOCATION_LIST = 12;
    public static final int REFRESH_CODE_LOCATION_DETAIL = 13;
    public static final int REFRESH_CODE_TRAINER_LIST = 14;
    public static final int REFRESH_CODE_TRAINER_LIST_NULL = 1400;
    public static final int REFRESH_CODE_TRAINER_DETAIL = 15;
    public static final int REFRESH_CODE_EVENT_LIST = 16;
    public static final int REFRESH_CODE_EVENT_LIST_NULL = 1600;
    public static final int REFRESH_CODE_EVENT_DETAIL = 17;
    public static final int REFRESH_CODE_INSTRUCT_CLASS_DETAIL = 18;

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
        soapCaller.setSchedule(params);
        soapCaller.execute(ParseOp.SCHEDULE_DATA_DATE);
    }

    public void getScheduleDataClass(Map<String, Object> params) {
        SoapCaller soapCaller = new SoapCaller();
        soapCaller.setSchedule(params);
        soapCaller.execute(ParseOp.SCHEDULE_DATA_CLASS);
    }

    public void getScheduleDataInstru(Map<String, Object> params) {
        SoapCaller soapCaller = new SoapCaller();
        soapCaller.setSchedule(params);
        soapCaller.execute(ParseOp.SCHEDULE_DATA_INSTRUC);
    }

    public void getScheduleDataArea(Map<String, Object> params) {
        SoapCaller soapCaller = new SoapCaller();
        soapCaller.setSchedule(params);
        soapCaller.execute(ParseOp.SCHEDULE_DATA_AREA);
    }

    public void getInstructorDetail(Map<String, Object> params) {
        SoapCaller soapCaller = new SoapCaller();
        soapCaller.setInstructorDetail(params);
        soapCaller.execute(ParseOp.SCHEDULE_DATA_INSTRUC_DETAIL);
    }

    public void getInstructorClassDetail(Map<String, Object> params) {
        SoapCaller soapCaller = new SoapCaller();
        soapCaller.setInstructorDetail(params);
        soapCaller.execute(ParseOp.SCHEDULE_DATA_INSTRUCTOR_CLASS_DETAIL);
    }

    public void getClassDetail(Map<String, Object> params) {
        SoapCaller soapCaller = new SoapCaller();
        soapCaller.setInstructorDetail(params);
        soapCaller.execute(ParseOp.SCHEDULE_DATA_CLASS_DETAIL);
    }

    public void getLocationList(Map<String, Object> params) {
        SoapCaller soapCaller = new SoapCaller();
        soapCaller.setInstructorDetail(params);
        soapCaller.execute(ParseOp.LOCATION_LIST);
    }

    public void getLocationDetail(Map<String, Object> params) {
        SoapCaller soapCaller = new SoapCaller();
        soapCaller.setInstructorDetail(params);
        soapCaller.execute(ParseOp.LOCATION_DETAIL);
    }

    public void getTrainerLIst(Map<String, Object> params) {
        SoapCaller soapCaller = new SoapCaller();
        soapCaller.setInstructorDetail(params);
        soapCaller.execute(ParseOp.TRAINER_LIST);
    }

    public void getTrainerDetail(Map<String, Object> params) {
        SoapCaller soapCaller = new SoapCaller();
        soapCaller.setInstructorDetail(params);
        soapCaller.execute(ParseOp.TRAINER_DETAIL);
    }

    public void getEventList(Map<String, Object> params) {
        SoapCaller soapCaller = new SoapCaller();
        soapCaller.setInstructorDetail(params);
        soapCaller.execute(ParseOp.EVENT_LIST);
    }

    public void getEventDetail(Map<String, Object> params) {
        SoapCaller soapCaller = new SoapCaller();
        soapCaller.setInstructorDetail(params);
        soapCaller.execute(ParseOp.EVENT_DETAIL);
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

        public void setInstructorDetail(Map<String, Object> params) {
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

                case SCHEDULE_DATA_INSTRUC_DETAIL:
                    try {
                        return httpCaller.getInstructorDetailData(deleteCard);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    break;
                case SCHEDULE_DATA_INSTRUCTOR_CLASS_DETAIL:
                    try {
                        return httpCaller.getClassDetailData(deleteCard);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    break;
                case SCHEDULE_DATA_CLASS_DETAIL:
                    try {
                        return httpCaller.getClassDetailData(deleteCard);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    break;

                case LOCATION_LIST:
                    try {
                        return httpCaller.getLocationLisData(deleteCard);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    break;
                case LOCATION_DETAIL:
                    try {
                        return httpCaller.getLocationDetailData(deleteCard);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    break;
                case TRAINER_LIST:
                    try {
                        return httpCaller.getTrainerListData(deleteCard);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    break;
                case TRAINER_DETAIL:
                    try {
                        return httpCaller.getTrainerDetailData(deleteCard);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    break;
                case EVENT_LIST:
                    try {
                        return httpCaller.getEventListData(deleteCard);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    break;
                case EVENT_DETAIL:
                    try {
                        return httpCaller.getEventDetailData(deleteCard);
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
                            SharedPreference.setDataInSharedPreference(DataManager.getInstance().getAppCompatActivity(), Constant.errorCodes, response);
                            sendRefreshData(null, REFRESH_CODE_ERROR_CODE);
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
//                                        String msgs[] = msg.split("--");
//                                        String msg1 = msgs[0];
//                                        String msg2 = msgs[1];
                                        String type = jsonObject1.getString("type");
                                        AlertDialog.Builder builder = new AlertDialog.Builder(DataManager.getInstance().getAppCompatActivity());
                                        builder.setTitle(type);
                                        builder.setCancelable(false);
                                        builder.setMessage(msg);
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
                                        String type = jsonObject1.getString("type");
                                        AlertDialog.Builder builder = new AlertDialog.Builder(DataManager.getInstance().getAppCompatActivity());
                                        builder.setTitle(type);
                                        builder.setCancelable(false);
                                        builder.setMessage(msg);
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
                                    myCardModelClass.setUserBarCodeNumber("CARD NUMBER: " + jsonObject.optString("membership_no"));
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

                                        String type = jsonObject1.getString("type");
                                        AlertDialog.Builder builder = new AlertDialog.Builder(DataManager.getInstance().getAppCompatActivity());
                                        builder.setTitle(type);
                                        builder.setCancelable(false);
                                        builder.setMessage(msg);
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
                                        String type = jsonObject1.getString("type");
                                        AlertDialog.Builder builder = new AlertDialog.Builder(DataManager.getInstance().getAppCompatActivity());
                                        builder.setTitle(type);
                                        builder.setCancelable(false);
                                        builder.setMessage(msg);
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
                                        String type = jsonObject1.getString("type");
                                        AlertDialog.Builder builder = new AlertDialog.Builder(DataManager.getInstance().getAppCompatActivity());
                                        builder.setTitle(type);
                                        builder.setCancelable(false);
                                        builder.setMessage(msg);
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
                                    dateModelClass.setScheduleDateId(jsonObject.optString("id"));
                                    dateModelClass.setScheduleDateName(jsonObject.optString("schedule_name"));
                                    dateModelClass.setScheduleDateNameWith(jsonObject.optString("schedule_instructor_name"));

                                    String startTime = jsonObject.optString("schedule_start_time");
                                    String endTime = jsonObject.optString("schedule_end_time");
                                    String timeDiff = DataManager.getInstance().differenceTwoTime(startTime, endTime);

                                    String time = DataManager.getInstance().hourConverter(jsonObject.optString("schedule_start_time"));
                                    dateModelClass.setScheduleDateWeekDays(jsonObject.optString("schedule_weekdays") + " " + "@" + time + " (" + timeDiff + ")");
                                    dateModelClass.setScheduleDateTime(time);
                                    dateModelClass.setScheduleDatePlace("in " + jsonObject.optString("schedule_date_group"));
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
                                        String type = jsonObject1.getString("type");
                                        AlertDialog.Builder builder = new AlertDialog.Builder(DataManager.getInstance().getAppCompatActivity());
                                        builder.setTitle(type);
                                        builder.setCancelable(false);
                                        builder.setMessage(msg);
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
                                        String type = jsonObject1.getString("type");
                                        AlertDialog.Builder builder = new AlertDialog.Builder(DataManager.getInstance().getAppCompatActivity());
                                        builder.setTitle(type);
                                        builder.setCancelable(false);
                                        builder.setMessage(msg);
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
                                        String type = jsonObject1.getString("type");
                                        AlertDialog.Builder builder = new AlertDialog.Builder(DataManager.getInstance().getAppCompatActivity());
                                        builder.setTitle(type);
                                        builder.setCancelable(false);
                                        builder.setMessage(msg);
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
                case SCHEDULE_DATA_INSTRUC_DETAIL:
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
                                        String type = jsonObject1.getString("type");
                                        AlertDialog.Builder builder = new AlertDialog.Builder(DataManager.getInstance().getAppCompatActivity());
                                        builder.setTitle(type);
                                        builder.setCancelable(false);
                                        builder.setMessage(msg);
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
                                JSONObject jsonObject = jsonObject1.getJSONObject("data");
                                DataManager.getInstance().clearInstructorDetailModelArrayList();
                                InstructorDetailModel areaModelClass = new InstructorDetailModel();
                                areaModelClass.setInstructorImgUrl(jsonObject.optString("istruc_img_url"));
                                areaModelClass.setInstructorName(jsonObject.optString("name"));
                                areaModelClass.setInstructorInfo(jsonObject.optString("info"));
                                JSONArray jsonArray = jsonObject.getJSONArray("class_detail");
                                areaModelClass.clearModelClasses();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                    ClassesModelClass classesModelClass = new ClassesModelClass();
                                    classesModelClass.setClassesId(jsonObject2.optString("id"));
                                    classesModelClass.setClassesName(jsonObject2.optString("name"));
//                                    String s = jsonArray.getString(i);
                                    areaModelClass.addModelClasses(classesModelClass);
                                }
                                DataManager.getInstance().addInstructorDetailModelArrayList(areaModelClass);
                                sendRefreshData(null, REFRESH_CODE_INSTRUCTOR_DETAIL);
                            }


                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
                case SCHEDULE_DATA_INSTRUCTOR_CLASS_DETAIL:
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
                                        String type = jsonObject1.getString("type");
                                        AlertDialog.Builder builder = new AlertDialog.Builder(DataManager.getInstance().getAppCompatActivity());
                                        builder.setTitle(type);
                                        builder.setCancelable(false);
                                        builder.setMessage(msg);
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
                                DataManager.getInstance().clearClassDetailModelClassArrayList();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                                    ClassDetailModelClass classDetailModelClass = new ClassDetailModelClass();
                                    String Name = jsonObject.optString("name");
                                    String bg_img = jsonObject.optString("bg_img");
                                    String description = jsonObject.optString("description");
                                    String date_time = jsonObject.optString("date_time");
                                    String instructor_name = jsonObject.optString("instructor_name");
                                    String instructor_img = jsonObject.optString("instructor_img");
                                    String days = jsonObject.optString("days").replace(",", "  ");
                                    String intensity_level = jsonObject.optString("intensity_level");

                                    if (intensity_level.equals("1")) {
                                        intensity_level = "Any Level";
                                    } else if (intensity_level.equals("2")) {
                                        intensity_level = "Beginner";
                                    } else if (intensity_level.equals("3")) {
                                        intensity_level = "Intermediate";
                                    } else if (intensity_level.equals("4")) {
                                        intensity_level = "Advanced";
                                    }

                                    String location_name = jsonObject.optString("location_name");
                                    classDetailModelClass.setClassDetailName(Name);
                                    classDetailModelClass.setClassDetailBgImg(bg_img);
                                    classDetailModelClass.setClassDetailDescription(description);
                                    classDetailModelClass.setClassDetailInstrName(instructor_name);
                                    classDetailModelClass.setClassDetailInstrImg(instructor_img);
                                    classDetailModelClass.setClassDetailWeekDays(days);
                                    classDetailModelClass.setClassDetailLocationName(location_name);
                                    classDetailModelClass.setClassDetailIntensityLevel(intensity_level);


                                    if (!date_time.equals("") || !date_time.equals(null)) {
                                        DateFormat theDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                        Date date = null;
                                        theDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                                        try {

                                            date = theDateFormat.parse(date_time);
                                        } catch (ParseException parseException) {
                                            // Date is invalid. Do what you want.
                                        } catch (Exception exception) {
                                            // Generic catch. Do what you want.
                                        }
//            theDateFormat.setTimeZone(TimeZone.getDefault());
                                        theDateFormat = new SimpleDateFormat("EEE");
                                        SimpleDateFormat theDayFormat = new SimpleDateFormat("MMM dd");

                                        if (date != null) {
                                            String classDate = theDateFormat.format(date);
                                            String classDay = theDayFormat.format(date);
                                            classDetailModelClass.setClassDetailDate(classDate);
                                            classDetailModelClass.setClassDetailDay(classDay);
                                        }

                                    }
                                    DataManager.getInstance().addClassDetailModelClassArrayList(classDetailModelClass);
                                    sendRefreshData(null, REFRESH_CODE_INSTRUCT_CLASS_DETAIL);
                                }

                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case SCHEDULE_DATA_CLASS_DETAIL:
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
                                        String type = jsonObject1.getString("type");
                                        AlertDialog.Builder builder = new AlertDialog.Builder(DataManager.getInstance().getAppCompatActivity());
                                        builder.setTitle(type);
                                        builder.setCancelable(false);
                                        builder.setMessage(msg);
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
                                DataManager.getInstance().clearClassDetailModelClassArrayList();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                                    ClassDetailModelClass classDetailModelClass = new ClassDetailModelClass();
                                    String Name = jsonObject.optString("name");
                                    String bg_img = jsonObject.optString("bg_img");
                                    String description = jsonObject.optString("description");
                                    String date_time = jsonObject.optString("date_time");
                                    String instructor_name = jsonObject.optString("instructor_name");
                                    String instructor_img = jsonObject.optString("instructor_img");
                                    String days = jsonObject.optString("days").replace(",", "  ");
                                    String intensity_level = jsonObject.optString("intensity_level");

                                    if (intensity_level.equals("1")) {
                                        intensity_level = "Any Level";
                                    } else if (intensity_level.equals("2")) {
                                        intensity_level = "Beginner";
                                    } else if (intensity_level.equals("3")) {
                                        intensity_level = "Intermediate";
                                    } else if (intensity_level.equals("4")) {
                                        intensity_level = "Advanced";
                                    }

                                    String location_name = jsonObject.optString("location_name");
                                    classDetailModelClass.setClassDetailName(Name);
                                    classDetailModelClass.setClassDetailBgImg(bg_img);
                                    classDetailModelClass.setClassDetailDescription(description);
                                    classDetailModelClass.setClassDetailInstrName(instructor_name);
                                    classDetailModelClass.setClassDetailInstrImg(instructor_img);
                                    classDetailModelClass.setClassDetailWeekDays(days);
                                    classDetailModelClass.setClassDetailLocationName(location_name);
                                    classDetailModelClass.setClassDetailIntensityLevel(intensity_level);


                                    if (!date_time.equals("") || !date_time.equals(null)) {
                                        DateFormat theDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                        Date date = null;
                                        theDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                                        try {

                                            date = theDateFormat.parse(date_time);
                                        } catch (ParseException parseException) {
                                            // Date is invalid. Do what you want.
                                        } catch (Exception exception) {
                                            // Generic catch. Do what you want.
                                        }
//            theDateFormat.setTimeZone(TimeZone.getDefault());
                                        theDateFormat = new SimpleDateFormat("EEE");
                                        SimpleDateFormat theDayFormat = new SimpleDateFormat("MMM dd");

                                        if (date != null) {
                                            String classDate = theDateFormat.format(date);
                                            String classDay = theDayFormat.format(date);
                                            classDetailModelClass.setClassDetailDate(classDate);
                                            classDetailModelClass.setClassDetailDay(classDay);
                                        }

                                    }
                                    DataManager.getInstance().addClassDetailModelClassArrayList(classDetailModelClass);
                                    sendRefreshData(null, REFRESH_CODE_CLASS_DETAIL);
                                }

                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case LOCATION_LIST:
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
                                        String type = jsonObject1.getString("type");
                                        AlertDialog.Builder builder = new AlertDialog.Builder(DataManager.getInstance().getAppCompatActivity());
                                        builder.setTitle(type);
                                        builder.setCancelable(false);
                                        builder.setMessage(msg);
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
                                DataManager.getInstance().clearLocationModelClasses();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    LocationModelClass locationModelClass = new LocationModelClass();
                                    locationModelClass.setLocationId(jsonObject.optString("location_id"));
                                    locationModelClass.setLocationName(jsonObject.optString("location_name"));
                                    locationModelClass.setLocationAddress(jsonObject.optString("location_address"));
                                    String lat1 = jsonObject.getString("location_lat");
                                    String lon1 = jsonObject.getString("location_long");
                                    String openTime = jsonObject.optString("opening_time");
                                    String closeTime = jsonObject.optString("closing_time");
                                    String time = DataManager.getInstance().differenceTwoTime(closeTime, openTime);
                                    if (!time.contains("0")) {
                                        locationModelClass.setLocationOpenCloseStatus("1");
                                    } else {
                                        locationModelClass.setLocationOpenCloseStatus("2");
                                    }

                                    String openCloseTime = DataManager.getInstance().hourConverter(openTime) + " " + "to" + " " + DataManager.getInstance().hourConverter(closeTime);
                                    locationModelClass.setLocationOpenCloseTime(openCloseTime);
                                    double currentLat = Double.parseDouble(SharedPreference.getSharedPrefData(DataManager.getInstance().getAppCompatActivity(), Constant.lati));
                                    double currentLong = Double.parseDouble(SharedPreference.getSharedPrefData(DataManager.getInstance().getAppCompatActivity(), Constant.longi));

                                    double miles = DataManager.getInstance().distance(currentLat, currentLong, 22.9612, 76.0514);
                                    String milesDouble = String.format("%.2f", miles);
                                    locationModelClass.setLocationMiles(milesDouble + " Mi");
                                    DataManager.getInstance().addLocationModelClasses(locationModelClass);
                                }
                                sendRefreshData(null, REFRESH_CODE_LOCATION_LIST);
                            }


                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
                case LOCATION_DETAIL:
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
                                        String type = jsonObject1.getString("type");
                                        AlertDialog.Builder builder = new AlertDialog.Builder(DataManager.getInstance().getAppCompatActivity());
                                        builder.setTitle(type);
                                        builder.setCancelable(false);
                                        builder.setMessage(msg);
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
                                    LocationModelClass locationModelClass = new LocationModelClass();
                                    locationModelClass.setLocationId(jsonObject.optString("location_id"));
                                    locationModelClass.setLocationName(jsonObject.optString("location_name"));
                                    locationModelClass.setLocationAddress(jsonObject.optString("location_address"));
                                    String lat1 = jsonObject.getString("location_lat");
                                    String lon1 = jsonObject.getString("location_long");
                                    String openTime = jsonObject.optString("opening_time");
                                    String closeTime = jsonObject.optString("closing_time");

                                    Calendar cal = Calendar.getInstance();
                                    Date currentLocalTime = cal.getTime();
                                    DateFormat date = new SimpleDateFormat("HH:mm a");

                                    String localTime = date.format(currentLocalTime);
                                    String time = DataManager.getInstance().differenceTwoTime(localTime, closeTime);
                                    if (!time.contains("0")) {
                                        locationModelClass.setLocationOpenCloseStatus("1");
                                    } else {
                                        locationModelClass.setLocationOpenCloseStatus("2");
                                    }

                                    String openCloseTime = DataManager.getInstance().hourConverter(openTime) + " " + "to" + " " + DataManager.getInstance().hourConverter(closeTime);
                                    locationModelClass.setLocationOpenCloseTime(openCloseTime);
                                    double currentLat = Double.parseDouble(SharedPreference.getSharedPrefData(DataManager.getInstance().getAppCompatActivity(), Constant.lati));
                                    double currentLong = Double.parseDouble(SharedPreference.getSharedPrefData(DataManager.getInstance().getAppCompatActivity(), Constant.longi));

                                    double miles = DataManager.getInstance().distance(currentLat, currentLong, 22.9612, 76.0514);
                                    String milesDouble = String.format("%.2f", miles);
                                    locationModelClass.setLocationMiles(milesDouble + " Mi");
                                    DataManager.getInstance().addLocationModelClasses(locationModelClass);
                                }
                                sendRefreshData(null, REFRESH_CODE_LOCATION_DETAIL);
                            }


                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;

                case TRAINER_LIST:
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
                                        String type = jsonObject1.getString("type");
                                        AlertDialog.Builder builder = new AlertDialog.Builder(DataManager.getInstance().getAppCompatActivity());
                                        builder.setTitle(type);
                                        builder.setCancelable(false);
                                        builder.setMessage(msg);
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
                                        sendRefreshData(null, REFRESH_CODE_TRAINER_LIST_NULL);
                                    }
                                });
                                builder.show();


                            } else {
                                JSONArray jsonArray = jsonObject1.getJSONArray("data");
                                DataManager.getInstance().clearTraineeModelClasses();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    TraineeModelClass traineeModelClass = new TraineeModelClass();
                                    traineeModelClass.setTraineeId(jsonObject.optString("trainer_id"));
                                    traineeModelClass.setTraineeImg(jsonObject.optString("trainer_img"));
                                    traineeModelClass.setTraineeName(jsonObject.optString("trainer_name"));

                                    DataManager.getInstance().addTraineeModelClasses(traineeModelClass);
                                }
                                sendRefreshData(null, REFRESH_CODE_TRAINER_LIST);
                            }


                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;

                case TRAINER_DETAIL:
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
                                        String type = jsonObject1.getString("type");
                                        AlertDialog.Builder builder = new AlertDialog.Builder(DataManager.getInstance().getAppCompatActivity());
                                        builder.setTitle(type);
                                        builder.setCancelable(false);
                                        builder.setMessage(msg);
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
                                        sendRefreshData(null, REFRESH_CODE_TRAINER_LIST_NULL);
                                    }
                                });
                                builder.show();


                            } else {
                                DataManager.getInstance().clearTrainerDetailModelClassArrayList();
                                JSONObject jsonObject = jsonObject1.getJSONObject("data");
                                TrainerDetailModelClass trainerDetailModelClass = new TrainerDetailModelClass();
                                trainerDetailModelClass.setTrainerName(jsonObject.optString("trainer_name"));
                                trainerDetailModelClass.setTrainerImg(jsonObject.optString("trainer_img"));
                                trainerDetailModelClass.setTrainerExperience(jsonObject.optString("trainer_experience"));
                                trainerDetailModelClass.setTrainerFunFact(jsonObject.optString("trainer_fun_fact"));

                                trainerDetailModelClass.clearCertificateList();
                                JSONArray jsonArray = jsonObject.getJSONArray("trainer_certificates");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    CustomTextModelClass modelClass = new CustomTextModelClass();
                                    modelClass.setTextViewString(jsonArray.getString(i));
                                    trainerDetailModelClass.addCertificateList(modelClass);
                                }
                                DataManager.getInstance().addTrainerDetailModelClassArrayList(trainerDetailModelClass);
                                sendRefreshData(null, REFRESH_CODE_TRAINER_DETAIL);
                            }


                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;

                case EVENT_LIST:
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
                                        String type = jsonObject1.getString("type");
                                        AlertDialog.Builder builder = new AlertDialog.Builder(DataManager.getInstance().getAppCompatActivity());
                                        builder.setTitle(type);
                                        builder.setCancelable(false);
                                        builder.setMessage(msg);
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
                                        sendRefreshData(null, REFRESH_CODE_EVENT_LIST_NULL);
                                    }
                                });
                                builder.show();


                            } else {
                                JSONArray jsonArray = jsonObject1.getJSONArray("data");
                                DataManager.getInstance().clearEventModelClasses();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    EventModelClass eventModelClass = new EventModelClass();
                                    eventModelClass.setEventId(jsonObject.optString("event_id"));
                                    eventModelClass.setEventName(jsonObject.optString("name"));
                                    String startDate = jsonObject.optString("start_date_time").substring(0, 10);
                                    String startTime = jsonObject.optString("start_date_time").substring(10);
                                    String endDate = jsonObject.optString("end_date_time").substring(0, 10);
                                    String endTime = jsonObject.optString("end_date_time").substring(10);

                                    List<Date> dates = DataManager.getInstance().getDates(startDate, endDate);
                                    for (Date date : dates) {
                                        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy/MM/dd");
                                        startDate = dateFormater.format(date);
                                        eventModelClass.addStartDates(startDate);
                                    }
                                    eventModelClass.setEventEndDates(endDate);
                                    DataManager.getInstance().addEventModelClasses(eventModelClass);
                                }
                                sendRefreshData(null, REFRESH_CODE_EVENT_LIST);
                            }


                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
                case EVENT_DETAIL:
                    try {
                        if (response.equals("")) {
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
                                        String type = jsonObject1.getString("type");
                                        AlertDialog.Builder builder = new AlertDialog.Builder(DataManager.getInstance().getAppCompatActivity());
                                        builder.setTitle(type);
                                        builder.setCancelable(false);
                                        builder.setMessage(msg);
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
                                        sendRefreshData(null, REFRESH_CODE_EVENT_LIST_NULL);
                                    }
                                });
                                builder.show();


                            } else {
                                JSONArray jsonArray = jsonObject1.getJSONArray("data");
                                DataManager.getInstance().clearEventDetailModelClassArrayList();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    EventDetailModelClass eventDetailModelClass = new EventDetailModelClass();
                                    eventDetailModelClass.setDescription(jsonObject.optString("post_content"));
                                    eventDetailModelClass.setLocationAddress(jsonObject.optString("location_name"));
                                    if(jsonObject.optString("start_date_time").length()!=0) {
                                        eventDetailModelClass.setLocationAddress(jsonObject.optString("date_time").substring(0, 10));
                                    }
                                    String latLong = jsonObject.optString("lat_long");
                                    String msg[] = latLong.split(",");
                                    double msg1 = Double.parseDouble(msg[0]);
                                    double msg2 = Double.parseDouble(msg[1]);
                                    eventDetailModelClass.setLati(msg1);
                                    eventDetailModelClass.setLongi(msg2);
                                    DataManager.getInstance().addEventDetailModelClassArrayList(eventDetailModelClass);
                                }
                                sendRefreshData(null, REFRESH_CODE_EVENT_DETAIL);
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
