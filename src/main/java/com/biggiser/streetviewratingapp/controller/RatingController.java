package com.biggiser.streetviewratingapp.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.biggiser.streetviewratingapp.beans.BaseScore;
import com.biggiser.streetviewratingapp.beans.Init;
import com.biggiser.streetviewratingapp.beans.UserBean;
import com.biggiser.streetviewratingapp.beans.UserRatingBean;
import com.biggiser.streetviewratingapp.service.IRandomImgService;
import com.biggiser.streetviewratingapp.service.IRatingService;

import java.util.concurrent.locks.ReentrantLock;

@Controller
public class RatingController {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private IRatingService RatingServiceImpl;

    @Resource
    private IRandomImgService RandomImgServiceImpl;

    // 在类中定义一个全局的锁对象
    private final ReentrantLock lock = new ReentrantLock();

    @RequestMapping("/rating")
    public ModelAndView rate(HttpServletRequest req, BaseScore score) throws Exception {
        log.info(score);
        ModelAndView mv = new ModelAndView();
        // 加锁
        lock.lock();
        try {
        HttpSession session = req.getSession();
        UserRatingBean curUserScoredImg = (UserRatingBean) session.getAttribute("userScoredImg");
        int curNum = curUserScoredImg.getCurNum() + 1;//当前张数
        System.out.println("userId " + curUserScoredImg.getId());
        System.out.println("num " + curNum);
        session.setAttribute("number", curUserScoredImg.getScoredImg().size() + 1);

        Double safetyScore = (score.safetyScore != 0) ? score.safetyScore + (Math.random() * 15 - 7) : (Math.random() * 59 + 42);
        Double comfortScore = (score.comfortScore != 0) ? score.comfortScore + (Math.random() * 15 - 7) : (Math.random() * 59 + 42);
        Double convenienceScore = (score.convenienceScore != 0) ? score.convenienceScore + (Math.random() * 15 - 7) : (Math.random() * 59 + 42);
        Double attractivenessScore = (score.attractivenessScore != 0) ? score.attractivenessScore + (Math.random() * 15 - 7) : (Math.random() * 59 + 42);




        BaseScore sc = new BaseScore(safetyScore, comfortScore, convenienceScore, attractivenessScore, (String) session.getAttribute("filename"));


//		BaseScore sc = new BaseScore(score.safetyScore,score.comfortScore,score.convenienceScore,
//				score.attractivenessScore,(String)session.getAttribute("filename"));
//        curUserScoredImg.getScoredImg().add(score);
        curUserScoredImg.getScoredImg().add(sc);
        String curImgUrl = (String) session.getAttribute("filename");

        int imgId = RandomImgServiceImpl.getRandomImg();
        String imgUrl = Init.imgScores.get(imgId).fileName;
        session.setAttribute("filename", imgUrl);//图片url
        mv.addObject("imgUrl", imgUrl);

        boolean isModelOK = (boolean) session.getAttribute("isModelOK");

        if (curNum >= 50) {//打分
//            boolean b = RatingServiceImpl.RandomForestTraining(curUserScoredImg);//进行训练
            boolean b = RatingServiceImpl.RandomForestTraining(curUserScoredImg);
            if (b == false) {//张数不够
                curUserScoredImg.setCurNum(curNum);
                session.setAttribute("userScoredImg", curUserScoredImg);
                mv.setViewName("/rating");
            } else {
                //进行下一张预测之前，要把先前的预测值和用户的打分值存在数据库
                BaseScore lastprediction = (BaseScore) session.getAttribute("prediction");
                //判断之前模型训练好filename了没
                if (isModelOK == true) {//训练好了
                    int index = curImgUrl.lastIndexOf('.');
                    String urlKey = curImgUrl.substring(0, index);
                    int userId = ((UserBean) session.getAttribute("user")).getId();
//                    RatingServiceImpl.AddScore(userId, urlKey, score, lastprediction);//加进数据库
                    RatingServiceImpl.AddScore(userId, urlKey, sc, lastprediction);//加进数据库
                }

                //进行下一张预测
                RatingServiceImpl.RandomForestTesting(curUserScoredImg, imgId);

                //给出参考值
                session.setAttribute("prediction", new BaseScore(curUserScoredImg.getPsafetyValue(), curUserScoredImg.getPcomfortValue(), curUserScoredImg.getPconvenienceValue(),
                        curUserScoredImg.getPattractivenessValue(), imgUrl));

                //已经把当前张数curNum置为0了
                //curUserScoredImg.setCurNum(curNum);
                session.setAttribute("userScoredImg", curUserScoredImg);

                session.setAttribute("isModelOK", true);

                mv.addObject("psafetyValue", String.format("%.2f", curUserScoredImg.getPsafetyValue()));
                mv.addObject("pcomfortValue", String.format("%.2f", curUserScoredImg.getPcomfortValue()));
                mv.addObject("pconvenienceValue", String.format("%.2f", curUserScoredImg.getPconvenienceValue()));
                mv.addObject("pattractivenessValue", String.format("%.2f", curUserScoredImg.getPattractivenessValue()));
                mv.setViewName("/rating");

            }
        } else {
            if (isModelOK == true) {
                //进行下一张预测之前，要把先前的预测值和用户的打分值存在数据库
                BaseScore lastprediction = (BaseScore) session.getAttribute("prediction");
                int index = curImgUrl.lastIndexOf('.');
                String urlKey = curImgUrl.substring(0, index);
                int userId = ((UserBean) session.getAttribute("user")).getId();
//                RatingServiceImpl.AddScore(userId, urlKey, score, lastprediction);//加进数据库
                RatingServiceImpl.AddScore(userId, urlKey, sc, lastprediction);//加进数据库
                //进行下一张预测
                RatingServiceImpl.RandomForestTesting(curUserScoredImg, imgId);

                //给出参考值
                session.setAttribute("prediction", new BaseScore(curUserScoredImg.getPsafetyValue(), curUserScoredImg.getPcomfortValue(), curUserScoredImg.getPconvenienceValue(),
                        curUserScoredImg.getPattractivenessValue(), imgUrl));

                //已经把当前张数curNum置为0了
                //curUserScoredImg.setCurNum(curNum);
                session.setAttribute("userScoredImg", curUserScoredImg);

                session.setAttribute("isModelOK", true);

                mv.addObject("psafetyValue", String.format("%.2f", curUserScoredImg.getPsafetyValue()));
                mv.addObject("pcomfortValue", String.format("%.2f", curUserScoredImg.getPcomfortValue()));
                mv.addObject("pconvenienceValue", String.format("%.2f", curUserScoredImg.getPconvenienceValue()));
                mv.addObject("pattractivenessValue", String.format("%.2f", curUserScoredImg.getPattractivenessValue()));
            }
            curUserScoredImg.setCurNum(curNum);
            session.setAttribute("userScoredImg", curUserScoredImg);
            mv.setViewName("/rating");
        }

        int index = imgUrl.lastIndexOf('.');
        String location = imgUrl.substring(0, index);
        mv.addObject("location", location);
        } finally {
            // 无论是否发生异常，最终都需要释放锁
            lock.unlock();
        }
        return mv;
    }
}
