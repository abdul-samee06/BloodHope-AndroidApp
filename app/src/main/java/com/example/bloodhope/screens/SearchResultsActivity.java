package com.example.bloodhope.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.bloodhope.R;
import com.example.bloodhope.adapter.SearchResultsAdapter;
import com.example.bloodhope.model.SearchResultsModel;

import java.util.List;

public class SearchResultsActivity extends AppCompatActivity
{

    private static final String TAG = "buttonCheck" ;
    List<SearchResultsModel>resultsList;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        getSupportActionBar().setTitle("Hopes in City");
        getSupportActionBar().setElevation(0);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        {
            resultsList = (List<SearchResultsModel>) bundle.getSerializable("list");
            Log.d(TAG, "onCreate: "+resultsList.toString());
        }

        recyclerView = findViewById(R.id.recyclerViewActivitySearchResults);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SearchResultsAdapter(this,resultsList);
        if(resultsList !=null)
        {
            recyclerView.setAdapter(adapter);
        }
        else
        {
            Toast.makeText(this, " No Donor Available ", Toast.LENGTH_SHORT).show();
        }



    }
}