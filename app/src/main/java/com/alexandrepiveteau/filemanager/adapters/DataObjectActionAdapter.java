package com.alexandrepiveteau.filemanager.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alexandrepiveteau.filemanager.R;
import com.alexandrepiveteau.filemanager.dialogs.listeners.OnDataObjectActionListener;
import com.alexandrepiveteau.filemanager.files.DataObjectAction;

import java.util.List;

/**
 * Created by Alexandre on 1/30/2015.
 */
public class DataObjectActionAdapter extends RecyclerView.Adapter<DataObjectActionAdapter.DataObjectActionHolder> {

    public static class DataObjectActionHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private OnDataObjectActionListener mListener;

        private ImageView mActionBackground;
        private TextView mActionTitle;
        private ImageView mActionIcon;
        private RelativeLayout mActionItem;
        private DataObjectAction mDataObjectAction;

        public DataObjectActionHolder(View itemView, OnDataObjectActionListener listener) {
            super(itemView);

            mListener = listener;

            mActionBackground = (ImageView) itemView.findViewById(R.id.data_object_action_background);
            mActionTitle = (TextView) itemView.findViewById(R.id.data_object_action_title);
            mActionIcon = (ImageView) itemView.findViewById(R.id.data_object_action_icon);
            mActionItem = (RelativeLayout) itemView.findViewById(R.id.data_object_action_item);

            mActionItem.setOnClickListener(this);
        }

        public void bindDataObject(DataObjectAction dataObjectAction) {
            mDataObjectAction = dataObjectAction;

            mActionTitle.setText(mDataObjectAction.getActionText());
            mActionIcon.setImageResource(mDataObjectAction.getActionIcon());

            switch (mDataObjectAction.getAction()) {
                case DELETE:
                    mActionBackground.setImageResource(R.drawable.file_icon_circle_red);
                    break;
            }
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.data_object_action_item:
                    mListener.onDataObjectActionClick(mDataObjectAction);
                    break;
            }
        }
    }

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
