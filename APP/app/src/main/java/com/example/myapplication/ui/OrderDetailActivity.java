package com.example.myapplication.ui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.model.Order;
import com.example.myapplication.model.Vehicle;
import com.example.myapplication.repository.OrderRepository;
import com.example.myapplication.utils.MockDataGenerator;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrderDetailActivity extends AppCompatActivity {

    private ImageView ivVehicleImage, btnBack, btnMore;
    private TextView tvOrderNumber, tvOrderStatus, tvOrderDate, tvVehicleName, tvVehicleSpecs, 
                     tvSelectedColor, tvVehiclePrice, tvCustomerName, tvCustomerPhone, 
                     tvCustomerEmail, tvCustomerAddress, tvPaymentMethod, tvDeliveryDate, 
                     tvNotes, tvVehiclePriceDetail, tvDeliveryFee, tvTotalAmount;
    private Button btnCancel, btnApprove;
    
    private Order currentOrder;
    private Vehicle orderVehicle;
    private OrderRepository orderRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        // Get order ID from intent
        String orderId = getIntent().getStringExtra("orderId");
        if (orderId == null) orderId = "1"; // Default to first order
        
        // Load order data
        loadOrderData(orderId);
        
        // Initialize repository
        orderRepository = new OrderRepository();
        
        // Initialize views
        initViews();
        
        // Setup click listeners
        setupClickListeners();
        
        // Start entrance animations
        startEntranceAnimations();
    }
    
    private void loadOrderData(String orderId) {
        List<Order> orders = MockDataGenerator.getMockOrders();
        for (Order order : orders) {
            if (orderId.equals(order.getId())) {
                currentOrder = order;
                break;
            }
        }
        
        if (currentOrder == null) {
            currentOrder = orders.get(0); // Fallback to first order
        }
        
        // Load vehicle data
        List<Vehicle> vehicles = MockDataGenerator.getMockVehicles();
        for (Vehicle vehicle : vehicles) {
            if (currentOrder.getVehicleId().equals(vehicle.getId())) {
                orderVehicle = vehicle;
                break;
            }
        }
        
        if (orderVehicle == null) {
            orderVehicle = vehicles.get(0); // Fallback to first vehicle
        }
    }
    
    private void initViews() {
        ivVehicleImage = findViewById(R.id.ivVehicleImage);
        btnBack = findViewById(R.id.btnBack);
        btnMore = findViewById(R.id.btnMore);
        tvOrderNumber = findViewById(R.id.tvOrderNumber);
        tvOrderStatus = findViewById(R.id.tvOrderStatus);
        tvOrderDate = findViewById(R.id.tvOrderDate);
        tvVehicleName = findViewById(R.id.tvVehicleName);
        tvVehicleSpecs = findViewById(R.id.tvVehicleSpecs);
        tvSelectedColor = findViewById(R.id.tvSelectedColor);
        tvVehiclePrice = findViewById(R.id.tvVehiclePrice);
        tvCustomerName = findViewById(R.id.tvCustomerName);
        tvCustomerPhone = findViewById(R.id.tvCustomerPhone);
        tvCustomerEmail = findViewById(R.id.tvCustomerEmail);
        tvCustomerAddress = findViewById(R.id.tvCustomerAddress);
        tvPaymentMethod = findViewById(R.id.tvPaymentMethod);
        tvDeliveryDate = findViewById(R.id.tvDeliveryDate);
        tvNotes = findViewById(R.id.tvNotes);
        tvVehiclePriceDetail = findViewById(R.id.tvVehiclePriceDetail);
        tvDeliveryFee = findViewById(R.id.tvDeliveryFee);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        btnCancel = findViewById(R.id.btnCancel);
        btnApprove = findViewById(R.id.btnApprove);
        
        // Populate data
        populateOrderData();
    }
    
    private void populateOrderData() {
        if (currentOrder == null || orderVehicle == null) return;
        
        // Order info
        tvOrderNumber.setText(currentOrder.getOrderNumber());
        tvOrderStatus.setText(getStatusText(currentOrder.getStatus()));
        tvOrderStatus.setBackgroundResource(getStatusBackground(currentOrder.getStatus()));
        
        // Format dates
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        if (currentOrder.getOrderDate() != null) {
            tvOrderDate.setText("Ngày đặt: " + sdf.format(currentOrder.getOrderDate()));
        }
        if (currentOrder.getDeliveryDate() != null) {
            tvDeliveryDate.setText(sdf.format(currentOrder.getDeliveryDate()));
        }
        
        // Vehicle info
        tvVehicleName.setText(orderVehicle.getName());
        tvVehicleSpecs.setText(orderVehicle.getBatteryCapacity() + " kWh • " + 
                              orderVehicle.getRange() + " km • " + 
                              orderVehicle.getYear());
        tvSelectedColor.setText("Màu: " + (currentOrder.getSelectedColor() != null ? currentOrder.getSelectedColor() : "Default"));
        
        // Format price
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String priceText = formatter.format(currentOrder.getTotalAmount());
        tvVehiclePrice.setText(priceText);
        tvVehiclePriceDetail.setText(priceText);
        tvTotalAmount.setText(priceText);
        
        // Customer info (mock data)
        tvCustomerName.setText("Nguyễn Văn A");
        tvCustomerPhone.setText("0123456789");
        tvCustomerEmail.setText("customer@email.com");
        tvCustomerAddress.setText("123 Đường ABC, Quận 1, TP.HCM");
        
        // Order details
        tvPaymentMethod.setText("FULL_PAYMENT".equals(currentOrder.getPaymentMethod()) ? 
                               "Thanh toán toàn bộ" : "Trả góp");
        tvNotes.setText(currentOrder.getNotes() != null ? currentOrder.getNotes() : "Không có ghi chú");
        
        // Show/hide buttons based on status
        updateButtonVisibility();
    }
    
    private String getStatusText(String status) {
        switch (status) {
            case "PENDING": return "Chờ duyệt";
            case "APPROVED": return "Đã duyệt";
            case "COMPLETED": return "Hoàn thành";
            case "CANCELLED": return "Đã hủy";
            case "IN_PROGRESS": return "Đang xử lý";
            default: return status;
        }
    }
    
    private int getStatusBackground(String status) {
        switch (status) {
            case "PENDING": return R.drawable.bg_status_pending;
            case "APPROVED": return R.drawable.bg_status_approved;
            case "COMPLETED": return R.drawable.bg_status_completed;
            case "CANCELLED": return R.drawable.bg_status_cancelled;
            case "IN_PROGRESS": return R.drawable.bg_status_pending;
            default: return R.drawable.bg_status_pending;
        }
    }
    
    private void updateButtonVisibility() {
        if ("PENDING".equals(currentOrder.getStatus())) {
            btnCancel.setVisibility(View.VISIBLE);
            btnApprove.setVisibility(View.VISIBLE);
            btnCancel.setText("Hủy đơn");
            btnApprove.setText("Duyệt đơn");
        } else if ("APPROVED".equals(currentOrder.getStatus())) {
            btnCancel.setVisibility(View.GONE);
            btnApprove.setVisibility(View.VISIBLE);
            btnApprove.setText("Hoàn thành");
        } else {
            btnCancel.setVisibility(View.GONE);
            btnApprove.setVisibility(View.GONE);
        }
    }
    
    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> {
            animateButtonClick(btnBack);
            finish();
        });
        
        btnMore.setOnClickListener(v -> {
            animateButtonClick(btnMore);
            // TODO: Show more options menu
            Toast.makeText(this, "Chức năng thêm", Toast.LENGTH_SHORT).show();
        });
        
        btnCancel.setOnClickListener(v -> {
            animateButtonClick(btnCancel);
            cancelOrder();
        });
        
        btnApprove.setOnClickListener(v -> {
            animateButtonClick(btnApprove);
            if ("PENDING".equals(currentOrder.getStatus())) {
                approveOrder();
            } else if ("APPROVED".equals(currentOrder.getStatus())) {
                completeOrder();
            }
        });
    }
    
    private void cancelOrder() {
        // Show confirmation dialog
        Toast.makeText(this, "Đơn hàng đã được hủy", Toast.LENGTH_SHORT).show();
        // TODO: Implement actual cancellation logic
        finish();
    }
    
    private void approveOrder() {
        // Show loading
        btnApprove.setEnabled(false);
        btnApprove.setText("Đang xử lý...");
        
        // Approve order
        orderRepository.approveOrder(currentOrder.getId(), new OrderRepository.OrderCallback() {
            @Override
            public void onSuccess(Order order) {
                runOnUiThread(() -> {
                    btnApprove.setEnabled(true);
                    btnApprove.setText("Hoàn thành");
                    
                    // Update UI
                    currentOrder = order;
                    tvOrderStatus.setText(getStatusText(order.getStatus()));
                    tvOrderStatus.setBackgroundResource(getStatusBackground(order.getStatus()));
                    updateButtonVisibility();
                    
                    Toast.makeText(OrderDetailActivity.this, 
                        "Đơn hàng đã được duyệt thành công!", 
                        Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onError(String error) {
                runOnUiThread(() -> {
                    btnApprove.setEnabled(true);
                    btnApprove.setText("Duyệt đơn");
                    Toast.makeText(OrderDetailActivity.this, 
                        "Lỗi duyệt đơn hàng: " + error, 
                        Toast.LENGTH_SHORT).show();
                });
            }
        });
    }
    
    private void completeOrder() {
        Toast.makeText(this, "Đơn hàng đã hoàn thành", Toast.LENGTH_SHORT).show();
        // TODO: Implement actual completion logic
        finish();
    }
    
    private void animateButtonClick(View button) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(button, "scaleX", 1f, 0.95f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(button, "scaleY", 1f, 0.95f, 1f);
        
        AnimatorSet buttonSet = new AnimatorSet();
        buttonSet.playTogether(scaleX, scaleY);
        buttonSet.setDuration(200);
        buttonSet.start();
    }
    
    private void startEntranceAnimations() {
        // Animate main content sliding up
        View mainContent = findViewById(android.R.id.content);
        if (mainContent != null) {
            mainContent.setTranslationY(50f);
            mainContent.setAlpha(0f);
            
            ObjectAnimator slideUp = ObjectAnimator.ofFloat(mainContent, "translationY", 50f, 0f);
            ObjectAnimator fadeIn = ObjectAnimator.ofFloat(mainContent, "alpha", 0f, 1f);
            
            AnimatorSet contentSet = new AnimatorSet();
            contentSet.playTogether(slideUp, fadeIn);
            contentSet.setDuration(800);
            contentSet.setInterpolator(new DecelerateInterpolator());
            contentSet.start();
        }
        
        // Animate buttons with delay
        btnApprove.setTranslationY(30f);
        btnApprove.setAlpha(0f);
        
        ObjectAnimator buttonSlide = ObjectAnimator.ofFloat(btnApprove, "translationY", 30f, 0f);
        ObjectAnimator buttonFade = ObjectAnimator.ofFloat(btnApprove, "alpha", 0f, 1f);
        
        AnimatorSet buttonSet = new AnimatorSet();
        buttonSet.playTogether(buttonSlide, buttonFade);
        buttonSet.setDuration(500);
        buttonSet.setInterpolator(new DecelerateInterpolator());
        buttonSet.setStartDelay(400);
        buttonSet.start();
    }
}
