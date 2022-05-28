package com.vire.cacheHandler;

import com.vire.dto.MasterDto;
import com.vire.repository.MasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class GenericCacheHandlerImpl implements  GenericCacheHandler {

    @Autowired
    MasterRepository masterRepository;

    List<MasterDto> profileSettingTypes = new ArrayList<>();

    @PostConstruct
    private void loadPrerequisiteData() {
        profileSettingTypes = masterRepository.findByMasterType("Profile_Setting_Type");
        System.out.println(profileSettingTypes);
    }

    @Override
    public List<MasterDto> getProfileSettingTypes() {
        return profileSettingTypes;
    }
}
