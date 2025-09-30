package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Customer;

import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {

    private List<Customer> customers;
    private OnCustomerClickListener listener;

    public interface OnCustomerClickListener {
        void onCustomerClick(Customer customer);
        void onEditCustomer(Customer customer);
    }

    public CustomerAdapter(List<Customer> customers, OnCustomerClickListener listener) {
        this.customers = customers;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_customer, parent, false);
        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        Customer customer = customers.get(position);
        holder.bind(customer, listener);
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }

    public void updateCustomers(List<Customer> newCustomers) {
        this.customers = newCustomers;
        notifyDataSetChanged();
    }

    static class CustomerViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivCustomerAvatar;
        private TextView tvCustomerName, tvCustomerEmail, tvCustomerPhone, tvCustomerStatus, tvCustomerOrders;
        private Button btnViewDetails, btnEdit, btnDelete;

        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            
            ivCustomerAvatar = itemView.findViewById(R.id.ivCustomerAvatar);
            tvCustomerName = itemView.findViewById(R.id.tvCustomerName);
            tvCustomerEmail = itemView.findViewById(R.id.tvCustomerEmail);
            tvCustomerPhone = itemView.findViewById(R.id.tvCustomerPhone);
            tvCustomerStatus = itemView.findViewById(R.id.tvCustomerStatus);
            tvCustomerOrders = itemView.findViewById(R.id.tvCustomerOrders);
            btnViewDetails = itemView.findViewById(R.id.btnViewDetails);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }

        public void bind(Customer customer, OnCustomerClickListener listener) {
            // Set basic info
            tvCustomerName.setText(customer.getName());
            tvCustomerEmail.setText(customer.getEmail());
            tvCustomerPhone.setText(customer.getPhone());
            
            // Set status
            String statusText = getStatusText(customer.getStatus());
            tvCustomerStatus.setText(statusText);
            tvCustomerStatus.setBackgroundResource(getStatusBackground(customer.getStatus()));
            
            // Set orders count (mock data)
            tvCustomerOrders.setText("2 đơn hàng");
            
            // Set avatar
            ivCustomerAvatar.setImageResource(R.drawable.ic_person);
            
            // Set click listeners
            btnViewDetails.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onCustomerClick(customer);
                }
            });
            
            btnEdit.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onEditCustomer(customer);
                }
            });
            
            btnDelete.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onEditCustomer(customer); // Use edit for delete functionality
                }
            });
            
            // Set item click listener for details
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onCustomerClick(customer);
                }
            });
        }
        
        private String getStatusText(String status) {
            switch (status) {
                case "ACTIVE": return "Hoạt động";
                case "INACTIVE": return "Không hoạt động";
                case "BLOCKED": return "Bị khóa";
                default: return status;
            }
        }
        
        private int getStatusBackground(String status) {
            switch (status) {
                case "ACTIVE": return R.drawable.bg_status_available;
                case "INACTIVE": return R.drawable.bg_status_maintenance;
                case "BLOCKED": return R.drawable.bg_status_sold;
                default: return R.drawable.bg_status_available;
            }
        }
    }
}
