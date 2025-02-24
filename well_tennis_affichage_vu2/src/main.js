import { createApp } from 'vue'
import './assets/index.css'
import App from './App.vue'
import router from './router'; // Importez le routeur
import './style.css'







const app = createApp(App);
app.use(router); // Activez le routeur dans l'application Vue
app.mount('#app');
