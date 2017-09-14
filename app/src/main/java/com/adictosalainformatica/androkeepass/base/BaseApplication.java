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

package com.adictosalainformatica.androkeepass.base;


import android.app.Application;

/**
 * Base Application Abstract Class.
 *
 * <p>Define Logging, Dagger and security initializer</p>
 */
public abstract class BaseApplication extends Application {
    protected abstract void initializeDagger();
    protected abstract void initializeLogging();
    protected abstract void initializeSecurity();

    protected abstract void initializeDiagnosticTools();
}
