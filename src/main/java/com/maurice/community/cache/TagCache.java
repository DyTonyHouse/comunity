package com.maurice.community.cache;

import com.maurice.community.dto.TagDTO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Maurice
 * @Date: 2019/8/23
 * @Description:
 */
@Repository
public class TagCache {
    public List<TagDTO> get(){
        List<TagDTO> tagDTOS = new ArrayList<>();

        TagDTO program = new TagDTO();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("javascript","nphp","css","html","html5","java","node.js","python","c++","c","golang","objective-c","typescript","shell","c#","swift","sass","bash","ruby","less","asp.net","lua","scala","coffeescript","actionscript","rust","erlang","perl"));

        TagDTO frameWork = new TagDTO();
        frameWork.setCategoryName("平台框架");
        frameWork.setTags(Arrays.asList("laravel","spring","express","django","flask","yii","ruby-on-rails tornado","koa","struts"));

        TagDTO server = new TagDTO();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("linux","nginx","docker","apache","ubuntu","centos","缓存 tomcat","负载均衡","unix","hadoop","windows-server"));

        tagDTOS.add(program);
        tagDTOS.add(frameWork);
        tagDTOS.add(server);
        return tagDTOS;
    }

    public String filterInvalid(String tags){
        String[] splitTags = StringUtils.split(tags,",");
        List<TagDTO> tagDTOS = get();

        List<String> tagList = tagDTOS.stream().flatMap(tagDTO -> tagDTO.getTags().stream()).collect(Collectors.toList());
        String invalid = Arrays.stream(splitTags).filter(splitTag -> !tagList.contains(splitTag)).collect(Collectors.joining(","));
        return invalid;
    }
}