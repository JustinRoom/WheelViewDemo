package jsc.exam.com.wheelview;

import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    private boolean firstLoad = true;

    public abstract void onLazyLoad();

    public void onReLazyLoad() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (firstLoad) {
            firstLoad = false;
            onLazyLoad();
        } else {
            onReLazyLoad();
        }
    }
}
