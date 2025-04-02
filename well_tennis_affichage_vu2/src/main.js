import { createApp } from 'vue'
import { createPinia } from "pinia";
import './assets/index.css'
import App from './App.vue'
import router from './router'; // Importez le routeur
import './style.css'








const app = createApp(App);
const pinia = createPinia();
app.use(pinia); // 🔹 Assure-toi que Pinia est bien utilisé avant tout store !
app.use(router); // Activez le routeur dans l'application Vue

app.mount('#app');
