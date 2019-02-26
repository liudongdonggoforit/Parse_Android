package com.ss.parse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.ss.base.imageloader.ILFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ILFactory.getLoader().loadResource((ImageView) findViewById(R.id.image), R.mipmap.ic_launcher,
                new RequestOptions().transform(new RoundedCorners(30)));
    }
}
