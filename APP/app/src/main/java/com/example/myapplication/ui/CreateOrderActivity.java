package com.example.myapplication.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.R;
import com.example.myapplication.model.Vehicle;
import com.example.myapplication.model.request.CreateOrderRequest;
import com.example.myapplication.repository.OrderRepository;
import com.example.myapplication.utils.MockDataGenerator;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CreateOrderActivity extends AppCompatActivity {

    private ImageView ivVehicleImage, btnBack;
    private TextView tvVehicleName, tvVehicleSpecs, tvSelectedColor, tvPrice, tvVehiclePrice, tvTotalAmount;
    private EditText etCustomerName, etPhoneNumber, etEmail, etAddress, etDeliveryDate;
    private RadioButton rbFullPayment, rbInstallment;
    private Button btnCancel, btnConfirmOrder;
    
    private Vehicle selectedVehicle;
    private String selectedColor = "Xanh";
    private OrderRepository orderRepository;
    private Date selectedDeliveryDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);

        // Get vehicle data from intent
        String vehicleId = getIntent().getStringExtra("vehicleId");
        selectedColor = getIntent().getStringExtra("selectedColor");
        if (selectedColor == null) selectedColor = "Xanh";
        
        // Load vehicle data
        loadVehicleData(vehicleId);
        
        // Initialize repository
        orderRepository = new OrderRepository();
        
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
            if (vehicleId != null && vehicleId.equals(vehicle.getId())) {
                selectedVehicle = vehicle;
                break;
            }
        }
        
        if (selectedVehicle == null) {
            selectedVehicle = vehicles.get(0); // Fallback to first vehicle
        }
    }
    
    private void initViews() {
        ivVehicleImage = findViewById(R.id.ivVehicleImage);
        btnBack = findViewById(R.id.btnBack);
        tvVehicleName = findViewById(R.id.tvVehicleName);
        tvVehicleSpecs = findViewById(R.id.tvVehicleSpecs);
        tvSelectedColor = findViewById(R.id.tvSelectedColor);
        tvPrice = findViewById(R.id.tvPrice);
        tvVehiclePrice = findViewById(R.id.tvVehiclePrice);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        etCustomerName = findViewById(R.id.etCustomerName);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etEmail = findViewById(R.id.etEmail);
        etAddress = findViewById(R.id.etAddress);
        etDeliveryDate = findViewById(R.id.etDeliveryDate);
        rbFullPayment = findViewById(R.id.rbFullPayment);
        rbInstallment = findViewById(R.id.rbInstallment);
        btnCancel = findViewById(R.id.btnCancel);
        btnConfirmOrder = findViewById(R.id.btnConfirmOrder);
        
        // Populate vehicle data
        populateVehicleData();
        
        // Set vehicle background
        setVehicleBackground();
        
        // Set default delivery date (7 days from now)
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        selectedDeliveryDate = calendar.getTime();
        updateDeliveryDateDisplay();
    }
    
    private void populateVehicleData() {
        if (selectedVehicle == null) return;
        
        tvVehicleName.setText(selectedVehicle.getName());
        tvVehicleSpecs.setText(selectedVehicle.getBatteryCapacity() + " kWh • " + 
                              selectedVehicle.getRange() + " km • " + 
                              selectedVehicle.getYear());
        tvSelectedColor.setText("Màu: " + selectedColor);
        
        // Format price
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String priceText = formatter.format(selectedVehicle.getPrice());
        tvPrice.setText(priceText);
        tvVehiclePrice.setText(priceText);
        tvTotalAmount.setText(priceText);
    }
    
    private void setVehicleBackground() {
        if (selectedVehicle == null) return;
        
        String imageUrl = getVehicleImageUrl(selectedVehicle);
        
        if (imageUrl != null && !imageUrl.isEmpty()) {
            // Load real image with Glide
            Glide.with(this)
                .load(imageUrl)
                .apply(new RequestOptions()
                    .transform(new RoundedCorners(16))
                    .placeholder(R.drawable.placeholder_car)
                    .error(R.drawable.placeholder_car))
                .into(ivVehicleImage);
        } else {
            // Fallback to gradient backgrounds
            String brand = selectedVehicle.getBrand().toLowerCase();
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
    
    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> {
            animateButtonClick(btnBack);
            finish();
        });
        
        etDeliveryDate.setOnClickListener(v -> showDatePicker());
        
        btnCancel.setOnClickListener(v -> {
            animateButtonClick(btnCancel);
            finish();
        });
        
        btnConfirmOrder.setOnClickListener(v -> {
            animateButtonClick(btnConfirmOrder);
            createOrder();
        });
    }
    
    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        if (selectedDeliveryDate != null) {
            calendar.setTime(selectedDeliveryDate);
        } else {
            calendar.add(Calendar.DAY_OF_MONTH, 7);
        }
        
        DatePickerDialog datePickerDialog = new DatePickerDialog(
            this,
            (view, year, month, dayOfMonth) -> {
                Calendar selectedCalendar = Calendar.getInstance();
                selectedCalendar.set(year, month, dayOfMonth);
                selectedDeliveryDate = selectedCalendar.getTime();
                updateDeliveryDateDisplay();
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        );
        
        // Set minimum date to tomorrow
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        
        datePickerDialog.show();
    }
    
    private void updateDeliveryDateDisplay() {
        if (selectedDeliveryDate != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            etDeliveryDate.setText(sdf.format(selectedDeliveryDate));
        }
    }
    
    private void createOrder() {
        // Validate input
        if (!validateInput()) {
            return;
        }
        
        // Show loading
        btnConfirmOrder.setEnabled(false);
        btnConfirmOrder.setText("Đang xử lý...");
        
        // Create order request
        CreateOrderRequest request = new CreateOrderRequest();
        request.setCustomerId("customer_" + System.currentTimeMillis());
        request.setVehicleId(selectedVehicle.getId());
        request.setPaymentMethod(rbFullPayment.isChecked() ? "FULL_PAYMENT" : "INSTALLMENT");
        request.setSelectedColor(selectedColor);
        request.setConfiguration("Standard");
        request.setDiscount("0%");
        request.setNotes("Đơn hàng được tạo từ ứng dụng mobile");
        
        // Create order
        orderRepository.createOrder(request, new OrderRepository.OrderCallback() {
            @Override
            public void onSuccess(com.example.myapplication.model.Order order) {
                runOnUiThread(() -> {
                    btnConfirmOrder.setEnabled(true);
                    btnConfirmOrder.setText("Xác nhận đặt xe");
                    
                    // Show success message
                    Toast.makeText(CreateOrderActivity.this, 
                        "Đặt xe thành công! Mã đơn hàng: " + order.getOrderNumber(), 
                        Toast.LENGTH_LONG).show();
                    
                    // Navigate back to vehicle list
                    Intent intent = new Intent(CreateOrderActivity.this, VehicleListActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                });
            }

            @Override
            public void onError(String error) {
                runOnUiThread(() -> {
                    btnConfirmOrder.setEnabled(true);
                    btnConfirmOrder.setText("Xác nhận đặt xe");
                    Toast.makeText(CreateOrderActivity.this, 
                        "Lỗi tạo đơn hàng: " + error, 
                        Toast.LENGTH_SHORT).show();
                });
            }
        });
    }
    
    private boolean validateInput() {
        if (TextUtils.isEmpty(etCustomerName.getText().toString().trim())) {
            etCustomerName.setError("Vui lòng nhập họ tên");
            etCustomerName.requestFocus();
            return false;
        }
        
        if (TextUtils.isEmpty(etPhoneNumber.getText().toString().trim())) {
            etPhoneNumber.setError("Vui lòng nhập số điện thoại");
            etPhoneNumber.requestFocus();
            return false;
        }
        
        if (TextUtils.isEmpty(etEmail.getText().toString().trim())) {
            etEmail.setError("Vui lòng nhập email");
            etEmail.requestFocus();
            return false;
        }
        
        if (TextUtils.isEmpty(etAddress.getText().toString().trim())) {
            etAddress.setError("Vui lòng nhập địa chỉ");
            etAddress.requestFocus();
            return false;
        }
        
        if (selectedDeliveryDate == null) {
            Toast.makeText(this, "Vui lòng chọn ngày giao hàng", Toast.LENGTH_SHORT).show();
            return false;
        }
        
        return true;
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
        btnConfirmOrder.setTranslationY(30f);
        btnConfirmOrder.setAlpha(0f);
        
        ObjectAnimator buttonSlide = ObjectAnimator.ofFloat(btnConfirmOrder, "translationY", 30f, 0f);
        ObjectAnimator buttonFade = ObjectAnimator.ofFloat(btnConfirmOrder, "alpha", 0f, 1f);
        
        AnimatorSet buttonSet = new AnimatorSet();
        buttonSet.playTogether(buttonSlide, buttonFade);
        buttonSet.setDuration(500);
        buttonSet.setInterpolator(new DecelerateInterpolator());
        buttonSet.setStartDelay(400);
        buttonSet.start();
    }
}