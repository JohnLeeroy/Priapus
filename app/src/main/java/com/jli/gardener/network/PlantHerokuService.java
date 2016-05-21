package com.jli.gardener.network;

import com.jli.gardener.model.Report;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by john on 5/20/16.
 */
public interface PlantHerokuService {

//    @POST("query/{pageNumber}")
//    Call<List<Animal>> queryAnimals(@Path("pageNumber") int pageNumber, @Field("pageSize") int pageSize);
//
//    @GET("list/{species}/{pageNumber}")
//    Call<List<Animal>> getAnimals(@Path("species") String species,  @Path("pageNumber") int pageNumber);
//    @GET("list/{species}")
//    Call<List<Animal>> getAnimals(@Path("species") String species);

    @GET("reports")
    Call<Report> getLatestReport();


    @GET("reports/{id}")
    Call<Report> getReport(@Path("id") String id);


    @GET("reports/{id}/{timestamp}")
    Call<Report> getReport(@Path("id") String id, @Path("timestamp") String timestamp);

    /*
    router.get('/api/reports', api.getAllReports);
    router.get('/api/reports/:id', api.getUnitReports);
    router.get('/api/reports/:id/:date', api.getReportByDate);
    router.get('/api/reports/:id/:date/:time', api.getReportByDateTime);
     */
}

