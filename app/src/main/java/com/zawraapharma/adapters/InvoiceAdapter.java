package com.zawraapharma.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.zawraapharma.R;
import com.zawraapharma.databinding.InviceRowBinding;
import com.zawraapharma.databinding.PharmacyRowBinding;
import com.zawraapharma.models.InvoiceModel;
import com.zawraapharma.models.PharmacyModel;
import com.zawraapharma.ui.activity_pay_bill.PayBillActivity;
import com.zawraapharma.ui.activity_pay_pill.PayPillActivity;

import java.util.List;

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.MyHolder> {

    private List<InvoiceModel> list;
    private Context context;
    private PayBillActivity activity;

    public InvoiceAdapter(List<InvoiceModel> list, Context context) {
        this.list = list;
        this.context = context;
        activity = (PayBillActivity) context;



    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        InviceRowBinding bankRowBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.invice_row, parent, false);
        return new MyHolder(bankRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {
        InvoiceModel model = list.get(position);
        holder.binding.setModel(model);
        int remain = (int) model.getRemaining();
        holder.binding.tvRemain.setText(String.valueOf(remain));
        if (model.isSelected()){
            holder.binding.edtAmount.setEnabled(false);

        }else {
            holder.binding.edtAmount.setEnabled(true);
        }
        holder.binding.imageEdit.setOnClickListener(view -> {
            InvoiceModel model2 = list.get(holder.getAdapterPosition());
            if (model2.isSelected()){
                model2.setSelected(false);
                activity.addUpdateItem(holder.getAdapterPosition(),model2,"0");

                list.set(holder.getAdapterPosition(),model2);
                notifyItemChanged(holder.getAdapterPosition());
            }else {
                String amount = holder.binding.edtAmount.getText().toString();
                if (!amount.isEmpty()&&model2.getRemaining()>0){

                    if (Double.parseDouble(amount)>model2.getRemaining()){
                        activity.createAlert();

                    }else {
                        model2.setSelected(true);
                        model2.setAmount(amount);
                        activity.addUpdateItem(holder.getAdapterPosition(),model2,amount);
                        list.set(holder.getAdapterPosition(),model2);
                        notifyItemChanged(holder.getAdapterPosition());
                    }

                }

            }




        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private InviceRowBinding binding;

        public MyHolder(InviceRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }


    }
}
