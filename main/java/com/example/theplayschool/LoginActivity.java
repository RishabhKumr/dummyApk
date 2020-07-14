package com.example.theplayschool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.Login;
import com.facebook.login.LoginResult;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import android.content.SharedPreferences;

import java.io.File;
import java.io.FileOutputStream;
import static com.google.android.gms.auth.api.signin.GoogleSignInOptions.DEFAULT_SIGN_IN;
public class LoginActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener   {

    private LinearLayout Prof_Section;
    private Button SignOut;
    private CardView box;
    private SignInButton SignIn;
    private TextView Name,Email;
    private ImageView Prof_Pic;
    public GoogleApiClient googleApiClient;
    private static final int REQ_CODE = 9001;
    private ImageView Cover;
    private TextView Text,versions,signup;
    private Button Launch,btn_signin,facebook_signin;
    private EditText get_Email,get_Pass;
    FirebaseAuth fAuth;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String user_name = "nameKey";
    public static final String user_email = "emailKey";
    SharedPreferences sharedpreferences;

    private String file = "UserName";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        setTitle("Let's Get Started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        fAuth = FirebaseAuth.getInstance();
        facebook_signin = (Button) findViewById(R.id.fb_login);
        versions = (TextView) findViewById(R.id.version);
        signup = (TextView) findViewById(R.id.createTxtBtn);
        btn_signin = (Button) findViewById(R.id.Sigin);
        get_Email = (EditText) findViewById(R.id.getEmail);
        get_Pass = (EditText) findViewById(R.id.getPassowrd);
        Prof_Section = (LinearLayout) findViewById(R.id.prof_section);
        SignOut = (Button) findViewById(R.id.bn_logout);
        SignIn = (SignInButton) findViewById(R.id.bn_login);
        Name = (TextView) findViewById(R.id.name);
        Email = (TextView) findViewById(R.id.email);
        Prof_Pic = (ImageView) findViewById(R.id.prof_pic);
        Cover = (ImageView) findViewById(R.id.cover);
        Text = (TextView) findViewById(R.id.text);
        Launch = (Button) findViewById(R.id.launch);
        SignIn.setOnClickListener(this);
        SignOut.setOnClickListener(this);
        Prof_Section.setVisibility(View.GONE);
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions).build();
        box = (CardView) findViewById(R.id.cardView);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Launch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent myIntent = new Intent(LoginActivity.this,
                       StudentInfoUpdate.class);
                startActivity(myIntent);
            }
        });
        //Firebase auth
        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = get_Email.getText().toString().trim();
                String Password = get_Pass.getText().toString().trim();

                if(TextUtils.isEmpty(Email))
                {
                    get_Email.setError("Please enter your Email!");
                    return;
                }
                if(TextUtils.isEmpty(Password))
                {
                    get_Pass.setError("Please enter your Password!");
                    return;
                }

                fAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(LoginActivity.this, "You are logged in!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Student_Dashboard.class));
                            get_Email.getText().clear();
                            get_Pass.getText().clear();
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, "[Email/Password Error!]" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Register_User.class));
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.bn_login:
                signIn();
                break;
            case R.id.bn_logout:
                signOut();
                break;
        }
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {

    }
    private void signIn()
    {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent,REQ_CODE);
    }
    private void signOut()
    {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                updateUI(false);
            }
        });
    }
    public void handleResult(GoogleSignInResult result)
    {
        if(result.isSuccess())
        {
            GoogleSignInAccount account = result.getSignInAccount();
            String name = account.getDisplayName();
            String email = account.getEmail();
            String img_url = String.valueOf(account.getPhotoUrl());
            Name.setText(name);
            Email.setText(email);
            Glide.with(this).load(img_url).into(Prof_Pic);
            updateUI(true);
            //Adding Into File
            try {

                FileOutputStream fout = openFileOutput(file, MODE_PRIVATE);
                fout.write(name.getBytes());
                fout.close();
                File fileDir = new File(getFilesDir(),file);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

        }
        else
        {
            updateUI(false);
        }
    }

    public void updateUI(boolean isLogin) {
        if (isLogin) {
            Prof_Section.setVisibility(View.VISIBLE);
            SignIn.setVisibility(View.GONE);
            Cover.setVisibility(View.GONE);
            Text.setVisibility(View.GONE);
            box.setVisibility(View.GONE);
            facebook_signin.setVisibility(View.GONE);
            versions.setVisibility(View.GONE);
            signup.setVisibility(View.GONE);
            btn_signin.setVisibility(View.GONE);
            get_Email.setVisibility(View.GONE);
            get_Pass.setVisibility(View.GONE);
        } else {
            Prof_Section.setVisibility(View.GONE);
            SignIn.setVisibility(View.VISIBLE);
            Cover.setVisibility(View.VISIBLE);
            Text.setVisibility(View.VISIBLE);
            box.setVisibility(View.VISIBLE);
            facebook_signin.setVisibility(View.VISIBLE);
            versions.setVisibility(View.VISIBLE);
            signup.setVisibility(View.VISIBLE);
            btn_signin.setVisibility(View.VISIBLE);
            get_Email.setVisibility(View.VISIBLE);
            get_Pass.setVisibility(View.VISIBLE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == REQ_CODE)
        {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);
            saveNameinFile(result);
        }
    }
    public void saveNameinFile(GoogleSignInResult result)
    {
        if(result.isSuccess())
        {
            GoogleSignInAccount account = result.getSignInAccount();
            String name = account.getDisplayName();
            //Adding Into File
            try {
                FileOutputStream fout = openFileOutput(file, MODE_PRIVATE);
                fout.write(name.getBytes());
                fout.close();
                File fileDir = new File(getFilesDir(),file);
                Toast.makeText(getBaseContext(),"File Saved at"+fileDir,Toast.LENGTH_LONG).show();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}