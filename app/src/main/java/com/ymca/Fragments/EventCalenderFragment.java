package com.ymca.Fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;
import com.ymca.Activities.HomeActivity;
import com.ymca.Adapters.EventAdapter;
import com.ymca.AppManager.DataManager;
import com.ymca.Constants.Constant;
import com.ymca.ModelClass.EventDetailModelClass;
import com.ymca.ModelClass.EventModelClass;
import com.ymca.ModelClass.EventNewModelClass;
import com.ymca.R;
import com.ymca.UserInterFace.RefreshDataListener;
import com.ymca.UserInterFace.Refreshable;
import com.ymca.WebManager.JsonCaller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Soni on 06-Aug-16.
 */
public class EventCalenderFragment extends Fragment implements View.OnClickListener {

    private View view;

    private boolean undo = false;
    private CaldroidFragment caldroidFragment;
    private TextView eventTv;
    private ArrayList<Date> dates = new ArrayList<>();
    private ListView eventListView;
    private EventAdapter eventAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.custom_event_calender_fragment, container, false);
        actionBarUpdate();
        final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");

        caldroidFragment = new CaldroidFragment();
        eventTv = (TextView) view.findViewById(R.id.eventTv);
        eventListView = (ListView) view.findViewById(R.id.eventListView);
        eventTv.setOnClickListener(this);
        if (savedInstanceState != null) {
            caldroidFragment.restoreStatesFromKey(savedInstanceState,
                    "CALDROID_SAVED_STATE");
        } else {
            Bundle args = new Bundle();
            Calendar cal = Calendar.getInstance();
            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
            args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
            args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);
            args.putString(CaldroidFragment.DIALOG_TITLE, "Hello");

            caldroidFragment.setArguments(args);

        }

        setCustomResourceForDates();

        FragmentTransaction t = getActivity().getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendar1, caldroidFragment);
        t.commit();

        // Setup listener
        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {
                if (dates.contains(date)) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                    String compareDate = dateFormat.format(date);
                    DataManager.getInstance().clearEventNewModelClassArrayList();
                    for (int i = 0; i < DataManager.getInstance().getEventModelClasses().size(); i++) {

                        for (int j = 0;j < DataManager.getInstance().getEventModelClasses().get(i).getStartDates().size(); j++) {
                            if (DataManager.getInstance().getEventModelClasses().get(i).getStartDates().get(j).equals(compareDate)) {
                                EventNewModelClass eventDetailModelClass = new EventNewModelClass();
                                eventDetailModelClass.setEventId(DataManager.getInstance().getEventModelClasses().get(i).getEventId());
                                eventDetailModelClass.setEventName(DataManager.getInstance().getEventModelClasses().get(i).getEventName());
                                DataManager.getInstance().addEventNewModelClassArrayList(eventDetailModelClass);
                            }
                        }
                    }
                    if (DataManager.getInstance().getEventNewModelClassArrayList().size() != 0) {
                        eventAdapter = new EventAdapter(getActivity(), DataManager.getInstance().getEventNewModelClassArrayList());
                        eventListView.setAdapter(eventAdapter);
                        setListViewHeightBasedOnChildren(eventListView);
                        eventTv.setText("This is event text");
                        eventTv.setVisibility(View.GONE);
                        eventListView.setVisibility(View.VISIBLE);
                    } else {
                        eventTv.setVisibility(View.GONE);
                        eventListView.setVisibility(View.GONE);
                    }
                } else {
                    eventTv.setVisibility(View.GONE);
                    eventListView.setVisibility(View.GONE);
                    return;
                }
            }


            @Override
            public void onChangeMonth(int month, int year) {
            }

            @Override
            public void onLongClickDate(Date date, View view) {
            }

            @Override
            public void onCaldroidViewCreated() {
                if (caldroidFragment.getLeftArrowButton() != null) {
                }
            }

        };

        // Setup Caldroid
        caldroidFragment.setCaldroidListener(listener);


        return view;
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    private void setCustomResourceForDates() {

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        for (int i = 0; i < DataManager.getInstance().getEventModelClasses().size(); i++) {
            try {
                for (int j = 0; j < DataManager.getInstance().getEventModelClasses().get(i).getStartDates().size(); j++) {
                    Date date = format.parse(DataManager.getInstance().getEventModelClasses().get(i).getStartDates().get(j));
                    dates.add(i, date);
                    if (caldroidFragment != null) {
                        ColorDrawable blue = new ColorDrawable(getResources().getColor(R.color.colorPrimary));
                        caldroidFragment.setThemeResource(R.style.AppTheme);
                        caldroidFragment.setBackgroundDrawableForDate(blue, date);
                        caldroidFragment.setShowsDialog(true);
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        DataManager.getInstance().hideProgressMessage();
    }

    private void actionBarUpdate() {
        // TODO Auto-generated method stub


        ActionBar actionBar = ((HomeActivity) getActivity()).getSupportActionBar();


        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.icon_menu);
        actionBar.setDisplayShowTitleEnabled(true);


        // TODO: 28-Jul-16 set action bar background
        Drawable actionBar_bg = getResources().getDrawable(R.drawable.header_bg);
        actionBar.setBackgroundDrawable(actionBar_bg);
        actionBar.setDisplayShowCustomEnabled(true);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT);

        // layoutParams.rightMargin = 20;

        LayoutInflater inflator = getActivity().getLayoutInflater();
        View view = inflator.inflate(R.layout.custom_layout_actionbar, null);

        ImageView notificationBell = (ImageView) view.findViewById(R.id.notificationBell);
        TextView badgeCount = (TextView) view.findViewById(R.id.badgeCount);

        notificationBell.setVisibility(View.GONE);
        badgeCount.setVisibility(View.GONE);


        actionBar.setCustomView(view, layoutParams);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.eventTv:

                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, new EventDetailFragment(), Constant.eventDetailFragment)
                        .addToBackStack(getActivity().getSupportFragmentManager().getClass().getName())
                        .commit();
                break;
        }
    }


    public void onRefreshData(Refreshable refreshable, int requestCode) {
        if (requestCode == JsonCaller.REFRESH_CODE_EVENT_DETAIL) {
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame, new EventDetailFragment(), Constant.eventDetailFragment)
                    .addToBackStack(getActivity().getSupportFragmentManager().getClass().getName())
                    .commit();
        }
    }
}
