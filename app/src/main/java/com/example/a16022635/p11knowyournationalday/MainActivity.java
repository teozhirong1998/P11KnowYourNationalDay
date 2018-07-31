package com.example.a16022635.p11knowyournationalday;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    ArrayList<String> al;
    ArrayAdapter aa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.lv);
        al = new ArrayList<String>();
        al.add("Singapore National Day is on 9th August");
        al.add("Singapore is 54 years old");
        al.add("Theme is 'We Are Singapore'");

        aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al);
        lv.setAdapter(aa);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String accesscode = preferences.getString("accesscode", "");

        if(!accesscode.equals("738964")) {
            loginDialog();
        }

    }

    private void loginDialog() {
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout accesscode =
                (LinearLayout) inflater.inflate(R.layout.login, null);
        final EditText etAccessCode = (EditText) accesscode
                .findViewById(R.id.editTextAccessCode);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please Login")
                .setView(accesscode)
                .setCancelable(false)
                .setNegativeButton("No Access Code", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        Toast.makeText(getApplicationContext(), "You must be login to view the contents", Toast.LENGTH_LONG).show();
                    }
                })
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if(etAccessCode.getText().toString().equals("738964")) {
                            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("accesscode", "738964");
                            editor.commit();

                            Toast.makeText(getApplicationContext(), "Successfully logged in", Toast.LENGTH_LONG).show();
                        } else {
                            loginDialog();
                        }
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void quitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure?")
                .setCancelable(false)
                // Set text for the positive button and the corresponding
                //  OnClickListener when it is clicked
                .setPositiveButton("Quit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("accesscode", "");
                        editor.commit();
                    }
                })
                // Set text for the negative button and the corresponding
                //  OnClickListener when it is clicked
                .setNegativeButton("Not Really", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        // Create the AlertDialog object and return it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
    private void sendToFriendDialog() {
        String [] list = new String[] { "Email", "SMS"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select the way to enrich your friend")
                // Set the list of items easily by just supplying an
                //  array of the items
                .setItems(list, new DialogInterface.OnClickListener() {
                    // The parameter "which" is the item index
                    // clicked, starting from 0
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            Intent email = new Intent(Intent.ACTION_SEND);
                            email.putExtra(Intent.EXTRA_EMAIL, new String[]{"tzhironggg@gmail.com"});
                            email.putExtra(Intent.EXTRA_SUBJECT, "Know Your National Day");
                            String message = "";

                            for(int i = 0; i < al.size(); i++) {
                                message += al.get(i) + "\n";
                            }

                            email.putExtra(Intent.EXTRA_TEXT, message);
                            email.setType("message/rfc822");
                            startActivity(Intent.createChooser(email,
                                    "Choose an Email client :"));

//                            Toast.makeText(MainActivity.this, "You chose Email",
//                                    Toast.LENGTH_LONG).show();
                            LinearLayout main = (LinearLayout) findViewById(R.id.main);
                            Snackbar sb = Snackbar.make(main, "Email has been sent", Snackbar.LENGTH_LONG);
                            sb.show();
                        } else {

                            Uri uri = Uri.parse("smsto:");
                            Intent sms = new Intent(Intent.ACTION_SENDTO, uri);

                            String message = "Knowing Your National Day - \n\n";
                            for(int i = 0; i < al.size(); i++) {
                                message += i + ") " + al.get(i) + "\n";
                            }
                            sms.putExtra("sms_body", message);
                            startActivity(sms);
//                            Toast.makeText(MainActivity.this, "You chose SMS",
//                                    Toast.LENGTH_LONG).show();
                            LinearLayout main = (LinearLayout) findViewById(R.id.main);
                            Snackbar sb = Snackbar.make(main, "SMS has been sent", Snackbar.LENGTH_LONG);
                            sb.show();
                        }
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void quizDialog() {
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final LinearLayout quiz =
                (LinearLayout) inflater.inflate(R.layout.quiz, null);

        final RadioGroup rg1 = (RadioGroup) quiz.findViewById(R.id.rg1);
        final RadioGroup rg2 = (RadioGroup) quiz.findViewById(R.id.rg2);
        final RadioGroup rg3 = (RadioGroup) quiz.findViewById(R.id.rg3);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please Login")
                .setView(quiz)
                .setCancelable(false)
                .setNegativeButton("Don't Know Lah", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        int score = 0;

                        //First Question - Answer = No
                        if (rg1.getCheckedRadioButtonId() == R.id.rb2) {
                            score = score + 1;
                        }

                        //First Question - Answer = Yes
                        if(rg2.getCheckedRadioButtonId() == R.id.rb3) {
                            score = score + 1;
                        }

                        //First Question - Answer = Yes
                        if(rg3.getCheckedRadioButtonId() == R.id.rb5) {
                            score = score + 1;
                        }


                        LinearLayout main = (LinearLayout) findViewById(R.id.main);

                        Snackbar sb = Snackbar.make(main, "Score: " + score + "/3", Snackbar.LENGTH_LONG);
                        sb.show();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Tally against the respective action item clicked
        //  and implement the appropriate action
        if (item.getItemId() == R.id.itemSendToFriend) {
            sendToFriendDialog();
        } else if (item.getItemId() == R.id.itemQuiz) {
            quizDialog();
        } else if (item.getItemId() == R.id.itemQuit) {
            quitDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return true;
    }

}
