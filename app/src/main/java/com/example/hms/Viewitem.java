package com.example.hms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Viewitem extends AppCompatActivity {

    TextView name;
    TextView room;
    TextView idate;
    TextView mdate;
    TextView status;

    String resultData;



    //private static final String API_URL = "http://192.168.8.126:8080/res/find/1";

    private  final String BASE_API_URL = "http://192.168.8.126:8080/res/find/1";

    public String API_URL = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
             resultData = extras.getString("result_data").trim();
            System.out.println("-------------------------------"+resultData+"--");
            // final String API_ENDPOINT_ID = resultData;
             // API_URL = BASE_API_URL+API_ENDPOINT_ID;
        }
        setContentView(R.layout.activity_viewitem);
        name = findViewById(R.id.iname);
        room = findViewById(R.id.iroomno);
        idate = findViewById(R.id.iidate);
        mdate = findViewById(R.id.ilmdate);
        status = findViewById(R.id.istatus);

        new APICallTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }




    private class APICallTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            try {
                URL url = new URL(BASE_API_URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    System.out.println("+++++++++++ok+++++++++++++++++");
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    return result.toString();
                } else {
                    // Handle the error case
                    return "Error: " + responseCode;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "Error: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonResponse = new JSONObject(result);

                // Extract values from the JSON response
                String nameValue = jsonResponse.getString("resName");
                String roomValue = jsonResponse.getString("roomNo");
                String idateValue = jsonResponse.getString("installationDate");
                String mdateValue = jsonResponse.getString("lastMaintenanceDate");
                String statusValue = jsonResponse.getString("status");

                // Set the values to the corresponding TextViews
                name.setText(nameValue);
                room.setText(roomValue);
                idate.setText(idateValue);
                mdate.setText(mdateValue);
                status.setText(statusValue);
            } catch (JSONException e) {
                e.printStackTrace();
                // Handle the JSON parsing error here
                showErrorMessage("JSON Parsing Error");
            }
        }

        // Display an error message to the user
        private void showErrorMessage(String message) {
            Toast.makeText(Viewitem.this, message, Toast.LENGTH_SHORT).show();
        }
    }
}
