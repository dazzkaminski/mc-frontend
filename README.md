# Example of front-end for Medical Centre CMS

## Features:
* You can see all of the appointments on the dashboard for the specified day
* Sends confirmation email after adding a new patient
* Sends a reminder email a day before the appointment

## Build with
* Vaadin 14
* Spring Boot
* Maven

You can go to https://mc-front-end-v2.herokuapp.com/ too see the front-end. Please make sure to wake up the services first by clicking on each link below as the app is deployed on Heroku and services go to sleep after 30 min if not used.

### Microservices on Eureka server:

* [Patient Service](https://mc-patient-service.herokuapp.com/)
* [Doctor Service](https://mc-doctor-service.herokuapp.com/)
* [Appointment Service](https://mc-appointment-service.herokuapp.com/)
* [Prescription Service](https://mc-prescription-service.herokuapp.com/)
* [Prescription Service](https://mc-mail-service.herokuapp.com/)
* [Eureka Discovery Service](https://mc-discovery-service.herokuapp.com/)

Source code for back-end: https://github.com/dazzkaminski/mc-backend


