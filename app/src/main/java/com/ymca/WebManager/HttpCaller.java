package com.ymca.WebManager;

import android.util.Base64;
import android.util.Log;

import com.ymca.AppManager.DataManager;
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
    private static String InstructorDetailServiceUrl = Constant.BaseUrl + "schedule_instructor_detail";
    private static String ClassDetailServiceUrl = Constant.BaseUrl + "schedule_class_detail";
    private static String LocationListServiceUrl = Constant.BaseUrl + "Location";
    private static String LocationDetailServiceUrl = Constant.BaseUrl + "Location_detail";
    private static String TrainerListServiceUrl = Constant.BaseUrl + "trainer";
    private static String TrainerDetailServiceUrl = Constant.BaseUrl + "trainer_detail";
    private static String EventListServiceUrl = Constant.BaseUrl + "Event";
    private static String EventDetailServiceUrl = Constant.BaseUrl + "Event_detail";
    private static String FacilityListServiceUrl = Constant.BaseUrl + "facilities";
    private static String AnnouncementListServiceUrl = Constant.BaseUrl + "announcement";
    private static String AnnouncementResetServiceUrl = Constant.BaseUrl + "ResetBadgeCount";
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

    public String getInstructorDetailData(Map<String, Object> scheduleData) throws MalformedURLException {
        return getPostMethods(scheduleData, Constant.post, InstructorDetailServiceUrl);
    }

    public String getClassDetailData(Map<String, Object> scheduleData) throws MalformedURLException {
        return getPostMethods(scheduleData, Constant.post, ClassDetailServiceUrl);
    }

    public String getLocationLisData(Map<String, Object> scheduleData) throws MalformedURLException {
        return getPostMethods(scheduleData, Constant.post, LocationListServiceUrl);
    }


    public String getLocationDetailData(Map<String, Object> locationDetail) throws MalformedURLException {
        return getPostMethods(locationDetail, Constant.post, LocationDetailServiceUrl);
    }

    public String getTrainerListData(Map<String, Object> trainerList) throws MalformedURLException {
        return getPostMethods(trainerList, Constant.post, TrainerListServiceUrl);
    }

    public String getTrainerDetailData(Map<String, Object> trainerDetail) throws MalformedURLException {
        return getPostMethods(trainerDetail, Constant.post, TrainerDetailServiceUrl);
    }

    public String getEventListData(Map<String, Object> eventList) throws MalformedURLException {
        return getPostMethods(eventList, Constant.post, EventListServiceUrl);
    }

    public String getEventDetailData(Map<String, Object> eventList) throws MalformedURLException {
        return getPostMethods(eventList, Constant.post, EventDetailServiceUrl);
    }

    public String getFacilityListData(Map<String, Object> eventList) throws MalformedURLException {
        return getPostMethods(eventList, Constant.post, FacilityListServiceUrl);
    }

    public String getAnnouncementListData(Map<String, Object> eventList) throws MalformedURLException {
        return getPostMethods(eventList, Constant.post, AnnouncementListServiceUrl);
    }

    public String getAnnouncementResetData(Map<String, Object> eventList) throws MalformedURLException {
        return getPostMethods(eventList, Constant.post, AnnouncementResetServiceUrl);
    }

    // TODO: 17-Aug-16 Here Call Post method

    private String getPostMethods(Map<String, Object> params, String methodName, String uri) throws MalformedURLException {
        URL url = new URL(uri);
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            String basicAuth = "Basic " + Base64.encodeToString("syscraft:sis999sis".getBytes(), Base64.NO_WRAP);
            conn.setRequestMethod(methodName);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            // conn.setConnectTimeout(20000); // 20 sec = 20000 milliSecond
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
//            e.printStackTrace();
            response = e.toString();
        } catch (Exception e) {
            Log.e("getPostMethods: ", e.toString());
            response = e.toString();
//            e.printStackTrace();

        }
        return response;
    }


}
