package com.example.myapplication.ui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.model.Customer;
import com.example.myapplication.utils.MockDataGenerator;

import java.util.UUID;

public class AddCustomerActivity extends AppCompatActivity {

    private ImageView btnBack;
    private EditText etName, etEmail, etPhone, etAddress;
    private Button btnSaveCustomer, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);

        initViews();
        setupClickListeners();
        startEntranceAnimations();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        etName = findViewById(R.id.etCustomerName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAddress);
        btnSaveCustomer = findViewById(R.id.btnSaveCustomer);
        btnCancel = findViewById(R.id.btnCancel);
    }

    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> {
            animateButtonClick(btnBack);
            finish();
        });

        btnSaveCustomer.setOnClickListener(v -> {
            animateButtonClick(btnSaveCustomer);
            saveCustomer();
        });

        btnCancel.setOnClickListener(v -> {
            animateButtonClick(btnCancel);
            finish();
        });
    }

    private void saveCustomer() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String address = etAddress.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        Customer newCustomer = new Customer();
        newCustomer.setId(UUID.randomUUID().toString());
        newCustomer.setName(name);
        newCustomer.setEmail(email);
        newCustomer.setPhone(phone);
        newCustomer.setAddress(address);
        newCustomer.setStatus("ACTIVE");
        newCustomer.setCreatedAt(java.time.LocalDate.now().toString());

        MockDataGenerator.addCustomer(newCustomer);
        Toast.makeText(this, "Đã thêm khách hàng thành công!", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void animateButtonClick(View button) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(button, "scaleX", 1f, 0.9f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(button, "scaleY", 1f, 0.9f, 1f);

        AnimatorSet buttonSet = new AnimatorSet();
        buttonSet.playTogether(scaleX, scaleY);
        buttonSet.setDuration(200);
        buttonSet.start();
    }

    private void startEntranceAnimations() {
        View contentView = findViewById(android.R.id.content);
        contentView.setAlpha(0f);
        contentView.setTranslationY(50f);

        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(contentView, "alpha", 0f, 1f);
        ObjectAnimator slideUp = ObjectAnimator.ofFloat(contentView, "translationY", 50f, 0f);

        AnimatorSet entranceSet = new AnimatorSet();
        entranceSet.playTogether(fadeIn, slideUp);
        entranceSet.setDuration(500);
        entranceSet.setInterpolator(new DecelerateInterpolator());
        entranceSet.start();
    }
}
