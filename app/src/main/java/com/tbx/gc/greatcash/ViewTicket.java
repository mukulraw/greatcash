package com.tbx.gc.greatcash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tbx.gc.greatcash.TicketReq.DataReqticket;
import com.tbx.gc.greatcash.TicketReq.Ticketrequest;
import com.tbx.gc.greatcash.createTicketPOJO.ticketBean;
import com.tbx.gc.greatcash.redeemPOJO.redeemBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ViewTicket extends Fragment
{

    private TextView text_Name,text_ID;
    SharedPreferences pref;
    private Button btn_recentTicket,btn_submit;
    private EditText ed_typed;
    private ProgressBar progressBar;
    private String str_typed;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.viewticket , container , false);
        pref = getActivity().getSharedPreferences("pref" , Context.MODE_PRIVATE);

        text_Name=view.findViewById(R.id.text_userName);
        text_ID=view.findViewById(R.id.text_ID);
        btn_recentTicket=view.findViewById(R.id.signupgreatcash);
        btn_submit=view.findViewById(R.id.btn_submit);
        ed_typed=view.findViewById(R.id.ed_textTyped);
        progressBar =view.findViewById(R.id.progress);

        String regID=pref.getString("refId", "");
        Log.e("rrrriddddd",""+regID);

        String userName=pref.getString("name", "");
        Log.e("userNAme",""+userName);

        text_Name.setText("Name:"+userName);
        text_ID.setText("Refer ID:"+regID);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                 str_typed=ed_typed.getText().toString();

                if(str_typed.equals(""))
                {
                    Toast.makeText(getActivity(),"Fill the feild",Toast.LENGTH_SHORT).show();
                }
                else
                    {
                        HitApi();
                    }


            }
        });


        return view;
    }

    private void HitApi()
    {

        Log.e("1111", "1111");
        progressBar.setVisibility(View.VISIBLE);

        bean b = (bean)getContext().getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface cr = retrofit.create(ApiInterface.class);

        Ticketrequest body=new Ticketrequest();

        DataReqticket data=new DataReqticket();

        body.setAction("create_ticket");

        data.setUserId(pref.getString("id", ""));
        data.setQuestionID("1");
        data.setReport(str_typed);
        body.setData(data);

        Call<ticketBean> call = cr.createTicket(body);


        call.enqueue(new Callback<ticketBean>()
        {

            @Override
            public void onResponse(Call<ticketBean> call, Response<ticketBean> response) {
                Log.e("222", "222");

                progressBar.setVisibility(View.GONE);

                if (response.body().getStatus().equals("1"))
                {
                    Toast.makeText(getActivity(), "Successfully Ticket Created", Toast.LENGTH_SHORT).show();
                    Log.e("3333", "3333");
                    ed_typed.setText("");

                }

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ticketBean> call, Throwable t)
            {
              progressBar.setVisibility(View.GONE);
            }

        });

    }
}
