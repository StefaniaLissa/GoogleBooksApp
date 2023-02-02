package com.example.libreriaapi.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.libreriaapi.models.Volume;
import com.example.libreriaapi.R;

import java.util.ArrayList;
import java.util.List;

public class BookSearchResultsAdapter extends RecyclerView.Adapter<BookSearchResultsAdapter.BookSearchResultHolder> {
    private Context context;
    private List<Volume> volumes = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public BookSearchResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_item, parent, false);
        return new BookSearchResultHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookSearchResultHolder holder, int position) {
        Volume volume = volumes.get(position);
        holder.titleTextView.setText(volume.getVolumeInfo().getTitle());
        holder.publishedDateTextView.setText(volume.getVolumeInfo().getPublishedDate());
        if (volume.getVolumeInfo().getImageLinks() != null) {
            String imageUrl = volume.getVolumeInfo().getImageLinks().getSmallThumbnail()
                    .replace("http://", "https://");

            Glide.with(holder.itemView)
                    .load(imageUrl)
                    .into(holder.smallThumbnailImageView);
        }
        if (volume.getVolumeInfo().getAuthors() != null) {
            String authors = "";
            for(String a: volume.getVolumeInfo().getAuthors()){
                authors += a+", ";
            }
            holder.authorsTextView.setText(authors);
        }
    }

    @Override
    public int getItemCount() {
        return volumes.size();
    }

    public void setResults(List<Volume> results) {
        this.volumes.addAll(results);
        notifyDataSetChanged();
    }

    public void removeAll(){
        this.volumes.removeAll(volumes);
    }

    public class BookSearchResultHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView titleTextView;
        private TextView authorsTextView;
        private TextView publishedDateTextView;
        private ImageView smallThumbnailImageView;

        public BookSearchResultHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.book_item_title);
            authorsTextView = itemView.findViewById(R.id.book_item_authors);
            publishedDateTextView = itemView.findViewById(R.id.book_item_publishedDate);
            smallThumbnailImageView = itemView.findViewById(R.id.book_item_smallThumbnail);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Volume volume = volumes.get(position);
            Bundle bundle = new Bundle();
            bundle.putSerializable("volume", volume);
            Navigation.findNavController(v).navigate(R.id.action_bookSearchFragment_to_bookDetailsFragment, bundle);

        }

    }
        public interface OnItemClickListener {
        void onItemClick(Volume volume);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}