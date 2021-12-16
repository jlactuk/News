package com.example.news;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.logging.Logger;

public class News extends AppCompatActivity {
    RecyclerView list;
    TextView title;
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        title = findViewById(R.id.title);
        title.setOnClickListener(v -> {
            addNew();
        });

    }
    public void addNew() {
        list = findViewById(R.id.list_news);
        RecyclerView.LayoutParams news = new RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        ImageView image = new ImageView(getApplicationContext());
        image.setImageDrawable(getDrawable(R.drawable.bgc));
        image.setPadding(30,30,30,30 );

        TextView text = new TextView(getApplicationContext());
        text.setTextColor(Color.BLACK);
        text.setText(R.string.test);
        list.addView(image);
        news = new RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        list.addView(text);

    }
}