# gwt-fileupload

```
./mvnw spring-boot:run -Penv-dev -pl *-server -am 
```

```
./mvnw gwt:codeserver -pl *-client -am
./mvnw gwt:codeserver -pl *-client -am -nsu 

```


```
curl -i -X POST -F file=@ch.so.afu.abbaustellen.xtf http://localhost:8080/rest/jobs

curl -i -X GET http://localhost:8080/rest/jobs/4d4aa583-6575-4200-a39c-621a5190d36d
```