package jsc.exam.com.wheelview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import jsc.exam.com.wheelview.bean.ClassItem;
import jsc.exam.com.wheelview.databinding.ActivityMainBinding;
import jsc.exam.com.wheelview.fragments.ColumnWheelFragment;
import jsc.exam.com.wheelview.fragments.DateTimeWheelFragment;
import jsc.exam.com.wheelview.fragments.WheelViewFragment;
import jsc.exam.com.wheelview.utils.ViewOutlineUtils;

public class MainActivity extends BaseActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnWheelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toNewActivity(new ClassItem(ClassItem.TYPE_FRAGMENT, "WheelView", WheelViewFragment.class, true, true));
            }
        });
        binding.btnColumnWheelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toNewActivity(new ClassItem(ClassItem.TYPE_FRAGMENT, "ColumnWheelDialog", ColumnWheelFragment.class, true, true));
            }
        });
        binding.btnDateTimeWheelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toNewActivity(new ClassItem(ClassItem.TYPE_FRAGMENT, "DateTimeWheelDialog", DateTimeWheelFragment.class, true, true));
            }
        });
        ViewOutlineUtils.applyEllipticOutline(binding.btnWheelView);
        ViewOutlineUtils.applyEllipticOutline(binding.btnColumnWheelDialog);
        ViewOutlineUtils.applyEllipticOutline(binding.btnDateTimeWheelDialog);
    }

    private void toNewActivity(ClassItem item) {
        switch (item.getType()) {
            case ClassItem.TYPE_ACTIVITY:
                startActivity(new Intent(this, item.getClazz()));
                break;
            case ClassItem.TYPE_FRAGMENT:
                Bundle bundle = new Bundle();
                bundle.putString(EmptyFragmentActivity.EXTRA_TITLE, item.getLabel());
                bundle.putBoolean(EmptyFragmentActivity.EXTRA_FULL_SCREEN, false);
                bundle.putBoolean(EmptyFragmentActivity.EXTRA_SHOW_ACTION_BAR, true);
                if (item.isLandscape())
                    bundle.putBoolean(EmptyFragmentActivity.EXTRA_LANDSCAPE, true);
                bundle.putString(EmptyFragmentActivity.EXTRA_FRAGMENT_CLASS_NAME, item.getClazz().getName());
                EmptyFragmentActivity.launch(this, bundle);
                break;
        }
    }

    @Override
    public void onLazyLoad() {

    }
}
