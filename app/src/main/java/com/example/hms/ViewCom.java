package com.example.hms;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.hms.model.MyDataModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ViewCom extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_com);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchDataFromAPI();
    }

    private void fetchDataFromAPI() {

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String index_no = sharedPreferences.getString("index_key", "");

        String url = "http://52.201.92.58:8080/complaint/complaintByUserId/"+index_no;

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        handleJSONResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                    }
                }
        );

        requestQueue.add(jsonArrayRequest);
    }

    private void handleJSONResponse(JSONArray response) {
        try {
            // Create a list to hold the extracted data
            List<MyDataModel> dataList = new ArrayList<>();

            for (int i = 0; i < response.length(); i++) {
                JSONObject jsonObject = response.getJSONObject(i);

                String evidenceImage = jsonObject.getString("evidenceImage");
                String complaint = jsonObject.getString("complaint");

                JSONObject resource = jsonObject.getJSONObject("resource");
                String resName = resource.getString("resName");

                MyDataModel data = new MyDataModel(evidenceImage, complaint, resName);
                dataList.add(data);
            }

            // Set up RecyclerView with the data list
            adapter = new CustomAdapter(dataList);
            recyclerView.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}