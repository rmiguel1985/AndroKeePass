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

package com.adictosalainformatica.androkeepass.base.presentation.presenter;

/**
 * Base Default Subscriber Class
 *
 * <p>Default subscriber base class to be used whenever you want default error handling.</p>
 */
public class BaseDefaultSubscriber<T> extends rx.Subscriber<T> {
    @Override public void onCompleted() {}

    @Override public void onError(Throwable e) {}

    @Override public void onNext(T t) {}
}
