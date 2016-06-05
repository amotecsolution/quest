package com.amotecsolution.quest;

import android.widget.EditText;
import java.util.Date;
import java.util.UUID;

/**
 * Created by iblue on 13/3/16.
 */
public class Quest {

    private UUID questId;
    private String mTitle;
    private Date mDate;
    private boolean isSolved;

    public Quest() {
        this(UUID.randomUUID());
    }

    public Quest(UUID uuid) {
        questId = uuid;
        //mDate = new Date();
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public UUID getQuestId() {
        return questId;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return isSolved;
    }

    public void setSolved(boolean isSolved) {
        this.isSolved = isSolved;
    }

    public String getPhotoFileName() {
        return "IMG_" + getQuestId().toString() + ".jpg";
    }
}
