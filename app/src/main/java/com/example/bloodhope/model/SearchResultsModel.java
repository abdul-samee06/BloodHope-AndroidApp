package com.example.bloodhope.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SearchResultsModel implements Serializable
{



    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("contact")
    @Expose
    private String contact;
    @SerializedName("city")
    @Expose
    private String city;


    public SearchResultsModel(String name, String contact, String city)
    {
        this.name = name;
        this.contact = contact;
        this.city = city;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getContact()
    {
        return contact;
    }

    public void setContact(String contact)
    {
        this.contact = contact;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    @Override
    public String toString()
    {
        return "SearchResultsModel{" +
                "name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
