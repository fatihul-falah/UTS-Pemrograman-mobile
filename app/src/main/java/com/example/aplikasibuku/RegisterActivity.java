package com.example.aplikasibuku;

import static com.example.aplikasibuku.LoginActivity.fromHtml;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
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

public class RegisterActivity extends AppCompatActivity {

    TextView tvLoginHere;
    EditText etRegisterEmail, etRegisterName, etRegisterPassword;
    Button btnRegister;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper = new DBHelper(this);

        etRegisterEmail = (EditText)findViewById(R.id.etRegisterEmail);
        etRegisterPassword = (EditText)findViewById(R.id.etRegisterPassword);
        etRegisterName = (EditText)findViewById(R.id.etRegisterName);
        btnRegister = (Button)findViewById(R.id.btnRegister);

        tvLoginHere = (TextView)findViewById(R.id.tvLoginHere);

        tvLoginHere.setText(fromHtml("Already Have Account? " +
                "</font><font color='#3b5998'>Login Here</font>"));

        tvLoginHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String email = etRegisterEmail.getText().toString().trim();
                String username = etRegisterName.getText().toString().trim();
                String password = etRegisterPassword.getText().toString().trim();

                ContentValues values = new ContentValues();


                if (email.equals("")){
                    etRegisterEmail.setError("Email Tidak Boleh Kosong!");
                }
                if (username.equals("")){
                    etRegisterName.setError("Nama Tidak Boleh Kosong!");
                }
                if (password.equals("")){
                    etRegisterPassword.setError("Password Tidak Boleh Kosong!");
                }else if (etRegisterPassword.length() < 6){
                    etRegisterPassword.setError("Password Harus 6 Karakter atau Lebih!");
                }else {
                    values.put(DBHelper.row_Email, email);
                    values.put(DBHelper.row_username, username);
                    values.put(DBHelper.row_password, password);
                    dbHelper.insertData(values);

                    Toast.makeText(RegisterActivity.this, "Berhasil membuat akun, silahkan Login", Toast.LENGTH_SHORT).show();
                    finish();
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