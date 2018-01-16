package com.example.phong_pc.firstloginapp;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {

    Button btnSignUp,btnLogin;
    EditText edUsername,edPassword;
    private ProgressDialog pDialog;
    private String TAG = LoginActivity.class.getSimpleName();
    private static String url = "http://172.27.9.23/api/User/";
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


        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        edUsername   = (EditText)findViewById(R.id.edUsername);
        edPassword   = (EditText)findViewById(R.id.edPassword);

        final Intent intent = new Intent(this, SignUpActivity.class);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSuccess=false;
                new Authentication(getApplicationContext()).execute();
            }
        });
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

                jsonStr=sh.makeServiceGet(url+edUsername.getText().toString());

                if (jsonStr != null) {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    String password = (String) jsonObj.get("Password");
                    String userPass=edPassword.getText().toString();

                    if(password.equalsIgnoreCase(userPass)){
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
                context.startActivity(new Intent(context, MainActivity.class));

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
