# Persistence Recreates
Some errors or strange situations encountered while working on Jakarta Data, recreated directly on Jakarta Persistence.

## Start postgres
User: postgres  
Password: password  
DB: postgres  
`docker run -d -p 5432:5432 -e POSTGRES_PASSWORD=password postgres:16.1`

## (Optional) Db2
Switch server.xml to jcc driver for DefaultDataSource (Not working on Apple Silicon)  
User: db2inst1  
Password: password  
DB: testdb  
`docker run -itd --privileged=true --platform=linux/amd64 -p 50000:50000 -e LICENSE=accept -e DB2INST1_PASSWORD=password -e DBNAME=testdb  ibmcom/db2:11.5.8.0`

## Run Liberty
`mvn liberty:dev`

## Recreates

After the application is started, use the following REST endpoints to demonstrate the issues. Errors should appear in the terminal where `liberty:dev` is running.

### ERROR: column "name" specified more than once
http://localhost:9080/db/crew/columnSpecifiedMoreThanOnce

``` 
Internal Exception: org.postgresql.util.PSQLException: ERROR: column "name" specified more than once
[INFO]   Position: 39
[INFO] Error Code: 0
[INFO] Call: INSERT INTO CREWMEMBER (CREWID, NAME, NAME, SIZE) VALUES (?, ?, ?, ?)
[INFO]  bind => [4 parameters bound]
[INFO] Query: InsertObjectQuery(com.example.application.CrewMember@506c9187)
[INFO] [ERROR   ] WTRN0074E: Exception caught from before_completion synchronization operation: jakarta.persistence.PersistenceException: Exception [EclipseLink-4002] (Eclipse Persistence Services - 4.0.2.v202306161219): org.eclipse.persistence.exceptions.DatabaseException
[INFO] Internal Exception: org.postgresql.util.PSQLException: ERROR: column "name" specified more than once
[INFO]   Position: 39
[INFO] Error Code: 0
[INFO] Call: INSERT INTO CREWMEMBER (CREWID, NAME, NAME, SIZE) VALUES (?, ?, ?, ?)
[INFO]  bind => [4 parameters bound]
[INFO] Query: InsertObjectQuery(com.example.application.CrewMember@506c9187)
[INFO]  at org.eclipse.persistence.internal.jpa.EntityManagerSetupImpl$1.handleException(EntityManagerSetupImpl.java:784)
[INFO]  at [internal classes]
[INFO]  at jdk.internal.reflect.GeneratedMethodAccessor1231.invoke(Unknown Source)
[INFO]  at java.base/java.lang.reflect.Method.invoke(Method.java:568)
[INFO]  at org.jboss.weld.interceptor.reader.SimpleInterceptorInvocation$SimpleMethodInvocation.invoke(SimpleInterceptorInvocation.java:73)
[INFO]  at org.jboss.weld.interceptor.proxy.InterceptorMethodHandler.executeAroundInvoke(InterceptorMethodHandler.java:84)
[INFO]  at org.jboss.weld.interceptor.proxy.InterceptorMethodHandler.executeInterception(InterceptorMethodHandler.java:72)
[INFO]  at org.jboss.weld.interceptor.proxy.InterceptorMethodHandler.invoke(InterceptorMethodHandler.java:56)
[INFO]  at org.jboss.weld.bean.proxy.CombinedInterceptorAndDecoratorStackMethodHandler.invoke(CombinedInterceptorAndDecoratorStackMethodHandler.java:79)
[INFO]  at org.jboss.weld.bean.proxy.CombinedInterceptorAndDecoratorStackMethodHandler.invoke(CombinedInterceptorAndDecoratorStackMethodHandler.java:68)
[INFO]  at com.example.application.PersistenceService$Proxy$_$$_WeldSubclass.columnSpecifiedMoreThanOnce(Unknown Source)
[INFO]  at com.example.application.PersistenceService$Proxy$_$$_WeldClientProxy.columnSpecifiedMoreThanOnce(Unknown Source)
[INFO]  at jdk.internal.reflect.GeneratedMethodAccessor1238.invoke(Unknown Source)
[INFO]  at java.base/java.lang.reflect.Method.invoke(Method.java:568)
[INFO]  at org.jboss.resteasy.core.MethodInjectorImpl.invoke(MethodInjectorImpl.java:170)
[INFO]  at [internal classes]
```

### /findOrDeleteBeforePersist

Appears to work outside of Jakarta Data


### /modifyEntity

Appears to work outside of Jakarta Data

### /listOrder

http://localhost:9080/db/crew/listOrder

On Db2 + EclipseLink this doesn't always return the correct order. Seems to work fine with Postgres and Db2 + Hibernate

### /uuid

http://localhost:9080/db/crew/uuid

On Postgres, the UUID is sent as a VARCHAR instead of a UUID, which is rejected.

```java
[INFO] Internal Exception: org.postgresql.util.PSQLException: ERROR: operator does not exist: character varying = uuid
[INFO]   Hint: No operator matches the given name and argument types. You might need to add explicit type casts.
[INFO]   Position: 50
[INFO] Error Code: 0
[INFO] Call: SELECT ID, PURCHASE FROM PURCHASEORDER WHERE (ID = ?)
```

### /entityExists

http://localhost:9080/db/crew/entityExists

On Postgres, when inserting an Entity with the same Id, instead of `jakarta.persistence.EntityExistsException` we get `jakarta.persistence.PersistenceException`.

```java
jakarta.persistence.PersistenceException: Exception [EclipseLink-4002] (Eclipse Persistence Services - 4.0.2.v202306161219): org.eclipse.persistence.exceptions.DatabaseException
Internal Exception: org.postgresql.util.PSQLException: ERROR: duplicate key value violates unique constraint "packages6_pkey"
  Detail: Key (id)=(70071) already exists.
  ```