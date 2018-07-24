package com.a3xh1.basecore;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    /**
     * 执行shell命令
     */
    @Test
    public void test() {
        Runtime run = Runtime.getRuntime();
        File wd = new File("/Users/chenwenhui/AndroidStudioProjects/3xh1/jyk");
        System.out.println(wd);
        Process proc = null;
        try {
            proc = run.exec("/bin/bash", null, wd);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (proc != null) {
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(proc.getOutputStream())), true);
            out.println("cd /Users/chenwenhui/AndroidStudioProjects/3xh1/jyk/mode/src/main/res/layout");
            out.println("pwd");
            out.println("file_name_tmp= 'ls'");
            out.println("file_names=($(echo $file_name_tmp));");
//            out.println("rm -fr /home/proxy.log");
            out.println("exit");
            try {
                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println(line);
                }
                proc.waitFor();
                in.close();
                out.close();
                proc.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 给子文件添加前缀
     */
    @Test
    public void addPrefix2Subfiles() {
        Runtime run = Runtime.getRuntime();
        File wd = new File("/Users/chenwenhui/AndroidStudioProjects/3xh1/jyk");
        System.out.println(wd);
        String[] var = {"folderName=/Users/chenwenhui/Desktop/久怡康切图", "prefix=a_"};               //参数
        Process proc = null;
        String cmd = "sh /Users/chenwenhui/Desktop/test";
        try {
            proc = run.exec(cmd, var, wd);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (proc != null) {
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            try {
                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println(line);
                }
                proc.waitFor();
                in.close();
                proc.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}