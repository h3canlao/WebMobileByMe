package com.example.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import com.example.myapplication.repository.OrderRepository;
import com.example.myapplication.utils.MockDataGenerator;

import java.util.ArrayList;
import java.util.List;

public class OrderListActivity extends AppCompatActivity implements OrderAdapter.OnOrderClickListener {

    private RecyclerView rvOrders;
    private LinearLayout layoutEmpty;
    private ImageView btnBack, btnFilter;
    private TextView chipAll, chipPending, chipApproved, chipCompleted;
    
    private OrderAdapter orderAdapter;
    private OrderRepository orderRepository;
    private List<Order> allOrders = new ArrayList<>();
    private List<Order> filteredOrders = new ArrayList<>();
    
    private String currentFilter = "ALL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        orderRepository = new OrderRepository();
        
        initViews();
        setupRecyclerView();
        setupClickListeners();
        loadOrders();
    }

    private void initViews() {
        rvOrders = findViewById(R.id.rvOrders);
        layoutEmpty = findViewById(R.id.layoutEmpty);
        btnBack = findViewById(R.id.btnBack);
        btnFilter = findViewById(R.id.btnFilter);
        chipAll = findViewById(R.id.chipAll);
        chipPending = findViewById(R.id.chipPending);
        chipApproved = findViewById(R.id.chipApproved);
        chipCompleted = findViewById(R.id.chipCompleted);
    }
    
    private void setupRecyclerView() {
        orderAdapter = new OrderAdapter(filteredOrders, this);
        rvOrders.setLayoutManager(new LinearLayoutManager(this));
        rvOrders.setAdapter(orderAdapter);
    }
    
    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> finish());
        
        btnFilter.setOnClickListener(v -> {
            Toast.makeText(this, "Chức năng lọc nâng cao", Toast.LENGTH_SHORT).show();
        });
        
        chipAll.setOnClickListener(v -> setFilter("ALL"));
        chipPending.setOnClickListener(v -> setFilter("PENDING"));
        chipApproved.setOnClickListener(v -> setFilter("APPROVED"));
        chipCompleted.setOnClickListener(v -> setFilter("COMPLETED"));
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
        chipApproved.setTextColor(getResources().getColor(R.color.text_secondary));chipCompleted.setBackgroundResource(R.drawable.bg_chip_unselected);
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
        
        if ("ALL".equals(currentFilter)) {
            filteredOrders.addAll(allOrders);
        } else {
            for (Order order : allOrders) {
                if (currentFilter.equals(order.getStatus())) {
                    filteredOrders.add(order);
                }
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
        // Use mock data directly
        allOrders.clear();
        allOrders.addAll(MockDataGenerator.getMockOrders());
        
        // Debug log
        System.out.println("OrderListActivity: Loaded " + allOrders.size() + " orders");
        
        applyFilters();
        
        Toast.makeText(this, "Đã tải " + allOrders.size() + " đơn hàng", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onOrderClick(Order order) {
        // Navigate to OrderDetailActivity
        Intent intent = new Intent(this, OrderDetailActivity.class);
        intent.putExtra("orderId", order.getId());
        startActivity(intent);
    }
}
