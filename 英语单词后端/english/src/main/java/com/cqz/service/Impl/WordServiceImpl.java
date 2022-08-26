package com.cqz.service.Impl;


import com.cqz.dao.WordDao;

import com.cqz.pojo.*;
import com.cqz.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WordServiceImpl implements WordService {
    @Autowired
    private WordDao wordDao;
    @Override
    public Word findWord(String word) {
        return wordDao.findWord(word);
    }

    public void setWord(String translation,String word){
        wordDao.setWord(translation,word);
    }
    public List<Word> findAll(){
        return wordDao.findAll();
    }
    public void insert(Word word){
        wordDao.insert(word);
    }
    public List<Word> findSortWord(String level,int start,int end){
        return wordDao.findSortWord(level,start,end);
    }
    public void saveWord(Userword uw){
        wordDao.saveWord(uw);
    }
    public Word getWord(int userid,String word){
        Word w = wordDao.getWord(userid,word);
        return w;
    }
    public List<Word> getTodayPlan(int userid,String date){
        return wordDao.getTodayPlan(userid,date);
    }
    @Override
    public void deleteWord(int userid, String word){
        wordDao.deleteWord(userid,word);
    }

    @Override
    public  int getSortId(String level){
        return wordDao.getSortId(level);
    }

    @Override
    public  int findSortCount(int id){
        return wordDao.findSortCount(id);
    }


    @Override
    public  int findSortCount2(int id){
        return wordDao.findSortCount2(id);
    }


    @Override
    public List<Word> findSortWord2(int userid,int start,int end){
        return wordDao.findSortWord2(userid,start,end);
    }

    public int findcount(int userid,String date){
        return wordDao.findcount(userid,date);
    }
    public int setcount(int userid,int count){
        return wordDao.setcount(userid,count);
    }

    public int getalreadycount(int userid){
        return wordDao.getalreadycount(userid);
    }

    public  void settotal(int userid,int total){
        wordDao.settotal(userid,total);
    }
    public String getSortName(String level){
        return wordDao.getSortName(level);
    }
    public void setState(int userid){
        wordDao.setState(userid);
    }
    public List<Word> getData(int userid){
        return wordDao.getData(userid);
    }
    public String continueStudy(int userid){
        return wordDao.continueStudy(userid);
    }
    public int getState(int userid){
        return wordDao.getState(userid);
    }
    public UserStudyInfo getInfo(int userid){
        return wordDao.getInfo(userid);
    }
    public List<Word> getSystemData(String level,int start,int end) {
        return wordDao.getSystemData(level, start, end);
    }
    public  int getCount(int userid){
        return wordDao.getCount(userid);
    }
    public int gettotal(int userid){
        return wordDao.gettotal(userid);
    }
    public void setStatus(int userid,String word){
        wordDao.setStatus(userid, word);
    }
    public int getLastTime(int userid){
        return wordDao.getLastTime(userid);
    }
    public int getIncomplete(int userid){
        return wordDao.getIncomplete(userid);
    }
    public void setuserplan(int userid){
        wordDao.setuserplan(userid);
    }
    public int getuserplan(int userid){
        return wordDao.getuserplan(userid);
    }
    public void resetPlan(){
        wordDao.resetPlan();
    }
    public List<Sort>  findAllSortName(){
        return wordDao.findAllSortName();
    }
    public void resetStudy(int userid){
        wordDao.resetStudy(userid);
    }
    public  int findstudycount(int userid,String level){
        return wordDao.findstudycount(userid,level);
    }
    public void deleteuserword(int userid,String level){
        wordDao.deleteuserword(userid,level);
    }

    public int getSortCount(String level){
        return wordDao.getSortCount(level);
    }
    public int getisfinish(int userid,String level){
        return wordDao.getisfinish(userid,level);
    }
    public void setisfinish(int userid,String level){
        wordDao.setisfinish(userid,level);
    }
    public  void addnewwordlist(int userid,String word,String level){
        wordDao.addnewwordlist(userid,word,level);
    }
    public  int getcollectcount(int userid){
        return wordDao.getcollectcount(userid);
    }
    public   List<Word> findCollectWord(int userid,int start,int end){
        return wordDao.findCollectWord(userid,start,end);
    }

    public List<Word> getReviewWord(int userid){
        return wordDao.getReviewWord(userid);
    }
    public void deletecollectword(int userid,String word){
        wordDao.deletecollectword(userid,word);
    }
    public int isreview(int userid,String word){
        return wordDao.isreview(userid,word);
    }
    public   void setreview(int userid,String word,int review){
        wordDao.setreview(userid,word,review);
    }
    public int getreview(int userid){
        return wordDao.getreview(userid);
    }
    public void setdimword(int userid,String word){
        wordDao.setdimword(userid,word);
    }
    public void setincognizancword(int userid,String word){
        wordDao.setincognizancword(userid,word);
    }
    public int getVague(int userid){
       return wordDao.getVague(userid);
    }
    public List<Word> getVaguelist(int userid){
        return wordDao.getVaguelist(userid);
    }
    public void setVague(int userid,String word,int vague){
        wordDao.setVague(userid,word,vague);
    }
    public int getIntVague(int userid,String word){
        return wordDao.getIntVague(userid,word);
    }
    public   void resetUserPlan(int userid){wordDao.resetUserPlan(userid);
    }
}
