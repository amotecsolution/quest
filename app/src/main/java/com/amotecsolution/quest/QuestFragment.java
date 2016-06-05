package com.amotecsolution.quest;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.security.AccessControlContext;
import java.util.UUID;

/**
 * A placeholder fragment containing a simple view.
 */
public class QuestFragment extends Fragment {

    private Quest mQuest;
    private EditText mTitleField;
    private static final String ARG_QUEST_ID = "quest_id";
    private static final String ARG_FUNC_BUTTON = " function_buttons";
    private ImageButton mPhotoButton;
    private ImageView mPhotoView;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;
    private File mPhotoFile;
    private Callbacks mCallbacks;
    private static final int REQUEST_PHOTO = 2;
    private View modifiyView;
    //private TextView mDetailTitle;

    public interface Callbacks {
        void onQuestUpdated(Quest quest);
    }

    public QuestFragment() {
    }

    public static QuestFragment newInstance(UUID questId, Boolean isFuncVisible) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_QUEST_ID, questId);
        args.putSerializable(ARG_FUNC_BUTTON, isFuncVisible);

        QuestFragment fragment = new QuestFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallbacks = (Callbacks)activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mQuest = new Quest();
        //UUID questId = (UUID)getActivity().getIntent().getSerializableExtra(QuestActivity.EXTRA_QUEST_ID);
        UUID questId = (UUID)getArguments().getSerializable(ARG_QUEST_ID);

        mQuest = QuestLab.get(getActivity()).getQuest(questId);
        mPhotoFile = QuestLab.get(getActivity()).getPhotoFile(mQuest);
    }

    @Override
    public void onPause() {
        super.onPause();

        QuestLab.get(getActivity()).updateQuest(mQuest);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_quest, container, false);

        modifiyView = v.findViewById(R.id.add_cancel_button_view);
        boolean isFuncVisible = (Boolean)getArguments().getSerializable(ARG_FUNC_BUTTON);
        if (isFuncVisible == false) {
            modifiyView.setVisibility(View.GONE);
        }

        mTitleField = (EditText) v.findViewById(R.id.quest_title);
        mTitleField.setText(mQuest.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mQuest.setTitle(charSequence.toString());
                updateQuest();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mPhotoButton = (ImageButton)v.findViewById(R.id.quest_camera);
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        PackageManager packageManager = getActivity().getPackageManager();
        boolean canTakePhoto = mPhotoFile != null && captureImage.resolveActivity(packageManager) != null;
        mPhotoButton.setEnabled(canTakePhoto);

        if(canTakePhoto) {
            Uri uri = Uri.fromFile(mPhotoFile);
            captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }

        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(captureImage, REQUEST_PHOTO);
            }
        });

        mPhotoView = (ImageView)v.findViewById(R.id.quest_photo);
        mDateButton = (Button)v.findViewById(R.id.quest_date);

        mSolvedCheckBox = (CheckBox)v.findViewById(R.id.quest_solved);
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mQuest.setSolved(b);
                updateQuest();
            }
        });

        updatePhotoView();

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK) {
            return;
        }

        if(requestCode == REQUEST_PHOTO) {
            updatePhotoView();
        }
    }

    private void updateQuest() {
        QuestLab.get(getActivity()).updateQuest(mQuest);
        mCallbacks.onQuestUpdated(mQuest);
    }

    public void updatePhotoView() {
        if(mPhotoFile == null || !mPhotoFile.exists()) {
            mPhotoView.setImageDrawable(null);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(mPhotoFile.getPath(), getActivity());
            mPhotoView.setImageBitmap(bitmap);
        }
    }
}
