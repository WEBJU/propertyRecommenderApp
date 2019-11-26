package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realestate.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import models.Property;

public class PropertyAdapter extends RecyclerView.Adapter<PropertyAdapter.MyViewHolder> {
    private LayoutInflater layoutInflater;
    private ArrayList<Property> dataModelArrayList;
    private Context context;
    public PropertyAdapter(Context context, List<Property> dataModelArrayList) {
        layoutInflater=LayoutInflater.from(context);
        this.dataModelArrayList = (ArrayList<Property>) dataModelArrayList;
    }
    @NonNull
    @Override

    public PropertyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=layoutInflater.from(parent.getContext()).inflate(R.layout.property_row,parent,false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PropertyAdapter.MyViewHolder holder, int position) {
        Picasso.get().load(dataModelArrayList.get(position).getPropertyyImage()).into(holder.property_img);
        holder.property_name.setText(dataModelArrayList.get(position).getPropertyName());
        holder.location.setText(dataModelArrayList.get(position).getLocation());
        holder.price.setText(dataModelArrayList.get(position).getPrice());
        holder.description.setText(dataModelArrayList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView property_name,price,location,description ;
        ImageView property_img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            property_name=itemView.findViewById(R.id.name);
            location=itemView.findViewById(R.id.location);
            price=itemView.findViewById(R.id.price);
            description=itemView.findViewById(R.id.description);
        }
    }
}
