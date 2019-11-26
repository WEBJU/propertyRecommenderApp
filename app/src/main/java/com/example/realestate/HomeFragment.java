package com.example.realestate;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import adapters.PropertyAdapter;
import adapters.PropertyAdapter.MyViewHolder;
import models.Property;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private static final String TAG=HomeFragment.class.getSimpleName();
    private static final String URL="http://10.20.140.21/propertyrecommender/public/api/display_properties";
    private RecyclerView recyclerView;
    private List<Property> propertyList;
    private PropertyAdapter propertyAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newIstance(String param1,String param2){
        HomeFragment homeFragment=new HomeFragment();
        Bundle args=new Bundle();
        homeFragment.setArguments(args);
        return homeFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView=view.findViewById(R.id.recycler);
        propertyList=new ArrayList<>();
        propertyAdapter=new PropertyAdapter(getActivity(),propertyList);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getActivity(),1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2,dpToPx(8),true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(propertyAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        fetchLawyerItems();
        return view;
    }

    private int dpToPx(int dp) {
        Resources r=getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,r.getDisplayMetrics()));
    }

    private void fetchLawyerItems() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading..");
        progressDialog.show();
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                Gson gson=new Gson();
                Type listType=new TypeToken<List<Property>>(){}.getType();
                try {
//                    JSONArray jsonArray = response.getJSONArray("lawyers");
                    List<Property> properties =new ArrayList<>();
                    properties=gson.fromJson(response.getJSONArray("properties").toString(),listType);
                    if (properties!=null && !properties.isEmpty()){
                        propertyList.clear();
                        propertyList.addAll(properties);
                        propertyAdapter.notifyDataSetChanged();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                String message=null;
                if (error instanceof NetworkError){
                    message="Could not load Lawyers..Check your connection";
                }
                if (error instanceof ServerError){
                    message="Server could not be reached..Make sure you have internet connection..";
                }
                if (error instanceof AuthFailureError){
                    message="Could not load Lawyers..Check your connection";
                }
                if (error instanceof NoConnectionError){
                    message="Could not connect to the internet";
                }


                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        MyProperties.getInstance().addToRequestQueue(jsonObjectRequest);

    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration{
        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            int position=parent.getChildAdapterPosition(view);
            int column=position % spanCount; //item
            if (includeEdge){
                outRect.left=spacing-column*spacing/spanCount;
                outRect.right=(column+1) *spacing / spanCount;

                if (position<spanCount){
                    outRect.top=spacing;
                }
                outRect.bottom=spacing;
            }
            else {
                outRect.left=column*spacing/spanCount;
                outRect.right=spacing-(column+1)*spacing /spanCount;
                if (position>=spanCount){
                    outRect.top=spacing;
                }
            }
            super.getItemOffsets(outRect, view, parent, state);
        }
    }

    class LawyerAdapter extends RecyclerView.Adapter<LawyerAdapter.MyViewHolder> {
        private Context context;
        private List<Property>lawyerList;

        public class MyViewHolder extends RecyclerView.ViewHolder{
            public TextView property_name,location,price,description;
            public ImageView thumbnail;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                property_name=itemView.findViewById(R.id.property_name);
                location=itemView.findViewById(R.id.location);
                price=itemView.findViewById(R.id.price);
                description=itemView.findViewById(R.id.description);

                thumbnail=itemView.findViewById(R.id.property_img);

            }
        }
        public LawyerAdapter(Context context, List<Property> lawyerList) {
            this.context=context;
            this.lawyerList=lawyerList;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView=LayoutInflater.from(parent.getContext()).inflate(R.layout.property_row,parent,false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder,final int position) {

            final Property property=propertyList.get(position);

            Log.d("Lawyer id:","id"+ property.getId());
            holder.property_name.setText("Property Name: " +property.getPropertyName());
            holder.location.setText("Contact: " +property.getLocation());
            holder.price.setText("Price: " +property.getPrice());
            holder.description.setText(("Description :"+property.getDescription()));
            holder.location.setText(("Location: " +property.getLocation()));
            Log.d("image",property.getPropertyyImage());
            Glide.with(context).load(property.getPropertyyImage()).into(holder.thumbnail);
        }

        @Override
        public int getItemCount() {
            return lawyerList.size();
        }
    }

}

