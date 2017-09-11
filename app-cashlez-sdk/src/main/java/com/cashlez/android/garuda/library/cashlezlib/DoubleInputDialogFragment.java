package com.cashlez.android.garuda.library.cashlezlib;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Taslim_Hartmann on 9/24/2016.
 */

public class DoubleInputDialogFragment extends DialogFragment {

    public static final String FRAGMENT_INPUT_TITLE = "FRAGMENT_INPUT_TITLE";
    public static final String FIRST_INPUT_TYPE = "FIRST_INPUT_TYPE";
    public static final String FIRST_MAX_LENGTH = "FIRST_MAX_LENGTH";
    public static final String SECOND_INPUT_TYPE = "SECOND_INPUT_TYPE ";
    public static final String SECOND_MAX_LENGTH = "SECOND_MAX_LENGTH ";
    public static final String FIRST_INPUT_HINT = "FIRST_INPUT_HINT";
    public static final String SECOND_INPUT_HINT = "SECOND_INPUT_HINT";

    @BindView(R.id.first_input)
    EditText firstInput;
    @BindView(R.id.second_input)
    EditText secondInput;

    private int firstInputType;
    private int fistMaxLength;
    private int secondInputType;
    private int secondMaxLength;

    public DoubleInputDialogFragment() {
    }

    public static DoubleInputDialogFragment newInstance(String title, int firstInputType, String firstHint,
                                                        int firstMaxLength, int secondInputType,
                                                        String secondHint, int secondMaxLength) {
        DoubleInputDialogFragment frag = new DoubleInputDialogFragment();
        Bundle args = new Bundle();
        args.putString(FRAGMENT_INPUT_TITLE, title);
        args.putInt(FIRST_INPUT_TYPE, firstInputType);
        args.putString(FIRST_INPUT_HINT, firstHint);
        args.putInt(FIRST_MAX_LENGTH, firstMaxLength);
        args.putInt(SECOND_INPUT_TYPE, secondInputType);
        args.putString(SECOND_INPUT_HINT, secondHint);
        args.putInt(SECOND_MAX_LENGTH, secondMaxLength);
        frag.setArguments(args);
        return frag;
    }

    public static DoubleInputDialogFragment newInstance(String title, int firstInputType, String firstHint,
                                                        int firstMaxLength) {
        DoubleInputDialogFragment frag = new DoubleInputDialogFragment();
        Bundle args = new Bundle();
        args.putString(FRAGMENT_INPUT_TITLE, title);
        args.putInt(FIRST_INPUT_TYPE, firstInputType);
        args.putString(FIRST_INPUT_HINT, firstHint);
        args.putInt(FIRST_MAX_LENGTH, firstMaxLength);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflated = inflater.inflate(R.layout.double_input_fragment, container);
        ButterKnife.bind(this, inflated);
        String title = getArguments().getString(FRAGMENT_INPUT_TITLE);
        getDialog().setTitle(title);
        setCancelable(false);

        return inflated;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firstInputType = getArguments().getInt(FIRST_INPUT_TYPE);
        fistMaxLength = getArguments().getInt(FIRST_MAX_LENGTH);
        secondInputType = getArguments().getInt(SECOND_INPUT_TYPE);
        secondMaxLength = getArguments().getInt(SECOND_MAX_LENGTH);

        firstInput.setInputType(firstInputType);
        firstInput.setSelection(firstInput.getText().length());
        firstInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(fistMaxLength)});
        firstInput.requestFocus();

        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

//    @OnEditorAction(R.id.double_input_fragment_container)
//    protected boolean dialogEditorAction(int id) {
//        return false;
//    }

    @OnClick(R.id.dialog_ok)
    protected void dialogOk() {
        IInputDialogListener activity = (IInputDialogListener) getActivity();
        activity.onFinishEditDialog(firstInput.getText().toString(), secondInput.getText().toString());
        dismiss();
    }

    @OnClick(R.id.dialog_cancel)
    protected void dialogCancel() {
        dismiss();
    }
}
