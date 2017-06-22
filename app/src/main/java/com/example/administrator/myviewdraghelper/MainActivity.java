package com.example.administrator.myviewdraghelper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    MyDragLayout myDragLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDragLayout= (MyDragLayout) findViewById(R.id.mydraglayout);
        findViewById(R.id.btn_close).setOnClickListener(this);
        findViewById(R.id.btn_open).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_close:
                myDragLayout.close();
                break;
            case R.id.btn_open:
                myDragLayout.open();
                break;
        }
    }
}
