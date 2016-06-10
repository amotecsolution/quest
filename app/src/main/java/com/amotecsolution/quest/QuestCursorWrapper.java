package com.amotecsolution.quest;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.util.Log;

import com.amotecsolution.quest.QuestDbSchema.QuestTable;
import java.util.Date;
import java.util.UUID;

/**
 * Created by iblue on 27/5/16.
 */
public class QuestCursorWrapper extends CursorWrapper{

    //private static final String TAG = "QuestCursorWrapper";

    public QuestCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Quest getQuest() {
        String uuidString = getString(getColumnIndex(QuestDbSchema.QuestTable.Cols.UUID));
        String title = getString(getColumnIndex(QuestTable.Cols.TITLE));
        long date = getLong(getColumnIndex(QuestTable.Cols.DATE));
        int isSolved = getInt(getColumnIndex(QuestTable.Cols.SOLVED));

        Quest quest = new Quest(uuidString);
        quest.setTitle(title);
        //quest.setDate(new Date(date));
        quest.setSolved(isSolved != 0);

        return quest;
    }
}
