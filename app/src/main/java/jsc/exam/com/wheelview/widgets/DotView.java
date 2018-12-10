package jsc.exam.com.wheelview.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.IntDef;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewOutlineProvider;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <br>Email:1006368252@qq.com
 * <br>QQ:1006368252
 * <br><a href="https://github.com/JustinRoom/WheelViewDemo" target="_blank">https://github.com/JustinRoom/WheelViewDemo</a>
 *
 * @author jiangshicheng
 */
public class DotView extends AppCompatTextView {

    public final static int CIRCULAR = 0;
    public final static int SQUARE = 1;
    public final static int ROUND_CORNER_SQUARE = 2;
    public final static int TRIANGLE = 3;
    @IntDef({CIRCULAR, SQUARE, ROUND_CORNER_SQUARE, TRIANGLE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DotShape {
    }

    int shape = CIRCULAR;
    float radius = 0;
    int unReadCount = 0;
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public DotView(Context context) {
        super(context);
        initAttr(context, null, 0);
    }

    public DotView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs, 0);
    }

    public DotView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs, defStyleAttr);
    }

    private void initAttr(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        setGravity(Gravity.CENTER);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setClipToOutline(true);
            setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    switch (shape){
                        case CIRCULAR:
                            outline.setOval(0, 0, view.getWidth(), view.getHeight());
                            break;
                        case SQUARE:
                            outline.setRect(0, 0, view.getWidth(), view.getHeight());
                            break;
                        case ROUND_CORNER_SQUARE:
                            outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), radius);
                            break;
                        case TRIANGLE:
                            Path path = new Path();
                            path.moveTo(view.getWidth() / 2.0f, 0);
                            path.lineTo(0, view.getHeight());
                            path.lineTo(view.getWidth(), view.getHeight());
                            path.close();
                            Log.i("DotView", "getOutline: isConvex =" + path.isConvex());
                            outline.setConvexPath(path);
                            break;
                    }
                }
            });
        } else {
            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.FILL);
        }
    }

    public int getUnReadCount() {
        return unReadCount;
    }

    public void setUnReadCount(@IntRange(from = 0) int unReadCount) {
        this.unReadCount = unReadCount;
        if (unReadCount > 99)
            setText("99+");
        else if (unReadCount > 0)
            setText(String.valueOf(unReadCount));
        else
            setText("");
    }

    public void setShape(@DotShape int shape) {
        setShape(shape, 0);
    }

    public void setShape(@DotShape int dotShape, float roundRadius) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.shape = dotShape;
            this.radius = roundRadius;
            invalidateOutline();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            float c = getWidth() / 2.0f;
            canvas.drawCircle(c, c, c, paint);
        }
        super.onDraw(canvas);
    }
}
