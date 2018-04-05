package com.example.pranav.whataeat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pranav.whataeat.Models.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Console;

public class SignInActivity extends AppCompatActivity {
    private static String TAG="SignInActivity";
    Button SignIn;
    String EmailText,PasswordText,FirstLogin="True";
    EditText EmailInputEditText,PasswordInputEditText;
    TextView RegisterTextView;
    TextView textView3;
    boolean EditTextEmptyCheck,EditTextStatus;
    // Creating Progress dialog.
    ProgressDialog progressDialog;
    // Creating FirebaseAuth object.
    FirebaseAuth firebaseAuth;
    Boolean isFirstRun;String userID;
    String FirstLoginDataBase,firstLoginDataBase;


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference user_data = database.getReference("User Data");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        SignIn=findViewById(R.id.SignInButton);
        EmailInputEditText=findViewById(R.id.EmailInputEditText);
        PasswordInputEditText=findViewById(R.id.PasswordInputEditText);
        RegisterTextView=findViewById(R.id.RegisterTextView);
        progressDialog=new ProgressDialog(SignInActivity.this);
        textView3=findViewById(R.id.textView3);
        firebaseAuth =FirebaseAuth.getInstance();


        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EmptyFieldCheck();
                if(EditTextEmptyCheck)
                {
                    // If  EditTextEmptyCheck == true then login function called.
                    LoginFunction();
                }
                else {
                    // If  EditTextEmptyCheck == false then toast display on screen.
                    Toast.makeText(SignInActivity.this, "Please Fill All the Fields", Toast.LENGTH_LONG).show();
                }
            }
        });
        RegisterTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SignInActivity.this,SIgnUpActivity.class);
                startActivity(intent);
            }
        });
    }
    public void EmptyFieldCheck() {
        // Getting value form Email's EditText and fill into EmailHolder string variable.
        EmailText = EmailInputEditText.getText().toString().trim();
        // Getting value form Password's EditText and fill into PasswordHolder string variable.
        PasswordText = PasswordInputEditText.getText().toString().trim();
        // Checking Both EditText is empty or not.
        if (TextUtils.isEmpty(EmailText) || TextUtils.isEmpty(PasswordText)) {
            // If any of EditText is empty then set value as false.
            EditTextEmptyCheck = false;
        } else {
            // If any of EditText is empty then set value as true.
            EditTextEmptyCheck = true;
        }
    }
    // Creating login function.
    public void LoginFunction(){
        // Setting up message in progressDialog.
        progressDialog.setMessage("Please Wait");
        // Showing progressDialog.
        progressDialog.show();
        // Calling  signInWithEmailAndPassword function with firebase object and passing EmailHolder and PasswordHolder inside it.
        firebaseAuth.signInWithEmailAndPassword(EmailText, PasswordText)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If task done Successful.
                        if(task.isSuccessful()){
                            // Hiding the progress dialog.
                            FirebaseUser currentFireBaseUser = firebaseAuth.getCurrentUser();
                            userID = currentFireBaseUser.getUid();
                            progressDialog.dismiss();
                            user_data.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                   Users users=new Users();
                                  users.setFirstloginDatabase(dataSnapshot.child(userID).getValue(Users.class).getFirstloginDatabase());
                                  Log.d(TAG,"showData:FirstLogin:"+users.getFirstloginDatabase());
                                  FirstLoginDataBase=users.getFirstloginDatabase();
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                            if(FirstLoginDataBase == "False")
                            {
                                Intent intent1=new Intent(SignInActivity.this,MenuActivity.class);
                                startActivity(intent1);

                            }
                            else
                            {

                                userID = currentFireBaseUser.getUid();
                                FirstLogin="False";
                                FirstLoginDataBase="False";
                                user_data.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        try
                                        {
                                            user_data.child(userID).child("firstLoginDataBase").setValue("False");
                                        }
                                        catch (Exception e)
                                        {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                Intent intent=new Intent(SignInActivity.this,MenuActivity.class);
                                startActivity(intent);
                            }

                            // Closing the current Login Activity.
                            finish();
                        }
                        else {
                            // Hiding the progress dialog.
                            progressDialog.dismiss();
                            // Showing toast message when email or password not found in Firebase Online database.
                            Toast.makeText(SignInActivity.this, "Email or Password Not found, Please Try Again", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }


}
