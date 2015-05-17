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
public class SendPackActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_pack);

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

    public void searchForOffers(View v) {
        JSONObject search = new JSONObject();
        EditText departure = ((EditText) findViewById(R.id.departureEntry));
        EditText arrival = ((EditText) findViewById(R.id.arrivalEntry));
        EditText date = ((EditText) findViewById(R.id.dateEntry));
        if(departure.getText().toString().trim().equals("") ||
            arrival.getText().toString().trim().equals("") ||
            date.getText().toString().trim().equals("")) {

            if(departure.getText().toString().trim().equals("")) {
                departure.setError("Departure is required.");
            }
            if(arrival.getText().toString().trim().equals("")) {
                arrival.setError("Arrival is required.");
            }
            if(date.getText().toString().trim().equals("")) {
                date.setError("Date is required.");
            }

        }
        else {
            try {
                search.put("departure", departure.getText().toString());
                search.put("arrival", arrival.getText().toString());
                search.put("date", date.getText().toString());

                new MyConnection(v.getContext()).execute(search);
            } catch (JSONException e) {
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
                data += URLEncoder.encode("departure", "UTF-8")
                        + "=" + URLEncoder.encode(params[0].getString("departure"), "UTF-8");
                data += "&" + URLEncoder.encode("arrival", "UTF-8")
                        + "=" + URLEncoder.encode(params[0].getString("arrival"), "UTF-8");
                data += "&" + URLEncoder.encode("date", "UTF-8")
                        + "=" + URLEncoder.encode(params[0].getString("date"), "UTF-8");
                java.net.URL url = new java.net.URL(URL.sendPack);
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
            JSONArray object;
            try {
                Log.v("debuvv", result);
                object = new JSONArray(result);
                Log.v("debuvv", object.toString());

                if(!(object.length() == 0)) {
                    Intent intent = new Intent(context, AllOffers.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(SendPackActivity.this, "There are no offers for you, you filthy animal!", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
