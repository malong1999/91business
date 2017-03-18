package com.yhjiankang.business.ui.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.yhjiankang.business.R;
import com.yhjiankang.business.common.UIHelper;
import com.yhjiankang.business.ui.BaseFragment;
import com.yhjiankang.business.ui.view.MineItemView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Project : 91健康商城
 * Program Name : com.yhjiankang.business.ui.fragment.MineFragment.java
 * Author :马庆龙
 * Creation Date : 2015-06-08 上午10:28
 * Description：我的
 * ModificationHistory ：
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {

    @Bind(R.id.miv_phone_authentication)
    MineItemView mMivPhoneAuthentication;
    @Bind(R.id.miv_bind_email)
    MineItemView mMivBindEmail;
    @Bind(R.id.miv_enterprise_qualification)
    MineItemView mMivEnterpriseQualification;
    @Bind(R.id.miv_feedback)
    MineItemView mMivFeedback;
    @Bind(R.id.miv_about_us)
    MineItemView mMivAboutUs;
    @Bind(R.id.miv_newcomer_read)
    MineItemView mMivNewcomerRead;
    @Bind(R.id.miv_clear_cache)
    MineItemView mMivClearCache;
    @Bind(R.id.btn_sign_out)
    Button mBtnSignOut;
    private View mRootView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, mRootView);

        return mRootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public static Fragment newInstance() {
        MineFragment fragment = new MineFragment();
        return fragment;
    }

    @OnClick({R.id.miv_phone_authentication, R.id.miv_bind_email, R.id.miv_enterprise_qualification
            , R.id.miv_feedback, R.id.miv_about_us, R.id.miv_newcomer_read, R.id.miv_clear_cache, R.id.btn_sign_out})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.miv_phone_authentication://手机认证
                UIHelper.showPhoneAuthenticationActivity(getActivity());
                break;
            case R.id.miv_bind_email:
                UIHelper.showBindEmailActivity(getActivity());//邮箱绑定
                break;
            case R.id.miv_feedback:
                UIHelper.showFeedBackActivity(getActivity());//意见反馈
                break;
            case R.id.miv_clear_cache:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setPositiveButton("清理", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO: 2015/6/24 清理
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO: 2015/6/24 取消
                    }
                });
                String s = "<font color='#3eb370'>将清理一万亿兆的缓存信息</font>";
                builder.setTitle(Html.fromHtml(s));
                AlertDialog dialog = builder.create();
                dialog.show();
                break;

            default:
                break;

        }
    }
}
