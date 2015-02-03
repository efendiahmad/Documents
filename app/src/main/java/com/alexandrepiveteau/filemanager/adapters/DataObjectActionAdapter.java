package com.alexandrepiveteau.filemanager.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexandrepiveteau.filemanager.R;
import com.alexandrepiveteau.filemanager.adapters.viewholders.DataObjectActionHolder;
import com.alexandrepiveteau.filemanager.dialogs.listeners.OnDataObjectActionListener;
import com.alexandrepiveteau.filemanager.files.DataObjectAction;

import java.util.List;

/**
 * Created by Alexandre on 1/30/2015.
 */
public class DataObjectActionAdapter extends RecyclerView.Adapter<DataObjectActionHolder> {

    private List<DataObjectAction> mDataObjectActions;
    private OnDataObjectActionListener mListener;

    public DataObjectActionAdapter (List<DataObjectAction> actions, OnDataObjectActionListener listener) {
        mDataObjectActions = actions;
        mListener = listener;
    }

    @Override
    public DataObjectActionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_data_object_action, parent, false);
        DataObjectActionHolder holder = new DataObjectActionHolder(v, mListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(DataObjectActionHolder holder, int position) {
        holder.bindDataObject(getItem(position));
    }

    public DataObjectAction getItem(int position) {
        return mDataObjectActions.get(position);
    }

    @Override
    public int getItemCount() {
        return mDataObjectActions.size();
    }
}
