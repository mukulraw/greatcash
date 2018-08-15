package com.tbx.gc.greatcash;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tbx.gc.greatcash.EarnMorePOJO.earnMoreBean;
import com.tbx.gc.greatcash.RedeemRequest.DataRedReq;
import com.tbx.gc.greatcash.RedeemRequest.RedeemReqBean;
import com.tbx.gc.greatcash.challengeRequestPOJO.Data;
import com.tbx.gc.greatcash.challengeRequestPOJO.challengeRequestBean;
import com.tbx.gc.greatcash.redeemHistoryPOJO.Datum;
import com.tbx.gc.greatcash.redeemHistoryPOJO.redeemHistoryBean;
import com.tbx.gc.greatcash.redeemPOJO.redeemBean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Priyanka on 7/11/2018.
 */

public class Redeem extends AppCompatActivity {
    private RelativeLayout rel_back, rel_dropDown;
    private String[] strName = {"Paypal", "bKash", "Bank", "Paytm"};
    private int[] str_img = {R.drawable.paypal_icon, R.drawable.bkash, R.drawable.bank_icon, R.drawable.paytm_icon};
    private RecyclerView recyclerView_dropDown;
    private RecyclerView.LayoutManager layoutManager;
    private DropDownAdapter adapter;
    private TextView text_selpayment;
    private RelativeLayout rel_payPal, rel_vikash, rel_bankOfIndia, rel_paytm;
    private Button btn_submit;
    private String tag;
    private EditText ed_redeemAmount;
    private EditText ed_accountNumber_B, ed_ifscCode_B, ed_enterName_B;
    private EditText ed_paypalID;
    private EditText ed_vikashID;
    private EditText ed_paytmID;
    SharedPreferences pref;
    String str_redeemAmount;
    String str_acountNum, str_ifsc, str_name;
    String str_paypal;
    String str_vikashID;
    String str_paytm;
    ProgressBar progress;

    RecyclerView grid;
    GridLayoutManager manager;
    List<Datum> list;
    RedeemAdapter redeemAdapter;

    String amo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.redeem);

        list = new ArrayList<>();

        amo = getIntent().getStringExtra("amount");

        pref = getApplication().getSharedPreferences("pref", Context.MODE_PRIVATE);
        setID();

        rel_dropDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView_dropDown.setVisibility(View.VISIBLE);
                layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView_dropDown.setLayoutManager(layoutManager);

                adapter = new DropDownAdapter(getApplicationContext(), strName, str_img);
                recyclerView_dropDown.setAdapter(adapter);
            }
        });

        rel_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        grid = findViewById(R.id.grid);
        manager = new GridLayoutManager(this , 1);
        redeemAdapter = new RedeemAdapter(this , list);

        grid.setAdapter(redeemAdapter);
        grid.setLayoutManager(manager);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str_redeemAmount = ed_redeemAmount.getText().toString();
                Log.e("redeemamounttt", "" + str_redeemAmount);


                float amp = Float.parseFloat(amo);

                float am = Float.parseFloat(str_redeemAmount);

                if (am >= 20 && am <= amp)
                {
                    String text_selected = text_selpayment.getText().toString();
                    Log.e("typeeeee", "" + text_selected);

                    if (text_selected.equals("")) {
                        Toast.makeText(getApplicationContext(), "Please select type", Toast.LENGTH_SHORT).show();
                    } else {
                        if (tag.equals("1")) {
                            str_paypal = ed_paypalID.getText().toString();
                            if (str_paypal.equals("")) {
                                Toast.makeText(getApplicationContext(), "Please enter paypalID", Toast.LENGTH_SHORT).show();
                            } else if (text_selected.equals("")) {
                                Toast.makeText(getApplicationContext(), "Please select type", Toast.LENGTH_SHORT).show();
                            } else if (str_redeemAmount.equals("")) {
                                Toast.makeText(getApplicationContext(), "Please enter redeem amount", Toast.LENGTH_SHORT).show();
                            } else {

                                payPal_Api();
                            }


                        }

                        if (tag.equals("2")) {
                            str_vikashID = ed_vikashID.getText().toString();
                            if (str_vikashID.equals("")) {
                                Toast.makeText(getApplicationContext(), "Please enter bKash ID", Toast.LENGTH_SHORT).show();
                            } else if (text_selected.equals("")) {
                                Toast.makeText(getApplicationContext(), "Please select type", Toast.LENGTH_SHORT).show();
                            } else if (str_redeemAmount.equals("")) {
                                Toast.makeText(getApplicationContext(), "Please enter redeem amount", Toast.LENGTH_SHORT).show();
                            } else {

                                vikash_Api();
                            }


                        }

                        if (tag.equals("3")) {
                            str_acountNum = ed_accountNumber_B.getText().toString();
                            str_ifsc = ed_ifscCode_B.getText().toString();
                            str_name = ed_enterName_B.getText().toString();

                            if (str_redeemAmount.equals("")) {
                                Toast.makeText(getApplicationContext(), "Please enter redeem amount", Toast.LENGTH_SHORT).show();
                            } else if (text_selected.equals("")) {
                                Toast.makeText(getApplicationContext(), "Please select type", Toast.LENGTH_SHORT).show();
                            } else if (str_acountNum.equals("")) {
                                Toast.makeText(getApplicationContext(), "Please enter account number", Toast.LENGTH_SHORT).show();
                            } else if (str_ifsc.equals("")) {
                                Toast.makeText(getApplicationContext(), "Please enter ifsc code", Toast.LENGTH_SHORT).show();
                            } else if (str_name.equals("")) {
                                Toast.makeText(getApplicationContext(), "Please enter name", Toast.LENGTH_SHORT).show();
                            } else {

                                bank_Api();
                            }

                        }

                        if (tag.equals("4")) {
                            str_paytm = ed_paytmID.getText().toString();

                            if (str_paytm.equals("")) {
                                Toast.makeText(getApplicationContext(), "Please enter paytm ID", Toast.LENGTH_SHORT).show();
                            } else if (text_selected.equals("")) {
                                Toast.makeText(getApplicationContext(), "Please select type", Toast.LENGTH_SHORT).show();
                            } else if (str_redeemAmount.equals("")) {
                                Toast.makeText(getApplicationContext(), "Please enter redeem amount", Toast.LENGTH_SHORT).show();
                            } else {

                                Paytm_Api();
                            }

                        }
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please enter an amount between $ 20 and $ " + amp, Toast.LENGTH_SHORT).show();
                }





            }
        });


    }

    private void Paytm_Api() {

        Log.e("1111", "1111");
        progress.setVisibility(View.VISIBLE);

        bean b = (bean) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface cr = retrofit.create(ApiInterface.class);

        // challengeRequestBean body = new challengeRequestBean();
        RedeemReqBean body = new RedeemReqBean();

        //  Data data = new Data();
        DataRedReq data = new DataRedReq();

        body.setAction("redeem");

        data.setUserId(pref.getString("id", ""));
        data.setPaymentMode("Paytm");
        data.setRedeemAmt(str_redeemAmount);
        data.setBankID(str_paytm);
        data.setAccountNum("");
        data.setIfscCode("");
        data.setBankName("");

        Log.d("iidd", pref.getString("id", ""));

        body.setData(data);

        Call<redeemBean> call = cr.redeenApi(body);


        call.enqueue(new Callback<redeemBean>() {

            @Override
            public void onResponse(Call<redeemBean> call, Response<redeemBean> response) {
                Log.e("222", "222");

                progress.setVisibility(View.GONE);

                if (response.body().getStatus_redeem().equals("1")) {
                    Toast.makeText(getApplicationContext(), "Successfully Payment Done", Toast.LENGTH_SHORT).show();
                    ed_redeemAmount.setText("");
                    ed_paytmID.setText("");
                    text_selpayment.setText("");
                }

                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<redeemBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }


        });

    }

    private void bank_Api() {
        Log.e("1111", "1111");
        progress.setVisibility(View.VISIBLE);

        bean b = (bean) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface cr = retrofit.create(ApiInterface.class);

        // challengeRequestBean body = new challengeRequestBean();
        RedeemReqBean body = new RedeemReqBean();

        //  Data data = new Data();
        DataRedReq data = new DataRedReq();

        body.setAction("redeem");

        data.setUserId(pref.getString("id", ""));
        data.setPaymentMode("Bank");
        data.setRedeemAmt(str_redeemAmount);
        data.setBankID("");
        data.setAccountNum(str_acountNum);
        data.setIfscCode(str_ifsc);
        data.setBankName(str_name);

        Log.d("iidd", pref.getString("id", ""));

        body.setData(data);

        Call<redeemBean> call = cr.redeenApi(body);


        call.enqueue(new Callback<redeemBean>() {

            @Override
            public void onResponse(Call<redeemBean> call, Response<redeemBean> response) {
                Log.e("222", "222");

                progress.setVisibility(View.GONE);

                if (response.body().getStatus_redeem().equals("1")) {
                    Toast.makeText(getApplicationContext(), "Successfully Payment Done", Toast.LENGTH_SHORT).show();
                    ed_redeemAmount.setText("");
                    ed_accountNumber_B.setText("");
                    ed_ifscCode_B.setText("");
                    ed_enterName_B.setText("");
                    text_selpayment.setText("");
                }

                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<redeemBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }


        });

    }

    private void vikash_Api() {

        Log.e("1111", "1111");
        progress.setVisibility(View.VISIBLE);

        bean b = (bean) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface cr = retrofit.create(ApiInterface.class);

        // challengeRequestBean body = new challengeRequestBean();
        RedeemReqBean body = new RedeemReqBean();

        //  Data data = new Data();
        DataRedReq data = new DataRedReq();

        body.setAction("redeem");

        data.setUserId(pref.getString("id", ""));
        data.setPaymentMode("bKash");
        data.setRedeemAmt(str_redeemAmount);
        data.setBankID(str_vikashID);
        data.setAccountNum("");
        data.setIfscCode("");
        data.setBankName("");

        Log.d("iidd", pref.getString("id", ""));

        body.setData(data);

        Call<redeemBean> call = cr.redeenApi(body);


        call.enqueue(new Callback<redeemBean>() {

            @Override
            public void onResponse(Call<redeemBean> call, Response<redeemBean> response) {
                Log.e("222", "222");

                progress.setVisibility(View.GONE);

                if (response.body().getStatus_redeem().equals("1")) {
                    Toast.makeText(getApplicationContext(), "Successfully Payment Done", Toast.LENGTH_SHORT).show();
                    ed_redeemAmount.setText("");
                    ed_vikashID.setText("");
                    text_selpayment.setText("");
                }

                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<redeemBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }


        });


    }

    private void payPal_Api() {

        Log.e("1111", "1111");
        progress.setVisibility(View.VISIBLE);

        bean b = (bean) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface cr = retrofit.create(ApiInterface.class);

        // challengeRequestBean body = new challengeRequestBean();
        RedeemReqBean body = new RedeemReqBean();

        //  Data data = new Data();
        DataRedReq data = new DataRedReq();

        body.setAction("redeem");

        data.setUserId(pref.getString("id", ""));
        data.setPaymentMode("Paypal");
        data.setRedeemAmt(str_redeemAmount);
        data.setBankID(str_paypal);
        data.setAccountNum("");
        data.setIfscCode("");
        data.setBankName("");

        Log.d("iidd", pref.getString("id", ""));

        body.setData(data);

        Call<redeemBean> call = cr.redeenApi(body);


        call.enqueue(new Callback<redeemBean>() {

            @Override
            public void onResponse(Call<redeemBean> call, Response<redeemBean> response) {
                Log.e("222", "222");

                progress.setVisibility(View.GONE);

                if (response.body().getStatus_redeem().equals("1")) {
                    Toast.makeText(getApplicationContext(), "Successfully Payment Done", Toast.LENGTH_SHORT).show();
                    ed_redeemAmount.setText("");
                    ed_paypalID.setText("");
                    text_selpayment.setText("");
                }

                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<redeemBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }


        });

    }


    private void setID() {
        recyclerView_dropDown = findViewById(R.id.recyclerView_dropDown);
        rel_back = findViewById(R.id.rel_back);
        rel_dropDown = findViewById(R.id.rel_dropdown);
        text_selpayment = findViewById(R.id.text_selected);
        btn_submit = findViewById(R.id.rank_dialog_button);
        ed_redeemAmount = findViewById(R.id.ed_redeemAmount);
        progress = findViewById(R.id.progress);

        rel_payPal = findViewById(R.id.rel_paypal);
        rel_vikash = findViewById(R.id.rel_vikash);
        rel_bankOfIndia = findViewById(R.id.rel_bankOfIndia);
        rel_paytm = findViewById(R.id.rel_paytm);

        rel_payPal.setVisibility(View.GONE);
        rel_vikash.setVisibility(View.GONE);
        rel_bankOfIndia.setVisibility(View.GONE);
        rel_paytm.setVisibility(View.GONE);

        ed_accountNumber_B = findViewById(R.id.ed_accountNumber);
        ed_ifscCode_B = findViewById(R.id.ed_ifscCode);
        ed_enterName_B = findViewById(R.id.ed_name);

        ed_paypalID = findViewById(R.id.ed_paypalID);
        ed_vikashID = findViewById(R.id.ed_vikashID);
        ed_paytmID = findViewById(R.id.ed_paytm_number);
    }


    private class DropDownAdapter extends RecyclerView.Adapter<DropDownAdapter.ViewHolder> {
        private Context context;
        private String[] Name;
        private int[] img;

        public DropDownAdapter(Context applicationContext, String[] strName, int[] str_img) {
            this.context = applicationContext;
            this.Name = strName;
            this.img = str_img;

        }

        @NonNull
        @Override
        public DropDownAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.reedem_icon, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull DropDownAdapter.ViewHolder viewHolder, final int i) {
            viewHolder.text_name.setText(Name[i]);
            viewHolder.image.setImageResource(img[i]);

            viewHolder.rel_click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    switch (i) {
                        case 0:
                            rel_payPal.setVisibility(View.VISIBLE);
                            rel_vikash.setVisibility(View.GONE);
                            rel_bankOfIndia.setVisibility(View.GONE);
                            rel_paytm.setVisibility(View.GONE);
                            tag = "1";
                            break;

                        case 1:
                            rel_vikash.setVisibility(View.VISIBLE);
                            rel_payPal.setVisibility(View.GONE);
                            rel_bankOfIndia.setVisibility(View.GONE);
                            rel_paytm.setVisibility(View.GONE);
                            tag = "2";
                            break;

                        case 2:
                            rel_bankOfIndia.setVisibility(View.VISIBLE);
                            rel_vikash.setVisibility(View.GONE);
                            rel_payPal.setVisibility(View.GONE);
                            rel_paytm.setVisibility(View.GONE);
                            tag = "3";
                            break;

                        case 3:
                            rel_paytm.setVisibility(View.VISIBLE);
                            rel_bankOfIndia.setVisibility(View.GONE);
                            rel_vikash.setVisibility(View.GONE);
                            rel_payPal.setVisibility(View.GONE);
                            tag = "4";
                            break;

                    }

                    text_selpayment.setText(Name[i]);
                    recyclerView_dropDown.setVisibility(View.GONE);
                }
            });

        }

        @Override
        public int getItemCount() {
            return Name.length;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView text_name;
            private RelativeLayout rel_click;
            private ImageView image;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                text_name = itemView.findViewById(R.id.text_country_name);
                rel_click = itemView.findViewById(R.id.rel);
                image = itemView.findViewById(R.id.img);
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        progress.setVisibility(View.VISIBLE);


        bean b = (bean) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        ApiInterface cr = retrofit.create(ApiInterface.class);

        challengeRequestBean body = new challengeRequestBean();

        Data data = new Data();

        body.setAction("redeem_history");

        data.setUserId(pref.getString("id", ""));

        body.setData(data);

        Call<redeemHistoryBean> call = cr.redeemHistory(body);

        call.enqueue(new Callback<redeemHistoryBean>() {
            @Override
            public void onResponse(Call<redeemHistoryBean> call, Response<redeemHistoryBean> response) {


                if (response.body().getStatus().equals("1"))
                {
                    redeemAdapter.setGridData(response.body().getData());
                }
                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<redeemHistoryBean> call, Throwable t) {
progress.setVisibility(View.GONE);
            }
        });

    }

    class RedeemAdapter extends RecyclerView.Adapter<RedeemAdapter.ViewHolder>
    {

        Context context;
        List<Datum> list = new ArrayList<>();


        public RedeemAdapter(Context context , List<Datum> list)
        {
            this.context = context;
            this.list = list;
        }

        public void setGridData(List<Datum> list)
        {
            this.list = list;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.redeem_list_model , viewGroup , false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

            Datum item = list.get(i);

            holder.amount.setText("Amount : $ " + item.getRedeemAmount());
            holder.mode.setText("Mode : " + item.getPaymentMode());
            holder.date.setText(item.getCreateDate());
            holder.status.setText(item.getStatus());


        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder
        {

            TextView amount , mode , date , status;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                amount = itemView.findViewById(R.id.textView133);
                mode = itemView.findViewById(R.id.textView134);
                date = itemView.findViewById(R.id.textView135);
                status = itemView.findViewById(R.id.textView136);
            }
        }
    }

}
