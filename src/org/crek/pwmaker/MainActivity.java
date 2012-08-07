/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.crek.pwmaker;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.app.Activity;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 *
 * @author damian
 */
public class MainActivity extends Activity {

    private EditText targetText;
    private Button generateButton;
    private Button pwd1;
	private Button pwd2;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        targetText = (EditText) findViewById(R.id.targetText);
        generateButton = (Button) findViewById(R.id.generateButton);
        pwd1 = (Button) findViewById(R.id.pwd1);
        pwd2 = (Button) findViewById(R.id.pwd2);

        generateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onGenerateClick();
            }
        });
        
        pwd1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	ClipboardManager clipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
                clipboard.setText(pwd1.getText());
            }
        });
        
        pwd2.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		ClipboardManager clipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
        		clipboard.setText(pwd2.getText());
        	}
        });
    }

    private void onGenerateClick() {
    	pwd1.setText(getMD5(targetText.getText().toString() + "<MASTER_PASSWORD_ONE>").substring(0, 8));
        pwd2.setText(getMD5(targetText.getText().toString() + "<MASTER_PASSWORD_TWO>").substring(0, 8));
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
}
