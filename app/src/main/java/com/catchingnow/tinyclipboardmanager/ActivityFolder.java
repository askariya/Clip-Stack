package com.catchingnow.tinyclipboardmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by Jayson on 2016-03-29.
 */
public class ActivityFolder extends ActionBarActivity { //TODO maybe change to MyActionBarActivity

    /*
    TODO variables go here
    --> maybe Toolbar variable (from MyActionBarActivity)
     */
    private ImageButton addClipButton;
    private Storage database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_folder);
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        Toast.makeText(ActivityFolder.this,
                "Folder button has been clicked!", Toast.LENGTH_SHORT).show();

        addClipButton = (ImageButton) findViewById(R.id.main_fab);
        database = Storage.getInstance(this); //get the database

        //TODO display the contents of the folder here -- probably just copy/paste setView(), and clipCardAdapter class from activity main and just change db.getClipHistory to getFolderClips or something
        //TODO in order to display only the contents in the folder, add a getFolderClips() to storage

    }


}
