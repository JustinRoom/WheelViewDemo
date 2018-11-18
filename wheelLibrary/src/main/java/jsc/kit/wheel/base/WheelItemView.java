package jsc.kit.wheel.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * <br>Email:1006368252@qq.com
 * <br>QQ:1006368252
 * <br><a href="https://github.com/JustinRoom/JSCKit" target="_blank">https://github.com/JustinRoom/JSCKit</a>
 *
 * @author jiangshicheng
 */
public class WheelItemView extends FrameLayout implements IViewAttrDelegate {

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
        wheelView = new WheelView(context);
        wheelMaskView = new WheelMaskView(context);
        addView(wheelView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(wheelMaskView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ViewGroup.LayoutParams params = wheelMaskView.getLayoutParams();
        params.height = wheelView.getMeasuredHeight();
        wheelMaskView.setLayoutParams(params);
        wheelMaskView.updateMask(wheelView.getShowCount(), wheelView.getItemHeight());
    }

    public WheelView getWheelView() {
        return wheelView;
    }

    public WheelMaskView getWheelMaskView() {
        return wheelMaskView;
    }
}
