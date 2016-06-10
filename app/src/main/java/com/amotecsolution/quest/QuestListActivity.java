package com.amotecsolution.quest;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

/**
 * Created by iblue on 13/3/16.
 */
public class QuestListActivity extends SingleFragmentActivity implements QuestListFragment.Callbacks, QuestFragment.Callbacks {

    private final String TAG = "QuestListActivity";

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected Fragment createFragment() {
        return new QuestListFragment();
    }

    //return id of activity_masterdetail
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }

    @Override
    public void onQuestSelected(Quest quest) {

        if(findViewById(R.id.detail_fragment_container) == null) {
            Log.d(TAG, "UUID = " + quest.getQuestId());
            Intent intent = QuestPagerActivity.newIntent(this, quest.getQuestId());
            startActivity(intent);
        } else {
            Fragment newDetail = QuestFragment.newInstance(quest.getQuestId());
            getSupportFragmentManager().beginTransaction().replace(R.id.detail_fragment_container, newDetail).commit();
        }
    }

    @Override
    public void onQuestUpdated(Quest quest) {
        QuestListFragment listFragment = (QuestListFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        listFragment.updateUI();
    }

    @Override
    public void onQuestEdit() {
        Quest quest = new Quest();
        if(findViewById(R.id.detail_fragment_container) == null) {
            Intent intent = QuestEditActivity.newIntent(this, quest.getQuestId());
            startActivity(intent);
        } else {
            Fragment newDetail = QuestEditFragment.newInstance(quest.getQuestId());
            getSupportFragmentManager().beginTransaction().replace(R.id.detail_fragment_container, newDetail).commit();
        }
    }
}
