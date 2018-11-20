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
import jsc.kit.wheel.dialog.ColumnWheelDialog;

/**
 * <br>Email:1006368252@qq.com
 * <br>QQ:1006368252
 * <br><a href="https://github.com/JustinRoom/JSCKit" target="_blank">https://github.com/JustinRoom/JSCKit</a>
 *
 * @author jiangshicheng
 */
public class ColumnWheelFragment extends Fragment {

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

    ColumnWheelDialog<WheelItem, WheelItem, WheelItem, WheelItem, WheelItem> dialog = null;

    private void showDialog() {
        if (dialog == null) {
            dialog = new ColumnWheelDialog<>(getActivity());
            dialog.show();
            dialog.setTitle("选择菜单");
            dialog.setCancelButton("取消", null);
            dialog.setOKButton("确定", new ColumnWheelDialog.OnClickCallBack<WheelItem, WheelItem, WheelItem, WheelItem, WheelItem>() {
                @Override
                public boolean callBack(View v, @Nullable WheelItem item0, @Nullable WheelItem item1, @Nullable WheelItem item2, @Nullable WheelItem item3, @Nullable WheelItem item4) {
                    String result = "";
                    if (item0 != null)
                        result += item0.getShowText() + "\n";
                    if (item1 != null)
                        result += item1.getShowText() + "\n";
                    if (item2 != null)
                        result += item2.getShowText() + "\n";
                    if (item3 != null)
                        result += item3.getShowText() + "\n";
                    if (item4 != null)
                        result += item4.getShowText() + "\n";
                    tvResult.setText(result);
                    return false;
                }
            });
            dialog.setItems(
                    initItems("菜单选项一"),
                    initItems("菜单选项二"),
                    initItems("菜单选项三"),
                    initItems("菜单选项四"),
                    initItems("菜单选项五")
            );
            dialog.setSelected(
                    new Random().nextInt(50),
                    new Random().nextInt(50),
                    new Random().nextInt(50),
                    new Random().nextInt(50),
                    new Random().nextInt(50)
            );
        } else {
            dialog.show();
        }
    }

    private WheelItem[] initItems(String label) {
        final WheelItem[] items = new WheelItem[50];
        for (int i = 0; i < 50; i++) {
            items[i] = new WheelItem(label + (i < 10 ? "0" + i : "" + i));
        }
        return items;
    }
}
