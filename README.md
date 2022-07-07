created two applcation seperatedlu on two different window

Go to window -> New Window

Microservice # 01 User : that fetch the user list with their contact [initaially without the microservic configuration, there is no contact in users]

Microservice # 02 Contacts : that fetch the user with their contacts only 

So, till now these are two independent application, no configuration of microservice architecture, that' mean
user are not talking with the contact application for fetching their contact list based on the user


*****************************************Implementing "Rest Template" ************************************************************************

For talking between bothe services we need to implement the Rest Template, where we need data from other service
so for the main() file we add these
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
		
	}
    
Now go to the controller and autowired the RestTepmlate 

@Autowired
	private RestTemplate restTemplate;
    

Now, under the GetMapping method give them the url address 

List<Contacts> contact =restTemplate.getForObject("http://localhost:9002/contacts/user/" + userId, List.class);
		
		user.setContact(contact);
		
	
		return user;

Now, they are talking with eachother.


*****************************************Implementing "Eureka Service Discovery" *****************************************************************
 we need to register all services[USer and Contacts] into the Eurka server, for discovery
 Eureka is also a microservice , like a springboot projectfrom start.spring.io
 used only Eueka Server [spring-cloud-netflix-eureka server]
 
than go to the main file of this application [Eurekaserver] and put annotation
@EnableEurekaServer  [creafully, we need server not client]

configure the application.yaml file 

server:
 port : 8761  //this is default port for the eureka server
 
 //now we need to tell th eeureka that don't register by themselve
 
 eureka:
 client:
  register-with-eureka : false
  
//fetch registery 

eureka:
 client:
  register-with-eureka : false
  fetchRegistery : false
  
so uptill now we dont the server configuration



*************************************Eureka Client *********************************

now we need to make the Eureka clients in out services [User, Contacts]
Now, we are making User as Eureka Client
 so in POM.xml file we need to add three things, first we need to get depenedency from Spring.start.io [Eureka discovery client]
 
 1 : first put the spring cloud version right after the java version , like below
 
  <java.version>17</java.version>
    <spring-cloud.version>2021.0.3</spring-cloud.version>
    
 2: now, add dependency 
 
 <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>

3: Now add dependencyManagement, right after all dependency closed

</dependencies>
  <dependencyManagement>
    <dependencies>
      <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-dependencies</artifactId>
        <version>${spring-cloud.version}</version>
        <type>pom</type>
        <scope>import</scope>
      </dependency>
    </dependencies>
  </dependencyManagement>


so till now we get one instance on eureka that will be for the User service, so similarly do for the Contact as well same things

 So, now you can see bith service in instance, these all done 
*************************************Change name on Eureka server for using in other services *************

Now renaming the clients services in Eureka Server, so we can see the meaning full name at Instance currentnly register with the eureka service tab 
at eureka server port 8761:

so go to the user POM file and add these,

Now, we have the name fo rthe application services, so we can changed the hardcode local host in our codes
 
 List<Contacts> contact =restTemplate.getForObject("http://localhost:9002/contacts/user/" + userId, List.class);
 
 so here we can add the name intead of the localhost:9002, we can used ""
 Also, we need to add the @loadBalenced at the main() file of user service,
 so here two things :
 1: replaced the URL with the application name[name comes from the eureka, firstly written in User yaml file]
 
 2: than add loadbalanced annotation [@LoadBalanced] under the @Bean in main() file before the RestTemplate() method
 
 [First start eureka server , than User and than contact] 
 ***********very Important : in .yaml file the **********
 spring:
 application:
  name: user-service
  
  these shoudl not contain the "_" symbols
*************************************************************Implementing "API Gateway" *****************************************************************

API gate way is another spring boot project , and it has different dependencies

put annotation "@EnableEurekaClient" in the main() file

after creating yaml file, with this below configuration

server:
 port: 8999
 
 
spring:
 application:
  name: api-gateway

eureka:
 instance:
  hostname: localhost
  

Now, uptill, we get the api-gateway service running and up.

Now, we need to defined the routing after in the same yaml apigateway file for further configuration
So, we need to defined the routing configuration here, so we can get access [url access] from the apigateway instead of
the services [user and contacts]
Let's do it

server:
  port: 8999

eureka:
  instance:
    hostname: localhost


spring:
  application:
    name: api-gateway
  cloud:
    gateway:
      routes:
        - id: user-service
          uri: lb://user-service
          predicates:
            - Path=/user/**
        - id: contact-service
          uri: lb://contact-service
          predicates:
            - Path=/contact/**
      
      
      
so here lb :loadbalancing
so here, we are routing the all traffic of /user/** to user-service ==> throug api gateway 
similarly, we are routing the all traffic of /contact/** to contact-service ==> throug api gateway 
so now we have api gateway configured 

so now the end pint will changed to following, by using api-gateway

http://localhost:8999/contact/user/3011

http://localhost:8999/user/3011

<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-circuitbreaker-reactor-resilience4j</artifactId>
		</dependency>

*************************************************************Hystrix configuration *****************************************************************

add annotation into the apigateway mainfile 

hystrix is used for the fault tolerance, it has callback fetaure, that we used if the any service is down

hystric has their owen dashbord for the montoring of the services

so, go to the apigateway yaml file and add these 
 we need to add the filter, so we named the circuitbraker,
 
 so we need to provide args, need to tell 2 things name of theservice
 and callbackuri [which url need to call]
 in the case of the user provide /userServiceFallback
 
 now create the Fallbackcontroller in api projec and paste these
 @GetMapping("/userServiceFallback")
	public String userServiceCallBack() {
		
		return "User service is down at this time";

	}
	
	@GetMapping("/contactServiceFallback")
	public String contactServiceCallBack() {
		
		return "Contact service is down at this time";
	}
    
    
    
yaml file:

spring:
  application:
    name: api-gateway
  cloud:
    gateway:
      routes:
        - id: user-service
          uri: lb://user-service
          predicates:
            - Path=/user/**
          filters:
            - name: CircuitBreaker
               args:
                 name: user-service
                 fallbackuri: forword:/userServiceFallback 
            
        - id: contact-service
          uri: lb://contact-service
          predicates:
            - Path=/contacts/**
          filters:
            - name: CircuitBreaker
               args:
                 name: contact-service
                 fallbackuri: forword:/contactServiceFallback 
 
 *****************************Also, we need to add the timeout as well*****************************
 
 we need to add these in apigateway yaml file
 
 hystrix:                
  command:
   fallbackmd:
    execution:
     isolation:
      thread:
       timeoutInMillieseconds: 5000       
       
       
       
now, we need to add the stream available that enable into the dashboard, so dashboard monitor 

       
management:
  endpoints:
    web:
      exposure:
        include: hystrix.stream
        
        
now we need to create the hystrix dashboard

*************************************************************Hystrix Dashboard configuration *****************************************************************
@EnableHystrix  //don't forget to add this aotherwise not working

this is another spring boot project for dashboard hystrix
so add spring web and generate the proj
now add the eurekadiscovery clientdependency as well by explore tab in spring.start.io

add this 
<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
		</dependency>
        
now google the "spring cloud starter netflix hystrix dashord" dependency 

		<!-- https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-netflix-eureka-client -->
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
			<version>2.2.9.RELEASE</version>
		</dependency>


		<!-- https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-netflix-hystrix-dashboard -->
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-netflix-hystrix-dashboard</artifactId>
			<version>2.2.9.RELEASE</version>
		</dependency>
    
    [bith should gave same version]
        
now add the spring cloud version after Java version 

<java.version>17</java.version>
    <spring-cloud.version>2021.0.3</spring-cloud.version>


no add the dependency managment from starter.spring.io

</dependencies>
	<dependencyManagement>
		<dependencies>
			<dependency>
				<groupId>org.springframework.cloud</groupId>
				<artifactId>spring-cloud-dependencies</artifactId>
				<version>${spring-cloud.version}</version>
				<type>pom</type>
				<scope>import</scope>
			</dependency>
		</dependencies>
	</dependencyManagement>
    


than go to the main() file and enable these

@SpringBootApplication
@EnableEurekaClient

now go to the eureka server end point and 

check Eureka all services running and ups
check : http://localhost:9555/actuator/hystrix.stream

than check localhost:9555/hystrix  => here we need to provide the stream, this is stream that we need to provide the starting mentioned url "http://localhost:9555/actuator/hystrix.stream"

now check the hystrix by down one service 



























