package com.demacia.garen.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demacia.garen.R;
import com.demacia.garen.activity.ActivityDyBothGuan;
import com.demacia.garen.activity.ActivityDyFenGuan;
import com.demacia.garen.activity.ActivityDyLive;
import com.demacia.garen.activity.ActivityDyPingXing;
import com.demacia.garen.activity.ActivityDyQuGuan;
import com.demacia.garen.activity.ActivityDyShiXing;
import com.demacia.garen.activity.ActivityDyYangHao;
import com.demacia.garen.activity.ActivityDyYingLiu;
import com.demacia.garen.utils.LOG;


public class FragmentMainHome extends BaseFragment implements View.OnClickListener {
    private Holder holder = new Holder();
    private View rootView = null;

    private class Holder {
        private Holder() {
        }
    }

    private void initHolder(View view) {
    }

    public void onClick(View view) {
        if (view.getId() == R.id.funcDyYingLiu) {
            startActivity(new Intent(this.context, ActivityDyYingLiu.class));
        } else if (view.getId() == R.id.funcDyYangHao) {
            startActivity(new Intent(this.context, ActivityDyYangHao.class));
        } else if (view.getId() == R.id.funcDyQuGuan) {
            startActivity(new Intent(this.context, ActivityDyQuGuan.class));
        } else if (view.getId() == R.id.funcDyBothGuan) {
            startActivity(new Intent(this.context, ActivityDyBothGuan.class));
        } else if (view.getId() == R.id.funcDyFenGuan) {
            startActivity(new Intent(this.context, ActivityDyFenGuan.class));
        } else if (view.getId() == R.id.funcDyShiXing) {
            startActivity(new Intent(this.context, ActivityDyShiXing.class));
        } else if (view.getId() == R.id.funcDyPingXing) {
            startActivity(new Intent(this.context, ActivityDyPingXing.class));
        } else if (view.getId() == R.id.funcDyLive) {
            startActivity(new Intent(this.context, ActivityDyLive.class));
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getBaseActivity().setStatusBarColor(-14408668, false);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        if (this.rootView == null) {
            View inflate = layoutInflater.inflate(R.layout.fragment_main_home, viewGroup, false);
            this.rootView = inflate;
            initHolder(inflate);
            LOG.m11v("onCreateView : " + this);
            this.rootView.findViewById(R.id.funcDyYingLiu).setOnClickListener(this);
            this.rootView.findViewById(R.id.funcDyYangHao).setOnClickListener(this);
            this.rootView.findViewById(R.id.funcDyQuGuan).setOnClickListener(this);
            this.rootView.findViewById(R.id.funcDyBothGuan).setOnClickListener(this);
            this.rootView.findViewById(R.id.funcDyFenGuan).setOnClickListener(this);
            this.rootView.findViewById(R.id.funcDyShiXing).setOnClickListener(this);
            this.rootView.findViewById(R.id.funcDyPingXing).setOnClickListener(this);
            this.rootView.findViewById(R.id.funcDyLive).setOnClickListener(this);
        }
        return this.rootView;
    }

    public void onDestroyView() {
        super.onDestroyView();
        LOG.m11v("onDestroyView : " + this);
    }

    public void onResume() {
        super.onResume();
    }
}
