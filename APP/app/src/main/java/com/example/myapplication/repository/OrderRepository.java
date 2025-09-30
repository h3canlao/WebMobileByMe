package com.example.myapplication.repository;

import com.example.myapplication.model.Order;
import com.example.myapplication.model.request.CreateOrderRequest;
import com.example.myapplication.utils.MockDataGenerator;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

public class OrderRepository {
    
    public OrderRepository() {
        // No network setup needed for mock data
    }
    
    public void getOrders(String status, String dealerId, String staffId, OrderListCallback callback) {
        // Use mock data directly
        List<Order> mockOrders = MockDataGenerator.getMockOrders();
        
        // Filter by status if specified
        if (status != null && !status.isEmpty() && !"ALL".equals(status)) {
            List<Order> filteredOrders = new ArrayList<>();
            for (Order order : mockOrders) {
                if (status.equals(order.getStatus())) {
                    filteredOrders.add(order);
                }
            }
            callback.onSuccess(filteredOrders);
        } else {
            callback.onSuccess(mockOrders);
        }
    }
    
    public void createOrder(CreateOrderRequest request, OrderCallback callback) {
        // Create mock order using setters with mock data
        Order newOrder = new Order();
        newOrder.setId(String.valueOf(System.currentTimeMillis()));
        newOrder.setOrderNumber("ORD" + System.currentTimeMillis());
        newOrder.setCustomerId(request.getCustomerId() != null ? request.getCustomerId() : "customer001");
        newOrder.setDealerId("dealer001"); // Mock dealer ID
        newOrder.setStaffId("staff001"); // Mock staff ID
        newOrder.setVehicleId(request.getVehicleId() != null ? request.getVehicleId() : "1");
        newOrder.setOrderDate(new Date());
        newOrder.setDeliveryDate(new Date(System.currentTimeMillis() + 86400000)); // 1 day from now
        newOrder.setTotalAmount(1200000000); // Mock amount
        newOrder.setStatus("PENDING");
        newOrder.setNotes(request.getNotes() != null ? request.getNotes() : "Đơn hàng mới được tạo");
        newOrder.setPaymentMethod(request.getPaymentMethod() != null ? request.getPaymentMethod() : "FULL_PAYMENT");
        newOrder.setPaidAmount(0);
        newOrder.setSelectedColor(request.getSelectedColor() != null ? request.getSelectedColor() : "Default");
        newOrder.setConfiguration(request.getConfiguration() != null ? request.getConfiguration() : "Standard");
        newOrder.setDiscount(request.getDiscount() != null ? request.getDiscount() : "0%");
        newOrder.setCreatedAt(new Date().toString());
        
        callback.onSuccess(newOrder);
    }
    
    public void approveOrder(String orderId, OrderCallback callback) {
        // Mock approval - find order and update status
        List<Order> mockOrders = MockDataGenerator.getMockOrders();
        for (Order order : mockOrders) {
            if (orderId.equals(order.getId())) {
                order.setStatus("APPROVED");
                callback.onSuccess(order);
                return;
            }
        }
        
        // If not found, create a mock approved order
        Order approvedOrder = new Order();
        approvedOrder.setId(orderId);
        approvedOrder.setOrderNumber("ORD" + orderId);
        approvedOrder.setCustomerId("customer001");
        approvedOrder.setDealerId("dealer001");
        approvedOrder.setStaffId("staff001");
        approvedOrder.setVehicleId("1");
        approvedOrder.setOrderDate(new Date());
        approvedOrder.setDeliveryDate(new Date(System.currentTimeMillis() + 86400000));
        approvedOrder.setTotalAmount(1200000000);
        approvedOrder.setStatus("APPROVED");
        approvedOrder.setNotes("Đơn hàng đã được duyệt");
        approvedOrder.setPaymentMethod("FULL_PAYMENT");
        approvedOrder.setPaidAmount(0);
        approvedOrder.setSelectedColor("Default");
        approvedOrder.setConfiguration("Standard");
        approvedOrder.setDiscount("0%");
        approvedOrder.setCreatedAt(new Date().toString());
        
        callback.onSuccess(approvedOrder);
    }
    
    public interface OrderListCallback {
        void onSuccess(List<Order> orders);
        void onError(String error);
    }
    
    public interface OrderCallback {
        void onSuccess(Order order);
        void onError(String error);
    }
}
