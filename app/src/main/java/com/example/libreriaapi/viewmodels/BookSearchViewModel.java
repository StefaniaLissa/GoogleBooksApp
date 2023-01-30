package com.example.libreriaapi.viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.libreriaapi.models.VolumesResponse;
import com.example.libreriaapi.repositories.BookRepository;
//import io.github.cdimascio.dotenv.Dotenv;

//  This is a ViewModel class that serves as a data holder between the Repository and the UI.
public class BookSearchViewModel extends AndroidViewModel {

//  It holds a reference to the BookRepository instance,
//  which is responsible for fetching data from a remote API,
//  and a LiveData instance of the VolumesResponse model,
//  which represents the response from the API call.
    private BookRepository bookRepository;
    private LiveData<VolumesResponse> volumesResponseLiveData;

    public BookSearchViewModel(@NonNull Application application) {
        super(application);
    }
//  The ViewModel has an init method,
    public void init() {
        // which initializes the BookRepository instance,
        bookRepository = new BookRepository();
        volumesResponseLiveData = bookRepository.getVolumesResponseLiveData();
    }

//  and a searchVolumes method, which is used to search for books with a specific keyword and/or author.
    public void searchVolumes(String keyword, String author) {
//        Dotenv dotenv = Dotenv.configure().directory("/assets").filename("env").load();
//        bookRepository.searchVolumes(keyword, author, dotenv.get("GOOGLE_API_KEY"));
        bookRepository.searchVolumes(keyword, author);
    }

//        The ViewModel also has a getVolumesResponseLiveData method,
//        which returns the LiveData instance of the VolumesResponse model,
//        allowing the UI to observe changes to the data.
    public LiveData<VolumesResponse> getVolumesResponseLiveData() {
        return volumesResponseLiveData;
    }
}