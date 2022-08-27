package com.mounika.bookingdonation;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mounika.bookingdonation.database.DBHelper;


public class RegisterActivity extends AppCompatActivity {
    private EditText ed_email,ed_password,ed_cnPassword;
    private Button btnSignUp;
    private TextView signInTextView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        initUI();
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validate()) {
                    String userName, password;
                    userName = ed_email.getText().toString();
                    password = ed_password.getText().toString();
                    //cnfPassword = ed.getText().toString();
                    // traPassword = transactionPassword.getText().toString();

                    DBHelper dbh = new DBHelper(getApplicationContext());
                    ContentValues cv = new ContentValues();
                    SQLiteDatabase db = dbh.getWritableDatabase();
                    cv.put("UserName", userName);
                    cv.put("Password", password);
                    //cv.put("ConformPassword", cnfPassword);
                    //cv.put("TransactionPassword", traPassword);
                    db.insert("Details", null, cv);
                    Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG).show();
                    db.close();
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
        signInTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  i = new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
    }

    private boolean validate(){
        if(ed_email.getText().toString().trim() == null || ed_email.getText().toString().length() ==0){
            Toast.makeText(getApplicationContext(), "Please enter all details", Toast.LENGTH_LONG).show();
            return  true;

        }if(ed_password.getText().toString().trim() == null || ed_password.getText().toString().length() == 0){
            Toast.makeText(getApplicationContext(), "Please enter Password", Toast.LENGTH_LONG).show();
            return  true;

        }if(ed_cnPassword.getText().toString().trim() == null || ed_cnPassword.getText().toString().length() == 0){
            Toast.makeText(getApplicationContext(), "Please enter Confirm password", Toast.LENGTH_LONG).show();
            return  true;

        }else if(ed_password.getText().toString().trim().equalsIgnoreCase(ed_cnPassword.getText().toString().trim())){
//            Toast.makeText(getApplicationContext(), "Password Not match", Toast.LENGTH_LONG).show();
//
            return  false;

        }else{
            return  false;

        }

    }

    private void initUI() {
        btnSignUp = (Button)  findViewById(R.id.email_sign_Up_button);
        ed_email = (EditText)  findViewById(R.id.email);
        ed_password = (EditText)  findViewById(R.id.passwordone);
        ed_cnPassword = (EditText)  findViewById(R.id.cnpasswordone);
        signInTextView = (TextView)  findViewById(R.id.signINTextView);
    }
}
