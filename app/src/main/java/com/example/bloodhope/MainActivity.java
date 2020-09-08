package com.example.bloodhope;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.bloodhope.adapter.RecyclerViewRequestsAdapter;
import com.example.bloodhope.constant.ConstantValues;
import com.example.bloodhope.model.RequestDataModel;
import com.example.bloodhope.screens.MakeRequestActivity;
import com.example.bloodhope.screens.SearchForDonorsActivity;
import com.example.bloodhope.utils.AppController;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<RequestDataModel>requestDataModelList;
    private Button buttonMakeRequest;
    private TextView textViewLocation;
    private Context context = MainActivity.this;
    String TAG = "buttonCheck";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestDataModelList = new ArrayList<>();
        buttonMakeRequest = findViewById(R.id.buttonMakeARequestMainActivity);
        textViewLocation  = findViewById(R.id.textViewLocationMainActivity);

        recyclerView = findViewById(R.id.RecyclerViewRequestsMainActivity);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        getRequestsFromDatabase();

        adapter = new RecyclerViewRequestsAdapter(this,requestDataModelList);
        recyclerView.setAdapter(adapter);


    buttonMakeRequest.setOnClickListener(new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            Intent intent = new Intent(MainActivity.this, MakeRequestActivity.class);
            startActivity(intent);
        }
    });



    }

    private void getRequestsFromDatabase()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstantValues.getRequestUrl, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {

                Gson gson = null;
                Type type = null;
                List<RequestDataModel>dataModels = null;
                try
                {
                    gson = new Gson();
                    type = new TypeToken<List<RequestDataModel>>(){}.getType();
                    dataModels = gson.fromJson(response,type);

                } catch (JsonSyntaxException e)
                {
                    e.printStackTrace();
                }
                requestDataModelList.addAll(dataModels);
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onErrorResponse: "+error.getMessage());

            }

        })
        {
            @Override
            protected Map<String,String> getParams() throws AuthFailureError
            {

                Map<String,String>params = new HashMap<>();
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);


    }

   public void searchDonorByLocation(View view)
   {
       startActivity(new Intent(context, SearchForDonorsActivity.class));
   }
}