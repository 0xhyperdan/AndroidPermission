package cn.ml.base.widget.sample;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * 解决ScrollView ListView 冲突
 *
 * @author Marcello
 */
public class MLNoScrollListView extends ListView {

    public MLNoScrollListView(Context context) {
        super(context);
    }

    public MLNoScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MLNoScrollListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }


}
