package com.mirea.zarin.mireaproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.mirea.zarin.mireaproject.practice7.Authorization;

public class HomeFragment extends Fragment
{

    FirebaseAuth auth;
    Button signOutButton;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        auth = FirebaseAuth.getInstance();

        signOutButton = (Button) view.findViewById(R.id.signOutButton);
        signOutButton.setOnClickListener(this::onSignOutClick);

        return view;
    }

    private void onSignOutClick(View view)
    {
        if(auth != null)
        {
            auth.signOut();
            Intent intent = new Intent(requireActivity(), Authorization.class);
            startActivity(intent);
        }
    }
}