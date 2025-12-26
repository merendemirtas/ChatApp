Chat Application (Java Swing)

This project is a Java-based desktop chat application developed using Java Swing.

The application follows the MVC (Model–View–Controller) architectural pattern to ensure a clean, modular, and maintainable codebase.



🚀 Features

User registration and login

Secure password hashing

Create new chat rooms

Join existing chats

Session management

Starred (favorite) messages

User settings management

Database integration

MVC-based modular structure


🛠 Technologies Used

Java

Java Swing (GUI)

JDBC (Database Connection)

MVC Design Pattern



BasePanel.java

DBConnection.java

Main.java

sessionManager.java

PasswordHasher.java


Login
 ├── LoginPanel.java
 ├── LoginPanel.form
 └── LoginController.java


Signup (Logup)
 ├── LogupPanel.java
 ├── LogupPanel.form
 └── LogupController.java


Main Panel
 ├── MainPanel.java
 ├── MainPanel.form
 └── MainPanelController.java


Chat
 ├── NewChatPanel.java
 ├── NewChatPanel.form
 ├── NewChatController.java
 ├── JoinChatPanel.java
 ├── JoinChatPanel.form
 └── JoinChatController.java


Starred Messages
 ├── StarredMessagesPanel.java
 ├── StarredMessagesPanel.form
 └── StarredMessagesController.java


Settings
 ├── SettingsPanel.java
 ├── SettingsPanel.form
 └── SettingsController.java



 🧩 Architecture Overview

Model

DBConnection.java

Manages database connections.

PasswordHasher.java

Handles secure password hashing.

sessionManager.java

Controls user session data.

View

Panel.java and .form files

User interface built with Java Swing.

Controller

Controller classes

Handle user actions and application logic.

▶️ How to Run

Clone the repository

Open the project in IntelliJ IDEA or another Java IDE

Configure database settings in DBConnection.java

Run Main.java


🔐 Security

Passwords are stored in hashed form

Session control is handled centrally

📌 Future Improvements

Real-time messaging support

Message search

User profile customization

Dark / Light theme support

