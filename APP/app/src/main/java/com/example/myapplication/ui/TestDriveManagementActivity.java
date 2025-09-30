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
import com.example.myapplication.adapter.TestDriveAdapter;
import com.example.myapplication.model.TestDrive;
import com.example.myapplication.utils.MockDataGenerator;

import java.util.ArrayList;
import java.util.List;

public class TestDriveManagementActivity extends AppCompatActivity implements TestDriveAdapter.OnTestDriveClickListener {

    private RecyclerView rvTestDrives;
    private LinearLayout layoutEmpty;
    private EditText etSearch;
    private ImageView btnBack, btnAddTestDrive;
    private TextView tvTotalTestDrives, tvScheduledTestDrives, tvCompletedTestDrives;
    
    private TestDriveAdapter testDriveAdapter;
    private List<TestDrive> allTestDrives = new ArrayList<>();
    private List<TestDrive> filteredTestDrives = new ArrayList<>();
    
    private String currentFilter = "ALL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_drive_management);

        initViews();
        setupRecyclerView();
        setupClickListeners();
        loadTestDrives();
        updateStats();
    }
    
    private void initViews() {
        try {
            rvTestDrives = findViewById(R.id.rvTestDrives);
            layoutEmpty = findViewById(R.id.layoutEmpty);
            etSearch = findViewById(R.id.etSearch);
            btnBack = findViewById(R.id.btnBack);
            btnAddTestDrive = findViewById(R.id.btnAddTestDrive);
            tvTotalTestDrives = findViewById(R.id.tvTotalCount);
            tvScheduledTestDrives = findViewById(R.id.tvScheduledCount);
            tvCompletedTestDrives = findViewById(R.id.tvCompletedCount);
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi khởi tạo UI: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
    
    private void setupRecyclerView() {
        try {
            testDriveAdapter = new TestDriveAdapter(filteredTestDrives, this);
            rvTestDrives.setLayoutManager(new LinearLayoutManager(this));
            rvTestDrives.setAdapter(testDriveAdapter);
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
            
            if (btnAddTestDrive != null) {
                btnAddTestDrive.setOnClickListener(v -> {
                    animateButtonClick(btnAddTestDrive);
                    // Navigate to AddTestDriveActivity
                    Intent intent = new Intent(this, AddTestDriveActivity.class);
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
        filteredTestDrives.clear();
        
        String searchQuery = etSearch.getText().toString().toLowerCase().trim();
        
        for (TestDrive testDrive : allTestDrives) {
            // Filter by search query only
            boolean searchMatch = searchQuery.isEmpty() || 
                testDrive.getCustomerName().toLowerCase().contains(searchQuery) ||
                testDrive.getVehicleName().toLowerCase().contains(searchQuery);
            
            if (searchMatch) {
                filteredTestDrives.add(testDrive);
            }
        }
        
        testDriveAdapter.updateTestDrives(filteredTestDrives);
        updateEmptyState();
    }
    
    private void updateEmptyState() {
        if (filteredTestDrives.isEmpty()) {
            rvTestDrives.setVisibility(View.GONE);
            layoutEmpty.setVisibility(View.VISIBLE);
        } else {
            rvTestDrives.setVisibility(View.VISIBLE);
            layoutEmpty.setVisibility(View.GONE);
        }
    }
    
    private void loadTestDrives() {
        // Use mock data directly - no network calls
        allTestDrives.clear();
        allTestDrives.addAll(MockDataGenerator.getMockTestDrives());
        
        // Debug log
        System.out.println("TestDriveManagementActivity: Loaded " + allTestDrives.size() + " test drives");
        
        applyFilters();
        updateStats();
        
        // Show success message
        Toast.makeText(this, "Đã tải " + allTestDrives.size() + " lịch chạy thử thành công!", Toast.LENGTH_SHORT).show();
    }
    
    private void updateStats() {
        int total = allTestDrives.size();
        int scheduled = 0;
        int completed = 0;
        
        for (TestDrive testDrive : allTestDrives) {
            if ("SCHEDULED".equals(testDrive.getStatus())) {
                scheduled++;
            } else if ("COMPLETED".equals(testDrive.getStatus())) {
                completed++;
            }
        }
        
        tvTotalTestDrives.setText(String.valueOf(total));
        tvScheduledTestDrives.setText(String.valueOf(scheduled));
        tvCompletedTestDrives.setText(String.valueOf(completed));
        
        // Animate stats
        animateStats();
    }
    
    private void animateStats() {
        ObjectAnimator totalAnim = ObjectAnimator.ofFloat(tvTotalTestDrives, "scaleX", 1f, 1.2f, 1f);
        ObjectAnimator scheduledAnim = ObjectAnimator.ofFloat(tvScheduledTestDrives, "scaleX", 1f, 1.2f, 1f);
        ObjectAnimator completedAnim = ObjectAnimator.ofFloat(tvCompletedTestDrives, "scaleX", 1f, 1.2f, 1f);
        
        AnimatorSet statsSet = new AnimatorSet();
        statsSet.playTogether(totalAnim, scheduledAnim, completedAnim);
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
    public void onViewDetails(TestDrive testDrive) {
        // Navigate to TestDriveDetailActivity
        Toast.makeText(this, "Xem chi tiết lịch chạy thử: " + testDrive.getCustomerName(), Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void onEdit(TestDrive testDrive) {
        // Navigate to EditTestDriveActivity
        Toast.makeText(this, "Chỉnh sửa lịch chạy thử: " + testDrive.getCustomerName(), Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void onCancel(TestDrive testDrive) {
        testDrive.setStatus("CANCELLED");
        MockDataGenerator.updateTestDrive(testDrive);
        loadTestDrives();
        updateStats();
        Toast.makeText(this, "Đã hủy lịch chạy thử", Toast.LENGTH_SHORT).show();
    }
}
