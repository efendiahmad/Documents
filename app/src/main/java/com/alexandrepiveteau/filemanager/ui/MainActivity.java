package com.alexandrepiveteau.filemanager.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.alexandrepiveteau.filemanager.R;
import com.alexandrepiveteau.filemanager.adapters.FileListAdapter;
import com.alexandrepiveteau.filemanager.adapters.NavigationDrawerAdapter;
import com.alexandrepiveteau.filemanager.adapters.listeners.OnDataObjectClickListener;
import com.alexandrepiveteau.filemanager.adapters.listeners.OnDataObjectInfoClickListener;
import com.alexandrepiveteau.filemanager.adapters.listeners.OnDataObjectLongClickListener;
import com.alexandrepiveteau.filemanager.dialogs.DataObjectActionDialog;
import com.alexandrepiveteau.filemanager.dialogs.DataObjectInfoDialog;
import com.alexandrepiveteau.filemanager.dialogs.listeners.OnDataObjectActionListener;
import com.alexandrepiveteau.filemanager.files.DataObject;
import com.alexandrepiveteau.filemanager.files.DataObjectAction;
import com.alexandrepiveteau.filemanager.managers.ActionBarManager;
import com.alexandrepiveteau.filemanager.ui.listeners.OnDataActionPerformedListener;
import com.alexandrepiveteau.filemanager.ui.listeners.OnNavigationItemClickListener;
import com.alexandrepiveteau.filemanager.utils.DataObjectActionsUtils;
import com.alexandrepiveteau.filemanager.utils.DataObjectOpenerUtils;
import com.alexandrepiveteau.filemanager.utils.FilesUtils;
import com.alexandrepiveteau.filemanager.utils.ZipUtils;
import com.melnykov.fab.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.psdev.licensesdialog.LicensesDialog;
import de.psdev.licensesdialog.licenses.ApacheSoftwareLicense20;
import de.psdev.licensesdialog.licenses.BSD3ClauseLicense;
import de.psdev.licensesdialog.licenses.License;
import de.psdev.licensesdialog.licenses.MITLicense;
import de.psdev.licensesdialog.model.Notice;
import de.psdev.licensesdialog.model.Notices;


public class MainActivity extends Activity implements
        View.OnClickListener,
        OnDataObjectActionListener,
        OnDataObjectClickListener,
        OnDataObjectInfoClickListener,
        OnDataObjectLongClickListener,
        OnDataActionPerformedListener,
        OnNavigationItemClickListener,
        SearchView.OnQueryTextListener{

    private static final String KEY_DIRECTORY = "KEY_DIRECTORY";

    private DataObject.ACTION mActionPending = DataObject.ACTION.NONE;
    private boolean isActionPending;
    private List<DataObject> mItemsSelected;

    private File dir;
    private List<DataObject> mFiles;
    private List<DataObject> mFolders;
    private String mQuery;

    private FloatingActionButton mContextualFloatingActionButton;

    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private ActionBarManager mActionBarManager;
    private Calendar mBackPressedCalendar;
    private DrawerLayout mDrawerLayout;
    private FileListAdapter mFilesAdapter;
    private FloatingActionButton mFloatingActionButton;
    private RecyclerView mListView;
    private RecyclerView mNavigationListView;
    private SearchView mSearchView;
    private TextView mTitle;
    private Toolbar mToolbar;

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.fab:
                File oldDir = dir;
                try {
                    dir = dir.getParentFile();
                    setDocumentsList(dir, mQuery);
                } catch (Exception e) {
                    dir = oldDir;
                }
                break;
            case R.id.contextual_fab:
                if(isActionPending) {
                    switch (mActionPending) {
                        case CUT:
                            List<File> mCutFiles = new ArrayList<File>();
                            for(DataObject object : mItemsSelected) {
                                mCutFiles.add(new File(dir, object.getName()));
                            }
                            DataObjectActionsUtils.cutDataObjects(this, mItemsSelected, mCutFiles, this);
                            mActionPending = DataObject.ACTION.NONE;
                            isActionPending = false;
                            mActionBarManager.setFloatingActionButtonVisibility(true);
                            mActionBarManager.setContextualFloatingActionButtonVisibility(false);
                            mActionBarManager.resetContextualActionButtonImage();
                            break;
                        case COPY:
                            List<File> mCopyFiles = new ArrayList<File>();
                            for(DataObject object : mItemsSelected) {
                                mCopyFiles.add(new File(dir, object.getName()));
                            }
                            DataObjectActionsUtils.copyDataObjects(this, mItemsSelected, mCopyFiles, this);
                            mActionPending = DataObject.ACTION.NONE;
                            isActionPending = false;
                            mActionBarManager.setFloatingActionButtonVisibility(true);
                            mActionBarManager.setContextualFloatingActionButtonVisibility(false);
                            mActionBarManager.resetContextualActionButtonImage();
                            break;
                    }
                }
                else {
                    DataObjectActionDialog dataObjectActionDialog = new DataObjectActionDialog(this);
                    dataObjectActionDialog.setDataObjectActionListener(this);
                    dataObjectActionDialog.show();
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dir = Environment.getExternalStorageDirectory();
        mQuery = "";

        mBackPressedCalendar = Calendar.getInstance();

        mContextualFloatingActionButton = (FloatingActionButton) findViewById(R.id.contextual_fab);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        mListView = (RecyclerView) findViewById(R.id.files_list);
        mNavigationListView = (RecyclerView) findViewById(R.id.navigation_list);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mListView.setLayoutManager(new LinearLayoutManager(this));
        mListView.setHasFixedSize(true);

        mNavigationListView.setLayoutManager(new LinearLayoutManager(this));
        mNavigationListView.setHasFixedSize(true);

        mNavigationListView.setAdapter(new NavigationDrawerAdapter(this));

        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close);
        mActionBarManager = new ActionBarManager(this,mContextualFloatingActionButton , mFloatingActionButton, mTitle);
        mDrawerLayout.setStatusBarBackground(R.color.material_light_blue_700);

        isActionPending = false;
        mItemsSelected = new ArrayList<DataObject>();
        mContextualFloatingActionButton.hide(false);
        mContextualFloatingActionButton.setOnClickListener(this);
        mFloatingActionButton.setOnClickListener(this);
        mActionBarManager.setFile(dir);
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
        setActionBar(mToolbar);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        setDocumentsList(dir, mQuery);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        Calendar calendar = Calendar.getInstance();
        if((calendar.getTimeInMillis() - mBackPressedCalendar.getTimeInMillis()) < 2000) {
            finish();
        }
        else {
            Toast.makeText(this, R.string.exit_application, Toast.LENGTH_SHORT).show();
            mBackPressedCalendar = calendar;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mQuery = "";

        mSearchView = (SearchView) menu.findItem(R.id.search).getActionView();
        mSearchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public void onDataActionCancelled() {
        mItemsSelected.clear();
        Toast.makeText(this, R.string.action_cancelled, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDataActionPerformed() {
        mItemsSelected.clear();
        setDocumentsList(dir, mQuery);
    }

    @Override
    public void onDataObjectActionClick(DataObjectAction action) {
        boolean showContFab = false;
        boolean showFab = false;
        switch (action.getAction()) {
            case CUT:
                isActionPending = true;
                mActionPending = DataObject.ACTION.CUT;
                mActionBarManager.setContextualActionButtonImage(R.drawable.ic_action_paste);
                showFab = true;
                showContFab = true;
                break;
            case COPY:
                isActionPending = true;
                mActionPending = DataObject.ACTION.COPY;
                mActionBarManager.setContextualActionButtonImage(R.drawable.ic_action_paste);
                showFab = true;
                showContFab = true;
                break;
            case DELETE:
                showFab = true;
                showContFab = false;
                DataObjectActionsUtils.deleteDataObjects(mItemsSelected, this);
                break;
            case RENAME:
                showFab = true;
                showContFab = false;
                DataObjectActionsUtils.renameDataObject(this, mItemsSelected, this);
                break;
        }
        mActionBarManager.setContextualFloatingActionButtonVisibility(showContFab);
        mActionBarManager.setFloatingActionButtonVisibility(showFab);
        //setDocumentsList(dir);
    }

    @Override
    public void onDataItemClick(DataObject dataObject) {
        if(ZipUtils.isZipDataObject(dataObject)) {
            ZipUtils.unzipDataObject(dataObject, this);
            setDocumentsList(dir, mQuery);
            return;
        }
        else {
            File oldDir = dir;
            try {
                dir = dataObject.getFile();
                setDocumentsList(dir, mQuery);
            } catch (Exception e) {
                dir = DataObjectOpenerUtils.openFile(oldDir, dir, this);
            }
        }
    }

    @Override
    public void onDataItemInfoClick(DataObject dataObject) {
        DataObjectInfoDialog dialog = new DataObjectInfoDialog(this, dataObject);
        dialog.show();
    }

    @Override
    public void onDataItemLongClick(DataObject dataObject, int position) {
        mFilesAdapter.setItemClicked(position);

        boolean addObject = true;

        for (DataObject obj : mItemsSelected) {
            if (obj.getFile().getPath().equals(dataObject.getFile().getPath())) {
                mItemsSelected.remove(obj);
                addObject = false;
                break;
            } else {
                addObject = true;
            }
        }

        if(addObject) {
            mItemsSelected.add(dataObject);
        }

        for (boolean b : mFilesAdapter.mItemsSelected) {
            if (b) {
                mActionBarManager.setContextualFloatingActionButtonVisibility(true);
                mActionBarManager.setFloatingActionButtonVisibility(false);
                return;
            } else {
                mActionBarManager.setContextualFloatingActionButtonVisibility(false);
                mActionBarManager.setFloatingActionButtonVisibility(true);
            }
        }
    }

    @Override
    public void onNavigationItemClicked(int position) {
        File oldDir = dir;
        switch (position) {
            case 1:
                dir = FilesUtils.INTERNAL_STORAGE;
                break;
            case 2:
                dir = FilesUtils.DCIM;
                break;
            case 3:
                dir = FilesUtils.MUSIC;
                break;
            case 4:
                dir = FilesUtils.DOWNLOADS;
                break;
            case 5:
                Intent i = new Intent(android.content.Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.alexandrepiveteau.filemanager"));
                startActivity(i);
                return;
            case 6:
                final Notices notices = new Notices();
                notices.addNotice(new Notice("Documents", "", "Alexandre Piveteau", new BSD3ClauseLicense()));
                notices.addNotice(new Notice("Zip4j", "http://www.lingala.net/zip4j/", "Srikanth Reddy Lingala", new ApacheSoftwareLicense20()));
                notices.addNotice(new Notice("FloatingActionButton", "", "Oleksandr Melnykov", new MITLicense()));

                new LicensesDialog.Builder(this).setNotices(notices).setIncludeOwnLicense(true).build().show();
                return;
        }
        try {
            setDocumentsList(dir, mQuery);
        }
        catch (Exception e) {
            dir = oldDir;
            Toast.makeText(this, R.string.access_denied, Toast.LENGTH_SHORT).show();
        }
        mDrawerLayout.closeDrawer(Gravity.LEFT);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mActionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch(item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mActionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mQuery = newText;
        setDocumentsList(dir, mQuery);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        onQueryTextChange(query);
        return true;
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        dir = new File(savedInstanceState.getString(KEY_DIRECTORY, Environment.getExternalStorageDirectory().getPath()));
        setDocumentsList(dir, mQuery);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_DIRECTORY, dir.getPath());
    }

    private void setDocumentsList(File file, String sequenceSearched) {
        mFolders = DataObject.getDirectoriesFrom(this, file, sequenceSearched);
        mFiles = DataObject.getFilesFrom(this, file, sequenceSearched);
        mFilesAdapter = new FileListAdapter(this, mFolders, mFiles, this, this, this);
        mListView.setAdapter(mFilesAdapter);
        mActionBarManager.setFile(dir);
        if(!isActionPending) {
            mItemsSelected.clear();
            mActionBarManager.setContextualFloatingActionButtonVisibility(false);
            mActionBarManager.setFloatingActionButtonVisibility(true);
        }
    }
}
