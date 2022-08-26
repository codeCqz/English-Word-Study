package com.cqz.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cqz.pojo.Sort;
import com.cqz.pojo.UserStudyInfo;
import com.cqz.pojo.Userword;
import com.cqz.pojo.Word;
import com.cqz.service.UserService;
import com.cqz.service.WordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@ResponseBody
public class  UserWordController {

    @Autowired
    private UserService userService;

    @Autowired
    private WordService wordService;



    @RequestMapping("addnewwordlist")
    public void addnewwordlist(@RequestParam("userid")int userid,@RequestParam("word")String word,@RequestParam("level")String level){
        wordService.addnewwordlist(userid,word,level);
    }

    @RequestMapping("setdimword")
    public void setdimword(@RequestParam("id")int userid,@RequestParam("word")String word){
        wordService.setdimword(userid,word);
    }


    @RequestMapping("setincognizancword")
    public void setincognizancword(@RequestParam("id")int userid,@RequestParam("word")String word){
        wordService.setincognizancword(userid,word);
    }



    @RequestMapping("checkcomplete")
    public int CheckComplete(@RequestParam("userid")int userid,@RequestParam("level")String level){
        int count = wordService.findstudycount(userid,level);
        int allCount = wordService.getSortCount(level);
        count+=1;

        if(count>=allCount){
            wordService.resetStudy(userid);
            wordService.deleteuserword(userid,level);
            wordService.setisfinish(userid,level);
            return 1;
        }else{
            return 0;
        }
    }





    @RequestMapping("getallcount")
    public List<Float> getAllCount(@RequestParam("userid")int userid){
        float stucount = 0;
        float allcount = 0;
        List<Sort> l = wordService.findAllSortName();
        List<Float> count = new ArrayList<Float>();
        for(Sort s:l){
            String level = s.getLevel();
            float f = 0;
            if(wordService.getisfinish(userid,level)!=0){
                f = 100;
            }else{
                stucount = (float)wordService.findstudycount(userid,level);
                allcount = (float)s.getSortcount();
                f = (stucount/ allcount)*100;

            }
            count.add(f);
        }
        return count;
    }


    @RequestMapping("getreview")
    public int getreview(@RequestParam("id")int userid){
       int count01 = wordService.getreview(userid);
       int count02 = wordService.getVague(userid);
       int count = count01 + count02;
       if(count==0){
           return 0;
       }else{
           return 1;
       }
    }


    @RequestMapping("recitewords")
    public void recitewords(@RequestParam("word")String word,@RequestParam("id")int userid){

        int isreview = wordService.isreview(userid,word);
        if(isreview==0){
            int review = 1;
            wordService.setreview(userid,word,review);
        }else{
            int review = 0;
            wordService.setreview(userid,word,review);
        }
        wordService.setStatus(userid,word);
    }




    @RequestMapping("isStudy")
    public int isStudy(@RequestParam("id")int userid){
        return wordService.getState(userid);
    }

    @RequestMapping("lastTime")
    public int lastTime(@RequestParam("id")Integer userid){
        return wordService.getLastTime(userid);
    }

    @RequestMapping("getinfo")
    public UserStudyInfo getInfo(@RequestParam("id") int userid){
        return wordService.getInfo(userid);
    }


    @RequestMapping("study")
    public int study(@RequestParam("form") String form,@RequestParam("id") int userid){
        JSONObject jsonObject = JSON.parseObject(form);
        //获取json对象中的code
        String way = jsonObject.getString("way");
        Integer count = jsonObject.getInteger("count");
        String level = jsonObject.getString("level");

        Integer id = wordService.getSortId(level);
        Integer sortcount =  wordService.findSortCount(id);
        if(wordService.getState(userid)==0){

            wordService.setState(userid);
            userService.setUserState(userid,way,count,level);

            return sortcount;
        }else{
            return 0;
        }
    }

    @RequestMapping("findsortcount")
    public int findsortcount(@RequestParam("id") int userid){
        return wordService.findSortCount2(userid);
    }


    @RequestMapping("finish")
    public void finish(@RequestParam("id") int userid){
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        Integer count =  wordService.findcount(userid,date);
        Integer  total = wordService.getalreadycount(userid);
        total+=count;
        wordService.setcount(userid,count);
        wordService.settotal(userid,total);
    }

    @RequestMapping("getsortname")
    public String  getSortName(@RequestParam("level")String level){

        String sortname = wordService.getSortName(level);

        return sortname;
    }
    @RequestMapping("getisreview")
    public int  getisReview(@RequestParam("userid") int userid){
        List<Word> l = new ArrayList<Word>();
        l = wordService.getReviewWord(userid);
        int size = l.size();
        System.out.println(size);
        return size;
    }

    @RequestMapping("getisstudy")
    public int  getisstudy(@RequestParam("userid") int userid){
        List<Word> l = new ArrayList<Word>();
        List<Word> l1 = new ArrayList<Word>();
        List<Word> l2 = new ArrayList<Word>();
        List<Word> merged = new ArrayList<Word>();
            l = wordService.getVaguelist(userid);
            for (Word word : l) {
                int vague = wordService.getIntVague(userid,word.getWord());
                vague-=1;
                wordService.setVague(userid,word.getWord(),vague);
            }
            l1 = wordService.getReviewWord(userid);
            l2 =  wordService.getData(userid);
            merged.addAll(l);
            merged.addAll(l1);
            merged.addAll(l2);


        int size = merged.size();
        System.out.println(size);
        return size;
    }

    @RequestMapping("getdata")
    public List<Word> getData(@RequestParam("userid") int userid){
//        System.out.println(userid);
//        JSONObject jsonObject = JSON.parseObject(form);
//        String way = jsonObject.getString("way");
//        Integer count = jsonObject.getInteger("count");
//        String level = jsonObject.getString("level");

//        System.out.println(way+"------"+userid);
        UserStudyInfo usi = wordService.getInfo(userid);
        String level = usi.getLevel();
        String way = usi.getWay();
        List<Word> l = new ArrayList<Word>();
        List<Word> l1 = new ArrayList<Word>();
        List<Word> l2 = new ArrayList<Word>();
        List<Word> merged = new ArrayList<Word>();
//        System.out.println(level+"++++"+way+"####");
       if("self".equals(way)){

//        if("self".equals(way)){
//            System.out.println("执行");
            l = wordService.getVaguelist(userid);
            for (Word word : l) {
                int vague = wordService.getIntVague(userid,word.getWord());
                    vague-=1;
                    wordService.setVague(userid,word.getWord(),vague);
            }
           l1 = wordService.getReviewWord(userid);
           l2 =  wordService.getData(userid);
           merged.addAll(l);
           merged.addAll(l1);
           merged.addAll(l2);

//
           return merged;
       }else{
//        if("self".equals(way)){
//            System.out.println("执行");
           l = wordService.getVaguelist(userid);
           for (Word word : l) {
               int vague = wordService.getIntVague(userid,word.getWord());
               vague-=1;
               wordService.setVague(userid,word.getWord(),vague);
           }
           l1 = wordService.getReviewWord(userid);
//        if("self".equals(way)){

           int count = wordService.getCount(userid);
           int total = wordService.gettotal(userid);
//           System.out.println(wordService.getIncomplete(userid));
           if(wordService.getIncomplete(userid)==0){
               int start = total;
               int end = count;
               l2 =  wordService.getSystemData(level,start,end);
               total+=count;
               wordService.settotal(userid,total);
               for (Word word : l2) {
                   Userword uw = new Userword();
                   uw.setWord(word.getWord());
                   uw.setUserid(userid);
                   uw.setStatus(0);
                   uw.setDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                   uw.setWay(way);
                   uw.setLevel(level);
                   wordService.saveWord(uw);


               }}else{
                l2 =  wordService.getData(userid);
           }
           merged.addAll(l);
           merged.addAll(l1);
           merged.addAll(l2);

           return merged;
       }
    }

    @RequestMapping("continueStudy")
    public String continueStudy(@RequestParam("userid")int userid){



        return null;
    }

    @RequestMapping("setplan")
    public void setPlan(@RequestParam("id")int userid){

        wordService.setuserplan(userid);
    }

    @RequestMapping("getplan")
    public int getPlan(@RequestParam("userid")int userid){

        return wordService.getuserplan(userid);
    }


    @RequestMapping("getallsortname")
    public List<Sort> getallsortname(){
        return wordService.findAllSortName();
    }



    @Scheduled(cron="0 0 0 * * 0-7")
    public void resetPlan(){
        wordService.resetPlan();
    }
    @RequestMapping("resetplan")
    public void resetUserPlan(@RequestParam("userid")int userid){
        wordService.resetUserPlan(userid);
    }

}
