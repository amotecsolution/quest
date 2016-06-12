package com.amotecsolution.quest;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.UUID;

/**
 * Created by iblue on 5/6/16.
 */
public class QuestEditFragment extends Fragment {

    private Quest mQuest;

    private EditText mTitleEditText;
    private CheckBox isSolved;
    private Button mAddButton;
    private Button mCancelButton;

    private final static String ARG_QUEST_ID = "quest_id";
    private final String TAG = "QuestEditFragment";

    public static Fragment newInstance (String questId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_QUEST_ID, questId);

        QuestEditFragment fragment = new QuestEditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String questId = (String)getArguments().getSerializable(ARG_QUEST_ID);
        mQuest = new Quest(questId);
        Log.i(TAG, "QuestId = " + mQuest.getQuestId());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_quest_edit_view, container, false);

        mTitleEditText = (EditText)v.findViewById(R.id.quest_title);
        mAddButton = (Button)v.findViewById(R.id.request_button);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Quest Added", Toast.LENGTH_SHORT).show();
                mQuest.setTitle(mTitleEditText.getText().toString());
                QuestLab.get(getActivity()).addQuest(mQuest);
                getActivity().onBackPressed();
            }
        });

        mCancelButton = (Button)v.findViewById(R.id.cancel_button);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuestLab.get(getActivity()).getQuests();
                getActivity().onBackPressed();
            }
        });

        return v;
    }
}
