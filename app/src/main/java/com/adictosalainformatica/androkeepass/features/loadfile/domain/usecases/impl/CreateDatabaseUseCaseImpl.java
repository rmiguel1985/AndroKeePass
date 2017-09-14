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

package com.adictosalainformatica.androkeepass.features.loadfile.domain.usecases.impl;

import com.adictosalainformatica.androkeepass.features.loadfile.data.repository.DatabaseRepository;
import com.adictosalainformatica.androkeepass.features.loadfile.domain.usecases.CreateDatabaseUseCase;

import java.io.FileNotFoundException;

import rx.Observable;

/**
 * CreateDatabaseUseCaseImpl Class
 *
 * <p>Use Case Class to create databases</p>
 */
public class CreateDatabaseUseCaseImpl extends CreateDatabaseUseCase {

    private DatabaseRepository databaseRepository;
    private String password;
    private String databaseName;

    public CreateDatabaseUseCaseImpl(DatabaseRepository databaseRepository) {
        this.databaseRepository = databaseRepository;
    }

    @Override
    public void createDatabase(String databaseName, String password) {
        this.password = password;
        this.databaseName = databaseName;
    }

    @Override
    protected Observable buildObservableUseCase() {
        try {
            return this.databaseRepository.createDatabase(databaseName, password);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

       return null;
    }

    @Override
    public void unsubscribe(){
        super.unsubscribe();
        databaseRepository = null;
    }
}
