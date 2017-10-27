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

package com.adictosalainformatica.androkeepass.features.loadfile.data.repository.impl;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.adictosalainformatica.androkeepass.features.loadfile.domain.model.Database;
import com.sromku.simple.storage.SimpleStorage;
import com.sromku.simple.storage.Storage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;

public class DatabaseRepositoryImplTest {

    private DatabaseRepositoryImpl databaseRepository;
    private String databaseName = "testDatabase";
    private Context context;
    private String databasePassword = "test1234";

    @Before
    public void setUp() throws Exception {
        context = InstrumentationRegistry.getContext();
        databaseRepository = new DatabaseRepositoryImpl(initializeStorage(context));

        RxAndroidPlugins.getInstance().registerSchedulersHook(new RxAndroidSchedulersHook() {
            @Override
            public Scheduler getMainThreadScheduler() {
                return Schedulers.immediate();
            }
        });
    }

    private Storage initializeStorage(Context context){
        Storage storage;

        if (SimpleStorage.isExternalStorageWritable()) {
            storage = SimpleStorage.getExternalStorage();
        }
        else {
            storage = SimpleStorage.getInternalStorage(context);
        }

        return storage;
    }

    @After
    public void tearDown() throws Exception {
        RxAndroidPlugins.getInstance().reset();
    }

    @Test
    public void test1_createDatabase_creates_expected_database() throws Exception {
        //Given
        Observable<String> obs = databaseRepository.createDatabase(databaseName, databasePassword);
        TestSubscriber<String> testSubscriber = new TestSubscriber<>();
        obs.subscribe(testSubscriber);
        testSubscriber.assertNoErrors();

        //When
        List<String> databaseList = testSubscriber.getOnNextEvents();

        //Then
        assertNotNull(databaseList);
        assertEquals(databaseName, databaseList.get(0));
    }

    @Test
    public void test2_getAllDatabases_returns_all_databases() throws Exception {
        //Given
        Observable<Database> obs = databaseRepository.getAllDatabases();
        TestSubscriber<Database> testSubscriber = new TestSubscriber<>();
        obs.subscribe(testSubscriber);

        //When
        testSubscriber.assertNoErrors();
        List<Database> databaseList = (List<Database>) testSubscriber.getOnNextEvents().get(0);

        //Then
        assertNotNull(databaseList);
        assertFalse(databaseList.isEmpty());
    }

    @Test
    public void test3_deleteDatabase_deletes_expected_database() throws Exception {
        //Given
        Observable<String> obs = databaseRepository.deleteDatabase(databaseName);
        TestSubscriber<String> testSubscriber = new TestSubscriber<>();
        obs.subscribe(testSubscriber);
        testSubscriber.assertNoErrors();

        //When
        List<String> databaseList = testSubscriber.getOnNextEvents();

        //Then
        assertNotNull(databaseList);
        assertEquals(databaseName, databaseList.get(0));
    }
}