package com.example.libreriaapi.viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.libreriaapi.models.Volume;
import com.example.libreriaapi.repositories.BookRepository;

public class BookDetailsViewModel extends AndroidViewModel {
    private BookRepository bookRepository;
    private LiveData<Volume> volumeLiveData;

    public BookDetailsViewModel(@NonNull Application application) {super(application);}

    public void init() {
        bookRepository = new BookRepository();
        volumeLiveData = bookRepository.getVolumeLiveData();
    }

    public void searchVolumeDetails(String id) {
        bookRepository.getVolume(id);
    }

    public LiveData<Volume> getVolumeLiveData() {
        return volumeLiveData;
    }

}