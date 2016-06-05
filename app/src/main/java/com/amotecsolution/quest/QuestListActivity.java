package com.amotecsolution.quest;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;

/**
 * Created by iblue on 13/3/16.
 */
public class QuestListActivity extends SingleFragmentActivity implements QuestListFragment.Callbacks, QuestFragment.Callbacks{

    private static final String FUNCBUTTON = "function_button";

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected Fragment createFragment() {
        return new QuestListFragment();
    }

    //retturn id of activity_masterdetail
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }

    @Override
    public void onQuestSelected(Quest quest, Boolean isVisible) {

        if(findViewById(R.id.detail_fragment_container) == null) {
            Intent intent = QuestPagerActivity.newIntent(this, quest.getQuestId(), isVisible);
            startActivity(intent);
        } else {
            Fragment newDetail = QuestFragment.newInstance(quest.getQuestId(), isVisible);
            getSupportFragmentManager().beginTransaction().replace(R.id.detail_fragment_container, newDetail).commit();
        }
    }

    @Override
    public void onQuestUpdated(Quest quest) {
        QuestListFragment listFragment = (QuestListFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        listFragment.updateUI();
    }
}
