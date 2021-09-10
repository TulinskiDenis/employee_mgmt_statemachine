Docker compose command to build images and start container:
docker-compose -f docker-compose.yaml up --build


Api description swagger url: http://localhost:8000/swagger-ui/

Statemachine h2 database: http://localhost:8000/db-statemachine
url:   jdbc:h2:mem:testdb

Employees table h2 database: http://localhost:9000/db-employees
url:   jdbc:h2:mem:testdb

Add employee request exaample:
POST http://localhost:8000/api/v1/employees
Body:
{
    "id": 1,
    "name": "A",
    "age": 33
}

Update state request example:
http://localhost:8000/api/v1/employees/1/CHECK
