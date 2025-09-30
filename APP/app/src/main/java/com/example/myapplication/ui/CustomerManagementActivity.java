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
import com.example.myapplication.adapter.CustomerAdapter;
import com.example.myapplication.model.Customer;
import com.example.myapplication.utils.MockDataGenerator;

import java.util.ArrayList;
import java.util.List;

public class CustomerManagementActivity extends AppCompatActivity implements CustomerAdapter.OnCustomerClickListener {

    private RecyclerView rvCustomers;
    private LinearLayout layoutEmpty;
    private EditText etSearch;
    private ImageView btnBack, btnAddCustomer;
    private TextView tvTotalCustomers, tvActiveCustomers, tvNewCustomers;
    
    private CustomerAdapter customerAdapter;
    private List<Customer> allCustomers = new ArrayList<>();
    private List<Customer> filteredCustomers = new ArrayList<>();
    
    private String currentFilter = "ALL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_management);

        initViews();
        setupRecyclerView();
        setupClickListeners();
        loadCustomers();
        updateStats();
    }
    
    private void initViews() {
        try {
            rvCustomers = findViewById(R.id.rvCustomers);
            layoutEmpty = findViewById(R.id.layoutEmpty);
            etSearch = findViewById(R.id.etSearch);
            btnBack = findViewById(R.id.btnBack);
            btnAddCustomer = findViewById(R.id.btnAddCustomer);
            tvTotalCustomers = findViewById(R.id.tvTotalCustomers);
            tvActiveCustomers = findViewById(R.id.tvActiveCustomers);
            tvNewCustomers = findViewById(R.id.tvNewCustomers);
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi khởi tạo UI: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
    
    private void setupRecyclerView() {
        try {
            customerAdapter = new CustomerAdapter(filteredCustomers, this);
            rvCustomers.setLayoutManager(new LinearLayoutManager(this));
            rvCustomers.setAdapter(customerAdapter);
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
            
            if (btnAddCustomer != null) {
                btnAddCustomer.setOnClickListener(v -> {
                    animateButtonClick(btnAddCustomer);
                    // Navigate to AddCustomerActivity
                    Intent intent = new Intent(this, AddCustomerActivity.class);
                    startActivity(intent);
                });
            }
            
            
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
    
    
    private void applyFilters() {
        filteredCustomers.clear();
        
        String searchQuery = etSearch.getText().toString().toLowerCase().trim();
        
        for (Customer customer : allCustomers) {
            // Filter by search query only
            boolean searchMatch = searchQuery.isEmpty() || 
                customer.getName().toLowerCase().contains(searchQuery) ||
                customer.getEmail().toLowerCase().contains(searchQuery) ||
                customer.getPhone().toLowerCase().contains(searchQuery);
            
            if (searchMatch) {
                filteredCustomers.add(customer);
            }
        }
        
        customerAdapter.updateCustomers(filteredCustomers);
        updateEmptyState();
    }
    
    private void updateEmptyState() {
        if (filteredCustomers.isEmpty()) {
            rvCustomers.setVisibility(View.GONE);
            layoutEmpty.setVisibility(View.VISIBLE);
        } else {
            rvCustomers.setVisibility(View.VISIBLE);
            layoutEmpty.setVisibility(View.GONE);
        }
    }
    
    private void loadCustomers() {
        // Use mock data directly - no network calls
        allCustomers.clear();
        allCustomers.addAll(MockDataGenerator.getMockCustomers());
        
        // Debug log
        System.out.println("CustomerManagementActivity: Loaded " + allCustomers.size() + " customers");
        
        applyFilters();
        updateStats();
        
        // Show success message
        Toast.makeText(this, "Đã tải " + allCustomers.size() + " khách hàng thành công!", Toast.LENGTH_SHORT).show();
    }
    
    private void updateStats() {
        int total = allCustomers.size();
        int active = 0;
        int newCustomers = 0;
        
        for (Customer customer : allCustomers) {
            if ("ACTIVE".equals(customer.getStatus())) {
                active++;
            }
            // Assume customers created in last 30 days are new
            if (customer.getCreatedAt() != null && customer.getCreatedAt().contains("2024")) {
                newCustomers++;
            }
        }
        
        tvTotalCustomers.setText(String.valueOf(total));
        tvActiveCustomers.setText(String.valueOf(active));
        tvNewCustomers.setText(String.valueOf(newCustomers));
        
        // Animate stats
        animateStats();
    }
    
    private void animateStats() {
        ObjectAnimator totalAnim = ObjectAnimator.ofFloat(tvTotalCustomers, "scaleX", 1f, 1.2f, 1f);
        ObjectAnimator activeAnim = ObjectAnimator.ofFloat(tvActiveCustomers, "scaleX", 1f, 1.2f, 1f);
        ObjectAnimator newAnim = ObjectAnimator.ofFloat(tvNewCustomers, "scaleX", 1f, 1.2f, 1f);
        
        AnimatorSet statsSet = new AnimatorSet();
        statsSet.playTogether(totalAnim, activeAnim, newAnim);
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
    public void onCustomerClick(Customer customer) {
        // Navigate to CustomerDetailActivity
        Toast.makeText(this, "Xem chi tiết khách hàng: " + customer.getName(), Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void onEditCustomer(Customer customer) {
        // Navigate to EditCustomerActivity
        Toast.makeText(this, "Chỉnh sửa khách hàng: " + customer.getName(), Toast.LENGTH_SHORT).show();
    }
}
