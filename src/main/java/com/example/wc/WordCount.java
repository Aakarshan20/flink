package com.example.wc;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;
import org.apache.log4j.BasicConfigurator;

import javax.annotation.Resource;

/**
 * @author User
 * 批處理word count
 */

public class WordCount {


    public static void main(String[] args) throws  Exception{
        BasicConfigurator.configure();
        // 創建執行環境
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        // 從文件中讀取數據
        String inputPath = "D:\\flink\\src\\main\\resources\\hello.txt";

        // DataSource<String> stringDataSource =  env.readTextFile(inputPath);
        DataSet<String> inputDataSet =  env.readTextFile(inputPath);

        // 對數據集進行處理 按分詞展開 轉換繩(word, 1)的二元組進行統計
        DataSet<Tuple2<String, Integer>> resultSet =
                inputDataSet.flatMap( new MyFlatMapper())
                            .groupBy(0) //按照第一個位置的word分組
                            .sum(1);//第二個位置上的數據求和
        resultSet.print();


    }

    /**
     * 自訂義類實現FlatMapFunction interface
      */
    public static class MyFlatMapper implements FlatMapFunction<String, Tuple2<String, Integer>> {

        @Override
        public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) throws Exception {
            // 按空格分詞
            String[] words = s.split(" ");
            // 遍歷所有words 包成tuple輸出
            for(String word: words){
                collector.collect(new Tuple2<String, Integer>(word, 1));
            }
        }
    }
}
