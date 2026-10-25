# Smart Chit 360

Smart Chit 360 is a Java-based backend application designed to simplify traditional chit savings, community loan management, and gold savings tracking. It brings three financial management modules into a single system: General Chit, Street Chit, and Gold Chit. The application automates financial calculations, organizes member and borrower details, and stores records in a MySQL database. It helps reduce manual record-keeping and makes financial information easier to manage. Built using Spring Boot, Hibernate/JPA, and MySQL, the project demonstrates how backend technologies can support traditional financial processes.

## 🚀 Problem Statement

Traditional chit and savings systems often rely on manual calculations, paper records, and handwritten receipts, which can lead to errors and missing information.

- **Manual Record-Keeping:** Maintaining member contributions, loan repayments, and gold savings in notebooks makes record management difficult.
- **Chit Rotation Confusion:** Manually deciding and tracking monthly turns can lead to confusion and disagreements among members.
- **Complex Loan Collection:** Managing multiple borrowers, calculating interest, and tracking leader-wise collections increases the workload of loan officers and group leaders.
- **Limited Gold Savings Tracking:** Losing a physical savings book can make it difficult to verify previous deposits and payment details.

## ✨ Key Features

- **General Chit Management:** Supports family and office chit schemes with member details, installment calculations, and rotational turn scheduling.
- **Emergency Turn Changes:** Allows family chit turns to be adjusted when a member has an urgent financial need.
- **Street Chit Management:** Calculates loan interest, total repayment, monthly installments, and leader-wise collection targets.
- **Gold Savings Ledger:** Calculates gold quantity based on the deposit amount and the applicable gold rate, and generates a digital receipt token.
- **Database Record Management:** Stores scheme details, participant information, and gold savings records in MySQL for future retrieval.
- **Console-Based Interaction:** Allows users to select modules, enter details, and view calculated results through the terminal.

## 🛠️ Tech Stack

- **Programming Language:** Java
- **Backend Framework:** Spring Boot
- **ORM Framework:** Hibernate
- **Data Access:** Spring Data JPA
- **Database:** MySQL
- **Database Connectivity:** JDBC / MySQL Connector
- **Development Tools:** Eclipse IDE, MySQL Workbench
- **Build Tool:** Apache Maven
- **Version Control:** Git & GitHub
