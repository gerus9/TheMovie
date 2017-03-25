package com.gerus.themovie.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import com.gerus.themovie.R;
import com.gerus.themovie.interfaces.OnMiniatureRecyclerInterface;
import com.gerus.themovie.models.Detail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gerus-mac on 14/03/17.
 */

public class MiniatureAdapter extends RecyclerView.Adapter{

    private static final int VIEW_TYPE_EMPTY_LIST_PLACEHOLDER = 0;
    private static final int VIEW_TYPE_HEADER_VIEW = 1;
    private static final int VIEW_TYPE_OBJECT_VIEW = 2;
    private static final int VIEW_TYPE_FOOTER_VIEW = 3;

    private static int TYPE;
    public static final int TYPE_POPULAR = 1;
    public static final int TYPE_RATED = 2;
    public static final int TYPE_UPCOMING = 3;

    private Context mContext;
    private List<? extends Detail> mList = new ArrayList<Detail>();
    private OnMiniatureRecyclerInterface mInterface;

    public MiniatureAdapter(Context poContext, List<? extends Detail> poList, OnMiniatureRecyclerInterface poInterface){
        mContext = poContext;
        mList = poList;
        mInterface = poInterface;
        TYPE = TYPE_POPULAR;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = null;
        switch(viewType) {
            case VIEW_TYPE_EMPTY_LIST_PLACEHOLDER:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_layout, parent, false);
                return new ViewEmpty(mView);
            case VIEW_TYPE_OBJECT_VIEW:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.miniature_item, parent, false);
                return new ViewHolder(mView);
            case VIEW_TYPE_FOOTER_VIEW:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.miniature_footer, parent, false);
                return new ViewFooter(mView);
            case VIEW_TYPE_HEADER_VIEW:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.miniature_header, parent, false);
                return new ViewHeader(mView);
        }
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder poHolder, int piPosition) {
        if(poHolder instanceof ViewHolder) {

            final ViewHolder voHolder = (ViewHolder) poHolder;
            final Detail voDetail = mList.get(piPosition-1);

            voHolder.mTitle.setText(voDetail.getTitle());
            Glide.with(mContext).load(voDetail.getPoster_path()).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(R.drawable.shape_placeholder).error(R.mipmap.ic_launcher).into(voHolder.mImageView);
            voHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mInterface!=null) mInterface.onItemSelected(voDetail);
                }
            });
        } else if (poHolder instanceof ViewHeader){
            final ViewHeader voHolder = (ViewHeader) poHolder;

            switch (TYPE){
                case TYPE_POPULAR: voHolder.mBtnPopular.setChecked(true); break;
                case TYPE_RATED: voHolder.mBtnTop.setChecked(true); break;
                case TYPE_UPCOMING: voHolder.mBtnUpcoming.setChecked(true); break;
            }

            voHolder.mBtnPopular.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mInterface!=null && TYPE!=TYPE_POPULAR){
                        TYPE = TYPE_POPULAR;
                        mInterface.onRefreshValues(TYPE);
                    }
                }
            });

            voHolder.mBtnTop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mInterface!=null && TYPE!=TYPE_RATED){
                        TYPE = TYPE_RATED;
                        mInterface.onRefreshValues(TYPE);
                    }
                }
            });

            voHolder.mBtnUpcoming.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mInterface!=null && TYPE!=TYPE_UPCOMING){
                        TYPE = TYPE_UPCOMING;
                        mInterface.onRefreshValues(TYPE);
                    }
                }
            });
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (mList.isEmpty()) {
            return VIEW_TYPE_EMPTY_LIST_PLACEHOLDER;
        } else if (position == 0){
            return VIEW_TYPE_HEADER_VIEW;
        } else if (mList.size() > position){
            return VIEW_TYPE_OBJECT_VIEW;
        } else {
            return VIEW_TYPE_FOOTER_VIEW;
        }
    }

    public int getNumberColumns(int position) {
        if(mList.isEmpty() || position == 0 ||  position == mList.size()) {
            return mContext.getResources().getInteger(R.integer.numberColumns);
        }else {
            return 1;
        }
    }


    @Override
    public int getItemCount() {
        if(mList.isEmpty()){
            return 1;
        }else {
            return mList.size() + 1;
        }
    }

    public void resetType() {
        TYPE = TYPE_POPULAR;
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


    public class ViewEmpty extends RecyclerView.ViewHolder {
        public final View mView;

        public ViewEmpty(View view) {
            super(view);
            mView = view;
        }
    }

    public class ViewFooter extends RecyclerView.ViewHolder {
        public final View mView;

        public ViewFooter(View view) {
            super(view);
            mView = view;
        }
    }

    public class ViewHeader extends RecyclerView.ViewHolder {
        public final View mView;
        public final RadioButton mBtnPopular, mBtnTop, mBtnUpcoming;

        public ViewHeader(View view) {
            super(view);
            mView = view;
            mBtnPopular = (RadioButton) view.findViewById(R.id.btnPopular);
            mBtnTop = (RadioButton) view.findViewById(R.id.btnTop);
            mBtnUpcoming = (RadioButton) view.findViewById(R.id.btnUpcoming);
        }
    }
}
