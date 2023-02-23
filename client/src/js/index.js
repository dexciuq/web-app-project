import { UI_ELEMENTS } from "./views.js"; 
import Cookies from 'js-cookie';

import { 
    getAllUsers,
    findUser,
    registration,
    auth,
    getAllPosts,
    getCurrentUser,
    postNews,
    logout,
    getUserById,
    updateUserById,
    deleteUserById,
    followUserByUsername,
    unfollowUserByUsername,
    searchPost,
    getPostById,
    deletePostById,
    postComment,
    deleteComment,
    updateComment,
} from "./server.js";

import { 
    renderAllPosts,
    renderSignIn,
    renderUser,
    renderUserPosts,
    renderUserProfile, 
    renderNews,
    renderNewsContainer,
    removeNewsContainer,
} from "./render.js";

UI_ELEMENTS.SEARCH_FORM.addEventListener('submit', function(event)   {
    event.preventDefault();
    switchProfile()
    searchUserById();
});
UI_ELEMENTS.HOME_BUTTON.addEventListener('click', function()    {
    switchHome();
})
UI_ELEMENTS.PROFILE_BUTTON.addEventListener('click', function()    {
    switchProfile()
    openMainProfile();
})
UI_ELEMENTS.REGISTER_FORM.addEventListener('submit', function(event) {
    event.preventDefault();
    getRegistrationData();
})
UI_ELEMENTS.SIGN_IN_FORM.addEventListener('submit', function(event)  {
    event.preventDefault();
    getSignInData();
}) 
UI_ELEMENTS.POST_BLOCK_FORM.addEventListener('submit', function(event)   {
    event.preventDefault();
    sendUserPost();
})
UI_ELEMENTS.LOGOUT_BUTTON.addEventListener('click', function()  {
    logoutToAuthorization();
})

UI_ELEMENTS.GET_USER_BY_ID_FORM.addEventListener('submit', function(event)   {
    event.preventDefault();
    let id = UI_ELEMENTS.GET_USER_BY_ID_INPUT.value;
    getUserById(id);
})
UI_ELEMENTS.DELETE_USER_BY_ID_FORM.addEventListener('submit', function(event)    {
    event.preventDefault();
    let id = UI_ELEMENTS.DELETE_USER_BY_ID_INPUT.value;
    deleteUserById(id);
})
UI_ELEMENTS.FOLLOW_USER_FORM.addEventListener('submit', function(event)     {
    event.preventDefault();
    let username = UI_ELEMENTS.FOLLOW_USER_INPUT.value;
    followUserByUsername(username);
})
UI_ELEMENTS.UNFOLLOW_USER_FORM.addEventListener('submit', function(event)   {
    event.preventDefault();
    let username = UI_ELEMENTS.UNFOLLOW_USER_INPUT.value;
    unfollowUserByUsername(username);
})
UI_ELEMENTS.SEARCH_POST_FORM.addEventListener('subnit', function(event)     {
    event.preventDefault();
    let tag = UI_ELEMENTS.SEARCH_FORM_INPUT_TAG.value;
    let title = UI_ELEMENTS.SEARCH_FORM_INPUT_TITLE.value;
    searchPost(tag, title)
})
UI_ELEMENTS.GET_POST_BY_ID_FORM.addEventListener('submit', function(event)  {
    event.preventDefault();
    let id = UI_ELEMENTS.GET_POST_BY_ID_INPUT.value;
    getPostById(id);
})
UI_ELEMENTS.DELETE_POST_BY_ID_FORM.addEventListener('submit', function(event)   {
    event.preventDefault();
    let id = UI_ELEMENTS.DELETE_POST_BY_ID_INPUT.value;
    deletePostById(id);
})
UI_ELEMENTS.POST_COMMENT_FORM.addEventListener('submit', function(event)    {
    event.preventDefault();
    let id = UI_ELEMENTS.POST_COMMENT_INPUT.value;
    let comment = UI_ELEMENTS.POST_COMMENT_INPUT_COMMENT.value;
    postComment(id, comment);
})
UI_ELEMENTS.UPDATE_COMMENT_FORM.addEventListener('submit', function(event)  {
    event.preventDefault();
    let id = UI_ELEMENTS.UPDATE_COMMENT_INPUT.value;
    let comment = UI_ELEMENTS.UPDATE_COMMENT_INPUT_COMMENT.value;
    updateComment(id, comment);
})
UI_ELEMENTS.DELETE_COMMENT_FORM.addEventListener('submit', function(event)  {
    event.preventDefault();
    let id = UI_ELEMENTS.DELETE_COMMENT_INPUT.value;
    let comment = UI_ELEMENTS.DELETE_COMMENT_INPUT_COMMENT.value;
    deleteComment(id, comment);
})
UI_ELEMENTS.BETA_BUTTON.addEventListener('click', function()    {
    if(this.value === 'OFF'){
        this.value = "ON";
        UI_ELEMENTS.BETA_CONTAINER.classList.add('hider');
    }else{
        this.value = "OFF";
        UI_ELEMENTS.BETA_CONTAINER.classList.remove('hider');
    }
})

async function logoutToAuthorization()    {
    logout();
    window.location.reload();
}

async function getCurrentSession()    {

    try{
        let user = await getCurrentUser();
        renderSignIn(user);
        saveAllUsers()

    }catch(err){
        console.log(err)
    }
}

getCurrentSession()

function getRegistrationData()  {

    try{
        let user = {
            username: UI_ELEMENTS.REGISTER_USERNAME.value,
            email: UI_ELEMENTS.REGISTER_EMAIL.value,
            password: UI_ELEMENTS.REGISTER_PASSWORD.value,
            name: UI_ELEMENTS.REGISTER_NAME.value,
            surname: UI_ELEMENTS.REGISTER_SURNAME.value,
            dob: UI_ELEMENTS.REGISTER_DOB.value,
            phoneNumber: UI_ELEMENTS.REGISTER_NUMBER.value,
        }
       
        registration(user);
        alert('Successful registration! Sign in please.');

    }catch(err){
        alert('Error!');
    }
}

async function getSignInData()    {
    try{
        let user = {
            username: UI_ELEMENTS.SIGN_IN_USERNAME.value,
            password: UI_ELEMENTS.SIGN_IN_PASSWORD.value,
        };

        let obj = await auth(user);
        Cookies.set('token', obj.token);
        let currentUser = await getCurrentUser();
        Cookies.set('id', currentUser.id);
        renderSignIn(currentUser);
        saveAllUsers()

    }catch(err){
        alert('Wrong password!')
    }
}

async function saveAllUsers()   {
    let users = await getAllUsers();

    for(let i = 0; i < users.length; i++)   {
        renderUser(users[i]);
    }

}

UI_ELEMENTS.RECOMMENDATIONS_LIST.onclick = function(event)  {
    let target = event.target.closest('#username-recommendations');
    let username = target.textContent;
    switchProfile()
    findAnotherUser(username);
}

function searchUserById()   {
    let userID = UI_ELEMENTS.SEARCH_INPUT.value;
    findUser(userID);
}

async function findAnotherUser(username)   {
    UI_ELEMENTS.POSTS_SECTION.classList.add('hider');
    UI_ELEMENTS.MAIN_USER_NEWS_FEED.classList.add('hider');
    let user = await findUser(username);
    renderUserProfile(user);
    removeNewsContainer();
    renderNewsContainer(true);
    renderNews(user, true);
}

function switchHome()   {
    UI_ELEMENTS.PROFILE_PAGE.classList.add('hider');
    UI_ELEMENTS.MAIN_USER_NEWS_FEED.classList.add('hider');
    UI_ELEMENTS.POSTS_SECTION.classList.remove('hider');
    UI_ELEMENTS.ALL_POSTS_FEED.classList.remove('hider');
    renderAllPosts();
}

function switchProfile()    {
    UI_ELEMENTS.PROFILE_PAGE.classList.remove('hider');
    UI_ELEMENTS.MAIN_USER_NEWS_FEED.classList.remove('hider');
    
}

async function openMainProfile()    {
    let currentUser = await getCurrentUser();
    renderSignIn(currentUser);
    UI_ELEMENTS.POSTS_SECTION.classList.remove('hider');
}

async function sendUserPost()  {
    let user = {
        title: "#",
        description: UI_ELEMENTS.SEND_POST_INPUT.value,
        imageURL: "#",
        tags: [
            "cool",
            "very",
            "sometimes",
            "hello"
        ]
    }

    await postNews(user);
    switchProfile()
    openMainProfile();

}

