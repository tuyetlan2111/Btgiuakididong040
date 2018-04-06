package com.example.lan.moneyloverapi;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Lan on 4/6/2018.
 */

public class Tinhtoan extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private Spinner spnloai,spndate;
    private TextView tvspnloai,tvspndate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_thoigian);
        spnloai = findViewById(R.id.spinnerloai);
//        tvspnloai= findViewById(R.id.tvspinerloai);
        spndate = findViewById(R.id.spinnerdate);
//        tvspndate= findViewById(R.id.tvspinerdate);

        ArrayAdapter<CharSequence> arrayradapter = ArrayAdapter.createFromResource(this,R.array.spinnerLoai,android.R.layout.simple_spinner_item);
       arrayradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

       spnloai.setAdapter(arrayradapter);
       spndate.setAdapter(arrayradapter);


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text=adapterView.getItemAtPosition(i).toString();
        
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
