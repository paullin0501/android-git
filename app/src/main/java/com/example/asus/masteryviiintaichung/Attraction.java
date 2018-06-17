package com.example.asus.masteryviiintaichung;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Attraction extends AppCompatActivity {

    SQLiteDatabase db;
    DBOpenHelper openHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attraction);

        ImageView img = (ImageView)findViewById(R.id.imageView);
        TextView tv_name = (TextView)findViewById(R.id.tv_name);
        TextView tv_info = (TextView)findViewById(R.id.tv_info);
        Button btn_check = (Button)findViewById(R.id.btn_check);
        Button btn_map = (Button)findViewById(R.id.btn_map);
        Button btn_findhotel = (Button)findViewById(R.id.btn_find);

        final Intent intent = getIntent();
        final String name = intent.getStringExtra("NAME");
        String info = intent.getStringExtra("INFO");
        int imgID = intent.getIntExtra("ID", R.drawable.ic_launcher_background);
        final double Px = intent.getDoubleExtra("PX",24);
        final double Py = intent.getDoubleExtra("PY", 120);
        final float score = intent.getFloatExtra("SCORE", 0);

        img.setImageResource(imgID);
        tv_name.setText(name);
        tv_info.setText(info);

        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(Attraction.this, MapsActivity.class);
                intent.putExtra("PX", Px);
                intent.putExtra("PY", Py);
                startActivity(intent);
            }
        });
        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHelper = new DBOpenHelper(Attraction.this);
                db = openHelper.getWritableDatabase();

                db.execSQL("insert into his values('" + name + "')");

                Cursor cursor = db.rawQuery("select * from me;", null);
                cursor.moveToFirst();

                float level = cursor.getFloat(1);
                float newlevel = level + score;
                String stringlevel = String.valueOf(newlevel);
                if(level < 7) {
                    db.execSQL("update me set level=" + newlevel + " where level=" + level + ";");
                    Toast toast = Toast.makeText(Attraction.this, "Your level is " + stringlevel + " now !", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(Attraction.this, "Congratulation ! Your level is the Highest 7 !", Toast.LENGTH_SHORT);
                    toast.show();
                }
                db.close();
                finish();
            }
        });
        btn_findhotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(Attraction.this, HotelList.class);
                startActivity(intent);
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
                Intent t = getIntent();
                t.setClass(Attraction.this, User.class);
                startActivity(t);
                break;
            case R.id.mn_set:
                Toast toast1 = Toast.makeText(Attraction.this,
                        "工作分配:\n程式碼:葉昭甫\n" +
                                "創git team-project:林崇寶\n" +
                                "上台報告:詹亞豪", Toast.LENGTH_LONG);
                toast1.show();
                break;
        }
        return true;
    }
}
