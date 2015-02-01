package com.alexandrepiveteau.filemanager.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexandrepiveteau.filemanager.R;
import com.alexandrepiveteau.filemanager.adapters.listeners.OnDataObjectClickListener;
import com.alexandrepiveteau.filemanager.adapters.listeners.OnDataObjectInfoClickListener;
import com.alexandrepiveteau.filemanager.adapters.listeners.OnDataObjectLongClickListener;
import com.alexandrepiveteau.filemanager.adapters.viewholders.DataObjectViewHolder;
import com.alexandrepiveteau.filemanager.adapters.viewholders.TitleViewHolder;
import com.alexandrepiveteau.filemanager.files.DataObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexandre on 1/29/2015.
 */
public class FileListAdapter extends RecyclerView.Adapter<ViewHolder> {

    public static final int TYPE_FILES = 1;
    public static final int TYPE_TITLE = 2;

    public List<Boolean> mItemsSelected = new ArrayList<Boolean>();

    private Context mContext;
    private OnDataObjectClickListener mDataObjectClickListener;
    private OnDataObjectInfoClickListener mDataObjectInfoClickListener;
    private OnDataObjectLongClickListener mDataObjectLongClickListener;
    private List<DataObject> mFolders;
    private List<DataObject> mFiles;

    public FileListAdapter(Context context, List<DataObject> folders, List<DataObject> files, OnDataObjectClickListener dataObjectClickListener, OnDataObjectInfoClickListener dataObjectInfoClickListener, OnDataObjectLongClickListener dataObjectLongClickListener) {
        mContext = context;
        mFiles = files;
        mFolders = folders;
        mDataObjectClickListener = dataObjectClickListener;
        mDataObjectInfoClickListener = dataObjectInfoClickListener;
        mDataObjectLongClickListener = dataObjectLongClickListener;

        for (int i = 0; i < getItemCount(); i++) {
            mItemsSelected.add(false);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_TITLE:
                ((TitleViewHolder) holder).bindTitle((String) getItem(position), position);
                break;
            case TYPE_FILES:
                ((DataObjectViewHolder) holder).bindDataObject((DataObject) getItem(position), position, getItemClicked(position));
                break;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        ViewHolder viewHolder;
        switch (viewType) {
            case TYPE_TITLE:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_file_type, parent, false);
                viewHolder = new TitleViewHolder(v);
                break;
            case TYPE_FILES:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_file, parent, false);
                viewHolder = new DataObjectViewHolder(v, mDataObjectClickListener, mDataObjectInfoClickListener, mDataObjectLongClickListener);
                break;
            default:
                viewHolder = null;
                break;
        }
        return viewHolder;
    }

    //We need a re-work of this part to be able to display only the headers we want/need

    public Object getItem(int position) {
        //position = position - getMaxHeaders() + getTotalHeaders();
        int positionTitleFolders = 0;
        int totalFolders = mFolders.size();
        int positionTitleFiles = 1 + totalFolders;
        if(position == positionTitleFolders) {
            if(mFolders.size() > 0)
                return mContext.getString(R.string.folders);
            else
                return mContext.getString(R.string.no_folders);
        } else if (position < positionTitleFiles) {
            return mFolders.get(position - 1);
        } else if (position == positionTitleFiles) {
            if(mFiles.size() > 0)
                return mContext.getString(R.string.files);
            else
                return mContext.getString(R.string.no_files);
        } else {
            return mFiles.get(position - mFolders.size() - 2);
        }
    }

    @Override
    public int getItemCount() {
        return mFiles.size() + mFolders.size() + getTotalHeaders();
    }

    @Override
    public int getItemViewType(int position) {
        int positionTitleFolders = 0;
        int totalFolders = mFolders.size();
        int positionTitleFiles = 1 + totalFolders;
        if(position == positionTitleFolders || position == positionTitleFiles) {
            return TYPE_TITLE;
        } else {
            return TYPE_FILES;
        }
    }

    private int getMaxHeaders () {
        return 2;
    }

    private int getTotalHeaders () {
        /*
        int headers = 2;
        if(mFiles.size() == 0) {
            headers--;
        }
        if(mFolders.size() == 0) {
            headers--;
        }
        return headers;
        */
        return 2;
    }

    public void setItemClicked(int position) {
        if (mItemsSelected.get(position)) {
            mItemsSelected.set(position, false);
        }
        else {
            mItemsSelected.set(position, true);
        }
        notifyDataSetChanged();
    }

    public boolean getItemClicked(int position) {
        return mItemsSelected.get(position);
    }
}
