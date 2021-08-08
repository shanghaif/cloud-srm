<template>
  <nav-tabs ref="tabs" :tabs-list="tabs" :cur-tab="activeTab" />
</template>
<script>
import NavTabs from "lib@/components/NavTabs";
import ${packageName}List from "./list";
export default {
  name: "${packageName}",
  components: {
    NavTabs
  },
  data() {
    return {
      activeTab: "${packageName}List", // 当前激活标签  与name相同
      tabs: [
        {
          title: "${codeVo.pageName}",   //页面名称
          name: "${packageName}List",
          component: ${packageName}List,
          closable: false
        }
      ]
    };
  }
};
</script>
