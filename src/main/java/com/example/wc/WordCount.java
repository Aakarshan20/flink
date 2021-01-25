package com.example.wc;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;

/**
 * @author User
 * 批處理word count
 */
public class WordCount {
    public static void main(String[] args) {
        // 創建執行環境
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        // 從文件中讀取數據
        String inputPath = "D:\\flink\\src\\main\\resources\\hello.txt";

        //DataSource<String> stringDataSource =  env.readTextFile(inputPath);
        DataSet<String> inputDataSet =  env.readTextFile(inputPath);

        // 對數據集進行處理
    }
}
