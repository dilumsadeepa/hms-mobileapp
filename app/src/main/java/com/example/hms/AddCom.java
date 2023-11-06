package com.example.hms;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.hms.model.Res;
import com.example.hms.model.Users;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.json.JSONException;
import org.json.JSONObject;
import cz.msebera.android.httpclient.Header;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddCom extends AppCompatActivity {
    private EditText editTextComplaint;
    private Button btnSelectImage, btnAddComplaint;
    private ImageView imageViewSelectedImage;
    private TextView eimgpath;

    private String imageURL;

    private TextView test;
    private static final int PICK_IMAGE = 1;
    private static final String CLOUDINARY_URL = "https://api.cloudinary.com/v1_1/dddyemhaw/image/upload";
    private static final String API_KEY = "465928523862425";
    private static final String API_SECRET = "BmYUHJDkvS_zUyZZFpOF8fnCrkE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_com);


        editTextComplaint = findViewById(R.id.editTextComplaint);
        btnSelectImage = findViewById(R.id.btnSelectImage);
        btnAddComplaint = findViewById(R.id.btnAddComplaint);
        imageViewSelectedImage = findViewById(R.id.imageViewSelectedImage);
        eimgpath = findViewById(R.id.evimagepath);

        btnSelectImage.setOnClickListener(v -> openGallery());

        test = findViewById(R.id.testText);

        test.setText("");


        btnAddComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addComplaintToServer();
            }
        });
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            Uri imageUri = data.getData();
            String imagePath = getPathFromURI(imageUri);

            uploadImageToCloudinary(imagePath);
        }
    }

    private String getPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(columnIndex);
        cursor.close();
        return path;
    }

    private void addComplaintToServer() {

        String complaintDetails = editTextComplaint.getText().toString().trim();
//
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String cdate = dateFormat.format(date).toString();

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int uid = sharedPreferences.getInt("id_key", 0);
        String index_no = sharedPreferences.getString("index_key","");


        test.setText("Adding");

        JSONObject complaintData = new JSONObject();
        try {
            complaintData.put("userId", Users.getId());
            complaintData.put("userIndex", String.valueOf(Users.getIndex_no()));
            complaintData.put("complaint", complaintDetails);
            complaintData.put("resId", Res.getResId());
            complaintData.put("complaintDate", cdate);
            complaintData.put("evidenceImage", String.valueOf(imageURL));
            complaintData.put("status", "Pending");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url = "http://52.201.92.58:8080/complaint/add";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, complaintData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        test.setText("success");
                        Toast.makeText(AddCom.this, "success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddCom.this, DashboardActivity.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                test.setText(error.toString());
                Toast.makeText(AddCom.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(request);
    }

    private void uploadImageToCloudinary(String imagePath) {
        eimgpath.setText("Uploading Image");
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        try {
            params.put("file", new File(imagePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        params.put("upload_preset", "pawhmucq"); // Add your upload preset name
        params.put("api_key", API_KEY);
        params.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        //params.put("signature", "ADD_GENERATED_SIGNATURE"); // Generate the signature
        client.post(CLOUDINARY_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String response = new String(responseBody);
                    JSONObject jsonResponse = new JSONObject(response);
                    imageURL = jsonResponse.getString("secure_url");
                    eimgpath.setText(imageURL);
                } catch (JSONException | NullPointerException | ArrayIndexOutOfBoundsException e) {
                    e.printStackTrace();
                    Toast.makeText(AddCom.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(AddCom.this, responseBody.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}