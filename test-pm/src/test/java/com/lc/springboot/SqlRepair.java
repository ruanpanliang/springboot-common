package com.lc.springboot;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** PDMAN sql 修复 mysql版本 */
public class SqlRepair {

  /** UTF-8编码 */
  private static final String UTF8 = "UTF-8";

  private static List<String> lineStart =
      Arrays.asList("ALTER TABLE", "DROP INDEX", "DROP TABLE", "ALTER TABLE");

  public static void main(String[] args) throws IOException {
    File sqlFile = new File("/Users/lc/xxxx.sql");

    if (!sqlFile.exists()) {
      throw new FileNotFoundException();
    }

    String parent = sqlFile.getParent();
    System.out.println(parent);

    List<String> resultList = new ArrayList<>();

    // 先清空之前的数据
    resultList.clear();

    String fileName = sqlFile.getName();

    List<String> lines = FileUtils.readLines(sqlFile, UTF8);

    for (String line : lines) {
      if (isMatch(line)) {
        continue;
      }

      resultList.add(line);
    }

    List<String> repairList = new ArrayList<>();
    // 清除建表语句中不要的行
    for (String line : resultList) {
      // 对于create_time 和 last_mod_time 的数据需要进行更新
      if (line.toLowerCase().contains("created_time") //
          || line.toLowerCase().contains("answer_time") //
          || line.toLowerCase().contains("top_create_time") //
      ) {
        line = line.replace("COMMENT", "DEFAULT current_timestamp COMMENT");
      } else if (line.toLowerCase().contains("last_mod_time") //
          || line.toLowerCase().contains("updated_time")) {
        line =
            line.replace(
                "COMMENT", "DEFAULT current_timestamp on update current_timestamp COMMENT");
      } else if (line.toLowerCase().contains("open_date")) {
        line = line.replace("COMMENT", "DEFAULT (current_date) COMMENT");
      } else if (line.startsWith(") COMMENT")) {
        line =
            line.replace(" '", "'").replace(";", "") + " ENGINE = InnoDB DEFAULT CHARSET=utf8mb4;";
      }

      repairList.add(line);
    }

    String outPutFileName = fileName.split("\\.")[0] + "_repair.sql";
    FileUtils.writeLines(new File(parent + "/" + outPutFileName), UTF8, repairList);

    repairList.clear();
  }

  /**
   * 行开头是否匹配
   *
   * @param lineStr
   * @return
   */
  private static boolean isMatch(String lineStr) {
    if (StringUtils.isBlank(lineStr)) {
      return true;
    }
    for (String oneLineStart : lineStart) {
      if (lineStr.startsWith(oneLineStart)) {
        return true;
      }
    }

    return false;
  }

  public static void cmd(String cmd) {
    Process process = null;
    String command = "/bin/sh -c " + cmd;

    List<String> processList = new ArrayList<String>();

    try {
      process = Runtime.getRuntime().exec(command);
      BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
      String line = "";
      while ((line = input.readLine()) != null) {
        processList.add(line);
      }
      input.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    for (String line : processList) {
      System.out.println(line);
    }
  }
}
