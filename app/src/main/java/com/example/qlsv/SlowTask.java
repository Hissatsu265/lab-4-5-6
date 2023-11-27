package com.example.qlsv;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

public class SlowTask extends AsyncTask<Void, Long, Void> {
    private ProgressDialog pdWaiting;
    private TextView tvStatus;
    private Context context;
    private Long startTime;

    public SlowTask(Context context, TextView tvStatus) {
        this.context = context;
        this.tvStatus = tvStatus;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try{
            for (Long i = 0L; i < 3L; i++) {
                Thread.sleep(2000);
                publishProgress((Long)i);
            }
        }catch (Exception e) {
            Log.e("Slowjob",e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pdWaiting = new ProgressDialog(context);
        startTime=System.currentTimeMillis();
        tvStatus.setText("Start time: "+ startTime);
        pdWaiting.setMessage(context.getString(R.string.please_wait));
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//        progressDialog.setCancelable(false);
//        progressDialog.setMax(100);
//        progressDialog.setProgress(0);
        pdWaiting.show();
    }

    @Override
    protected void onProgressUpdate(Long... values) {
        super.onProgressUpdate(values);
        tvStatus.append("\nWorking... " + values[0]);
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        if(pdWaiting.isShowing()) pdWaiting.dismiss();
        tvStatus.append("\nEnd Time: "+System.currentTimeMillis());
        tvStatus.append("\n Done");
    }
}
