package com.tbx.gc.greatcash;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by jasyc on 19/7/17.
 */
public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener
{
    private ClickListener listener;


    public interface ClickListener
    {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }

    private GestureDetector gestureDetector;



    public RecyclerTouchListener(Context applicationContext, final RecyclerView recyclerView, ClickListener clickListener)
    {

        this.listener=clickListener;
        gestureDetector = new GestureDetector(applicationContext, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null && listener != null) {
                    listener.onLongClick(child, recyclerView.getChildPosition(child));
                }
            }
        });

    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e)
    {
        View child = rv.findChildViewUnder(e.getX(), e.getY());
        if (child != null && listener != null && gestureDetector.onTouchEvent(e)) {
            listener.onClick(child, rv.getChildPosition(child));
        }
        return false;

    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

}

