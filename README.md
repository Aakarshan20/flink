# flink
## with java

####測試無界數據

#####windows: 
1. 下載netcat
2. 配置path
3. cmd下執行  nc -l -p 7777
4. 啟動StreamWordCount.java 監聽7777端口
5. 往內輸入字串
6. 可見到類似以下的輸出

* 8> (456,1)
* 2> (123,1)
* 7> (789,1)
* 2> (i,1)
* 4> (your,1)
* 2> (am,1)
* 2> (father,1)
* 2> (123,2)


