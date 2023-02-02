package cvs;

import java.io.*;

/**
 * @author heshan
 */
public class Main {
    static int batch = 24;

    public static void main(String[] args) throws IOException {
        File file = new File("/Users/heshan/Downloads/项目工时投入/batch" + batch + "/input.csv");
        String sql = readCvs(file);
        System.out.println(sql);
    }

    static String readCvs(File cvsFile) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(cvsFile)));

        boolean skipHeader = true;

        String sqlHeader = "insert into tengyunwork.t_rpt_project_stage (rdpm, project_status, project_name, created, initiate, list, stop, suspend, batch) values" + System.lineSeparator();

        StringBuilder valuesClause = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            if (skipHeader) {
                skipHeader = false;
                continue;
            }

            String[] columns = line.split(",", -1);
            StringBuilder valueClause = new StringBuilder();
            valueClause.append("(");
            for (String column : columns) {
                String c = null;
                if (column != null) {
                    c = column.trim();
                    if ("".equals(c)) {
                        c = null;
                    }
                }
                if (c == null) {
                    valueClause
                            .append(c)
                            .append(",");
                } else {
                    valueClause
                            .append("'")
                            .append(c)
                            .append("'")
                            .append(",");
                }
            }
            valueClause.deleteCharAt(valueClause.lastIndexOf(","))
                    .append(", " + batch + "),")
                    .append(System.lineSeparator());
            valuesClause.append(valueClause);
        }

        reader.close();

        valuesClause.deleteCharAt(valuesClause.lastIndexOf(System.lineSeparator()))
                .deleteCharAt(valuesClause.lastIndexOf(","))
                .append(";");

        return sqlHeader + valuesClause;
    }
}
