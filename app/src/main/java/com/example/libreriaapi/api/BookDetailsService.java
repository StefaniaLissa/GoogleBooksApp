package com.example.libreriaapi.api;

import com.example.libreriaapi.models.Volume;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BookDetailsService {
    @GET("/books/v1/volumes/{id}")
    Call<Volume> detailsBook(
    @Path("id") String id);
}