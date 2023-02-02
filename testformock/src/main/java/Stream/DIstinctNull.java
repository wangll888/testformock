package java.Stream;

import java.util.Objects;
import java.util.stream.Stream;

public class DIstinctNull {
    public static void main(String[] args) {
        Stream<String> s = Stream.of(null, "1", "2", "3", null, "3", "2", "1", null)
                .distinct()
                .map(e -> "123");

        s.anyMatch(obj -> false);

        s.anyMatch(obj -> false);

    }
}
