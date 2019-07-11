package com.css.netchmap;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {
    public JSONArray elements;
    private Context context;
    public String userFromId;

    public RestaurantAdapter(JSONArray elements, Context context, String userFromId){
        this.elements = elements;
        this.context = context;
        this.userFromId = userFromId;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView first_line, second_line;
        RelativeLayout container;

        public ViewHolder(View itemView) {
            super(itemView);
            first_line = itemView.findViewById(R.id.element_view_first_line);
            second_line = itemView.findViewById(R.id.element_view_second_line);
            container = itemView.findViewById(R.id.element_view_container);
        }
    }

    @NonNull
    @Override
    public RestaurantAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_restaurants,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantAdapter.ViewHolder holder, int position) {
        try {
            JSONObject element = elements.getJSONObject(position);
            final String id = element.getString("id");
            final String name_res = element.getString("name_restaurant");
            final String address = element.getString("address");
            holder.first_line.setText(name_res);
            holder.second_line.setText(address);

            holder.container.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    Intent goToMessage = new Intent(context,MenuActivity.class);
                    goToMessage.putExtra("id_restaurant", id);
                    goToMessage.putExtra("restaurant_name", name_res);
                    context.startActivity(goToMessage);
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return elements.length();
    }
}
