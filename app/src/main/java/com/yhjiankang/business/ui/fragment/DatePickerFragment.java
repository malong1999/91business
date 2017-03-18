package com.yhjiankang.business.ui.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import com.yhjiankang.business.interfaces.OnDateCheckedListener;

import java.util.Calendar;

/**
 * Created by 马小布 on 2015/7/4.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private OnDateCheckedListener mListener;


    public void setOnDateCheckedListener(OnDateCheckedListener listener){
        mListener=listener;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        if (mListener!=null){
            monthOfYear=monthOfYear+1;
            mListener.setDate(year+"-"+String.format("%02d", monthOfYear)+"-"+String.format("%02d", dayOfMonth));

        }

    }
}
