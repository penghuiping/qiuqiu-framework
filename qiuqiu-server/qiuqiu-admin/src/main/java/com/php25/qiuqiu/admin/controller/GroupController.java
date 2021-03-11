package com.php25.qiuqiu.admin.controller;

import com.google.common.collect.Lists;
import com.php25.common.core.tree.TreeNode;
import com.php25.common.core.tree.Trees;
import com.php25.common.flux.web.APIVersion;
import com.php25.common.flux.web.JSONController;
import com.php25.common.flux.web.JSONResponse;
import com.php25.qiuqiu.admin.vo.in.group.GroupCreateVo;
import com.php25.qiuqiu.admin.vo.in.group.GroupDeleteVo;
import com.php25.qiuqiu.admin.vo.out.TreeVo;
import com.php25.qiuqiu.monitor.aop.AuditLog;
import com.php25.qiuqiu.user.dto.group.GroupCreateDto;
import com.php25.qiuqiu.user.dto.group.GroupDto;
import com.php25.qiuqiu.user.service.GroupService;
import io.github.yedaxia.apidocs.ApiDoc;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author penghuiping
 * @date 2021/3/6 21:36
 */
@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
public class GroupController extends JSONController {

    private final GroupService groupService;

    /**
     * 获取系统中所有组列表
     */
    @ApiDoc(result = TreeVo.class, url = "/qiuqiu_admin/v1/group/getAll")
    @APIVersion("v1")
    @PostMapping("/getAll")
    public JSONResponse getAll() {
        TreeNode<GroupDto> res = groupService.getAllGroupTree();
        TreeVo root = new TreeVo();
        buildTree(root, res);
        return succeed(root);
    }

    /**
     * 创建用户组
     *
     * @param groupCreateVo
     */
    @AuditLog
    @ApiDoc(stringResult = "true:创建成功", url = "/qiuqiu_admin/v1/group/create")
    @APIVersion("v1")
    @PostMapping("/create")
    public JSONResponse create(@Valid @RequestBody GroupCreateVo groupCreateVo) {
        GroupCreateDto groupCreateDto = new GroupCreateDto();
        BeanUtils.copyProperties(groupCreateVo, groupCreateDto);
        return succeed(groupService.create(groupCreateDto));
    }

    /**
     * 删除用户组
     *
     * @param groupDeleteVo
     */
    @AuditLog
    @ApiDoc(stringResult = "true:创建成功", url = "/qiuqiu_admin/v1/group/delete")
    @APIVersion("v1")
    @PostMapping("/delete")
    public JSONResponse delete(@Valid @RequestBody GroupDeleteVo groupDeleteVo) {
        return succeed(groupService.delete(groupDeleteVo.getGroupId()));
    }

    private void buildTree(TreeVo node, TreeNode<GroupDto> node0) {
        node.setId(node0.getData().getId() + "");
        node.setValue(node0.getData().getId() + "");
        node.setLabel(node0.getData().getDescription());
        node.setDisabled(false);
        node.setChildren(Lists.newArrayList());
        if (!Trees.isLeafNode(node0)) {
            List<TreeNode<GroupDto>> children = node0.getChildren();
            for (TreeNode<GroupDto> child : children) {
                TreeVo treeVo = new TreeVo();
                buildTree(treeVo, child);
                node.getChildren().add(treeVo);
            }
        } else {
            node.setChildren(null);
        }
    }


}
