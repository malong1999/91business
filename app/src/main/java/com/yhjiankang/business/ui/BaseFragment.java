package com.yhjiankang.business.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yhjiankang.business.App;
import com.yhjiankang.business.R;

/**
 * Created by 马小布 on 2015-06-06.
 */

public class BaseFragment extends Fragment {


    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        getActivity().overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_remain);
    }
}
