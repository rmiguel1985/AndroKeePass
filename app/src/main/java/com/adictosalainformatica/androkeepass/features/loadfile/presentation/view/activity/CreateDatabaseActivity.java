package com.adictosalainformatica.androkeepass.features.loadfile.presentation.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.adictosalainformatica.androkeepass.R;

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

        createDatabase.setFilterTouchesWhenObscured(true);
    }

    @OnClick(R.id.create_database_button)
    public void onCreateDatabaseClicked() {
        Timber.d("create database button clicked");

        if(databasePassword.getText().toString().
                equals(databasePasswordRepeat.getText().toString())){
            Intent resultIntent = new Intent();
            resultIntent.putExtra("databaseName", databaseName.getText().toString());
            resultIntent.putExtra("password", databasePassword.getText().toString());
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        }else{
            Toast.makeText(getApplicationContext(),"Error", Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.button)
    public void onCreatePasswordClicked(){
        Intent intent = new Intent(this, GeneratePasswordActivity.class);
        startActivityForResult(intent, CREATE_PASSWORD_DIALOG);
    }

    /**
     * If user clicked update redirects to play store,
     * otherwise app is closed
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
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
