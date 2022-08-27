package com.mounika.bookingdonation;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.mounika.bookingdonation.database.DBHelper;

import java.util.concurrent.Executor;

public class MainFragment extends Fragment {

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    private EditText ed_login,ed_password;
    private Button btnLogin;
    private TextView signUpTextView;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //Inflate the layout for this fragment

        view = inflater.inflate(R.layout.activity_main, container, false);
        initUI();



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent  i = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(i);*/
                if(!validate()) {
                    doLogin();
                }
            }
        });
        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  i = new Intent(getActivity(), RegisterActivity.class);
                getActivity().startActivity(i);
            }
        });


        executor = ContextCompat.getMainExecutor(getActivity());
        biometricPrompt = new BiometricPrompt(getActivity(),
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getActivity().getApplicationContext(),
                                "Authentication error: " + errString, Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getActivity().getApplicationContext(),
                        "Authentication succeeded!", Toast.LENGTH_SHORT).show();
                Intent  itemIntent = new Intent(getActivity(),HomeActivity.class);
                startActivity(itemIntent);

            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getActivity().getApplicationContext(), "Authentication failed",
                                Toast.LENGTH_SHORT)
                        .show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login for my app")
                .setSubtitle("Log in using your biometric credential")
                .setNegativeButtonText("Use account password")
                .build();

        // Prompt appears when user clicks "Log in".
        // Consider integrating with the keystore to unlock cryptographic operations,
        // if needed by your app.
        ImageView biometricLoginButton =view. findViewById(R.id.biometric_login);
        biometricLoginButton.setOnClickListener(view -> {
            biometricPrompt.authenticate(promptInfo);
        });

        return  view;
    }


    private boolean validate(){
        if(ed_login.getText().toString().trim() == null ||  ed_login.getText().length() == 0){
            Toast.makeText(getActivity(), "Please enter all details", Toast.LENGTH_LONG).show();
            return  true;

        }if(ed_password.getText().toString().trim() == null ||  ed_password.getText().length() == 0){
            Toast.makeText(getActivity(), "Please enter Password", Toast.LENGTH_LONG).show();
            return  true;

        }
        return  false;
    }


    private void initUI() {
        btnLogin = (Button) view. findViewById(R.id.email_sign_in_button);
        ed_login = (EditText) view. findViewById(R.id.email);
        ed_password = (EditText)  view.findViewById(R.id.password);
        signUpTextView = (TextView) view. findViewById(R.id.signUpTextView);
    }

    private void doLogin() {
        // TODO Auto-generated method stub
        String Lname,Lpassword;
        Lname = ed_login.getText().toString();
        Lpassword = ed_password.getText().toString();
       /* Intent userDetails = new Intent(MainActivity.this, Home.class);
        startActivity(userDetails);*/

        DBHelper DBCon = new DBHelper(getActivity());
        SQLiteDatabase db = DBCon.getWritableDatabase();
        String sql = "select * from Details where UserName = '"+ Lname+"' and Password = '"+Lpassword+"'" ;
        Cursor c = db.rawQuery(sql, null);
        if (c!= null && c.getCount() > 0) {
            Intent i = new Intent(getActivity(), HomeActivity.class);
            getActivity().startActivity(i);
            Toast.makeText(getActivity(), "success",
                    Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(getActivity(), "not success",
                    Toast.LENGTH_LONG).show();
        }

    }

}