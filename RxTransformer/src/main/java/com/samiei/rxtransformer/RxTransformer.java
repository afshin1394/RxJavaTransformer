package com.samiei.rxtransformer;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import retrofit2.Response;

public class RxTransformer {

    public static int maxRetries = 3;
    public static int retryDelayMillis = 2000;

    public static <T > ObservableTransformer<Response<T>, Response<T>> parseHttpErrors(String CLASS_NAME, String ACTIVITY_NAME, String FUNCTION, String FUNCTION_CHILD) {
        return upstream -> upstream
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(Observable.empty())
                .timeout(2, TimeUnit.MINUTES)
                .doOnNext((Consumer<Response<T>>) t -> {
                    Log.i("ObservableTransformer", "doOnNext: code" + t.code() + "body" + t.raw().body());
                    String errorBody = "";
                    String message = "";

                    if (t != null)
                    {
                        RxProperties.HttpErrorCode httpErrorCode = RxProperties.VerifyHttpError(t);
                        if (!httpErrorCode.equals(RxProperties.HttpErrorCode.Success))
                        {

                            switch (httpErrorCode) {


                                case Bad_Gateway:
                                    errorBody = "Bad_Gateway";
                                    break;

                                case BadRequest:
                                    errorBody = "BAD_REQUEST";
                                    break;


                                case Forbidden:
                                    errorBody = "Forbidden";
                                    break;


                                case Internal_Server_Error:
                                    errorBody = "Internal_Server_Error";
                                    break;


                                case Not_Found:
                                    errorBody = "Not_Found";
                                    break;


                                case Service_Unavailable:
                                    errorBody = "Service_Unavailable";
                                    break;


                                case Gateway_Timeout:
                                    errorBody = "Gateway_Timeout";
                                    break;


                                case Unauthorized:
                                    errorBody = "Unauthorized";
                                    break;

                                case Unknown:
                                    errorBody = "NullData";
                                    break;
                            }
                            message = String.format("error body : %1$s , code : %2$s , url : %3$s", errorBody, t.code(), t.raw().request().url());
                            throw new Exception(new Throwable(message, new Throwable(t.raw().request().url().toString())));
                        }

                    }
                    else
                    {
                        errorBody = "null-response";
                        message = String.format("error body : %1$s , code : %2$s , url : %3$s", errorBody, t.code(), t.raw().request().url());
                        throw new Exception(new Throwable(message, new Throwable(t.raw().request().url().toString())));
                    }
                }).retryWhen(new RetryWithDelay(maxRetries, retryDelayMillis));


    }



}
