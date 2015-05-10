package com.idp.packpickup;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
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


public class SignUpActivity extends ActionBarActivity {
   private class SignUpConnection extends AsyncTask<JSONObject, String, String> {

        public SignUpConnection(Context context) {
            dialog = new ProgressDialog(context);
            this.context = context;
        }

        private ProgressDialog dialog;
        private Context context;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(JSONObject... params) {
            StringBuffer responseString = new StringBuffer();
            String data  = "";

            try {
                data += URLEncoder.encode("username", "UTF-8")
                        + "=" + URLEncoder.encode(params[0].getString("username"), "UTF-8");
                data += "&" + URLEncoder.encode("email", "UTF-8")
                        + "=" + URLEncoder.encode(params[0].getString("email"), "UTF-8");
                data += "&" + URLEncoder.encode("password", "UTF-8")
                        + "=" + URLEncoder.encode(params[0].getString("password"), "UTF-8");
                data += "&" + URLEncoder.encode("location", "UTF-8")
                        + "=" + URLEncoder.encode(params[0].getString("location"), "UTF-8");
                data += "&" + URLEncoder.encode("logging", "UTF-8")
                        + "=" + URLEncoder.encode(params[0].getString("logging"), "UTF-8");
                java.net.URL url = new java.net.URL(URL.createRowScript);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter
                        (conn.getOutputStream());
                wr.write( data );
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
            Log.v("debuvkv", responseString.toString());
            Looper.prepare();
            return responseString.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            String[] split = result.split(", :");
            Toast.makeText(SignUpActivity.this, result, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
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

    public void signUpHandler(View v) {
        JSONObject signup = new JSONObject();
        String username = ((EditText) findViewById(R.id.signupusername)).getText().toString();
        String email = ((EditText) findViewById(R.id.signupemail)).getText().toString();
        String password = ((EditText) findViewById(R.id.signuppassword)).getText().toString();
        String location = ((EditText) findViewById(R.id.signuplocation)).getText().toString();
        try {
            signup.put("username", username);
            signup.put("email", email);
            signup.put("password", password);
            signup.put("location", location);
            signup.put("logging", "signup");
            new SignUpConnection(this).execute(signup);
        } catch (JSONException e) {
            Log.v("Oops!", "SignUp Exception");
            e.printStackTrace();
        }
    }
}
