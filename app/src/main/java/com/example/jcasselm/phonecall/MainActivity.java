package com.example.jcasselm.phonecall;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CALL_PHONE = 100;
    private String phoneNumber;

    protected void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putString("phoneNumber", phoneNumber);

        super.onSaveInstanceState(savedInstanceState);

    };

    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);

        phoneNumber = savedInstanceState.getString("phoneNumber");

        TextView phoneView = findViewById(R.id.phoneNumber);

        phoneView.setText(phoneNumber);

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView phoneView = findViewById(R.id.phoneNumber);
        Button callButton = findViewById(R.id.callButton);
        checkCallPhonePermission();


        callButton.setOnClickListener((View v) ->
        {

            int permissionCheck = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CALL_PHONE);
            if (permissionCheck == PackageManager.PERMISSION_GRANTED)
            {
                makeCall();
            }
            else
            {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        PERMISSION_REQUEST_CALL_PHONE);
            }

        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults)
    {
        if (requestCode == PERMISSION_REQUEST_CALL_PHONE)
        {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                makeCall();
            }
            else
            {
                Toast.makeText(this, "Call Permission Not Granted",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private void checkCallPhonePermission()
    {
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED)
        {

        }
        else
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CALL_PHONE))
            {
                //do nothing
            }
            else
            {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        PERMISSION_REQUEST_CALL_PHONE);
            }
        }
    }

    private void makeCall()
    {

        TextView phoneNumber = findViewById(R.id.phoneNumber);
        String phoneNumberValue = phoneNumber.getText().toString();

        try
        {
            Uri uri = Uri.parse("tel:" + phoneNumberValue);
            Intent intent = new Intent(Intent.ACTION_CALL, uri);
            startActivity(intent);
        }
        catch (SecurityException ex)
        {
            String errorMsg = "No permission to make phone call.";
            Log.e("Permission", errorMsg, ex);
        }
    }

}
