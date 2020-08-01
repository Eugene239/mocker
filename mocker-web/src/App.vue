
<template>
  <div id="app" class="app">
      <Main></Main>
  </div>
</template>

<script>

import Main from './components/Main.vue'

export default {
  name: 'App',
  //store: store,
  components: {
    Main
  }
}

</script>

<style>
    .app{
        height: 100vh;
        background-color: red;
    }
</style>
