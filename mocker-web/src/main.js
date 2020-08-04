import Vue from 'vue'
import Vuex from 'vuex'
import App from './App.vue'
import VueMaterial from 'vue-material'
import VueRouter from 'vue-router'
import Vuelidate from 'vuelidate'

import 'vue-material/dist/vue-material.min.css'
import 'vue-material/dist/theme/default-dark.css'
// import 'vue-material/dist/theme/default.css'

Vue.config.productionTip = false
Vue.use(VueMaterial)
Vue.use(Vuex);
Vue.use(VueRouter)
Vue.use(Vuelidate)

import Create from './components/Create'
import MockList from './components/MockList'
import Info from "./components/Info";
// import MockDialog from "./components/MockDialog";


const routes = [
  { path: '/', redirect: '/info' },
  { path: '/info', component: Info },
  { path: '/create', component: Create },
  { path: '/list', component: MockList },
  // { path: '/test', component: MockDialog }
]

const router = new VueRouter({
  routes,
 // linkActiveClass
})

var Store = new Vuex.Store({
  state: {
    loading: false,
    uuid: null,
    colors: {
      GET: '#2bcc40',
      POST: '#e6a147',
      DELETE: '#e03d3d',
      PUT: '#3f87eb'
    },
    methods: ['GET', 'POST', 'DELETE', 'PUT']
  },
  mutations: {
    setLoading(state, value) {
      console.log('[store] loading',value);
      this.state.loading = value;
    },
    setUUID(state, value){
      console.log('[store] uuid',value);
      this.state.uuid = value;
    }
  }
})



new Vue({
  render: h => h(App),
  store: Store,
  router
}).$mount('#app')


