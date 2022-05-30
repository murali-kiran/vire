package com.vire.cacheHandler;

import com.vire.dto.MasterDto;

import java.util.List;

public interface GenericCacheHandler {

    public List<MasterDto> getPersonalProfileSettingTypes();
    public List<MasterDto> getFirmProfileSettingTypes();

}
