package io.geekidea.boot.system.controller;

import io.geekidea.boot.auth.annotation.Permission;
import io.geekidea.boot.framework.page.Paging;
import io.geekidea.boot.framework.response.ApiResult;
import io.geekidea.boot.system.dto.ResetSysUserPasswordDto;
import io.geekidea.boot.system.dto.SysUserAddDto;
import io.geekidea.boot.system.dto.SysUserUpdateDto;
import io.geekidea.boot.system.query.SysUserQuery;
import io.geekidea.boot.system.service.SysUserService;
import io.geekidea.boot.system.vo.SysUserInfoVo;
import io.geekidea.boot.system.vo.SysUserVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 系统用户 控制器
 *
 * @author geekidea
 * @since 2022-12-26
 */
@Slf4j
@RestController
@RequestMapping("/sysUser")
@Tag(name = "系统用户")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 添加系统用户
     *
     * @param sysUserAddDto
     * @return
     * @throws Exception
     */
    @PostMapping("/addSysUser")
    @Operation(summary = "添加系统用户")
    @Permission("sys:user:add")
    public ApiResult addSysUser(@Valid @RequestBody SysUserAddDto sysUserAddDto) throws Exception {
        boolean flag = sysUserService.addSysUser(sysUserAddDto);
        return ApiResult.result(flag);
    }

    /**
     * 修改系统用户
     *
     * @param sysUserUpdateDto
     * @return
     * @throws Exception
     */
    @PostMapping("/updateSysUser")
    @Operation(summary = "修改系统用户")
    @Permission("sys:user:update")
    public ApiResult updateSysUser(@Valid @RequestBody SysUserUpdateDto sysUserUpdateDto) throws Exception {
        boolean flag = sysUserService.updateSysUser(sysUserUpdateDto);
        return ApiResult.result(flag);
    }

    /**
     * 删除系统用户
     *
     * @param id
     * @return
     * @throws Exception
     */
    @PostMapping("/deleteSysUser/{id}")
    @Operation(summary = "删除系统用户")
    @Permission("sys:user:delete")
    public ApiResult deleteSysUser(@PathVariable Long id) throws Exception {
        boolean flag = sysUserService.deleteSysUser(id);
        return ApiResult.result(flag);
    }

    /**
     * 获取系统用户详情
     *
     * @param id
     * @return
     * @throws Exception
     */
    @PostMapping("/getSysUser/{id}")
    @Operation(summary = "系统用户详情")
    @Permission("sys:user:info")
    public ApiResult<SysUserInfoVo> getSysUser(@PathVariable Long id) throws Exception {
        SysUserInfoVo sysUserInfoVo = sysUserService.getSysUserById(id);
        return ApiResult.success(sysUserInfoVo);
    }

    /**
     * 系统用户分页列表
     *
     * @param sysUserQuery
     * @return
     * @throws Exception
     */
    @PostMapping("/getSysUserList")
    @Operation(summary = "系统用户分页列表")
    @Permission("sys:user:list")
    public ApiResult<SysUserVo> getSysUserList(@Valid @RequestBody SysUserQuery sysUserQuery) throws Exception {
        Paging<SysUserVo> paging = sysUserService.getSysUserList(sysUserQuery);
        return ApiResult.success(paging);
    }

    /**
     * 重置系统用户密码
     *
     * @param resetSysUserPasswordDto
     * @return
     * @throws Exception
     */
    @PostMapping("/resetSysUserPassword")
    @Operation(summary = "重置系统用户密码")
    @Permission("sys:user:reset-password")
    public ApiResult resetSysUserPassword(@Valid @RequestBody ResetSysUserPasswordDto resetSysUserPasswordDto) throws Exception {
        boolean flag = sysUserService.resetSysUserPassword(resetSysUserPasswordDto);
        return ApiResult.result(flag);
    }

}
