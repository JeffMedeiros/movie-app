package br.com.training.movie_manager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Provide views to RecyclerView with data from mDataSet array.
 *
 * @author Jefferson Sampaio de Medeiros <jefferson.medeiros@nutes.uepb.edu.br>
 * @copyright Copyright (c) 2020, NUTES/UEPB
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<String> mDataSet;

    public RecyclerAdapter() {
        mDataSet = new ArrayList<>();
    }

    public void setmDataSet(List<String> mDataSet) {
        this.mDataSet = mDataSet;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgView;

        public ViewHolder(View view) {
            super(view);

            imgView = (ImageView) view.findViewById(R.id.imgView);
        }
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.image_column_item, viewGroup, false);

        return new RecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        Glide.with(holder.imgView.getContext())
                .load(mDataSet.get(position))
                .into(holder.imgView);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
