package com.tbx.gc.greatcash;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Rating  extends Fragment {


    RecyclerView grid;

    GridLayoutManager manager;

    RatingAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.rating , container , false);

        grid = view.findViewById(R.id.grid);

        manager = new GridLayoutManager(getContext() , 1);

        adapter = new RatingAdapter(getContext());

        grid.setLayoutManager(manager);

        grid.setAdapter(adapter);




        return view;
    }


    public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.MyViewHolder>{

        Context context;


        public RatingAdapter(Context context){

            this.context = context;

        }


        @NonNull
        @Override
        public RatingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


            View view = LayoutInflater.from(context).inflate(R.layout.rating_list_model , parent , false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RatingAdapter.MyViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 12;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {


            public MyViewHolder(View itemView) {
                super(itemView);
            }
        }
    }


}

