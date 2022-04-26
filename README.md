# Reddit-Clone
Reddit Clone using SpringBoot and Angular

<h2> About SpringBoot Application</h2>

1) To read more about Lombok, Project Lombok is a java library that automatically plugs into your editor and build tools, spicing up your java.
Never write another getter or equals method again, with one annotation your class has a fully featured builder, Automate your logging variables, and much more.
   https://objectcomputing.com/resources/publications/sett/january-2010-reducing-boilerplate-code-with-project-lombok

2) To read more about @JoinColumn annotation, 
   https://www.baeldung.com/jpa-join-column
   If any class annotated with @NoRepositoryBean Spring Data does not create instances at run-time. Because it's intermediate class and it's created to add     
   functionality for the derived class.</br>
   <b>Why dont we need any implementation for repository interfaces??</b></br>
   Ans - @Component and @Repository can be used on classes to use them as Spring Beans. However, AlienRepo is not a class, it's an interface which extends a Spring Data interface. Spring Data doesn't use annotations, it detects the interfaces by scanning the classpath and looking at the hierarchy of interfaces to see if a Spring Data interface is extended. If this is the case, it creates an implementation for each interface during runtime, which is then added to the application context as Spring Bean. That's why you don't need any annotations on a Spring Data interface.

If you extend an interface that has @NoRepositoryBean and your derived interface doesn't have @NoRepositoryBean Spring knows that you are trying to use that functionality in your real Repository interface so adding @Repository annotation is verbose.

3) We set "spring.jpa.hibernate.ddl-auto=update" in resources/application.properties, but when we have to deploy it to production we change the value to none

4) We create a dto - Data Transfer Object for RegisterRequest that will be part of the requestbody for the "$/signup" url. 
5) Before saving the user in AuthService.java, we need to make sure that we encode the password. Storing the password in real text is a bad idea. Even if our DB is compromised, it will be hard for a hacker to crack passwords if they are encoded with a hashcode. One of the best hashing algorithms we can use is "Bcrypt Hashing". Spring Security provides us a class, which implements this hashing algorithm, known as "BCryptPasswordEncoder"

6) There is nothing wrong with Field Injection @Autowire objects, but spring recommends that we should use constructor injection whenever possible.
   https://odrotbohm.de/2013/11/why-field-injection-is-evil/
7) Now once the signup method from AuthService.java class ids done, next work we have to do is send an email activation link. The Idea here is to generate a verification token at the time of saving the user info and send it as part of the email verification. Once the verification is done, we change the enabled field to "true".

8) In the backend when we are creating REST APIs, exceptions are pretty common in the code. So whenever exceptions occur, we don't want any technical details to be exposed to the user. Rather than showing NullPointerException, IllegalStateException, etc., it is better if we share the exception with the user in a much understandable message format. We can do this exact thing by creating our own custom exceptions.

9) MailTrap - Capture SMTP traffic from staging and dev environments. Automate test flows and scenarios with flexible API. Analyze email content for spam score and validate HTML/CSS 

10) I would remove the referencedColumnName attribute in User class because you are naming the primary key field of Post and Subreddit. I think it is only necessary if you it to reference a non-primary-key field. Now the signup REST API is complete.

<h3>Success message in Postman for /signup REST API</h3>

![Postman Success](https://user-images.githubusercontent.com/38052562/165365574-d7d698af-781f-42db-9111-66627ae39362.PNG)

<h3>Received mail for authentication in mailtrap</h3>

![image](https://user-images.githubusercontent.com/38052562/165365874-7f49fa28-46c9-4cf5-8b28-2d677168a61e.png)
-----------------------------------------------------------------------------------------------------------------------------------

<h2>User Verification and Async Processing</h2>
1) Testing User verification API 

![image](https://user-images.githubusercontent.com/38052562/165388410-8a73d357-7fad-4e1a-9750-dccee4d418cf.png)
But the entire /signup call will take approx 10-15 second, until this email is sent and then account is verified, meaning the user has to wait for 10-15 second after clicking on signup button in the UI. As we are contacting external mail server, these kind of calls are expensive and may take up a lot of time. To counter this delay we can execute the code which sends the verification mail asynchronously by running this part in a different thread. Spring provides us simple asynchronous capabilities whenever required, we just need to add @EnableAsync at the top of main app class "RedditCloneApplication" and @Async on top of every expensive method.

Before @Async

![image](https://user-images.githubusercontent.com/38052562/165390172-5ae0f046-3bc1-4eab-a3e6-094eed0cbb3e.png)

After @Async
![image](https://user-images.githubusercontent.com/38052562/165390285-e3a8d846-0a59-4301-bc57-0305310d1729.png)

Alternate way of doing it is by using some Message Queues like RabbitMQ or ActiveMQ, They provide reliability and also faster ways to perform such a heavy task. But for a normal project we can do it using @Async, as these are too heavyweight for our normal usecase. For large usecases we better use these MQs.



