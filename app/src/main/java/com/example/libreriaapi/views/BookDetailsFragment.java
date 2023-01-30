package com.example.libreriaapi.views;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.libreriaapi.R;
import com.example.libreriaapi.models.Volume;
import com.example.libreriaapi.models.VolumeInfo;
import com.example.libreriaapi.viewmodels.BookDetailsViewModel;

public class BookDetailsFragment extends Fragment {
    private BookDetailsViewModel viewModel;
    private TextView title;
    private TextView subtitle;
    private TextView description;
    private TextView authors;
    private TextView publisher;
    private TextView published_date;
    private TextView page_count;
    private TextView categories;
    private ImageView thumbnail;
    private VolumeInfo volumeInfo;
    private String id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            id = ((Volume) bundle.getSerializable("volume")).getId();

            viewModel = new ViewModelProvider(getActivity()).get(BookDetailsViewModel.class);
            viewModel.init();
            viewModel.searchVolumeDetails(id);
            viewModel.getVolumeLiveData().observe(this, volume -> {
                if (volume != null) {
                    volumeInfo = volume.getVolumeInfo();

                    try {
                        title.setText(volumeInfo.getTitle());
                        subtitle.setText(volumeInfo.getSubtitle());
                        description.setText(volumeInfo.getDescription());
                        String authorsList = "";
                        for (String a : volumeInfo.getAuthors()) {
                            authorsList += a;
                        }
                        authors.setText(authorsList);
                        publisher.setText(volumeInfo.getPublisher());
                        published_date.setText(volumeInfo.getPublishedDate());
                        page_count.setText("Pages: " + volumeInfo.getPageCount());
                        String categoriesList = "";
                        for (String c : volumeInfo.getCategories()) {
                            categoriesList += c;
                        }
                        categories.setText(categoriesList);
                        String imageURL = volumeInfo.getImageLinks().getSmallThumbnail().replace("http://", "https://");
                        Glide.with(getContext()).load(imageURL).into(thumbnail);

                    } catch (Resources.NotFoundException e) {
                        Log.i("Error", "String resource(s) not found");
                    } catch (NullPointerException n){
                        Log.i("Error", "Authors or categories List is empty");
                    }

                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookdetails, container, false);

            title = view.findViewById(R.id.title);
            subtitle = view.findViewById(R.id.subtitle);
            description = view.findViewById(R.id.description);
            authors = view.findViewById(R.id.authors);
            publisher = view.findViewById(R.id.publisher);
            published_date = view.findViewById(R.id.published_date);
            page_count = view.findViewById(R.id.page_count);
            categories = view.findViewById(R.id.categories);
            thumbnail = view.findViewById(R.id.thumbnail);

        return view;
    }
}