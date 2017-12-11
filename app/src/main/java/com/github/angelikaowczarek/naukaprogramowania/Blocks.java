package com.github.angelikaowczarek.naukaprogramowania;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class Blocks {

//    private static final String COLOUR_BLOCKS_PATH = "blocks/colour_blocks_temp.json";
//    private static final String LIST_BLOCKS_PATH = "blocks/list_blocks_temp.json";
//    private static final String LOGIC_BLOCKS_PATH = "blocks/logic_blocks_temp.json";
//    private static final String LOOP_BLOCKS_PATH = "blocks/loop_blocks_temp.json";
//    private static final String MATH_BLOCKS_PATH = "blocks/math_blocks_temp.json";
//    private static final String PROCEDURES_BLOCKS_PATH = "blocks/procedures_temp.json";
//    private static final String TEXT_BLOCKS_PATH = "blocks/text_blocks_temp.json";
//    private static final String VARIABLE_BLOCKS_PATH = "blocks/variable_blocks_temp.json";
//    public static final String TOOLBOX_PATH = "blocks/toolbox_temp.xml";

    private static final String LOGIC_BLOCKS_PATH = "blocks/logic_blocks.json";
    private static final String LOOP_BLOCKS_PATH = "blocks/loop_blocks.json";
    private static final String MATH_BLOCKS_PATH = "blocks/math_blocks.json";
    private static final String TEXT_BLOCKS_PATH = "blocks/text_blocks.json";
    private static final String VARIABLE_BLOCKS_PATH = "blocks/variable_blocks.json";
    public static final String TOOLBOX_PATH = "blocks/toolbox.xml";

    private static List<String> ALL_BLOCK_DEFINITIONS = null;

    public static List<String> getAllBlockDefinitions() {
        if (ALL_BLOCK_DEFINITIONS == null) {
            ALL_BLOCK_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                    LOGIC_BLOCKS_PATH,
                    LOOP_BLOCKS_PATH,
                    MATH_BLOCKS_PATH,
                    TEXT_BLOCKS_PATH,
                    VARIABLE_BLOCKS_PATH

//                    TEXT_BLOCKS_PATH,
//                    LOOP_BLOCKS_PATH,
//                    MATH_BLOCKS_PATH,
//                    VARIABLE_BLOCKS_PATH,
//                    COLOUR_BLOCKS_PATH,
//                    LIST_BLOCKS_PATH,
//                    LOGIC_BLOCKS_PATH,
//                    PROCEDURES_BLOCKS_PATH
            ));
        }
        return ALL_BLOCK_DEFINITIONS;
    }

    private Blocks() {}
}
