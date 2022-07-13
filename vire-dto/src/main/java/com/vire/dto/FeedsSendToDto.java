package com.vire.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
public class FeedsSendToDto implements Serializable {
    private Long feedsSendToId;
    private String type;
    private String value;
    private String name;
    private Long feedId;
    private  Long createdTime;
    private  Long updatedTime;

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + feedsSendToId + ", " +
                "type = " + type + ", " +
                "value = " + value + ", " +
                "feed = " + feedId + ")";
    }
}
