package com.sas.uas_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText nimEdt,nameEdt;
    Button loginBtn;
    Context ctx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctx = this;
        nimEdt = findViewById(R.id.nim);
        nameEdt = findViewById(R.id.name);
        loginBtn = findViewById(R.id.login_btn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nim = nimEdt.getText().toString();
                String name = nameEdt.getText().toString();
                if(nim.equals(""))
                {
                    Toast.makeText(ctx,"Nim Must be Filled",Toast.LENGTH_SHORT).show();
                }
                else if(nim.length()!= 10)
                {
                    Toast.makeText(ctx,"Nim Must be 10 numbers",Toast.LENGTH_SHORT).show();
                }
                else if (name.equals(""))
                {
                    Toast.makeText(ctx,"Name Must be Filled (max 20 characters)",Toast.LENGTH_SHORT).show();
                }
                else{
                    SharedPreferences sp = getSharedPreferences("details",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("nim",nim);
                    editor.putString("name",name);
                    editor.apply();
                    Intent intent = new Intent(ctx,HomeActivity.class);
                    ctx.startActivity(intent);
                }
            }
        });
    }
}