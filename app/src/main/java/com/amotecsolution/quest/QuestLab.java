package com.amotecsolution.quest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;
import com.amotecsolution.quest.QuestDbSchema.QuestTable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by iblue on 13/3/16.
 */
public class QuestLab {
    private static QuestLab sQuestLab; //Make QuestLab object to static
    //private List<Quest> mQuests; //Make a list within the QuestLab
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private static final String TAG = "QuestLab";


    //Getting QuestLab object
    public static QuestLab get(Context context) {
        if (sQuestLab == null) {
            sQuestLab = new QuestLab(context);
        }
        return sQuestLab;
    }

    //Passing context to Constructor to create QuestList
    private QuestLab(Context context) {

        /* for list creation
        mQuests = new ArrayList<>();
        for (int i = 1; i<100; i++) {
            Quest quest = new Quest();
            quest.setTitle("Quest #" + i);
            mQuests.add(quest);
        } */


        mContext = context.getApplicationContext();
        mDatabase = new QuestBaseHelper(mContext).getWritableDatabase();

        /*
        Quest q1 = new Quest();
        q1.setTitle("Quest1");
        q1.setSolved(true);

        Quest q2 = new Quest();
        q2.setTitle("Quest2");
        q2.setSolved(false);

        Quest q3 = new Quest();
        q3.setTitle("Quest3");
        q3.setSolved(true);

        this.addQuest(q1);
        this.addQuest(q2);
        this.addQuest(q3);
        */

        /*
        mQuests = new ArrayList<>();
        mQuests.add(q1);
        mQuests.add(q2);
        mQuests.add(q3);
        */


    }

    //Getting EngineList by EngineLab object (*for list operation only)
    public List<Quest> getQuests() {
        //return mQuests; //For list operation

        List<Quest> quests = new ArrayList<>();
        QuestCursorWrapper cursor = queryQuests(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                quests.add(cursor.getQuest());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();;
        }

        return quests;
    }

    //Getting Quest by passing UUID to QuestLab object
    public Quest getQuest(UUID id) {

        /* For list operation
        for (Quest quest:mQuests) {
            if (quest.getQuestId().equals(id)) {
                return quest;
            }
        }
        return null;
        */

        QuestCursorWrapper cursor = queryQuests(QuestTable.Cols.UUID + " = ?", new String[]{id.toString()});
        //Log.d(TAG, "quest ID = " + id.toString());

        try {
            if(cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            Quest quest = cursor.getQuest();
            //Log.d(TAG, "quest ID = " + quest.getQuestId().toString());
            return quest;
            //return cursor.getQuest();
        } finally {
            cursor.close();
        }
    }

    public File getPhotoFile(Quest quest) {
        File externalFileDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if(externalFileDir == null) {
            return null;
        }
        return new File(externalFileDir, quest.getPhotoFileName());
    }

    private static ContentValues getContentValues (Quest quest) {
        ContentValues values = new ContentValues();
        values.put(QuestTable.Cols.UUID, quest.getQuestId().toString());
        values.put(QuestTable.Cols.TITLE, quest.getTitle());
        //values.put(QuestTable.Cols.DATE, quest.getDate().getTime());
        values.put(QuestTable.Cols.SOLVED, quest.isSolved() ? 1:0);

        return values;
    }


    private QuestCursorWrapper queryQuests(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query (
                QuestTable.NAME,
                null, //Column
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return new QuestCursorWrapper(cursor);
    }

    public void addQuest(Quest quest) {

        ContentValues values = getContentValues(quest);
        mDatabase.insert(QuestTable.NAME, null, values);
    }


    public void updateQuest(Quest quest) {
        String uuidString = quest.getQuestId().toString();
        ContentValues values = getContentValues(quest);
        Log.d(TAG, "quest ID: " + uuidString);
        mDatabase.update(QuestTable.NAME, values, QuestTable.Cols.UUID + " = ?", new String[]{uuidString});
    }
}
