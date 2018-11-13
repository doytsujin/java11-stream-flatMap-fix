import org.junit.Test;

import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by mtumilowicz on 2018-11-13.
 */
public class FlatMapLaziness {
    
    @Test
    public void flatMap_laziness_unlimitedStream() {
        Stream.of(1)
                .flatMap(x -> Stream.generate(Math::random))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    @Test
    public void flatMap_laziness_limitedStream() {
        Integer d = Stream.of(1)
                .flatMap(x -> Stream.iterate(0, i -> ++i).peek(System.out::println).limit(10))
                .findFirst()
                .orElseThrow(IllegalStateException::new);

        assertThat(d, is(0));
    }
}
