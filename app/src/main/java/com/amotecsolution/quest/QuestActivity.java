package com.amotecsolution.quest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.UUID;

public class QuestActivity extends SingleFragmentActivity {

    private static final String EXTRA_QUEST_ID = "com.amotecsolution.android.quest_id";
    private static final String EXTRA_FUNC_BUTTON = "com.amoutecsolution.quest.funciton_button";

    public static Intent newIntent(Context packageContext, UUID questId, boolean isFuncVisible) {
        Intent intent = new Intent(packageContext, QuestActivity.class);
        intent.putExtra(EXTRA_QUEST_ID, questId);
        intent.putExtra(EXTRA_FUNC_BUTTON, isFuncVisible);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        //return new QuestFragment();
        UUID questId = (UUID)getIntent().getSerializableExtra(EXTRA_QUEST_ID);
        boolean isFuncVisible = (Boolean)getIntent().getSerializableExtra(EXTRA_FUNC_BUTTON);
        return QuestFragment.newInstance(questId, isFuncVisible);
    }
}
