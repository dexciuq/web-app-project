import Cookies from 'js-cookie'
import { UI_ELEMENTS } from './views.js';

const SERVER_DATA = {
    SERVER_URL: 'http://localhost:8080',

}

async function getAllUsers()    {
    let response = await fetch(`${SERVER_DATA.SERVER_URL}/users`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${Cookies.get('token')}`,
        },
    })

    let result = await response.json();
    console.log(result)
    return result;
}

async function findUser(user)    {
    let response = await fetch(`${SERVER_DATA.SERVER_URL}/users/search?username=${user}`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${Cookies.get('token')}`,
        },
    })

    let result = await response.json();
    return result;
}

async function registration(user)   {

    let response = await fetch(`${SERVER_DATA.SERVER_URL}/auth/register`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8',
            'Accept': 'application/json',
            },
            body: JSON.stringify(user)
    })

    let result = await response.json();
    console.log(result);
    return result;
}

async function auth(user)   {

    let response = await fetch(`${SERVER_DATA.SERVER_URL}/auth/authenticate`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8',
            },
            body: JSON.stringify(user)
    })

    let result = await response.json();
    return result;

}

async function getAllPosts()    {

    let response = await fetch(`${SERVER_DATA.SERVER_URL}/posts`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${Cookies.get('token')}`,
            },
    })

    let result = await response.json();
    console.log(result)
    return result;
}

getAllPosts(); 

async function getCurrentUser()     {
    let user = {
        token: Cookies.get('token'),
    }
    let response = await fetch(`${SERVER_DATA.SERVER_URL}/users/token`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8',
            'Authorization': `Bearer ${Cookies.get('token')}`,
        },
        body: JSON.stringify(user)
    })

    let result = await response.json();
    console.log(result);
    return result;
}

async function postNews(user)   {
    let response = await fetch(`${SERVER_DATA.SERVER_URL}/posts`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8',
            'Authorization': `Bearer ${Cookies.get('token')}`,
        },
        body: JSON.stringify(user)
    })

    let result = await response.json();
    console.log(result);
    return result;
}

async function logout()     {
    let response = await fetch(`${SERVER_DATA.SERVER_URL}/auth/logout`, {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${Cookies.get('token')}`,
            },
    })

    let result = await response.json();
    console.log(result)
    return result;
}

async function updateUser(user, userID)     {
    let response = await fetch(`${SERVER_DATA.SERVER_URL}/users/${userID}`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json;charset=utf-8',
            'Authorization': `Bearer ${Cookies.get('token')}`,
            },
        body: JSON.stringify(user)
    })

    let result = await response.json();
    console.log(result)
    return result;
}

async function healthcheck()    {
    let response = await fetch(`${SERVER_DATA.SERVER_URL}/healthcheck`, {
        method: 'GET',
    })

    let result = await response.text();
    console.log(result);
    return result;
}

healthcheck();

async function getUserById(userID)    {
    let response = await fetch(`${SERVER_DATA.SERVER_URL}/users/${userID}`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${Cookies.get('token')}`,
        },
    })

    let result = await response.json();
    console.log(result);
    return result;
}

async function deleteUserById(userID)    {
    let response = await fetch(`${SERVER_DATA.SERVER_URL}/users/${userID}`, {
        method: 'DELETE',
        headers: {
            'Authorization': `Bearer ${Cookies.get('token')}`,
        },
    })

    let result = await response.json();
    console.log(result);
    return result;
}

async function updateUserById(userID, user)    {
    let response = await fetch(`${SERVER_DATA.SERVER_URL}/users/${userID}`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json;charset=utf-8',
            'Authorization': `Bearer ${Cookies.get('token')}`,
        },
        body: JSON.stringify(user)
    })

    let result = await response.json();
    console.log(result);
    return result;
}

async function followUserByUsername(username)    {
    let response = await fetch(`${SERVER_DATA.SERVER_URL}/users/follow/${username}`, {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${Cookies.get('token')}`,
        },
    })

    let result = await response.text();
    console.log(result);
    return result;
}

async function unfollowUserByUsername(username)    {
    let response = await fetch(`${SERVER_DATA.SERVER_URL}/users/follow/${username}`, {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${Cookies.get('token')}`,
        },
    })

    let result = await response.text();
    console.log(result);
    return result;
}

async function searchPost(tag, title)    {
    let response = await fetch(`${SERVER_DATA.SERVER_URL}/posts/search?tag=${tag}&title=${title}`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${Cookies.get('token')}`,
        },
    })

    let result = await response.json();
    console.log(result);
    return result;
}

async function getPostById(postID)    {
    let response = await fetch(`${SERVER_DATA.SERVER_URL}/posts/${postID}`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${Cookies.get('token')}`,
        },
    })

    let result = await response.json();
    console.log(result);
    return result;
}

async function deletePostById(postID)    {
    let response = await fetch(`${SERVER_DATA.SERVER_URL}/posts/${postID}`, {
        method: 'DELETE',
        headers: {
            'Authorization': `Bearer ${Cookies.get('token')}`,
        },
    })

    let result = await response.text();
    console.log(result);
    return result;
}

async function postComment(postID, comment)    {
    let response = await fetch(`${SERVER_DATA.SERVER_URL}/posts/${postID}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8',
            'Authorization': `Bearer ${Cookies.get('token')}`,
        },
        body: JSON.stringify(comment),
    })

    let result = await response.json();
    console.log(result);
    return result;
}

async function updateComment(postID, comment)    {
    let response = await fetch(`${SERVER_DATA.SERVER_URL}/posts/${postID}/${comment.id}`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json;charset=utf-8',
            'Authorization': `Bearer ${Cookies.get('token')}`,
        },
        body: JSON.stringify(comment)
    })

    let result = await response.json();
    console.log(result);
    return result;
}

async function deleteComment(postID, comment)    {
    let response = await fetch(`${SERVER_DATA.SERVER_URL}/posts/${postID}/${comment.id}`, {
        method: 'DELETE',
        headers: {
            'Authorization': `Bearer ${Cookies.get('token')}`,
        },
    })

    let result = await response.json();
    console.log(result);
    return result;
}






export {
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
    updateComment
}