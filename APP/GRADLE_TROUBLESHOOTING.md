# 🔧 Gradle Troubleshooting Guide

## ❌ **Lỗi đang gặp:**
```
Task 'wrapper' not found in project ':app'.
```

## 🎯 **Giải pháp:**

### **1. Không cần chạy Gradle commands!**
Project Android này được thiết kế để chạy trực tiếp trên **Android Studio**, không cần terminal commands.

### **2. Cách chạy đúng trên Android Studio:**

#### **Bước 1: Mở Project**
1. Mở **Android Studio**
2. **File > Open** → Chọn thư mục `D:\PRM202\PRM\APP`
3. Đợi Android Studio sync project

#### **Bước 2: Build & Run**
1. **Build > Clean Project** (nếu cần)
2. **Build > Rebuild Project**
3. Click nút **Run** (▶️) hoặc **Shift + F10**

### **3. Nếu vẫn muốn dùng Terminal (không khuyến khích):**

#### **Windows (PowerShell):**
```powershell
# Đảm bảo đang ở thư mục gốc của project
cd D:\PRM202\PRM\APP

# Sử dụng gradlew.bat (không phải gradlew)
.\gradlew.bat assembleDebug

# Hoặc
.\gradlew.bat build
```

#### **Linux/Mac:**
```bash
# Đảm bảo đang ở thư mục gốc của project
cd /path/to/APP

# Sử dụng gradlew
./gradlew assembleDebug

# Hoặc
./gradlew build
```

### **4. Các lệnh Gradle hữu ích (nếu cần):**

```bash
# Xem tất cả tasks có sẵn
.\gradlew.bat tasks

# Build debug APK
.\gradlew.bat assembleDebug

# Build release APK
.\gradlew.bat assembleRelease

# Clean project
.\gradlew.bat clean

# Run tests
.\gradlew.bat test
```

## 🚀 **Khuyến nghị:**

### **✅ Nên làm:**
- Sử dụng **Android Studio** để build và run
- Click nút **Run** (▶️) thay vì terminal commands
- Sử dụng **Build > Clean Project** nếu gặp lỗi

### **❌ Không nên:**
- Chạy Gradle commands phức tạp
- Sử dụng `gradlew` thay vì `gradlew.bat` trên Windows
- Chạy commands từ thư mục con

## 🔍 **Kiểm tra Project Structure:**

Đảm bảo cấu trúc thư mục như sau:
```
D:\PRM202\PRM\APP\
├── app/
├── gradle/
│   └── wrapper/
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradlew
├── gradlew.bat
├── build.gradle
├── settings.gradle
└── gradle.properties
```

## 📱 **Test App:**

1. **Mở Android Studio**
2. **Open Project** → Chọn `D:\PRM202\PRM\APP`
3. **Sync Project** (tự động)
4. **Run** (▶️ button)
5. **Chọn device/emulator**
6. **App sẽ build và chạy**

---

## 🎉 **Kết luận:**

**Không cần chạy Gradle commands!** Chỉ cần mở Android Studio và click **Run** (▶️). Project đã được cấu hình sẵn để chạy trực tiếp.

**Chúc bạn code vui vẻ! 🚀**


