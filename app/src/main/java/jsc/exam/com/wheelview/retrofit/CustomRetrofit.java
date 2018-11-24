package jsc.exam.com.wheelview.retrofit;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * <br>Email:1006368252@qq.com
 * <br>QQ:1006368252
 * <br>https://github.com/JustinRoom/WheelViewDemo
 *
 * @author jiangshicheng
 */
public class CustomRetrofit {
    private String baseUrl;
    private OkHttpClient okHttpClient;
    private List<Converter.Factory> converterFactories = new ArrayList<>();
    private List<CallAdapter.Factory> callAdapterFactories = new ArrayList<>();
    private boolean isDefaultFactoriesAdded = false;

    public CustomRetrofit setBaseUrl(@NonNull String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public CustomRetrofit setOkHttpClient(@NonNull OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
        return this;
    }

    private CustomRetrofit addConverterFactory(@NonNull Converter.Factory factory){
        checkIsDefaultFactoriesAdded();
        converterFactories.add(factory);
        return this;
    }

    private CustomRetrofit addCallAdapterFactory(@NonNull CallAdapter.Factory factory){
        checkIsDefaultFactoriesAdded();
        callAdapterFactories.add(factory);
        return this;
    }

    public Retrofit createRetrofit() {
        if (baseUrl == null || baseUrl.trim().length() == 0)
            throw new IllegalArgumentException("Base url can't be null.");

        if (okHttpClient == null)
            throw new IllegalArgumentException("You have not initialized OkHttpClient.");

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(baseUrl).client(okHttpClient);
        checkIsDefaultFactoriesAdded();
        for (Converter.Factory factory : converterFactories) {
            builder.addConverterFactory(factory);
        }
        for (CallAdapter.Factory factory : callAdapterFactories) {
            builder.addCallAdapterFactory(factory);
        }
        return builder.build();
    }

    private void checkIsDefaultFactoriesAdded(){
        if (isDefaultFactoriesAdded)
            return;

        //增加返回值为String的支持
        converterFactories.add(ScalarsConverterFactory.create());
        //增加返回值为Gson的支持(以实体类返回)
        converterFactories.add(GsonConverterFactory.create());
        //增加返回值为Oservable<T>的支持
        callAdapterFactories.add(RxJava2CallAdapterFactory.create());
        isDefaultFactoriesAdded = true;
    }
}
