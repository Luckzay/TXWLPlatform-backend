package com.txwl.txwlplatform.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.txwl.txwlplatform.mapper.ReportMapper;
import com.txwl.txwlplatform.mapper.RoleMapper;
import com.txwl.txwlplatform.model.entity.User;
import com.txwl.txwlplatform.service.IAiService;
import com.txwl.txwlplatform.service.IQuestionService;
import com.txwl.txwlplatform.service.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class QuestionnaireSubmissionServiceImplTest {

    @Mock
    private ReportMapper reportMapper;

    @Mock
    private IAiService aiService;

    @Mock
    private IQuestionService questionService;

    @Mock
    private IUserService userService;

    @Mock
    private RoleMapper roleMapper;

    @InjectMocks
    private QuestionnaireSubmissionServiceImpl questionnaireSubmissionService;

    private JsonNode reportData;
    private String testJsonPath = "src/main/resources/test.json";

    @BeforeEach
    void setUp() throws IOException {
        // 读取test.json文件内容 - 使用UTF-8编码
        byte[] encoded = Files.readAllBytes(Paths.get(testJsonPath));
        String jsonContent = new String(encoded, StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        reportData = objectMapper.readTree(jsonContent);

        // 设置mock对象的行为
        User mockUser = new User();
        mockUser.setUid(1L);
        mockUser.setUsername("testUser");
        mockUser.setPhone("12345678901");
        lenient().when(userService.getUserById(anyLong())).thenReturn(mockUser);
    }

    @Test
    void testGenerateHtmlReport() throws Exception {
        // 准备测试参数 - 使用持久化路径而非临时路径
        String testFilePath = "src/main/resources/static/reports/test_report.html"; // 保存到实际报告目录
        
        // 确保目录存在
        File directory = new File("src/main/resources/static/reports/");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        
        // 调用私有方法generateHtmlReport
        try {
            ReflectionTestUtils.invokeMethod(
                questionnaireSubmissionService, 
                "generateHtmlReport", 
                testFilePath, 
                reportData, 
                1L, 
                1L
            );

            // 验证文件是否已创建
            assertTrue(Files.exists(Paths.get(testFilePath)), "HTML报告文件应该被创建");

            // 读取生成的文件内容，使用UTF-8编码
            byte[] encoded = Files.readAllBytes(Paths.get(testFilePath));
            String htmlContent = new String(encoded, StandardCharsets.UTF_8);
            
            // 输出一些调试信息
            System.out.println("HTML内容长度: " + htmlContent.length());
            System.out.println("前200字符: " + htmlContent.substring(0, Math.min(200, htmlContent.length())));
            
            // 验证HTML内容是否包含关键部分
            assertTrue(htmlContent.contains("<!DOCTYPE html>"), "HTML文档应该包含DOCTYPE声明");
            assertTrue(htmlContent.contains("生涯测评报告"), "HTML文档应该包含标题");
            assertTrue(htmlContent.contains("职业特质"), "HTML文档应该包含职业特质部分");
            assertTrue(htmlContent.contains("专业推荐"), "HTML文档应该包含专业推荐部分");
            assertTrue(htmlContent.contains("生涯规划"), "HTML文档应该包含生涯规划部分");
            
            // 验证中文内容是否正确显示
            assertTrue(htmlContent.contains("严谨细致，常自检工作，追求零误差"), "应该包含八型人格分析内容");
            assertTrue(htmlContent.contains("逻辑与动手双高，适合智能制造、电子信息"), "应该包含学科分析内容");
            
            // 不删除文件，保留生成的测试报告
            System.out.println("测试报告已保存至: " + testFilePath);
        } catch (Exception e) {
            // 输出错误信息以便调试
            System.out.println("生成HTML报告时发生错误: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Test
    void testGetStringValue() throws Exception {
        // 由于JSON结构使用中文键名，我们需要创建一个符合路径解析的JSON进行测试
        String testJson = "{"
            + "\"test\": {"
            + "  \"nested\": {"
            + "    \"value\": \"expected_value\""
            + "  }"
            + "}"
            + "}";
        ObjectMapper om = new ObjectMapper();
        JsonNode testNode = om.readTree(testJson);
        
        String result = (String) ReflectionTestUtils.invokeMethod(
            questionnaireSubmissionService,
            "getStringValue",
            testNode,
            "test.nested.value",
            "default"
        );
        
        assertEquals("expected_value", result);
    }

    @Test
    void testGetIntValue() throws Exception {
        // 由于JSON结构使用中文键名，我们需要创建一个符合路径解析的JSON进行测试
        String testJson = "{"
            + "\"test\": {"
            + "  \"nested\": {"
            + "    \"value\": 123"
            + "  }"
            + "}"
            + "}";
        ObjectMapper om = new ObjectMapper();
        JsonNode testNode = om.readTree(testJson);
        
        int result = (Integer) ReflectionTestUtils.invokeMethod(
            questionnaireSubmissionService,
            "getIntValue",
            testNode,
            "test.nested.value",
            0
        );
        
        assertEquals(123, result);
    }

    @Test
    void testGetTraitDetailsHtml() throws Exception {
        // 测试生成特质详情HTML的方法
        // 创建一个英文键名的测试JSON
        String testJson = "{"
            + "\"career_traits\": {"
            + "  \"holland_types\": {"
            + "    \"realistic\": { \"score\": 72, \"analysis\": \"动手能力强，偏好具体任务\" },"
            + "    \"investigative\": { \"score\": 85, \"analysis\": \"逻辑思维缜密，爱探索未知\" }"
            + "  }"
            + "}"
            + "}";
        ObjectMapper om = new ObjectMapper();
        JsonNode testNode = om.readTree(testJson);
        
        String result = (String) ReflectionTestUtils.invokeMethod(
            questionnaireSubmissionService,
            "getTraitDetailsHtml",
            testNode,
            "career_traits.holland_types",
            new String[]{"realistic", "investigative"}
        );
        
        assertNotNull(result, "结果不应该为空");
        assertTrue(result.contains("trait-card"), "应该包含trait-card类");
        assertTrue(result.contains("实用型"), "应该包含实用型（中文转换）");
        assertTrue(result.contains("研究型"), "应该包含研究型（中文转换）");
        // 检查是否包含评分相关的HTML
        assertTrue(result.contains("rating-bar-container"), "应该包含评分条容器");
    }

    @Test
    void testGetSubjectDetailsHtml() throws Exception {
        // 测试生成学科详情HTML的方法
        // 创建一个英文键名的测试JSON
        String testJson = "{"
            + "\"major_recommendation\": {"
            + "  \"twelve_disciplines\": {"
            + "    \"engineering\": { \"score\": 86, \"analysis\": \"逻辑与动手双高，适合智能制造、电子信息\" },"
            + "    \"science\": { \"score\": 88, \"analysis\": \"数学与基础学科兴趣浓，建议数据科学、应用物理\" }"
            + "  }"
            + "}"
            + "}";
        ObjectMapper om = new ObjectMapper();
        JsonNode testNode = om.readTree(testJson);
        
        String result = (String) ReflectionTestUtils.invokeMethod(
            questionnaireSubmissionService,
            "getSubjectDetailsHtml",
            testNode
        );
        
        assertNotNull(result, "结果不应该为空");
        assertTrue(result.contains("subject-card"), "应该包含subject-card类");
        assertTrue(result.contains("工学"), "应该包含工学（中文转换）");
        assertTrue(result.contains("理学"), "应该包含理学（中文转换）");
        // 检查是否包含评分相关的HTML
        assertTrue(result.contains("rating-bar-container"), "应该包含评分条容器");
    }

    @Test
    void testGetChartDataScript() throws Exception {
        // 测试生成图表数据脚本的方法
        // 创建一个英文键名的测试JSON
        String testJson = "{"
            + "\"career_traits\": {"
            + "  \"holland_types\": {"
            + "    \"realistic\": { \"score\": 72 },"
            + "    \"investigative\": { \"score\": 85 },"
            + "    \"artistic\": { \"score\": 68 },"
            + "    \"social\": { \"score\": 78 },"
            + "    \"enterprising\": { \"score\": 63 },"
            + "    \"conventional\": { \"score\": 59 }"
            + "  },"
            + "  \"five_powers\": {"
            + "    \"affinity\": { \"score\": 80 },"
            + "    \"driving_force\": { \"score\": 75 },"
            + "    \"execution\": { \"score\": 88 },"
            + "    \"stress_resistance\": { \"score\": 70 },"
            + "    \"planning\": { \"score\": 82 }"
            + "  },"
            + "  \"eight_personality\": {"
            + "    \"perfectionist_dolphin\": { \"score\": 84 },"
            + "    \"lively_parrot\": { \"score\": 65 },"
            + "    \"brave_tiger\": { \"score\": 71 },"
            + "    \"achiever_lion\": { \"score\": 77 },"
            + "    \"feeling_peacock\": { \"score\": 69 },"
            + "    \"thinker_owl\": { \"score\": 90 },"
            + "    \"peaceful_dove\": { \"score\": 73 },"
            + "    \"friendly_panda\": { \"score\": 78 }"
            + "  }"
            + "},"
            + "\"major_recommendation\": {"
            + "  \"twelve_disciplines\": {"
            + "    \"engineering\": { \"score\": 86 },"
            + "    \"literature\": { \"score\": 60 },"
            + "    \"science\": { \"score\": 88 },"
            + "    \"management\": { \"score\": 70 },"
            + "    \"medicine\": { \"score\": 75 },"
            + "    \"arts\": { \"score\": 62 },"
            + "    \"law\": { \"score\": 58 },"
            + "    \"agriculture\": { \"score\": 55 },"
            + "    \"education\": { \"score\": 76 },"
            + "    \"economics\": { \"score\": 68 },"
            + "    \"history\": { \"score\": 52 },"
            + "    \"philosophy\": { \"score\": 64 }"
            + "  }"
            + "}"
            + "}";
        ObjectMapper om = new ObjectMapper();
        JsonNode testNode = om.readTree(testJson);
        
        String result = (String) ReflectionTestUtils.invokeMethod(
            questionnaireSubmissionService,
            "getChartDataScript",
            testNode
        );
        
        assertNotNull(result, "结果不应该为空");
        assertTrue(result.contains("hollandData"), "应该包含hollandData变量");
        assertTrue(result.contains("fivePowersData"), "应该包含fivePowersData变量");
        assertTrue(result.contains("eightPersonalityData"), "应该包含eightPersonalityData变量");
        assertTrue(result.contains("twelveSubjectsData"), "应该包含twelveSubjectsData变量");
        assertTrue(result.contains("drawRadarChart"), "应该包含drawRadarChart函数");
        
        // 检查具体数据值
        assertTrue(result.contains("72,"), "霍兰德数据应该包含实用型评分72");
        assertTrue(result.contains("85,"), "霍兰德数据应该包含研究型评分85");
    }
}