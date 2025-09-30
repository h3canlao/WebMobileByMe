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
import com.example.myapplication.model.TestDrive;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TestDriveAdapter extends RecyclerView.Adapter<TestDriveAdapter.TestDriveViewHolder> {

    private List<TestDrive> testDrives;
    private OnTestDriveClickListener listener;

    public interface OnTestDriveClickListener {
        void onViewDetails(TestDrive testDrive);
        void onEdit(TestDrive testDrive);
        void onCancel(TestDrive testDrive);
    }

    public TestDriveAdapter(List<TestDrive> testDrives, OnTestDriveClickListener listener) {
        this.testDrives = testDrives;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TestDriveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_test_drive, parent, false);
        return new TestDriveViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestDriveViewHolder holder, int position) {
        TestDrive testDrive = testDrives.get(position);
        holder.bind(testDrive, listener);
    }

    @Override
    public int getItemCount() {
        return testDrives.size();
    }

    public void updateTestDrives(List<TestDrive> newTestDrives) {
        this.testDrives.clear();
        this.testDrives.addAll(newTestDrives);
        notifyDataSetChanged();
    }

    static class TestDriveViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCustomerName, tvVehicleName, tvDate, tvTime, tvStatus;
        private Button btnViewDetails, btnEdit;

        public TestDriveViewHolder(@NonNull View itemView) {
            super(itemView);
            
            tvCustomerName = itemView.findViewById(R.id.tvCustomerName);
            tvVehicleName = itemView.findViewById(R.id.tvVehicleName);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            btnViewDetails = itemView.findViewById(R.id.btnViewDetails);
            btnEdit = itemView.findViewById(R.id.btnEdit);
        }

        public void bind(TestDrive testDrive, OnTestDriveClickListener listener) {
            // Set basic info
            tvCustomerName.setText(testDrive.getCustomerName());
            tvVehicleName.setText(testDrive.getVehicleName());
            
            // Set date and time
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
            
            tvDate.setText(dateFormat.format(testDrive.getScheduledDate()));
            tvTime.setText(timeFormat.format(testDrive.getScheduledDate()));
            
            // Set status
            String statusText = getStatusText(testDrive.getStatus());
            tvStatus.setText(statusText);
            tvStatus.setBackgroundResource(getStatusBackground(testDrive.getStatus()));
            
            // Set click listeners
            btnViewDetails.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onViewDetails(testDrive);
                }
            });
            
            btnEdit.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onEdit(testDrive);
                }
            });
            
            // Set item click listener for details
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onViewDetails(testDrive);
                }
            });
        }
        
        private String getStatusText(String status) {
            switch (status) {
                case "SCHEDULED": return "Đã lên lịch";
                case "COMPLETED": return "Hoàn thành";
                case "CANCELLED": return "Đã hủy";
                case "IN_PROGRESS": return "Đang thực hiện";
                default: return status;
            }
        }
        
        private int getStatusBackground(String status) {
            switch (status) {
                case "SCHEDULED": return R.drawable.bg_status_available;
                case "COMPLETED": return R.drawable.bg_status_approved;
                case "CANCELLED": return R.drawable.bg_status_cancelled;
                case "IN_PROGRESS": return R.drawable.bg_status_pending;
                default: return R.drawable.bg_status_available;
            }
        }
    }
}
