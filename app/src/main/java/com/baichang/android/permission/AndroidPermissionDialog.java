package com.baichang.android.permission;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by iscod.
 * Time:2016/11/10-13:13.
 */
public class AndroidPermissionDialog extends DialogFragment {


    public AndroidPermissionDialog() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_layout, container, false);
        // Inflate the layout for this fragment
        Window window = getDialog().getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setGravity(Gravity.CENTER);
        window.requestFeature(Window.FEATURE_NO_TITLE);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.dialog_btn_image, R.id.dialog_btn_photo, R.id.dialog_btn_call})
    void OnClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_btn_photo:
                listener.photo();
                dismiss();
                break;
            case R.id.dialog_btn_image:
                listener.image();
                dismiss();
                break;
            case R.id.dialog_btn_call:
                listener.call();
                dismiss();
                break;
        }
    }

    public ResetDialogListener listener;

    public void setResetDialogListener(ResetDialogListener listener) {
        if (listener == null)
            throw new NullPointerException("ResetDialogListener not null");
        this.listener = listener;
    }

    public interface ResetDialogListener {
        void photo();

        void image();

        void call();
    }
}
