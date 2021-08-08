/**
 * 自动生成的API
 */

import http from "@/utils/http";
import { sysPrefix } from "@/config/ipConfig";

const prefix = '/${codeVo.applicationName}/${modelName}/${packageName}';
const getUrl = path => sysPrefix() + prefix + '/' + path;

export const ${packageName} = {
  list: async data =>
    http({
      url: getUrl("listPage"),
      method: "POST",
      data,
      loading: true
    }),

  delete: async id =>
    http({
      url: getUrl("delete"),
      method: "GET",
      params: { id },
      loading: true
    }),

    getById: async id =>
    http({
      url: getUrl("get"),
      method: "GET",
      params: { id },
      loading: true
    }),

  addOrUpdate: async data =>
    http({
      url: getUrl("addOrUpdate"),
      method: "POST",
      data,
      loading: true
    }),

};