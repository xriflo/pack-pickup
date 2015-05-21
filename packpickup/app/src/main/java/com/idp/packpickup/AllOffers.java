package com.idp.packpickup;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class AllOffers extends ActionBarActivity {
    private ListView packsListView;
    private String[] stringArray ;
    private CustomListAdapter packsItemArrayAdapter;
    private JSONObject j;
    private HashMap<String, String> hash = new HashMap<String, String>();


    public class MyListAdapter extends BaseAdapter {

        /**
         * this is our own collection of data, can be anything we want it to be as long as we get the
         * abstract methods implemented using this data and work on this data (see getter) you should
         * be fine
         */
        private Context mContext;
        private List<Offer> mData;

        /**
         * our ctor for this adapter, we'll accept all the things we need here
         *
         * @param mData
         */
        public MyListAdapter(final Context context, final List<Offer> mData) {
            this.mData = mData;
            this.mContext = context;
        }

        public List<Offer> getData() {
            return mData;
        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            return null;
        }

// implement all abstract methods here
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_offers);
        String result = getIntent().getStringExtra("result");
        final ArrayList offers = getListData(result);
        final ListView lv = (ListView) findViewById(R.id.packsList);
        packsItemArrayAdapter = new CustomListAdapter(this, offers);
        lv.setAdapter(packsItemArrayAdapter);

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> a, View v, int position, long id) {
                JSONObject search = new JSONObject();
                Object o = lv.getItemAtPosition(position);
                Offer offer = (Offer) o;
                Toast.makeText(AllOffers.this, "Selected: " + " " + offer.getName_user(), Toast.LENGTH_SHORT).show();

                try {
                    for(int i = 0; i < offers.size(); ++i) {
                        offer.setPhone("");
                    }
                    offer.setPhone(hash.get(offer.getName_user()));
                    packsItemArrayAdapter.notifyDataSetChanged();
                    search.put("username_signed", SignInActivity.getUsername_signed());
                    search.put("username", offer.getName_user());
                    new UpdateHistory().execute(search);
                    return true;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                JSONObject search = new JSONObject();
                Object o = lv.getItemAtPosition(position);
                Offer offer = (Offer) o;
                Toast.makeText(AllOffers.this, "Tap long to select user", Toast.LENGTH_SHORT).show();

                try {
                    for(int i = 0; i < offers.size(); ++i) {
                        offer.setPhone("");
                    }
                    offer.setPhone(hash.get(offer.getName_user()));
                    packsItemArrayAdapter.notifyDataSetChanged();
                    search.put("username_signed", SignInActivity.getUsername_signed());
                    search.put("username", offer.getName_user());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private ArrayList getListData(String result) {
        ArrayList<Offer> offers = new ArrayList<Offer>();
        JSONArray object = null;
        try {
            object = new JSONArray(result);
            for(int i=0; i<object.length(); i++) {
                j = object.getJSONObject(i);
                Offer offer = new Offer();
                String imageEncoded = j.getString("image");
                byte[] decodedString = Base64.decode(imageEncoded, Base64.DEFAULT);
                Bitmap imageDecoded = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                offer.setImage(imageDecoded);
                offer.setName_user(j.getString("name")+" ("+j.getString("username")+")");
                hash.put(j.getString("name")+" ("+j.getString("username")+")", j.getString("phone"));
//                offer.setPhone(j.getString("phone"));
                offer.setDeparture("Plecare: "+j.getString("departure_city")+", "+j.getString("departure_date") +", "+
                        j.getString("departure_time")+", "+j.get("departure_location"));
                offer.setArrival("Sosire: " + j.getString("arrival_city") + ", " + j.getString("arrival_date") + ", " +
                        j.getString("arrival_time") + ", " + j.get("arrival_location"));

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

    private class UpdateHistory extends AsyncTask<JSONObject, String, String> {

        @Override
        protected String doInBackground(JSONObject... params) {
            String toReturn = "";
            StringBuffer responseString = new StringBuffer();
            String data = "";

            try {
                data += URLEncoder.encode("username_signed", "UTF-8")
                        + "=" + URLEncoder.encode(params[0].getString("username_signed"), "UTF-8");
                data += "&" + URLEncoder.encode("username", "UTF-8")
                        + "=" + URLEncoder.encode(params[0].getString("username"), "UTF-8");

                java.net.URL url = new java.net.URL(URL.alterate_history);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter
                        (conn.getOutputStream());
                wr.write(data);
                wr.flush();

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                responseString = new StringBuffer("");
                String line;
                while ((line = reader.readLine()) != null) {
                    responseString.append(line);
                }

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return responseString.toString();
        }

        @Override
        public void onPostExecute(String result) {
            super.onPostExecute(result);
            Toast.makeText(AllOffers.this, "Selected :" + " " + result, Toast.LENGTH_SHORT).show();
        }
    }
}
