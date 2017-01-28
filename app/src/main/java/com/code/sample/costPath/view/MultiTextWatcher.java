package com.code.sample.costPath.view;

import android.app.Activity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.code.sample.costPath.R;
import com.code.sample.costPath.base.Constants;
import com.code.sample.costPath.base.UserInputs;

import static com.code.sample.costPath.base.Constants.MAX_MATRIX;

/**
 * Created on 1/27/2017.
 * TextWatcher class for multi EditText view
 */

public class MultiTextWatcher implements TextWatcher {
    private static final String TAG = MultiTextWatcher.class.getSimpleName();

    private final Activity activity;
    private final EditText editText;
    private boolean ignoreChange = false;

    //    public MultiTextWatcher(Activity activity, UserInputs userInputs, EditText editText) {
    public MultiTextWatcher(Activity activity, EditText editText) {
        this.activity = activity;
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    /**
     * detect and check user's input for row, column and max cost
     */
    @Override
    public void afterTextChanged(Editable s) {

        switch (editText.getId()) {
            case R.id.rowNumber:

                int row = checkInput(s.toString());
                if ((row <= 0) || (row > MAX_MATRIX)) {
                    if (!ignoreChange) {
                        ignoreChange = true;
                        editText.setText(R.string.empty_string);
                        editText.setError(activity.getString(R.string.inValidRowNo));
                        ignoreChange = false;
                    }
                    UserInputs.Instance().setRow(0);
                    break;
                }

                for (int i = 0; i < MAX_MATRIX; i++) {
                    TextView textView = (TextView) activity.findViewById(Constants.textViewIDArray[i]);
                    EditText eText = (EditText) activity.findViewById(Constants.editTextIDArray[i]);
                    eText.setText("");
                    eText.setEnabled(true);

                    if (i < row) {
                        textView.setVisibility(View.VISIBLE);
                        eText.setVisibility(View.VISIBLE);
                        eText.setInputType(InputType.TYPE_NULL);

                    } else {
                        textView.setVisibility(View.INVISIBLE);
                        eText.setVisibility(View.INVISIBLE);
                    }
                }
                UserInputs.Instance().setRow(row);

                break;

            case R.id.columnNumbers:
                int column = checkInput(s.toString());
                if ((column <= 0) || (column > MAX_MATRIX)) {
                    if (!ignoreChange) {
                        ignoreChange = true;
                        editText.setText("");
                        editText.setError(activity.getString(R.string.invalidColumnNumber));
                        ignoreChange = false;
                    }
                    UserInputs.Instance().setColumn(0);
                    break;
                }
                UserInputs.Instance().setColumn(column);

                break;
            case R.id.costlimit:
                int maxCost = checkInput(s.toString());
                // suppose Cost must be > 0
                if (maxCost <= 0) {
                    if (!ignoreChange) {
                        ignoreChange = true;
                        editText.setText("");
                        editText.setError(activity.getString(R.string.invalidMaxCost));
                        ignoreChange = false;
                    }
                    UserInputs.Instance().setMaxCost(0);
                    break;
                }
                UserInputs.Instance().setMaxCost(maxCost);

                break;
            default:
                break;
        }


    }


    private Integer checkInput(String string) {
        // check the valid input of Row No, Column No, and Max Cost, should be > 0
        Log.d(TAG, "checkInput: input String: " + string);
        Integer inputNumber = Constants.INPUT_FAILURE;

        if ((string != null) && (!string.isEmpty())) {
            try {
                inputNumber = Integer.parseInt(string);
            } catch (NumberFormatException e) {
                Log.d(TAG, "checkInput: Input digit is invalid: " + string);
                return inputNumber;
            }
        } else {
            Log.d(TAG, "checkInput: Input is null or empty");
            return inputNumber;
        }

        return inputNumber;

    }

}
