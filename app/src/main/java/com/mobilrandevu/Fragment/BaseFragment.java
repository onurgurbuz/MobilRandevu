package com.mobilrandevu.Fragment;

import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alamkanak.weekview.DateTimeInterpreter;
import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.mobilrandevu.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public abstract class BaseFragment extends Fragment implements CalendarDatePickerDialogFragment.OnDateSetListener, WeekView.EventClickListener, WeekView.EmptyViewClickListener,
        MonthLoader.MonthChangeListener, WeekView.EventLongPressListener, WeekView.EmptyViewLongPressListener, WeekView.ScrollListener {
    private WeekView mWeekView;
    private Toolbar toolbar;
    private TextView mTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_base, container, false);

        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");
        //((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(df.format(date));
        mTitle.setText(df.format(date));

        mWeekView = (WeekView) view.findViewById(R.id.weekView);
        mWeekView.setMinimumWidth(10);
        mWeekView.setOnEventClickListener(this);
        mWeekView.setMonthChangeListener(this);
        mWeekView.setEventLongPressListener(this);
        mWeekView.setEmptyViewLongPressListener(this);
        mWeekView.setEmptyViewClickListener(this);
        mWeekView.setScrollListener(this);
        mWeekView.setXScrollingSpeed(.50f);
        mWeekView.setScrollDuration(1000);
        mWeekView.setEventTextColor(getContext().getResources().getColor(R.color.black));
        mWeekView.setNewEventLengthInMinutes(15);
        mWeekView.setHorizontalFlingEnabled(false);

       /* SimpleDateFormat sdf = new SimpleDateFormat("hh:MM");
        try {
            Date dateMin = sdf.parse(Statikler.minDate);
            Date dateMax = sdf.parse(Statikler.maxDate);
            Calendar cal = Calendar.getInstance(Locale.getDefault());
            cal.set(Calendar.HOUR,8);
            cal.set(Calendar.HOUR,20);
            mWeekView.setMaxDate(cal);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/

        setupDateTimeInterpreter(false);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.actionbar_datepicker, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_datepicker) {
            CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment().setOnDateSetListener(BaseFragment.this);
            cdp.setCancelText(getString(R.string.iptal));
            cdp.setDoneText(getString(R.string.tamam));
            cdp.show(getChildFragmentManager(), "fragment_date_picker_name");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
        String month = "12", day = "31";
        if (dayOfMonth < 10)
            day = "0" + dayOfMonth;
        else
            day = "" + dayOfMonth;

        if ((monthOfYear + 1) < 10)
            month = "0" + (monthOfYear + 1);
        else
            month = "" + (monthOfYear + 1);

        //((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(day + "-" + month + "-" + year + " " + getString(R.string.tarihindekisatislar));
        mTitle.setText(day + "-" + month + "-" + year);

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date date = df.parse(day + "-" + month + "-" + year);
            Calendar cal = Calendar.getInstance(Locale.getDefault());
            cal.setTime(date);
            mWeekView.goToDate(cal);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void setupDateTimeInterpreter(final boolean shortDate) {
        mWeekView.setDateTimeInterpreter(new DateTimeInterpreter() {
            @Override
            public String interpretDate(Calendar date) {
                SimpleDateFormat weekdayNameFormat = new SimpleDateFormat("EEE", Locale.getDefault());
                String weekday = weekdayNameFormat.format(date.getTime());
                SimpleDateFormat format = new SimpleDateFormat(" M/d", Locale.getDefault());
                // All android api level do not have a standard way of getting the first letter of
                // the week day name. Hence we get the first char programmatically.
                // Details: http://stackoverflow.com/questions/16959502/get-one-letter-abbreviation-of-week-day-of-a-date-in-java#answer-16959657
                if (shortDate)
                    weekday = String.valueOf(weekday.charAt(0));
                return weekday.toUpperCase() + format.format(date.getTime());
            }

            @Override
            public String interpretTime(int hour, int minutes) {
                //return hour > 11 ? (hour - 12) + " PM" : (hour == 0 ? "12 AM" : hour + " AM");
                return String.format("%02d:%02d", hour, minutes);
            }
        });
    }

    protected String getEventTitle(Calendar time) {
        return String.format("Event of %02d-%02d-%02d %02d:%02d", time.get(Calendar.DAY_OF_MONTH), time.get(Calendar.MONTH) + 1, time.get(Calendar.YEAR), time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE));
    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {
         Toast.makeText(getContext(), "Clicked " + event.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {
         Toast.makeText(getContext(), "Long pressed event: " + event.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEmptyViewClicked(Calendar time) {
          Toast.makeText(getContext(), "Empty view clicked: " + getEventTitle(time), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEmptyViewLongPress(Calendar time) {
         Toast.makeText(getContext(), "Empty view long pressed: " + getEventTitle(time), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFirstVisibleDayChanged(Calendar newFirstVisibleDay, Calendar oldFirstVisibleDay) {
        // ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(String.format("%02d-%02d-%02d", newFirstVisibleDay.get(Calendar.DAY_OF_MONTH), newFirstVisibleDay.get(Calendar.MONTH) + 1, newFirstVisibleDay.get(Calendar.YEAR)));
        mTitle.setText(String.format("%02d-%02d-%02d", newFirstVisibleDay.get(Calendar.DAY_OF_MONTH), newFirstVisibleDay.get(Calendar.MONTH) + 1, newFirstVisibleDay.get(Calendar.YEAR)));
        int height = mWeekView.getMeasuredHeight();
        int textSize = mWeekView.getTextSize();
        int padding = mWeekView.getHeaderRowPadding();
/*        height = height - textSize - (2 * padding);
        mWeekView.setHourHeight(height / 8);
        try {
            String[] dizi = Statikler.randevuayarlari.getListe().get(0).getRBaslangicSaat().split(":");
            mWeekView.goToHour(Double.parseDouble(dizi[0]));
        } catch (Exception e) {
            Log.e("TAG", e.getMessage());
        }
               try {
            Toast.makeText(getContext(), String.format("%02d-%02d-%02d", newFirstVisibleDay.get(Calendar.DAY_OF_MONTH), newFirstVisibleDay.get(Calendar.MONTH) + 1, newFirstVisibleDay.get(Calendar.YEAR)), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {

        }*/
    }

    public WeekView getWeekView() {
        return mWeekView;
    }

    @Override
    public void onResume() {
        super.onResume();
    /*    try {
            String[] dizi = Statikler.randevuayarlari.getListe().get(0).getRBaslangicSaat().split(":");
            mWeekView.goToHour(Double.parseDouble(dizi[0]));
        } catch (Exception e) {
            Log.e("TAG", e.getMessage());
        }*/
    }
}
