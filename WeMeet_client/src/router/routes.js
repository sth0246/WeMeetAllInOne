
const Home = () => import ("@/components/Home.vue")
const Login = () => import("@/components/Login.vue")
const RoomSerach = () => import("@/components/RoomSearch.vue")
const FriendList = () => import("@/components/FriendList.vue")
const Profile = () => import("@/components/Profile.vue")
const AddFriend = () => import("@/components/AddFriend.vue")
const routes = [
    {
        path:'/',
        redirect:'/home'
    },
    {
        path:"/home",
        name:"home",
        component:Home,
        children:[
            {
                path:'login',
                name:'login',
                component:Login
            },
            {
                path:'searchroom',
                name:'searchRoom',
                component:RoomSerach    
            },
            {
                path:'friendlist',
                name:'friendList',
                component:FriendList,
            },
            {
                path:'profile',
                name:'profile',
                component:Profile,
            },
            {
                path:'addfriend',
                name:'addFriend',
                component:AddFriend
            }
        ]
    }
]

export default routes