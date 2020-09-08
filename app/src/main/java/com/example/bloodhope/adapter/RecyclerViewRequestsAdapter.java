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

import java.util.List;

public class RecyclerViewRequestsAdapter extends RecyclerView.Adapter<RecyclerViewRequestsAdapter.ViewHolder>
{

    Context context;
    List<RequestDataModel>requestDataModelList;

    public RecyclerViewRequestsAdapter(Context context, List<RequestDataModel> requestDataModelList)
    {
        this.context = context;
        this.requestDataModelList = requestDataModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_main_activity_requests,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {

        RequestDataModel bean = requestDataModelList.get(position);


        holder.textViewMessage.setText(""+bean.getMessage());

        final String number = bean.getNumber();


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
        return requestDataModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView textViewMessage;
        private ImageView imageViewCall,imageViewMessage;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            textViewMessage  = itemView.findViewById(R.id.textViewRecyclerViewRequestsMessage);
            imageViewCall = itemView.findViewById(R.id.imageViewSearchResultAdapterCall);
            imageViewMessage = itemView.findViewById(R.id.imageViewSearchResultAdapterMessage);



        }

    }
}
