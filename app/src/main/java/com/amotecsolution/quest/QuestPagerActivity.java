package com.amotecsolution.quest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.UUID;
import java.util.List;

/**
 * Created by iblue on 25/5/16.
 */
public class QuestPagerActivity extends AppCompatActivity implements QuestFragment.Callbacks{

    private ViewPager mViewPager;
    private List<Quest> mQuests;
    private Quest mQuest;
    private static final String EXTRA_QUEST_ID = "com.amotecsolution.quest.quest_id";
    private final String TAG = "QuestPagerActivity";

    public static Intent newIntent(Context packageContext, UUID questId) {
        Intent intent = new Intent(packageContext, QuestPagerActivity.class);
        intent.putExtra(EXTRA_QUEST_ID, questId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_pager);

        UUID questId = (UUID)getIntent().getSerializableExtra(EXTRA_QUEST_ID);
        //mQuest = QuestLab.get(getActivity()).getQuest(questId);

        Log.d(TAG, "UUID = " + questId.toString());

        //FragmentStatePagerAdaptor will destroy the fragment when not used. But FragmentPagerAdaptor not.
        mViewPager = (ViewPager)findViewById(R.id.activity_quest_pager_view_pager);
        mQuests = QuestLab.get(this).getQuests();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Quest quest = mQuests.get(position);
                return QuestFragment.newInstance(quest.getQuestId());
            }

            @Override
            public int getCount() {
                return mQuests.size();
            }
        });

        //Setting initial pager item
        for (int i = 0; i < mQuests.size(); i++) {
            if(mQuests.get(i).getQuestId().equals(questId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

    @Override
    public void onQuestUpdated(Quest quest) {

    }
}
