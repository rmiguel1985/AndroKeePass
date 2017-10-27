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

package com.adictosalainformatica.androkeepass.features.loadfile.presentation.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.adictosalainformatica.androkeepass.R;
import com.adictosalainformatica.androkeepass.utils.password.GeneratePasswordActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class CreateDatabaseActivity extends AppCompatActivity {

    private static final int CREATE_PASSWORD_DIALOG = 1001;
    @BindView(R.id.create_database_button)
    Button createDatabase;
    @BindView(R.id.create_database_txt_name)
    EditText databaseName;
    @BindView(R.id.create_database_txt_password)
    EditText databasePassword;
    @BindView(R.id.create_database_txt_password_repeat)
    EditText databasePasswordRepeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_database);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        createDatabase.setFilterTouchesWhenObscured(true);
    }

    @OnClick(R.id.create_database_button)
    public void onCreateDatabaseClicked() {
        Timber.d("create database button clicked");

        if(checkPassword() && checkDatabaseName()){
            Intent resultIntent = new Intent();
            resultIntent.putExtra("databaseName", databaseName.getText().toString());
            resultIntent.putExtra("password", databasePassword.getText().toString());
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        }
    }

    private boolean checkPassword(){

        if(databasePassword.getText().toString().
                equals(databasePasswordRepeat.getText().toString()) &&
                databasePassword.getText().toString().length() >2){
            return true;
        }

        Toast.makeText(getApplicationContext(),
                getString(R.string.loadfile_cretate_database_password_error),
                Toast.LENGTH_LONG).show();

        return false;
    }

    private boolean checkDatabaseName(){

        if(!databaseName.getText().toString().isEmpty() &&
                databaseName.getText().toString().length() >2){
            return true;
        }

        Toast.makeText(getApplicationContext(),
                getString(R.string.loadfile_cretate_database_database_name_error),
                Toast.LENGTH_LONG).show();

        return false;
    }

    @OnClick(R.id.create_database_generate_button)
    public void onCreatePasswordClicked(){
        Intent intent = new Intent(this, GeneratePasswordActivity.class);
        startActivityForResult(intent, CREATE_PASSWORD_DIALOG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CREATE_PASSWORD_DIALOG:
                if (resultCode == Activity.RESULT_OK) {
                    Timber.d("Create database click: " + data.getStringExtra("generatedPassword"));
                    databasePassword.setText(data.getStringExtra("generatedPassword"));
                    databasePasswordRepeat.setText(data.getStringExtra("generatedPassword"));
                }
                break;
            default:
                finish();
        }
    }
}
