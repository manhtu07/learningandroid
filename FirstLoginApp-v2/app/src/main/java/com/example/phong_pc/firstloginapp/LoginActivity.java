package com.example.phong_pc.firstloginapp;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;

import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    Button btnSignUp,btnLogin;
    EditText edUsername,edPassword;
    private ProgressDialog pDialog;
    private String TAG = LoginActivity.class.getSimpleName();
    private boolean isSuccess=false;

    public void doChangeUi()
    {
        final Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialize();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean validate =  validateInput();
                if(!validate){
                    Log.d("TAG", "onClick: "+validate);
                    Toast.makeText(LoginActivity.this, "Please input the above fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    isSuccess=false;
                    new Authentication(getApplicationContext()).execute();
                }
            }
        });

        final Intent intent = new Intent(this, SignUpActivity.class);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });

    }

    private  boolean validateInput(){
        Boolean valid = true;

        String username = edUsername.getText().toString();
        String password = edPassword.getText().toString();

        if(!CommonMethod.isValidUsername(username)){
            valid = false;
            edUsername.setError("The username must be from 4 to 12 characters");
        }
        else if(!CommonMethod.isValidPassword(password)){
            valid = false;
            edUsername.setError("The password must be from 6 to 12 characters maybe including !,$,@");
        }


//        edUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                String username = edUsername.getText().toString();
//                Boolean valid = CommonMethod.isValidUsername(username);
//                if(!valid){
//                    valid = false;
//                    edUsername.setError("The username must be from 4 to 10 characters");
//                }
//                else {
//                    valid = true;
//                }
//            }
//        });
//
//        edPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                String password = edPassword.getText().toString();
//                Boolean valid = CommonMethod.isValidPassword(password);
//                if(!valid){
//                    valid = false;
//                    edPassword.setError("The password must be from 6 to 10 characters including !,$,@");
//                }
//                else {
//                    valid = true;
//                }
//            }
//        });

        return  valid;

    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class Authentication extends AsyncTask<Void, Void, Void> {
        Context context;

        private Authentication(Context context) {
            this.context = context.getApplicationContext();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();
            String jsonStr = "";
            try {

                jsonStr=sh.makeServiceGet(TextConst.URL+edUsername.getText().toString());

                if (jsonStr != null) {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    String password = (String) jsonObj.get("Password");
                    String userPass=edPassword.getText().toString();
                    String hashPass= null;
                    try {
                        hashPass = AESCrypt.encrypt(userPass);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if(password.equalsIgnoreCase(hashPass)){
                        isSuccess=true;
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /** Updating parsed JSON data**/
            if(isSuccess){
                Toast.makeText(getApplicationContext(),
                        "Login success",
                        Toast.LENGTH_LONG)
                        .show();
                context.startActivity(new Intent(context, HomeScreenActivity.class));

            }
        }

    }

    private void initialize(){
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnLogin= (Button) findViewById(R.id.btnLogin);
        edUsername = (EditText) findViewById(R.id.edUsername);
        edPassword = (EditText) findViewById(R.id.edPassword);
    }

}
