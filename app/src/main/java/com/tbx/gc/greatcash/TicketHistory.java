package com.tbx.gc.greatcash;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tbx.gc.greatcash.challengeRequestPOJO.Data;
import com.tbx.gc.greatcash.challengeRequestPOJO.challengeRequestBean;
import com.tbx.gc.greatcash.ticketHistoryPOJO.Datum;
import com.tbx.gc.greatcash.ticketHistoryPOJO.ticketHistoryBean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class TicketHistory extends AppCompatActivity {

    String id;


    ProgressBar progress;
    RecyclerView grid;
    GridLayoutManager manager;

    List<Datum> list;
    List<String> dones;
    SharedPreferences pref;

    HistoryAdapter adapter;

    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_history);
        pref = getSharedPreferences("pref", Context.MODE_PRIVATE);

        list = new ArrayList<>();
        dones = new ArrayList<>();

        id = getIntent().getStringExtra("id");
        grid = findViewById(R.id.grid);
        progress = findViewById(R.id.progressBar17);
        manager = new GridLayoutManager(this, 1);
        adapter = new HistoryAdapter(TicketHistory.this, list);

        back = findViewById(R.id.back);

        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        loadData();

    }


    public void loadData()
    {

        progress.setVisibility(View.VISIBLE);

        bean b = (bean) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        ApiInterface cr = retrofit.create(ApiInterface.class);


        challengeRequestBean body1 = new challengeRequestBean();

        Data data1 = new Data();

        body1.setAction("ticket_history");

        data1.setUserId(pref.getString("id", ""));

        body1.setData(data1);

        Call<ticketHistoryBean> call = cr.ticketHistory(body1);

        call.enqueue(new Callback<ticketHistoryBean>() {
            @Override
            public void onResponse(Call<ticketHistoryBean> call, Response<ticketHistoryBean> response) {

                if (response.body().getStatus().equals("1"))
                {
                    adapter.setGridData(response.body().getData());
                }

                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ticketHistoryBean> call, Throwable t) {
progress.setVisibility(View.GONE);
            }
        });

    }


    class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
        Context context;
        List<Datum> list = new ArrayList<>();

        public HistoryAdapter(Context context, List<Datum> list) {
            this.context = context;
            this.list = list;
        }

        public void setGridData(List<Datum> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.history_list_model , viewGroup , false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

            Datum item = list.get(i);

            viewHolder.tickNumber.setText(item.getTicketNo());
            viewHolder.report.setText(item.getReport());
            viewHolder.date.setText(item.getCreateDate());
            viewHolder.status.setText(item.getStatus());

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView tickNumber, report, date, status;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                tickNumber = itemView.findViewById(R.id.textView125);
                report = itemView.findViewById(R.id.textView127);
                date = itemView.findViewById(R.id.textView126);
                status = itemView.findViewById(R.id.textView128);

            }
        }
    }

}
