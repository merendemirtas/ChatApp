Chat Application (Java Swing)
This project is a desktop chat application developed with Java using Swing for the user interface. The application follows the MVC architecture, separating UI panels, controllers, and core logic to ensure maintainability and scalability.
🚀 Features
User registration (sign up) and login
Secure password hashing
Create new chats and join existing chats
Active session management
Starred (favorite) messages
User settings management
Database-backed data persistence
Modular and extensible MVC structure
🛠 Technologies Used
Java
Java Swing (GUI)
JDBC (Database connection)
MVC Design Pattern
Password Hashing (Security)
📂 Project Structure
├── BasePanel.java
├── DBConnection.java
├── Main.java
├── sessionManager.java
├── PasswordHasher.java
│
├── Login
│   ├── LoginPanel.java
│   ├── LoginPanel.form
│   └── LoginController.java
│
├── Signup (Logup)
│   ├── LogupPanel.java
│   ├── LogupPanel.form
│   └── LogupController.java
│
├── Main Panel
│   ├── MainPanel.java
│   ├── MainPanel.form
│   └── MainPanelController.java
│
├── Chat
│   ├── NewChatPanel.java
│   ├── NewChatPanel.form
│   ├── NewChatController.java
│   ├── JoinChatPanel.java
│   ├── JoinChatPanel.form
│   └── JoinChatController.java
│
├── Starred Messages
│   ├── StarredMessagesPanel.java
│   ├── StarredMessagesPanel.form
│   └── StarredMessagesController.java
│
└── Settings
    ├── SettingsPanel.java
    ├── SettingsPanel.form
    └── SettingsController.java
🧩 Architecture Overview
Model
DBConnection.java
Handles database connectivity.
PasswordHasher.java
Provides secure password hashing.
sessionManager.java
Manages logged-in user sessions.
View
.form and Panel.java files
Built using Java Swing GUI designer.
Controller
*Controller.java files
Handle user interactions and business logic.
▶️ How to Run
Clone the repository:
git clone <repository-url>
Open the project in IntelliJ IDEA or any Java IDE supporting Swing forms.
Configure the database connection in DBConnection.java.
Run Main.java.
🔐 Security Notes
Passwords are never stored in plain text.
Hashing is handled via PasswordHasher.
Session state is centrally managed.
📌 Future Improvements
Real-time messaging (WebSocket support)
Message search functionality
User profile customization
Dark/Light theme support
Group chat permissions
