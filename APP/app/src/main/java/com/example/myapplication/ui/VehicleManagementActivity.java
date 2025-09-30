package com.example.myapplication.ui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.VehicleAdapter;
import com.example.myapplication.model.Vehicle;
import com.example.myapplication.utils.MockDataGenerator;

import java.util.ArrayList;
import java.util.List;

public class VehicleManagementActivity extends AppCompatActivity implements VehicleAdapter.OnVehicleClickListener {

    private RecyclerView rvVehicles;
    private LinearLayout layoutEmpty;
    private EditText etSearch;
    private ImageView btnBack, btnAddVehicle;
    private Button btnFilter;
    private TextView chipAll, chipAvailable, chipSold, chipMaintenance;
    private TextView tvTotalVehicles, tvAvailableVehicles, tvSoldVehicles;
    
    private VehicleAdapter vehicleAdapter;
    private List<Vehicle> allVehicles = new ArrayList<>();
    private List<Vehicle> filteredVehicles = new ArrayList<>();
    
    private String currentFilter = "ALL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_management);

        initViews();
        setupRecyclerView();
        setupClickListeners();
        loadVehicles();
        updateStats();
    }
    
    private void initViews() {
        try {
            rvVehicles = findViewById(R.id.rvVehicles);
            layoutEmpty = findViewById(R.id.layoutEmpty);
            etSearch = findViewById(R.id.etSearch);
            btnBack = findViewById(R.id.btnBack);
            btnAddVehicle = findViewById(R.id.btnAddVehicle);
            btnFilter = findViewById(R.id.btnFilter);
            chipAll = findViewById(R.id.chipAll);
            chipAvailable = findViewById(R.id.chipAvailable);
            chipSold = findViewById(R.id.chipSold);
            chipMaintenance = findViewById(R.id.chipMaintenance);
            tvTotalVehicles = findViewById(R.id.tvTotalVehicles);
            tvAvailableVehicles = findViewById(R.id.tvAvailableVehicles);
            tvSoldVehicles = findViewById(R.id.tvSoldVehicles);
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi khởi tạo UI: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
    
    private void setupRecyclerView() {
        try {
            vehicleAdapter = new VehicleAdapter(filteredVehicles, this);
            rvVehicles.setLayoutManager(new LinearLayoutManager(this));
            rvVehicles.setAdapter(vehicleAdapter);
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi setup RecyclerView: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
    
    private void setupClickListeners() {
        try {
            if (btnBack != null) {
                btnBack.setOnClickListener(v -> {
                    animateButtonClick(btnBack);
                    finish();
                });
            }
            
                    if (btnAddVehicle != null) {
                        btnAddVehicle.setOnClickListener(v -> {
                            animateButtonClick(btnAddVehicle);
                            // Navigate to AddVehicleActivity
                            Intent intent = new Intent(this, AddVehicleActivity.class);
                            startActivity(intent);
                        });
                    }
            
            if (btnFilter != null) {
                btnFilter.setOnClickListener(v -> {
                    animateButtonClick(btnFilter);
                    // TODO: Show advanced filter dialog
                    Toast.makeText(this, "Chức năng lọc nâng cao", Toast.LENGTH_SHORT).show();
                });
            }
            
            if (chipAll != null) chipAll.setOnClickListener(v -> setFilter("ALL"));
            if (chipAvailable != null) chipAvailable.setOnClickListener(v -> setFilter("AVAILABLE"));
            if (chipSold != null) chipSold.setOnClickListener(v -> setFilter("SOLD"));
            if (chipMaintenance != null) chipMaintenance.setOnClickListener(v -> setFilter("MAINTENANCE"));
            
            if (etSearch != null) {
                etSearch.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        applyFilters();
                    }

                    @Override
                    public void afterTextChanged(Editable s) {}
                });
            }
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi setup click listeners: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
    
    private void setFilter(String filter) {
        currentFilter = filter;
        updateChipSelection();
        applyFilters();
    }
    
    private void updateChipSelection() {
        // Reset all chips
        chipAll.setBackgroundResource(R.drawable.bg_chip_unselected);
        chipAll.setTextColor(getResources().getColor(R.color.text_secondary));
        chipAvailable.setBackgroundResource(R.drawable.bg_chip_unselected);
        chipAvailable.setTextColor(getResources().getColor(R.color.text_secondary));
        chipSold.setBackgroundResource(R.drawable.bg_chip_unselected);
        chipSold.setTextColor(getResources().getColor(R.color.text_secondary));
        chipMaintenance.setBackgroundResource(R.drawable.bg_chip_unselected);
        chipMaintenance.setTextColor(getResources().getColor(R.color.text_secondary));
        
        // Select current chip
        switch (currentFilter) {
            case "ALL":
                chipAll.setBackgroundResource(R.drawable.bg_chip_selected);
                chipAll.setTextColor(getResources().getColor(R.color.primary_green));
                break;
            case "AVAILABLE":
                chipAvailable.setBackgroundResource(R.drawable.bg_chip_selected);
                chipAvailable.setTextColor(getResources().getColor(R.color.primary_green));
                break;
            case "SOLD":
                chipSold.setBackgroundResource(R.drawable.bg_chip_selected);
                chipSold.setTextColor(getResources().getColor(R.color.primary_green));
                break;
            case "MAINTENANCE":
                chipMaintenance.setBackgroundResource(R.drawable.bg_chip_selected);
                chipMaintenance.setTextColor(getResources().getColor(R.color.primary_green));
                break;
        }
    }
    
    private void applyFilters() {
        filteredVehicles.clear();
        
        String searchQuery = etSearch.getText().toString().toLowerCase().trim();
        
        for (Vehicle vehicle : allVehicles) {
            // Filter by status
            boolean statusMatch = "ALL".equals(currentFilter) || currentFilter.equals(vehicle.getStatus());
            
            // Filter by search query
            boolean searchMatch = searchQuery.isEmpty() || 
                vehicle.getName().toLowerCase().contains(searchQuery) ||
                vehicle.getBrand().toLowerCase().contains(searchQuery) ||
                String.valueOf(vehicle.getYear()).contains(searchQuery);
            
            if (statusMatch && searchMatch) {
                filteredVehicles.add(vehicle);
            }
        }
        
        vehicleAdapter.updateVehicles(filteredVehicles);
        updateEmptyState();
    }
    
    private void updateEmptyState() {
        if (filteredVehicles.isEmpty()) {
            rvVehicles.setVisibility(View.GONE);
            layoutEmpty.setVisibility(View.VISIBLE);
        } else {
            rvVehicles.setVisibility(View.VISIBLE);
            layoutEmpty.setVisibility(View.GONE);
        }
    }
    
    private void loadVehicles() {
        // Use mock data directly - no network calls
        allVehicles.clear();
        allVehicles.addAll(MockDataGenerator.getMockVehicles());
        
        // Debug log
        System.out.println("VehicleManagementActivity: Loaded " + allVehicles.size() + " vehicles");
        
        applyFilters();
        updateStats();
        
        // Show success message
        Toast.makeText(this, "Đã tải " + allVehicles.size() + " xe thành công!", Toast.LENGTH_SHORT).show();
    }
    
    private void updateStats() {
        int total = allVehicles.size();
        int available = 0;
        int sold = 0;
        
        for (Vehicle vehicle : allVehicles) {
            if ("AVAILABLE".equals(vehicle.getStatus())) {
                available++;
            } else if ("SOLD".equals(vehicle.getStatus())) {
                sold++;
            }
        }
        
        tvTotalVehicles.setText(String.valueOf(total));
        tvAvailableVehicles.setText(String.valueOf(available));
        tvSoldVehicles.setText(String.valueOf(sold));
        
        // Animate stats
        animateStats();
    }
    
    private void animateStats() {
        ObjectAnimator totalAnim = ObjectAnimator.ofFloat(tvTotalVehicles, "scaleX", 1f, 1.2f, 1f);
        ObjectAnimator availableAnim = ObjectAnimator.ofFloat(tvAvailableVehicles, "scaleX", 1f, 1.2f, 1f);
        ObjectAnimator soldAnim = ObjectAnimator.ofFloat(tvSoldVehicles, "scaleX", 1f, 1.2f, 1f);
        
        AnimatorSet statsSet = new AnimatorSet();
        statsSet.playTogether(totalAnim, availableAnim, soldAnim);
        statsSet.setDuration(500);
        statsSet.setInterpolator(new DecelerateInterpolator());
        statsSet.setStartDelay(300);
        statsSet.start();
    }
    
    private void animateButtonClick(View button) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(button, "scaleX", 1f, 0.9f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(button, "scaleY", 1f, 0.9f, 1f);
        
        AnimatorSet buttonSet = new AnimatorSet();
        buttonSet.playTogether(scaleX, scaleY);
        buttonSet.setDuration(200);
        buttonSet.start();
    }
    
    @Override
    public void onVehicleClick(Vehicle vehicle) {
        // Navigate to VehicleDetailActivity
        Intent intent = new Intent(this, VehicleDetailActivity.class);
        intent.putExtra("vehicleId", vehicle.getId());
        startActivity(intent);
    }
    
    @Override
    public void onBookVehicle(Vehicle vehicle) {
        // Navigate to EditVehicleActivity (for management purposes)
        Intent intent = new Intent(this, EditVehicleActivity.class);
        intent.putExtra("vehicleId", vehicle.getId());
        startActivity(intent);
    }
}
