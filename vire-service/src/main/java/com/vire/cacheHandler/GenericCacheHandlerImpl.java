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

    List<MasterDto> personalProfileSettingTypes = new ArrayList<>();
    List<MasterDto> firmProfileSettingTypes = new ArrayList<>();

    @PostConstruct
    private void loadPrerequisiteData() {
        personalProfileSettingTypes = masterRepository.findByMasterType("Personal_Profile_Setting_Type");
        firmProfileSettingTypes = masterRepository.findByMasterType("Firm_Profile_Setting_Type");
    }

    @Override
    public List<MasterDto> getPersonalProfileSettingTypes() {
        return personalProfileSettingTypes;
    }

    @Override
    public List<MasterDto> getFirmProfileSettingTypes(){
        return firmProfileSettingTypes;
    }
}
