package com.lyc.community.intercepter;

import com.lyc.community.mapper.NotificationMapper;
import com.lyc.community.mapper.UserMapper;
import com.lyc.community.model.User;
import com.lyc.community.model.UserExample;
import com.lyc.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @program: community
 * @description:
 * @author: liyuecheng
 * @create: 2019-10-05 11:28
 **/
@Service
public class SectionInterceptor implements HandlerInterceptor {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NotificationService notificationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for(Cookie c:cookies){
                if("token".equals(c.getName())){
                    String token = c.getValue();
                    UserExample userExample = new UserExample();
                    userExample.createCriteria().andTokenEqualTo(token);
                    List<User> users = userMapper.selectByExample(userExample);
                    if(users.size()!=0){
                        User user = users.get(0);
                        request.getSession().setAttribute("user", user);
                        Long unreadCount = notificationService.getUnreadCount(user.getId());
                        request.getSession().setAttribute("unreadCount", unreadCount);
                    }
                    break;
                }
            }
        }

        return true;
    }
}
