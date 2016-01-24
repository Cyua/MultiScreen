package zju.mobile;

/**
 * Created by Cyua on 1/24/16.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutput;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;


public class MySender {
    Socket socket = null;
    OutputStream ou;
    String buffer = "";
    boolean findServer=false;
    boolean conn_finish=false;


    private static MySender sInstance;

    private MySender() {
    }

    public static synchronized MySender getInstance() {
        if (sInstance == null) {
            sInstance = new MySender();
        }
        return sInstance;
    }

    public boolean connect(){
        new MyThread().start();
        while (!conn_finish) {}
        System.out.println("****************** findServer = " + findServer + " **********************");
        return findServer;
    }

    public void sendData(String str){
        try {
            System.out.println("*********** Socket = "+socket);
            System.out.println("############# Socket Closed = " + socket.isClosed() + " ####################");
            //OutputStream ou = socket.getOutputStream();
            System.out.println(" *********** 2 here *****************");
            ou.write((str+"\n").getBytes("gbk"));
            ou.flush();
        } catch (IOException e){
            System.out.println("************* No Socket Connetion ***************");
            e.printStackTrace();
        }
    }



    class MyThread extends Thread {

        @Override
        public void run() {
            for (int i=100;i<=105;i++) {
                //定义消息
                if (findServer) break;
                Message msg = new Message();
                msg.what = 0x11;
                Bundle bundle = new Bundle();
                bundle.clear();
                try {
                    //连接服务器 并设置连接超时为5秒
                    System.out.println("############################## Run "+i+" ##########################");
                    socket = new Socket();
                    socket.connect(new InetSocketAddress("192.168.1." + i, 10000), 1000);
                    //获取输入输出流
                    System.out.println("###################### Connect Success "+i+" #################");

                    BufferedReader bff = new BufferedReader(new InputStreamReader(
                            socket.getInputStream()));
                    //读取发来服务器信息
                    String line = null;
                    buffer = "";
                    //System.out.println("*************** here 1 **************");
                    long startTime=System.currentTimeMillis(),now;
                    while ((line = bff.readLine()) != null) {
                        buffer = line + buffer;
                        now=System.currentTimeMillis();
                        if (now-startTime>1000){
                            System.out.println("*********** Can't receive accept ***************");
                            continue;
                        }
                    }
                    //System.out.println("************** here 2******************");
                    System.out.println("######### buffer = " + buffer);

                    ou = socket.getOutputStream();
                    //ou = socket.getOutputStream();
                    System.out.println("**************** yyy *******************");
                    if (buffer.equals("accept")) {
                        findServer=true;
                        System.out.println("*********** Find Correct Server ****************");
                        //向服务器发送信息
                        ou.write("I'm android client\n".getBytes("gbk"));
                        ou.flush();
                        //bundle.putString("msg", buffer.toString());
                        //msg.setData(bundle);
                        //发送消息 修改UI线程中的组件
                        //myHandler.sendMessage(msg);
                    }
                    else {
                        System.out.println("********** Not correct Server *************");
                        //bundle.putString("msg", "192.168.1."+i+"不是对应的服务器！尝试下一个ip");
                        //msg.setData(bundle);
                        //myHandler.sendMessage(msg);
                    }

                    //关闭各种输入输出流



                    //socket.shutdownOutput();
                    //ou=socket.getOutputStream();
                    //System.out.println("*********** pos 3 *******************");
                    //ou.write("~~~byebye3~~~".getBytes("gbk"));
                    //ou.flush();

                    //ou.close();

                    //ou=socket.getOutputStream();
                    //socket.close();
                    if (findServer) {
                        System.out.println("************** finish ***************");
                        break;
                    }
                    else System.out.println("***************** not finish ************");
                } catch (SocketTimeoutException aa) {
                    //连接超时 在UI界面显示消息
                    System.out.println("192.168.1."+i+"服务器连接失败！尝试下一个ip");
                    //bundle.putString("msg", "192.168.1."+i+"服务器连接失败！尝试下一个ip");
                    //msg.setData(bundle);
                    //发送消息 修改UI线程中的组件
                    //myHandler.sendMessage(msg);
                } catch (IOException e) {
                    System.out.println("************* get an exception ***************");
                    e.printStackTrace();

                    //bundle.putString("msg", "192.168.1."+i+"不是对应的服务器！尝试下一个ip");
                    //msg.setData(bundle);
                    //myHandler.sendMessage(msg);
                }
            }
            conn_finish=true;
        }
    }


}