package com.css.netchmap;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.drm.DrmStore;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public Activity getActivity(){
        return this;
    }

    public void onBtnLoginClicked(View view) {
        // 1. Getting username and password inputs from view
        EditText txtUsername = (EditText) findViewById(R.id.txtUsername);
        EditText txtPassword = (EditText) findViewById(R.id.txtPassword);
        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();

        // 2. Creating a message from user input data
        Map<String, String> message = new HashMap<>();
        message.put("username", username);
        message.put("password", password);

        // 3. Converting the message object to JSON string (jsonify)
        JSONObject jsonMessage = new JSONObject(message);

        // 4. Sending json message to Server
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                "http://3.82.172.4/authenticate",
                jsonMessage,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String message = response.getString("message");
                            String name = response.getString("name");
                            String fullname = response.getString("fullname");

                            if(message.equals("Authorized")) {
                                showMessage("Authenticated");
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                intent.putExtra("user_id", response.getInt("user_id"));
                                intent.putExtra("username", response.getString("username"));
                                intent.putExtra("name", response.getString("name"));
                                intent.putExtra("fullname", response.getString("fullname"));
                                startActivity(intent);
                            }
                            else {
                                showMessage("Wrong username or password");
                            }
                            showMessage("Bienvenido " + name + " " + fullname);
                        }catch (Exception e) {
                            e.printStackTrace();
                            showMessage(e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        if( error instanceof  AuthFailureError ){
                            showMessage("Unauthorized");
                        }
                        else {
                            showMessage(error.getMessage());
                        }
                    }
                }
        );

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}
