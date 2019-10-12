package com.lyc.community.advice;

import com.alibaba.fastjson.JSON;
import com.lyc.community.dto.ResultDTO;
import com.lyc.community.exception.CustomizeErrorCode;
import com.lyc.community.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @program: community
 * @description:
 * @author: liyuecheng
 * @create: 2019-10-06 09:30
 **/
// @ControllerAdvice
// public class CustomizeExceptionHandler {
//
//     @ExceptionHandler
//     Object handle(HttpServletRequest request, Throwable e, Model model, HttpServletResponse response){
//
//         String contentType = request.getContentType();
//
//         //发送错误之后根据请求的类型返回对应的类型
//         if("application/json".equals(contentType)){
//             ResultDTO resultDTO;
//             if(e instanceof CustomizeException){
//                 resultDTO =  ResultDTO.errorOf((CustomizeException) e);
//             }else{
//                 resultDTO = ResultDTO.errorOf(CustomizeErrorCode.SYSTEM_ERROR);
//             }
//             try {
//                 response.setContentType("application/json");
//                 response.setStatus(200);
//                 response.setCharacterEncoding("utf-8");
//                 PrintWriter writer = response.getWriter();
//                 writer.write(JSON.toJSONString(resultDTO));
//                 writer.close();
//             } catch (IOException ex) {
//                 ex.printStackTrace();
//             }
//             return null;
//         }else{
//             if(e instanceof CustomizeException){
//                 model.addAttribute("message",e.getMessage());
//             }else{
//                 model.addAttribute("message",CustomizeErrorCode.SYSTEM_ERROR.getMessage());
//             }
//             return new ModelAndView("error");
//         }
//     }
// }
