package com.ims_team4.controller.utils;

import com.ims_team4.dto.ChatDTO;
import com.ims_team4.dto.ChatDetailDTO;
import com.ims_team4.dto.EmployeeDTO;

import java.util.List;

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

    public ChatResponse() {
    }

    public ChatResponse(EmployeeDTO recruiter, EmployeeDTO manager, int conversationId, String username, List<ChatDTO> recruiterChats, List<ChatDTO> managerChats, List<ChatDetailDTO> latestChats, List<EmployeeDTO> managers, List<EmployeeDTO> recruiters, List<ChatDetailDTO> chatDetails, int rid, int mid, String error) {
        this.recruiter = recruiter;
        this.manager = manager;
        this.conversationId = conversationId;
        this.username = username;
        this.recruiterChats = recruiterChats;
        this.managerChats = managerChats;
        this.latestChats = latestChats;
        this.managers = managers;
        this.recruiters = recruiters;
        this.chatDetails = chatDetails;
        this.rid = rid;
        this.mid = mid;
        this.error = error;
    }

    public EmployeeDTO getRecruiter() {
        return recruiter;
    }

    public void setRecruiter(EmployeeDTO recruiter) {
        this.recruiter = recruiter;
    }

    public EmployeeDTO getManager() {
        return manager;
    }

    public void setManager(EmployeeDTO manager) {
        this.manager = manager;
    }

    public int getConversationId() {
        return conversationId;
    }

    public void setConversationId(int conversationId) {
        this.conversationId = conversationId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<ChatDTO> getRecruiterChats() {
        return recruiterChats;
    }

    public void setRecruiterChats(List<ChatDTO> recruiterChats) {
        this.recruiterChats = recruiterChats;
    }

    public List<ChatDTO> getManagerChats() {
        return managerChats;
    }

    public void setManagerChats(List<ChatDTO> managerChats) {
        this.managerChats = managerChats;
    }

    public List<ChatDetailDTO> getLatestChats() {
        return latestChats;
    }

    public void setLatestChats(List<ChatDetailDTO> latestChats) {
        this.latestChats = latestChats;
    }

    public List<EmployeeDTO> getManagers() {
        return managers;
    }

    public void setManagers(List<EmployeeDTO> managers) {
        this.managers = managers;
    }

    public List<EmployeeDTO> getRecruiters() {
        return recruiters;
    }

    public void setRecruiters(List<EmployeeDTO> recruiters) {
        this.recruiters = recruiters;
    }

    public List<ChatDetailDTO> getChatDetails() {
        return chatDetails;
    }

    public void setChatDetails(List<ChatDetailDTO> chatDetails) {
        this.chatDetails = chatDetails;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
