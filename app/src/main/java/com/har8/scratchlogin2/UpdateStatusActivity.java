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

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class UpdateStatusActivity extends Activity {

    protected EditText mStatusUpdate;
    protected Button mStatusUpdateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_status);

        Parse.initialize(this, "3ErDoBMVha14GaLpgCgUV1uyAJ1aRWRXq1EZPs3l", "WXxeBDznQMhigZhptheOnzODvY66fvDfUSgcDqZ7");


        //initialize stuff

        mStatusUpdate = (EditText)findViewById(R.id.updateStatusTextBox);
        mStatusUpdateBtn = (Button)findViewById(R.id.statusUpdateButton);

        //listen to the update button click
        mStatusUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newStatus = mStatusUpdate.getText().toString();

                if(newStatus.isEmpty()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(UpdateStatusActivity.this);
                    builder.setMessage("Status is empty!");
                    builder.setTitle("Oops!");
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

                else {

                    //save the status text to Parse
                    ParseObject statusObject = new ParseObject("Status");
                    statusObject.put("newStatus", newStatus);
                    statusObject.put("User", ParseUser.getCurrentUser().getUsername());
                    statusObject.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                //successfully stored status in Parse
                                Toast.makeText(UpdateStatusActivity.this, "Status Posted!", Toast.LENGTH_LONG).show();

                                //take to homepage
                                Intent takeUserHome = new Intent(UpdateStatusActivity.this, Homepage.class);
                                startActivity(takeUserHome);

                            } else {
                                //problem with status post
                                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateStatusActivity.this);
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
                }//END ELSE
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_update_status, menu);
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
