package com.midea.cloud.srm.model.logistics.soap.tms.request;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 *  <pre>
 *  tms服务项目接口表实体类
 * </pre>
 *
 * @author xiexh12@meiCloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-11-26 15:38:43
 *  修改内容:
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "projectsRequest")
@XmlType(name="", propOrder = {
        "esbInfo","requestInfo"
})
public class TmsProjectRequest {

    @XmlElement(required = true)
    protected RequestEsbInfo esbInfo;

    @XmlElement(required = true)
    private RequestInfo requestInfo;

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = {
            "projects"
    })

    public static class RequestInfo{

        @XmlElement
        private Projects projects;

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(propOrder = {"project"})
        public static class Projects{
            @XmlElement
            private List<TmsProjectEntity> project;

            public List<TmsProjectEntity> getProject() {
                return project;
            }
            public void setProject(List<TmsProjectEntity> project) {
                this.project = project;
            }
        }

        public Projects getProjects() {
            return projects;
        }
        public void setProjects(Projects projects) {
            this.projects = projects;
        }
    }

    public RequestEsbInfo getEsbInfo() {
        return esbInfo;
    }

    public void setEsbInfo(RequestEsbInfo esbInfo) {
        this.esbInfo = esbInfo;
    }

    public RequestInfo getRequestInfo() {
        return requestInfo;
    }

    public void setRequestInfo(RequestInfo requestInfo) {
        this.requestInfo = requestInfo;
    }
}
