package com.idp.packpickup;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class AllOffers extends ActionBarActivity {
    private ListView packsListView;
    private String[] stringArray ;
    private ArrayAdapter packsItemArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_offers);
        String result = getIntent().getStringExtra("result");
        ArrayList offers = getListData(result);
        final ListView lv = (ListView) findViewById(R.id.packsList);
        lv.setAdapter(new CustomListAdapter(this, offers));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = lv.getItemAtPosition(position);
                Offer offer = (Offer) o;
                Toast.makeText(AllOffers.this, "Selected :" + " " + offer, Toast.LENGTH_LONG).show();
            }
        });

    }

    private ArrayList getListData(String result) {
        ArrayList<Offer> offers = new ArrayList<Offer>();
        JSONArray object = null;
        try {
            object = new JSONArray(result);
            for(int i=0; i<object.length(); i++) {
                JSONObject j = object.getJSONObject(i);
                Offer offer = new Offer();
                String imageEncoded = j.getString("image");
                byte[] decodedString = Base64.decode(imageEncoded, Base64.DEFAULT);
                Bitmap imageDecoded = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                offer.setImage(imageDecoded);
                offer.setName_user(j.getString("name")+" ("+j.getString("username")+")");
                offer.setPhone(j.getString("phone"));
                offer.setDeparture("Plecare: "+j.getString("departure_city")+", "+j.getString("departure_date") +", "+
                        j.getString("departure_time")+", "+j.get("departure_location"));
                offer.setArrival("Sosire: " + j.getString("arrival_city") + ", " + j.getString("arrival_date") + ", " +
                        j.getString("arrival_time"));
                offers.add(offer);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return offers;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_all_offers, menu);
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
