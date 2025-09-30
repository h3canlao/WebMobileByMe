package com.example.myapplication.ui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
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
import com.example.myapplication.repository.VehicleRepository;
import com.example.myapplication.utils.MockDataGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VehicleListActivity extends AppCompatActivity implements VehicleAdapter.OnVehicleClickListener {

    private RecyclerView rvVehicles;
    private LinearLayout layoutEmpty;
    private EditText etSearch;
    private ImageView btnBack, btnFilter;
    private TextView tvAvailableCount, tvTotalCount, tvSoldCount;
    
    private VehicleAdapter vehicleAdapter;
    private VehicleRepository vehicleRepository;
    private List<Vehicle> allVehicles = new ArrayList<>();
    private List<Vehicle> filteredVehicles = new ArrayList<>();
    
    private String currentSearchQuery = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_list);

        vehicleRepository = new VehicleRepository();
        
        initViews();
        setupRecyclerView();
        setupClickListeners();
        setupSearchFilter();
        loadVehicles();
        updateStats();
    }

    private void initViews() {
        rvVehicles = findViewById(R.id.rvVehicles);
        layoutEmpty = findViewById(R.id.layoutEmpty);
        etSearch = findViewById(R.id.etSearch);
        btnBack = findViewById(R.id.btnBack);
        btnFilter = findViewById(R.id.btnFilter);
        tvAvailableCount = findViewById(R.id.tvAvailableCount);
        tvTotalCount = findViewById(R.id.tvTotalCount);
        tvSoldCount = findViewById(R.id.tvSoldCount);
    }
    
    private void setupRecyclerView() {
        vehicleAdapter = new VehicleAdapter(filteredVehicles, this);
        rvVehicles.setLayoutManager(new LinearLayoutManager(this));
        rvVehicles.setAdapter(vehicleAdapter);
    }
    
    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> {
            animateButtonClick(btnBack);
            finish();
        });
        
        btnFilter.setOnClickListener(v -> {
            animateButtonClick(btnFilter);
            Toast.makeText(this, "Chức năng lọc", Toast.LENGTH_SHORT).show();
        });
    }
    
    private void setupSearchFilter() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentSearchQuery = s.toString().toLowerCase().trim();
                applyFilters();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
    
    private void loadVehicles() {
        allVehicles.clear();
        allVehicles.addAll(MockDataGenerator.getMockVehicles());
        applyFilters();
        Toast.makeText(this, "Đã tải danh sách xe thành công!", Toast.LENGTH_SHORT).show();
    }
    
    private void applyFilters() {
        filteredVehicles.clear();
        
        if (currentSearchQuery.isEmpty()) {
            filteredVehicles.addAll(allVehicles);
        } else {
            filteredVehicles.addAll(allVehicles.stream()
                    .filter(vehicle -> 
                        vehicle.getName().toLowerCase().contains(currentSearchQuery) ||
                        vehicle.getBrand().toLowerCase().contains(currentSearchQuery) ||
                        vehicle.getModel().toLowerCase().contains(currentSearchQuery) ||
                        vehicle.getColor().toLowerCase().contains(currentSearchQuery))
                    .collect(Collectors.toList()));
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
    
    private void updateStats() {
        int total = allVehicles.size();
        long available = allVehicles.stream().filter(v -> "AVAILABLE".equals(v.getStatus())).count();
        long sold = allVehicles.stream().filter(v -> "SOLD".equals(v.getStatus())).count();
        
        tvTotalCount.setText(String.valueOf(total));
        tvAvailableCount.setText(String.valueOf(available));
        tvSoldCount.setText(String.valueOf(sold));
    }

    private void animateButtonClick(View view) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 0.95f, 1.0f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 0.95f, 1.0f);
        scaleX.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleY.setInterpolator(new AccelerateDecelerateInterpolator());
        AnimatorSet buttonSet = new AnimatorSet();
        buttonSet.playTogether(scaleX, scaleY);
        buttonSet.setDuration(150);
        buttonSet.start();
    }

    @Override
    public void onVehicleClick(Vehicle vehicle) {
        Intent intent = new Intent(this, VehicleDetailActivity.class);
        intent.putExtra("vehicleId", vehicle.getId());
        startActivity(intent);
    }

    @Override
    public void onBookVehicle(Vehicle vehicle) {
        Intent intent = new Intent(this, CreateOrderActivity.class);
        intent.putExtra("vehicleId", vehicle.getId());
        startActivity(intent);
    }
}