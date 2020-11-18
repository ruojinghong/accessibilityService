package com.demacia.garen.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demacia.garen.R;
import com.demacia.garen.utils.LOG;

public class FragmentMainHot extends BaseFragment implements View.OnClickListener {
    private Holder holder = new Holder();
    private View rootView = null;

    private class Holder {
        private Holder() {
        }
    }

    private void initHolder(View view) {
    }

    public void onClick(View view) {
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getBaseActivity().setStatusBarColor(-14408668, false);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        if (this.rootView == null) {
            View inflate = layoutInflater.inflate(R.layout.fragment_main_hot, viewGroup, false);
            this.rootView = inflate;
            initHolder(inflate);
            LOG.m11v("onCreateView : " + this);
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
