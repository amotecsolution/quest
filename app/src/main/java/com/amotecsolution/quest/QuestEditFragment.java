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

    //private Callbacks mCallbacks;
    private Quest mQuest;

    private EditText mTitleEditText;
    private CheckBox isSolved;
    private Button mAddButton;
    private Button mCancelButton;

    private final static String ARG_QUEST_ID = "quest_id";
    private final String TAG = "QuestEditFragment";

    public static Fragment newInstance(UUID questId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_QUEST_ID, questId);

        QuestEditFragment fragment = new QuestEditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /*
    public interface Callbacks {
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
    }*/


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID questId = (UUID)getArguments().getSerializable(ARG_QUEST_ID);
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
                Toast.makeText(getActivity(), "Add QUEST", Toast.LENGTH_SHORT).show();
            }
        });

        mCancelButton = (Button)v.findViewById(R.id.cancel_button);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return v;
    }
}
