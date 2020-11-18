package com.demacia.garen.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.demacia.garen.activity.BaseActivity;
import com.demacia.garen.utils.LOG;


public class BaseFragment extends Fragment {
    protected Context context;

    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.context = getActivity();
        LOG.m11v("onCreateFragment : " + this);
    }

    public void onDestroy() {
        super.onDestroy();
        LOG.m11v("onDestroyFragment : " + this);
    }
}
