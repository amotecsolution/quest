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

    public static Intent newIntent(Context packageContext, UUID questId) {
        Intent intent = new Intent(packageContext, QuestActivity.class);
        intent.putExtra(EXTRA_QUEST_ID, questId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        //return new QuestFragment();
        String questId = (String)getIntent().getSerializableExtra(EXTRA_QUEST_ID);
        return QuestFragment.newInstance(questId);
    }
}
