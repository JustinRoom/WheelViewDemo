package jsc.kit.wheel.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Calendar;
import java.util.Date;

import jsc.kit.wheel.R;
import jsc.kit.wheel.base.WheelItemView;
import jsc.kit.wheel.base.WheelView;

/**
 * date time picker dialog
 * <br>Email:1006368252@qq.com
 * <br>QQ:1006368252
 * <br><a href="https://github.com/JustinRoom/WheelViewDemo" target="_blank">https://github.com/JustinRoom/WheelViewDemo</a>
 *
 * @author jiangshicheng
 */
public class DateTimeWheelDialog extends Dialog {

    public static final int SHOW_YEAR = 0;
    public static final int SHOW_YEAR_MONTH = 1;
    public static final int SHOW_YEAR_MONTH_DAY = 2;
    public static final int SHOW_YEAR_MONTH_DAY_HOUR = 3;
    public static final int SHOW_YEAR_MONTH_DAY_HOUR_MINUTE = 4;

    @IntDef({
            SHOW_YEAR,
            SHOW_YEAR_MONTH,
            SHOW_YEAR_MONTH_DAY,
            SHOW_YEAR_MONTH_DAY_HOUR,
            SHOW_YEAR_MONTH_DAY_HOUR_MINUTE
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface ShowConfig {
    }

    private final String TAG = "DateTimeWheelDialog";
    private final int MIN_MONTH = 1;
    private final int MAX_MONTH = 12;
    private final int MIN_DAY = 1;
    private final int MIN_HOUR = 0;
    private final int MAX_HOUR = 23;
    private final int MIN_MINUTE = 0;
    private final int MAX_MINUTE = 59;
    private TextView tvTitle;
    private TextView tvCancel;
    private TextView tvOK;
    private CharSequence clickTipsWhenIsScrolling = "Scrolling, wait a minute.";

    private WheelItemView yearWheelItemView;
    private WheelItemView monthWheelItemView;
    private WheelItemView dayWheelItemView;
    private WheelItemView hourWheelItemView;
    private WheelItemView minuteWheelItemView;

    private DateItem[] yearItems;
    private DateItem[] monthItems;
    private DateItem[] dayItems;
    private DateItem[] hourItems;
    private DateItem[] minuteItems;

    private Calendar startCalendar = Calendar.getInstance();
    private Calendar endCalendar = Calendar.getInstance();
    private Calendar selectedCalendar = Calendar.getInstance();
    private OnClickCallBack cancelCallBack = null;
    private OnClickCallBack okCallBack = null;

    private boolean isViewInitialized = false;
    private boolean keepLastSelected = false;
    private int showConfig = SHOW_YEAR_MONTH_DAY_HOUR_MINUTE;

    public DateTimeWheelDialog(@NonNull Context context) {
        this(context, R.style.WheelDialog);
    }

    private DateTimeWheelDialog(@NonNull Context context, int themeResId) {
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
        //year
        yearWheelItemView = new WheelItemView(lyPickerContainer.getContext());
        lyPickerContainer.addView(yearWheelItemView, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        //month
        monthWheelItemView = new WheelItemView(lyPickerContainer.getContext());
        lyPickerContainer.addView(monthWheelItemView, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        //day
        dayWheelItemView = new WheelItemView(lyPickerContainer.getContext());
        lyPickerContainer.addView(dayWheelItemView, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        //hour
        hourWheelItemView = new WheelItemView(lyPickerContainer.getContext());
        lyPickerContainer.addView(hourWheelItemView, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        //minute
        minuteWheelItemView = new WheelItemView(lyPickerContainer.getContext());
        lyPickerContainer.addView(minuteWheelItemView, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

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
                if (!cancelCallBack.callBack(v, selectedCalendar.getTime())) dismiss();
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
                if (!okCallBack.callBack(v, selectedCalendar.getTime())) dismiss();
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

    public void setOKButton(CharSequence ok, OnClickCallBack okCallBack) {
        ensureIsViewInitialized();
        tvOK.setText(ok);
        this.okCallBack = okCallBack;
    }

    public void setCancelButton(CharSequence cancel, OnClickCallBack cancelCallBack) {
        ensureIsViewInitialized();
        tvCancel.setText(cancel);
        this.cancelCallBack = cancelCallBack;
    }

    public void configShowUI(@ShowConfig int config) {
        configShowUI(config, -1);
    }

    public void configShowUI(@ShowConfig int showConfig, int totalOffsetX) {
        ensureIsViewInitialized();
        if (totalOffsetX == -1) {
            totalOffsetX = getContext().getResources().getDimensionPixelSize(R.dimen.wheel_picker_total_offset_x);
        }
        this.showConfig = showConfig;
        yearWheelItemView.setTotalOffsetX(0);
        monthWheelItemView.setTotalOffsetX(0);
        dayWheelItemView.setTotalOffsetX(0);
        hourWheelItemView.setTotalOffsetX(0);
        minuteWheelItemView.setTotalOffsetX(0);
        switch (showConfig) {
            case SHOW_YEAR:
                yearWheelItemView.setVisibility(View.VISIBLE);
                monthWheelItemView.setVisibility(View.GONE);
                dayWheelItemView.setVisibility(View.GONE);
                hourWheelItemView.setVisibility(View.GONE);
                minuteWheelItemView.setVisibility(View.GONE);
                break;
            case SHOW_YEAR_MONTH:
                yearWheelItemView.setVisibility(View.VISIBLE);
                monthWheelItemView.setVisibility(View.VISIBLE);
                dayWheelItemView.setVisibility(View.GONE);
                hourWheelItemView.setVisibility(View.GONE);
                minuteWheelItemView.setVisibility(View.GONE);
                break;
            case SHOW_YEAR_MONTH_DAY:
                yearWheelItemView.setVisibility(View.VISIBLE);
                monthWheelItemView.setVisibility(View.VISIBLE);
                dayWheelItemView.setVisibility(View.VISIBLE);
                hourWheelItemView.setVisibility(View.GONE);
                minuteWheelItemView.setVisibility(View.GONE);
                yearWheelItemView.setTotalOffsetX(totalOffsetX);
                dayWheelItemView.setTotalOffsetX(-totalOffsetX);
                break;
            case SHOW_YEAR_MONTH_DAY_HOUR:
                yearWheelItemView.setVisibility(View.VISIBLE);
                monthWheelItemView.setVisibility(View.VISIBLE);
                dayWheelItemView.setVisibility(View.VISIBLE);
                hourWheelItemView.setVisibility(View.VISIBLE);
                minuteWheelItemView.setVisibility(View.GONE);
                yearWheelItemView.setTotalOffsetX(totalOffsetX);
                hourWheelItemView.setTotalOffsetX(-totalOffsetX);
                break;
            case SHOW_YEAR_MONTH_DAY_HOUR_MINUTE:
                yearWheelItemView.setVisibility(View.VISIBLE);
                monthWheelItemView.setVisibility(View.VISIBLE);
                dayWheelItemView.setVisibility(View.VISIBLE);
                hourWheelItemView.setVisibility(View.VISIBLE);
                minuteWheelItemView.setVisibility(View.VISIBLE);
                yearWheelItemView.setTotalOffsetX(totalOffsetX);
                minuteWheelItemView.setTotalOffsetX(-totalOffsetX);
                break;
        }
    }

    public void setDateArea(@NonNull Date startDate, @NonNull Date endDate, boolean keepLastSelected) {
        ensureIsViewInitialized();
        if (startDate.after(endDate))
            throw new IllegalArgumentException("start date should be before end date");
        startCalendar.setTime(startDate);
        endCalendar.setTime(endDate);
        selectedCalendar.setTimeInMillis(startDate.getTime());
        this.keepLastSelected = keepLastSelected;
        initAreaDate();
    }

    public void updateSelectedDate(@NonNull Date selectedDate) {
        ensureIsViewInitialized();
        if (selectedDate.before(startCalendar.getTime()) || selectedDate.after(endCalendar.getTime()))
            throw new IllegalArgumentException("selected date must be between start date and end date");
        selectedCalendar.setTime(selectedDate);
        initSelectedDate();
        initOnScrollListener();
    }

    private void initAreaDate() {
        int startYear = startCalendar.get(Calendar.YEAR);
        int endYear = endCalendar.get(Calendar.YEAR);
        int startMonth = startCalendar.get(Calendar.MONTH) + 1;
        int startDay = startCalendar.get(Calendar.DAY_OF_MONTH);
        int startHour = startCalendar.get(Calendar.HOUR_OF_DAY);
        int startMinute = startCalendar.get(Calendar.MINUTE);

        yearItems = updateItems(DateItem.TYPE_YEAR, startYear, endYear);
        monthItems = updateItems(DateItem.TYPE_MONTH, startMonth, MAX_MONTH);
        int dayActualMaximum = startCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        dayItems = updateItems(DateItem.TYPE_DAY, startDay, dayActualMaximum);
        hourItems = updateItems(DateItem.TYPE_HOUR, startHour, MAX_HOUR);
        minuteItems = updateItems(DateItem.TYPE_MINUTE, startMinute, MAX_MINUTE);
        yearWheelItemView.setItems(yearItems);
        monthWheelItemView.setItems(monthItems);
        dayWheelItemView.setItems(dayItems);
        hourWheelItemView.setItems(hourItems);
        minuteWheelItemView.setItems(minuteItems);
    }

    private void initOnScrollListener() {
        yearWheelItemView.setOnSelectedListener(new WheelView.OnSelectedListener() {
            @Override
            public void onSelected(Context context, int selectedIndex) {
                selectedCalendar.set(Calendar.YEAR, yearItems[selectedIndex].getValue());
                if (showConfig > SHOW_YEAR)
                    onYearChanged();
            }
        });
        monthWheelItemView.setOnSelectedListener(new WheelView.OnSelectedListener() {
            @Override
            public void onSelected(Context context, int selectedIndex) {
                selectedCalendar.set(Calendar.MONTH, monthItems[selectedIndex].getValue() - 1);
                if (showConfig > SHOW_YEAR_MONTH)
                    onMonthChanged();
            }
        });
        dayWheelItemView.setOnSelectedListener(new WheelView.OnSelectedListener() {
            @Override
            public void onSelected(Context context, int selectedIndex) {
                selectedCalendar.set(Calendar.DAY_OF_MONTH, dayItems[selectedIndex].getValue());
                if (showConfig > SHOW_YEAR_MONTH_DAY)
                    onDayChanged();
            }
        });
        hourWheelItemView.setOnSelectedListener(new WheelView.OnSelectedListener() {
            @Override
            public void onSelected(Context context, int selectedIndex) {
                selectedCalendar.set(Calendar.HOUR_OF_DAY, hourItems[selectedIndex].getValue());
                if (showConfig > SHOW_YEAR_MONTH_DAY_HOUR)
                    onHourChanged();
            }
        });
        minuteWheelItemView.setOnSelectedListener(new WheelView.OnSelectedListener() {
            @Override
            public void onSelected(Context context, int selectedIndex) {
                selectedCalendar.set(Calendar.MINUTE, minuteItems[selectedIndex].getValue());
            }
        });
    }

    private void initSelectedDate() {
        int year = selectedCalendar.get(Calendar.YEAR);
        int month = selectedCalendar.get(Calendar.MONTH);
        int day = selectedCalendar.get(Calendar.DAY_OF_MONTH);
        int hour = selectedCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = selectedCalendar.get(Calendar.MINUTE);
        int index = findSelectedIndexByValue(yearItems, year);
        yearWheelItemView.setSelectedIndex(index, false);
        index = findSelectedIndexByValue(monthItems, month);
        monthWheelItemView.setSelectedIndex(index, false);
        index = findSelectedIndexByValue(dayItems, day);
        dayWheelItemView.setSelectedIndex(index, false);
        index = findSelectedIndexByValue(hourItems, hour);
        hourWheelItemView.setSelectedIndex(index, false);
        index = findSelectedIndexByValue(minuteItems, minute);
        minuteWheelItemView.setSelectedIndex(index, false);
    }

    private void onYearChanged() {
        //update month list
        int startYear = startCalendar.get(Calendar.YEAR);
        int endYear = endCalendar.get(Calendar.YEAR);
        int selectedYear = selectedCalendar.get(Calendar.YEAR);
        int startMonth = startCalendar.get(Calendar.MONTH) + 1;
        int endMonth = endCalendar.get(Calendar.MONTH) + 1;
        int selectedMonth = selectedCalendar.get(Calendar.MONTH) + 1;
        int tempIndex = -1;
        int lastSelectedIndex = -1;
        int startValue, endValue;
        if (isSameValue(selectedYear, startYear)) {
            startValue = startMonth;
            endValue = MAX_MONTH;
        } else if (isSameValue(selectedYear, endYear)) {
            startValue = MIN_MONTH;
            endValue = endMonth;
        } else {
            startValue = MIN_MONTH;
            endValue = MAX_MONTH;
        }
        monthItems = new DateItem[endValue - startValue + 1];
        for (int i = startValue; i <= endValue; i++) {
            tempIndex++;
            monthItems[tempIndex] = new DateItem(DateItem.TYPE_MONTH, i);
            if (isSameValue(selectedMonth, i)) {
                lastSelectedIndex = tempIndex;
            }
        }
        int newSelectedIndex = keepLastSelected ? (lastSelectedIndex == -1 ? 0 : lastSelectedIndex) : 0;
        monthWheelItemView.setItems(monthItems);
        monthWheelItemView.setSelectedIndex(newSelectedIndex);
    }

    private void onMonthChanged() {
        //update day list
        int startYear = startCalendar.get(Calendar.YEAR);
        int endYear = endCalendar.get(Calendar.YEAR);
        int selectedYear = selectedCalendar.get(Calendar.YEAR);
        int startMonth = startCalendar.get(Calendar.MONTH) + 1;
        int endMonth = endCalendar.get(Calendar.MONTH) + 1;
        int selectedMonth = selectedCalendar.get(Calendar.MONTH) + 1;
        int startDay = startCalendar.get(Calendar.DAY_OF_MONTH);
        int endDay = endCalendar.get(Calendar.DAY_OF_MONTH);
        int selectedDay = selectedCalendar.get(Calendar.DAY_OF_MONTH);
        int tempIndex = -1;
        int lastSelectedIndex = -1;
        int startValue, endValue;
        if (isSameValue(selectedYear, startYear) && isSameValue(selectedMonth, startMonth)) {
            startValue = startDay;
            endValue = selectedCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        } else if (isSameValue(selectedYear, endYear) && isSameValue(selectedMonth, endMonth)) {
            startValue = MIN_DAY;
            endValue = endDay;
        } else {
            startValue = MIN_DAY;
            endValue = selectedCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
        dayItems = new DateItem[endValue - startValue + 1];
        for (int i = startValue; i <= endValue; i++) {
            tempIndex++;
            dayItems[tempIndex] = new DateItem(DateItem.TYPE_DAY, i);
            if (isSameValue(selectedDay, i)) {
                lastSelectedIndex = tempIndex;
            }
        }
        int newSelectedIndex = keepLastSelected ? (lastSelectedIndex == -1 ? 0 : lastSelectedIndex) : 0;
        dayWheelItemView.setItems(dayItems);
        dayWheelItemView.setSelectedIndex(newSelectedIndex);
    }

    private void onDayChanged() {
        //update hour list
        int startYear = startCalendar.get(Calendar.YEAR);
        int endYear = endCalendar.get(Calendar.YEAR);
        int selectedYear = selectedCalendar.get(Calendar.YEAR);
        int startMonth = startCalendar.get(Calendar.MONTH) + 1;
        int endMonth = endCalendar.get(Calendar.MONTH) + 1;
        int selectedMonth = selectedCalendar.get(Calendar.MONTH) + 1;
        int startDay = startCalendar.get(Calendar.DAY_OF_MONTH);
        int endDay = endCalendar.get(Calendar.DAY_OF_MONTH);
        int selectedDay = selectedCalendar.get(Calendar.DAY_OF_MONTH);
        int startHour = startCalendar.get(Calendar.HOUR_OF_DAY);
        int endHour = endCalendar.get(Calendar.HOUR_OF_DAY);
        int selectedHour = selectedCalendar.get(Calendar.HOUR_OF_DAY);
        int tempIndex = -1;
        int lastSelectedIndex = -1;
        int startValue, endValue;
        if (isSameValue(selectedYear, startYear) && isSameValue(selectedMonth, startMonth) && isSameValue(selectedDay, startDay)) {
            startValue = startHour;
            endValue = MAX_HOUR;
        } else if (isSameValue(selectedYear, endYear) && isSameValue(selectedMonth, endMonth) && isSameValue(selectedDay, endDay)) {
            startValue = MIN_HOUR;
            endValue = endHour;
        } else {
            startValue = MIN_HOUR;
            endValue = MAX_HOUR;
        }
        hourItems = new DateItem[endValue - startValue + 1];
        for (int i = startValue; i <= endValue; i++) {
            tempIndex++;
            hourItems[tempIndex] = new DateItem(DateItem.TYPE_HOUR, i);
            if (isSameValue(selectedHour, i)) {
                lastSelectedIndex = tempIndex;
            }
        }
        int newSelectedIndex = keepLastSelected ? (lastSelectedIndex == -1 ? 0 : lastSelectedIndex) : 0;
        hourWheelItemView.setItems(hourItems);
        hourWheelItemView.setSelectedIndex(newSelectedIndex);
    }

    private void onHourChanged() {
        //update minute list
        int startYear = startCalendar.get(Calendar.YEAR);
        int endYear = endCalendar.get(Calendar.YEAR);
        int selectedYear = selectedCalendar.get(Calendar.YEAR);
        int startMonth = startCalendar.get(Calendar.MONTH) + 1;
        int endMonth = endCalendar.get(Calendar.MONTH) + 1;
        int selectedMonth = selectedCalendar.get(Calendar.MONTH) + 1;
        int startDay = startCalendar.get(Calendar.DAY_OF_MONTH);
        int endDay = endCalendar.get(Calendar.DAY_OF_MONTH);
        int selectedDay = selectedCalendar.get(Calendar.DAY_OF_MONTH);
        int startHour = startCalendar.get(Calendar.HOUR_OF_DAY);
        int endHour = endCalendar.get(Calendar.HOUR_OF_DAY);
        int selectedHour = selectedCalendar.get(Calendar.HOUR_OF_DAY);
        int startMinute = startCalendar.get(Calendar.MINUTE);
        int endMinute = endCalendar.get(Calendar.MINUTE);
        int selectedMinute = selectedCalendar.get(Calendar.MINUTE);
        int tempIndex = -1;
        int lastSelectedIndex = -1;
        int startValue, endValue;
        if (isSameValue(selectedYear, startYear) && isSameValue(selectedMonth, startMonth) && isSameValue(selectedDay, startDay) && isSameValue(selectedHour, startHour)) {
            startValue = startMinute;
            endValue = MAX_MINUTE;
        } else if (selectedYear == endYear && selectedMonth == endMonth && selectedDay == endDay && selectedHour == endHour) {
            startValue = MIN_MINUTE;
            endValue = endMinute;
        } else {
            startValue = MIN_MINUTE;
            endValue = MAX_MINUTE;
        }
        minuteItems = new DateItem[endValue - startValue + 1];
        for (int i = startValue; i <= endValue; i++) {
            tempIndex++;
            minuteItems[tempIndex] = new DateItem(DateItem.TYPE_MINUTE, i);
            if (isSameValue(selectedMinute, i)) {
                lastSelectedIndex = tempIndex;
            }
        }
        int newSelectedIndex = keepLastSelected ? (lastSelectedIndex == -1 ? 0 : lastSelectedIndex) : 0;
        minuteWheelItemView.setItems(minuteItems);
        minuteWheelItemView.setSelectedIndex(newSelectedIndex);
    }

    private int findSelectedIndexByValue(DateItem[] items, int value) {
        int selectedIndex = 0;
        for (int i = 0; i < items.length; i++) {
            if (isSameValue(value, items[i].getValue())) {
                selectedIndex = i;
                break;
            }
        }
        return selectedIndex;
    }

    private DateItem[] updateItems(@DateItem.DateType int type, int startValue, int endValue) {
        int index = -1;
        DateItem[] items = new DateItem[endValue - startValue + 1];
        for (int i = startValue; i <= endValue; i++) {
            index++;
            items[index] = new DateItem(type, i);
        }
        return items;
    }

    private boolean isScrolling() {
        if (showConfig == SHOW_YEAR) {
            return yearWheelItemView.isScrolling();
        } else if (showConfig == SHOW_YEAR_MONTH) {
            return yearWheelItemView.isScrolling()
                    || monthWheelItemView.isScrolling();
        } else if (showConfig == SHOW_YEAR_MONTH_DAY) {
            return yearWheelItemView.isScrolling()
                    || monthWheelItemView.isScrolling()
                    || dayWheelItemView.isScrolling();
        } else if (showConfig == SHOW_YEAR_MONTH_DAY_HOUR) {
            return yearWheelItemView.isScrolling()
                    || monthWheelItemView.isScrolling()
                    || dayWheelItemView.isScrolling()
                    || hourWheelItemView.isScrolling();
        } else {
            return yearWheelItemView.isScrolling()
                    || monthWheelItemView.isScrolling()
                    || dayWheelItemView.isScrolling()
                    || hourWheelItemView.isScrolling()
                    || minuteWheelItemView.isScrolling();
        }
    }

    private boolean isSameValue(int value1, int value2) {
        return value1 == value2;
    }

    private void ensureIsViewInitialized() {
        if (!isViewInitialized)
            throw new IllegalStateException("View wasn't initialized, call show() first.");
    }

    public interface OnClickCallBack {
        boolean callBack(View v, @NonNull Date selectedDate);
    }
}
