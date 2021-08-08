package com.midea.cloud.common.utils;

import com.midea.cloud.srm.model.base.organization.dto.OrgDto;
import com.midea.cloud.srm.model.base.organization.entity.Organization;
import com.midea.cloud.srm.model.base.organization.entity.OrganizationRelation;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/7/10
 *  修改内容:
 * </pre>
 */
public class OrgUtils {

    /**
     * 检查全路径
     * @param errorMsg  错误信息收集
     * @param orgName   全路径名称
     * @param organizationMap   组织集合(组织名-组织对象)
     * @return
     */
    public static OrgDto checkOrgFullPath(StringBuffer errorMsg, String orgName,
                                          Map<String,Organization> organizationMap){
        OrgDto org = null;
        if(StringUtil.notEmpty(orgName)){
            orgName = orgName.trim();
            if(StringUtil.checkStringNoSlash(orgName)){
                errorMsg.append("组织全路径分隔符只能用:/;");
            }else if (orgName.contains("/")){
                StringBuffer error = new StringBuffer();
                // 分组
                List<String> orgNames = Arrays.asList(orgName.split("/"));
                // 检查组织是否存在
                ArrayList<Organization> organizations = new ArrayList<>();
                orgNames.forEach(orgNameTemp->{
                    if (StringUtil.notEmpty(orgNameTemp)) {
                        Organization organization = organizationMap.get(orgNameTemp.trim());
                        if(null != organization && organization.getOrganizationId() > 1){
                            organizations.add(organization);
                        }else {
                            error.append(orgNameTemp+"不存在;");
                        }
                    }else {
                        error.append("全路径分隔后存在空字符串;");
                    }
                });

                // 检查组织层级依赖关系
                if(CollectionUtils.isNotEmpty(organizations) && organizations.size() > 1){
                    int size = organizations.size();
                    for(int i = 1;i < size;i ++){
                        boolean flag = organizations.get(i).getParentOrganizationIds().contains(organizations.get(i - 1).getOrganizationId().toString());
                        if(!flag){
                            error.append(organizations.get(i).getOrganizationName()+"与"+organizations.get(i-1).getOrganizationName()+
                                    "不是父子关系;");
                        }
                    }
                }
                if(error.length()>1){
                    errorMsg.append(error);
                }else {
                    if (CollectionUtils.isNotEmpty(organizations)) {
                        int size = organizations.size();
                        if(size == 1){
                            Organization organization = organizations.get(0);
                            Long organizationId = organization.getOrganizationId();
                            // 检查是否根节点
                            String parentOrganizationIds = organization.getParentOrganizationIds();
                            if(StringUtil.notEmpty(parentOrganizationIds) || !"-1".equals(parentOrganizationIds)){
                                errorMsg.append("该组织不是根节点,请填写全路径;");
                            }else {
                                String md5 = EncryptUtil.getMD5("-1" + organizationId);
                                org = new OrgDto();
                                org.setOrgId(organizationId);
                                org.setOrgCode(organization.getOrganizationCode());
                                org.setOrgName(organization.getOrganizationName());
                                org.setFullPathId(md5);
                            }
                        }else {
                            Organization organization = organizations.get(0);
                            Long organizationId = organization.getOrganizationId();
                            String md5 = EncryptUtil.getMD5("-1" + organizationId);
                            for(int i= 1;i< size;i++){
                                Long organizationId1 = organizations.get(i).getOrganizationId();
                                md5 = EncryptUtil.getMD5(md5 + organizationId1);
                            }
                            Organization organization1 = organizations.get(size - 1);
                            org = new OrgDto();
                            org.setOrgId(organization1.getOrganizationId());
                            org.setOrgCode(organization1.getOrganizationCode());
                            org.setOrgName(organization1.getOrganizationName());
                            org.setFullPathId(md5);
                        }
                    }
                }
            }else {
                // 检查组织是否存在
                Organization organization = organizationMap.get(orgName.trim());
                if(null != organization && organization.getOrganizationId() > 1){
                    Long organizationId = organization.getOrganizationId();
                    // 检查是否根节点
                    String parentOrganizationIds = organization.getParentOrganizationIds();
                    if(StringUtil.notEmpty(parentOrganizationIds) || !"-1".equals(parentOrganizationIds)){
                        errorMsg.append("该组织不是根节点,请填写全路径;");
                    }else {
                        String md5 = EncryptUtil.getMD5("-1" + organizationId);
                        org = new OrgDto();
                        org.setOrgId(organization.getOrganizationId());
                        org.setOrgCode(organization.getOrganizationCode());
                        org.setOrgName(organization.getOrganizationName());
                        org.setFullPathId(md5);
                    }
                }else {
                    errorMsg.append("该组织不存在;");
                }
            }
        }
        return org;
    }

    /**
     * 检查组织
     * @param organizations   事业部/业务实体/库存组织
     * @return
     */
    public static OrgDto getOrgFullPathNew(List<Organization> organizations) {
        OrgDto org = null;
        if(CollectionUtils.isNotEmpty(organizations)){
            Long organizationId = organizations.get(0).getOrganizationId();
            String md5 = EncryptUtil.getMD5("-1" + organizationId);
            if (organizations.size() > 1) {
                for(int i = 1;i < organizations.size();i++){
                    Organization organization = organizations.get(i);
                    Long organizationId1 = organization.getOrganizationId();
                    md5 = EncryptUtil.getMD5(md5 + organizationId1);
                }
            }
            org = new OrgDto();
            org.setFullPathId(md5);
        }
        return org;
    }

    public static void main(String[] args) {

        List<String> strings = Arrays.asList("7546243049783296", "7715042154184704", "7711154585272320");
        String md5 = EncryptUtil.getMD5("-1" + "7546243049783296");
        for(int i = 1;i < 3;i++){
            md5 = EncryptUtil.getMD5(md5 + strings.get(i));
        }
        System.out.println(md5);
    }
    
    public static List<String> findParentStart(List<OrganizationRelation> treeNewList,String targetParentFullPathId,Map<String, Organization> permissionNodeResultMap) {
    	List<String> fullPathIds = new ArrayList<String>();
        //找出头节点
        fullPathIds.add(targetParentFullPathId);
        OrganizationRelation root = findRoot(targetParentFullPathId, treeNewList);
        if (null != root) {
        	findChild(fullPathIds, permissionNodeResultMap, root.getChildOrganRelation());
        }
        return fullPathIds;
    }
    
    /**
     * 递归查找根节点
     * @param fullPathId
     * @param relation
     * @param trees
     */
    private static OrganizationRelation findRoot(String fullPathId,List<OrganizationRelation> trees) {
    	OrganizationRelation result = null;
    	for (OrganizationRelation r : trees) {
    		if (r.getFullPathId().equals(fullPathId)) {
    			result = r;
    		}
    		if (null == result) {
    			if (r.getChildOrganRelation().size() > 0) {
    				result = findRoot(fullPathId, r.getChildOrganRelation());
        		}
    		} else {
    			break;
    		}
    	}
    	return result;
    }
    
    /**
     * 递归查找当前节点
     * @param organizationRelationList
     * @param fullPathId
     * @param permissionNodeResultMap
     * @param treeNewList
     * @return
     */
    public static void findChild(List<String> fullPathIds,Map<String, Organization> permissionNodeResultMap,List<OrganizationRelation> treeNewList) {
    	for (OrganizationRelation o : treeNewList) {
    		if (permissionNodeResultMap.containsKey(o.getFullPathId())) {
    			fullPathIds.add(o.getFullPathId());
    		}
    		if (o.getChildOrganRelation().size() > 0) {
    			findChild(fullPathIds, permissionNodeResultMap, o.getChildOrganRelation());
    		}
    	}
    }

}
