-- 1. 插入第二条问卷
INSERT INTO txwl_platform.paper (paper_name, paper_type, access_role_ids)
VALUES ('HRP问卷2', 1, '[1,2,3]');

-- 2. 一次性插入 126 道题目
INSERT INTO txwl_platform.question (question_text, q_type, paper_id) VALUES
('你创意很多','SINGLE',2),
('你胆子非常大','SINGLE',2),
('你经常微笑','SINGLE',2),
('你擅长讲故事','SINGLE',2),
('你对自己要求很高','SINGLE',2),
('你做事小心翼翼','SINGLE',2),
('你擅长写作文','SINGLE',2),
('你有时会害羞','SINGLE',2),
('你喜欢音乐类工作','SINGLE',2),
('你有很多好建议','SINGLE',2),
('你说话比较直接','SINGLE',2),
('你会按照固定习惯来整理书包','SINGLE',2),
('你喜欢美术类工作','SINGLE',2),
('你自己养宠物或喜爱宠物','SINGLE',2),
('你善于理财','SINGLE',2),
('你擅长地理','SINGLE',2),
('你有时会迷茫','SINGLE',2),
('你唱歌很好听','SINGLE',2),
('你说话很谨慎','SINGLE',2),
('你喜欢看讲大自然、动植物的书或节目','SINGLE',2),
('你非常勇敢','SINGLE',2),
('你从不吵架','SINGLE',2),
('你善于观察','SINGLE',2),
('你玩魔方、拼图挺顺手','SINGLE',2),
('做事前你会充分准备','SINGLE',2),
('你善于辩论','SINGLE',2),
('你了解自己的长处和短处','SINGLE',2),
('你善于社交','SINGLE',2),
('你的作业总能按时完成','SINGLE',2),
('你能快速地模仿他人的肢体动作、姿势、舞蹈等','SINGLE',2),
('你画画时，能把物体样子画准','SINGLE',2),
('你喜欢自己动手修理东西','SINGLE',2),
('你性格内向','SINGLE',2),
('你以前的目标都实现了','SINGLE',2),
('你常思考自己的行为是否符合内心的价值观','SINGLE',2),
('你有时会发脾气','SINGLE',2),
('你会弹奏乐器','SINGLE',2),
('你有时讲话会紧张','SINGLE',2),
('你喜欢动手制作一些小物件','SINGLE',2),
('你喜欢写日记','SINGLE',2),
('你经常晨练','SINGLE',2),
('你精力充沛','SINGLE',2),
('你方向感很强','SINGLE',2),
('你计算能力很强','SINGLE',2),
('同学们都非常相信你','SINGLE',2),
('你喜欢设计类工作','SINGLE',2),
('你善于反思','SINGLE',2),
('你非常憨厚','SINGLE',2),
('你擅长数学','SINGLE',2),
('你出去玩，更喜欢自然景色','SINGLE',2),
('你喜欢热闹的环境','SINGLE',2),
('你善解人意','SINGLE',2),
('你自己可以说出许多花草树木的名称','SINGLE',2),
('你答应的事都能做到','SINGLE',2),
('你的演讲非常棒','SINGLE',2),
('你的动手能力很好','SINGLE',2),
('你在学校表现非常好','SINGLE',2),
('你喜欢睡懒觉','SINGLE',2),
('你定了计划后很少拖延','SINGLE',2),
('你喜欢体育类工作','SINGLE',2),
('你反应非常迅速','SINGLE',2),
('你在班级里的人缘很好','SINGLE',2),
('你擅长数学','SINGLE',2),
('你很有耐心','SINGLE',2),
('你喜欢自我挑战','SINGLE',2),
('你喜欢在户外活动','SINGLE',2),
('你非常细心','SINGLE',2),
('你经常请教老师或父母','SINGLE',2),
('你在陌生地方，易记住显眼建筑','SINGLE',2),
('你善于交流','SINGLE',2),
('你每天都会按照计划去学习','SINGLE',2),
('你能在脑中想象立体物转动拆分后的样子','SINGLE',2),
('学习时你会很严肃','SINGLE',2),
('你善于分析','SINGLE',2),
('你想象力丰富','SINGLE',2),
('对别人借给你的和你借给别人的东西，你都能记得很清楚','SINGLE',2),
('你不关注他人表现','SINGLE',2),
('你经常鼓励他人','SINGLE',2),
('你经常参加各项活动','SINGLE',2),
('你能从过去的经历中总结出对自己有用的经验','SINGLE',2),
('你有时会感到疲惫','SINGLE',2),
('你喜欢拆东西，组装东西','SINGLE',2);

-- 题 1
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (71, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (71, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (71, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (71, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (71, '完全符合', 'OPEN');

-- 题 2
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (72, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (72, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (72, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (72, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (72, '完全符合', 'OPEN');

-- 题 3
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (73, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (73, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (73, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (73, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (73, '完全符合', 'OPEN');

-- 题 4
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (74, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (74, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (74, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (74, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (74, '完全符合', 'OPEN');

-- 题 5
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (75, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (75, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (75, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (75, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (75, '完全符合', 'OPEN');

-- 题 6
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (76, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (76, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (76, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (76, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (76, '完全符合', 'OPEN');

-- 题 7
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (77, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (77, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (77, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (77, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (77, '完全符合', 'OPEN');

-- 题 8
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (78, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (78, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (78, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (78, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (78, '完全符合', 'OPEN');

-- 题 9
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (79, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (79, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (79, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (79, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (79, '完全符合', 'OPEN');

-- 题 10
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (80, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (80, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (80, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (80, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (80, '完全符合', 'OPEN');

-- 题 11
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (81, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (81, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (81, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (81, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (81, '完全符合', 'OPEN');

-- 题 12
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (82, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (82, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (82, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (82, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (82, '完全符合', 'OPEN');

-- 题 13
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (83, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (83, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (83, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (83, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (83, '完全符合', 'OPEN');

-- 题 14
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (84, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (84, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (84, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (84, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (84, '完全符合', 'OPEN');

-- 题 15
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (85, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (85, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (85, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (85, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (85, '完全符合', 'OPEN');

-- 题 16
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (86, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (86, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (86, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (86, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (86, '完全符合', 'OPEN');

-- 题 17
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (87, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (87, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (87, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (87, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (87, '完全符合', 'OPEN');

-- 题 18
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (88, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (88, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (88, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (88, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (88, '完全符合', 'OPEN');

-- 题 19
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (89, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (89, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (89, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (89, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (89, '完全符合', 'OPEN');

-- 题 20
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (90, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (90, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (90, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (90, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (90, '完全符合', 'OPEN');

-- 题 21
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (91, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (91, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (91, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (91, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (91, '完全符合', 'OPEN');

-- 题 22
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (92, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (92, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (92, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (92, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (92, '完全符合', 'OPEN');

-- 题 23
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (93, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (93, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (93, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (93, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (93, '完全符合', 'OPEN');

-- 题 24
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (94, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (94, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (94, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (94, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (94, '完全符合', 'OPEN');

-- 题 25
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (95, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (95, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (95, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (95, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (95, '完全符合', 'OPEN');

-- 题 26
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (96, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (96, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (96, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (96, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (96, '完全符合', 'OPEN');

-- 题 27
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (97, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (97, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (97, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (97, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (97, '完全符合', 'OPEN');

-- 题 28
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (98, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (98, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (98, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (98, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (98, '完全符合', 'OPEN');

-- 题 29
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (99, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (99, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (99, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (99, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (99, '完全符合', 'OPEN');

-- 题 30
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (100, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (100, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (100, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (100, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (100, '完全符合', 'OPEN');

-- 题 31
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (101, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (101, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (101, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (101, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (101, '完全符合', 'OPEN');

-- 题 32
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (102, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (102, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (102, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (102, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (102, '完全符合', 'OPEN');

-- 题 33
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (103, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (103, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (103, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (103, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (103, '完全符合', 'OPEN');

-- 题 34
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (104, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (104, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (104, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (104, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (104, '完全符合', 'OPEN');

-- 题 35
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (105, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (105, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (105, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (105, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (105, '完全符合', 'OPEN');

-- 题 36
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (106, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (106, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (106, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (106, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (106, '完全符合', 'OPEN');

-- 题 37
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (107, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (107, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (107, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (107, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (107, '完全符合', 'OPEN');

-- 题 38
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (108, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (108, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (108, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (108, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (108, '完全符合', 'OPEN');

-- 题 39
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (109, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (109, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (109, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (109, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (109, '完全符合', 'OPEN');

-- 题 40
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (110, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (110, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (110, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (110, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (110, '完全符合', 'OPEN');

-- 题 41
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (111, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (111, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (111, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (111, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (111, '完全符合', 'OPEN');

-- 题 42
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (112, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (112, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (112, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (112, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (112, '完全符合', 'OPEN');

-- 题 43
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (113, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (113, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (113, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (113, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (113, '完全符合', 'OPEN');

-- 题 44
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (114, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (114, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (114, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (114, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (114, '完全符合', 'OPEN');

-- 题 45
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (115, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (115, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (115, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (115, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (115, '完全符合', 'OPEN');

-- 题 46
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (116, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (116, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (116, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (116, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (116, '完全符合', 'OPEN');

-- 题 47
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (117, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (117, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (117, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (117, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (117, '完全符合', 'OPEN');

-- 题 48
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (118, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (118, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (118, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (118, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (118, '完全符合', 'OPEN');

-- 题 49
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (119, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (119, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (119, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (119, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (119, '完全符合', 'OPEN');

-- 题 50
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (120, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (120, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (120, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (120, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (120, '完全符合', 'OPEN');

-- 题 51
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (121, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (121, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (121, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (121, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (121, '完全符合', 'OPEN');

-- 题 52
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (122, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (122, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (122, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (122, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (122, '完全符合', 'OPEN');

-- 题 53
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (123, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (123, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (123, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (123, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (123, '完全符合', 'OPEN');

-- 题 54
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (124, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (124, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (124, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (124, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (124, '完全符合', 'OPEN');

-- 题 55
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (125, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (125, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (125, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (125, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (125, '完全符合', 'OPEN');

-- 题 56
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (126, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (126, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (126, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (126, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (126, '完全符合', 'OPEN');

-- 题 57
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (127, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (127, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (127, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (127, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (127, '完全符合', 'OPEN');

-- 题 58
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (128, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (128, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (128, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (128, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (128, '完全符合', 'OPEN');

-- 题 59
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (129, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (129, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (129, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (129, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (129, '完全符合', 'OPEN');

-- 题 60
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (130, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (130, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (130, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (130, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (130, '完全符合', 'OPEN');

-- 题 61
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (131, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (131, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (131, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (131, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (131, '完全符合', 'OPEN');

-- 题 62
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (132, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (132, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (132, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (132, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (132, '完全符合', 'OPEN');

-- 题 63
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (133, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (133, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (133, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (133, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (133, '完全符合', 'OPEN');

-- 题 64
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (134, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (134, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (134, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (134, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (134, '完全符合', 'OPEN');

-- 题 65
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (135, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (135, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (135, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (135, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (135, '完全符合', 'OPEN');

-- 题 66
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (136, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (136, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (136, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (136, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (136, '完全符合', 'OPEN');

-- 题 67
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (137, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (137, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (137, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (137, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (137, '完全符合', 'OPEN');

-- 题 68
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (138, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (138, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (138, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (138, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (138, '完全符合', 'OPEN');

-- 题 69
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (139, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (139, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (139, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (139, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (139, '完全符合', 'OPEN');

-- 题 70
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (140, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (140, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (140, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (140, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (140, '完全符合', 'OPEN');

-- 题 71
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (141, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (141, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (141, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (141, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (141, '完全符合', 'OPEN');

-- 题 72
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (142, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (142, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (142, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (142, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (142, '完全符合', 'OPEN');

-- 题 73
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (143, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (143, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (143, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (143, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (143, '完全符合', 'OPEN');

-- 题 74
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (144, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (144, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (144, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (144, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (144, '完全符合', 'OPEN');

-- 题 75
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (145, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (145, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (145, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (145, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (145, '完全符合', 'OPEN');

-- 题 76
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (146, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (146, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (146, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (146, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (146, '完全符合', 'OPEN');

-- 题 77
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (147, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (147, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (147, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (147, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (147, '完全符合', 'OPEN');

-- 题 78
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (148, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (148, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (148, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (148, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (148, '完全符合', 'OPEN');

-- 题 79
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (149, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (149, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (149, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (149, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (149, '完全符合', 'OPEN');

-- 题 80
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (150, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (150, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (150, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (150, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (150, '完全符合', 'OPEN');

-- 题 81
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (151, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (151, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (151, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (151, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (151, '完全符合', 'OPEN');

-- 题 82
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (152, '恰恰相反', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (152, '不太符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (152, '不确定', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (152, '比较符合', 'OPEN');
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES (152, '完全符合', 'OPEN');


-- 83 你经常感到：1.寂寞或忧郁，2.热闹或开朗
INSERT INTO txwl_platform.question (question_text, q_type, paper_id) VALUES
('你经常感到：1.寂寞或忧郁，2.热闹或开朗','SINGLE',2);
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES
(LAST_INSERT_ID(),'非常偏向前者','OPEN'),
(LAST_INSERT_ID(),'比较偏向前者','OPEN'),
(LAST_INSERT_ID(),'中立','OPEN'),
(LAST_INSERT_ID(),'比较偏向后者','OPEN'),
(LAST_INSERT_ID(),'非常偏向后者','OPEN');

-- 84 你做事：1.没毅力，遇到点困难就想放弃，2.有韧劲，碰到困难也能坚持下去
INSERT INTO txwl_platform.question (question_text, q_type, paper_id) VALUES
('你做事：1.没毅力，遇到点困难就想放弃，2.有韧劲，碰到困难也能坚持下去','SINGLE',2);
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES
(LAST_INSERT_ID(),'非常偏向前者','OPEN'),
(LAST_INSERT_ID(),'比较偏向前者','OPEN'),
(LAST_INSERT_ID(),'中立','OPEN'),
(LAST_INSERT_ID(),'比较偏向后者','OPEN'),
(LAST_INSERT_ID(),'非常偏向后者','OPEN');

-- 85 你在艺术方面：1.有很多爱好，2.没有任何爱好
INSERT INTO txwl_platform.question (question_text, q_type, paper_id) VALUES
('你在艺术方面：1.有很多爱好，2.没有任何爱好','SINGLE',2);
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES
(LAST_INSERT_ID(),'非常偏向前者','OPEN'),
(LAST_INSERT_ID(),'比较偏向前者','OPEN'),
(LAST_INSERT_ID(),'中立','OPEN'),
(LAST_INSERT_ID(),'比较偏向后者','OPEN'),
(LAST_INSERT_ID(),'非常偏向后者','OPEN');

-- 86 你倾向于：1.喋喋不休，2.沉默寡言
INSERT INTO txwl_platform.question (question_text, q_type, paper_id) VALUES
('你倾向于：1.喋喋不休，2.沉默寡言','SINGLE',2);
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES
(LAST_INSERT_ID(),'非常偏向前者','OPEN'),
(LAST_INSERT_ID(),'比较偏向前者','OPEN'),
(LAST_INSERT_ID(),'中立','OPEN'),
(LAST_INSERT_ID(),'比较偏向后者','OPEN'),
(LAST_INSERT_ID(),'非常偏向后者','OPEN');

-- 87 你比别人更：1.积极活跃，2.消极懈怠
INSERT INTO txwl_platform.question (question_text, q_type, paper_id) VALUES
('你比别人更：1.积极活跃，2.消极懈怠','SINGLE',2);
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES
(LAST_INSERT_ID(),'非常偏向前者','OPEN'),
(LAST_INSERT_ID(),'比较偏向前者','OPEN'),
(LAST_INSERT_ID(),'中立','OPEN'),
(LAST_INSERT_ID(),'比较偏向后者','OPEN'),
(LAST_INSERT_ID(),'非常偏向后者','OPEN');

-- 88 很多时候，当事情不对劲时，你会感到：1.挫败、想放弃，2.振作、想坚持
INSERT INTO txwl_platform.question (question_text, q_type, paper_id) VALUES
('很多时候，当事情不对劲时，你会感到：1.挫败、想放弃，2.振作、想坚持','SINGLE',2);
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES
(LAST_INSERT_ID(),'非常偏向前者','OPEN'),
(LAST_INSERT_ID(),'比较偏向前者','OPEN'),
(LAST_INSERT_ID(),'中立','OPEN'),
(LAST_INSERT_ID(),'比较偏向后者','OPEN'),
(LAST_INSERT_ID(),'非常偏向后者','OPEN');

-- 89 你的思维：1.复杂，且经常沉思，2.简单，且很少深思
INSERT INTO txwl_platform.question (question_text, q_type, paper_id) VALUES
('你的思维：1.复杂，且经常沉思，2.简单，且很少深思','SINGLE',2);
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES
(LAST_INSERT_ID(),'非常偏向前者','OPEN'),
(LAST_INSERT_ID(),'比较偏向前者','OPEN'),
(LAST_INSERT_ID(),'中立','OPEN'),
(LAST_INSERT_ID(),'比较偏向后者','OPEN'),
(LAST_INSERT_ID(),'非常偏向后者','OPEN');

-- 90 以下选项更适合你的是：1.你很少对别人无礼，2.你经常对别人粗鲁
INSERT INTO txwl_platform.question (question_text, q_type, paper_id) VALUES
('以下选项更适合你的是：1.你很少对别人无礼，2.你经常对别人粗鲁','SINGLE',2);
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES
(LAST_INSERT_ID(),'非常偏向前者','OPEN'),
(LAST_INSERT_ID(),'比较偏向前者','OPEN'),
(LAST_INSERT_ID(),'中立','OPEN'),
(LAST_INSERT_ID(),'比较偏向后者','OPEN'),
(LAST_INSERT_ID(),'非常偏向后者','OPEN');

-- 91 你总会把自己的东西摆放得：1.整齐且保持清洁，2.杂乱无章且弄得肮脏
INSERT INTO txwl_platform.question (question_text, q_type, paper_id) VALUES
('你总会把自己的东西摆放得：1.整齐且保持清洁，2.杂乱无章且弄得肮脏','SINGLE',2);
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES
(LAST_INSERT_ID(),'非常偏向前者','OPEN'),
(LAST_INSERT_ID(),'比较偏向前者','OPEN'),
(LAST_INSERT_ID(),'中立','OPEN'),
(LAST_INSERT_ID(),'比较偏向后者','OPEN'),
(LAST_INSERT_ID(),'非常偏向后者','OPEN');

-- 92 你对待事情：1.很认真，2.很敷衍
INSERT INTO txwl_platform.question (question_text, q_type, paper_id) VALUES
('你对待事情：1.很认真，2.很敷衍','SINGLE',2);
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES
(LAST_INSERT_ID(),'非常偏向前者','OPEN'),
(LAST_INSERT_ID(),'比较偏向前者','OPEN'),
(LAST_INSERT_ID(),'中立','OPEN'),
(LAST_INSERT_ID(),'比较偏向后者','OPEN'),
(LAST_INSERT_ID(),'非常偏向后者','OPEN');

-- 93 以下选项更适合你的是：1.你富有创造力，2.你缺乏创造力
INSERT INTO txwl_platform.question (question_text, q_type, paper_id) VALUES
('以下选项更适合你的是：1.你富有创造力，2.你缺乏创造力','SINGLE',2);
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES
(LAST_INSERT_ID(),'非常偏向前者','OPEN'),
(LAST_INSERT_ID(),'比较偏向前者','OPEN'),
(LAST_INSERT_ID(),'中立','OPEN'),
(LAST_INSERT_ID(),'比较偏向后者','OPEN'),
(LAST_INSERT_ID(),'非常偏向后者','OPEN');

-- 94 你喜欢：1.占据主导地位，充当领导者，2.处于从属地位，充当追随者
INSERT INTO txwl_platform.question (question_text, q_type, paper_id) VALUES
('你喜欢：1.占据主导地位，充当领导者，2.处于从属地位，充当追随者','SINGLE',2);
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES
(LAST_INSERT_ID(),'非常偏向前者','OPEN'),
(LAST_INSERT_ID(),'比较偏向前者','OPEN'),
(LAST_INSERT_ID(),'中立','OPEN'),
(LAST_INSERT_ID(),'比较偏向后者','OPEN'),
(LAST_INSERT_ID(),'非常偏向后者','OPEN');

-- 95 你对艺术、音乐或文学：1.非常着迷，2.非常反感
INSERT INTO txwl_platform.question (question_text, q_type, paper_id) VALUES
('你对艺术、音乐或文学：1.非常着迷，2.非常反感','SINGLE',2);
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES
(LAST_INSERT_ID(),'非常偏向前者','OPEN'),
(LAST_INSERT_ID(),'比较偏向前者','OPEN'),
(LAST_INSERT_ID(),'中立','OPEN'),
(LAST_INSERT_ID(),'比较偏向后者','OPEN'),
(LAST_INSERT_ID(),'非常偏向后者','OPEN');

-- 96 你是一个：1.充满烦恼的人，2.满心欢喜的人
INSERT INTO txwl_platform.question (question_text, q_type, paper_id) VALUES
('你是一个：1.充满烦恼的人，2.满心欢喜的人','SINGLE',2);
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES
(LAST_INSERT_ID(),'非常偏向前者','OPEN'),
(LAST_INSERT_ID(),'比较偏向前者','OPEN'),
(LAST_INSERT_ID(),'中立','OPEN'),
(LAST_INSERT_ID(),'比较偏向后者','OPEN'),
(LAST_INSERT_ID(),'非常偏向后者','OPEN');

-- 97 以下选项更适合你的是：1.你尊重别人，2.你轻视别人
INSERT INTO txwl_platform.question (question_text, q_type, paper_id) VALUES
('以下选项更适合你的是：1.你尊重别人，2.你轻视别人','SINGLE',2);
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES
(LAST_INSERT_ID(),'非常偏向前者','OPEN'),
(LAST_INSERT_ID(),'比较偏向前者','OPEN'),
(LAST_INSERT_ID(),'中立','OPEN'),
(LAST_INSERT_ID(),'比较偏向后者','OPEN'),
(LAST_INSERT_ID(),'非常偏向后者','OPEN');

-- 98 你经常对别人：1.冷若冰霜或漠不关心，2.热情洋溢或关怀备至
INSERT INTO txwl_platform.question (question_text, q_type, paper_id) VALUES
('你经常对别人：1.冷若冰霜或漠不关心，2.热情洋溢或关怀备至','SINGLE',2);
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES
(LAST_INSERT_ID(),'非常偏向前者','OPEN'),
(LAST_INSERT_ID(),'比较偏向前者','OPEN'),
(LAST_INSERT_ID(),'中立','OPEN'),
(LAST_INSERT_ID(),'比较偏向后者','OPEN'),
(LAST_INSERT_ID(),'非常偏向后者','OPEN');

-- 99 你经常感到：1.紧张及心神不定，2.从容及镇定自若
INSERT INTO txwl_platform.question (question_text, q_type, paper_id) VALUES
('你经常感到：1.紧张及心神不定，2.从容及镇定自若','SINGLE',2);
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES
(LAST_INSERT_ID(),'非常偏向前者','OPEN'),
(LAST_INSERT_ID(),'比较偏向前者','OPEN'),
(LAST_INSERT_ID(),'中立','OPEN'),
(LAST_INSERT_ID(),'比较偏向后者','OPEN'),
(LAST_INSERT_ID(),'非常偏向后者','OPEN');

-- 100 你经常会：1.以最大的善意去揣测别人，2.以最大的恶意去揣测别人
INSERT INTO txwl_platform.question (question_text, q_type, paper_id) VALUES
('你经常会：1.以最大的善意去揣测别人，2.以最大的恶意去揣测别人','SINGLE',2);
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES
(LAST_INSERT_ID(),'非常偏向前者','OPEN'),
(LAST_INSERT_ID(),'比较偏向前者','OPEN'),
(LAST_INSERT_ID(),'中立','OPEN'),
(LAST_INSERT_ID(),'比较偏向后者','OPEN'),
(LAST_INSERT_ID(),'非常偏向后者','OPEN');

-- 101 你喜欢：1.原创事物，常有新想法，2.模仿事物，常有旧想法
INSERT INTO txwl_platform.question (question_text, q_type, paper_id) VALUES
('你喜欢：1.原创事物，常有新想法，2.模仿事物，常有旧想法','SINGLE',2);
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES
(LAST_INSERT_ID(),'非常偏向前者','OPEN'),
(LAST_INSERT_ID(),'比较偏向前者','OPEN'),
(LAST_INSERT_ID(),'中立','OPEN'),
(LAST_INSERT_ID(),'比较偏向后者','OPEN'),
(LAST_INSERT_ID(),'非常偏向后者','OPEN');

-- 102 你的情绪：1.不稳定，喜怒无常，容易情绪化，2.很稳定，心平气和，不容易情绪化
INSERT INTO txwl_platform.question (question_text, q_type, paper_id) VALUES
('你的情绪：1.不稳定，喜怒无常，容易情绪化，2.很稳定，心平气和，不容易情绪化','SINGLE',2);
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES
(LAST_INSERT_ID(),'非常偏向前者','OPEN'),
(LAST_INSERT_ID(),'比较偏向前者','OPEN'),
(LAST_INSERT_ID(),'中立','OPEN'),
(LAST_INSERT_ID(),'比较偏向后者','OPEN'),
(LAST_INSERT_ID(),'非常偏向后者','OPEN');

-- 103 你愿意：1.承担责任，2.推卸责任
INSERT INTO txwl_platform.question (question_text, q_type, paper_id) VALUES
('你愿意：1.承担责任，2.推卸责任','SINGLE',2);
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES
(LAST_INSERT_ID(),'非常偏向前者','OPEN'),
(LAST_INSERT_ID(),'比较偏向前者','OPEN'),
(LAST_INSERT_ID(),'中立','OPEN'),
(LAST_INSERT_ID(),'比较偏向后者','OPEN'),
(LAST_INSERT_ID(),'非常偏向后者','OPEN');

-- 104 你为人：1.可靠，值得别人信赖，2.不靠谱，让人难以信任
INSERT INTO txwl_platform.question (question_text, q_type, paper_id) VALUES
('你为人：1.可靠，值得别人信赖，2.不靠谱，让人难以信任','SINGLE',2);
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES
(LAST_INSERT_ID(),'非常偏向前者','OPEN'),
(LAST_INSERT_ID(),'比较偏向前者','OPEN'),
(LAST_INSERT_ID(),'中立','OPEN'),
(LAST_INSERT_ID(),'比较偏向后者','OPEN'),
(LAST_INSERT_ID(),'非常偏向后者','OPEN');

-- 105 以下选项更适合你的是：1.你有同情心，内心柔软，2.你缺乏同情心，内心坚硬
INSERT INTO txwl_platform.question (question_text, q_type, paper_id) VALUES
('以下选项更适合你的是：1.你有同情心，内心柔软，2.你缺乏同情心，内心坚硬','SINGLE',2);
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES
(LAST_INSERT_ID(),'非常偏向前者','OPEN'),
(LAST_INSERT_ID(),'比较偏向前者','OPEN'),
(LAST_INSERT_ID(),'中立','OPEN'),
(LAST_INSERT_ID(),'比较偏向后者','OPEN'),
(LAST_INSERT_ID(),'非常偏向后者','OPEN');

-- 106 你更愿意：1.与人合作，而非与人竞争，2.与人竞争，而非与人合作
INSERT INTO txwl_platform.question (question_text, q_type, paper_id) VALUES
('你更愿意：1.与人合作，而非与人竞争，2.与人竞争，而非与人合作','SINGLE',2);
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES
(LAST_INSERT_ID(),'非常偏向前者','OPEN'),
(LAST_INSERT_ID(),'比较偏向前者','OPEN'),
(LAST_INSERT_ID(),'中立','OPEN'),
(LAST_INSERT_ID(),'比较偏向后者','OPEN'),
(LAST_INSERT_ID(),'非常偏向后者','OPEN');

-- 107 以下选项更适合你的是：1.你善于完成任务，2.你不擅长把任务完成好
INSERT INTO txwl_platform.question (question_text, q_type, paper_id) VALUES
('以下选项更适合你的是：1.你善于完成任务，2.你不擅长把任务完成好','SINGLE',2);
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES
(LAST_INSERT_ID(),'非常偏向前者','OPEN'),
(LAST_INSERT_ID(),'比较偏向前者','OPEN'),
(LAST_INSERT_ID(),'中立','OPEN'),
(LAST_INSERT_ID(),'比较偏向后者','OPEN'),
(LAST_INSERT_ID(),'非常偏向后者','OPEN');

-- 108 有时你会：1.羞愧得想躲起来，2.坦然得想展示自己
INSERT INTO txwl_platform.question (question_text, q_type, paper_id) VALUES
('有时你会：1.羞愧得想躲起来，2.坦然得想展示自己','SINGLE',2);
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES
(LAST_INSERT_ID(),'非常偏向前者','OPEN'),
(LAST_INSERT_ID(),'比较偏向前者','OPEN'),
(LAST_INSERT_ID(),'中立','OPEN'),
(LAST_INSERT_ID(),'比较偏向后者','OPEN'),
(LAST_INSERT_ID(),'非常偏向后者','OPEN');

-- 109 你做事：1.有条不紊，2.杂乱无章
INSERT INTO txwl_platform.question (question_text, q_type, paper_id) VALUES
('你做事：1.有条不紊，2.杂乱无章','SINGLE',2);
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES
(LAST_INSERT_ID(),'非常偏向前者','OPEN'),
(LAST_INSERT_ID(),'比较偏向前者','OPEN'),
(LAST_INSERT_ID(),'中立','OPEN'),
(LAST_INSERT_ID(),'比较偏向后者','OPEN'),
(LAST_INSERT_ID(),'非常偏向后者','OPEN');

-- 110 你对抽象概念：1.很感兴趣，2.很排斥
INSERT INTO txwl_platform.question (question_text, q_type, paper_id) VALUES
('你对抽象概念：1.很感兴趣，2.很排斥','SINGLE',2);
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES
(LAST_INSERT_ID(),'非常偏向前者','OPEN'),
(LAST_INSERT_ID(),'比较偏向前者','OPEN'),
(LAST_INSERT_ID(),'中立','OPEN'),
(LAST_INSERT_ID(),'比较偏向后者','OPEN'),
(LAST_INSERT_ID(),'非常偏向后者','OPEN');

INSERT INTO txwl_platform.question (question_text, q_type, paper_id) VALUES
('按照您的对不同社团的兴趣程度打分（满分5分），音乐社团','SCORE',2);
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES
(LAST_INSERT_ID(),'1分','OPEN'),
(LAST_INSERT_ID(),'2分','OPEN'),
(LAST_INSERT_ID(),'3分','OPEN'),
(LAST_INSERT_ID(),'4分','OPEN'),
(LAST_INSERT_ID(),'5分','OPEN');
INSERT INTO txwl_platform.question (question_text, q_type, paper_id) VALUES
('按照您的对不同社团的兴趣程度打分（满分5分），体育社团','SCORE',2);
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES
(LAST_INSERT_ID(),'1分','OPEN'),
(LAST_INSERT_ID(),'2分','OPEN'),
(LAST_INSERT_ID(),'3分','OPEN'),
(LAST_INSERT_ID(),'4分','OPEN'),
(LAST_INSERT_ID(),'5分','OPEN');
INSERT INTO txwl_platform.question (question_text, q_type, paper_id) VALUES
('按照您的对不同社团的兴趣程度打分（满分5分），绘画社团','SCORE',2);
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES
(LAST_INSERT_ID(),'1分','OPEN'),
(LAST_INSERT_ID(),'2分','OPEN'),
(LAST_INSERT_ID(),'3分','OPEN'),
(LAST_INSERT_ID(),'4分','OPEN'),
(LAST_INSERT_ID(),'5分','OPEN');INSERT INTO txwl_platform.question (question_text, q_type, paper_id) VALUES
('按照您的对不同社团的兴趣程度打分（满分5分），摄影社团','SCORE',2);
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES
(LAST_INSERT_ID(),'1分','OPEN'),
(LAST_INSERT_ID(),'2分','OPEN'),
(LAST_INSERT_ID(),'3分','OPEN'),
(LAST_INSERT_ID(),'4分','OPEN'),
(LAST_INSERT_ID(),'5分','OPEN');INSERT INTO txwl_platform.question (question_text, q_type, paper_id) VALUES
('按照您的对不同社团的兴趣程度打分（满分5分），舞蹈社团','SCORE',2);
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES
(LAST_INSERT_ID(),'1分','OPEN'),
(LAST_INSERT_ID(),'2分','OPEN'),
(LAST_INSERT_ID(),'3分','OPEN'),
(LAST_INSERT_ID(),'4分','OPEN'),
(LAST_INSERT_ID(),'5分','OPEN');
INSERT INTO txwl_platform.question (question_text, q_type, paper_id) VALUES
('按照您的对不同社团的兴趣程度打分（满分5分），表演社团','SCORE',2);
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES
(LAST_INSERT_ID(),'1分','OPEN'),
(LAST_INSERT_ID(),'2分','OPEN'),
(LAST_INSERT_ID(),'3分','OPEN'),
(LAST_INSERT_ID(),'4分','OPEN'),
(LAST_INSERT_ID(),'5分','OPEN');

INSERT INTO txwl_platform.question (question_text, q_type, paper_id) VALUES
('请想象你有机会参加一场涵盖多学科的体验营，请按照你对这些活动的感兴趣程度拖拽下方选项，进行排序。','SORT',2);
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES
(LAST_INSERT_ID(),'体验营1：参与经典文学作品的赏析会，和大家一起探讨作品中的人物形象与情感表达','SORT'),
(LAST_INSERT_ID(),'体验营2：运用公式和模型，计算并预测某一自然现象的发展趋势','SORT'),
(LAST_INSERT_ID(),'体验营3：和来自不同国家的同龄人进行外语对话，交流各自国家的文化习俗','SORT'),
(LAST_INSERT_ID(),'体验营4：动手操作实验，观察并记录不同电路连接方式下灯泡的亮度变化','SORT'),
(LAST_INSERT_ID(),'体验营5：进行小实验，混合不同的试剂，观察其产生的颜色变化和气体生成','SORT'),
(LAST_INSERT_ID(),'体验营6：到植物园观察各种植物的生长状态，记录不同植物的叶片形状和开花特点','SORT');

INSERT INTO txwl_platform.question (question_text, q_type, paper_id) VALUES
('结合你平时各科的考试成绩，从目前展示的学科中，拖拽学科名称，进行排序。','SORT',2);
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES
(LAST_INSERT_ID(),'语文','SORT'),
(LAST_INSERT_ID(),'数学','SORT'),
(LAST_INSERT_ID(),'外语','SORT'),
(LAST_INSERT_ID(),'物理','SORT'),
(LAST_INSERT_ID(),'化学','SORT'),
(LAST_INSERT_ID(),'生物','SORT');

INSERT INTO txwl_platform.question (question_text, q_type, paper_id) VALUES
('情景一：一位秘典守护者精心挑选了6个魔力视频，只要你看完这些视频，就能掌握视频里面的知识，请你将这6个视频按照你的喜好度拖拽，进行排序。','SORT',2);
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES
(LAST_INSERT_ID(),'学习用品收纳方法','SORT'),
(LAST_INSERT_ID(),'人际交往的那些事','SORT'),
(LAST_INSERT_ID(),'领导力21法则','SORT'),
(LAST_INSERT_ID(),'量子科学趣识','SORT'),
(LAST_INSERT_ID(),'厨房宝典','SORT'),
(LAST_INSERT_ID(),'世界音乐之旅','SORT');

INSERT INTO txwl_platform.question (question_text, q_type, paper_id) VALUES
('情景二：假如你获得了一场高端名人聚会的参与邀请，现场汇聚了来自各行各业不同领域的顶尖优秀人士，而你被安排为这些名人提供临时助理支持。现在需要你从 “最想共事” 的角度出发，选出自己最希望服务的对象，并按个人意愿度从高到低排序，你会如何确定这个先后顺序呢？','SORT',2);
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES
(LAST_INSERT_ID(),'企业家','SORT'),
(LAST_INSERT_ID(),'高级会计师','SORT'),
(LAST_INSERT_ID(),'科学家','SORT'),
(LAST_INSERT_ID(),'教育家','SORT'),
(LAST_INSERT_ID(),'艺术家','SORT'),
(LAST_INSERT_ID(),'陶艺师','SORT');
INSERT INTO txwl_platform.question (question_text, q_type, paper_id) VALUES
('情景三：假如你所在的城市将要举办一次科技展览会，你想报名参加科技展览的工作人员招募，请将以下6个岗位按照喜好度，进行排序。','SORT',2);
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES
(LAST_INSERT_ID(),'项目负责人','SORT'),
(LAST_INSERT_ID(),'机械修理师','SORT'),
(LAST_INSERT_ID(),'科技史研究员','SORT'),
(LAST_INSERT_ID(),'志愿者','SORT'),
(LAST_INSERT_ID(),'财务专员','SORT'),
(LAST_INSERT_ID(),'展馆布景师','SORT');
INSERT INTO txwl_platform.question (question_text, q_type, paper_id) VALUES
('情景四：假如你所在的城市里有一块空地，现在需要你为它规划改造方向。改造后的空间既可以作为私人使用，也能对外开放。下方给出了6个不同的改造选项，请你根据个人喜好程度，将这6个改造方向进行排序，你会如何确定这个先后顺序呢？','SORT',2);
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES
(LAST_INSERT_ID(),'机械工作室','SORT'),
(LAST_INSERT_ID(),'科学实验室','SORT'),
(LAST_INSERT_ID(),'档案托管中心','SORT'),
(LAST_INSERT_ID(),'战略指挥中心','SORT'),
(LAST_INSERT_ID(),'救助站','SORT'),
(LAST_INSERT_ID(),'录音棚','SORT');

INSERT INTO txwl_platform.question (question_text, q_type, paper_id) VALUES
('情景五：某次旅行时，你乘坐的飞机突发故障，必须立刻紧急降落。此时飞机恰好飞在 6 座岛屿之间，而这些岛屿都没有与外界联系的渠道，这意味着你若在此停留，至少要在岛上生活 5 年。值得庆幸的是，6 座岛屿的居民都非常友善，会积极接纳你。请你按照个人对岛屿的喜好程度，给这 6 座岛屿排个序，选出你最愿意生活的岛屿依次排列。','SORT',2);
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES
(LAST_INSERT_ID(),'显赫富庶的岛屿','SORT'),
(LAST_INSERT_ID(),'井然有序的岛屿','SORT'),
(LAST_INSERT_ID(),'自然原始的岛屿','SORT'),
(LAST_INSERT_ID(),'深思冥想的岛屿','SORT'),
(LAST_INSERT_ID(),'美丽浪漫的岛屿','SORT'),
(LAST_INSERT_ID(),'友善亲切的岛屿','SORT');
INSERT INTO txwl_platform.question (question_text, q_type, paper_id) VALUES
('大学毕业后你有以下打算吗？','MULTI',2);
INSERT INTO txwl_platform.answer (question_id, content, correctness) VALUES
(LAST_INSERT_ID(),'持续深造到博士','OPEN'),
(LAST_INSERT_ID(),'出国留学','OPEN'),
(LAST_INSERT_ID(),'进入国央企','OPEN'),
(LAST_INSERT_ID(),'考公考编','OPEN'),
(LAST_INSERT_ID(),'创业','OPEN'),
(LAST_INSERT_ID(),'不确定','OPEN');