package com.onlinefood.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.onlinefood.dao.AddressDao;
import com.onlinefood.dao.CategoryDao;
import com.onlinefood.dao.UserDao;
import com.onlinefood.entity.Address;
import com.onlinefood.entity.Category;
import com.onlinefood.entity.User;
import com.onlinefood.utility.Constants.CategoryStatus;
import com.onlinefood.utility.Constants.UserRole;
import com.onlinefood.utility.Constants.UserStatus;

@Component
public class InitialDataLoader implements CommandLineRunner {

    @Autowired
    private UserDao userDao;
    
    @Autowired
    private CategoryDao categoryDao;
    
    @Autowired
    private AddressDao addressDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Create default admin if not exists
        createDefaultAdmin();
        
        // Create default categories if not exists
        createDefaultCategories();
        
        // Create default restaurant and delivery person for testing
        createDefaultRestaurantAndDelivery();
        
        // Create default customer for testing
        createDefaultCustomer();
    }
    
    private void createDefaultAdmin() {
        if (userDao.findByRoleAndStatus(UserRole.ROLE_ADMIN.value(), UserStatus.ACTIVE.value()).isEmpty()) {
            // Create default admin
            User admin = new User();
            admin.setFirstName("Admin");
            admin.setLastName("User");
            admin.setEmailId("admin@food.com");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRole(UserRole.ROLE_ADMIN.value());
            admin.setStatus(UserStatus.ACTIVE.value());
            admin.setPhoneNo("1234567890");
            
            userDao.save(admin);
            
            System.out.println("Default admin user created with email: admin@food.com and password: admin");
        }
    }
    
    private void createDefaultCategories() {
        if (categoryDao.findAll().isEmpty()) {
            List<String[]> defaultCategories = Arrays.asList(
                new String[]{"Pizza", "Delicious Italian pizzas with various toppings"},
                new String[]{"Burger", "Juicy burgers with fresh ingredients"},
                new String[]{"Chinese", "Authentic Chinese cuisine"},
                new String[]{"Indian", "Traditional Indian dishes with rich flavors"},
                new String[]{"Dessert", "Sweet treats to satisfy your cravings"},
                new String[]{"Drinks", "Refreshing beverages for any occasion"}
            );
            
            for (String[] categoryInfo : defaultCategories) {
                Category category = new Category();
                category.setName(categoryInfo[0]);
                category.setDescription(categoryInfo[1]);
                category.setStatus(CategoryStatus.ACTIVE.value());
                categoryDao.save(category);
            }
            
            System.out.println("Default categories created");
        }
    }
    
    private void createDefaultRestaurantAndDelivery() {
        // Check if a restaurant exists already
        if (userDao.findByRoleAndStatus(UserRole.ROLE_RESTAURANT.value(), UserStatus.ACTIVE.value()).isEmpty()) {
            // Create a default restaurant
            Address restaurantAddress = new Address();
            restaurantAddress.setCity("Mumbai");
            restaurantAddress.setPincode(400001);
            restaurantAddress.setStreet("Restaurant Street");
            addressDao.save(restaurantAddress);
            
            User restaurant = new User();
            restaurant.setFirstName("Test");
            restaurant.setLastName("Restaurant");
            restaurant.setEmailId("restaurant@food.com");
            restaurant.setPassword(passwordEncoder.encode("restaurant"));
            restaurant.setRole(UserRole.ROLE_RESTAURANT.value());
            restaurant.setStatus(UserStatus.ACTIVE.value());
            restaurant.setPhoneNo("9876543210");
            restaurant.setAddress(restaurantAddress);
            
            User savedRestaurant = userDao.save(restaurant);
            
            System.out.println("Default restaurant created with email: restaurant@food.com and password: restaurant");
            
            // Create a default delivery person for this restaurant
            Address deliveryAddress = new Address();
            deliveryAddress.setCity("Mumbai");
            deliveryAddress.setPincode(400001);
            deliveryAddress.setStreet("Delivery Street");
            addressDao.save(deliveryAddress);
            
            User delivery = new User();
            delivery.setFirstName("Test");
            delivery.setLastName("Delivery");
            delivery.setEmailId("delivery@food.com");
            delivery.setPassword(passwordEncoder.encode("delivery"));
            delivery.setRole(UserRole.ROLE_DELIVERY.value());
            delivery.setStatus(UserStatus.ACTIVE.value());
            delivery.setPhoneNo("7894561230");
            delivery.setAddress(deliveryAddress);
            delivery.setRestaurant(savedRestaurant);
            
            userDao.save(delivery);
            
            System.out.println("Default delivery person created with email: delivery@food.com and password: delivery");
        }
    }
    
    private void createDefaultCustomer() {
        // Check if customer exists already
        if (userDao.findByRoleAndStatus(UserRole.ROLE_CUSTOMER.value(), UserStatus.ACTIVE.value()).isEmpty()) {
            // Create a default customer
            Address customerAddress = new Address();
            customerAddress.setCity("Delhi");
            customerAddress.setPincode(110001);
            customerAddress.setStreet("Customer Street");
            addressDao.save(customerAddress);
            
            User customer = new User();
            customer.setFirstName("Test");
            customer.setLastName("Customer");
            customer.setEmailId("customer@food.com");
            customer.setPassword(passwordEncoder.encode("customer"));
            customer.setRole(UserRole.ROLE_CUSTOMER.value());
            customer.setStatus(UserStatus.ACTIVE.value());
            customer.setPhoneNo("8765432109");
            customer.setAddress(customerAddress);
            
            userDao.save(customer);
            
            System.out.println("Default customer created with email: customer@food.com and password: customer");
        }
    }
} 