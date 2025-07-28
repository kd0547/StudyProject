package com.example.boardexample.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardViewInterceptor implements HandlerInterceptor {

    //30분
    private static final long VIEW_TIMEOUT = 30 * 60 * 1000;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String url = request.getRequestURI();
        if(url.startsWith("/post/") && !url.contains("/like"))
        {
            HttpSession session = request.getSession();
            //post_id, time
            Map<Long,Long> viewPost = (Map<Long, Long>) session.getAttribute("viewPost");
            if(viewPost == null) {
                viewPost = new HashMap<>();
            }
            String idParam = "";

            int index = 0;
            //int start = url.indexOf("/");
            while(true) {
                if(index == -1 ||  index > url.length()) {
                    break;
                }
                int temp = url.indexOf("/",index+1);
                if(temp == -1) {
                    idParam = url.substring(index + 1,url.length()) ;
                }
                index = temp;
            }

            if(!idParam.isEmpty()) {
                long id = Long.parseLong(idParam);
                long currentTimeMillis = System.currentTimeMillis();
                //게시글을 처음 보거나 VIEW_TIMEOUT 시간이 지나면 새로운 조회로 간주
                if(viewPost.get(id) != null) {
                    if(viewPost.containsKey(id) || (currentTimeMillis - viewPost.get(id)) > VIEW_TIMEOUT) {
                        request.setAttribute("viewPost",false);
                    } else {
                        viewPost.put(id,currentTimeMillis);
                        session.setAttribute("viewPost",viewPost);
                        request.setAttribute("viewPost",true);
                    }
                }
                else {
                    viewPost.put(id,currentTimeMillis);
                    session.setAttribute("viewPost",viewPost);
                    request.setAttribute("viewPost",true);

                }

            }
        }


        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
