package com.code.sample.costPath.async;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.code.sample.costPath.core.MatrixPath;

/**
 * Created  on 1/27/2017.
 * AsyncMatrixPath for best cost search in Matrix in background
 */

public class AsyncMatrixPath extends AsyncTask<Void, Void, String> {

    private static final String TAG = AsyncMatrixPath.class.getSimpleName();

    private Activity activity;
    private MatrixPath matrixPath;

    // a constructor so that you can pass the object and use
    public AsyncMatrixPath(Activity activity, MatrixPath matrixPath) {
        this.activity = activity;
        this.matrixPath = matrixPath;
        Log.d(TAG, "AsyncMatrixPath: constructor completed");
    }

    @Override
    protected String doInBackground(Void... params) {
        String result = matrixPath.bestCostSearch();
        Log.d(TAG, "doInBackground: completed");
        return result;
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        String finalResults = activity.getString(com.code.sample.costPath.R.string.results) + s;
        TextView showResults = (TextView) activity.findViewById(com.code.sample.costPath.R.id.results);
        showResults.setText(finalResults);
        Log.d(TAG, "onPostExecute: " + finalResults);
    }
}
