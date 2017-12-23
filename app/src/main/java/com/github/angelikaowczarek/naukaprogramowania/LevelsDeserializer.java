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

        Level level = new Level(
                number,
                getValue("block_" + number),
                getValue("description_" + number)
        );

        for (int i = 0; i < Integer.valueOf(getValue("tests_code_count_" + number)); i++) {
            level.addCodeTest(getValue("code_" + number + "_" + i));
        }

        for (int i = 0; i < Integer.valueOf(getValue("tests_io_count_" + number)); i++) {
            level.addIoTestsInput(getValue("input_" + number + "_" + i));
            level.addIoTestsOutput(getValue("output_" + number + "_" + i));
            level.addIoTestsVariable(getValue("variable_" + number + "_" + i));
        }

        return level;
    }

    private String getValue(String name) {
        int id = resources.getIdentifier(name, "string", context.getPackageName());
        return resources.getText(id).toString();
    }

}
