package kr.tangmopanko.springbatchold.utils;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class faker {}
 /**    
    public faker() {
        
    }
    
    public List<ItemData> dummyDatas() {
        Faker faker = new Faker();
        List<ItemData> results = new ArrayList<>();

        for(int idx = 0 ; idx < 20 ; idx++) {
            results.add(new ItemData(faker.name().fullName(), faker.code().isbnGs1())); 
        }

        return results; 

    }

    
}

@Setter
@Getter
@AllArgsConstructor
public record ItemData {
    private String name; 
    private String code;
}
*/