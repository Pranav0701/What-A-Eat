package com.example.pranav.whataeat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pranav.whataeat.Models.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    String FirstName, LastName, City, MobileNumber, Address, EmailId;
    EditText FirstNameEditText, LastNameEDitText, CityEditText, MobileNumberEditText, AddressEditText, EmailIdEditText;
    Boolean isFormFilled;
    Button SaveDetails, Proceed;
    FirebaseUser currentFireBaseUser = FirebaseAuth.getInstance().getCurrentUser();
    String userID = currentFireBaseUser.getUid();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference user_data = database.getReference("User Data");
    // Creating Progress dialog.
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
/*


        FirstNameEditText=findViewById(R.id.FirstNameTextView);
        LastNameEDitText=findViewById(R.id.LastNameTextView);
        CityEditText=findViewById(R.id.CityTextView);
        progressDialog=new ProgressDialog(ProfileActivity.this);
        MobileNumberEditText=findViewById(R.id.MobilePhoneTextView);
        AddressEditText=findViewById(R.id.AddressEditText);
        EmailIdEditText=findViewById(R.id.EMailIdTextView);
        SaveDetails=findViewById(R.id.SaveDetailsButton);
        Proceed=findViewById(R.id.ProceedButton);
        CityEditText.setText(userID);

        SaveDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveData();
            }
        });
        Proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveData();
                Intent intent=new Intent(ProfileActivity.this,MenuActivity2.class);
                startActivity(intent);

            }
        });

    }
    public void isFormFilled(EditText FirstNameEditText,EditText LastNameEDitText,EditText CityEditText,EditText MobileNumberEditText,EditText AddressEditText,EditText EmailIdEditText)
    {
        FirstName=FirstNameEditText.getText().toString().trim();
        LastName=LastNameEDitText.getText().toString().trim();
        City=CityEditText.getText().toString().trim();
        MobileNumber=MobileNumberEditText.getText().toString().trim();
        Address=AddressEditText.getText().toString().trim();
        EmailId=EmailIdEditText.getText().toString().trim();

        if(TextUtils.isEmpty(FirstName) || TextUtils.isEmpty(LastName) || TextUtils.isEmpty(City) || TextUtils.isEmpty(MobileNumber) || TextUtils.isEmpty(Address) || TextUtils.isEmpty(EmailId))
        {
            isFormFilled=false;
        }
        else
        {
            isFormFilled=true;
        }

    }
    public void SaveData()
    {
        isFormFilled(FirstNameEditText,LastNameEDitText,CityEditText,MobileNumberEditText,AddressEditText,EmailIdEditText);
        if(isFormFilled)
        {
            // Showing progress dialog at user registration time.
            progressDialog.setMessage("Please Wait, We are Registering Your Data on Server");
            progressDialog.show();
            user_data.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Users users =new Users(FirstName,LastName,City,MobileNumber,Address,EmailId);
                    user_data.child(userID).setValue(users);
                    progressDialog.dismiss();

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else
        {
            Toast.makeText(ProfileActivity.this,"Please Enter All Details!!!", Toast.LENGTH_LONG).show();
        }

    }

    }






        //
*/


    }
}
