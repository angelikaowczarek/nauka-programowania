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

import java.lang.reflect.Field;
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

//        ImageButton imageButton = new ImageButton(context);
//        imageButton.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, rowHeight));
//        imageButton.setImageResource(idsArray[i]);
//        imageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                System.out.println("supcio ");
//            }
//        });
//
//        return imageButton;


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

        int count = 0;

        Field[] fields = R.xml.class.getFields();
        for (Field field : fields) {
            System.out.println(field.getName());
            if (field.getName().startsWith("level_")) {
                count++;
            }
        }

        for (int i = idsList.size() - 1; i >= count; i--) {
            idsList.remove(i);
        }
        idsArray = idsList.toArray(new Integer[idsList.size()]);
    }


    private void setupIdsList() {
        idsList.add(R.drawable.button_number1_1080x1920);
        idsList.add(R.drawable.button_number2_1080x1920);
        idsList.add(R.drawable.button_number3_1080x1920);
        idsList.add(R.drawable.button_number4_1080x1920);
        idsList.add(R.drawable.button_number5_1080x1920);
        idsList.add(R.drawable.button_number6_1080x1920);
        idsList.add(R.drawable.button_number7_1080x1920);
        idsList.add(R.drawable.button_number8_1080x1920);
        idsList.add(R.drawable.button_number9_1080x1920);
    }
}
