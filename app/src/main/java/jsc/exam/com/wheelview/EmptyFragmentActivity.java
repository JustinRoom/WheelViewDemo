package jsc.exam.com.wheelview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.ActionMenuView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import jsc.exam.com.wheelview.utils.CompatResourceUtils;
import jsc.exam.com.wheelview.utils.WindowUtils;

/**
 * <br>Email:1006368252@qq.com
 * <br>QQ:1006368252
 * <br><a href="https://github.com/JustinRoom/WheelViewDemo" target="_blank">https://github.com/JustinRoom/WheelViewDemo</a>
 *
 * @author jiangshicheng
 */
public class EmptyFragmentActivity extends BaseEmptyFragmentActivity {

    public static void launch(Context context, Bundle extras) {
        context.startActivity(createIntent(context, extras));
    }

    private static Intent createIntent(Context context, Bundle extras) {
        Intent intent = new Intent(context, EmptyFragmentActivity.class);
        if (extras != null)
            intent.putExtras(extras);
        return intent;
    }

    @Override
    public void initActionBar(ActionBar actionBar) {
        if (actionBar == null)
            return;

        //You can initialize custom action bar here.
        int padding = CompatResourceUtils.getDimensionPixelSize(this, R.dimen.space_12);
        FrameLayout customView = new FrameLayout(this);
//        customView.setPadding(padding, 0, padding, 0);
        ActionBar.LayoutParams barParams = new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, WindowUtils.getActionBarSize(this));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(customView, barParams);
        //添加标题
        TextView tvTitle = new TextView(this);
        tvTitle.setTextColor(Color.WHITE);
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        tvTitle.setGravity(Gravity.CENTER);
        tvTitle.setText(getClass().getSimpleName().replace("Activity", ""));
        customView.addView(tvTitle, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        //添加返回按钮
        ImageView ivBack = new ImageView(this);
        ivBack.setPadding(padding / 2, 0, padding / 2, 0);
        ivBack.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        ivBack.setImageResource(R.drawable.ic_chevron_left_white_24dp);
        ivBack.setBackground(WindowUtils.getSelectableItemBackgroundBorderless(this));
        customView.addView(ivBack, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //添加menu菜单
        ActionMenuView actionMenuView = new ActionMenuView(this);
        FrameLayout.LayoutParams menuParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        menuParams.gravity = Gravity.END | Gravity.CENTER_VERTICAL;
        customView.addView(actionMenuView, menuParams);

        String title = getIntent().getStringExtra(EXTRA_TITLE);
        tvTitle.setText(TextUtils.isEmpty(title) ? getClass().getSimpleName().replace("Activity", "") : title);
    }
}