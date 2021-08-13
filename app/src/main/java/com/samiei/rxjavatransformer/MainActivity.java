package com.samiei.rxjavatransformer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.samiei.rxtransformer.ApiClientOwghat;
import com.samiei.rxtransformer.ApiServiceOwghat;
import com.samiei.rxtransformer.OwghatResult;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ApiServiceOwghat apiServiceOwghat = ApiClientOwghat.getInstance().getClientServiceOwghat();

        apiServiceOwghat.getOwghatByLatLong(34.0893,34.0824)
                .compose(RxTransformer.parseHttpErrors("MainActivity.class","MainActivity","onCreate"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<OwghatResult>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<OwghatResult> owghatResultResponse) {
                     //response
                    }

                    @Override
                    public void onError( Throwable e) {
                        //reason of network error
                        // e.getMessage();

                         //url of network error
                         //e.getCause()

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
