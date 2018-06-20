package com.example.asus.masteryviiintaichung;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class User extends AppCompatActivity {

    EditText us_name;
    ImageView mastery;
    SQLiteDatabase db;
    DBOpenHelper openHelper;
    String oldname;
    float level;
    Button reset;
    ListView listHistory;
    ArrayList<String> history = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Intent intent = getIntent();
        String listname = intent.getStringExtra("NAME");

        us_name = (EditText)findViewById(R.id.us_name);
        mastery = (ImageView)findViewById(R.id.mastery);
        reset = (Button)findViewById(R.id.btn_reset);
        listHistory = (ListView)findViewById(R.id.history);

        openHelper = new DBOpenHelper(this);
        db = openHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from me;", null);
        cursor.moveToFirst();
        Cursor his = db.rawQuery("select * from his;", null);
        his.moveToFirst();

        for (int i = 0 ; i < his.getCount() ; i++) {
            String add = his.getString(0);
            history.add(add);
            his.moveToNext();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                history);
        listHistory.setAdapter(adapter);

        oldname = cursor.getString(0);
        level = cursor.getFloat(1);
        us_name.setText(oldname);
        db.close();
        if(level >= 1)   mastery.setImageResource(R.drawable.one);
        if(level >= 2)   mastery.setImageResource(R.drawable.two);
        if(level >= 3)   mastery.setImageResource(R.drawable.three);
        if(level >= 4)   mastery.setImageResource(R.drawable.four);
        if(level >= 5)   mastery.setImageResource(R.drawable.five);
        if(level >= 6)   mastery.setImageResource(R.drawable.six);
        if(level >= 7)   mastery.setImageResource(R.drawable.seven);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHelper = new DBOpenHelper(User.this);
                db = openHelper.getWritableDatabase();
                db.execSQL("update me set level=" + 1 + " where level=" + level + ";");
                db.execSQL("delete from his");
                Toast t = Toast.makeText(User.this, "Reset complete !", Toast.LENGTH_SHORT);
                t.show();
                db.close();
                finish();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mn_me:
                Toast toast = Toast.makeText(User.this, "Already in ME menu !", Toast.LENGTH_SHORT);
                toast.show();
                break;
            case R.id.mn_set:
                Toast toast1 = Toast.makeText(User.this, "545621354", Toast.LENGTH_LONG);
                toast1.show();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if(!us_name.getText().toString().equals(oldname)){
            openHelper = new DBOpenHelper(this);
            db = openHelper.getWritableDatabase();
            db.execSQL("update me set name='" + us_name.getText() + "' where name='" + oldname + "';");
            db.close();
        }
        finish();
    }
}
