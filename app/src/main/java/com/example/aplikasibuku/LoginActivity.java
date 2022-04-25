package com.example.aplikasibuku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnLogin;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = (EditText)findViewById(R.id.etEmail);
        etPassword = (EditText)findViewById(R.id.etPassword);
        btnLogin = (Button)findViewById(R.id.btnLogin);

        dbHelper = new DBHelper(this);

        TextView tvCreateAccount = (TextView)findViewById(R.id.tvCreateAccount);

        tvCreateAccount.setText(fromHtml("Belum punya akun?. " +
                "</font><font color='#3b5998'>Buat disini!</font>"));
        tvCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                Boolean res = dbHelper.checkUser(email,password);
                Boolean resEmail = dbHelper.checkemail(email);
                Boolean resPasswd = dbHelper.checkPasswd(password);
                if(res == true){
                    Toast.makeText(LoginActivity.this, "Login Berhasil!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, ListActivity.class));
                }else {
                    if (etEmail.getText().toString().isEmpty()) {
                        etEmail.setError("Email Tidak Boleh Kosong!");
                    }else if(resEmail == false){
                        etEmail.setError("Harap Masukkan Email Yang Valid!");
                    }
                    if (etPassword.getText().toString().isEmpty()) {
                        etPassword.setError("Password Tidak Boleh Kosong!");
                    } else if (etPassword.length() < 6) {
                        etPassword.setError("Password Harus 6 Karakter atau Lebih!");
                    } else if(resPasswd == false) {
                        etPassword.setError("Password Salah");
                    } else {
                        Toast.makeText(LoginActivity.this, "Login gagal, Silahkan coba kembali!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public static Spanned fromHtml(String html){
        Spanned result;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        }else {
            result = Html.fromHtml(html);
        }
        return result;
    }
}