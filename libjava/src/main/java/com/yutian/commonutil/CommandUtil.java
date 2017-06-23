package com.yutian.utillib.CommonUtil;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * Created by wuwenchuan on 2017/3/14.
 */
public class CommandUtil {

    public static String[] runCmdScript(String ... commnads) {
        String[] results = new String[3];

        try {
            final Process mProcess = Runtime.getRuntime().exec("cmd");

            DataOutputStream os = new DataOutputStream(mProcess.getOutputStream());
            for (String command : commnads) {
                os.write(command.getBytes());
                os.writeBytes("\n");
                os.flush();
            }
            os.writeBytes("exit\n");
            os.flush();

            final StringBuilder sbread = new StringBuilder();
            Thread tout = new Thread(new Runnable() {
                public void run() {
                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(mProcess.getInputStream(), Charset.forName("GBK")),
                            8192);
                    String ls_1;
                    try {
                        while ((ls_1 = bufferedReader.readLine()) != null) {
                            System.out.println(ls_1);
                            sbread.append(ls_1).append("\n");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            bufferedReader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            tout.start();

            final StringBuilder sberr = new StringBuilder();
            Thread terr = new Thread(new Runnable() {
                public void run() {
                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(mProcess.getErrorStream(), Charset.forName("GBK")),
                            8192);
                    String ls_1;
                    try {
                        while ((ls_1 = bufferedReader.readLine()) != null) {
                            sberr.append(ls_1).append("\n");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            bufferedReader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            terr.start();

            int retvalue = mProcess.waitFor();
            long startTime = System.currentTimeMillis();
            long countTime = System.currentTimeMillis() - startTime;
            while (tout.isAlive() && countTime < 4500) {
                Thread.sleep(50);
                countTime = System.currentTimeMillis() - startTime;
            }
            if (terr.isAlive())
                terr.interrupt();
            results[0] = String.valueOf(retvalue);
            results[1] = sbread.toString();
            results[2] = sberr.toString();

            mProcess.destroy();

        } catch (IOException ex) {
            //Do nothing
        } catch (InterruptedException ex) {
            //Do nothing
        }

        return results;

    }
}
