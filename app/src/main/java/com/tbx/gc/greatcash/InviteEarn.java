package com.tbx.gc.greatcash;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class InviteEarn extends Fragment {


    //RecyclerView grid;

    //GridLayoutManager manager;

   // InviteAdapter adapter;

    TextView iidd;

    SharedPreferences pref;

    LinearLayout llcopy;

    Button invitebtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.invitefragment, container, false);

        pref = getContext().getSharedPreferences("pref" , Context.MODE_PRIVATE);

        iidd = view.findViewById(R.id.iidd);

        llcopy = view.findViewById(R.id.linearLayout);

        iidd.setText("http://greatcash.com/" + pref.getString("refId" , ""));


        //grid = view.findViewById(R.id.grid);

        invitebtn = view.findViewById(R.id.button);

      //  manager = new GridLayoutManager(getContext(), 1);

       // adapter = new InviteAdapter(getContext());

       // grid.setLayoutManager(manager);

      //  grid.setAdapter(adapter);

        llcopy.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View view)
            {
                Toast.makeText(getActivity(),"Your link copied",Toast.LENGTH_SHORT).show();
                ClipboardManager cm = (ClipboardManager)getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(iidd.getText());
                return false;
            }
        });

//        llcopy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)
//            {
//                Toast.makeText(getActivity(),"Your link copied",Toast.LENGTH_SHORT).show();
//                ClipboardManager cm = (ClipboardManager)getContext().getSystemService(Context.CLIPBOARD_SERVICE);
//                cm.setText(iidd.getText());
//            }
//        });

        invitebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, iidd.getText().toString());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

        return view;
    }


//    public class InviteAdapter extends RecyclerView.Adapter<InviteAdapter.MyViewHolder>
//    {
//
//        Context context;
//
//        public InviteAdapter(Context context) {
//
//            this.context = context;
//
//        }
//
//
//        @NonNull
//        @Override
//        public InviteAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//
//            View view = LayoutInflater.from(context).inflate(R.layout.invite_list_model, parent, false);
//            return new MyViewHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull InviteAdapter.MyViewHolder holder, int position) {
//
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    Intent sendIntent = new Intent();
//                    sendIntent.setAction(Intent.ACTION_SEND);
//                    sendIntent.putExtra(Intent.EXTRA_TEXT,
//                            "Hey check out my app at: https://play.google.com/store/apps/details?id=champ.cash.com");
//                    sendIntent.setType("text/plain");
//                    startActivity(sendIntent);
//
//                }
//            });
//        }
//
//        @Override
//        public int getItemCount() {
//            return 1;
//        }
//
//        public class MyViewHolder extends RecyclerView.ViewHolder
//        {
//
//
//            public MyViewHolder(View itemView)
//            {
//                super(itemView);
//            }
//        }
//    }
}