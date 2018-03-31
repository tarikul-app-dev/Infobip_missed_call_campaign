package limited.it.planet.infobipmissedcallcampaign.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import limited.it.planet.infobipmissedcallcampaign.R;
import limited.it.planet.infobipmissedcallcampaign.constant.Constants;
import limited.it.planet.infobipmissedcallcampaign.database.DataHelper;
import limited.it.planet.infobipmissedcallcampaign.util.SendMobNumberToServer;
import limited.it.planet.infobipmissedcallcampaign.util.SendSMSSendToServer;

import static limited.it.planet.infobipmissedcallcampaign.util.SharedPreferenceSaveAndGet.getBoleanValueSharedPreferences;
import static limited.it.planet.infobipmissedcallcampaign.util.SharedPreferenceSaveAndGet.getValueFromSharedPreferences;
import static limited.it.planet.infobipmissedcallcampaign.util.SharedPreferenceSaveAndGet.saveBoleanValueSharedPreferences;
import static limited.it.planet.infobipmissedcallcampaign.util.SharedPreferenceSaveAndGet.saveToSharedPreferences;


public class SettingsFragment extends AppFragment {

    static EditText editUserName,editPassword,edtSender,edtMsgText;
    CheckBox checkUnicode;


    static String sendMobNumberAPI = "";
    static String sendSmsAPI = "";




    static SendMobNumberToServer sendMobNumberToServer;
    static SendSMSSendToServer sendSMSSendToServer;




   Button btnSave;
   DataHelper dataHelper;


    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sendMobNumberToServer = new SendMobNumberToServer(getActivity());
        sendSMSSendToServer= new SendSMSSendToServer(getActivity());
        dataHelper = new DataHelper(getActivity());
        dataHelper.open();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        editUserName = (EditText)rootView.findViewById(R.id.edt_user_name);
       // edtHTTPURLSms = (EditText)rootView.findViewById(R.id.edt_http_url_sms);
        editPassword = (EditText)rootView.findViewById(R.id.edt_password);
        edtSender = (EditText)rootView.findViewById(R.id.edt_sender);
        edtMsgText = (EditText)rootView.findViewById(R.id.edt_message);
        checkUnicode = (CheckBox)rootView.findViewById(R.id.checkbox_unicode);


        btnSave = (Button)rootView.findViewById(R.id.btn_save);


        sendMobNumberAPI = Constants.baseAPI;
        sendSmsAPI = Constants.baseAPI;
        //saveToSharedPreferences("mob_number_api",sendMobNumberAPI,getActivity());






        checkUnicode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkUnicode.isChecked()){
                    saveBoleanValueSharedPreferences("is_unicode",true,getActivity());
                    String unicode = "8";
                   saveToSharedPreferences("unicode_value",unicode,getActivity());
                }else {
                    String unicode = "0";
                    saveBoleanValueSharedPreferences("is_unicode",false,getActivity());
                    saveToSharedPreferences("unicode_value",unicode,getActivity());
                }
            }
        });




        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String   txtUserName = editUserName.getText().toString();
               String  txtPassword = editPassword.getText().toString();
               String   txtSender = edtSender.getText().toString();
               String   txtMessage = edtMsgText.getText().toString();

                saveToSharedPreferences("user_name",txtUserName,getActivity());
                saveToSharedPreferences("password",txtPassword,getActivity());
                saveToSharedPreferences("sender",txtSender,getActivity());
                saveToSharedPreferences("message",txtMessage,getActivity());

                Toast.makeText(getActivity(), "Your Information Save Successfully ", Toast.LENGTH_LONG).show();

            }
        });


        return rootView;
    }




    @Override
    public void onResume() {
        super.onResume();
       boolean  isCheckUnicode =getBoleanValueSharedPreferences("is_unicode",getActivity());


        if(isCheckUnicode){
            checkUnicode.setChecked(isCheckUnicode);
        }



        String saveUserName = getValueFromSharedPreferences("user_name",getActivity());
        if(saveUserName!=null && !saveUserName.isEmpty()){
            editUserName.setText(saveUserName);

        }


        String savePassword = getValueFromSharedPreferences("password",getActivity());
        if(savePassword!=null && !savePassword.isEmpty()){
            editPassword.setText(savePassword );
        }
        String saveSender= getValueFromSharedPreferences("sender",getActivity());
        if(saveSender!=null && !saveSender.isEmpty()){
            edtSender.setText(saveSender );
        }

        String saveMsg= getValueFromSharedPreferences("message",getActivity());
        if(saveMsg!=null && !saveMsg.isEmpty()){
            edtMsgText.setText(saveSender );
        }



    }

}
