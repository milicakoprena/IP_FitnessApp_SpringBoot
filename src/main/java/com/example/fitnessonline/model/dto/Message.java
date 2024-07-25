package com.example.fitnessonline.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message implements Serializable {
    private Integer id;
    private String content;
    private Boolean isRead;
    private User user;
}
