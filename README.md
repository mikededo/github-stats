# github-stats

## Available commands

`github-stats` runs in an interactive shell where you can run commands that fetch Github data.

Command definition:

```sh
user # Get data from a specified user.
Usage: user -n <github-user-name> -k <github-api-key> --from <from-date> --to <to-date>
    FROM_DATE: month and year in MM/yy format (starting Jan 2000)
    TO_DATE: same format as FROM_DATE, accepts until current_month - 1

team # Get data from a specified team and its sub teams.
Usage: team -n <NAME> --from <FROM_DATE> --to <TO_DATE>
    FROM_DATE: month and year in MM/yy format (starting Jan 2000)
    TO_DATE: same format as FROM_DATE, accepts until current_month - 1 
    
# You can find the available commands running
help
# and also help for each command
user help ...
team help ...
```

## Software stack
- Java 17
- Spring Boot 2.7.5
- Apache Maven 3.8.6

## Build, test and run with Maven

```sh
# compile the code
./mvnw compile

# run tests
./mvnw test

# run app
./mvnw spring-boot:run

# package to a jar
./mvnw package

# run jar
java -jar target/github-stats-*.jar
```
