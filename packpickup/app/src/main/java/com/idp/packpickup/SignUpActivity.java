package com.idp.packpickup;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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

    private static int RESULT_LOAD_IMAGE = 1;
    private static Bitmap imageFromUser;

    private class SignUpConnection extends AsyncTask<JSONObject, String, String> {
        ImageView imageView = (ImageView) findViewById(R.id.imgUser);
        byte[] decodedByte;
        Bitmap x;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(JSONObject... params) {
            StringBuffer responseString = new StringBuffer();
            String data = "";
            String imageEncoded = "";

            try {
                imageEncoded = Functions.encodeImage(imageFromUser);
                decodedByte = Base64.decode(imageEncoded, 0);
                x =  BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
                data += URLEncoder.encode("username", "UTF-8")
                        + "=" + URLEncoder.encode(params[0].getString("username"), "UTF-8");
                data += "&" + URLEncoder.encode("email", "UTF-8")
                        + "=" + URLEncoder.encode(params[0].getString("email"), "UTF-8");
                data += "&" + URLEncoder.encode("password", "UTF-8")
                        + "=" + URLEncoder.encode(params[0].getString("password"), "UTF-8");
                data += "&" + URLEncoder.encode("location", "UTF-8")
                        + "=" + URLEncoder.encode(params[0].getString("location"), "UTF-8");
                data += "&" + URLEncoder.encode("phone", "UTF-8")
                        + "=" + URLEncoder.encode(params[0].getString("phone"), "UTF-8");
                data += "&" + URLEncoder.encode("image", "UTF-8")
                        + "=" + URLEncoder.encode(imageEncoded, "UTF-8");
                data += "&" + URLEncoder.encode("sging", "UTF-8")
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
                return imageEncoded;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return imageEncoded;
            } catch (JSONException e) {
                e.printStackTrace();
                return imageEncoded;
            }
            return responseString.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                JSONObject object = new JSONObject(result);
                result = object.getString("message");
                imageView.setImageBitmap(x);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Toast.makeText(SignUpActivity.this, result, Toast.LENGTH_LONG).show();
        }
    }

    public void signUpHandler(View v) {
        JSONObject signup = new JSONObject();
        String username = ((EditText) findViewById(R.id.signupusername)).getText().toString();
        String email = ((EditText) findViewById(R.id.signupemail)).getText().toString();
        String password = ((EditText) findViewById(R.id.signuppassword)).getText().toString();
        String location = ((EditText) findViewById(R.id.signuplocation)).getText().toString();
        String phone = ((EditText) findViewById(R.id.phone)).getText().toString();

        try {
            signup.put("username", username);
            signup.put("email", email);
            signup.put("password", password);
            signup.put("location", location);
            signup.put("phone", phone);
            signup.put("logging", "signup");
            new SignUpConnection().execute(signup);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void chooseImage(View v) {
        Toast.makeText(SignUpActivity.this, "It worked the image", Toast.LENGTH_LONG).show();
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView = (ImageView) findViewById(R.id.imgUser);
            Bitmap rescaled = Functions.resolveOrientation(picturePath);
            int scale_factor = Math.max(rescaled.getWidth(),rescaled.getHeight())/100;
            Bitmap resized = Bitmap.createScaledBitmap(rescaled, rescaled.getWidth()/scale_factor,
                    rescaled.getHeight()/scale_factor,true);
            imageView.setImageBitmap(resized);
            imageFromUser = resized;
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

}
