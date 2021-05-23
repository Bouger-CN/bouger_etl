package com.bouger.bouger_etl.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program:bouger_etl
 * @description:
 * @author:Bouger
 * @date:2021-05-23 18:36:56
 **/
@Controller
public class IndexController {
    @RequestMapping(value = {"","/index"})
    @ResponseBody
    public String index(Model model){
        model.addAttribute("msg","欢迎光临Bouger_etl！");
        return model.toString();
    }
}
