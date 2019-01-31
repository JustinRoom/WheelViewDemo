package jsc.kit.wheel.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import jsc.kit.wheel.R;
import jsc.kit.wheel.base.IWheel;
import jsc.kit.wheel.base.WheelItemView;

/**
 * date time picker dialog
 * <br>Email:1006368252@qq.com
 * <br>QQ:1006368252
 * <br><a href="https://github.com/JustinRoom/WheelViewDemo" target="_blank">https://github.com/JustinRoom/WheelViewDemo</a>
 *
 * @author jiangshicheng
 */
public class ColumnWheelDialog<T0 extends IWheel, T1 extends IWheel, T2 extends IWheel, T3 extends IWheel, T4 extends IWheel> extends Dialog {

    private TextView tvTitle;
    private TextView tvCancel;
    private TextView tvOK;

    private WheelItemView wheelItemView0;
    private WheelItemView wheelItemView1;
    private WheelItemView wheelItemView2;
    private WheelItemView wheelItemView3;
    private WheelItemView wheelItemView4;

    private T0[] items0;
    private T1[] items1;
    private T2[] items2;
    private T3[] items3;
    private T4[] items4;
    private CharSequence clickTipsWhenIsScrolling = "Scrolling, wait a minute.";

    private OnClickCallBack<T0, T1, T2, T3, T4> cancelCallBack = null;
    private OnClickCallBack<T0, T1, T2, T3, T4> okCallBack = null;

    private boolean isViewInitialized = false;
    private float textSize;
    private int itemVerticalSpace;

    public ColumnWheelDialog(@NonNull Context context) {
        this(context, R.style.WheelDialog);
    }

    private ColumnWheelDialog(@NonNull Context context, int themeResId) {
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
        setContentView(R.layout.wheel_dialog_base);
        initView();
    }

    private void initView() {
        isViewInitialized = true;
        LinearLayout lyPickerContainer = findViewById(R.id.wheel_id_picker_container);
        wheelItemView0 = new WheelItemView(lyPickerContainer.getContext());
        lyPickerContainer.addView(wheelItemView0, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        wheelItemView1 = new WheelItemView(lyPickerContainer.getContext());
        lyPickerContainer.addView(wheelItemView1, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        wheelItemView2 = new WheelItemView(lyPickerContainer.getContext());
        lyPickerContainer.addView(wheelItemView2, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        wheelItemView3 = new WheelItemView(lyPickerContainer.getContext());
        lyPickerContainer.addView(wheelItemView3, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        wheelItemView4 = new WheelItemView(lyPickerContainer.getContext());
        lyPickerContainer.addView(wheelItemView4, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        if (textSize > 0) {
            wheelItemView0.setTextSize(textSize);
            wheelItemView1.setTextSize(textSize);
            wheelItemView2.setTextSize(textSize);
            wheelItemView3.setTextSize(textSize);
            wheelItemView4.setTextSize(textSize);
        }
        if (itemVerticalSpace > 0) {
            wheelItemView0.setItemVerticalSpace(itemVerticalSpace);
            wheelItemView1.setItemVerticalSpace(itemVerticalSpace);
            wheelItemView2.setItemVerticalSpace(itemVerticalSpace);
            wheelItemView3.setItemVerticalSpace(itemVerticalSpace);
            wheelItemView4.setItemVerticalSpace(itemVerticalSpace);
        }

        tvTitle = findViewById(R.id.wheel_id_title_bar_title);
        tvCancel = findViewById(R.id.wheel_id_title_bar_cancel);
        tvOK = findViewById(R.id.wheel_id_title_bar_ok);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cancelCallBack == null) {
                    dismiss();
                    return;
                }
                if (!cancelCallBack.callBack(
                        v,
                        wheelItemView0.isShown() ? items0[wheelItemView0.getSelectedIndex()] : null,
                        wheelItemView1.isShown() ? items1[wheelItemView1.getSelectedIndex()] : null,
                        wheelItemView2.isShown() ? items2[wheelItemView2.getSelectedIndex()] : null,
                        wheelItemView3.isShown() ? items3[wheelItemView3.getSelectedIndex()] : null,
                        wheelItemView4.isShown() ? items4[wheelItemView4.getSelectedIndex()] : null
                )) dismiss();
            }
        });
        tvOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (okCallBack == null) {
                    dismiss();
                    return;
                }
                if (isScrolling()) {
                    if (!TextUtils.isEmpty(clickTipsWhenIsScrolling))
                        Toast.makeText(v.getContext(), clickTipsWhenIsScrolling, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!okCallBack.callBack(
                        v,
                        wheelItemView0.isShown() ? items0[wheelItemView0.getSelectedIndex()] : null,
                        wheelItemView1.isShown() ? items1[wheelItemView1.getSelectedIndex()] : null,
                        wheelItemView2.isShown() ? items2[wheelItemView2.getSelectedIndex()] : null,
                        wheelItemView3.isShown() ? items3[wheelItemView3.getSelectedIndex()] : null,
                        wheelItemView4.isShown() ? items4[wheelItemView4.getSelectedIndex()] : null
                )) dismiss();
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

    public void setClickTipsWhenIsScrolling(CharSequence clickTipsWhenIsScrolling) {
        this.clickTipsWhenIsScrolling = clickTipsWhenIsScrolling;
    }

    public void setTitle(CharSequence title) {
        ensureIsViewInitialized();
        tvTitle.setText(title);
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public void setItemVerticalSpace(int itemVerticalSpace) {
        this.itemVerticalSpace = itemVerticalSpace;
    }

    public void setOKButton(CharSequence ok, OnClickCallBack<T0, T1, T2, T3, T4> okCallBack) {
        ensureIsViewInitialized();
        tvOK.setText(ok);
        this.okCallBack = okCallBack;
    }

    public void setCancelButton(CharSequence cancel, OnClickCallBack<T0, T1, T2, T3, T4> cancelCallBack) {
        ensureIsViewInitialized();
        tvCancel.setText(cancel);
        this.cancelCallBack = cancelCallBack;
    }

    public void setItems(T0[] items0, T1[] items1, T2[] items2, T3[] items3, T4[] items4) {
        setItems(items0, items1, items2, items3, items4, -1);
    }

    /**
     *
     * @param items0 items0
     * @param items1 items1
     * @param items2 items2
     * @param items3 items3
     * @param items4 items4
     * @param totalOffsetX the total offset of x axis. The default value is 4dp.
     */
    public void setItems(T0[] items0, T1[] items1, T2[] items2, T3[] items3, T4[] items4, int totalOffsetX) {
        ensureIsViewInitialized();
        if (totalOffsetX == -1) {
            totalOffsetX = getContext().getResources().getDimensionPixelSize(R.dimen.wheel_picker_total_offset_x);
        }
        this.items0 = items0;
        this.items1 = items1;
        this.items2 = items2;
        this.items3 = items3;
        this.items4 = items4;
        updateShowPicker(wheelItemView0, items0);
        updateShowPicker(wheelItemView1, items1);
        updateShowPicker(wheelItemView2, items2);
        updateShowPicker(wheelItemView3, items3);
        updateShowPicker(wheelItemView4, items4);
        updateOffsetX(totalOffsetX);
    }

    public void setSelected(int selected0, int selected1, int selected2, int selected3, int selected4) {
        executeSelected(wheelItemView0, selected0);
        executeSelected(wheelItemView1, selected1);
        executeSelected(wheelItemView2, selected2);
        executeSelected(wheelItemView3, selected3);
        executeSelected(wheelItemView4, selected4);
    }

    private boolean isScrolling() {
        return isScrolling(wheelItemView0)
                || isScrolling(wheelItemView1)
                || isScrolling(wheelItemView2)
                || isScrolling(wheelItemView3)
                || isScrolling(wheelItemView4);
    }

    private void ensureIsViewInitialized() {
        if (!isViewInitialized)
            throw new IllegalStateException("View wasn't initialized, call show() first.");
    }

    private void updateShowPicker(WheelItemView wheelItemView, IWheel[] items) {
        boolean hide = (items == null || items.length == 0);
        wheelItemView.setVisibility(hide ? View.GONE : View.VISIBLE);
        if (!hide)
            wheelItemView.setItems(items);
    }

    private void executeSelected(WheelItemView view, int selectedIndex) {
        if (view.isShown())
            view.setSelectedIndex(selectedIndex);
    }

    private void updateOffsetX(int totalOffsetX) {
        List<WheelItemView> views = new ArrayList<>();
        addVisibleView(views, wheelItemView0);
        addVisibleView(views, wheelItemView1);
        addVisibleView(views, wheelItemView2);
        addVisibleView(views, wheelItemView3);
        addVisibleView(views, wheelItemView4);
        for (int i = 0; i < views.size(); i++) {
            views.get(i).setTotalOffsetX(0);
        }
        if (views.size() > 2) {
            views.get(0).setTotalOffsetX(totalOffsetX);
            views.get(views.size() - 1).setTotalOffsetX(-totalOffsetX);
        }
    }

    private void addVisibleView(List<WheelItemView> views, WheelItemView v) {
        if (v.isShown())
            views.add(v);
    }

    private boolean isScrolling(WheelItemView view) {
        return view.isShown() && view.isScrolling();
    }

    public interface OnClickCallBack<D0, D1, D2, D3, D4> {
        boolean callBack(View v, @Nullable D0 item0, @Nullable D1 item1, @Nullable D2 item2, @Nullable D3 item3, @Nullable D4 item4);
    }
}
