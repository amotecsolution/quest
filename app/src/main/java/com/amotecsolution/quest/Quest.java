package com.amotecsolution.quest;

import java.util.Date;
import java.util.UUID;

/**
 * Created by iblue on 13/3/16.
 */
public class Quest {

    private String questId;
    private String mTitle;
    //private Date mDate;
    private boolean isSolved;
    private String mFilePhotoName;

    public Quest() {
        this.questId = (UUID.randomUUID()).toString();
    }

    public Quest(String uuid) {
        this.questId = uuid;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    /*
    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }*/

    public boolean isSolved() {
        return isSolved;
    }

    public void setSolved(boolean isSolved) {
        this.isSolved = isSolved;
    }

    public String getPhotoFileName() {
        return "IMG_" + getQuestId().toString() + ".jpg";
    }

    public void setFilePhotoName(String filePhotoName) {
        mFilePhotoName = filePhotoName;
    }

    public String getQuestId() {
        return questId;
    }
}
