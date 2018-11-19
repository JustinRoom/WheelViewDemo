package jsc.kit.wheel.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import jsc.kit.wheel.R;
import jsc.kit.wheel.base.IWheel;
import jsc.kit.wheel.base.WheelItemView;

/**
 * <br>Email:1006368252@qq.com
 * <br>QQ:1006368252
 * <br><a href="https://github.com/JustinRoom/JSCKit" target="_blank">https://github.com/JustinRoom/JSCKit</a>
 *
 * @author jiangshicheng
 */
public class OneColumnWheelDialog<T extends IWheel> extends Dialog {

    private TextView tvTitle;
    private TextView tvCancel;
    private TextView tvOK;
    private WheelItemView wheelItemView;
    private T[] items;
    private WheelDialogInterface<T> negativeDialogInterface = null;
    private WheelDialogInterface<T> positiveDialogInterface = null;

    public OneColumnWheelDialog(@NonNull Context context) {
        this(context, R.style.WheelDialog);
    }

    private OneColumnWheelDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getWindow() != null) {
            getWindow().setGravity(Gravity.BOTTOM);
            getWindow().setBackgroundDrawable(null);
            getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.wheel_dialog_one_column);
        tvTitle = findViewById(R.id.tv_title);
        tvCancel = findViewById(R.id.tv_cancel);
        tvOK = findViewById(R.id.tv_ok);
        wheelItemView = findViewById(R.id.wheel_item_view);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wheelItemView.isScrolling())
                    return;

                if (negativeDialogInterface == null) {
                    dismiss();
                    return;
                }
                if (negativeDialogInterface.onClick(
                        0,
                        wheelItemView.getSelectedIndex(),
                        items[wheelItemView.getSelectedIndex()]
                )) {
                    dismiss();
                }
            }
        });
        tvOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wheelItemView.isScrolling())
                    return;
                if (positiveDialogInterface == null) {
                    dismiss();
                    return;
                }
                if (positiveDialogInterface.onClick(
                        1,
                        wheelItemView.getSelectedIndex(),
                        items[wheelItemView.getSelectedIndex()]
                )) {
                    dismiss();
                }
            }
        });
    }

    @Override
    public void show() {
        super.show();
        if (getWindow() != null) {
            getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    private void ensureIsShowing() {
        if (!isShowing())
            throw new IllegalStateException("Call show() first.");
    }

    public void setTitle(CharSequence title) {
        ensureIsShowing();
        tvTitle.setText(title);
    }

    public void setPositiveButton(CharSequence positive, WheelDialogInterface<T> positiveDialogInterface) {
        ensureIsShowing();
        tvOK.setText(positive);
        this.positiveDialogInterface = positiveDialogInterface;
    }

    public void setNegativeButton(CharSequence negative, WheelDialogInterface<T> negativeDialogInterface) {
        ensureIsShowing();
        tvCancel.setText(negative);
        this.negativeDialogInterface = negativeDialogInterface;
    }

    public void setItems(T[] items) {
        ensureIsShowing();
        this.items = items;
        wheelItemView.setItems(items);
    }

    public void setSelectedIndex(int index) {
        ensureIsShowing();
        wheelItemView.setSelectedIndex(index);
    }
}
