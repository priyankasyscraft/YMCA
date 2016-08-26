package com.ymca.AppManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.text.Html;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.ymca.ModelClass.AreaModelClass;
import com.ymca.ModelClass.CampModelClass;
import com.ymca.ModelClass.ClassDetailModelClass;
import com.ymca.ModelClass.ClassesModelClass;
import com.ymca.ModelClass.CustomTextModelClass;
import com.ymca.ModelClass.DateModelClass;
import com.ymca.ModelClass.EventDetailModelClass;
import com.ymca.ModelClass.EventModelClass;
import com.ymca.ModelClass.EventNewModelClass;
import com.ymca.ModelClass.FacilityModelClass;
import com.ymca.ModelClass.HomeClassesModelClass;
import com.ymca.ModelClass.InstructorDetailModel;
import com.ymca.ModelClass.InstructorModelClass;
import com.ymca.ModelClass.LocationModelClass;
import com.ymca.ModelClass.MyCardModelClass;
import com.ymca.ModelClass.PopUpLocationModel;
import com.ymca.ModelClass.SliderModelClass;
import com.ymca.ModelClass.TraineeModelClass;
import com.ymca.ModelClass.TrainerDetailModelClass;
import com.ymca.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.ymca.WebManager.JsonCaller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Soni on 28-Jul-16.
 */
public class DataManager {
    private static DataManager ourInstance = new DataManager();
    private Dialog mDialog;

    public static DataManager getInstance() {
        return ourInstance;
    }

    private DataManager() {
    }

    private ImageLoader imageLoader;
    private Activity appCompatActivity;
    private boolean isProgressDialogRunning = false;
    private boolean flagClassList = false;
    private boolean flagInstructorList = false;
    private boolean flagCheckIn = false;
    private boolean flagScedule = false;
    private boolean flagLocation = false;
    private boolean flagCardShow = false;
    private boolean flagCardShowBack = false;
    private boolean flagAddCard = false;
    private String memberName;
    private String memberCardNumber;

    private ArrayList<CustomTextModelClass> customTextModelClassArrayList = new ArrayList<>();
    private ArrayList<TraineeModelClass> traineeModelClasses = new ArrayList<>();
    private ArrayList<InstructorModelClass> instructorModelClassArrayList = new ArrayList<>();
    private ArrayList<EventModelClass> eventModelClasses = new ArrayList<>();
    private ArrayList<EventNewModelClass> eventNewModelClassArrayList = new ArrayList<>();
    private ArrayList<MyCardModelClass> myCardModelClasses = new ArrayList<>();
    private ArrayList<ClassesModelClass> classesModelClassArrayList = new ArrayList<>();
    private ArrayList<HomeClassesModelClass> homeClassesModelClassArrayList = new ArrayList<>();
    private ArrayList<DateModelClass> dateModelClasses = new ArrayList<>();
    private ArrayList<LocationModelClass> locationModelClasses = new ArrayList<>();
    private ArrayList<FacilityModelClass> facilityModelClassArrayList = new ArrayList<>();
    private ArrayList<CampModelClass> campModelClassArrayList = new ArrayList<>();
    private ArrayList<AreaModelClass> areaModelClassArrayList = new ArrayList<>();
    private ArrayList<SliderModelClass> sliderModelClasses = new ArrayList<>();
    private ArrayList<PopUpLocationModel> popUpLocationModelArrayList = new ArrayList<>();
    private ArrayList<InstructorDetailModel> instructorDetailModelArrayList = new ArrayList<>();
    private ArrayList<ClassDetailModelClass> classDetailModelClassArrayList = new ArrayList<>();
    private ArrayList<TrainerDetailModelClass> trainerDetailModelClassArrayList = new ArrayList<>();
    private ArrayList<EventDetailModelClass> eventDetailModelClassArrayList = new ArrayList<>();

    private InstructorModelClass instructorModelClass = new InstructorModelClass();
    private LocationModelClass locationModelClass = new LocationModelClass();
    private TraineeModelClass traineeModelClass = new TraineeModelClass();
    private EventNewModelClass eventModelClass = new EventNewModelClass();

    public static boolean chkStatus(Context context) {
        // TODO Auto-generated method stub
        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connMgr.getActiveNetworkInfo() != null
                && connMgr.getActiveNetworkInfo().isAvailable()
                && connMgr.getActiveNetworkInfo().isConnected()) {

            return true;
        } else {
            try {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Alert");
                builder.setMessage("No Internet Connection");
                builder.setCancelable(false);
                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getInstance().hideProgressMessage();
                        dialog.dismiss();

                    }
                });
                builder.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    public Activity getAppCompatActivity() {
        return appCompatActivity;
    }

    public void setAppCompatActivity(Activity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
    }


    // region progress dialog..
    public void showProgressMessage(Activity dialogActivity, String msg) {
        if (isProgressDialogRunning) {
            hideProgressMessage();
        }
        isProgressDialogRunning = true;
        this.appCompatActivity = dialogActivity;
        mDialog = new Dialog(appCompatActivity, android.R.style.Theme_Translucent_NoTitleBar);
        // mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.dialog_loading);

        TextView textView = (TextView) mDialog.findViewById(R.id.textView);
        textView.setText(Html.fromHtml(msg));

        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.dimAmount = 0.8f;

        mDialog.getWindow().setAttributes(lp);
        mDialog.getWindow()
                .addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
        try {
            mDialog.show();

        } catch (Exception ex) {
            ex.printStackTrace();
        } catch (Error ex) {
            ex.printStackTrace();
        }

    }

    public void hideMessage() {
        if (mDialog != null)
            mDialog.dismiss();
    }


    public void hideProgressMessage() {
        isProgressDialogRunning = true;
        try {
            if (mDialog != null)
                mDialog.dismiss();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getNotifyDate(String notifyDate) {
        if (!notifyDate.equals("") || !notifyDate.equals(null)) {
            DateFormat theDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = null;
            theDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            try {

                date = theDateFormat.parse(notifyDate);
            } catch (ParseException parseException) {
                // Date is invalid. Do what you want.
            } catch (Exception exception) {
                // Generic catch. Do what you want.
            }
//            theDateFormat.setTimeZone(TimeZone.getDefault());
            theDateFormat = new SimpleDateFormat("hh:mm a");

            if (date != null) {
                notifyDate = theDateFormat.format(date);
                if (notifyDate.contains("AM")) {
                    notifyDate = notifyDate.replace("AM", "AM");
                } else {
                    notifyDate = notifyDate.replace("PM", "PM");
                }

            }
            return notifyDate;
        } else {
            return notifyDate;
        }
    }


    public String getDayAndDate(String notifyDate) {
        if (!notifyDate.equals("") || !notifyDate.equals(null)) {
            DateFormat theDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = null;
            theDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            try {

                date = theDateFormat.parse(notifyDate);
            } catch (ParseException parseException) {
                // Date is invalid. Do what you want.
            } catch (Exception exception) {
                // Generic catch. Do what you want.
            }
//            theDateFormat.setTimeZone(TimeZone.getDefault());
            theDateFormat = new SimpleDateFormat("EEE");
            SimpleDateFormat theDayFormat = new SimpleDateFormat("MMM dd");

            if (date != null) {
                notifyDate = theDateFormat.format(date) + "," + theDayFormat.format(date);

            }
            return notifyDate;
        } else {
            return notifyDate;
        }
    }

    public double distance(double lat1, double lon1, double lat2, double lon2) {

        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        return (dist);
    }

    public double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    public double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }


    public void uniImageLoader(Context context, String imgUrl, ImageView imageView) {
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        imageLoader.displayImage(imgUrl, imageView);
    }

    public ArrayList<InstructorModelClass> getInstructorModelClassArrayList() {
        return instructorModelClassArrayList;
    }

    public void setInstructorModelClassArrayList(ArrayList<InstructorModelClass> instructorModelClassArrayList) {
        this.instructorModelClassArrayList = instructorModelClassArrayList;
    }

    public void addInstructorModelClassArrayList(InstructorModelClass instructorModelClassArrayList) {
        this.instructorModelClassArrayList.add(instructorModelClassArrayList);
    }

    public void clearInstructorModelClassArrayList() {
        this.instructorModelClassArrayList.clear();
    }

    public ArrayList<EventModelClass> getEventModelClasses() {
        return eventModelClasses;
    }

    public void setEventModelClasses(ArrayList<EventModelClass> eventModelClasses) {
        this.eventModelClasses = eventModelClasses;
    }

    public void addEventModelClasses(EventModelClass eventModelClasses) {
        this.eventModelClasses.add(eventModelClasses);
    }

    public void clearEventModelClasses() {
        this.eventModelClasses.clear();
    }

    public ArrayList<MyCardModelClass> getMyCardModelClasses() {
        return myCardModelClasses;
    }

    public void setMyCardModelClasses(ArrayList<MyCardModelClass> myCardModelClasses) {
        this.myCardModelClasses = myCardModelClasses;
    }

    public void addMyCardModelClasses(MyCardModelClass myCardModelClasses) {
        this.myCardModelClasses.add(myCardModelClasses);
    }

    public void clearMyCardModelClasses() {
        this.myCardModelClasses.clear();
    }

    public ArrayList<ClassesModelClass> getClassesModelClassArrayList() {
        return classesModelClassArrayList;
    }

    public void setClassesModelClassArrayList(ArrayList<ClassesModelClass> classesModelClassArrayList) {
        this.classesModelClassArrayList = classesModelClassArrayList;
    }

    public void addClassesModelClassArrayList(ClassesModelClass classesModelClassArrayList) {
        this.classesModelClassArrayList.add(classesModelClassArrayList);
    }

    public void clearClassesModelClassArrayList() {
        this.classesModelClassArrayList.clear();
    }

    public ArrayList<DateModelClass> getDateModelClasses() {
        return dateModelClasses;
    }

    public void setDateModelClasses(ArrayList<DateModelClass> dateModelClasses) {
        this.dateModelClasses = dateModelClasses;
    }

    public void addDateModelClasses(DateModelClass dateModelClasses) {
        this.dateModelClasses.add(dateModelClasses);
    }

    public void clearDateModelClasses() {
        this.dateModelClasses.clear();
    }

    public boolean isFlagClassList() {
        return flagClassList;
    }

    public void setFlagClassList(boolean flagClassList) {
        this.flagClassList = flagClassList;
    }

    public boolean isFlagInstructorList() {
        return flagInstructorList;
    }

    public void setFlagInstructorList(boolean flagInstructorList) {
        this.flagInstructorList = flagInstructorList;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberCardNumber() {
        return memberCardNumber;
    }

    public void setMemberCardNumber(String memberCardNumber) {
        this.memberCardNumber = memberCardNumber;
    }

    public ArrayList<HomeClassesModelClass> getHomeClassesModelClassArrayList() {
        return homeClassesModelClassArrayList;
    }

    public void setHomeClassesModelClassArrayList(ArrayList<HomeClassesModelClass> homeClassesModelClassArrayList) {
        this.homeClassesModelClassArrayList = homeClassesModelClassArrayList;
    }

    public void addHomeClassesModelClassArrayList(HomeClassesModelClass homeClassesModelClassArrayList) {
        this.homeClassesModelClassArrayList.add(homeClassesModelClassArrayList);
    }

    public void clearHomeClassesModelClassArrayList() {
        this.homeClassesModelClassArrayList.clear();
    }

    public ArrayList<LocationModelClass> getLocationModelClasses() {
        return locationModelClasses;
    }

    public void setLocationModelClasses(ArrayList<LocationModelClass> locationModelClasses) {
        this.locationModelClasses = locationModelClasses;
    }

    public void addLocationModelClasses(LocationModelClass locationModelClasses) {
        this.locationModelClasses.add(locationModelClasses);
    }

    public void clearLocationModelClasses() {
        this.locationModelClasses.clear();
    }

    public ArrayList<FacilityModelClass> getFacilityModelClassArrayList() {
        return facilityModelClassArrayList;
    }

    public void setFacilityModelClassArrayList(ArrayList<FacilityModelClass> facilityModelClassArrayList) {
        this.facilityModelClassArrayList = facilityModelClassArrayList;
    }

    public void addFacilityModelClassArrayList(FacilityModelClass facilityModelClassArrayList) {
        this.facilityModelClassArrayList.add(facilityModelClassArrayList);
    }

    public void clearFacilityModelClassArrayList() {
        this.facilityModelClassArrayList.clear();
    }

    public ArrayList<CampModelClass> getCampModelClassArrayList() {
        return campModelClassArrayList;
    }

    public void setCampModelClassArrayList(ArrayList<CampModelClass> campModelClassArrayList) {
        this.campModelClassArrayList = campModelClassArrayList;
    }

    public void addCampModelClassArrayList(CampModelClass campModelClassArrayList) {
        this.campModelClassArrayList.add(campModelClassArrayList);
    }

    public void clearCampModelClassArrayList() {
        this.campModelClassArrayList.clear();
    }

    public boolean isFlagCheckIn() {
        return flagCheckIn;
    }

    public void setFlagCheckIn(boolean flagCheckIn) {
        this.flagCheckIn = flagCheckIn;
    }

    public void showIFramePopUp(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(context.getResources().getString(R.string.iframe_msg));
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public ArrayList<AreaModelClass> getAreaModelClassArrayList() {
        return areaModelClassArrayList;
    }

    public void setAreaModelClassArrayList(ArrayList<AreaModelClass> areaModelClassArrayList) {
        this.areaModelClassArrayList = areaModelClassArrayList;
    }

    public void addAreaModelClassArrayList(AreaModelClass areaModelClassArrayList) {
        this.areaModelClassArrayList.add(areaModelClassArrayList);
    }

    public void clearAreaModelClassArrayList() {
        this.areaModelClassArrayList.clear();
    }

    public boolean isFlagLocation() {
        return flagLocation;
    }

    public void setFlagLocation(boolean flagLocation) {
        this.flagLocation = flagLocation;
    }

    public boolean isFlagScedule() {
        return flagScedule;
    }

    public void setFlagScedule(boolean flagScedule) {
        this.flagScedule = flagScedule;
    }

    public ArrayList<TraineeModelClass> getTraineeModelClasses() {
        return traineeModelClasses;
    }

    public void setTraineeModelClasses(ArrayList<TraineeModelClass> traineeModelClasses) {
        this.traineeModelClasses = traineeModelClasses;
    }

    public void addTraineeModelClasses(TraineeModelClass traineeModelClasses) {
        this.traineeModelClasses.add(traineeModelClasses);
    }

    public void clearTraineeModelClasses() {
        this.traineeModelClasses.clear();
    }

    public ArrayList<CustomTextModelClass> getCustomTextModelClassArrayList() {
        return customTextModelClassArrayList;
    }

    public void setCustomTextModelClassArrayList(ArrayList<CustomTextModelClass> customTextModelClassArrayList) {
        this.customTextModelClassArrayList = customTextModelClassArrayList;
    }

    public void addCustomTextModelClassArrayList(CustomTextModelClass customTextModelClassArrayList) {
        this.customTextModelClassArrayList.add(customTextModelClassArrayList);
    }

    public void clearCustomTextModelClassArrayList() {
        this.customTextModelClassArrayList.clear();
    }

    public boolean isFlagCardShow() {
        return flagCardShow;
    }

    public void setFlagCardShow(boolean flagCardShow) {
        this.flagCardShow = flagCardShow;
    }

    public boolean isFlagAddCard() {
        return flagAddCard;
    }

    public void setFlagAddCard(boolean flagAddCard) {
        this.flagAddCard = flagAddCard;
    }


    public void showServerErrorPopUp(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(R.string.server_error));
        builder.setMessage(context.getResources().getString(R.string.please_error));
        builder.setCancelable(false);
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                DataManager.getInstance().hideProgressMessage();
                JsonCaller.getInstance().sendRefreshData(null, JsonCaller.REFRESH_CODE_SERVER_ERROR);
            }
        });
        builder.show();
    }


    public boolean isFlagCardShowBack() {
        return flagCardShowBack;
    }

    public void setFlagCardShowBack(boolean flagCardShowBack) {
        this.flagCardShowBack = flagCardShowBack;
    }

    public ArrayList<SliderModelClass> getSliderModelClasses() {
        return sliderModelClasses;
    }

    public void setSliderModelClasses(ArrayList<SliderModelClass> sliderModelClasses) {
        this.sliderModelClasses = sliderModelClasses;
    }

    public void addSliderModelClasses(SliderModelClass sliderModelClasses) {
        this.sliderModelClasses.add(sliderModelClasses);
    }

    public void clearSliderModelClasses() {
        this.sliderModelClasses.clear();
    }

    public ArrayList<PopUpLocationModel> getPopUpLocationModelArrayList() {
        return popUpLocationModelArrayList;
    }

    public void setPopUpLocationModelArrayList(ArrayList<PopUpLocationModel> popUpLocationModelArrayList) {
        this.popUpLocationModelArrayList = popUpLocationModelArrayList;
    }

    public void addPopUpLocationModelArrayList(PopUpLocationModel popUpLocationModelArrayList) {
        this.popUpLocationModelArrayList.add(popUpLocationModelArrayList);
    }

    public void clearPopUpLocationModelArrayList() {
        this.popUpLocationModelArrayList.clear();
    }

    public String hourConverter(String time) {

        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            final Date dateObj = sdf.parse(time);
            time = new SimpleDateFormat("K:mm a").format(dateObj);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public String differenceTwoTime(String startTime, String endTime) {
        String diffTime = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date1 = null;
        try {
            date1 = simpleDateFormat.parse(startTime);

            Date date2 = simpleDateFormat.parse(endTime);

            long difference = date2.getTime() - date1.getTime();
            int days = (int) (difference / (1000 * 60 * 60 * 24));
            int hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
            int min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);
            hours = (hours < 0 ? -hours : hours);
            if (hours == 0) {
                diffTime = "" + min;
            } else {
                diffTime = "" + hours + ":";
            }

            if (diffTime.equalsIgnoreCase("0:")) {
                diffTime = diffTime + " min";
            } else if (diffTime.equalsIgnoreCase("1:")) {
                diffTime = diffTime.replace(":", " hour");
            } else {
                diffTime = diffTime.replace(":", " hours");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return diffTime;
    }

    public ArrayList<InstructorDetailModel> getInstructorDetailModelArrayList() {
        return instructorDetailModelArrayList;
    }

    public void setInstructorDetailModelArrayList(ArrayList<InstructorDetailModel> instructorDetailModelArrayList) {
        this.instructorDetailModelArrayList = instructorDetailModelArrayList;
    }

    public void addInstructorDetailModelArrayList(InstructorDetailModel instructorDetailModelArrayList) {
        this.instructorDetailModelArrayList.add(instructorDetailModelArrayList);
    }

    public void clearInstructorDetailModelArrayList() {
        this.instructorDetailModelArrayList.clear();
    }

    public InstructorModelClass getInstructorModelClass() {
        return instructorModelClass;
    }

    public void setInstructorModelClass(InstructorModelClass instructorModelClass) {
        this.instructorModelClass = instructorModelClass;
    }

    public ArrayList<ClassDetailModelClass> getClassDetailModelClassArrayList() {
        return classDetailModelClassArrayList;
    }

    public void setClassDetailModelClassArrayList(ArrayList<ClassDetailModelClass> classDetailModelClassArrayList) {
        this.classDetailModelClassArrayList = classDetailModelClassArrayList;
    }

    public void addClassDetailModelClassArrayList(ClassDetailModelClass classDetailModelClassArrayList) {
        this.classDetailModelClassArrayList.add(classDetailModelClassArrayList);
    }

    public void clearClassDetailModelClassArrayList() {
        this.classDetailModelClassArrayList.clear();
    }

    public LocationModelClass getLocationModelClass() {
        return locationModelClass;
    }

    public void setLocationModelClass(LocationModelClass locationModelClass) {
        this.locationModelClass = locationModelClass;
    }

    public ArrayList<TrainerDetailModelClass> getTrainerDetailModelClassArrayList() {
        return trainerDetailModelClassArrayList;
    }

    public void setTrainerDetailModelClassArrayList(ArrayList<TrainerDetailModelClass> trainerDetailModelClassArrayList) {
        this.trainerDetailModelClassArrayList = trainerDetailModelClassArrayList;
    }

    public void addTrainerDetailModelClassArrayList(TrainerDetailModelClass trainerDetailModelClassArrayList) {
        this.trainerDetailModelClassArrayList.add(trainerDetailModelClassArrayList);
    }

    public void clearTrainerDetailModelClassArrayList() {
        this.trainerDetailModelClassArrayList.clear();
    }

    public TraineeModelClass getTraineeModelClass() {
        return traineeModelClass;
    }

    public void setTraineeModelClass(TraineeModelClass traineeModelClass) {
        this.traineeModelClass = traineeModelClass;
    }

    public EventNewModelClass getEventModelClass() {
        return eventModelClass;
    }

    public void setEventModelClass(EventNewModelClass eventModelClass) {
        this.eventModelClass = eventModelClass;
    }

    public ArrayList<EventNewModelClass> getEventNewModelClassArrayList() {
        return eventNewModelClassArrayList;
    }

    public void setEventNewModelClassArrayList(ArrayList<EventNewModelClass> eventNewModelClassArrayList) {
        this.eventNewModelClassArrayList = eventNewModelClassArrayList;
    }

    public void addEventNewModelClassArrayList(EventNewModelClass eventNewModelClassArrayList) {
        this.eventNewModelClassArrayList.add(eventNewModelClassArrayList);
    }

    public void clearEventNewModelClassArrayList() {
        this.eventNewModelClassArrayList.clear();
    }

    public ArrayList<EventDetailModelClass> getEventDetailModelClassArrayList() {
        return eventDetailModelClassArrayList;
    }

    public void setEventDetailModelClassArrayList(ArrayList<EventDetailModelClass> eventDetailModelClassArrayList) {
        this.eventDetailModelClassArrayList = eventDetailModelClassArrayList;
    }

    public void addEventDetailModelClassArrayList(EventDetailModelClass eventDetailModelClassArrayList) {
        this.eventDetailModelClassArrayList.add(eventDetailModelClassArrayList);
    }

    public void clearEventDetailModelClassArrayList() {
        this.eventDetailModelClassArrayList.clear();
    }

    public List<Date> getDates(String dateString1, String dateString2)
    {
        ArrayList<Date> dates = new ArrayList<Date>();
        DateFormat df1 = new SimpleDateFormat("yyyy/MM/dd");

        Date date1 = null;
        Date date2 = null;

        try {
            date1 = df1 .parse(dateString1);
            date2 = df1 .parse(dateString2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);


        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        while(!cal1.after(cal2))
        {
            dates.add(cal1.getTime());
            cal1.add(Calendar.DATE, 1);
        }
        return dates;
    }
}
