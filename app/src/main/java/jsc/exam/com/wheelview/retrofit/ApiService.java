package jsc.exam.com.wheelview.retrofit;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {

    @GET("JustinRoom/WheelViewDemo/master/output/output.json")
    Observable<String> getVersionInfo();

}
