package com.example.bloodhope.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
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

import java.util.HashMap;
import java.util.Map;

public class LogInActivity extends AppCompatActivity
{
    private static final String TAG = "buttonCheck" ;
    private EditText editTextNumber , editTextPassword;
    private TextView textViewCreateNewAccount;
    private Context context = LogInActivity.this;
    private ProgressBar progressBar;
    // button has onCLick method
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        getSupportActionBar().setTitle("Log In");
        getSupportActionBar().setElevation(0);

        progressBar = findViewById(R.id.progressBarLogIn);


      textViewCreateNewAccount =  findViewById(R.id.textViewLogInActivityCreateAccount);

      textViewCreateNewAccount.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(context,SignUpActivity.class));
            }
        });

      editTextNumber = findViewById(R.id.editTextLogInActivityNumber);
      editTextPassword = findViewById(R.id.editTextLogInActivityPassword);

    }



    public void onClickLogIn(View view)
    {
        String number = editTextNumber.getText().toString().toLowerCase().trim();
        String pass = editTextPassword.getText().toString().toLowerCase().trim();
        if(number.isEmpty() || pass .isEmpty())
        {
            if(number.isEmpty())
            {

                editTextNumber.setError(getString(R.string.emptyfielderror));
            }
            else if (pass.isEmpty())
            {
                editTextPassword.setError(getString(R.string.emptyfielderror));
            }
        }
        else
        {
                logInToAccount(number,pass);
        }

    }

    private void logInToAccount(final String number, final String pass)
    {
        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstantValues.logInUrl, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                if(response.contains("Success"))
                {

                    Log.d(TAG, "onResponse: "+response);;
                    progressBar.setVisibility(View.GONE);
                    startActivity(new Intent(context, MainActivity.class));
                    PreferenceManager.getDefaultSharedPreferences(context).edit().putString("contact",number).apply();

                    finish();

                }
                else
                {
                    Toast.makeText(context, "Invalid Number or Password", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onResponse: "+response);
                    progressBar.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onErrorResponse: "+error.getMessage());
                progressBar.setVisibility(View.GONE);

            }

        })
        {
            @Override
            protected Map<String,String> getParams() throws AuthFailureError
            {

                Map<String,String>params = new HashMap<>();
                params.put("contact",number);
                params.put("password",pass);
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest);






    }
}