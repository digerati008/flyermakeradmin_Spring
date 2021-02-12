package com.project.flyermakeradmin.service;

import com.project.flyermakeradmin.entity.Text;
import com.project.flyermakeradmin.repository.TextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TextService {
    @Autowired
    TextRepository textRepository;

    @Autowired
    PosterTextMappingService posterTextMappingService;

    public Text insertNewRecord(Integer fontId, Integer height, Integer width, String value, Integer xAxis, Integer yAxis, Integer fontSize, String color, Integer rotation, String align, Integer opacity, Integer zIndex, Integer lineSpacing, Integer letterSpacing) {
        Text text = new Text();
        return getText(text, fontId, height, width, value, xAxis, yAxis, fontSize, color, rotation, align, opacity, zIndex, lineSpacing, letterSpacing);
    }

    public Text updateRecord(Text text, Integer fontId, Integer height, Integer width, String value, Integer xAxis, Integer yAxis, Integer fontSize, String color, Integer rotation, String align, Integer opacity, Integer zIndex, Integer lineSpacing, Integer letterSpacing) {
        return getText(text, fontId, height, width, value, xAxis, yAxis, fontSize, color, rotation, align, opacity, zIndex, lineSpacing, letterSpacing);
    }

    private Text getText(Text text, Integer fontId, Integer height, Integer width, String value, Integer xAxis, Integer yAxis, Integer fontSize, String color, Integer rotation, String align, Integer opacity, Integer zIndex, Integer lineSpacing, Integer letterSpacing) {
        text.setFontId(fontId);
        text.setHeight(height);
        text.setWidth(width);
        text.setValue(value);
        text.setXAxis(xAxis);
        text.setYAxis(yAxis);
        text.setFontSize(fontSize);
        text.setColor(color);
        text.setRotation(rotation);
        text.setAlign(align);
        text.setZIndex(zIndex);
        text.setOpacity(opacity);
        text.setLineSpacing(lineSpacing);
        text.setLetterSpacing(letterSpacing);

        return textRepository.save(text);
    }

    public List<Text> getTextByPosterId(Integer posterId) {
        List<Integer> textIdList = posterTextMappingService.getTextIdsByPosterId(posterId);
        if(!CollectionUtils.isEmpty(textIdList))
            return textRepository.findAllById(textIdList);
        else
            return new ArrayList<>();
    }

    public void deleteByPosterId(Integer posterId) {
        textRepository.deleteByPosterId(posterId);
    }

    public Boolean checkIfTextIdPresent(Integer textId) {
        return textRepository.existsById(textId);
    }

    public void deleteByTextId(Integer textId) {
        textRepository.deleteById(textId);
    }

    public Optional<Text> findByTextId(Integer textId) {
        return textRepository.findById(textId);
    }
}
