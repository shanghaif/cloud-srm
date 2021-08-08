<template>
  <el-container
    direction="vertical"
    class="flex-container ${packageName}_list_wrapper"
  >
    <form-wrapper
      :formArray="filterConfig"
      @getFormData="search"
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
    <el-main>
      <easy-table
        ref="table"
        :selection="true"
        :methods="methods"
        :columns="columns"
        rowKey="${pk}"
        tableName="${packageName}_table"
        :queryParams.sync="queryParams"
      >
        <template #btns>
          <#if codeVo.isAdd == 1>
          <AuthorityButton type="primary" @click="add" code="${modelName}:${packageName}:add">{{
            $t("common.add")
          }}</AuthorityButton>
          </#if>
          <AuthorityButton type="primary" @click="save" code="${modelName}:${packageName}:save">{{
            $t("common.submit")
          }}</AuthorityButton>
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
        <#list selectHeadFileList as field>
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
      </easy-table>
    </el-main>
  </el-container>
</template>
<script>
import { tabTodoWatch, tabTodoMixin } from "@/utils/mixins";
import EasyTable from "lib@/components/BaseTable/EasyTable";
import createDictionary from "lib@/utils/ponyStore";
import CUploadFile from "@/library/components/c-upload-file";
import CDownloadLink from "lib@/components/c-download-link";
import FormWrapper from "lib@/components/Table/FormWrapper";
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
    ${field.dictItemCode}: [],
    </#if>
  </#list>
});

const RenderSelect = renderSelect();

export default {
  name: "${packageName}",
  components: {
    EasyTable,
    RenderSelect,
    CUploadFile,
    CDownloadLink,
      FormWrapper,
    <#if codeVo.isImport == 1>
    MImport,
    </#if>
    <#if codeVo.isExport == 1>
    ExportExcel,
    </#if>
  },
  mixins: [tabTodoWatch, tabTodoMixin],
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
            <#if (field.dictItemCode)?? && field.dictItemCode !=''>
              ${field.javaCode}:"${field.dictItemCode}",
            </#if>
          </#list>
        },
        filterParams: {},
      </#if>
      queryParams: {},
      methods: {
        listPage: async params => {
          const res = await this.$api.generate.${packageName}.list(params);
          return res;
        }
      },
      columns: [
        <#list selectHeadFileList as field>
          <#if (field.isShowOnForm == '1')>
            {
              attrs: {
                label: "${field.name}",
                prop: "${field.javaCode}",
                  <#if (field.dictItemCode)?? && (field.dictItemCode != '')>
                  formatter: value => getLabel("${field.dictItemCode}", value),
                  </#if>
              },
              slot: '${field.javaCode}',
              rules: { required: ${field.isRequired}, message: '必填' }
            },
          <#elseif (field.isShowOnGrid == '1')>
            {
              attrs: {
                label: "${field.name}",
                prop: "${field.javaCode}",
              },
            },
          <#else>
          </#if>
        </#list>
        {
          attrs: {
            prop: 'operation',
            label: '操作',
            width: 100,
            fixed: 'right',
          },
          operations: [
            {
              event: 'deleteItem',
              name: this.$t("common.delete"),
              func: this.deleteItem
            }
          ]
        }
      ]
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
  },
  methods: {
    <#if codeVo.isImport == 1>
      syncFilterParams(values) {
        this.filterParams = values;
      },
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
    </#if>
    // 上传附件成功
    handleUploadSuccess(file, row, key) {
      const { id, name } = file;
      row[key] = id.toString();
    },
    // 删除文件
    handleAttachmentRemove(row, key) {
      row[key] = "";
    },
    deleteItem(scope, data) {
      if (scope.row.${pk}) {
        // 有主键ID
        this.$confirm(this.$t('common.deleteViews'), {
          confirmButtonText: this.$t('common.confirm'),
          cancelButtonText: this.$t('common.cancel'),
          type: 'warning'
        }).then(() => {
          this.$api.generate.${packageName}.delete(scope.row.${pk}).then(res => {
            this.$message.success(res.message);
            this.$refs.table.search(this.queryParams, true);
          })
        }).catch(() => {

        })
      } else {
        // 无主键ID
        data.splice(scope.$index, 1);
      }
    },
    search(params) {
      const { pageSize, pageNum } = this.queryParams;
      this.$refs.table.search({ pageSize, pageNum, ...params }, true);
    },
    add() {
      this.$refs.table.add({});
    },
    save() {
      const list = this.$refs.table.getUpdatedRows();
      this.$refs.table.validate((f) => {
        if (f) {
          this.$api.generate.${packageName}.batchSaveOrUpdate(list.map(({ ${pk}, ...rest }) => {
              if (!${pk}) {
                  return rest;
              } else {
                  return { ${pk}, ...rest };
              }
          })).then(res => {
            this.$message.success(res.message);
            this.$refs.table.search(this.queryParams, true);
          });
        } else {
          this.$message({
            message: '请输入单据必填信息',
            type: "error"
          });
        }
      })  
    },
  }
};
</script>
<style scoped lang="scss">
</style>
