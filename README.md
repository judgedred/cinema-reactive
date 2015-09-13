# cinema
Study project.

Contents of the project:

- sql. Contains sql scripts for database create and fill the content.
Also cinema model view.

- src. Contains cinema src files.


About the project.

"Cinema" is a study project for the real cinema located in Borisov, called "October". The project's goal is to improve Java skills and learn best java practices by creating a service, that can help people to preorder a ticket online with no need going to the place. At the moment the "October" can only provide a social network group with the information of current film shows. The service can make going to the cinema more convinient, people's life a little bit easier as this is information technologies are made for.

The service acquire all the functionality you need to order a ticket:
- "Register". Get your own personal area with your preorders by registering on a website. Your data will be secured by the password you choose, which is stored in a database as a hash code. While choosing a login, the system will automatically inform you if the name is used or free. Also the service will popup if the email is already registered in the system.

-  "Film show". The service provides a list of next film shows, grouped by date. Also you can see the "today" shows meeting you at the main page. Click on any to move to the next page, containing tickets left for the film show. Choose the seat and make it yours! The reserved seat will appear in your personal area, where you can view it. 

- "News". Is made to contain the changes in life of the cinema (new furniture, equipment, changes in the schedule, etc.).

- "Films". Contains coming films with it's description.

- "About". Basic information of the cinema (adress, phones).

- "Admin panel". By typing ../cinema/admin you access the login form to the admin panel. It will help you easily maintain the service. Default user is "admin" with the same password. Here you can view, add, delete film shows, tickets, films, seats, halls, reservations, users. The system will not let you to add duplicates or null. When a film show added, you can easily generate the tickets for the show according to the seat list of the hall the film is shown in.

The design can seem a little bit poor, because the main focus was functionality and Java technologies. It is not the final version of the project. Work will go on by improving knowledge in Java and making project more up to date. 

Java and web technologies used in the project: DAO pattern, MySql database, Maven project object model, Hibernate + JPA + JDBC, JUnit tests, Spring DI, Ajax + jQuery, CSS, Intellij Idea IDE.
