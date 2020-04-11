`<template>
  <div id="dmap">
    <h1>{{msg}}</h1>
    <div id="map" style="width:750px; height:350px;"></div>
    <button v-on:click="openNew">gps 받는 임시버튼</button>
    <div>{{x}}</div>
  </div>
</template>

<script>
  export default {
    name: 'dmap',
        data() {
      return {
        msg:"카카오맵 테스트",
        x:35.895243,
        container : '',
        marker : '',
        mapOptions : '',
        map : '',
        infowindow : ''
      }
    },

      mounted() {
          this.container = document.getElementById('map')
          this.mapOptions = {
              center: new daum.maps.LatLng(35.895243, 128.623593),
              level: 3, //지도의 레벨(확대, 축소 정도)
              mapTypeId : kakao.maps.MapTypeId.ROADMAP // 지도종류
          };
          this.marker = new kakao.maps.Marker({
              position: new kakao.maps.LatLng(this.x, 128.623593), // 마커의 좌표35.895243, 128.623593
              map: this.map // 마커를 표시할 지도 객체
          });
          this.map = new daum.maps.Map(this.container, this.mapOptions);
          this.marker.setMap(this.map);
          // 마커 위에 표시할 인포윈도우를 생성한다
          this.infowindow = new kakao.maps.InfoWindow({
          content : '<div style="padding:5px;">영진전문대 정문</div>' // 인포윈도우에 표시할 내용
          });

          // 인포윈도우를 지도에 표시한다
          this.infowindow.open(this.map, this.marker);
              // 마커에 클릭 이벤트를 등록한다 (우클릭 : rightclick)
          kakao.maps.event.addListener(this.marker, 'click', function() {
              alert('마커를 클릭했습니다!');
          });
          },

    methods: {
      openNew: function() {
          this.x=this.x+0.000005; 
          this.infowindow.setMap(null);
          this.marker.setMap(null);
          this.marker = new kakao.maps.Marker({
              position: new kakao.maps.LatLng(this.x, 128.623593), // 마커의 좌표35.895243, 128.623593
              map: this.map // 마커를 표시할 지도 객체
          });
          this.infowindow = new kakao.maps.InfoWindow({
          content : '<div style="padding:5px;">영진전문대 정문</div>' // 인포윈도우에 표시할 내용
          });
        	// 인포윈도우를 지도에 표시한다
          this.infowindow.open(this.map, this.marker);
          // 마커에 클릭 이벤트를 등록한다 (우클릭 : rightclick)
		      kakao.maps.event.addListener(this.marker, 'click', function() {
		          alert('마커를 클릭했습니다!');
          });
      }
    }
  }
</script>

<style scoped>

</style>`