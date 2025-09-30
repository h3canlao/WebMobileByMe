package com.example.myapplication.ui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.model.response.LoginResponse;
import com.example.myapplication.repository.AuthRepository;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private AuthRepository authRepository;
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "MyAppPrefs";
    private static final String TOKEN_KEY = "token";
    private static final String USERNAME_KEY = "username";
    private static final String ROLE_KEY = "role";
    
    // UI Elements
    private View loginCard;
    private Button btnLogin, btnDemoAccount;
    private TextInputEditText etUsername, etPassword;
    private Chip chipAdmin, chipDealerManager, chipDealerStaff;
    
    private String selectedRole = "ADMIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize repository and preferences
        authRepository = new AuthRepository();
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        
        // Check if user is already logged in
        // Temporarily disabled for testing
        // if (isLoggedIn()) {
        //     navigateToDashboard();
        //     return;
        // }
        
        // Initialize views
        initViews();
        
        // Start entrance animations
        startEntranceAnimations();
        
        // Setup click listeners
        setupClickListeners();
    }
    
    private void initViews() {
        // Main elements
        loginCard = findViewById(R.id.loginCard);
        
        // Form elements
        btnLogin = findViewById(R.id.btnLogin);
        btnDemoAccount = findViewById(R.id.btnDemoAccount);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        
        // Demo chips
        chipAdmin = findViewById(R.id.chipAdmin);
        chipDealerManager = findViewById(R.id.chipDealerManager);
        chipDealerStaff = findViewById(R.id.chipDealerStaff);
        
        // Set initial states for animation
        setInitialAnimationStates();
    }
    
    private void setInitialAnimationStates() {
        // Hide login card initially
        if (loginCard != null) {
            loginCard.setAlpha(0f);
            loginCard.setTranslationY(100f);
            loginCard.setScaleX(0.8f);
            loginCard.setScaleY(0.8f);
        }
    }
    
    private void startEntranceAnimations() {
        // Animate login card entrance
        if (loginCard != null) {
            ObjectAnimator fadeIn = ObjectAnimator.ofFloat(loginCard, "alpha", 0f, 1f);
            ObjectAnimator slideUp = ObjectAnimator.ofFloat(loginCard, "translationY", 100f, 0f);
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(loginCard, "scaleX", 0.8f, 1f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(loginCard, "scaleY", 0.8f, 1f);
            
            AnimatorSet cardSet = new AnimatorSet();
            cardSet.playTogether(fadeIn, slideUp, scaleX, scaleY);
            cardSet.setDuration(800);
            cardSet.setInterpolator(new AccelerateDecelerateInterpolator());
            cardSet.start();
        }
    }
    
    private void setupClickListeners() {
        // Login button
        btnLogin.setOnClickListener(v -> {
            animateButtonClick(btnLogin);
            performLogin();
        });
        
        // Demo account button
        btnDemoAccount.setOnClickListener(v -> {
            animateButtonClick(btnDemoAccount);
            fillDemoAccount();
        });
        
        // Role selection chips
        chipAdmin.setOnClickListener(v -> selectRole("ADMIN", chipAdmin));
        chipDealerManager.setOnClickListener(v -> selectRole("DEALER_MANAGER", chipDealerManager));
        chipDealerStaff.setOnClickListener(v -> selectRole("DEALER_STAFF", chipDealerStaff));
    }
    
    private void selectRole(String role, Chip selectedChip) {
        // Reset all chips
        chipAdmin.setChecked(false);
        chipDealerManager.setChecked(false);
        chipDealerStaff.setChecked(false);
        
        // Select the clicked chip
        selectedChip.setChecked(true);
        selectedRole = role;
        
        Toast.makeText(this, "Đã chọn vai trò: " + getRoleDisplayName(role), Toast.LENGTH_SHORT).show();
    }
    
    private String getRoleDisplayName(String role) {
        switch (role) {
            case "ADMIN": return "Quản trị viên";
            case "DEALER_MANAGER": return "Quản lý đại lý";
            case "DEALER_STAFF": return "Nhân viên đại lý";
            default: return role;
        }
    }
    
    private void fillDemoAccount() {
        etUsername.setText("admin");
        etPassword.setText("admin123");
        selectedRole = "ADMIN";
        
        // Select admin chip
        chipAdmin.setChecked(true);
        chipDealerManager.setChecked(false);
        chipDealerStaff.setChecked(false);
        
        Toast.makeText(this, "Đã điền tài khoản demo", Toast.LENGTH_SHORT).show();
    }
    
    private void performLogin() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Show loading
        btnLogin.setText("Đang đăng nhập...");
        btnLogin.setEnabled(false);
        
        // Simulate login process (using mock data)
        new Thread(() -> {
            try {
                Thread.sleep(1000); // Simulate network delay
                
                runOnUiThread(() -> {
                    // Mock successful login
                    saveLoginData(username, selectedRole, "mock_token_123");
                    Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                    navigateToDashboard();
                });
                
            } catch (InterruptedException e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    btnLogin.setText("Đăng nhập");
                    btnLogin.setEnabled(true);
                    Toast.makeText(this, "Lỗi đăng nhập", Toast.LENGTH_SHORT).show();
                });
            }
        }).start();
    }
    
    private void saveLoginData(String username, String role, String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN_KEY, token);
        editor.putString(USERNAME_KEY, username);
        editor.putString(ROLE_KEY, role);
        editor.apply();
    }
    
    private boolean isLoggedIn() {
        String token = sharedPreferences.getString(TOKEN_KEY, null);
        return token != null && !token.isEmpty();
    }
    
    private void navigateToDashboard() {
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
    
    private void animateButtonClick(View view) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 0.95f, 1.0f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 0.95f, 1.0f);
        scaleX.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleY.setInterpolator(new AccelerateDecelerateInterpolator());
        AnimatorSet buttonSet = new AnimatorSet();
        buttonSet.playTogether(scaleX, scaleY);
        buttonSet.setDuration(150);
        buttonSet.start();
    }
    
    // Static methods for other activities to access user data
    public static String getUsername(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(USERNAME_KEY, "User");
    }
    
    public static String getRole(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(ROLE_KEY, "ADMIN");
    }
    
    public static String getToken(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(TOKEN_KEY, "");
    }
    
    public static void logout(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
    }
}