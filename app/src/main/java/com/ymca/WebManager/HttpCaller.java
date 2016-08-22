package com.ymca.WebManager;

import android.util.Base64;
import android.util.Log;

import com.ymca.Constants.Constant;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Soni on 17-Aug-16.
 */
public class HttpCaller {

    private static String errorCodeUrl = Constant.BaseUrl + "ErrorCodes";
    private static String addCardUrl = Constant.BaseUrl + "AddCard";
    private static String getAllCardUrl = Constant.BaseUrl + "GetCodes";
    private static String getDeleteCardUrl = Constant.BaseUrl + "DeleteCodes";
    private static String getSettingNotifyUrl = Constant.BaseUrl + "Settings";
    private static String SliderServiceUrl = Constant.BaseUrl + "Slider";
    private static String ScheduleServiceUrl = Constant.BaseUrl + "schedule";
    private String response;

    public String getErrorCode(Map<String, Object> params) throws MalformedURLException {

        return getPostMethods(params, Constant.post, errorCodeUrl);
    }

    public String getAddCard(Map<String, Object> addCard) throws MalformedURLException {
        return getPostMethods(addCard, Constant.post, addCardUrl);
    }

    public String getAllCardData(Map<String, Object> addCard) throws MalformedURLException {
        return getPostMethods(addCard, Constant.post, getAllCardUrl);
    }

    public String getDeleteCardData(Map<String, Object> deleteCard) throws MalformedURLException {
        return getPostMethods(deleteCard, Constant.post, getDeleteCardUrl);
    }

    public String getSettingNotify(Map<String, Object> settingData) throws MalformedURLException {
        return getPostMethods(settingData, Constant.post, getSettingNotifyUrl);
    }

    public String getSliderImg(Map<String, Object> sliderData) throws MalformedURLException {
        return getPostMethods(sliderData, Constant.post, SliderServiceUrl);
    }

    public String getScheduleData(Map<String, Object> scheduleData) throws MalformedURLException {
        return getPostMethods(scheduleData, Constant.post, ScheduleServiceUrl);
    }

    // TODO: 17-Aug-16 Here Call Post method

    private String getPostMethods(Map<String, Object> params, String methodName, String uri) throws MalformedURLException {
        URL url = new URL(uri);
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setReadTimeout(20000);
//            conn.setConnectTimeout(20000);
            // TODO: TimeOut region

//            SharedPreferences sharedPreferences1 = DataManager.getInstance().getActivity().getSharedPreferences(Constant.PREF_NAME, Context.MODE_PRIVATE);
//            String responses = sharedPreferences1.getString(Constant.TIME_OUT_RESPONSE, "");
//            JSONObject object = new JSONObject(responses);
//            JSONArray jsonData = object.optJSONArray("data");
//            for (int i = 0; i < jsonData.length(); i++) {
//                JSONObject jsonObject = jsonData.getJSONObject(i);
//                String value = jsonObject.optString("value");
//                if (value.equals("check_register")) {
//                    int timeOut = jsonObject.optInt(value + "_timeout");
//                    conn.setReadTimeout(timeOut * 1000);
//                    conn.setConnectTimeout(timeOut * 1000);
//                }
//            }

            // TODO : end region
            String basicAuth = "Basic " + Base64.encodeToString("syscraft:sis999sis".getBytes(), Base64.NO_WRAP);
            conn.setRequestMethod(methodName);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", basicAuth);

            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String, Object> param : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            String urlParameters = postData.toString();
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(urlParameters);
            wr.flush();
            BufferedInputStream in = new BufferedInputStream(conn.getInputStream());

            response = org.apache.commons.io.IOUtils.toString(in, "UTF-8");


        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            Log.e("getPostMethods: ", e.toString());
            e.printStackTrace();
        }
        return response;
    }


}
