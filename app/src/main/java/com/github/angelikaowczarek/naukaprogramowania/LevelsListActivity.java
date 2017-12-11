package com.github.angelikaowczarek.naukaprogramowania;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class LevelsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels_list);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        setupList();
    }

    private void setupList() {
        boolean nextFileExists = true;
        int fileCounter = 1;
        while (nextFileExists) {
          String SERIALIZED_FILE_NAME = "levels/level_" + fileCounter + ".xml";
            try {
                InputStream inputStream = new BufferedInputStream(new FileInputStream(SERIALIZED_FILE_NAME));
                // tu jakieś czytanie
                fileCounter++;

            } catch (FileNotFoundException e) {
                System.out.println("ERROR: File dvd.xml not found");
                nextFileExists = false;
            }
        }
    }

    public void runMainActivity(View view) {
        startActivity(
                new Intent(this, MainActivity.class));
    }
}
