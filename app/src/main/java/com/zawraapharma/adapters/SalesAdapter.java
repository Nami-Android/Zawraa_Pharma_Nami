package com.zawraapharma.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.zawraapharma.R;
import com.zawraapharma.databinding.InviceRowBinding;
import com.zawraapharma.databinding.SalesRowBinding;
import com.zawraapharma.models.CompanyProductModel;
import com.zawraapharma.models.InvoiceModel;
import com.zawraapharma.ui.activity_pay_bill.PayBillActivity;
import com.zawraapharma.ui.activity_sales.SalesActivity;

import java.util.List;

public class SalesAdapter extends RecyclerView.Adapter<SalesAdapter.MyHolder> {

    private List<CompanyProductModel> list;
    private Context context;
    private SalesActivity activity;

    public SalesAdapter(List<CompanyProductModel> list, Context context) {
        this.list = list;
        this.context = context;
        activity = (SalesActivity) context;



    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SalesRowBinding bankRowBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.sales_row, parent, false);
        return new MyHolder(bankRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {
        CompanyProductModel model = list.get(position);
        holder.binding.setModel(model);
        if (model.isSelected()){
            holder.binding.edtAmount.setEnabled(false);
            holder.binding.edtBonus.setEnabled(false);
        }else {
            holder.binding.edtAmount.setEnabled(true);
            holder.binding.edtBonus.setEnabled(true);

        }
        holder.binding.imageEdit.setOnClickListener(view -> {
            CompanyProductModel model2 = list.get(holder.getAdapterPosition());
            if (model2.isSelected()){
                model2.setSelected(false);
                activity.addUpdateItem(holder.getAdapterPosition(),model2,"0","0");

                list.set(holder.getAdapterPosition(),model2);
                notifyItemChanged(holder.getAdapterPosition());
            }else {
                String amount = holder.binding.edtAmount.getText().toString();
                String bonus = holder.binding.edtBonus.getText().toString();
                if (!amount.isEmpty()&&!bonus.isEmpty()){

                    model2.setSelected(true);
                    model2.setAmount(amount);
                    model.setBonus(bonus);
                    activity.addUpdateItem(holder.getAdapterPosition(),model2,amount,bonus);
                    list.set(holder.getAdapterPosition(),model2);
                    notifyItemChanged(holder.getAdapterPosition());

                }

            }




        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private SalesRowBinding binding;

        public MyHolder(SalesRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }


    }
}
