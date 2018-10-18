package yunnuo.baseframe.module;


import android.text.TextUtils;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import yunnuo.baseframe.BuildConfig;
import yunnuo.baseframe.function.gsonconverter.BaseGsonConverterFactory;
import yunnuo.baseframe.net.ApiService;

/**
 * Created by hxb on 2018/4/19.
 */
@Module
public class HttpModule {

    @Provides
    OkHttpClient.Builder provideOkHttpClient() {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }
                }
        };

        // Install the all-trusting trust manager
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        // Create an ssl socket factory with our all-trusting manager
        javax.net.ssl.SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.sslSocketFactory(sslSocketFactory);
        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        builder.connectTimeout(20, TimeUnit.SECONDS);
        builder.readTimeout(30, TimeUnit.SECONDS);
        builder.writeTimeout(30, TimeUnit.SECONDS);
        builder.protocols(Collections.singletonList(Protocol.HTTP_1_1));

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }

//        builder.addInterceptor(new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request.Builder build = chain.request()
//                        .newBuilder()
//                        .addHeader("AppId", "eSchool")
//                        .addHeader("AppSecrity", "")
//                        .addHeader("AppVersion", CommonUtil.getAppVersionName(MyApp.getInstance()))
//                        .addHeader("ClientOs", "ANDROID")
//                        .addHeader("ClientOsVersion", "")//操作系统版本
//                        .addHeader("ClientMac", CommonUtil.getIdentity(MyApp.getInstance()))//mac地址
//                        .addHeader("RefreshToken", SPUtils.getString(MyApp.getInstance(), "RefreshToken", ""))
//                        .addHeader("AccessToken", SPUtils.getString(MyApp.getInstance(), "AccessToken", ""))
//                        .addHeader("TranDate", DateUtils.getyyyymmddhhmmsssss())
//                        .addHeader("TranUUID", CommonUtil.getUUID())
//                        .addHeader("CampusId", SPUtils.getString(MyApp.getInstance(), "CampusId", ""))
//                        .addHeader("AccessTokenExpire",SPUtils.getString(MyApp.getInstance(),"AccessTokenExpire",""));
//                        if (!TextUtils.isEmpty(SPUtils.getString(MyApp.getInstance(), "UserId", ""))){
//                            String userId = SPUtils.getString(MyApp.getInstance(), "UserId", "");
//                            build.addHeader("UserId",userId );
//                        }
//                        return chain.proceed(build.build());
//            }
//        });

        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Response originalResponse = chain.proceed(chain.request());
                String accessToken = originalResponse.header("AccessToken");
                String refreshToken = originalResponse.header("RefreshToken");
                String userId = originalResponse.header("UserId");
                String campusId = originalResponse.header("CampusId");
                String CampusName = originalResponse.header("CampusName");
                String accessTokenExpire = originalResponse.header("AccessTokenExpire");

                if (!TextUtils.isEmpty(accessToken)) {
//                    SPUtils.setString(MyApp.getInstance(), "AccessToken", accessToken);
                }

                if (!TextUtils.isEmpty(userId)) {
//                    SPUtils.setString(MyApp.getInstance(), "UserId", userId);
                }

                if (!TextUtils.isEmpty(campusId)) {
//                    SPUtils.setString(MyApp.getInstance(), "CampusId", campusId);
                }
                if (!TextUtils.isEmpty(CampusName)) {
//                    SPUtils.setString(MyApp.getInstance(), "CampusName", URLDecoder.decode(CampusName, "UTF-8"));
                }

                if (!TextUtils.isEmpty(refreshToken)) {
//                    SPUtils.setString(MyApp.getInstance(), "RefreshToken", refreshToken);
                }

                if (!TextUtils.isEmpty(accessTokenExpire)){
//                    SPUtils.setString(MyApp.getInstance(),"AccessTokenExpire",accessTokenExpire);
                }

                return originalResponse;
            }
        });

        return builder;
    }

    @Provides
    ApiService provideApiService(OkHttpClient.Builder builder) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://180.168.146.77")
                .client(builder.build())
                .addConverterFactory(BaseGsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(ApiService.class);
    }

}
