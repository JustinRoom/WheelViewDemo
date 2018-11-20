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

import jsc.exam.com.wheelview.R;
import jsc.kit.wheel.dialog.DateTimeWheelDialog;

/**
 * <br>Email:1006368252@qq.com
 * <br>QQ:1006368252
 * <br><a href="https://github.com/JustinRoom/JSCKit" target="_blank">https://github.com/JustinRoom/JSCKit</a>
 *
 * @author jiangshicheng
 */
public class DateTimeWheelFragment extends Fragment {

    TextView tvResult;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_wheel_dialog, container, false);
        tvResult = root.findViewById(R.id.tv_result);
        root.findViewById(R.id.btn_choose_time).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        return root;
    }

    DateTimeWheelDialog dialog = null;

    private void showDialog() {
        if (dialog == null) {
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

            dialog = new DateTimeWheelDialog(getActivity());
            dialog.show();
            dialog.setTitle("选择时间");
            dialog.configShowUI(DateTimeWheelDialog.SHOW_YEAR_MONTH_DAY_HOUR_MINUTE);
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
        } else {
            dialog.show();
        }
    }
}
