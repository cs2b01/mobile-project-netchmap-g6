package com.css.netchmap;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private JSONArray elements;
    private Context context;

    public MenuAdapter(JSONArray elements, Context context){
        this.elements = elements;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView first_line;
        RelativeLayout container;

        public ViewHolder(View itemView) {
            super(itemView);
            first_line = itemView.findViewById(R.id.line_menu);
            container = itemView.findViewById(R.id.menu_view_container);
        }
    }

    @NonNull
    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_menu,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuAdapter.ViewHolder holder, int position) {
        try {
            JSONObject element = elements.getJSONObject(position);
            String name = element.getString("name");
            holder.first_line.setText(name);

            //System.out.println("Nombre menu: " + name);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return elements.length();
    }

}
