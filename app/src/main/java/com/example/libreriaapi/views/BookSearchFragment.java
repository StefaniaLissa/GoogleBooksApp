package com.example.libreriaapi.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libreriaapi.R;
import com.example.libreriaapi.adapters.BookSearchResultsAdapter;
import com.example.libreriaapi.models.Volume;
import com.example.libreriaapi.viewmodels.BookSearchViewModel;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;

import java.io.Serializable;

public class BookSearchFragment extends Fragment{
    private static final int ITEMS_PER_PAGE = 10;
    private BookSearchViewModel viewModel;
    private BookSearchResultsAdapter adapter;
    private TextInputEditText keywordEditText, authorEditText;
    private Button searchButton;
    private int index;
    private String author = "", keyword = "";
    private ProgressBar loadingPB;
    private NestedScrollView nestedSV;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        index = 0;
        adapter = new BookSearchResultsAdapter();
        viewModel = new ViewModelProvider(getActivity()).get(BookSearchViewModel.class);
        viewModel.init();
        viewModel.getVolumesResponseLiveData().observe(this, volumesResponse -> {
            if (volumesResponse != null) {
                adapter.setResults(volumesResponse.getItems());
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booksearch, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.fragment_booksearch_searchResultsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(adapter);
        nestedSV = view.findViewById(R.id.idNestedSV);
        loadingPB = view.findViewById(R.id.idPBLoading);
        loadingPB.setVisibility(View.INVISIBLE);
        nestedSV.setOnScrollChangeListener(scrollChangeListener);
        keywordEditText = view.findViewById(R.id.fragment_booksearch_keyword);
        authorEditText = view.findViewById(R.id.fragment_booksearch_author);
        searchButton = view.findViewById(R.id.fragment_booksearch_search);
        searchButton.setOnClickListener(v -> {
                loadingPB.setVisibility(View.VISIBLE);
                performSearch();
        });

        return view;
    }

    public void performSearch() {
        if(!(author.equals(authorEditText.getEditableText().toString()) &&
                keyword.equals(keywordEditText.getEditableText().toString()))){
            adapter.removeAll();
        }
        author = authorEditText.getEditableText().toString();
        keyword = keywordEditText.getEditableText().toString();
        viewModel.searchVolumes(keyword, author, index);
    }

    private NestedScrollView.OnScrollChangeListener scrollChangeListener = new NestedScrollView.OnScrollChangeListener() {
        @Override
        public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
            if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                loadingPB.setVisibility(View.VISIBLE);
                index = index+ITEMS_PER_PAGE;
                performSearch();
            }
        }
    };

}