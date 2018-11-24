package jsc.kit.wheel.base;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import jsc.kit.wheel.R;

/**
 * Wheel view with selected mask.
 *
 * <br><br>
 *  * {@code attrs:}
 *  * <br>{@link R.styleable#WheelItemView_wheelTextColor}
 *  * <br>{@link R.styleable#WheelItemView_wheelTextSize}
 *  * <br>{@link R.styleable#WheelItemView_wheelShowCount}
 *  * <br>{@link R.styleable#WheelItemView_wheelTotalOffsetX}
 *  * <br>{@link R.styleable#WheelItemView_wheelItemVerticalSpace}
 *  * <br>{@link R.styleable#WheelItemView_wheelMaskLineColor}
 *
 * <br>Email:1006368252@qq.com
 * <br>QQ:1006368252
 * <br><a href="https://github.com/JustinRoom/WheelViewDemo" target="_blank">https://github.com/JustinRoom/WheelViewDemo</a>
 *
 * @author jiangshicheng
 */
public class WheelItemView extends FrameLayout implements IWheelViewSetting {

    private WheelView wheelView;
    private WheelMaskView wheelMaskView;

    public WheelItemView( @NonNull Context context) {
        super(context);
        initAttr(context, null, 0);
    }

    public WheelItemView( @NonNull Context context,  @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs, 0);
    }

    public WheelItemView( @NonNull Context context,  @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs, defStyleAttr);
    }

    private void initAttr(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WheelItemView, defStyleAttr, 0);
        float defaultTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 14, getResources().getDisplayMetrics());
        int textColor = a.getColor(R.styleable.WheelItemView_wheelTextColor, 0xFF333333);
        float textSize = a.getDimension(R.styleable.WheelItemView_wheelTextSize, defaultTextSize);
        int showCount = a.getInt(R.styleable.WheelItemView_wheelShowCount, 5);
        int totalOffsetX = a.getDimensionPixelSize(R.styleable.WheelItemView_wheelTotalOffsetX, 0);
        int itemVerticalSpace = a.getDimensionPixelSize(R.styleable.WheelItemView_wheelItemVerticalSpace, 32);
        int maskLineColor = a.getColor(R.styleable.WheelItemView_wheelMaskLineColor, Color.BLUE);
        a.recycle();

        wheelView = new WheelView(context);
        wheelMaskView = new WheelMaskView(context);
        addView(wheelView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(wheelMaskView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        setTextColor(textColor);
        setTextSize(textSize);
        setShowCount(showCount);
        setTotalOffsetX(totalOffsetX);
        setItemVerticalSpace(itemVerticalSpace);

        setMaskLineColor(maskLineColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ViewGroup.LayoutParams params = wheelMaskView.getLayoutParams();
        params.height = wheelView.getMeasuredHeight();
        wheelMaskView.setLayoutParams(params);
        wheelMaskView.updateMask(wheelView.getShowCount(), wheelView.getItemHeight());
    }

    @Override
    public void setTextSize(float textSize) {
        wheelView.setTextSize(textSize);
    }

    @Override
    public void setTextColor(@ColorInt int textColor) {
        wheelView.setTextColor(textColor);
    }

    @Override
    public void setShowCount(int showCount) {
        wheelView.setShowCount(showCount);
    }

    @Override
    public void setTotalOffsetX(int totalOffsetX) {
        wheelView.setTotalOffsetX(totalOffsetX);
    }

    @Override
    public void setItemVerticalSpace(int itemVerticalSpace) {
        wheelView.setItemVerticalSpace(itemVerticalSpace);
    }

    @Override
    public void setItems(IWheel[] items) {
        wheelView.setItems(items);
    }

    @Override
    public int getSelectedIndex() {
        return wheelView.getSelectedIndex();
    }

    @Override
    public void setSelectedIndex(int targetIndexPosition) {
        setSelectedIndex(targetIndexPosition, true);
    }

    @Override
    public void setSelectedIndex(int targetIndexPosition, boolean withAnimation) {
        wheelView.setSelectedIndex(targetIndexPosition, withAnimation);
    }

    @Override
    public void setOnSelectedListener(WheelView.OnSelectedListener onSelectedListener) {
        wheelView.setOnSelectedListener(onSelectedListener);
    }

    public void  setMaskLineColor(@ColorInt int color) {
        wheelMaskView.setLineColor(color);
    }

    public WheelView getWheelView() {
        return wheelView;
    }

    public WheelMaskView getWheelMaskView() {
        return wheelMaskView;
    }

    @Override
    public boolean isScrolling() {
        return wheelView.isScrolling();
    }
}
