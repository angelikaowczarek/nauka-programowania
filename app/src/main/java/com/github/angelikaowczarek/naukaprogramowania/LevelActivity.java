package com.github.angelikaowczarek.naukaprogramowania;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.evgenii.jsevaluator.JsEvaluator;
import com.evgenii.jsevaluator.interfaces.JsCallback;
import com.google.blockly.android.AbstractBlocklyActivity;
import com.google.blockly.android.codegen.CodeGenerationRequest;

import java.util.Collections;
import java.util.List;

public class LevelActivity extends AbstractBlocklyActivity {

    private static final String TAG = "LevelActivity";
    private int level_no = 0;
    private AlertDialog dialog;
    private String code = "";
    private int runSelect = 0;

    private static final List<String> BLOCK_DEFINITIONS = Blocks.getAllBlockDefinitions();
    private static final List<String> JAVASCRIPT_GENERATORS = Collections.singletonList(
            "generators/text_print.js");
    private JsEvaluator jsEvaluator = new JsEvaluator(this);

    private String mNoCodeText;
    private TextView mGeneratedTextView;
    private Handler mHandler;

    private Level level;

    private CodeGenerationRequest.CodeGeneratorCallback mCodeGeneratorCallback =
            new CodeGenerationRequest.CodeGeneratorCallback() {
                @Override
                public void onFinishCodeGeneration(final String generatedCode) {
                    code = generatedCode;
                    if (runSelect < 1) {
                        return;
                    }
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            final String processedCode = "var theFinalResult = '';\n" + generatedCode;
                            evaluateJavascript(processedCode);
                            runTests(processedCode);
                        }
                    });
                    runSelect--;
                }
            };

    private void evaluateJavascript(final String processedCode) {
        jsEvaluator.evaluate(processedCode, new JsCallback() {
            @Override
            public void onResult(String result) {
                if (result.equals("undefined")) {
                    mGeneratedTextView.setText(getString(R.string.compilation_error));
                } else {
                    mGeneratedTextView.setText(result);
                }
                updateTextMinHeight(result);
            }

            @Override
            public void onError(String errorMessage) {
                mGeneratedTextView.setText(errorMessage);
                updateTextMinHeight(errorMessage);
            }
        });
    }

    private void runTests(String code) {

        List<String> ioTestsInput = level.getIoTestsInput();
        List<String> ioTestsOutput = level.getIoTestsOutput();

        for (String codeTest : level.getCodeTests()) {
            int lastIndex = 0;
            int count = 0;

            while (lastIndex != -1) {
                lastIndex = code.indexOf(codeTest, lastIndex);

                if (lastIndex != -1) {
                    count++;
                    lastIndex += codeTest.length();
                }
            }

            if (count != 1) {
                return;
            }

//            if (!code.contains(codeTest)) {
//                return;
//            }
        }

        for (int i = 0; i < ioTestsInput.size(); i++) {

        }

        createSuccessDialog();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler();
        updateActionBar();

        level = new LevelsDeserializer(this).deserialize(level_no);

        createDescriptionDialog();
        showInstructions();
    }

    private void createDescriptionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(level.getDescription())
                .setTitle(level.getNo() + ": " + level.getBlockName());

        builder.setPositiveButton("Kontynuuj", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        dialog = builder.create();
    }

    private void createSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Prawidłowo rozwiązałeś zadanie! :)")
                .setTitle(level.getNo() + ": Gratulacje!");

        builder.setPositiveButton("Dalej", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                startActivity(
                        new Intent(LevelActivity.this, LevelsListActivity.class));
                onDestroy();
            }
        });

        AlertDialog successDialog = builder.create();
        successDialog.show();
    }

    public void showInstructions(MenuItem item) {
        showInstructions();
    }

    public void showInstructions() {
        dialog.show();
    }

    public void showCode(MenuItem item) {
        onRunCode();

        code = code.replace("theFinalResult += ", "document.write(");
        code = code.replace(" + \"\\n\";", ");");
        mGeneratedTextView.setText(code);
        updateTextMinHeight(code);
    }

    @Override
    protected View onCreateContentView(int parentId) {
        View root = getLayoutInflater().inflate(R.layout.activity_sandbox, null);
        mGeneratedTextView = root.findViewById(R.id.generated_code);
        mNoCodeText = mGeneratedTextView.getText().toString(); // Capture initial value.
        updateTextMinHeight(mNoCodeText);

        return root;
    }

    @Override
    public void onClearWorkspace() {
        super.onClearWorkspace();
        mGeneratedTextView.setText(mNoCodeText);
        updateTextMinHeight(mGeneratedTextView.getText().toString());
    }

    private void updateTextMinHeight(String text) {
        int linesQuantity = 1;
        int start = 0;
        int index = text.indexOf('\n', start);
        while (index > 0) {
            linesQuantity++;
            start = index + 1;
            index = text.indexOf('\n', start);
        }

        float density = getResources().getDisplayMetrics().density;
        int minLines = 2;
        int maxLines = 10;

        if (linesQuantity < minLines) {
            linesQuantity = minLines;
        }
        if (linesQuantity > maxLines) {
            linesQuantity = maxLines;
        }

        mGeneratedTextView.setMinHeight((int) (linesQuantity * 33 * density));
    }

    private void updateActionBar() {
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    protected int getActionBarMenuResId() {
        return R.menu.blockly_actionbar_levels;
    }

    public ActionBar getSupportActionBar() {
        return getDelegate().getSupportActionBar();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @NonNull
    @Override
    protected List<String> getBlockDefinitionsJsonPaths() {
        return BLOCK_DEFINITIONS;
    }

    @NonNull
    @Override
    protected String getToolboxContentsXmlPath() {
        return getToolboxFilename();
    }

    @NonNull
    @Override
    protected List<String> getGeneratorsJsPaths() {
        return JAVASCRIPT_GENERATORS;
    }

    @NonNull
    @Override
    protected CodeGenerationRequest.CodeGeneratorCallback getCodeGenerationCallback() {
        return mCodeGeneratorCallback;
    }

    private String getToolboxFilename() {
        findLevelNumber();
        String toolboxPath = "toolbox/toolbox.xml";

        if (level_no != 0) {
            toolboxPath = "toolbox/toolbox_" + level_no + ".xml";
        }
        return toolboxPath;
    }

    private void findLevelNumber() {
        Bundle b = getIntent().getExtras();

        String filename = null;

        if (b != null) {
            filename = b.getString("name");
        }

        if (filename != null) {
            filename = filename.replace(getString(R.string.level_name_regex_1), "");

            level_no = Integer.valueOf(filename);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == com.google.blockly.android.R.id.action_save) {
            onSaveWorkspace();
            return true;
        } else if (id == com.google.blockly.android.R.id.action_load) {
            onLoadWorkspace();
            return true;
        } else if (id == com.google.blockly.android.R.id.action_clear) {
            onClearWorkspace();
            return true;
        } else if (id == com.google.blockly.android.R.id.action_run) {
            if (getController().getWorkspace().hasBlocks()) {
                onRunCode();
                runSelect++;
            } else {
                Log.i(TAG, "No blocks in workspace. Skipping run request.");
            }
            return true;
        } else if (id == android.R.id.home && mNavigationDrawer != null) {
            setNavDrawerOpened(!isNavDrawerOpen());
        }

        return super.onOptionsItemSelected(item);
    }
}