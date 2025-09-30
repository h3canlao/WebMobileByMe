package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Order;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<Order> orders;
    private OnOrderClickListener listener;

    public interface OnOrderClickListener {
        void onOrderClick(Order order);
    }

    public OrderAdapter(List<Order> orders, OnOrderClickListener listener) {
        this.orders = orders;
        this.listener = listener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.bind(order);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public void updateOrders(List<Order> newOrders) {
        this.orders = newOrders;
        notifyDataSetChanged();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder {
        private TextView tvOrderId, tvVehicleName, tvCustomer, tvAmount, tvStatus, tvDate;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderId = itemView.findViewById(R.id.tvOrderId);
            tvVehicleName = itemView.findViewById(R.id.tvVehicleName);
            tvCustomer = itemView.findViewById(R.id.tvCustomer);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvDate = itemView.findViewById(R.id.tvDate);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onOrderClick(orders.get(getAdapterPosition()));
                }
            });
        }

        public void bind(Order order) {
            tvOrderId.setText(order.getOrderNumber());
            tvVehicleName.setText(order.getVehicleName());
            tvCustomer.setText("Khách hàng: " + order.getCustomerName());
            
            // Format amount
            NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            tvAmount.setText(formatter.format(order.getTotalAmount()));
            
            // Set status with color
            tvStatus.setText(getStatusText(order.getStatus()));
            tvStatus.setBackgroundResource(getStatusBackground(order.getStatus()));
            
            // Format date
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            tvDate.setText(dateFormat.format(order.getOrderDate()));
        }

        private String getVehicleName(String vehicleId) {
            switch (vehicleId) {
                case "1": return "VinFast VF8";
                case "2": return "VinFast VF9";
                case "3": return "Tesla Model 3";
                case "4": return "Tesla Model Y";
                case "5": return "VinFast VF6";
                default: return "Xe điện";
            }
        }

        private String getStatusText(String status) {
            switch (status) {
                case "PENDING": return "Chờ duyệt";
                case "APPROVED": return "Đã duyệt";
                case "COMPLETED": return "Hoàn thành";
                case "CANCELLED": return "Đã hủy";
                case "IN_PROGRESS": return "Đang xử lý";
                default: return status;
            }
        }

        private int getStatusBackground(String status) {
            switch (status) {
                case "PENDING": return R.drawable.bg_status_pending;
                case "APPROVED": return R.drawable.bg_status_approved;
                case "COMPLETED": return R.drawable.bg_status_completed;
                case "CANCELLED": return R.drawable.bg_status_cancelled;
                case "IN_PROGRESS": return R.drawable.bg_status_pending;
                default: return R.drawable.bg_status_pending;
            }
        }
    }
}



