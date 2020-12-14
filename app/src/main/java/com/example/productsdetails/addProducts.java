package com.example.productsdetails;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class addProducts extends AppCompatActivity {
    Spinner spinner;
    private String productRandomKey;
    String pcategory, saveCurrentDate, saveCurrentTime;
    EditText pname,pprice,pdescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products);
        pname=findViewById(R.id.txtProductName);
        pprice=findViewById(R.id.txtProductPrice);
        pdescription=findViewById(R.id.txtProductDesc);
        Button btn_add=findViewById(R.id.btnAddProduct);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveProductInfoToDatabase();
            }
        });




        String[] category_list = getResources().getStringArray(R.array.computerSystem);
         spinner=findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,category_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pcategory = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    public void SaveProductInfoToDatabase() {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MM,dd,yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        RetrofitInterface mApiService = this.getInterfaceService();

        Call<String> responseBodyCall = mApiService.Add(saveCurrentDate,saveCurrentTime,pname.getText().toString(),pprice.getText().toString(),pdescription.getText().toString());

        responseBodyCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if(response.body().toLowerCase().contains("Success")){

                    Toast.makeText(addProducts.this, "Successfully Added", Toast.LENGTH_LONG).show();
                    pprice.setText("");
                    pname.setText("");
                    pdescription.setText("");
                    pname.requestFocus();
                }
                else{
                    Toast.makeText(addProducts.this, response.body(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(addProducts.this, t.toString(),
                        Toast.LENGTH_LONG).show();
            }
        });



    }
    private RetrofitInterface getInterfaceService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.2.64:4040")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final RetrofitInterface mInterfaceService = retrofit.create(RetrofitInterface.class);
        return mInterfaceService;
    }


}