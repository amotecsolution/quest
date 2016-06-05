package com.amotecsolution.quest;

/**
 * Created by iblue on 27/5/16.
 */
public class QuestDbSchema {

    public static final class QuestTable {
        public static final String NAME = "quest";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String SOLVED = "solved";

        }
    }
}
