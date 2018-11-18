package jsc.exam.com.wheelview.fragments;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Locale;

import jsc.exam.com.wheelview.BuildConfig;
import jsc.exam.com.wheelview.R;

/**
 * <br>Email:1006368252@qq.com
 * <br>QQ:1006368252
 * <br><a href="https://github.com/JustinRoom/JSCKit" target="_blank">https://github.com/JustinRoom/JSCKit</a>
 *
 * @author jiangshicheng
 */
public class AboutFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_abount, container, false);
        TextView tvVersion = root.findViewById(R.id.tv_version);
        TextView tvBuildTime = root.findViewById(R.id.tv_build_time);
        try {
            PackageInfo packageInfo = inflater.getContext().getPackageManager().getPackageInfo(inflater.getContext().getPackageName(), PackageManager.GET_GIDS);
            tvVersion.setText(String.format(Locale.CHINA, "version:%s", packageInfo.versionName));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        tvBuildTime.setText(String.format(Locale.CHINA, "build time:%s", BuildConfig.BUILD_TIME));
        return root;
    }
}
