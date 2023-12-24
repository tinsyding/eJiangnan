import '../../utils/index.mjs';
import Alert from './src/alert.mjs';
export { alertEffects, alertEmits, alertProps } from './src/alert2.mjs';
import { withInstall } from '../../utils/vue/install.mjs';

const ElAlert = withInstall(Alert);

export { ElAlert, ElAlert as default };
//# sourceMappingURL=index.mjs.map
