package com.STM.action;

import com.STM.model.User;
import com.STM.model.Whisper;
import com.STM.service.UserService;
import com.STM.service.WhisperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2016/11/6 0006.
 */
@Controller
@RequestMapping(value = "/whisper")
public class WhisperController extends AbstractController {

    @Autowired
    private WhisperService whisperService;
    @Autowired
    private UserService userService;

    @Override
    @RequestMapping(value = "/getWhisperData", method = RequestMethod.GET)
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
         /*
        获取时间范围
         */
        Date enddate=new Date();
        Calendar cl = Calendar.getInstance();
        cl.setTime(enddate);
        cl.add(Calendar.DATE,-2);
        Date startdate=cl.getTime();
        SimpleDateFormat dd = new SimpleDateFormat("yyyy/MM/dd");
        String start=dd.format(startdate);
        String end=dd.format(enddate);

        String hql="from Whisper w where w.publishTime between '"+start+" 17:00:00' and '"+end+" 19:00:00' and valid=1";
        List<Whisper> whispers=whisperService.getWhisperData(hql);
        if (whispers.size()==0) return null;
        List<String> names= new ArrayList<>();
        for (int i=0;i<whispers.size();i++){
            String openid=whispers.get(i).getOpenid();
            String userhql="from User u where u.openid='"+openid+"'";
            List<User> users=userService.getUsers(userhql);
            if (users.size()==0){
                names.add("");
            }else {
                names.add(users.get(0).getName());
            }
        }

        Map<String,Object> whisperdata = new HashMap<String,Object>();
        whisperdata.put("whispers",whispers);
        whisperdata.put("names",names);
        return new ModelAndView("ExcelWhisper","whisperData",whisperdata);
    }
}
