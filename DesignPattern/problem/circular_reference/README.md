## 순환참조 문제 

***************************
APPLICATION FAILED TO START
***************************

Description:

The dependencies of some of the beans in the application context form a cycle:

   userController defined in file [/Users/thlee/Desktop/gitRepo/BackEndDevelopment/DesignPattern/problem/circular_reference/build/classes/java/main/problem/circular_reference/user_mngt/controller/UserController.class]
┌─────┐
|  userServiceImpl defined in file [/Users/thlee/Desktop/gitRepo/BackEndDevelopment/DesignPattern/problem/circular_reference/build/classes/java/main/problem/circular_reference/user_mngt/service/impl/UserServiceImpl.class]
↑     ↓
|  userGrpServiceImpl defined in file [/Users/thlee/Desktop/gitRepo/BackEndDevelopment/DesignPattern/problem/circular_reference/build/classes/java/main/problem/circular_reference/usergrp_mngt/service/imple/UserGrpServiceImpl.class]
└─────┘


Action:

Relying upon circular references is discouraged and they are prohibited by default. Update your application to remove the dependency cycle between beans. As a last resort, it may be possible to break the cycle automatically by setting spring.main.allow-circular-references to true.


Process finished with exit code 1