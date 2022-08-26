package com.cqz.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cqz.pojo.*;
import com.cqz.service.WordService;
import com.cqz.utils.TransApi;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@ResponseBody
public class WordController {
    @Autowired
    private WordService wordService;

    private static final String APP_ID = "20211024000981058";
    private static final String SECURITY_KEY = "9Fi3fUvoLBcRYrdQOABb";



    @RequestMapping("getcollectcount")
    public int getcollectcount(@Param("userid")int userid){
        return wordService.getcollectcount(userid);
    }

    @RequestMapping("/findcollectword")
    public List<Word> findCollectWord(@Param("userid")int userid,@Param("currentPage")int currentPage) {
        List<Word> l = new ArrayList<Word>();
        int start = (currentPage-1)*9;
        int end = 9;
        l = wordService.findCollectWord(userid,start,end);

        return l;


    }


    @RequestMapping("/findsortword")
    public List<Word> findSortWord(@Param("userid")Integer userid,@Param("currentPage")int currentPage) {
        List<Word> l = new ArrayList<Word>();
        int start = (currentPage-1)*9;
        int end = 9;
        UserStudyInfo usi = wordService.getInfo(userid);
        String level = usi.getLevel();
        System.out.println(level);
        l = wordService.findSortWord(level,start,end);
        return l;

    }

    @RequestMapping("/findsortword2")
    public List<Word> findSortWord2(@Param("userid")int userid,@Param("currentPage")int currentPage) {
        List<Word> l = new ArrayList<Word>();
        int start = (currentPage-1)*9;
        int end = 9;

        l = wordService.findSortWord2(userid,start,end);

        return l;


    }


    @RequestMapping("getInfo")
    public UserStudyInfo getInfo(@RequestParam("userid")int userid){
        UserStudyInfo usi =  wordService.getInfo(userid);
        return usi;
    }

    @RequestMapping("resetstudy")
    public void  resetStudy(@RequestParam("userid")int userid){



        UserStudyInfo usi =  wordService.getInfo(userid);
        wordService.resetStudy(userid);



        wordService.deleteuserword(userid,usi.getLevel());

    }




    @RequestMapping("deleteword")
    public String deleteWord(@RequestParam("userid")int userid, @Param("word")String word){
        wordService.deleteWord(userid,word);
        return "200";
    }

    @RequestMapping("deletecollectword")
    public String deletecollectword(@RequestParam("userid")int userid, @Param("word")String word){
        wordService.deletecollectword(userid,word);
        return "200";
    }


    @RequestMapping("gettodayplan")
    public List<Word> getTodayPlan(@RequestParam("id")int userid){
        System.out.println(userid);
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        List<Word> l = new ArrayList<Word>();
        l=wordService.getTodayPlan(userid,date);

        return l;
    }





    @RequestMapping("/findmyword")
    public List<Word> findmyWord(@Param("word")String word) {

        Word word1 = wordService.findWord(word);
        List<Word> l = new ArrayList<Word>();
        l.add(word1);
        return l;


    }


    @RequestMapping("saveword")
    public String saveWord(@RequestParam("form") String words){

        JSONObject jsonObject = JSON.parseObject(words);
        String word = jsonObject.getString("word");
        String userid = jsonObject.getString("userid");
        String way = "self";
        int id = Integer.parseInt(userid);
        String code = saveWord2(word,id,way);
        return code;
    }


    public String saveWord2(String word,int id,String way){
        String[] arr = word.split(",");
        String code = "";

        for (int i = 0;i<arr.length;i++){
            Word w = wordService.getWord(id,arr[i]);
            UserStudyInfo usi = wordService.getInfo(id);
            if(w==null){
                Userword uw = new Userword();
                String time = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

                uw.setWord(arr[i]);
                uw.setUserid(id);
                uw.setStatus(0);
                uw.setDate(time);
                uw.setWay(way);
                uw.setLevel(usi.getLevel());
                wordService.saveWord(uw);
                code="200";
            }else{
                code="500";
            }

        }
        return code;
    }


    @RequestMapping("/findword")
    public List<Definition> findWord(@Param("word")String word) {
        List<Definition> l = new ArrayList<Definition>();
        Word word1 = wordService.findWord(word);
        if(word1!=null){
            l = translation(word1);
            return l;
        }else{
            return null;
        }

    }


    public List<Definition> translation(Word word){

        String c = word.getTranslation();
        List<Definition> l = new ArrayList<Definition>();
        if(c.indexOf(";")!=-1){
            String[] s = c.split(";");
            for(int i = 0 ; i< s.length;i++){
                if(s[i].indexOf(".")!=-1){
                    String part = s[i].substring(0,s[i].indexOf(".")+1);
                    String translation = s[i].substring(s[i].indexOf(".")+1);

                    if(translation.indexOf("&")!=-1){
                        String part01 = translation.substring(0,translation.indexOf(".")+1);
                        part=part+part01;

                        translation = translation.substring(translation.indexOf(".")+1);

                        l.add(new Definition(word.getWord(),part,translation));

                    }else{
                        l.add(new Definition(word.getWord(),part,translation));
                    }
                }else{
                    l.add(new Definition(word.getWord(),s[i]));
                }
            }
        }else{
            if(c.indexOf(".")!=-1) {
                String part = c.substring(0, c.indexOf(".") + 1);
                String translation = c.substring(c.indexOf(".") + 1);
                System.out.println(translation);
                if(translation.indexOf("&")!=-1) {
                    String part01 = translation.substring(0, translation.indexOf(".") + 1);
                    part = part + part01;

                    translation = translation.substring(translation.indexOf(".") + 1);


                }
                l.add(new Definition(word.getWord(), part, translation));
            }else {
                l.add(new Definition(word.getWord(), c));
            }
        }
        return l;
    }
    @RequestMapping("translate")
    public String Translate(@Param("article")String article,@Param("code1")String code1,@Param("code2")String code2){
        // 在平台申请的APP_ID 详见 http://api.fanyi.baidu.com/api/trans/product/desktop?req=developer



        TransApi api = new TransApi(APP_ID, SECURITY_KEY);

        String query = article;
        String result = api.getTransResult(query, code1, code2);
        System.out.println(result);

        return result;
    }

//    @RequestMapping("/gettoken")
//    public QiNiu getToken() {
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
//        return qiNiu;
//    }

}
