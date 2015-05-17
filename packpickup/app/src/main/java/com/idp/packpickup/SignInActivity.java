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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URLConnection;
import java.net.URLEncoder;


public class SignInActivity extends ActionBarActivity {

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
                data += URLEncoder.encode("username", "UTF-8")
                        + "=" + URLEncoder.encode(params[0].getString("username"), "UTF-8");
                data += "&" + URLEncoder.encode("password", "UTF-8")
                        + "=" + URLEncoder.encode(params[0].getString("password"), "UTF-8");
                data += "&" + URLEncoder.encode("logging", "UTF-8")
                        + "=" + URLEncoder.encode(params[0].getString("logging"), "UTF-8");
                java.net.URL url = new java.net.URL(URL.sign_in_up);
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
            JSONObject object;
            try {
                object = new JSONObject(result);
                result = object.getString("message");
                Toast.makeText(SignInActivity.this, result, Toast.LENGTH_LONG).show();
                if(object.getString("success").equals("1")) {
                    Intent intent = new Intent(context, ClientTypeActivity.class);
                    Log.v("debuv","aicix");
                    startActivity(intent);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public void signInHandler(View v) {
        JSONObject login = new JSONObject();
        String username = ((EditText) findViewById(R.id.username)).getText().toString();
        String password = ((EditText) findViewById(R.id.password)).getText().toString();
        try {
            login.put("username", username);
            login.put("password", password);
            login.put("logging", "signin");
            new MyConnection(v.getContext()).execute(login);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void signUpHandler(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_in, menu);
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
