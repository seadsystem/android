package com.seads.seadsv2.main_menu_pages.devices_fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;


public class DeviceRecyclerViewTouchListener implements RecyclerView.OnItemTouchListener {

    private GestureDetector mGestureDetector;
    private DeviceRecyclerViewClickListener mDeviceRecyclerViewClickListener;


    public DeviceRecyclerViewTouchListener(
            Context context, final RecyclerView recyclerView,
            DeviceRecyclerViewClickListener deviceRecyclerViewClickListener) {
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            /**
             * single tap
             * @param e
             * @return whether to take input
             */
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.d("PageFragmentFactory", "in onSingleTapUp!");
                return true;
            }

            /**
             * long press, hold down finger, pass this to the DeviceRecyclerViewClickListener we
             * created
             * @param e the motion event that triggered
             */
            @Override
            public void onLongPress(MotionEvent e) {
                Log.d("PageFragmentFactory", "in onLongPress!");
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null && mDeviceRecyclerViewClickListener != null) {
                    mDeviceRecyclerViewClickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                }
            }
        });

    }



    @Override
    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent e) {
        Log.d("PageFragmentFactory","in onintercept");
        View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
        if (child != null && mDeviceRecyclerViewClickListener != null && mGestureDetector.onTouchEvent(e)) {
            mDeviceRecyclerViewClickListener.onClick(child, recyclerView.getChildAdapterPosition(child));
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
