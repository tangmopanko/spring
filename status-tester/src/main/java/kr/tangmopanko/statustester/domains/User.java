package kr.tangmopanko.statustester.domains;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class User {

    private long id;
    private String name;
    private int age;
    private double salary;

}
