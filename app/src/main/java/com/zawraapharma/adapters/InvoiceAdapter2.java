package com.zawraapharma.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.zawraapharma.R;
import com.zawraapharma.databinding.InviceRow2Binding;
import com.zawraapharma.databinding.InviceRowBinding;
import com.zawraapharma.models.BillResponse;
import com.zawraapharma.models.InvoiceModel;
import com.zawraapharma.ui.activity_pay_bill.PayBillActivity;

import java.util.List;
import java.util.Locale;

public class InvoiceAdapter2 extends RecyclerView.Adapter<InvoiceAdapter2.MyHolder> {

    private List<BillResponse.Data.Bill> list;
    private Context context;

    public InvoiceAdapter2(List<BillResponse.Data.Bill> list, Context context) {
        this.list = list;
        this.context = context;



    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        InviceRow2Binding bankRowBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.invice_row2, parent, false);
        return new MyHolder(bankRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {
        BillResponse.Data.Bill model = list.get(position);
        holder.binding.setModel(model);
        holder.binding.tvRemain.setText(String.format(Locale.ENGLISH, "%.2f",model.getBill_fk().getRemaining()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private InviceRow2Binding binding;

        public MyHolder(InviceRow2Binding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }


    }
}
