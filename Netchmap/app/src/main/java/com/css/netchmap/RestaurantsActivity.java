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

public class RestaurantsActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);
        mRecyclerView = findViewById(R.id.recycler_view);
        FloatingActionButton fab = findViewById(R.id.fab_res);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("id", getIntent().getExtras().get("user_id").toString());
                intent.putExtra("user_id", getIntent().getExtras().get("user_id").toString());
                intent.putExtra("username", getIntent().getExtras().get("username").toString());
                intent.putExtra("name", getIntent().getExtras().get("name").toString());
                intent.putExtra("fullname", getIntent().getExtras().get("fullname").toString());
                startActivity(intent);
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        getRestaurants();
    }

    public Activity getActivity(){
        return this;
    }

    public void getRestaurants(){
        String url = "http://3.82.172.4/restaurantes";
        RequestQueue queue = Volley.newRequestQueue(this);
        Map<String, String> params = new HashMap();
        JSONObject parameters = new JSONObject(params);
        final String userId = getIntent().getExtras().get("user_id").toString();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray data = response.getJSONArray("data");
                            mAdapter = new RestaurantAdapter(data, getActivity(), userId);
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
