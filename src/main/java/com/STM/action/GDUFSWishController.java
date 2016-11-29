package com.STM.action;

import com.STM.model.Wish;
import com.STM.service.WishService;
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
 * Created by chenjunkai on 16-11-29.
 */
@Controller
@RequestMapping(value = "/GDUFS/wish")
public class GDUFSWishController extends AbstractController {

    @Autowired
    private WishService wishService;

    @Override
    @RequestMapping(value = "/getWishData", method = RequestMethod.GET)
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        Date enddate=new Date();
        Calendar cl = Calendar.getInstance();
        cl.setTime(enddate);
        cl.add(Calendar.DATE,-1);
        Date startdate=cl.getTime();
        SimpleDateFormat dd = new SimpleDateFormat("yyyy/MM/dd");
        String start=dd.format(startdate);
        String end=dd.format(enddate);

        String hql="from Wish w where w.publishTime between '"+start+" 17:00:00' and '"+end+" 17:00:00' and w.valid=1 and w.school='广东外语外贸大学'";
        List<Wish> wishes=wishService.getWishData(hql);
        if (wishes.size()==0) return null;
        Map<String,Object> wishdata = new HashMap<String,Object>();
        wishdata.put("wishes",wishes);
        return new ModelAndView("ExcelWish","wishdata",wishdata);
    }
}
