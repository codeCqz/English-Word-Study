package com.cqz.dao;

import com.cqz.pojo.Sort;
import com.cqz.pojo.UserStudyInfo;
import com.cqz.pojo.Userword;
import com.cqz.pojo.Word;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WordDao {
    Word findWord(String word);
    List<Word> findSortWord(String level,int start,int end);

    List<Word> findCollectWord(int userid,int start,int end);

    List<Word> findSortWord2(int userid,int start,int end);
    void setWord(String translation,String word);
    List<Word> findAll();
    void insert(Word word);
    void saveWord(Userword uw);
    Word getWord(int userid,String word);
    List<Word> getTodayPlan(int userid,String date);
    void deleteWord(int userid,String word);
    int getSortId(String level);
    int findSortCount(int id);
    int findSortCount2(int id);
    int findcount(int userid,String date);
    int setcount(int userid,int count);
    int getalreadycount(int userid);
    void settotal(int userid,int total);
    String getSortName(String level);
    void setState(int userid);
    int getState(int userid);
    List<Word> getData(int userid);
    String continueStudy(int userid);
    UserStudyInfo getInfo(int userid);
    List<Word> getSystemData(String level,int start,int end);
    int getCount(int userid);
    int gettotal(int userid);
    void setStatus(int userid,String word);
    int getLastTime(int userid);
    int getIncomplete(int userid);
    void setuserplan(int userid);
    int getuserplan(int userid);
    void resetPlan();
    List<Sort> findAllSortName();
    void resetStudy(int userid);
    int findstudycount(int userid,String level);
    void deleteuserword(int userid,String level);
    int getSortCount(String level);
    int getisfinish(int userid,String level);
    void setisfinish(int userid,String level);


    void addnewwordlist(int userid,String word,String level);
    int getcollectcount(int userid);


    List<Word> getReviewWord(int userid);

    void deletecollectword(int userid,String word);


     int isreview(int userid,String word);
    void setreview(int userid,String word,int review);

    int getreview(int userid);
    void setdimword(int userid,String word);
    void setincognizancword(int userid,String word);
    int getVague(int userid);
    List<Word> getVaguelist(int userid);
    void setVague(int userid,String word,int vague);
    int getIntVague(int userid,String word);
    void resetUserPlan(int userid);
}
