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

package com.adictosalainformatica.androkeepass.utils.password;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

import com.adictosalainformatica.androkeepass.R;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class GeneratePasswordActivity extends AppCompatActivity {

    @BindView(R.id.generate_password_length)
    SeekBar passwordLengthProgressBar;
    @BindView(R.id.generatepassword_generated_password)
    TextView generatedPasswordTextView;

    @BindViews({
            R.id.generate_password_check_box_lower_case,
            R.id.generate_password_check_box_upper_case,
            R.id.generate_password_check_box_numbers,
            R.id.generate_password_check_box_punctuation})
    List<CheckBox> passwordOptionsList;

    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_password);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        passwordLengthProgressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                generatePassword();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @OnClick(R.id.generate_password_check_box_lower_case)
    public void onLowerCaseCheckBox(){
        generatePassword();
    }

    @OnClick(R.id.generate_password_check_box_upper_case)
    public void onUpperCaseCheckBox(){
        generatePassword();
    }

    @OnClick(R.id.generate_password_check_box_numbers)
    public void onNumbersCaseCheckBox(){
        generatePassword();
    }

    @OnClick(R.id.generate_password_check_box_punctuation)
    public void onPunctuationCaseCheckBox(){
        generatePassword();
    }

    @OnClick(R.id.generate_password_button)
    public void onViewClicked(){
        if(!password.isEmpty()){
            Timber.d("password: " + password);

            Intent resultIntent = new Intent();
            resultIntent.putExtra("generatedPassword", password);
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        }
    }

    private void generatePassword(){
        password = new PasswordGenerator
                .PasswordGeneratorBuilder()
                .useLower(passwordOptionsList.get(0).isChecked())
                .useUpper(passwordOptionsList.get(1).isChecked())
                .useDigits(passwordOptionsList.get(2).isChecked())
                .usePunctuation(passwordOptionsList.get(3).isChecked())
                .build().generate(passwordLengthProgressBar.getProgress() + 1);

        generatedPasswordTextView.setText(getString(R.string.generatedpassword_generated_password)
                + ": " + password);
    }
}
