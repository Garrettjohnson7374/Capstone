<template>
  <div>
    <div class="google-map" ref="googleMap">
      <template v-if="Boolean(this.google) && Boolean(this.map)">
        <slot :google="google" :map="map" />
      </template>
    </div>
  </div>
</template>

<script>
import GoogleMapsApiLoader from "google-maps-api-loader";

export default {
  props: {
    mapConfig: Object,
    markers: Array,
    playdateList: Array,
  },
  data() {
    return {
      google: null,
      map: null,
    };
  },
  async mounted() {
    const googleMapApi = await GoogleMapsApiLoader({
      apiKey: process.env.VUE_APP_GOOGLE_API_KEY,
    });
    this.google = googleMapApi;
    this.initializeMap();
  },
  methods: {
    initializeMap() {
      const mapContainer = this.$refs.googleMap;
      this.map = new this.google.maps.Map(mapContainer, this.mapConfig);

      const playdateIcon = "https://www.gstatic.com/earth/images/stockicons/190201-2016-animal-paw_4x.png";
      const homeIcon = "http://maps.google.com/mapfiles/kml/pal3/icon56.png";

      //create markers and infowindows for each playdate
      this.markers.forEach((element) => {
        const playdate = this.playdateList.find(
          (playdate) => playdate.playdateId == element.id
        );

        const contentString = this.createContentString(playdate);

        const infoWindow = this.createInfoWindow(contentString);
        const title = "Playdate with " + playdate.pet.name;
        const marker = this.createMarker(element,playdateIcon, title);
        

        marker.addListener("click", () => {
          infoWindow.open(this.map, marker);
        });
      });

      //create marker for user home
      const homeMarker = {
        id: "home",
        position: { lat: Number(this.$store.state.accountInfo.lat), lng: Number(this.$store.state.accountInfo.lng) }
      }
      this.createMarker(homeMarker, homeIcon, homeMarker.id);
    },
    formatDate(roughDate) {
      const date = new Date(roughDate);
      const timeStringLong = date.toLocaleString();
      const timeString = timeStringLong.substring(
        timeStringLong.length - 11,
        timeStringLong.length - 6
      );
      const amPm = timeStringLong.substring(
        timeStringLong.length - 3,
        timeStringLong.length
      );
      const dateString = date.toDateString();
      const dateArray = dateString.split(" ");
      const formattedDate =
        dateArray[0] +
        ", " +
        dateArray[1] +
        " " +
        dateArray[2] +
        ", " +
        dateArray[3] +
        " at " +
        timeString +
        " " +
        amPm;
      return formattedDate;
    },
    createContentString(playdate) {
      //create variables for optional and formatted playdate info
      const pic =
        playdate.pet.pic != null
          ? "<img height='100px' src=" + playdate.pet.pic + "/>"
          : "";
      const bio =
        playdate.pet.bio != null ? "<p>" + playdate.pet.bio + "</p>" : "";
      const formattedDate = this.formatDate(playdate.date);
      const breed =
        playdate.pet.breed != null
          ? "<p>Breed: " + playdate.pet.breed + "</p>"
          : "";
      const age = new Date().getFullYear() - playdate.pet.birthYear;
      const ageString = age == 0 ? "Less than 1 Year Old" : age + " years";

      const contentString =
        "<h4>Playdate with " +
        playdate.pet.name +
        "</h4>" +
        "<p>Location: " +
        playdate.address +
        ", " +
        playdate.city +
        ", " +
        playdate.state +
        " " +
        playdate.zip +
        "</p>" +
        "<p> Date & Time: " +
        formattedDate +
        "</p>" +
        pic +
        "<p>" +
        bio +
        "</p>" +
        "<p>" +
        playdate.pet.energeticRelaxed +
        " | " +
        playdate.pet.shyFriendly +
        " | " +
        playdate.pet.apatheticCurious +
        "</p>" +
        "<p>Species: " +
        playdate.pet.species +
        "</p>" +
        breed +
        "<p>Weight: " +
        playdate.pet.weight +
        " lbs</p>" +
        "<p>Age: " +
        ageString +
        "</p>" +

        "<p>" +
        "<a href='/playdates/" + playdate.playdateId +"'>Join Playdate</a>"
        "</p>";

        return contentString;
    },
    createInfoWindow(contentString) {
      const infoWindow = new this.google.maps.InfoWindow({
          size: new this.google.maps.Size(150, 50),
          content: contentString,
        });
        return infoWindow;
    },
    createMarker(element, icon, title){
      const marker = new this.google.maps.Marker({
          position: element.position,
          marker: element,
          map: this.map,
          icon: icon,
          title: title,
          animation: this.google.maps.Animation.DROP,
        });
        return marker;

    }
  },
};
</script>

<style scoped>
.google-map {
  width: 100%;
  min-height: 100%;
}
</style>