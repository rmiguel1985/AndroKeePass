package com.adictosalainformatica.androkeepass.features.pin.view;

import android.app.FragmentManager;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.SwitchPreference;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.adictosalainformatica.androkeepass.R;

import org.wordpress.passcodelock.AppLockManager;
import org.wordpress.passcodelock.PasscodePreferenceFragment;

public class PinLockPreferenceActivity extends AppCompatActivity{
    private static final String KEY_PASSCODE_FRAGMENT = "passcode-fragment";
    private static final String KEY_PREFERENCE_FRAGMENT = "preference-fragment";

    private PasscodePreferenceFragment passcodePreferenceFragment;
    private PinLockPreferenceFragment pinLockPreferenceFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewGroup rootView = (ViewGroup)findViewById(R.id.action_bar_root);
        View view = getLayoutInflater().inflate(R.layout.toolbar, rootView, false);
        rootView.addView(view, 0);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        FragmentManager fragmentManager = getFragmentManager();
        pinLockPreferenceFragment = (PinLockPreferenceFragment) fragmentManager.findFragmentByTag(KEY_PREFERENCE_FRAGMENT);
        passcodePreferenceFragment = (PasscodePreferenceFragment) fragmentManager.findFragmentByTag(KEY_PASSCODE_FRAGMENT);

        if (pinLockPreferenceFragment == null || passcodePreferenceFragment == null) {
            Bundle passcodeArgs = new Bundle();
            passcodeArgs.putBoolean(PasscodePreferenceFragment.KEY_SHOULD_INFLATE, false);
            pinLockPreferenceFragment = new PinLockPreferenceFragment();
            passcodePreferenceFragment = new PasscodePreferenceFragment();
            passcodePreferenceFragment.setArguments(passcodeArgs);

            fragmentManager.beginTransaction()
                    .replace(android.R.id.content, passcodePreferenceFragment, KEY_PASSCODE_FRAGMENT)
                    .add(android.R.id.content, pinLockPreferenceFragment, KEY_PREFERENCE_FRAGMENT)
                    .commit();
        }

        /*setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }*/
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

        Preference togglePreference = pinLockPreferenceFragment.findPreference(
                getString(org.wordpress.passcodelock.R.string.pref_key_passcode_toggle));
        Preference changePreference = pinLockPreferenceFragment.findPreference(
                getString(org.wordpress.passcodelock.R.string.pref_key_change_passcode));

        if (togglePreference != null && changePreference != null) {
            passcodePreferenceFragment.setPreferences(togglePreference, changePreference);
            ((SwitchPreference) togglePreference).setChecked(
                    AppLockManager.getInstance().getAppLock().isPasswordLocked());
        }
    }
}