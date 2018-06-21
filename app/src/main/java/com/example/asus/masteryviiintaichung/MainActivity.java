package com.example.asus.masteryviiintaichung;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<Place> places = new ArrayList<Place>();
    SQLiteDatabase db;
    DBOpenHelper openHelper;

    private  PlaceArrayAdapter lvadapter = null;
    List<Place> lsPlaces = new ArrayList<>();
    private static final int LIST_PLACE = 0;


    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LIST_PLACE: {
                    List<Place> Places = (List<Place>)msg.obj;
                    refreshPlacesList(Places);
                    break;
                }
            }
        }
    };

    private void refreshPlacesList(List<Place> Places) {
        lvadapter.clear();
        lvadapter.addAll(Places);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openHelper = new DBOpenHelper(this);
        db = openHelper.getWritableDatabase();
        db.execSQL("insert into me values('My name', 1.0)");
        db.close();


        lvadapter = new PlaceArrayAdapter(this, new ArrayList<Place>());
        ListView lv  = (ListView)findViewById(R.id.lv_place);
        lv.setAdapter(lvadapter);

        getPlacesFromFirebase();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = lsPlaces.get(position).getName();
                Bitmap imgid = lsPlaces.get(position).getImgID();
                double px = lsPlaces.get(position).getPx();
                double py = lsPlaces.get(position).getPy();
                String info = lsPlaces.get(position).getInfo();

                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Attraction.class);
                intent.putExtra("NAME", name);
                intent.putExtra("PX", px);
                intent.putExtra("PY", py);
                intent.putExtra("INFO", info);
                intent.putExtra("ID", imgid);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        return true;
    }

    class FirebaseThread extends Thread {

        private DataSnapshot dataSnapshot;

        public FirebaseThread(DataSnapshot dataSnapshot) {
            this.dataSnapshot = dataSnapshot;
        }

        @Override
        public void run() {
            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                DataSnapshot dsName = ds.child("LocName");
                DataSnapshot dsAdd = ds.child("LocAddr");
                DataSnapshot dsPx = ds.child("LocPx");
                DataSnapshot dsPy = ds.child("LocPy");
                DataSnapshot dsInfo = ds.child("Info");



                String Name = (String)dsName.getValue();
                String Add = (String)dsAdd.getValue();
                String Info = (String)dsInfo.getValue();

                double Px = Double.parseDouble((String)dsPx.getValue());
                double Py = Double.parseDouble((String)dsPy.getValue());

                DataSnapshot dsImg = ds.child("LocPic");
                String imgUrl = (String) dsImg.getValue();
                Bitmap placeImg = getImgBitmap(imgUrl);

                Place aPlace = new Place();
                aPlace.setName(Name);
                aPlace.setAddr(Add);
                aPlace.setInfo(Info);
                aPlace.setPx(Px);
                aPlace.setPy(Py);
                aPlace.setImgID(placeImg);

                lsPlaces.add(aPlace);
            }
            Message msg = new Message();
            msg.what = LIST_PLACE;
            msg.obj = lsPlaces;
            handler.sendMessage(msg);
        }
    }

    private void getPlacesFromFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                new MainActivity.FirebaseThread(dataSnapshot).start();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.v("PlaceList", databaseError.getMessage());
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mn_me:
                Intent t = new Intent();
                t.setClass(MainActivity.this, User.class);
                startActivity(t);
                break;
            case R.id.mn_set:
                AlertDialog.Builder ad = new AlertDialog.Builder(this);
                ad.setTitle("關於本程式");
                ad.setMessage("作者:\nD0588785  葉昭甫\nD0543074  詹亞豪\nD0542278  林崇寶");
                DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                };
                ad.setPositiveButton("確定",listener);
                ad.show();
                break;
        }
        return true;
    }

    private Bitmap getImgBitmap(String imgUrl) {
        try {
            URL url = new URL(imgUrl);
            Bitmap bm = BitmapFactory.decodeStream(url.openConnection()
                    .getInputStream());
            return bm;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void showMyLocation(View view) {
        startActivity(new Intent(getApplicationContext(),MapsActivity2.class));
    }

    public class PlaceArrayAdapter extends ArrayAdapter<Place> {
        Context context;
        public PlaceArrayAdapter(@NonNull Context context, ArrayList<Place> items) {
            super(context, 0, items);
            this.context = context;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(context);
            LinearLayout itemlayout = null;
            if(convertView == null) {
                itemlayout = (LinearLayout)inflater.inflate(R.layout.placeitem, null);
            } else {
                itemlayout = (LinearLayout) convertView;
            }

            Place item = (Place) getItem(position);

            TextView tv_name = (TextView)itemlayout.findViewById(R.id.place_name);
            tv_name.setText(item.name);
            TextView tv_addr = (TextView)itemlayout.findViewById(R.id.place_addr);
            tv_addr.setText(item.addr);
            ImageView iv = (ImageView)itemlayout.findViewById(R.id.place_img);
            iv.setImageBitmap(item.imgID);
            return itemlayout;
        }

    }
}
