package com.txwl.txwlplatform.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.txwl.txwlplatform.model.entity.Paper;
import com.txwl.txwlplatform.model.entity.Question;
import com.txwl.txwlplatform.model.entity.Answer;
import com.txwl.txwlplatform.service.IPaperService;
import com.txwl.txwlplatform.service.IQuestionService;
import com.txwl.txwlplatform.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/paper-management")
public class PaperManagementController {

    @Autowired
    private IPaperService paperService;

    @Autowired
    private IQuestionService questionService;

    @Autowired
    private IUserService userService;

    /**
     * 获取所有问卷列表（仅限超级管理员和问卷管理员访问）
     */
    @GetMapping("/papers")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('PAPER_ADMIN')")
    public ResponseEntity<List<Paper>> getAllPapers() {
        List<Paper> papers = paperService.getAllPapers();
        return ResponseEntity.ok(papers);
    }

    /**
     * 分页获取问卷列表（仅限超级管理员和问卷管理员访问）
     */
    @GetMapping("/papers/page")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('PAPER_ADMIN')")
    public ResponseEntity<Page<Paper>> getPapersPage(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        Page<Paper> papersPage = paperService.getPapersPage(pageNum, pageSize);
        return ResponseEntity.ok(papersPage);
    }

    /**
     * 根据ID获取问卷详情（仅限超级管理员和问卷管理员访问）
     */
    @GetMapping("/papers/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('PAPER_ADMIN')")
    public ResponseEntity<Paper> getPaperById(@PathVariable Long id) {
        Paper paper = paperService.getPaperById(id);
        if (paper == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(paper);
    }

    /**
     * 创建问卷（仅限超级管理员和问卷管理员访问）
     */
    @PostMapping("/papers")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('PAPER_ADMIN')")
    public ResponseEntity<Paper> createPaper(@RequestBody Paper paper) {
        // 确保超级管理员和问卷管理员始终可见
        ensureAdminAccess(paper);
        paperService.createPaper(paper);
        return ResponseEntity.ok(paper);
    }

    /**
     * 更新问卷（仅限超级管理员和问卷管理员访问）
     */
    @PutMapping("/papers/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('PAPER_ADMIN')")
    public ResponseEntity<Paper> updatePaper(@PathVariable Long id, @RequestBody Paper paper) {
        paper.setId(id);
        // 确保超级管理员和问卷管理员始终可见
        ensureAdminAccess(paper);
        paperService.updatePaper(paper);
        return ResponseEntity.ok(paper);
    }

    /**
     * 删除问卷（仅限超级管理员和问卷管理员访问）
     */
    @DeleteMapping("/papers/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('PAPER_ADMIN')")
    public ResponseEntity<Void> deletePaper(@PathVariable Long id) {
        paperService.deletePaper(id);
        return ResponseEntity.ok().build();
    }

    /**
     * 获取问卷下的题目分页（仅限超级管理员和问卷管理员访问）
     */
    @GetMapping("/papers/{paperId}/questions/page")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('PAPER_ADMIN')")
    public ResponseEntity<Page<Map<String, Object>>> getQuestionsByPaperId(
            @PathVariable Long paperId,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        
        Page<Map<String, Object>> questionsPage = questionService.getQuestionsWithAnswersByPaperIdPaginated(paperId, pageNum, pageSize);
        return ResponseEntity.ok(questionsPage);
    }

    /**
     * 添加题目到问卷（仅限超级管理员和问卷管理员访问）
     */
    @PostMapping("/papers/{paperId}/questions")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('PAPER_ADMIN')")
    public ResponseEntity<Question> addQuestionToPaper(@PathVariable Long paperId, @RequestBody Question question) {
        question.setPaperId(paperId);
        Question createdQuestion = questionService.createQuestion(question);
        return ResponseEntity.ok(createdQuestion);
    }

    /**
     * 更新题目（仅限超级管理员和问卷管理员访问）
     */
    @PutMapping("/questions/{questionId}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('PAPER_ADMIN')")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long questionId, @RequestBody Question question) {
        question.setId(questionId);
        Question updatedQuestion = questionService.updateQuestion(question);
        return ResponseEntity.ok(updatedQuestion);
    }

    /**
     * 删除题目（仅限超级管理员和问卷管理员访问）
     */
    @DeleteMapping("/questions/{questionId}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('PAPER_ADMIN')")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long questionId) {
        questionService.deleteQuestion(questionId);
        return ResponseEntity.ok().build();
    }

    /**
     * 为题目添加选项（仅限超级管理员和问卷管理员访问）
     */
    @PostMapping("/questions/{questionId}/answers")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('PAPER_ADMIN')")
    public ResponseEntity<Answer> addAnswerToQuestion(@PathVariable Long questionId, @RequestBody Answer answer) {
        answer.setQuestionId(questionId);
        Answer createdAnswer = questionService.createAnswer(answer);
        return ResponseEntity.ok(createdAnswer);
    }

    /**
     * 更新选项（仅限超级管理员和问卷管理员访问）
     */
    @PutMapping("/answers/{answerId}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('PAPER_ADMIN')")
    public ResponseEntity<Answer> updateAnswer(@PathVariable Long answerId, @RequestBody Answer answer) {
        answer.setId(answerId);
        Answer updatedAnswer = questionService.updateAnswer(answer);
        return ResponseEntity.ok(updatedAnswer);
    }

    /**
     * 删除选项（仅限超级管理员和问卷管理员访问）
     */
    @DeleteMapping("/answers/{answerId}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('PAPER_ADMIN')")
    public ResponseEntity<Void> deleteAnswer(@PathVariable Long answerId) {
        questionService.deleteAnswer(answerId);
        return ResponseEntity.ok().build();
    }

    /**
     * 确保超级管理员和问卷管理员始终可以访问问卷
     */
    private void ensureAdminAccess(Paper paper) {
        try {
            // 获取现有的访问角色ID列表
            Set<Long> roleIds = parseAccessRoleIds(paper.getAccessRoleIds());
            // 添加超级管理员和问卷管理员的角色ID（假设为1和2）
            roleIds.add(1L); // 超级管理员
            roleIds.add(2L); // 问卷管理员
            // 更新访问角色ID列表
            paper.setAccessRoleIds(objectToJsonArray(roleIds));
        } catch (Exception e) {
            // 如果解析失败，直接设置默认值
            Set<Long> defaultRoles = new HashSet<>();
            defaultRoles.add(1L); // 超级管理员
            defaultRoles.add(2L); // 问卷管理员
            paper.setAccessRoleIds(objectToJsonArray(defaultRoles));
        }
    }

    private Set<Long> parseAccessRoleIds(String accessRoleIdsJson) {
        Set<Long> roleIds = new HashSet<>();
        if (accessRoleIdsJson == null || accessRoleIdsJson.trim().isEmpty()) {
            return roleIds;
        }
        try {
            // 尝试解析JSON格式
            String cleaned = accessRoleIdsJson.replaceAll("[\\[\\]]", "");
            String[] parts = cleaned.split(",");
            for (String part : parts) {
                if (!part.trim().isEmpty()) {
                    roleIds.add(Long.parseLong(part.trim()));
                }
            }
        } catch (Exception e) {
            // 如果解析失败，返回空集合
        }
        return roleIds;
    }

    private String objectToJsonArray(Set<Long> roleIds) {
        return "[" + String.join(",", roleIds.stream().map(String::valueOf).toArray(String[]::new)) + "]";
    }
}