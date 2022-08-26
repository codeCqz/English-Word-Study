<template>
    <div id="main">
      <Header></Header>
      <!-- <keep-alive> -->
        <router-view></router-view>
      <!-- </keep-alive> -->
    </div>
</template>

<script>

import Header from  '../components/Header.vue'

export default {
  name: 'Main',
  components:{
    Header
  },
}
</script>


<style>
#main {
height: 100%;
width: 100%;
}

</style>
