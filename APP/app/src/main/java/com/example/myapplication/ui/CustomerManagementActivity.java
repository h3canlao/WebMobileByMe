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
import java.util.stream.Collectors;

public class CustomerManagementActivity extends AppCompatActivity implements CustomerAdapter.OnCustomerClickListener {

    private RecyclerView rvCustomers;
    private LinearLayout layoutEmpty;
    private EditText etSearch;
    private ImageView btnBack, btnAddCustomer;
    private Button btnFilter;
    private TextView tvTotalCustomers, tvActiveCustomers, tvNewCustomers;

    private CustomerAdapter customerAdapter;
    private List<Customer> allCustomers = new ArrayList<>();
    private List<Customer> filteredCustomers = new ArrayList<>();

    private String currentSearchQuery = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_management);

        initViews();
        setupRecyclerView();
        setupClickListeners();
        setupSearchFilter();
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

            // btnFilter removed from new layout
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi setup click listeners: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void setupSearchFilter() {
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
    }

    private void applyFilters() {
        currentSearchQuery = etSearch != null ? etSearch.getText().toString().toLowerCase() : "";
        filteredCustomers.clear();

        List<Customer> tempFiltered = allCustomers.stream()
                .filter(customer -> {
                    boolean matchesSearch = customer.getName().toLowerCase().contains(currentSearchQuery) ||
                            customer.getEmail().toLowerCase().contains(currentSearchQuery) ||
                            customer.getPhone().toLowerCase().contains(currentSearchQuery);
                    return matchesSearch;
                })
                .collect(Collectors.toList());

        filteredCustomers.addAll(tempFiltered);
        if (customerAdapter != null) {
            customerAdapter.updateCustomers(filteredCustomers);
        }
        updateEmptyState();
    }

    private void updateEmptyState() {
        if (layoutEmpty != null && rvCustomers != null) {
            if (filteredCustomers.isEmpty()) {
                rvCustomers.setVisibility(View.GONE);
                layoutEmpty.setVisibility(View.VISIBLE);
            } else {
                rvCustomers.setVisibility(View.VISIBLE);
                layoutEmpty.setVisibility(View.GONE);
            }
        }
    }

    private void loadCustomers() {
        allCustomers.clear();
        allCustomers.addAll(MockDataGenerator.getMockCustomers());
        applyFilters();
        Toast.makeText(this, "Đã tải danh sách khách hàng thành công!", Toast.LENGTH_SHORT).show();
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

        if (tvTotalCustomers != null) tvTotalCustomers.setText(String.valueOf(total));
        if (tvActiveCustomers != null) tvActiveCustomers.setText(String.valueOf(active));
        if (tvNewCustomers != null) tvNewCustomers.setText(String.valueOf(newCustomers));

        animateStats();
    }

    private void animateStats() {
        // Animation for stats cards (if any)
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
        // Intent intent = new Intent(this, CustomerDetailActivity.class);
        // intent.putExtra("customerId", customer.getId());
        // startActivity(intent);
        Toast.makeText(this, "Xem chi tiết khách hàng: " + customer.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEditCustomer(Customer customer) {
        // Navigate to EditCustomerActivity
        // Intent intent = new Intent(this, EditCustomerActivity.class);
        // intent.putExtra("customerId", customer.getId());
        // startActivity(intent);
        Toast.makeText(this, "Chỉnh sửa khách hàng: " + customer.getName(), Toast.LENGTH_SHORT).show();
    }

    // onDelete method removed as it's not in the interface
}
