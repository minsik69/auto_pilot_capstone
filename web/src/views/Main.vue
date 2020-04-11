<template>
  <div class="Main">
학교코드
  <input type="text" class="univ_id" v-model="univ_id">
  <button @click="go">검색</button>
  </div>
</template>

<script>

export default {
  name: 'Main',
  methods:{
    go (){q
      if(this.univ_id=="1"){
      this.$router.push('/dmap')}
    }

  }
}
</script>
