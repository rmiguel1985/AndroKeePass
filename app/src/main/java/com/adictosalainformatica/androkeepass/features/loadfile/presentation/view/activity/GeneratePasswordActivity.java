package com.adictosalainformatica.androkeepass.features.loadfile.presentation.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

import com.adictosalainformatica.androkeepass.R;
import com.adictosalainformatica.androkeepass.utils.PasswordGenerator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class GeneratePasswordActivity extends AppCompatActivity {

    @BindView(R.id.generate_password_button)
    Button generatePasswordButton;
    @BindView(R.id.generate_password_check_box_lower_case)
    CheckBox lowerCaseCheckBox;
    @BindView(R.id.generate_password_check_box_upper_case)
    CheckBox upperCaseCheckBox;
    @BindView(R.id.generate_password_check_box_numbers)
    CheckBox numbersCheckBox;
    @BindView(R.id.generate_password_check_box_punctuation)
    CheckBox punctuationCheckBox;
    @BindView(R.id.generate_password_length)
    SeekBar passwordLengthProgressBar;
    @BindView(R.id.generatepassword_generated_password)
    TextView generatedPasswordTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_password);
        ButterKnife.bind(this);

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
        String password;
        password = generatedPasswordTextView.getText().toString();

        if(!password.isEmpty()){
            Timber.d("password: " + password);

            Intent resultIntent = new Intent();
            resultIntent.putExtra("generatedPassword", password);
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        }

    }

    private void generatePassword(){
        String password = new PasswordGenerator
                .PasswordGeneratorBuilder()
                .useDigits(numbersCheckBox.isChecked())
                .useLower(lowerCaseCheckBox.isChecked())
                .useUpper(upperCaseCheckBox.isChecked())
                .usePunctuation(punctuationCheckBox.isChecked())
                .build().generate(passwordLengthProgressBar.getProgress() + 1);

        generatedPasswordTextView.setText(getString(R.string.generatedpassword_generated_password)
                + ": " + password);
    }
}
