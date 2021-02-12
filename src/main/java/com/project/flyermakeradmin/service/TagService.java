package com.project.flyermakeradmin.service;

import com.project.flyermakeradmin.entity.Tag;
import com.project.flyermakeradmin.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    @Autowired
    TagRepository tagRepository;

    @Autowired
    PosterTagMappingService posterTagMappingService;

    public Tag insertNewRecord(String tagType, String tagName) {
        Tag tag = new Tag();
        tag.setTagType(tagType);
        tag.setTagName(tagName);
        return tagRepository.save(tag);
    }

    public Tag updateRecord(Tag tag, String tagType, String tagName) {
        tag.setTagType(tagType);
        tag.setTagName(tagName);
        return tagRepository.save(tag);
    }

    public List<Tag> getAllTag() {
        return tagRepository.findAll();
    }

    public boolean checkIfTagIdPresent(Integer tagId, String tagType) {
        return tagRepository.existsByTagIdAndTagType(tagId, tagType);
    }

    public boolean checkIfTagIdPresent(Integer tagId) {
        return tagRepository.existsById(tagId);
    }

    public void deleteByTagId(Integer tagId) {
        tagRepository.deleteById(tagId);
    }

    public Optional<Tag> findByTagId(Integer tagId) {
        return tagRepository.findById(tagId);
    }

    public Tag getTagById(Integer tagId) {
        return tagRepository.findByTagId(tagId);
    }
}
