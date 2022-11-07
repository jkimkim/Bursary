package com.example.bursary;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bursary.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

        /**
        * Use this factory method to create a new instance of
        * this fragment using the provided parameters.
        *
        * @return A new instance of fragment ProfileFragment.
        */

        private FragmentProfileBinding binding;

        public View onCreateView(@NonNull LayoutInflater inflater,
                                    ViewGroup container, Bundle savedInstanceState) {
                binding = FragmentProfileBinding.inflate(inflater, container, false);
                View root = binding.getRoot();

                return root;
            }

            @Override
            public void onDestroyView() {
                super.onDestroyView();
                binding = null;
            }
}