package com.vire.model.response;

import lombok.Data;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class SocialChatMapResponse implements Serializable {
    private Map<String, List<SocialChatResponse>> socialChatMapResponse;

}
