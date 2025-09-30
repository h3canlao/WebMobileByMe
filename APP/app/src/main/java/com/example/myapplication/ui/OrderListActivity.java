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
import com.example.myapplication.adapter.OrderAdapter;
import com.example.myapplication.model.Order;
import com.example.myapplication.utils.MockDataGenerator;

import java.util.ArrayList;
import java.util.List;

public class OrderListActivity extends AppCompatActivity implements OrderAdapter.OnOrderClickListener {

    private RecyclerView rvOrders;
    private LinearLayout layoutEmpty;
    private EditText etSearch;
    private ImageView btnBack, btnAddOrder;
    private Button btnFilter;
    private TextView chipAll, chipPending, chipApproved, chipCompleted;
    private TextView tvTotalOrders, tvPendingOrders, tvCompletedOrders;
    
    private OrderAdapter orderAdapter;
    private List<Order> allOrders = new ArrayList<>();
    private List<Order> filteredOrders = new ArrayList<>();
    
    private String currentFilter = "ALL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        initViews();
        setupRecyclerView();
        setupClickListeners();
        loadOrders();
        updateStats();
    }
    
    private void initViews() {
        try {
            rvOrders = findViewById(R.id.rvOrders);
            layoutEmpty = findViewById(R.id.layoutEmpty);
            etSearch = findViewById(R.id.etSearch);
            btnBack = findViewById(R.id.btnBack);
            btnAddOrder = findViewById(R.id.btnAddOrder);
            btnFilter = findViewById(R.id.btnFilter);
            chipAll = findViewById(R.id.chipAll);
            chipPending = findViewById(R.id.chipPending);
            chipApproved = findViewById(R.id.chipApproved);
            chipCompleted = findViewById(R.id.chipCompleted);
            tvTotalOrders = findViewById(R.id.tvTotalOrders);
            tvPendingOrders = findViewById(R.id.tvPendingOrders);
            tvCompletedOrders = findViewById(R.id.tvCompletedOrders);
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi khởi tạo UI: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
    
    private void setupRecyclerView() {
        try {
            orderAdapter = new OrderAdapter(filteredOrders, this);
            rvOrders.setLayoutManager(new LinearLayoutManager(this));
            rvOrders.setAdapter(orderAdapter);
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
            
            if (btnAddOrder != null) {
                btnAddOrder.setOnClickListener(v -> {
                    animateButtonClick(btnAddOrder);
                    // Navigate to CreateOrderActivity
                    Intent intent = new Intent(this, CreateOrderActivity.class);
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
            if (chipPending != null) chipPending.setOnClickListener(v -> setFilter("PENDING"));
            if (chipApproved != null) chipApproved.setOnClickListener(v -> setFilter("APPROVED"));
            if (chipCompleted != null) chipCompleted.setOnClickListener(v -> setFilter("COMPLETED"));
            
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
        chipPending.setBackgroundResource(R.drawable.bg_chip_unselected);
        chipPending.setTextColor(getResources().getColor(R.color.text_secondary));
        chipApproved.setBackgroundResource(R.drawable.bg_chip_unselected);
        chipApproved.setTextColor(getResources().getColor(R.color.text_secondary));
        chipCompleted.setBackgroundResource(R.drawable.bg_chip_unselected);
        chipCompleted.setTextColor(getResources().getColor(R.color.text_secondary));
        
        // Select current chip
        switch (currentFilter) {
            case "ALL":
                chipAll.setBackgroundResource(R.drawable.bg_chip_selected);
                chipAll.setTextColor(getResources().getColor(R.color.primary_green));
                break;
            case "PENDING":
                chipPending.setBackgroundResource(R.drawable.bg_chip_selected);
                chipPending.setTextColor(getResources().getColor(R.color.primary_green));
                break;
            case "APPROVED":
                chipApproved.setBackgroundResource(R.drawable.bg_chip_selected);
                chipApproved.setTextColor(getResources().getColor(R.color.primary_green));
                break;
            case "COMPLETED":
                chipCompleted.setBackgroundResource(R.drawable.bg_chip_selected);
                chipCompleted.setTextColor(getResources().getColor(R.color.primary_green));
                break;
        }
    }
    
    private void applyFilters() {
        filteredOrders.clear();
        
        String searchQuery = etSearch.getText().toString().toLowerCase().trim();
        
        for (Order order : allOrders) {
            // Filter by status
            boolean statusMatch = "ALL".equals(currentFilter) || currentFilter.equals(order.getStatus());
            
            // Filter by search query
            boolean searchMatch = searchQuery.isEmpty() || 
                order.getOrderNumber().toLowerCase().contains(searchQuery) ||
                order.getCustomerName().toLowerCase().contains(searchQuery) ||
                order.getVehicleName().toLowerCase().contains(searchQuery);
            
            if (statusMatch && searchMatch) {
                filteredOrders.add(order);
            }
        }
        
        orderAdapter.updateOrders(filteredOrders);
        updateEmptyState();
    }
    
    private void updateEmptyState() {
        if (filteredOrders.isEmpty()) {
            rvOrders.setVisibility(View.GONE);
            layoutEmpty.setVisibility(View.VISIBLE);
        } else {
            rvOrders.setVisibility(View.VISIBLE);
            layoutEmpty.setVisibility(View.GONE);
        }
    }
    
    private void loadOrders() {
        // Use mock data directly - no network calls
        allOrders.clear();
        allOrders.addAll(MockDataGenerator.getMockOrders());
        
        // Debug log
        System.out.println("OrderListActivity: Loaded " + allOrders.size() + " orders");
        
        applyFilters();
        updateStats();
        
        // Show success message
        Toast.makeText(this, "Đã tải " + allOrders.size() + " đơn hàng thành công!", Toast.LENGTH_SHORT).show();
    }
    
    private void updateStats() {
        int total = allOrders.size();
        int pending = 0;
        int completed = 0;
        
        for (Order order : allOrders) {
            if ("PENDING".equals(order.getStatus())) {
                pending++;
            } else if ("COMPLETED".equals(order.getStatus())) {
                completed++;
            }
        }
        
        tvTotalOrders.setText(String.valueOf(total));
        tvPendingOrders.setText(String.valueOf(pending));
        tvCompletedOrders.setText(String.valueOf(completed));
        
        // Animate stats
        animateStats();
    }
    
    private void animateStats() {
        ObjectAnimator totalAnim = ObjectAnimator.ofFloat(tvTotalOrders, "scaleX", 1f, 1.2f, 1f);
        ObjectAnimator pendingAnim = ObjectAnimator.ofFloat(tvPendingOrders, "scaleX", 1f, 1.2f, 1f);
        ObjectAnimator completedAnim = ObjectAnimator.ofFloat(tvCompletedOrders, "scaleX", 1f, 1.2f, 1f);
        
        AnimatorSet statsSet = new AnimatorSet();
        statsSet.playTogether(totalAnim, pendingAnim, completedAnim);
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
    public void onOrderClick(Order order) {
        // Navigate to OrderDetailActivity
        Intent intent = new Intent(this, OrderDetailActivity.class);
        intent.putExtra("orderId", order.getId());
        startActivity(intent);
    }
}
