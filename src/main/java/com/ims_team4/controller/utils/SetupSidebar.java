package com.ims_team4.controller.utils;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.Locale;

@Component
public class SetupSidebar {
    private final MessageSource source;

    public SetupSidebar(@Qualifier("messageSource") MessageSource source) {
        this.source = source;
    }

    public void setupLeftSidebar(@NotNull Model model, Locale locale) {
        model.addAttribute("homeLeftSidebar", source.getMessage("home", null, locale));
        model.addAttribute("candidateLeftSidebar", source.getMessage("candidate", null, locale));
        model.addAttribute("jobLeftSidebar", source.getMessage("job", null, locale));
        model.addAttribute("interviewLeftSidebar", source.getMessage("interview", null, locale));
        model.addAttribute("offerLeftSidebar", source.getMessage("offer", null, locale));
        model.addAttribute("userManagementLeftSidebar", source.getMessage("user_management", null, locale));
    }

    public void setupDashboard(@NotNull Model model, Locale locale) {
        model.addAttribute("home_page", source.getMessage("home_page", null, locale));
        model.addAttribute("system_name", source.getMessage("system_name", null, locale));
        model.addAttribute("header_title", source.getMessage("header_title", null, locale));
        model.addAttribute("h5_title", source.getMessage("h5_title", null, locale));
        model.addAttribute("list_candidates", source.getMessage("list_candidates", null, locale));
        model.addAttribute("list_jobs", source.getMessage("list_jobs", null, locale));
        model.addAttribute("list_interviews", source.getMessage("list_interviews", null, locale));
        model.addAttribute("list_offers", source.getMessage("list_offers", null, locale));
        model.addAttribute("list_users", source.getMessage("list_users", null, locale));
        model.addAttribute("click_link", source.getMessage("click_link", null, locale));
    }

    // <editor-fold> desc="admin features"
    public void setupCreateUserPage(@NotNull Model model, Locale locale) {
        model.addAttribute("create_new_user", source.getMessage("create-new-user", null, locale));
        commonAttributes(model, locale);
    }

    public void setupEditUserPage(@NotNull Model model, Locale locale) {
        model.addAttribute("edit_user_title", source.getMessage("edit_user", null, locale));
        commonAttributes(model, locale);
    }

    public void setupUsersDetailPage(@NotNull Model model, Locale locale) {
        model.addAttribute("detailsTitle", source.getMessage("detailsTitle", null, locale));
        model.addAttribute("deactive_user", source.getMessage("deactive_user", null, locale));
        model.addAttribute("active_user", source.getMessage("active_user", null, locale));
        model.addAttribute("other_gender", source.getMessage("other_gender", null, locale));
        model.addAttribute("editBtn", source.getMessage("editBtn", null, locale));
        model.addAttribute("deleteBtn", source.getMessage("deleteBtn", null, locale));
        model.addAttribute("confirm_deletion", source.getMessage("confirm_deletion", null, locale));
        model.addAttribute("notification_before_delete", source.getMessage("notification_before_delete", null, locale));
    }

    public void setupUserListPage(@NotNull Model model, Locale locale) {
        model.addAttribute("listTitle", source.getMessage("listTitle", null, locale));
        model.addAttribute("search_by_title", source.getMessage("search_by_title", null, locale));
        model.addAttribute("search", source.getMessage("search", null, locale));
        model.addAttribute("add_new", source.getMessage("add_new", null, locale));
        model.addAttribute("username", source.getMessage("username", null, locale));
        model.addAttribute("action", source.getMessage("action", null, locale));
    }
    // </editor-fold>

    private void commonAttributes(@NotNull Model model, Locale locale) {
        model.addAttribute("user_management", source.getMessage("user_management", null, locale));
        model.addAttribute("home_page", source.getMessage("home_page", null, locale));
        model.addAttribute("userH3", source.getMessage("userH3", null, locale));
        model.addAttribute("full_name", source.getMessage("full_name", null, locale));
        model.addAttribute("input_full_name", source.getMessage("input_full_name", null, locale));
        model.addAttribute("dob", source.getMessage("dob", null, locale));
        model.addAttribute("phone", source.getMessage("phone", null, locale));
        model.addAttribute("input_phone", source.getMessage("input_phone", null, locale));
        model.addAttribute("positionTitle", source.getMessage("position", null, locale));
        model.addAttribute("role", source.getMessage("role", null, locale));
        model.addAttribute("admin", source.getMessage("admin", null, locale));
        model.addAttribute("manager", source.getMessage("manager", null, locale));
        model.addAttribute("interviewer", source.getMessage("interviewer", null, locale));
        model.addAttribute("recruiter", source.getMessage("recruiter", null, locale));
        model.addAttribute("status", source.getMessage("status", null, locale));
        model.addAttribute("active", source.getMessage("active", null, locale));
        model.addAttribute("deactive", source.getMessage("deactive", null, locale));
        model.addAttribute("email", source.getMessage("email", null, locale));
        model.addAttribute("input_email", source.getMessage("input_email", null, locale));
        model.addAttribute("address", source.getMessage("address", null, locale));
        model.addAttribute("input_address", source.getMessage("input_address", null, locale));
        model.addAttribute("gender", source.getMessage("gender", null, locale));
        model.addAttribute("male", source.getMessage("male", null, locale));
        model.addAttribute("female", source.getMessage("female", null, locale));
        model.addAttribute("departmentTitle", source.getMessage("department", null, locale));
        model.addAttribute("note", source.getMessage("note", null, locale));
        model.addAttribute("back_to_list", source.getMessage("back-to-list", null, locale));
        model.addAttribute("submitBtn", source.getMessage("submitBtn", null, locale));
        model.addAttribute("cancelBtn", source.getMessage("cancelBtn", null, locale));
    }
}
