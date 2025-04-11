package com.ims_team4.dto.response;

import com.ims_team4.dto.ChatDTO;
import com.ims_team4.dto.ChatDetailDTO;
import com.ims_team4.dto.EmployeeDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponse {
    private EmployeeDTO recruiter;
    private EmployeeDTO manager;
    private int conversationId;
    private String username;
    private List<ChatDTO> recruiterChats;
    private List<ChatDTO> managerChats;
    private List<ChatDetailDTO> latestChats;
    private List<EmployeeDTO> managers;
    private List<EmployeeDTO> recruiters;
    private List<ChatDetailDTO> chatDetails;
    private int rid;
    private int mid;
    private String error;
}
