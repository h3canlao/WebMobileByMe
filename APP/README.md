# Hệ thống Quản lý Đại lý Xe Điện (Electric Vehicle Dealer Management System)

## Tổng quan
Ứng dụng Android quản lý đại lý xe điện với hệ thống phân quyền 4 cấp: Admin, EVN Staff, Dealer Manager, và Dealer Staff.

## Tính năng chính

### 🔐 Hệ thống Authentication
- Đăng nhập với username/password
- Phân quyền theo 4 roles
- Lưu trữ token và thông tin user với SharedPreferences

### 📊 Dashboard theo Role
- **Admin/EVN Staff**: Tổng quan toàn hệ thống
- **Dealer Manager**: Quản lý đại lý riêng
- **Dealer Staff**: Công việc cá nhân

### 🚗 Quản lý Xe Điện
- Danh sách xe với filter và search
- Thông tin chi tiết: pin, tầm hoạt động, giá cả
- Trạng thái xe (Có sẵn, Đã bán, Bảo trì)
- UI hiện đại với Material Design

### 📝 Quản lý Đơn hàng
- Tạo đơn hàng mới
- Theo dõi trạng thái đơn hàng
- Duyệt/từ chối đơn hàng (Manager)

### 👥 Quản lý Khách hàng
- Thông tin khách hàng
- Lịch sử mua hàng
- Lịch hẹn chạy thử

## Công nghệ sử dụng

### Backend Integration
- **Retrofit 2.9.0**: REST API client
- **Gson 2.8.7**: JSON parsing
- **Base URL**: `http://10.0.2.2:8080/api/`

### UI/UX
- **Material Design Components**
- **RecyclerView**: Danh sách xe, đơn hàng
- **CardView**: Layout cards hiện đại
- **Custom drawables**: Gradient backgrounds, status badges

### Architecture
- **Repository Pattern**: Tách biệt business logic
- **Model-View pattern**: Tổ chức code rõ ràng
- **Lombok**: Giảm boilerplate code

## Cài đặt và Chạy

### Yêu cầu hệ thống
- Android Studio Arctic Fox trở lên
- Android SDK API 24+ (Android 7.0)
- Java 11
- Gradle 7.0+

### Bước 1: Clone và Import
```bash
# Project đã có sẵn, mở bằng Android Studio
# File > Open > Chọn thư mục APP
```

### Bước 2: Sync Dependencies
```bash
# Android Studio sẽ tự động sync, hoặc:
# File > Sync Project with Gradle Files
```

### Bước 3: Cấu hình Backend
```java
// Trong RetrofitClient.java, cập nhật BASE_URL nếu cần:
private static final String BASE_URL = "http://YOUR_SERVER_IP:8080/api/";
```

### Bước 4: Build và Run
```bash
# Build project
./gradlew assembleDebug

# Hoặc Run từ Android Studio (Shift + F10)
```

## Cấu trúc Project

```
app/src/main/java/com/example/myapplication/
├── model/                 # Data models
│   ├── Vehicle.java       # Model xe điện
│   ├── Order.java         # Model đơn hàng  
│   ├── Customer.java      # Model khách hàng
│   ├── User.java          # Model người dùng
│   ├── request/           # Request DTOs
│   └── response/          # Response DTOs
├── network/               # API interfaces
│   ├── AuthApi.java       # Authentication API
│   ├── VehicleApi.java    # Vehicle API
│   ├── OrderApi.java      # Order API
│   └── RetrofitClient.java # HTTP client config
├── repository/            # Business logic layer
│   ├── AuthRepository.java
│   ├── VehicleRepository.java
│   └── OrderRepository.java
├── ui/                    # Activities
│   ├── LoginActivity.java
│   ├── DashboardActivity.java
│   ├── VehicleListActivity.java
│   ├── CreateOrderActivity.java
│   └── OrderListActivity.java
└── adapter/               # RecyclerView adapters
    └── VehicleAdapter.java
```

## API Endpoints

### Authentication
- `POST /v1/auth/login` - Đăng nhập

### Vehicles
- `GET /v1/vehicles` - Danh sách xe
- `GET /v1/vehicles/{id}` - Chi tiết xe
- `GET /v1/vehicles/available` - Xe có sẵn

### Orders
- `GET /v1/orders` - Danh sách đơn hàng
- `POST /v1/orders` - Tạo đơn hàng
- `PUT /v1/orders/{id}/approve` - Duyệt đơn hàng

### Dashboard
- `GET /v1/dashboard/stats` - Thống kê dashboard

## Mock Data
Khi API không khả dụng, app sẽ tự động load mock data để demo:
- 4 xe điện mẫu (VinFast VF8, VF9, Tesla Model 3, Model Y)
- Thống kê dashboard giả lập
- Trạng thái xe đa dạng

## Roles và Permissions

### Admin
- Quản lý toàn bộ hệ thống
- Xem tất cả đại lý
- Quản lý users

### EVN Staff  
- Quản lý xe điện
- Duyệt yêu cầu từ đại lý
- Xem báo cáo tổng

### Dealer Manager
- Quản lý đại lý riêng
- Duyệt đơn hàng của đại lý
- Quản lý nhân viên

### Dealer Staff
- Tạo đơn hàng
- Quản lý khách hàng
- Lịch hẹn chạy thử

## UI Features

### Thiết kế hiện đại
- **Color Scheme**: Xanh lá/xanh dương (Electric theme)
- **Gradient Cards**: Backgrounds đẹp mắt
- **Status Badges**: Màu sắc phân biệt trạng thái
- **Responsive**: Hoạt động tốt trên các kích thước màn hình

### Animations & Interactions
- Smooth transitions giữa màn hình
- Hover effects trên buttons
- Loading states
- Empty states với illustrations

### User Experience
- Search và filter thông minh
- Chip filters dễ sử dụng
- Toast notifications rõ ràng
- Navigation trực quan

## Phát triển tiếp

### Các tính năng cần bổ sung
- [ ] Màn hình chi tiết xe
- [ ] Form tạo đơn hàng đầy đủ
- [ ] Quản lý lịch chạy thử
- [ ] Push notifications
- [ ] Offline support
- [ ] Image loading (Glide/Picasso)
- [ ] Dark mode
- [ ] Biometric authentication

### Cải tiến kỹ thuật
- [ ] MVVM Architecture với ViewModel
- [ ] Room Database cho offline
- [ ] Dagger/Hilt Dependency Injection
- [ ] Unit tests
- [ ] UI tests với Espresso
- [ ] Continuous Integration

## Troubleshooting

### Lỗi thường gặp

1. **Network Error**: 
   - Kiểm tra BASE_URL trong RetrofitClient
   - Đảm bảo backend đang chạy
   - Kiểm tra INTERNET permission

2. **Build Error**:
   - Clean project: `Build > Clean Project`
   - Invalidate caches: `File > Invalidate Caches and Restart`

3. **Lombok không hoạt động**:
   - Enable annotation processing trong Android Studio
   - Sync project lại

## Liên hệ & Đóng góp

Project này được phát triển cho môn PRM202. Mọi góp ý và cải tiến đều được hoan nghênh!

---

**Phiên bản**: 1.0  
**Ngày cập nhật**: 2024  
**Tác giả**: PRM202 Team
