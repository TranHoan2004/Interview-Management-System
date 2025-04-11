package com.ims_team4.dto.request;

import com.ims_team4.dto.Message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
// HoanTX
public class MessageRequest {
    private Message message; // Address and content of notification
    private String title; // Title of notification
}
