package com.example.bloodhope.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.bloodhope.MainActivity;
import com.example.bloodhope.R;
import com.example.bloodhope.constant.ConstantValues;
import com.example.bloodhope.utils.AppController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity
{
    private static final String TAG = "buttonCheck" ;
    private List<String>validBloodGroups = new ArrayList<>();
    private EditText editTextName, editTextContact ,  editTextPassword,editTextCity, editTextBloodGroup;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().setTitle("Join US");
        getSupportActionBar().setElevation(0);

        progressBar = findViewById(R.id.progressBarSignUp);
        editTextBloodGroup = findViewById(R.id.editTextSignUpActivityBloodGroup);
        editTextName = findViewById(R.id.editTextSignUpActivityName);
        editTextContact = findViewById(R.id.editTextSignUpActivityContact);
        editTextPassword = findViewById(R.id.editTextSignUpActivityPassword);
        editTextCity = findViewById(R.id.editTextSignUpActivityCity);

        for(int i=0;i<ConstantValues.bloodGroupArray.length;i++)
        {
            validBloodGroups.add(ConstantValues.bloodGroupArray[i]);
        }


    } // onCreate closed


    public void onClickSignUp(View view)
    {

        String name = editTextName.getText().toString().toLowerCase();
        String contact = editTextContact.getText().toString().toLowerCase();
        String city = editTextCity.getText().toString().toLowerCase();
        String password  = editTextPassword.getText().toString().toLowerCase();
        String blood_group = editTextBloodGroup.getText().toString().toLowerCase();
        if(checkValidation(name,city,contact,password,blood_group))
        {
            register(name,city,contact,password, blood_group);
        }


    }

    private void register(final String name, final String city, final String contact, final String password, final String blood_group)
    {
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,ConstantValues.registerUrl, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {

                if(response.contains("Success"))
                {

                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(SignUpActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUpActivity.this,LogInActivity.class));
                    finish();

                }
                else
                {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(SignUpActivity.this, "Sign Up Failed", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(SignUpActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onErrorResponse: "+error.getMessage());

            }

        })
        {
            @Override
            protected Map<String,String>getParams() throws AuthFailureError
            {

                Map<String,String>params = new HashMap<>();
                params.put("name",name);
                params.put("city",city);
                params.put("contact",contact);
                params.put("password",password);
                params.put("blood_group",blood_group);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }


    private boolean checkValidation(String name, String city, String contact, String password,String blood_group)
    {

        if(name.isEmpty())
        {
            editTextName.setError("Empty Field");
            return false;
        }
        else if(contact.isEmpty())
        {
            editTextContact.setError("Empty Field");
            return false;
        }
        else if(!validBloodGroups.contains(blood_group))
        {
            editTextBloodGroup.setError("Enter valid blood group");
            return false;
        }
        else if(city.isEmpty())
        {
            editTextCity.setError("Empty Field");
            return false;
        }

        else if(password.isEmpty())
        {
            editTextPassword.setError("Empty Field");
            return false;
        }
        else
        {
            return true;
        }

    }

}