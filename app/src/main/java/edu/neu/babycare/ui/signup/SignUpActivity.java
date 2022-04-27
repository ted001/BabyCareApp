package edu.neu.babycare.ui.signup;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import edu.neu.babycare.MainActivity;
import edu.neu.babycare.MyApplication;
import edu.neu.babycare.R;
import edu.neu.babycare.model.Baby;
import edu.neu.babycare.model.User;
import edu.neu.babycare.ui.login.LoginActivity;
import edu.neu.babycare.ui.training.TrainingCenterActivity;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = SignUpActivity.class.getSimpleName();

    //firebaseDatabase
    private FirebaseDatabase mFirebaseDb;
    private DatabaseReference mDbUsersRef;
    private DatabaseReference mDbFamilyCodeRef;

    private EditText editTextUserName;
    private EditText editTextBabyName;
    private Spinner spinnerRelationship;
    private RadioGroup radioGroupGender;
    private DatePicker datePickerBirthday;
    private ProgressBar progressBar;
    private ConstraintLayout mBabyInfoLayout;
    private CheckBox familyCodeCb;
    private EditText familyCodeEt;

    private boolean isBoy = true;
    private boolean hasFamilyCode = false;
    private String babyBirthday;
    private String relationship;
    private String familyCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initView();
        // Connect with firebase
        mFirebaseDb = FirebaseDatabase.getInstance();
        mDbUsersRef = mFirebaseDb.getReference("Users");
        mDbFamilyCodeRef = mFirebaseDb.getReference("FamilyCodes");

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        babyBirthday =  year + "-" + month + "-" + day;
    }

    private void initView() {
        progressBar = findViewById(R.id.progressBar);
        editTextUserName = findViewById(R.id.nameEt);
        editTextBabyName = findViewById(R.id.babyNameEt);
        spinnerRelationship = findViewById(R.id.relationSpinner);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        datePickerBirthday = findViewById(R.id.datePicker);
        mBabyInfoLayout = findViewById(R.id.babyInfoLayout);
        familyCodeCb = findViewById(R.id.checkBox);
        familyCodeEt = findViewById(R.id.familyCodeEt);

        datePickerBirthday.setMaxDate(System.currentTimeMillis());

        radioGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.boy) {
                    isBoy = true;
                } else {
                    isBoy = false;
                }
            }
        });

        datePickerBirthday.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                babyBirthday = year + "-" + monthOfYear + "-" + dayOfMonth;

            }
        });


        spinnerRelationship.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                relationship = ((TextView) view).getText().toString();
                Log.d(TAG, "onItemSelected: " + relationship);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        familyCodeCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                hasFamilyCode = isChecked;
                if (isChecked) {
                    mBabyInfoLayout.setVisibility(View.INVISIBLE);
                } else {
                    mBabyInfoLayout.setVisibility(View.VISIBLE);
                }

            }
        });
        familyCodeCb.setChecked(true);
    }

    public void signUp(View view) {
        String userName = editTextUserName.getText().toString().trim();
        if (userName.isEmpty()) {
            editTextUserName.setError("Full name is required");
            editTextUserName.requestFocus();
            return;
        }

        if (hasFamilyCode) {
            familyCode = familyCodeEt.getText().toString().trim();
            if (userName.isEmpty()) {
                familyCodeEt.setError("Please input Family Code");
                familyCodeEt.requestFocus();
                return;
            } else {
                mDbFamilyCodeRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e(TAG, "Error getting data", task.getException());
                        } else {
                            DataSnapshot snapshot = task.getResult();
                            for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                String code = postSnapshot.getKey();
                                if (familyCode.equals(code)) {
                                    //Baby baby = postSnapshot.getValue(Baby.class);
                                    User user = new User(userName, relationship, familyCode);
                                    mDbUsersRef.child(user.getUserName()).setValue(user);
                                    //mDbFamilyCodeRef.child("1111").child(userName).setValue(userName);
                                    //User user = new User(userName, babyName, relationship, babyBirthday);
                                    //Task t = mDbUsersRef.child(user.getUserName()).setValue(user);
                                    startActivity(new Intent(SignUpActivity.this, TrainingCenterActivity.class));
                                    SignUpActivity.this.finish();
                                    return;
                                }
                            }
                            familyCodeEt.setError("Family Code does not exist, please try again");
                            familyCodeEt.requestFocus();
                        }
                    }
                });
            }


        } else {
            String babyName = editTextBabyName.getText().toString().trim();

            if (babyName.isEmpty()) {
                editTextBabyName.setError("Full name is required");
                editTextBabyName.requestFocus();
                return;
            }

            progressBar.setVisibility(View.VISIBLE);

            Baby baby = new Baby(babyName, babyBirthday, isBoy ? "Boy" : "Girl");
            User user = new User(userName, relationship, "1111");

            Task t = mDbUsersRef.child(user.getUserName()).setValue(user);

            t.addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(),
                                "Unable to login, Please check the network connection", Toast.LENGTH_SHORT).show();
                    } else {
                        //mDbFamilyCodeRef.child("1111").child(userName).setValue(userName);
                        mDbFamilyCodeRef.child("1111").child(babyName).setValue(baby);
                        MyApplication.getInstance().setLoginUserName(userName);
                        Intent intent = new Intent();
                        intent.setClass(SignUpActivity.this, TrainingCenterActivity.class);
                        startActivity(intent);
                        SignUpActivity.this.finish();
                    }
                    progressBar.setVisibility(View.GONE);
                }
            });
        }

    }
}
