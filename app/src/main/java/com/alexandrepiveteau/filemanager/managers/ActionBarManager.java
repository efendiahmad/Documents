package com.alexandrepiveteau.filemanager.managers;

import android.content.Context;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import com.alexandrepiveteau.filemanager.R;
import com.melnykov.fab.FloatingActionButton;
import com.nineoldandroids.view.ViewPropertyAnimator;

import java.io.File;

/**
 * Created by Alexandre on 1/30/2015.
 */
public class ActionBarManager {

    private Context mContext;
    private File mFile;
    private FloatingActionButton mContextualFloatingActionButton;
    private FloatingActionButton mFloatingActionButton;
    private TextView mTitle;

    public ActionBarManager(Context context,FloatingActionButton contextualFloatingActionButton, FloatingActionButton floatingActionButton, TextView title) {
        mContext = context;
        mContextualFloatingActionButton = contextualFloatingActionButton;
        mFloatingActionButton = floatingActionButton;
        mTitle = title;

        if(context.getResources().getBoolean(R.bool.show_big_fab)) {
            mFloatingActionButton.setType(FloatingActionButton.TYPE_NORMAL);
        }
    }

    private String getTitle() {
        if(mFile.getPath().equals("/")) {
            return mContext.getString(R.string.root);
        } else if (mFile.getPath().equals(Environment.getExternalStorageDirectory().getPath())) {
            return mContext.getString(R.string.internal_storage);
        } else if (mFile.getPath().equals(Environment.getExternalStorageDirectory().getPath() + "/DCIM")) {
            return mContext.getString(R.string.dcim);
        }
        else {
            return mFile.getName();
        }
    }

    public void setFile(File file) {
        mFile = file;
        updateFloatingActionButton();
        updateTitle();
    }

    public void resetContextualActionButtonImage() {
        mContextualFloatingActionButton.setImageResource(R.drawable.ic_overflow_more);
    }

    public void setContextualActionButtonImage(int resource) {
        mContextualFloatingActionButton.setImageResource(resource);
    }

    public void setContextualFloatingActionButtonVisibility(boolean visibility) {
        if(visibility) {
            mContextualFloatingActionButton.show(true);
        } else {
            mContextualFloatingActionButton.hide(true);
        }
    }

    public void setFloatingActionButtonVisibility(boolean visibility) {
        int marginLeft = 0;
        final ViewGroup.LayoutParams layoutParams = mFloatingActionButton.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            marginLeft = ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin;
        }
        int translationX = visibility ? 0 : mFloatingActionButton.getWidth() + marginLeft;
        ViewPropertyAnimator.animate(mFloatingActionButton).setInterpolator(new AccelerateDecelerateInterpolator())
                .setDuration(200)
                .translationX(-translationX);
    }

    public void updateFloatingActionButton() {
        if(mFile.getPath().equals("/")) {
            mFloatingActionButton.setVisibility(View.INVISIBLE);
        } else {
            mFloatingActionButton.setVisibility(View.VISIBLE);
        }
    }

    public void updateTitle() {
        mTitle.setText(getTitle());
    }
}
