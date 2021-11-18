package com.zawraapharma.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.zawraapharma.R;
import com.zawraapharma.databinding.InviceRow2Binding;
import com.zawraapharma.databinding.InviceRow3Binding;
import com.zawraapharma.models.BillResponse;
import com.zawraapharma.models.CompanyModel;
import com.zawraapharma.models.RetrieveResponseModel;

import java.util.List;

public class InvoiceAdapter3 extends RecyclerView.Adapter<InvoiceAdapter3.MyHolder> {

    private List<RetrieveResponseModel.BackItemsFk> list;
    private CompanyModel companyModel;
    private Context context;

    public InvoiceAdapter3(List<RetrieveResponseModel.BackItemsFk> list, CompanyModel companyModel, Context context) {
        this.list = list;
        this.context = context;
        this.companyModel = companyModel;


    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        InviceRow3Binding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.invice_row3, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {
        RetrieveResponseModel.BackItemsFk model = list.get(position);
        holder.binding.setCompanyModel(companyModel);
        holder.binding.setModel(model);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private InviceRow3Binding binding;

        public MyHolder(InviceRow3Binding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }


    }
}
