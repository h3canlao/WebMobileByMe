package com.example.myapplication.ui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.model.Vehicle;
import com.example.myapplication.utils.MockDataGenerator;

import java.text.NumberFormat;
import java.util.Locale;

public class AddVehicleActivity extends AppCompatActivity {

    private ImageView btnBack;
    private EditText etVehicleName, etBrand, etModel, etYear, etPrice, etBatteryCapacity, 
                     etRange, etStockQuantity, etColor, etConfiguration;
    // private Spinner spinnerStatus; // Removed as not in layout
    private Button btnSaveVehicle, btnCancel;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);

        initViews();
        setupClickListeners();
        startEntranceAnimations();
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
        // spinnerStatus = findViewById(R.id.spinnerStatus); // Removed as not in layout
        btnSaveVehicle = findViewById(R.id.btnSaveVehicle);
        btnCancel = findViewById(R.id.btnCancel);
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

        btnSaveVehicle.setOnClickListener(v -> {
            animateButtonClick(btnSaveVehicle);
            if (validateInputs()) {
                saveVehicle();
            }
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

    private void saveVehicle() {
        try {
            Vehicle newVehicle = new Vehicle();
            newVehicle.setId("vehicle_" + System.currentTimeMillis());
            newVehicle.setName(etVehicleName.getText().toString().trim());
            newVehicle.setBrand(etBrand.getText().toString().trim());
            newVehicle.setModel(etModel.getText().toString().trim());
            newVehicle.setYear(Integer.parseInt(etYear.getText().toString().trim()));
            newVehicle.setPrice(Double.parseDouble(etPrice.getText().toString().trim()));
            newVehicle.setBatteryCapacity((int) Double.parseDouble(etBatteryCapacity.getText().toString().trim()));
            newVehicle.setRange(Integer.parseInt(etRange.getText().toString().trim()));
            newVehicle.setStockQuantity(Integer.parseInt(etStockQuantity.getText().toString().trim()));
            newVehicle.setColor(etColor.getText().toString().trim());
            newVehicle.setConfiguration(etConfiguration.getText().toString().trim());
            newVehicle.setStatus("AVAILABLE");

            // Add to mock data
            MockDataGenerator.addVehicle(newVehicle);

            Toast.makeText(this, "Thêm xe thành công!", Toast.LENGTH_SHORT).show();
            finish();

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Vui lòng nhập đúng định dạng số", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi thêm xe: " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
