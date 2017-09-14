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


package com.adictosalainformatica.androkeepass.base.presentation.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.adictosalainformatica.androkeepass.BuildConfig;
import com.adictosalainformatica.androkeepass.base.presentation.presenter.BasePresenter;

import static android.view.WindowManager.LayoutParams.FLAG_SECURE;

/**
 * Base class for Fragments.
 *
 * <P>Base class that contains the basic set of methods for fragments.</P>
 *
 * @param <T>
 */
public class BasePresenterFragment<T extends BasePresenter> extends Fragment {

    private T presenter;
    public static final String BUILD_TYPE = "release";

    @Override
    public void onCreate(Bundle savedInstanceState){
        preventScreenCapture();

        super.onCreate(savedInstanceState);
    }

    /**
     * Call Presenter onPause.
     *
     */
    @Override
    public void onPause() {
        super.onPause();
        if (presenter != null) {
            presenter.pause();
        }
    }

    /**
     * Call Presenter onResume.
     *
     */
    @Override public void onResume() {
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
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.destroy();
        }
    }

    /**
     * Set presenter to BaseFragment
     *
     * @param presenter Presenter to save
     */
    protected void setPresenter(T presenter) {
        this.presenter = presenter;
    }

    /**
     * Get presenter
     *
     * @return BaseFragment Presenter
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
            getActivity().getWindow().setFlags(FLAG_SECURE, FLAG_SECURE);
        }
    }
}

