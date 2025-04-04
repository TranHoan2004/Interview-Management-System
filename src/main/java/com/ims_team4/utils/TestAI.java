//package com.ims_team4.utils;
//
//import com.ims_team4.model.*;
//import org.jetbrains.annotations.Contract;
//import org.jetbrains.annotations.NotNull;
//import org.json.JSONObject;
//import org.springframework.context.i18n.LocaleContextHolder;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//public class TestAI {
//    public static void main(String[] args) {
//        boolean status = true;
//        while (status) {
//            try {
//                System.out.println("==========");
//                // runUsers();
//                // runNotification();
//                // runStatusOffer();
//                // runSkill();
//                // runPosition();
//                // runOffer();
//                runLevel();
//                status = false;
//            } catch (Exception e) {
//                System.err.println("Lỗi: " + e.getMessage());
//            }
//        }
//    }
//
//    @NotNull
//    private static String callPythonScript(String prompt) throws Exception {
//        StringBuilder output = new StringBuilder();
//        ProcessBuilder pb = new ProcessBuilder("python", "src/main/python/bot/assistant.py", prompt);
//        pb.redirectErrorStream(true);
//        Process process = pb.start();
//
//        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//        String line;
//        String answer = "";
//        while ((line = reader.readLine()) != null) {
//            output.append(line);
//            answer = output.toString();
//            answer = answer.substring(1, answer.length() - 1);
//        }
//        answer = "{" + answer + "}";
//        return answer;
//    }
//
//    // <editor-fold> desc="Users"
//    @NotNull
//    private static List<String> nghichUsers(@NotNull Users users) throws Exception {
//        String prompt = "address:'" + users.getAddress() + "', " +
//                "fullname:'" + users.getFullname() + "', " +
//                "note:'" + users.getNote() + "', " +
//                "jp";
//        System.out.println(prompt);
//        String str6 = callPythonScript(prompt);
//
//        List<String> list = new ArrayList<>();
//        list.add(str6);
//        return list;
//    }
//
//    @NotNull
//    @Contract("_, _ -> param2")
//    private static Users convertToUsers(String input, @NotNull Users users) {
//        JSONObject object = new JSONObject(input);
//        users.setAddress(object.getString("address"));
//        users.setFullname(object.getString("fullname"));
//        users.setNote(object.getString("note"));
//        return users;
//    }
//
//    public static void runUsers() throws Exception {
//        Users users = new Users(10L, LocalDate.now(), 1, "hoana5k44nknd@gmail.com", "Hanoi city",
//                new byte[] { 1, 2, 3, 4, 5 }, "Tran Hoan",
//                "0949279204", true, "note",
//                null, null, null, null);
//        Users users1 = new Users(10L, LocalDate.now(), 1, "hoana5k44nknd@gmail.com", "Hanoi city",
//                new byte[] { 1, 2, 3, 4, 5 }, "Tran Hoan",
//                "0949279204", true, "note",
//                null, null, null, null);
//        Users users2 = new Users(10L, LocalDate.now(), 1, "hoana5k44nknd@gmail.com", "Hanoi city",
//                new byte[] { 1, 2, 3, 4, 5 }, "Tran Hoan",
//                "0949279204", true, "note",
//                null, null, null, null);
//        Users users3 = new Users(10L, LocalDate.now(), 1, "hoana5k44nknd@gmail.com", "Hanoi city",
//                new byte[] { 1, 2, 3, 4, 5 }, "Tran Hoan",
//                "0949279204", true, "note",
//                null, null, null, null);
//        String str = nghichUsers(users).getFirst();
//        String str1 = nghichUsers(users1).getFirst();
//        String str2 = nghichUsers(users2).getFirst();
//        String str3 = nghichUsers(users3).getFirst();
//
//        System.out.println(convertToUsers(str, users));
//        System.out.println(convertToUsers(str1, users1));
//        System.out.println(convertToUsers(str2, users2));
//        System.out.println(convertToUsers(str3, users3));
//    }
//    /*
//     * Output:
//     * address:'Hanoi city', fullname:'Tran Hoan', note:'note', jp
//     * address:'Hanoi city', fullname:'Tran Hoan', note:'note', jp
//     * address:'Hanoi city', fullname:'Tran Hoan', note:'note', jp
//     * address:'Hanoi city', fullname:'Tran Hoan', note:'note', jp
//     * Users(id=10, dob=2025-04-02, gender=1, email=hoana5k44nknd@gmail.com,
//     * address=????, avatar=[1, 2, 3, 4, 5], fullname=???????, phone=0949279204,
//     * status=true, note=?)
//     * Users(id=10, dob=2025-04-02, gender=1, email=hoana5k44nknd@gmail.com,
//     * address=????, avatar=[1, 2, 3, 4, 5], fullname=???????, phone=0949279204,
//     * status=true, note=???)
//     * Users(id=10, dob=2025-04-02, gender=1, email=hoana5k44nknd@gmail.com,
//     * address=????, avatar=[1, 2, 3, 4, 5], fullname=???????, phone=0949279204,
//     * status=true, note=??)
//     * Users(id=10, dob=2025-04-02, gender=1, email=hoana5k44nknd@gmail.com,
//     * address=????, avatar=[1, 2, 3, 4, 5], fullname=???????, phone=0949279204,
//     * status=true, note=???)
//     */
//    // </editor-fold>
//
//    // <editor-fold> desc="Notification"
//    @NotNull
//    private static List<String> nghichNotification(@NotNull Notification noti) throws Exception {
//        String prompt = "link:'" + noti.getLink() + "', " +
//                "message:'" + noti.getMessage() + "', " +
//                "title:'" + noti.getTitle() + "', " +
//                "vi";
//        System.out.println(prompt);
//        String str6 = callPythonScript(prompt);
//
//        List<String> list = new ArrayList<>();
//        list.add(str6);
//        return list;
//    }
//
//    @NotNull
//    @Contract("_, _ -> param2")
//    private static Notification convertToNotification(String input, @NotNull Notification noti) {
//        JSONObject object = new JSONObject(input);
//        noti.setLink(object.getString("link"));
//        noti.setMessage(object.getString("message"));
//        noti.setTitle(object.getString("title"));
//        return noti;
//    }
//
//    public static void runNotification() throws Exception {
//        Notification users = new Notification("link 1", "Message 1", "Title 1");
//        Notification users1 = new Notification("link 2", "Message for you from me", "Call him please");
//        Notification users2 = new Notification("link 3", "Are you sure for that?", "I do not know");
//        Notification users3 = new Notification("link 4", "Hey you, yes, that's you", "We are meeting");
//
//        String str = nghichNotification(users).getFirst();
//        String str1 = nghichNotification(users1).getFirst();
//        String str2 = nghichNotification(users2).getFirst();
//        String str3 = nghichNotification(users3).getFirst();
//
//        System.out.println(convertToNotification(str, users));
//        System.out.println(convertToNotification(str1, users1));
//        System.out.println(convertToNotification(str2, users2));
//        System.out.println(convertToNotification(str3, users3));
//    }
//    /*
//     * Output:
//     * link:'link 1', message:'Message 1', title:'Title 1', vi
//     * link:'link 2', message:'Message for you from me', title:'Call him please', vi
//     * link:'link 3', message:'Are you sure for that?', title:'I do not know', vi
//     * link:'link 4', message:'Hey you, yes, that's you', title:'We are meeting', vi
//     * Notification(id=0, link=liên k?t 1, message=Tin nh?n 1, title=Tiêu đ? 1,
//     * status=false, user=null)
//     * Notification(id=0, link=link 2, message=Tin nh?n cho b?n t? tôi, title=G?i
//     * cho anh ?y làm ơn, status=false, user=null)
//     * Notification(id=0, link=link 3, message=B?n có ch?c ch?n v? đi?u đó không?,
//     * title=Tôi không bi?t, status=false, user=null)
//     * Notification(id=0, link=liên k?t 4, message=Này b?n, đúng v?y, là b?n đó,
//     * title=Chúng ta đang g?p nhau, status=false, user=null)
//     */
//    // </editor-fold>
//
//    // <editor-fold> desc="Status Offer"
//    @NotNull
//    private static List<String> nghichStatusOffer(@NotNull StatusOffer noti) throws Exception {
//        String prompt = "statusName:'" + noti.getStatusName() + "', " + "vi";
//        System.out.println(prompt);
//        String str6 = callPythonScript(prompt);
//
//        List<String> list = new ArrayList<>();
//        list.add(str6);
//        return list;
//    }
//
//    @NotNull
//    @Contract("_, _ -> param2")
//    private static StatusOffer convertToStatusOffer(String input, @NotNull StatusOffer noti) {
//        JSONObject object = new JSONObject(input);
//        noti.setStatusName(object.getString("statusName"));
//        return noti;
//    }
//
//    public static void runStatusOffer() throws Exception {
//        StatusOffer users = new StatusOffer("Accepted offer");
//        StatusOffer users2 = new StatusOffer("Accepted offer");
//        StatusOffer users1 = new StatusOffer("Approved offer");
//        StatusOffer users3 = new StatusOffer("Approved offer");
//
//        String str = nghichStatusOffer(users).getFirst();
//        String str2 = nghichStatusOffer(users2).getFirst();
//        String str1 = nghichStatusOffer(users1).getFirst();
//        String str3 = nghichStatusOffer(users3).getFirst();
//
//        System.out.println(convertToStatusOffer(str, users));
//        System.out.println(convertToStatusOffer(str2, users2));
//        System.out.println(convertToStatusOffer(str1, users1));
//        System.out.println(convertToStatusOffer(str3, users3));
//    }
//    /*
//     * Output:
//     * statusName:'Accepted offer', vi
//     * statusName:'Accepted offer', vi
//     * statusName:'Approved offer', vi
//     * statusName:'Approved offer', vi
//     * StatusOffer(id=null, statusName=Đ? ch?p nh?n đ? ngh?, offers=null)
//     * StatusOffer(id=null, statusName=Đ? ch?p nh?n đ? ngh?, offers=null)
//     * StatusOffer(id=null, statusName=Đ? duy?t đ? ngh?, offers=null)
//     * StatusOffer(id=null, statusName=Đ? phê duy?t đ? ngh?, offers=null)
//     */
//    // </editor-fold>
//
//    // <editor-fold> desc="Skill"
//    @NotNull
//    private static List<String> nghichSkill(@NotNull Skill noti) throws Exception {
//        // locale fr dead
//        // String prompt = "name:'" + noti.getName() + "', " + "fr";
//        String prompt = "name:'" + noti.getName() + "', " + "vi";
//        System.out.println(prompt);
//        String str6 = callPythonScript(prompt);
//
//        List<String> list = new ArrayList<>();
//        list.add(str6);
//        return list;
//    }
//
//    @NotNull
//    @Contract("_, _ -> param2")
//    private static Skill convertToSkill(String input, @NotNull Skill noti) {
//        JSONObject object = new JSONObject(input);
//        noti.setName(object.getString("name"));
//        return noti;
//    }
//
//    public static void runSkill() throws Exception {
//        Skill users = new Skill("Business analysis");
//        Skill users2 = new Skill("Business analysis");
//        Skill users1 = new Skill("Communication");
//        Skill users3 = new Skill("Communication");
//
//        String str = nghichSkill(users).getFirst();
//        String str2 = nghichSkill(users2).getFirst();
//        String str1 = nghichSkill(users1).getFirst();
//        String str3 = nghichSkill(users3).getFirst();
//
//        System.out.println(convertToSkill(str, users));
//        System.out.println(convertToSkill(str2, users2));
//        System.out.println(convertToSkill(str1, users1));
//        System.out.println(convertToSkill(str3, users3));
//    }
//    /*
//     * Output:
//     * name:'Business analysis', vi
//     * name:'Business analysis', vi
//     * name:'Communication', vi
//     * name:'Communication', vi
//     * name='Phân tích kinh doanh'
//     * name='Phân tích nghi?p v?'
//     * name='Truy?n thông'
//     * name='Truy?n thông'
//     */
//    // </editor-fold>
//
//    // <editor-fold> desc="Position"
//    @NotNull
//    private static List<String> nghichPosition(@NotNull Position noti) throws Exception {
//        String prompt = "name:'" + noti.getName() + "', " + "vi";
//        System.out.println(prompt);
//        String str6 = callPythonScript(prompt);
//
//        List<String> list = new ArrayList<>();
//        list.add(str6);
//        return list;
//    }
//
//    @NotNull
//    @Contract("_, _ -> param2")
//    private static Position convertToPosition(String input, @NotNull Position noti) {
//        JSONObject object = new JSONObject(input);
//        noti.setName(object.getString("name"));
//        return noti;
//    }
//
//    public static void runPosition() throws Exception {
//        Position users = new Position("Backend Developer");
//        Position users2 = new Position("Backend Developer");
//        Position users1 = new Position("Tester");
//        Position users3 = new Position("Tester");
//
//        String str = nghichPosition(users).getFirst();
//        String str2 = nghichPosition(users2).getFirst();
//        String str1 = nghichPosition(users1).getFirst();
//        String str3 = nghichPosition(users3).getFirst();
//
//        System.out.println(convertToPosition(str, users));
//        System.out.println(convertToPosition(str2, users2));
//        System.out.println(convertToPosition(str1, users1));
//        System.out.println(convertToPosition(str3, users3));
//    }
//    /*
//     * Output:
//     * name:'Backend Developer', vi
//     * name:'Backend Developer', vi
//     * name:'Tester', vi
//     * name:'Tester', vi
//     * name:'Nhà phát tri?n Backend'
//     * name:'Nhà phát tri?n Backend'
//     * name:'Ngư?i ki?m th?'
//     * name:'Ngư?i ki?m th?'
//     */
//    // </editor-fold>
//
//    // <editor-fold> desc="Offer"
//    @NotNull
//    private static List<String> nghichOffer(@NotNull Offer noti) throws Exception {
//        String prompt = "interviewNote:'" + noti.getInterviewNotes() + "', " +
//                "note:'" + noti.getNote() + "', " +
//                "vi";
//        System.out.println(prompt);
//        String str6 = callPythonScript(prompt);
//
//        List<String> list = new ArrayList<>();
//        list.add(str6);
//        return list;
//    }
//
//    @NotNull
//    @Contract("_, _ -> param2")
//    private static Offer convertToOffer(String input, @NotNull Offer noti) {
//        JSONObject object = new JSONObject(input);
//        noti.setInterviewNotes(object.getString("interviewNote"));
//        noti.setNote(object.getString("note"));
//        return noti;
//    }
//
//    public static void runOffer() throws Exception {
//        Offer users = new Offer("Strong problem-solving skills", "Offer for Senior Developer");
//        Offer users2 = new Offer("Strong problem-solving skills", "Offer for Senior Developer");
//        Offer users1 = new Offer("Good leadership experience", "Offer for HR Manager");
//        Offer users3 = new Offer("Good leadership experience", "Offer for HR Manager");
//
//        String str = nghichOffer(users).getFirst();
//        String str2 = nghichOffer(users2).getFirst();
//        String str1 = nghichOffer(users1).getFirst();
//        String str3 = nghichOffer(users3).getFirst();
//
//        System.out.println(convertToOffer(str, users));
//        System.out.println(convertToOffer(str2, users2));
//        System.out.println(convertToOffer(str1, users1));
//        System.out.println(convertToOffer(str3, users3));
//    }
//    /*
//     * Output:
//     * interviewNote:'Strong problem-solving skills', note:'Offer for Senior
//     * Developer', vi
//     * interviewNote:'Strong problem-solving skills', note:'Offer for Senior
//     * Developer', vi
//     * interviewNote:'Good leadership experience', note:'Offer for HR Manager', vi
//     * interviewNote:'Good leadership experience', note:'Offer for HR Manager', vi
//     * interviewNote:'K? n?ng gi?i quy?t v?n ?? t?t', note:'?? ngh? cho Senior
//     * Developer'
//     * interviewNote:'K? n?ng gi?i quy?t v?n ?? t?t', note:'?? ngh? cho Senior
//     * Developer'
//     * interviewNote:'Kinh nghi?m lănh ??o t?t', note:'Offer cho Qu?n lư Nhân s?'
//     * interviewNote:'Kinh nghi?m lănh ??o t?t', note:'?? ngh? cho v? trí Tr??ng
//     * pḥng Nhân s?'
//     */
//    // </editor-fold>
//
//    // <editor-fold> desc="Level"
//    @NotNull
//    private static List<String> nghichLevel(@NotNull Level noti) throws Exception {
//        String prompt = "name:'" + noti.getName() + "', " + LocaleContextHolder.getLocale().getLanguage();
//        System.out.println(prompt);
//        String str6 = callPythonScript(prompt);
//
//        List<String> list = new ArrayList<>();
//        list.add(str6);
//        return list;
//    }
//
//    @NotNull
//    @Contract("_, _ -> param2")
//    private static Level convertToLevel(String input, @NotNull Level noti) {
//        JSONObject object = new JSONObject(input);
//        noti.setName(object.getString("name"));
//        return noti;
//    }
//
//    public static void runLevel() throws Exception {
//        Level users = new Level("Vice Head");
//        Level users2 = new Level("Vice Head");
//        Level users1 = new Level("Senior");
//        Level users3 = new Level("Senior");
//
//        String str = nghichLevel(users).getFirst();
//        String str2 = nghichLevel(users2).getFirst();
//        String str1 = nghichLevel(users1).getFirst();
//        String str3 = nghichLevel(users3).getFirst();
//
//        System.out.println(convertToLevel(str, users));
//        System.out.println(convertToLevel(str2, users2));
//        System.out.println(convertToLevel(str1, users1));
//        System.out.println(convertToLevel(str3, users3));
//    }
//    /*
//     * Output:
//     * name:'Vice Head', vi
//     * name:'Vice Head', vi
//     * name:'Senior', vi
//     * name:'Senior', vi
//     * name:'Phó phòng'
//     * name:'Phó phòng'
//     * name:'Cao cấp'
//     * name:'Cao cấp'
//     */
//    // </editor-fold>
//}
