package course.android.android_lesson6;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private String[] gsm_arena_urls_from_strings_xml;
    private String[] phones_from_strings_xml;
    private ListView lv;
    private Button button;
    private Button simpleDialogButton;
    final Context context = this;
    private MediaPlayer mediaPlayer;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        lv = (ListView) findViewById(R.id.languages);

        initResources();
        initLanguagesListView();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {
                openPhoneDisplayActivity(pos);
            }
        });


        button = (Button) findViewById(R.id.buttonShowCustomDialog);
        simpleDialogButton = (Button) findViewById(R.id.simpleDialog);
        simpleDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set title
                alertDialogBuilder.setTitle("Are you a quitter?");

                // set dialog message
                alertDialogBuilder
                        .setMessage("Click yes to exit!")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, close
                                // current activity
                                MainActivity.this.finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });

        // add button listener
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // custom dialog
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.custom);
                dialog.setTitle("Would you like to exit the application?");

                // set the custom dialog components - text, image and button
                TextView text = (TextView) dialog.findViewById(R.id.text);
                text.setText("Would you like to close the app?");

                Button dialogButtonExitYes = (Button) dialog.findViewById(R.id.dialogButtonExitYes);
                // if button is clicked, close the custom dialog
                dialogButtonExitYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                        System.exit(0);
                    }
                });

                Button dialogButtonExitNo = (Button) dialog.findViewById(R.id.dialogButtonExitNo);
                // if button is clicked, close the custom dialog
                dialogButtonExitNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
    }

    private void openPhoneDisplayActivity(int pos) {

        String url = gsm_arena_urls_from_strings_xml[pos];
        Intent intent = new Intent(this, PhoneDisplayActivity.class);

        intent.putExtra("urlToPhoneDescription", url);
        startActivity(intent);
    }

    private void initResources() {
        Resources res = getResources();
        phones_from_strings_xml = res.getStringArray(R.array.phones);
        gsm_arena_urls_from_strings_xml = res.getStringArray(R.array.gsmarena_urls);
    }

    private void initLanguagesListView() {
        lv.setAdapter(new ArrayAdapter<>(getBaseContext(),
                android.R.layout.simple_list_item_1, phones_from_strings_xml));
    }

    public void playSound(View view) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(this, R.raw.staying_alive);
        mediaPlayer.start();
    }

    public void playingStayingAlive() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(this, R.raw.staying_alive);
        mediaPlayer.start();
    }


    public void playingSchoolsOut() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(this, R.raw.schools_out);
        mediaPlayer.start();
    }
    public void showSongsDialog(View view) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity.this);
        builderSingle.setTitle("Select one song to play:-");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("Staying Alive");
        arrayAdapter.add("School's Out");


        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                AlertDialog.Builder builderInner = new AlertDialog.Builder(MainActivity.this);
                builderInner.setMessage(strName);
                builderInner.setTitle("Now playing");
                if (strName.equals("Staying Alive")) {
                    playingStayingAlive();
                } else {
                    playingSchoolsOut();
                }
                builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });
                builderInner.setNegativeButton("Stop", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mediaPlayer.stop();

                    }
                });
                builderInner.show();
            }
        });
        builderSingle.show();
    }

}