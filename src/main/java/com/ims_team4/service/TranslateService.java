//package com.ims_team4.service;
//
//import com.ims_team4.dto.EmployeeDTO;
//import org.jetbrains.annotations.Contract;
//import org.jetbrains.annotations.NotNull;
//import org.json.JSONObject;
//import org.springframework.stereotype.Service;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Locale;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//@Service
//public class TranslateService {
//    private final Logger logger = Logger.getLogger(this.getClass().getName());
//
//    public List<EmployeeDTO> translateUserDTOs(@NotNull List<EmployeeDTO> emps, Locale locale) {
//        List<EmployeeDTO> list = new ArrayList<>();
//        for (EmployeeDTO emp : emps) {
//            String prompt = getElement(emp, locale);
//            list.add(convertToEmployeeDTOs(prompt, emp));
//        }
//        return list;
//    }
//
//    @NotNull
//    private String callGeminiAssistant(String prompt) {
//        logger.info("translate");
//        StringBuilder output = new StringBuilder();
//        String answer = "";
//        ProcessBuilder pb = new ProcessBuilder("python", "src/main/python/bot/assistant.py", prompt);
//        pb.redirectErrorStream(true);
//
//        boolean status = true;
//        while (status) {
//            try {
//                Process process = pb.start();
//                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    output.append(line);
//                    answer = output.toString();
//                    answer = answer.substring(1, answer.length() - 1);
//                }
//                answer = "{" + answer + "}";
//                status = false;
//            } catch (Exception e) {
//                logger.log(Level.SEVERE, e.getMessage(), e);
//            }
//        }
//        return answer;
//    }
//
//    @NotNull
//    private String getElement(@NotNull EmployeeDTO users, @NotNull Locale locale) {
//        String prompt = "address:'" + users.getAddress() + "', " +
//                "fullname:'" + users.getFullname() + "', " +
//                "positionName:'" + users.getPositionName() + "', " +
//                "departmentName:'" + users.getDepartmentName() + "', " +
//                locale.getLanguage();
//        logger.info(prompt);
//        return callGeminiAssistant(prompt);
//    }
//
//    @NotNull
//    @Contract("_, _ -> param2")
//    private EmployeeDTO convertToEmployeeDTOs(String input, @NotNull EmployeeDTO emp) {
//        JSONObject object = new JSONObject(input);
//        emp.setAddress(object.getString("address"));
//        emp.setFullname(object.getString("fullname"));
//        emp.setPositionName(object.getString("positionName"));
//        emp.setDepartmentName(object.getString("departmentName"));
//        return emp;
//    }
//}
