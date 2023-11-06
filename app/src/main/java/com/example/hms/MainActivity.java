package com.example.hms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.hms.model.Users;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText editTextIdentifier, editTextPassword;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Users.getId() != 0){
            Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
            startActivity(intent);
        }

        editTextIdentifier = findViewById(R.id.editTextIdentifier);
        editTextPassword = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String identifier = editTextIdentifier.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                // Call the method to authenticate using the API
                authenticateUser(identifier, password);
            }
        });
    }

    private void authenticateUser(String identifier, String password) {
        String apiUrl = "http://52.201.92.58:8080/users/authenticate";

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("identifier", identifier);
            jsonBody.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, apiUrl, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String message = response.getString("message");
                            Users.setId(response.getInt("id"));
                            Users.setName(response.getString("name"));
                            Users.setRole(response.getInt("role"));
                            Users.setIndex_no(response.getString("index_no"));

                            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putInt("id_key", response.getInt("id"));
                            editor.putString("index_key", response.getString("index_no"));
                            editor.putString("name_key", response.getString("name"));

                            editor.apply();

                            if (message.toLowerCase().contains("successful")) {
                                // Authentication successful
                                Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                                startActivity(intent);
                            } else {
                                // Authentication failed
                                Toast.makeText(MainActivity.this, "Login Failed: " + message, Toast.LENGTH_SHORT).show();
                                // Save the response data for future use
                                // Do something with the response data here
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }
}
