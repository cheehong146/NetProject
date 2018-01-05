package com.example.yipfeilee.newproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PassCode extends AppCompatActivity {

    TextView tv_passcodeInfo;
    EditText tf_passcode;
    TextView tv_passcodeAttempt;
    Button btn_confirm;
    boolean is_firstTimeUser = true;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_code);

        tv_passcodeInfo = (TextView)findViewById(R.id.tv_passcodeInfo);
        tv_passcodeAttempt = (TextView)findViewById(R.id.tv_passcodeAttempt);
        tf_passcode = (EditText)findViewById(R.id.tf_passcode);
        btn_confirm = (Button)findViewById(R.id.btn_confirm);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        if(is_firstTimeUser){
            tv_passcodeInfo.setText("Register your passcode");
            tv_passcodeAttempt.setText("");
        }else {
            tv_passcodeInfo.setText("Enter your passcode");
            tv_passcodeAttempt.setText("");
        }

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(is_firstTimeUser){
                    registerCode();
                }else {
                    verifyPasscode();
                }
            }
        });
    }

    public void setUserLoginSetting(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        is_firstTimeUser = sharedPreferences.getBoolean("userFirstTime", false);
    }

    public void verifyPasscode(){
        int passcode = Integer.parseInt(tf_passcode.getText().toString());
        int storedPasscode = sharedPreferences.getInt("userLoginPasscode", -1);

        if(passcode == storedPasscode){
            Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
        }else{
            tv_passcodeAttempt.setText("Invalid Credential");
            tv_passcodeAttempt.setTextColor(Color.RED);
        }

    }

    public void registerCode(){
        int passcode = Integer.parseInt(tf_passcode.getText().toString());
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt("userLoginPasscode", passcode);
        edit.putBoolean("userFirstTime", false);
        edit.commit();
        setUserLoginSetting();

        tv_passcodeAttempt.setText("Passcode registered");
        tv_passcodeInfo.setText("Enter your registered passcode");
    }
}
