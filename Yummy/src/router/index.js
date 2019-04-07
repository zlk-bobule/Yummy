import Vue from 'vue'
import Router from 'vue-router'
import store from '../store/index'
// import HelloWorld from '@/components/HelloWorld'
// import Login from '@/view/login/Login'

// import Signin from '@/components/signin/signin.vue'
import Signup from '@/components/signup/signup.vue'
import Signin from '@/components/signin/signin.vue'
import StartPage from '@/components/startPage/startPage'

import NavigationBar from '@/view/home/navigationBar'
import UserMenu from '@/view/user/userMenu'
import User from '@/view/user/user'
import PersonalData from '@/view/user/personalData'
import Address from '@/view/user/address'
import RestaurantReg from '@/view/restaurant/restaurantReg'
import ResHome from '@/view/restaurant/resHome'
import ResInfo from '@/view/restaurant/resInfo'
import JIS from '@/view/restaurant/jis'
import Publish from '@/view/restaurant/publish'
import ProductPublish from '@/view/restaurant/publish/product'
import PackagePublish from '@/view/restaurant/publish/package'
import Home from '@/view/home/home'
import Choose from '@/view/choose/choose'
import ResDetail from '@/view/resDetail/resDetail'
import OrderInfo from '@/view/order/orderInfo'
import UserCenter from '@/view/user/userCenter'
import UserData from '@/view/user/userData'
import Pay from '@/view/order/pay'
import MyOrderList from '@/view/myOrder/myOrderList'
import ConcreteOrder from '@/view/myOrder/orderConcrete'
import ResOrderList from '@/view/restaurant/resOrderList/resOrderList'
import Discoount from '@/view/restaurant/publish/discount'
import DeliverList from '@/view/restaurant/deliverList/deliverList'
import ResOrderConcrete from '@/view/restaurant/deliverList/resOrderConcrete'
import AdminHome from '@/view/admin/adminHome'
import Examine from '@/view/admin/examine'
import Statistics from '@/view/admin/statistics'
import YummyFinance from '@/view/admin/yummyFinance'

Vue.use(Router)


export default new Router({
  routes: [
    {
      path: '/',
      redirect: '/startPage'
    },
    {
      path:'/startPage',
      component:StartPage
    },
    {
      path:'/sign',
      component:Signup
    },
    {
      path:'/login',
      component:Signin
    },
    {
      path:'/home',
      component:Home,
      children: [
        {
          path:'/',
          component:Choose
        },
        {
          path:'/userInfo',
          component:UserMenu,
          redirect:'/userCenter',
          children: [
            {
              path:'/userCenter',
              component:UserCenter
            },
            {
              path:'/userData',
              component:UserData
            },
            {
              path:'/userAddress',
              component:Address
            },
            {
              path:'/myOrderList',
              component:MyOrderList
            },
            {
              path:'/concreteOrder',
              component:ConcreteOrder
            }
          ]
        },
        {
          path:'/resDetail',
          component:ResDetail
        },
        {
          path:'/orderInfo',
          component:OrderInfo
        },
        {
          path:'/pay',
          component:Pay
        }
      ]
    },
    {
      path:'/restaurantSign',
      component:RestaurantReg
    },
    {
      path:'/resHome',
      component:ResHome,
      redirect:'/publish',
      children: [
        {
          path:'/resInfo',
          component:ResInfo
        },
        {
          path:'/publish',
          component:Publish,
          redirect:'/productPublish',
          children: [
            {
              path:'/productPublish',
              component:ProductPublish
            },
            {
              path:'/packagePublish',
              component:PackagePublish
            },
            {
              path:'/discountPublish',
              component:Discoount
            }
          ]
        },
        {
          path:'/resOrderList',
          component:ResOrderList
        },
        {
          path:'/deliverList',
          component:DeliverList
        },
        {
          path:'/resOrderConcrete',
          component:ResOrderConcrete
        }
      ]
    },
    {
      path:'/adminHome',
      component:AdminHome,
      children: [
        {
          path:'/',
          component:Examine
        },
        {
          path:'/statistics',
          component:Statistics
        },
        {
          path:'/yummyFinance',
          component:YummyFinance
        }
      ]
    }
  ],
  linkActiveClass: 'active'
})

