package com.zawraapharma.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.zawraapharma.R;
import com.zawraapharma.databinding.InviceRowBinding;
import com.zawraapharma.databinding.ProductRowBinding;
import com.zawraapharma.models.CompanyProductModel;
import com.zawraapharma.models.InvoiceModel;
import com.zawraapharma.ui.activity_pay_bill.PayBillActivity;
import com.zawraapharma.ui.activity_retrieve_bill.RetrieveBillActivity;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyHolder> {

    private List<CompanyProductModel> list;
    private Context context;
    private RetrieveBillActivity activity;

    public ProductAdapter(List<CompanyProductModel> list, Context context) {
        this.list = list;
        this.context = context;
        activity = (RetrieveBillActivity) context;



    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductRowBinding bankRowBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.product_row, parent, false);
        return new MyHolder(bankRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {
        CompanyProductModel model = list.get(position);
        holder.binding.setModel(model);
        if (model.isSelected()){
            holder.binding.edtAmount.setEnabled(false);

        }else {
            holder.binding.edtAmount.setEnabled(true);
        }
        holder.binding.imageEdit.setOnClickListener(view -> {
            CompanyProductModel model2 = list.get(holder.getAdapterPosition());
            if (model2.isSelected()){
                model2.setSelected(false);
                activity.addUpdateItem(holder.getAdapterPosition(),model2,"0");

                list.set(holder.getAdapterPosition(),model2);
                notifyItemChanged(holder.getAdapterPosition());
            }else {
                String amount = holder.binding.edtAmount.getText().toString();
                if (!amount.isEmpty()){

                    model2.setSelected(true);
                    model2.setAmount(amount);
                    activity.addUpdateItem(holder.getAdapterPosition(),model2,amount);
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
        private ProductRowBinding binding;

        public MyHolder(ProductRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }


    }
}
