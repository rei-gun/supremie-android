package com.cashlez.android.garuda.library.cashlezlib;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Taslim on 11/8/2016.
 */

public class SingleInputDialogFragment  extends DialogFragment implements TextView.OnEditorActionListener,
        View.OnClickListener {

    public static final String FRAGMENT_INPUT_TITLE = "FRAGMENT_INPUT_TITLE";
    public static final String FIRST_INPUT_TYPE = "FIRST_INPUT_TYPE";
    public static final String FIRST_MAX_LENGTH = "FIRST_MAX_LENGTH";
    public static final String FIRST_INPUT_HINT = "FIRST_INPUT_HINT";
    private EditText firstInput;
    private Button btnDismiss;
    private Button btnOk;
    private int firstInputType;
    private int fistMaxLength;


    public SingleInputDialogFragment() {
    }

    public static SingleInputDialogFragment newInstance(String title, int firstInputType, String firstHint,
                                                        int firstMaxLength) {
        SingleInputDialogFragment frag = new SingleInputDialogFragment();
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
        View inflated = inflater.inflate(R.layout.single_input_fragment, container);
        String title = getArguments().getString(FRAGMENT_INPUT_TITLE);
        getDialog().setTitle(title);
        setCancelable(false);
        return inflated;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firstInput = (EditText) view.findViewById(R.id.first_input);

        firstInputType = getArguments().getInt(FIRST_INPUT_TYPE);
        fistMaxLength = getArguments().getInt(FIRST_MAX_LENGTH);

        firstInput.setInputType(firstInputType);
        firstInput.setSelection(firstInput.getText().length());
        firstInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(fistMaxLength)});
        firstInput.requestFocus();

        btnDismiss = (Button) view.findViewById(R.id.dialog_cancel);
        btnOk = (Button) view.findViewById(R.id.dialog_ok);

        btnDismiss.setOnClickListener(this);
        btnOk.setOnClickListener(this);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.dialog_cancel:
                dismiss();
                break;
            case R.id.dialog_ok:
                ISingleInputDialogListener activity = (ISingleInputDialogListener) getActivity();
                activity.onFinishEditDialog(firstInput.getText().toString());
                dismiss();
                break;
        }
    }
}
