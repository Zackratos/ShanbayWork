package org.zackratos.shanbaywork.customcontrols;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.zackratos.shanbaywork.R;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/8.
 */

public class ParagraphAdapter extends RecyclerView.Adapter<ParagraphAdapter.ParagraphViewHolder> {

    private String[] paragraphs;

    private int start;
    private int end;

    private static final String TAG = "ParagraphAdapter";

//    private SpannableString spannableString;

    private AppCompatActivity activity;

    public ParagraphAdapter(AppCompatActivity activity, String[] paragraphs) {
        this.activity = activity;
        this.paragraphs = paragraphs;
    }


    @Override
    public ParagraphViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_paragraph, parent, false);


        return new ParagraphViewHolder(view);
    }



    @Override
    public void onBindViewHolder(final ParagraphViewHolder holder, int position) {

        final String paragraph = paragraphs[position];
        final SpannableString spannableString = new SpannableString(paragraph);

/*        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    ((TextView) holder.itemView).setText(spannableString);
                }
            }
        };
        cachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                start = 0;
                end = 0;
                for (String word : paragraph.split(" ")) {
                    end = start + word.length();
                    spannableString.setSpan(new ClickableSpan() {
                        @Override
                        public void onClick(View widget) {

                        }
                    }, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    start = end + 1;
                }
                Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);
            }
        });*/


        Observable.just(paragraph)
                .subscribeOn(Schedulers.newThread())
                .flatMap(new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(@NonNull String s) throws Exception {
                        return Observable.fromArray(s.split(" "));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        end = start + s.length();
                        spannableString.setSpan(new ClickableSpan() {
                            @Override
                            public void onClick(View widget) {
                                TextView tv = (TextView) widget;
                                String s = tv
                                        .getText()
                                        .subSequence(tv.getSelectionStart(),
                                                tv.getSelectionEnd()).toString();
                                QueryWordDialog dialog = QueryWordDialog.newInstance(s);
                                dialog.show(activity.getSupportFragmentManager(), "tag");

                            }

                            @Override
                            public void updateDrawState(TextPaint ds) {

                            }
                        }, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        start = end + 1;
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Log.d(TAG, "accept: " + throwable.getMessage());
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        ((TextView) holder.itemView).setText(spannableString);
                        ((TextView) holder.itemView).setMovementMethod(LinkMovementMethod.getInstance());
                        start = 0;
                        end = 0;
                    }
                });
    }


    @Override
    public int getItemCount() {
        return paragraphs.length;
    }



    class ParagraphViewHolder extends RecyclerView.ViewHolder {
        public ParagraphViewHolder(View itemView) {
            super(itemView);
        }
    }
}
