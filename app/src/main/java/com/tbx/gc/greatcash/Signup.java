package com.tbx.gc.greatcash;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tbx.gc.greatcash.CityPOJO.CityBean;
import com.tbx.gc.greatcash.CityPOJO.DataCity;
import com.tbx.gc.greatcash.CountryPOJO.DataCountry;
import com.tbx.gc.greatcash.CountryPOJO.countryBean;
import com.tbx.gc.greatcash.EarnMorePOJO.DataEarn;
import com.tbx.gc.greatcash.EarnMorePOJO.earnMoreBean;
import com.tbx.gc.greatcash.StatePOJO.DataState;
import com.tbx.gc.greatcash.StatePOJO.StateBean;
import com.tbx.gc.greatcash.challengeRequestPOJO.challengeRequestBean;
import com.tbx.gc.greatcash.registerRequestPOJO.Data;
import com.tbx.gc.greatcash.registerRequestPOJO.registerRequestBean;
import com.tbx.gc.greatcash.registerResponsePOJO.registerResponseBean;

import java.nio.channels.InterruptedByTimeoutException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import github.nisrulz.easydeviceinfo.base.EasyDeviceMod;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Signup extends AppCompatActivity implements LocationListener {

    Button signup;
    ProgressBar progress;
    ProgressBar progressBar_country;
    ProgressBar progressBar_state;
    ProgressBar progressBar_city;


    EditText name, email, password, phone;

    TextView dob,country,state,city;
    private String p;

    SharedPreferences pref;
    SharedPreferences.Editor edit;
    double latitude, longitude;
    boolean isGPSEnable = false;
    boolean isNetworkEnable = false;
    LocationManager locationManager;
    Location location;
    private String lat="",lng="";

    private int mDay,mMonth,mYear;

    private RecyclerView recyclerView_country;
    private RecyclerView.LayoutManager layoutManager;

    private RecyclerView recyclerView_state;
    private RecyclerView.LayoutManager layoutManager_state;

    private RecyclerView recyclerView_city;
    private RecyclerView.LayoutManager layoutManager_city;

    private CountryAdapter adapter_country;
    private List<DataCountry> countryList=new ArrayList<>();

    private StateAdapter adapter_state;
    private List<DataState>StateList=new ArrayList<>();

    private CityAdapter adapter_city;
    private List<DataCity>cityList=new ArrayList<>();

    AlertDialog dialog;
    AlertDialog dialog_state;
    AlertDialog dialog_city;
    String countryID,stateID,cityID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getLocation();

        pref = getSharedPreferences("pref" , Context.MODE_PRIVATE);
        edit = pref.edit();

        signup = findViewById(R.id.button3);
        progress = findViewById(R.id.progressBar3);


        name = findViewById(R.id.editText);
        email = findViewById(R.id.editText2);
        password = findViewById(R.id.editText3);
        dob = findViewById(R.id.editText4);
        phone = findViewById(R.id.editText5);
        country = findViewById(R.id.editText6);
        state = findViewById(R.id.editText7);
        city = findViewById(R.id.editText8);

        dob.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                final Calendar calendar=Calendar.getInstance();
                mYear=calendar.get(Calendar.YEAR);
                mMonth=calendar.get(Calendar.MONTH);
                mDay=calendar.get(Calendar.DAY_OF_MONTH);

//                Log.e("year",""+mYear);
//                Log.e("month",""+mMonth);
//                Log.e("date",""+mDay);

                DatePickerDialog datePickerDialog=new DatePickerDialog(Signup.this, new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthofyear, int dayofmonth)
                    {
                        mYear=year;
                        mMonth=monthofyear;
                        mDay=dayofmonth;

                        dob.setText(new StringBuilder().append(mYear).append("-").append(mMonth+1).append("-").append(mDay));
                    }
                },mYear,mMonth,mDay);
                datePickerDialog.show();

            }
        });


        country.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                LayoutInflater layoutInflater=getLayoutInflater();
                View alertLayout =layoutInflater.inflate(R.layout.country_dialog,null);

                AlertDialog.Builder alertDialog=new AlertDialog.Builder(Signup.this);
                alertDialog.setView(alertLayout);
                dialog=alertDialog.create();
                Button btn_ok;

                recyclerView_country=alertLayout.findViewById(R.id.recyclerView_country);
                progressBar_country=alertLayout.findViewById(R.id.progressBar_country);


                layoutManager=new LinearLayoutManager(getApplicationContext());
                recyclerView_country.setLayoutManager(layoutManager);

                adapter_country=new CountryAdapter(getApplicationContext(),countryList);
                recyclerView_country.setAdapter(adapter_country);

                country_Api();

                dialog.show();

            }
        });


        state.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String text_country=country.getText().toString();
                if(text_country.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Please select country first",Toast.LENGTH_SHORT).show();
                }

                else
                    {

                        LayoutInflater layoutInflater=getLayoutInflater();
                        View alertLayout =layoutInflater.inflate(R.layout.state_dialog,null);

                        AlertDialog.Builder alertDialog=new AlertDialog.Builder(Signup.this);
                        alertDialog.setView(alertLayout);
                        dialog_state=alertDialog.create();

                        recyclerView_state=alertLayout.findViewById(R.id.recyclerView_state);
                        progressBar_state=alertLayout.findViewById(R.id.progressBar_state);


                        layoutManager_state=new LinearLayoutManager(getApplicationContext());
                        recyclerView_state.setLayoutManager(layoutManager_state);

                        adapter_state=new StateAdapter(getApplicationContext(),StateList);
                        recyclerView_state.setAdapter(adapter_state);

                        state_Api();

                        dialog_state.show();
                    }

            }
        });


        city.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String str_country=country.getText().toString();
                String str_state=state.getText().toString();
                if(str_country.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Please select country",Toast.LENGTH_SHORT).show();
                }
                else if(str_state.equals(""))
                {
                  Toast.makeText(getApplicationContext(),"Please select state",Toast.LENGTH_SHORT).show();
                }
                else
                    {
                        LayoutInflater layoutInflater=getLayoutInflater();
                        View alertLayout =layoutInflater.inflate(R.layout.city_dialog,null);

                        AlertDialog.Builder alertDialog=new AlertDialog.Builder(Signup.this);
                        alertDialog.setView(alertLayout);
                        dialog_city=alertDialog.create();

                        recyclerView_city=alertLayout.findViewById(R.id.recyclerView_city);
                        progressBar_city=alertLayout.findViewById(R.id.progressBar_city);


                        layoutManager_city=new LinearLayoutManager(getApplicationContext());
                        recyclerView_city.setLayoutManager(layoutManager_city);

                        adapter_city=new CityAdapter(getApplicationContext(),cityList);
                        recyclerView_city.setAdapter(adapter_city);

                        city_Api();

                        dialog_city.show();

                    }

            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String n = name.getText().toString();
                String e = email.getText().toString();
                p = password.getText().toString();
                String d = dob.getText().toString();
                String ph = phone.getText().toString();
                String c = country.getText().toString();
                String s = state.getText().toString();
                String ci = city.getText().toString();

                if (n.length() > 0) {
                    if (e.length() > 0) {
                        if (p.length() > 0) {
                            if (d.length() > 0) {
                                if (ph.length() > 0) {
                                    if (c.length() > 0) {
                                        if (s.length() > 0) {
                                            if (ci.length() > 0) {


                                                progress.setVisibility(View.VISIBLE);


                                                bean b = (bean) getApplicationContext();

                                                Retrofit retrofit = new Retrofit.Builder()
                                                        .baseUrl(b.BASE_URL)
                                                        .addConverterFactory(ScalarsConverterFactory.create())
                                                        .addConverterFactory(GsonConverterFactory.create())
                                                        .build();


                                                ApiInterface cr = retrofit.create(ApiInterface.class);

                                                registerRequestBean body = new registerRequestBean();

                                                body.setAction("register");
                                                Data data = new Data();

                                                SharedPreferences fcmPref = getSharedPreferences("fcm" , Context.MODE_PRIVATE);

                                                String keey = fcmPref.getString("token" , "");



                                                EasyDeviceMod easyDeviceMod = new EasyDeviceMod(Signup.this);


                                                Log.d("deviceid" , easyDeviceMod.getDevice());

                                                data.setName(n);
                                                data.setEmail(e);
                                                data.setPassword(p);
                                                data.setDob(d);
                                                data.setPhone(ph);
                                                data.setCountry(c);
                                                data.setState(s);
                                                data.setCity(ci);
                                                data.setDeviceId(easyDeviceMod.getDevice());
                                                data.setFirbaseId(keey);

                                                body.setData(data);

                                                Call<registerResponseBean> call = cr.register(body);

                                                call.enqueue(new Callback<registerResponseBean>() {
                                                    @Override
                                                    public void onResponse(Call<registerResponseBean> call, Response<registerResponseBean> response) {

                                                        if (response.body().getStatus().equals("1"))
                                                        {

                                                            //edit.putString("id" , response.body().getData().getUserId());
                                                            edit.putString("refId" , response.body().getData().getRefId());
                                                            edit.putString("parentId" , response.body().getData().getParentId());
                                                            edit.putString("name" , response.body().getData().getName());
                                                            edit.putString("email" , response.body().getData().getEmail());
                                                            edit.putString("phone" , response.body().getData().getMobile());
                                                            edit.putString("pic" , response.body().getData().getUserPic());
                                                            edit.putString("dob" , response.body().getData().getBirthDate());
                                                            edit.putString("country" , response.body().getData().getCountry());
                                                            edit.putString("state" , response.body().getData().getState());
                                                            edit.putString("city" , response.body().getData().getCity());
                                                            edit.putString("pid" , response.body().getData().getPid());
                                                            edit.putString("password" , password.getText().toString());
                                                            edit.putString("passwordSave",p);
                                                            edit.apply();


                                                            Intent intent = new Intent(Signup.this , OTP.class);
                                                            intent.putExtra("otp" , response.body().getData().getOtp());
                                                            startActivity(intent);
                                                            finish();

                                                        }
                                                        else
                                                        {
                                                            Toast.makeText(Signup.this , response.body().getMessage() , Toast.LENGTH_SHORT).show();
                                                        }


                                                        progress.setVisibility(View.GONE);
                                                    }

                                                    @Override
                                                    public void onFailure(Call<registerResponseBean> call, Throwable t) {
                                                        progress.setVisibility(View.GONE);
                                                    }
                                                });


                                            } else {
                                                Toast.makeText(Signup.this, "Invalid city", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(Signup.this, "Invalid state", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(Signup.this, "Invalid country", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(Signup.this, "Invalid phone", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(Signup.this, "Invalid dob", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Signup.this, "Invalid password", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Signup.this, "Invalid email", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(Signup.this, "Invalid name", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void city_Api()
    {
        progressBar_city.setVisibility(View.VISIBLE);

        bean b = (bean) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface cr=retrofit.create(ApiInterface.class);

        challengeRequestBean body = new challengeRequestBean();

        com.tbx.gc.greatcash.challengeRequestPOJO.Data data=new com.tbx.gc.greatcash.challengeRequestPOJO.Data();

        body.setAction("city_list");

        data.setUserId(pref.getString("id", ""));

        data.setCityId(stateID);

        Log.e("iidd" , pref.getString("id" , ""));

        body.setData(data);

        Call<CityBean> call = cr.cityApi(body);

        call.enqueue(new Callback<CityBean>()
        {

            @Override
            public void onResponse(Call<CityBean> call, Response<CityBean> response)
            {
                Log.e("222","222");

                progressBar_city.setVisibility(View.GONE);

                if (response.body().getStatus_city().equals("1"))
                {
                    adapter_city.setGridData(response.body().getData_city());
                    Log.e("3333","3333");
                }

                //progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<CityBean> call, Throwable t)
            {
                progressBar_country.setVisibility(View.GONE);
            }
        });

    }

    private void state_Api()
    {
        progressBar_state.setVisibility(View.VISIBLE);

        bean b = (bean) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface cr=retrofit.create(ApiInterface.class);

        challengeRequestBean body = new challengeRequestBean();

        com.tbx.gc.greatcash.challengeRequestPOJO.Data data=new com.tbx.gc.greatcash.challengeRequestPOJO.Data();

        body.setAction("state_list");

        data.setUserId(pref.getString("id", ""));

        data.setCountryId(countryID);

        Log.e("iidd" , pref.getString("id" , ""));

        body.setData(data);

        Call<StateBean> call = cr.stateApi(body);

        call.enqueue(new Callback<StateBean>()
        {

            @Override
            public void onResponse(Call<StateBean> call, Response<StateBean> response)
            {
                Log.e("222","222");

                progressBar_state.setVisibility(View.GONE);

                if (response.body().getStatus_state().equals("1"))
                {

                    adapter_state.setGridData(response.body().getData_state());
                    Log.e("3333","3333");
                }

                //progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<StateBean> call, Throwable t) {
                progressBar_country.setVisibility(View.GONE);
            }
        });



    }

    private void country_Api()
    {

        progressBar_country.setVisibility(View.VISIBLE);

        bean b = (bean) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface cr=retrofit.create(ApiInterface.class);

        challengeRequestBean body = new challengeRequestBean();

        com.tbx.gc.greatcash.challengeRequestPOJO.Data data=new com.tbx.gc.greatcash.challengeRequestPOJO.Data();

        body.setAction("country_list");

        data.setUserId(pref.getString("id", ""));

        Log.e("iidd" , pref.getString("id" , ""));

        body.setData(data);

        Call<countryBean> call = cr.countryApi(body);


        call.enqueue(new Callback<countryBean>()
        {

            @Override
            public void onResponse(Call<countryBean> call, Response<countryBean> response)
            {
                Log.e("222","222");

                progressBar_country.setVisibility(View.GONE);

                if (response.body().getStatus_country().equals("1"))
                {

                    adapter_country.setGridData(response.body().getData_country());
                    Log.e("3333","3333");
                }

                //progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<countryBean> call, Throwable t) {
                progressBar_country.setVisibility(View.GONE);
            }
        });


    }

    private void getLocation()
    {
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnable && !isNetworkEnable)
        {

        } else {

            if (isNetworkEnable) {
                location = null;
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, this);
                if (locationManager != null) {
                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {

                        Log.e("latitude", location.getLatitude() + "");
                        Log.e("longitude", location.getLongitude() + "");

                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        lat = String.valueOf(latitude);
                        lng = String.valueOf(longitude);

                        Toast.makeText(getApplicationContext(),lat+lng,Toast.LENGTH_SHORT).show();

                        Log.e("latitudeeee",""+latitude);
                        Log.e("logitudeeee",""+longitude);

                    }
                }
            }

            if (isGPSEnable) {
                location = null;
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
                if (locationManager != null) {
                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location!=null){
                        Log.e("latitude",location.getLatitude()+"");
                        Log.e("longitude",location.getLongitude()+"");

                        latitude = location.getLatitude();
                        longitude = location.getLongitude();

                        lat = String.valueOf(latitude);
                        lng = String.valueOf(longitude);

                        Log.e("latitudeeee",""+latitude);
                        Log.e("logitudeeee",""+longitude);

                        Toast.makeText(getApplicationContext(),lat+lng,Toast.LENGTH_SHORT).show();

                        //  Toast.makeText(this, "gps"+lat+lng, Toast.LENGTH_SHORT).show();

                    }
                }
            }
        }

    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    private class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder>
    {
        private Context context;
        private List<DataCountry>dataCountryList;


        public CountryAdapter(Context applicationContext, List<DataCountry> countryList)
        {
            this.context=applicationContext;
            this.dataCountryList=countryList;
        }


        public void setGridData(List<DataCountry> list)
        {
            this.dataCountryList = list;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public CountryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.country_icon , viewGroup , false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CountryAdapter.ViewHolder viewHolder, int i)
        {
            final DataCountry item = dataCountryList.get(i);

            viewHolder.text_countryName.setText(item.getCountryName());
            Log.e("textname",""+item.getCountryName());

            viewHolder.relative.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    String countryName=item.getCountryName();
                    country.setText(countryName);

                    countryID=item.getCountry_id();

                    dialog.dismiss();
                }
            });
        }

        @Override
        public int getItemCount() {
            return dataCountryList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder
        {
            private TextView text_countryName;
            private RelativeLayout relative;

            public ViewHolder(@NonNull View itemView)
            {
                super(itemView);
                text_countryName=itemView.findViewById(R.id.text_country_name);
                relative=itemView.findViewById(R.id.rel);
            }
        }
    }





    private class StateAdapter extends RecyclerView.Adapter<StateAdapter.ViewHolder>
    {
        private Context context;
        private List<DataState>dataStateList;

        public StateAdapter(Context applicationContext, List<DataState> stateList)
        {
            this.context=applicationContext;
            this.dataStateList=stateList;
        }

        public void setGridData(List<DataState> list)
        {
            this.dataStateList = list;
            notifyDataSetChanged();
        }


        @NonNull
        @Override
        public StateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.country_icon , viewGroup , false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull StateAdapter.ViewHolder viewHolder, int i)
        {

            final DataState item = dataStateList.get(i);

            viewHolder.text_stateName.setText(item.getStateName());

            viewHolder.relative.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    String stateName=item.getStateName();
                    state.setText(stateName);

                    stateID=item.getStateID();

                    dialog_state.dismiss();
                }
            });

        }

        @Override
        public int getItemCount()
        {
            return dataStateList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder
        {
            private TextView text_stateName;
            private RelativeLayout relative;

            public ViewHolder(@NonNull View itemView)
            {
                super(itemView);
                text_stateName=itemView.findViewById(R.id.text_country_name);
                relative=itemView.findViewById(R.id.rel);

            }
        }
    }





    private class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder>
    {
        private Context context;
        private List<DataCity>dataCityList;

        public CityAdapter(Context applicationContext, List<DataCity> cityList)
        {
            this.context=applicationContext;
            this.dataCityList=cityList;
        }

        @NonNull
        @Override
        public CityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.country_icon , viewGroup , false);
            return new ViewHolder(view);
        }

        public void setGridData(List<DataCity> list)
        {
            this.dataCityList = list;
            notifyDataSetChanged();
        }


        @Override
        public void onBindViewHolder(@NonNull CityAdapter.ViewHolder viewHolder, int i)
        {

            final DataCity item = dataCityList.get(i);

            viewHolder.text_stateName.setText(item.getCityName());

            viewHolder.relative.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    String cityName=item.getCityName();
                    city.setText(cityName);
                    Log.e("citynameeee",""+item.getCityName());

                    cityID=item.getCityID();

                    dialog_city.dismiss();
                }
            });

        }

        @Override
        public int getItemCount()
        {
            return dataCityList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder
        {
            private TextView text_stateName;
            private RelativeLayout relative;

            public ViewHolder(@NonNull View itemView)
            {
                super(itemView);

                text_stateName=itemView.findViewById(R.id.text_country_name);
                relative=itemView.findViewById(R.id.rel);
            }
        }
    }
}
