package com.amotecsolution.quest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.amotecsolution.quest.QuestDbSchema.QuestTable;

/**
 * Created by iblue on 27/5/16.
 */
public class QuestBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "quest_db";

    public QuestBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + QuestTable.NAME + "(" +
        " _id integer primary key autoincrement, " +
        QuestTable.Cols.UUID  + ", " +
        QuestTable.Cols.TITLE + ", " +
        QuestTable.Cols.DATE + ", " + QuestTable.Cols.SOLVED + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
