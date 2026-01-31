DROP DATABASE IF EXISTS txwl_platform;
CREATE DATABASE txwl_platform;

use txwl_platform;

-- 1 权限表
CREATE TABLE permission (
                            id          BIGINT AUTO_INCREMENT PRIMARY KEY,
                            description VARCHAR(100) NOT NULL COMMENT '权限中文描述'
) ENGINE=InnoDB COMMENT='权限字典';

-- 2 角色表
CREATE TABLE role (
                      id        BIGINT AUTO_INCREMENT PRIMARY KEY,
                      role_name VARCHAR(50) NOT NULL COMMENT '角色名称'
) ENGINE=InnoDB COMMENT='角色字典';

-- 3 角色-权限关联表
CREATE TABLE role_permission (
                                 id            BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 role_id       BIGINT NOT NULL,
                                 permission_id BIGINT NOT NULL,
                                 CONSTRAINT uk_role_perm UNIQUE (role_id, permission_id),
                                 CONSTRAINT fk_rp_role  FOREIGN KEY (role_id)       REFERENCES role(id)       ON DELETE CASCADE,
                                 CONSTRAINT fk_rp_perm  FOREIGN KEY (permission_id) REFERENCES permission(id) ON DELETE CASCADE
) ENGINE=InnoDB COMMENT='角色拥有的权限';

-- 4 用户表
CREATE TABLE user (
                      uid      BIGINT AUTO_INCREMENT PRIMARY KEY,
                      username VARCHAR(50)  NOT NULL COMMENT '登录账号',
                      phone    VARCHAR(20)  NOT NULL COMMENT '手机号',
                      pwd      VARCHAR(100) NOT NULL COMMENT '密码（密文）',
                      realname VARCHAR(50)  NOT NULL COMMENT '真实姓名',
                      role_id  BIGINT       NOT NULL,
                      CONSTRAINT uk_user_name  UNIQUE (username),
                      CONSTRAINT uk_user_phone UNIQUE (phone),
                      CONSTRAINT fk_user_role  FOREIGN KEY (role_id) REFERENCES role(id)
) ENGINE=InnoDB COMMENT='系统用户';

-- 5 试卷表
CREATE TABLE paper (
                       id              BIGINT AUTO_INCREMENT PRIMARY KEY,
                       paper_name      VARCHAR(100) NOT NULL COMMENT '试卷名称',
                       paper_type      TINYINT      NOT NULL COMMENT '1普通 2测评',
                       access_role_ids JSON         NOT NULL COMMENT '允许访问的角色ID数组，例：[1,2]',
                       INDEX idx_type (paper_type)
) ENGINE=InnoDB COMMENT='问卷/试卷';

-- 6 题目表
CREATE TABLE question (
                          id            BIGINT AUTO_INCREMENT PRIMARY KEY,
                          question_text TEXT NOT NULL COMMENT '题干',
                          q_type        ENUM('SINGLE','MULTI','BLANK','SORT','SCORE','FILE') NOT NULL COMMENT '题型',
                          paper_id      BIGINT       NOT NULL COMMENT '关联的试卷ID',
                          INDEX idx_paper_id (paper_id),
                          CONSTRAINT fk_question_paper FOREIGN KEY (paper_id) REFERENCES paper(id) ON DELETE CASCADE
) ENGINE=InnoDB COMMENT='题库';

-- 7 答案表（题库标准答案/选项）
CREATE TABLE answer (
                        id          BIGINT AUTO_INCREMENT PRIMARY KEY,
                        question_id BIGINT NOT NULL,
                        content     TEXT NOT NULL COMMENT '选项/答案内容',
                        correctness ENUM('CORRECT','WRONG','OPEN','SORT')
                            NOT NULL DEFAULT 'WRONG' COMMENT '正确程度',
                        CONSTRAINT fk_ans_qst FOREIGN KEY (question_id)
                            REFERENCES question(id) ON DELETE CASCADE,
                        INDEX idx_qid (question_id)
) ENGINE=InnoDB COMMENT='题库答案/选项';

-- 8 报告表（用户交卷后生成）
CREATE TABLE report (
                        id          BIGINT AUTO_INCREMENT PRIMARY KEY,
                        user_id     BIGINT NOT NULL,
                        paper_id    BIGINT NOT NULL,
                        url         VARCHAR(255) NOT NULL COMMENT '报告PDF/Html存放路径',
                        create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
                        CONSTRAINT fk_report_user  FOREIGN KEY (user_id)  REFERENCES user(uid)  ON DELETE CASCADE,
                        CONSTRAINT fk_report_paper FOREIGN KEY (paper_id) REFERENCES paper(id)  ON DELETE CASCADE,
                        INDEX idx_uid (user_id),
                        INDEX idx_pid (paper_id)
) ENGINE=InnoDB COMMENT='用户答卷报告';

-- 权限
INSERT INTO permission(description) VALUES
                                        ('问卷管理'),('题目管理'),('查看报告'),('导出报告'),('用户管理'),('角色权限管理');

-- 角色
INSERT INTO role(role_name) VALUES
('SUPER_ADMIN'),('PAPER_ADMIN'),('MATHER'),('FATHER'),('STUDENT');

-- 超级管理员拥有全部权限
INSERT INTO role_permission(role_id,permission_id)
SELECT 1,id FROM permission;

-- 问卷管理员：问卷+题目+查看报告
INSERT INTO role_permission(role_id,permission_id)
VALUES (2,1),(2,2),(2,3);

-- 普通用户：只能查看自己的报告
INSERT INTO role_permission(role_id,permission_id)
VALUES (3,3);

INSERT INTO paper(paper_name,paper_type,access_role_ids)
VALUES ('HRP问卷1',1,'[1,2,3]');