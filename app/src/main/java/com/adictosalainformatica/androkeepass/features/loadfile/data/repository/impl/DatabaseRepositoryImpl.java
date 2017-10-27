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

import android.os.Environment;

import com.adictosalainformatica.androkeepass.features.loadfile.data.repository.DatabaseRepository;
import com.adictosalainformatica.androkeepass.features.loadfile.domain.model.Database;
import com.sromku.simple.storage.SimpleStorage;
import com.sromku.simple.storage.Storage;
import com.sromku.simple.storage.helpers.OrderType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import de.slackspace.openkeepass.KeePassDatabase;
import de.slackspace.openkeepass.domain.EntryBuilder;
import de.slackspace.openkeepass.domain.Group;
import de.slackspace.openkeepass.domain.GroupBuilder;
import de.slackspace.openkeepass.domain.KeePassFile;
import de.slackspace.openkeepass.domain.KeePassFileBuilder;
import rx.Observable;
import timber.log.Timber;

import static com.adictosalainformatica.androkeepass.features.loadfile.data.repository.mapper.DatabaseListMapper.fromFileToDtabaseModel;

/**
 * Repository Class
 *
 * <p>Manages databases CRUD operations</p>
 */
public class DatabaseRepositoryImpl implements DatabaseRepository{
    private static final String DATABASE_EXTENSION = ".kdbx";
    private final String DEFAULT_FOLDER = "AndroKeePass";
    private Storage storage;
    private String path;

    /**
     * Repository constructor
     *
     * @param storage
     */
    public DatabaseRepositoryImpl(Storage storage) {
        this.storage = storage;

        if (storage.getStorageType().equals(SimpleStorage.StorageType.EXTERNAL)) {
            path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + DEFAULT_FOLDER + "/";
        }else {
            path = Environment.getRootDirectory().getAbsolutePath() + "/" + DEFAULT_FOLDER + "/";
        }

        if(!isDefaultFolderCreated()){
            storage.createDirectory(DEFAULT_FOLDER);
        }
    }

    /**
     * Check if default folder is created
     *
     * @return true if exists, otherwise false
     */
    private boolean isDefaultFolderCreated(){
        return storage.isDirectoryExists(DEFAULT_FOLDER);
    }

    /**
     * Creates a database with default example group and entry
     *
     * @param fileName
     * @param password
     * @return
     * @throws FileNotFoundException
     */
    @Override
    public Observable createDatabase(String fileName, String password) throws FileNotFoundException {
        return Observable.create(subscriber -> {
            Group root = new GroupBuilder()
                    .addGroup(new GroupBuilder("Matrix")
                            .addEntry(new EntryBuilder("The liberator")
                                    .username("Trinity")
                                    .password("follow the white rabbit")
                                    .build())
                            .build())
                    .build();

            KeePassFile keePassFile = new KeePassFileBuilder("writingDB")
                    .addTopGroups(root)
                    .build();

            // Write KeePass file to disk
            if(!storage.isFileExist(DEFAULT_FOLDER, fileName)){
                if(!path.isEmpty()){
                    Timber.d( "database: " + path+ fileName + DATABASE_EXTENSION);
                    try {
                        KeePassDatabase.write(keePassFile, password, new FileOutputStream(path + fileName + DATABASE_EXTENSION));
                        subscriber.onNext(fileName);
                        subscriber.onCompleted();
                    } catch (FileNotFoundException e) {
                        subscriber.onError(new Throwable(
                                "Error creating database: " +  e.getMessage()));
                    }


                }
            }
        });
    }

    /**
     * Delete a single database
     *
     * @param databaseName
     * @return database name if it is deleted, otherwise error
     */
    @Override
    public Observable deleteDatabase(String databaseName) {
        return Observable.create(subscriber -> {
            storage.deleteFile(DEFAULT_FOLDER, databaseName);

            if(!storage.isFileExist(DEFAULT_FOLDER, databaseName)){
                subscriber.onNext(databaseName);
                subscriber.onCompleted();
            }else{
                subscriber.onError(new Throwable(
                        "Error deleting database: " + databaseName));
            }
        });
    }

    /**
     * Delete database list
     *
     * @param databases
     * @return true if all databases are deleted, otherwise error
     */
    /*@Override
    public Observable deleteDatabase(List<String> databases) {
        return Observable.create(subscriber -> {

            for (String databaseName: databases){
                storage.deleteFile(DEFAULT_FOLDER, databaseName);

                if(storage.isFileExist(DEFAULT_FOLDER, databaseName)){
                    subscriber.onError(new Throwable(
                            "Error deleting database: " + databaseName));
                }
            }

            subscriber.onNext(true);
            subscriber.onCompleted();
        });
    }*/

    /**
     * Get all recent databases
     *
     * @return recent databases list, otherwise error
     */
    @Override
    public Observable getAllDatabases() {
        return Observable.create(subscriber -> {
                List<Database> databaseList = new ArrayList<>();
                List<File> files;

                files = storage.getFiles(DEFAULT_FOLDER, OrderType.DATE);
                for(File file: files){
                    databaseList.add(fromFileToDtabaseModel(file));
                }

                if(databaseList != null && !databaseList.isEmpty()){
                    subscriber.onNext(databaseList);
                    subscriber.onCompleted();
                }else{
                    subscriber.onError(new Throwable(
                            "No databases found"));
                }
        });
    }
}
