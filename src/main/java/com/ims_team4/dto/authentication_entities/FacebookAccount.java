package com.ims_team4.dto.authentication_entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacebookAccount {
    private String id;
    private String email;
    private String name;
}
