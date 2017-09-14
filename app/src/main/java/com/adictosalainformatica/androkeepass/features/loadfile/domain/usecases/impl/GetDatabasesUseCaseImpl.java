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
import com.adictosalainformatica.androkeepass.features.loadfile.domain.usecases.GetDatabasesUseCase;

import rx.Observable;

/**
 * GetDatabasesUseCaseImpl Class
 *
 * <p>Use Case Class to get databases</p>
 */
public class GetDatabasesUseCaseImpl extends GetDatabasesUseCase {
    private DatabaseRepository databaseRepository;

    public GetDatabasesUseCaseImpl(DatabaseRepository databaseRepository) {
        this.databaseRepository = databaseRepository;
    }

    @Override
    protected Observable buildObservableUseCase() {
        return this.databaseRepository.getAllDatabases();
    }

    @Override
    public void unsubscribe(){
        super.unsubscribe();
        databaseRepository = null;
    }
}
