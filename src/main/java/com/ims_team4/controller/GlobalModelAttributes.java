package com.ims_team4.controller;

import com.ims_team4.controller.utils.SessionController;
import com.ims_team4.controller.utils.SetupSidebar;
import com.ims_team4.dto.EmployeeDTO;
import jakarta.servlet.http.HttpSession;
import org.jetbrains.annotations.NotNull;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Logger;

@ControllerAdvice
// HoanTX
public class GlobalModelAttributes {
    private final SetupSidebar sidebar;
    private final SessionController sController;
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public GlobalModelAttributes(SetupSidebar sidebar, SessionController sController) {
        this.sidebar = sidebar;
        this.sController = sController;
    }

    @ModelAttribute
    public void addAttributes(Model model, Locale locale, HttpSession session) {
        throwDataToTopSidebar(session);
        Map<String, Consumer<Model>> map = createAttributes(model, locale);

        for (Map.Entry<String, Consumer<Model>> key : map.entrySet()) {
            logger.info("setup page's attributes: " + key.getKey());
            key.getValue().accept(model);
        }
    }

    private void throwDataToTopSidebar(@NotNull HttpSession session) {
        EmployeeDTO employeeDTO = sController.getEntityFromSession(session);
        logger.info("throwDataToTopSidebar: " + employeeDTO.toString());
        session.setAttribute("account", employeeDTO);
    }

    @NotNull
    private Map<String, Consumer<Model>> createAttributes(Model model, Locale locale) {
        Map<String, Consumer<Model>> map = new HashMap<>();
        map.put("loginPage", m -> sidebar.setupLoginPage(model, locale));
        map.put("leftSidebar", m -> sidebar.setupLeftSidebar(model, locale));
        map.put("dashboard", m -> sidebar.setupDashboard(model, locale));
        map.put("resetPassword", m -> sidebar.setupResetPasswordPage(model, locale));
        map.put("logout", m -> sidebar.setupLogoutPage(model, locale));
        map.put("accessDenied", m -> sidebar.setupAccessDeniedPage(model, locale));
        map.put("page404", m -> sidebar.setup404ErrorPage(model, locale));
        map.put("topSidebar", m -> sidebar.setupTopSidebar(model, locale));

        // user
        map.put("createUser", m -> sidebar.setupCreateUserPage(model, locale));
        map.put("editUser", m -> sidebar.setupEditUserPage(model, locale));
        map.put("userDetails", m -> sidebar.setupUsersDetailPage(model, locale));
        map.put("userList", m -> sidebar.setupUserListPage(model, locale));

        // offer
        map.put("adminOffer", m -> sidebar.setupAdminOfferPage(model, locale));
        map.put("candidateOffer", m -> sidebar.setupCandidateOfferPage(model, locale));
        map.put("createOffer", m -> sidebar.setupCreateOfferPage(model, locale));
        map.put("editOffer", m -> sidebar.setupEditOfferPage(model, locale));
        map.put("managerOffer", m -> sidebar.setupManagerOfferPage(model, locale));
        map.put("offerDetails", m -> sidebar.setupOfferDetailsPage(model, locale));
        map.put("popupScreen", m -> sidebar.setupPopupScreen(model, locale));
        map.put("searchOffer", m -> sidebar.setupSearchOfferPage(model, locale));
        map.put("chat", m -> sidebar.setupChatPage(model, locale));

        // candidate
        map.put("candidateDetails", m -> sidebar.setupCandidateDetailPage(model, locale));
        map.put("candidatesList", m -> sidebar.setupCandidateListPage(model, locale));
        map.put("createCandidate", m -> sidebar.setupCreateCandidatePage(model, locale));
        map.put("editCandidate", m -> sidebar.setupEditCandidatePage(model, locale));

        // jobs
        map.put("jobAdmin", m -> sidebar.setupJobAdminPage(model, locale));
        map.put("jobDetailsAdmin", m -> sidebar.setupJobDetailsAdminPage(model, locale));
        map.put("jobsInterview", m -> sidebar.setupJobsInterviewPage(model, locale));
        map.put("createJob", m -> sidebar.setupCreateJobsPage(model, locale));
        map.put("deleteJob", m -> sidebar.setupDeleteJobPage(model, locale));
        map.put("editJob", m -> sidebar.setupEditJobPage(model, locale));

        // interview
        map.put("interviewList", m -> sidebar.setupInterviewListPage(model, locale));
        map.put("createInterview", m -> sidebar.setupCreateInterviewPage(model, locale));
        map.put("interviewDetails", m -> sidebar.setupInterviewDetailPage(model, locale));
        map.put("createInterview", m -> sidebar.setupEditInterviewPage(model, locale));
        map.put("submitResult", m -> sidebar.setupSubmitResultPage(model, locale));
        return map;
    }
}