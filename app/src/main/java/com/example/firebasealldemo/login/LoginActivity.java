package com.example.firebasealldemo.login;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firebasealldemo.MainActivity;
import com.example.firebasealldemo.R;
import com.example.firebasealldemo.mvp.MVPBaseActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class LoginActivity extends MVPBaseActivity<LoginContract.View, LoginPresenter> implements LoginContract.View, View.OnClickListener {

    private static final String TAG = LoginActivity.class.getName();
    private EditText et_account;
    private EditText et_pwd;
    private Button login;
    private Button regist;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;


    @Override
    public int getLayoutResId() {
        return R.layout.login_layout;
    }

    @Override
    public void initView() {
        et_account = (EditText) findViewById(R.id.editText);
        et_pwd = (EditText) findViewById(R.id.editText2);
        login = (Button) findViewById(R.id.login);
        regist = (Button) findViewById(R.id.regist);
    }

    @Override
    public void initListener() {
        login.setOnClickListener(this);
        regist.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.regist:
                submit(false);
                break;

            case R.id.login:
                submit(true);
                break;
        }
    }

    public void submit(boolean isLogin) {
        String account = et_account.getText().toString().toString();
        String pwd = et_pwd.getText().toString().toString();
        if (!TextUtils.isEmpty(pwd) && !TextUtils.isEmpty(pwd)) {
            if (isLogin) {
                loginAccount(account, pwd);
            } else {
                registCount(account, pwd);
            }
        } else {
            Toast.makeText(LoginActivity.this, "请检查账号和密码", Toast.LENGTH_SHORT);
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void registCount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "创建账号失败",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void loginAccount(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(LoginActivity.this, "登录失败",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
