"use strict";Object.defineProperty(exports, "__esModule", {value: true}); function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }// src/index.ts
var _pluginutils = require('@rollup/pluginutils');
var _unplugin = require('unplugin');

// src/core/style.ts
var _esmodulelexer = require('es-module-lexer');
var _magicstring = require('magic-string'); var _magicstring2 = _interopRequireDefault(_magicstring);
var hyphenateRE = /\B([A-Z])/g;
var hyphenate = (str) => str.replaceAll(hyphenateRE, "-$1").toLowerCase();
var formatMap = {
  cjs: "lib",
  esm: "es"
};
var multilineCommentsRE = /\/\*\s(.|[\n\r])*?\*\//gm;
var singlelineCommentsRE = /\/\/\s.*/g;
function stripeComments(code) {
  return code.replaceAll(multilineCommentsRE, "").replaceAll(singlelineCommentsRE, "");
}
var transformImportStyle = (specifier, source, useSource = false, options) => {
  const { prefix, lib, format, ignoreComponents } = options;
  const statement = stripeComments(source.slice(specifier.ss, specifier.se));
  const leftBracket = statement.indexOf("{");
  if (leftBracket > -1) {
    const identifiers = statement.slice(leftBracket + 1, statement.indexOf("}"));
    const components = identifiers.split(",");
    const styleImports = [];
    components.forEach((c) => {
      const trimmed = c.replace(/\sas\s.+/, "").trim();
      if (trimmed.startsWith(prefix)) {
        const component = trimmed.slice(prefix.length);
        if (ignoreComponents.includes(component))
          return;
        if (useSource) {
          styleImports.push(
            `import '${lib}/${formatMap[format]}/components/${hyphenate(
              component
            )}/style/index'`
          );
        } else {
          styleImports.push(
            `import '${lib}/${formatMap[format]}/components/${hyphenate(
              component
            )}/style/css'`
          );
        }
      }
    });
    return styleImports.join("\n");
  }
};
var transformStyle = async (source, options) => {
  const { useSource, lib, prefix, format, ignoreComponents } = options;
  if (!source)
    return;
  await _esmodulelexer.init;
  const specifiers = _esmodulelexer.parse.call(void 0, source)[0].filter(({ n }) => {
    return n === lib || n === `${lib}/es/components` || n === `${lib}/lib/components`;
  });
  if (specifiers.length === 0)
    return;
  const styleImports = specifiers.map((s2) => {
    const ret = transformImportStyle(s2, source, useSource, {
      lib,
      prefix,
      format,
      ignoreComponents
    });
    return ret;
  }).filter((s2) => s2).join("\n");
  const lastSpecifier = specifiers.at(-1);
  const s = new (0, _magicstring2.default)(source);
  s.appendLeft(lastSpecifier.se + 1, `
${styleImports}
`);
  return {
    code: s.toString(),
    get map() {
      return s.generateMap({ hires: true, includeContent: true });
    }
  };
};

// node_modules/.pnpm/escape-string-regexp@5.0.0/node_modules/escape-string-regexp/index.js
function escapeStringRegexp(string) {
  if (typeof string !== "string") {
    throw new TypeError("Expected a string");
  }
  return string.replace(/[|\\{}()[\]^$+*?.]/g, "\\$&").replace(/-/g, "\\x2d");
}

// src/core/default-locale.ts
var getLocaleRE = (options) => new RegExp(
  `${escapeStringRegexp(`${options.lib}/`)}(es|lib)${escapeStringRegexp(
    "/hooks/use-locale/index"
  )}`
);
var transformDefaultLocale = (options, source, id) => {
  if (!id.match(getLocaleRE(options)))
    return;
  return source.replace(
    "locale/lang/en",
    `locale/lang/${options.defaultLocale}`
  );
};
var getViteDepPlugin = (options) => {
  const localeImporterRE = new RegExp(
    `${escapeStringRegexp(
      `node_modules/${options.lib}/`
    )}(es|lib)${escapeStringRegexp("/hooks/use-locale/index")}`
  );
  const localePath = "/locale/lang/en";
  const localePathFixed = `/locale/lang/${options.defaultLocale}`;
  return {
    name: "unplugin-element-plus:default-locale",
    setup(build) {
      build.onResolve(
        {
          filter: new RegExp(escapeStringRegexp(localePath)),
          namespace: "file"
        },
        ({ path, importer, kind, resolveDir }) => {
          if (localeImporterRE.test(importer))
            return build.resolve(path.replace(localePath, localePathFixed), {
              importer,
              kind,
              resolveDir
            });
        }
      );
    }
  };
};

// src/index.ts
var defaultOptions = {
  include: [
    "**/*.vue",
    "**/*.ts",
    "**/*.js",
    "**/*.tsx",
    "**/*.jsx",
    "**/*.vue?vue&type=script*"
  ],
  exclude: [/[/\\]node_modules[/\\]/, /[/\\]\.git[/\\]/, /[/\\]\.nuxt[/\\]/],
  lib: "element-plus",
  ignoreComponents: [],
  useSource: false,
  defaultLocale: "",
  format: "esm",
  prefix: "El",
  sourceMap: false
};
var src_default = _unplugin.createUnplugin.call(void 0, (userOptions = {}) => {
  const options = Object.assign(defaultOptions, userOptions);
  const filter = _pluginutils.createFilter.call(void 0, options.include, options.exclude);
  return {
    name: "unplugin-element-plus",
    enforce: "post",
    transformInclude(id) {
      return getLocaleRE(options).test(id) || filter(id);
    },
    transform(source, id) {
      if (options.defaultLocale) {
        const result = transformDefaultLocale(options, source, id);
        if (result)
          return result;
      }
      return transformStyle(source, options);
    },
    vite: {
      config() {
        if (options.defaultLocale) {
          return {
            optimizeDeps: {
              esbuildOptions: {
                plugins: [getViteDepPlugin(options)]
              }
            }
          };
        }
      }
    }
  };
});



exports.src_default = src_default;
