"use strict";Object.defineProperty(exports, "__esModule", {value: true});

var _chunk5MUHROAQjs = require('./chunk-5MUHROAQ.js');
require('./chunk-WRE7G5OD.js');
require('./chunk-5JVO2UWC.js');
require('./chunk-6F4PWJZI.js');

// src/nuxt.ts
var _kit = require('@nuxt/kit');
var nuxt_default = _kit.defineNuxtModule.call(void 0, {
  setup(options) {
    _kit.addWebpackPlugin.call(void 0, _chunk5MUHROAQjs.unplugin_default.webpack(options));
    _kit.addVitePlugin.call(void 0, _chunk5MUHROAQjs.unplugin_default.vite(options));
  }
});


module.exports = nuxt_default;
exports.default = module.exports;