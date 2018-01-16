package com.example.phong_pc.firstloginapp;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import com.example.phong_pc.firstloginapp.TextConst;

public class SignUpActivity extends AppCompatActivity {

    EditText edBirthDay,edUsernameR,edFullName,edPasswordR,edPasswordR2,edEmailR;
    TextView txtSignUp;
    Button btnSignUp;
    private boolean isSuccess=false;
    private ProgressDialog pDialog;
    private String TAG = SignUpActivity.class.getSimpleName();
    private static String url = "http://172.27.9.23/api/User/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edBirthDay = (EditText) findViewById(R.id.edBirthdayR);
        edUsernameR = (EditText) findViewById(R.id.edUsernameR);
        edEmailR = (EditText) findViewById(R.id.edEmailR);
        edPasswordR = (EditText) findViewById(R.id.edPasswordR);
        edPasswordR2 = (EditText) findViewById(R.id.edPasswordR2);
        btnSignUp = (Button) findViewById(R.id.btnSignUpR);
        edFullName=(EditText) findViewById(R.id.edFullName);

        edBirthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });

        final Intent intent = new Intent(this,LoginActivity.class);

        txtSignUp = (TextView) findViewById(R.id.txtSignUp);
        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSuccess=false;
                new SingupAsyn(getApplicationContext()).execute();
            }
        });

    }

    private void showDatePicker(){
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int date = calendar.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i,i1,i2);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                edBirthDay.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, year,month,date);
        datePickerDialog.show();
    }
    /**
     * Async task class to get json by making HTTP call
     */
    private class SingupAsyn extends AsyncTask<Void, Void, Void> {

        Context context;

        private SingupAsyn(Context context) {
            this.context = context.getApplicationContext();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(SignUpActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }
        @Override
        protected Void doInBackground(Void... voids) {

            HttpHandler sh = new HttpHandler();
            String jsonStr = "";

            String hashPass= null;
            try {
                hashPass = AESCrypt.encrypt(edPasswordR.getText().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

            String jsonData="{'Username':'"+edUsernameR.getText().toString()+"','Password':'"+hashPass+"','Fullname':'"+edFullName.getText().toString()+"','Email':'"+edEmailR.getText().toString()+"','Phone':'0966630091','Status':1}";
            try {

                jsonStr=sh.makeServicePostOrPut(TextConst.URL,jsonData,"");
                if (jsonStr != null) {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    String password = (String) jsonObj.get("Status");
                    if(password.equalsIgnoreCase("OK")){
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
}
