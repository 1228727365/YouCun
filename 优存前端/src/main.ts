import { createApp } from 'vue'
import { createPinia } from 'pinia';
import App from './App.vue'
import routes from './router/index.ts'

import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

import * as ElementPlusIconsVue from '@element-plus/icons-vue'
const pinia = createPinia();
const app=createApp(App)
app.use(pinia);
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}
app.use(routes)
app.use(ElementPlus)
app.mount('#app')