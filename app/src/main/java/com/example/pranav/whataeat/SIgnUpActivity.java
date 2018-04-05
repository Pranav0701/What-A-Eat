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

public class SIgnUpActivity extends AppCompatActivity {

    Button SignUp;
    String Email, Password,RPassword;
    EditText EmailEditText, PasswordEditText,RPasswordEditText;
    boolean EditTextEmptyCheck, EditTextStatus;
    // Creating Progress dialog.
    ProgressDialog progressDialog;
    // Creating FirebaseAuth object.
    FirebaseAuth firebaseAuth;
    Boolean isFirstRun;String userID;
    String FirstLoginDataBase;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference user_data = database.getReference("User Data");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        TextView LogInTextView = findViewById(R.id.LoginTextView);
        progressDialog = new ProgressDialog(SIgnUpActivity.this);
        SignUp = findViewById(R.id.SignUpButton);
        EmailEditText = findViewById(R.id.RegisterEmailInputEditText);
        PasswordEditText = findViewById(R.id.ConfirmPasswordInputEditText);
        RPasswordEditText = findViewById(R.id.RPasswordInputEditText);
        firebaseAuth = FirebaseAuth.getInstance();

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Calling method to check EditText is empty or no status.
                Email = EmailEditText.getText().toString().trim();
                Password = PasswordEditText.getText().toString().trim();
                RPassword = RPasswordEditText.getText().toString().trim();
                if(TextUtils.isEmpty(Email) || TextUtils.isEmpty(Password) || TextUtils.isEmpty(RPassword))
                {
                    EditTextStatus = false;
                }
                else {
                    EditTextStatus = true ;
                }
                // If EditText is true then this block with execute.
                if (EditTextStatus) {
                    if(Password.equals(RPassword))
                    {
                        // If EditText is not empty than UserRegistrationFunction method will call.
                        progressDialog.setMessage("Please Wait, We are Registering Your Data on Server");
                        progressDialog.show();
                        // Creating createUserWithEmailAndPassword method and pass email and password inside it.
                        firebaseAuth.createUserWithEmailAndPassword(Email, Password).
                                addOnCompleteListener(SIgnUpActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        // Checking if user is registered successfully.
                                        if(task.isSuccessful()){
                                            // If user registered successfully then show this toast message.
                                            Toast.makeText(SIgnUpActivity.this,"User Registration Successfully",Toast.LENGTH_LONG).show();
                                            FirstLoginDataBase="True";
                                            FirebaseUser currentFireBaseUser = firebaseAuth.getCurrentUser();
                                            userID = currentFireBaseUser.getUid();
                                            user_data.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    Users users =new Users(FirstLoginDataBase);

                                                    user_data.child(userID).setValue(users);
                                                    showData(dataSnapshot);
                                                }
                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });
                                            Intent in=new Intent(SIgnUpActivity.this,SignInActivity.class);
                                            startActivity(in);
                                        }else{
                                            // If something goes wrong.
                                            Toast.makeText(SIgnUpActivity.this,"Something Went Wrong.", Toast.LENGTH_LONG).show();
                                        }
                                        // Hiding the progress dialog after all task complete.
                                        progressDialog.dismiss();
                                    }
                                });
                    }
                    else
                    {
                        Toast.makeText(SIgnUpActivity.this,"Passwords Don't Match",Toast.LENGTH_LONG).show();
                    }

                }
                // If EditText is false then this block with execute.
                else {
                    Toast.makeText(SIgnUpActivity.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();
                }
            }
        });

        //What Happens When the SignIn Button is Clicked

      LogInTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(SIgnUpActivity.this, SignInActivity.class);
                startActivity(in);
            }
        });


// Creating UserRegistrationFunction
        
       
    }
    private void showData(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds :dataSnapshot.getChildren())
        {
            Users users1=dataSnapshot.getValue(Users.class);
            Log.i("USER",String.valueOf(users1));
        }
    }

}