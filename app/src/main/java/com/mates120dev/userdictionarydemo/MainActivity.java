package com.mates120dev.userdictionarydemo;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.UserDictionary;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    TextView textView;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        editText = (EditText) findViewById(R.id.editText);
        ButtonClickListener buttonClickListener = new ButtonClickListener();
        findViewById(R.id.buttonAdd).setOnClickListener(buttonClickListener);
        findViewById(R.id.buttonRequest).setOnClickListener(buttonClickListener);
    }

    class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.buttonAdd) {
                UserDictionary.Words.addWord(MainActivity.this, editText.getText().toString(), 255, UserDictionary.Words.LOCALE_TYPE_ALL);
            }
            if (v.getId() == R.id.buttonRequest) {
                textView.setText("");
                Uri dic = UserDictionary.Words.CONTENT_URI;
                ContentResolver resolver = getContentResolver();
                String selection = null;
                String []selectionArgs = null;
                Cursor cursor = resolver.query(dic, null, selection, selectionArgs, null);
                while (cursor.moveToNext())
                {
                    String word = cursor.getString(cursor.getColumnIndex(UserDictionary.Words.WORD));
                    String frequency = cursor.getString(cursor.getColumnIndex(UserDictionary.Words.FREQUENCY));
                    String appId = cursor.getString(cursor.getColumnIndex(UserDictionary.Words.APP_ID));
                    String locale = cursor.getString(cursor.getColumnIndex(UserDictionary.Words.LOCALE));
                    textView.append(String.format("\n\n%s\t-\t freq: %s, appuid: %s, locale: %s", word, frequency, appId, locale));
                }
                cursor.close();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.stub_menu, menu);
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
