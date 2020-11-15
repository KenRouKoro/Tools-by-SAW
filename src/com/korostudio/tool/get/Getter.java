package com.korostudio.tool.get;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Getter {
    protected Scanner scan ;
    protected String name;
    protected JSONArray array;
    public void run(){
        scan = new Scanner(System.in);
        scan.useDelimiter("\n");
        while (true){
            array=new JSONArray();
            System.out.println("输入位置：");
            String first=scan.next();
            File basefile=new File(first);
            if(!basefile.isDirectory()){
                System.out.println("输入目录有误！");
                continue;
            }
            System.out.println("请输入基础名（带连接符）");
            name=scan.next();
            File files[]=basefile.listFiles();
            int i=0;
            for(File file:files){
                if (!file.isFile()){
                    continue;
                }
                JSONObject object;
                object=new JSONObject();
                object.put("name",name+i);
                object.put("file",file.getName());
                array.add(object);
                i++;
            }
            String json=array.toJSONString();
            System.out.println("JSON文件如下:"+json);
            try {
                FileUtils.write(new File(first+"/map.json"), json,"UTF-8",false);
                System.out.println("写入map.json完成!");
            } catch (IOException e) {
                e.printStackTrace();
            }
            //******************************************************************************************
            System.out.println("对应表：");
            String map=new String();
            for (Object obj: array.toArray()){
                JSONObject jsonObject =(JSONObject)obj;
                System.out.println(jsonObject.get("name")+"---->"+jsonObject.get("file"));
                map=map+jsonObject.get("name")+"---->"+jsonObject.get("file")+"\n";
            }
            try {
                FileUtils.write(new File(first+"/map.txt"), map,"UTF-8",false);
                System.out.println("对照表写入完成!");
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("是否继续？ (Y/N)");
            String next=scan.next();
            if(next.equalsIgnoreCase("n"))continue;
        }
    }
}
