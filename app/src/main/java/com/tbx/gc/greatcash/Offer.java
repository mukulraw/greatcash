package com.tbx.gc.greatcash;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tbx.gc.greatcash.CountryPOJO.DataCountry;
import com.tbx.gc.greatcash.CountryPOJO.countryBean;
import com.tbx.gc.greatcash.challengeRequestPOJO.challengeRequestBean;
import com.tbx.gc.greatcash.comboPOJO.ComboItem;
import com.tbx.gc.greatcash.comboPOJO.Datum;
import com.tbx.gc.greatcash.comboPOJO.comboBean;
import com.tbx.gc.greatcash.offerRequestPOHO.offerRequestBean;
import com.tbx.gc.greatcash.transactionRequestPOJO.Data;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Offer extends Fragment {

    RecyclerView grid;

    GridLayoutManager manager;

    OfferAdapter adapter;
    List<Datum> list;
    ProgressBar progress;
    SharedPreferences pref;


    private List<String> countryList = new ArrayList<>();
    private List<String> countryId = new ArrayList<>();

    String cid;
    Spinner spinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.offer, container, false);

        pref = getContext().getSharedPreferences("pref", Context.MODE_PRIVATE);

        list = new ArrayList<>();

        grid = view.findViewById(R.id.grid);
        progress = view.findViewById(R.id.progress);

        spinner = view.findViewById(R.id.spinner);
        manager = new GridLayoutManager(getContext(), 1);

        adapter = new OfferAdapter(getActivity(), list);

        grid.setLayoutManager(manager);

        grid.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                loadData(countryId.get(i));
                cid = countryId.get(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        return view;

    }

    private void country_Api() {
        progress.setVisibility(View.VISIBLE);

        bean b = (bean) getContext().getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface cr = retrofit.create(ApiInterface.class);

        challengeRequestBean body = new challengeRequestBean();

        com.tbx.gc.greatcash.challengeRequestPOJO.Data data = new com.tbx.gc.greatcash.challengeRequestPOJO.Data();

        body.setAction("country_list");

        data.setUserId(pref.getString("id", ""));

        Log.e("iidd", pref.getString("id", ""));

        body.setData(data);

        Call<countryBean> call = cr.countryApi(body);
        call.enqueue(new Callback<countryBean>() {

            @Override
            public void onResponse(Call<countryBean> call, Response<countryBean> response) {
                Log.e("222", "222");

                progress.setVisibility(View.GONE);

                if (response.body().getStatus_country().equals("1")) {

                    for (int i = 0; i < response.body().getData_country().size(); i++) {
                        countryList.add(response.body().getData_country().get(i).getCountryName());
                        countryId.add(response.body().getData_country().get(i).getCountry_id());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                            android.R.layout.simple_list_item_1, countryList);


                    spinner.setAdapter(adapter);


                }

                //progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<countryBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });


    }


    @Override
    public void onResume() {
        super.onResume();

        country_Api();

    }

    public void loadData(String countryId) {


        progress.setVisibility(View.VISIBLE);


        bean b = (bean) getContext().getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        ApiInterface cr = retrofit.create(ApiInterface.class);

        offerRequestBean body = new offerRequestBean();

        com.tbx.gc.greatcash.offerRequestPOHO.Data data = new com.tbx.gc.greatcash.offerRequestPOHO.Data();

        body.setAction("combo_offer");

        data.setUserId(pref.getString("id", ""));

        data.setCountryId(countryId);

        Log.d("iidd", pref.getString("id", ""));

        body.setData(data);

        Call<comboBean> call = cr.comboOffers(body);

        call.enqueue(new Callback<comboBean>() {
            @Override
            public void onResponse(Call<comboBean> call, Response<comboBean> response) {

                if (response.body().getStatus().equals("1")) {
                    adapter.setGridData(response.body().getData());

                }


                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<comboBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });


    }

    public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.MyViewHolder> {

        Context context;
        List<Datum> list = new ArrayList<>();
        List<ComboItem> comboItem_List = new ArrayList<>();


        public OfferAdapter(Context context, List<Datum> list) {
            this.context = context;
            this.list = list;

        }

        public void setGridData(List<Datum> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(context).inflate(R.layout.offer_list_model, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

            final Datum item = list.get(position);

            holder.text_heading.setText(item.getHeading());
            holder.text_desc.setText(item.getDescription());
            holder.text_earnAmt.setText(item.getEarnAmount());
            comboItem_List = item.getComboItem();
            Log.e("lidttttt", "" + comboItem_List);


//            ImageLoader loader = ImageLoader.getInstance();
//
//            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();
//
//            loader.displayImage(item.getImage() , holder.image , options);
//
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    String url = item.getRedirectUrl();
//                    Intent i = new Intent(Intent.ACTION_VIEW);
//                    i.setData(Uri.parse(url));
//                    context.startActivity(i);
//
//                }
//            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent intent = new Intent(context, ComboitemList.class);
                    intent.putExtra("id", item.getComboId());
                    intent.putExtra("cid", cid);
                    context.startActivity(intent);

                }
            });


        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            private TextView text_heading, text_desc, text_earnAmt;
            private CardView cardView;


            public MyViewHolder(View itemView) {
                super(itemView);

                text_heading = itemView.findViewById(R.id.text_heading);
                text_desc = itemView.findViewById(R.id.text_des_value);
                text_earnAmt = itemView.findViewById(R.id.text_earnAmt_value);
                cardView = itemView.findViewById(R.id.cardView_combo);
            }
        }
    }

}
