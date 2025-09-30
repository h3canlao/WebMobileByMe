package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.R;
import com.example.myapplication.model.Vehicle;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.VehicleViewHolder> {

    private List<Vehicle> vehicles;
    private OnVehicleClickListener listener;

    public interface OnVehicleClickListener {
        void onVehicleClick(Vehicle vehicle);
        void onBookVehicle(Vehicle vehicle);
    }

    public VehicleAdapter(List<Vehicle> vehicles, OnVehicleClickListener listener) {
        this.vehicles = vehicles;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VehicleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vehicle, parent, false);
        return new VehicleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleViewHolder holder, int position) {
        Vehicle vehicle = vehicles.get(position);
        holder.bind(vehicle, listener);
    }

    @Override
    public int getItemCount() {
        return vehicles.size();
    }
    
    public void updateVehicles(List<Vehicle> newVehicles) {
        this.vehicles = newVehicles;
        notifyDataSetChanged();
    }

    static class VehicleViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivVehicleImage;
        private TextView tvVehicleName, tvStatus, tvBrandModel, tvPrice, tvStock;
        private Button btnViewDetails, btnBookVehicle;

        public VehicleViewHolder(@NonNull View itemView) {
            super(itemView);
            
            ivVehicleImage = itemView.findViewById(R.id.ivVehicleImage);
            tvVehicleName = itemView.findViewById(R.id.tvVehicleName);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvBrandModel = itemView.findViewById(R.id.tvBrandModel);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvStock = itemView.findViewById(R.id.tvStock);
            btnViewDetails = itemView.findViewById(R.id.btnViewDetails);
            btnBookVehicle = itemView.findViewById(R.id.btnBookVehicle);
        }

        public void bind(Vehicle vehicle, OnVehicleClickListener listener) {
            // Set basic info
            tvVehicleName.setText(vehicle.getName());
            tvBrandModel.setText(vehicle.getBrand() + " • " + vehicle.getModel());
            
            // Format price
            NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            tvPrice.setText(formatter.format(vehicle.getPrice()));
            
            // Set stock
            tvStock.setText(vehicle.getStockQuantity() + " xe");
            
            // Set status
            String statusText = getStatusText(vehicle.getStatus());
            tvStatus.setText(statusText);
            tvStatus.setBackgroundResource(getStatusBackground(vehicle.getStatus()));
            
            // Set vehicle image
            setVehicleImage(vehicle);
            
            // Handle button states based on availability
            boolean isAvailable = "AVAILABLE".equals(vehicle.getStatus()) && vehicle.getStockQuantity() > 0;
            btnBookVehicle.setEnabled(isAvailable);
            btnBookVehicle.setText(isAvailable ? "Đặt xe" : "Hết hàng");
            btnBookVehicle.setAlpha(isAvailable ? 1.0f : 0.5f);
            
            // Set click listeners
            btnViewDetails.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onVehicleClick(vehicle);
                }
            });
            
            btnBookVehicle.setOnClickListener(v -> {
                if (listener != null && isAvailable) {
                    listener.onBookVehicle(vehicle);
                }
            });
            
            // Set item click listener for details
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onVehicleClick(vehicle);
                }
            });
        }
        
        private void setVehicleImage(Vehicle vehicle) {
            String imageUrl = getVehicleImageUrl(vehicle);
            
            if (imageUrl != null && !imageUrl.isEmpty()) {
                // Load real image with Glide
                Glide.with(itemView.getContext())
                    .load(imageUrl)
                    .apply(new RequestOptions()
                        .transform(new RoundedCorners(12))
                        .placeholder(R.drawable.placeholder_car)
                        .error(R.drawable.placeholder_car))
                    .into(ivVehicleImage);
            } else {
                // Fallback to gradient backgrounds
                String brand = vehicle.getBrand().toLowerCase();
                if (brand.contains("tesla")) {
                    ivVehicleImage.setBackgroundResource(R.drawable.tesla_model_s_background);
                } else if (brand.contains("vinfast")) {
                    ivVehicleImage.setBackgroundResource(R.drawable.vinfast_background);
                } else if (brand.contains("bmw")) {
                    ivVehicleImage.setBackgroundResource(R.drawable.bmw_background);
                } else if (brand.contains("mercedes")) {
                    ivVehicleImage.setBackgroundResource(R.drawable.mercedes_background);
                } else {
                    ivVehicleImage.setBackgroundResource(R.drawable.placeholder_car);
                }
            }
        }
        
        private String getVehicleImageUrl(Vehicle vehicle) {
            String brand = vehicle.getBrand().toLowerCase();
            String model = vehicle.getName().toLowerCase();
            
            if (brand.contains("vinfast")) {
                return "https://static-cms-prod.vinfast.com/statics/2024-06/vf7-1.webp";
            } else if (brand.contains("tesla")) {
                return "https://cdn1.smartprix.com/rx-iHovaGL7k-w1200-h1200/HovaGL7k.webp";
            }
            
            return vehicle.getImageUrl(); // Use the URL from the vehicle object
        }
        
        private String getStatusText(String status) {
            switch (status) {
                case "AVAILABLE": return "Có sẵn";
                case "SOLD": return "Đã bán";
                case "MAINTENANCE": return "Bảo trì";
                default: return status;
            }
        }
        
        private int getStatusBackground(String status) {
            switch (status) {
                case "AVAILABLE": return R.drawable.bg_status_available;
                case "SOLD": return R.drawable.bg_status_sold;
                case "MAINTENANCE": return R.drawable.bg_status_maintenance;
                default: return R.drawable.bg_status_available;
            }
        }
    }
}