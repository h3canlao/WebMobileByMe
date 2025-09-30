package com.example.myapplication.ui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.model.response.DashboardResponse;
import com.example.myapplication.repository.DashboardRepository;
import com.example.myapplication.utils.MockDataGenerator;

import java.text.NumberFormat;
import java.util.Locale;

public class DashboardActivity extends AppCompatActivity {

    private DashboardRepository dashboardRepository;
    private TextView tvWelcome, tvTotalVehicles, tvTotalOrders, tvTotalCustomers;
    private Button btnViewVehicles, btnCreateOrder, btnManageVehicles, btnViewOrders, btnManageCustomers, btnTestDrive;
    private ImageView btnMenu;
    private EditText etSearch;
    
    private String currentRole;
    private String currentUsername;
    private String currentDealerId;
    private String currentStaffId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Khởi tạo repository
        dashboardRepository = new DashboardRepository();
        
        // Lấy thông tin user từ SharedPreferences
        currentUsername = LoginActivity.getUsername(this);
        currentRole = LoginActivity.getRole(this);
        
        // TODO: Lấy dealerId và staffId từ API hoặc SharedPreferences
        currentDealerId = "dealer001"; // Mock data
        currentStaffId = "staff001"; // Mock data
        
        // Initialize views
        initViews();
        
        // Setup UI based on role
        setupUI();
        
        // Load dashboard data
        loadDashboardData();
        
        // Setup click listeners
        setupClickListeners();
    }

    private void initViews() {
        btnMenu = findViewById(R.id.btnMenu);
        tvWelcome = findViewById(R.id.tvWelcome);
        etSearch = findViewById(R.id.etSearch);
        btnCreateOrder = findViewById(R.id.btnCreateOrder);
        btnViewVehicles = findViewById(R.id.btnViewVehicles);
        btnViewOrders = findViewById(R.id.btnViewOrders);
        btnManageVehicles = findViewById(R.id.btnManageVehicles);
        btnManageCustomers = findViewById(R.id.btnManageCustomers);
        btnTestDrive = findViewById(R.id.btnTestDrive);
        tvTotalVehicles = findViewById(R.id.tvTotalVehicles);
        tvTotalOrders = findViewById(R.id.tvTotalOrders);
        tvTotalCustomers = findViewById(R.id.tvTotalCustomers);
    }
    
    private void setupUI() {
        // Set welcome message
        tvWelcome.setText("Xin chào, " + (currentUsername != null ? currentUsername : "User"));
        
        // Setup buttons based on role
        if ("DEALER_STAFF".equals(currentRole)) {
            btnCreateOrder.setText("Tạo đơn hàng");
            btnViewVehicles.setText("Xem xe có sẵn");
        } else if ("DEALER_MANAGER".equals(currentRole)) {
            btnCreateOrder.setText("Tạo đơn hàng");
            btnViewVehicles.setText("Xem danh sách xe");
        } else if ("ADMIN".equals(currentRole)) {
            btnCreateOrder.setText("Quản lý hệ thống");
            btnViewVehicles.setText("Quản lý xe toàn hệ thống");
        } else if ("EVN_STAFF".equals(currentRole)) {
            btnCreateOrder.setText("Quản lý đơn hàng");
            btnViewVehicles.setText("Quản lý xe");
        }
    }
    
    private void loadDashboardData() {
        try {
            // Sử dụng mock data thay vì API call
            DashboardResponse dashboardData = MockDataGenerator.getMockDashboardData(currentRole);
            
            // Update UI with dashboard data
            tvTotalVehicles.setText(String.valueOf(dashboardData.getTotalVehicles()));
            tvTotalOrders.setText(String.valueOf(dashboardData.getTotalOrders()));
            tvTotalCustomers.setText(String.valueOf(dashboardData.getTotalCustomers()));
            
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Lỗi khi tải dữ liệu dashboard: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    
    private void setupClickListeners() {
        // Menu button
        btnMenu.setOnClickListener(v -> {
            animateButtonClick(btnMenu);
            Toast.makeText(this, "Menu", Toast.LENGTH_SHORT).show();
        });

        // Search functionality
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO: Implement search functionality
                if (s.length() > 0) {
                    Toast.makeText(DashboardActivity.this, "Tìm kiếm: " + s.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Vehicle Management
        btnViewVehicles.setOnClickListener(v -> {
            animateButtonClick(btnViewVehicles);
            Intent intent = new Intent(this, VehicleListActivity.class);
            startActivity(intent);
        });

        btnManageVehicles.setOnClickListener(v -> {
            animateButtonClick(btnManageVehicles);
            Intent intent = new Intent(this, VehicleManagementActivity.class);
            startActivity(intent);
        });

        // Order Management
        btnCreateOrder.setOnClickListener(v -> {
            animateButtonClick(btnCreateOrder);
            String buttonText = btnCreateOrder.getText().toString();
            Intent intent;
            
            if ("Quản lý hệ thống".equals(buttonText)) {
                // For ADMIN role - go to system management (use VehicleManagement for now)
                intent = new Intent(this, VehicleManagementActivity.class);
            } else if ("Quản lý đơn hàng".equals(buttonText)) {
                // For EVN_STAFF role - go to order management
                intent = new Intent(this, OrderListActivity.class);
            } else {
                // For DEALER roles - go to create order
                intent = new Intent(this, CreateOrderActivity.class);
            }
            startActivity(intent);
        });

        btnViewOrders.setOnClickListener(v -> {
            animateButtonClick(btnViewOrders);
            Intent intent = new Intent(this, OrderListActivity.class);
            startActivity(intent);
        });

        // Customer Management
        btnManageCustomers.setOnClickListener(v -> {
            animateButtonClick(btnManageCustomers);
            Intent intent = new Intent(this, CustomerManagementActivity.class);
            startActivity(intent);
        });

        // Test Drive Management
        btnTestDrive.setOnClickListener(v -> {
            animateButtonClick(btnTestDrive);
            Intent intent = new Intent(this, TestDriveManagementActivity.class);
            startActivity(intent);
        });
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

    private String getRoleDisplayName(String role) {
        switch (role) {
            case "ADMIN":
                return "Quản trị viên";
            case "EVN_STAFF":
                return "Nhân viên EVN";
            case "DEALER_MANAGER":
                return "Quản lý đại lý";
            case "DEALER_STAFF":
                return "Nhân viên đại lý";
            default:
                return role;
        }
    }
}