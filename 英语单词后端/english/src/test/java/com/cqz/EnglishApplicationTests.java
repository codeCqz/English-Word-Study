package com.cqz;


import com.cqz.pojo.Word;
import com.cqz.service.SortService;
import com.cqz.service.WordService;
import com.csvreader.CsvReader;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
class EnglishApplicationTests {

    @Autowired
    private WordService wordService;
    @Autowired
    private SortService sortService;

    @Test
    void contextLoads() {
    }

    @Test
    void test1() throws IOException {
        File file = new File("E:\\data\\gaoZHONG.txt");// Text文件
        BufferedReader br = new BufferedReader(new FileReader(file));// 构造一个BufferedReader类来读取文件
        String s = null;
        while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
            sortService.set(s);
//            System.out.println("执行");
            System.out.println(s);
        }
        br.close();
    }

    @Test
    public void test2(){
        String c = "n.名字,名称,姓名,名誉;vt.命名,提名,叫出,指定;adj.姓名的,据以取名的";
        String[] arr = c.split(";");
        for (int i = 0 ; i < arr.length;i++) {
            System.out.println(arr[i]+"\n");
        }
    }

    @Test
    public void test3(){
        List<Word> list  = wordService.findAll();
        int result1 = -2;
        for (Word l:list
             ) {
            if (l.getTranslation() != null) {
                String c = l.getTranslation();
                if (c.indexOf(";") == -1 && c != null) {
                    String part = "vt";
//                c=update(result1,c,part);
//                part = "vi";
//                c=update(result1,c,part);
                    part = "adv";
                    c = update(result1, c, part);
                    part = "adj";
                    c = update(result1, c, part);
//                part = "v";
//                c=update(result1,c,part);
//                part = "n";
//                c=update(result1,c,part);
                    part = "prep";
                    c = update(result1, c, part);
                }
                System.out.println(c);
                wordService.setWord(c,l.getWord());
            }

        }
    }
    public String update(int result1,String c,String part){
        result1 = c.indexOf(part);
        String str1 = c;
        if(result1 >= 6 && c.indexOf(';')!=result1-1){
            StringBuilder sb = new StringBuilder(c);//构造一个StringBuilder对象
            sb.insert(result1, ";");//在指定的位置1，插入指定的字符串
            str1 = sb.toString();
//            System.out.println(str1+"@@"+part);
        }
        return str1;
    }
    @Test
    public void test4() throws IOException {
        File file = new File("E:\\data\\word.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(file,true));
        List<Word> list  = wordService.findAll();
        for (Word l:list
        ) {
            writer.append(l.getWord()+"\n");
        }
        writer.close();
    }
    @Test
    public void test6(){
        String fileName = "E:\\毕业之项目\\english\\src\\test\\java\\com\\cqz\\words.csv";
        read(fileName);
    }


//    @Test
//    public void test7(){
//        List<Word> l = new ArrayList<Word>();
//        l = wordService.findSortWord(1,0,9);
//
//        System.out.println(l.toString());
//    }


    public void read(String filePath){

        try {

            CsvReader csvReader = new CsvReader(filePath,',', Charset.forName("UTF-8"));


            csvReader.readHeaders();
            while (csvReader.readRecord()){
                // 读一整行
                String word = csvReader.get(0);
                String translation = csvReader.get(1);
                if (translation!=""){
                    Word w1 = new Word(word,translation);
                    Word w2 = wordService.findWord(word);
                    if(w2==null){
                        wordService.insert(w1);
//                        System.out.println("插入");
                    }
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void test09() {
        String way = "self";
        int userid = 1;
        System.out.println(way + "------" + userid);
        List<Word> l = new ArrayList<Word>();
        if ("self".equals(way)) {
            System.out.println("执行");
            l = wordService.getData(userid);
            for (Word word : l) {
                System.out.println(word.toString());;
            }
        }
    }

//    @Test
//    public void test10(){
//        QiNiu qiNiu = new QiNiu();
//        String accessKey = "bFQvYlrf08oYWOlZw_3D8";
//        String secretKey = "bFQvYlrf08oYWOlZw_3D8r1Rob7f9QkKGBIArL6P";
//        String bucket = "head--sculpture";
//        long expireSeconds = 600;   //过期时间
//        StringMap putPolicy = new StringMap();
//        Auth auth = Auth.create(accessKey, secretKey);
//        String upToken = auth.uploadToken(bucket,null, expireSeconds,putPolicy);
//        qiNiu.setKey(UUID.randomUUID().toString().replaceAll("\\-", ""));
//        qiNiu.setToken(upToken);
//        System.out.println(qiNiu.toString());
//    }
}