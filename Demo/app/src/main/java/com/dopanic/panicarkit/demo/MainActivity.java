package com.dopanic.panicarkit.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startARPortrait(View view) {
        Intent intent = new Intent(this, ARPortraitActivity.class);
        startActivity(intent);
    }
    public void startARLandscape(View view) {
        Intent intent = new Intent(this, ARLandscapeActivity.class);
        startActivity(intent);
    }
    public void startARAutoOrienting(View view) {
        Intent intent = new Intent(this, ARAutoOrientingActivity.class);
        startActivity(intent);
    }

}
