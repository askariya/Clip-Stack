package com.catchingnow.tinyclipboardmanager;

/**
 * Created by Jayson on 2016-03-29.
 */

import java.util.ArrayList;
import java.util.Date;


public class FolderObject {

    private ArrayList folderContents;
    private String name;
    private Date creationDate;

    private int FIRST_POSITION = 0;

    public FolderObject(String fName, Date cDate) {
        name = fName;
        creationDate = cDate;
        folderContents = new ArrayList();
    }

    public FolderObject(String fName, Date cDate, ArrayList folderContents) {
        name = fName;
        creationDate = cDate;
        this.folderContents = folderContents;
    }

    public ArrayList getFolderContents() {
        return folderContents;
    }

    public void setFolderContents(ArrayList folderContents) {
        this.folderContents = folderContents;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void addFolderObject(FolderObject folder) { folderContents.add(FIRST_POSITION, folder); }

    public void addClipObject(ClipObject clip) { folderContents.add(FIRST_POSITION, clip); }


}

