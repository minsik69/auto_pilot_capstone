<template>
  <div class="Main">
학교코드
  <input type="text" v-model="univ_id">
  <button @click="go">검색</button>
  </div>
</template>

<script>

export default {
  name: 'Main',
  data() {
    return {
      univ_id : ''
    }
  },
  methods:{
    go (){
      if(this.univ_id=="1"){
      this.$router.push('/dmap')}
    }

  }
}
</script>
