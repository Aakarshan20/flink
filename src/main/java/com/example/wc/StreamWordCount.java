package com.example.wc;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author User
 */
public class StreamWordCount {
    public static void main(String[] args) throws  Exception
    {

        //  流數據架構: 等數據來再一個一個輸出

        // 創建流處理執行環境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 設定併行度(不設定的話默認是4 ,因為取決於電腦核心數量)
        // env.setParallelism(8);

        //env.setParallelism(1); 也可以設為1 這樣的話只有一條thread

        // 從文件中讀取數據(有界流 開發環境測試用)
        //String inputPath = "D:\\flink\\src\\main\\resources\\hello.txt";
        //DataStream<String> inputDataStream = env.readTextFile(inputPath);

        // 用parameter tool 工具從程序啟動參數中提取配置項
        ParameterTool parameterTool = ParameterTool.fromArgs(args);
        String host = parameterTool.get("host");
        int port = parameterTool.getInt("port");



        // 真正意義上的流式數據(從socket文本流 讀取數據)
        DataStream<String> inputDataStream = env.socketTextStream(host, port);

        // 基於數據流 進行轉換計算
        SingleOutputStreamOperator<Tuple2<String, Integer>> resultStream = inputDataStream.flatMap(new WordCount.MyFlatMapper())
                .keyBy(0)
                .sum(1);
        resultStream.print();//此處並沒有執行任務

        // 啟動任務
        env.execute();
    }
}
