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


package com.adictosalainformatica.androkeepass.features.loadfile.presentation.presenter;


import com.adictosalainformatica.androkeepass.base.presentation.presenter.BasePresenter;
import com.adictosalainformatica.androkeepass.base.presentation.view.BaseView;
import com.adictosalainformatica.androkeepass.features.loadfile.domain.model.Database;

import java.util.List;

public interface LoadFilePresenter extends BasePresenter<LoadFilePresenter.View> {
    interface View extends BaseView {

        void showProgress();

        void hideProgress();

        void onDatabaseCreated(String fileName);

        void onDatabaseListFiltered(List<Database> databaseList);

        void onDatabaseListLoaded(List<Database> databases);

        void onDatabaseDeleted(String databaseName);

        void showErrorCreatingDatabase();

        void showErrorDeletingDatabase();
    }

    void loadDatabaseList();

    void createDatabase(String fileName, String password);

    void deleteDatabase(String databaseName);

    void filter(List<Database> databaseList, String query);
}