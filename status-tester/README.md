### test를 위한 dummy spring-boot server  


#### docker env 
- https://aggarwal-rohan17.medium.com/docker-build-arguments-and-environment-variables-1bdca0c0ef92 

#### helm chart(environment variables)
- https://phoenixnap.com/kb/helm-environment-variables


#### readness, liveness 
- https://github.com/eugenp/tutorials/blob/master/spring-boot-modules/spring-boot-actuator/README.md


#### docker image 최적화 
- https://velog.io/@yyong3519/SpringBoot-%EB%8F%84%EC%BB%A4-%EC%9D%B4%EB%AF%B8%EC%A7%80-%EB%A7%8C%EB%93%A4%EA%B8%B0-%EC%B5%9C%EC%A0%81%ED%99%94 

#### spring boot
- https://godekdls.github.io/Spring%20Boot/spring-boot-actuator/
- 
- https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/htmlsingle/
```
# build
docker build -t tangmopanko/status-tester:0.1.0 . 
docker run status-tester:0.1.0  

docker login
docker push tangmopanko/status-tester:0.1.0

# test 
curl -v -XGET -H 'Content-Type/application' localhost:8080/api/ok
curl -v -XGET -H 'Content-Type/application' localhost:8080/api/unauthroized
curl -v -XGET -H 'Content-Type/application' localhost:8080/api/internalerror

#helm deply
cd helm 
h delete status-tester -n demo 
h install status-tester . -n demo 
```