package jsc.exam.com.wheelview.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;

import jsc.exam.com.wheelview.R;

/**
 * <br>Email:1006368252@qq.com
 * <br>QQ:1006368252
 * <br><a href="https://github.com/JustinRoom/WheelViewDemo" target="_blank">https://github.com/JustinRoom/WheelViewDemo</a>
 *
 * @author jiangshicheng
 */
public class TextView3D extends android.support.v7.widget.AppCompatTextView {
    Matrix matrix = new Matrix();
    Camera camera = new Camera();
    float angle;
    public TextView3D(Context context) {
        this(context, null);
    }

    public TextView3D(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextView3D(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TextView3D, defStyleAttr, 0);
        angle = a.getFloat(R.styleable.TextView3D_angle, 0);
        a.recycle();
        setGravity(Gravity.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float cx = getWidth() / 2.0f;
        float cy = getHeight() / 2.0f;
        matrix.reset();
        camera.save();
        camera.rotateX(angle);
        camera.getMatrix(matrix);
        camera.restore();

        matrix.postTranslate(cx, cy);
        matrix.preTranslate(-cx, -cy);

        canvas.concat(matrix);
        super.onDraw(canvas);
    }
}
