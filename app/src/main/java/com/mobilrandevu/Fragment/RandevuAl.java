package com.mobilrandevu.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.alamkanak.weekview.WeekViewEvent;
import com.mobilrandevu.DBHelper;
import com.mobilrandevu.Model.AkademisyenPOJO;
import com.mobilrandevu.Model.RandevuPOJO;
import com.mobilrandevu.R;
import com.mobilrandevu.Statikler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class RandevuAl extends BaseFragment {
    private Intent intent;
    private List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();

    private int currentYear = 0, currentMonth = 0;
    private DBHelper dbHelper;
    private int year = 0, month = 0, day = 0, hour = 0, minute = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(getActivity().getApplicationContext());
    }

    @Override
    public void onFirstVisibleDayChanged(Calendar newFirstVisibleDay, Calendar oldFirstVisibleDay) {
        super.onFirstVisibleDayChanged(newFirstVisibleDay, oldFirstVisibleDay);
        String currentDate = String.format("%02d.%02d.%02d", newFirstVisibleDay.get(Calendar.DAY_OF_MONTH), newFirstVisibleDay.get(Calendar.MONTH) + 1, newFirstVisibleDay.get(Calendar.YEAR));
        List<RandevuPOJO> randevular = new ArrayList<RandevuPOJO>();
        randevular = dbHelper.RandevulariGetirByOgrenciID(currentDate, Statikler.ogrenciID);
        events.clear();
        for (int i = 0; i < randevular.size(); i++) {
            String[] randevuTarihi = randevular.get(i).getTarih().split("\\.");
            int day = Integer.parseInt(randevuTarihi[0]), month = Integer.parseInt(randevuTarihi[1]), year = Integer.parseInt(randevuTarihi[2]);
            String[] baslangicSaati = randevular.get(i).getSaat().split(":");
            int startHour = Integer.parseInt(baslangicSaati[0]), startMinute = Integer.parseInt(baslangicSaati[1]);
            Calendar startTime = Calendar.getInstance(Locale.getDefault());
            startTime.set(Calendar.HOUR_OF_DAY, startHour);
            startTime.set(Calendar.DAY_OF_MONTH, day);
            startTime.set(Calendar.MONTH, month - 1);
            startTime.set(Calendar.YEAR, year);
            startTime.set(Calendar.MINUTE, startMinute);
            Calendar endTime = (Calendar) startTime.clone();
            endTime.add(Calendar.MINUTE, 30);
            String randevuBasligi = "";
            List<AkademisyenPOJO> akademisyen = new ArrayList<AkademisyenPOJO>();
            akademisyen = dbHelper.AkademisyenGetirByID(randevular.get(i).getAkademisyenID());
            randevuBasligi = akademisyen.get(0).getAdi() + " adlı akademisyen ile randevunuz var.";
            WeekViewEvent event = new WeekViewEvent(1, randevuBasligi, startTime, endTime);
            events.add(event);
        }
    }

    @Override
    public void onEmptyViewClicked(Calendar time) {
        year = time.get(Calendar.YEAR);
        month = time.get(Calendar.MONTH) + 1;
        day = time.get(Calendar.DAY_OF_MONTH);
        hour = time.get(Calendar.HOUR_OF_DAY);
        minute = time.get(Calendar.MINUTE);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setMessage(String.format("%02d.%02d.%02d", day, month, year) + " tarihine " + String.format("%02d:%02d", hour, minute) + ":00 saatine randevunuz ayarlansın mı?")
                .setCancelable(false);
        alertDialogBuilder.setPositiveButton(getString(R.string.evet), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                RandevuPOJO randevuPOJO = new RandevuPOJO();
                randevuPOJO.setOgrenciID(Statikler.ogrenciID);
                randevuPOJO.setAkademisyenID(Statikler.secilenAkademisyen.getId());
                randevuPOJO.setTarih(String.format("%02d.%02d.%02d", day, month, year));
                randevuPOJO.setSaat(String.format("%02d:%02d", hour, minute) + ":00");
                dbHelper.RandevuEkle(randevuPOJO);
            }
        });
        alertDialogBuilder.setNegativeButton(getString(R.string.hayir), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertDialogBuilder.create().show();
    }

    @Override
    public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        currentYear = newYear;
        currentMonth = newMonth;
        List<WeekViewEvent> weekviewEvents = new ArrayList<WeekViewEvent>();
        for (WeekViewEvent e : events) {
            int m = e.getStartTime().get(Calendar.MONTH) + 1, y = e.getStartTime().get(Calendar.YEAR);
            if (m == currentMonth && y == currentYear) {
                weekviewEvents.add(e);
            }
        }
        return weekviewEvents;
    }
}
