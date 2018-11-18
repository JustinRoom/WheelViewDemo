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
 *  * <ul>
 *  * <li>{@link R.styleable#WheelItemView_wheelTextColor}——the color of item showing text</li>
 *  * <li>{@link R.styleable#WheelItemView_wheelTextSize}——the size of item showing text</li>
 *  * <li>{@link R.styleable#WheelItemView_wheelShowCount}——the show count</li>
 *  * <li>{@link R.styleable#WheelItemView_wheelTotalOffsetX}——the horizontal offset</li>
 *  * <li>{@link R.styleable#WheelItemView_wheelItemVerticalSpace}——the vertical space of two items</li>
 *  * <li>{@link R.styleable#WheelItemView_wheelMaskLineColor}——the line color of selected item</li>
 *  * </ul>
 *
 * <br>Email:1006368252@qq.com
 * <br>QQ:1006368252
 * <br><a href="https://github.com/JustinRoom/JSCKit" target="_blank">https://github.com/JustinRoom/JSCKit</a>
 *
 * @author jiangshicheng
 */
public class WheelItemView extends FrameLayout implements IViewAttrDelegate, IWheelViewSetting {

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

    @Override
    public void initAttr(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WheelItemView, defStyleAttr, 0);
        float defaultTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 15, getResources().getDisplayMetrics());
        int textColor = a.getColor(R.styleable.WheelItemView_wheelTextColor, 0xFF333333);
        float textSize = a.getDimension(R.styleable.WheelItemView_wheelTextSize, defaultTextSize);
        int showCount = a.getInt(R.styleable.WheelItemView_wheelShowCount, 7);
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

    public void  setMaskLineColor(@ColorInt int color) {
        wheelMaskView.setLineColor(color);
    }

    public WheelView getWheelView() {
        return wheelView;
    }

    public WheelMaskView getWheelMaskView() {
        return wheelMaskView;
    }
}
