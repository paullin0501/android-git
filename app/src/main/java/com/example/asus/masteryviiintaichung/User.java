package com.example.asus.masteryviiintaichung;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class User extends AppCompatActivity {

    EditText us_name;
    ImageView mastery;
    SQLiteDatabase db;
    DBOpenHelper openHelper;
    String oldname;
    int level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        us_name = (EditText)findViewById(R.id.us_name);
        mastery = (ImageView)findViewById(R.id.mastery);

        openHelper = new DBOpenHelper(this);
        db = openHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from me;", null);
        cursor.moveToFirst();

        oldname = cursor.getString(0);
        level = cursor.getInt(1);
        us_name.setText(oldname);
        db.close();
        if(level > 0)   mastery.setImageResource(R.drawable.i);
        if(level > 1)   mastery.setImageResource(R.drawable.ii);
        if(level > 2)   mastery.setImageResource(R.drawable.iii);
        if(level > 3)   mastery.setImageResource(R.drawable.iv);
        if(level > 4)   mastery.setImageResource(R.drawable.v);
        if(level > 5)   mastery.setImageResource(R.drawable.vi);
        if(level > 6)   mastery.setImageResource(R.drawable.vii);
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
