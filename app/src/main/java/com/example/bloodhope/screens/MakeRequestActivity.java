package com.example.bloodhope.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbEndpoint;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.bloodhope.MainActivity;
import com.example.bloodhope.R;
import com.example.bloodhope.constant.ConstantValues;
import com.example.bloodhope.utils.AppController;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MakeRequestActivity extends AppCompatActivity
{
    private Button buttonPostRequest;
    private EditText editTextMessage;
    private Context context = MakeRequestActivity.this;
    private String TAG = "buttonCheck";
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_request);
        getSupportActionBar().setTitle("Post Request");
        getSupportActionBar().setElevation(0);
        buttonPostRequest = findViewById(R.id.buttonMakeRequestActivityPostRequest);
        editTextMessage = findViewById(R.id.editTextMakeRequestActivityMessage);
        progressBar = findViewById(R.id.progressBarMakeRequest);

        buttonPostRequest.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String message = editTextMessage.getText().toString();
                if(message.isEmpty())
                {
                    Toast.makeText(MakeRequestActivity.this, "Please type message", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    postRequest(message);
                }
            }
        });
    }

    private void postRequest(final String message)
    {
        progressBar.setVisibility(View.VISIBLE);
        final String number = PreferenceManager.getDefaultSharedPreferences(this).getString("contact","12345");

        StringRequest stringRequest = new StringRequest(Request.Method.POST,ConstantValues.uploadUrl, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {

                if(response.contains("Success"))
                {


                    progressBar.setVisibility(View.GONE);
                    startActivity(new Intent(MakeRequestActivity.this,MainActivity.class));
                    finish();

                }
                else
                {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MakeRequestActivity.this, "Something went Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MakeRequestActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onErrorResponse: "+error.getMessage());

            }

        })
        {
            @Override
            protected Map<String,String>getParams() throws AuthFailureError
            {

                Map<String,String>params = new HashMap<>();
                params.put("message",message);
                params.put("number",number);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);

    }
}