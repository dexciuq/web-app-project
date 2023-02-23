import { UI_ELEMENTS } from "./views.js";
import { getAllPosts } from "./server.js";

function renderSignIn(user)  {

    UI_ELEMENTS.AUTHORIZATION_SECTION.classList.add('hider');
    UI_ELEMENTS.FULLSCREEN_SECTION.classList.add('hider');
    UI_ELEMENTS.PERSONAL_PROFILE_PAGE.classList.remove('hider');
    
    renderUserProfile(user);
    renderUserPosts(user);
    removeNewsContainer();
    renderNewsContainer(false);
    renderNews(user);
}

function renderNewsContainer(personal = false)  {
    let div = document.createElement('div');
    div.classList.add('news-container');

    if(personal === false)    {
        UI_ELEMENTS.MAIN_USER_NEWS_FEED.append(div);
    }else{
        UI_ELEMENTS.ALL_POSTS_FEED.append(div);
    }

}

function removeNewsContainer()  {
    try{
        document.querySelector('.news-container').remove();
    }catch(err){
        console.log(err)
    }
    
}

function renderNews(user, personal = false)   {

    for(let i = 0; i < user.posts.length; i++)   {
        let clon = UI_ELEMENTS.POST_TEMPLATE.content.cloneNode(true);

        clon.querySelector('#post-avatar').setAttribute('src', user?.userProfile?.profilePictureURL);
        clon.querySelector('.user').textContent = user?.username;
        clon.querySelector('.username').textContent = user?.email;
        clon.querySelector('.date').textContent = user?.posts[i]?.creationDate;
        clon.querySelector('.text').textContent = user?.posts[i]?.description;
        clon.querySelector('#post-img').setAttribute('src', user?.posts[i]?.imageURL);

        if(personal === false)    {
            UI_ELEMENTS.MAIN_USER_NEWS_FEED.querySelector('.news-container').prepend(clon);
        }else{
            UI_ELEMENTS.ALL_POSTS_FEED.querySelector('.news-container').prepend(clon);
        }
        
    }

}

function renderUser(user)  {

    let clon = UI_ELEMENTS.USERS_RECOMMENDATIONS.content.cloneNode(true);

    clon.querySelector('#img-recommendations').setAttribute('src', user.userProfile.profilePictureURL)
    clon.querySelector('#username-recommendations').textContent = user.username;
    clon.querySelector('#email-recommendations').textContent = user.email;

    UI_ELEMENTS.RECOMMENDATIONS_LIST.append(clon);

}

function renderUserProfile(user)    {

    UI_ELEMENTS.USER_PROFILE_TITLE.textContent = user.username;
    UI_ELEMENTS.PROFILE_AVATAR.setAttribute('src', user.userProfile.profilePictureURL);
    UI_ELEMENTS.PROFILE_USERNAME.textContent = user.username;
    UI_ELEMENTS.PROFILE_EMAIL.textContent = user.email;
    UI_ELEMENTS.PROFILE_REGISTRATION_DATE.textContent = `Joined: ${user.registrationDate}`;
    UI_ELEMENTS.PROFILE_FOLLOWING.textContent = `Following: ${user.following.length ?? 0}`;
    UI_ELEMENTS.PROFILE_FOLLOWERS.textContent = `Followers: ${user.followers.length ?? 0}`;
    
}

function renderUserPosts(user)  {
    UI_ELEMENTS.POST_BLOCK_AVATAR.setAttribute('src', user.userProfile.profilePictureURL);
}

async function renderAllPosts()   {
    let posts = await getAllPosts();
    console.log(posts);
    removeNewsContainer();
    renderNewsContainer(true);

    for(let i = 0; posts.length; i++)   {   
        let clon = UI_ELEMENTS.POST_TEMPLATE.content.cloneNode(true);

        clon.querySelector('#post-avatar').setAttribute('src', posts[i].postOwner?.userProfile?.profilePictureURL);
        clon.querySelector('.user').textContent = posts[i]?.postOwner?.username;
        clon.querySelector('.username').textContent = posts[i]?.postOwner?.email;
        clon.querySelector('.date').textContent = posts[i]?.creationDate;
        clon.querySelector('.text').textContent = posts[i]?.description;
        clon.querySelector('#post-img').setAttribute('src', posts[i]?.imageURL);

        UI_ELEMENTS.ALL_POSTS_FEED.querySelector('.news-container').prepend(clon);

    }


}

export {
    renderAllPosts,
    renderSignIn,
    renderUser,
    renderUserPosts,
    renderUserProfile,
    renderNews,
    renderNewsContainer,
    removeNewsContainer,
}

