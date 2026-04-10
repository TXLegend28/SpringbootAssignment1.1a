# SpringbootAssignment1.1 - CSC313 Assignment 1

**University of Fort Hare**  
**CSC313 – Object-Oriented Programming**  

**Assignment 1: Spring Boot Project with CRUD and Validation**  
**Submission Date:** 10 April 2026  

**Repository:** https://github.com/TXLegend28/SpringbootAssignment1.1a  

**Group Members/ collaborators**
1.	Zuma Gareth - 223038030 (TXLegend28)
2.	Ndamase Someleze - 202337430 (ndamasesomeleze24-arch)
3.	Mdingi Asemahle - 223031950 (mdingiasie-crypto) (Group Representative)
4.	Ziyatsha Axolile - 202359774
5.	Enam Ntlonti - 202210889 (Ed-Gift)
6.	Thokozani Nyingizwayo - 224028634

---

## Repository History & Name Changes

During development we created **multiple repositories** while learning and fixing issues:

- First repository: `springbootassignment`
- Second repository: `springbootassignment1.1`
- Final repository: `SpringbootAssignment1.1a` (this one)

All previous repositories still exist and contain the **initial states** of the project at different stages.  
**All initialisation, updates, commits, pushes by all contributors was done in Gareth Zuma (TXLegend28)** on his IntelliJ IDEA from using the **master = orign/master** branch.

---

## What is in this Repository

This is a **complete Spring Boot web application** for the **Computer Science Department** at the University of Fort Hare.  

It fully meets **all requirements** for **Part A** and **Part B** of the assignment:

- **Part A**: Basic Spring Boot application + HTTP endpoint + GitHub setup (50 marks)
- **Part B**: Full CRUD operations + Bean Validation + persistent MySQL database using XAMPP (50 marks)

The application features a **modern, professional dashboard** ( Bit-by-Bit Management System style) with clean UI, Tailwind CSS, hover effects, and responsive design.

---

## How to Run the Application

### 1. XAMPP Setup (Very Important – Database)

1. Open **XAMPP Control Panel**
2. Start **Apache** (runs on port **8081**)
3. Start **MySQL** (runs on port **3306**)
   - **Make sure no other MySQL server is running** on port 3306 (stop any other MySQL services if needed)
4. Open browser → `http://localhost:8081/phpmyadmin` (MySQL database)
5. Create a new database named exactly: `ufh_cs_courses`

### 2. Run the Spring Boot Application

1. Open the project in **IntelliJ IDEA**
2. Run the main class: `Application.java`
3. Wait until you see in the console:

4. Open your web browser and go to:  
**http://localhost:8082**

This is the **main professional dashboard** where you can view all courses.

You can also test the CRUD API directly at:  
- `http://localhost:8082/manage/`

---

## What We Built (Part A)

- Created a fresh Spring Boot Maven project named `SpringbootAssignment1.1` (Due to replication of project we result to this naming)
- Built an HTTP GET endpoint `/courses` that returns exactly:
- **2 Foundation courses**
- **5 Undergraduate courses**
- **4 Honours courses** (all real CSC codes used at UFH)
- Initialized local Git repository
- Pushed everything to GitHub
- Added all group members + lecturer as collaborators
- Made the page browser-friendly (HTML)

---

## What We Built (Part B)

- Full **CRUD** operations (Create, Read, Update, Delete) using REST endpoints
- **Validation** using Jakarta Bean Validation (`@NotBlank`, `@NotNull`)
- Persistent storage with **MySQL + JPA + Hibernate** (data survives app restart)
- Professional **Course-style management dashboard** with:
- Modern navbar
- Sidebar
- Beautiful course cards with hover animations
- Live data from MySQL
- Switched to custom port **8082** so it doesn’t clash with XAMPP Apache (8081)

---

## Easy and Hard Parts of the Assignment

**Easy parts:**
- Setting up the Spring Boot assignment and GitHub
- Creating the basic HTML endpoint for Part A
- Adding the initial 2 + 5 + 4 courses
- Making the first version of the dashboard

**Hard parts:**
- Switching from in-memory `ArrayList` to real MySQL database with XAMPP
- Fixing the DataSource configuration error
- Changing the server port to 8082 while keeping XAMPP on 8081
- Implementing full CRUD with proper validation
- Creating a clean, professional GUI that looks like a real management system
- Making sure the dashboard pulls live data from MySQL

---

## CRUD Attempts We Made

- **First attempt**: Used in-memory `ArrayList` (data disappeared on restart)
- **Second attempt**: Added Spring Data JPA + MySQL connector
- **Third attempt**: Fixed `application.properties` and XAMPP connection
- **Final version**: Everything works perfectly with persistent MySQL database + beautiful dashboard

---

## XAMPP Configuration Notes

- **Apache**: Runs on port **8081** (do not change)
- **MySQL**: Runs on port **3306** – **strictly reserved for this project**. Do not run any other MySQL instance.
- **Database name**: `ufh_cs_courses`
- **Application port**: **8082** (configured in `application.properties`)
- You can view/edit the raw database anytime at `http://localhost/phpmyadmin`

---

## If You Struggle to Make It Work

If anything doesn’t run or you get errors:
- Make sure XAMPP MySQL and Apache are running
- Check that port 3306 is free and port 8082 is not blocked
- Double-check `application.properties` file is in `src/main/resources`

**Contact for help:**  
**Email:** 223038030@ufh.ac.za

---

## Summary

This project has **everything** the assignment asked for:

- Correct Spring Boot setup
- Exact course counts for Foundation / Undergraduate / Honours
- Full working CRUD + Validation
- Persistent MySQL database via XAMPP
- Professional, fluid GUI dashboard
- Complete Git history and GitHub collaboration

Thank you for checking our work.

You can test the full application by:
1. Starting XAMPP (Apache + MySQL)
2. Running the app in IntelliJ
3. Opening **http://localhost:8082/courses**

Enjoy!
