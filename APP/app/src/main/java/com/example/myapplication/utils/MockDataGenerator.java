package com.example.myapplication.utils;

import com.example.myapplication.model.Vehicle;
import com.example.myapplication.model.Order;
import com.example.myapplication.model.Customer;
import com.example.myapplication.model.TestDrive;
import com.example.myapplication.model.response.DashboardResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

/**
 * Utility class để tạo mock data cho demo
 */
public class MockDataGenerator {

    // Static lists to store data
    private static List<Vehicle> vehicles = new ArrayList<>();
    private static List<Order> orders = new ArrayList<>();
    private static List<Customer> customers = new ArrayList<>();
    private static List<TestDrive> testDrives = new ArrayList<>();

    public static List<Vehicle> getMockVehicles() {
        if (vehicles.isEmpty()) {
        
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
            "https://example.com/vf8.jpg", 
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
            "https://example.com/vf9.jpg", 
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
            "https://example.com/model3.jpg", 
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
            "https://example.com/modely.jpg", 
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
            "https://example.com/vf6.jpg", 
            "Crossover điện thân thiện với môi trường", 
            "MAINTENANCE", 
            "dealer001", 
            1
        ));
        
        }
        return vehicles;
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
        
        // Order 1: VinFast VF8
        Order order1 = new Order();
        order1.setId("1");
        order1.setOrderNumber("ORD001");
        order1.setCustomerId("customer001");
        order1.setVehicleId("1");
        order1.setDealerId("dealer001");
        order1.setStaffId("staff001");
        order1.setOrderDate(new Date(System.currentTimeMillis() - 86400000)); // 1 day ago
        order1.setDeliveryDate(new Date(System.currentTimeMillis() + 86400000)); // 1 day from now
        order1.setTotalAmount(1200000000);
        order1.setStatus("PENDING");
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
        order2.setVehicleId("3");
        order2.setDealerId("dealer001");
        order2.setStaffId("staff001");
        order2.setOrderDate(new Date(System.currentTimeMillis() - 172800000)); // 2 days ago
        order2.setDeliveryDate(new Date(System.currentTimeMillis() + 172800000)); // 2 days from now
        order2.setTotalAmount(1100000000);
        order2.setStatus("APPROVED");
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
        order3.setVehicleId("2");
        order3.setDealerId("dealer001");
        order3.setStaffId("staff002");
        order3.setOrderDate(new Date(System.currentTimeMillis() - 259200000)); // 3 days ago
        order3.setDeliveryDate(new Date(System.currentTimeMillis() + 259200000)); // 3 days from now
        order3.setTotalAmount(1500000000);
        order3.setStatus("COMPLETED");
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
        order4.setVehicleId("5");
        order4.setDealerId("dealer001");
        order4.setStaffId("staff001");
        order4.setOrderDate(new Date(System.currentTimeMillis() - 345600000)); // 4 days ago
        order4.setDeliveryDate(new Date(System.currentTimeMillis() + 345600000)); // 4 days from now
        order4.setTotalAmount(800000000);
        order4.setStatus("PENDING");
        order4.setNotes("Đơn hàng VinFast VF6 màu xám");
        order4.setPaymentMethod("FULL_PAYMENT");
        order4.setPaidAmount(0);
        order4.setSelectedColor("Xám");
        order4.setConfiguration("Standard");
        order4.setDiscount("0%");
        order4.setCreatedAt(new Date().toString());
        orders.add(order4);
        
        }
        return orders;
    }
    
    public static List<Customer> getMockCustomers() {
        if (customers.isEmpty()) {
        
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
        
        }
        return customers;
    }
    
    public static List<TestDrive> getMockTestDrives() {
        if (testDrives.isEmpty()) {
        
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
        
        }
        return testDrives;
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
}
