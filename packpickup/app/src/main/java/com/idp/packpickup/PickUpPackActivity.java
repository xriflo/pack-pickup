package com.idp.packpickup;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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

/**
 * Created by Mardaloescu on 5/12/2015.
 */
public class PickUpPackActivity extends ActionBarActivity {
    private String username_signed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickup_pack);
        username_signed = getIntent().getStringExtra("username_signed");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
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

    public void sendOfferHandler(View v) {
        JSONObject sendOffer = new JSONObject();
        EditText departure_city = ((EditText) findViewById(R.id.departure_cityEntry));
        EditText departure_location = ((EditText) findViewById(R.id.departure_locationEntry));
        EditText departure_time = ((EditText) findViewById(R.id.departure_timeEntry));
        EditText departure_hour = ((EditText) findViewById(R.id.departure_hourEntry));

        EditText arrival_city = ((EditText) findViewById(R.id.arrival_cityEntry));
        EditText arrival_location = ((EditText) findViewById(R.id.arrival_locationEntry));
        EditText arrival_time = ((EditText) findViewById(R.id.arrival_timeEntry));
        EditText arrival_hour = ((EditText) findViewById(R.id.arrival_hourEntry));

        if(departure_city.getText().toString().trim().equals("") ||
                departure_location.getText().toString().trim().equals("") ||
                departure_time.getText().toString().trim().equals("") ||
                departure_hour.getText().toString().trim().equals("") ||
                arrival_city.getText().toString().trim().equals("") ||
                arrival_location.getText().toString().trim().equals("") ||
                arrival_time.getText().toString().trim().equals("") ||
                arrival_hour.getText().toString().trim().equals("")) {
            if(departure_city.getText().toString().trim().equals("")) {
                departure_city.setError("Departure city is required.");
            }
            if(departure_location.getText().toString().trim().equals("")) {
                departure_location.setError("Departure location is required.");
            }
            if(departure_time.getText().toString().trim().equals("")) {
                departure_time.setError("Departure date is required.");
            }
            if(departure_hour.getText().toString().trim().equals("")) {
                departure_hour.setError("Departure hour is required.");
            }
            if(arrival_city.getText().toString().trim().equals("")) {
                arrival_city.setError("Arrival city is required.");
            }
            if(arrival_location.getText().toString().trim().equals("")) {
                arrival_location.setError("Arrival location is required.");
            }
            if(arrival_time.getText().toString().trim().equals("")) {
                arrival_time.setError("Arrival date is required.");
            }
            if(arrival_hour.getText().toString().trim().equals("")) {
                arrival_hour.setError("Arrival hour is required.");
            }
        }
        else {


            try {
                sendOffer.put("departure_city", departure_city.getText().toString());
                sendOffer.put("departure_location", departure_location.getText().toString());
                sendOffer.put("departure_time", departure_time.getText().toString());
                sendOffer.put("departure_hour", departure_hour.getText().toString());

                sendOffer.put("arrival_city", arrival_city.getText().toString());
                sendOffer.put("arrival_location", arrival_location.getText().toString());
                sendOffer.put("arrival_time", arrival_time.getText().toString());
                sendOffer.put("arrival_hour", arrival_hour.getText().toString());
                sendOffer.put("username", username_signed);

                new MyConnection(v.getContext()).execute(sendOffer);
            } catch (JSONException e) {
                Log.v("Oops!", "SignUp Exception");
                e.printStackTrace();
            }
        }

    }

    private class MyConnection extends AsyncTask<JSONObject, String, String> {
        private Context context;
        private JSONObject jsonResponse;
        public MyConnection(Context context) {
            this.context = context;
        }

        // Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        // This is run in a background thread
        @Override
        protected String doInBackground(JSONObject... params) {
            StringBuffer responseString = new StringBuffer();
            String data = "";

            try {
                data += URLEncoder.encode("departure_city", "UTF-8")
                        + "=" + URLEncoder.encode(params[0].getString("departure_city"), "UTF-8");
                data += "&" + URLEncoder.encode("departure_location", "UTF-8")
                        + "=" + URLEncoder.encode(params[0].getString("departure_location"), "UTF-8");
                data += "&" + URLEncoder.encode("departure_time", "UTF-8")
                        + "=" + URLEncoder.encode(params[0].getString("departure_time"), "UTF-8");
                data += "&" + URLEncoder.encode("departure_hour", "UTF-8")
                        + "=" + URLEncoder.encode(params[0].getString("departure_hour"), "UTF-8");
                data += "&" + URLEncoder.encode("arrival_city", "UTF-8")
                        + "=" + URLEncoder.encode(params[0].getString("arrival_city"), "UTF-8");
                data += "&" + URLEncoder.encode("arrival_location", "UTF-8")
                        + "=" + URLEncoder.encode(params[0].getString("arrival_location"), "UTF-8");
                data += "&" + URLEncoder.encode("arrival_time", "UTF-8")
                        + "=" + URLEncoder.encode(params[0].getString("arrival_time"), "UTF-8");
                data += "&" + URLEncoder.encode("arrival_hour", "UTF-8")
                        + "=" + URLEncoder.encode(params[0].getString("arrival_hour"), "UTF-8");
                data += "&" + URLEncoder.encode("username", "UTF-8")
                        + "=" + URLEncoder.encode(params[0].getString("username"), "UTF-8");
                java.net.URL url = new java.net.URL(URL.add_offer);
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
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return responseString.toString();

        }

        // This is called from background thread but runs in UI
        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        // This runs in UI when background thread finishes
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.v("debuvv", result);
            JSONObject object;
            try {

                object = new JSONObject(result);
                Toast.makeText(PickUpPackActivity.this, object.getString("message"), Toast.LENGTH_LONG).show();

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
