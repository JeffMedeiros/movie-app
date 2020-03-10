package br.com.training.movie_manager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides views to RecyclerView with data from mDataSet array.
 *
 * @author Jefferson Sampaio de Medeiros <jefferson.medeiros@nutes.uepb.edu.br>
 * @copyright Copyright (c) 2020, NUTES/UEPB
 */
public class BaseAdapter extends RecyclerView.Adapter<BaseAdapter.ViewHolder> {
    private Context mContext;
    private List<String> mDataSet;

    public BaseAdapter(Context context) {
        this.mContext = context;
        this.mDataSet = new ArrayList<>();
    }

    public void setmDataSet(List<String> mDataSet) {
        this.mDataSet = mDataSet;
    }


    @Override
    public BaseAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.image_column_item, viewGroup, false);

        return new BaseAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseAdapter.ViewHolder holder, int position) {
        Glide.with(this.mContext)
                .load(mDataSet.get(position))
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * Bind Layout elements
         */
        @BindView(R.id.imgMovie)
        ImageView imageView;

        public ViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }
    }
}
