package com.ims_team4.controller;

import com.ims_team4.controller.utils.SessionController;
import com.ims_team4.controller.utils.SetupSidebar;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Locale;

@ControllerAdvice
public class GlobalModelAttributes {
    private final SetupSidebar sidebar;
    private final SessionController sController;

    public GlobalModelAttributes(SetupSidebar sidebar, SessionController sController) {
        this.sidebar = sidebar;
        this.sController = sController;
    }

    @ModelAttribute
    public void addLeftSidebarAttributes(Model model, Locale locale, HttpSession session) {
        sController.throwDataToTopSidebar(session);
        sidebar.setupLeftSidebar(model, locale);
    }

    @ModelAttribute
    public void addDashboardAttributes(Model model, Locale locale) {
        sidebar.setupDashboard(model, locale);
    }

    @ModelAttribute
    public void addCreateUserPageAttributes(Model model, Locale locale) {
        sidebar.setupCreateUserPage(model, locale);
    }

    @ModelAttribute
    public void addEditUserPageAttributes(Model model, Locale locale) {
        sidebar.setupEditUserPage(model, locale);
    }

    @ModelAttribute
    public void addUserDetailPageAttributes(Model model, Locale locale) {
        sidebar.setupUsersDetailPage(model, locale);
    }

    @ModelAttribute
    public void addUserListPageAttributes(Model model, Locale locale) {
        sidebar.setupUserListPage(model, locale);
    }
}
