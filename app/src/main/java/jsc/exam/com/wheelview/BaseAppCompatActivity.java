package jsc.exam.com.wheelview;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * <br>Email:1006368252@qq.com
 * <br>QQ:1006368252
 * <br><a href="https://github.com/JustinRoom/WheelViewDemo" target="_blank">https://github.com/JustinRoom/WheelViewDemo</a>
 *
 * @author jiangshicheng
 */
public abstract class BaseAppCompatActivity extends AppCompatActivity {

    /**
     * Show full screen or not.
     *
     * @return {@code true}, show full screen, else not.
     */
    protected boolean fullScreen() {
        return false;
    }

    protected void initActionBar(ActionBar actionBar) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponent();
        if (fullScreen()) {
            //without ActionBar
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (getSupportActionBar() != null)
                getSupportActionBar().hide();
            //show full screen
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            initActionBar(getSupportActionBar());
        }
    }

    /**
     * Initialize components here.
     */
    protected void initComponent() {

    }

    /**
     * Destroy components here.
     */
    @CallSuper
    protected void destroyComponent() {

    }

    @Override
    protected void onDestroy() {
        destroyComponent();
        super.onDestroy();
    }

    public final void showToast(@StringRes int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }

    public final void showToast(CharSequence txt) {
        Toast.makeText(this, txt, Toast.LENGTH_SHORT).show();
    }
}
