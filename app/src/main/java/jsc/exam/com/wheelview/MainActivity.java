package jsc.exam.com.wheelview;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import jsc.exam.com.wheelview.adapter.BaseRecyclerViewAdapter;
import jsc.exam.com.wheelview.adapter.BlankSpaceItemDecoration;
import jsc.exam.com.wheelview.adapter.ClassItemAdapter;
import jsc.exam.com.wheelview.bean.ClassItem;
import jsc.exam.com.wheelview.fragments.WheelViewFragment;
import jsc.exam.com.wheelview.utils.CompatResourceUtils;

public class MainActivity extends BaseActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView = new RecyclerView(this);
        recyclerView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new BlankSpaceItemDecoration(
                CompatResourceUtils.getDimensionPixelSize(this, R.dimen.space_16),
                CompatResourceUtils.getDimensionPixelSize(this, R.dimen.space_2),
                CompatResourceUtils.getDimensionPixelSize(this, R.dimen.space_16),
                CompatResourceUtils.getDimensionPixelSize(this, R.dimen.space_2)
        ));
        setContentView(recyclerView);
        setTitleBarTitle(getClass().getSimpleName().replace("Activity", ""));
        showTitleBarBackView(false);

        ClassItemAdapter adapter = new ClassItemAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<ClassItem>() {
            @Override
            public void onItemClick(View itemView, int position, ClassItem item, int viewType) {
                toNewActivity(item);
            }
        });
        adapter.setItems(getClassItems());
    }

    private void toNewActivity(ClassItem item) {
        Bundle bundle = new Bundle();
        switch (item.getLabel()){
            case "WheelViewFragment":
                bundle.putString(EmptyFragmentActivity.EXTRA_TITLE, "Take photo");
                bundle.putBoolean(EmptyFragmentActivity.EXTRA_FULL_SCREEN, false);
                bundle.putBoolean(EmptyFragmentActivity.EXTRA_SHOW_ACTION_BAR, false);
                bundle.putString(EmptyFragmentActivity.EXTRA_FRAGMENT_CLASS_NAME, WheelViewFragment.class.getName());
                break;
        }
        EmptyFragmentActivity.launch(this, bundle);
    }

    private List<ClassItem> getClassItems() {
        List<ClassItem> classItems = new ArrayList<>();
        classItems.add(new ClassItem("WheelViewFragment", EmptyFragmentActivity.class));
//        classItems.add(new ClassItem("About", AboutActivity.class));
        return classItems;
    }
}
