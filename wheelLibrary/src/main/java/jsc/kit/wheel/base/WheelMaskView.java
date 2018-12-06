package jsc.kit.wheel.base;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import jsc.kit.wheel.R;

/**
 * <br>Email:1006368252@qq.com
 * <br>QQ:1006368252
 * <br><a href="https://github.com/JustinRoom/WheelViewDemo" target="_blank">https://github.com/JustinRoom/WheelViewDemo</a>
 *
 * @author jiangshicheng
 */
public class WheelMaskView extends View {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int top = 0;
    private int bottom = 0;
    private int lineColor = 0x8F0000FF;

    public WheelMaskView(Context context) {
        super(context);
        initAttr(context, null, 0);
    }

    public WheelMaskView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs, 0);
    }

    public WheelMaskView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs, defStyleAttr);
    }

    public void initAttr(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WheelMaskView, defStyleAttr, 0);
        lineColor = a.getColor(R.styleable.WheelMaskView_wheelMaskLineColor, 0x8F0000FF);
        a.recycle();

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
    }

    public void updateMask(int heightCount, int itemHeight) {
        if (heightCount > 0) {
            int centerIndex = heightCount / 2;
            top = centerIndex * itemHeight;
            bottom = top + itemHeight;
        } else {
            top = 0;
            bottom = 0;
        }
        invalidate();
    }

    public void setLineColor(@ColorInt int lineColor) {
        this.lineColor = lineColor;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (top > 0 && bottom > 0) {
            paint.setColor(lineColor);
            canvas.drawLine(0, top, getWidth(), top, paint);
            canvas.drawLine(0, bottom, getWidth(), bottom, paint);
        }
    }
}
