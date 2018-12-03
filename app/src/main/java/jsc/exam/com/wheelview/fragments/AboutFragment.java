package jsc.exam.com.wheelview.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import jsc.exam.com.wheelview.BuildConfig;
import jsc.exam.com.wheelview.R;
import jsc.exam.com.wheelview.retrofit.ApiService;
import jsc.exam.com.wheelview.retrofit.CustomHttpClient;
import jsc.exam.com.wheelview.retrofit.CustomRetrofit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * <br>Email:1006368252@qq.com
 * <br>QQ:1006368252
 * <br><a href="https://github.com/JustinRoom/WheelViewDemo" target="_blank">https://github.com/JustinRoom/WheelViewDemo</a>
 *
 * @author jiangshicheng
 */
public class AboutFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_abount, container, false);
        TextView tvVersion = root.findViewById(R.id.tv_version);
        TextView tvUpdateContent = root.findViewById(R.id.tv_update_content);
        TextView tvBuildTime = root.findViewById(R.id.tv_build_time);
        try {
            PackageInfo packageInfo = inflater.getContext().getPackageManager().getPackageInfo(inflater.getContext().getPackageName(), PackageManager.GET_GIDS);
            tvVersion.setText(String.format(Locale.CHINA, "version:%s", packageInfo.versionName));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        tvBuildTime.setText(String.format(Locale.CHINA, "build time:%s", BuildConfig.BUILD_TIME));

        root.findViewById(R.id.btn_check_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);
                checkUpdate();
            }
        });
        tvUpdateContent.setText("当前版本更新内容:" + getString(R.string.app_update_content));
        return root;
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

                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        if (getView() != null) {
                            getView().findViewById(R.id.btn_check_update).setEnabled(true);
                        }
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

            PackageInfo packageInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), PackageManager.GET_GIDS);
            long curVersionCode = packageInfo.versionCode;
            String curVersionName = packageInfo.versionName;

            Log.i("MainActivity", "explainVersionInfoJson: {versionCod" + versionCode + ", curVersionCode:" + curVersionCode);
            //a new version
            if (versionCode > curVersionCode) {
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
            } else {
                Toast.makeText(getActivity(), "已是最新版本", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException | PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void showNewVersionDialog(String content, final String fileName) {
        if (getActivity() == null)
            return;

        new AlertDialog.Builder(getActivity())
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
