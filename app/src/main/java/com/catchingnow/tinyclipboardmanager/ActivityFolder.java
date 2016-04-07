package com.catchingnow.tinyclipboardmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageButton;

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
        setContentView(R.layout.activity_editor);
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        database = Storage.getInstance(this); //get the database
    }


}
