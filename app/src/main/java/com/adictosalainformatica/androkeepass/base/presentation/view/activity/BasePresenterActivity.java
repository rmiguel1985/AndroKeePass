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

package com.adictosalainformatica.androkeepass.base.presentation.view.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.adictosalainformatica.androkeepass.BuildConfig;
import com.adictosalainformatica.androkeepass.base.BaseApplication;
import com.adictosalainformatica.androkeepass.base.presentation.presenter.BasePresenter;

import static android.view.WindowManager.LayoutParams.FLAG_SECURE;

/**
 *  Base Presenter Activity Class
 *
 * <P>Activity that gets  a presenter and holds its life cycle.</P>
 *
 * @param <T>
 */
public abstract class BasePresenterActivity<T extends BasePresenter> extends AppCompatActivity {

    private T presenter;
    public static final String BUILD_TYPE = "release";
    private boolean upNavigationEnabled;

    @Override
    public void onCreate(Bundle savedInstanceState){
        preventScreenCapture();

        super.onCreate(savedInstanceState);
    }

    /**
     * Gets the base application instance.
     *
     * @return Base application object.
     */
    public BaseApplication getBaseApplication() {
        return (BaseApplication) getApplication();
    }

    /**
     * Call Presenter onPause.
     *
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (presenter != null) {
            presenter.pause();
        }
    }

    /**
     * Call Presenter onResume.
     *
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (presenter != null) {
            presenter.resume();
        }
    }

    /**
     * Call Presenter onDestroy.
     *
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.destroy();
        }
    }

    /**
     * Set presenter to BaseActivity.
     *
     * @param presenter Presenter to save.
     */
    protected void setPresenter(T presenter) {
        if (presenter==null){
            throw new NullPointerException("Null Presenter");
        }
        this.presenter = presenter;
    }

    /**
     * Get presenter.
     *
     * @return BaseActivity presenter.
     */
    protected T getPresenter() {
        return presenter;
    }

    /**
     * Prevent screen capture on release build
     *
     */
    private void preventScreenCapture(){
        if(BuildConfig.BUILD_TYPE.equals(BUILD_TYPE)){
            getWindow().setFlags(FLAG_SECURE, FLAG_SECURE);
        }
    }

    protected void enableUpNavigation() {
        ActionBar actionBar = this.getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        this.upNavigationEnabled = true;
    }
}