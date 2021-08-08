<template>
  <el-container class="${packageName}Edit" direction="vertical">
    <el-main>
        <div class="form-container">
    <el-form :model="form" :rules="rules" ref="form">
    <#if partHeadMap??>
        <el-row :gutter="27">
      <#list partHeadMap?keys as key>
      <el-collapse v-model="activeDims" class="tab-form-style">
        <el-collapse-item
          title="${key}"
          name="${key_index}"
        >
          <#list partHeadMap["${key}"] as field>
            <#if field.isShowOnForm == '1'>
            <el-col :span="6">
              <el-form-item prop="${field.javaCode}" label="${field.name}">
               <#if (field.dictItemCode)?? && (field.dictItemCode != '')>
                  <RenderSelect code="${field.dictItemCode}" v-model="form.${field.javaCode}" />
                <#elseif ((field.componentType)! == 'date')>
                  <el-date-picker v-model="form.${field.javaCode}" type="date" value-format="yyyy-MM-dd" placeholder="选择日期" />
                <#elseif ((field.componentType)! == 'download')>
                  <c-download-link
                    :id="form.${field.javaCode}"
                    :name="form.${field.javaCode}"
                    ellipsis
                  />
                <#elseif ((field.componentType)! == 'downloadAndUpload')>
                  <c-upload-file
                    v-if="!form.${field.javaCode}"
                    :limit="1"
                    showProgress
                    :show-file-list="false"
                    :cus-data="fileInfo"
                    @upload-success="file => handleUploadSuccess(file, form, '${field.javaCode}')"
                  />
                  <div v-else class="download-link-wrap">
                    <c-download-link
                      :id="form.${field.javaCode}"
                      :name="form.${field.javaCode}"
                      ellipsis
                      class="download-link-item"
                    />
                    <i
                      class="el-icon-close close-icon"
                      @click="handleAttachmentRemove(form, '${field.javaCode}')"
                    />
                  </div>
                <#else>
                  <el-input v-model="form.${field.javaCode}" />
                </#if>
              </el-form-item>
            </el-col>
            </#if>
          </#list>
        </el-collapse-item>
      </el-collapse>
      </#list>
    </el-row>
<#else>
    <el-row :gutter="27">
      <#list selectHeadFileList as field>
        <#if field.isShowOnForm == '1'>
          <el-col :span="6">
        <el-form-item prop="${field.javaCode}" label="${field.name}">
           <#if (field.dictItemCode)?? && (field.dictItemCode != '')>
              <RenderSelect code="${field.dictItemCode}" v-model="form.${field.javaCode}" />
            <#elseif ((field.componentType)! == 'date')>
              <el-date-picker v-model="form.${field.javaCode}" type="date" value-format="yyyy-MM-dd" placeholder="选择日期" />
            <#elseif ((field.componentType)! == 'download')>
              <c-download-link
                :id="form.${field.javaCode}"
                :name="form.${field.javaCode}"
                ellipsis
              />
            <#elseif ((field.componentType)! == 'downloadAndUpload')>
              <c-upload-file
                v-if="!form.${field.javaCode}"
                :limit="1"
                showProgress
                :show-file-list="false"
                :cus-data="fileInfo"
                @upload-success="file => handleUploadSuccess(file, form, '${field.javaCode}')"
              />
              <div v-else class="download-link-wrap">
                <c-download-link
                  :id="form.${field.javaCode}"
                  :name="form.${field.javaCode}"
                  ellipsis
                  class="download-link-item"
                />
                <i
                  class="el-icon-close close-icon"
                  @click="handleAttachmentRemove(form, '${field.javaCode}')"
                />
              </div>
            <#else>
              <el-input v-model="form.${field.javaCode}" />
            </#if>
        </el-form-item>
         </el-col>
        </#if>
      </#list>
      </el-row>
    </#if>
    </el-form>
        </div>
        <el-collapse v-model="activeLine">
        <el-collapse-item title="明细" name="1">
          <el-container class="flex-container" style="height: 300px;">
            <el-main>
              <div style="padding: 12px 0;">
                <el-button class="detail-pbtn" type="primary" @click="addLine"
                  >新增</el-button
                >
                <el-button
                  class="detail-pbtn"
                  type="primary"
                  @click="exportExcel"
                  >导出</el-button
                >
                <m-import
                  btnClassName="detail-pbtn"
                  style="display: inline-block;margin: 0 10px;"
                  title="导入"
                  @downloadTemplate="downloadTemplate"
                  @handleSuccess="handleSuccess"
                  upLoadUrl="/cloud-srm/${codeVo.applicationName}/${modelName}/${packageName}/import${lineClassFileName}Excel"
                  :extraData="extraData"
                />
              </div>
              <base-table
                :columns="columns"
                :dataSource="dataSource"
                :initialize="false"
                rowKey="quotaLineId"
                @asyncGetRealDataSource="asyncGetRealDataSource"
                ref="table"
                border
              >
                <#list selectLineFileList as field>
          <#if field.isShowOnForm == '1'>
            <#if (field.dictItemCode)?? && (field.dictItemCode != '')>
          <template #${field.javaCode}="{ scope }">
              <RenderSelect code="${field.dictItemCode}" v-model="scope.row.${field.javaCode}" />
          </template>
            <#elseif (((field.componentType)!)! == 'date')>
              <template #${field.javaCode}="{ scope }">
                <el-date-picker v-model="scope.row.${field.javaCode}" type="date" value-format="yyyy-MM-dd" placeholder="选择日期" />
              </template>
            <#elseif (((field.componentType)!)! == 'download')>
              <template #${field.javaCode}="{ scope }">
                <c-download-link
                  :id="scope.row.${field.javaCode}"
                  :name="scope.row.${field.javaCode}"
                  ellipsis
                />
              </template>
            <#elseif (((field.componentType)!)! == 'downloadAndUpload')>
              <template #${field.javaCode}="{ scope }">
                <c-upload-file
                  v-if="!scope.row.${field.javaCode}"
                  :limit="1"
                  showProgress
                  :show-file-list="false"
                  :cus-data="fileInfo"
                  @upload-success="file => handleUploadSuccess(file, scope.row, '${field.javaCode}')"
                />
                <div v-else class="download-link-wrap">
                  <c-download-link
                    :id="scope.row.${field.javaCode}"
                    :name="scope.row.${field.javaCode}"
                    ellipsis
                    class="download-link-item"
                  />
                  <i
                    class="el-icon-close close-icon"
                    @click="handleAttachmentRemove(scope.row, '${field.javaCode}')"
                  />
                </div>
              </template>
            <#else>
              <template #${field.javaCode}="{ scope }">
                <el-input v-model="scope.row.${field.javaCode}" />
              </template>
            </#if>
          </#if>
        </#list>
              </base-table>
            </el-main>
          </el-container>
        </el-collapse-item>
      </el-collapse>
      <c-toolbar>
        <template #right>
            <el-button size="mini" @click="cancelBill">取消</el-button>
          <el-button
            type="primary"
            :disabled="readOnly"
            size="mini"
            @click="save"
            >确认</el-button
          >
        </template>
      </c-toolbar>
    </el-main>
  </el-container>
</template>
<script>
import { tabTodoMixin } from "@/utils/mixins";
import MainHeader from "lib@/components/Table/MainHeader";
import CToolbar from "lib@/components/c-toolbar";
import createDictionary from "lib@/utils/ponyStore";
import CUploadFile from "@/library/components/c-upload-file";
import CDownloadLink from "lib@/components/c-download-link";
import BaseTable from "lib@/components/BaseTable/baseTable";
import MImport from "lib@/components/import";
import { downloadFileLink } from "lib@/utils/file";
import axios from "axios";
import { getToken } from "@/utils/auth";

const { store, mutation, getLabel, renderSelect } = createDictionary({
<#list selectHeadFileList as field>
  <#if (field.dictItemCode)?? && (field.dictItemCode != '')>
  ${field.dictItemCode}: [],
  </#if>
</#list>
});
const RenderSelect = renderSelect();

export default {
  name: "${packageName}Edit",
  components: {
    MainHeader,
    CToolbar,
    BaseTable,
    MImport,
    CDownloadLink,
    CUploadFile,
    RenderSelect,
  },
  mixins: [tabTodoMixin],
  data() {
    return {
        // 文件上传配置信息
        fileInfo: {
            uploadType: "FASTDFS", // 固定参数
            sourceType: "WEB_APP", // 固定参数
            fileModular: "workFlow", // 文件所属模块 -》审批流程
            fileFunction: "workflowReport", // 审批流相关文件
            fileType: "images" // 文件所属类型
        },
        realDataSource: [],
      dataSource: [],
      activeLine: ['1'],
        columns: [
            <#list selectLineFileList as field>
            <#if field.isShowOnForm == '1'>
            {
                attrs: {
                    prop: '${field.javaCode}',
                    label: '${field.name}',
                    <#if field.dictItemCode??>
                        formatter: value => getLabel('${field.dictItemCode}', value)
                    </#if>
                },
                slot: '${field.javaCode}',
                <#if (field.isRequired == '1')>
                rules: { required: true, message: '必填' }
                </#if>
            },
            </#if>
            </#list>
            {
                attrs: {
                    prop: "operation",
                    label: "操作",
                    width: 150,
                    fixed: "right"
                },
                operations: [
                    {
                        event: "deleteItem",
                        name: this.$t("common.delete"),
                        func: this.deleteItem
                    }
                ]
            }
        ],
      extraData: {
        sourceType: "WEB_APP",
        uploadType: "FASTDFS",
        fileModular: "base",
        fileFunction: "quotalinetest",
        fileType: "excel"
      },
      form: {
        <#list selectHeadFileList as field>
          <#if field.isShowOnForm == '1'>
        ${field.javaCode}: null,
          </#if>
        </#list>
      },
      rules: {
      <#list selectHeadFileList as field>
        <#if (field.isRequired == "1")>
        ${field.javaCode}: [{ required: true, message: "必填" }],
        </#if>
      </#list>
      },
     <#if partHeadMap??>
      activeDims: [
      <#list partHeadMap?keys as key>
        "${key_index}",   //定义分区序号 $index
      </#list>
      ],
      </#if>
      readOnly: false,
    };
  },
  created() {
    mutation.loadDictionary([
  <#list selectHeadFileList as field>
    <#if field.isShowOnForm == '1'>
    <#if (field.dictItemCode)?? && (field.dictItemCode != '')>
    "${field.dictItemCode}",
    </#if>
    </#if> 
  </#list>
    ]);
  },
  mounted() {
    const { flag, row, readOnly = false } = this.$attrs.params;
    this.readOnly = readOnly;
    if (flag === 'edit') {
      this.getDetail();
    }
  },
  watch: {
  },
  computed: {
  },
  methods: {
    getDetail() {
       this.$api.generate.${packageName}.getById(this.$attrs.params.row.quotaHeadId).then(res => {
        const { ${lineTargetName}List, ...rest } = res.data;
        this.form = rest;
        this.dataSource = ${lineTargetName}List;
      })
    },
    downloadTemplate() {
      downloadFileLink(
        "/cloud-srm/${codeVo.applicationName}/${modelName}/${packageName}/export${lineClassFileName}ExcelTemplate",
        "导入模板.xlsx"
      ).catch(() => {
        this.$message.error("下载失败");
      });
    },
    handleSuccess() {
      this.getDetail()
    },
    exportExcel() {
      axios({
        method: "POST",
        url: '/cloud-srm/${codeVo.applicationName}/${modelName}/${packageName}/export${lineClassFileName}Excel',
        timeout: this.timeout,
        headers: {
          Authorization: "Bearer " + getToken()
        },
        data: { id: this.$attrs.params.row.quotaHeadId },
        responseType: "arraybuffer"
      })
        .then(response => {
          console.log(response);
          const { data } = response;
          if (response.headers["content-type"].startsWith("application/json")) {
            let enc = new TextDecoder("utf-8");
            let res = JSON.parse(enc.decode(new Uint8Array(data))); //转化成json对象
            throw new Error(res.message);
          }
          const blob = new Blob([data]);
          const disposition = response.headers["content-disposition"] || "";
          const filename = decodeURIComponent(disposition.split("=")[1]);
          const url = window.URL.createObjectURL(blob); // URL.createObjectURL(object)表示生成一个File对象或Blob对象
          let dom = document.createElement("a"); // 设置一个隐藏的a标签，href为输出流，设置download
          dom.style.display = "none";
          dom.href = url;
          dom.setAttribute("download", filename || `${r'${this.fileName}'}.xlsx`); // 指示浏览器下载url,而不是导航到它；因此将提示用户将其保存为本地文件
          document.body.appendChild(dom);
          dom.click();
        })
        .catch(error => {
          console.log(error);
          this.$message({ type: "error", message: error.message });
        });
    },
    save() {
      this.$refs.form.validate(result => {
          this.$refs.table.validate(res => {
              if (result && res) {
                  const { flag } = this.$attrs.params;
                  const data = {
                      ...this.form,
                      ${lineTargetName}List: this.realDataSource
                  }
                  this.$api.generate.${packageName}.addOrUpdate(data).then(res => {
                      this.$message({
                          type: "success",
                          message: res.message
                      });
                      this.cancelBill();
                  });
              } else {
                  this.__focus_error__();
              }
          })
      });
    },
    asyncGetRealDataSource(data) {
      this.realDataSource = data;
    },
    addLine() {
      this.$refs.table.add({});
    },
    deleteItem(scope, data) {
      data.splice(scope.$index, 1)
    },
    cancelBill() {
      const { flag, row } = this.$attrs.params;
      if (flag === "add") {
        this.$emit("tab-remove", "${packageName}Edit");
      } else {
        this.$emit("tab-remove", "${packageName}Edit" + row.${pk});
      }
      this.__setTabTodo("${packageName}List.getQuerydata");
    },
    // 上传附件成功
    handleUploadSuccess(file, row, key) {
      const { id, name } = file;
      row[key] = id.toString();
    },
    // 删除文件
    handleAttachmentRemove(row, key) {
      row[key] = "";
    },
  }
};
</script>
<style scoped lang="scss">
.${packageName}Edit {
  height: 100%;
  padding-bottom: 50px;
  /deep/ .table-wrapper {
      padding-left: 0;
      padding-right: 0;
  }
  .sub_header {
    padding: 4px 11px;
    background: #eee;
  }
  .el-table .el-date-editor {
    width: 135px;
  }
  .base-form {
    padding: 15px 30px 0;
  }
  .toRequired {
    color: #ff4949;
    padding-right: 2px;
  }
  .edit_cond {
    color: #23adf4;
    cursor: pointer;
  }
}
</style>
