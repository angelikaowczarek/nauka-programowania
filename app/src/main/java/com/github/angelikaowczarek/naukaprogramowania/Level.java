package com.github.angelikaowczarek.naukaprogramowania;

import java.util.ArrayList;
import java.util.List;

public class Level {

    private int no;
    private String blockName;
    private String description;
    private List<String> codeTests = new ArrayList<>();
    private List<String> ioTestsInput = new ArrayList<>();
    private List<String> ioTestsOutput = new ArrayList<>();

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

    public void addCodeTest(String test) {
        codeTests.add(test);
    }

    public void addIoTestsInput(String test) {
        ioTestsInput.add(test);
    }

    public void addIoTestsOutput(String test) {
        ioTestsOutput.add(test);
    }

    public List<String> getCodeTests() {
        return codeTests;
    }

    public List<String> getIoTestsInput() {
        return ioTestsInput;
    }

    public List<String> getIoTestsOutput() {
        return ioTestsOutput;
    }
}


