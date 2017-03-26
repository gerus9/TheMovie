package com.gerus.themovie.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gerus.themovie.R;
import com.gerus.themovie.interfaces.OnDetailRecyclerInterface;
import com.gerus.themovie.models.Detail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gerus-mac on 14/03/17.
 */

public class DetailAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_OBJECT_VIEW = 0;

    private Context mContext;
    private List<? extends Detail> mList = new ArrayList<Detail>();
    private OnDetailRecyclerInterface mInterface;

    public DetailAdapter(Context poContext, List<? extends Detail> poList, OnDetailRecyclerInterface poInterface) {
        mContext = poContext;
        mList = poList;
        mInterface = poInterface;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = null;
        switch (viewType) {
            case VIEW_TYPE_OBJECT_VIEW:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.miniature_item, parent, false);
                return new ViewHolder(mView);
        }
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder poHolder, int piPosition) {
        if (poHolder instanceof ViewHolder) {

            final ViewHolder voHolder = (ViewHolder) poHolder;
            final Detail voDetail = mList.get(piPosition);

            voHolder.mTitle.setText(voDetail.getIdentifier());
            Glide.with(mContext).load(voDetail.getPoster_path()).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(R.drawable.shape_placeholder).error(R.mipmap.ic_launcher).into(voHolder.mImageView);
            voHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mInterface != null) mInterface.onItemSelected(voDetail);
                }
            });
        }

    }

    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_OBJECT_VIEW;
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final TextView mTitle;
        public final ImageView mImageView;

        public ViewHolder(View poView) {
            super(poView);
            mView = poView;
            mTitle = (TextView) poView.findViewById(R.id.txt_card_news);
            mImageView = (ImageView) poView.findViewById(R.id.image_card_news);

        }
    }


}
