package com.example.a84965.bookstoreadmin.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a84965.bookstoreadmin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    private static FirebaseAuth mAuthentication;
    private static DatabaseReference mDatabase;
    EditText txtEmail,txtMK;
    Button btnDangNhap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        callControls();
        clickButtonDangNhap();
    }

    private void clickButtonDangNhap(){
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidate();
            }
        });
    }

    private void callControls() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuthentication = FirebaseAuth.getInstance();
        txtEmail = findViewById(R.id.txtEmail);
        txtMK = findViewById(R.id.txtMK);
        btnDangNhap = findViewById(R.id.btnDangNhap);
    }



    private void checkValidate(){
        String email = txtEmail.getText().toString();
        String matKhau = txtMK.getText().toString();
        if(email.equals("") || matKhau.equals("")){
            Toast.makeText(LoginActivity.this, "Email và mật khẩu không được rỗng", Toast.LENGTH_SHORT).show();
        }else{
            mAuthentication.signInWithEmailAndPassword(email, matKhau)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                Intent intentManagement = new Intent(LoginActivity.this,ManagementActivity.class);
                                startActivity(intentManagement);
                            } else {
                                Intent intentManagement = new Intent(LoginActivity.this,ManagementActivity.class);
                                startActivity(intentManagement);
                                Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                            }

                            // ...
                        }
                    });
        }
    }
}
