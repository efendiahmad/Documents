package com.alexandrepiveteau.filemanager.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alexandrepiveteau.filemanager.R;
import com.alexandrepiveteau.filemanager.adapters.listeners.OnDataObjectClickListener;
import com.alexandrepiveteau.filemanager.adapters.listeners.OnDataObjectInfoClickListener;
import com.alexandrepiveteau.filemanager.adapters.listeners.OnDataObjectLongClickListener;
import com.alexandrepiveteau.filemanager.files.DataObject;

/**
 * Created by Alexandre on 1/29/2015.
 */
public class DataObjectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    private DataObject mDataObject;

    private OnDataObjectClickListener mDataObjectListener;
    private OnDataObjectInfoClickListener mDataObjectInfoClickListener;
    private OnDataObjectLongClickListener mDataObjectLongClickListener;

    private int mPosition;
    private boolean mIsSelected;

    private ImageView mBackgroundImage;
    private TextView mDescriptionFile;
    private RelativeLayout mFileItem;
    private ImageView mIcon;
    private ImageButton mInfoButton;
    private TextView mTitleFile;

    public DataObjectViewHolder(View view, OnDataObjectClickListener dataObjectClickListener, OnDataObjectInfoClickListener dataObjectInfoClickListener, OnDataObjectLongClickListener dataObjectLongClickListener) {
        super(view);
        mBackgroundImage = (ImageView) view.findViewById(R.id.file_icon_background);
        mDescriptionFile = (TextView) view.findViewById(R.id.file_description);
        mFileItem = (RelativeLayout) view.findViewById(R.id.file_item);
        mIcon = (ImageView) view.findViewById(R.id.file_icon);
        mInfoButton = (ImageButton) view.findViewById(R.id.file_info_button);
        mTitleFile = (TextView) view.findViewById(R.id.file_title);

        mDataObjectListener = dataObjectClickListener;
        mDataObjectInfoClickListener = dataObjectInfoClickListener;
        mDataObjectLongClickListener = dataObjectLongClickListener;

        mFileItem.setOnClickListener(this);
        mFileItem.setOnLongClickListener(this);
        mIcon.setOnClickListener(this);
        mInfoButton.setOnClickListener(this);
    }

    public void bindDataObject(DataObject dataObject, int position, boolean isSelected) {
        mDataObject = dataObject;
        mPosition = position;
        mIsSelected = isSelected;

        if(isSelected) {
            mFileItem.setBackgroundResource(R.color.material_grey_500);
        } else {
            mFileItem.setBackgroundResource(R.drawable.ripple_default_background);
        }

        mBackgroundImage.setImageResource(mDataObject.getBackgroundIcon());
        mDescriptionFile.setText(mDataObject.getDescription());
        mIcon.setImageResource(mDataObject.getIcon());
        mTitleFile.setText(mDataObject.getName());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.file_item:
                mDataObjectListener.onDataItemClick(mDataObject);
                break;
            case R.id.file_info_button:
                mDataObjectInfoClickListener.onDataItemInfoClick(mDataObject);
                break;
            case R.id.file_icon:
                mDataObjectLongClickListener.onDataItemLongClick(mDataObject, mPosition);
                break;
        }
    }

    @Override
    public boolean onLongClick(View view) {
        switch (view.getId()) {
            case R.id.file_item:
                mDataObjectLongClickListener.onDataItemLongClick(mDataObject, mPosition);
                return true;
            default:
                return false;
        }
    }
}