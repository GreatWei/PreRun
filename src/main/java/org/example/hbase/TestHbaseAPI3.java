package org.example.hbase;


import java.io.IOException;

/**
 * 测试HBase API
 */
public class TestHbaseAPI3 {

    public static void main(String[] args) throws IOException {

        //创建连接对象
        HBaseUtil.makeHBaseConnection("localhost", "2181");

        HBaseUtil.insertData("ross:student", "1003", "info", "name", "jinqi");

        //关闭连接
        HBaseUtil.close();
    }
}
