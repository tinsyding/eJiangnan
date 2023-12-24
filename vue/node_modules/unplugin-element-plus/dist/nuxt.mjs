import {
  src_default
} from "./chunk-G5M4GEJP.mjs";

// src/nuxt.ts
function nuxt_default(options) {
  this.extendBuild((config) => {
    config.plugins = config.plugins || [];
    config.plugins.unshift(src_default.webpack(options));
  });
  this.nuxt.hook("vite:extend", (vite) => {
    vite.config.plugins = vite.config.plugins || [];
    vite.config.plugins.push(src_default.vite(options));
  });
}
export {
  nuxt_default as default
};
