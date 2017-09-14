package com.adictosalainformatica.androkeepass.features.pin.view;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.adictosalainformatica.androkeepass.R;

public class PinLockPreferenceFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        addPreferencesFromResource(R.xml.preferences);
    }
}