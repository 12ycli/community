package com.lyc.community.service;

import com.lyc.community.dto.PageDTO;
import com.lyc.community.dto.QuestionDTO;
import com.lyc.community.dto.QuestionQueryDTO;
import com.lyc.community.exception.CustomizeErrorCode;
import com.lyc.community.exception.CustomizeException;
import com.lyc.community.mapper.QuestionMapper;
import com.lyc.community.mapper.QuestionMapperExtension;
import com.lyc.community.mapper.UserMapper;
import com.lyc.community.model.Question;
import com.lyc.community.model.QuestionExample;
import com.lyc.community.model.User;
import com.lyc.community.model.UserExample;
import org.apache.ibatis.session.RowBounds;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: community
 * @description:
 * @author: liyuecheng
 * @create: 2019-10-04 09:57
 **/
@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionMapperExtension questionMapperExtension;

    @Autowired
    private UserMapper userMapper;

    public PageDTO<QuestionDTO> list(String search, Integer page, Integer size) {
        if(!StringUtils.isEmpty(search)){
            String[] tags = search.split(" ");
            search = Arrays.stream(tags).collect(Collectors.joining("|"));
        }

        QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
        questionQueryDTO.setSearch(search);
        int count = questionMapperExtension.countBySearch(questionQueryDTO);
        // int count = (int)questionMapper.countByExample(new QuestionExample());

        questionQueryDTO.setPage((page-1)*size);
        questionQueryDTO.setSize(size);
        List<Question> questions = questionMapperExtension.selectBySearch(questionQueryDTO);
        // List<Question> questions = questionMapper.selectByExampleWithRowbounds(new QuestionExample(),new RowBounds(size*(page-1),size));

        List<QuestionDTO> questionDTOs = new ArrayList<>();
        PageDTO pageDTO = new PageDTO(count,page,size);
        for(Question question:questions){
            Long creator = question.getCreator();
            User user = null;
            if(creator!=null) {
                UserExample userExample = new UserExample();
                userExample.createCriteria().andIdEqualTo(creator);
                user = userMapper.selectByExample(userExample).get(0);
            }
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            // questionDTO.setDescription("这是一个热启动测试55555555555555！！");
            questionDTOs.add(questionDTO);
        }
        pageDTO.setData(questionDTOs);
        return pageDTO;
    }

    public PageDTO list(Long userId, Integer page, Integer size) {
        // List<Question> questions = questionMapper.listByUserId(userId,size*(page-1),size);
        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(userId);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(example,new RowBounds(size*(page-1),size));
        List<QuestionDTO> questionDTOs = new ArrayList<>();

        // Integer count = questionMapper.countByUserId(userId);
        example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(userId);
        int count = (int) questionMapper.countByExample(example);
        PageDTO<QuestionDTO> pageDTO = new PageDTO(count,page,size);
        for(Question question:questions){
            Long creator = question.getCreator();
            User user = null;
            if(creator!=null) {
                UserExample userExample = new UserExample();
                userExample.createCriteria().andIdEqualTo(creator);
                user = userMapper.selectByExample(userExample).get(0);
            }
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            // questionDTO.setDescription("这是一个热启动测试55555555555555！！");
            questionDTOs.add(questionDTO);
        }
        pageDTO.setData(questionDTOs);
        return pageDTO;
    }

    public QuestionDTO findQuestionById(Long id) {
        // Question question = questionMapper.findQuestionById(id);
        Question question = questionMapper.selectByPrimaryKey(id);

        //创建者不应该为空，很有可能数据库是手工创建的，默认creator为空
        if(question.getCreator()==null){
            throw new CustomizeException(CustomizeErrorCode.CREATOR_IS_EMPTY);
        }

        if(question==null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdEqualTo(question.getCreator());
        User user = userMapper.selectByExample(userExample).get(0);
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void update(Question question) {
        if(question.getId()==null) {
            long now = System.currentTimeMillis();
            question.setGmtCreate(now);
            question.setGmtModified(now);
            question.setViewCount(0);
            question.setLikeCount(0);
            question.setCommentCount(0);
            questionMapper.insert(question);
        }else{
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            QuestionExample example = new QuestionExample();
            example.createCriteria().andIdEqualTo(question.getId());
            int updated = questionMapper.updateByExampleSelective(updateQuestion, example);
            if(updated!=1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void increaseViewCount(Long id) {
        questionMapperExtension.increaseViewCount(id);
    }
}
