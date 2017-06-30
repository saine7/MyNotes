package com.gayedesign.alagiesaine.mynotes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    String savedText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editText = (EditText) findViewById(R.id.multiLineText);

        if (savedInstanceState != null){
            savedText = savedInstanceState.getString("Notes");
            editText.setText(savedText);
        }

        String sharedPreferences = getPreferences(Context.MODE_PRIVATE).getString("Notes","Empty");
        if (!sharedPreferences.equals("Empty")){
            editText.setText(sharedPreferences);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("Notes",editText.getText().toString());
    }

    public  void sharedPreferences(){
        SharedPreferences.Editor sharedPreferences = getPreferences(Context.MODE_PRIVATE).edit();
        sharedPreferences.putString("Notes",editText.getText().toString());
        sharedPreferences.commit();
    }

    @Override
    protected void onStop() {
        super.onStop();
        sharedPreferences();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            Intent intent = new Intent(this,Settings.class);
            startActivityForResult(intent,1);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            updatePreference();
        }
    }

    private void updatePreference() {
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(this);
        boolean checked = preference.getBoolean("boldChecked",false);
        if (checked){
            editText.setTypeface(null,Typeface.BOLD);
        }else {
            editText.setTypeface(null,Typeface.NORMAL);
        }

        SharedPreferences getSize = PreferenceManager.getDefaultSharedPreferences(this);
        String size = getSize.getString("textSize","16");
        Float f =  Float.parseFloat(size);
        editText.setTextSize(f);


    }
}
