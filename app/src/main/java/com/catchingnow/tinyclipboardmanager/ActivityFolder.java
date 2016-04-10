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


    private ImageButton folderFAB;
    private Storage database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_folder);
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        Toast.makeText(ActivityFolder.this,
                "Folder button has been clicked!", Toast.LENGTH_SHORT).show();

        database = Storage.getInstance(this); //get the database

        //TODO display the contents of the folder here

    }


}
