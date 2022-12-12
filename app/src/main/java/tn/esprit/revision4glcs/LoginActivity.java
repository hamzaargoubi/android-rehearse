package tn.esprit.revision4glcs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import tn.esprit.revision4glcs.database.AppDataBase;
import tn.esprit.revision4glcs.models.User;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail, edtPassword;
    private CheckBox cbRemember;
    private Button btnLogin, btnSignup;
    private User tempUser = null;
    private SharedPreferences mPref;

    public static final String sharedPrefFileName = "tn.esprit.recapapp.file1";
    public static final String sharedPrefFileName1 = "tn.esprit.recapapp.file2";
    public static final String sharedPrefFileName2 = "tn.esprit.recapapp.file3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        cbRemember = findViewById(R.id.cbRemember);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignUp);
        mPref = getSharedPreferences(sharedPrefFileName, MODE_PRIVATE);

        btnSignup.setOnClickListener(view -> {

            Intent mainIntent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(mainIntent);

        });

        btnLogin.setOnClickListener(view -> {
            if (validate()){

                if (cbRemember.isChecked()){

                    Log.e("user signedIn", tempUser.toString());

                    SharedPreferences.Editor editor = mPref.edit();

                    editor.putString("FIRSTNAME", tempUser.getFirstName());
                    editor.putString("LASTNAME", tempUser.getLastName());
                    editor.putString("LOGIN", edtEmail.getText().toString());
                    editor.putString("MDP", edtPassword.getText().toString());
                    editor.putInt("PHONENUMBER", tempUser.getPhoneNumber());
                    editor.putBoolean("REMEMBERED", cbRemember.isChecked());

                    editor.apply();

                }else{
                    mPref.edit().clear().apply();
                }

                Intent mainIntent = new Intent(LoginActivity.this,
                        HomeActivity.class);
                startActivity(mainIntent);
            }
        });
    }

    private boolean validate(){

        if (edtEmail.getText().toString().isEmpty()){
            Toast.makeText(this, "Empty Fields !", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (edtPassword.getText().toString().isEmpty()){
            Toast.makeText(this, "Empty Fields !", Toast.LENGTH_SHORT).show();
            return false;
        }

//        if (!edtUserName.getText().toString().equals("admin") && edtPassword.getText().toString().equals("admin")){
//            Toast.makeText(this, "Wrong Credentials !", Toast.LENGTH_SHORT).show();
//            return false;
//        }
        tempUser = AppDataBase.getInstance(this).userDao()
                .findUserByLoginPassword(edtEmail.getText().toString(), edtPassword.getText().toString());

        if (tempUser == null){
            Toast.makeText(this, "User Not Found !", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}