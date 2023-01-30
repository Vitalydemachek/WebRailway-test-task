# WebRailway-test-task

Short description: Basic purpose is opportunity to determine amount of empty seats and those numbers for particular trains on appropriate dates. Ticket cost accounting
isn't interesting within this task. It is essential that application strucShort description:
Basic purpose is opportunity to determine amount of empty seats and those numbers for particular trains on appropriate dates.
Ticket cost accounting isn't interesting within this task.
It is essential that application structure was designed optimally.
So database must contain minimum information however it must be enough to implement current task.
Project restrictions:
1. Project GUI has implemented as a two web pages named: "mainPage" and "search".
1.1. The first page are used to fill in customer's request parameters:
-departure point,
-arrive point,
-assigned departure date;
1.2. Then after customer writes down data required for a search suitable options and pushes button "Search" the second page is appeared. List of empty tickets is outputted there.
2. Data is entered in database directly by the same SQL request, which creates and sets tables. (deploy.sql)
Technologies were used:
1. Apache Maven (build system);
2. Spring boot module:
2.1. spring-boot-starter-parent (was use to inherit project and get benefits of configuring);
2.2. spring-boot-starter-web (was use to implement Spring MVC, apply Tomcat to manage with web application functions and to interact with Apache web server);
2.3. spring-boot-starter-thymeleaf (was use to develop of templates)
3. Database management system:
3.1. PostgrSQL
