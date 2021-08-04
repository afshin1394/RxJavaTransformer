package com.samiei.rxtransformer;




import retrofit2.http.GET;
import retrofit2.http.Query;
import io.reactivex.Observable;
import retrofit2.Response;

public interface ApiServiceOwghat {

    @GET("owghat/")
    Observable<Response<OwghatResult>> getOwghatByLatLong(@Query("lat") double lat , @Query("long") double lng);
}
