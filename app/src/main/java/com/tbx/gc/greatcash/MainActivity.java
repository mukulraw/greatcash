package com.tbx.gc.greatcash;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.heyzap.sdk.ads.HeyzapAds;
import com.heyzap.sdk.ads.VideoAd;
import com.tbx.gc.greatcash.challengeRequestPOJO.Data;
import com.tbx.gc.greatcash.challengeRequestPOJO.challengeRequestBean;
import com.tbx.gc.greatcash.dashboardPOJO.dashboardBean;
import com.tbx.gc.greatcash.hotListPOJO.hotListBean;
import com.tbx.gc.greatcash.rateUsPojo.DataRateUs;
import com.tbx.gc.greatcash.rateUsPojo.rateUsBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;

    DrawerLayout drawerLayout;
    LinearLayout replace;

    TextView dashboard , challenge , shopping , name;

    TextView wallet , idActivator , invite , notification , offer , transaction , network , help , achievement  ,faq , survey , video , youtube , about , regId,setting,rateus,createTicket,kyc,earnmore,hot_list,help_menu;

    SharedPreferences pref;
    ProgressBar progress;
    float rating_value;
    public static String str_totalAmount;

    private List<DataRateUs>dataRateUsList=new ArrayList<>();

    //private RewardedVideoAd mRewardedVideoAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        pref = getSharedPreferences("pref" , Context.MODE_PRIVATE);

        toolbar = findViewById(R.id.toolbar);
        replace = findViewById(R.id.replace);
        name = findViewById(R.id.name);
        video = findViewById(R.id.video);

        dashboard = findViewById(R.id.dashboard);
        challenge = findViewById(R.id.challenge);
        shopping = findViewById(R.id.shopping);
        idActivator = findViewById(R.id.id_activator);
        invite = findViewById(R.id.invite_and_earn);
        offer = findViewById(R.id.offer);
        transaction = findViewById(R.id.transaction);
        earnmore = findViewById(R.id.earnmore);
        network = findViewById(R.id.network);
        help = findViewById(R.id.help);
        wallet = findViewById(R.id.wallet);
        achievement = findViewById(R.id.achievement);
        notification = findViewById(R.id.notification);
        setting = findViewById(R.id.setting);
        kyc  = findViewById(R.id.kyc);
        rateus = findViewById(R.id.rate);
        createTicket = findViewById(R.id.createTicket);
        survey = findViewById(R.id.online_survey);
        faq = findViewById(R.id.faq);
        about = findViewById(R.id.about);
        regId = findViewById(R.id.reg_id);
        hot_list=findViewById(R.id.hot_list);
        help_menu=findViewById(R.id.help_icon);


        youtube = findViewById(R.id.youtube);
        progress = findViewById(R.id.progress);


        setSupportActionBar(toolbar);

        name.setText(pref.getString("name" , ""));
        regId.setText("Reg.ID: " + pref.getString("refId" , ""));

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setTitleTextColor(Color.WHITE);

        drawerLayout = findViewById(R.id.drawer);

        final ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout , toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();
        actionBarDrawerToggle.setDrawerIndicatorEnabled(false);

        toolbar.setNavigationIcon(R.drawable.ic_menu);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (drawerLayout.isDrawerOpen(GravityCompat.START))
                {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else
                {
                    drawerLayout.openDrawer(GravityCompat.START);
                }

            }
        });







       /* drawerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //finish();

                if (drawerLayout.isDrawerOpen(GravityCompat.START)){

                    drawerLayout.closeDrawer(GravityCompat.START);

                }else {

                    drawerLayout.openDrawer(GravityCompat.START);
                }


            }
        });*/

       help_menu.setOnClickListener(new View.OnClickListener()
       {
           @Override
           public void onClick(View view)
           {
               FragmentManager fm = getSupportFragmentManager();
               FragmentTransaction ft = fm.beginTransaction();
               Help help=new Help();
               ft.replace(R.id.replace,help);
               ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
               //ft.addToBackStack(null);
               ft.commit();
               drawerLayout.closeDrawer(GravityCompat.START);


           }
       });

       final bean b = (bean)getApplicationContext();

       dashboard.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               if (!b.locked)
               {
                   FragmentManager fm = getSupportFragmentManager();
                   FragmentTransaction ft = fm.beginTransaction();
                   Home frag1 = new Home();
                   Bundle b = new Bundle();
                   b.putInt("position" , 0);
                   frag1.setArguments(b);
                   ft.replace(R.id.replace , frag1);
                   ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                   //ft.addToBackStack(null);
                   ft.commit();

                   drawerLayout.closeDrawer(GravityCompat.START);

               }
               else
               {
                   Toast.makeText(MainActivity.this , "This feature is b.locked, please unlock it by completing the challenge" , Toast.LENGTH_SHORT).show();
               }

           }
       });



       challenge.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               FragmentManager fm = getSupportFragmentManager();
               FragmentTransaction ft = fm.beginTransaction();
               Home frag1 = new Home();
               Bundle b = new Bundle();
               b.putInt("position" , 1);
               frag1.setArguments(b);
               ft.replace(R.id.replace , frag1);
               ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
               //ft.addToBackStack(null);
               ft.commit();
               drawerLayout.closeDrawer(GravityCompat.START);

           }
       });

        kyc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                com.tbx.gc.greatcash.kyc k = new kyc();

                ft.replace(R.id.replace , k);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                //ft.addToBackStack(null);
                ft.commit();
                drawerLayout.closeDrawer(GravityCompat.START);

            }
        });

       shopping.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               if (!b.locked)
               {
                   FragmentManager fm = getSupportFragmentManager();
                   FragmentTransaction ft = fm.beginTransaction();
                   Home frag1 = new Home();
                   Bundle b = new Bundle();
                   b.putInt("position" , 2);
                   frag1.setArguments(b);
                   ft.replace(R.id.replace , frag1);
                   ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                   //ft.addToBackStack(null);
                   ft.commit();
                   drawerLayout.closeDrawer(GravityCompat.START);
               }
               else
               {
                   Toast.makeText(MainActivity.this , "This feature is b.locked, please unlock it by completing the challenge" , Toast.LENGTH_SHORT).show();
               }


           }
       });

       name.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {


               showDialog();

           }
       });


       wallet.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               if (!b.locked) {

                   FragmentManager fm = getSupportFragmentManager();
                   FragmentTransaction ft = fm.beginTransaction();
                   Wallat frag1 = new Wallat();
                   ft.replace(R.id.replace, frag1);
                   ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                   //ft.addToBackStack(null);
                   ft.commit();
                   drawerLayout.closeDrawer(GravityCompat.START);
               }
               else
               {
                   Toast.makeText(MainActivity.this , "This feature is b.locked, please unlock it by completing the challenge" , Toast.LENGTH_SHORT).show();

               }
           }
       });


        idActivator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                IDActivator frag1 = new IDActivator();
                ft.replace(R.id.replace , frag1);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                //ft.addToBackStack(null);
                ft.commit();
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        invite.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                InviteEarn frag1 = new InviteEarn();
                ft.replace(R.id.replace , frag1);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                //ft.addToBackStack(null);
                ft.commit();
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Notification frag1 = new Notification();
                ft.replace(R.id.replace , frag1);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                //ft.addToBackStack(null);
                ft.commit();
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!b.locked) {

                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    CountryOffers frag1 = new CountryOffers();
                    ft.replace(R.id.replace, frag1);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                    //ft.addToBackStack(null);
                    ft.commit();
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else
                {
                    Toast.makeText(MainActivity.this , "This feature is b.locked, please unlock it by completing the challenge" , Toast.LENGTH_SHORT).show();

                }

                }
        });

        transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!b.locked) {

                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    TransactionHistory frag1 = new TransactionHistory();
                    ft.replace(R.id.replace, frag1);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                    //ft.addToBackStack(null);
                    ft.commit();
                    drawerLayout.closeDrawer(GravityCompat.START);

                }
                else
                {
                    Toast.makeText(MainActivity.this , "This feature is b.locked, please unlock it by completing the challenge" , Toast.LENGTH_SHORT).show();

                }
            }
        });

        earnmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!b.locked) {

                    Intent intent =new Intent(MainActivity.this,EarnMore.class);
                    startActivity(intent);

//

//                    Intent intent = new Intent(MainActivity.this , TrasnsactionList.class);
//                    intent.putExtra("userId" , pref.getString("id" , ""));
//                    intent.putExtra("type" , "earn_more");
//                    startActivity(intent);

                }
                else
                {
                    Toast.makeText(MainActivity.this , "This feature is b.locked, please unlock it by completing the challenge" , Toast.LENGTH_SHORT).show();

                }
            }
        });

        network.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!b.locked) {

                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    TreeView frag1 = new TreeView();
                    ft.replace(R.id.replace, frag1);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                    //ft.addToBackStack(null);
                    ft.commit();
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else
                {
                    Toast.makeText(MainActivity.this , "This feature is b.locked, please unlock it by completing the challenge" , Toast.LENGTH_SHORT).show();

                }
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ViewTicket frag1 = new ViewTicket();
                ft.replace(R.id.replace , frag1);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                //ft.addToBackStack(null);
                ft.commit();
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        achievement.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                if (!b.locked) {


                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    Uplinears frag1 = new Uplinears();
                    ft.replace(R.id.replace, frag1);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                    //ft.addToBackStack(null);
                    ft.commit();
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else
                {
                    Toast.makeText(MainActivity.this , "This feature is b.locked, please unlock it by completing the challenge" , Toast.LENGTH_SHORT).show();

                }
            }
        });



        hot_list.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                if (!b.locked) {


                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();

                    HotLIst hot_list=new HotLIst();
                    ft.replace(R.id.replace,hot_list);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                    //ft.addToBackStack(null);
                    ft.commit();
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else
                {
                    Toast.makeText(MainActivity.this , "This feature is b.locked, please unlock it by completing the challenge" , Toast.LENGTH_SHORT).show();

                }

            }
        });

        faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Faq frag1 = new Faq();
                ft.replace(R.id.replace , frag1);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                //ft.addToBackStack(null);
                ft.commit();
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        survey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Survey frag1 = new Survey();
                ft.replace(R.id.replace , frag1);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                //ft.addToBackStack(null);
                ft.commit();
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (!b.locked) {


                    Intent intent = new Intent(MainActivity.this , AdVideos.class);
                    startActivity(intent);
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else
                {
                    Toast.makeText(MainActivity.this , "This feature is b.locked, please unlock it by completing the challenge" , Toast.LENGTH_SHORT).show();

                }
            }
        });

        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this , YouTube.class);
              //  intent.putExtra("title" , "Youtube");
                intent.putExtra("url" , "https://www.youtube.com/channel/UCA98kCuybWJh5aDJHuEkzEw ");
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);

              //  https://www.youtube.com/watch?v=Y6RxiIuhJvI&t=3s

            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this , WWView.class);
                //intent.putExtra("title" , "About Us");
                //intent.putExtra("url" , "https://champcash.com/Home/About");
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);

            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent intent=new Intent(MainActivity.this,Support.class);
//                startActivity(intent);

                Intent intent = new Intent(MainActivity.this, ChangePassword.class);
                intent.putExtra("id", pref.getString("id", ""));
                startActivity(intent);

            }
        });


        createTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ViewTicket viewTicket = new ViewTicket();
                ft.replace(R.id.replace , viewTicket);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                //ft.addToBackStack(null);
                ft.commit();
                drawerLayout.closeDrawer(GravityCompat.START);


            }
        });



        rateus.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                View subView = inflater.inflate(R.layout.rank_dialog, null);

                final RatingBar ratingBar = (RatingBar) subView.findViewById(R.id.dialog_ratingbar);
                final Button submit = (Button) subView.findViewById(R.id.rank_dialog_button);

                SharedPreferences sharedPreferences ;
                sharedPreferences = getSharedPreferences("pref",MODE_PRIVATE);
                final String password = sharedPreferences.getString("password","");

                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Rate Us");

                builder.setView(subView);
                final AlertDialog alertDialog = builder.create();

                ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener()
                {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float v, boolean b)
                    {
                        rating_value=ratingBar.getRating();
                        Log.e("ratingValue",""+rating_value);

                    }
                });

                submit.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        RateUs_Api();
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();
            }


//                rankDialog = new Dialog(MyActivity.this, R.style.FullHeightDialog);
//                rankDialog.setContentView(R.layout.rank_dialog);
//                rankDialog.setCancelable(true);
//                ratingBar = (RatingBar)rankDialog.findViewById(R.id.dialog_ratingbar);
//                ratingBar.setRating(userRankValue);
//
//                TextView text = (TextView) rankDialog.findViewById(R.id.rank_dialog_text1);
//                text.setText(name);
//
//                Button updateButton = (Button) rankDialog.findViewById(R.id.rank_dialog_button);
//                updateButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        rankDialog.dismiss();
//                    }
//                });
//                //now that the dialog is set up, it's time to show it
//                rankDialog.show();

            //}
        });


        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Home frag1 = new Home();
        Bundle b1 = new Bundle();
        b1.putInt("position" , 1);
        frag1.setArguments(b1);
        ft.replace(R.id.replace , frag1);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        //ft.addToBackStack(null);
        ft.commit();

    }

    private void loadRewardedVideoAd() {

    }

    private void RateUs_Api()
    {
       // progress.setVisibility(View.VISIBLE);
        bean b=(bean)getApplicationContext();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(b.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).build();

        ApiInterface cr=retrofit.create(ApiInterface.class);

        challengeRequestBean boby=new challengeRequestBean();

        Data data=new Data();

        boby.setAction("app_rating");
        data.setUserId(pref.getString("id", ""));
        data.setRating(rating_value);


        Log.e("params_id",pref.getString("id", ""));
        Log.e("params_rating",""+rating_value);

        boby.setData(data);

        Call<rateUsBean> call=cr.rateUsApi(boby);
        Log.e("1111","1111");


        call.enqueue(new Callback<rateUsBean>()
        {
            @Override
            public void onResponse(Call<rateUsBean> call, Response<rateUsBean> response)
            {

                Log.e("2222","222");
                if(response.body().getStatuss().equals("1"))
                {
                    Log.e("3333","3333");
                    String msg = response.body().getMessagee();
                    Toast.makeText(getApplicationContext(),"Successfully Rating Done", Toast.LENGTH_SHORT).show();

                    String userRating=response.body().getData().getUserRating();
                    String appRating=response.body().getData().getAppRating();

                    Log.e("userRatinghggggg",userRating);
                    Log.e("appRatingggg",appRating);

                }
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<rateUsBean> call, Throwable t)
            {

            }
        });


    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {

            drawerLayout.closeDrawer(GravityCompat.START);


        } else {
            super.onBackPressed();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();




        final bean b = (bean) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        ApiInterface cr = retrofit.create(ApiInterface.class);

        challengeRequestBean body = new challengeRequestBean();

        Data data = new Data();

        body.setAction("dashboard");

        data.setUserId(pref.getString("id", ""));

        Log.d("iidd" , pref.getString("id" , ""));

        body.setData(data);

        Call<dashboardBean> call = cr.dashboard(body);
        call.enqueue(new Callback<dashboardBean>() {
            @Override
            public void onResponse(Call<dashboardBean> call, Response<dashboardBean> response) {

                if (response.body().getStatus().equals("1"))
                {
                    str_totalAmount=response.body().getData().getTotalAmountEarned();
                    Log.e("amounttttttt",""+str_totalAmount);
                    wallet.setText("GC BAL.$"+str_totalAmount);

                    if (response.body().getData().getDashboardStatus().equals("1"))
                    {
                        b.locked = true;

                    }
                    else
                    {
                        b.locked = false;
                    }
                }


            }

            @Override
            public void onFailure(Call<dashboardBean> call, Throwable t) {

            }
        });


    }

    public void showDialog()
    {
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        View subView = inflater.inflate(R.layout.dialog_layout, null);
        final EditText subEditText = (EditText)subView.findViewById(R.id.dialogEditText);
        final Button submit = (Button) subView.findViewById(R.id.btnaleartdialog);

        SharedPreferences sharedPreferences ;
        sharedPreferences = getSharedPreferences("pref",MODE_PRIVATE);
        final String password = sharedPreferences.getString("passwordSave","");



        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter your password");
        //builder.setMessage("AlertDialog Message");
        builder.setView(subView);
        final AlertDialog alertDialog = builder.create();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                String str_password=subEditText.getText().toString();

                if(str_password.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Enter your password",Toast.LENGTH_SHORT).show();
                }

                else if(!subEditText.getText().toString().equals(password))
                {
                    Toast.makeText(MainActivity.this, "Your password is incorrect", Toast.LENGTH_SHORT).show();

                }

                else
                    {
                        alertDialog.dismiss();
                   FragmentManager fm = getSupportFragmentManager();
                   FragmentTransaction ft = fm.beginTransaction();
                   Profile frag1 = new Profile();
                   ft.replace(R.id.replace , frag1);
                   ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                   //ft.addToBackStack(null);
                   ft.commit();
                      drawerLayout.closeDrawer(GravityCompat.START);
                    }

//               if (subEditText.getText().toString().equals(password))
//               {
//                   alertDialog.dismiss();
//                   FragmentManager fm = getSupportFragmentManager();
//                   FragmentTransaction ft = fm.beginTransaction();
//                   Profile frag1 = new Profile();
//                   ft.replace(R.id.replace , frag1);
//                   ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
//               //ft.addToBackStack(null);
//                   ft.commit();
//               drawerLayout.closeDrawer(GravityCompat.START);
//               }
//               else
//                   {
//                       subEditText.setText("");
//                       Toast.makeText(MainActivity.this, "Your password is incorrect", Toast.LENGTH_SHORT).show();
//                   }
            }
        });

//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(MainActivity.this, "Cancel", Toast.LENGTH_LONG).show();
//            }
//        });

       alertDialog.show();
    }


    public void rate()
    {

    }



// String post = new Gson().toJson(property);
   // https://stackoverflow.com/questions/11392183/how-to-check-programmatically-if-an-application-is-installed-or-not-in-android



}
