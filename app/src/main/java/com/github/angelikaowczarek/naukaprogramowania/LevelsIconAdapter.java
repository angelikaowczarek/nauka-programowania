package com.github.angelikaowczarek.naukaprogramowania;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class LevelsIconAdapter extends BaseAdapter {
    private Context context;
    private WindowManager windowManager;
    private Integer[] idsArray;
    private List<Integer> idsList = new ArrayList<>();

    public LevelsIconAdapter(Context context, WindowManager windowManager) {
        this.context = context;
        this.windowManager = windowManager;
        populateIdsArray();
    }

    @Override
    public int getCount() {
        return idsArray.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        int rowHeight = calculateRowHeight();

        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, rowHeight));
        imageView.setId(idsArray[i]);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();

                bundle.putString("name", context.getResources().getResourceName(v.getId()));

                Intent intent = new Intent(context, LevelActivity.class);
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        imageView.setImageResource(idsArray[i]);
        return imageView;
    }

    private int calculateRowHeight() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;

        Integer padding = context.getResources().getInteger(R.integer.padding);
        Integer spacing = context.getResources().getInteger(R.integer.spacing);

        return (width - 2 * padding - 2 * spacing) / 3;
    }

    private void populateIdsArray() {
        setupIdsList();

        int count = context.getResources().getInteger(R.integer.levels_count);

        for (int i = idsList.size() - 1; i >= count; i--) {
            idsList.remove(i);
        }
        idsArray = idsList.toArray(new Integer[idsList.size()]);
    }


    private void setupIdsList() {
        idsList.add(R.drawable.button_number1);
        idsList.add(R.drawable.button_number2);
        idsList.add(R.drawable.button_number3);
        idsList.add(R.drawable.button_number4);
        idsList.add(R.drawable.button_number5);
        idsList.add(R.drawable.button_number6);
        idsList.add(R.drawable.button_number7);
        idsList.add(R.drawable.button_number8);
        idsList.add(R.drawable.button_number9);
    }
}
