package com.example.asus.masteryviiintaichung;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
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



public class HotelList extends AppCompatActivity {

    List<Hotel> lsHotels = new ArrayList<>();

    private HotelArrayAdapter lvadapter = null;

    private static final int LIST_HOTEL = 1;


    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LIST_HOTEL: {
                    List<Hotel> hotels = (List<Hotel>)msg.obj;
                    refreshHotelList(hotels);
                    break;
                }
            }
        }
    };

    private void refreshHotelList(List<Hotel> hotels) {
        lvadapter.clear();
        lvadapter.addAll(hotels);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_list);

        ListView hotels = (ListView)findViewById(R.id.lv_hotel);

        lvadapter = new HotelArrayAdapter(this, new ArrayList<Hotel>());
        hotels.setAdapter(lvadapter);

        getHotelsFromFirebase();

        hotels.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                double px = lsHotels.get(i).getPx();
                double py = lsHotels.get(i).getPy();
                Intent intent = getIntent();
                intent.setClass(HotelList.this, MapsActivity.class);
                intent.putExtra("PX", px);
                intent.putExtra("PY", py);
                startActivity(intent);
            }
        });
    }

    class FirebaseThread extends Thread {

        private DataSnapshot dataSnapshot;

        public FirebaseThread(DataSnapshot dataSnapshot) {
            this.dataSnapshot = dataSnapshot;
        }

        @Override
        public void run() {
            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                DataSnapshot dsName = ds.child("Name");
                DataSnapshot dsAdd = ds.child("Addr");
                DataSnapshot dsPx = ds.child("Px");
                DataSnapshot dsPy = ds.child("Py");

                String Name = (String)dsName.getValue();
                String Add = (String)dsAdd.getValue();
                double Px = Double.parseDouble((String)dsPx.getValue());
                double Py = Double.parseDouble((String)dsPy.getValue());

                DataSnapshot dsImg = ds.child("Pic");
                String imgUrl = (String) dsImg.getValue();
                Bitmap hotelImg = getImgBitmap(imgUrl);

                Hotel aHotel = new Hotel();
                aHotel.setName(Name);
                aHotel.setAddr(Add);
                aHotel.setPx(Px);
                aHotel.setPy(Py);
                aHotel.setImg(hotelImg);

                lsHotels.add(aHotel);
            }
            Message msg = new Message();
            msg.what = LIST_HOTEL;
            msg.obj = lsHotels;
            handler.sendMessage(msg);
        }
    }

    private void getHotelsFromFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                new FirebaseThread(dataSnapshot).start();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.v("HotelList", databaseError.getMessage());
            }
        });
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

    class HotelArrayAdapter extends ArrayAdapter<Hotel> {
        Context context;

        public HotelArrayAdapter(Context context, List<Hotel> items) {
            super(context, 0, items);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(context);
            LinearLayout itemlayout = null;
            if (convertView == null) {
                itemlayout = (LinearLayout) inflater.inflate(R.layout.hotelitem, null);
            } else {
                itemlayout = (LinearLayout) convertView;
            }
            Hotel item = (Hotel) getItem(position);

            TextView tvName = (TextView) itemlayout.findViewById(R.id.hotelname);
            TextView tvAddr = (TextView) itemlayout.findViewById(R.id.hoteladdr);
            ImageView ivPic = (ImageView) itemlayout.findViewById(R.id.hotelimg);

            tvName.setText(item.getName());
            tvAddr.setText(item.getAddr());
            ivPic.setImageBitmap(item.getImg());

            return itemlayout;
        }
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
                t.setClass(HotelList.this, User.class);
                startActivity(t);
                break;
            case R.id.mn_set:
                Toast toast1 = Toast.makeText(HotelList.this,
                        "工作分配:\n程式碼:葉昭甫\n" +
                                "創git team-project:林崇寶\n" +
                                "上台報告:詹亞豪", Toast.LENGTH_LONG);
                toast1.show();
                break;
        }
        return true;
    }
}
