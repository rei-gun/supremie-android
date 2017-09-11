package com.cashlez.android.garuda.library.cashlezlib.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.cashlez.android.garuda.library.cashlezlib.BaseViewStateActivity;
import com.cashlez.android.garuda.library.cashlezlib.R;
import com.cashlez.android.garuda.library.cashlezlib.activation.ActivationActivity;
import com.cashlez.android.garuda.library.cashlezlib.changepin.ChangePinActivity;
import com.cashlez.android.garuda.library.cashlezlib.payment.landing.LandingActivity;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnEditorAction;

import static com.cashlez.android.garuda.library.cashlezlib.util.CommonUtil.displayInfo;

public class LoginActivity extends BaseViewStateActivity<LoginView, LoginPresenter, LoginViewState> implements LoginView {

    @BindView(R.id.login_user_name)
    EditText userName;
    @BindView(R.id.login_pin)
    EditText pin;
    @BindView(R.id.loadingView)
    ProgressBar loginProgress;
    @BindView(R.id.login_mode)
    SwitchCompat loginMode;
    @BindView(R.id.login_aggregator_id)
    EditText aggregatorId;
    @BindView(R.id.login_pin_input)
    TextInputLayout pinInput;
    @BindView(R.id.login_aggregator_input)
    TextInputLayout aggregatorInput;

    private final String serverPublicKey = "<RSAPublicKey>\n" +
            "\t<Modulus>wdrcLwl0A26rIr/HE7GQ2cpPZX49ezl781nDs5y+4QR6BdcMxuWuaUInbI3yBcitWX4O/ii21TPtXNpOTsNU7fmHK/Jna9cCnqgJPNANLkZK5mHz2feB/eYxJX9cCTY00e/Ot7L8mhsZepct0MUVZxwWu/ky3eHhAS7RyVwAJzehol+gx6T4rZechWQidAbSTkJMoCjPG1/bhoP+et+uTKE4Nxe1DLybW3wWii30RObh1EalbVfduVZJ/1wRyqbah+r1XuTRq7/l2DEUkdtaoGszouK4kC8zS0CivvF0DPT+0CcBLRLjUE2wdCtjZEhITwfcVhMfSNKpseGlF3N8qw==</Modulus>\n" +
            "\t<Exponent>AQAB</Exponent>\n" +
            "</RSAPublicKey>";

    private final String clientPrivateKey = "<RSAKeyValue>\n" +
            "\t<Modulus>xRLQqutpZhj84YIbGrvX91sJlWITxA51ADnpcEq6KVZGzSwMjmGx1hDzhGfNGbYRXPMbR+WL7XZVLC5Rt8ctShY/IOIRvkSrUL5siQo5TlSf1jm0DrAgPogvbxouAS48OSFzDAhAX2ZEu0fOo2EKswrAvoxWRPKLoswo19bYTV7QImwUqM3Cz4inCMnyeRAD2G51SDSX0v+bZXAma9Nfw2uLDJQylTVKJSOaHevYhOg8XsLQszgY01wLaERrVP0oUDJwSMP8gkZ70MwC8NfAwH+EdnIV16idvEpMJx2KSl0RKa7Ga0JooxwG92rxvjI1ZKvuyqq2tTn/S16pvmfDLQ==</Modulus>\n" +
            "\t<Exponent>AQAB</Exponent>\n" +
            "\t<D>RNfzySF/qkjkXirDKS2hnilRSbv/R6f9O1z/rViNe89F4HqY0ExUgYFecEyKm4a8vgm26ADKRuPlkQ+FQHv12EG5P9V5eUwnxIchByZpKnHpfD6gJdllCjMsztUIaSUrqJGEzRk805a4P+wvxWcrA6yn9Gi14lQQb4h+ZBgLa8ssW63EBHMLrGFIQdE205ut1wz99+U5dfKAXf6LRWINxf72I41fwXapvXBtO6nvfYo1V8WQfq6HmCBD1Xy9hLGLgI/lGnFixZD3KtbfRG5YdRky+aBdHqoBvR4IDnlevubz2G8qKODY2qnTUsSlwL4XTw8QawKKxJpjq02ol2zsiQ==</D>\n" +
            "\t<P>3UhusOKgQBWRZ83dVOPh0fCoTWGVXUzCyDth/eUj/VD4yrsV+GPfXh32t03mWZJIkQC63+qe/Y9OOMLEtLySrLUnDZUV3JSe6JyImuEhd+azWkKDOMmUeHqZu53UDljRdkExcCfBqRIfF2ePFl803+7AqDdgfC+zFjQagtz8uB8=</P>\n" +
            "\t<Q>4/4KAaOGLqqz8mAZW80bZJ5dxZ+xTk2oQfjYLhK8UgJOp4LPng+J/n/fJDQndCyYKui41hnEiMCZr/Z450QAp4yxjajC166izaSvtr7ZL0uFoM/coOgowrVhhG6GGWHzKgWYnUTJ7RUbSKc6vcSc8da6dgfigdPPaU6I94YiSzM=</Q>\n" +
            "\t<DP>GDL2n/N4sOlq6F40CTeOl4Xo8eVtDzH4zyrnUXvAjtBPFOSWx34sjD9cnrkvKrZ7pxfcV+ZxkqscU8rA9j71D1wUNEEMjf3WzvtnWQCrx0/8Zy+E6C3rRa2qqEDfUt5Vscf5XxmJ7TJlIgsaM8kfoCmc+ghsTchtnkz+ZTdDj+M=</DP>\n" +
            "\t<DQ>F5/wW4EdW2KW6OuqVQfo6cE7SEom7k0/vS5TAFsypnDUw6jbaK6Fhxiq/65j1Db2waOB27Wp1t3WTxSELLqwMqxyjZJKNl1DQ3noN1CJYsw5mZNQcl/8MUjoRPfK74Bl2RnhWZKSNf306M9jV9yywqCUi7x/bSKMoIXhzXxZu18=</DQ>\n" +
            "\t<InverseQ>uvd2bogXKkeWGxdHRyV2fpHNL+kpATuZx6ZTLMoYeCN9DRqKmB67DPCkBXZKAsck3J75L9fM0MT+TeR6P+/vu9OEQ8tV769q7oA15Yk4OleQgmbG8auaXQ4T2E8K9LKxTOHQXzYFtvRpGGZS8LvgvV8tQUhO6Jg/KEInXBQlA/8=</InverseQ>\n" +
            "</RSAKeyValue>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.login;
    }

    @OnClick(R.id.login_btn)
    protected void doLogin() {
        executeLogin();
    }

    private void executeLogin() {
        if (loginMode.isChecked()) {
            presenter.doLoginAggregator(new User(serverPublicKey, clientPrivateKey, userName.getText().toString().trim(),
                    aggregatorId.getText().toString().trim()));
        } else {
            presenter.doLogin(new User(userName.getText().toString().trim(), pin.getText().toString().trim()));
        }
    }

    @OnClick(R.id.login_forgot_pin)
    protected void doForgotPin() {
        Intent intent = new Intent(this, ChangePinActivity.class);
        startActivityForResult(intent, 1);
    }

    @OnCheckedChanged(R.id.login_mode)
    protected void checkBoxToogle(boolean isChecked) {
        if (isChecked) {
            loginMode.setText(getResources().getString(R.string.pinless));
            aggregatorInput.setVisibility(View.VISIBLE);
            pinInput.setVisibility(View.GONE);
        } else {
            loginMode.setText(getResources().getString(R.string.pin));
            aggregatorInput.setVisibility(View.GONE);
            pinInput.setVisibility(View.VISIBLE);
        }
    }

    @OnEditorAction(R.id.login_pin)
    protected boolean passwordEditorAction(int id) {
        if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_ACTION_NEXT) {
            executeLogin();
            return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //System.exit(0);
    }

    @NonNull
    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public void onShowLoading() {
        viewState.setStateShowLoading();
        showProgress(loginProgress, true);
    }

    @Override
    public void onHideLoading() {
        viewState.setStateHideLoading();
        showProgress(loginProgress, false);
    }

    @Override
    public void onStartActivation() {
        startActivity(new Intent(this, ActivationActivity.class));
    }

    @Override
    public void onLoginSuccess() {
        viewState.setStateHideLoading();
        Intent intent = new Intent(this, LandingActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLoginFailed(String failedMessage) {
        viewState.setStateLoginFailed(failedMessage);
        displayInfo(this, failedMessage);
    }

    @NonNull
    @Override
    public LoginViewState createViewState() {
        return new LoginViewState();
    }

    @Override
    public void onNewViewStateInstance() {
        new LoginViewState();
    }
}