package com.ims_team4.controller.utils;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.Locale;

@Component
// HoanTX
public class SetupGlobalAttributes {
    private final MessageSource source;

    public SetupGlobalAttributes(@Qualifier("messageSource") MessageSource source) {
        this.source = source;
    }

    // <editor-fold> desc="User"
    public void setupCreateUserPage(@NotNull Model model, Locale locale) {
        model.addAttribute("create_new_user", source.getMessage("create-new-user", null, locale));
        model.addAttribute("avatar_title", source.getMessage("avatar_title", null, locale));
        model.addAttribute("avatar_preview", source.getMessage("avatar_preview", null, locale));
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
        model.addAttribute("grid_view", source.getMessage("grid_view", null, locale));
        model.addAttribute("table_view", source.getMessage("table_view", null, locale));
        model.addAttribute("user_not_found", source.getMessage("user_not_found", null, locale));
        model.addAttribute("user_not_found_suggest", source.getMessage("user_not_found_suggest", null, locale));
    }

    public void setupUserProfilePage(@NotNull Model model, Locale locale) {
        model.addAttribute("user_profile", source.getMessage("user_profile", null, locale));
    }
    // </editor-fold>

    // <editor-fold> desc="Common"
    public void setupLoginPage(@NotNull Model model, Locale locale) {
        model.addAttribute("loginTitle", source.getMessage("login_title", null, locale));
        model.addAttribute("loginButton", source.getMessage("login_button", null, locale));
        model.addAttribute("otherLoginMethod", source.getMessage("other_way_to_login", null, locale));
        model.addAttribute("rememberMe", source.getMessage("remember_me", null, locale));
        model.addAttribute("username", source.getMessage("email_input_field", null, locale));
        model.addAttribute("password", source.getMessage("password_input_field", null, locale));
        model.addAttribute("forgotPassword", source.getMessage("reset_password", null, locale));
        model.addAttribute("choose_language", source.getMessage("choose_language", null, locale));
        model.addAttribute("google_title", source.getMessage("google_title", null, locale));
    }

    public void setupLeftSidebar(@NotNull Model model, Locale locale) {
        model.addAttribute("homeLeftSidebar", source.getMessage("home", null, locale));
        model.addAttribute("candidateLeftSidebar", source.getMessage("candidate", null, locale));
        model.addAttribute("jobLeftSidebar", source.getMessage("job", null, locale));
        model.addAttribute("interviewLeftSidebar", source.getMessage("interview", null, locale));
        model.addAttribute("offerLeftSidebar", source.getMessage("offer", null, locale));
        model.addAttribute("userManagementLeftSidebar", source.getMessage("user_management", null, locale));
    }

    public void setupTopSidebar(@NotNull Model model, Locale locale) {
        model.addAttribute("setting_title", source.getMessage("setting_title", null, locale));
        model.addAttribute("language_title", source.getMessage("language_title", null, locale));
        model.addAttribute("view_all_notification", source.getMessage("view_all_notification", null, locale));
    }

    public void setupDashboard(@NotNull Model model, Locale locale) {
        model.addAttribute("home_title", source.getMessage("home_title", null, locale));
        model.addAttribute("dashboard_title", source.getMessage("dashboard_title", null, locale));
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
        model.addAttribute("language", locale.getLanguage());
    }

    public void setupAccessDeniedPage(@NotNull Model model, Locale locale) {
        model.addAttribute("access_denied_title", source.getMessage("access_denied_title", null, locale));
        model.addAttribute("h1_title", source.getMessage("h1_title", null, locale));
        model.addAttribute("h3_title", source.getMessage("h3_title", null, locale));
        model.addAttribute("p_title", source.getMessage("p_title", null, locale));
        model.addAttribute("a_title", source.getMessage("a_title", null, locale));
    }

    public void setup404ErrorPage(@NotNull Model model, Locale locale) {
        model.addAttribute("error_404_title", source.getMessage("404_error_title", null, locale));
        model.addAttribute("error_404_h1", source.getMessage("404_error_h1", null, locale));
        model.addAttribute("error_404_h2", source.getMessage("404_error_h2", null, locale));
        model.addAttribute("error_404_p", source.getMessage("404_error_p", null, locale));
        model.addAttribute("error_404_a", source.getMessage("404_error_a", null, locale));
    }

    public void setupResetPasswordPage(@NotNull Model model, Locale locale) {
        model.addAttribute("reset_password_title", source.getMessage("reset_password_title", null, locale));
        model.addAttribute("enter_email_title", source.getMessage("enter_email_title", null, locale));
        model.addAttribute("send_verify_code", source.getMessage("send_verify_code", null, locale));
        model.addAttribute("enter_verified_code", source.getMessage("enter_verified_code", null, locale));
        model.addAttribute("code_title", source.getMessage("code_title", null, locale));
        model.addAttribute("verify_btn", source.getMessage("verify_btn", null, locale));
        model.addAttribute("change_your_password_btn", source.getMessage("change_your_password_btn", null, locale));
        model.addAttribute("rewrite_password_label", source.getMessage("rewrite_password_label", null, locale));
        model.addAttribute("password_input_field", source.getMessage("password_input_field", null, locale));
    }

    public void setupLogoutPage(@NotNull Model model, Locale locale) {
        model.addAttribute("logout_title", source.getMessage("logout_title", null, locale));
        model.addAttribute("ok_btn", source.getMessage("ok_btn", null, locale));
        model.addAttribute("confirm_logout", source.getMessage("confirm_logout", null, locale));
        model.addAttribute("logout_title_h5", source.getMessage("logout_title_h5", null, locale));
    }
    // </editor-fold>

    // <editor-fold> desc="Offer"
    public void setupAdminOfferPage(@NotNull Model model, Locale locale) {
        model.addAttribute("admin_offer_title", source.getMessage("admin_offer_title", null, locale));
        model.addAttribute("offer_list", source.getMessage("offer_list", null, locale));
        model.addAttribute("search", source.getMessage("search", null, locale));
        model.addAttribute("department", source.getMessage("department", null, locale));
        commonAttributes1(model, locale);
        commonAttributes3(model, locale);
        model.addAttribute("department", source.getMessage("department", null, locale));
        model.addAttribute("note", source.getMessage("note", null, locale));
        model.addAttribute("status", source.getMessage("status", null, locale));
        model.addAttribute("action", source.getMessage("action", null, locale));
    }

    public void setupCandidateOfferPage(@NotNull Model model, Locale locale) {
        model.addAttribute("offer_detail_title", source.getMessage("offer_detail_title", null, locale));
        model.addAttribute("created_on", source.getMessage("created_on", null, locale));
        model.addAttribute("last_updated_by", source.getMessage("last_updated_by", null, locale));
        model.addAttribute("candidate", source.getMessage("candidate", null, locale));
        model.addAttribute("positionTitle", source.getMessage("positionTitle", null, locale));
        model.addAttribute("approver_title", source.getMessage("approver_title", null, locale));
        model.addAttribute("interview_info", source.getMessage("interview_info", null, locale));
        model.addAttribute("interviewTitle", source.getMessage("interview", null, locale));
        model.addAttribute("contract_period", source.getMessage("contract_period", null, locale));
        model.addAttribute("from_label", source.getMessage("from_label", null, locale));
        model.addAttribute("to_lower_case_title", source.getMessage("to_lower_case_title", null, locale));
        model.addAttribute("note", source.getMessage("note", null, locale));
        model.addAttribute("status", source.getMessage("status", null, locale));
        model.addAttribute("contract_type", source.getMessage("contract_type", null, locale));
        model.addAttribute("level_title", source.getMessage("level_title", null, locale));
        model.addAttribute("department", source.getMessage("department", null, locale));
        model.addAttribute("recruiter", source.getMessage("recruiter", null, locale));
        model.addAttribute("due_date", source.getMessage("due_date", null, locale));
        model.addAttribute("basic_salary", source.getMessage("basic_salary", null, locale));
        model.addAttribute("notification_reply_email", source.getMessage("notification_reply_email", null, locale));
        model.addAttribute("interview", source.getMessage("interview", null, locale));
        model.addAttribute("interview_note", source.getMessage("interview_note", null, locale));
    }

    public void setupCreateOfferPage(@NotNull Model model, Locale locale) {
        model.addAttribute("create_offer_title", source.getMessage("create_offer_title", null, locale));
        model.addAttribute("select_candidate_name", source.getMessage("select_candidate_name", null, locale));
        model.addAttribute("select_position_name", source.getMessage("select_position_name", null, locale));
        model.addAttribute("select_approver_name", source.getMessage("select_approver_name", null, locale));
        model.addAttribute("select_interview_schedule", source.getMessage("select_interview_schedule", null, locale));
        model.addAttribute("select_type_contract", source.getMessage("select_type_contract", null, locale));
        model.addAttribute("select_level", source.getMessage("select_level", null, locale));
        model.addAttribute("select_department", source.getMessage("select_department", null, locale));
        model.addAttribute("select_recruiter", source.getMessage("select_recruiter", null, locale));
        model.addAttribute("assigned_to_me", source.getMessage("assigned_to_me", null, locale));
        model.addAttribute("contract_period", source.getMessage("contract_period", null, locale));
        commonAttributes2(model, locale);
        model.addAttribute("interviewTitle", source.getMessage("interview", null, locale));
        model.addAttribute("note", source.getMessage("note", null, locale));
        model.addAttribute("contract_type", source.getMessage("contract_type", null, locale));
        model.addAttribute("level_title", source.getMessage("level_title", null, locale));
        model.addAttribute("departmentTitle", source.getMessage("department", null, locale));
        model.addAttribute("recruiter", source.getMessage("recruiter", null, locale));
        model.addAttribute("due_date", source.getMessage("due_date", null, locale));
        model.addAttribute("basic_salary", source.getMessage("basic_salary", null, locale));
        model.addAttribute("cancelBtn", source.getMessage("cancelBtn", null, locale));
        model.addAttribute("submitBtn", source.getMessage("submitBtn", null, locale));
    }

    public void setupEditOfferPage(@NotNull Model model, Locale locale) {
        model.addAttribute("edit_offer_title", source.getMessage("edit_offer_title", null, locale));
        model.addAttribute("offer_detail_title", source.getMessage("offer_detail_title", null, locale));
        model.addAttribute("select_interview_schedule", source.getMessage("select_interview_schedule", null, locale));
        commonAttributes2(model, locale);
        model.addAttribute("contract_period", source.getMessage("contract_period", null, locale));
        model.addAttribute("note", source.getMessage("note", null, locale));
        model.addAttribute("interviewTitle", source.getMessage("interview", null, locale));
        model.addAttribute("status", source.getMessage("status", null, locale));
        model.addAttribute("contract_type", source.getMessage("contract_type", null, locale));
        model.addAttribute("level_title", source.getMessage("level_title", null, locale));
        model.addAttribute("departmentTitle", source.getMessage("department", null, locale));
        model.addAttribute("recruiter", source.getMessage("recruiter", null, locale));
        model.addAttribute("assigned_to_me", source.getMessage("assigned_to_me", null, locale));
        model.addAttribute("due_date", source.getMessage("due_date", null, locale));
        model.addAttribute("basic_salary", source.getMessage("basic_salary", null, locale));
        model.addAttribute("submitBtn", source.getMessage("submitBtn", null, locale));
        model.addAttribute("cancelBtn", source.getMessage("cancelBtn", null, locale));
    }

    public void setupManagerOfferPage(@NotNull Model model, Locale locale) {
        model.addAttribute("manager_offer_title", source.getMessage("manager_offer_title", null, locale));
        model.addAttribute("chat_title", source.getMessage("chat_title", null, locale));
        model.addAttribute("offer_list", source.getMessage("offer_list", null, locale));
        model.addAttribute("search", source.getMessage("search", null, locale));
        model.addAttribute("candidate_name", source.getMessage("candidate_name", null, locale));
        commonAttributes1(model, locale);
        model.addAttribute("email", source.getMessage("email", null, locale));
        model.addAttribute("approver_title", source.getMessage("approver_title", null, locale));
        model.addAttribute("note", source.getMessage("note", null, locale));
        model.addAttribute("action", source.getMessage("action", null, locale));
    }

    public void setupOfferDetailsPage(@NotNull Model model, Locale locale) {
        model.addAttribute("chat_with_manager", source.getMessage("chat_with_manager", null, locale));
        model.addAttribute("chat_with_recruiter", source.getMessage("chat_with_recruiter", null, locale));
        model.addAttribute("approve_link", source.getMessage("approve_link", null, locale));
        model.addAttribute("reject_link", source.getMessage("reject_link", null, locale));
        model.addAttribute("mark_as_sent", source.getMessage("mark_as_sent", null, locale));
        model.addAttribute("accept_link", source.getMessage("accept_link", null, locale));
        model.addAttribute("decline_link", source.getMessage("decline_link", null, locale));
        model.addAttribute("offer_detail_title", source.getMessage("offer_detail_title", null, locale));
        commonAttributes2(model, locale);
        model.addAttribute("interviewerTitle", source.getMessage("interviewerTitle", null, locale));
        model.addAttribute("contract_period", source.getMessage("contract_period", null, locale));
        model.addAttribute("from_label", source.getMessage("from_label", null, locale));
        model.addAttribute("to_label", source.getMessage("to_label", null, locale));
        model.addAttribute("note", source.getMessage("note", null, locale));
        model.addAttribute("status", source.getMessage("status", null, locale));
        model.addAttribute("contract_type", source.getMessage("contract_type", null, locale));
        model.addAttribute("level_title", source.getMessage("level_title", null, locale));
        model.addAttribute("departmentTitle", source.getMessage("department", null, locale));
        model.addAttribute("due_date", source.getMessage("due_date", null, locale));
        model.addAttribute("recruiter", source.getMessage("recruiter", null, locale));
        model.addAttribute("basic_salary", source.getMessage("basic_salary", null, locale));
        model.addAttribute("editBtn", source.getMessage("editBtn", null, locale));
        model.addAttribute("created_on", source.getMessage("created_on", null, locale));
        model.addAttribute("last_updated_by", source.getMessage("last_updated_by", null, locale));
        model.addAttribute("recruiter_owner", source.getMessage("recruiter_owner", null, locale));
        model.addAttribute("back_link", source.getMessage("back_link", null, locale));
    }

    public void setupPopupScreen(@NotNull Model model, Locale locale) {
        model.addAttribute("show_notification", source.getMessage("show_notification", null, locale));
        model.addAttribute("notification_title", source.getMessage("notification_title", null, locale));
        model.addAttribute("popup_title", source.getMessage("popup_title", null, locale));
    }

    public void setupSearchOfferPage(@NotNull Model model, Locale locale) {
        model.addAttribute("search_offer_title", source.getMessage("search_offer_title", null, locale));
        model.addAttribute("result_offer", source.getMessage("result_offer", null, locale));
        model.addAttribute("no_item_matches", source.getMessage("no_item_matches", null, locale));
        model.addAttribute("offer_list", source.getMessage("offer_list", null, locale));
        model.addAttribute("departmentTitle", source.getMessage("department", null, locale));
        model.addAttribute("statusTitle", source.getMessage("status", null, locale));
        model.addAttribute("search", source.getMessage("search", null, locale));
        model.addAttribute("chat_title", source.getMessage("chat_title", null, locale));
        model.addAttribute("exportBtn", source.getMessage("exportBtn", null, locale));
        model.addAttribute("export_offer_btn", source.getMessage("export_offer_btn", null, locale));
        model.addAttribute("from_label", source.getMessage("from_label", null, locale));
        model.addAttribute("to_label", source.getMessage("to_label", null, locale));
        commonAttributes3(model, locale);
        model.addAttribute("note", source.getMessage("note", null, locale));
        model.addAttribute("action", source.getMessage("action", null, locale));
    }

    public void setupChatPage(@NotNull Model model, Locale locale) {
        model.addAttribute("message_title", source.getMessage("message_title", null, locale));
        model.addAttribute("search_on_messenger", source.getMessage("search_on_messenger", null, locale));
        model.addAttribute("you_title", source.getMessage("you_title", null, locale));
        model.addAttribute("no_message_noti", source.getMessage("no_message_noti", null, locale));
        model.addAttribute("type_your_message", source.getMessage("type_your_message", null, locale));
    }
    // </editor-fold>

    // <editor-fold> desc="Candidate"
    public void setupCandidateDetailPage(@NotNull Model model, Locale locale) {
        model.addAttribute("candidate_details", source.getMessage("candidate_details", null, locale));
        model.addAttribute("candidate_list", source.getMessage("candidate_list", null, locale));
        model.addAttribute("personal_information", source.getMessage("personal_information", null, locale));
        model.addAttribute("professional_information", source.getMessage("professional_information", null, locale));
        model.addAttribute("cv_attachment", source.getMessage("cv_attachment", null, locale));
        model.addAttribute("no_cv", source.getMessage("no_cv", null, locale));
        model.addAttribute("current_position", source.getMessage("current_position", null, locale));
        model.addAttribute("year_of_experience", source.getMessage("year_of_experience", null, locale));
        model.addAttribute("skill", source.getMessage("skill", null, locale));
        model.addAttribute("ban_title", source.getMessage("ban_title", null, locale));
        model.addAttribute("confirm_delete", source.getMessage("confirm_delete", null, locale));
        model.addAttribute("confirm_delete_notification",
                source.getMessage("confirm_delete_notification", null, locale));
        model.addAttribute("confirm_ban", source.getMessage("confirm_ban", null, locale));
        model.addAttribute("confirm_ban_notification", source.getMessage("confirm_ban_notification", null, locale));
        model.addAttribute("highest_level_title", source.getMessage("highest_level_title", null, locale));
        model.addAttribute("candidate_title", source.getMessage("candidate", null, locale));
        model.addAttribute("full_name", source.getMessage("full_name", null, locale));
        model.addAttribute("phone", source.getMessage("phone", null, locale));
        model.addAttribute("address", source.getMessage("address", null, locale));
        model.addAttribute("gender", source.getMessage("gender", null, locale));
        model.addAttribute("male", source.getMessage("male", null, locale));
        model.addAttribute("female", source.getMessage("female", null, locale));
        model.addAttribute("other_gender", source.getMessage("other_gender", null, locale));
        model.addAttribute("statusTitle", source.getMessage("status", null, locale));
        model.addAttribute("recruiter", source.getMessage("recruiter", null, locale));
        model.addAttribute("note", source.getMessage("note", null, locale));
        model.addAttribute("back_to_list", source.getMessage("back-to-list", null, locale));
        model.addAttribute("cancelBtn", source.getMessage("cancelBtn", null, locale));
        model.addAttribute("editBtn", source.getMessage("editBtn", null, locale));
        model.addAttribute("deleteBtn", source.getMessage("deleteBtn", null, locale));
        model.addAttribute("download_cv", source.getMessage("download_cv", null, locale));
        model.addAttribute("bannedSuccess", source.getMessage("bannedSuccess", null, locale));
        model.addAttribute("error_title", source.getMessage("error_title", null, locale));
        model.addAttribute("invalidDateFormat", source.getMessage("invalidDateFormat", null, locale));
        model.addAttribute("send_invitation", source.getMessage("send_invitation", null, locale));
    }

    public void setupCandidateListPage(@NotNull Model model, Locale locale) {
        model.addAttribute("candidate_list_title", source.getMessage("candidate_list_title", null, locale));
        model.addAttribute("all_status", source.getMessage("all_status", null, locale));
        model.addAttribute("name_title", source.getMessage("name_title", null, locale));
        model.addAttribute("experience_title", source.getMessage("experience_title", null, locale));
        model.addAttribute("view_title", source.getMessage("view_title", null, locale));
        model.addAttribute("details_title", source.getMessage("details_title", null, locale));
        model.addAttribute("unban_title", source.getMessage("unban_title", null, locale));
        model.addAttribute("previous_title", source.getMessage("previous_title", null, locale));
        model.addAttribute("next_title", source.getMessage("next_title", null, locale));
        model.addAttribute("confirm_action", source.getMessage("confirm_action", null, locale));
        model.addAttribute("confirm_change_status", source.getMessage("confirm_change_status", null, locale));
        model.addAttribute("confirm_btn", source.getMessage("confirm_btn", null, locale));
        model.addAttribute("candidate_title", source.getMessage("candidate", null, locale));
        model.addAttribute("add_new", source.getMessage("add_new", null, locale));
        model.addAttribute("search", source.getMessage("search", null, locale));
        model.addAttribute("skill", source.getMessage("skill", null, locale));
        model.addAttribute("positionTitle", source.getMessage("positionTitle", null, locale));
        model.addAttribute("statusTitle", source.getMessage("status", null, locale));
        model.addAttribute("action", source.getMessage("action", null, locale));
        model.addAttribute("editBtn", source.getMessage("editBtn", null, locale));
        model.addAttribute("deleteBtn", source.getMessage("deleteBtn", null, locale));
        model.addAttribute("ban_title", source.getMessage("ban_title", null, locale));
        model.addAttribute("cancelBtn", source.getMessage("cancelBtn", null, locale));
        model.addAttribute("confirm_delete", source.getMessage("confirm_delete", null, locale));
        model.addAttribute("confirm_delete_notification",
                source.getMessage("confirm_delete_notification", null, locale));
        model.addAttribute("lang", locale);
        model.addAttribute("confirm_unban_message", source.getMessage("confirm_unban_message", null, locale));
        model.addAttribute("confirm_ban_message", source.getMessage("confirm_ban_message", null, locale));
        model.addAttribute("candidate_unbanned", source.getMessage("candidate_unbanned", null, locale));
        model.addAttribute("total_candidate", source.getMessage("total_candidate", null, locale));
        model.addAttribute("open_candidate", source.getMessage("open_candidate", null, locale));
        model.addAttribute("waiting_for_interview", source.getMessage("waiting_for_interview", null, locale));
        model.addAttribute("pass_interview_candidate", source.getMessage("pass_interview_candidate", null, locale));
    }

    public void setupCreateCandidatePage(@NotNull Model model, Locale locale) {
        model.addAttribute("create_candidate_title", source.getMessage("create_candidate_title", null, locale));
        model.addAttribute("personal_information_title", source.getMessage("personal_information_title", null, locale));
        model.addAttribute("professional_information_title",
                source.getMessage("professional_information_title", null, locale));
        model.addAttribute("select_gender", source.getMessage("select_gender", null, locale));
        model.addAttribute("select_position", source.getMessage("select_position", null, locale));
        model.addAttribute("select_skill", source.getMessage("select_skill", null, locale));
        model.addAttribute("select_education", source.getMessage("select_education", null, locale));
        model.addAttribute("open_title", source.getMessage("open_title", null, locale));
        model.addAttribute("banned_title", source.getMessage("banned_title", null, locale));
        model.addAttribute("yes_btn", source.getMessage("yes_btn", null, locale));
        model.addAttribute("no_btn", source.getMessage("no_btn", null, locale));
        model.addAttribute("candidate_list_title", source.getMessage("candidate_list_title", null, locale));
        model.addAttribute("full_name", source.getMessage("full_name", null, locale));
        model.addAttribute("dob", source.getMessage("dob", null, locale));
        model.addAttribute("phone", source.getMessage("phone", null, locale));
        model.addAttribute("input_phone", source.getMessage("input_phone", null, locale));
        commonAttribute4(model, locale);
        model.addAttribute("other_gender", source.getMessage("other_gender", null, locale));
        model.addAttribute("cv_attachment", source.getMessage("cv_attachment", null, locale));
        model.addAttribute("positionTitle", source.getMessage("positionTitle", null, locale));
        model.addAttribute("recruiter", source.getMessage("recruiter", null, locale));
        model.addAttribute("assigned_to_me", source.getMessage("assigned_to_me", null, locale));
        model.addAttribute("note", source.getMessage("note", null, locale));
        model.addAttribute("statusTitle", source.getMessage("status", null, locale));
        model.addAttribute("year_of_experience", source.getMessage("year_of_experience", null, locale));
        model.addAttribute("highest_level_title", source.getMessage("highest_level_title", null, locale));
        model.addAttribute("submitBtn", source.getMessage("submitBtn", null, locale));
        model.addAttribute("cancelBtn", source.getMessage("cancelBtn", null, locale));
        model.addAttribute("confirm_btn", source.getMessage("confirm_btn", null, locale));
        model.addAttribute("input_yoe", source.getMessage("input_yoe", null, locale));
        model.addAttribute("confirm_create_candidate", source.getMessage("confirm_create_candidate", null, locale));
        model.addAttribute("confirm_submit", source.getMessage("confirm_submit", null, locale));
        model.addAttribute("age_title", source.getMessage("age_title", null, locale));
    }

    public void setupEditCandidatePage(@NotNull Model model, Locale locale) {
        model.addAttribute("edit_candidate_title", source.getMessage("edit_candidate_title", null, locale));
        model.addAttribute("confirm_update", source.getMessage("confirm_update", null, locale));
        model.addAttribute("confirm_update_btn", source.getMessage("confirm_update_btn", null, locale));
        model.addAttribute("confirm_exit", source.getMessage("confirm_exit", null, locale));
        model.addAttribute("leaving_page_alert", source.getMessage("leaving_page_alert", null, locale));
        model.addAttribute("save_changes", source.getMessage("save_changes", null, locale));
        model.addAttribute("cv_attachment_title", source.getMessage("cv_attachment_title", null, locale));
        model.addAttribute("not_updating_blank_field", source.getMessage("not_updating_blank_field", null, locale));
        model.addAttribute("yes_btn", source.getMessage("yes_btn", null, locale));
        model.addAttribute("no_btn", source.getMessage("no_btn", null, locale));
        model.addAttribute("note", source.getMessage("note", null, locale));
        model.addAttribute("recruiter", source.getMessage("recruiter", null, locale));
        model.addAttribute("select_skill", source.getMessage("select_skill", null, locale));
        model.addAttribute("skill_title", source.getMessage("skill", null, locale));
        model.addAttribute("highest_level_title", source.getMessage("highest_level_title", null, locale));
        model.addAttribute("year_of_experience", source.getMessage("year_of_experience", null, locale));
        model.addAttribute("statusTitle", source.getMessage("status", null, locale));
        model.addAttribute("current_position", source.getMessage("current_position", null, locale));
        model.addAttribute("personal_information", source.getMessage("personal_information", null, locale));
        model.addAttribute("professional_information", source.getMessage("professional_information", null, locale));
        model.addAttribute("address", source.getMessage("address", null, locale));
        model.addAttribute("dob", source.getMessage("dob", null, locale));
        model.addAttribute("phone", source.getMessage("phone", null, locale));
        model.addAttribute("age_title", source.getMessage("age_title", null, locale));
        model.addAttribute("validatedAgeAlert", source.getMessage("validatedAgeAlert", null, locale));
        model.addAttribute("requiredFullname", source.getMessage("requiredFullname", null, locale));
        model.addAttribute("validEmailFormat", source.getMessage("validEmailFormat", null, locale));
        model.addAttribute("validPhoneFormat", source.getMessage("validPhoneFormat", null, locale));
        model.addAttribute("requiredAddress", source.getMessage("requiredAddress", null, locale));
        model.addAttribute("requiredExperience", source.getMessage("requiredExperience", null, locale));
        model.addAttribute("experienceLimit", source.getMessage("experienceLimit", null, locale));
        model.addAttribute("cvErrorMessage", source.getMessage("cvErrorMessage", null, locale));
        model.addAttribute("editCandidateFailed", source.getMessage("editCandidateFailed", null, locale));
    }
    // </editor-fold>

    // <editor-fold> desc="Job"
    public void setupJobAdminPage(@NotNull Model model, Locale locale) {
        model.addAttribute("jobs_admin_title", source.getMessage("jobs_admin_title", null, locale));
        model.addAttribute("job_list", source.getMessage("job_list", null, locale));
        model.addAttribute("all_option", source.getMessage("all_option", null, locale));
        model.addAttribute("open_option", source.getMessage("open_option", null, locale));
        model.addAttribute("closed_option", source.getMessage("closed_option", null, locale));
        model.addAttribute("import_btn", source.getMessage("import_btn", null, locale));
        model.addAttribute("job_title", source.getMessage("job_title", null, locale));
        model.addAttribute("required_skill_title", source.getMessage("required_skill_title", null, locale));
        model.addAttribute("start_date_title", source.getMessage("start_date_title", null, locale));
        model.addAttribute("end_date_title", source.getMessage("end_date_title", null, locale));
        model.addAttribute("no_job_found", source.getMessage("no_job_found", null, locale));
        model.addAttribute("confirm_delete_alert", source.getMessage("confirm_delete_alert", null, locale));
        model.addAttribute("confirm_yes_and_delete", source.getMessage("confirm_yes_and_delete", null, locale));
        model.addAttribute("delete_successfully", source.getMessage("delete_successfully", null, locale));
        model.addAttribute("search_by_title", source.getMessage("search_by_title", null, locale));
        model.addAttribute("total_job", source.getMessage("total_job", null, locale));
        model.addAttribute("job_closed", source.getMessage("job_closed", null, locale));
        model.addAttribute("job_open", source.getMessage("job_open", null, locale));
//        model.addAttribute("job_open", source.getMessage("job_closed", null, locale));

    }

    public void setupJobDetailsAdminPage(@NotNull Model model, Locale locale) {
        model.addAttribute("jobs_admin_details_title", source.getMessage("jobs_admin_details_title", null, locale));
        model.addAttribute("job_details_title", source.getMessage("job_details_title", null, locale));
        model.addAttribute("salary_range_title", source.getMessage("salary_range_title", null, locale));
        model.addAttribute("to_toUpperCase", source.getMessage("to_toUpperCase", null, locale));
        model.addAttribute("benefits_label", source.getMessage("benefits_label", null, locale));
        model.addAttribute("working_address_title", source.getMessage("working_address_title", null, locale));
        model.addAttribute("description_title", source.getMessage("description_title", null, locale));
    }

    public void setupJobsInterviewPage(@NotNull Model model, Locale locale) {
        model.addAttribute("interview_job_title", source.getMessage("interview_job_title", null, locale));
        model.addAttribute("account_setting", source.getMessage("account_setting", null, locale));
        model.addAttribute("appearance_link", source.getMessage("appearance_link", null, locale));
        model.addAttribute("notification_setting", source.getMessage("notification_setting", null, locale));
        model.addAttribute("help_center", source.getMessage("help_center", null, locale));
        model.addAttribute("user_name_title", source.getMessage("user_name_title", null, locale));
        model.addAttribute("my_profile", source.getMessage("my_profile", null, locale));
        model.addAttribute("activity_log", source.getMessage("activity_log", null, locale));
        model.addAttribute("grid_view", source.getMessage("grid_view", null, locale));
        model.addAttribute("table_view", source.getMessage("table_view", null, locale));
        model.addAttribute("search", source.getMessage("search", null, locale));
        model.addAttribute("enter_job_title", source.getMessage("enter_job_title", null, locale));

        model.addAttribute("departmentTitle", source.getMessage("department", null, locale));
        model.addAttribute("logout_title", source.getMessage("logout_title", null, locale));
        model.addAttribute("statusTitle", source.getMessage("status", null, locale));
        model.addAttribute("closed_option", source.getMessage("closed_option", null, locale));
        model.addAttribute("open_title", source.getMessage("open_title", null, locale));
        model.addAttribute("job_title", source.getMessage("job_title", null, locale));
        model.addAttribute("skillTitle", source.getMessage("skill", null, locale));
        model.addAttribute("start_date_title", source.getMessage("start_date_title", null, locale));
        model.addAttribute("end_date_title", source.getMessage("end_date_title", null, locale));
        model.addAttribute("level_title", source.getMessage("level_title", null, locale));
        model.addAttribute("statusTitle", source.getMessage("status", null, locale));
        model.addAttribute("action", source.getMessage("action", null, locale));
        model.addAttribute("no_job_found", source.getMessage("no_job_found", null, locale));
        model.addAttribute("previous_title", source.getMessage("previous_title", null, locale));
        model.addAttribute("next_title", source.getMessage("next_title", null, locale));
        model.addAttribute("all_statuses", source.getMessage("all_statuses", null, locale));
    }

    public void setupCreateJobsPage(@NotNull Model model, Locale locale) {
        model.addAttribute("job_create_title", source.getMessage("job_create_title", null, locale));
    }

    public void setupDeleteJobPage(@NotNull Model model, Locale locale) {
        model.addAttribute("delete_job_title", source.getMessage("delete_job_title", null, locale));
        model.addAttribute("confirm_delete_job_title", source.getMessage("confirm_delete_job_title", null, locale));
        model.addAttribute("confirm_delete_job", source.getMessage("confirm_delete_job", null, locale));
        // model.addAttribute("", source.getMessage("", null, locale));

    }

    public void setupEditJobPage(@NotNull Model model, Locale locale) {
        model.addAttribute("save_btn", source.getMessage("save_btn", null, locale));
        model.addAttribute("edit_job_title", source.getMessage("edit_job_title", null, locale));
    }
    // </editor-fold>

    // <editor-fold> desc="Interview"
    public void setupInterviewListPage(@NotNull Model model, Locale locale) {
        model.addAttribute("interview_list_title", source.getMessage("interview_list_title", null, locale));
        model.addAttribute("interview_schedules", source.getMessage("interview_schedules", null, locale));
        model.addAttribute("interview_schedules_list", source.getMessage("interview_schedules_list", null, locale));
        model.addAttribute("enter_title_location", source.getMessage("enter_title_location", null, locale));
        model.addAttribute("new_title", source.getMessage("new_title", null, locale));
        model.addAttribute("invited_title", source.getMessage("invited_title", null, locale));
        model.addAttribute("interviewed_title", source.getMessage("interviewed_title", null, locale));
        model.addAttribute("canceled_title", source.getMessage("canceled_title", null, locale));
        model.addAttribute("interview_id", source.getMessage("interview_id", null, locale));
        model.addAttribute("create_new_interview", source.getMessage("create_new_interview", null, locale));
        model.addAttribute("id_label", source.getMessage("id_label", null, locale));
        model.addAttribute("title_label", source.getMessage("title_label", null, locale));
        model.addAttribute("schedule_time_title", source.getMessage("schedule_time_title", null, locale));
        model.addAttribute("location_label", source.getMessage("location_label", null, locale));
        model.addAttribute("interview_not_found_suggest",
                source.getMessage("interview_not_found_suggest", null, locale));
        model.addAttribute("searchTitle", source.getMessage("search", null, locale));
        model.addAttribute("loading_title", source.getMessage("loading_title", null, locale));
        model.addAttribute("all_interview", source.getMessage("all_interview", null, locale));
        model.addAttribute("today_title", source.getMessage("today_title", null, locale));
        model.addAttribute("upcoming_title", source.getMessage("upcoming_title", null, locale));
        model.addAttribute("completed_title", source.getMessage("completed_title", null, locale));
        model.addAttribute("edit_interview_title", source.getMessage("edit_interview_title", null, locale));
        model.addAttribute("view_interview_title", source.getMessage("view_interview_title", null, locale));
        model.addAttribute("total_interview", source.getMessage("total_interview", null, locale));
        model.addAttribute("success_rate", source.getMessage("success_rate", null, locale));
    }

    public void setupCreateInterviewPage(@NotNull Model model, Locale locale) {
        model.addAttribute("create_interview_title", source.getMessage("create_interview_title", null, locale));
        model.addAttribute("interview_title", source.getMessage("interview_title", null, locale));
        model.addAttribute("optional_title", source.getMessage("optional_title", null, locale));
        model.addAttribute("candidate_id", source.getMessage("candidate_id", null, locale));
        model.addAttribute("job_id", source.getMessage("job_id", null, locale));
        model.addAttribute("recruiter_owner_id", source.getMessage("recruiter_owner_id", null, locale));
        model.addAttribute("start_time", source.getMessage("start_time", null, locale));
        model.addAttribute("end_time", source.getMessage("end_time", null, locale));
        model.addAttribute("conference_room", source.getMessage("conference_room", null, locale));
        model.addAttribute("meeting_id", source.getMessage("meeting_id", null, locale));
        model.addAttribute("zoom_google_link", source.getMessage("zoom_google_link", null, locale));
        model.addAttribute("basic_infomation", source.getMessage("basic_infomation", null, locale));
        model.addAttribute("enter_schedule_title", source.getMessage("enter_schedule_title", null, locale));
        model.addAttribute("enter_jobID", source.getMessage("enter_jobID", null, locale));
        model.addAttribute("schedule_info", source.getMessage("schedule_info", null, locale));
        model.addAttribute("enter_meet_link", source.getMessage("enter_meet_link", null, locale));
        model.addAttribute("enter_interview_location", source.getMessage("enter_interview_location", null, locale));
        model.addAttribute("assi_info", source.getMessage("assi_info", null, locale));
        model.addAttribute("enter_recruiter", source.getMessage("enter_recruiter", null, locale));
        model.addAttribute("enter_notes", source.getMessage("enter_notes", null, locale));
        model.addAttribute("character", source.getMessage("character", null, locale));

    }

    public void setupInterviewDetailPage(@NotNull Model model, Locale locale) {
        model.addAttribute("interview_details", source.getMessage("interview_details", null, locale));
        model.addAttribute("details_title", source.getMessage("details_title", null, locale));
        model.addAttribute("interview_information", source.getMessage("interview_information", null, locale));
        model.addAttribute("interview_id", source.getMessage("interview_id", null, locale));
        model.addAttribute("result_title", source.getMessage("result_title", null, locale));
        model.addAttribute("no_note_provide", source.getMessage("no_note_provide", null, locale));
        model.addAttribute("send_reminder", source.getMessage("send_reminder", null, locale));
        model.addAttribute("submit_result", source.getMessage("submit_result", null, locale));
        model.addAttribute("message_goes_here", source.getMessage("message_goes_here", null, locale));
        model.addAttribute("interview_title", source.getMessage("interview", null, locale));
        model.addAttribute("normal_back_to_list", source.getMessage("normal_back_to_list", null, locale));
        model.addAttribute("interview_overview", source.getMessage("interview_overview", null, locale));
        model.addAttribute("participants", source.getMessage("participants", null, locale));
        model.addAttribute("location_details", source.getMessage("location_details", null, locale));
    }

    public void setupEditInterviewPage(@NotNull Model model, Locale locale) {
        model.addAttribute("edit_interview", source.getMessage("edit_interview", null, locale));
        model.addAttribute("note_title", source.getMessage("note", null, locale));
        model.addAttribute("candidate_id", source.getMessage("candidate_id", null, locale));
        model.addAttribute("job_id", source.getMessage("job_id", null, locale));
        model.addAttribute("start_time", source.getMessage("start_time", null, locale));
        model.addAttribute("end_time", source.getMessage("end_time", null, locale));
        model.addAttribute("meeting_id", source.getMessage("meeting_id", null, locale));
    }

    public void setupSubmitResultPage(@NotNull Model model, Locale locale) {
        model.addAttribute("submit_result_title", source.getMessage("submit_result_title", null, locale));
        model.addAttribute("select_label", source.getMessage("select_label", null, locale));
        model.addAttribute("passed_title", source.getMessage("passed_title", null, locale));
        model.addAttribute("failed_title", source.getMessage("failed_title", null, locale));
        model.addAttribute("select_result", source.getMessage("select_result", null, locale));
        model.addAttribute("guide_label", source.getMessage("guide_label", null, locale));
        model.addAttribute("submission_tips", source.getMessage("submission_tips", null, locale));
        model.addAttribute("first_tips", source.getMessage("first_tips", null, locale));
        model.addAttribute("second_tips", source.getMessage("second_tips", null, locale));
        model.addAttribute("third_tips", source.getMessage("third_tips", null, locale));
        model.addAttribute("important_label", source.getMessage("important_label", null, locale));
        model.addAttribute("important_content", source.getMessage("important_content", null, locale));

    }
    // </editor-fold>

    // <editor-fold> desc="Notification"
    public void setupNotificationListPage(@NotNull Model model, Locale locale) {
        model.addAttribute("notification_list", source.getMessage("notification_list", null, locale));
        model.addAttribute("notification_label", source.getMessage("notification_label", null, locale));
        model.addAttribute("notification_details", source.getMessage("notification_details", null, locale));
    }
    // </editor-fold>

    // <editor-fold> desc="private method"
    private void commonAttribute4(@NotNull Model model, Locale locale) {
        model.addAttribute("email", source.getMessage("email", null, locale));
        model.addAttribute("input_email", source.getMessage("input_email", null, locale));
        model.addAttribute("address", source.getMessage("address", null, locale));
        model.addAttribute("input_address", source.getMessage("input_address", null, locale));
        model.addAttribute("gender", source.getMessage("gender", null, locale));
        model.addAttribute("male", source.getMessage("male", null, locale));
        model.addAttribute("female", source.getMessage("female", null, locale));
    }

    private void commonAttributes3(@NotNull Model model, Locale locale) {
        model.addAttribute("submitBtn", source.getMessage("submitBtn", null, locale));
        model.addAttribute("cancelBtn", source.getMessage("cancelBtn", null, locale));
        model.addAttribute("candidate_name", source.getMessage("candidate_name", null, locale));
        model.addAttribute("email", source.getMessage("email", null, locale));
        model.addAttribute("approver_title", source.getMessage("approver_title", null, locale));
    }

    private void commonAttributes2(@NotNull Model model, Locale locale) {
        model.addAttribute("offer_list", source.getMessage("offer_list", null, locale));
        model.addAttribute("candidate", source.getMessage("candidate", null, locale));
        model.addAttribute("positionTitle", source.getMessage("positionTitle", null, locale));
        model.addAttribute("approver_title", source.getMessage("approver_title", null, locale));
        model.addAttribute("interview_info", source.getMessage("interview_info", null, locale));
        model.addAttribute("cancelBtn", source.getMessage("cancelBtn", null, locale));
    }

    private void commonAttributes1(@NotNull Model model, Locale locale) {
        model.addAttribute("status", source.getMessage("status", null, locale));
        model.addAttribute("exportBtn", source.getMessage("exportBtn", null, locale));
        model.addAttribute("export_offer_btn", source.getMessage("export_offer_btn", null, locale));
        model.addAttribute("from_label", source.getMessage("from_label", null, locale));
        model.addAttribute("to_label", source.getMessage("to_label", null, locale));
    }

    private void commonAttributes(@NotNull Model model, Locale locale) {
        model.addAttribute("user_management", source.getMessage("user_management", null, locale));
        model.addAttribute("home_page", source.getMessage("home_page", null, locale));
        model.addAttribute("userH3", source.getMessage("userH3", null, locale));
        model.addAttribute("full_name", source.getMessage("full_name", null, locale));
        model.addAttribute("input_full_name", source.getMessage("input_full_name", null, locale));
        model.addAttribute("dob", source.getMessage("dob", null, locale));
        model.addAttribute("phone", source.getMessage("phone", null, locale));
        model.addAttribute("input_phone", source.getMessage("input_phone", null, locale));
        model.addAttribute("positionTitle", source.getMessage("positionTitle", null, locale));
        model.addAttribute("role", source.getMessage("role", null, locale));
        model.addAttribute("admin", source.getMessage("admin", null, locale));
        model.addAttribute("manager", source.getMessage("manager", null, locale));
        model.addAttribute("interviewerTitle", source.getMessage("interviewerTitle", null, locale));
        model.addAttribute("recruiter", source.getMessage("recruiter", null, locale));
        model.addAttribute("status", source.getMessage("status", null, locale));
        model.addAttribute("active", source.getMessage("active", null, locale));
        model.addAttribute("deactive", source.getMessage("deactive", null, locale));
        commonAttribute4(model, locale);
        model.addAttribute("departmentTitle", source.getMessage("department", null, locale));
        model.addAttribute("note", source.getMessage("note", null, locale));
        model.addAttribute("back_to_list", source.getMessage("back-to-list", null, locale));
        model.addAttribute("submitBtn", source.getMessage("submitBtn", null, locale));
        model.addAttribute("cancelBtn", source.getMessage("cancelBtn", null, locale));
    }
    // </editor-fold>
}
