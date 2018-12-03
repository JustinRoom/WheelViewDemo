package jsc.exam.com.wheelview.retrofit;

import io.reactivex.Observable;
import jsc.exam.com.wheelview.BuildConfig;
import retrofit2.http.GET;

public interface ApiService {

    @GET(BuildConfig.VERSION_URL)
    Observable<String> getVersionInfo();

}
