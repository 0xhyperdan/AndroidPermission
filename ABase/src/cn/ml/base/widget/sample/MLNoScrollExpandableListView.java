package cn.ml.base.widget.sample;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;

/**
 * 解决ScrollView ListView 冲突
 *
 * @author Marcello
 */
public class MLNoScrollExpandableListView extends ExpandableListView {

    public MLNoScrollExpandableListView(Context context) {
        super(context);
    }

    public MLNoScrollExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MLNoScrollExpandableListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }


}
