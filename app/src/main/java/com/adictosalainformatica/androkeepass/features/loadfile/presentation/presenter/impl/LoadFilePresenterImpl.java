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

package com.adictosalainformatica.androkeepass.features.loadfile.presentation.presenter.impl;


import com.adictosalainformatica.androkeepass.base.presentation.presenter.BaseDefaultSubscriber;
import com.adictosalainformatica.androkeepass.features.loadfile.domain.model.Database;
import com.adictosalainformatica.androkeepass.features.loadfile.domain.usecases.CreateDatabaseUseCase;
import com.adictosalainformatica.androkeepass.features.loadfile.domain.usecases.DeleteDatabaseUseCase;
import com.adictosalainformatica.androkeepass.features.loadfile.domain.usecases.GetDatabasesUseCase;
import com.adictosalainformatica.androkeepass.features.loadfile.presentation.presenter.LoadFilePresenter;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * LoadFilePresenterImpl Class
 *
 * <p>Class to manage LoadFileActivity business logic</p>
 */
public class LoadFilePresenterImpl implements LoadFilePresenter{
    private DeleteDatabaseUseCase deleteDatabaseUseCase;
    private CreateDatabaseUseCase createDatabaseUseCase;
    private GetDatabasesUseCase getDatabasesUseCase;
    private View view;

    /**
     * LoadFilePresenterImpl Constructor
     *
     * @param createDatabaseUseCase Use case to create databases
     * @param getDatabasesUseCase  Use case to get databases
     * @param deleteDatabaseUseCase  Use case to delete databases
     */
    public LoadFilePresenterImpl(CreateDatabaseUseCase createDatabaseUseCase,
                                 GetDatabasesUseCase getDatabasesUseCase,
                                 DeleteDatabaseUseCase deleteDatabaseUseCase) {
        this.getDatabasesUseCase = getDatabasesUseCase;
        this.createDatabaseUseCase= createDatabaseUseCase;
        this.deleteDatabaseUseCase = deleteDatabaseUseCase;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        createDatabaseUseCase.unsubscribe();
        deleteDatabaseUseCase.unsubscribe();
        getDatabasesUseCase.unsubscribe();
        createDatabaseUseCase = null;
        deleteDatabaseUseCase = null;
        getDatabasesUseCase = null;

        this.detachView();
    }

    @Override
    public void attachView(View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    /**
     * Create database
     *
     * @param fileName database file name
     * @param password password to encrypt database
     */
    @Override
    public void createDatabase(String fileName, String password) {
        view.showProgress();
        createDatabaseUseCase.createDatabase(fileName, password);
        createDatabaseUseCase.execute(new CreateDatabaseSubscriber());
    }

    /**
     * Delete database
     *
     * @param databaseName database name to delete
     */
    @Override
    public void deleteDatabase(String databaseName) {
        view.showProgress();
        deleteDatabaseUseCase.deleteDatabase(databaseName);
        deleteDatabaseUseCase.execute(new DeleteDatabaseSubscriber());
    }

    /**
     * Load local database list
     *
     */
    @Override
    public void loadDatabaseList(){
        view.showProgress();
        getDatabasesUseCase.execute(new GetDatabaseListSubscriber());
    }

    /**
     * Filter data base list by database name
     *
     * @param databaseList database list to filter
     * @param query query to search
     */
    @Override
    public void filter(List<Database> databaseList, String query) {
        final String lowerCaseQuery = query.toLowerCase();

        final List<Database> filteredDatabaseList = new ArrayList<>();
        for (Database database : databaseList) {
            final String databaseName = database.getDatabaseName().toLowerCase();
            if (databaseName.contains(lowerCaseQuery)) {
                filteredDatabaseList.add(database);
            }
        }

        if(!filteredDatabaseList.isEmpty()){
            view.onDatabaseListFiltered(filteredDatabaseList);
        }

    }

    /**
     * GetDatabaseListSubscriber Class
     *
     * <p>Class to manage RxJava stream when getting database</p>
     */
    private final class GetDatabaseListSubscriber extends BaseDefaultSubscriber<List<Database>>{
        @Override public void onCompleted() {
            super.onCompleted();
            view.hideProgress();
        }

        @Override public void onError(Throwable e) {
            super.onError(e);
            Timber.e("Error loading recent databases: " + e.getMessage());
            view.hideProgress();
            //view.showErrorLoadingRecentDatabasesList();
        }

        @Override
        public void onNext(List<Database> databases) {
            super.onNext(databases);
            view.onDatabaseListLoaded(databases);
        }
    }

    /**
     * CreateDatabaseSubscriber Class
     *
     * <p>Class to manage RxJava streams when creating database</p>
     */
    private final class CreateDatabaseSubscriber extends BaseDefaultSubscriber<String> {

        @Override public void onCompleted() {
            super.onCompleted();
            view.hideProgress();
        }

        @Override public void onError(Throwable e) {
            super.onError(e);
            Timber.e("Error creating database: " + e.getMessage());
            view.hideProgress();
            view.showErrorCreatingDatabase();
        }

        @Override public void onNext(String fileName) {
            super.onNext(fileName);
            view.onDatabaseCreated(fileName);
        }
    }

    /**
     * DeleteDatabaseSubscriber Class
     *
     * <p>Class to manage RxJava streams when deleting database</p>
     */
    private class DeleteDatabaseSubscriber extends BaseDefaultSubscriber<String> {
        @Override public void onCompleted() {
            super.onCompleted();
            view.hideProgress();
        }

        @Override public void onError(Throwable e) {
            super.onError(e);
            Timber.e("Error deleting database: " + e.getMessage());
            view.hideProgress();
            view.showErrorDeletingDatabase();
        }

        @Override public void onNext(String fileName) {
            super.onNext(fileName);
            view.onDatabaseDeleted(fileName);
        }
    }
}
