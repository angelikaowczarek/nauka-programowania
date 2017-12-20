package com.github.angelikaowczarek.naukaprogramowania;

import android.content.Context;
import android.content.res.Resources;

public class LevelsDeserializer {

    private Resources resources;
    private Context context;

    public LevelsDeserializer(Context context) {
        this.context = context;
        resources = context.getResources();
    }

    public Level deserialize(int number) {

        return new Level(
                number,
                getValue("block_" + number),
                getValue("description_" + number)
        );
    }

    private String getValue(String name) {
        int id = resources.getIdentifier(name, "string", context.getPackageName());
        return resources.getText(id).toString();
    }

}
