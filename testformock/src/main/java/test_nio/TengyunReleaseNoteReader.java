package test_nio;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TengyunReleaseNoteReader {
    public static void main(String[] args) throws IOException, ParseException {
        Path path = Paths.get("/Users/heshan/Downloads/Tengyun-release-note.txt");

        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

        List<String> insertSql = new ArrayList<>();

        String createTime = getCreateTime();

        while(!lines.isEmpty()) {
            insertSql.add(parseOneReleaseNoteToSql(lines, createTime, "何山", "102504"));
        }

        insertSql.forEach(System.out::println);
    }

    private static String parseOneReleaseNoteToSql(List<String> lines, String createdOn, String createdBy, String createdById) throws ParseException {
        StringBuilder sql = new StringBuilder();

        String version = "'" + readVersion(lines) + "', ";

        String date = "'" + readDate(lines) + "', ";

        String content = "'" + readContent(lines) + "', ";

        String createTime = "'" + createdOn + "', ";

        String creator = "'" + createdBy + "', ";

        String creatorId = "'" + createdById + "'";

        return "insert into tengyunwork.t_release_note (date, version, content, createtime, creator, creator_id) value (" + date + version + content + createTime + creator + creatorId + ");" + System.lineSeparator();
    }

    private static String getCreateTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(new Date());
    }

    private static void skipEmptyLines(List<String> lines) {
        while (!lines.isEmpty() && (lines.get(0)).isBlank()) {
            lines.remove(0);
        }
    }

    private static String readDate(List<String> lines) throws ParseException {
        skipEmptyLines(lines);
        Date date = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA).parse(lines.remove(0).trim());
        return new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(date);
    }

    private static String readVersion(List<String> lines) {
        skipEmptyLines(lines);
        return lines.remove(0).trim();
    }

    private static String readContent(List<String> lines) {
        StringBuilder content = new StringBuilder();

        while (!lines.isEmpty() && !isVersionLine(lines.get(0))) {
            content.append(lines.remove(0))
                    .append(System.lineSeparator());
        }
        return content.toString().trim();
    }

    private static boolean isVersionLine(String line) {
        return line.contains("Tengyun");
    }
}