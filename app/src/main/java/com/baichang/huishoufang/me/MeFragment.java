package com.baichang.huishoufang.me;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baichang.huishoufang.R;

import cn.bc.base.BaseFrag;


public class MeFragment extends BaseFrag {

    private View view;

    public MeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            initView(inflater, container);
        } else {
            container.removeView(view);
        }
        return view;
    }

    private void initView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_me, container,false);
    }

}
