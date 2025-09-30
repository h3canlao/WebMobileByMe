# 🚀 Hướng dẫn Setup Project trên Android Studio

## Bước 1: Mở Project
1. Mở **Android Studio**
2. Chọn **"Open an existing Android Studio project"**
3. Navigate đến thư mục `D:\PRM202\PRM\APP`
4. Chọn thư mục `APP` và click **"OK"**

## Bước 2: Sync Project
1. Android Studio sẽ tự động detect project
2. Nếu có popup **"Gradle Sync"**, click **"Sync Now"**
3. Hoặc vào menu: **File > Sync Project with Gradle Files**

## Bước 3: Cấu hình Build
1. **File > Project Structure**
2. Đảm bảo:
   - **Compile SDK Version**: 36 (hoặc cao nhất có sẵn)
   - **Target SDK Version**: 36
   - **Min SDK Version**: 24

## Bước 4: Chạy App
1. Kết nối Android device hoặc tạo AVD
2. Click nút **Run** (▶️) hoặc **Shift + F10**
3. Chọn device/emulator
4. App sẽ build và chạy

## 🔧 Nếu gặp lỗi:

### Lỗi Gradle Sync:
```
Build > Clean Project
File > Invalidate Caches and Restart
```

### Lỗi Lombok:
1. **File > Settings > Build > Compiler > Annotation Processors**
2. Tick **"Enable annotation processing"**
3. **Apply > OK**

### Lỗi Dependencies:
```
File > Project Structure > Dependencies
Kiểm tra các thư viện:
- androidx.recyclerview:recyclerview:1.3.2
- androidx.cardview:cardview:1.0.0
```

## 📱 Test App Flow:
1. **Login Screen** → Nhập username/password bất kỳ
2. **Dashboard** → Xem thống kê theo role
3. **Vehicle List** → Click "Xem xe" để xem danh sách
4. **Mock Data** → App sẽ hiển thị 4 xe mẫu

## 🎯 Features đã hoàn thành:
- ✅ Login với role-based authentication
- ✅ Dashboard với stats cards đẹp
- ✅ Vehicle list với search & filter
- ✅ Material Design UI
- ✅ Mock data cho demo
- ✅ Navigation giữa các màn hình

## 📝 Ghi chú:
- App sử dụng mock data khi không có backend
- Tất cả API calls sẽ fallback về mock data
- UI responsive và hiện đại
- Code structure sạch và dễ mở rộng

---
**Chúc bạn code vui vẻ! 🎉**
