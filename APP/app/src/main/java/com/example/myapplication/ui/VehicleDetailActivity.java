package com.example.myapplication.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import com.example.myapplication.R;
import com.example.myapplication.model.Vehicle;
import com.example.myapplication.utils.MockDataGenerator;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class VehicleDetailActivity extends AppCompatActivity {

    private ImageView ivVehicleImage, btnBack, btnFavorite;
    private TextView tvVehicleName, tvVehicleModel, tvPrice, tvStatus, tvBattery, tvRange, tvYear;
    private View colorGreen, colorWhite, colorBlack, colorRed;
    private Button btnTestDrive, btnOrderNow;
    
    private Vehicle currentVehicle;
    private String selectedColor = "Xanh";
    private boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_detail);

        // Get vehicle ID from intent
        String vehicleId = getIntent().getStringExtra("vehicleId");
        if (vehicleId == null) vehicleId = "1"; // Default to first vehicle
        
        // Load vehicle data
        loadVehicleData(vehicleId);
        
        // Initialize views
        initViews();
        
        // Setup click listeners
        setupClickListeners();
        
        // Start entrance animations
        startEntranceAnimations();
    }
    
    private void loadVehicleData(String vehicleId) {
        List<Vehicle> vehicles = MockDataGenerator.getMockVehicles();
        for (Vehicle vehicle : vehicles) {
            if (vehicleId.equals(vehicle.getId())) {
                currentVehicle = vehicle;
                break;
            }
        }
        
        if (currentVehicle == null) {
            currentVehicle = vehicles.get(0); // Fallback to first vehicle
        }
    }
    
    private void initViews() {
        ivVehicleImage = findViewById(R.id.ivVehicleImage);
        btnBack = findViewById(R.id.btnBack);
        btnFavorite = findViewById(R.id.btnFavorite);
        tvVehicleName = findViewById(R.id.tvVehicleName);
        tvVehicleModel = findViewById(R.id.tvVehicleModel);
        tvPrice = findViewById(R.id.tvPrice);
        tvStatus = findViewById(R.id.tvStatus);
        tvBattery = findViewById(R.id.tvBattery);
        tvRange = findViewById(R.id.tvRange);
        tvYear = findViewById(R.id.tvYear);
        colorGreen = findViewById(R.id.colorGreen);
        colorWhite = findViewById(R.id.colorWhite);
        colorBlack = findViewById(R.id.colorBlack);
        colorRed = findViewById(R.id.colorRed);
        btnTestDrive = findViewById(R.id.btnTestDrive);
        btnOrderNow = findViewById(R.id.btnOrderNow);
        
        // Populate data
        populateVehicleData();
        
        // Set vehicle background
        setVehicleBackground();
    }
    
    private void populateVehicleData() {
        if (currentVehicle == null) return;
        
        tvVehicleName.setText(currentVehicle.getName());
        tvVehicleModel.setText(currentVehicle.getDescription());
        tvBattery.setText(currentVehicle.getBatteryCapacity() + " kWh");
        tvRange.setText(currentVehicle.getRange() + " km");
        tvYear.setText(String.valueOf(currentVehicle.getYear()));
        
        // Format price
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        tvPrice.setText(formatter.format(currentVehicle.getPrice()));
        
        // Set status
        String statusText = getStatusText(currentVehicle.getStatus());
        tvStatus.setText(statusText);
        tvStatus.setBackgroundResource(getStatusBackground(currentVehicle.getStatus()));
        
        // Set initial color selection
        selectColor(colorGreen);
    }
    
    private void setVehicleBackground() {
        if (currentVehicle == null) return;
        
        String imageUrl = getVehicleImageUrl(currentVehicle);
        
        if (imageUrl != null && !imageUrl.isEmpty()) {
            // Load real image with Glide
            Glide.with(this)
                .load(imageUrl)
                .apply(new RequestOptions()
                    .transform(new RoundedCorners(20))
                    .placeholder(R.drawable.placeholder_car)
                    .error(R.drawable.placeholder_car))
                .into(ivVehicleImage);
        } else {
            // Fallback to gradient backgrounds
            String brand = currentVehicle.getBrand().toLowerCase();
            if (brand.contains("tesla")) {
                ivVehicleImage.setBackgroundResource(R.drawable.tesla_model_s_background);
            } else if (brand.contains("vinfast")) {
                ivVehicleImage.setBackgroundResource(R.drawable.vinfast_background);
            } else if (brand.contains("bmw")) {
                ivVehicleImage.setBackgroundResource(R.drawable.bmw_background);
            } else if (brand.contains("mercedes")) {
                ivVehicleImage.setBackgroundResource(R.drawable.mercedes_background);
            } else if (brand.contains("audi")) {
                ivVehicleImage.setBackgroundResource(R.drawable.audi_background);
            } else if (brand.contains("ford")) {
                ivVehicleImage.setBackgroundResource(R.drawable.ford_background);
            } else {
                ivVehicleImage.setBackgroundResource(R.drawable.placeholder_car);
            }
        }
    }
    
    private String getVehicleImageUrl(Vehicle vehicle) {
        String brand = vehicle.getBrand().toLowerCase();
        String model = vehicle.getName().toLowerCase();
        
        if (brand.contains("vinfast")) {
            if (model.contains("vf8")) {
                return "https://static-cms-prod.vinfast.com/statics/2024-06/vf7-1.webp";
            } else if (model.contains("vf9")) {
                return "https://static-cms-prod.vinfast.com/statics/2024-06/vf7-1.webp";
            } else if (model.contains("vf6")) {
                return "https://static-cms-prod.vinfast.com/statics/2024-06/vf7-1.webp";
            }
        } else if (brand.contains("tesla")) {
            if (model.contains("model 3")) {
                return "https://cdn1.smartprix.com/rx-iHovaGL7k-w1200-h1200/HovaGL7k.webp";
            } else if (model.contains("model s")) {
                return "https://cdn1.smartprix.com/rx-iHovaGL7k-w1200-h1200/HovaGL7k.webp";
            } else if (model.contains("model y")) {
                return "https://cdn1.smartprix.com/rx-iHovaGL7k-w1200-h1200/HovaGL7k.webp";
            }
        }
        
        return null; // No specific image found
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
    
    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> {
            animateButtonClick(btnBack);
            finish();
        });
        
        btnFavorite.setOnClickListener(v -> {
            toggleFavorite();
        });
        
        colorGreen.setOnClickListener(v -> selectColor(colorGreen));
        colorWhite.setOnClickListener(v -> selectColor(colorWhite));
        colorBlack.setOnClickListener(v -> selectColor(colorBlack));
        colorRed.setOnClickListener(v -> selectColor(colorRed));
        
        btnTestDrive.setOnClickListener(v -> {
            animateButtonClick(btnTestDrive);
            // TODO: Navigate to TestDriveActivity
            Toast.makeText(this, "Chức năng đặt lái thử", Toast.LENGTH_SHORT).show();
        });
        
        btnOrderNow.setOnClickListener(v -> {
            animateButtonClick(btnOrderNow);
            // Navigate to CreateOrderActivity
            Intent intent = new Intent(this, CreateOrderActivity.class);
            intent.putExtra("vehicleId", currentVehicle.getId());
            intent.putExtra("selectedColor", selectedColor);
            startActivity(intent);
        });
    }
    
    private void selectColor(View colorView) {
        // Reset all colors
        colorGreen.setScaleX(1f);
        colorGreen.setScaleY(1f);
        colorWhite.setScaleX(1f);
        colorWhite.setScaleY(1f);
        colorBlack.setScaleX(1f);
        colorBlack.setScaleY(1f);
        colorRed.setScaleX(1f);
        colorRed.setScaleY(1f);
        
        // Animate selected color
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(colorView, "scaleX", 1f, 1.2f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(colorView, "scaleY", 1f, 1.2f);
        
        AnimatorSet scaleSet = new AnimatorSet();
        scaleSet.playTogether(scaleX, scaleY);
        scaleSet.setDuration(200);
        scaleSet.setInterpolator(new BounceInterpolator());
        scaleSet.start();
        
        // Set selected color
        if (colorView == colorGreen) selectedColor = "Xanh";
        else if (colorView == colorWhite) selectedColor = "Trắng";
        else if (colorView == colorBlack) selectedColor = "Đen";
        else if (colorView == colorRed) selectedColor = "Đỏ";
    }
    
    private void toggleFavorite() {
        isFavorite = !isFavorite;
        
        // Animate favorite button
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(btnFavorite, "scaleX", 1f, 1.3f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(btnFavorite, "scaleY", 1f, 1.3f, 1f);
        
        AnimatorSet favoriteSet = new AnimatorSet();
        favoriteSet.playTogether(scaleX, scaleY);
        favoriteSet.setDuration(300);
        favoriteSet.setInterpolator(new BounceInterpolator());
        favoriteSet.start();
        
        // Update icon
        if (isFavorite) {
            btnFavorite.setImageResource(R.drawable.ic_favorite_border); // TODO: Add filled heart icon
            Toast.makeText(this, "Đã thêm vào yêu thích", Toast.LENGTH_SHORT).show();
        } else {
            btnFavorite.setImageResource(R.drawable.ic_favorite_border);
            Toast.makeText(this, "Đã bỏ khỏi yêu thích", Toast.LENGTH_SHORT).show();
        }
    }
    
    private void animateButtonClick(View button) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(button, "scaleX", 1f, 0.95f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(button, "scaleY", 1f, 0.95f, 1f);
        
        AnimatorSet buttonSet = new AnimatorSet();
        buttonSet.playTogether(scaleX, scaleY);
        buttonSet.setDuration(200);
        buttonSet.setInterpolator(new AccelerateDecelerateInterpolator());
        buttonSet.start();
    }
    
    private void startEntranceAnimations() {
        // Image animation
        ivVehicleImage.setAlpha(0f);
        ivVehicleImage.setScaleX(0.8f);
        ivVehicleImage.setScaleY(0.8f);
        
        ObjectAnimator imageFadeIn = ObjectAnimator.ofFloat(ivVehicleImage, "alpha", 0f, 1f);
        ObjectAnimator imageScaleX = ObjectAnimator.ofFloat(ivVehicleImage, "scaleX", 0.8f, 1f);
        ObjectAnimator imageScaleY = ObjectAnimator.ofFloat(ivVehicleImage, "scaleY", 0.8f, 1f);
        
        AnimatorSet imageSet = new AnimatorSet();
        imageSet.playTogether(imageFadeIn, imageScaleX, imageScaleY);
        imageSet.setDuration(800);
        imageSet.setInterpolator(new DecelerateInterpolator());
        imageSet.start();
        
        // Buttons animation
        btnTestDrive.setTranslationY(100f);
        btnOrderNow.setTranslationY(100f);
        btnTestDrive.setAlpha(0f);
        btnOrderNow.setAlpha(0f);
        
        ObjectAnimator testDriveSlide = ObjectAnimator.ofFloat(btnTestDrive, "translationY", 100f, 0f);
        ObjectAnimator testDriveFade = ObjectAnimator.ofFloat(btnTestDrive, "alpha", 0f, 1f);
        ObjectAnimator orderNowSlide = ObjectAnimator.ofFloat(btnOrderNow, "translationY", 100f, 0f);
        ObjectAnimator orderNowFade = ObjectAnimator.ofFloat(btnOrderNow, "alpha", 0f, 1f);
        
        AnimatorSet buttonsSet = new AnimatorSet();
        buttonsSet.playTogether(testDriveSlide, testDriveFade, orderNowSlide, orderNowFade);
        buttonsSet.setDuration(600);
        buttonsSet.setInterpolator(new DecelerateInterpolator());
        buttonsSet.setStartDelay(400);
        buttonsSet.start();
    }
}
