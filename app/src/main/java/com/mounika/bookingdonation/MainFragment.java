package com.mounika.bookingdonation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.concurrent.Executor;

public class MainFragment extends Fragment {

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    private EditText edPhone;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //Inflate the layout for this fragment

        view = inflater.inflate(R.layout.activity_main, container, false);





        TextView btnNext = (TextView) view.findViewById(R.id.btn_next);
        edPhone = (EditText) view.findViewById(R.id.edPhone);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                Toast.makeText(getActivity(), "work  in progress", Toast.LENGTH_SHORT).show();
                if(isValidate()) {

                    Intent itemIntent = new Intent(getActivity(), MainActivity.class);
                    startActivity(itemIntent);
                }
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
                Intent  itemIntent = new Intent(getActivity(),MainActivity.class);
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

    private boolean isValidate() {
        if(edPhone.getText().length() < 10){
            Toast.makeText(getActivity()," Enter valid phone number ",Toast.LENGTH_SHORT).show();
            return  false;
        }else{
            return true;
        }
    }

}