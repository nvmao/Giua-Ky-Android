package com.group.foodmanagement.ui.invoices;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.group.foodmanagement.R;

public class InvoicesFragment extends Fragment {

    private InvoicesViewModel invoicesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        invoicesViewModel =
                ViewModelProviders.of(this).get(InvoicesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_invoices, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        invoicesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
