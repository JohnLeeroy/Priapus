package com.jli.gardener.model;

import android.content.Context;

import com.jli.gardener.network.ModuleHerokuService;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by john on 5/20/16.
 */
public class ModuleProvider {

    private static final int CONNECT_TIMEOUT = 15;
    private static final int WRITE_TIMEOUT = 30;
    private static final int READ_TIMEOUT = 30;
    private static final long CACHE_SIZE = 4 * 1024 * 1024; // 10 MB

    ModuleHerokuService service;

    public interface ReportHandler {
        void onResult(boolean isSuccessful, Report result);
    }

    public ModuleProvider(Context context) {
        File httpCacheDirectory = new File(context.getCacheDir(), "cached_responses");
        Cache cache = new Cache(httpCacheDirectory, CACHE_SIZE);
        OkHttpClient client = new OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://priapus.herokuapp.com/api/")
                .client(client)
                .build();

        service = retrofit.create(ModuleHerokuService.class);
    }

    public void getLatestReport(final ReportHandler handler) {
        if(handler == null)
            return;

        Call<Report> reportCall = service.getLatestReport();
        reportCall.enqueue(new Callback<Report>() {
            @Override
            public void onResponse(Call<Report> call, Response<Report> response) {
                handler.onResult(true, response.body());
            }

            @Override
            public void onFailure(Call<Report> call, Throwable t) {
                handler.onResult(false, null);
            }
        });
    }

    public void getReports(final String moduleId, final ReportHandler handler) {
        if(handler == null)
            return;

        Call<Report> reportCall = service.getReport(moduleId);
        reportCall.enqueue(new Callback<Report>() {
            @Override
            public void onResponse(Call<Report> call, Response<Report> response) {
                handler.onResult(true, response.body());
            }

            @Override
            public void onFailure(Call<Report> call, Throwable t) {
                handler.onResult(false, null);
            }
        });
    }

    public void getReportsForDay(final String moduleId, final String datestamp, final ReportHandler handler) {
        if(handler == null)
            return;

        Call<Report> reportCall = service.getReport(moduleId, datestamp);
        reportCall.enqueue(new Callback<Report>() {
            @Override
            public void onResponse(Call<Report> call, Response<Report> response) {
                handler.onResult(true, response.body());
            }

            @Override
            public void onFailure(Call<Report> call, Throwable t) {
                handler.onResult(false, null);
            }
        });
    }
}
