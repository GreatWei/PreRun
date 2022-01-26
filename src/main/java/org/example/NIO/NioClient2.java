package org.example.NIO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Nio聊天室客户端
 *
 * @author csp
 * @date 2021-12-09 17:03:33
 */
public class NioClient2 {
    public static void main(String[] args) {
        // 当前客户端的用户名
        String username = "lufei2";
        // 为当前客户端开辟一个线程
        ClientThread client = new ClientThread(username);
        client.start();

        // 输入输出流
        BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));

        try {
            // 循环读取键盘输入
            String readline;
            while ((readline = sin.readLine()) != null) {
                if (readline.equals("bye")) {
                    client.close();
                    System.exit(0);
                }
                // 发送消息
                client.send(username + ":" + readline);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}