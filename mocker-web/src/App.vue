
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
    pre code {
        background-color: #424242;
        border: 1px solid #999;
        display: block;
        padding: 20px;
        overflow: hidden;
        word-break: break-word;
        white-space: break-spaces;
    }
</style>
