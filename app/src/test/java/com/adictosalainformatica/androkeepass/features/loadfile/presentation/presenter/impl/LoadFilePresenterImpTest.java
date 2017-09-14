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

import com.adictosalainformatica.androkeepass.features.loadfile.data.repository.DatabaseRepository;
import com.adictosalainformatica.androkeepass.features.loadfile.domain.model.Database;
import com.adictosalainformatica.androkeepass.features.loadfile.domain.usecases.CreateDatabaseUseCase;
import com.adictosalainformatica.androkeepass.features.loadfile.domain.usecases.DeleteDatabaseUseCase;
import com.adictosalainformatica.androkeepass.features.loadfile.domain.usecases.GetDatabasesUseCase;
import com.adictosalainformatica.androkeepass.features.loadfile.domain.usecases.impl.CreateDatabaseUseCaseImpl;
import com.adictosalainformatica.androkeepass.features.loadfile.domain.usecases.impl.DeleteDatabaseUseCaseImpl;
import com.adictosalainformatica.androkeepass.features.loadfile.domain.usecases.impl.GetDatabasesUseCaseImpl;
import com.adictosalainformatica.androkeepass.features.loadfile.presentation.presenter.LoadFilePresenter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.plugins.RxJavaHooks;
import rx.schedulers.Schedulers;

import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LoadFilePresenterImpTest {

    @Mock
    LoadFilePresenter.View view;

    @Mock
    DatabaseRepository databaseRepository;

    private LoadFilePresenter loadFilePresenter;
    private CreateDatabaseUseCase createDatabaseUseCase;
    private DeleteDatabaseUseCase deleteDatabaseUseCase;
    private GetDatabasesUseCase getDatabasesUseCase;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        // Override RxJava schedulers
        RxJavaHooks.setOnIOScheduler(scheduler -> Schedulers.immediate());
        RxJavaHooks.setOnComputationScheduler(scheduler -> Schedulers.immediate());
        RxJavaHooks.setOnNewThreadScheduler(scheduler -> Schedulers.immediate());

        // Override RxAndroid schedulers
        final RxAndroidPlugins rxAndroidPlugins = RxAndroidPlugins.getInstance();
        rxAndroidPlugins.registerSchedulersHook(new RxAndroidSchedulersHook() {
            @Override
            public Scheduler getMainThreadScheduler() {
                return Schedulers.immediate();
            }
        });

        createDatabaseUseCase = new CreateDatabaseUseCaseImpl(databaseRepository);
        deleteDatabaseUseCase = new DeleteDatabaseUseCaseImpl(databaseRepository);
        getDatabasesUseCase = new GetDatabasesUseCaseImpl(databaseRepository);

        loadFilePresenter = new LoadFilePresenterImpl(createDatabaseUseCase, getDatabasesUseCase, deleteDatabaseUseCase);
        loadFilePresenter.attachView(view);
    }

    @After
    public void tearDown() throws Exception {
        createDatabaseUseCase = null;
        deleteDatabaseUseCase = null;
        getDatabasesUseCase = null;
        loadFilePresenter = null;

        RxJavaHooks.reset();
        RxAndroidPlugins.getInstance().reset();
    }

    @Test
    public void createDatabase_calls_onDatabaseCreated_with_correct_database_name() throws Exception {
        //Given
        String databaseName = "createTest";
        String password = "asñldfasdh";

        //When
        when(databaseRepository.createDatabase(databaseName, password)).thenReturn(
                Observable.create(subscriber -> {
                        subscriber.onNext(databaseName);
                        subscriber.onCompleted();
                })
        );

        loadFilePresenter.createDatabase(databaseName, password);
        verify(view).showProgress();

        //Then
        verify(view).onDatabaseCreated(databaseName);
        verify(view).hideProgress();
    }

    @Test
    public void createDatabase_calls_showErrorCreatingDatabase_when_database_could_not_be_created() throws Exception {
        //Given
        String databaseName = "createTest";
        String password = "asñldfasdh";

        //When
        when(databaseRepository.createDatabase(databaseName, password)).thenReturn(
                Observable.create(subscriber -> subscriber.onError(new Throwable(
                        "Error creating database:")))
        );

        loadFilePresenter.createDatabase(databaseName, password);
        verify(view).showProgress();

        //Then
        verify(view).showErrorCreatingDatabase();
        verify(view).hideProgress();
    }

    @Test
    public void deleteDatabase_calls_onDatabaseDeleted_when_database_could_be_deleted() throws Exception {
        //Given
        String databaseName = "createTest";

        //When
        when(databaseRepository.deleteDatabase(databaseName)).thenReturn(
                Observable.create(subscriber -> {
                    subscriber.onNext(databaseName);
                    subscriber.onCompleted();
                })
        );

        loadFilePresenter.deleteDatabase(databaseName);
        verify(view).showProgress();

        //Then
        verify(view).onDatabaseDeleted(databaseName);
        verify(view).hideProgress();
    }

    @Test
    public void deleteDatabase_calls_showErrorDeletingDatabase_when_database_could_not_be_deleted() throws Exception {
        //Given
        String databaseName = "createTest";

        //When
        when(databaseRepository.deleteDatabase(databaseName)).thenReturn(
                Observable.create(subscriber -> subscriber.onError(new Throwable(
                        "Error deleting database")))
        );

        loadFilePresenter.deleteDatabase(databaseName);
        verify(view).showProgress();

        //Then
        verify(view).showErrorDeletingDatabase();
        verify(view).hideProgress();
    }

    @Test
    public void loadDatabaseList_calls_onDatabaseListLoaded_when_database_list_could_be_loaded() throws Exception {
        //Given
        List<Database> databaseList = new ArrayList<>();
        databaseList.add(new Database("testDatabase", "path"));
        databaseList.add(new Database("firstDatabase", "path"));
        databaseList.add(new Database("secondDatabase", "path"));

        //When
        when(databaseRepository.getAllDatabases()).thenReturn(
                Observable.create(subscriber -> {
                    subscriber.onNext(databaseList);
                    subscriber.onCompleted();
                })
        );

        loadFilePresenter.loadDatabaseList();
        verify(view).showProgress();

        //Then
        verify(view).onDatabaseListLoaded(databaseList);
        verify(view).hideProgress();
    }

    @Test
    public void loadDatabaseList_calls_showErrorLoadingRecentDatabasesList_when_database_list_could_not_be_loaded() throws Exception {
        //When
        when(databaseRepository.getAllDatabases()).thenReturn(
                Observable.create(subscriber -> subscriber.onError(new Throwable(
                        "Error loading databases")))
        );

        loadFilePresenter.loadDatabaseList();
        verify(view).showProgress();

        //Then
        verify(view).showErrorLoadingRecentDatabasesList();
        verify(view).hideProgress();
    }

    @Test
    public void filter_given_search_parameter_returns_expected_value() throws Exception {
        //Given
        List<Database> databaseList = new ArrayList<>();
        databaseList.add(new Database("testDatabase", "path"));
        databaseList.add(new Database("firstDatabase", "path"));
        databaseList.add(new Database("secondDatabase", "path"));

        List<Database> expectedDatabaseList = new ArrayList<>();
        expectedDatabaseList.add(new Database("testDatabase", "path"));

        //When
        loadFilePresenter.filter(databaseList, "test");

        //Then
        verify(view).onDatabaseListFiltered((List<Database>) argThat(new MessagesArgumentMatcher()));
    }

   class MessagesArgumentMatcher extends ArgumentMatcher {
       public boolean matches(Object o) {
           if (o instanceof List) {
               List<Database> strings = (List<Database>) o;
               if (strings.size() != 1) return false;
               if (!strings.get(0).getDatabaseName().equals("testDatabase")) return false;
           }
           return true;
       }
    }
}