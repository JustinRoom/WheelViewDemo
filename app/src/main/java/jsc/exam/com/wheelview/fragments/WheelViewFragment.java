package jsc.exam.com.wheelview.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Random;

import jsc.exam.com.wheelview.R;
import jsc.kit.wheel.base.WheelItem;
import jsc.kit.wheel.base.WheelItemView;
import jsc.kit.wheel.base.WheelView;

public class WheelViewFragment extends Fragment {

    TextView tvSelectedItems;
    WheelItemView wheelViewLeft;
    WheelItemView wheelViewCenter;
    WheelItemView wheelViewRight;
    WheelView.OnSelectedListener listener = new WheelView.OnSelectedListener() {
        @Override
        public void onSelected(Context context, int selectedIndex) {
            tvSelectedItems.setText(String.format(Locale.CHINA, "{left:%d, center:%d, right:%d}", wheelViewLeft.getSelectedIndex(), wheelViewCenter.getSelectedIndex(),  wheelViewRight.getSelectedIndex()));
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_wheel_view, container, false);
        tvSelectedItems = root.findViewById(R.id.tv_selected_items);
        wheelViewLeft = root.findViewById(R.id.wheel_view_left);
        wheelViewCenter = root.findViewById(R.id.wheel_view_center);
        wheelViewRight = root.findViewById(R.id.wheel_view_right);
        wheelViewLeft.setOnSelectedListener(listener);
        wheelViewCenter.setOnSelectedListener(listener);
        wheelViewRight.setOnSelectedListener(listener);
        root.findViewById(R.id.btn_random).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wheelViewLeft.setSelectedIndex(new Random().nextInt(50));
                wheelViewCenter.setSelectedIndex(new Random().nextInt(50));
                wheelViewRight.setSelectedIndex(new Random().nextInt(50));
            }
        });
        loadData(wheelViewLeft, "很长的左边菜单");
        loadData(wheelViewCenter, "中间菜单");
        loadData(wheelViewRight,  "很长的右边边菜单");
        return root;
    }

    private void loadData(WheelItemView wheelItemView) {
        String[] randomShowText = {"菜单", "子菜单", "父系菜单", "很长的家族菜单", "ScrollMenu"};
        Random random = new Random();
        WheelItem[] items = new WheelItem[50];
        for (int i = 0; i < 50; i++) {
            items[i] = new WheelItem(randomShowText[random.nextInt(5)] + (i < 10 ? "0" + i : "" + i));
        }
        wheelItemView.setItems(items);
    }

    private void loadData(WheelItemView wheelItemView, String label) {
        WheelItem[] items = new WheelItem[50];
        for (int i = 0; i < 50; i++) {
            items[i] = new WheelItem(label + (i < 10 ? "0" + i : "" + i));
        }
        wheelItemView.setItems(items);
    }
}
