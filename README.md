# Trade Tracker Service

Trade Tracker is a trade market management application. 

You can:
 - Add/Delete stocks
 - Add/Delete stock exchanges
 - Update prices of stocks
 
## Usage
After you fetch the repository, use docker-compose to build and run in your local.

``` dockerfile
docker-compose build
```

After build command finished, run the command given below to run Postgres and Trade Tracker application:
``` dockerfile
docker-compose up
```

The project will be accessible on [localhost:8080](http://localhost:8080)

Here is the Swagger url to access the APIs: [swagger-ui](http://localhost:8080/swagger-ui/index.html)

Additionally, if postman is available in your local environment, you can use ready to use postman requests via [postman workspace](https://elements.getpostman.com/redirect?entityId=26186123-13e17866-feb4-4147-bd49-8fb4e3f22aca&entityType=collection)
