package jsc.exam.com.wheelview.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;

import jsc.exam.com.wheelview.R;
import jsc.kit.wheel.base.WheelItem;
import jsc.kit.wheel.dialog.OneColumnWheelDialog;
import jsc.kit.wheel.dialog.WheelDialogInterface;

/**
 * <br>Email:1006368252@qq.com
 * <br>QQ:1006368252
 * <br><a href="https://github.com/JustinRoom/JSCKit" target="_blank">https://github.com/JustinRoom/JSCKit</a>
 *
 * @author jiangshicheng
 */
public class OneColumnWheelFragment extends Fragment {

    TextView tvChoose;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_one_column_wheel, container, false);
        tvChoose = root.findViewById(R.id.tv_choose);
        tvChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        return root;
    }

    OneColumnWheelDialog<WheelItem> dialog = null;

    private void showDialog() {
        if (dialog == null) {
            String[] randomShowText = {"菜单", "子菜单", "父系菜单", "很长的家族菜单", "ScrollMenu"};
            Random random = new Random();
            final WheelItem[] items = new WheelItem[50];
            for (int i = 0; i < 50; i++) {
                items[i] = new WheelItem(randomShowText[random.nextInt(5)] + (i < 10 ? "0" + i : "" + i));
            }
            dialog = new OneColumnWheelDialog<>(getActivity());
            dialog.show();
//            dialog.setTitle("选择");
            dialog.setNegativeButton("取消", null);
            dialog.setPositiveButton("确定", new WheelDialogInterface<WheelItem>() {
                @Override
                public boolean onClick(int witch, int selectedIndex, WheelItem item) {
                    tvChoose.setText(item.getShowText());
                    return true;
                }
            });
            dialog.setItems(items);
            dialog.setSelectedIndex(new Random().nextInt(50));
        } else {
            dialog.show();
        }
    }
}
