package com.example.asus.masteryviiintaichung;

import android.content.Intent;
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
        String name = intent.getStringExtra("NAME");
        String addr = intent.getStringExtra("ADDR");
        String info = intent.getStringExtra("INFO");
        int imgID = intent.getIntExtra("ID", R.drawable.ic_launcher_background);
        final double Px = intent.getDoubleExtra("PX",24);
        final double Py = intent.getDoubleExtra("PY", 120);

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
                Toast toast = Toast.makeText(Attraction.this, "asdfasdfasdf", Toast.LENGTH_LONG);
                toast.show();
                break;
            case R.id.mn_set:
                Toast toast1 = Toast.makeText(Attraction.this, "545621354", Toast.LENGTH_LONG);
                toast1.show();
                break;
        }
        return true;
    }
}
