package com.damianfarina.pwm;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class PasswordFormActivity extends AppCompatActivity {
    private ListView passwordsListView;
    EditText keywordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        passwordsListView = (ListView) findViewById(R.id.passwords_list);
        keywordInput = (EditText) findViewById(R.id.keyword_input);

        Button generateButton = (Button) findViewById(R.id.button_generate);

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onGenerateButtonClick();
            }
        });

        passwordsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String password = (String) parent.getItemAtPosition(position);
                ClipboardManager clipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("PwM generated", password);
                clipboard.setPrimaryClip(clip);
            }
        });
    }


    private void onGenerateButtonClick() {
        List<String> passwordsList = new ArrayList<>();

        passwordsList.add(getMD5(keywordInput.getText().toString().trim() + "_MASTER1").substring(0, 8));
        passwordsList.add(getMD5(keywordInput.getText().toString().trim() + "_MASTER2").substring(0, 8));
        passwordsList.add(getMD5(keywordInput.getText().toString().trim() + "_MASTER3").substring(0, 8));
        passwordsList.add(getMD5(keywordInput.getText().toString().trim() + "_MASTER4").substring(0, 10));

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                passwordsList );

        passwordsListView.setAdapter(arrayAdapter);
    }

    private String getMD5(String s) {
        MessageDigest m = null;

        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        m.update(s.getBytes(), 0, s.length());
        String hash = new BigInteger(1, m.digest()).toString(16);

        return hash;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_password_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
