import Vue from 'vue'
import Router from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import Logout from '../views/Logout.vue'
import Register from '../views/Register.vue'
import RegisterPet from '../views/RegisterPet.vue'
import store from '../store/index'
import Profile from '@/views/Profile.vue'
import PetProfile from '@/views/PetProfile.vue'
import UpdatePet from '@/views/UpdatePet.vue'
import UpdateProfile from '@/views/UpdateProfile.vue'
import CreateProfile from '@/views/CreateProfile.vue'
import CreatePlaydate from '@/views/CreatePlaydate.vue'
import Playdates from '@/views/Playdates.vue'
import UpdatePlaydate from '@/views/UpdatePlaydate.vue'
import MessageForum from '@/views/MessageForum.vue'
import JoinPlaydate from '@/views/JoinPlaydate.vue'
import WriteMessage from '@/views/WriteMessage.vue'

Vue.use(Router)

/**
 * The Vue Router is used to "direct" the browser to render a specific view component
 * inside of App.vue depending on the URL.
 *
 * It also is used to detect whether or not a route requires the user to have first authenticated.
 * If the user has not yet authenticated (and needs to) they are redirected to /login
 * If they have (or don't need to) they're allowed to go about their way.
 */

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/login",
      name: "login",
      component: Login,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/logout",
      name: "logout",
      component: Logout,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/register",
      name: "register",
      component: Register,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/pets/register",
      name: "registerPet",
      component: RegisterPet,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/pets/:id",
      name: "petProfile",
      component: PetProfile,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/profile",
      name: "profile",
      component: Profile,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/pets/:id/update",
      name: "updatePet",
      component: UpdatePet,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/profile/update",
      name: "updateProfile",
      component: UpdateProfile,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/profile/create",
      name: "createProfile",
      component: CreateProfile,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/create/playdate",
      name: "createPlaydate",
      component: CreatePlaydate,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/playdates",
      name: "playdates",
      component: Playdates,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/playdates/:id",
      name: "joinPlaydate",
      component: JoinPlaydate,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/playdates/:id/update",
      name: "updatePlaydate",
      component: UpdatePlaydate,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/messageforum",
      name: "messageforum",
      component: MessageForum,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/messageforum/write",
      name: "writeMessage",
      component: WriteMessage,
      meta: {
        requiresAuth: true
      }
    },

  ]
})

router.beforeEach((to, from, next) => {
  // Determine if the route requires Authentication
  const requiresAuth = to.matched.some(x => x.meta.requiresAuth);

  // If it does and they are not logged in, send the user to "/login"
  if (requiresAuth && store.state.token === '') {
    next("/");
  } else {
    // Else let them go to their next destination
    next();
  }
});

export default router;
