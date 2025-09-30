# 🔧 Đã sửa lỗi CardView!

## ❌ **Lỗi đã gặp:**
```
error: attribute android:cardCornerRadius not found.
error: attribute android:cardElevation not found.
```

## ✅ **Đã sửa:**

### 1. **Thêm namespace `app` vào layout files:**
```xml
<!-- Trước -->
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android">

<!-- Sau -->
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">
```

### 2. **Thay đổi attributes từ `android:` sang `app:`:**
```xml
<!-- Trước -->
<androidx.cardview.widget.CardView
    android:cardCornerRadius="12dp"
    android:cardElevation="4dp">

<!-- Sau -->
<androidx.cardview.widget.CardView
    app:cardCornerRadius="12dp"
    app:cardElevation="4dp">
```

## 📁 **Files đã được sửa:**
- ✅ `app/src/main/res/layout/activity_dashboard.xml`
- ✅ `app/src/main/res/layout/item_vehicle.xml`

## 🚀 **Bây giờ có thể build thành công!**

### **Cách test:**
1. **Clean Project**: `Build > Clean Project`
2. **Rebuild**: `Build > Rebuild Project`
3. **Run**: Click ▶️ button

### **Lý do lỗi:**
- CardView attributes như `cardCornerRadius` và `cardElevation` thuộc về thư viện CardView
- Cần sử dụng namespace `app:` thay vì `android:` cho custom attributes
- Namespace `app` được định nghĩa trong `xmlns:app="http://schemas.android.com/apk/res-auto"`

---
**Project giờ đã sẵn sàng chạy! 🎉**


