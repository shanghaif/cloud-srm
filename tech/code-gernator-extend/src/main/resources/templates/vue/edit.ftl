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
                    v-if="!form.${filed.javaCode}"
                    :limit="1"
                    showProgress
                    :show-file-list="false"
                    :cus-data="fileInfo"
                    @upload-success="file => handleUploadSuccess(file, form, '${filed.javaCode}')"
                  />
                  <div v-else class="download-link-wrap">
                    <c-download-link
                      :id="form.${filed.javaCode}"
                      :name="form.${field.javaCode}"
                      ellipsis
                      class="download-link-item"
                    />
                    <i
                      class="el-icon-close close-icon"
                      @click="handleAttachmentRemove(form, '${filed.javaCode}')"
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
                v-if="!form.${filed.javaCode}"
                :limit="1"
                showProgress
                :show-file-list="false"
                :cus-data="fileInfo"
                @upload-success="file => handleUploadSuccess(file, form, '${filed.javaCode}')"
              />
              <div v-else class="download-link-wrap">
                <c-download-link
                  :id="form.${filed.javaCode}"
                  :name="form.${field.javaCode}"
                  ellipsis
                  class="download-link-item"
                />
                <i
                  class="el-icon-close close-icon"
                  @click="handleAttachmentRemove(form, '${filed.javaCode}')"
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
      this.form = row;
    }
  },
  watch: {
  },
  computed: {
  },
  methods: {
    save() {
      this.$refs.form.validate(result => {
        if (result) {
          const { flag } = this.$attrs.params;
          // 新增时不用提交主键值
          const { ${pk}, ...rest } = this.form;
          if (flag === "add") {
            this.$api.generate.${packageName}.add(rest).then(res => {
              this.$message({
                type: "success",
                message: res.message
              });
              this.cancelBill();
            });
          } else if (flag === "edit") {
            this.$api.generate.${packageName}.update(this.form).then(res => {
              this.$message({
                type: "success",
                message: res.message
              });
              this.cancelBill();
            });
          }
        } else {
            this.__focus_eror__();
        }
      });
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
