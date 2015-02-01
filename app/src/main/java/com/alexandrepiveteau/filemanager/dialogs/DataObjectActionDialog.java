package com.alexandrepiveteau.filemanager.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.alexandrepiveteau.filemanager.R;
import com.alexandrepiveteau.filemanager.adapters.DataObjectActionAdapter;
import com.alexandrepiveteau.filemanager.dialogs.listeners.OnDataObjectActionListener;
import com.alexandrepiveteau.filemanager.files.DataObject;
import com.alexandrepiveteau.filemanager.files.DataObjectAction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexandre on 1/30/2015.
 */
public class DataObjectActionDialog extends Dialog implements OnDataObjectActionListener{

    private DataObjectActionAdapter mDataObjectActionAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private OnDataObjectActionListener mListener;
    private RecyclerView mRecyclerView;

    public DataObjectActionDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rootView = layoutInflater.inflate(R.layout.dialog_data_object_actions, null);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.actions_list);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        DataObjectAction action = new DataObjectAction();
        action.setAction(DataObject.ACTION.COPY);
        action.setActionText(getContext().getString(R.string.copy));
        action.setActionIcon(R.drawable.ic_action_copy);

        DataObjectAction action2 = new DataObjectAction();
        action2.setAction(DataObject.ACTION.CUT);
        action2.setActionText(getContext().getString(R.string.cut));
        action2.setActionIcon(R.drawable.ic_action_cut);

        DataObjectAction action3 = new DataObjectAction();
        action3.setAction(DataObject.ACTION.RENAME);
        action3.setActionText(getContext().getString(R.string.rename));
        action3.setActionIcon(R.drawable.ic_action_rename);

        DataObjectAction action4 = new DataObjectAction();
        action4.setAction(DataObject.ACTION.DELETE);
        action4.setActionText(getContext().getString(R.string.delete));
        action4.setActionIcon(R.drawable.ic_action_delete);

        List<DataObjectAction> list = new ArrayList<DataObjectAction>();

        list.add(action);
        list.add(action2);
        list.add(action3);
        list.add(action4);

        mDataObjectActionAdapter = new DataObjectActionAdapter(list, this);

        mRecyclerView.setAdapter(mDataObjectActionAdapter);

        setTitle(getContext().getString(R.string.select_action));
        setContentView(rootView);
    }

    public void setDataObjectActionListener(OnDataObjectActionListener listener) {
        mListener = listener;
    }

    @Override
    public void onDataObjectActionClick(DataObjectAction action) {
        dismiss();
        mListener.onDataObjectActionClick(action);
    }
}
