package com.ims_team4.dto.authentication_entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoogleAccount {
    private String id, email, name, firstName, givenName, familyName, picture;
    private boolean verifiedEmail;
}
