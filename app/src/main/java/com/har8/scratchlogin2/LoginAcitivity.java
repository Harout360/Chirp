package com.har8.scratchlogin2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseUser;


public class LoginAcitivity extends Activity {


    protected EditText mUsernameL;
    protected EditText mPasswordL;
    protected Button mLoginButton;
    protected Button mCreateAccountButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_acitivity);

        //initialize
        Parse.initialize(this, "3ErDoBMVha14GaLpgCgUV1uyAJ1aRWRXq1EZPs3l", "WXxeBDznQMhigZhptheOnzODvY66fvDfUSgcDqZ7");
        mUsernameL = (EditText)findViewById(R.id.usernameLogin);
        mPasswordL = (EditText)findViewById(R.id.passwordLogin);
        mLoginButton = (Button)findViewById(R.id.LoginButton);
        mCreateAccountButton = (Button)findViewById(R.id.createAccountLogin);


        //listen to when the mLoginButton is clicked...
        //login the user using parse sdk


        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the user inputs from the textboxes and convert to string
                String username = mUsernameL.getText().toString().trim();
                String password = mPasswordL.getText().toString().trim();
                ParseUser.logInInBackground(username, password, new LogInCallback() {
                    @Override
                    public void done(ParseUser parseUser, com.parse.ParseException e) {
                        if(e==null)
                        {
                            //successful login
                            Toast.makeText(LoginAcitivity.this, "Welcome Back!", Toast.LENGTH_LONG).show();

                            //Take user to homepage
                            Intent takeUserHome = new Intent(LoginAcitivity.this, Homepage.class);
                            startActivity(takeUserHome);
                        }
                        else{
                            //failed to login, there's an error
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginAcitivity.this);
                            builder.setMessage(e.getMessage());
                            builder.setTitle("Sorry!");
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //close dialog window
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                });
            }
        });

        mCreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent goCreateAccount = new Intent(LoginAcitivity.this, Register.class);
                startActivity(goCreateAccount);
            }
        });




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login_acitivity, menu);
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
