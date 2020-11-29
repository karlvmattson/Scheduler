*********************************************************************************************
Program:        C195-Scheduler
*********************************************************************************************

Purpose:        An appointment scheduling program for a global consulting organization.
Version:        1.02
Date:           11/29/2020
Author:         Karl Mattson
Email:          kmatts5@wgu.edu

*********************************************************************************************
---Software Versions---

IDE:            IntelliJ IDEA Community 2020.2.3
JDK:            Java jdk-11.0.9
JavaFX:         javafx-sdk-11.0.2

*********************************************************************************************
---User Instructions---

Set user system to either English or French.

Build program and run src\scheduler\Main.java.
Login:  test
Pass:   test
Login attempts are logged to src\logs\login.txt

Customers tab allows creation and editing of customer records. It also allows creation of new
appointments after selecting a customer in the TableView.

View Schedule tab allows viewing and editing of appointments.

Reports tab allows running of reports.

*********************************************************************************************
---Additional report---

Division Report: Displays a list of customers organized by First Level Division. Also displays
a total of the appointments for each customer. Divisions without customers are omitted.
