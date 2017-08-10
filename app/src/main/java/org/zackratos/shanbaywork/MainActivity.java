package org.zackratos.shanbaywork;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.zackratos.shanbaywork.customcontrols.DialogActivity;
import org.zackratos.shanbaywork.customcontrols.ViewActivity;
import org.zackratos.shanbaywork.loadimage.GlideActivity;
import org.zackratos.shanbaywork.loadimage.RxLoaderActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        rxTest();
    }



    public void customDialog(View view) {
        enter(DialogActivity.newIntent(this));
    }



    public void customView(View view) {
        enter(ViewActivity.newIntent(this));
    }



    public void loadGlide(View view) {
        enter(GlideActivity.newIntent(this));
    }


    public void loadRxJava(View view) {
        enter(RxLoaderActivity.newIntent(this));
    }




    private void enter(Intent intent) {
        startActivity(intent);
    }





/*
    private void rxTest() {
        Observable<String> observable1 = Observable.just("1111");
        Observable<String> observable2 = Observable.just("2222");
        Observable<String> observable3 = Observable.just("3333");


        Observable.concat(observable1, observable2, observable3)
                .first("1111").toObservable()
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(@NonNull String s) throws Exception {
                        return true;
                    }
                })
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        Log.d(TAG, "accept: " + s);
                    }
                })
                .subscribe(new Observer<String>() {

                    Disposable disposable;

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        Log.d(TAG, "onNext: " + s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
*/




}
