package org.zackratos.shanbaywork.loadimage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import org.zackratos.shanbaywork.R;
import org.zackratos.shanbaywork.loadimage.imageloader.ImageInfo;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;

public class ImageActivity extends AppCompatActivity {

    private static final String TAG = "ImageActivity";


    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, ImageActivity.class);
        return intent;
    }

    private static final String NAME_1 = "image - 1";
    private static final String NAME_2 = "image - 2";
    private static final String NAME_3 = "image - 3";
    private static final String NAME_4 = "image - 4";
    private static final String NAME_5 = "image - 5";
    private static final String NAME_6 = "image - 6";
    private static final String NAME_7 = "image - 7";
    private static final String NAME_8 = "image - 8";


    private static final String URL_1 = "https://static.baydn.com/media/media_store/image/f1672263006c6e28bb9dee7652fa4cf6.jpg";
    private static final String URL_2 = "https://static.baydn.com/media/media_store/image/8c997fae9ebb2b22ecc098a379cc2ca3.jpg";
    private static final String URL_3 = "https://static.baydn.com/media/media_store/image/2a4616f067285b4bd59fe5401cd7106b.jpeg";
    private static final String URL_4 = "https://static.baydn.com/media/media_store/image/b0e3ab329c8d8218d2af5c8dfdc21125.jpg";
    private static final String URL_5 = "https://static.baydn.com/media/media_store/image/670abb28408a9a0fc3dd9666e5ca1584.jpeg";
    private static final String URL_6 = "https://static.baydn.com/media/media_store/image/1e8d675468ab61f4e5bdebd4bcb5f007.jpeg";
    private static final String URL_7 = "https://static.baydn.com/media/media_store/image/9b2f93cbfa104dae1e67f540ff14a4c2.jpg";
    private static final String URL_8 = "https://static.baydn.com/media/media_store/image/f5e0631e00a09edbbf2eb21eb71b4d3c.jpeg";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        RecyclerView rv = (RecyclerView) findViewById(R.id.recycler_view);

        rv.setLayoutManager(new GridLayoutManager(this, 3));

        ImageInfo[] imageInfos = new ImageInfo[] {
                new ImageInfo(NAME_1, URL_1),
                new ImageInfo(NAME_2, URL_2),
                new ImageInfo(NAME_3, URL_3),
                new ImageInfo(NAME_4, URL_4),
                new ImageInfo(NAME_5, URL_5),
                new ImageInfo(NAME_6, URL_6),
                new ImageInfo(NAME_7, URL_7),
                new ImageInfo(NAME_8, URL_8)
        };


        rv.setAdapter(new ImageAdapter(this, imageInfos));


        Observable<String> observable1 = Observable.just("1111");

        Observable<String> observable2 = Observable.just("2222");

        Observable<String> observable3 = Observable.just("3333");



        Observable.concat(observable1, observable2, observable3)
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(@NonNull String s) throws Exception {
                        return !TextUtils.isEmpty(s);
                    }
                })
                .subscribe(new Observer<String>() {

                    Disposable d;

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        this.d = d;
                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        Log.d(TAG, "onNext: " + s);
                        if (!d.isDisposed()) {
                            d.dispose();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }



}
