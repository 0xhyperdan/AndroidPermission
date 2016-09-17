package cn.ml.base.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MLSweetDialog extends Dialog implements DialogInterface.OnDismissListener {

    private final static String ID_TITLE = "sweet_title";
    private final static String ID_MESSAGE = "sweet_message";
    private final static String ID_ICON = "sweet_icon";
    private final static String ID_POSITIVE = "sweet_positive";
    private final static String ID_NEGATIVE = "sweet_negative";

    private static int default_anim;
    private static int default_layout;

    public static void setDefaultAnimation(int style_id) {
        MLSweetDialog.default_anim = style_id;
    }

    public static void setDefaultLayout(int layout_id) {
        MLSweetDialog.default_layout = layout_id;
    }

    private long delay;
    private Timer delayTimer;

    private DelayDismissTask delayTask;

    public MLSweetDialog(Context context) {
        this(context, default_layout);
    }

    public MLSweetDialog(Context context, int layout) {
        this(context, LayoutInflater.from(context).inflate(layout, null));
    }

    public MLSweetDialog(Context context, View contentView) {
        super(context, false, null);
        super.setOnDismissListener(this);
        super.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.getWindow().setBackgroundDrawable(new BitmapDrawable());
        super.setContentView(contentView);
        init();
    }

    private void init() {
        if (default_anim > 0) {
            setSweetAnimations(default_anim);
        }
        setSweetPositive(null, new OnClickToDismissListener() {
            @Override
            public void onClickAfterDismiss(View v) {

            }
        });
        setSweetNegative(null, new OnClickToDismissListener() {
            @Override
            public void onClickAfterDismiss(View v) {

            }
        });
    }

    private int getId(String name) {
        return getContext().getResources().getIdentifier(name, "id", getContext().getPackageName());
    }

    public MLSweetDialog setWidthAndHeight(int width, int height) {
        WindowManager.LayoutParams params = super.getWindow().getAttributes();
        params.width = width;
        params.height = height;
        super.getWindow().setAttributes(params);
        return this;
    }

    public MLSweetDialog setAttributeById(int id, String txt, int drawable_id, View.OnClickListener onClick) {
        Drawable drawable = null;
        if (drawable_id > 0) {
            drawable = getContext().getResources().getDrawable(drawable_id);
        }
        return setAttributeById(id, txt, drawable, onClick);
    }

    public MLSweetDialog setAttributeById(int id, int txt_id, Drawable drawable, View.OnClickListener onClick) {
        String txt = null;
        if (txt_id > 0) {
            txt = getContext().getString(txt_id);
        }
        return setAttributeById(id, txt, drawable, onClick);
    }

    public MLSweetDialog setAttributeById(int id, int txt_id, int drawable_id, View.OnClickListener onClick) {
        String txt = null;
        if (txt_id > 0) {
            txt = getContext().getString(txt_id);
        }
        Drawable drawable = null;
        if (drawable_id > 0) {
            drawable = getContext().getResources().getDrawable(drawable_id);
        }
        return setAttributeById(id, txt, drawable, onClick);
    }

    public MLSweetDialog setAttributeById(int id, String txt, Drawable drawable, View.OnClickListener onClick) {
        View v = findViewById(id);
        if (v == null) {
            return this;
        }else{
            v.setVisibility(View.VISIBLE);
        }

        v.setOnClickListener(onClick);
        if (onClick != null && onClick instanceof OnClickListener) {
            OnClickListener listener = (OnClickListener) onClick;
            listener.setSweetDialog(this);
        }

        if (drawable != null) {
            if (v instanceof ImageView) {
                ImageView imageView = (ImageView) v;
                imageView.setImageDrawable(drawable);
            } else {
                v.setBackgroundDrawable(drawable);
            }
        }
        if (txt != null) {
            if (v instanceof TextView) {
                TextView textView = (TextView) v;
                textView.setText(txt);
            }
        }
        return this;
    }

    public MLSweetDialog setVisibilityById(int id, int visibility) {
        View v = findViewById(id);
        v.setVisibility(visibility);
        return this;
    }

    public MLSweetDialog setSweetTitle(int txt_id) {
        return setAttributeById(getId(ID_TITLE), txt_id, 0, null);
    }

    public MLSweetDialog setSweetTitle(String txt) {
        return setAttributeById(getId(ID_TITLE), txt, null, null);
    }

    public MLSweetDialog setSweetMessage(int txt_id) {
        return setAttributeById(getId(ID_MESSAGE), txt_id, 0, null);
    }

    public MLSweetDialog setSweetMessage(String txt) {
        return setAttributeById(getId(ID_MESSAGE), txt, null, null);
    }

    public MLSweetDialog setSweetPositive(int txt_id, OnClickToDismissListener positiveClick) {
        return setAttributeById(getId(ID_POSITIVE), txt_id, 0, positiveClick);
    }

    public MLSweetDialog setSweetPositive(String txt, OnClickToDismissListener positiveClick) {
        return setAttributeById(getId(ID_POSITIVE), txt, null, positiveClick);
    }

    public MLSweetDialog setSweetPositiveGone() {
        return setVisibilityById(getId(ID_POSITIVE), View.GONE);
    }

    public MLSweetDialog setSweetNegative(int txt_id, OnClickToDismissListener negativeClick) {
        return setAttributeById(getId(ID_NEGATIVE), txt_id, 0, negativeClick);
    }

    public MLSweetDialog setSweetNegative(String txt, OnClickToDismissListener negativeClick) {
        return setAttributeById(getId(ID_NEGATIVE), txt, null, negativeClick);
    }

    public MLSweetDialog setSweetNegativeGone() {
        return setVisibilityById(getId(ID_NEGATIVE), View.GONE);
    }

    public MLSweetDialog setSweetIcon(int drawable_id) {
        return setAttributeById(getId(ID_ICON), 0, drawable_id, null);
    }

    public MLSweetDialog setSweetIcon(Drawable drawable) {
        return setAttributeById(getId(ID_ICON), null, drawable, null);
    }

    public MLSweetDialog setSweetAnimations(int style_id) {
        if (style_id > 0) {
            super.getWindow().setWindowAnimations(style_id);
        }
        return this;
    }

    public MLSweetDialog setSweetDelay(long delay) {
        this.delay = delay;
        return this;
    }

    public MLSweetDialog setSweetCancelable(boolean flag) {
        super.setCancelable(flag);
        return this;
    }

    @Override
    public void show() {
        super.show();
        if (delay > 0) {
            resetDelayDismiss();

            delayTimer = new Timer();
            delayTask = new DelayDismissTask();
            delayTimer.schedule(delayTask, delay);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        resetDelayDismiss();
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialog);
        }
    }

    private void resetDelayDismiss() {
        if (delayTimer != null) {
            delayTimer.cancel();
            delayTimer = null;
        }
        if (delayTask != null) {
            delayTask.cancel();
            delayTask = null;
        }
    }

    private class DelayDismissTask extends TimerTask {
        @Override
        public void run() {
            if (isShowing()) {
                dismiss();
            }
        }
    }

    private OnDismissListener onDismissListener = null;

    @Override
    public void setOnDismissListener(OnDismissListener listener) {
        this.onDismissListener = listener;
    }

    public static abstract class OnClickListener implements View.OnClickListener {
        private MLSweetDialog dialog;

        public final void setSweetDialog(MLSweetDialog dialog) {
            this.dialog = dialog;
        }

        @Override
        public final void onClick(View v) {
            onClick(dialog, v);
            this.dialog = null;
        }

        public abstract void onClick(MLSweetDialog dialog, View v);
    }

    public static abstract class OnClickToDismissListener extends OnClickListener {

        @Override
        public final void onClick(MLSweetDialog dialog, View v) {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
            onClickAfterDismiss(v);
        }

        public abstract void onClickAfterDismiss(View v);
    }

}
