package com.alexandrepiveteau.filemanager.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alexandrepiveteau.filemanager.R;

/**
 * Created by Alexandre on 1/29/2015.
 */
public class TitleViewHolder extends RecyclerView.ViewHolder {

    private int mPosition;
    private String mTitleText;

    private View mDivider;
    private TextView mTitle;

    public TitleViewHolder(View view) {
        super(view);
        mDivider = (View) view.findViewById(R.id.file_type_divider);
        mTitle = (TextView) view.findViewById(R.id.file_type_title);
    }

    public void bindTitle(String titleText, int position) {
        mTitleText = titleText;
        mPosition = position;

        boolean showDivider = mPosition != 0 && mTitle.getResources().getInteger(R.integer.file_list_num_columns) == 1;

        if(showDivider) {
            mDivider.setVisibility(View.VISIBLE);
        } else {
            mDivider.setVisibility(View.INVISIBLE);
        }
        mTitle.setText(mTitleText);
    }
}