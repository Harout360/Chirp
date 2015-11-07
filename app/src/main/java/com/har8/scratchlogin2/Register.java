package com.har8.scratchlogin2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class Register extends Activity {


    protected EditText mUsername;
    protected EditText mUserEmail;
    protected EditText mUserPassword;
    protected Button mButton;
    protected Button mBackToLog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //PARSE.COM call line
        Parse.initialize(this, "3ErDoBMVha14GaLpgCgUV1uyAJ1aRWRXq1EZPs3l", "WXxeBDznQMhigZhptheOnzODvY66fvDfUSgcDqZ7");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //initialize Register
        mUsername = (EditText)findViewById(R.id.usernameRegister);
        mUserEmail = (EditText)findViewById(R.id.emailRegister);
        mUserPassword = (EditText)findViewById(R.id.passwordRegister);
        mButton = (Button)findViewById(R.id.buttonRegister);
        mBackToLog = (Button)findViewById(R.id.backToLoginButton);


        //listen to register button

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //toast
                //Toast.makeText(Register.this, "hi", Toast.LENGTH_LONG).show();

                String username = mUsername.getText().toString().trim();
                String email = mUserEmail.getText().toString().trim();
                String password = mUserPassword.getText().toString().trim();



                ParseUser user = new ParseUser();
                user.setUsername(username);
                user.setPassword(password);
                user.setEmail(email);

                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null){
                            //signed up successfully
                            Toast.makeText(Register.this, "Welcome!", Toast.LENGTH_LONG).show();


                            //go HOMEPAGE
                            Intent takeUserHome = new Intent(Register.this, Homepage.class);
                            startActivity(takeUserHome);
                        }
                        else{
                            //there was an error with signup
                        }
                    }
                });



            }
        });


        //already has an account button

        mBackToLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent goCreateAccount = new Intent(Register.this, LoginAcitivity.class);
                startActivity(goCreateAccount);
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
