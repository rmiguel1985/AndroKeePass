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

package com.adictosalainformatica;

import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.adictosalainformatica.androkeepass.BuildConfig;
import com.adictosalainformatica.androkeepass.base.BaseApplication;
import com.adictosalainformatica.androkeepass.base.dagger.AndroKeePassComponent;
import com.adictosalainformatica.androkeepass.base.dagger.AndroKeePassModule;
import com.adictosalainformatica.androkeepass.base.dagger.DaggerAndroKeePassComponent;
import com.github.javiersantos.piracychecker.PiracyChecker;
import com.github.javiersantos.piracychecker.enums.PiracyCheckerCallback;
import com.github.javiersantos.piracychecker.enums.PiracyCheckerError;
import com.github.javiersantos.piracychecker.enums.PirateApp;
import com.scottyab.rootbeer.RootBeer;
import com.squareup.leakcanary.LeakCanary;

import org.wordpress.passcodelock.AppLockManager;

import javax.inject.Inject;

import timber.log.Timber;

public class AndroKeePassApplication extends BaseApplication {

    private static AndroKeePassComponent daggerAndroKeePassComponent;
    @Inject
    PiracyChecker piracyChecker;
    @Inject
    RootBeer rootBeer;

    @Override
    public void onCreate() {
        super.onCreate();

        initializeDagger();
        initializeLogging();
        initializeSecurity();
    }

    /**
     * Init Dagger
     *
     */
    @Override
    protected void initializeDagger(){
        daggerAndroKeePassComponent = DaggerAndroKeePassComponent.builder()
                .androKeePassModule(new AndroKeePassModule(this))
                .build();
        daggerAndroKeePassComponent.inject(this);
    }

    /**
     * Init logging in release build
     *
     */
    @Override
    protected void initializeLogging(){
        if (isDebugBuild()) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    /**
     * Initialize security
     *
     */
    @Override
    protected void initializeSecurity() {
        AppLockManager.getInstance().enableDefaultAppLockIfAvailable(this);
        if (!isDebugBuild()) {
            piracyChecker
                    .enableDebugCheck()
                    .enableEmulatorCheck(false)
                    .enableSigningCertificate("")
                    .callback(new PiracyCheckerCallback() {
                        @Override
                        public void allow() {
                            if(rootBeer.isRootedWithoutBusyBoxCheck()){
                                Toast.makeText(getApplicationContext(),
                                        "Device is rooted, it will incurs in security issues. Don't grant full access to any app",
                                        Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void dontAllow(@NonNull PiracyCheckerError error, @Nullable PirateApp app) {
                           System.exit(0);
                        }
                    })
                    .start();
        }
    }

    /**
     * Initialize diagnostic tools
     *
     */
    @Override
    protected void initializeDiagnosticTools(){
        if (isDebugBuild()) {
            LeakCanary.install(this);

            final StrictMode.ThreadPolicy strictModeThreadPolicy = new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyDeath()
                    .build();
            StrictMode.setThreadPolicy(strictModeThreadPolicy);
        }
    }

    /**
     * Check build type
     *
     * @return true if is a debug build otherwise false
     */
    private boolean isDebugBuild(){
        return BuildConfig.DEBUG ? true:false;
    }

    /**
     * Get DaggerComponent
     *
     * @return DaggerComponent
     */
    public static AndroKeePassComponent getDaggerComponent() {
        return daggerAndroKeePassComponent;
    }

}
