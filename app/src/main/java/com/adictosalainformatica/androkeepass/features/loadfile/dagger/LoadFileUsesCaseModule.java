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

package com.adictosalainformatica.androkeepass.features.loadfile.dagger;

import com.adictosalainformatica.androkeepass.features.loadfile.data.repository.DatabaseRepository;
import com.adictosalainformatica.androkeepass.features.loadfile.domain.usecases.CreateDatabaseUseCase;
import com.adictosalainformatica.androkeepass.features.loadfile.domain.usecases.DeleteDatabaseUseCase;
import com.adictosalainformatica.androkeepass.features.loadfile.domain.usecases.GetDatabasesUseCase;
import com.adictosalainformatica.androkeepass.features.loadfile.domain.usecases.impl.CreateDatabaseUseCaseImpl;
import com.adictosalainformatica.androkeepass.features.loadfile.domain.usecases.impl.DeleteDatabaseUseCaseImpl;
import com.adictosalainformatica.androkeepass.features.loadfile.domain.usecases.impl.GetDatabasesUseCaseImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class LoadFileUsesCaseModule {
    @Provides
    public CreateDatabaseUseCase providesCreateDatabaseUseCase(DatabaseRepository databaseRepository){
        return new CreateDatabaseUseCaseImpl(databaseRepository);
    }

    @Provides
    public GetDatabasesUseCase providesGetDatabasesUseCase(DatabaseRepository databaseRepository){
        return new GetDatabasesUseCaseImpl(databaseRepository);
    }

    @Provides
    public DeleteDatabaseUseCase providesDeleteDatabaseUseCase(DatabaseRepository databaseRepository){
        return new DeleteDatabaseUseCaseImpl(databaseRepository);
    }
}
