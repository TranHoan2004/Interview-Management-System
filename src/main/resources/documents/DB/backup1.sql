USE ims;
INSERT INTO `benefit`
VALUES (1, 'Lunch'),
       (2, '25-day leave'),
       (3, 'Healthcare insurance'),
       (4, 'Hybrid working'),
       (5, 'Travel'),
       (6, 'Lunch'),
       (7, '25-day leave'),
       (8, 'Healthcare insurance'),
       (9, 'Hybrid working'),
       (10, 'Travel');

INSERT INTO `contract_type`
VALUES (3, '1 year'),
       (4, '3 years'),
       (2, 'Trainee 3 months'),
       (1, 'Trial 2 months'),
       (5, 'Unlimited');
INSERT INTO `department`
VALUES (6, 'Accounting'),
       (4, 'Communication'),
       (3, 'Finance'),
       (2, 'HR'),
       (1, 'IT'),
       (5, 'Marketing');

INSERT INTO `highest_level`
VALUES (1, 'High school'),
       (2, 'Bachelors Degree'),
       (3, 'Master Degree, PhD');
INSERT INTO `levels`
VALUES (1, 'Fresher'),
       (2, 'Junior'),
       (4, 'Leader'),
       (5, 'Manager'),
       (3, 'Senior'),
       (6, 'Vice Head');
INSERT INTO `position`
VALUES (1, 'Backend Developer'),
       (2, 'Business Analyst'),
       (3, 'Tester'),
       (4, 'HR'),
       (5, 'Project manager'),
       (6, 'Not available');
INSERT INTO `skill`
VALUES (1, 'Java'),
       (2, 'Nodejs'),
       (3, '.Net'),
       (4, 'C++'),
       (5, 'Business analysis'),
       (6, 'Communication');
INSERT INTO `status_offer`
VALUES (5, 'Accepted offer'),
       (2, 'Approved offer'),
       (7, 'Cancelled'),
       (6, 'Declined offer'),
       (3, 'Rejected offer'),
       (1, 'Waiting for approval'),
       (4, 'Waiting for response');
INSERT INTO `users`
VALUES (1, '12 Đường Lê Lợi, Hà Nội', 'images/avatar/avatar1.jpg
', '1990-05-14', 'tran.dai.nghia@example.com',
        'Trần Đại Nghĩa', 0, 'Lập trình viên Java', '0987123456', _binary ''),
       (2, '56 Đường Nguyễn Trãi, TP.HCM', 'images/avatar/avatar2.jpg
', '1993-08-22', 'pham.thu.trang@example.com',
        'Phạm Thu Trang', 1, 'Nhân sự', '0902345678', _binary ''),
       (3, '89 Đường Hai Bà Trưng, Đà Nẵng', 'images/avatar/avatar3.jpg
', '1985-12-03', 'le.van.nam@example.com',
        'Lê Văn Nam', 0, 'Nhân viên kinh doanh', '0918765432', _binary ''),
       (4, '101 Đường Phạm Văn Đồng, Cần Thơ', 'images/avatar/avatar4.jpg
', '1997-06-17', 'hoang.hai.yen@example.com',
        'Hoàng Hải Yến', 2, 'Chuyên viên Marketing', '0939876543', _binary ''),
       (5, '77 Đường Lý Thường Kiệt, Huế', 'images/avatar/avatar5.jpg
', '1991-11-29', 'nguyen.thu.ha@example.com',
        'Nguyễn Thu Hà', 1, 'Kỹ sư phần mềm', '0976549871', _binary ''),
       (6, '88 Đường Tô Hiến Thành, TP.HCM', 'images/avatar/avatar6.jpg
', '1996-03-21', 'hoang.bich.ngoc@example.com',
        'Hoàng Bích Ngọc', 1, 'Chuyên viên tuyển dụng', '0987654322', _binary ''),
       (7, '75 Đường Lạc Long Quân, Nha Trang', 'images/avatar/avatar7.jpg
', '1994-10-09', 'tran.tuan.khang@example.com',
        'Trần Tuấn Khang', 0, 'Lập trình viên Frontend', '0912345567', _binary ''),
       (8, '27 Đường Trần Phú, Buôn Ma Thuột', 'images/avatar/avatar8.jpg
', '1997-07-17', 'nguyen.thanh.truc@example.com',
        'Nguyễn Thanh Trúc', 1, 'Chuyên viên truyền thông', '0934765890', _binary ''),
       (9, '12 Đường Quang Trung, Đà Nẵng', 'images/avatar/avatar9.jpg
', '1995-09-10', 'pham.my.anh@example.com',
        'Phạm Mỹ Anh', 1, 'Chuyên viên quan hệ khách hàng', '0979988776', _binary ''),
       (10, '111 Đường Nguyễn Văn Cừ, Cần Thơ', 'images/avatar/avatar10.jpg
', '1999-02-14', 'le.hoang.phuc@example.com',
        'Lê Hoàng Phúc', 0, 'Nhân viên hỗ trợ kỹ thuật', '0955544332', _binary ''),
       (11, '10 Đường Trần Hưng Đạo, Huế', 'images/avatar/avatar11.jpg
', '1987-04-09', 'user1@example.com',
        'Đặng Việt Hoàng', 0, 'Trưởng phòng tài chính', '0954321678', _binary ''),
       (12, '34 Đường Võ Thị Sáu, Đà Lạt', 'images/avatar/avatar12.jpg
', '1994-09-25', 'bui.thanh.huyen@example.com',
        'Bùi Thanh Huyền', 1, 'Trợ lý nhân sự', '0967896543', _binary ''),
       (13, '19 Đường Hoàng Văn Thụ, Quy Nhơn', 'images/avatar/avatar13.jpg
', '1996-03-11', 'pham.tuan.kiet@example.com',
        'Phạm Tuấn Kiệt', 0, 'Nhân viên IT', '0945678234', _binary ''),
       (14, '61 Đường Nguyễn Huệ, Vũng Tàu', 'images/avatar/avatar14.jpg
', '1989-07-20', 'le.ngoc.diep@example.com',
        'Lê Ngọc Diệp', 2, 'Quản lý dịch vụ', '0923456780', _binary ''),
       (15, '42 Đường Trần Quang Khải, Hà Nội', 'images/avatar/avatar15.jpg
', '1993-10-05', 'nguyen.quoc.an@example.com',
        'Nguyễn Quốc An', 0, 'Chuyên viên phân tích kinh doanh', '0998765321', _binary ''),
       (16, '30 Đường Hoàng Hoa Thám, Hà Nội', 'images/avatar/avatar16.jpg
', '1989-06-06', 'vu.ngoc.tuan@example.com',
        'Vũ Ngọc Tuấn', 2, 'Trưởng phòng kinh doanh', '0956789012', _binary ''),
       (17, '55 Đường Phạm Hồng, Hải Dương', 'images/avatar/avatar17.jpg
', '1992-01-13', 'le.tan.binh@example.com',
        'Lê Tấn Bình', 0, 'Chuyên viên chăm sóc khách hàng', '0976543278', _binary ''),
       (18, '99 Đường Trần Nhân Tông, Hà Nội', 'images/avatar/avatar18.jpg
', '1998-04-30', 'tran.hong.dao@example.com',
        'Trần Hồng Đào', 1, 'Nhân viên kế toán', '0912233445', _binary ''),
       (19, '50 Đường Cách Mạng Tháng 8, TP.HCM', 'images/avatar/avatar19.jpg
', '1986-07-21', 'nguyen.dinh.khoa@example.com',
        'Nguyễn Đình Khoa', 0, 'Trưởng phòng kỹ thuật', '0933344556', _binary ''),
       (20, '15 Đường Bạch Đằng, Quảng Ninh', 'images/avatar/avatar20.jpg
', '1991-05-15', 'pham.thanh.van@example.com',
        'Phạm Thanh Vân', 1, 'Chuyên viên pháp lý', '0988776655', _binary ''),
       (21, '78 Đường Nguyễn Thái Học, Huế', 'images/avatar/avatar21.jpg
', '1990-02-28', 'hoang.van.ninh@example.com',
        'Hoàng Văn Ninh', 0, 'Chuyên viên bảo trì hệ thống', '0911223344', _binary ''),
       (22, '67 Đường Hàm Nghi, Đà Nẵng', 'images/avatar/avatar22.jpg
', '1993-07-09', 'vu.kim.nhu@example.com',
        'Vũ Kim Như', 1, 'Nhân viên thiết kế đồ họa', '0944332211', _binary ''),
       (23, '44 Đường 3/2, TP.HCM', 'images/avatar/avatar23.jpg
', '1995-04-18', 'tran.duy.khoa@example.com',
        'Trần Duy Khoa', 0, 'Nhân viên an toàn thông tin', '0922113344', _binary ''),
       (24, '29 Đường Lê Lai, Bình Dương', 'images/avatar/avatar24.jpg
', '1988-11-22', 'le.phuong.trang@example.com',
        'Lê Phương Trang', 2, 'Trưởng phòng nhân sự', '0998877665', _binary '');
INSERT INTO `notification`
VALUES (1, '/jobs/software-engineer', 'Check out new job opening for Software Engineer.', 0,
        'New job opportunity available', 11),
       (2, '/hr/policies', 'Updated HR policies for 2024.', 1, 'Interview policy update', 12),
       (3, '/finance/reports', 'Monthly finance report submission due.', 0, 'Finance report deadline', 13),
       (4, '/it/maintenance', 'Scheduled system maintenance on weekend.', 1, 'IT system maintenance', 14),
       (5, '/marketing/meeting', 'Important marketing strategy discussion.', 0, 'Marketing strategy meeting', 15),
       (6, '/sales/targets', 'Congratulations on achieving Q2 sales target.', 1, 'Sales target achievement', 16),
       (7, '/business/expansion', 'Company is expanding, exciting opportunities ahead.', 0, 'Business expansion plan',
        17),
       (8, '/employee/survey', 'Participate in employee engagement survey.', 1, 'User engagement survey', 18),
       (9, '/townhall/announcement', 'Join the upcoming company-wide town hall.', 0, 'Company-wide town hall', 19),
       (10, '/learning/programs', 'Explore new learning & development courses.', 1,
        'Job need to be updated', 11),
       (11, '/learning/programs', 'Message 2', 1,
        'New Interview Schedules', 12),
       (12, '/learning/programs', 'Message 3', 1,
        'New Interview Schedules', 12),
       (13, '/learning/programs', 'Message 4', 1,
        'Candidate updated profile', 13),
       (14, '/learning/programs', 'Message 5', 1,
        'Interview reminder', 15),
       (15, '/learning/programs', 'Message 6', 1,
        'Candidate updated profile', 11),
       (16, '/learning/programs', 'Message 7', 1,
        'Interview reminder', 12),
       (17, '/learning/programs', 'Message 8', 1,
        'Candidate updated profile', 11),
       (18, '/learning/programs', 'Message 9', 1,
        'New Interview Schedules', 13);
INSERT INTO `employee`
VALUES (11, '$2a$10$iLTvOnKv29rTx7fwPIBJR.n5iSuvCPrUEA.FKFYjLQeitcLdonkZW', 'ROLE_ADMINISTRATOR', 'HoangDV', 1, 1),
       (12, '$2a$10$iLTvOnKv29rTx7fwPIBJR.n5iSuvCPrUEA.FKFYjLQeitcLdonkZW', 'ROLE_RECRUITER', 'HuyenBT', 6, 6),
       (13, '$2a$10$iLTvOnKv29rTx7fwPIBJR.n5iSuvCPrUEA.FKFYjLQeitcLdonkZW', 'ROLE_RECRUITER', 'KietPT', 4, 3),
       (14, '$2a$10$iLTvOnKv29rTx7fwPIBJR.n5iSuvCPrUEA.FKFYjLQeitcLdonkZW', 'ROLE_INTERVIEWER', 'DiepLN', 2, 4),
       (15, '$2a$10$iLTvOnKv29rTx7fwPIBJR.n5iSuvCPrUEA.FKFYjLQeitcLdonkZW', 'ROLE_INTERVIEWER', 'AnNQ', 3, 4),
       (16, '$2a$10$iLTvOnKv29rTx7fwPIBJR.n5iSuvCPrUEA.FKFYjLQeitcLdonkZW', 'ROLE_MANAGER', 'TuanVN', 2, 1),
       (17, '$2a$10$iLTvOnKv29rTx7fwPIBJR.n5iSuvCPrUEA.FKFYjLQeitcLdonkZW', 'ROLE_INTERVIEWER', 'BinhLT', 4, 5),
       (18, '$2a$10$iLTvOnKv29rTx7fwPIBJR.n5iSuvCPrUEA.FKFYjLQeitcLdonkZW', 'ROLE_INTERVIEWER', 'DaoTH', 5, 5),
       (19, '$2a$10$iLTvOnKv29rTx7fwPIBJR.n5iSuvCPrUEA.FKFYjLQeitcLdonkZW', 'ROLE_MANAGER', 'KhoaND', 3, 1),
       (20, '$2a$10$iLTvOnKv29rTx7fwPIBJR.n5iSuvCPrUEA.FKFYjLQeitcLdonkZW', 'ROLE_RECRUITER', 'VanPT', 3, 2),
       (21, '$2a$10$iLTvOnKv29rTx7fwPIBJR.n5iSuvCPrUEA.FKFYjLQeitcLdonkZW', 'ROLE_MANAGER', 'NinhHV', 4, 2),
       (22, '$2a$10$iLTvOnKv29rTx7fwPIBJR.n5iSuvCPrUEA.FKFYjLQeitcLdonkZW', 'ROLE_RECRUITER', 'NhuVK', 2, 1),
       (23, '$2a$10$iLTvOnKv29rTx7fwPIBJR.n5iSuvCPrUEA.FKFYjLQeitcLdonkZW', 'ROLE_RECRUITER', 'KhoaTD', 1, 6),
       (24, '$2a$10$iLTvOnKv29rTx7fwPIBJR.n5iSuvCPrUEA.FKFYjLQeitcLdonkZW', 'ROLE_MANAGER', 'TrangLP', 5, 2);
INSERT INTO `candidate`
VALUES (_binary 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/CV.docx', 10, 'FAILED_INTERVIEW', 1, 11, 1, 1),
       (_binary 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/CV.docx', 8, 'FAILED_INTERVIEW', 2, 13, 2, 2),
       (_binary 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/CV.docx', 5, 'APPROVED_OFFER', 3, 15, 3, 3),
       (_binary 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/CV.docx', 6, 'OPEN', 4, 17, 1, 4),
       (_binary 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/CV.docx', 7, 'FAILED_INTERVIEW', 5, 19, 2, 5),
       (_binary 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/CV.docx', 12, 'PASSED_INTERVIEW', 6, 21, 3, 6);
INSERT INTO `candidate_skill`
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5);
INSERT INTO `job`
VALUES (1, 'Develop and maintain software applications.', '2025-06-01', 'Hanoi', 10000000, 20000000, '2025-03-01',
        _binary '', 'Software Engineer', 11),
       (2, 'Analyze data and generate insights.', '2025-06-05', 'Ho Chi Minh City', 12000000, 22000000, '2025-03-05',
        _binary '', 'Data Analyst', 12),
       (3, 'Manage IT projects and coordinate teams.', '2025-06-10', 'Da Nang', 15000000, 30000000, '2025-03-10',
        _binary '', 'Project Manager', 13),
       (4, 'Develop user-friendly web applications.', '2025-06-15', 'Can Tho', 9000000, 18000000, '2025-03-15',
        _binary '', 'Frontend Developer', 14),
       (5, 'Implement backend services and APIs.', '2025-06-20', 'Quy Nhon', 11000000, 21000000, '2025-03-20',
        _binary '', 'Backend Developer', 15),
       (6, 'Manage cloud infrastructure and CI/CD pipelines.', '2025-06-25', 'Hanoi', 13000000, 25000000, '2025-03-25',
        _binary '', 'DevOps Engineer', 16),
       (7, 'Design intuitive user interfaces.', '2025-06-30', 'Ho Chi Minh City', 10000000, 20000000, '2025-03-30',
        _binary '', 'UI/UX Designer', 17),
       (8, 'Ensure security for IT systems.', '2025-07-01', 'Da Nang', 14000000, 28000000, '2025-04-01', _binary '',
        'Cybersecurity Analyst', 18),
       (9, 'Develop mobile applications.', '2025-07-05', 'Can Tho', 9500000, 19000000, '2025-04-05', _binary '',
        'Mobile Developer', 19),
       (10, 'Work on AI and machine learning projects.', '2025-07-10', 'Quy Nhon', 16000000, 32000000, '2025-04-10',
        _binary '', 'AI Engineer', 20),
       (11, 'Develop and maintain software applications.', '2025-06-01', 'Hanoi', 10000000, 20000000, '2025-03-01',
        _binary '', 'Software Engineer', 21),
       (12, 'Analyze data and generate insights.', '2025-06-05', 'Ho Chi Minh City', 12000000, 22000000, '2025-03-05',
        _binary '', 'Data Analyst', 22),
       (13, 'Manage IT projects and coordinate teams.', '2025-06-10', 'Da Nang', 15000000, 30000000, '2025-03-10',
        _binary '', 'Project Manager', 23),
       (14, 'Develop user-friendly web applications.', '2025-06-15', 'Can Tho', 9000000, 18000000, '2025-03-15',
        _binary '', 'Frontend Developer', 24),
       (15, 'Implement backend services and APIs.', '2025-06-20', 'Quy Nhon', 11000000, 21000000, '2025-03-20',
        _binary '', 'Backend Developer', 11),
       (16, 'Manage cloud infrastructure and CI/CD pipelines.', '2025-06-25', 'Hanoi', 13000000, 25000000, '2025-03-25',
        _binary '', 'DevOps Engineer', 12),
       (17, 'Design intuitive user interfaces.', '2025-06-30', 'Ho Chi Minh City', 10000000, 20000000, '2025-03-30',
        _binary '', 'UI/UX Designer', 13),
       (18, 'Ensure security for IT systems.', '2025-07-01', 'Da Nang', 14000000, 28000000, '2025-04-01', _binary '',
        'Cybersecurity Analyst', 14),
       (19, 'Develop mobile applications.', '2025-07-05', 'Can Tho', 9500000, 19000000, '2025-04-05', _binary '',
        'Mobile Developer', 15),
       (20, 'Work on AI and machine learning projects.', '2025-07-10', 'Quy Nhon', 16000000, 32000000, '2025-04-10',
        _binary '', 'AI Engineer', 16);
INSERT INTO interview (
    id, schedule_time, start_time, end_time, locations, meet_id, note, notification_sent,
    recruiter_owner, status, title, candidate_id, job_id, result
)
VALUES
    (11, '2024-07-10 09:00:00', '10:30:00', '11:30:00', 'Hà Nội', 'M101', 'Ứng viên có kỹ năng tốt', FALSE,
     12, 'CANCELLED', 'Phỏng vấn vị trí Dev', 1, 1, NULL),
    (12, '2024-07-11 10:30:00', '10:30:00', '11:30:00', 'Hồ Chí Minh', 'M102', 'Cần kiểm tra kỹ năng code', FALSE,
     12, 'INTERVIEWED', 'Phỏng vấn vị trí Tester', 2, 2, NULL),
    (13, '2024-07-12 14:00:00', '10:30:00', '11:30:00', 'Đà Nẵng', 'M103', 'Ứng viên có chứng chỉ AWS', FALSE,
     12, 'INVITED', 'Phỏng vấn vị trí Cloud Engineer', 3, 3, NULL),
    (14, '2024-07-13 08:45:00', '10:30:00', '11:30:00', 'Cần Thơ', 'M104', 'Ứng viên có 3 năm kinh nghiệm', FALSE,
     16, 'NEW', 'Phỏng vấn vị trí Backend Developer', 4, 4, NULL),
    (15, '2024-07-14 16:00:00', '09:30:00', '10:30:00', 'Hải Phòng', 'M105', 'Kỹ năng giao tiếp tốt', FALSE,
     19, 'CANCELLED', 'Phỏng vấn vị trí Sales', 5, 5, NULL),
    (16, '2024-07-15 11:30:00', '09:30:00', '10:30:00', 'Hà Nội', 'M106', 'Ứng viên chưa có nhiều kinh nghiệm', FALSE,
     21, 'INTERVIEWED', 'Phỏng vấn vị trí Intern', 6, 6, NULL),
    (17, '2024-07-16 15:00:00', '09:30:00', '10:30:00', 'Hồ Chí Minh', 'M107', 'Ứng viên có kinh nghiệm quản lý', FALSE,
     23, 'INVITED', 'Phỏng vấn vị trí Project Manager', 1, 7, NULL),
    (18, '2024-07-17 09:15:00', '09:30:00', '10:30:00', 'Đà Nẵng', 'M108', 'Chuyên môn cao về UI/UX', FALSE,
     12, 'NEW', 'Phỏng vấn vị trí Designer', 2, 8, NULL),
    (19, '2024-07-18 13:45:00', '09:30:00', '10:30:00', 'Cần Thơ', 'M109', 'Ứng viên đã làm việc tại công ty lớn', FALSE,
     16, 'CANCELLED', 'Phỏng vấn vị trí Data Analyst', 3, 9, NULL),
    (20, '2024-07-19 10:00:00', '09:30:00', '10:30:00', 'Hải Phòng', 'M110', 'Cần kiểm tra kỹ năng mềm', FALSE,
     19, 'INTERVIEWED', 'Phỏng vấn vị trí HR', 4, 10, NULL);

INSERT into `interview_employee`
VALUES (11, 11),
       (11, 16),
       (11, 19),
       (11, 21),
       (12, 14),
       (12, 15),
       (12, 24),
       (13, 17),
       (13, 18),
       (14, 12),
       (14, 23),
       (15, 20),
       (15, 22),
       (16, 13);
INSERT INTO `job_benefit`
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5);
INSERT INTO `job_candidates`
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5);
INSERT INTO `job_level`
VALUES (1, 1),
       (4, 1),
       (7, 1),
       (10, 1),
       (2, 2),
       (5, 2),
       (8, 2),
       (3, 3),
       (6, 3),
       (9, 3);
INSERT INTO `job_skill`
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5);
INSERT INTO `offer`
VALUES (7, 5000, '2024-07-01', '2025-07-01', '2023-01-01 10:00:00.000000', '2024-06-30',
        'Strong problem-solving skills', 'Offer for Senior Developer', 12, 12, 1, 3, 1, 16, 11, 3, 1, 1),
       (8, 4500, '2024-07-01', '2025-07-01', '2023-01-01 10:00:00.000000', '2024-06-30', 'Good leadership experience',
        'Offer for HR Manager', 13, 13, 2, 1, 2, 19, 11, 2, 2, 2),
       (9, 4000, '2024-07-01', '2025-07-01', '2023-01-01 10:00:00.000000', '2024-06-30', 'Good negotiation skills',
        'Offer for Sales Representative', 20, 20, 3, 3, 3, 21, 12, 3, 3, 3),
       (10, 4200, '2024-07-01', '2025-07-01', '2023-01-01 10:00:00.000000', '2024-06-30', 'Creative mindset',
        'Offer for Marketing Specialist', 22, 22, 4, 4, 4, 24, 12, 4, 4, 4),
       (11, 4800, '2024-07-01', '2025-07-01', '2023-01-01 10:00:00.000000', '2024-06-30', 'Strong technical background',
        'Offer for Software Engineer', 23, 23, 5, 5, 5, 16, 12, 5, 5, 5),
       (12, 5500, '2024-07-01', '2025-07-01', '2023-01-01 10:00:00.000000', '2024-06-30', 'Strong analytical skills',
        'Offer for Finance Manager', 12, 12, 6, 1, 5, 16, 13, 6, 5, 6),
       (14, 5500, '2024-07-01', '2025-07-01', '2023-01-01 10:00:00.000000', '2024-06-30', 'Strong analytical skills',
        'Offer for Finance Manager', 12, 12, 1, 1, 2, 11, 11, 4, 4, 4),
       (15, 5500, '2024-07-01', '2025-07-01', '2023-01-01 10:00:00.000000', '2024-06-30', 'Strong analytical skills',
        'Offer for Finance Manager', 12, 12, 2, 1, 2, 12, 11, 4, 4, 4),
       (16, 5500, '2024-07-01', '2025-07-01', '2023-01-01 10:00:00.000000', '2024-06-30', 'Strong analytical skills',
        'Offer for Finance Manager', 12, 12, 3, 1, 2, 13, 11, 4, 4, 4),
       (17, 5500, '2024-07-01', '2025-07-01', '2023-01-01 10:00:00.000000', '2024-06-30', 'Strong analytical skills',
        'Offer for Finance Manager', 12, 12, 4, 1, 2, 14, 13, 4, 4, 4);

INSERT INTO chat(manager_id, recruiter_id)
VALUES (16, 12),
       (19, 13),
       (21, 20),
       (24, 22),
       (16, 23),
       (19, 12),
       (21, 12),
       (24, 12);

ALTER TABLE chat_detail
    MODIFY COLUMN Timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

INSERT INTO chat_detail(message, sender, chatid)
VALUES ('alo', 'ROLE_RECRUITER', 1),
       ('alo anh ơi', 'ROLE_RECRUITER', 6),
       ('em hỏi với', 'ROLE_RECRUITER', 7),
       ('duyệt hộ em cái offer với anh', 'ROLE_RECRUITER', 8);
