package com.example.bloodhope.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.bloodhope.R;
import com.example.bloodhope.constant.ConstantValues;
import com.example.bloodhope.model.RequestDataModel;
import com.example.bloodhope.model.SearchResultsModel;
import com.example.bloodhope.utils.AppController;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchForDonorsActivity extends AppCompatActivity
{

    private EditText editTextCity , editTextBlood;
    private List<String> validBloodGroups = new ArrayList<>();
    private Context context = SearchForDonorsActivity.this;
    private String TAG = "buttonCheck";
    private List<SearchResultsModel>searchResultsModelsList;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_for_blood);
        getSupportActionBar().setTitle("Search for Hope");
        getSupportActionBar().setElevation(0);
        searchResultsModelsList = new ArrayList<>();
        editTextBlood = findViewById(R.id.editTextSearchForBloodActivityBloodGroup);
        editTextCity = findViewById(R.id.editTextSearchForBloodActivityCity);
        progressBar = findViewById(R.id.progressBarSearchDonor);

        for(int i = 0; i< ConstantValues.bloodGroupArray.length; i++)
        {
            validBloodGroups.add(ConstantValues.bloodGroupArray[i]);
        }
    }
    public void onClickFindDonor(View view)
    {
        String city = editTextCity.getText().toString().toLowerCase().trim();
        String blood_group = editTextBlood.getText().toString().toLowerCase().trim();
        if(city.isEmpty())
        {
            editTextCity.setError("Enter a valid city name");
        }
        else if(!validBloodGroups.contains(blood_group))
        {
            editTextBlood.setError("Enter valid blood group");
        }
        else
        {
                searchForDonor(city,blood_group);
        }


    }

    private void searchForDonor(final String city , final String blood_group)
    {
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstantValues.findDonors, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Gson gson =  new Gson();
                Type type = new TypeToken<List<SearchResultsModel>>(){}.getType();
                List<SearchResultsModel>dataModels = gson.fromJson(response,type);
                if(dataModels!=null)
                {
                    searchResultsModelsList.addAll(dataModels);
                    progressBar.setVisibility(View.GONE);
                Intent intent = new Intent(context,SearchResultsActivity.class);
                intent.putExtra("list", (Serializable) searchResultsModelsList);
                startActivity(intent);
                }
                else
                {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(context, "List is empty", Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onErrorResponse: "+error.getMessage());

            }

        })
        {
            @Override
            protected Map<String,String> getParams() throws AuthFailureError
            {

                Map<String,String>params = new HashMap<>();
                params.put("city",city);
                params.put("blood_group",blood_group);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);

    }
}