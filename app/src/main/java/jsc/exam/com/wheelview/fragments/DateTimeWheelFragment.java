package jsc.exam.com.wheelview.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import jsc.exam.com.wheelview.R;
import jsc.kit.wheel.dialog.ColumnWheelDialog;
import jsc.kit.wheel.dialog.DateTimeWheelDialog;

/**
 * <br>Email:1006368252@qq.com
 * <br>QQ:1006368252
 * <br><a href="https://github.com/JustinRoom/WheelViewDemo" target="_blank">https://github.com/JustinRoom/WheelViewDemo</a>
 *
 * @author jiangshicheng
 */
public class DateTimeWheelFragment extends Fragment {

    TextView tvResult;
    DateTimeWheelDialog dialog1 = null;
    DateTimeWheelDialog dialog2 = null;
    DateTimeWheelDialog dialog3 = null;
    DateTimeWheelDialog dialog4 = null;
    DateTimeWheelDialog dialog5 = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_wheel_dialog, container, false);
        tvResult = root.findViewById(R.id.tv_result);
        root.findViewById(R.id.btn_choose_time_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog1 == null)
                    dialog1 = createDialog(1);
                else
                    dialog1.show();
            }
        });
        root.findViewById(R.id.btn_choose_time_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog2 == null)
                    dialog2 = createDialog(2);
                else
                    dialog2.show();
            }
        });
        root.findViewById(R.id.btn_choose_time_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog3 == null)
                    dialog3 = createDialog(3);
                else
                    dialog3.show();
            }
        });
        root.findViewById(R.id.btn_choose_time_4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog4 == null)
                    dialog4 = createDialog(4);
                else
                    dialog4.show();
            }
        });
        root.findViewById(R.id.btn_choose_time_5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog5 == null)
                    dialog5 = createDialog(5);
                else
                    dialog5.show();
            }
        });
        return root;
    }

    private DateTimeWheelDialog createDialog(int type) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2015);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        Date startDate = calendar.getTime();
        calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2020);
        Date endDate = calendar.getTime();

        DateTimeWheelDialog dialog = new DateTimeWheelDialog(getActivity());
//        dialog.setShowCount(7);
//        dialog.setItemVerticalSpace(24);
        dialog.show();
        dialog.setTitle("选择时间");
        int config = DateTimeWheelDialog.SHOW_YEAR_MONTH_DAY_HOUR_MINUTE;
        switch (type) {
            case 1:
                config = DateTimeWheelDialog.SHOW_YEAR;
                break;
            case 2:
                config = DateTimeWheelDialog.SHOW_YEAR_MONTH;
                break;
            case 3:
                config = DateTimeWheelDialog.SHOW_YEAR_MONTH_DAY;
                break;
            case 4:
                config = DateTimeWheelDialog.SHOW_YEAR_MONTH_DAY_HOUR;
                break;
            case 5:
                config = DateTimeWheelDialog.SHOW_YEAR_MONTH_DAY_HOUR_MINUTE;
                break;
        }
        dialog.configShowUI(config);
        dialog.setCancelButton("取消", null);
        dialog.setOKButton("确定", new DateTimeWheelDialog.OnClickCallBack() {
            @Override
            public boolean callBack(View v, @NonNull Date selectedDate) {
                tvResult.setText(SimpleDateFormat.getInstance().format(selectedDate));
                return false;
            }
        });
        dialog.setDateArea(startDate, endDate, true);
        dialog.updateSelectedDate(new Date());
        return dialog;
    }
}
