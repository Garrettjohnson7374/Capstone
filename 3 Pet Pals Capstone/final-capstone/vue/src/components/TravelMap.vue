<template>
  <GoogleMapLoader
  :mapConfig="mapConfig"
  :markers="markers"
  :playdateList="playdateList"
>

</GoogleMapLoader>
</template>

<script>
import GoogleMapLoader from "./GoogleMapLoader";
// import GoogleMapMarker from "./GoogleMapMarker";


import { mapSettings } from "@/constants/mapSettings";

export default {
  name: "travelMap",
  components: {
    GoogleMapLoader,

  },
  props: ["playdateList", "markers"],

  methods:{

   
  },

  data() {
    return {
    };
  },

  computed: {
    mapConfig() {
      return {
        ...mapSettings,
        center: this.mapCenter
      };
    },

    mapCenter() {
      let mapCenter = this.markers[0].position
      if(this.$store.state.token != ''){
        mapCenter = { lat: Number(this.$store.state.accountInfo.lat), lng: Number(this.$store.state.accountInfo.lng) }
      }
      return mapCenter;
    }
  }
};
</script>

<style>

</style>