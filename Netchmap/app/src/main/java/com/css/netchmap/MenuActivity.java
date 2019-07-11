package com.css.netchmap;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MenuActivity extends AppCompatActivity{

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    TextView textView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mRecyclerView = findViewById(R.id.recycler_view_menu);
        textView = findViewById(R.id.restaurant_name);
        textView.setText(getIntent().getExtras().get("restaurant_name").toString());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        getMenu();
    }

    public Activity getActivity() {
        return this;
    }

    public void getMenu(){
        final String id_rest =getIntent().getExtras().get("id_restaurant").toString();
        String url = "http://3.82.172.4/menu/<id_rest>";
        url = url.replace("<id_rest>", id_rest);
        RequestQueue queue = Volley.newRequestQueue(this);
        Map<String, String> params = new HashMap();
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray data = response.getJSONArray("menu");
                            System.out.println(data);
                            mAdapter = new MenuAdapter(data, getActivity());
                            mRecyclerView.setAdapter(mAdapter);
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                error.printStackTrace();

            }
        });
        queue.add(jsonObjectRequest);

    }

}
