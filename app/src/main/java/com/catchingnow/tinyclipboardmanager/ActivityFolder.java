package com.catchingnow.tinyclipboardmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
    private FolderObject currentFolder;

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

        String folderName = intent.getStringExtra("folderObjName");
        List<FolderObject> listOfFolders = database.getFolderHistory();

        //search in the database for the folder in question
        for(int i = 0; i < listOfFolders.size(); i++){
            if(listOfFolders.get(i).getName().equals(folderName))
                currentFolder = listOfFolders.get(i); //store the folder in the variable 'currentFolder'
        }

        /********************************************TESTING CODE**************************************/
        Log.v("TEST: ", currentFolder.getName()); //test that the correct folder was opened

        if(!currentFolder.getFolderContents().isEmpty()){


            ClipObject cpObj = (ClipObject)currentFolder.getFolderContents().get(0);
//
            if(cpObj != null)
                Log.v("TEST: ", cpObj.getText());
        }

        /**********************************************************************************************/

    }


    //TODO implement code that makes the add button work properly

    public void addButtonOnClick(View view){
        //TODO call modifyFolder for testing purposes
        //TODO in actual practice: call ActivityEditor and add the clip object to the database then
        Toast.makeText(ActivityFolder.this,
                "Add button has been clicked!", Toast.LENGTH_SHORT).show();

        /**************************************TESTING CODE ******************************************/
        ArrayList<ClipObject>clipArray = new ArrayList<ClipObject>();
        clipArray.add(new ClipObject("eyyy", new Date()));
        clipArray.add(new ClipObject("nah", new Date()));
        clipArray.add(new ClipObject("meow", new Date()));

        database.modifyFolder(currentFolder.getName(), currentFolder.getName(), clipArray);
        /**********************************************************************************************/
    }


}
