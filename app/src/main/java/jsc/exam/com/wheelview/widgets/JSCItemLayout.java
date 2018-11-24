package jsc.exam.com.wheelview.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import jsc.exam.com.wheelview.R;
import jsc.exam.com.wheelview.utils.CompatResourceUtils;

/**
 * <br>Email:1006368252@qq.com
 * <br>QQ:1006368252
 * <br><a href="https://github.com/JustinRoom/WheelViewDemo" target="_blank">https://github.com/JustinRoom/WheelViewDemo</a>
 *
 * @author jiangshicheng
 */
public class JSCItemLayout extends FrameLayout {

    private ImageView iconView;
    private TextView labelView;
    private TextView valueView;
    private DotView dotView;
    private ImageView arrowView;

    public JSCItemLayout(Context context) {
        super(context);
        initAttr(context, null, 0);
    }

    public JSCItemLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs, 0);
    }

    public JSCItemLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs, defStyleAttr);
    }

    private void initAttr(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.JSCItemLayout, defStyleAttr, 0);

        LayoutParams contentParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        contentParams.gravity = Gravity.CENTER_VERTICAL;
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(Gravity.CENTER_VERTICAL);
        addView(layout, contentParams);

        //icon
        iconView = new AppCompatImageView(context);
        iconView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        layout.addView(iconView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        iconView.setImageResource(a.getResourceId(R.styleable.JSCItemLayout_il_icon, R.drawable.kit_ic_assignment_blue_24dp));

        //label
        labelView = new TextView(context);
        labelView.setTextColor(0xFF666666);
        labelView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        layout.addView(labelView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        if (a.hasValue(R.styleable.JSCItemLayout_il_label)) {
            labelView.setText(a.getString(R.styleable.JSCItemLayout_il_label));
        }

        //value
        valueView = new TextView(context);
        valueView.setTextColor(0xFF333333);
        valueView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        layout.addView(valueView, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        if (a.hasValue(R.styleable.JSCItemLayout_il_value)) {
            valueView.setText(a.getString(R.styleable.JSCItemLayout_il_value));
        }

        //red dot
        int size = CompatResourceUtils.getDimensionPixelSize(this, R.dimen.space_8);
        dotView = new DotView(context);
        dotView.setBackgroundColor(Color.RED);
        dotView.setTextColor(Color.WHITE);
        if (a.hasValue(R.styleable.JSCItemLayout_il_dotSize)){
            size = a.getDimensionPixelSize(R.styleable.JSCItemLayout_il_dotSize, 0);
        }
        dotView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        layout.addView(dotView, new LinearLayout.LayoutParams(size, size));
        showDotView(a.getBoolean(R.styleable.JSCItemLayout_il_showDot, false));

        //right arrow
        arrowView = new AppCompatImageView(context);
        arrowView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        layout.addView(arrowView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        arrowView.setImageResource(a.getResourceId(R.styleable.JSCItemLayout_il_arrowIcon, R.drawable.kit_ic_chevron_right_gray_24dp));

        a.recycle();
    }

    public ImageView getIconView() {
        return iconView;
    }

    public TextView getLabelView() {
        return labelView;
    }

    public TextView getValueView() {
        return valueView;
    }

    public DotView getDotView() {
        return dotView;
    }

    public ImageView getArrowView() {
        return arrowView;
    }

    public void showIconView(boolean show) {
        iconView.setVisibility(show ? VISIBLE : GONE);
    }

    public void showDotView(boolean show) {
        dotView.setVisibility(show ? VISIBLE : GONE);
    }

    public void showArrowView(boolean show){
        arrowView.setVisibility(show ? VISIBLE : GONE);
    }

    public int getUnreadCount() {
        return dotView.getUnReadCount();
    }

    public void setUnreadCount(int unreadCount) {
        dotView.setUnReadCount(unreadCount);
    }

    public void setIcon(@DrawableRes int resId) {
        iconView.setImageResource(resId);
    }

    public void setLabel(CharSequence label) {
        labelView.setText(label);
    }

    public void setLabel(@StringRes int resId) {
        labelView.setText(resId);
    }

    public void setValue(CharSequence label) {
        valueView.setText(label);
    }

    public void setValue(@StringRes int resId) {
        valueView.setText(resId);
    }

    public void setArrow(@DrawableRes int resId) {
        arrowView.setImageResource(resId);
    }
}
