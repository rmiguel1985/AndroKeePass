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


import com.adictosalainformatica.androkeepass.base.presentation.view.BaseView;

/**
 * Base Presenter Interface.
 *
 * @param <T>
 */
public interface BasePresenter<T extends BaseView> {

    void resume();

    void pause();

    void destroy();

    void attachView(T view);

    void detachView();
}
