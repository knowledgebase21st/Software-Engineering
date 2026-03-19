This is for test purpose 

Before building run the setclasspath.bash  to add the correct classpath. This is made for java 17. 
You can add appropriate jars to classpath if needed. Here in libs directory the necessary jars are there.

Build each java individually and then run. 

JwtTokenGenerator.java : This will generate JWT (Jason Web Token)
JWTValidation.java     : This will validate if the JWT is correct, 
                         you can pass the generated, as a parameter. 
                         It will throw exception for any error.
JWTTokenDecoder.java   : This will decode the JWT, you need to pass the token as a parameter.

