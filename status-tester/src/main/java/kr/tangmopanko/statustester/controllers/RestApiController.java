package kr.tangmopanko.statustester.controllers;

import kr.tangmopanko.statustester.domains.User;
import kr.tangmopanko.statustester.utils.CustomError;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RestApiController {

    /**
     * test - apis
     * get api/user/200 -> http crated
     * get api/user/!200 -> User with id not found :  http not found
     * get api/users -> find All Users :  http ok
     * get api/login -> http unauthroized
     * get api/internal -> http is internal server error
     * post api/user -> name (hyeongmin) : Unable to create. A User with name already exist : http conflict
     */

    public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

    @GetMapping(value = {"/user/{id}", "/users"} )
    public ResponseEntity<?> getUser(@PathVariable(required = false) Optional<Long> id) {
        if (id.isPresent()) {
            long _id = id.get();
            logger.info("Fetching User with id {}", _id);

            if(_id == 200) {
                User user = User.builder().age(43).name("hyeongmin").id(id.get()).salary(130050).build();
                return new ResponseEntity<User>(user, HttpStatus.CREATED);
            }

            return new ResponseEntity(new CustomError("User with id " + _id + " not found"), HttpStatus.NOT_FOUND);

        }

        logger.info("Fetching All Users");
        return new ResponseEntity<String>("find All Users", HttpStatus.OK);
    }

    // throw Exception : CONFLICT
    @PostMapping(value = "/user")
    public ResponseEntity<?> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
        logger.info("Creating User : {}", user);
        if (user.getName().equals("hyeongmin")) {
            logger.error("Unable to create. A User with name {} already exist", user.getName());
            return new ResponseEntity(new CustomError("Unable to create. A User with name " +
                    user.getName() + " already exist."), HttpStatus.CONFLICT);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    @GetMapping(value = "/login")
    public ResponseEntity<?> unauthorized() {
        return new ResponseEntity<>(new CustomError("is unauthorized test"), HttpStatus.UNAUTHORIZED);
    }

    @GetMapping(value = "/internal")
    public ResponseEntity<?> internal() {
        return new ResponseEntity<>(new CustomError("is internal server error test"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
