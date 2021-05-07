package com.group.foodmanagement.ui.invoices;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.group.foodmanagement.MainActivity;
import com.group.foodmanagement.R;
import com.group.foodmanagement.adapter.InvoiceGridAdapter;
import com.group.foodmanagement.adapter.ProductGridAdapter;
import com.group.foodmanagement.model.Invoice;
import com.group.foodmanagement.model.Product;
import com.group.foodmanagement.model.User;
import com.group.foodmanagement.repository.InvoiceRepository;
import com.group.foodmanagement.repository.ProductRepository;
import com.group.foodmanagement.repository.UserRepository;
import com.group.foodmanagement.ui.login.LoginActivity;

import java.util.List;

public class InvoicesFragment extends Fragment {

    private InvoicesViewModel invoicesViewModel;
    GridView gridView;
    User user;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        invoicesViewModel =
                ViewModelProviders.of(this).get(InvoicesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_invoices, container, false);

        InvoiceRepository db = new InvoiceRepository(getActivity());
        List<Invoice> invoices = db.getInvoicesByUser(UserRepository.getLoginUser().getUsername());

        gridView = (GridView) root.findViewById(R.id.invoiceGridView);
        InvoiceGridAdapter ad = new InvoiceGridAdapter(getActivity().getApplicationContext(),invoices);
        gridView.setAdapter(ad);

        return root;
    }

    @Override
    public void onResume() {
        InvoiceRepository db = new InvoiceRepository(getActivity());
        List<Invoice> invoices = db.getInvoicesByUser(UserRepository.getLoginUser().getUsername());
        InvoiceGridAdapter ad = new InvoiceGridAdapter(getActivity().getApplicationContext(),invoices);
        gridView.setAdapter(ad);

        super.onResume();
    }
}
