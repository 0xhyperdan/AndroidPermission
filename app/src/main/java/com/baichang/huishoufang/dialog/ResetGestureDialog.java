package com.baichang.huishoufang.dialog;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.baichang.huishoufang.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResetGestureDialog extends DialogFragment {


    public ResetGestureDialog() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reset_gesture_dialog, container, false);
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
