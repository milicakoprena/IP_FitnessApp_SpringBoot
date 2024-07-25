package com.example.fitnessonline.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserHasProgram implements Serializable {

    private Integer id;
    private Boolean isCompleted;
    private Date startDate;
    private User user;
    private Program program;
}
