package com.makapps.streeksha.Fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.makapps.streeksha.LiveLocationActivity;
import com.makapps.streeksha.MainActivity;
import com.makapps.streeksha.Models.Guardian;
import com.makapps.streeksha.R;
import com.makapps.streeksha.SPHandlers.GuardianManager;

import java.util.ArrayList;
import java.util.Objects;

import static com.makapps.streeksha.Utilities.Constants.MY_PERMISSIONS_REQUEST_SEND_SMS;

public class HomeFragment extends Fragment {

    private FloatingActionButton location_fab,msg_fab,videostream_fab,fakecall_fab,crimemap_fab;
    private Button alertButton;
    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        setViews(v);
        location_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), LiveLocationActivity.class);
                startActivity(i);
            }
        });
        msg_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"DISABLED FOR NOW",Toast.LENGTH_SHORT).show();
                //TODO enable after setting proper guardians
                //sendSMSMessage();
            }
        });
        videostream_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"DISABLED FOR NOW",Toast.LENGTH_SHORT).show();
            }
        });
        fakecall_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"DISABLED FOR NOW",Toast.LENGTH_SHORT).show();
            }
        });
        crimemap_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"DISABLED FOR NOW",Toast.LENGTH_SHORT).show();
            }
        });
        alertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"DISABLED FOR NOW",Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }

    private void setViews(View v) {
        location_fab = v.findViewById(R.id.openlocationact);
        msg_fab = v.findViewById(R.id.sos_msg_send);
        videostream_fab = v.findViewById(R.id.video_stream);
        fakecall_fab = v.findViewById(R.id.fake_caller);
        crimemap_fab = v.findViewById(R.id.crime_mapping);
        alertButton = v.findViewById(R.id.start_alerting);
    }
    private void sendSMSMessage() {
        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()),
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(Objects.requireNonNull(getActivity()),
                    Manifest.permission.SEND_SMS)) {
                Toast.makeText(getContext(),"Permission de",Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        } else {
            confirmSendMSG();
        }
    }

    private void confirmSendMSG() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        AlertDialog alertDialog;
        alertDialogBuilder.setTitle("SEND SOS?");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        String message = "SEND HELP!";
                        GuardianManager manager = new GuardianManager(Objects.requireNonNull(getContext()));
                        ArrayList<Guardian> guardians = manager.getAllGuardianList();
                        for (Guardian g:guardians) {
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(g.getPhone(), null,message ,
                                    null, null);
                            Toast.makeText(getContext(), "SMS sent.",
                                    Toast.LENGTH_LONG).show();
                        }

                    }
                });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    confirmSendMSG();
                } else {
                    Toast.makeText(getContext(),
                            "SMS failed, please try again.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
