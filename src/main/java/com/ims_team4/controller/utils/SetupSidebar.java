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
}
