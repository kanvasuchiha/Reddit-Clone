# Reddit-Clone
Reddit Clone using SpringBoot and Angular

<h2> ABout SpringBoot Application</h2>

1) To read more about Lombok, Project Lombok is a java library that automatically plugs into your editor and build tools, spicing up your java.
Never write another getter or equals method again, with one annotation your class has a fully featured builder, Automate your logging variables, and much more.
   https://objectcomputing.com/resources/publications/sett/january-2010-reducing-boilerplate-code-with-project-lombok

2) To read more about @JoinColumn annotation, 
   https://www.baeldung.com/jpa-join-column

3) We set "spring.jpa.hibernate.ddl-auto=update" in resources/application.properties, but when we have to deploy it to production we change the value to none

4) We create a dto - Data Transfer Object for RegisterRequest that will be part of the requestbody for the "$/signup" url. 
5) Before saving the user in AuthService.java, we need to make sure that we encode the password. Storing the password in real text is a bad idea. Even if our DB is compromised, it will be hard for a hacker to crack passwords if they are encoded with a hashcode. One of the best hashing algorithms we can use is "Bcrypt Hashing". Spring Security provides us a class, which implements this hashing algorithm, known as "BCryptPasswordEncoder"

6) There is nothing wrong with Field Injection @Autowire objects, but spring recommends that we should use constructor injection whenever possible.
   https://odrotbohm.de/2013/11/why-field-injection-is-evil/
7) Now once the signup method from AuthService.java class ids done, next work we have to do is send an email activation link. The Idea here is to generate a verification token at the time of saving the user info and send it as part of the email verification. Once the verification is done, we change the enabled field to "true".

8) In the backend when we are creating REST APIs, exceptions are pretty common in the code. So whenever exceptions occur, we don't want any technical details to be exposed to the user. Rather than showing NullPointerException, IllegalStateException, etc., it is better if we share the exception with the user in a much understandable message format. We can do this exact thing by creating our own custom exceptions.

9) MailTrap - Capture SMTP traffic from staging and dev environments. Automate test flows and scenarios with flexible API. Analyze email content for spam score and validate HTML/CSS 

10) I would remove the referencedColumnName attribute in User class because you are naming the primary key field of Post and Subreddit. I think it is only necessary if you it to reference a non-primary-key field. Now the signup REST API is complete.

![Postman Success](https://user-images.githubusercontent.com/38052562/165365574-d7d698af-781f-42db-9111-66627ae39362.PNG)
