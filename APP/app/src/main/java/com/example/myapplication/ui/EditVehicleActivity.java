package com.example.myapplication.ui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.model.Vehicle;
import com.example.myapplication.utils.MockDataGenerator;

import java.util.List;

public class EditVehicleActivity extends AppCompatActivity {

    private ImageView btnBack;
    private EditText etVehicleName, etBrand, etModel, etYear, etPrice, etBatteryCapacity, 
                     etRange, etStockQuantity, etColor, etConfiguration;
    private Button btnUpdateVehicle, btnDeleteVehicle, btnCancel;
    private TextView tvTitle;

    private Vehicle currentVehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_vehicle);

        String vehicleId = getIntent().getStringExtra("vehicleId");
        loadVehicleData(vehicleId);

        initViews();
        populateData();
        setupClickListeners();
        startEntranceAnimations();
    }

    private void loadVehicleData(String vehicleId) {
        if (vehicleId != null) {
            List<Vehicle> vehicles = MockDataGenerator.getMockVehicles();
            currentVehicle = vehicles.stream()
                    .filter(v -> v.getId().equals(vehicleId))
                    .findFirst()
                    .orElse(null);
        }
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        tvTitle = findViewById(R.id.tvTitle);
        etVehicleName = findViewById(R.id.etVehicleName);
        etBrand = findViewById(R.id.etBrand);
        etModel = findViewById(R.id.etModel);
        etYear = findViewById(R.id.etYear);
        etPrice = findViewById(R.id.etPrice);
        etBatteryCapacity = findViewById(R.id.etBatteryCapacity);
        etRange = findViewById(R.id.etRange);
        etStockQuantity = findViewById(R.id.etStockQuantity);
        etColor = findViewById(R.id.etColor);
        etConfiguration = findViewById(R.id.etConfiguration);
        btnUpdateVehicle = findViewById(R.id.btnUpdateVehicle);
        btnDeleteVehicle = findViewById(R.id.btnDeleteVehicle);
        btnCancel = findViewById(R.id.btnCancel);
    }

    private void populateData() {
        if (currentVehicle != null) {
            etVehicleName.setText(currentVehicle.getName());
            etBrand.setText(currentVehicle.getBrand());
            etModel.setText(currentVehicle.getModel());
            etYear.setText(String.valueOf(currentVehicle.getYear()));
            etPrice.setText(String.valueOf(currentVehicle.getPrice()));
            etBatteryCapacity.setText(String.valueOf(currentVehicle.getBatteryCapacity()));
            etRange.setText(String.valueOf(currentVehicle.getRange()));
            etStockQuantity.setText(String.valueOf(currentVehicle.getStockQuantity()));
            etColor.setText(currentVehicle.getColor());
            etConfiguration.setText(currentVehicle.getConfiguration());
        } else {
            Toast.makeText(this, "Không tìm thấy thông tin xe", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> {
            animateButtonClick(btnBack);
            finish();
        });

        btnCancel.setOnClickListener(v -> {
            animateButtonClick(btnCancel);
            finish();
        });

        btnUpdateVehicle.setOnClickListener(v -> {
            animateButtonClick(btnUpdateVehicle);
            if (validateInputs()) {
                updateVehicle();
            }
        });

        btnDeleteVehicle.setOnClickListener(v -> {
            animateButtonClick(btnDeleteVehicle);
            deleteVehicle();
        });
    }

    private boolean validateInputs() {
        boolean isValid = true;

        if (etVehicleName.getText().toString().trim().isEmpty()) {
            etVehicleName.setError("Tên xe không được để trống");
            isValid = false;
        }

        if (etBrand.getText().toString().trim().isEmpty()) {
            etBrand.setError("Hãng xe không được để trống");
            isValid = false;
        }

        if (etPrice.getText().toString().trim().isEmpty()) {
            etPrice.setError("Giá xe không được để trống");
            isValid = false;
        }

        if (etStockQuantity.getText().toString().trim().isEmpty()) {
            etStockQuantity.setError("Số lượng tồn kho không được để trống");
            isValid = false;
        }

        return isValid;
    }

    private void updateVehicle() {
        try {
            if (currentVehicle != null) {
                currentVehicle.setName(etVehicleName.getText().toString().trim());
                currentVehicle.setBrand(etBrand.getText().toString().trim());
                currentVehicle.setModel(etModel.getText().toString().trim());
                currentVehicle.setYear(Integer.parseInt(etYear.getText().toString().trim()));
                currentVehicle.setPrice(Double.parseDouble(etPrice.getText().toString().trim()));
                currentVehicle.setBatteryCapacity((int) Double.parseDouble(etBatteryCapacity.getText().toString().trim()));
                currentVehicle.setRange(Integer.parseInt(etRange.getText().toString().trim()));
                currentVehicle.setStockQuantity(Integer.parseInt(etStockQuantity.getText().toString().trim()));
                currentVehicle.setColor(etColor.getText().toString().trim());
                currentVehicle.setConfiguration(etConfiguration.getText().toString().trim());

                // Update in mock data
                MockDataGenerator.updateVehicle(currentVehicle);

                Toast.makeText(this, "Cập nhật xe thành công!", Toast.LENGTH_SHORT).show();
                finish();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Vui lòng nhập đúng định dạng số", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi cập nhật xe: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteVehicle() {
        if (currentVehicle != null) {
            MockDataGenerator.deleteVehicle(currentVehicle.getId());
            Toast.makeText(this, "Xóa xe thành công!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void animateButtonClick(View button) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(button, "scaleX", 1f, 0.9f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(button, "scaleY", 1f, 0.9f, 1f);

        AnimatorSet buttonSet = new AnimatorSet();
        buttonSet.playTogether(scaleX, scaleY);
        buttonSet.setDuration(200);
        buttonSet.setInterpolator(new DecelerateInterpolator());
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
    }
}
