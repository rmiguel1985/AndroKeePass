/*
 *      DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 *                  Version 2, December 2004
 *
 *      Copyright (C) 2004 Sam Hocevar <sam@hocevar.net>
 *
 *      Everyone is permitted to copy and distribute verbatim or modified
 *      copies of this license document, and changing it is allowed as long
 *      as the name is changed.
 *
 *      DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 *      TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
 *
 *      0. You just DO WHAT THE FUCK YOU WANT TO.
 */

package com.adictosalainformatica.androkeepass.features.loadfile.presentation.view.activity;

import android.Manifest;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.adictosalainformatica.androkeepass.R;
import com.adictosalainformatica.androkeepass.base.presentation.view.activity.BasePresenterActivity;
import com.adictosalainformatica.androkeepass.features.loadfile.domain.model.Database;
import com.adictosalainformatica.androkeepass.features.loadfile.presentation.presenter.LoadFilePresenter;
import com.adictosalainformatica.androkeepass.features.loadfile.presentation.view.activity.adapter.DatabaseListAdapter;
import com.adictosalainformatica.androkeepass.features.pin.view.PinLockPreferenceActivity;
import com.adictosalainformatica.androkeepass.utils.dialog.DialogUtils;
import com.adictosalainformatica.androkeepass.utils.dialog.DialogUtilsManageDatabaseListener;
import com.adictosalainformatica.androkeepass.utils.recyclerview.DividerItemDecoration;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.wordpress.passcodelock.AppLockManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.slackspace.openkeepass.KeePassDatabase;
import de.slackspace.openkeepass.domain.Entry;
import de.slackspace.openkeepass.domain.Group;
import de.slackspace.openkeepass.domain.KeePassFile;
import timber.log.Timber;

import static com.adictosalainformatica.AndroKeePassApplication.getDaggerComponent;

public class LoadFileActivity extends BasePresenterActivity<LoadFilePresenter> implements
        LoadFilePresenter.View, DatabaseListAdapter.OnDatabaseListItemClickedListener,
        DatabaseListAdapter.OnDatabaseListItemLongClickedListener,
        DialogUtilsManageDatabaseListener {

    private static final int CREATE_DATABASE_DIALOG = 1000;
    @Inject LoadFilePresenter loadFilePresenter;

    @BindView (R.id.loadfile_recycler_view_recents_databases)
    RecyclerView recentDatabasesRecyclerView;
    @BindView (R.id.loadfile_button_create_file)
    Button createFile;
    @BindView(R.id.loadfile_progressbar_list)
    ProgressBar progressBar;

    private DatabaseListAdapter adapter;
    private Context context = this;
    private SearchView searchView;
    private Toolbar toolbar;
    private static List<Database> databaseList = new ArrayList<>();
    private MenuItem clearButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loadfile_activity);
        ButterKnife.bind(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        createFile.setFilterTouchesWhenObscured(true);

        getDaggerComponent().loadFileSubcomponent().inject(this);

        loadFilePresenter.attachView(this);
        setPresenter(loadFilePresenter);

        createDatabaseListAdapter();

        getStoragePermission();
    }

    @Override
    public void onPause(){
        super.onPause();

        if (searchView != null) {
            searchView.setQuery("", false);
            searchView.clearFocus();
            searchView.onActionViewCollapsed();
        }
    }

    private void createDatabaseListAdapter(){
        adapter = new DatabaseListAdapter();
        adapter.setOnDatabaseListItemClickedListener(this);
        adapter.setOnDatabaseListItemLongClickedListener(this);
        recentDatabasesRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, OrientationHelper.VERTICAL, false));
        recentDatabasesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        recentDatabasesRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recentDatabasesRecyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_user_search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        final MenuItem searchActionMenuItem = menu.findItem(R.id.menu_search);

        clearButton = menu.findItem(R.id.menu_clear_search);
        clearButton.setVisible(false);

        searchView = (SearchView) searchActionMenuItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true);

        searchView.setOnCloseListener(() -> {
            onDatabaseListFiltered(databaseList);
            Timber.d("search close");
            return false;
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                }

                if(query.isEmpty()){
                    onDatabaseListFiltered(databaseList);
                }else{
                    getPresenter().filter(databaseList, query);
                    toolbar.setTitle(query);
                    clearButton.setVisible(true);
                    searchActionMenuItem.collapseActionView();
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                /*if(query.equals("")){
                    onDatabaseListFiltered(databaseList);
                }*/
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_passcodelock:
                if(AppLockManager.getInstance().getAppLock().isPasswordLocked()){
                    Intent i = new Intent(Intent.ACTION_MAIN);
                    i.addCategory(Intent.CATEGORY_HOME);
                    startActivity(i);
                }else{
                    Toast.makeText(context, "Enable app lock in setting", Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.action_settings:
                startActivity(new Intent(this, PinLockPreferenceActivity.class));
                return true;
            case R.id.menu_clear_search:
                clearButton.setVisible(false);
                setTitle(getString(R.string.app_name));
                onDatabaseListFiltered(databaseList);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getStoragePermission() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        getPresenter().loadDatabaseList();
                        Timber.d("permission granted");
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        DialogUtils.createErrorDialog(context, getString(R.string.permission_denied));
                        Timber.w("permission denied");
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission,
                                                                   PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .withErrorListener(error -> Timber.e("There was an error: " + error.toString()))
                .check();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onDatabaseCreated(String databaseName) {
        Toast.makeText(this, getString(R.string.loadfile_create_database_toast_message, databaseName), Toast.LENGTH_LONG).show();
        getPresenter().loadDatabaseList();
    }

    @Override
    public void onDatabaseListFiltered(List<Database> databaseList) {
        //this.databaseList = databaseList;
        Timber.d( "databases");
        Timber.d( "database: " + databaseList.get(0).getDatabaseName());
        adapter.setDatabases(databaseList);
    }

    @Override
    public void onDatabaseListLoaded(List<Database> databaseList) {
        this.databaseList = databaseList;
        Timber.d( "databases");
        Timber.d( "database: " + databaseList.get(0).getDatabaseName());
        adapter.setDatabases(databaseList);
    }

    @Override
    public void onDatabaseDeleted(String databaseName) {
        Timber.d("Database " + databaseName + " deleted");
        Toast.makeText(this, getString(R.string.loadfile_delete_database_toast_message, databaseName), Toast.LENGTH_LONG).show();
        getPresenter().loadDatabaseList();
    }

    @Override
    public void showErrorCreatingDatabase() {
        DialogUtils.createErrorDialog(context, getString(R.string.loadfile_error_creating_database));
    }

    @Override
    public void showErrorDeletingDatabase() {
        DialogUtils.createErrorDialog(context,  getString(R.string.loadfile_error_deleting_databases));
    }

    @OnClick(R.id.loadfile_button_create_file)
    public void onViewClicked() {
        Timber.d("create database button clicked");
        //DialogUtils.createDatabaseDialog(context, this);
        Intent intent = new Intent(this, CreateDatabaseActivity.class);
        startActivityForResult(intent, CREATE_DATABASE_DIALOG);
    }

    /**
     * If user clicked update redirects to play store,
     * otherwise app is closed
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CREATE_DATABASE_DIALOG:
                if (resultCode == Activity.RESULT_OK) {
                    Timber.d("Create database click: " + data.getStringExtra("databaseName"));
                    getPresenter().createDatabase(data.getStringExtra("databaseName"),
                            data.getStringExtra("password"));
                }
                break;
            default:
                finish();
        }
    }

    @Override
    public void onDatabaseListItemClicked(Database database) {
        Timber.d( "database clicked: " + database.getDatabaseName());
        DialogUtils.openDatabaseDialog(database, context, this);
    }

    @Override
    public void onDatabaseListItemLongClickedListener(Database database) {
        Timber.d("Long click: " + database.getDatabaseName());
        DialogUtils.deleteDatabaseDialog(database.getDatabaseName(), this, context);
    }

    @Override
    public void onOpenDatabaseDialogClicked(Database database) {
        Timber.d("Open database click: " + database.getDatabaseName());
        //database.setPassword(password);

        Timber.d("path" + database.getDatabasePath() + " - " + database.getPassword());

        KeePassFile databaseInstance = KeePassDatabase.getInstance(database.getDatabasePath()).openDatabase(database.getPassword());

        List<Group> groups = databaseInstance.getTopGroups();
        for (Group group : groups) {
            Timber.d("Group:" + group.getName());

            for(Entry entry: group.getEntries()){
                Timber.d( "Title: " + entry.getTitle() + " - " + "Name: " + entry.getUsername() + " - "  + " Password: " + entry.getPassword());
            }
        }
    }

    @Override
    public void onDeleteDatabaseDialogClicked(String databaseName) {
        getPresenter().deleteDatabase(databaseName);
    }
}
