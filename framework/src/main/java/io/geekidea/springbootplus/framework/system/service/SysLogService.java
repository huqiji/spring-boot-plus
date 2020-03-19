/*
 * Copyright 2019-2029 geekidea(https://github.com/geekidea)
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.geekidea.springbootplus.framework.system.service;

import io.geekidea.springbootplus.framework.common.service.BaseService;
import io.geekidea.springbootplus.framework.core.pagination.Paging;
import io.geekidea.springbootplus.framework.system.entity.SysLog;
import io.geekidea.springbootplus.framework.system.param.SysLogPageParam;
import io.geekidea.springbootplus.framework.system.vo.SysLogQueryVo;

import java.io.Serializable;

/**
 * <p>
 * 系统日志 服务类
 * </p>
 *
 * @author geekidea
 * @since 2019-10-11
 */
public interface SysLogService extends BaseService<SysLog> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     * @throws Exception
     */
    SysLogQueryVo getSysLogById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param sysLogPageParam
     * @return
     * @throws Exception
     */
    Paging<SysLogQueryVo> getSysLogPageList(SysLogPageParam sysLogPageParam) throws Exception;

}