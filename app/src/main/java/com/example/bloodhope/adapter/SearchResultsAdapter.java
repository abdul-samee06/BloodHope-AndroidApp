package com.example.bloodhope.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodhope.R;
import com.example.bloodhope.model.RequestDataModel;
import com.example.bloodhope.model.SearchResultsModel;

import java.util.List;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.ViewHolder>
{
    private Context context;
    private List<SearchResultsModel>searchResultsModelList;

    public SearchResultsAdapter(Context context, List<SearchResultsModel> searchResultsModelList)
    {
        this.context = context;
        this.searchResultsModelList = searchResultsModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result_recycler_adapter,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        SearchResultsModel bean = searchResultsModelList.get(position);
        holder.textViewName.setText("Name : "+bean.getName());
        holder.textViewCity.setText("From : "+bean.getCity());

        final String number = bean.getContact();


        holder.imageViewCall.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+number));
                context.startActivity(intent);

            }
        });

        holder.imageViewMessage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms",number, null)));
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return searchResultsModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView textViewName , textViewCity;
        private ImageView imageViewCall,imageViewMessage;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            textViewName  = itemView.findViewById(R.id.textViewSearchResultAdapterName);
            textViewCity = itemView.findViewById(R.id.textViewSearchResultAdapterCity);
            imageViewCall = itemView.findViewById(R.id.imageViewSearchResultAdapterCall);
            imageViewMessage = itemView.findViewById(R.id.imageViewSearchResultAdapterMessage);
        }

    }


}
