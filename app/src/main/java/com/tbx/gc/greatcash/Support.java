package com.tbx.gc.greatcash;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by Priyanka on 7/11/2018.
 */

public class Support extends AppCompatActivity
{
    private RelativeLayout rel_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.support);
        setID();

        rel_back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
              finish();
            }
        });
    }

    private void setID()
    {
        rel_back=findViewById(R.id.rel_back);
    }
}
