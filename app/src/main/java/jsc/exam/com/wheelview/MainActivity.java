package jsc.exam.com.wheelview;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.FrameLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import jsc.exam.com.wheelview.adapter.BaseRecyclerViewAdapter;
import jsc.exam.com.wheelview.adapter.BlankSpaceItemDecoration;
import jsc.exam.com.wheelview.adapter.ClassItemAdapter;
import jsc.exam.com.wheelview.bean.ClassItem;
import jsc.exam.com.wheelview.fragments.AboutFragment;
import jsc.exam.com.wheelview.fragments.DateTimeWheelFragment;
import jsc.exam.com.wheelview.fragments.ColumnWheelFragment;
import jsc.exam.com.wheelview.fragments.TestFragment;
import jsc.exam.com.wheelview.fragments.WheelViewFragment;
import jsc.exam.com.wheelview.retrofit.ApiService;
import jsc.exam.com.wheelview.retrofit.CustomHttpClient;
import jsc.exam.com.wheelview.retrofit.CustomRetrofit;
import jsc.exam.com.wheelview.utils.CompatResourceUtils;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class MainActivity extends BaseActivity {

    RecyclerView recyclerView;
    SharedPreferences sharedPreferences = null;

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

        //check upgrade if the latest checking time is 2 hours ago
        sharedPreferences = getSharedPreferences("share_wheel_view", MODE_PRIVATE);
        long lastCheckUpdateTimeStamp = sharedPreferences.getLong("lastCheckUpdateTimeStamp", 0);
        long curTime = new Date().getTime();
        if (curTime - lastCheckUpdateTimeStamp > 2 * 60 * 60_000) {
            checkUpdate();
        }
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

    private List<ClassItem> getClassItems() {
        List<ClassItem> classItems = new ArrayList<>();
        classItems.add(new ClassItem(ClassItem.TYPE_FRAGMENT, "WheelView", WheelViewFragment.class, true, true));
        classItems.add(new ClassItem(ClassItem.TYPE_FRAGMENT, "ColumnWheelDialog", ColumnWheelFragment.class, true, true));
        classItems.add(new ClassItem(ClassItem.TYPE_FRAGMENT, "DateTimeWheelDialog", DateTimeWheelFragment.class, true, true));
        classItems.add(new ClassItem(ClassItem.TYPE_FRAGMENT, "Test", TestFragment.class, true));
        classItems.add(new ClassItem(ClassItem.TYPE_FRAGMENT, "About", AboutFragment.class, false));
        return classItems;
    }

    private void checkUpdate() {
        OkHttpClient client = new CustomHttpClient()
                .addHeader(new Pair<>("token", ""))
                .setConnectTimeout(5_000)
                .setShowLog(true)
                .createOkHttpClient();
        Retrofit retrofit = new CustomRetrofit()
                //我在app的build.gradle文件的defaultConfig标签里定义了BASE_URL
                .setBaseUrl(BuildConfig.BASE_URL)
                .setOkHttpClient(client)
                .createRetrofit();
        Disposable disposable = retrofit.create(ApiService.class)
                .getVersionInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        explainVersionInfoJson(s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        showToast(throwable.getLocalizedMessage());
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                });
    }

    private void explainVersionInfoJson(String json) {
        json = json.substring(1, json.length() - 1);
        try {
            JSONObject object = new JSONObject(json).getJSONObject("apkInfo");
            int versionCode = object.getInt("versionCode");
            String versionName = object.getString("versionName");
            String fileName = object.getString("outputFile");
            String content = object.getString("content");

            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_GIDS);
            long curVersionCode = packageInfo.versionCode;
            String curVersionName = packageInfo.versionName;

            Log.i("MainActivity", "explainVersionInfoJson: {versionCod" + versionCode + ", curVersionCode:" + curVersionCode);
            //a new version
            if (versionCode > curVersionCode) {
                sharedPreferences.edit().putLong("lastCheckUpdateTimeStamp", new Date().getTime()).apply();
                showNewVersionDialog(String.format(
                        Locale.CHINA,
                        "当前版本:\u2000%1s\n"
                                + "最新版本:\u2000%2s\n\n"
                                + "更新内容:\n%3s"
                                + "\n\n立即更新？",
                        curVersionName,
                        versionName,
                        content
                ), fileName);
            }
        } catch (JSONException | PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void showNewVersionDialog(String content, final String fileName) {
        new AlertDialog.Builder(this)
                .setTitle("新版本提示")
                .setMessage(content)
                .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String url = BuildConfig.BASE_URL + BuildConfig.DOWNLOAD_URL;
                        Uri uri = Uri.parse(String.format(Locale.CHINA, url, fileName));
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);

                    }
                })
                .setNegativeButton("知道了", null)
                .show();
    }
}
