# Multivendor E-Commerce Backend

## About the Project

This is a comprehensive backend system for a Multivendor E-Commerce Platform built using Spring Boot. The platform facilitates online shopping where multiple sellers (vendors) can list and sell their products, and customers can browse, purchase, and manage their orders. The system also includes administrative features for managing users, sellers, and overall platform operations.

The platform supports three main types of users:
- **Customers (Users)**: Shoppers who can browse products, add to cart, place orders, and track their purchases.
- **Sellers (Vendors)**: Business owners who can manage their product inventory, view sales, and handle order fulfillment.
- **Admins**: Platform administrators who oversee user management, seller approvals, and system monitoring.

## Features

### Core Functionality
- **User Authentication & Authorization**: JWT-based authentication with role-based access control (USER, SELLER, ADMIN)
- **Product Management**: CRUD operations for products with categories, inventory tracking, and featured products
- **Cart Management**: Add/remove items, update quantities, persistent cart storage
- **Order Processing**: Complete order lifecycle from placement to delivery tracking
- **Seller Management**: Seller registration, approval workflow, business details, and bank information
- **Admin Dashboard**: User/seller management, product moderation, order oversight

### Technical Features
- RESTful API design
- Secure JWT token validation
- Database persistence with JPA/Hibernate
- Exception handling with custom error responses
- Input validation and data mapping
- Cross-origin resource sharing (CORS) support

## Tech Stack

- **Language**: Java 21
- **Framework**: Spring Boot 4.0.5
- **Database**: MySQL (configurable via environment variables)
- **ORM**: Spring Data JPA with Hibernate
- **Security**: Spring Security with JWT (JJWT library)
- **Build Tool**: Maven
- **Utilities**: Lombok for boilerplate reduction
- **Testing**: Spring Boot Test framework

## Architecture

The application follows a layered architecture pattern:

### Layers
1. **Controller Layer**: Handles HTTP requests and responses
   - `AuthController`: Authentication endpoints
   - `UserController`: User profile management
   - `ProductController`: Product browsing and search
   - `CartController`: Shopping cart operations
   - `OrderController`: Customer order management
   - `SellerController`: Seller account management
   - `SellerProductController`: Seller product management
   - `SellerOrderController`: Seller order handling
   - `AdminController`: Administrative operations

2. **Service Layer**: Business logic implementation
   - Interfaces in `service/` directory
   - Implementations in `service/impl/` directory

3. **Repository Layer**: Data access layer
   - JPA repositories for database operations
   - Custom query methods for complex data retrieval

4. **Model Layer**: Data entities and DTOs
   - JPA entities in `model/` directory
   - Request/Response DTOs in `request/` and `response/` directories
   - Enums for status and role definitions

### Key Components
- **Configuration**: JWT providers, security config, application settings
- **Exception Handling**: Global exception handler with custom error details
- **Mappers**: Object mapping between entities and DTOs

## Database Schema

### Main Entities
- **User**: Platform users with roles (CUSTOMER, SELLER, ADMIN)
- **Seller**: Extended user profile with business and bank details
- **Product**: Items for sale with categories and inventory
- **Category**: Product categorization
- **Cart/CartItem**: Shopping cart functionality
- **Order/OrderItem**: Order management and tracking
- **Address**: User addresses for shipping
- **Featured**: Featured products display

### Relationships
- User has many Orders, Addresses
- Seller has many Products
- Product belongs to Category and Seller
- Order has many OrderItems, belongs to User
- Cart has many CartItems, belongs to User

## API Endpoints

### Authentication
- `POST /auth/signup` - User registration
- `POST /auth/signin` - User login

### User Management
- `GET /api/users/profile` - Get user profile
- `PUT /api/users/profile` - Update user profile

### Product Management
- `GET /api/products` - List all products
- `GET /api/products/{id}` - Get product details
- `POST /api/products` - Create product (Seller/Admin)
- `PUT /api/products/{id}` - Update product (Seller/Admin)
- `DELETE /api/products/{id}` - Delete product (Seller/Admin)

### Cart Management
- `GET /api/cart` - Get user's cart
- `PUT /api/cart/add` - Add item to cart
- `PUT /api/cart/update` - Update cart item quantity
- `DELETE /api/cart/remove/{cartItemId}` - Remove item from cart

### Order Management
- `POST /api/orders` - Place order
- `GET /api/orders` - Get user's orders
- `GET /api/orders/{orderId}` - Get order details
- `PUT /api/orders/{orderId}/status` - Update order status

### Seller Operations
- `GET /api/sellers/products` - Get seller's products
- `POST /api/sellers/products` - Add new product
- `GET /api/sellers/orders` - Get orders for seller's products

### Admin Operations
- `GET /api/admin/users` - List all users
- `PUT /api/admin/users/{userId}/status` - Update user status
- `GET /api/admin/sellers` - List all sellers
- `PUT /api/admin/sellers/{sellerId}/status` - Approve/reject seller

## Prerequisites

Before running this application, ensure you have the following installed:

- **Java Development Kit (JDK) 21** or later
- **Apache Maven 3.6+** for dependency management and build
- **MySQL 8.0+** database server
- **Git** for version control

## Installation and Setup

### 1. Clone the Repository
```bash
git clone <repository-url>
cd multivendor-ecommerce
```

### 2. Configure Environment Variables
Create a `.env` file or set environment variables for database configuration:

```bash
# Server Configuration
SERVER_PORT=8080

# Database Configuration
DB_URL=jdbc:mysql://localhost:3306/multivendor_ecommerce
DB_DRIVER_CLASS=com.mysql.cj.jdbc.Driver
DB_USERNAME=your_db_username
DB_PASSWORD=your_db_password

# JPA Configuration
DDL_AUTO=update  # Options: create, update, validate, create-drop
SHOW_SQL=false   # Set to true for debugging
```

### 3. Database Setup
1. Create a MySQL database named `multivendor_ecommerce`
2. Ensure the database user has appropriate permissions
3. The application will automatically create/update tables based on `DDL_AUTO` setting

### 4. Build the Application
```bash
mvn clean install
```

### 5. Run the Application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080` (or the port specified in `SERVER_PORT`).

## Testing

### Unit Tests
Run unit tests using Maven:
```bash
mvn test
```

### Integration Tests
The application includes Spring Boot test configurations for integration testing.

## API Documentation

For detailed API documentation, you can use tools like:
- **Swagger/OpenAPI**: Configure and access at `/swagger-ui.html` (if enabled)
- **Postman**: Import the provided collection for testing endpoints

## Security

- JWT tokens are used for authentication
- Passwords are securely hashed
- Role-based access control ensures users can only access authorized resources
- CORS is configured for cross-origin requests

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Development Guidelines
- Follow Java naming conventions
- Write unit tests for new features
- Update documentation for API changes
- Ensure code passes all tests before submitting

## License

This project is licensed under the MIT License - see the LICENSE file for details.

---

*Built with Spring Boot for scalable e-commerce solutions.*

---