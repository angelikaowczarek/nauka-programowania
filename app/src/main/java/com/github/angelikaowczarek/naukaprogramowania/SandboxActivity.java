package com.github.angelikaowczarek.naukaprogramowania;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.TextView;

import com.google.blockly.android.AbstractBlocklyActivity;
import com.google.blockly.android.codegen.CodeGenerationRequest;
import com.google.blockly.model.DefaultBlocks;

import java.util.Arrays;
import java.util.List;

public class SandboxActivity extends AbstractBlocklyActivity {
    private static final String TAG = "SandboxActivity";

    private static final String SAVE_FILENAME = "sandbox_workspace.xml";
    private static final String AUTOSAVE_FILENAME = "sandbox_workspace_autosave.xml";

    private static final List<String> BLOCK_DEFINITIONS = DefaultBlocks.getAllBlockDefinitions();
    private static final List<String> JAVASCRIPT_GENERATORS = Arrays.asList(
            // Custom block generators go here. Default blocks are already included.
    );

//    CodeGenerationRequest.CodeGeneratorCallback mCodeGeneratorCallback =
//            new LoggingCodeGeneratorCallback(this, TAG);


    private String mNoCodeText;
    private TextView mGeneratedTextView;
    private Handler mHandler;

    CodeGenerationRequest.CodeGeneratorCallback mCodeGeneratorCallback =
        new CodeGenerationRequest.CodeGeneratorCallback() {
            @Override
            public void onFinishCodeGeneration(final String generatedCode) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mGeneratedTextView.setText(generatedCode);
                        updateTextMinHeight();
                    }
                });
            }
        };

    @Override
    protected View onCreateContentView(int parentId) {
        View root = getLayoutInflater().inflate(R.layout.activity_sandbox, null);
        mGeneratedTextView = root.findViewById(R.id.generated_code);
        updateTextMinHeight();

        mNoCodeText = mGeneratedTextView.getText().toString(); // Capture initial value.

        return root;
    }

    @Override
    public void onClearWorkspace() {
        super.onClearWorkspace();
        mGeneratedTextView.setText(mNoCodeText);
        updateTextMinHeight();
    }

    private void updateTextMinHeight() {

        String text = mGeneratedTextView.getText().toString();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler();
        updateActionBar();
    }

    private void updateActionBar() {
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    protected int getActionBarMenuResId() {
        return R.menu.blockly_actionbar;
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
        // Replace with a toolbox that includes application specific blocks.
        return DefaultBlocks.TOOLBOX_PATH;
    }

    @NonNull
    @Override
    protected List<String> getGeneratorsJsPaths() {
        return JAVASCRIPT_GENERATORS;
    }

    @NonNull
    @Override
    protected CodeGenerationRequest.CodeGeneratorCallback getCodeGenerationCallback() {
        // Uses the same callback for every generation call.
        return mCodeGeneratorCallback;
    }

    /**
     * Optional override of the save path, since this demo Activity has multiple Blockly
     * configurations.
     *
     * @return Workspace save path used by SimpleActivity and SimpleFragment.
     */
    @Override
    @NonNull
    protected String getWorkspaceSavePath() {
        return SAVE_FILENAME;
    }

    /**
     * Optional override of the auto-save path, since this demo Activity has multiple Blockly
     * configurations.
     *
     * @return Workspace auto-save path used by SimpleActivity and SimpleFragment.
     */
    @Override
    @NonNull
    protected String getWorkspaceAutosavePath() {
        return AUTOSAVE_FILENAME;
    }
}