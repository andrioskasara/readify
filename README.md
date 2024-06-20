# Readify

## Description
Readify is a full-stack web application built to manage book collections. It provides users with tools to register, authenticate securely, 
and engage in various book-related activities such as creating, updating, sharing, and archiving books. The application facilitates book borrowing with availability checks, 
enables users to return books with optional ratings and reviews, and supports the approval process for book returns by owners. Readify aims to enhance the experience of managing and 
sharing books within a community setting.

## Key Features
   - User Registration and Authentication: Secure registration and login processes.
   - Email Validation: Account activation using secure email validation codes.
   - Book Management: CRUD operations for managing books, including sharing and archiving.
   - Book Borrowing: Check availability and manage borrowing of books within the community.
   - Book Returning: Option to return borrowed books and provide ratings and reviews.
   - Approval System: Functionality for owners to approve book returns initiated by others.
   - Search Functionality: Search for books by title, author, or ISBN.

## Technologies Used
### Backend
Java, Spring Boot, Spring Data JPA, Spring Security
### Frontend
Frontend: Angular, HTML, SCSS, TypeScript, Bootstrap
### Database
PostgreSQL
### Other
Docker, JWT for authentication, MailDev for local mail testing, OpenAPI for API documentation

## Setup Instructions
To run this application locally, follow these steps:
1. Clone the repository from GitHub:
   ```
   git clone https://github.com/andrioskasara/readify.git
   cd readify-app
   ```
2. Set up PostgreSQL and MailDev containers using Docker Compose:
   ```
   docker-compose up -d
   ```
3. Configure application-dev.yml:    
   Navigate to readify-app/readify/src/main/resources and set the following environment variables:  
   - db_username: Your PostgreSQL database username.  
   - db_password: Your PostgreSQL database password.  
   - mail_username: Your MailDev username for local email testing.  
   - mail_password: Your MailDev password for local email testing.  
   - secret_key: Secret key used for JWT token generation and authentication.  
   Ensure these variables are correctly configured according to your local environment setup.  
4. Build and run the backend:
   - Navigate to the backend directory (readify-app/readify).
   - Start the backend server using Maven or your IDE.
5. Build and run the frontend:
   - Navigate to the frontend directory (readify-app/frontend).
   - Install dependencies:
     ```
     npm install
     ```
   - Start the Angular development server:
     ```
     ng serve
     ```
6. Access the application: 
   - Open your web browser and navigate to http://localhost:4200 to access the Readify application.
