package com.passion.attendancesheet.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.passion.attendancesheet.LoginBottomSheet;
import com.passion.attendancesheet.R;
import com.passion.attendancesheet.databinding.FragmentLoginBinding;

import timber.log.Timber;


public class Login extends Fragment {

    private FragmentLoginBinding binding;

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentLoginBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get the navController using fragment
        NavController navController = NavHostFragment.findNavController( this );

        // Check if User Existed Already and wait until loginActivity does something
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if( mAuth.getCurrentUser() != null ){
            Timber.d("User is null");
            binding.lottieAnimationView.setVisibility(View.VISIBLE);

            binding.clgLogo.setEnabled(false);
            binding.signInBtn.setVisibility(View.GONE);
            binding.signUpBtn.setVisibility(View.GONE);
        }




        // Sign In
        binding.signInBtn.setOnClickListener(v -> {
            LoginBottomSheet bottomSheet = new LoginBottomSheet( navController , 0);
            bottomSheet.show( getParentFragmentManager() , "signInSheet");
        });

        // Sign - Up
        binding.signUpBtn.setOnClickListener(v -> {
            LoginBottomSheet bottomSheet = new LoginBottomSheet(navController,1);
            bottomSheet.show( getParentFragmentManager() , "SignUpSheet");

        });
    }
}