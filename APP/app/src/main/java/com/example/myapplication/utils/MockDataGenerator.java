package com.example.myapplication.utils;

import com.example.myapplication.model.Vehicle;
import com.example.myapplication.model.Order;
import com.example.myapplication.model.Customer;
import com.example.myapplication.model.TestDrive;
import com.example.myapplication.model.User;
import com.example.myapplication.model.response.DashboardResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 * Utility class để tạo mock data cho demo
 */
public class MockDataGenerator {

    // Static lists to store data
    private static List<Vehicle> vehicles = new ArrayList<>();
    private static List<Order> orders = new ArrayList<>();
    private static List<Customer> customers = new ArrayList<>();
    private static List<TestDrive> testDrives = new ArrayList<>();
    private static List<User> users = new ArrayList<>();
    
    // Helper method to parse date strings
    private static Date parseDate(String dateString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(dateString);
        } catch (ParseException e) {
            return new Date(); // Return current date if parsing fails
        }
    }

    public static List<Vehicle> getMockVehicles() {
        // Always ensure we have data
        if (vehicles.isEmpty()) {
            initializeVehicles();
        }
        return vehicles;
    }
    
    private static void initializeVehicles() {
        // Clear existing data
        vehicles.clear();
        
        // Add vehicles with simple constructor
        vehicles.add(new Vehicle(
            "1", 
            "VinFast VF8", 
            "VinFast", 
            "VF8", 
            2024, 
            1200000000, 
            "Xanh", 
            "Standard", 
            82, 
            420, 
            "https://static-cms-prod.vinfast.com/statics/2024-06/vf7-1.webp", 
            "SUV điện cao cấp với công nghệ tiên tiến", 
            "AVAILABLE", 
            "dealer001", 
            5
        ));
        
        vehicles.add(new Vehicle(
            "2", 
            "VinFast VF9", 
            "VinFast", 
            "VF9", 
            2024, 
            1500000000, 
            "Trắng", 
            "Premium", 
            92, 
            450, 
            "https://static-cms-prod.vinfast.com/statics/2024-06/vf7-1.webp", 
            "SUV điện hạng sang với không gian rộng rãi", 
            "AVAILABLE", 
            "dealer001", 
            3
        ));
        
        vehicles.add(new Vehicle(
            "3", 
            "Tesla Model 3", 
            "Tesla", 
            "Model 3", 
            2024, 
            1100000000, 
            "Đen", 
            "Long Range", 
            75, 
            400, 
            "https://cdn1.smartprix.com/rx-iHovaGL7k-w1200-h1200/HovaGL7k.webp", 
            "Sedan điện thông minh với Autopilot", 
            "AVAILABLE", 
            "dealer001", 
            2
        ));
        
        vehicles.add(new Vehicle(
            "4", 
            "Tesla Model Y", 
            "Tesla", 
            "Model Y", 
            2024, 
            1300000000, 
            "Đỏ", 
            "Performance", 
            81, 
            430, 
            "https://cdn1.smartprix.com/rx-iHovaGL7k-w1200-h1200/HovaGL7k.webp", 
            "SUV điện hiệu suất cao", 
            "SOLD", 
            "dealer001", 
            0
        ));
        
        vehicles.add(new Vehicle(
            "5", 
            "VinFast VF6", 
            "VinFast", 
            "VF6", 
            2024, 
            800000000, 
            "Xám", 
            "Standard", 
            60, 
            350, 
            "https://static-cms-prod.vinfast.com/statics/2024-06/vf7-1.webp", 
            "Crossover điện thân thiện với môi trường", 
            "MAINTENANCE", 
            "dealer001", 
            1
        ));
        
        vehicles.add(new Vehicle(
            "6", 
            "Tesla Model S", 
            "Tesla", 
            "Model S", 
            2024, 
            2000000000L, 
            "Trắng", 
            "Plaid", 
            100, 
            600, 
            "https://cdn1.smartprix.com/rx-iHovaGL7k-w1200-h1200/HovaGL7k.webp", 
            "Sedan điện cao cấp với hiệu suất vượt trội", 
            "AVAILABLE", 
            "dealer001", 
            2
        ));
        
        vehicles.add(new Vehicle(
            "7", 
            "Tesla Model X", 
            "Tesla", 
            "Model X", 
            2024, 
            2500000000L, 
            "Đen", 
            "Plaid", 
            100, 
            550, 
            "https://cdn1.smartprix.com/rx-iHovaGL7k-w1200-h1200/HovaGL7k.webp", 
            "SUV điện với cửa cánh bướm độc đáo", 
            "AVAILABLE", 
            "dealer001", 
            1
        ));
        
        vehicles.add(new Vehicle(
            "8", 
            "VinFast VF7", 
            "VinFast", 
            "VF7", 
            2024, 
            900000000, 
            "Đỏ", 
            "Premium", 
            70, 
            380, 
            "https://static-cms-prod.vinfast.com/statics/2024-06/vf7-1.webp", 
            "SUV điện với thiết kế hiện đại", 
            "SOLD", 
            "dealer001", 
            0
        ));
        
        vehicles.add(new Vehicle(
            "9", 
            "BMW iX", 
            "BMW", 
            "iX", 
            2024, 
            1800000000L, 
            "Xanh dương", 
            "xDrive50", 
            105, 
            500, 
            "https://example.com/bmw-ix.jpg", 
            "SUV điện cao cấp từ BMW", 
            "AVAILABLE", 
            "dealer001", 
            3
        ));
        
        vehicles.add(new Vehicle(
            "10", 
            "Mercedes EQS", 
            "Mercedes", 
            "EQS", 
            2024, 
            2200000000L, 
            "Bạc", 
            "450+", 
            108, 
            580, 
            "https://example.com/mercedes-eqs.jpg", 
            "Sedan điện sang trọng từ Mercedes", 
            "AVAILABLE", 
            "dealer001", 
            2
        ));
        
        System.out.println("MockDataGenerator: Initialized " + vehicles.size() + " vehicles");
    }
    
    public static DashboardResponse getMockDashboardData(String role) {
        DashboardResponse dashboard = new DashboardResponse();
        
        switch (role) {
            case "ADMIN":
            case "EVN_STAFF":
                dashboard.setTotalOrders(156);
                dashboard.setPendingOrders(12);
                dashboard.setCompletedOrders(134);
                dashboard.setTotalRevenue(187500000000.0);
                dashboard.setTotalCustomers(89);
                dashboard.setTotalVehicles(45);
                dashboard.setTodayTestDrives(8);
                dashboard.setUpcomingTestDrives(15);
                break;
                
            case "DEALER_MANAGER":
                dashboard.setTotalOrders(45);
                dashboard.setPendingOrders(5);
                dashboard.setCompletedOrders(38);
                dashboard.setTotalRevenue(54000000000.0);
                dashboard.setTotalCustomers(23);
                dashboard.setTotalVehicles(12);
                dashboard.setTodayTestDrives(3);
                dashboard.setUpcomingTestDrives(7);
                break;
                
            case "DEALER_STAFF":
            default:
                dashboard.setTotalOrders(12);
                dashboard.setPendingOrders(3);
                dashboard.setCompletedOrders(8);
                dashboard.setTotalRevenue(14400000000.0);
                dashboard.setTotalCustomers(6);
                dashboard.setTotalVehicles(5);
                dashboard.setTodayTestDrives(2);
                dashboard.setUpcomingTestDrives(4);
                break;
        }
        
        return dashboard;
    }
    
    public static List<Order> getMockOrders() {
        if (orders.isEmpty()) {
            initializeOrders();
        }
        return orders;
    }
    
    private static void initializeOrders() {
        orders.clear();
        
        // Order 1: VinFast VF8
        Order order1 = new Order();
        order1.setId("1");
        order1.setOrderNumber("ORD001");
        order1.setCustomerId("customer001");
        order1.setCustomerName("Nguyễn Văn A");
        order1.setVehicleId("1");
        order1.setVehicleName("VinFast VF8");
        order1.setDealerId("dealer001");
        order1.setStaffId("staff001");
        order1.setOrderDate(new Date(System.currentTimeMillis() - 86400000)); // 1 day ago
        order1.setDeliveryDate(new Date(System.currentTimeMillis() + 86400000)); // 1 day from now
        order1.setQuantity(1);
        order1.setUnitPrice(1200000000);
        order1.setTotalAmount(1200000000);
        order1.setStatus("PENDING");
        order1.setPaymentStatus("PENDING");
        order1.setNotes("Đơn hàng VinFast VF8 màu xanh");
        order1.setPaymentMethod("FULL_PAYMENT");
        order1.setPaidAmount(0);
        order1.setSelectedColor("Xanh");
        order1.setConfiguration("Standard");
        order1.setDiscount("0%");
        order1.setCreatedAt(new Date().toString());
        orders.add(order1);
        
        // Order 2: Tesla Model 3
        Order order2 = new Order();
        order2.setId("2");
        order2.setOrderNumber("ORD002");
        order2.setCustomerId("customer002");
        order2.setCustomerName("Trần Thị B");
        order2.setVehicleId("3");
        order2.setVehicleName("Tesla Model 3");
        order2.setDealerId("dealer001");
        order2.setStaffId("staff001");
        order2.setOrderDate(new Date(System.currentTimeMillis() - 172800000)); // 2 days ago
        order2.setDeliveryDate(new Date(System.currentTimeMillis() + 172800000)); // 2 days from now
        order2.setQuantity(1);
        order2.setUnitPrice(1100000000);
        order2.setTotalAmount(1100000000);
        order2.setStatus("APPROVED");
        order2.setPaymentStatus("PARTIAL");
        order2.setNotes("Đơn hàng Tesla Model 3 màu đen");
        order2.setPaymentMethod("INSTALLMENT");
        order2.setPaidAmount(550000000);
        order2.setSelectedColor("Đen");
        order2.setConfiguration("Premium");
        order2.setDiscount("5%");
        order2.setCreatedAt(new Date().toString());
        orders.add(order2);
        
        // Order 3: VinFast VF9
        Order order3 = new Order();
        order3.setId("3");
        order3.setOrderNumber("ORD003");
        order3.setCustomerId("customer003");
        order3.setCustomerName("Lê Văn C");
        order3.setVehicleId("2");
        order3.setVehicleName("VinFast VF9");
        order3.setDealerId("dealer001");
        order3.setStaffId("staff002");
        order3.setOrderDate(new Date(System.currentTimeMillis() - 259200000)); // 3 days ago
        order3.setDeliveryDate(new Date(System.currentTimeMillis() + 259200000)); // 3 days from now
        order3.setQuantity(1);
        order3.setUnitPrice(1500000000);
        order3.setTotalAmount(1500000000);
        order3.setStatus("COMPLETED");
        order3.setPaymentStatus("PAID");
        order3.setNotes("Đơn hàng VinFast VF9 màu trắng");
        order3.setPaymentMethod("FULL_PAYMENT");
        order3.setPaidAmount(1500000000);
        order3.setSelectedColor("Trắng");
        order3.setConfiguration("Luxury");
        order3.setDiscount("10%");
        order3.setCreatedAt(new Date().toString());
        orders.add(order3);

        // Order 4: VinFast VF6
        Order order4 = new Order();
        order4.setId("4");
        order4.setOrderNumber("ORD004");
        order4.setCustomerId("customer004");
        order4.setCustomerName("Hoàng Văn D");
        order4.setVehicleId("5");
        order4.setVehicleName("VinFast VF6");
        order4.setDealerId("dealer001");
        order4.setStaffId("staff001");
        order4.setOrderDate(new Date(System.currentTimeMillis() - 345600000)); // 4 days ago
        order4.setDeliveryDate(new Date(System.currentTimeMillis() + 345600000)); // 4 days from now
        order4.setQuantity(1);
        order4.setUnitPrice(800000000);
        order4.setTotalAmount(800000000);
        order4.setStatus("PENDING");
        order4.setPaymentStatus("PENDING");
        order4.setNotes("Đơn hàng VinFast VF6 màu xám");
        order4.setPaymentMethod("FULL_PAYMENT");
        order4.setPaidAmount(0);
        order4.setSelectedColor("Xám");
        order4.setConfiguration("Standard");
        order4.setDiscount("0%");
        order4.setCreatedAt(new Date().toString());
        orders.add(order4);
        
        // Order 5: Tesla Model S
        Order order5 = new Order();
        order5.setId("5");
        order5.setOrderNumber("ORD005");
        order5.setCustomerId("customer_2");
        order5.setCustomerName("Trần Thị B");
        order5.setVehicleId("6");
        order5.setVehicleName("Tesla Model S");
        order5.setOrderDate(parseDate("2024-06-12"));
        order5.setDeliveryDate(parseDate("2024-06-28"));
        order5.setQuantity(1);
        order5.setUnitPrice(2000000000L);
        order5.setTotalAmount(2000000000L);
        order5.setStatus("IN_PROGRESS");
        order5.setPaymentStatus("PARTIAL");
        order5.setDealerId("dealer001");
        order5.setStaffId("staff002");
        order5.setNotes("Khách hàng VIP, ưu tiên giao hàng");
        order5.setPaidAmount(1000000000L);
        order5.setSelectedColor("Trắng");
        order5.setConfiguration("Plaid");
        order5.setDiscount("5%");
        order5.setCreatedAt(new Date().toString());
        orders.add(order5);
        
        // Order 6: BMW iX
        Order order6 = new Order();
        order6.setId("6");
        order6.setOrderNumber("ORD006");
        order6.setCustomerId("customer_3");
        order6.setCustomerName("Lê Văn C");
        order6.setVehicleId("9");
        order6.setVehicleName("BMW iX");
        order6.setOrderDate(parseDate("2024-06-08"));
        order6.setDeliveryDate(parseDate("2024-06-22"));
        order6.setQuantity(1);
        order6.setUnitPrice(1800000000L);
        order6.setTotalAmount(1800000000L);
        order6.setStatus("COMPLETED");
        order6.setPaymentStatus("PAID");
        order6.setDealerId("dealer001");
        order6.setStaffId("staff001");
        order6.setNotes("Đã giao xe thành công");
        order6.setPaidAmount(1800000000L);
        order6.setSelectedColor("Xanh dương");
        order6.setConfiguration("xDrive50");
        order6.setDiscount("0%");
        order6.setCreatedAt(new Date().toString());
        orders.add(order6);
        
        System.out.println("MockDataGenerator: Initialized " + orders.size() + " orders");
    }
    
    public static List<Customer> getMockCustomers() {
        // Always ensure we have data
        if (customers.isEmpty()) {
            initializeCustomers();
        }
        return customers;
    }
    
    private static void initializeCustomers() {
        customers.clear();
        
        Customer customer1 = new Customer();
        customer1.setId("customer_1");
        customer1.setName("Nguyễn Văn A");
        customer1.setEmail("nguyenvana@email.com");
        customer1.setPhone("0987654321");
        customer1.setAddress("123 Đường ABC, Quận 1, TP.HCM");
        customer1.setStatus("ACTIVE");
        customer1.setCreatedAt("2024-01-15");
        customers.add(customer1);
        
        Customer customer2 = new Customer();
        customer2.setId("customer_2");
        customer2.setName("Trần Thị B");
        customer2.setEmail("tranthib@email.com");
        customer2.setPhone("0987654322");
        customer2.setAddress("456 Đường XYZ, Quận 2, TP.HCM");
        customer2.setStatus("ACTIVE");
        customer2.setCreatedAt("2024-02-20");
        customers.add(customer2);
        
        Customer customer3 = new Customer();
        customer3.setId("customer_3");
        customer3.setName("Lê Văn C");
        customer3.setEmail("levanc@email.com");
        customer3.setPhone("0987654323");
        customer3.setAddress("789 Đường DEF, Quận 3, TP.HCM");
        customer3.setStatus("INACTIVE");
        customer3.setCreatedAt("2024-03-10");
        customers.add(customer3);
        
        Customer customer4 = new Customer();
        customer4.setId("customer_4");
        customer4.setName("Hoàng Văn D");
        customer4.setEmail("hoangvand@email.com");
        customer4.setPhone("0987654324");
        customer4.setAddress("321 Đường GHI, Quận 4, TP.HCM");
        customer4.setStatus("ACTIVE");
        customer4.setCreatedAt("2024-04-05");
        customers.add(customer4);
        
        Customer customer5 = new Customer();
        customer5.setId("customer_5");
        customer5.setName("Phạm Thị E");
        customer5.setEmail("phamthie@email.com");
        customer5.setPhone("0987654325");
        customer5.setAddress("654 Đường JKL, Quận 5, TP.HCM");
        customer5.setStatus("ACTIVE");
        customer5.setCreatedAt("2024-05-12");
        customers.add(customer5);
        
        Customer customer6 = new Customer();
        customer6.setId("customer_6");
        customer6.setName("Võ Thị F");
        customer6.setEmail("vothif@email.com");
        customer6.setPhone("0987654326");
        customer6.setAddress("987 Đường MNO, Quận 6, TP.HCM");
        customer6.setStatus("ACTIVE");
        customer6.setCreatedAt("2024-06-01");
        customers.add(customer6);
        
        System.out.println("MockDataGenerator: Initialized " + customers.size() + " customers");
    }
    
    public static List<TestDrive> getMockTestDrives() {
        // Always ensure we have data
        if (testDrives.isEmpty()) {
            initializeTestDrives();
        }
        return testDrives;
    }
    
    private static void initializeTestDrives() {
        testDrives.clear();
        
        TestDrive testDrive1 = new TestDrive();
        testDrive1.setId("testdrive_1");
        testDrive1.setCustomerId("customer_1");
        testDrive1.setCustomerName("Nguyễn Văn A");
        testDrive1.setVehicleId("1");
        testDrive1.setVehicleName("VinFast VF8");
        testDrive1.setScheduledDate(new Date(System.currentTimeMillis() + 86400000)); // Tomorrow
        testDrive1.setStatus("SCHEDULED");
        testDrive1.setNotes("Khách hàng muốn test drive vào buổi chiều");
        testDrives.add(testDrive1);
        
        TestDrive testDrive2 = new TestDrive();
        testDrive2.setId("testdrive_2");
        testDrive2.setCustomerId("customer_2");
        testDrive2.setCustomerName("Trần Thị B");
        testDrive2.setVehicleId("2");
        testDrive2.setVehicleName("Tesla Model 3");
        testDrive2.setScheduledDate(new Date(System.currentTimeMillis() + 172800000)); // Day after tomorrow
        testDrive2.setStatus("SCHEDULED");
        testDrive2.setNotes("Test drive cho gia đình");
        testDrives.add(testDrive2);
        
        TestDrive testDrive3 = new TestDrive();
        testDrive3.setId("testdrive_3");
        testDrive3.setCustomerId("customer_3");
        testDrive3.setCustomerName("Lê Văn C");
        testDrive3.setVehicleId("3");
        testDrive3.setVehicleName("BMW iX");
        testDrive3.setScheduledDate(new Date(System.currentTimeMillis() - 86400000)); // Yesterday
        testDrive3.setStatus("COMPLETED");
        testDrive3.setNotes("Đã hoàn thành test drive");
        testDrives.add(testDrive3);
        
        TestDrive testDrive4 = new TestDrive();
        testDrive4.setId("testdrive_4");
        testDrive4.setCustomerId("customer_4");
        testDrive4.setCustomerName("Hoàng Văn D");
        testDrive4.setVehicleId("6");
        testDrive4.setVehicleName("Tesla Model S");
        testDrive4.setScheduledDate(new Date(System.currentTimeMillis() + 259200000)); // 3 days later
        testDrive4.setScheduledTime("15:30");
        testDrive4.setStatus("SCHEDULED");
        testDrive4.setNotes("Khách hàng quan tâm đến hiệu suất cao");
        testDrives.add(testDrive4);
        
        TestDrive testDrive5 = new TestDrive();
        testDrive5.setId("testdrive_5");
        testDrive5.setCustomerId("customer_5");
        testDrive5.setCustomerName("Phạm Thị E");
        testDrive5.setVehicleId("9");
        testDrive5.setVehicleName("BMW iX");
        testDrive5.setScheduledDate(new Date(System.currentTimeMillis() + 345600000)); // 4 days later
        testDrive5.setScheduledTime("10:00");
        testDrive5.setStatus("SCHEDULED");
        testDrive5.setNotes("Test drive cho gia đình 4 người");
        testDrives.add(testDrive5);
        
        TestDrive testDrive6 = new TestDrive();
        testDrive6.setId("testdrive_6");
        testDrive6.setCustomerId("customer_1");
        testDrive6.setCustomerName("Nguyễn Văn A");
        testDrive6.setVehicleId("7");
        testDrive6.setVehicleName("Tesla Model X");
        testDrive6.setScheduledDate(new Date(System.currentTimeMillis() - 172800000)); // 2 days ago
        testDrive6.setScheduledTime("14:00");
        testDrive6.setStatus("COMPLETED");
        testDrive6.setRating(5);
        testDrive6.setFeedback("Xe rất tốt, tôi sẽ đặt mua");
        testDrive6.setPurchaseProbability(90);
        testDrive6.setNotes("Khách hàng rất hài lòng");
        testDrives.add(testDrive6);
        
        System.out.println("MockDataGenerator: Initialized " + testDrives.size() + " test drives");
    }
    
    // CRUD operations for vehicles
    public static void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    public static void updateVehicle(Vehicle updatedVehicle) {
        for (int i = 0; i < vehicles.size(); i++) {
            if (vehicles.get(i).getId().equals(updatedVehicle.getId())) {
                vehicles.set(i, updatedVehicle);
                break;
            }
        }
    }

    public static void deleteVehicle(String vehicleId) {
        vehicles.removeIf(vehicle -> vehicle.getId().equals(vehicleId));
    }

    public static Vehicle getVehicleById(String vehicleId) {
        return vehicles.stream()
                .filter(vehicle -> vehicle.getId().equals(vehicleId))
                .findFirst()
                .orElse(null);
    }

    // CRUD operations for orders
    public static void addOrder(Order order) {
        orders.add(order);
    }

    public static void updateOrder(Order updatedOrder) {
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getId().equals(updatedOrder.getId())) {
                orders.set(i, updatedOrder);
                break;
            }
        }
    }

    public static void deleteOrder(String orderId) {
        orders.removeIf(order -> order.getId().equals(orderId));
    }

    public static Order getOrderById(String orderId) {
        return orders.stream()
                .filter(order -> order.getId().equals(orderId))
                .findFirst()
                .orElse(null);
    }

    // CRUD operations for customers
    public static void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public static void updateCustomer(Customer updatedCustomer) {
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getId().equals(updatedCustomer.getId())) {
                customers.set(i, updatedCustomer);
                break;
            }
        }
    }

    public static void deleteCustomer(String customerId) {
        customers.removeIf(customer -> customer.getId().equals(customerId));
    }

    public static Customer getCustomerById(String customerId) {
        return customers.stream()
                .filter(customer -> customer.getId().equals(customerId))
                .findFirst()
                .orElse(null);
    }

    // CRUD operations for test drives
    public static void addTestDrive(TestDrive testDrive) {
        testDrives.add(testDrive);
    }

    public static void updateTestDrive(TestDrive updatedTestDrive) {
        for (int i = 0; i < testDrives.size(); i++) {
            if (testDrives.get(i).getId().equals(updatedTestDrive.getId())) {
                testDrives.set(i, updatedTestDrive);
                break;
            }
        }
    }

    public static void deleteTestDrive(String testDriveId) {
        testDrives.removeIf(testDrive -> testDrive.getId().equals(testDriveId));
    }

    public static TestDrive getTestDriveById(String testDriveId) {
        return testDrives.stream()
                .filter(testDrive -> testDrive.getId().equals(testDriveId))
                .findFirst()
                .orElse(null);
    }
    
    // User/Account Management for Admin
    public static List<User> getMockUsers() {
        if (users.isEmpty()) {
            initializeUsers();
        }
        return users;
    }
    
    private static void initializeUsers() {
        users.clear();
        
        // Admin users
        User admin1 = new User();
        admin1.setId("user_1");
        admin1.setUsername("admin");
        admin1.setEmail("admin@evdms.com");
        admin1.setFullName("Nguyễn Văn Admin");
        admin1.setPhone("0987654321");
        admin1.setRole("ADMIN");
        admin1.setStatus("ACTIVE");
        admin1.setDealerId(null);
        admin1.setCreatedAt("2024-01-01");
        admin1.setLastLoginAt("2024-06-15");
        admin1.setPassword("admin123");
        admin1.setAddress("123 Đường Admin, Quận 1, TP.HCM");
        admin1.setDepartment("IT");
        admin1.setPosition("System Administrator");
        admin1.setApproved(true);
        admin1.setApprovedBy("system");
        admin1.setApprovedAt("2024-01-01");
        users.add(admin1);
        
        // EVN Staff
        User evnStaff1 = new User();
        evnStaff1.setId("user_2");
        evnStaff1.setUsername("evn_staff_001");
        evnStaff1.setEmail("evnstaff1@evn.com");
        evnStaff1.setFullName("Trần Thị EVN");
        evnStaff1.setPhone("0987654322");
        evnStaff1.setRole("EVN_STAFF");
        evnStaff1.setStatus("ACTIVE");
        evnStaff1.setDealerId(null);
        evnStaff1.setCreatedAt("2024-01-15");
        evnStaff1.setLastLoginAt("2024-06-14");
        evnStaff1.setPassword("evn123");
        evnStaff1.setAddress("456 Đường EVN, Quận 2, TP.HCM");
        evnStaff1.setDepartment("Sales");
        evnStaff1.setPosition("Sales Manager");
        evnStaff1.setApproved(true);
        evnStaff1.setApprovedBy("admin");
        evnStaff1.setApprovedAt("2024-01-15");
        users.add(evnStaff1);
        
        // Dealer Manager
        User dealerManager1 = new User();
        dealerManager1.setId("user_3");
        dealerManager1.setUsername("dealer_manager_001");
        dealerManager1.setEmail("manager@vinfast.com");
        dealerManager1.setFullName("Lê Văn Manager");
        dealerManager1.setPhone("0987654323");
        dealerManager1.setRole("DEALER_MANAGER");
        dealerManager1.setStatus("ACTIVE");
        dealerManager1.setDealerId("dealer001");
        dealerManager1.setCreatedAt("2024-02-01");
        dealerManager1.setLastLoginAt("2024-06-15");
        dealerManager1.setPassword("manager123");
        dealerManager1.setAddress("789 Đường Manager, Quận 3, TP.HCM");
        dealerManager1.setDepartment("Management");
        dealerManager1.setPosition("Dealer Manager");
        dealerManager1.setApproved(true);
        dealerManager1.setApprovedBy("admin");
        dealerManager1.setApprovedAt("2024-02-01");
        users.add(dealerManager1);
        
        // Dealer Staff
        User dealerStaff1 = new User();
        dealerStaff1.setId("user_4");
        dealerStaff1.setUsername("dealer_staff_001");
        dealerStaff1.setEmail("staff1@vinfast.com");
        dealerStaff1.setFullName("Phạm Thị Staff");
        dealerStaff1.setPhone("0987654324");
        dealerStaff1.setRole("DEALER_STAFF");
        dealerStaff1.setStatus("ACTIVE");
        dealerStaff1.setDealerId("dealer001");
        dealerStaff1.setCreatedAt("2024-02-15");
        dealerStaff1.setLastLoginAt("2024-06-14");
        dealerStaff1.setPassword("staff123");
        dealerStaff1.setAddress("321 Đường Staff, Quận 4, TP.HCM");
        dealerStaff1.setDepartment("Sales");
        dealerStaff1.setPosition("Sales Staff");
        dealerStaff1.setApproved(true);
        dealerStaff1.setApprovedBy("dealer_manager_001");
        dealerStaff1.setApprovedAt("2024-02-15");
        users.add(dealerStaff1);
        
        // Pending approval user
        User pendingUser = new User();
        pendingUser.setId("user_5");
        pendingUser.setUsername("new_dealer_001");
        pendingUser.setEmail("newdealer@tesla.com");
        pendingUser.setFullName("Nguyễn Văn Mới");
        pendingUser.setPhone("0987654325");
        pendingUser.setRole("DEALER_MANAGER");
        pendingUser.setStatus("PENDING_APPROVAL");
        pendingUser.setDealerId("dealer002");
        pendingUser.setCreatedAt("2024-06-10");
        pendingUser.setLastLoginAt(null);
        pendingUser.setPassword("newdealer123");
        pendingUser.setAddress("654 Đường Mới, Quận 5, TP.HCM");
        pendingUser.setDepartment("Management");
        pendingUser.setPosition("Dealer Manager");
        pendingUser.setApproved(false);
        pendingUser.setApprovedBy(null);
        pendingUser.setApprovedAt(null);
        users.add(pendingUser);
        
        // Inactive user
        User inactiveUser = new User();
        inactiveUser.setId("user_6");
        inactiveUser.setUsername("inactive_staff");
        inactiveUser.setEmail("inactive@vinfast.com");
        inactiveUser.setFullName("Võ Thị Inactive");
        inactiveUser.setPhone("0987654326");
        inactiveUser.setRole("DEALER_STAFF");
        inactiveUser.setStatus("INACTIVE");
        inactiveUser.setDealerId("dealer001");
        inactiveUser.setCreatedAt("2024-01-20");
        inactiveUser.setLastLoginAt("2024-05-01");
        inactiveUser.setPassword("inactive123");
        inactiveUser.setAddress("987 Đường Inactive, Quận 6, TP.HCM");
        inactiveUser.setDepartment("Sales");
        inactiveUser.setPosition("Sales Staff");
        inactiveUser.setApproved(true);
        inactiveUser.setApprovedBy("dealer_manager_001");
        inactiveUser.setApprovedAt("2024-01-20");
        users.add(inactiveUser);
        
        System.out.println("MockDataGenerator: Initialized " + users.size() + " users");
    }
    
    // CRUD operations for users
    public static void addUser(User user) {
        users.add(user);
    }

    public static void updateUser(User updatedUser) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(updatedUser.getId())) {
                users.set(i, updatedUser);
                break;
            }
        }
    }

    public static void deleteUser(String userId) {
        users.removeIf(user -> user.getId().equals(userId));
    }

    public static User getUserById(String userId) {
        return users.stream()
                .filter(user -> user.getId().equals(userId))
                .findFirst()
                .orElse(null);
    }
    
    public static List<User> getUsersByRole(String role) {
        return users.stream()
                .filter(user -> user.getRole().equals(role))
                .collect(java.util.stream.Collectors.toList());
    }
    
    public static List<User> getPendingApprovalUsers() {
        return users.stream()
                .filter(user -> "PENDING_APPROVAL".equals(user.getStatus()))
                .collect(java.util.stream.Collectors.toList());
    }
}