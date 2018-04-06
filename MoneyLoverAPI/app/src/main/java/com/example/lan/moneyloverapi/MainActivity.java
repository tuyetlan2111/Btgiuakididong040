package com.example.lan.moneyloverapi;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    Adapter adapter;
    ListView listView;
    ArrayList<InforMoney> moneyArrayList;
    FloatingActionButton floatingButton;
    EditText noidung, tien, thoigian;
    RadioButton thu, chi;
    Button themdialog, huydialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();


        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.layout_dialog_add);


                noidung = dialog.findViewById(R.id.edtnoidung);
                tien = dialog.findViewById(R.id.edttien);
                thoigian = dialog.findViewById(R.id.edtngay);
                thu = dialog.findViewById(R.id.radiothu);
                chi = dialog.findViewById(R.id.radiochi);
                themdialog = dialog.findViewById(R.id.btnthem);
                huydialog = dialog.findViewById(R.id.btnhuy);

                themdialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (thu.isChecked() == false && chi.isChecked() == false || noidung.getText().toString().trim().isEmpty() || tien.getText().toString().trim().isEmpty() || thoigian.getText().toString().trim().isEmpty()) {
                            Toast.makeText(MainActivity.this, "Vui lòng nhập thông tin đầy đủ", Toast.LENGTH_SHORT).show();
                        } else {
                            String tnd, ttien, tthoigian, tchi, tthu;
                            tnd = noidung.getText().toString().trim();
                            ttien = tien.getText().toString().trim();
                            tthoigian = thoigian.getText().toString().trim();
                            tthu = thu.getText().toString();
                            tchi = chi.getText().toString();
                            InforMoney obj = new InforMoney(0, thu.isChecked(), tnd, Integer.valueOf(ttien), tthoigian);

                            // them vao arraylist, bao adapter co su thay doi

                            // post len db, dung asynctask

                            SetEmptyText();
                        }
                    }

                });

                huydialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });

                dialog.show();

            }
        });
    }

    private class MyAsyncTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... strings) {

            return readURL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            moneyArrayList.clear();
            String jsonStr = s.trim();
            try {
                JSONArray jsonArray = new JSONArray(jsonStr);
                for (int i = 0; i < jsonArray.length(); i++) {
                    int id = jsonArray.getJSONObject(i).optInt("id");
                    String noiDung = jsonArray.getJSONObject(i).optString("noi_dung");
                    String ngay = jsonArray.getJSONObject(i).optString("ngay");
                    String tien = jsonArray.getJSONObject(i).optString("so_tien");
                    int loai = jsonArray.getJSONObject(i).optInt("loai");
                    // 0 la chi
                    // 1 thu
                    moneyArrayList.add(new InforMoney(id, (loai == 0 ? false : true), noiDung, Integer.valueOf(tien), ngay));
                }
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }


    }
    private class PostToServer extends AsyncTask<String,Void,String>{

        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        String noidung, ngay;
        int loai, tien;

        public PostToServer(String noidung, String ngay, int loai, int tien) {
            this.noidung = noidung;
            this.ngay = ngay;
            this.loai = loai;
            this.tien = tien;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("",s);
        }

        @Override
        protected String doInBackground(String... strings) {

            noidung = noidung.replace(" ", "%20");

            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
            RequestBody requestBody = RequestBody.create(mediaType,"&loai=" + loai +"noi_dung=" + noidung+"&so_tien=" + tien  + "&ngay=" + ngay + "");
            Request request = new Request.Builder()
                    .url(strings[0])
                    .post(requestBody)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .build();

            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (IOException exc) {
                exc.printStackTrace();
                Log.d("AAA", exc.toString());
            }

            return null;
        }
    }

    private String readURL(String theUrl) {
        StringBuilder content = new StringBuilder();
        try {
            URL url = new URL(theUrl);
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    public void init() {
        floatingButton = findViewById(R.id.fltbtn);
        moneyArrayList = new ArrayList<>();
        adapter = new Adapter(MainActivity.this, R.layout.layout_dong_infor, moneyArrayList);
        listView = findViewById(R.id.lvds);
        listView.setAdapter(adapter);

        new MyAsyncTask().execute("http://192.168.1.23:9000/api/money");
    }

    public void SetEmptyText() {
        noidung.setText("");
        thoigian.setText("");
        tien.setText("");
        thu.setChecked(false);
        chi.setChecked(false);
    }
}
