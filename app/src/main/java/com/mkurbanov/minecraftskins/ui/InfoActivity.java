package com.mkurbanov.minecraftskins.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.gson.Gson;
import com.mkurbanov.minecraftskins.App;
import com.mkurbanov.minecraftskins.BuildConfig;
import com.mkurbanov.minecraftskins.R;
import com.mkurbanov.minecraftskins.data.HomeModel;
import com.mkurbanov.minecraftskins.data.database.models.Favorite;
import com.mkurbanov.minecraftskins.tools.Functions;

import java.io.File;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class InfoActivity extends AppCompatActivity implements View.OnClickListener {
    private boolean isAddedToFav = false;
    private ImageView imageViewFav;
    private HomeModel.Yo4List data;
    private Button btnDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        TextView tvTitle = findViewById(R.id.text_title);
        TextView tvText = findViewById(R.id.text_text);
        btnDownload = findViewById(R.id.button_download);
        ImageView imageView = findViewById(R.id.image);
        imageViewFav = findViewById(R.id.image_fav);
        ImageButton btnClose = findViewById(R.id.button_back);
        data = new Gson().fromJson(getIntent().getStringExtra("data"), HomeModel.Yo4List.class);

        imageView.setImageBitmap(App.getInstance().getFunctions().getBitmapFromAssets("images/" + getIntent().getExtras().getString("image")));
        tvTitle.setText(getIntent().getExtras().getString("title"));
        tvText.setText(getIntent().getExtras().getString("text"));
        isAddedToFav = getIntent().getBooleanExtra("fav", false);

        check();

        btnClose.setOnClickListener(this);
        btnDownload.setOnClickListener(this);
        imageViewFav.setOnClickListener(this);
    }

    private void check() {
        if (isAddedToFav) {
            imageViewFav.setBackgroundResource(R.drawable.progress_bg);
            imageViewFav.setColorFilter(getResources().getColor(R.color.white));
        } else {
            imageViewFav.setBackgroundResource(R.drawable.progress_bg_white);
            imageViewFav.setColorFilter(getResources().getColor(R.color.colorAccent));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_back:
                finish();
                break;
            case R.id.image_fav:
                if (isAddedToFav) {
                    new Thread(() -> App.getInstance().db.favDao().deleteByFileName(data.yo4f2)).start();
                } else {
                    new Thread(() -> App.getInstance().db.favDao().insert(new Favorite(data.yo4f2, data.yo4t3, data.yo4i1, data.yo4_jo2x6, data.yo4_1apqg, data.yo4d4, data.yo4_6g))).start();
                }
                isAddedToFav = !isAddedToFav;
                check();
                break;
            case R.id.button_download:
                if (btnDownload.getText().toString().equals(getString(R.string.install))) {
                    test();
                    return;
                }
                btnDownload.setText(getString(R.string.downloading));
                download().observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(o -> {
                            btnDownload.setText(getString(R.string.install));
                        });
                break;
        }
    }

    private Observable download() {
        return Observable.create(emitter -> {
            TimeUnit.SECONDS.sleep(2);
            emitter.onNext(true);
        });
    }


    private void test() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setPackage("com.mojang.minecraftpe");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        Functions.copyFile("files/" + getIntent().getExtras().getString("file"));
        String newFileName = "/data/data/" + this.getPackageName() + "/" + "files/" + getIntent().getExtras().getString("file");
        Uri uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", new File(newFileName));
        intent.setDataAndType(uri, "application/octet-stream");
        //intent.setData(Uri.parse("minecraft://?=import=" + uri.toString()));

        // check is app installed or not?
        if (Functions.isPackageInstalled("com.mojang.minecraftpe", getPackageManager())) {
            startActivity(intent);
        } else openPlayStore();
    }

    private void openPlayStore() {
        final String appPackageName = "com.mojang.minecraftpe";
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }
}