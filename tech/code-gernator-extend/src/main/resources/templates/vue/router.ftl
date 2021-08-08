export default {
  path: "/generate",
  name: "generate",
  component: () => import("@/layout"),
  redirect: {
    name: "${packageName}"
  },
  children: [
    {
      path: "${packageName}",
      component: () =>
        import("mod@/generate/views/${packageName}"),
      name: "${packageName}",
      meta: {
        title: "${codeVo.pageName}",
        requiresAuth: true
      }
    },
  ]
};
