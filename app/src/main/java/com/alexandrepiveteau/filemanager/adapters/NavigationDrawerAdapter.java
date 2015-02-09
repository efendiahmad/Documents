package com.alexandrepiveteau.filemanager.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alexandrepiveteau.filemanager.R;
import com.alexandrepiveteau.filemanager.ui.listeners.OnNavigationItemClickListener;

/**
 * Created by Alexandre on 2/9/2015.
 */
public class NavigationDrawerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_IMAGE_HEADER = 0;
    private static final int TYPE_NAVIGATION_ITEM = 1;

    public static final int[] NAVIGATION_ITEMS_IMAGES = {
            R.drawable.ic_navigation_internal_storage,
            R.drawable.ic_navigation_dcim,
            R.drawable.ic_navigation_music,
            R.drawable.ic_navigation_download,
            R.drawable.ic_navigation_rate};

    private static final int[] NAVIGATION_ITEMS_TITLES = {
            R.string.internal_storage,
            R.string.dcim,
            R.string.music,
            R.string.downloads,
            R.string.rate_on_play_store};

    private OnNavigationItemClickListener mListener;

    public NavigationDrawerAdapter(OnNavigationItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        RecyclerView.ViewHolder viewHolder;
        switch (viewType) {
            case TYPE_IMAGE_HEADER:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_navigation_header, parent, false);
                viewHolder = new NavigationHeaderViewHolder(v);
                return viewHolder;
            case TYPE_NAVIGATION_ITEM:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_navigation_item, parent, false);
                viewHolder = new NavigationItemViewHolder(v, mListener);
                return viewHolder;
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_NAVIGATION_ITEM:
                ((NavigationItemViewHolder) holder).bindNavigationItem(getNavigationTitleId(position), getNavigationImageId(position), position);
                break;
        }
    }

    public int getNavigationImageId(int position) {
        return NAVIGATION_ITEMS_IMAGES [position-1];
    }

    public int getNavigationTitleId(int position) {
        return NAVIGATION_ITEMS_TITLES [position-1];
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_IMAGE_HEADER;
            default:
                return TYPE_NAVIGATION_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return Math.min(NAVIGATION_ITEMS_IMAGES.length, NAVIGATION_ITEMS_TITLES.length) + 1;
    }

    private static class NavigationHeaderViewHolder extends RecyclerView.ViewHolder {

        public NavigationHeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    private static class NavigationItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private OnNavigationItemClickListener mListener;
        private int mPosition;

        private View mDivider;
        private RelativeLayout mItem;
        private ImageView mImage;
        private TextView mTitle;

        public NavigationItemViewHolder(View itemView, OnNavigationItemClickListener listener) {
            super(itemView);

            mListener = listener;

            mDivider = itemView.findViewById(R.id.navigation_item_divider);
            mItem = (RelativeLayout) itemView.findViewById(R.id.navigation_item);
            mImage = (ImageView) itemView.findViewById(R.id.navigation_item_image);
            mTitle = (TextView) itemView.findViewById(R.id.navigation_item_title);
        }

        public void bindNavigationItem(int resTitle, int resImage, int position) {
            mItem.setOnClickListener(this);
            mImage.setImageResource(resImage);
            mTitle.setText(resTitle);
            mPosition = position;

            //If the item is the first one or the fifth
            if(!(position == 1) && !(position == 5)) {
                View v = mItem.findViewById(R.id.navigation_item_padding);
                v.setVisibility(View.GONE);
            } else {
                View v = mItem.findViewById(R.id.navigation_item_padding);
                v.setVisibility(View.VISIBLE);
            }

            if(!(position == 5)) {
                mDivider.setVisibility(View.INVISIBLE);
            } else {
                mDivider.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.navigation_item:
                    mListener.onNavigationItemClicked(mPosition);
                    break;
            }
        }
    }
}
