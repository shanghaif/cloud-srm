<template>
  <el-container
    class="flex-container ${packageName}_list_wrapper"
    direction="vertical"
  >
    <el-main>
      <form-wrapper
        :formArray="filterConfig"
        @getFormData="getQuerydata"
        <#if (codeVo.isExport == 1)>
        @synchronous-value="syncFilterParams"
        </#if>
      >
      <#list selectHeadFileList as field>
          <#if field.isQueryCondition == '1'>
           <#if (field.dictItemCode)?? && (field.dictItemCode != '')>
            <template #${field.javaCode}="{ scope }">
              <RenderSelect code="${field.dictItemCode}" v-model="scope.${field.javaCode}" />
            </template>
           </#if>
          </#if>
      </#list>
      </form-wrapper>
      <main-header :lSpan="22" :rSpan="2">
        <template slot="left">
          <#if codeVo.isAdd == 1>
          <AuthorityButton type="primary" @click="addHandle">{{ $t('common.add') }}</AuthorityButton>
          </#if>
          <#if codeVo.isImport == 1>
          <m-import
            style="display: inline-block;margin: 0 10px;"
            title="导入"
            @downloadTemplate="downloadTemplate"
            @handleSuccess="handleSuccess"
            upLoadUrl="/cloud-srm/${codeVo.applicationName}/${modelName}/${packageName}/importExcel"
            :extraData="extraData"
          />
          </#if>
          <#if codeVo.isExport == 1>
          <ExportExcel
            pageUrl="/cloud-srm/${codeVo.applicationName}/${modelName}/${packageName}/listPage"
            :filterParams="filterParams"
            :tableHeader="tableHeader"
            :dictCodes="dictCodes"
            timeout="1000000"
            exportMode="front"
          />
          </#if>
        </template>
      </main-header>
      <table-view
        :ref="gridId"
        :table-header="tableHeader"
        :checkChange="handleCurrentChange"
        :page-size="pageSize"
        :checkbox="true"
        :preQueryData="queryParam"
        :openCustomTable="true"
        :source="$api.generate.${packageName}.list"
      />
    </el-main>
<#if codeVo.addOrEditorMode == 'pop'>
    <srm-dialog :title="title" size="large" :visible.sync="visible">
              <el-container class="${packageName}Edit" direction="vertical">
            <el-main>
                <el-form :model="form" :rules="rules" ref="form">
                    <#if partHeadMap??>
                        <el-row>
                            <#list partHeadMap?keys as key>
                                <el-collapse v-model="activeDims" class="tab-form-style">
                                    <el-collapse-item
                                            title="${key}"
                                            name="${key_index}"
                                    >
                                        <#list partHeadMap["${key}"] as field>
                                            <#if field.isShowOnForm == 1>
                                            <el-col :span="8">
                                                <el-form-item prop="${field.javaCode}" label="${field.name}">
                                                    <#if (field.dictItemCode)?? && (field.dictItemCode != '')>
                                                      <RenderSelect code="${field.dictItemCode}" v-model="form.${field.javaCode}" />
                                                    <#elseif (field.componentType == 'date')>
                                                      <el-date-picker v-model="scope.row.${field.javaCode}" type="date" value-format="yyyy-MM-dd" placeholder="选择日期" />
                                                    <#elseif ((field.componentType)! == 'download')>
                                                      <c-download-link
                                                        :id="scope.row.${field.javaCode}"
                                                        :name="scope.row.${field.javaCode}"
                                                        ellipsis
                                                      />
                                                    <#elseif ((field.componentType)! == 'downloadAndUpload')>
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
                                                    <#else>
                                                      <el-input v-model="scope.row.${field.javaCode}" />
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
                        <el-row>
                            <#list selectHeadFileList as field>
                                <#if field.isShowOnForm == 1>
                                <el-col :span="8">
                                    <el-form-item prop="${field.javaCode}" label="${field.name}">
                                        <#if (field.dictItemCode)?? && (field.dictItemCode != '')>
                                          <RenderSelect code="${field.dictItemCode}" v-model="form.${field.javaCode}" />
                                        <#elseif (field.componentType == 'date')>
                                          <el-date-picker v-model="scope.row.${field.javaCode}" type="date" value-format="yyyy-MM-dd" placeholder="选择日期" />
                                        <#elseif ((field.componentType)! == 'download')>
                                          <c-download-link
                                            :id="scope.row.${field.javaCode}"
                                            :name="scope.row.${field.javaCode}"
                                            ellipsis
                                          />
                                        <#elseif ((field.componentType)! == 'downloadAndUpload')>
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
                                        <#else>
                                          <el-input v-model="scope.row.${field.javaCode}" />
                                        </#if>
                                    </el-form-item>
                                </el-col>
                                </#if>
                            </#list>
                        </el-row>
                    </#if>
                </el-form>

              <c-toolbar>
                <template #center>
                  <el-button
                    type="primary"
                    :disabled="readOnly"
                    size="mini"
                    @click="save"
                    >{{ $t('common.confirm') }}</el-button
                  >
                  <el-button size="mini" @click="cancelBill">{{ $t('common.cancel') }}</el-button>
                </template>
              </c-toolbar>
            </el-main>
          </el-container>
            <div #footer class="dialog-footer">
                <el-button
                  type="primary"
                  @click="confirm"
                >
                  {{ $t('common.confirm') }}
                </el-button>
                <el-button @click="cancel">
                  {{ $t('common.cancel') }}
                </el-button>
              </div>
    </srm-dialog>
</#if>
  </el-container>
</template>
<script>
import { tabTodoWatch, tabTodoMixin } from "@/utils/mixins";
import TableView from "lib@/components/Table/TableView";
import MainHeader from "lib@/components/Table/MainHeader";
import FormWrapper from "lib@/components/Table/FormWrapper";
import ${packageName}Edit from "./edit.vue";
import createDictionary from "lib@/utils/ponyStore";
import CUploadFile from "@/library/components/c-upload-file";
import CDownloadLink from "lib@/components/c-download-link";
<#if codeVo.isImport == 1>
import MImport from "lib@/components/import";
import { downloadFileLink } from "lib@/utils/file";
</#if>
<#if codeVo.isExport == 1>
import ExportExcel from "lib@/components/export-excel";
</#if>
const { store, mutation, getLabel, renderSelect } = createDictionary({

<#list selectHeadFileList as field>
    <#if (field.dictItemCode)?? && (field.dictItemCode != '')>
    "${field.dictItemCode}": [],
    </#if>
</#list>
});
const RenderSelect = renderSelect();

export default {
  name: "${packageName}List",
  components: {
    TableView,
    MainHeader,
    FormWrapper,
    RenderSelect,
    CUploadFile,
    CDownloadLink,
   <#if codeVo.isImport == 1>
    MImport,
    </#if>
    <#if codeVo.isExport == 1>
    ExportExcel,
    </#if>
  },
  mixins: [tabTodoWatch, tabTodoMixin],
  provide() {
    return { context: this };
  },
  data() {
    return {
      name: "${packageName}List",
      tableName: "${packageName}Table",
      pageSize: 15,
      gridId: "list",
        // 文件上传配置信息
        fileInfo: {
            uploadType: "FASTDFS", // 固定参数
            sourceType: "WEB_APP", // 固定参数
            fileModular: "workFlow", // 文件所属模块 -》审批流程
            fileFunction: "workflowReport", // 审批流相关文件
            fileType: "images" // 文件所属类型
        },
      currentRows: [],
      <#if codeVo.addOrEditorMode =='pop'>
      visible: false,
      form: {
          <#list selectHeadFileList as field>
          ${field.javaCode}: ${field.name},
          </#list>
      },
      rules: {
      <#list selectHeadFileList as field>
          <#if (field.isRequired == 1)>
          ${field.javaCode}: [{ required: true, message: "必填" }],
          </#if>
      </#list>
      },
      </#if>
       <#if codeVo.isImport == 1>
      extraData: {
        sourceType: "WEB_APP",
        uploadType: "FASTDFS",
        fileModular: "base",
        fileFunction: "${packageName}",
        fileType: "excel"
      },
      </#if>
      <#if codeVo.isExport == 1>
          dictCodes: {
              <#list selectHeadFileList as field>
                  <#if (field.dictItemCode)?? && (field.dictItemCode != '')>
                ${field.javaCode}:${field.dictItemCode},
                  </#if>
              </#list>
          },
      filterParams: {},
      </#if>
      tableHeader: [
        <#list selectHeadFileList as field>
        <#if field.isShowOnGrid == '1'>
        {
          prop: "${field.javaCode}",
          label: "${field.name}",
          width: 100,
        <#if (field.dictItemCode)?? && (field.dictItemCode != '')>
                formattor: val => getLabel("${field.dictItemCode}", val),
        </#if>
        },
        </#if>
        </#list>
        {
          prop: "operation",
          label: "操作",
          showType: "buttons",
          btnStyle: "text",
          fixed: "right",
          width: 130,
          buttons: [
            <#if codeVo.isEditor == 1>
            {
              callback: row => this.editHandle(row),
              // code: "pr:requirementApply:edit",
              // show: row => row.status === "DRAFT",
              formattor: () => {
                return this.$t("common.edit");
              }
            },
            </#if>
            <#if codeVo.isDeleted == 1>
            {
              callback: row => this.deleteHandle(row),
              // code: "pr:requirementApply:edit",
              // show: row => row.status === "DRAFT",
              formattor: () => {
                return this.$t("common.delete");
              }
            },
            </#if>
          ]
        }
      ],

      filterConfig: [
        <#list selectHeadFileList as field>
          <#if field.isQueryCondition == '1'>
            <#if (field.dictItemCode)?? && (field.dictItemCode != '')>
              { prop: "${field.javaCode}", label: "${field.name}", type: 'slot', slot: '${field.javaCode}' },
            <#elseif ((field.componentType)! == 'date')>
              { prop: "${field.javaCode}", label: "${field.name}", type: 'date' },
            <#else>
              { prop: "${field.javaCode}", label: "${field.name}" },
            </#if>
          </#if>
        </#list>
      ],
      queryParam: {},
    };
  },
  created() {
    mutation.loadDictionary([
  <#list selectHeadFileList as field>
      <#if (field.dictItemCode)?? && (field.dictItemCode != '')>
              "${field.dictItemCode}",
      </#if>
  </#list>
    ]);
    this.defaultTableHeader = this.tableHeader;
    this.$nextTick(() => {
      this.getQuerydata();
    });
  },
  methods: {
<#if codeVo.isImport == 1>
    handleSuccess() {
      this.getQuerydata();
    },
    downloadTemplate() {
      downloadFileLink(
        "/cloud-srm/api-${modelName}/${modelName}/${packageName}/exportExcelTemplate",
        "导入模板.xlsx"
      ).catch(() => {
        this.$message.error("下载失败");
      });
    },
    <#if codeVo.addOrEditorMode == 'pop'>
    cancel() {
      this.visible = false;
    },
    confirm() {
      this.$refs.form.validate(result => {
        if (result) {
          const flag = this.mode;
          // 新增时不用提交主键值
          const { ${pk}, ...rest } = this.form;
          if (flag === "add") {
            this.$api.generate.${packageName}.add(rest).then(res => {
              this.$message({
                type: "success",
                message: res.message
              });
              this.visible = false;
            });
          } else if (flag === "edit") {
            this.$api.generate.${packageName}.update(this.form).then(res => {
              this.$message({
                type: "success",
                message: res.message
              });
              this.visible = false;
            });
          }
        }
      });
    },
    </#if>
    </#if>
    <#if codeVo.isImport == 1>
    syncFilterParams(values) {
      this.filterParams = values;
    },
   </#if>
    getQuerydata(params) {
      this.queryParam = params;
      this.$nextTick(() => {
        this.$refs[this.gridId].query();
      });
    },
    deleteHandle(row) {
      this.$confirm(this.$t("common.confirmDelete"), {
        confirmButtonText: this.$t("common.confirm"),
        cancelButtonText: this.$t("common.cancel"),
        type: "warning"
      })
        .then(() => {
          this.$api.generate.${packageName}.delete(row.${pk}).then(res => {
            this.$message.success(res.message);
            this.getQuerydata();
          });
        })
        .catch(() => {});
    },
   <#if codeVo.isAdd == 1>
    addHandle(row) {
      this.mode = 'add';
      const tab = {
        component: ${packageName}Edit,
        params: {
          row,
          flag: this.mode
        },
        title: "${codeVo.pageName}新增",
        name: "${packageName}Edit"
      };
      this.$emit("tab-add", tab);
    },
    </#if>
   <#if codeVo.isEditor == 1>
    editHandle(row) {
      this.mode = 'edit';
      const tab = {
        component: ${packageName}Edit,
        params: {
          row,
          flag: this.mode
        },
        title: "${codeVo.pageName}编辑",
        name: "${packageName}Edit" + row.${pk}
      };
      this.$emit("tab-add", tab);
    },
    </#if>
    handleCurrentChange(val) {
      this.currentRows = val;
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
}
</script>
