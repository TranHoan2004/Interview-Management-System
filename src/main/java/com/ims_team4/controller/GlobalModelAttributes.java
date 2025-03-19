package com.ims_team4.controller;

import com.ims_team4.controller.utils.SetupSidebar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Locale;

@ControllerAdvice
public class GlobalModelAttributes {
    private final SetupSidebar sidebar;

    public GlobalModelAttributes(SetupSidebar sidebar) {
        this.sidebar = sidebar;
    }

    @ModelAttribute
    public void addLeftSidebarAttributes(Model model, Locale locale) {
        sidebar.setupLeftSidebar(model, locale);
    }

    @ModelAttribute
    public void addDashboardAttributes(Model model, Locale locale) {
        sidebar.setupDashboard(model, locale);
    }
}
