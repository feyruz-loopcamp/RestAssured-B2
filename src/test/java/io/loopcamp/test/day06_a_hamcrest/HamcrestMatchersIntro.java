package io.loopcamp.test.day06_a_hamcrest;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
public class HamcrestMatchersIntro {

    @Test
    public void numberTEst() {
        assertThat(1+3, is(4));
        assertThat(5+5, equalTo(10));
        assertThat(10+5, is(equalTo(15)));

        assertThat(100+4, is(greaterThan(100)));

        // In JUnit, the one below would be the same as above
        // assertTrue (104 > 100);

        int num = 10+2;
        assertThat(num, is(not(5)));
        assertThat(num, is(not(equalTo(5))));
    }


    @Test
    public void stringTest() {
        String word = "loopcamp";

        assertThat(word, is("loopcamp1"));
        assertThat(word, equalTo("loopcamp"));
        assertThat(word, is(equalTo("loopcamp")));


        // startsWith
        assertThat(word, startsWith("loop"));
        assertThat(word, startsWithIgnoringCase("LOOP"));


        // contains
        assertThat(word, containsString("pc"));
        assertThat(word, containsStringIgnoringCase("PC"));


        // blank String - empty String
        String str = " ";
        assertThat(str, is(blankString()));
        assertThat(str.replace(" ",  ""), is(emptyOrNullString()));
        assertThat(str.trim(), is(emptyOrNullString()));

    }


    @Test
    public void listsTest () {

        List<Integer> nums = Arrays.asList(5, 20, 1, 54, 0);
        List<String> words = Arrays.asList("java", "selenium", "git", "maven", "api");

        // size
        assertThat(nums, hasSize(5));
        assertThat(words, hasSize(5));

        // contains item
        assertThat(nums, hasItem(20));
        //assertThat(nums, hasItem(7));  // this fails since 7 is not among the items from nums list

        assertThat(words, hasItem("git"));
        assertThat(words, hasItems("git", "api"));
        // assertThat(words, hasItems("git", "db"));  // all the provided parameters have to be in the list for positive result
        assertThat(nums, containsInAnyOrder(54, 20, 0, 5, 1)); // You need to provide all the elements (same number of the total elements )


        // every element
        assertThat(nums, everyItem(greaterThanOrEqualTo(0)));
        assertThat(words, everyItem( is( not( blankString()))));

    }

}
