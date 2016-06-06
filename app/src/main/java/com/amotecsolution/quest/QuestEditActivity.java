package com.amotecsolution.quest;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import java.util.UUID;

/**
 * Created by iblue on 5/6/16.
 */
public class QuestEditActivity extends SingleFragmentActivity {

    private static final String EXTRA_QUEST_ID = "com.amotecsolution.android.quest_id";
    private final String TAG = "QuestEditActivity";

    public static Intent newIntent(Context packageContext, UUID questId) {
        Intent intent = new Intent(packageContext, QuestEditActivity.class);
        intent.putExtra(EXTRA_QUEST_ID, questId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        //return new QuestEditFragment();

        UUID questId = (UUID)getIntent().getSerializableExtra(EXTRA_QUEST_ID);
        Log.d(TAG, "UUID = " + questId.toString());
        return QuestEditFragment.newInstance(questId);
    }
}
