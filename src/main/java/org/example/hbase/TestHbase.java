package org.example.hbase;


import org.apache.commons.net.ntp.TimeStamp;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

public class TestHbase {
    private static String zkServer = "localhost";
    private static Integer port = 9095;
    private static TableName tableName = TableName.valueOf("testflink1");
    private static final String cf = "ke";

    @Test
    public void testWH() throws IOException{
        write2HBase("test");
    }


    public static void write2HBase(String value) throws IOException {
        Configuration config = HBaseConfiguration.create();

        config.clear();
       // config.setInt("timeout",12000);
      //  config.set("zookeeper.znode.parent","/hbase");
        config.set("hbase.zookeeper.quorum","localhost");
        config.set("hbase.zookeeper.property.clientPort","2181");
        //config.set("hbase.master","localhost:16000");

        System.out.println("开始连接hbase");
        Connection connect = ConnectionFactory.createConnection(config);
        System.out.println(connect.isClosed());
        Admin admin = connect.getAdmin();
        System.out.println("连接成功");
        for (TableName tableName:admin.listTableNames())
            System.out.println(tableName.toString());
//        Table table = connect.getTable(TableName.valueOf("midas_ctr_test"));
        System.out.println("获取表数据成功");
//        for i :table.getScanner().iterator();


        if (!admin.tableExists(tableName)) {
            System.out.println("开始建表");
            admin.createTable(new HTableDescriptor(tableName).addFamily(new HColumnDescriptor(cf)));
        }
        System.out.println("建表数据成功");

        Table table = connect.getTable(tableName);
        TimeStamp ts = new TimeStamp(new Date());
        Date date = ts.getDate();
        Put put = new Put(Bytes.toBytes(date.getTime()));
        put.addColumn(Bytes.toBytes(cf), Bytes.toBytes("test"), Bytes.toBytes(value));
        table.put(put);
        table.close();
        connect.close();
    }
}
