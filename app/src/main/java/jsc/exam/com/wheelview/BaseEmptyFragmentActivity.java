package jsc.exam.com.wheelview;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public abstract class BaseEmptyFragmentActivity extends AppCompatActivity {

    public final static String EXTRA_FULL_SCREEN = "full_screen";
    public final static String EXTRA_SHOW_ACTION_BAR = "show_action_bar";
    public final static String EXTRA_FRAGMENT_CLASS_NAME = "class_name";
    public final static String EXTRA_TITLE = "title";
    public final static String EXTRA_LANDSCAPE = "landscape";

    public abstract void initActionBar(ActionBar actionBar);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getBooleanExtra(EXTRA_FULL_SCREEN, false)) {
            //without ActionBar
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (getSupportActionBar() != null)
                getSupportActionBar().hide();
            //show full screen
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            if (getIntent().getBooleanExtra(EXTRA_SHOW_ACTION_BAR, true)) {
                initActionBar(getSupportActionBar());
            } else if (getSupportActionBar() != null) {
                getSupportActionBar().hide();
            }
        }

        String className = getIntent().getStringExtra(EXTRA_FRAGMENT_CLASS_NAME);
        if (className != null && className.length() > 0) {
            Class<?> clzz = null;
            Object obj = null;
            try {
                clzz = Class.forName(className);
                obj = clzz.newInstance();
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
            if (obj instanceof Fragment) {
                Fragment fragment = (Fragment) obj;
                Bundle arguments = getIntent().getExtras();
                if (arguments != null) {
                    fragment.setArguments(arguments);
                }
                getSupportFragmentManager().beginTransaction().replace(android.R.id.content, fragment).commit();
            }
        }
    }

    @Override
    protected void onResume() {
        if (getIntent().getBooleanExtra(EXTRA_LANDSCAPE, false)
                && getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        super.onResume();
    }
}
