package java;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class tengyun_slow_neo4j_cypher_analy {
    public static DateTimeFormatter timestampDtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    private static LocalDateTime startTimestamp = LocalDateTime.parse("2020-05-28 10:00:00.000", timestampDtf);
    private static LocalDateTime stopTimestamp = LocalDateTime.parse("2020-05-28 19:35:00.000", timestampDtf);
    private static long lowElapsed = 0;
    private static long highElapsed = 99999999L;

    public static void main(String[] args) throws Exception {
        int topN = 3000;
        boolean isNeo4j = false;

        Pattern headPattern = isNeo4j ?
                Pattern.compile("^(?<timestamp>\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}.\\d{3}) .+Neo4jDriverProvider {5}: Elapsed: (?<elapsed>\\d+) \\[")
                : Pattern.compile("^(?<timestamp>\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}.\\d{3}) .+SqlExeInterceptor +: Elapsed: (?<elapsed>\\d+) \\[");
        Pattern tailPattern = Pattern.compile("^\\d{4}-\\d{2}-\\d{2} ");

        Path rootPath = Paths.get("/Users/heshan/Downloads/17_error_log");
        Path analysisPath = Paths.get(rootPath + "/analysis");
        if (Files.exists(analysisPath)) {
            Files.list(analysisPath).forEach(toRemove -> {
                try {
                    Files.deleteIfExists(toRemove);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        Files.deleteIfExists(analysisPath);
        Files.createDirectory(analysisPath);

        Files.list(rootPath).filter(e -> !Files.isDirectory(e)).forEach(errorLogPath -> {
            try (Stream<String> stream = Files.lines(errorLogPath, StandardCharsets.ISO_8859_1)) {

                List<Item> itemList = new ArrayList<>();

                boolean[] print = {false};

                Item[] item = {null};
                StringBuilder[] cypherBuilder = {null};
                boolean[] headMatched = {false};


                stream.forEach(line -> {
                    try {
                        boolean tempPrint = print[0] && !tailPattern.matcher(line).find();
                        if (print[0] && (!tempPrint)) { // 匹配结束
                            item[0].setCypher(cypherBuilder[0].toString());
                            itemList.add(item[0]);
                            item[0] = null;
                        }
                        print[0] = tempPrint;


                        Matcher headMatcher = headPattern.matcher(line);
                        tempPrint = headMatcher.find();
                        if ((!print[0]) && tempPrint) { // 匹配开始
                            item[0] = new Item();
                            cypherBuilder[0] = new StringBuilder(System.lineSeparator());
                            headMatched[0] = true;
                        }
                        print[0] = print[0] || tempPrint;

                        if (print[0]) {
                            if (headMatched[0]) {
                                item[0].setTimestamp(LocalDateTime.parse(headMatcher.group("timestamp"), timestampDtf));
                                item[0].setElapsed(Long.parseLong(headMatcher.group("elapsed")));
                                headMatched[0] = false;
                            } else {
                                cypherBuilder[0].append(line).append(System.lineSeparator());
                            }

                        }
                    } catch (Throwable t) {
                        System.out.println(t);
                    }

                });

                List<Item> result = itemList.stream()
                        .filter(it -> tengyun_slow_neo4j_cypher_analy.checkTimestampRange(it.getTimestamp()))
                        .filter(it -> tengyun_slow_neo4j_cypher_analy.checkElapesdRange(it.getElapsed()))
                        .sorted(Comparator.comparingLong(Item::getElapsed).reversed())
                        .limit(topN)
                        .collect(Collectors.toList());

                String fileName = errorLogPath.getFileName().toString();
                Path outputFilePath = Paths.get(analysisPath + "/analysis_" + (isNeo4j ? "neo4j" : "sql") + "_" + fileName);
                Files.deleteIfExists(outputFilePath);
                Files.createFile(outputFilePath);

                try (BufferedWriter writer = Files.newBufferedWriter(outputFilePath, StandardCharsets.ISO_8859_1))
                {
                    writer.write("From log file: " + fileName);
                    writer.write(System.lineSeparator());
                    writer.write(System.lineSeparator());

                    result.forEach(it -> {
                        try {
//                            System.out.println(it.getElapsed());
                            writer.write(it.toString());
                            writer.write(System.lineSeparator());
                            writer.write("========================================================");
                            writer.write(System.lineSeparator());
                            writer.write(System.lineSeparator());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static boolean checkTimestampRange(LocalDateTime timestamp) {
        return (startTimestamp.isBefore(timestamp) || startTimestamp.isEqual(timestamp))
                && (stopTimestamp.isAfter(timestamp) || stopTimestamp.isEqual(timestamp));
    }

    private static boolean checkElapesdRange(long elapsed) {
        return lowElapsed <= elapsed && highElapsed >= elapsed;
    }
}

class Item {
    private LocalDateTime timestamp;
    private String cypher;
    private long elapsed;

    public Item() {
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getCypher() {
        return cypher;
    }

    public void setCypher(String cypher) {
        this.cypher = cypher;
    }

    public long getElapsed() {
        return elapsed;
    }

    public void setElapsed(long elapsed) {
        this.elapsed = elapsed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return cypher.equals(item.cypher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cypher);
    }

    @Override
    public String toString() {
        return "java.Item{" +
                "timestamp=" + timestamp.format(tengyun_slow_neo4j_cypher_analy.timestampDtf) +
                ", cypher='" + cypher + '\'' +
                ", elapsed=" + elapsed +
                '}';
    }
}

