package com.amotecsolution.quest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by iblue on 13/3/16.
 */
public class QuestListFragment extends Fragment {

    private RecyclerView mQuestRecyclerView;
    private QuestAdapter mAdapter;
    private Callbacks mCallbacks;
    private boolean mSubtitleVisible = true;
    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";
    private static final String TAG = "QuestListFragment";

    public interface Callbacks {
        void onQuestSelected(Quest quest);
        void onQuestEdit();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallbacks = (Callbacks)activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    //Insert RecyclerView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_quest_list, container, false);
        mQuestRecyclerView = (RecyclerView)view.findViewById(R.id.quest_recycler_view);
        mQuestRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //return super.onCreateView(inflater, container, savedInstanceState);

        if(savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }

        updateUI();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_quest_list, menu);

        MenuItem subtitleItem = menu.findItem(R.id.menu_Item_show_subtitle);
        if (mSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_crime:
                //Quest quest = new Quest();
                //QuestLab.get(getActivity()).addQuest(quest);
                //updateUI();
                //Log.d(TAG, "UUID = " + quest.getQuestId().toString());
                mCallbacks.onQuestEdit();
                return true;

            case R.id.menu_Item_show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateSubtitle() {
        QuestLab questLab = QuestLab.get(getActivity());
        int questCount = questLab.getQuests().size();
        String subtitle = getResources().getQuantityString(R.plurals.subtitle_plural, questCount, questCount);

        if(!mSubtitleVisible) {
            subtitle = null;
        }

        AppCompatActivity activity = (AppCompatActivity)getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    public void updateUI() {
        List<Quest> quests = QuestLab.get(getActivity()).getQuests();
        if (mAdapter == null) {
            mAdapter = new QuestAdapter(quests);
            mQuestRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setQuests(quests); //Transfer Quests from QuestLab to quests(List)
            mAdapter.notifyDataSetChanged();
        }

        updateSubtitle();
    }

    //Embed the components into item view
    private class QuestHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mTitleTextView;
        //public TextView mDateTextView;
        private Quest mQuest;

        public QuestHolder(View itemView) {
            super(itemView);
            //mTitleTextView = (TextView)itemView;
            mTitleTextView = (TextView)itemView.findViewById(R.id.list_item_quest_title_text_view);
            //mDateTextView = (TextView)itemView.findViewById(R.id.list_item_date);
            itemView.setOnClickListener(this);
        }

        public void bindQuest(Quest quest) {
            mQuest = quest;
            mTitleTextView.setText(mQuest.getTitle());
            //mDateTextView.setText(DateFormat.getDateInstance().format(new Date()));
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "quest ID = " + mQuest.getQuestId());
            //Intent intent = new Intent(getActivity(),QuestActivity.class);
            //Intent intent = QuestPagerActivity.newIntent(getActivity(), mQuest.getQuestId());
            //startActivity(intent);
            mCallbacks.onQuestSelected(mQuest);
        }
    }

    public class QuestAdapter extends RecyclerView.Adapter<QuestHolder> {

        private List<Quest> mQuests; //Adpater inclides an QuestList

        //Create constructor by putting QuestList
        public QuestAdapter(List<Quest> quests) {
            mQuests = quests;
        }

        //Create view holder and inflate item view layout
        @Override
        public QuestHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_quest, parent, false);
            return new QuestHolder(view);
        }

        //Binding quest to holder
        @Override
        public void onBindViewHolder(QuestHolder holder, int position) {

            Quest quest = mQuests.get(position);
            //holder.mTitleTextView.setText(quest.getTitle());
            //super.onBindViewHolder(holder, position);
            holder.bindQuest(quest);
        }

        @Override
        public int getItemCount() {
            return mQuests.size();
        }

        //Refreshing model data
        public void setQuests(List<Quest> quests) {
            mQuests = quests;
        }
    }
}
