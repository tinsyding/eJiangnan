import {
  unplugin_default
} from "./chunk-XR5T4LZ3.mjs";
import "./chunk-L2JFDMEX.mjs";
import "./chunk-T2FESLJY.mjs";
import "./chunk-WBQAMGXK.mjs";

// src/nuxt.ts
import { addVitePlugin, addWebpackPlugin, defineNuxtModule } from "@nuxt/kit";
var nuxt_default = defineNuxtModule({
  setup(options) {
    addWebpackPlugin(unplugin_default.webpack(options));
    addVitePlugin(unplugin_default.vite(options));
  }
});
export {
  nuxt_default as default
};
