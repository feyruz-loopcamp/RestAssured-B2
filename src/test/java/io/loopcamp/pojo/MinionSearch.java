package io.loopcamp.pojo;

import lombok.Data;

import java.util.List;

@Data
public class MinionSearch {

    private List <Minion> content;
    private int totalElement;

}
