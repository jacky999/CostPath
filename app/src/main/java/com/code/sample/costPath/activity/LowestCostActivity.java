package com.code.sample.costPath.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.code.sample.costPath.R;
import com.code.sample.costPath.async.AsyncMatrixPath;
import com.code.sample.costPath.base.Constants;
import com.code.sample.costPath.base.UserInputs;
import com.code.sample.costPath.core.MatrixPath;
import com.code.sample.costPath.view.MultiTextWatcher;

import java.util.Arrays;

import static android.widget.Toast.makeText;
import static com.code.sample.costPath.base.Constants.INPUT_FAILURE;
import static com.code.sample.costPath.base.Constants.INPUT_SUCCESS;
import static com.code.sample.costPath.base.Constants.MAX_MATRIX;
import static com.code.sample.costPath.base.Constants.editTextIDArray;

public class LowestCostActivity extends AppCompatActivity {

    private static final String TAG = LowestCostActivity.class.getSimpleName();

    private EditText[] editRow = new EditText[editTextIDArray.length];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserInputs.Instance();

        final EditText rowNo = (EditText) findViewById(com.code.sample.costPath.R.id.rowNumber);
        rowNo.setText("");
        rowNo.setInputType(InputType.TYPE_NULL);
        rowNo.addTextChangedListener(new MultiTextWatcher(this, rowNo));

        final EditText columnNo = (EditText) findViewById(com.code.sample.costPath.R.id.columnNumbers);
        columnNo.setText("");
        columnNo.setInputType(InputType.TYPE_NULL);
        columnNo.addTextChangedListener(new MultiTextWatcher(this, columnNo));

        final EditText costLimitation = (EditText) findViewById(com.code.sample.costPath.R.id.costlimit);
        costLimitation.setText("");
        costLimitation.setInputType(InputType.TYPE_NULL);
        costLimitation.addTextChangedListener(new MultiTextWatcher(this, costLimitation));

        for (int i = 0; i < MAX_MATRIX; i++) {
            EditText eText = (EditText) findViewById(Constants.editTextIDArray[i]);
            eText.setText("");
            eText.setVisibility(View.VISIBLE);
            eText.setInputType(InputType.TYPE_NULL);
            eText.setEnabled(false);
        }

        final Button button = (Button) findViewById(com.code.sample.costPath.R.id.startButton);
        button.setOnClickListener(listener);

    }


    private View.OnClickListener listener = new View.OnClickListener() {
        // private Activity activity;
        @Override
        public void onClick(View v) {

            int row = UserInputs.Instance().getRow();
            int column = UserInputs.Instance().getColumn();
            int maxCost = UserInputs.Instance().getMaxCost();

            Log.d(TAG, "onClick: get input: Row: " + row + "; column: " + column + "; maxCost: " + maxCost);


            if (((0 < row) && (row <= MAX_MATRIX)) &&
                    ((0 < column) && (column <= MAX_MATRIX)) &&
                    (maxCost > 0)) {

                int costArray[][] = new int[row][column];

                for (int i = 0; i < row; i++) {
                    editRow[i] = (EditText) findViewById(editTextIDArray[i]);
                    editRow[i].setInputType(InputType.TYPE_NULL);
                    String etRow = editRow[i].getText().toString();
                    Log.d(TAG, "onClick: get input line for a Row[" + i + "]: " + etRow);

                    int[] inputCostLine = new int[column + 1];

                    inputCostLine = validatedInputCostLine(etRow, column);

                    if (inputCostLine[0] == INPUT_FAILURE) {
                        editRow[i].setText("");
                        editRow[i].setError(getString(com.code.sample.costPath.R.string.invalidInputLine));
                        return;
                    }

                    try {
                        System.arraycopy(inputCostLine, 1, costArray[i], 0, column);
                        Log.d(TAG, "onClick: CostArray input:" + Arrays.toString(costArray[i]));
                    } catch (Exception e) {
                        Log.d(TAG, "onClick: CostArray input exception:" + Arrays.toString(costArray[i]));
                    }
                }

                MatrixPath matrixPath = new MatrixPath();
                // also the validation logic inside the class
                if ((matrixPath.setInputRow(row)) && (matrixPath.setInputColumn(column))
                        && (matrixPath.setInputMaxCost(maxCost)) && (matrixPath.setInputMatrix(row, column, costArray))) {
                    AsyncMatrixPath asyncMatrixPath = new AsyncMatrixPath(LowestCostActivity.this, matrixPath);
                    asyncMatrixPath.execute();
                } else {
                    makeText(LowestCostActivity.this, com.code.sample.costPath.R.string.pleaseEnterTheValidNumber, Toast.LENGTH_LONG).show();
                }
            } else {
                makeText(LowestCostActivity.this, R.string.inValidRowColumnCostNumbers, Toast.LENGTH_LONG).show();
            }

        }


    };


    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        UserInputs.Instance().setRow(0);
        UserInputs.Instance().setColumn(0);
        UserInputs.Instance().setMaxCost(0);
    }


    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    private int[] validatedInputCostLine(String string, int column) {

        int[] resultData = new int[column + 1];
        resultData[0] = INPUT_SUCCESS;

        /** return resultData Array[0] with over maxCost value if validation failed      */

        if ((string == null) || (string.isEmpty())) {
            Log.d(TAG, "validatedInputCostLine: input line is null or empty");
            resultData[0] = INPUT_FAILURE;
            return resultData;
        }

        String delimiter = ",";
        String[] tmpInputString;
        tmpInputString = string.trim().split(delimiter);

        if (tmpInputString.length != column) {
            resultData[0] = INPUT_FAILURE;
            Log.d(TAG, "validatedInputCostLine: Input digits numbers not equal to expected column numbers: " +
                    "inputCostLines contents: " + Arrays.toString(tmpInputString) + ", column No: " + column);
            return resultData;
        }

        for (int j = 0; j < column; j++) {
            try {
                resultData[j + 1] = Integer.parseInt(tmpInputString[j].trim());
            } catch (NumberFormatException e) {
                resultData[0] = INPUT_FAILURE;
                Log.d(TAG, "isValidInputCostLine: Input digit is invalid: " + tmpInputString[j]);
                return resultData;
            }
        }

        Log.d(TAG, "validatedInputCostLine: resultData" + Arrays.toString(resultData));
        return resultData;
    }

}



