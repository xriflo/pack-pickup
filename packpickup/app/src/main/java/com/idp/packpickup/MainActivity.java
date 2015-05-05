package com.idp.packpickup;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;


public class MainActivity extends Activity {
    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";

    private class MyConnection extends AsyncTask<JSONObject, String, String> {

        // Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // Do something like display a progress bar
        }

        // This is run in a background thread
        @Override
        protected String doInBackground(JSONObject... params) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(URL.loginScript);
            httpPost.addHeader("Content-Type", "application/xml");
            Log.v("debuv","ici1");
            try {
                HttpResponse response = httpClient.execute(httpPost);
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(response.getEntity()
                                .getContent(), "UTF-8"));
                StringBuffer responseString = new StringBuffer("");
                String line;
                while ((line = reader.readLine()) != null) {
                    responseString.append(line);
                }
                System.out.println("respose QQQQQQQQQQQ");
                System.out.println("11response "
                        + responseString.toString());
                Log.v("debuv",responseString.toString());

            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Log.v("debuv","ici2");

            return "bla";
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
        }
    }

    public void signInHandler(View view)
    {
        // Do something in response to button
        Intent intent = new Intent(this, SignInActivity.class);
        Button buttonText = (Button) findViewById(R.id.login);
        String message = buttonText.getText().toString();

        intent.putExtra(EXTRA_MESSAGE, message);
        JSONObject login = new JSONObject();
        String username = ((EditText)findViewById(R.id.username)).getText().toString();
        String password = ((EditText)findViewById(R.id.password)).getText().toString();
        try {
            login.put("username",username);
            login.put("password",password);
            new MyConnection().execute(login);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //startActivity(intent);
    }

    public void signUpHandler(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, SignUpActivity.class);
        Button buttonText = (Button) findViewById(R.id.signup);
        String message = buttonText.getText().toString();

        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
