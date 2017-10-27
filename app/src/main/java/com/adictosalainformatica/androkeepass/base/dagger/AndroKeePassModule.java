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

package com.adictosalainformatica.androkeepass.base.dagger;

import android.app.Application;
import android.content.Context;

import com.github.javiersantos.piracychecker.PiracyChecker;
import com.scottyab.rootbeer.RootBeer;
import com.sromku.simple.storage.SimpleStorage;
import com.sromku.simple.storage.Storage;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AndroKeePassModule {
    private final Application application;

    public AndroKeePassModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Context providesContext() {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    public PiracyChecker providesPiracyChecker(Context context){
        return new PiracyChecker(context);
    }

    @Provides
    @Singleton
    public RootBeer providesRootBeer(Context context){
        return new RootBeer(context);
    }

    @Provides
    @Singleton
    Storage providesSimpleStorage(Context context){
        Storage storage;
        if (SimpleStorage.isExternalStorageWritable()) {
            storage = SimpleStorage.getExternalStorage();
        }
        else {
            storage = SimpleStorage.getInternalStorage(context);
        }

        return storage;
    }
}
