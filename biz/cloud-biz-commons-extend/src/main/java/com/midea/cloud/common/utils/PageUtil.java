package com.midea.cloud.common.utils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 分页参数处理工具
 * 
 * @author artifact
 *
 */
@Slf4j
public class PageUtil {

	/**
	 * 分页参数，起始位置，从0开始
	 */
	public static final String START = "start";
	/**
	 * 分页参数，每页数据条数
	 */
	public static final String LENGTH = "length";

	/**
	 * 转换并校验分页参数<br>
	 * mybatis中limit #{start, JdbcType=INTEGER}, #{length,
	 * JdbcType=INTEGER}里的类型转换貌似失效<br>
	 * 我们这里先把他转成Integer的类型
	 * 
	 * @param params
	 * @param required 分页参数是否是必填
	 */
	public static void pageParamConver(Map<String, Object> params, boolean required) {
		if (required) {// 分页参数必填时，校验参数
			if (params == null || !params.containsKey(START) || !params.containsKey(LENGTH)) {
				throw new IllegalArgumentException("请检查分页参数," + START + "," + LENGTH);
			}
		}

		if (!CollectionUtils.isEmpty(params)) {
			if (params.containsKey(START)) {
				Integer start = MapUtils.getInteger(params, START);
				if (start < 0) {
					log.error("start：{}，重置为0", start);
					start = 0;
				}
				params.put(START, start);
			}

			if (params.containsKey(LENGTH)) {
				Integer length = MapUtils.getInteger(params, LENGTH);
				if (length < 0) {
					log.error("length：{}，重置为0", length);
					length = 0;
				}
				params.put(LENGTH, length);
			}
		}
	}

	/**
	 * 默认分页查询第一页10行数据
	 * 
	 * @param pageNum
	 * @param pageSize
	 */
	public static void startPage(Integer pageNum, Integer pageSize) {
		pageNum = pageNum == null ? 1 : pageNum;
		pageSize = pageSize == null ? 10 : pageSize;
		PageHelper.startPage(pageNum, pageSize);
	}

	/**
	 * 分页列表转换后设置原始分页信息
	 * 
	 * @param before
	 * @param <T>
	 * @return
	 */
	public static <S, T> PageInfo<T> buildPageInfoAfertConvert(Collection<S> before, List<T> after) {

		if (CollectionUtils.isEmpty(before) || !(before instanceof Page)) {
			return new PageInfo<>(new ArrayList<>());
		}

		Page originalPage = (Page) before;
		PageInfo<T> nowPage = new PageInfo<T>(originalPage);
		nowPage.setList(after);
		return nowPage;
	}

	/**
	 * 根据全量数据，手工分页
	 * 
	 * @param <T>
	 * @param pageNum
	 * @param pageSize
	 * @param fullDataList
	 * @return
	 */
	public static <T> PageInfo<T> pagingByFullData(Integer pageNum, Integer pageSize, List<T> fullDataList) {
		Page<T> page = new Page<T>(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
		if (fullDataList == null) {
			return new PageInfo<T>(page);
		}
		page.setTotal(fullDataList.size());
		for (int i = ((page.getPageNum() - 1) * page.getPageSize()); i < page.getPageNum() * page.getPageSize(); i++) {
			if (i >= fullDataList.size()) {
				break;
			}
			page.add(fullDataList.get(i));
		}
		return new PageInfo<T>(page);

	}
}
