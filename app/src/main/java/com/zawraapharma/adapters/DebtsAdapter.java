package com.zawraapharma.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.zawraapharma.R;
import com.zawraapharma.databinding.DebtsRowBinding;
import com.zawraapharma.databinding.ProductRowBinding;
import com.zawraapharma.models.CompanyProductModel;
import com.zawraapharma.models.DebtsModel;
import com.zawraapharma.ui.activity_dept_disclosure.DebtDisclosureActivity;
import com.zawraapharma.ui.activity_retrieve_bill.RetrieveBillActivity;

import java.util.List;
import java.util.Locale;

public class DebtsAdapter extends RecyclerView.Adapter<DebtsAdapter.MyHolder> {

    private List<DebtsModel> list;
    private Context context;
    private DebtDisclosureActivity activity;

    public DebtsAdapter(List<DebtsModel> list, Context context) {
        this.list = list;
        this.context = context;
        activity = (DebtDisclosureActivity) context;

    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DebtsRowBinding bankRowBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.debts_row, parent, false);
        return new MyHolder(bankRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {
        DebtsModel model = list.get(position);
        holder.binding.setModel(model);
        holder.binding.tvDebtAmount.setText(String.format(Locale.ENGLISH,"%.2f",model.getDebt_amount()));
        holder.itemView.setOnClickListener(view -> {
            DebtsModel model2 = list.get(holder.getAdapterPosition());
            activity.setItemData(model2);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private DebtsRowBinding binding;

        public MyHolder(DebtsRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }


    }
}
