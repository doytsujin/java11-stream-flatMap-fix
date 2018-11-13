# java11-stream-flatMap-fix
_Reference_: https://blog.jooq.org/2017/07/03/are-java-8-streams-truly-lazy-not-completely/  
_Reference_: https://stackoverflow.com/questions/46288915/is-flatmap-guaranteed-to-be-lazy#comment79550832_46288915  
_Reference_: https://bugs.openjdk.java.net/browse/JDK-8075939

# project description
We provide two tests in `FlatMapLaziness`:
* flat map produces unlimited stream
    ```
    @Test
    public void flatMap_laziness_unlimitedStream() {
        Stream.of(1)
                .flatMap(x -> Stream.generate(Math::random))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }    
    ```
* flat map produces limited stream
    ```
    @Test
    public void flatMap_laziness_limitedStream() {
        Integer d = Stream.of(1)
                .flatMap(x -> Stream.iterate(0, i -> ++i).peek(System.out::println).limit(10))
                .findFirst()
                .orElseThrow(IllegalStateException::new);

        assertThat(d, is(0));
    }    
    ```
And when using different JDKs, we get different results:
* **openjdk 8**:
    * `flatMap_laziness_unlimitedStream` never ends
    * `flatMap_laziness_limitedStream` prints (`peek(System.out::println)`) 
    numbers from 0..9
* **openjdk 11**:
    * `flatMap_laziness_unlimitedStream` ends after first generated 
    number
    * `flatMap_laziness_limitedStream` prints (`peek(System.out::println)`) 
    only 0