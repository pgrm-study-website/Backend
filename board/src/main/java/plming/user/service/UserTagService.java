package plming.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plming.tag.entity.Tag;
import plming.tag.entity.TagRepository;
import plming.user.entity.User;
import plming.user.entity.UserTag;
import plming.user.entity.UserTagRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserTagService {

    @Autowired
    private UserTagRepository userTagRepository;
    @Autowired
    private TagRepository tagRepository;

    @Transactional
    public void save(List<Long> tagIdList, User user) {

        tagIdList.stream().map(tagId -> UserTag.builder()
                .user(user)
                .tag(tagRepository.getById(tagId)).build())
                .forEach(userTagRepository::save);
    }

    public List<String> findTagNameByUser (User user) {
        List<UserTag> userTagList = userTagRepository.findAllByUser(user);
        return userTagList.stream().map(UserTag::getTag).map(Tag::getName).collect(Collectors.toList());
    }
}
