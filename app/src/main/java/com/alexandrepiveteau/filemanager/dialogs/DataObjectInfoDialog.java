package com.alexandrepiveteau.filemanager.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alexandrepiveteau.filemanager.R;
import com.alexandrepiveteau.filemanager.files.DataObject;

/**
 * Created by Alexandre on 1/29/2015.
 */
public class DataObjectInfoDialog extends Dialog implements View.OnClickListener {

    private Button mButton;
    private DataObject mDataObject;
    private LayoutInflater mLayoutInflater;
    private TextView mTextViewName;
    private TextView mTextViewPath;
    private TextView mTextViewModificationDate;
    private View mView;

    public DataObjectInfoDialog(Context context, DataObject dataObject) {
        super(context);
        mDataObject = dataObject;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.data_object_dialog_button) {
            dismiss();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mView = mLayoutInflater.inflate(R.layout.dialog_data_object_properties, null, false);
        mButton = (Button) mView.findViewById(R.id.data_object_dialog_button);
        mTextViewName = (TextView) mView.findViewById(R.id.data_object_title);
        mTextViewPath = (TextView) mView.findViewById(R.id.data_object_path);
        mTextViewModificationDate = (TextView) mView.findViewById(R.id.data_object_modification_date);

        mButton.setOnClickListener(this);

        setCancelable(true);
        setContentView(mView);
        setTitle(R.string.file_info);
        mTextViewName.setText(Html.fromHtml("<b>" + getContext().getString(R.string.data_object_name)+ " </b>" + mDataObject.getName()));
        mTextViewPath.setText(Html.fromHtml("<b>" + getContext().getString(R.string.data_object_path)+ " </b>" + mDataObject.getFile().getPath()));
        mTextViewModificationDate.setText(Html.fromHtml("<b>" + getContext().getString(R.string.data_object_modification_date)+ " </b>" + mDataObject.getDescription()));
    }
}
