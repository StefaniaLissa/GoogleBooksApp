package com.example.libreriaapi.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.libreriaapi.api.BookDetailsService;
import com.example.libreriaapi.api.BookSearchService;
import com.example.libreriaapi.models.Volume;
import com.example.libreriaapi.models.VolumesResponse;

//import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class BookRepository {
    private static final String BOOK_SEARCH_SERVICE_BASE_URL = "https://www.googleapis.com/";

    private BookSearchService bookSearchService;
    private MutableLiveData<VolumesResponse> volumesResponseLiveData;

    private BookDetailsService bookDetailsService;
    private MutableLiveData<Volume> volumeLiveData;

    public BookRepository() {
        volumesResponseLiveData = new MutableLiveData<>();
        bookSearchService = new retrofit2.Retrofit.Builder()
                .baseUrl(BOOK_SEARCH_SERVICE_BASE_URL)
//                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(BookSearchService.class);

        volumeLiveData = new MutableLiveData<>();
        bookDetailsService = new retrofit2.Retrofit.Builder()
                .baseUrl(BOOK_SEARCH_SERVICE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(BookDetailsService.class);
    }


    public void searchVolumes(String keyword, String author) {
        bookSearchService.searchVolumes(keyword, author)
                .enqueue(new Callback<VolumesResponse>() {
                    @Override
                    public void onResponse(Call<VolumesResponse> call, Response<VolumesResponse> response) {
                        if (response.body() != null) {
                            volumesResponseLiveData.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<VolumesResponse> call, Throwable t) {
                        volumesResponseLiveData.postValue(null);
                    }
                });
    }

    public LiveData<VolumesResponse> getVolumesResponseLiveData() {
        return volumesResponseLiveData;
    }

    public void getVolume(String bookId) {
        bookDetailsService.detailsBook(bookId)
                .enqueue(new Callback<Volume>() {
                    @Override
                    public void onResponse(Call<Volume> call, Response<Volume> response) {
                        if (response.body() != null) {
                            volumeLiveData.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<Volume> call, Throwable t) {
                        volumeLiveData.postValue(null);
                    }
                });
    }

    public LiveData<Volume> getVolumeLiveData() {
        return volumeLiveData;
    }

}