package jsc.exam.com.wheelview.retrofit;

import android.app.Application;
import android.content.Context;
import android.util.Pair;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.annotations.NonNull;
import jsc.exam.com.wheelview.utils.ConnectivityHelper;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 *
 * <br>Email:1006368252@qq.com
 * <br>QQ:1006368252
 * <br><a href="https://github.com/JustinRoom/WheelViewDemo" target="_blank">https://github.com/JustinRoom/WheelViewDemo</a>
 *
 * create time: 6/6/2018 6:02 PM
 * @author jiangshicheng
 */
public class CustomHttpClient {
    private boolean showLog = false;
    private int connectTimeout = 10_000;
    private int readTimeout = 10_000;
    private int writeTimeout = 10_000;
    private List<Pair<String, String>> headers = null;
    private List<Interceptor> interceptors = null;
    private Context context;
    private Cache cache;

    public CustomHttpClient setShowLog(boolean showLog) {
        this.showLog = showLog;
        return this;
    }

    public CustomHttpClient setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    public CustomHttpClient setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    public CustomHttpClient setWriteTimeout(int writeTimeout) {
        this.writeTimeout = writeTimeout;
        return this;
    }

    public CustomHttpClient addHeader(@NonNull Pair<String, String> header) {
        if (headers == null) {
            headers = new ArrayList<>();
        }
        headers.add(header);
        return this;
    }

    public CustomHttpClient addInterceptor(Interceptor interceptor) {
        if (interceptors == null) {
            interceptors = new ArrayList<>();
        }
        interceptors.add(interceptor);
        return this;
    }


    public CustomHttpClient setContext(Application applicationContext) {
        this.context = applicationContext;
        return this;
    }

    /**
     * Set network cache config.
     * You must call method {@link #setContext(Application)} before calling this method.
     *
     * @param cacheFileName cache file name.
     * @param maxCacheSize cache max size.
     * @return CustomHttpClient
     */
    public CustomHttpClient setCache(String cacheFileName, long maxCacheSize) {
        if (context != null){
            File cacheFile = new File(context.getCacheDir(), cacheFileName);
            cache = new Cache(cacheFile, maxCacheSize);
        }
        return this;
    }

    public OkHttpClient createOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
                .readTimeout(readTimeout, TimeUnit.MILLISECONDS)
                .writeTimeout(writeTimeout, TimeUnit.MILLISECONDS);
        //拦截服务器端的Log日志并打印,如果未debug状态就打印日志，否则就什么都不做
        builder.addInterceptor(new HttpLoggingInterceptor().setLevel(showLog ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE));

        if (headers != null && !headers.isEmpty())
            builder.addInterceptor(createHeaderInterceptor());

        if (interceptors != null && !interceptors.isEmpty()) {
            for (Interceptor it : interceptors) {
                builder.addInterceptor(it);
            }
        }

        if (cache != null) {
            builder.cache(cache);
            builder.addNetworkInterceptor(createCacheInterceptor());
        }
        return builder.build();
    }

    private Interceptor createHeaderInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder();
                for (Pair<String, String> header : headers) {
                    requestBuilder.addHeader(header.first, header.second);
                }
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };
    }

    private Interceptor createCacheInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                // Add FORCE_CACHE cache control for each request if network is not available.
                if (!ConnectivityHelper.isNetworkAvailable(context)) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }

                Response originalResponse = chain.proceed(request);
                if (ConnectivityHelper.isNetworkAvailable(context)) {
                    String cacheControl = request.cacheControl().toString();
                    // Add cache control header for response same as request's while network is available.
                    return originalResponse.newBuilder()
                            .header("Cache-Control", cacheControl)
                            .build();
                } else {
                    // Add cache control header for response to FORCE_CACHE while network is not available.
                    return originalResponse.newBuilder()
                            .header("Cache-Control", CacheControl.FORCE_CACHE.toString())
                            .build();
                }
            }
        };
    }
}
