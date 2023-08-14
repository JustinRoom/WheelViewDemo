package jsc.kit.wheel.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

    private WheelItemView mYearView;
    private WheelItemView mMonthView;
    private WheelItemView mDayView;
    private WheelItemView mHourView;
    private WheelItemView mMinuteView;

    private final List<DateItem> yearItems = new ArrayList<>();
    private final List<DateItem> monthItems = new ArrayList<>();
    private final List<DateItem> dayItems = new ArrayList<>();
    private final List<DateItem> hourItems = new ArrayList<>();
    private final List<DateItem> minuteItems = new ArrayList<>();

    private final Calendar startCalendar = Calendar.getInstance();
    private final Calendar endCalendar = Calendar.getInstance();
    private final Calendar selectedCalendar = Calendar.getInstance();
    private OnClickCallBack cancelCallBack = null;
    private OnClickCallBack okCallBack = null;

    private int showCount = 5;
    private int itemVerticalSpace = 32;
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
        initOnScrollListener();
    }

    private void initView() {
        isViewInitialized = true;
        LinearLayout mPickerContainer = findViewById(R.id.wheel_id_picker_container);
        //year
        mYearView = new WheelItemView(mPickerContainer.getContext());
        mYearView.setItemVerticalSpace(itemVerticalSpace);
        mYearView.setShowCount(showCount);
        mPickerContainer.addView(mYearView, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        //month
        mMonthView = new WheelItemView(mPickerContainer.getContext());
        mMonthView.setItemVerticalSpace(itemVerticalSpace);
        mMonthView.setShowCount(showCount);
        mPickerContainer.addView(mMonthView, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        //day
        mDayView = new WheelItemView(mPickerContainer.getContext());
        mDayView.setItemVerticalSpace(itemVerticalSpace);
        mDayView.setShowCount(showCount);
        mPickerContainer.addView(mDayView, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        //hour
        mHourView = new WheelItemView(mPickerContainer.getContext());
        mHourView.setItemVerticalSpace(itemVerticalSpace);
        mHourView.setShowCount(showCount);
        mPickerContainer.addView(mHourView, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        //minute
        mMinuteView = new WheelItemView(mPickerContainer.getContext());
        mMinuteView.setItemVerticalSpace(itemVerticalSpace);
        mMinuteView.setShowCount(showCount);
        mPickerContainer.addView(mMinuteView, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

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

    public void setShowCount(int showCount) {
        this.showCount = showCount;
    }

    public void setItemVerticalSpace(int itemVerticalSpace) {
        this.itemVerticalSpace = itemVerticalSpace;
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
        configShowUI(config, getContext().getResources().getDimensionPixelSize(R.dimen.wheel_picker_total_offset_x));
    }

    public void configShowUI(@ShowConfig int showConfig, int totalOffsetX) {
        ensureIsViewInitialized();
        this.showConfig = showConfig;
        mYearView.setTotalOffsetX(0);
        mMonthView.setTotalOffsetX(0);
        mDayView.setTotalOffsetX(0);
        mHourView.setTotalOffsetX(0);
        mMinuteView.setTotalOffsetX(0);
        switch (showConfig) {
            case SHOW_YEAR:
                mYearView.setVisibility(View.VISIBLE);
                mMonthView.setVisibility(View.GONE);
                mDayView.setVisibility(View.GONE);
                mHourView.setVisibility(View.GONE);
                mMinuteView.setVisibility(View.GONE);
                break;
            case SHOW_YEAR_MONTH:
                mYearView.setVisibility(View.VISIBLE);
                mMonthView.setVisibility(View.VISIBLE);
                mDayView.setVisibility(View.GONE);
                mHourView.setVisibility(View.GONE);
                mMinuteView.setVisibility(View.GONE);
                break;
            case SHOW_YEAR_MONTH_DAY:
                mYearView.setVisibility(View.VISIBLE);
                mMonthView.setVisibility(View.VISIBLE);
                mDayView.setVisibility(View.VISIBLE);
                mHourView.setVisibility(View.GONE);
                mMinuteView.setVisibility(View.GONE);
                mYearView.setTotalOffsetX(totalOffsetX);
                mDayView.setTotalOffsetX(-totalOffsetX);
                break;
            case SHOW_YEAR_MONTH_DAY_HOUR:
                mYearView.setVisibility(View.VISIBLE);
                mMonthView.setVisibility(View.VISIBLE);
                mDayView.setVisibility(View.VISIBLE);
                mHourView.setVisibility(View.VISIBLE);
                mMinuteView.setVisibility(View.GONE);
                mYearView.setTotalOffsetX(totalOffsetX);
                mHourView.setTotalOffsetX(-totalOffsetX);
                break;
            case SHOW_YEAR_MONTH_DAY_HOUR_MINUTE:
                mYearView.setVisibility(View.VISIBLE);
                mMonthView.setVisibility(View.VISIBLE);
                mDayView.setVisibility(View.VISIBLE);
                mHourView.setVisibility(View.VISIBLE);
                mMinuteView.setVisibility(View.VISIBLE);
                mYearView.setTotalOffsetX(totalOffsetX);
                mMinuteView.setTotalOffsetX(-totalOffsetX);
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
    }

    private void initAreaDate() {
        int startYear = startCalendar.get(Calendar.YEAR);
        int endYear = endCalendar.get(Calendar.YEAR);
        int startMonth = startCalendar.get(Calendar.MONTH) + 1;
        int startDay = startCalendar.get(Calendar.DAY_OF_MONTH);
        int startHour = startCalendar.get(Calendar.HOUR_OF_DAY);
        int startMinute = startCalendar.get(Calendar.MINUTE);

        updateItems(yearItems, DateItem.TYPE_YEAR, startYear, endYear);
        updateItems(monthItems, DateItem.TYPE_MONTH, startMonth, MAX_MONTH);
        int dayActualMaximum = startCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        updateItems(dayItems, DateItem.TYPE_DAY, startDay, dayActualMaximum);
        updateItems(hourItems, DateItem.TYPE_HOUR, startHour, MAX_HOUR);
        updateItems(minuteItems, DateItem.TYPE_MINUTE, startMinute, MAX_MINUTE);
        mYearView.setItems(toArray(yearItems));
        mMonthView.setItems(toArray(monthItems));
        mDayView.setItems(toArray(dayItems));
        mHourView.setItems(toArray(hourItems));
        mMinuteView.setItems(toArray(minuteItems));
    }

    private void initOnScrollListener() {
        mYearView.setOnSelectedListener(new WheelView.OnSelectedListener() {
            @Override
            public void onSelected(Context context, int selectedIndex) {
                selectedCalendar.set(Calendar.YEAR, yearItems.get(selectedIndex).getValue());
                if (showConfig > SHOW_YEAR)
                    onYearChanged();
            }
        });
        mMonthView.setOnSelectedListener(new WheelView.OnSelectedListener() {
            @Override
            public void onSelected(Context context, int selectedIndex) {
                int oldDayCount = getDayCount(selectedCalendar.get(Calendar.YEAR), selectedCalendar.get(Calendar.MONTH) + 1);
                int month = monthItems.get(selectedIndex).getValue();
                int newDayCount = getDayCount(selectedCalendar.get(Calendar.YEAR), month);
                if (newDayCount < oldDayCount) {
                    int oldSelectedDay = selectedCalendar.get(Calendar.DAY_OF_MONTH);
                    selectedCalendar.set(Calendar.DAY_OF_MONTH, Math.min(newDayCount, oldSelectedDay));
                }
                selectedCalendar.set(Calendar.MONTH, month - 1);
                if (showConfig > SHOW_YEAR_MONTH)
                onMonthChanged();
            }
        });
        mDayView.setOnSelectedListener(new WheelView.OnSelectedListener() {
            @Override
            public void onSelected(Context context, int selectedIndex) {
                selectedCalendar.set(Calendar.DAY_OF_MONTH, dayItems.get(selectedIndex).getValue());
                if (showConfig > SHOW_YEAR_MONTH_DAY)
                    onDayChanged();
            }
        });
        mHourView.setOnSelectedListener(new WheelView.OnSelectedListener() {
            @Override
            public void onSelected(Context context, int selectedIndex) {
                selectedCalendar.set(Calendar.HOUR_OF_DAY, hourItems.get(selectedIndex).getValue());
                if (showConfig > SHOW_YEAR_MONTH_DAY_HOUR)
                    onHourChanged();
            }
        });
        mMinuteView.setOnSelectedListener(new WheelView.OnSelectedListener() {
            @Override
            public void onSelected(Context context, int selectedIndex) {
                selectedCalendar.set(Calendar.MINUTE, minuteItems.get(selectedIndex).getValue());
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
        mYearView.setSelectedIndex(index, false);
        index = findSelectedIndexByValue(monthItems, month);
        mMonthView.setSelectedIndex(index, false);
        index = findSelectedIndexByValue(dayItems, day);
        mDayView.setSelectedIndex(index, false);
        index = findSelectedIndexByValue(hourItems, hour);
        mHourView.setSelectedIndex(index, false);
        index = findSelectedIndexByValue(minuteItems, minute);
        mMinuteView.setSelectedIndex(index, false);
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
        monthItems.clear();
        for (int i = startValue; i <= endValue; i++) {
            tempIndex++;
            monthItems.add(new DateItem(DateItem.TYPE_MONTH, i));
            if (isSameValue(selectedMonth, i)) {
                lastSelectedIndex = tempIndex;
            }
        }
        int newSelectedIndex = keepLastSelected ? (lastSelectedIndex == -1 ? 0 : lastSelectedIndex) : 0;
        mMonthView.setItems(toArray(monthItems));
        mMonthView.setSelectedIndex(newSelectedIndex);
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
            endValue = getDayCount(selectedYear, selectedMonth);
        } else if (isSameValue(selectedYear, endYear) && isSameValue(selectedMonth, endMonth)) {
            startValue = MIN_DAY;
            endValue = endDay;
        } else {
            startValue = MIN_DAY;
            endValue = getDayCount(selectedYear, selectedMonth);
        }
        dayItems.clear();
        for (int i = startValue; i <= endValue; i++) {
            tempIndex++;
            dayItems.add(new DateItem(DateItem.TYPE_DAY, i));
            if (isSameValue(selectedDay, i)) {
                lastSelectedIndex = tempIndex;
            }
        }
        int newSelectedIndex = keepLastSelected ? (lastSelectedIndex == -1 ? 0 : lastSelectedIndex) : 0;
        mDayView.setItems(toArray(dayItems));
        mDayView.setSelectedIndex(newSelectedIndex, false);
    }

    private int getDayCount(int year, int month) {
        int day = 0;
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
            day = 29;
        } else {
            day = 28;
        }
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                return day;

        }
        return 0;
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
        hourItems.clear();
        for (int i = startValue; i <= endValue; i++) {
            tempIndex++;
            hourItems.add(new DateItem(DateItem.TYPE_HOUR, i));
            if (isSameValue(selectedHour, i)) {
                lastSelectedIndex = tempIndex;
            }
        }
        int newSelectedIndex = keepLastSelected ? (lastSelectedIndex == -1 ? 0 : lastSelectedIndex) : 0;
        mHourView.setItems(toArray(hourItems));
        mHourView.setSelectedIndex(newSelectedIndex);
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
        minuteItems.clear();
        for (int i = startValue; i <= endValue; i++) {
            tempIndex++;
            minuteItems.add(new DateItem(DateItem.TYPE_MINUTE, i));
            if (isSameValue(selectedMinute, i)) {
                lastSelectedIndex = tempIndex;
            }
        }
        int newSelectedIndex = keepLastSelected ? (lastSelectedIndex == -1 ? 0 : lastSelectedIndex) : 0;
        mMinuteView.setItems(toArray(minuteItems));
        mMinuteView.setSelectedIndex(newSelectedIndex);
    }

    private int findSelectedIndexByValue(List<DateItem> items, int value) {
        int selectedIndex = 0;
        for (int i = 0; i < items.size(); i++) {
            if (isSameValue(value, items.get(i).getValue())) {
                selectedIndex = i;
                break;
            }
        }
        return selectedIndex;
    }

    private void updateItems(@NonNull List<DateItem> list, @DateItem.DateType int type, int startValue, int endValue) {
        list.clear();
        for (int i = startValue; i <= endValue; i++) {
            list.add(new DateItem(type, i));
        }
    }

    private boolean isScrolling() {
        if (showConfig == SHOW_YEAR) {
            return mYearView.isScrolling();
        } else if (showConfig == SHOW_YEAR_MONTH) {
            return mYearView.isScrolling()
                    || mMonthView.isScrolling();
        } else if (showConfig == SHOW_YEAR_MONTH_DAY) {
            return mYearView.isScrolling()
                    || mMonthView.isScrolling()
                    || mDayView.isScrolling();
        } else if (showConfig == SHOW_YEAR_MONTH_DAY_HOUR) {
            return mYearView.isScrolling()
                    || mMonthView.isScrolling()
                    || mDayView.isScrolling()
                    || mHourView.isScrolling();
        } else {
            return mYearView.isScrolling()
                    || mMonthView.isScrolling()
                    || mDayView.isScrolling()
                    || mHourView.isScrolling()
                    || mMinuteView.isScrolling();
        }
    }

    private boolean isSameValue(int value1, int value2) {
        return value1 == value2;
    }

    private DateItem[] toArray(List<DateItem> items) {
        DateItem[] result = new DateItem[items.size()];
        items.toArray(result);
        return result;
    }

    private void ensureIsViewInitialized() {
        if (!isViewInitialized)
            throw new IllegalStateException("View wasn't initialized, call show() first.");
    }

    public interface OnClickCallBack {
        boolean callBack(View v, @NonNull Date selectedDate);
    }
}
