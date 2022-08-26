<template>
  <div id="app">
      <keep-alive >
        <router-view></router-view>
      </keep-alive>
  </div>
</template>

<script>



export default {
  name: 'App',
  
}


</script>
<style>
#app {
  width: 100%;
  height: 100%;
}
* { 
  padding: 0;
  margin: 0;
  box-sizing: border-box;
  -moz-box-sizing: border-box; 
}
html,body {
  margin: 0;
  padding: 0;
  width: 100%;
  height: 100%;
}
@media screen and (max-width: 720px) {

}
</style>