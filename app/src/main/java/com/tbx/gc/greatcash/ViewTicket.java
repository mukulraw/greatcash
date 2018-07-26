package com.tbx.gc.greatcash;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tbx.gc.greatcash.QuestionPOJO.DataQuestion;
import com.tbx.gc.greatcash.QuestionPOJO.QuestionBean;
import com.tbx.gc.greatcash.TicketReq.DataReqticket;
import com.tbx.gc.greatcash.TicketReq.Ticketrequest;
import com.tbx.gc.greatcash.challengeRequestPOJO.challengeRequestBean;
import com.tbx.gc.greatcash.createTicketPOJO.ticketBean;

import java.util.ArrayList;
import java.util.List;

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
    private LinearLayout linearLayout_que;
    private RecyclerView recyclerView_question;
    private RecyclerView.LayoutManager layoutManager_question;
    private ProgressBar progressBar_question;
    private QuestionAdapter adapter;
    private List<DataQuestion>dataQuestionList=new ArrayList<>();
    String id,que_id;
    private TextView text_sel;
    private android.app.AlertDialog dialog;


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
        linearLayout_que=view.findViewById(R.id.linear_question);
        text_sel=view.findViewById(R.id.text_sel_que);

        String regID=pref.getString("refId", "");
        Log.e("rrrriddddd",""+regID);

        String userName=pref.getString("name", "");
        Log.e("userNAme",""+userName);

        id= pref.getString("id" , "");

        text_Name.setText("Name:"+userName);
        text_ID.setText("Refer ID:"+regID);

        btn_submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                 str_typed=ed_typed.getText().toString();

                 String text_question=text_sel.getText().toString();

                if(str_typed.equals(""))
                {
                    Toast.makeText(getActivity(),"Fill the feild",Toast.LENGTH_SHORT).show();
                }
                else if(text_question.equals(""))
                {
                    Toast.makeText(getActivity(),"Select the question",Toast.LENGTH_SHORT).show();
                }
                else
                    {
                        HitApi();
                    }


            }
        });


        linearLayout_que.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                View alertLayout = LayoutInflater.from(getActivity()).inflate(R.layout.question_dialog,null);
                android.app.AlertDialog.Builder alertDialog=new android.app.AlertDialog.Builder(getActivity());
                alertDialog.setView(alertLayout);
                 dialog=alertDialog.create();

                recyclerView_question=alertLayout.findViewById(R.id.recyclerView_question);
                progressBar_question=alertLayout.findViewById(R.id.progressBar_question);

                layoutManager_question=new LinearLayoutManager(getActivity());
                recyclerView_question.setLayoutManager(layoutManager_question);

                adapter=new QuestionAdapter(getActivity(),dataQuestionList);
                recyclerView_question.setAdapter(adapter);

                question_Api();

                dialog.show();

            }
        });


        return view;
    }

    private void question_Api()
    {
        progressBar_question.setVisibility(View.VISIBLE);

        bean b = (bean) getContext().getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface cr=retrofit.create(ApiInterface.class);

        challengeRequestBean body = new challengeRequestBean();

        com.tbx.gc.greatcash.challengeRequestPOJO.Data data=new com.tbx.gc.greatcash.challengeRequestPOJO.Data();

        body.setAction("ticket_question");

        data.setUserId(id);

        Log.e("iidd" , pref.getString("id" , ""));

        body.setData(data);

        Call<QuestionBean> call = cr.questionApi(body);


        call.enqueue(new Callback<QuestionBean>()
        {

            @Override
            public void onResponse(Call<QuestionBean> call, Response<QuestionBean> response)
            {
                Log.e("222","222");

                progressBar_question.setVisibility(View.GONE);

                if (response.body().getStatus().equals("1"))
                {
                    adapter.setGridData(response.body().getData());
                    Log.e("3333","3333");
                }

                //progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<QuestionBean> call, Throwable t)
            {
                progressBar_question.setVisibility(View.INVISIBLE);
            }


        });

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
        data.setQuestionID(que_id);
        data.setReport(str_typed);

        Log.e("questioiddd_send",""+que_id);
        Log.e("questitypeee_send",""+str_typed);
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

    private class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder>
    {
        private Context context;
        private List<DataQuestion>dataQuesList;

        public QuestionAdapter(FragmentActivity activity, List<DataQuestion> dataQuestionList)
        {
            this.context=activity;
            this.dataQuesList=dataQuestionList;

        }


        public void setGridData(List<DataQuestion> list)
        {
           this.dataQuesList=list;
           notifyDataSetChanged();
        }

        @NonNull
        @Override
        public QuestionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.question_popup , viewGroup , false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull QuestionAdapter.ViewHolder viewHolder, int i)
        {
           final DataQuestion dataQuestion=dataQuesList.get(i);
           viewHolder.text_question.setText(dataQuestion.getQuestion());

           viewHolder.relative.setOnClickListener(new View.OnClickListener()
           {
               @Override
               public void onClick(View view)
               {

                   String str_que=dataQuestion.getQuestion();
                   que_id=dataQuestion.getQuestionID();

                   text_sel.setText(str_que);
                   dialog.dismiss();


               }
           });

        }

        @Override
        public int getItemCount()
        {
            return dataQuesList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder
        {
            private TextView text_question;
            private RelativeLayout relative;

            public ViewHolder(@NonNull View itemView)
            {
                super(itemView);
                text_question=itemView.findViewById(R.id.text_country_name);
                relative=itemView.findViewById(R.id.rel);
            }
        }
    }
}
