package jsc.exam.com.wheelview.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Random;

import jsc.exam.com.wheelview.R;
import jsc.kit.wheel.base.WheelItem;
import jsc.kit.wheel.base.WheelItemView;
import jsc.kit.wheel.base.WheelView;

public class WheelViewFragment extends Fragment {

    WheelView wheelView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_wheel_view, container, false);
        WheelItemView wheelItemView = root.findViewById(R.id.wheel_item_view);
        wheelView = wheelItemView.getWheelView();
        wheelView.setOnSelectedListener(new WheelView.OnSelectedListener() {
            @Override
            public void onSelected(Context context, int selectedIndex) {
                Toast.makeText(context, "选中" + selectedIndex, Toast.LENGTH_SHORT).show();
            }
        });
        root.findViewById(R.id.btn_random).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wheelView.setSelectedIndex(new Random().nextInt(50));
            }
        });
        loadData();
        return root;
    }

    private void loadData() {
        String[] randomShowText = {"菜单", "子菜单", "父系菜单", "很长的家族菜单", "ScrollMenu"};
        Random random = new Random();
        WheelItem[] items = new WheelItem[50];
        for (int i = 0; i < 50; i++) {
            items[i] = new WheelItem(randomShowText[random.nextInt(5)] + (i < 10 ? "0" + i : "" + i));
        }
        wheelView.setItems(items);
    }
}
