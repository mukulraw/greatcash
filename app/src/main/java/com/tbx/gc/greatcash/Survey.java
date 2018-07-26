package com.tbx.gc.greatcash;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.tbx.gc.greatcash.challengeRequestPOJO.Data;
import com.tbx.gc.greatcash.challengeRequestPOJO.challengeRequestBean;
import com.tbx.gc.greatcash.registerResponsePOJO.registerResponseBean;
import com.tbx.gc.greatcash.submitSurveyPOJO.submitSurveyBean;
import com.tbx.gc.greatcash.surveryPOJO.Datum;
import com.tbx.gc.greatcash.surveryPOJO.surveyBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Survey extends Fragment {

    TextView ques;
    RadioGroup options;
    TextView next;
    ProgressBar progress;
    SharedPreferences pref;

    String qid;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.survey, container, false);

        pref = getContext().getSharedPreferences("pref", Context.MODE_PRIVATE);

        ques = view.findViewById(R.id.textView96);
        options = view.findViewById(R.id.radioGroup);
        next = view.findViewById(R.id.textView98);
        progress = view.findViewById(R.id.progressBar10);


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int iidd = options.getCheckedRadioButtonId();

                if (iidd != -1) {
                    progress.setVisibility(View.VISIBLE);


                    bean b = (bean) getContext().getApplicationContext();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(b.BASE_URL)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();


                    ApiInterface cr = retrofit.create(ApiInterface.class);


                    submitSurveyBean body = new submitSurveyBean();

                    com.tbx.gc.greatcash.submitSurveyPOJO.Data data = new com.tbx.gc.greatcash.submitSurveyPOJO.Data();

                    body.setAction("online_survey_answer");

                    RadioButton rb = options.findViewById(iidd);

                    data.setQuestionId(qid);
                    data.setUserId(pref.getString("id", ""));
                    data.setAnswer(rb.getText().toString());

                    body.setData(data);

                    Call<registerResponseBean> call = cr.submitSurvey(body);

                    call.enqueue(new Callback<registerResponseBean>() {
                        @Override
                        public void onResponse(Call<registerResponseBean> call, Response<registerResponseBean> response) {

                            if (response.body().getStatus().equals("1"))
                            {
                            loadData();
                            }


                            progress.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Call<registerResponseBean> call, Throwable t) {
                            progress.setVisibility(View.GONE);
                        }
                    });

                } else {

                }


            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        loadData();

    }

    public void loadData() {

        progress.setVisibility(View.VISIBLE);


        bean b = (bean) getContext().getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        ApiInterface cr = retrofit.create(ApiInterface.class);

        challengeRequestBean body = new challengeRequestBean();

        Data data = new Data();

        body.setAction("online_survey");

        data.setUserId(pref.getString("id", ""));

        Log.d("iidd", pref.getString("id", ""));

        body.setData(data);

        Call<surveyBean> call = cr.aurvey(body);

        call.enqueue(new Callback<surveyBean>() {
            @Override
            public void onResponse(Call<surveyBean> call, Response<surveyBean> response) {

                if (response.body().getStatus().equals("1")) {
                    Datum item = response.body().getData().get(0);

                    ques.setText(item.getQuestion());
                    qid = item.getQuestionId();

                    options.removeAllViews();

                    RadioButton rb1 = new RadioButton(getContext());
                    RadioButton rb2 = new RadioButton(getContext());
                    RadioButton rb3 = new RadioButton(getContext());
                    RadioButton rb4 = new RadioButton(getContext());
                    rb1.setText(item.getOptionA());
                    rb2.setText(item.getOptionB());
                    rb3.setText(item.getOptionC());
                    rb4.setText(item.getOptionD());


                    options.addView(rb1);
                    options.addView(rb2);
                    options.addView(rb3);
                    options.addView(rb4);

                }

                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<surveyBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

    }
}
