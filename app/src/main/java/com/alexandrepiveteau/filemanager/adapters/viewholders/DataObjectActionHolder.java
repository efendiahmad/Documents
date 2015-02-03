package com.alexandrepiveteau.filemanager.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alexandrepiveteau.filemanager.R;
import com.alexandrepiveteau.filemanager.dialogs.listeners.OnDataObjectActionListener;
import com.alexandrepiveteau.filemanager.files.DataObjectAction;

/**
 * Created by Alexandre on 2/3/2015.
 */
public class DataObjectActionHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

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
