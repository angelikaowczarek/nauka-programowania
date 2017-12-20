package com.github.angelikaowczarek.naukaprogramowania;

public class Level {

    private int no;
    private String blockName;
    private String description;

    public Level(int no, String blockName, String description) {
        this.no = no;
        this.blockName = blockName;
        this.description = description;
    }

    public int getNo() {
        return no;
    }

    public String getBlockName() {
        return blockName;
    }

    public String getDescription() {
        return description;
    }
}


