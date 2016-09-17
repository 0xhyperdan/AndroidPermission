package cn.ml.base.widget.sample;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Property;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import cn.ml.base.R;

/**
 * Created by 7heaven on 15/3/14.
 */
public class MLShSwitchView extends View {

    private ObjectAnimator innerContentAnimator;
    private Property<MLShSwitchView, Float> innerContentProperty = new Property<MLShSwitchView, Float>(Float.class, "innerBound"){
        @Override
        public void set(MLShSwitchView sv, Float innerContentRate){
            sv.setInnerContentRate(innerContentRate);
        }

        @Override
        public Float get(MLShSwitchView sv){
            return sv.getInnerContentRate();
        }
    };

    private ObjectAnimator knobExpandAnimator;
    private Property<MLShSwitchView, Float> knobExpandProperty = new Property<MLShSwitchView, Float>(Float.class, "knobExpand"){
        @Override
        public void set(MLShSwitchView sv, Float knobExpandRate){
            sv.setKnobExpandRate(knobExpandRate);
        }

        @Override
        public Float get(MLShSwitchView sv){
            return sv.getKnobExpandRate();
        }
    };

    private ObjectAnimator knobMoveAnimator;
    private Property<MLShSwitchView, Float> knobMoveProperty = new Property<MLShSwitchView, Float>(Float.class, "knobMove"){
        @Override
        public void set(MLShSwitchView sv, Float knobMoveRate){
            sv.setKnobMoveRate(knobMoveRate);
        }

        @Override
        public Float get(MLShSwitchView sv){
            return sv.getKnobMoveRate();
        }
    };

    private GestureDetector gestureDetector;
    private GestureDetector.SimpleOnGestureListener gestureListener = new GestureDetector.SimpleOnGestureListener(){
        @Override
        public boolean onDown(MotionEvent event){

            preIsOn = isOn;

            return true;
        }

        @Override
        public void onShowPress(MotionEvent event){



            innerContentAnimator = ObjectAnimator.ofFloat(MLShSwitchView.this, innerContentProperty, innerContentRate, 0.0F);
            innerContentAnimator.setDuration(300L);
            innerContentAnimator.setInterpolator(new DecelerateInterpolator());

            innerContentAnimator.start();

            knobExpandAnimator = ObjectAnimator.ofFloat(MLShSwitchView.this, knobExpandProperty, knobExpandRate, 1.0F);
            knobExpandAnimator.setDuration(300L);
            knobExpandAnimator.setInterpolator(new DecelerateInterpolator());

            knobExpandAnimator.start();
        }

        @Override
        public boolean onSingleTapUp(MotionEvent event){



            isOn = knobState;

            if(!knobState){
                innerContentAnimator = ObjectAnimator.ofFloat(MLShSwitchView.this, innerContentProperty, innerContentRate, 1.0F);
                innerContentAnimator.setDuration(300L);
                innerContentAnimator.setInterpolator(new DecelerateInterpolator());

                innerContentAnimator.start();
            }

            knobExpandAnimator = ObjectAnimator.ofFloat(MLShSwitchView.this, knobExpandProperty, knobExpandRate, 0.0F);
            knobExpandAnimator.setDuration(300L);
            knobExpandAnimator.setInterpolator(new DecelerateInterpolator());

            knobExpandAnimator.start();

            if(MLShSwitchView.this.onSwitchStateChangeListener != null && isOn != preIsOn){
                MLShSwitchView.this.onSwitchStateChangeListener.onSwitchStateChange(isOn);
            }

            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            if(e2.getX() > centerX){
                if(!knobState){
                    knobMoveAnimator = ObjectAnimator.ofFloat(MLShSwitchView.this, knobMoveProperty, knobMoveRate, 1.0F);
                    knobMoveAnimator.setDuration(300L);
                    knobMoveAnimator.setInterpolator(new DecelerateInterpolator());
                    knobState = !knobState;

                    knobMoveAnimator.start();

                    innerContentAnimator = ObjectAnimator.ofFloat(MLShSwitchView.this, innerContentProperty, innerContentRate, 0.0F);
                    innerContentAnimator.setDuration(300L);
                    innerContentAnimator.setInterpolator(new DecelerateInterpolator());

                    innerContentAnimator.start();
                }
            }else{
                if(knobState){
                    knobMoveAnimator = ObjectAnimator.ofFloat(MLShSwitchView.this, knobMoveProperty, knobMoveRate, 0.0F);
                    knobMoveAnimator.setDuration(300L);
                    knobMoveAnimator.setInterpolator(new DecelerateInterpolator());
                    knobState = !knobState;

                    knobMoveAnimator.start();


                }
            }

            return true;
        }
    };


    private static final int intrinsicWidth = 0;
    private static final int intrinsicHeight = 0;

    private int width;
    private int height;

    private int centerX;
    private int centerY;

    private float cornerRadius;

    private int shadowSpace;
    private int outerStrokeWidth;

    private RectF knobBound;
    private float knobMaxExpandWidth;
    private float intrinsicKnobWidth;
    private float knobExpandRate;
    private float knobMoveRate;

    private boolean knobState;
    private boolean isOn;
    private boolean preIsOn;

    private RectF innerContentBound;
    private float innerContentRate = 1.0F;
    private float intrinsicInnerWidth;
    private float intrinsicInnerHeight;

    private int tintColor;
    private static final int backgroundColor = 0xFFCCCCCC;
    private int colorStep = backgroundColor;
    private static final int foregroundColor = 0xFFEFEFEF;

    private Paint paint;

    private RectF ovalForPath;
    private Path roundRectPath;

    public interface OnSwitchStateChangeListener{
        void onSwitchStateChange(boolean isOn);
    }

    private OnSwitchStateChangeListener onSwitchStateChangeListener;

    public MLShSwitchView(Context context){
        this(context, null);
    }

    public MLShSwitchView(Context context, AttributeSet attrs){
        this(context, attrs, 0);
    }

    public MLShSwitchView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MLShSwitchView);

        tintColor = ta.getColor(R.styleable.MLShSwitchView_tintColor, 0xFF9CE949);

        int defaultOuterStrokeWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1.5F, context.getResources().getDisplayMetrics());
        int defaultShadowSpace = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, context.getResources().getDisplayMetrics());

        outerStrokeWidth = ta.getDimensionPixelOffset(R.styleable.MLShSwitchView_outerStrokeWidth, defaultOuterStrokeWidth);
        shadowSpace = ta.getDimensionPixelOffset(R.styleable.MLShSwitchView_shadowSpace, defaultShadowSpace);

        ta.recycle();

        knobBound = new RectF();
        innerContentBound = new RectF();
        ovalForPath = new RectF();

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        roundRectPath = new Path();

        gestureDetector = new GestureDetector(context, gestureListener);
        gestureDetector.setIsLongpressEnabled(false);

        if(Build.VERSION.SDK_INT >= 11){
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    public void setOnSwitchStateChangeListener(OnSwitchStateChangeListener onSwitchStateChangeListener){
        this.onSwitchStateChangeListener = onSwitchStateChangeListener;
    }

    public OnSwitchStateChangeListener getOnSwitchStateChangeListener(){
        return this.onSwitchStateChangeListener;
    }

    void setInnerContentRate(float rate){
        this.innerContentRate = rate;

        float w = intrinsicInnerWidth / 2.0F * rate;
        float h = intrinsicInnerHeight / 2.0F * rate;

        this.innerContentBound.left = centerX - w;
        this.innerContentBound.top = centerY - h;
        this.innerContentBound.right = centerX + w;
        this.innerContentBound.bottom = centerY + h;

        invalidate();
    }

    float getInnerContentRate(){
        return this.innerContentRate;
    }

    void setKnobExpandRate(float rate){
        this.knobExpandRate = rate;

        float w = intrinsicKnobWidth + (knobMaxExpandWidth - intrinsicKnobWidth) * rate;

        boolean left = knobBound.left + knobBound.width() / 2 > centerX;

        if(left){
            knobBound.left = knobBound.right - w;
        }else{
            knobBound.right = knobBound.left + w;
        }

        invalidate();
    }

    float getKnobExpandRate(){
        return this.knobExpandRate;
    }

    void setKnobMoveRate(float rate){
        this.knobMoveRate = rate;

        float kw = knobBound.width();
        float w = (width - kw - ((shadowSpace + outerStrokeWidth) * 2)) * rate;

        this.colorStep = RGBColorTransform(rate, backgroundColor, tintColor);


        knobBound.left = shadowSpace + outerStrokeWidth + w;
        knobBound.right = knobBound.left + kw;

        invalidate();
    }

    float getKnobMoveRate(){
        return knobMoveRate;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);

        //make sure widget remain in a good appearance
        if((float) height / (float) width < 0.33333F){
            height = (int) ((float) width * 0.33333F);

            widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.getMode(widthMeasureSpec));
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.getMode(heightMeasureSpec));

            super.setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        }

        centerX = width / 2;
        centerY = height / 2;

        cornerRadius = centerY - shadowSpace;

        innerContentBound.left = outerStrokeWidth + shadowSpace;
        innerContentBound.top = outerStrokeWidth + shadowSpace;
        innerContentBound.right = width - outerStrokeWidth - shadowSpace;
        innerContentBound.bottom = height - outerStrokeWidth - shadowSpace;

        intrinsicInnerWidth = innerContentBound.width();
        intrinsicInnerHeight = innerContentBound.height();

        knobBound.left = outerStrokeWidth + shadowSpace;
        knobBound.top = outerStrokeWidth + shadowSpace;
        knobBound.right = height - outerStrokeWidth - shadowSpace;
        knobBound.bottom = height - outerStrokeWidth - shadowSpace;

        intrinsicKnobWidth = knobBound.height();
        knobMaxExpandWidth = (float) width * 0.7F;
        if(knobMaxExpandWidth > knobBound.width() * 1.25F){
            knobMaxExpandWidth = knobBound.width() * 1.25F;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){

        switch(event.getAction()){
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if(!knobState){
                    innerContentAnimator = ObjectAnimator.ofFloat(MLShSwitchView.this, innerContentProperty, innerContentRate, 1.0F);
                    innerContentAnimator.setDuration(300L);
                    innerContentAnimator.setInterpolator(new DecelerateInterpolator());

                    innerContentAnimator.start();
                }

                knobExpandAnimator = ObjectAnimator.ofFloat(MLShSwitchView.this, knobExpandProperty, knobExpandRate, 0.0F);
                knobExpandAnimator.setDuration(300L);
                knobExpandAnimator.setInterpolator(new DecelerateInterpolator());

                knobExpandAnimator.start();

                isOn = knobState;

                if(MLShSwitchView.this.onSwitchStateChangeListener != null && isOn != preIsOn){
                    MLShSwitchView.this.onSwitchStateChangeListener.onSwitchStateChange(isOn);
                }

                break;
        }

        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);

        //background
        paint.setColor(colorStep);
        paint.setStyle(Paint.Style.FILL);

        drawRoundRect(shadowSpace, shadowSpace, width - shadowSpace, height - shadowSpace, cornerRadius, canvas, paint);

        //innerContent
        paint.setColor(foregroundColor);
        drawRoundRect(innerContentBound, innerContentBound.height() / 2, canvas, paint);

        //knob
        paint.setShadowLayer(shadowSpace / 2, 0, shadowSpace / 2, 0x44000000);
        drawRoundRect(knobBound, cornerRadius - outerStrokeWidth, canvas, paint);
        paint.setShadowLayer(0, 0, 0, 0);

        paint.setColor(backgroundColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);

        drawRoundRect(knobBound, cornerRadius - outerStrokeWidth, canvas, paint);

    }

    private void drawRoundRect(RectF bound, float radius, Canvas canvas, Paint paint){
        drawRoundRect(bound.left, bound.top, bound.right, bound.bottom, radius, canvas, paint);
    }

    private void drawRoundRect(float left, float top, float right, float bottom, float radius, Canvas canvas, Paint paint){
        roundRectPath.reset();

        float radiusSize = radius * 2;

        roundRectPath.moveTo(left, top + radius);

        ovalForPath.set(left, top, left + radiusSize, top + radiusSize);
        roundRectPath.arcTo(ovalForPath, 180, 90, false);
        roundRectPath.lineTo(right - radius, top);
        ovalForPath.set(right - radiusSize, top, right, top + radiusSize);
        roundRectPath.arcTo(ovalForPath, 270, 90, false);
        roundRectPath.lineTo(right, bottom - radius);
        ovalForPath.set(right - radiusSize, bottom - radiusSize, right, bottom);
        roundRectPath.arcTo(ovalForPath, 0, 90, false);
        roundRectPath.lineTo(left + radius, bottom);
        ovalForPath.set(left, bottom - radiusSize, left + radiusSize, bottom);
        roundRectPath.arcTo(ovalForPath, 90, 90, false);
        roundRectPath.close();

        canvas.drawPath(roundRectPath, paint);
    }

    //seperate RGB channels and calculate new value for each channel
    //ignore alpha channel
    private int RGBColorTransform(float progress, int fromColor, int toColor) {
        int or = (fromColor >> 16) & 0xFF;
        int og = (fromColor >> 8) & 0xFF;
        int ob = fromColor & 0xFF;

        int nr = (toColor >> 16) & 0xFF;
        int ng = (toColor >> 8) & 0xFF;
        int nb = toColor & 0xFF;

        int rGap = (int) ((float) (nr - or) * progress);
        int gGap = (int) ((float) (ng - og) * progress);
        int bGap = (int) ((float) (nb - ob) * progress);

        return 0xFF000000 | ((or + rGap) << 16) | ((og + gGap) << 8) | (ob + bGap);

    }
    /**
     * Marcello 修改
     */
    public void open(){
    	isOn =true;
        knobMoveAnimator = ObjectAnimator.ofFloat(MLShSwitchView.this, knobMoveProperty, knobMoveRate, 1.0F);
        knobMoveAnimator.setDuration(300L);
        knobMoveAnimator.setInterpolator(new DecelerateInterpolator());
        knobState = !knobState;

        knobMoveAnimator.start();

        innerContentAnimator = ObjectAnimator.ofFloat(MLShSwitchView.this, innerContentProperty, innerContentRate, 0.0F);
        innerContentAnimator.setDuration(300L);
        innerContentAnimator.setInterpolator(new DecelerateInterpolator());

        innerContentAnimator.start();
    }
    public void close(){
    	isOn =false;
        knobMoveAnimator = ObjectAnimator.ofFloat(MLShSwitchView.this, knobMoveProperty, knobMoveRate, 0.0F);
        knobMoveAnimator.setDuration(300L);
        knobMoveAnimator.setInterpolator(new DecelerateInterpolator());
        knobState = !knobState;

        knobMoveAnimator.start();
        
        innerContentAnimator = ObjectAnimator.ofFloat(MLShSwitchView.this, innerContentProperty, innerContentRate, 1.0F);
        innerContentAnimator.setDuration(300L);
        innerContentAnimator.setInterpolator(new DecelerateInterpolator());

        innerContentAnimator.start();
    }
}
