package com.makapps.streeksha.Fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.makapps.streeksha.Adapters.GuardianRVAdapter;
import com.makapps.streeksha.AuthActivity;
import com.makapps.streeksha.Models.Guardian;
import com.makapps.streeksha.Models.UserProfile;
import com.makapps.streeksha.R;
import com.makapps.streeksha.SPHandlers.GuardianManager;
import com.makapps.streeksha.SPHandlers.ProfileManager;
import com.makapps.streeksha.Utilities.UtilFn;

import java.util.ArrayList;
import java.util.Objects;

public class ProfileFragment extends Fragment {
    private TextView name,id,email;
    private EditText phone;
    private RecyclerView guardians_rv;
    private FloatingActionButton add_guardian_fab;
    private ArrayList<Guardian> guardians;
    private GuardianRVAdapter guardianRVAdapter;

    private UserProfile userProfile;
    private ProfileManager profileManager;
    private GuardianManager guardianManager;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
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
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        setViews(v);
        profileManager = new ProfileManager(Objects.requireNonNull(getContext()));
        userProfile = profileManager.getUserData();
        if (userProfile==null){
            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getContext());
            if (account==null){
                Intent i = new Intent(getContext(), AuthActivity.class);
                startActivity(i);
                Objects.requireNonNull(getActivity()).finish();
            } else {
                userProfile = new UserProfile(account.getId(),
                        account.getDisplayName(), account.getEmail(), "");
                userProfile.setOauth(account.getIdToken());
                profileManager.updateUser(userProfile);
            }
        }
        //id.setText(userProfile.getId());
        name.setText(userProfile.getName());
        email.setText(userProfile.getEmail());
        phone.setText(userProfile.getPhone());
        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                userProfile.setPhone(String.valueOf(charSequence));
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });
        guardianManager = new GuardianManager(getContext());
        guardians = guardianManager.getAllGuardianList();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        guardians_rv.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(guardians_rv.getContext(),
                layoutManager.getOrientation());
        guardians_rv.addItemDecoration(dividerItemDecoration);
        guardianRVAdapter = new GuardianRVAdapter(getContext(), guardians);
        guardianRVAdapter.setClickListener(new GuardianRVAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Select");
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                        android.R.layout.select_dialog_singlechoice);
                arrayAdapter.add("Update");
                arrayAdapter.add("Delete");

                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = arrayAdapter.getItem(which);
                        assert strName != null;
                        if (strName.equals("Update")){
                            showGuardianBox(guardianRVAdapter.getItem(position),position);
                            guardianRVAdapter.notifyItemChanged(position);
                        } else if (strName.equals("Delete")){
                            guardians.remove(guardianRVAdapter.getItem(position));
                            guardianRVAdapter.notifyItemRemoved(position);
                        }
                    }
                });
                builder.show();
            }
        });
        guardians_rv.setAdapter(guardianRVAdapter);
        add_guardian_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showGuardianBox(null,-1);
            }
        });
        return v;
    }

    private void showGuardianBox(Guardian o, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        @SuppressLint("InflateParams")
        View alertLayout = inflater.inflate(R.layout.guardian_add_layout, null);
        final TextView tv = alertLayout.findViewById(R.id.item_id);
        tv.setText(UtilFn.randomAlphaNumeric(10));
        final EditText name = alertLayout.findViewById(R.id.item_name);
        final EditText email = alertLayout.findViewById(R.id.item_email);
        final EditText phone = alertLayout.findViewById(R.id.item_phone);
        final EditText relation = alertLayout.findViewById(R.id.item_relationship);
        if (o!=null){
            tv.setText(o.getId());
            name.setText(o.getName());
            email.setText(o.getEmail());
            phone.setText(o.getPhone());
            relation.setText(o.getRelationship());
        }
        builder.setTitle("Add New Guardian")
                .setView(alertLayout)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(email.getText()) ||
                                TextUtils.isEmpty(phone.getText()) || TextUtils.isEmpty(relation.getText())) {
                                Toast.makeText(getContext(), "Empty fields", Toast.LENGTH_SHORT).show();
                        } else {
                            Guardian g = new Guardian(String.valueOf(tv.getText()),
                                        String.valueOf(name.getText()),
                                        String.valueOf(email.getText()),
                                        String.valueOf(phone.getText()),
                                        String.valueOf(relation.getText()));
                            if (position==-1) {
                                if (guardianManager.guardianRecSize()<5 && guardianRVAdapter.getItemCount()<5) {
                                    guardians.add(g);
                                    guardianRVAdapter.notifyItemInserted(guardians.indexOf(g));
                                } else {
                                    Toast.makeText(getContext(),"MAX GUARDIAN LIMIT REACHED!",Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                guardians.set(position,g);
                                guardianRVAdapter.notifyItemChanged(position);
                            }
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        builder.create().show();
    }

    private void setViews(View v) {
        name = v.findViewById(R.id.user_name);
        //id = v.findViewById(R.id.user_id);
        email = v.findViewById(R.id.user_email);
        phone = v.findViewById(R.id.user_phone);
        guardians_rv = v.findViewById(R.id.guardian_rv);
        add_guardian_fab = v.findViewById(R.id.add_guardian_fab);
    }

    @Override
    public void onPause() {
        super.onPause();
        profileManager.updateUser(userProfile);
        guardianManager.updateGuardianList(guardians);
    }
}
