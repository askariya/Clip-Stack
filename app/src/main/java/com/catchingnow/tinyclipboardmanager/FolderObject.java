package com.catchingnow.tinyclipboardmanager;

/**
 * Created by Jayson on 2016-03-29.
 */

import java.util.ArrayList;
import java.util.Date;


public class FolderObject {

    private ArrayList FolderContents;
    private String name;
    private Date creationDate;

    private int FIRST_POSITION = 0;

    public FolderObject(String fName, Date cDate) {
        name = fName;
        creationDate = cDate;
        FolderContents = new ArrayList();
    }

    public ArrayList getFolderContents() {
        return FolderContents;
    }

    public void setFolderContents(ArrayList folderContents) {
        FolderContents = folderContents;
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

    public void addFolderObject(FolderObject folder) { FolderContents.add(FIRST_POSITION, folder); }

    public void addClipObject(ClipObject clip) { FolderContents.add(FIRST_POSITION, clip); }


}

