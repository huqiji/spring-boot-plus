package io.geekidea.boot.system.service.impl;

import io.geekidea.boot.auth.util.LoginUtil;
import io.geekidea.boot.framework.exception.BusinessException;
import io.geekidea.boot.framework.service.impl.BaseServiceImpl;
import io.geekidea.boot.framework.util.ObjectValueUtil;
import io.geekidea.boot.system.dto.SysMenuAddDto;
import io.geekidea.boot.system.dto.SysMenuUpdateDto;
import io.geekidea.boot.system.entity.SysMenu;
import io.geekidea.boot.system.mapper.SysMenuMapper;
import io.geekidea.boot.system.query.SysMenuQuery;
import io.geekidea.boot.system.service.SysMenuService;
import io.geekidea.boot.system.vo.SysMenuInfoVo;
import io.geekidea.boot.system.vo.SysMenuTreeVo;
import io.geekidea.boot.system.vo.SysNavMenuTreeVo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统菜单 服务实现类
 *
 * @author geekidea
 * @since 2022-12-26
 */
@Service
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addSysMenu(SysMenuAddDto sysMenuAddDto) throws Exception {
        SysMenu sysMenu = new SysMenu();
        BeanUtils.copyProperties(sysMenuAddDto, sysMenu);
        return save(sysMenu);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSysMenu(SysMenuUpdateDto sysMenuUpdateDto) throws Exception {
        Long id = sysMenuUpdateDto.getId();
        SysMenu sysMenu = getById(id);
        if (sysMenu == null) {
            throw new BusinessException("系统菜单不存在");
        }
        BeanUtils.copyProperties(sysMenuUpdateDto, sysMenu);
        sysMenu.setUpdateTime(new Date());
        return updateById(sysMenu);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSysMenu(Long id) throws Exception {
        return removeById(id);
    }

    @Override
    public SysMenuInfoVo getSysMenuById(Long id) throws Exception {
        return sysMenuMapper.getSysMenuById(id);
    }

    @Override
    public List<SysMenuTreeVo> getAllSysMenuTreeList(SysMenuQuery sysMenuQuery) throws Exception {
        List<SysMenuTreeVo> list = sysMenuMapper.getSysMenuTreeList(sysMenuQuery);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        // 如果搜索条件有值，则直接返回普通列表
        boolean flag = ObjectValueUtil.isHaveValue(sysMenuQuery);
        if (flag) {
            return list;
        }
        // 递归返回树形列表
        return recursionSysMenuTreeList(0L, list);
    }

    @Override
    public List<SysMenuTreeVo> getSysMenuTreeList() throws Exception {
        SysMenuQuery sysMenuQuery = new SysMenuQuery();
        sysMenuQuery.setStatus(true);
        List<SysMenuTreeVo> list = sysMenuMapper.getSysMenuTreeList(sysMenuQuery);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        // 递归返回树形列表
        return recursionSysMenuTreeList(0L, list);
    }

    @Override
    public List<SysNavMenuTreeVo> getNavMenuTreeList() throws Exception {
        Long userId = LoginUtil.getUserId();
        // 如果是管理员，则查询所有可用菜单，否则获取当前用户所有可用的菜单
        boolean isAdmin = LoginUtil.isAdmin();
        List<SysNavMenuTreeVo> list;
        if (isAdmin) {
            list = sysMenuMapper.getNavMenuTreeAllList();
        } else {
            list = sysMenuMapper.getNavMenuTreeList(userId);
        }
        // 递归返回树形列表
        return recursionSysNavMenuTreeList(0L, list);
    }

    @Override
    public List<Long> getMenuIdsByRoleId(Long roleId) throws Exception {
        return sysMenuMapper.getMenuIdsByRoleId(roleId);
    }

    /**
     * 递归设置树形菜单
     *
     * @param parentId
     * @param list
     * @return
     */
    private List<SysMenuTreeVo> recursionSysMenuTreeList(Long parentId, List<SysMenuTreeVo> list) {
        return list.stream()
                .filter(vo -> vo.getParentId().equals(parentId))
                .map(item -> {
                    item.setChildren(recursionSysMenuTreeList(item.getId(), list));
                    return item;
                })
                .collect(Collectors.toList());
    }

    /**
     * 递归设置树形导航菜单
     *
     * @param parentId
     * @param list
     * @return
     */
    private List<SysNavMenuTreeVo> recursionSysNavMenuTreeList(Long parentId, List<SysNavMenuTreeVo> list) {
        return list.stream()
                .filter(vo -> vo.getParentId().equals(parentId))
                .map(item -> {
                    item.setChildren(recursionSysNavMenuTreeList(item.getId(), list));
                    return item;
                })
                .collect(Collectors.toList());
    }

}
