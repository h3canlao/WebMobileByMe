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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.model.TestDrive;
import com.example.myapplication.utils.MockDataGenerator;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class AddTestDriveActivity extends AppCompatActivity {

    private ImageView btnBack;
    private EditText etCustomerName, etCustomerPhone, etCustomerEmail, etVehicleName, etScheduledDate, etScheduledTime, etNotes;
    private Button btnSaveTestDrive, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_test_drive);

        initViews();
        setupClickListeners();
        startEntranceAnimations();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        etCustomerName = findViewById(R.id.etCustomerName);
        etCustomerPhone = findViewById(R.id.etCustomerPhone);
        etCustomerEmail = findViewById(R.id.etCustomerEmail);
        etVehicleName = findViewById(R.id.etVehicleName);
        etScheduledDate = findViewById(R.id.etScheduledDate);
        etScheduledTime = findViewById(R.id.etScheduledTime);
        etNotes = findViewById(R.id.etNotes);
        btnSaveTestDrive = findViewById(R.id.btnSaveTestDrive);
        btnCancel = findViewById(R.id.btnCancel);
    }

    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> {
            animateButtonClick(btnBack);
            finish();
        });

        btnSaveTestDrive.setOnClickListener(v -> {
            animateButtonClick(btnSaveTestDrive);
            saveTestDrive();
        });

        btnCancel.setOnClickListener(v -> {
            animateButtonClick(btnCancel);
            finish();
        });
    }

    private void saveTestDrive() {
        String customerName = etCustomerName.getText().toString().trim();
        String customerPhone = etCustomerPhone.getText().toString().trim();
        String customerEmail = etCustomerEmail.getText().toString().trim();
        String vehicleName = etVehicleName.getText().toString().trim();
        String scheduledDate = etScheduledDate.getText().toString().trim();
        String scheduledTime = etScheduledTime.getText().toString().trim();
        String notes = etNotes.getText().toString().trim();

        if (customerName.isEmpty() || customerPhone.isEmpty() || vehicleName.isEmpty() || scheduledDate.isEmpty() || scheduledTime.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        TestDrive newTestDrive = new TestDrive();
        newTestDrive.setId(UUID.randomUUID().toString());
        newTestDrive.setCustomerName(customerName);
        newTestDrive.setVehicleName(vehicleName);
        newTestDrive.setScheduledDate(getTomorrowDate()); // Simplified for demo
        newTestDrive.setScheduledTime(scheduledTime);
        newTestDrive.setStatus("SCHEDULED");
        newTestDrive.setNotes(notes);

        MockDataGenerator.addTestDrive(newTestDrive);
        Toast.makeText(this, "Đã tạo lịch chạy thử thành công!", Toast.LENGTH_SHORT).show();
        finish();
    }

    private Date getTomorrowDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
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
