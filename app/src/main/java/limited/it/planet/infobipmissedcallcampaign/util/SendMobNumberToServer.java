package limited.it.planet.infobipmissedcallcampaign.util;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import limited.it.planet.infobipmissedcallcampaign.constant.Constants;
import limited.it.planet.infobipmissedcallcampaign.database.DataHelper;
import limited.it.planet.infobipmissedcallcampaign.fragments.DashboardFragment;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static limited.it.planet.infobipmissedcallcampaign.util.SharedPreferenceSaveAndGet.getValueFromSharedPreferences;

/**
 * Created by Tarikul on 3/1/2018.
 */

public class SendMobNumberToServer {
    public static String baseAPI = "";
    String paramUserName = "";
    String paramPassword = "";
    String paramSender = "";
    String paramMessage = "";
    String paramUnicode = "";


    public static final String RESPONSE_LOG = Constants.LOG_TAG_RESPONSE;

    Context mContext;
    DataHelper dataHelper;

      public  SendMobNumberToServer(Context context){
            this.mContext = context;
          baseAPI = getValueFromSharedPreferences("base_api",mContext);
          paramUserName = getValueFromSharedPreferences("user_name",mContext);
          paramPassword = getValueFromSharedPreferences("password",mContext);
          paramSender = getValueFromSharedPreferences("sender",mContext);
          paramMessage = getValueFromSharedPreferences("message",mContext);
          paramUnicode = getValueFromSharedPreferences("unicode_value",mContext);

          dataHelper = new DataHelper(mContext);


        }

        public void mobileNumberSendToServer(String mIncomingNumber){
            baseAPI = baseAPI + "user="+paramUserName + "&password=" + paramPassword +
                    "&sender=" + paramSender + "&SMSText=" + paramMessage +
                    "&GSM=" + mIncomingNumber + "&type=longSMS&datacoding="+ paramUnicode ;

            if((baseAPI!=null && !baseAPI.isEmpty()) ){
                SendIncomingNumberTask sendIncomingNumberTask = new SendIncomingNumberTask(mIncomingNumber);
                sendIncomingNumberTask.execute();
            }


        }

    public class SendIncomingNumberTask extends AsyncTask<String, Integer, String> {

            String mIncomingNumber ;

        public  SendIncomingNumberTask (String incomingNumber){
            this.mIncomingNumber = incomingNumber;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {

            OkHttpClient client = new OkHttpClient();

            try {

                Request request = new Request.Builder()
                        .url(baseAPI)

                        .build();


                Response response = null;
                //client.setRetryOnConnectionFailure(true);
                response = client.newCall(request).execute();
                if (response.isSuccessful()){
                    final String result = response.body().string();
                    Log.d(RESPONSE_LOG,result);

                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String responseResult = jsonObject.getString("response");
//                        if(responseResult.equals("success")){
//                            dataHelper.open();
//
//                            try {
//
//                                    dataHelper.updateSyncStatus(responseResult);
//
//
//
//
//                            }catch (NumberFormatException e){
//                                e.getMessage();
//                            }
//
//
//
//                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }

    }
}
